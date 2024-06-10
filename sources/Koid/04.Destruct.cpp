#include "conector.h"

bool dps_string_found = false;

void cucklord_destruct_clean_strings_function(DWORD pid, std::vector<const char*> findvector, bool isdps)
{
	bool cucklord_destruct_failed = false;
	int cucklord_destruct_total_cleaned = 0;
	int cucklord_destruct_total_failed = 0;
	bool isjava = false;
	if (pid == cucklord_get_processid("javaw.exe")) { isjava = true; }
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
									std::cout << pid << std::endl;
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
										                        (sizeof(WCHAR)), 0)) { std::cout << pid << std::endl; }
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

void cucklord_selfdestruct_function()
{
	cucklord_clicker_enabled = false;
	cucklord_reach_enabled = false;
	cucklord_speed_enabled = false;
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
	if (cucklord_destruct_clean_strings)
	{
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("PcaSvc"), exenamevector, false);
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("DPS"), exenamevector, false);
		cucklord_destruct_clean_strings_function(cucklord_get_service_processid("DPS"), exenamevectordps, true);
	}
	bool breakcheck = false;
	while (true)
	{
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
		if (!cucklord_reach_used & !cucklord_speed_used)
		{
			breakcheck = true;
		}
		if (breakcheck) { break; }
	}
	if (cucklord_destruct_selfdelete)
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
	else { exit(EXIT_SUCCESS); }
}
