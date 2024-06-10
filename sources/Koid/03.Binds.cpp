#include "conector.h"

std::string cucklord_check_bind(int k) {
	const char* const cucklord_key_string[] = {
		"Unknown",
		"LBUTTON",
		"RBUTTON",
		"CANCEL",
		"MBUTTON",
		"XBUTTON1",
		"XBUTTON2",
		"Unknown",
		"BACK",
		"TAB",
		"Unknown",
		"Unknown",
		"CLEAR",
		"RETURN",
		"Unknown",
		"Unknown",
		"SHIFT",
		"CONTROL",
		"MENU",
		"PAUSE",
		"CAPITAL",
		"KANA",
		"Unknown",
		"JUNJA",
		"FINAL",
		"KANJI",
		"Unknown",
		"ESCAPE",
		"CONVERT",
		"NONCONVERT",
		"ACCEPT",
		"MODECHANGE",
		"SPACE",
		"PRIOR",
		"NEXT",
		"END",
		"HOME",
		"LEFT",
		"UP",
		"RIGHT",
		"DOWN",
		"SELECT",
		"PRINT",
		"EXECUTE",
		"SNAPSHOT",
		"INSERT",
		"DELETE",
		"HELP",
		"0",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"A",
		"B",
		"C",
		"D",
		"E",
		"F",
		"G",
		"H",
		"I",
		"J",
		"K",
		"L",
		"M",
		"N",
		"O",
		"P",
		"Q",
		"R",
		"S",
		"T",
		"U",
		"V",
		"W",
		"X",
		"Y",
		"Z",
		"LWIN",
		"RWIN",
		"APPS",
		"Unknown",
		"SLEEP",
		"0",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"*",
		"+",
		"SEPARATOR",
		"-",
		"DECIMAL",
		"/",
		"F1",
		"F2",
		"F3",
		"F4",
		"F5",
		"F6",
		"F7",
		"F8",
		"F9",
		"F10",
		"F11",
		"F12",
		"F13",
		"F14",
		"F15",
		"F16",
		"F17",
		"F18",
		"F19",
		"F20",
		"F21",
		"F22",
		"F23",
		"F24",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"NUMLOCK",
		"SCROLL",
		"EQUAL",
		"MASSHOU",
		"TOUROKU",
		"LOYA",
		"ROYA",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"Unknown",
		"LSHIFT",
		"RSHIFT",
		"CONTROL",
		"CONTROL",
		"MENU",
		"RMENU"
	};
	return std::string(cucklord_key_string[k]);
}

int cucklord_bind_selected = 0;
int cucklord_bind_module = 0;
int cucklord_bind_selected2 = 0;
int cucklord_bind_module2 = 0;
int cucklord_bind_selected3 = 0;
int cucklord_bind_module3 = 0;
int cucklord_bind_selected4 = 0;
int cucklord_bind_module4 = 0;
int cucklord_bind_selected5 = 0;
int cucklord_bind_module5 = 0;
int cucklord_bind_selected6 = 0;
int cucklord_bind_module6 = 0;
int cucklord_bind_selected7 = 0;
int cucklord_bind_module7 = 0;
int cucklord_bind_selected8 = 0;
int cucklord_bind_module8 = 0;

void thread_binds() {
	while (true) {

		if (cucklord_clicker_bind_pressed) {
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
			cucklord_bind_selected = 1;
			cucklord_bind_module = 0;
			bool cucklord_bind_first_loop = false; int bool_counter = 0;
			while (cucklord_bind_module == 0) {
				if (bool_counter < 1) { cucklord_bind_first_loop = true; } else { cucklord_bind_first_loop = false; }
				std::this_thread::sleep_for(std::chrono::milliseconds(2));
				for (int i = 3; i < 256; i++) {
					if (i != 13) {//VK_PAUSE
						if (GetAsyncKeyState((i)&SHRT_MAX) && cucklord_bind_module == 0) {
							if (!cucklord_bind_first_loop) {
								cucklord_bind_module = i; cucklord_clicker_bind_text = cucklord_check_bind(i);
								std::this_thread::sleep_for(std::chrono::milliseconds(250));
							}
						}
					}
				}
				cucklord_bind_selected = 0;
				cucklord_clicker_bind_pressed = false;
				bool_counter++;
			}		
		}
		if (GetAsyncKeyState(cucklord_bind_module) & 0x8000) {
			if (!cucklord_clicker_enabled) {
				while (GetAsyncKeyState(cucklord_bind_module) & 0x8000) {
					cucklord_clicker_enabled = true; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
			else {
				while (GetAsyncKeyState(cucklord_bind_module) & 0x8000) {
					cucklord_clicker_enabled = false; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
		}

		if (cucklord_reach_bind_pressed) {
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
			cucklord_bind_selected2 = 1;
			cucklord_bind_module2 = 0;
			bool cucklord_bind_first_loop = false; int bool_counter = 0;
			while (cucklord_bind_module2 == 0) {
				if (bool_counter < 1) { cucklord_bind_first_loop = true; }
				else { cucklord_bind_first_loop = false; }
				std::this_thread::sleep_for(std::chrono::milliseconds(2));
				for (int i = 3; i < 256; i++) {
					if (i != 13) {//VK_PAUSE
						if (GetAsyncKeyState((i)&SHRT_MAX) && cucklord_bind_module2 == 0) {
							if (!cucklord_bind_first_loop) {
								cucklord_bind_module2 = i; cucklord_reach_bind_text = cucklord_check_bind(i);
								std::this_thread::sleep_for(std::chrono::milliseconds(250));
							}
						}
					}
				}
				cucklord_bind_selected2 = 0;
				cucklord_reach_bind_pressed = false;
				bool_counter++;
			}
		}
		if (GetAsyncKeyState(cucklord_bind_module2) & 0x8000) {
			if (!cucklord_reach_enabled) {
				while (GetAsyncKeyState(cucklord_bind_module2) & 0x8000) {
					cucklord_reach_enabled = true; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
			else {
				while (GetAsyncKeyState(cucklord_bind_module2) & 0x8000) {
					cucklord_reach_enabled = false; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
		}

		if (cucklord_speed_bind_pressed) {
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
			cucklord_bind_selected3 = 1;
			cucklord_bind_module3 = 0;
			bool cucklord_bind_first_loop = false; int bool_counter = 0;
			while (cucklord_bind_module3 == 0) {
				if (bool_counter < 1) { cucklord_bind_first_loop = true; }
				else { cucklord_bind_first_loop = false; }
				std::this_thread::sleep_for(std::chrono::milliseconds(2));
				for (int i = 3; i < 256; i++) {
					if (i != 13) {//VK_PAUSE
						if (GetAsyncKeyState((i)&SHRT_MAX) && cucklord_bind_module3 == 0) {
							if (!cucklord_bind_first_loop) {
								cucklord_bind_module3 = i; cucklord_speed_bind_text = cucklord_check_bind(i);
								std::this_thread::sleep_for(std::chrono::milliseconds(250));
							}
						}
					}
				}
				cucklord_bind_selected3 = 0;
				cucklord_speed_bind_pressed = false;
				bool_counter++;
			}
		}
		if (GetAsyncKeyState(cucklord_bind_module3) & 0x8000) {
			if (!cucklord_speed_enabled) {
				while (GetAsyncKeyState(cucklord_bind_module3) & 0x8000) {
					cucklord_speed_enabled = true; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
			else {
				while (GetAsyncKeyState(cucklord_bind_module3) & 0x8000) {
					cucklord_speed_enabled = false; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
		}
		if (cucklord_velocity_bind_pressed) {
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
			cucklord_bind_selected4 = 1;
			cucklord_bind_module4 = 0;
			bool cucklord_bind_first_loop = false; int bool_counter = 0;
			while (cucklord_bind_module4 == 0) {
				if (bool_counter < 1) { cucklord_bind_first_loop = true; }
				else { cucklord_bind_first_loop = false; }
				std::this_thread::sleep_for(std::chrono::milliseconds(2));
				for (int i = 3; i < 256; i++) {
					if (i != 13) {//VK_PAUSE
						if (GetAsyncKeyState((i)&SHRT_MAX) && cucklord_bind_module4 == 0) {
							if (!cucklord_bind_first_loop) {
								cucklord_bind_module4 = i; cucklord_velocity_bind_text = cucklord_check_bind(i);
								std::this_thread::sleep_for(std::chrono::milliseconds(250));
							}
						}
					}
				}
				cucklord_bind_selected4 = 0;
				cucklord_velocity_bind_pressed = false;
				bool_counter++;
			}
		}
		if (GetAsyncKeyState(cucklord_bind_module4) & 0x8000) {
			if (!cucklord_velocity_enabled) {
				while (GetAsyncKeyState(cucklord_bind_module4) & 0x8000) {
					cucklord_velocity_enabled = true; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
			else {
				while (GetAsyncKeyState(cucklord_bind_module4) & 0x8000) {
					cucklord_velocity_enabled = false; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
		}

		if (cucklord_hide_bind_pressed) {
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
			cucklord_bind_selected6 = 1;
			cucklord_bind_module6 = 0;
			bool cucklord_bind_first_loop = false; int bool_counter = 0;
			while (cucklord_bind_module6 == 0) {
				if (bool_counter < 1) { cucklord_bind_first_loop = true; }
				else { cucklord_bind_first_loop = false; }
				std::this_thread::sleep_for(std::chrono::milliseconds(2));
				for (int i = 3; i < 256; i++) {
					if (i != 13) {//VK_PAUSE
						if (GetAsyncKeyState((i)&SHRT_MAX) && cucklord_bind_module6 == 0) {
							if (!cucklord_bind_first_loop) {
								cucklord_bind_module6 = i; cucklord_hide_bind_text = cucklord_check_bind(i);
								std::this_thread::sleep_for(std::chrono::milliseconds(250));
							}
						}
					}
				}
				cucklord_bind_selected6 = 0;
				cucklord_hide_bind_pressed = false;
				bool_counter++;
			}
		}
		if (GetAsyncKeyState(cucklord_bind_module6) & 0x8000) {
			if (!cucklord_hide_enabled) {
				ShowWindow(hwnd, SW_HIDE);
				while (GetAsyncKeyState(cucklord_bind_module6) & 0x8000) {
					cucklord_hide_enabled = true; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
			else {
				ShowWindow(hwnd, SW_SHOW);
				while (GetAsyncKeyState(cucklord_bind_module6) & 0x8000) {
					cucklord_hide_enabled = false; std::this_thread::sleep_for(std::chrono::milliseconds(5));
				}
			}
		}

		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}