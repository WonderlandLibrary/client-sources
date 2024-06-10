#include "main.h"

std::string check_bind(int k) {
	const char* const key_string[] = {
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
	return std::string(key_string[k]);
}

void thread::binds()
{
	while (true)
	{
		while (modules::clicker::bind_edit)
		{
			for (int i = 3; i < 256; i++)
			{
				if (i == VK_ESCAPE && GetAsyncKeyState(VK_ESCAPE) & SHRT_MAX)
				{
					modules::clicker::bind = 0; modules::clicker::bind_text = "[NONE]";
					modules::clicker::bind_edit = false;
					std::this_thread::sleep_for(std::chrono::milliseconds(500));
				}

				if (i != 13) // VK_PAUSE 
				{
					if (GetAsyncKeyState((i)&SHRT_MAX) && modules::clicker::bind_edit) {
						modules::clicker::bind = i; modules::clicker::bind_text = "[Bind: " + check_bind(i) + "]";
						modules::clicker::bind_edit = false;
						std::this_thread::sleep_for(std::chrono::milliseconds(500));
					}
				}
			}
		}

		while (modules::reach::bind_edit)
		{
			for (int i = 3; i < 256; i++)
			{
				if (i == VK_ESCAPE && GetAsyncKeyState(VK_ESCAPE) & SHRT_MAX)
				{
					modules::reach::bind = 0; modules::reach::bind_text = "[NONE]";
					modules::reach::bind_edit = false;
					std::this_thread::sleep_for(std::chrono::milliseconds(500));
				}

				if (i != 13) // VK_PAUSE 
				{
					if (GetAsyncKeyState((i)&SHRT_MAX) && modules::reach::bind_edit) {
						modules::reach::bind = i; modules::reach::bind_text = "[Bind: " + check_bind(i) + "]";
						modules::reach::bind_edit = false;
						std::this_thread::sleep_for(std::chrono::milliseconds(500));
					}
				}
			}
		}

		while (modules::velocity::bind_edit)
		{
			for (int i = 3; i < 256; i++)
			{
				if (i == VK_ESCAPE && GetAsyncKeyState(VK_ESCAPE) & SHRT_MAX)
				{
					modules::velocity::bind = 0; modules::velocity::bind_text = "[NONE]";
					modules::velocity::bind_edit = false;
					std::this_thread::sleep_for(std::chrono::milliseconds(500));
				}

				if (i != 13) // VK_PAUSE 
				{
					if (GetAsyncKeyState((i)&SHRT_MAX) && modules::velocity::bind_edit) {
						modules::velocity::bind = i; modules::velocity::bind_text = "[Bind: " + check_bind(i) + "]";
						modules::velocity::bind_edit = false;
						std::this_thread::sleep_for(std::chrono::milliseconds(500));
					}
				}
			}
		}

		if (GetAsyncKeyState(modules::clicker::bind) & 0x8000)
		{
			modules::clicker::enabled = !modules::clicker::enabled;
			std::this_thread::sleep_for(std::chrono::milliseconds(250));
		}

		if (GetAsyncKeyState(modules::reach::bind) & 0x8000)
		{
			modules::reach::enabled = !modules::reach::enabled;
			std::this_thread::sleep_for(std::chrono::milliseconds(250));
		}

		if (GetAsyncKeyState(modules::velocity::bind) & 0x8000)
		{
			modules::velocity::enabled = !modules::velocity::enabled;
			std::this_thread::sleep_for(std::chrono::milliseconds(250));
		}

		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}