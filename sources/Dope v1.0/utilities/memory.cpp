#include "memory.hpp"
#include "../common.hpp"

#ifndef _DEBUG
#include <ThemidaSDK.h>
#endif

uintptr_t MemoryHelper::find_pattern(HANDLE handle, MODULEENTRY32 module, const char* ptrn)
{
#ifndef _DEBUG
	VM_DOLPHIN_WHITE_START
#endif 
	auto pattern_to_byte = [](const char* szpattern) {
		auto       m_iBytes = std::vector<int>{};
		const auto szStartAddr = const_cast<char*>(szpattern);
		const auto szEndAddr = const_cast<char*>(szpattern) + strlen(szpattern);

		for (auto szCurrentAddr = szStartAddr; szCurrentAddr < szEndAddr; ++szCurrentAddr) {
			if (*szCurrentAddr == '?') {
				++szCurrentAddr;
				if (*szCurrentAddr == '?') ++szCurrentAddr;
				m_iBytes.push_back(-1);
			}
			else m_iBytes.push_back(strtoul(szCurrentAddr, &szCurrentAddr, 16));
		}
		return m_iBytes;
	};

	auto pattern = pattern_to_byte(ptrn);
#ifndef _DEBUG
	VM_DOLPHIN_WHITE_END
#endif
	for (size_t adr = (uintptr_t)module.modBaseAddr; adr < (uintptr_t)(module.modBaseAddr + module.modBaseSize); adr += 100000)
	{
		std::vector<byte>memory(100000);
		if(ZwReadVirtualMemory(handle, (LPVOID)adr, &memory[0], 100000, 0) < 0)
			continue;

		for (size_t it = 0; it < 100000; ++it) 
		{
			if (adr + it >= (uintptr_t)(module.modBaseAddr + module.modBaseSize)) break;
			if (it + pattern.size() >= memory.size()) break;

			if (pattern.at(0) == -1 || memory.at(it) == pattern.at(0))
			{
				bool is_pattern = true;
				for (size_t i = 0; i < pattern.size(); ++i) 
				{
					if (pattern.at(i) == -1) continue;

					if (pattern.at(i) != memory.at(it + i)) {
						is_pattern = false;
						break;
					}
				}

				if (is_pattern) 
					return adr + it;
			}
		}
	}

	return NULL;
}

std::vector<std::pair<uintptr_t, size_t>> MemoryHelper::find_str(HANDLE handle, std::vector<std::string> strs)
{
	MEMORY_BASIC_INFORMATION mbi;
	std::vector<std::pair<std::string, uintptr_t>> memory;
	for (uintptr_t addr = 0; VirtualQueryEx(handle, (LPVOID)addr, &mbi, sizeof(mbi)); addr += mbi.RegionSize)
	{
		if (mbi.State != MEM_COMMIT) continue;
		if (mbi.Protect != PAGE_READWRITE) continue;

		memory.resize(memory.size() + 1);
		memory[memory.size() - 1].first.resize(mbi.RegionSize, 0);

		memory[memory.size() - 1].second = addr;
		ZwReadVirtualMemory(handle, (LPVOID)addr, &memory[memory.size() - 1].first[0], mbi.RegionSize, nullptr);
	}

	std::vector<std::pair<uintptr_t, size_t>> ret;
	for (auto& mem : memory) {
		for (const auto& str : strs) {
			auto pos = mem.first.find(str);
			if (pos != std::string::npos) {
				ret.push_back(std::make_pair(mem.second + pos, str.size()));
			}
		}
	}

	return ret;
}

MODULEENTRY32 MemoryHelper::get_module_entry(DWORD pid, const char* mod)
{
	HANDLE hSnap = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, pid);
	if (hSnap != INVALID_HANDLE_VALUE)
	{
		MODULEENTRY32 modEntry;
		modEntry.dwSize = sizeof(modEntry);
		if (Module32First(hSnap, &modEntry))
		{
			do
			{
				if (!strcmp(modEntry.szModule, mod))
				{
					return modEntry;
				}
			} while (Module32Next(hSnap, &modEntry));
		}
	}
	CloseHandle(hSnap);
	return (MODULEENTRY32)NULL;
}
