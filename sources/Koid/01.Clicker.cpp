#include "conector.h"



int cucklord_clicker_cps_temp; int cucklord_clicker_random_drop; int cucklord_clicker_sleep_ms; int cucklord_clicker_cps_min; int cucklord_clicker_cps_max;
int cucklord_clicker_random_stop; bool cucklord_clicker_random_drop_bool = false; bool cucklord_clicker_random_stop_bool = false;
int cucklord_clicker_random_drop_counter; int cucklord_clicker_current_cps; int cucklord_clicker_drop_counter = 0;
int cucklord_clicker_spike_counter = 0; bool cucklord_clicker_random_spike_bool = false; int cucklord_clicker_casual_int = 0;
int cucklord_clicker_casual_random_int = 0; int cucklord_clicker_total_clicks = 0; int cucklord_click_counter = 0;
int totalclicks = 0; bool hasdropped = false;

int cucklord_clicker_randomization() {

	cucklord_clicker_current_cps = cucklord_clicker_value + cucklord_random_int(0, 1); //current cps value
	cucklord_clicker_cps_min = cucklord_clicker_current_cps - cucklord_random_int(1, 2);
	cucklord_clicker_cps_max = cucklord_clicker_current_cps + cucklord_random_int(1, 2);

	cucklord_clicker_cps_temp = cucklord_random_int(cucklord_clicker_cps_min, cucklord_clicker_cps_max); //value between min and max
	cucklord_clicker_sleep_ms = (cucklord_random_int(950, 1050) / cucklord_clicker_cps_temp); //start using milliseconds
	cucklord_clicker_sleep_ms += cucklord_random_int(-3, 3); //anti randomization guess
	totalclicks++;
	return cucklord_clicker_sleep_ms;
}


char cucklord_clicker_jitter_window[128]{};

void thread_jitter()
{
	while (true)
	{
		while (cucklord_clicker_enabled && GetAsyncKeyState(VK_LBUTTON) & 0x8000)
		{
			POINT P;
			POINT P2;
			GetCursorPos(&P);
			GetCursorPos(&P2);
			HWND cucklord_clicker_jitter_window_mouse = WindowFromPoint(P);
			if (ScreenToClient(cucklord_clicker_jitter_window_mouse, &P))
			{
				if (!cucklord_inventory_status)
				{
					HWND cucklord_clicker_window_foreground = GetForegroundWindow();
					GetWindowTextA(cucklord_clicker_window_foreground, cucklord_clicker_jitter_window, 128);
					if (strstr(cucklord_clicker_jitter_window, cucklord_clicker_window))
					{
						int cucklord_clicker_jitter_random = cucklord_random_int(1, 10);
						if (cucklord_clicker_jitter_random < 2.5)
						{
							SetCursorPos(P2.x + cucklord_clicker_jitter_value, P2.y + cucklord_clicker_jitter_value);
						}
						if (cucklord_clicker_jitter_random > 2.5 & cucklord_clicker_jitter_random < 5)
						{
							SetCursorPos(P2.x - cucklord_clicker_jitter_value, P2.y + cucklord_clicker_jitter_value);
						}
						if (cucklord_clicker_jitter_random > 5 & cucklord_clicker_jitter_random < 7.5)
						{
							(P2.x - cucklord_clicker_jitter_value, P2.y - cucklord_clicker_jitter_value);
						}
						if (cucklord_clicker_jitter_random > 7.5 & cucklord_clicker_jitter_random < 11)
						{
							SetCursorPos(P2.x + cucklord_clicker_jitter_value, P2.y - cucklord_clicker_jitter_value);
						}
					}
				}
			}
			std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_random_int(20, 50)));
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}

char cucklord_clicker_clicker_window[128];

void thread_clicker()
{
	while (true)
	{
		while (cucklord_clicker_enabled && GetAsyncKeyState(VK_LBUTTON) & 0x8000)
		{
			POINT cucklord_clicker_mouse1; GetCursorPos(&cucklord_clicker_mouse1);
			HWND cucklord_clicker_clicker_window_mouse = WindowFromPoint(cucklord_clicker_mouse1);
			if (cucklord_clicker_minecraftonly) {
				if (FindWindow(("LWJGL"), nullptr) == GetForegroundWindow()) {
					if (ScreenToClient(cucklord_clicker_clicker_window_mouse, &cucklord_clicker_mouse1)) {
						if (cucklord_clicker_mouse1.y > 20) {
							if (!cucklord_clicker_inventory) {
								if (!cucklord_inventory_status) {
									PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
									std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
									PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
									std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
								}
							}
							else {
								PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
								std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
								PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
								std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
							}
						}
					}
				}
			}
			else {
				if (cucklord_clicker_clicker_window_mouse != hwnd) {
					PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
					std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
					PostMessage(cucklord_clicker_clicker_window_mouse, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(cucklord_clicker_mouse1.x, cucklord_clicker_mouse1.y));
					std::this_thread::sleep_for(std::chrono::milliseconds(cucklord_clicker_randomization() / 2));
				}
			}
			
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(100));
	}
}
