
#include <windows.h>
#include <vector>
#include <TlHelp32.h>
#include <codecvt>
#include <random>
#include <iostream>

#define MEMBLOCK 4096
#define OPENFLAGS THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE
#define SNAPFLAGS TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32

namespace koid
{
	namespace memory
	{
		template <class val>
		std::vector<DWORD> ScanProcessMemory(HANDLE ProcessHandle, std::vector<val> Value ,bool Region, DWORD Min, DWORD Max) {
			std::vector<DWORD>Locations; bool BreakScan = false; INT64 ActAddress; if (!Region) { ActAddress = 0; } else { ActAddress = Min; }
			if (!Region) {
				MEMORY_BASIC_INFORMATION Memory;
				while (VirtualQueryEx(ProcessHandle, (LPVOID)ActAddress, &Memory, sizeof(MEMORY_BASIC_INFORMATION))) {
					if (Memory.State == MEM_COMMIT) {
						std::vector<val> Buffer(Memory.RegionSize);
						if (ReadProcessMemory(ProcessHandle, (LPVOID)ActAddress, &Buffer[0], Memory.RegionSize, 0)) {
							for (size_t i = 0; i < Buffer.size(); ++i) {
								for (auto f : Value) {
									if (Buffer[i] == f) {
										Locations.push_back(ActAddress + ((i + 1) * sizeof(val)) - sizeof(val));
									}
								}
							}
						}
					} ActAddress += Memory.RegionSize;
				}
			}
			else {
				while (ActAddress < Max) {
					std::vector<val> Buffer(MEMBLOCK);
					if (ReadProcessMemory(ProcessHandle, (LPVOID)ActAddress, &Buffer[0], MEMBLOCK, 0)) {
						for (size_t i = 0; i < Buffer.size(); ++i) {
							if (ActAddress + ((i + 1) * sizeof(val)) - sizeof(val) > Max) {
								BreakScan = true;
								break;
							}
							for (auto f : Value) {
								if (Buffer[i] == f) {
									Locations.push_back(ActAddress + ((i + 1) * sizeof(val)) - sizeof(val));
								}
							} if (BreakScan) { break; }
						}
					} if (BreakScan) { break; } ActAddress += MEMBLOCK;
				}
			}return Locations;
		}
	}
}