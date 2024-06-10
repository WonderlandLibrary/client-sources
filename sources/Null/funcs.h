#pragma once
#include "xor.h"
#define VC_EXTRALEAN
#define WIN32_LEAN_AND_MEAN             // Exclude rarely-used stuff from Windows headers
#include <windows.h>
#include <random>
#include <string>
#include <thread>
#include<stdio.h>
#include <string.h>
#include <iostream>
#include <tchar.h>
#define MAX_KEY_LENGTH 255
#define MAX_VALUE_NAME 16383
static float value_foundreach;
static float reachlegit = 2.125;
static float r;
static float x;
static int keyac = 0;
static int delaycps;
float max_cps = 0.0f;
float min_cps = 0.0f;
float max_ms;
static bool mouse_down;
static bool bool_ac = false;
static bool pressed_ac = false;
HHOOK mouse_hook;

DWORD pid;
HANDLE pHandle;
LRESULT __stdcall call_back(int c, WPARAM wprm, LPARAM lprm)
{

	MSLLHOOKSTRUCT *h = (MSLLHOOKSTRUCT*)lprm;
	if ((h->flags != LLMHF_INJECTED) || (h->flags != LLMHF_LOWER_IL_INJECTED))
	{
		if ((h->flags & LLMHF_INJECTED) != LLMHF_INJECTED)

		{
			if (wprm != WM_MOUSEMOVE)
			{
				if ((h->flags == LLMHF_LOWER_IL_INJECTED) || (h->flags == LLMHF_INJECTED))
					return false;
				switch (wprm)
				{
				case WM_LBUTTONDOWN:
					mouse_down = true;
					break;
				case WM_LBUTTONUP:
					mouse_down = false;
					break;
				}
			}
			return CallNextHookEx(mouse_hook, c, wprm, lprm);
		}
		return false;
	}
	return false;
}


DWORD __cdecl mouse_h()
{
	mouse_hook = SetWindowsHookEx(WH_MOUSE_LL, &call_back, nullptr, 0);

	MSG message;

	while (GetMessage(&message, nullptr, 0, 0))
	{
		TranslateMessage(&message);
		DispatchMessage(&message);
	}
	UnhookWindowsHookEx(mouse_hook);

	return 0;

}

void DeleteKey()
{
	HKEY hKey = NULL;
	long resposta = RegOpenKeyEx(HKEY_CURRENT_USER,
		_T(xor("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\OpenSavePidlMRU\\*")),
		0L,
		KEY_ALL_ACCESS,
		&hKey);
	if (resposta == ERROR_SUCCESS)
	{
		RegDeleteValue(hKey, _T(xor ("MRUListEx")));
		RegCloseKey(hKey);
	}
	resposta = RegOpenKeyEx(HKEY_CURRENT_USER, _T(xor("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\LastVisitedPidlMRU")), 0L, KEY_ALL_ACCESS, &hKey);
	if (resposta == ERROR_SUCCESS)
	{
		RegDeleteValue(hKey, _T(xor ("MRUListEx")));
		RegCloseKey(hKey);
	}
	resposta = RegOpenKeyEx(HKEY_CURRENT_USER, _T(xor("Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\OpenSavePidlMRU\\dll")), 0L, KEY_ALL_ACCESS, &hKey);
	if (resposta == ERROR_SUCCESS)
	{
		RegDeleteValue(hKey, _T(xor("MRUListEx")));
		RegCloseKey(hKey);
	}
}
BOOL e_d_p()
{
	HWND hWnd11 = FindWindow(_T(xor ("LWJGL")), nullptr);
	HANDLE hToken;
	LUID luid;
	TOKEN_PRIVILEGES tkp;

	if (OpenProcessToken(hWnd11, TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &hToken))
	{
	LookupPrivilegeValue(nullptr, SE_DEBUG_NAME, &luid);
	tkp.PrivilegeCount = 1;
	tkp.Privileges[0].Luid = luid;
	tkp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
	AdjustTokenPrivileges(hToken, false, &tkp, sizeof(tkp), nullptr, nullptr);
	CloseHandle(hToken);
	}
	return TRUE;
}
int randomizationAc(int max, int min)
{
	int  range;
	range = max - min;
	return rand() % range + min - rand() % 30;
}
void autoc(void)
{
	HWND hWnd;
	int loop_ac = 1;
	max_ms = max_cps * 1000;
	while (loop_ac == 1)
	{
		if (bool_ac && mouse_down)
		{
				mouse_event(MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0);

				mouse_event(MOUSEEVENTF_LEFTUP, 0, 0, 0, 0);
			}
			std::this_thread::sleep_for(std::chrono::milliseconds((randomizationAc(min_cps, max_ms)))); //randomization cps
	}
}


void menu()
{

	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	printf(xor (" \n") );
	printf(xor ("                        88b 88 88   88 88     88    "));
	printf(xor ("                        88Yb88 88   88 88     88    ") );
	printf(xor ("                        88 Y88 Y8   8P 88  .o 88  .o"));
	printf(xor ("                        88  Y8 `YbodP' 88ood8 88ood8"));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("                                            by six#0404   \n\n\n"));
	printf(xor ("\n\n\n\n\n\nReach-> "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	std::cout << r << std::endl;
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf( xor("Autoclicker->"));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	printf(xor(" Min Cps "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("["));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf("%i",min_cps);
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("\n] | "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	printf(xor("Max Cps "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("["));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf("%i", max_cps);
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("]"));
	printf(xor("Toggle A.C KEY -> "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	std::cout << xor("0x") << keyac;
	printf("\n");
	while (true)
	{
		if (GetKeyState(keyac) & 0x8000 && !pressed_ac) { /* Basic keybind system credits to https://github.com/concordsdev/c-clicker/blob/master/main.cpp#L54 */
			pressed_ac = true;
		}
		else if (!(GetKeyState(keyac) & 0x8000) && pressed_ac) {
			bool_ac = !bool_ac;
			pressed_ac = false;
		}
		if (bool_ac == true)
		{
			SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 2 | FOREGROUND_INTENSITY);
			printf(xor("\r[!] < A.C"));
		}
		else
		{
			SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
			printf(xor("\r[!] < A.C"));
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}
void reachtest(void)
{
	e_d_p(); //badlion bypass
	HWND mc_hWnd = FindWindow(_T("LWJGL"), nullptr);
	GetWindowThreadProcessId(mc_hWnd, &pid);
	pHandle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pid);
	int loop = 1;
	int init_addr = 0x02A0000C;
	while (loop == 1)
	{
		while (init_addr < 0x04FFFFFF)
		{
			if (ReadProcessMemory(pHandle, (void*)init_addr, &value_foundreach, sizeof(float), 0))
			{
				if (value_foundreach == reachlegit) {
					WriteProcessMemory(pHandle, (void*)(init_addr), &x, sizeof(float), 0);
					std::this_thread::sleep_for(std::chrono::milliseconds(1));
				}
			}
			init_addr += 0x00000008; 
		}
		init_addr  = 0x02A0000C; //resets init_addr to repeat again
		std::this_thread::sleep_for(std::chrono::milliseconds(1));
	}
}
