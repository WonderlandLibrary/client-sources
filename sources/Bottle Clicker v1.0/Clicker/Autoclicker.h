#pragma once
#include <Windows.h>
#include <random>
void clickerthread();
inline HHOOK hookdoMouse;
inline bool pressedclicker;
inline bool mouseDown;
inline bool othermouse;

enum class MouseClick : int
{
	Left, Right
};
namespace hook
{
	inline	LRESULT __stdcall mouse_callback(int code, WPARAM wparam, LPARAM lparam)
	{

		MSLLHOOKSTRUCT* hook = (MSLLHOOKSTRUCT*)lparam;

		if ((hook->flags == LLMHF_INJECTED) || (hook->flags == LLMHF_LOWER_IL_INJECTED))

			return false;

		if ((hook->flags & LLMHF_INJECTED) == LLMHF_INJECTED)
		{

			return false;
		}

		if (wparam != WM_MOUSEMOVE)
		{

			if ((hook->flags == LLMHF_INJECTED) || (hook->flags == LLMHF_LOWER_IL_INJECTED))
				return false;

			switch (wparam)
			{

			case WM_LBUTTONDOWN:

				mouseDown = true;

				break;

			case WM_LBUTTONUP:

				mouseDown = false;

				break;

			case WM_RBUTTONDOWN:

				othermouse = true;

				break;

			case WM_RBUTTONUP:

				othermouse = false;

				break;
			}
		}
		return CallNextHookEx(hookdoMouse, code, wparam, lparam);
	}

	inline DWORD WINAPI hookmouse()
	{
		hookdoMouse = SetWindowsHookEx(WH_MOUSE_LL, &mouse_callback, NULL, 0);
		MSG msg;

		while (GetMessage(&msg, NULL, 0, 0))
		{
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}

		UnhookWindowsHookEx(hookdoMouse);
		return 0;
	}
}
namespace Random
{
	/// <summary>
	/// Initialize our mersenne twister with a random seed based on the clock (once at system startup)
	/// </summary>
	inline std::mt19937 mersenne{ static_cast<std::mt19937::result_type>(std::time(nullptr)) };

	__forceinline int GenerateNum(int min, int max)
	{
		// Create the distributiion
		//
		std::uniform_int_distribution die{ min, max };

		// Generate a random number from our global generator
		//
		return die(Random::mersenne);
	}
}
