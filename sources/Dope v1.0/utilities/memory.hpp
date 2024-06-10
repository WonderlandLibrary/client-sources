#pragma once
#include <windows.h>
#include <TlHelp32.h>
#include <vector>
#include <string>
#include "../../vendors/singleton.h"

class MemoryHelper : public singleton<MemoryHelper>
{
public:
	uintptr_t find_pattern(HANDLE handle, MODULEENTRY32 module, const char* ptrn);
	std::vector<std::pair<uintptr_t, size_t>> find_str(HANDLE handle, std::vector<std::string> strs);
	MODULEENTRY32 get_module_entry(DWORD pid, const char* mod);
};