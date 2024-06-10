#include "selfdestruct.hpp"

#include "../../utilities/memory.hpp"
#include "../../common.hpp"

#include <future>

inline std::string get_exe()
{
	char szAppPath[MAX_PATH];
	::GetModuleFileName(0, szAppPath, MAX_PATH);

	std::string strAppName = szAppPath;
	return strAppName.substr(strAppName.rfind("\\") + 1);
}

inline std::string get_exe_path()
{
	TCHAR buffer[MAX_PATH] = { 0 };
	GetModuleFileName(NULL, buffer, MAX_PATH);
	return std::string(buffer);
}

inline DWORD get_service_pid(const char* serviceName)
{
	const auto hScm = OpenSCManager(nullptr, nullptr, NULL);
	const auto hSc = OpenService(hScm, serviceName, SERVICE_QUERY_STATUS);

	SERVICE_STATUS_PROCESS ssp = {};
	DWORD bytesNeeded = 0;
	QueryServiceStatusEx(hSc, SC_STATUS_PROCESS_INFO, reinterpret_cast<LPBYTE>(&ssp), sizeof(ssp), &bytesNeeded);

	CloseServiceHandle(hSc);
	CloseServiceHandle(hScm);

	return (DWORD)ssp.dwProcessId;
}

inline std::vector<DWORD> get_pid(std::wstring procname)
{
	std::vector<DWORD> pids;
	HANDLE snap = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);

	PROCESSENTRY32W entry;
	entry.dwSize = sizeof entry;

	if (!Process32FirstW(snap, &entry)) {
		return {};
	}

	do {
		if (std::wstring(entry.szExeFile) == procname) {
			pids.push_back(entry.th32ProcessID);
		}
	} while (Process32NextW(snap, &entry));

	return pids;
}

void modules::selfdestruct::close()
{
	std::string lower_exe = get_exe();
	std::string lower_path = get_exe_path(); 
	std::transform(lower_exe.begin(), lower_exe.end(), lower_exe.begin(), ::tolower);
	std::transform(lower_path.begin(), lower_path.end(), lower_path.begin(), ::tolower);

	std::vector<std::string> str_to_clear{ get_exe_path(), lower_path, get_exe(), lower_exe};

	std::vector<HANDLE> pHandle{ OpenProcess(PROCESS_ALL_ACCESS, FALSE, get_service_pid("PcaSvc")), OpenProcess(PROCESS_ALL_ACCESS, FALSE, get_service_pid("DPS")) };
	for (auto p : get_pid(L"explorer.exe"))
		pHandle.push_back(OpenProcess(PROCESS_ALL_ACCESS, FALSE, p));

	for (auto& handle : pHandle) {
		auto addresses = MemoryHelper::get().find_str(handle, str_to_clear);

		for (auto& addr : addresses) {
			ZwWriteVirtualMemory(handle, (LPVOID)(addr.first), (PVOID)"\x0", addr.second, 0);
		}
	}

	std::string deleteprefetchfile = "del \\Windows\\prefetch\\" + get_exe() + "* /F /Q";
	system(deleteprefetchfile.data());
	system("ipconfig /flushdns");
}
