#pragma once
#include "stdafx.h"
#include <stdlib.h>

void auto_clicker() {
	auto click_left = []() {
		INPUT Input = { 0 };

		// left down
		Input.type = INPUT_MOUSE;
		Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;
		SendInput(1, &Input, sizeof(INPUT));

		// left up
		RtlSecureZeroMemory(&Input, sizeof(INPUT));
		Input.type = INPUT_MOUSE;
		Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;
		SendInput(1, &Input, sizeof(INPUT));
	};

	auto timer = []() {
		static double start_time = GetTickCount();
		double current_time = GetTickCount() - start_time;

		int cps = rand() % 2 + 11;//11-13 cps

		if (current_time >= (1000 / cps)) {
			start_time = GetTickCount();
			return true;
		}
		return false;
	};

	if (timer()) {
		click_left();
	}
}
