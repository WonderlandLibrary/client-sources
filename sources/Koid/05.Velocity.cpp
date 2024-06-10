
#include "conector.h"

bool isbetween(double value, double min, double max) {
	if (value < min) { return false; }
	if (value > max) { return false; }
	return true;
}

std::vector<DWORD>locationsv1;
bool cucklord_velocity_used = false;

void velocityupdater() {
	double defaultvelocity = 8000;
	double buffer;

	while (true) {

		if (cucklord_velocity_used) {
			double newvelocity = 800000 / cucklord_velocity_value;
			if (HANDLE processhandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, false, cucklord_get_processid("javaw.exe"))) {

				if (cucklord_velocity_enabled) {
					for (size_t i = 0; i < locationsv1.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsv1[i]), &buffer, sizeof(double), 0)) {
							if (isbetween(buffer, -999999999, 999999999) && !isbetween(buffer, -1, 1)) {
								WriteProcessMemory(processhandle, (LPVOID)(locationsv1[i]), &newvelocity, sizeof(double), 0);
							}
							else { locationsv1.erase(locationsv1.begin() + i); i -= 2; }
						}
					}
				}
				else {
					for (size_t i = 0; i < locationsv1.size(); ++i) {
						if (ReadProcessMemory(processhandle, (LPVOID)(locationsv1[i]), &buffer, sizeof(double), 0)) {
							if (isbetween(buffer, -999999999, 999999999) && !isbetween(buffer, -1, 1)) {
								WriteProcessMemory(processhandle, (LPVOID)(locationsv1[i]), &defaultvelocity, sizeof(double), 0);
							}
							else { locationsv1.erase(locationsv1.begin() + i); i -= 2; }
						}
					}
					cucklord_velocity_used = false;
				}
			}
			else { cucklord_velocity_used = false; }
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(100));
	}
}

void velocityscan(DWORD min, DWORD max) {

	if (HANDLE processhandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, false, cucklord_get_processid("javaw.exe"))) {

		bool breakscan = cucklord_velocity_enabled; size_t address = min;
		while (address < max) {
			if (breakscan != cucklord_velocity_enabled) { break; }
			std::vector<double> buffer(MEMBLOCK);
			if (ReadProcessMemory(processhandle, (LPVOID)address, &buffer[0], MEMBLOCK, 0)) {
				for (size_t i = 0; i < buffer.size(); ++i) {
					if (breakscan != cucklord_velocity_enabled) { break; }
					if (buffer[i] == 8000) {
						if (buffer[i + 1] == 8000 && buffer[i + 2] == 8000) {
							if (buffer[i + 3] == 8000) { breakscan = true; break; }
							else {
								locationsv1.push_back(address + ((i + 1) * sizeof(double)) - sizeof(double));
								locationsv1.erase(unique(locationsv1.begin(), locationsv1.end()), locationsv1.end());

								locationsv1.push_back(address + ((i + 3) * sizeof(double)) - sizeof(double));
								locationsv1.erase(unique(locationsv1.begin(), locationsv1.end()), locationsv1.end());

								sort(locationsv1.begin(), locationsv1.end());
								locationsv1.erase(unique(locationsv1.begin(), locationsv1.end()), locationsv1.end());
							}
						}
					}
				}
			} address += MEMBLOCK;
		} CloseHandle(processhandle);
	}
}

void thread_velocity() {
	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&velocityupdater, nullptr, 0, 0);
	while (true) {
		while (cucklord_velocity_enabled) {
			if (!cucklord_velocity_used) { cucklord_velocity_used = true; } velocityscan(0x02000000, 0x08000000);
			for (int i = 0; i < 2500; ++i) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); if (!cucklord_velocity_enabled) { break; } }
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}