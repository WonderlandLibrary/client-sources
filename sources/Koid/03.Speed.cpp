
#include "conector.h"

std::vector<double>cucklord_speed_slider_values = { 1.0f, 1.1f,  1.2f,  1.3f,  1.4f,  1.5f,  1.6f,  1.7f,  1.8f,  1.9f,  2.0f };
std::vector<double>cucklord_speed_values = { 2000.00000000123, 1900.00000000123, 1800.00000000123, 1700.00000000123, 1600.00000000123, 1500.00000000123, 1400.00000000123, 1300.00000000123, 1200.00000000123, 1100.00000000123, 1000, 950.00000000123,  900.00000000123,  850.00000000123,  800.00000000123,  750.00000000123,  700.00000000123,  650.00000000123,  600.00000000123,  550.00000000123, 500.00000000123 };
DWORD cucklord_speed_pid; HANDLE cucklord_speed_phandle; bool cucklord_speed_used = false; double cucklord_speed_new; double cucklord_speed_default = 1000;

void cucklord_speed_function(std::vector<double> value, bool status, DWORD min, DWORD max)
{
	cucklord_speed_pid = cucklord_get_processid("javaw.exe"); int cucklord_reach_chance_temp = cucklord_random_int(0, 99);
	if (cucklord_speed_phandle = OpenProcess(THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, false, cucklord_speed_pid)) {
		INT64 cucklord_current_address = min; bool breakscan = cucklord_speed_enabled; MEMORY_BASIC_INFORMATION cucklord_memory;
		while (cucklord_current_address < max) {
			if (breakscan != cucklord_speed_enabled) { break; }
			std::vector<double> buffer(MEMBLOCK);
			if (ReadProcessMemory(cucklord_speed_phandle, (LPVOID)cucklord_current_address, &buffer[0], MEMBLOCK, 0)) {
				for (size_t i = 0; i < buffer.size(); ++i) {
					for (const auto f : value) {
						if (buffer[i] == f) {
							if (status) {
								if (cucklord_speed_tpmode) {
									WriteProcessMemory(cucklord_speed_phandle, (LPVOID)(cucklord_current_address + ((i + 1) * sizeof(double)) - sizeof(double)), &cucklord_speed_new, sizeof(double), 0);
									std::this_thread::sleep_for(std::chrono::milliseconds(20));
									WriteProcessMemory(cucklord_speed_phandle, (LPVOID)(cucklord_current_address + ((i + 1) * sizeof(double)) - sizeof(double)), &cucklord_speed_default, sizeof(double), 0);
								}
								else {
									WriteProcessMemory(cucklord_speed_phandle, (LPVOID)(cucklord_current_address + ((i + 1) * sizeof(double)) - sizeof(double)), &cucklord_speed_new, sizeof(double), 0);
								}
							}
							if (!status) {
								cucklord_speed_used = false;
								WriteProcessMemory(cucklord_speed_phandle, (LPVOID)(cucklord_current_address + ((i + 1) * sizeof(double)) - sizeof(double)), &cucklord_speed_default, sizeof(double), 0);
							}
							std::this_thread::sleep_for(std::chrono::milliseconds(1));
						}
					}
				}
			}
			cucklord_current_address += MEMBLOCK;
		}
		CloseHandle(cucklord_speed_phandle);
		if (cucklord_speed_tpmode) {
			cucklord_speed_enabled = false;
		}
	} else { cucklord_speed_used = false; }
}

void thread_speed() {
	while (true) {
		while (cucklord_speed_enabled) {
			cucklord_speed_used = true;
			if (cucklord_speed_value >= -1.05 & cucklord_speed_value < -0.95) { cucklord_speed_new = cucklord_speed_values[0]; }
			if (cucklord_speed_value >= -0.95 & cucklord_speed_value < -0.85) { cucklord_speed_new = cucklord_speed_values[1]; }
			if (cucklord_speed_value >= -0.85 & cucklord_speed_value < -0.75) { cucklord_speed_new = cucklord_speed_values[2]; }
			if (cucklord_speed_value >= -0.75 & cucklord_speed_value < -0.65) { cucklord_speed_new = cucklord_speed_values[3]; }
			if (cucklord_speed_value >= -0.65 & cucklord_speed_value < -0.55) { cucklord_speed_new = cucklord_speed_values[4]; }
			if (cucklord_speed_value >= -0.55 & cucklord_speed_value < -0.45) { cucklord_speed_new = cucklord_speed_values[5]; }
			if (cucklord_speed_value >= -0.45 & cucklord_speed_value < -0.35) { cucklord_speed_new = cucklord_speed_values[6]; }
			if (cucklord_speed_value >= -0.35 & cucklord_speed_value < -0.25) { cucklord_speed_new = cucklord_speed_values[7]; }
			if (cucklord_speed_value >= -0.25 & cucklord_speed_value < -0.15) { cucklord_speed_new = cucklord_speed_values[8]; }
			if (cucklord_speed_value >= -0.15 & cucklord_speed_value < -0.05) { cucklord_speed_new = cucklord_speed_values[9]; }

			if (cucklord_speed_value >= -0.05 & cucklord_speed_value < 0.05) { cucklord_speed_new = cucklord_speed_values[10]; }

			if (cucklord_speed_value >= 0.05 & cucklord_speed_value < 0.15) { cucklord_speed_new = cucklord_speed_values[11]; }
			if (cucklord_speed_value >= 0.15 & cucklord_speed_value < 0.25) { cucklord_speed_new = cucklord_speed_values[12]; }
			if (cucklord_speed_value >= 0.25 & cucklord_speed_value < 0.35) { cucklord_speed_new = cucklord_speed_values[13]; }
			if (cucklord_speed_value >= 0.35 & cucklord_speed_value < 0.45) { cucklord_speed_new = cucklord_speed_values[14]; }
			if (cucklord_speed_value >= 0.45 & cucklord_speed_value < 0.55) { cucklord_speed_new = cucklord_speed_values[15]; }
			if (cucklord_speed_value >= 0.55 & cucklord_speed_value < 0.65) { cucklord_speed_new = cucklord_speed_values[16]; }
			if (cucklord_speed_value >= 0.65 & cucklord_speed_value < 0.75) { cucklord_speed_new = cucklord_speed_values[17]; }
			if (cucklord_speed_value >= 0.75 & cucklord_speed_value < 0.85) { cucklord_speed_new = cucklord_speed_values[18]; }
			if (cucklord_speed_value >= 0.85 & cucklord_speed_value < 0.95) { cucklord_speed_new = cucklord_speed_values[19]; }
			if (cucklord_speed_value >= 0.95 & cucklord_speed_value < 1.05) { cucklord_speed_new = cucklord_speed_values[20]; }
			cucklord_speed_function(cucklord_speed_values, true, 0x02000000, 0x06FFFFFF);
			for (int i = 0; i < 2500; ++i) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); if (!cucklord_speed_enabled) { break; } }
		}
		while (!cucklord_speed_enabled) {
			if (cucklord_speed_used) {
				cucklord_speed_function(cucklord_speed_values, false, 0x02000000, 0x06FFFFFF);
				cucklord_speed_used = false;
			}
			for (int i = 0; i < 2500; ++i) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); if (cucklord_speed_enabled) { break; } }
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}