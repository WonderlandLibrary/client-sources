#include <d3d9.h>
#include <random>
#include <chrono>

int random(int min, int max)
{
	srand((int)time*(time(NULL)));

	int r = (rand() % (max - min)) + min;

	return r;
}

void autoclicker(bool mouse_down, int cps, bool randomize, bool breakblock) {
	int simin = 1, simax = 2;
	static int counter = 1;
	static int counter3 = 0;
	static bool counter2 = true;
	static int mincps = cps - 1;
	static int maxcps = cps + 1;
	static bool first = true;

	static auto clicker = []() {
		INPUT Input = { 0 };

		//up
		RtlSecureZeroMemory(&Input, sizeof(INPUT));
		Input.type = INPUT_MOUSE;
		Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;
		SendInput(1, &Input, sizeof(INPUT));

		// left down
		Input.type = INPUT_MOUSE;
		Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;
		SendInput(1, &Input, sizeof(INPUT));
	};

	static auto timer = [randomize, simin, simax, cps]() {
		static double start_time = GetTickCount();
		double current_time = GetTickCount() - start_time;

		int cps2 = randomize ? random(simin, simax) : cps;


		if (current_time >= (1000 / cps2)) {
			start_time = GetTickCount();
			cps2 = cps;
			return true;
		}
		return false;
	};

	if (timer()) {

		if (mouse_down)
		{
			if (counter2) {
				if (1000 / counter == 20)
				{
					simax -= 1;
					simin -= 1;
					counter = 1;
					counter3++;
				}
			}
			else {
				if (1000 / counter == 20)
				{
					simax += 1;
					simin += 1;
					counter = 1;
					counter3++;
				}
			}
			if (counter3 > 2) {
				counter2 = !counter2;
				counter3 = 0;
			}
			clicker();
			counter++;
			//autoclicker(mouse_down, cps, randomize, breakblock);
		}
		else
		{
			simax = maxcps;
			simin = mincps;
		}
		counter++;
	}
}