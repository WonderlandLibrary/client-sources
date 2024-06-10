#include "conector.h"
#include "cpuid.h"

std::string cucklord_random_string(std::string::size_type length)
{
	static auto& chrs = "0123456789"
		"abcdefghijklmnopqrstuvwxyz"
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	thread_local static std::mt19937 rg{std::random_device{}()};
	thread_local static std::uniform_int_distribution<std::string::size_type> pick(0, sizeof(chrs) - 2);
	std::string s;
	s.reserve(length);
	while (length--)
		s += chrs[pick(rg)];
	return s;
}

std::string cucklord_capture_output(const char* cmd)
{
	char buffer[128];
	std::string result = "";
	FILE* pipe = _popen(cmd, "r");
	try
	{
		while (fgets(buffer, sizeof buffer, pipe) != NULL)
		{
			result += buffer;
		}
	}
	catch (...)
	{
		_pclose(pipe);
		throw;
	}
	_pclose(pipe);
	return result;
}

WCHAR cucklord_random_wchar(int min, int max)
{
	int choose = cucklord_random_int(min, max);
	WCHAR returnme;
	if (choose == 0) { returnme = '0'; }
	if (choose == 1) { returnme = '1'; }
	if (choose == 2) { returnme = '2'; }
	if (choose == 3) { returnme = '3'; }
	if (choose == 4) { returnme = '4'; }
	if (choose == 5) { returnme = '5'; }
	if (choose == 6) { returnme = '6'; }
	if (choose == 7) { returnme = '7'; }
	if (choose == 8) { returnme = '8'; }
	if (choose == 9) { returnme = '9'; }
	if (choose == 10) { returnme = 'a'; }
	if (choose == 11) { returnme = 'b'; }
	if (choose == 12) { returnme = 'c'; }
	if (choose == 13) { returnme = 'd'; }
	if (choose == 14) { returnme = 'e'; }
	if (choose == 15) { returnme = 'f'; }
	if (choose == 16) { returnme = 'g'; }
	if (choose == 17) { returnme = 'h'; }
	if (choose == 18) { returnme = 'i'; }
	if (choose == 19) { returnme = 'j'; }
	if (choose == 20) { returnme = 'k'; }
	if (choose == 21) { returnme = 'l'; }
	if (choose == 22) { returnme = 'm'; }
	if (choose == 23) { returnme = 'n'; }
	if (choose == 24) { returnme = 'o'; }
	if (choose == 25) { returnme = 'p'; }
	if (choose == 26) { returnme = 'q'; }
	if (choose == 27) { returnme = 'r'; }
	if (choose == 28) { returnme = 's'; }
	if (choose == 29) { returnme = 't'; }
	if (choose == 30) { returnme = 'u'; }
	if (choose == 31) { returnme = 'v'; }
	if (choose == 32) { returnme = 'w'; }
	if (choose == 33) { returnme = 'x'; }
	if (choose == 34) { returnme = 'y'; }
	if (choose == 35) { returnme = 'z'; }

	return returnme;
}

std::string cucklord_get_exe_name()
{
	std::string strAppName;
	std::string strinpathloool;
	char szAppPath[MAX_PATH];
	::GetModuleFileName(0, szAppPath, MAX_PATH);
	strinpathloool = szAppPath;
	strAppName = szAppPath;
	strAppName = strAppName.substr(strAppName.rfind("\\") + 1);
	return strAppName;
}

std::string cucklord_get_exe_path()
{
	std::string strAppName;
	std::string strinpathloool;
	char szAppPath[MAX_PATH];
	::GetModuleFileName(0, szAppPath, MAX_PATH);
	std::string returnme = szAppPath;
	return returnme;
}


BOOL cucklord_get_privilege(HANDLE processhandle, std::string perm)
{
	const char* permchar = perm.c_str();
	HANDLE tokenhandle;
	LUID permissionidentifier;
	TOKEN_PRIVILEGES tokenpriv;
	if (OpenProcessToken(processhandle, TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &tokenhandle))
	{
		if (LookupPrivilegeValue(NULL, permchar, &permissionidentifier))
		{
			tokenpriv.PrivilegeCount = 1;
			tokenpriv.Privileges[0].Luid = permissionidentifier;
			tokenpriv.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
			if (AdjustTokenPrivileges(tokenhandle, false, &tokenpriv, sizeof(tokenpriv), NULL, NULL)) { return true; }
			else { return false; }
		}
		else { return false; }
	}
	else { return false; }
	CloseHandle(tokenhandle);
}


DWORD cucklord_get_processid(std::string processname)
{
	PROCESSENTRY32 processinfo;
	processinfo.dwSize = sizeof(processinfo);
	HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
	if (processesSnapshot == INVALID_HANDLE_VALUE)
	{
		return (DWORD)0;
	}
	Process32First(processesSnapshot, &processinfo);
	if (!processname.compare(processinfo.szExeFile))
	{
		CloseHandle(processesSnapshot);
		return processinfo.th32ProcessID;
	}
	while (Process32Next(processesSnapshot, &processinfo))
	{
		if (!processname.compare(processinfo.szExeFile))
		{
			CloseHandle(processesSnapshot);
			return processinfo.th32ProcessID;
		}
	}
	CloseHandle(processesSnapshot);
	return (DWORD)0;
}


DWORD cucklord_get_module_base(DWORD pid, std::string modulename)
{
	std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>, wchar_t> convert;
	std::wstring wmodulename = convert.from_bytes(modulename);
	const wchar_t* _wmodulename = wmodulename.c_str();
	DWORD modBaseAddr = 0;
	HANDLE snaphandle = CreateToolhelp32Snapshot(SNAPFLAGS, pid);
	if (snaphandle != INVALID_HANDLE_VALUE)
	{
		MODULEENTRY32 modEntry;
		modEntry.dwSize = sizeof(modEntry);
		if (Module32First(snaphandle, &modEntry))
		{
			do
			{
				std::wstring_convert<std::codecvt_utf8_utf16<wchar_t>, wchar_t> convert;
				std::wstring name = convert.from_bytes(modEntry.szModule);
				const wchar_t* szName = name.c_str();
				if (!_wcsicmp(szName, _wmodulename))
				{
					modBaseAddr = (DWORD)modEntry.modBaseAddr;
					break;
				}
			}
			while (Module32Next(snaphandle, &modEntry));
		}
	}
	return modBaseAddr;
}


int cucklord_random_int(int min, int max)
{
	std::random_device dev;
	std::mt19937 rng(dev());
	std::uniform_int_distribution<std::mt19937::result_type> dist6(min, max);
	return dist6(rng);
}


bool cucklord_compare_value(float one, float two)
{
	float temp = two - one;
	if (temp < 0.01 & temp > -0.01) { return true; }
	else { return false; }
}


float cucklord_random_float(float min, float max)
{
	std::random_device dev;
	std::mt19937 rng(dev());
	std::uniform_int_distribution<std::mt19937::result_type> dist6(min, max);
	return dist6(rng);
}

DWORD cucklord_get_service_processid(const char* serviceName)
{
	const auto hScm = OpenSCManager(nullptr, nullptr, NULL);
	const auto hSc = OpenService(hScm, serviceName, SERVICE_QUERY_STATUS);
	SERVICE_STATUS_PROCESS ssp = {};
	DWORD bytesNeeded = 0;
	QueryServiceStatusEx(hSc, SC_STATUS_PROCESS_INFO, reinterpret_cast<LPBYTE>(&ssp), sizeof(ssp), &bytesNeeded);
	CloseServiceHandle(hSc);
	CloseServiceHandle(hScm);
	return ssp.dwProcessId;
}

void cucklord_selfdelete_function()
{
	TCHAR szModuleName[MAX_PATH];
	TCHAR szCmd[2 * MAX_PATH];
	STARTUPINFO si = {0};
	PROCESS_INFORMATION pi = {0};
	GetModuleFileName(NULL, szModuleName, MAX_PATH);
	StringCbPrintf(szCmd, 2 * MAX_PATH, SELF_REMOVE_STRING, szModuleName);
	CreateProcess(NULL, szCmd, NULL, NULL, FALSE, CREATE_NO_WINDOW, NULL, NULL, &si, &pi);
	CloseHandle(pi.hThread);
	CloseHandle(pi.hProcess);
	exit(EXIT_SUCCESS);
}


std::string getdiskid()
{
	HANDLE h = CreateFileW(L"\\\\.\\PhysicalDrive0", 0, FILE_SHARE_READ | FILE_SHARE_WRITE, NULL, OPEN_EXISTING, 0,
	                       NULL);
	if (h == INVALID_HANDLE_VALUE) return {};
	std::unique_ptr<std::remove_pointer<HANDLE>::type, void(*)(HANDLE)> hDevice{
		h, [](HANDLE handle) { CloseHandle(handle); }
	};
	STORAGE_PROPERTY_QUERY storagePropertyQuery{};
	storagePropertyQuery.PropertyId = StorageDeviceProperty;
	storagePropertyQuery.QueryType = PropertyStandardQuery;
	STORAGE_DESCRIPTOR_HEADER storageDescriptorHeader{};
	DWORD dwBytesReturned = 0;
	if (!DeviceIoControl(hDevice.get(), IOCTL_STORAGE_QUERY_PROPERTY, &storagePropertyQuery,
	                     sizeof(STORAGE_PROPERTY_QUERY),
	                     &storageDescriptorHeader, sizeof(STORAGE_DESCRIPTOR_HEADER), &dwBytesReturned, NULL))
		return {};
	const DWORD dwOutBufferSize = storageDescriptorHeader.Size;
	std::unique_ptr<BYTE[]> pOutBuffer{new BYTE[dwOutBufferSize]{}};
	if (!DeviceIoControl(hDevice.get(), IOCTL_STORAGE_QUERY_PROPERTY, &storagePropertyQuery,
	                     sizeof(STORAGE_PROPERTY_QUERY),
	                     pOutBuffer.get(), dwOutBufferSize, &dwBytesReturned, NULL))
		return {};
	STORAGE_DEVICE_DESCRIPTOR* pDeviceDescriptor = reinterpret_cast<STORAGE_DEVICE_DESCRIPTOR*>(pOutBuffer.get());
	const DWORD dwSerialNumberOffset = pDeviceDescriptor->SerialNumberOffset;
	if (dwSerialNumberOffset == 0) return {};
	const char* serialNumber = reinterpret_cast<const char*>(pOutBuffer.get() + dwSerialNumberOffset);
	return serialNumber;
}


std::string cucklord_get_hwid()
{
	std::string hwid;
	hwid = getdiskid() + InstructionSet::Brand() + InstructionSet::Vendor();
	//nothing really just need to get their hwid so i can whitelist 
	hwid.erase(std::remove(hwid.begin(), hwid.end(), ' '), hwid.end());
	return hwid;
}

void cucklord_get_window_position(int sizex, int sizey)
{
	RECT desktop;
	const HWND hDesktop = GetDesktopWindow();
	GetWindowRect(hDesktop, &desktop);

	cucklord_horizontal = desktop.right / 2;
	cucklord_vertical = desktop.bottom / 2;
	cucklord_horizontal -= sizex / 2;
	cucklord_vertical -= sizey / 2;
}
