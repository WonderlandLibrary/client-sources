
#include "conector.h"

std::vector<DWORD>locationslimit;
std::vector<DWORD>locationsreachf;
std::vector<DWORD>locationsreachd;
bool cucklord_reach_used = false;

void reachupdater() {
	double dlimitd = 4.5;
	double dreachd = 3;
	float dreachf = 3;

	float bufferf;
	double bufferd;
	while (true) {
		if (cucklord_reach_used) {

			double nlimitd = cucklord_reach_value + 1.5;
			float nreachf = cucklord_reach_value;
			double nreachd = cucklord_reach_value;
			if (HANDLE processhandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, false, cucklord_get_processid("javaw.exe"))) {

				if (cucklord_reach_enabled) {
					std::this_thread::sleep_for(std::chrono::milliseconds(1));
					for (size_t i = 0; i < locationsreachf.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsreachf[i]), &bufferf, sizeof(float), 0)) {
							if (bufferf > 2.99 && bufferf < 20.01) { WriteProcessMemory(processhandle, (LPVOID)(locationsreachf[i]), &nreachf, sizeof(float), 0); }
							else { locationsreachf.erase(locationsreachf.begin() + i); i -= 2; }
						}
					}
					for (size_t i = 0; i < locationsreachd.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsreachd[i]), &bufferd, sizeof(double), 0)) {
							if (bufferd > 2.99 && bufferd < 20.01) { WriteProcessMemory(processhandle, (LPVOID)(locationsreachd[i]), &nreachd, sizeof(double), 0); }
							else { locationsreachd.erase(locationsreachd.begin() + i); i -= 2; }
						}
					}
					for (size_t i = 0; i < locationslimit.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationslimit[i]), &bufferd, sizeof(double), 0)) {
							if (bufferd > 4.49 && bufferd < 21.51) { WriteProcessMemory(processhandle, (LPVOID)(locationslimit[i]), &nlimitd, sizeof(double), 0); }
							else { locationslimit.erase(locationslimit.begin() + i); i -= 2; }
						}
					}
				}
				else {
					std::this_thread::sleep_for(std::chrono::milliseconds(1));
					for (size_t i = 0; i < locationsreachf.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsreachf[i]), &bufferf, sizeof(float), 0)) {
							if (bufferf > 2.99 && bufferf < 6.01) { WriteProcessMemory(processhandle, (LPVOID)(locationsreachf[i]), &dreachf, sizeof(float), 0); }
							else { locationsreachf.erase(locationsreachf.begin() + i); i -= 2; }
						}
					}
					for (size_t i = 0; i < locationsreachd.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsreachd[i]), &bufferd, sizeof(double), 0)) {
							if (bufferd > 2.99 && bufferd < 6.01) { WriteProcessMemory(processhandle, (LPVOID)(locationsreachd[i]), &dreachd, sizeof(double), 0); }
							else { locationsreachd.erase(locationsreachd.begin() + i); i -= 2; }
						}
					}
					for (size_t i = 0; i < locationslimit.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationslimit[i]), &bufferd, sizeof(double), 0)) {
							if (bufferd > 4.49 && bufferd < 7.51) { WriteProcessMemory(processhandle, (LPVOID)(locationslimit[i]), &dlimitd, sizeof(double), 0); }
							else { locationslimit.erase(locationslimit.begin() + i); i -= 2; }
						}
					}
					cucklord_reach_used = false;
				}
			}
			else { cucklord_reach_used = false; }
		} std::this_thread::sleep_for(std::chrono::milliseconds(50));
	}
}

void reachscan(DWORD min, DWORD max) {
	if (HANDLE processhandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, false, cucklord_get_processid("javaw.exe"))) {

		bool breakscan = cucklord_reach_enabled; size_t address = min;
		while (address < max) {
			if (breakscan != cucklord_reach_enabled) { break; }
			std::vector<double> buffer(MEMBLOCK);
			std::vector<float> buffer2(MEMBLOCK);
			if (ReadProcessMemory(processhandle, (LPVOID)address, &buffer[0], MEMBLOCK, 0)) {
				for (size_t i = 0; i < buffer.size(); ++i) {
					if (breakscan != cucklord_reach_enabled) { break; }
					if (buffer[i] == 4.5) {
						for (size_t o = 0; o < buffer.size(); ++o) {
							if (buffer[o] == 3) {
								locationslimit.push_back(address + ((i + 1) * sizeof(double)) - sizeof(double)); sort(locationslimit.begin(), locationslimit.end());
								locationslimit.erase(unique(locationslimit.begin(), locationslimit.end()), locationslimit.end());

								locationsreachd.push_back(address + ((o + 1) * sizeof(double)) - sizeof(double)); sort(locationsreachd.begin(), locationsreachd.end());
								locationsreachd.erase(unique(locationsreachd.begin(), locationsreachd.end()), locationsreachd.end());
							}
						}
						if (ReadProcessMemory(processhandle, (LPVOID)address, &buffer2[0], MEMBLOCK, 0)) {
							for (size_t o = 0; o < buffer2.size(); ++o) {
								if (buffer2[o] == 3) {
									locationslimit.push_back(address + ((i + 1) * sizeof(double)) - sizeof(double)); sort(locationslimit.begin(), locationslimit.end());
									locationslimit.erase(unique(locationslimit.begin(), locationslimit.end()), locationslimit.end());

									locationsreachf.push_back(address + ((o + 1) * sizeof(float)) - sizeof(float)); sort(locationsreachf.begin(), locationsreachf.end());
									locationsreachf.erase(unique(locationsreachf.begin(), locationsreachf.end()), locationsreachf.end());
								}
							}
						}

					}
				}
			} address += MEMBLOCK;
		} CloseHandle(processhandle);
	}
}

namespace koid {
	namespace thread {
		void reach() {
			CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&reachupdater, nullptr, 0, 0);
			while (true) {

				while (cucklord_reach_enabled) {
					if (!cucklord_reach_used) { cucklord_reach_used = true; } reachscan(0x02000000, 0x08000000);
					for (int i = 0; i < 2500; ++i) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); if (!cucklord_reach_enabled) { break; } }
				}
				std::this_thread::sleep_for(std::chrono::milliseconds(10));
			}
		}
	}
}

void thread_reach() {
	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&reachupdater, nullptr, 0, 0);
	while (true) {

		while (cucklord_reach_enabled) {
			if (!cucklord_reach_used) { cucklord_reach_used = true; } reachscan(0x02000000, 0x06000000);
			for (int i = 0; i < 2500; ++i) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); if (!cucklord_reach_enabled) { break; } }
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}