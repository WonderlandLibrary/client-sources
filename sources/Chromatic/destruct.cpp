#include "main.h"

bool dps_string_found = false;

int cucklord_random_int(int min, int max)
{
	std::random_device dev;
	std::mt19937 rng(dev());
	std::uniform_int_distribution<std::mt19937::result_type> dist6(min, max);
	return dist6(rng);
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

void cucklord_destruct_clean_strings_function(DWORD pid, std::vector<const char*> findvector, bool isdps)
{
	bool cucklord_destruct_failed = false;
	int cucklord_destruct_total_cleaned = 0;
	int cucklord_destruct_total_failed = 0;
	bool isjava = false;
	if (pid == client::get_pid("javaw.exe")) { isjava = true; }
	double processid = pid;
	HANDLE processhandle;
	if (processhandle = OpenProcess(PROCESS_ALL_ACCESS, false, pid))
	{
		MEMORY_BASIC_INFORMATION cucklord_memory;
		INT64 ActAddress = 0;
		INT64 pos = 0;
		while (VirtualQueryEx(processhandle, (LPVOID)ActAddress, &cucklord_memory, sizeof(MEMORY_BASIC_INFORMATION)))
		{
			if (isjava && ActAddress > 0x2000000) { break; }
			if (cucklord_memory.State == MEM_COMMIT && ((cucklord_memory.Protect == PAGE_EXECUTE_READWRITE) | (
				cucklord_memory.Protect == PAGE_READWRITE) | (cucklord_memory.Protect == PAGE_EXECUTE_WRITECOPY) | (
					cucklord_memory.Protect == PAGE_WRITECOPY)))
			{
				std::vector<byte> buffer(cucklord_memory.RegionSize);
				if (ReadProcessMemory(processhandle, (LPVOID)ActAddress, &buffer[0], cucklord_memory.RegionSize, 0))
				{
					for (const char* removeme : findvector)
					{
						INT64 StringLenght = strlen(removeme);
						INT64 BufferSize = static_cast<int>(cucklord_memory.RegionSize);
						for (INT64 i = 0; i <= BufferSize; i++)
						{
							INT64 j;
							for (j = 0; j < StringLenght; j++)
								if (buffer[i + j] != removeme[j])
									break;
							if (j == StringLenght)
							{
								std::string RewriteMem = "";
								if (!WriteProcessMemory(processhandle, (LPVOID)(ActAddress + i), &RewriteMem,
									StringLenght, 0))
								{
									//std::cout << pid << std::endl;
								}
							}
						}
					}
				}
				std::vector<WCHAR> buffer2(cucklord_memory.RegionSize);
				if (ReadProcessMemory(processhandle, (LPVOID)ActAddress, &buffer2[0], cucklord_memory.RegionSize, 0))
				{
					for (const char* removeme : findvector)
					{
						INT64 StringLenght2 = strlen(removeme);
						INT64 BufferSize = static_cast<int>(cucklord_memory.RegionSize);
						for (INT64 i = 0; i <= BufferSize; i++)
						{
							INT64 j;
							for (j = 0; j < StringLenght2; j++)
								if (buffer2[i + j] != removeme[j])
									break;
							if (j == StringLenght2)
							{
								if (isdps)
								{
									dps_string_found = true;
									char container;
									int counter = 0;
									int x = i;
									int loopingint = 0;
									ActAddress = ActAddress - 4;
									for (;;)
									{
										WCHAR writeme = cucklord_random_wchar(0, 35);
										ReadProcessMemory((processhandle), (LPVOID)(ActAddress + x * 2), &container,
											sizeof(char), 0);
										if (container == '!') { counter++; }
										if (!WriteProcessMemory(processhandle, (LPVOID)(ActAddress + x * 2), &writeme,
											(sizeof(WCHAR)), 0)) {
											//std::cout << pid << std::endl;
										}
										x++;
										if (counter == 5) { break; }
									}
								}
								else
								{
									WCHAR RewriteMem = NULL;
									WriteProcessMemory(processhandle, (LPVOID)(ActAddress + i * 2), &RewriteMem,
										(StringLenght2 * 2), 0);
								}
							}
						}
					}
				}
			}
			ActAddress += cucklord_memory.RegionSize;
		}
	}
	if (isdps) { if (!dps_string_found) { system("sc stop DPS"); } }
}

int cleanedprocesses = 0;
std::vector<const char*> exenamevectordps;
std::vector<const char*> exenamevector;
std::vector<const char*> exenamevector2;


void client::destruct()
{
	modules::clicker::enabled = false;
	modules::reach::enabled = false;
	modules::clicker::cps = 0.0f;
	modules::reach::blocks = 3.0f;
	std::string exename = cucklord_get_exe_name();
	std::string exepath = cucklord_get_exe_path();
	std::string exenamedps = exename + "!";
	exenamevector.push_back(exename.c_str());
	exenamevector.push_back(exepath.c_str());
	exenamevectordps.push_back(exenamedps.c_str());
	system("ipconfig /flushdns");
	std::string prefetchstring = "del \\Windows\\prefetch\\" + exename + "* /F /Q";
	system(prefetchstring.c_str());
	exenamevector2 = exenamevector;
	exenamevector2.push_back(cucklord_get_exe_path().c_str());

	// clean strings
	if (client::cleanstrings)
	{
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("PcaSvc"), exenamevector, false);
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("DPS"), exenamevector, false);
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("DPS"), exenamevectordps, true);
	}

	if (client::selfdelete)
	{
		TCHAR szModuleName[MAX_PATH];
		TCHAR szCmd[2 * MAX_PATH];
		STARTUPINFO si = { 0 };
		PROCESS_INFORMATION pi = { 0 };
		GetModuleFileName(NULL, szModuleName, MAX_PATH);
		StringCbPrintf(szCmd, 2 * MAX_PATH, SELF_REMOVE_STRING, szModuleName);
		CreateProcess(NULL, szCmd, NULL, NULL, FALSE, CREATE_NO_WINDOW, NULL, NULL, &si, &pi);
		CloseHandle(pi.hThread);
		CloseHandle(pi.hProcess);
		exit(EXIT_SUCCESS);
	}
	else { exit(EXIT_SUCCESS); }
}