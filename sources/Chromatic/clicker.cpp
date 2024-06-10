#include "main.h"

int random(int min, int max)
{
	int randNum = rand() % (max - min + 1) + min;
	return randNum;
}

int randomac(int min, int max)
{
	int randNum = rand() % (max - min + 1) + min;
	return 1000 / randNum;
}

void thread::clicker()
{
	POINT pt;
	HWND hwnd;

	int mn;
	int mx;

	while (true)
	{
		while (modules::clicker::enabled)
		{
			GetCursorPos(&pt);
			hwnd = FindWindow(_T("LWJGL"), nullptr);

			if (modules::clicker::cps < 15)
			{
				mn = (modules::clicker::cps - 2);
				mx = (modules::clicker::cps + 2);
			}
			else
			{
				mn = (modules::clicker::cps - 1);
				mx = (modules::clicker::cps + 3);
			}

			if (random(1, 100) <= modules::clicker::chance)
			{
				if (GetAsyncKeyState(VK_LBUTTON) & 0x8000)
				{
					if (random(1, 100) <= modules::clicker::chance)
					{
						if (modules::clicker::sounds)
							PlaySound(modules::clicker::soundfile, 0, SND_FILENAME | SND_ASYNC);
						
						GetCursorPos(&pt);
						SendMessage(hwnd, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
						std::this_thread::sleep_for(std::chrono::milliseconds(random(6, 14) - random(random(-1, -3), random(1, 3))));
						SendMessage(hwnd, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
						std::this_thread::sleep_for(std::chrono::milliseconds(randomac(mn, mx) - random(random(-1, -3), random(1, 3))));

						if (random(1, 100) <= 5) // drop
						{
							std::this_thread::sleep_for(std::chrono::milliseconds(randomac(mn, mx) / 5));
						}
						else if (random(1, 100) <= 10) // spike
						{
							SendMessage(hwnd, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
							std::this_thread::sleep_for(std::chrono::milliseconds(random(6, 14) - random(random(-3, -6), random(3, 6))));
							SendMessage(hwnd, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
							std::this_thread::sleep_for(std::chrono::milliseconds(random(6, 14) - random(random(-3, -6), random(3, 6))));
						}
					}
				}
			}
			std::this_thread::sleep_for(std::chrono::milliseconds(1));
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}