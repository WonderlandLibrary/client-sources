#include "Main.h"
#include "Auth.h"
#include "util/hwid.h"
#include "imgui/fonts/icons2.h"
#include "imgui/fonts/Montserrat.h"
#include "imgui/fonts/OpenSans.h"
#include "imgui/imgui.h"
#include "imgui/imgui_impl_win32.h"
#include "imgui/imgui_impl_dx9.h"
#include <d3dx9tex.h>
#include "vpn.h"
#include "checks/signature.h"
#include "checks/usn.h"
#include "util/registry.h"
#include <winnt.h>
#include "resource.h"
//#define DEBUG
bool output = false;

HANDLE ph_checker_thread = NULL;
HANDLE kernel_checker_thread = NULL;
HANDLE timeout_thread = NULL;
int timeout = 30;
									
using json = nlohmann::json;

#ifndef DEBUG
KernelInterface *driver;
DWORD lsass_pid = 0;
#endif

void Scan();

Scanner *scan = 0;
Auth *auth = 0;
HWID h;
json j;
char pinchar[6] = "";
std::string id = "";

std::string drive_serial;
std::string board_serial;
std::string cpu_model;

std::string b2s(bool b) { return b ? xors("true") : xors("false"); }

static LPDIRECT3DDEVICE9        g_pd3dDevice = NULL;
static D3DPRESENT_PARAMETERS    g_d3dpp;
static LPDIRECT3D9              pD3D;

namespace ImGui 
{
	void CenteredText(const char *text, int width)
	{
		ImVec2 s = ImGui::CalcTextSize(text);

		ImGui::SetCursorPosX((width - s.x) / 2);
		ImGui::Text(text);
	}
}

static std::string WindowsVersionString()
{
	VMProtectBeginMutation("windowsversionstring");
	RTL_OSVERSIONINFOW i = WindowsVersion();

	std::string ver = "";
	ver.append(std::to_string(i.dwMajorVersion));
	ver.append(".");
	ver.append(std::to_string(i.dwMinorVersion));
	ver.append(".");
	ver.append(std::to_string(i.dwBuildNumber));

	DWORD ubr = GetRegValue(HKEY_LOCAL_MACHINE, REG_DWORD, xors("SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion"), xors("UBR"));
	if (ubr != -69)
	{
		ver.append(".");
		ver.append(std::to_string(ubr));
	}

	return ver;
	VMProtectEnd();
}

bool check_parentprocess()
{
	#ifdef DEBUG
	return true;
	#endif
	DWORD parent_id = FindProcessParentId(GetCurrentProcessId());
	wchar_t parent_path[MAX_PATH];
	std::wstring parent_path_s;

	GetModuleFileNameExW(OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, false, parent_id), NULL, parent_path, MAX_PATH);
	parent_path_s = parent_path;

	std::wstring real_explorer_path = _wgetenv(L"SYSTEMDRIVE");
	real_explorer_path.append(L"\\Windows\\explorer.exe");

	bool has_explorer = (parent_path_s.find(L"explorer.exe") != std::string::npos);
	bool is_real_explorer = _wcsicmp(parent_path_s.c_str(), real_explorer_path.c_str()) == 0;
	bool signed_exe = true;

	auto winver = WindowsVersion();
	if (winver.dwMajorVersion >= 10) {
		signed_exe = util::verifyembeddedsignature(parent_path);
	}

	return (has_explorer && is_real_explorer && signed_exe);
}

HWND hwnd;
std::pair<DWORD, HWND> java_window;
WNDCLASSEX wc;

ImFont* font1;
ImFont* font2;
ImFont* font3;
ImFont* icons;

int width = 690;
int height = 490;

int stage = 1;
int extra_stage = 0;
int extra_extra_stage = 0;

MSG msg;
bool alive = true;

bool has_scanned = false;

int64_t time_to_finish = 0;

std::string detection_message = xors("Scan complete");
std::string extra_detection_message = "";

void exit_with_message(std::string msg)
{
	VMProtectBeginMutation("exit_with_message");
	alive = false;
	#ifndef DEBUG
	if (driver->Alive)
		driver->Stop();
	#endif
	ShellExecute(0, xors("runas"), xors("cmd.exe"), msg.c_str(), 0, SW_SHOW);
	exit(0);
	VMProtectEnd();
}

extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
		return true;

	switch (msg)
	{
		case WM_SIZE:
		{
			if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED)
			{
				ImGui_ImplDX9_InvalidateDeviceObjects();
				g_d3dpp.BackBufferWidth = LOWORD(lParam);
				g_d3dpp.BackBufferHeight = HIWORD(lParam);
				HRESULT hr = g_pd3dDevice->Reset(&g_d3dpp);
				if (hr == D3DERR_INVALIDCALL)
					IM_ASSERT(0);
				ImGui_ImplDX9_CreateDeviceObjects();
			}

			return 0;
		}
		case WM_SYSCOMMAND:
		{
			if ((wParam & 0xfff0) == SC_KEYMENU) // Disable ALT application menu
				return 0;

			break;
		}
		case WM_DESTROY:
		{
			PostQuitMessage(0);
			DestroyWindow(hwnd);
			return 0;
		}
		case WM_CLOSE:
		{
			alive = false;
			return 0;
		}
	}

	return DefWindowProc(hWnd, msg, wParam, lParam);
}

bool replace(std::string& str, const std::string& from, const std::string& to) {
	size_t start_pos = str.find(from);

	if (start_pos == std::string::npos)
		return false;

	str.replace(start_pos, from.length(), to);

	return true;
}

void check_process_hacker()
{
	VMProtectBeginMutation("check_process_hacker");
	SC_HANDLE service_manager = OpenSCManager(NULL, NULL, SC_MANAGER_ALL_ACCESS);

	if (!service_manager)
		return;

	while (alive)
	{
		SC_HANDLE h1 = OpenService(service_manager, xors("KProcessHacker3"), SERVICE_QUERY_STATUS);

		if (h1 != NULL)
		{
			SERVICE_STATUS ss;

			if (QueryServiceStatus(h1, &ss) && ss.dwCurrentState == SERVICE_STOPPED)
			{
				CloseServiceHandle(h1);
				continue;
			}

			CloseServiceHandle(h1);

			SC_HANDLE h2 = OpenService(service_manager, xors("KProcessHacker3"), SERVICE_STOP);

			SERVICE_STATUS ServiceStatus;

			ControlService(h2, SERVICE_CONTROL_STOP, &ServiceStatus);
			CloseServiceHandle(h2);

			Sleep(1000);

			SC_HANDLE h3 = OpenService(service_manager, xors("KProcessHacker3"), SERVICE_QUERY_STATUS);

			if (h3 != NULL)
			{
				exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo Please close Process Hacker and reopen Paladin & echo. & pause"));
			}

			CloseServiceHandle(h3);
		}

		Sleep(500);
	}

	CloseServiceHandle(service_manager);
	VMProtectEnd();
}
void check_driver()
{
	VMProtectBeginMutation("check_driver");
	SC_HANDLE service_manager = OpenSCManager(NULL, NULL, SC_MANAGER_ALL_ACCESS);
	if (!service_manager)
		return;

	while (alive)
	{
		SC_HANDLE h1 = OpenService(service_manager, xors("PaladinDriver"), SERVICE_QUERY_STATUS);
		if (h1 != NULL)
		{
			SERVICE_STATUS ss;

			if (QueryServiceStatus(h1, &ss) && (ss.dwCurrentState == SERVICE_STOPPED || ss.dwCurrentState == SERVICE_STOP_PENDING))
			{
				exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo Tampering with the kernel driver has been detected & echo Please close any \"Paladin Killers\" you have open & echo. & pause"));
			}
			CloseServiceHandle(h1);
		}
		else
		{
			exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo Tampering with the kernel driver has been detected & echo Please close any \"Paladin Killers\" you have open & echo. & pause"));
		}
		
		Sleep(2500);
	}

	CloseServiceHandle(service_manager);
	VMProtectEnd();
}
void check_timeout()
{
	while (alive && !has_scanned)
	{
		if (timeout < 0)
		{
			alive = false;
			#ifndef DEBUG
			driver->Stop();
			#endif
			exit(0);
		}
		Sleep(1000);
		timeout--;
	}
}

void authenticate()
{
	VMProtectBeginVirtualization("authenticate");
	extra_stage = 1;
	extra_extra_stage = -3;

	std::vector<std::pair<DWORD, HWND>> windows = util::findprocessesbywindowclass(xors("LWJGL"));

	if (windows.size() != 1)
	{
		stage = -3;
		extra_stage = 0;
		return;
	}
	else
	{
		auto w = windows.at(0);
		java_window = w;
	}

	std::string* main_key = new std::string(xorr(xors("3qeDJWmxv4qnwEE4c2aBRGZaXEu6evVHDfrYZuNjBnApAe4aMgnSw7LqtRXda98ERDA8NdbamHeThZGWpxUDwtM3vnLth8sWtQR5"), drive_serial.c_str(), drive_serial.size()));
	
	int authi = -999;
	authi = auth->authenticate(std::string(pinchar), id, j, main_key, drive_serial, board_serial, cpu_model);

	extra_extra_stage = -2;

	delete main_key;

	switch (authi) {
		case Auth::ABUSE:
		{
			stage = -1;
			break;
		}
		case Auth::ERR:
		{
			MessageBoxA(hwnd, xors("An error has happened while contacting the server."), xors("Paladin"), MB_ICONERROR);
			break;
		}
		case Auth::MAINTENANCE:
		{
			MessageBoxA(hwnd, xors("Maintenance, check back later."), xors("Paladin"), MB_ICONERROR);
			break;
		}
		case Auth::WRONGPIN:
		{
			stage = -2;
			break;
		}
		case Auth::OUTDATED:
		{
			MessageBoxA(hwnd, xors("This version is outdated, please download a newer one."), xors("Paladin"), MB_ICONERROR);
			break;
		}
		case Auth::AUTHENTICATED:
		{
			has_scanned = true;
			stage = 3;
			extra_extra_stage = 0;

			Sleep(10);

			HANDLE scan_thread = CreateThread(NULL, 4096, reinterpret_cast<LPTHREAD_START_ROUTINE>(Scan), NULL, NULL, NULL);
			break;
		}
	}

	extra_stage = 0;
	VMProtectEnd();
}

std::vector<std::tuple<std::string, std::string>> *j2v(json j)
{
	std::vector<std::tuple<std::string, std::string>> *k = new std::vector<std::tuple<std::string, std::string>>();

	for (json::iterator jj = j.begin(); jj != j.end(); ++jj)
	{
		for (std::string client_string : jj.value()) 
		{
			k->push_back(std::tuple<std::string, std::string>(jj.key(), client_string));
		}
	}

	return k;
}

void exceptionmessage(int stage, std::string what)
{
	std::stringstream str;

	str << xors("An exception has occured!\nProvide this info to staff.\n\nStage: ");
	str << xors("0x") << std::setfill('0') << std::uppercase << std::hex << stage;
	str << xors("\nType: ") + what;

	MessageBoxA(NULL, str.str().c_str(), xors("Paladin"), MB_ICONERROR);
}

json usnjson;
bool usn_done = false;
void usn_from_thread() {

	usn::search_records();
	usn::scan_records();
	usnjson[xors("m")] = usn::final_mods;
	usnjson[xors("f")] = usn::final_files;
	usnjson[xors("o")] = usn::final_other;
	usnjson[xors("sf")] = usn::final_self_files;
	usnjson[xors("p")] = usn::final_prefetch;

	usn_done = true;
}

void Scan()
{
	VMProtectBeginMutation("scan");
	stage = 3;
	extra_extra_stage = 1;

	auto keks = util::findhandlestoprocess(0);
	for (auto k : keks)
	{
		HANDLE gtfo = OpenProcess(PROCESS_TERMINATE, false, k.first);
		TerminateProcess(gtfo, 0);
		std::cout << k.first << " " << k.second.first << " " << k.second.second << std::endl;
	}

	auto start = std::chrono::system_clock::now();

	json string_array;
	json rdi_array;

	try
	{
		std::string stringarraystring = j[xors("s")].get<std::string>();
		std::string rdiarraystring = j[xors("rdi")].get<std::string>();

		string_array = json::parse(stringarraystring.c_str());
		rdi_array = json::parse(rdiarraystring.c_str());

		stringarraystring = xors("k");
		rdiarraystring = xors("k");

		j.clear();
	}
	catch (const std::exception& ex) 
	{
		exceptionmessage(3652, ex.what());

		exit(0);
	}
	catch (const std::string& ex) 
	{
		exceptionmessage(3652, ex);

		exit(0);
	}

	extra_extra_stage = 2;

	std::vector<std::string> failed;
	std::vector<std::tuple<std::string, std::string>> detected_java;
	std::vector<std::tuple<std::string, std::string>> detected_strings;
	std::vector<std::tuple<std::string, std::string>> detected_registry;

	FILETIME java_createtime;
	FILETIME java_exittime;
	FILETIME java_kerneltime;
	FILETIME java_usertime;

	auto win_ver = WindowsVersion();

	DWORD P_JV;
	DWORD P_DNS;
	DWORD P_PCA;
	DWORD P_DPS;
	DWORD P_MSMP;
	DWORD P_NIS = FindProcessId(xors("NisSrv.exe"));
	DWORD P_CTF = FindProcessId(xors("ctfmon.exe"));

	if (java_window.first)
		P_JV = java_window.first;
	else
		P_JV = FindProcessId(xors("javaw.exe"));

	if (win_ver.dwMajorVersion >= 10)
	{
		P_DNS = get_svc_host(L"Dnscache");
		P_PCA = get_svc_host(L"PcaSvc");
		P_DPS = get_svc_host(L"-s DPS");
		P_MSMP = FindProcessId(xors("MsMpEng.exe"));
	}
	else
	{
		P_DNS = get_svc_host(L"NetworkService"); // Windows 7, 8?
		P_PCA = get_svc_host(L"LocalSystemNetworkRestricted"); // Windows 7, 8?
		P_MSMP = get_svc_host(L"secsvcs"); // Windows 7, 8?
	}

	extra_extra_stage = 3;

	std::thread usn1(usn_from_thread);
	usn1.detach();

	try
	{
		if (P_DNS != 12345678)
		{
			HANDLE H_DNS = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_DNS);

			if (!H_DNS) 
			{
				std::cout << xors("Failed to open Dnscache handle") << std::endl;
				failed.push_back(xors("H_DNS"));
			}
			else if (!string_array[xors("dns")].empty()) 
			{
				if (output) 
					std::cout << xors("Scanning DNSCache...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *dns_detection_vector = j2v(string_array[xors("dns")]);

				scan->run(H_DNS, dns_detection_vector, &detected_strings);

				delete dns_detection_vector;
			}
		}
		else failed.push_back(xors("P_DNS"));

		if (P_PCA != 12345678)
		{
			HANDLE H_PCA = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_PCA);

			if (!H_PCA) {
				std::cout << xors("Failed to open PcaSvc handle") << std::endl;
				failed.push_back(xors("H_PCA"));
			}
			else if (!string_array[xors("pcasvc")].empty()) 
			{
				if (output) 
					std::cout << xors("Scanning Pcasvc...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *pcasvc_detection_vector = j2v(string_array[xors("pcasvc")]);

				scan->run(H_PCA, pcasvc_detection_vector, &detected_strings, 9173);

				delete pcasvc_detection_vector;
			}
		}
		else failed.push_back(xors("P_PCA"));
		
		if (P_CTF != NULL)
		{
			HANDLE H_CTF = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_CTF);

			if (!H_CTF) {
				std::cout << xors("Failed to open Ctfmon handle") << std::endl;
				failed.push_back(xors("H_CTF"));
			}
			else
			{
				if (output)
					std::cout << xors("Scanning CTF...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *ctf_detection_vector = j2v(string_array[xors("ctf")]);

				scan->run(H_CTF, ctf_detection_vector, &detected_strings, 1);

				delete ctf_detection_vector;
			}
		}
		else failed.push_back(xors("P_CTF"));

		if (P_DPS != 12345678)
		{
			HANDLE H_DPS = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_QUERY_INFORMATION, false, P_DPS);

			if (!H_DPS) {
				std::cout << xors("Failed to open DPS handle") << std::endl;
				failed.push_back(xors("H_DPS"));
			}
			else
			{
				if (output)
					std::cout << xors("Scanning DPS...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *dps_detection_vector = j2v(string_array[xors("dps")]);

				scan->run(H_DPS, dps_detection_vector, &detected_strings, 1932);

				delete dps_detection_vector;
			}
		}
		else failed.push_back(xors("P_DPS"));

		if (P_JV != NULL)
		{
			HANDLE H_JV = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_JV);

			if (!H_JV) {
				std::cout << std::endl << xors("Failed to open javaw handle") << std::endl;
				failed.push_back(xors("H_JV"));
			}
			else if (!string_array[xors("javaw")].empty()) {
				if (output)
					std::cout << xors("Scanning Java...") << std::endl;

				GetProcessTimes(H_JV, &java_createtime, &java_exittime, &java_kerneltime, &java_usertime);

				std::vector<std::tuple<std::string, std::string>> *javaw_detection_vectors = j2v(string_array[xors("javaw")]);

				scan->run(H_JV, javaw_detection_vectors, &detected_java, 2);

				delete javaw_detection_vectors;

				std::vector<std::tuple<std::string, std::string>> *directinput_detection_vectors = j2v(rdi_array);
				HKEY hDirectInputKey;
				if (RegOpenKeyEx(HKEY_CURRENT_USER, xors("SOFTWARE\\Microsoft\\DirectInput"), 0, KEY_READ, &hDirectInputKey) == ERROR_SUCCESS)
				{
					auto k = QuerySubKeys(hDirectInputKey);
					std::vector<std::tuple<std::string, std::string>> *directinput_strings = j2v(rdi_array);
					for (auto a : k)
					{
						if (a.first.find(xors(".EXE")) == std::string::npos && a.first.find(xors(".exe")) == std::string::npos)
							continue;

						int dif = (int)std::difftime(std::time(nullptr), a.second);
						if (a.second < filetime_to_time_t(java_createtime))
							continue;

						for (auto it = directinput_detection_vectors->begin(); it != directinput_detection_vectors->end(); ++it)
						{
							if (a.first.find(std::get<1>(*it)) != std::string::npos)
								detected_registry.push_back(std::tuple(std::get<0>(*it), std::get<1>(*it)));
						}
					}
				}
			}
		}
		else failed.push_back(xors("P_JV"));

		if (P_MSMP != NULL)
		{
			int old_prot = 0;

			#ifndef DEBUG
			driver->Unprotect(P_MSMP, old_prot);
			#endif

			HANDLE H_MSMP = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_MSMP);

			if (!H_MSMP) {
				std::cout << xors("Failed to open MsMpEng handle") << std::endl;
				failed.push_back(xors("H_MSMP"));
			}
			else if (!string_array[xors("msmp")].empty()) {
				if (output) 
					std::cout << xors("Scanning MsMpEng...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *msmp_detection_vectors = j2v(string_array[xors("msmp")]);

				scan->run(H_MSMP, msmp_detection_vectors, &detected_strings, 5);

				delete msmp_detection_vectors;
			}

			#ifndef DEBUG
			driver->Protect(P_MSMP, old_prot);
			#endif
		}
		else failed.push_back(xors("P_MSMP"));

		if (P_NIS != NULL)
		{
			int oldprot = 0;

			#ifndef DEBUG
			driver->Unprotect(P_NIS, oldprot);
			#endif

			HANDLE H_NIS = OpenProcess(PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE | PROCESS_QUERY_INFORMATION, false, P_NIS);

			if (!H_NIS) {
				std::cout << xors("Failed to open NisSrv handle") << std::endl;
				failed.push_back(xors("H_NIS"));
			}
			else if (!string_array[xors("nissrv")].empty()) {
				if (output) 
					std::cout << xors("Scanning NisSrv...") << std::endl;

				std::vector<std::tuple<std::string, std::string>> *nissrv_detection_vectors = j2v(string_array[xors("nissrv")]);

				scan->run(H_NIS, nissrv_detection_vectors, &detected_strings, 5);

				delete nissrv_detection_vectors;
			}

			#ifndef DEBUG
			driver->Protect(P_NIS, oldprot);
			#endif
		}
		else failed.push_back(xors("P_NIS"));
	}
	catch (const std::exception& ex) 
	{
		exceptionmessage(6331, ex.what());
		exit(0);
	}

	string_array.erase(xors("dns"));
	string_array.erase(xors("pcasvc"));
	string_array.erase(xors("ctf"));
	string_array.erase(xors("dps"));
	string_array.erase(xors("javaw"));
	string_array.erase(xors("msmp"));
	string_array.erase(xors("nissrv"));

	extra_extra_stage = 4;

	try
	{
		string_array.clear();
		rdi_array.clear();
		delete scan;
	}
	catch (const std::exception& ex) 
	{
		exceptionmessage(7655, ex.what());
		exit(0);
	}

	extra_extra_stage = 5;

	while (!usn_done)
		Sleep(100);
	if (usn::count < 10)
		failed.push_back(xors("U"));

	auto end = std::chrono::system_clock::now();

	time_to_finish = std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count();

	json results;
	try
	{
		std::string winver = WindowsVersionString();

		results[xors("dj")] = detected_java;
		results[xors("ds")] = detected_strings;
		results[xors("dr")] = detected_registry;
		results[xors("du")] = usnjson;

		results[xors("wv")] = winver;
		results[xors("wu")] = WindowsUsername();
		results[xors("nb")] = WindowsNetbios();

		results[xors("mca")] = AccountList();
		char windowtitle[1024];
		GetWindowText(java_window.second, windowtitle, 1024);
		results[xors("mct")] = windowtitle;
		results[xors("mcs")] = (int)filetime_to_time_t(java_createtime);

		results[xors("took")] = time_to_finish;
		results[xors("rb")] = recyclebin();
		results[xors("vpn")] = vpn_check::is_using_vpn();
		results[xors("vm")] = VMProtectIsVirtualMachinePresent();

		if (failed.size() != 0)
			results[xors("f")] = failed;
		if (Paladin::gErrors.size() != 0)
			results[xors("e")] = Paladin::gErrors;
	}
	catch (const std::exception& ex) 
	{
		exceptionmessage(32583, ex.what());
		exit(0);
	}

	extra_extra_stage = 6;

	try
	{
		int succ = auth->send_results(id, str2hex(results.dump(-1, ' ', false, json::error_handler_t::ignore)));

		if (succ != Auth::AUTHENTICATED) {
			MessageBoxA(hwnd, std::string(xors("Failed to send results to server.\n") + GetLastErrorAsString()).c_str(), xors("Paladin"), MB_ICONERROR);
			extra_detection_message = xors("Failed to send results ") + std::to_string(GetLastError());
		}
		results.clear();
	}
	catch (const std::exception& ex) {
		exceptionmessage(8815, ex.what());
		exit(0);
	}

	extra_extra_stage = 7;

	detection_message = detection_message + xors(" (") + std::to_string(time_to_finish) + xors("ms)");

	stage = 4;

	auto keks2 = util::findhandlestoprocess(0);
	for (auto k : keks2)
	{
		if (k.first == java_window.first)
			continue;
		HANDLE gtfo = OpenProcess(PROCESS_TERMINATE, false, k.first);
		TerminateProcess(gtfo, 0);
		std::cout << k.first << " " << k.second.first << " " << k.second.second << std::endl;
	}

	VMProtectEnd();
}

int messages()
{
	ImGuiStyle* style = &ImGui::GetStyle();
	ImGuiIO& io = ImGui::GetIO();

	style->Alpha = 1.0f;
	style->WindowPadding = ImVec2(0, 0);
	style->WindowRounding = 0.0f;
	style->FramePadding = ImVec2(5, 5);
	style->FrameRounding = 2.0f;
	style->ItemSpacing = ImVec2(5, 5);
	style->ItemInnerSpacing = ImVec2(5, 5);
	style->IndentSpacing = 10.0f;
	style->TouchExtraPadding = ImVec2(5, 5);
	style->ScrollbarSize = 13.0f;
	style->ScrollbarRounding = 15.0f;
	style->GrabMinSize = 10.0f;
	style->GrabRounding = 2.0f;
	style->ColumnsMinSpacing = 10.0f;
	style->WindowBorderSize = 0;

	style->Colors[ImGuiCol_Text] = ImVec4(1.f, 1.f, 1.f, 1.f);
	style->Colors[ImGuiCol_TextDisabled] = ImVec4(1.00f, 1.00f, 1.00f, 0.57f);
	style->Colors[ImGuiCol_WindowBg] = ImVec4(0.02f, 0.02f, 0.02f, 1.f);
	style->Colors[ImGuiCol_ChildWindowBg] = ImVec4(0.02f, 0.02f, 0.02f, 1.f);
	style->Colors[ImGuiCol_PopupBg] = ImVec4(0.02f, 0.02f, 0.02f, 0.90f);
	style->Colors[ImGuiCol_Border] = ImVec4(1.00f, 1.00f, 1.00f, 0.00f);
	style->Colors[ImGuiCol_BorderShadow] = ImVec4(1.00f, 1.00f, 1.00f, 0.00f);
	style->Colors[ImGuiCol_FrameBg] = ImVec4(0.341f, 0.f, 0.624f, 0.5f);
	style->Colors[ImGuiCol_FrameBgHovered] = ImVec4(0.204f, 0.596f, 0.859f, 0.5f);
	style->Colors[ImGuiCol_FrameBgActive] = ImVec4(0.204f, 0.596f, 0.859f, 0.9f);
	style->Colors[ImGuiCol_TitleBg] = ImVec4(0.008f, 0.008f, 0.008f, 1.00f);
	style->Colors[ImGuiCol_TitleBgCollapsed] = ImVec4(0.008f, 0.008f, 0.008f, 1.00f);
	style->Colors[ImGuiCol_TitleBgActive] = ImVec4(0.008f, 0.008f, 0.008f, 1.00f);
	style->Colors[ImGuiCol_MenuBarBg] = ImVec4(0.14f, 0.28f, 0.34f, 0.57f);
	style->Colors[ImGuiCol_ScrollbarBg] = ImVec4(0.20f, 0.34f, 0.39f, 1.00f);
	style->Colors[ImGuiCol_ScrollbarGrab] = ImVec4(0.19f, 0.50f, 0.63f, 0.31f);
	style->Colors[ImGuiCol_ScrollbarGrabHovered] = ImVec4(0.19f, 0.50f, 0.63f, 0.78f);
	style->Colors[ImGuiCol_ScrollbarGrabActive] = ImVec4(0.19f, 0.50f, 0.63f, 1.00f);
	style->Colors[ImGuiCol_CheckMark] = ImVec4(0.204f, 0.596f, 0.859f, 1.00f);
	style->Colors[ImGuiCol_SliderGrab] = ImVec4(0.204f, 0.596f, 0.859f, 1.00f);
	style->Colors[ImGuiCol_SliderGrabActive] = ImVec4(0.906f, 0.298f, 0.235f, 0.2f);

	style->Colors[ImGuiCol_Button] = ImVec4(0.341f, 0.f, 0.624f, 1.0f);
	style->Colors[ImGuiCol_ButtonHovered] = ImVec4(0.341f, 0.f, 0.624f, 0.9f);
	style->Colors[ImGuiCol_ButtonActive] = ImVec4(0.341f, 0.f, 0.624f, 0.8f);

	style->Colors[ImGuiCol_Header] = ImVec4(0.33f, 0.63f, 0.74f, 0.76f);
	style->Colors[ImGuiCol_HeaderHovered] = ImVec4(0.20f, 0.48f, 0.61f, 0.86f);
	style->Colors[ImGuiCol_HeaderActive] = ImVec4(0.19f, 0.50f, 0.63f, 1.00f);
	style->Colors[ImGuiCol_Separator] = ImVec4(1.00f, 1.00f, 1.00f, 1.f);
	style->Colors[ImGuiCol_SeparatorHovered] = ImVec4(1.00f, 1.00f, 1.00f, 0.78f);
	style->Colors[ImGuiCol_SeparatorActive] = ImVec4(1.00f, 1.00f, 1.00f, 1.00f);
	style->Colors[ImGuiCol_ResizeGrip] = ImVec4(0.19f, 0.50f, 0.63f, 0.20f);
	style->Colors[ImGuiCol_ResizeGripHovered] = ImVec4(0.19f, 0.50f, 0.63f, 0.78f);
	style->Colors[ImGuiCol_ResizeGripActive] = ImVec4(0.19f, 0.50f, 0.63f, 1.00f);
	style->Colors[ImGuiCol_PlotLines] = ImVec4(0.85f, 0.90f, 0.92f, 0.63f);
	style->Colors[ImGuiCol_PlotLinesHovered] = ImVec4(0.19f, 0.50f, 0.63f, 1.00f);
	style->Colors[ImGuiCol_PlotHistogram] = ImVec4(0.85f, 0.90f, 0.92f, 0.63f);
	style->Colors[ImGuiCol_PlotHistogramHovered] = ImVec4(0.19f, 0.50f, 0.63f, 1.00f);
	style->Colors[ImGuiCol_TextSelectedBg] = ImVec4(0.19f, 0.50f, 0.63f, 0.43f);
	style->Colors[ImGuiCol_ModalWindowDarkening] = ImVec4(0.20f, 0.20f, 0.20f, 0.35f);

	ImVec4 clear_color = ImVec4(0.45f, 0.55f, 0.60f, 1.00f);

	ShowWindow(hwnd, SW_SHOWDEFAULT);
	UpdateWindow(hwnd);

	std::string versionrender = xors("Paladin");

	int menu_movement_x = 0;
	int menu_movement_y = 0;

	//auto logoRes = FindResourceA(NULL, MAKEINTRESOURCE(IDB_LOGO), "PNG");
	//auto logoData = LockResource(LoadResource(NULL, logoRes));
	//auto logoSize = SizeofResource(NULL, logoRes);
	//
	//LPDIRECT3DTEXTURE9 logoTex = NULL;
	//D3DXCreateTextureFromFileInMemoryEx(g_pd3dDevice, logoData, logoSize, 500, 500, D3DX_DEFAULT, D3DUSAGE_DYNAMIC, D3DFMT_UNKNOWN, D3DPOOL_DEFAULT, D3DX_FILTER_LINEAR, D3DX_DEFAULT, 0, NULL, NULL, &logoTex);

	while (msg.message != WM_QUIT && alive) {
		
		if (PeekMessage(&msg, hwnd, 0, 0, PM_REMOVE))
		{
			if (msg.message == WM_QUIT)
				alive = false;

			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}

		Sleep(10);

		ImGui_ImplDX9_NewFrame();
		ImGui_ImplWin32_NewFrame();
		ImGui::NewFrame();
		ImGui::SetNextWindowPos(ImVec2(0, 0));

		const ImVec4 color = style->Colors[ImGuiCol_Button];
		const ImVec4 color_active = style->Colors[ImGuiCol_ButtonActive];
		const ImVec4 color_hover = style->Colors[ImGuiCol_ButtonHovered];

		ImGui::Begin(xors("Paladin"), nullptr, ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoNav); //I deleted ImGuiWindowFlags_NoMove so you can move the window around in fullscreen
		{
			if (ImGui::IsMouseClicked(0))
			{
				POINT CursorPosition;
				RECT MenuPosition;

				GetCursorPos(&CursorPosition);  //ratting & keylogging your every mouse movement
				GetWindowRect(hwnd, &MenuPosition);   // gets window size

				// calculating difference between the cursor position and window position so it knows where to move the menu
				menu_movement_x = CursorPosition.x - MenuPosition.left;
				menu_movement_y = CursorPosition.y - MenuPosition.top;
			}

			if ((menu_movement_y >= 0 && menu_movement_y <= 30) && ImGui::IsMouseDragging()) //only works on pixels between 0-25
			{
				POINT cursor_position;

				GetCursorPos(&cursor_position);

				SetWindowPos(hwnd, nullptr, cursor_position.x - menu_movement_x, cursor_position.y - menu_movement_y, 0, 0, SWP_NOSIZE);
			}

			const ImU32 col = ImGui::GetColorU32(ImGuiCol_ButtonHovered);
			const ImU32 bg = ImGui::GetColorU32(ImGuiCol_Button);

			ImGui::SetWindowSize(ImVec2((float)width, (float)height));

			ImGui::PushStyleColor(ImGuiCol_ChildWindowBg, ImVec4(0.341f, 0.f, 0.624f, 1.0f));
			ImGui::BeginChild(xors("footerChild"), ImVec2((float)width + 5, 35), false);
			{
				ImGui::PushFont(font1);
				ImGui::SetCursorPosY(10);
				ImGui::SetCursorPosX((width / 2) - (ImGui::CalcTextSize(versionrender.c_str()).x / 2));
				ImGui::Text(versionrender.c_str());
				ImGui::PopFont();

				ImGui::SetCursorPosX((float)width - 32);
				ImGui::SetCursorPosY(5);
				if (ImGui::Button(xors("X"), ImVec2(26, 26)))
					alive = false;
			}
			ImGui::EndChild();
			ImGui::PopStyleColor();

			switch (stage) {
				case -3:
				{
					ImGui::SetCursorPosX(((float)width - 585) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(585, 300), true);
					{
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("6"), 585);
						ImGui::PopFont();

						ImGui::PushFont(font3);
						ImGui::SetCursorPosY(110);
						ImGui::CenteredText(xors("Minecraft not found"), 585);
						ImGui::SetCursorPosY(150);
						ImGui::CenteredText(xors("Minecraft could not be found or you have multiple open."), 585);
						ImGui::PopFont();

						ImGui::SetCursorPosY(220);
						ImGui::SetCursorPosX(20);

						if (ImGui::Button(xors("Return"), ImVec2(270, 30)))
							stage = 1;

						ImGui::SameLine();

						if (ImGui::Button(xors("Exit"), ImVec2(270, 30)))
							alive = false;
					}

					ImGui::EndChild();
					break;
				}
				case -2:
				{
					ImGui::SetCursorPosX(((float)width - 585) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(585, 300), true);
					{
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("6"), 585);
						ImGui::PopFont();

						ImGui::PushFont(font3);
						ImGui::SetCursorPosY(110);
						ImGui::CenteredText(xors("Invalid PIN"), 585);
						ImGui::SetCursorPosY(150);
						ImGui::CenteredText(xors("The pin you have entered is invalid or has been used."), 585);
						ImGui::PopFont();

						ImGui::SetCursorPosY(220);
						ImGui::SetCursorPosX(20);

						if (ImGui::Button(xors("Return"), ImVec2(270, 30)))
							stage = 1;

						ImGui::SameLine();

						if (ImGui::Button(xors("Exit"), ImVec2(270, 30)))
							alive = false;
					}

					ImGui::EndChild();
					break;
				}
				case -1:
				{
					ImGui::SetCursorPosX(((float)width - 585) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(585, 300), true);
					{
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("6"), 585);
						ImGui::PopFont();

						ImGui::PushFont(font3);
						ImGui::SetCursorPosY(110);
						ImGui::CenteredText(xors("Anti-Abuse"), 585);
						ImGui::SetCursorPosY(150);
						ImGui::CenteredText(xors("Our system has detected suspicious activity done"), 585);
						ImGui::CenteredText(xors("with your computer or by the person generating this PIN"), 585);
						ImGui::PopFont();

						ImGui::SetCursorPosY(230);
						ImGui::SetCursorPosX(20);
						if (ImGui::Button(xors("Exit"), ImVec2(545, 30)))
							alive = false;
					}
					ImGui::EndChild();
					break;
				}
				case 1:
				{
					ImGui::SetCursorPosX(((float)width - 300) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(300, 300), true);
					{
						//ImGui::SetCursorPosY(20);
						//ImGui::SetCursorPosX((300 - 115) / 2);
						//ImGui::Image(logoTex, ImVec2(115, 115));
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("3"), 300);
						ImGui::PopFont();

						ImGui::PushFont(font2);
						ImGui::SetCursorPosY(120);
						ImGui::SetCursorPosX(20);
						ImGui::Text(xors("PIN:"));
						ImGui::PopFont();

						ImGui::SetCursorPosY(150);
						ImGui::SetCursorPosX(20);
						ImGui::PushItemWidth(260);
						ImGui::InputText(xors(" "), pinchar, 7);

						ImGui::SetCursorPosY(190);
						ImGui::SetCursorPosX(20);

						if (ImGui::Button(xors("Scan"), ImVec2(260, 30)))
						{
							HANDLE auththread = CreateThread(NULL, 4096, (LPTHREAD_START_ROUTINE)authenticate, NULL, NULL, NULL);
						}

						if (extra_stage == 1)
						{
							ImGui::SetCursorPosX((300 - 40) / 2);
							ImGui::SetCursorPosY(235);
							ImGui::Spinner(xors("##spinner"), 20, 5, col);
						}
					}
					ImGui::EndChild();

					ImGui::PushFont(font1);
					ImGui::SetCursorPosX(12.5);
					ImGui::SetCursorPosY(ImGui::GetWindowHeight() - 85);
					ImGui::Text(xors("%i"), timeout);
					ImGui::SetCursorPosX(12.5);
					ImGui::SetCursorPosY(ImGui::GetWindowHeight() - 65);
					ImGui::Text(xors("Stage: %i"), extra_extra_stage);

					std::string build_render = "Version " + Paladin::Version;

					ImGui::SetCursorPosX((float)(width - (double)(ImGui::CalcTextSize(build_render.c_str()).x) - 10.5));
					ImGui::SetCursorPosY(ImGui::GetWindowHeight() - 65);

					ImGui::Text(build_render.c_str(), extra_extra_stage);

					ImGui::PopFont();
					break;
				}
				case 2:
				{
					ImGui::SetCursorPosX(((float)width - 585) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(585, 300), true);
					{
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("3"), 585);
						ImGui::PopFont();

						ImGui::PushFont(font3);
						ImGui::SetCursorPosY(120);
						ImGui::CenteredText(xors("Please bring your Minecraft visible on one of your screens."), 585);
						ImGui::SetCursorPosY(150);
						ImGui::CenteredText(xors("Make sure the chat is toggled and not the ESC menu."), 585);
						ImGui::PopFont();

						ImGui::SetCursorPosY(230);
						ImGui::SetCursorPosX(20);
						if (ImGui::Button(xors("Ready"), ImVec2(545, 30)))
						{
							std::thread scanthread(Scan);
							scanthread.detach();
						}
					}
					ImGui::EndChild();
					break;
				}
				case 3:
				{
					ImGui::SetCursorPosX(((float)width - 50) / 2);
					ImGui::SetCursorPosY(190);
					ImGui::Spinner(xors("##spinner"), 25, 5, col);
					ImGui::SetCursorPosY(270);
					ImGui::PushFont(font3);
					ImGui::CenteredText(xors("Please wait"), width);
					ImGui::PopFont();

					ImGui::PushFont(font1);
					ImGui::SetCursorPosX(12.5);
					ImGui::SetCursorPosY(ImGui::GetWindowHeight() - 65);
					ImGui::Text(xors("Stage: %i"), extra_extra_stage);
					ImGui::PopFont();
					break;
				}
				case 4:
				{
					ImGui::SetCursorPosX(((float)width - 585) / 2);
					ImGui::SetCursorPosY(90);
					ImGui::BeginChild(xors("contentChild"), ImVec2(585, 300), true);
					{
						ImGui::PushFont(icons);
						ImGui::SetCursorPosY(40);
						ImGui::CenteredText(xors("5"), 585);
						ImGui::PopFont();

						ImGui::PushFont(font3);
						ImGui::SetCursorPosY(120);
						ImGui::CenteredText(detection_message.c_str(), 585);
						ImGui::SetCursorPosY(145);
						ImGui::CenteredText(extra_detection_message.c_str(), 585);
						ImGui::PopFont();

						ImGui::SetCursorPosY(230);
						ImGui::SetCursorPosX(20);
						if (ImGui::Button(xors("Delete & Exit"), ImVec2(540, 30)))
						{
							alive = false;
							Paladin::Delete = true;
						}
					}

					ImGui::EndChild();
					break;
				}
			}
		}

		ImGui::End();
		
		g_pd3dDevice->SetRenderState(D3DRS_ZENABLE, FALSE);
		g_pd3dDevice->SetRenderState(D3DRS_ALPHABLENDENABLE, FALSE);
		g_pd3dDevice->SetRenderState(D3DRS_SCISSORTESTENABLE, FALSE);

		D3DCOLOR clear_col_dx = D3DCOLOR_RGBA((int)(clear_color.x*255.0f), (int)(clear_color.y*255.0f), (int)(clear_color.z*255.0f), (int)(clear_color.w*255.0f));

		g_pd3dDevice->Clear(0, NULL, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, clear_col_dx, 1.0f, 0);

		if (g_pd3dDevice->BeginScene() >= 0)
		{
			ImGui::Render();
			ImGui_ImplDX9_RenderDrawData(ImGui::GetDrawData());
			g_pd3dDevice->EndScene();
		}

		HRESULT result = g_pd3dDevice->Present(NULL, NULL, NULL, NULL);

		// Handle loss of D3D9 device
		if (result == D3DERR_DEVICELOST && g_pd3dDevice->TestCooperativeLevel() == D3DERR_DEVICENOTRESET)
		{
			ImGui_ImplDX9_InvalidateDeviceObjects();
			g_pd3dDevice->Reset(&g_d3dpp);
			ImGui_ImplDX9_CreateDeviceObjects();
		}
	}
	alive = false;

	ImGui_ImplDX9_Shutdown();
	ImGui_ImplWin32_Shutdown();
	ImGui::DestroyContext();

	if (g_pd3dDevice) 
		g_pd3dDevice->Release();

	if (pD3D) 
		pD3D->Release();

	UnregisterClass("Paladin", wc.hInstance); // DONT XOR
	
	return 0;
}

int WINAPI wWinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, PWSTR pCmdLine, int nCmdShow)
{
	VMProtectBeginMutation("main");

	#ifndef DEBUG
	_set_FMA3_enable(0); // DO NOT REMOVE
	#endif

	drive_serial = ws2s(h.get_hdd_serial());
	board_serial = ws2s(h.get_board_serial());
	cpu_model = ws2s(h.GetCPUInfo());

	if (drive_serial.size() == 0 || drive_serial == " ")
		drive_serial = xors("NotFound");
	if (board_serial.size() == 0 || drive_serial == " ")
		board_serial = xors("NotFound");
	if (cpu_model.size() == 0 || cpu_model == " ")
		cpu_model = xors("Unknown");
	srand((unsigned int)time(NULL));

	auth = new Auth();
	#ifndef DEBUG
	driver = new KernelInterface();
	#endif

	//if (!check_parentprocess())
	//{
	//	exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo Please reopen Paladin with Explorer & echo You cannot open the exe with Chrome, Firefox, etc. & echo. & pause"));
	//}
	if (!auth->codeintegrityenabled())
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo This program cannot be ran with test signing enabled & echo. & pause"));
	}
	HANDLE titanhide = CreateFileA("\\\\.\\TitanHide", GENERIC_READ | GENERIC_WRITE, 0, 0, OPEN_EXISTING, 0, 0);
	if (titanhide != INVALID_HANDLE_VALUE)
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - AntiTamper & echo. & echo Please disable the debugger hider TitanHide & echo. & pause"));
	}

	#ifndef DEBUG
	if (IsDebuggerPresent())
		exit(0);
	wchar_t path[MAX_PATH];
	GetModuleFileNameW(NULL, path, MAX_PATH);
	if (!util::verifyembeddedsignature(path))
		return 0;
	#else
	AllocConsole();
	freopen("conout$", "w", stdout);
	#endif

	auto keks = util::findhandlestoprocess(0);
	for (auto k : keks)
	{
		HANDLE gtfo = OpenProcess(PROCESS_TERMINATE, false, k.first);
		TerminateProcess(gtfo, 0);
		std::cout << k.first << " " << k.second.first << " " << k.second.second << std::endl;
	}

	#pragma region Driver
	#ifndef DEBUG

	std::vector<DWORD> lsass_processes = util::findprocessesbyname(xors("lsass.exe"));
	std::string lsass_path = getenv(xors("SystemDrive"));
	lsass_path.append(xors("\\Windows\\system32\\lsass.exe"));
	auto winver = WindowsVersion();
	for (auto p : lsass_processes)
	{
		std::string path = util::getprocesspath(p);
		if (_stricmp(path.c_str(), lsass_path.c_str()) == 0)
		{
			lsass_pid = p;
			break;
		}
	}
	if (lsass_pid == 0)
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - Error & echo. & echo Failed to find LSASS process: ") + std::to_string(GetLastError()).c_str() + xors(" & echo. & pause"));
	}

	int driver_status = -999;

	driver_status = driver->Start(xors("\\\\.\\PaladinKernel"));
	if (driver_status != 0)
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - Error & echo. & echo Failed to start kernel driver: ") + std::to_string(driver_status).c_str() + xors(" & echo. & pause"));
		return 0;
	}

	char signature_buf[512];
	memset(signature_buf, 0, 512);
	int signature_result = signature::get(xors("SIGNATURE ") + Paladin::Version, signature_buf, 512, false);

	if (!signature_result || strlen(signature_buf) == 0)
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - Error & echo. & echo Failed to contact signature server (") + std::to_string(signature_result).c_str() + xors("), try again later & echo. & pause"));
		return 0;
	}

	std::stringstream sss;
	std::string signature_hex = signature_buf;
	std::vector<unsigned char> signature_uv;

	unsigned int bufferr;
	int offsett = 0;
	while (offsett < signature_hex.length())
	{
		sss.clear();
		sss << std::hex << signature_hex.substr(offsett, 2);
		sss >> bufferr;
		signature_uv.push_back(static_cast<unsigned char>(bufferr));
		offsett += 2;
	}

	PUCHAR signature_u = (PUCHAR)malloc(signature_uv.size());
	memcpy(signature_u, signature_uv.data(), signature_uv.size() * sizeof(unsigned char));

	if (!driver->Verify(signature_u, (ULONG)signature_uv.size()))
	{
		driver->Stop();
		return 0;
	}
	
	driver->Protect(GetCurrentProcessId(), PROCESSPROTECTION);
	driver->SetObProtected(GetCurrentProcessId(), lsass_pid);

	ph_checker_thread = CreateThread(NULL, 4096, (LPTHREAD_START_ROUTINE)check_process_hacker, NULL, NULL, NULL);
	kernel_checker_thread = CreateThread(NULL, 4096, (LPTHREAD_START_ROUTINE)check_driver, NULL, NULL, NULL);
	timeout_thread = CreateThread(NULL, 4096, (LPTHREAD_START_ROUTINE)check_timeout, NULL, NULL, NULL);
	driver->Protect(GetCurrentProcessId(), PROCESSPROTECTION);
	driver->SetObProtected(GetCurrentProcessId(), lsass_pid);
	#endif
	#pragma endregion

	#pragma region Window
	// Create application window
	wc = { sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, NULL, NULL, NULL, "Paladin", NULL }; // DONT XOR
	RegisterClassEx(&wc);

	HICON icon = LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_ICON));

	wc.hIcon = icon;

	hwnd = CreateWindow("Paladin", xors(""), WS_POPUP, 100, 100, width, height - 40, NULL, NULL, wc.hInstance, NULL); // DONT XOR

	if (!hwnd)
	{
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - Error & echo. & echo Failed to create window & echo. & pause"));
		return 0;
	}

	SetWindowLong(hwnd, GWL_STYLE, GetWindowLong(hwnd, GWL_STYLE)&~WS_SIZEBOX&~WS_MAXIMIZEBOX);
	SendMessage(hwnd, WM_SETICON, ICON_BIG, (LPARAM)icon);
	SendMessage(hwnd, WM_SETICON, ICON_SMALL, (LPARAM)icon);

	HMENU hmenu = GetSystemMenu(hwnd, FALSE);

	if ((pD3D = Direct3DCreate9(D3D_SDK_VERSION)) == NULL)
	{
		UnregisterClass("Paladin", wc.hInstance); // DONT XOR
		return 0;
	}
	
	ZeroMemory(&g_d3dpp, sizeof(g_d3dpp));

	g_d3dpp.Windowed = TRUE;
	g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
	g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
	g_d3dpp.EnableAutoDepthStencil = TRUE;
	g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
	g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE; // Present with vsync
	//g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_IMMEDIATE; // Present without vsync, maximum unthrottled framerate
	
	// Create the D3DDevice
	HRESULT p3d3create = pD3D->CreateDevice(D3DADAPTER_DEFAULT, D3DDEVTYPE_HAL, hwnd, D3DCREATE_SOFTWARE_VERTEXPROCESSING, &g_d3dpp, &g_pd3dDevice);
	if (p3d3create < 0)
	{
		pD3D->Release();
		UnregisterClass("Paladin", wc.hInstance); // DONT XOR
		exit_with_message(xors("/C echo Paladin ") + Paladin::Version + xors(" - Error & echo. & echo Failed to create DirectX device & echo. & pause"));
		return 0;
	}

	// Setup Dear ImGui binding
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGui_ImplWin32_Init(hwnd);
	ImGui_ImplDX9_Init(g_pd3dDevice);

	// Setup style
	ImGui::StyleColorsDark();

	ImGuiStyle& style = ImGui::GetStyle();

	style.WindowRounding = 0.0f;
	style.FrameRounding = 0.0f;

	ImGuiIO& io = ImGui::GetIO(); (void)io;

	io.IniFilename = NULL;
	io.LogFilename = NULL;

	ImFontConfig *fontcfg = new ImFontConfig;

	font1 = io.Fonts->AddFontFromMemoryCompressedTTF(MontserratRegular_compressed_data, MontserratRegular_compressed_size, 15.f, fontcfg);
	font2 = io.Fonts->AddFontFromMemoryCompressedTTF(OpenSans_Bold_compressed_data, OpenSans_Bold_compressed_size, 20.f, fontcfg);
	font3 = io.Fonts->AddFontFromMemoryCompressedTTF(MontserratRegular_compressed_data, MontserratRegular_compressed_size, 20.f, fontcfg);
	icons = io.Fonts->AddFontFromMemoryCompressedTTF(icons2_compressed_data, icons2_compressed_size, 40.f, fontcfg);
	#pragma endregion
	
	scan = new Scanner();
	messages();
	alive = false;

	#ifndef DEBUG
	driver->Stop();
	delete driver;
	#endif
	delete auth;

	if (Paladin::Delete)
	{
		char path2[MAX_PATH];
		GetModuleFileNameA(NULL, path2, MAX_PATH);

		std::string cmd = "/C timeout 6 & DEL \"";
		cmd.append(path2);
		cmd.append("\"");
		ShellExecute(0, "runas", "cmd.exe", cmd.c_str(), 0, SW_HIDE);
	}

	return 0;
	VMProtectEnd();
}
