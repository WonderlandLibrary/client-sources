//N0LE'S amazing cpp autoclicker
//cracked by shitkid
// src.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include "pch.h"
#include <cstdlib>
#include <float.h>
#include <math.h>
#include <string>
#include <stdio.h>      
#include <stdlib.h> 
#include <ctime>
#include <iostream>
#include <iomanip>
#include <fstream>
#include <strsafe.h>
#include <winsock2.h>
#include <wininet.h>
#include <Windows.h>
#include <windowsx.h>
#include <algorithm>
#include <tchar.h>
#include <urlmon.h>
#include <conio.h>
#include <time.h>
#include <intrin.h>    
#include <iphlpapi.h>     
#include <cstdint>

#pragma comment(lib, "iphlpapi.lib")

#pragma comment(lib, "urlmon.lib")
#pragma comment(lib,"wininet.lib")
#pragma comment(lib, "Iphlpapi.lib")
#pragma comment(lib, "Urlmon.lib")
#pragma comment(lib, "Ws2_32.lib")
#pragma comment(lib, "winmm.lib")
#pragma comment(lib, "Dbghelp.lib")
#pragma comment(lib, "Advapi32.lib")
#pragma comment(lib, "User32.lib")

#define WIN_32_LEAN_AND_MEAN
#define	VK_F4 0x73
#define	VK_F5 0x74		
#define VK_W  0x57
#define VK_D  0x44
#define VK_A  0x41 
#define _CRT_SECURE_NO_WARNINGS
#define HEART   ((char)0x03)




HHOOK mouseHook;
HHOOK keyboardHook;

BOOLEAN toggle;
BOOLEAN hide = false;
BOOLEAN mouseDown;
BOOLEAN rmouseDown;

BOOLEAN firstClick;

long lastClick;

double s;
double s2;
double s3;
double s4;
double s5;
double s6;
double s7;
double s8;
double s9;
double s10;
double	h;
int c;
int c2;
int c3;
int c4;
int c5;
int c6;
int c7;
int c8;
int c9;
int c10;

int ucps;
int cps;
int cps2;
int cps3;
int cps4;
int cps5;
int cps6;
int cps7;
int cps8;
int cps9;
int cps10;
double M;
double N;
double display;
using namespace std;

double Double(double fMin, double fMax)
{
	double f = (double)rand() / RAND_MAX;
	return fMin + f * (fMax - fMin);
}

void WriteInColor(unsigned short color, string outputString)
{
	HANDLE hcon = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hcon, color);
	cout << outputString;
}

void ChangeColor(unsigned short color)
{
	HANDLE hcon = GetStdHandle(STD_OUTPUT_HANDLE);
	SetConsoleTextAttribute(hcon, color);
	
}
void down()
{
	INPUT    Input = { 0 };
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = 0x0002;
	::SendInput(1, &Input, sizeof(INPUT));
}
void up()
{
	INPUT    Input = { 0 };
	::ZeroMemory(&Input, sizeof(INPUT));
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = 0x0004;
	::SendInput(1, &Input, sizeof(INPUT));
}

void ClearConsole()
{
	HANDLE hStdOut = GetStdHandle(STD_OUTPUT_HANDLE);
	COORD coord = { 0, 0 };
	DWORD count;
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	if (GetConsoleScreenBufferInfo(hStdOut, &csbi))
	{
		FillConsoleOutputCharacter(hStdOut, (TCHAR)32, csbi.dwSize.X * csbi.dwSize.Y, coord, &count);
		FillConsoleOutputAttribute(hStdOut, csbi.wAttributes, csbi.dwSize.X * csbi.dwSize.Y, coord, &count);
		SetConsoleCursorPosition(hStdOut, coord);
	}
	return;
}
LRESULT CALLBACK MouseCallBack(int nCode, WPARAM wParam, LPARAM lParam)
{
	PMSLLHOOKSTRUCT pMouse = (PMSLLHOOKSTRUCT)lParam;
	if (NULL != pMouse)
	{
		if (WM_MOUSEMOVE != wParam)
		{
			// if it returns 0 then its a real click, u can check here: https://msdn.microsoft.com/en-us/library/windows/desktop/ms644970(v=vs.85).aspx
			if (0 == pMouse->flags)
			{
				switch (wParam)
				{
				case WM_LBUTTONDOWN:
					mouseDown = TRUE;
					firstClick = TRUE;
					break;
				case WM_LBUTTONUP:
					mouseDown = FALSE;
					break;
				case WM_RBUTTONUP:
					rmouseDown = FALSE;
					break;
				case WM_RBUTTONDOWN:
					rmouseDown = TRUE;
					break;

				}
			}
		}
	}
	return CallNextHookEx(mouseHook, nCode, wParam, lParam);
}

LRESULT CALLBACK KeyboardCallBack(int nCode, WPARAM wParam, LPARAM lParam)
{
	PKBDLLHOOKSTRUCT keyStruct = (PKBDLLHOOKSTRUCT)lParam;
	if (NULL != keyStruct
		&& WM_KEYUP == wParam
		&& VK_F4 == keyStruct->vkCode)
	{
		toggle = !toggle;
	}
	if (NULL != keyStruct
		&& WM_KEYUP == wParam
		&& VK_RSHIFT == keyStruct->vkCode)
	{
		hide = !hide;
	}
	return CallNextHookEx(keyboardHook, nCode, wParam, lParam);
}

DWORD WINAPI HookThread(LPVOID lParam)
{
	mouseHook = SetWindowsHookEx(WH_MOUSE_LL, &MouseCallBack, NULL, NULL);
	keyboardHook = SetWindowsHookEx(WH_KEYBOARD_LL, &KeyboardCallBack, NULL, NULL);

	MSG msg;
	while (GetMessage(&msg, NULL, 0, 0))
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	UnhookWindowsHookEx(mouseHook);
	UnhookWindowsHookEx(keyboardHook);
	return 0;
}

int RandomInt(int min, int max)
{
	srand(time(NULL));
	return ((rand() % (int)(((max)+1) - (min))) + (min));
}

DWORD WINAPI ClickThread(LPVOID lParam)
{

	while (TRUE)
	{
		Sleep(1);
		if (mouseDown && toggle)
		{	
			if (firstClick)
			{
				Sleep(30);
				up();
				firstClick = FALSE;
			}
			else
			{
				if (rmouseDown)
				{
					cps=ucps + 2;
				}
				double cha = Double(20, 50);
				double cha2 = Double(20, cha);
				double cha3 = Double(20, cha2);
				double cha4 = Double(cha3, 50);
				double cha5 = Double(cha4, 50);
				double cha6 = Double(20, cha5);
				double cha7 = Double(cha6, 50);
				double cha8 = Double(20, cha7);
				double cha9 = Double(cha8, 50);
				double cha10 = Double(20, cha9);
				int c = RandomInt(9, 11);
				int c2 = RandomInt(9, 11);
				int c3 = RandomInt(9, 11);
				int c4 = RandomInt(9, 11);
				int c5 = RandomInt(9, 11);
				int c6 = RandomInt(9, 11);
				int c7 = RandomInt(9, 11);
				int c8 = RandomInt(9, 11);
				int c9 = RandomInt(9, 11);
				int c10 = RandomInt(9, 11);
				double b = Double(1, 2);
				double b2 = Double(1, 2);
				double b3 = Double(1, 2);
				double b4 = Double(1, 2);
				double b5 = Double(1, 2);
				double b6 = Double(1, 2);
				double b7 = Double(1, 2);
				double b8 = Double(1, 2);
				double b9 = Double(1, 2);
				double b10 = Double(1, 2);
				double cpsA;
				cpsA = cps + b;
				double cpsB;
				cpsB = cps - b2;
				double cpsC;
				cpsC = cps + b3;
				double cpsD;
				cpsD = cps + b4;
				double cpsI;
				cpsI = cps + b5;
				double cpsH;
				cpsH = cps - b6;
				double cpsG;
				cpsG = cps + b7;
				double cpsT;
				cpsT = cps - b8;
				double cpsL;
				cpsL = cps - b9;
				double cpsS;
				cpsS = cps + b10;
				double cps2 = Double(cps, cpsA);
				double cps3 = Double(cps, cps2);
				double cps4 = Double(cps, cps3);
				double cps5 = Double(cps2, cps3);
				double cps6 = Double(cps, cps2);
				double cps7 = Double(cps4, cps5);
				double cps8 = Double(cps3, cps);
				double cps9 = Double(cps3, cps2);
				double cps10 = Double(cps7, cps);
				int g;
				int g2;
				int g3;
				int g4;
				int g5;
				int g6;
				int g7;
				int g8;
				int g9;
				double rmax1 = Double(0, 2);
				double rmax2 = Double(0, 2);
				double rmax3 = Double(0, 2);
				double rmax4 = Double(0, 2);
				double rmax5 = Double(0, 2);
				double rmax6 = Double(0, 2);
				double rmax7 = Double(0, 2);
				double rmax8 = Double(0, 2);
				double rmax9 = Double(0, 2);
				double rmax10 = Double(0, 2);
				s = (double)rand() / (RAND_MAX + rmax1) + cps + (rand() % c);
				if (rand() % 100 < cha) { g = cpsA + 1; }
				else { g = cpsA; }
				s2 = (double)rand() / (RAND_MAX + rmax2) + g + (rand() % c2);
				if (rand() % 100 < cha2) { g2 = cps2 + 1; }
				else { g2 = cpsA; }
				s3 = (double)rand() / (RAND_MAX + rmax3) + g2 + (rand() % c3);
				if (rand() % 100 < cha3) { g3 = cps + 1; }
				else { g3 = cpsA; }
				s4 = (double)rand() / (RAND_MAX + rmax4) + g3 + (rand() % c4);
				if (rand() % 100 < cha4) { g4 = cpsC; }
				else { g4 = cpsB; }
				s5 = (double)rand() / (RAND_MAX + rmax5) + g4 + (rand() % c5);
				if (rand() % 100 < cha5) { g5 = cpsG; }
				else { g5 = cpsD; }
				s6 = (double)rand() / (RAND_MAX + rmax6) + g5 + (rand() % c6);
				if (rand() % 100 < cha6) { g6 = cpsI + 1; }
				else { g6 = cpsA; }
				s7 = (double)rand() / (RAND_MAX + rmax7) + g6 + (rand() % c7);
				if (rand() % 100 < cha7) { g7 = cpsI + 1; }
				else { g7 = cpsL; }
				s8 = (double)rand() / (RAND_MAX + rmax8) + g7 + (rand() % c8);
				if (rand() % 100 < cha8) { g8 = cpsL + 1; }
				else { g8 = cpsA; }
				s9 = (double)rand() / (RAND_MAX + rmax9) + g8 + (rand() % c9);
				if (rand() % 100 < cha9) { g9 = cpsI + 1; }
				else { g9 = cpsB; }
				s10 = (double)rand() / (RAND_MAX + rmax10) + g9 + (rand() % c10);
				M = (s + s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9 + s10) / 10;
				double negativ = (3, 6);
				double delay = 1000 / (M - negativ);
				display = 970 / delay;
				if ((clock() - lastClick) > delay)
				{
					down();
					//calling left down
					//delay betwen clicks. waiting till clock-last click is > than M
					lastClick = clock();
					//bypass for cb check 
					Sleep(RandomInt(20, 35));
					//calling left up
					up();
				}
			}
		}

	}
}
void selfdestructed()
{
	system("color F3");
	cout << "Self Destructed !!! ";
	cout << "Delete Autoclicker with Shift+Delete !!! ";
}
void selfdestruct()
{
	WriteInColor(22, "");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\MuiCache \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\Bags \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Classes\\Local Settings\\Software\\Microsoft\\Windows\\Shell\\BagMRU \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\Shell\\Bags \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\Shell\\BagMRU \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Store \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\AppCompatFlags\\Compatibility Assistant\\Persisted \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\ShellNoRoam\\MUICache \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\OpenSavePidlMRU \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\LastVisitedPidlMRU \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\LastVisitedPidlMRULegacy \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\ComDlg32\\OpenSaveMRU \" /f \n");
	system("reg delete \"HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\UserAssist \" /f \n");
	system("for /F \"tokens=*\" % 1 in('wevtutil.exe el') DO wevtutil.exe cl \"%1\"");
	system("del C:\\Windows\\Prefetch\\*.* /Q");
}
void features()
{
	if (GetAsyncKeyState(VK_F8))
	{
		selfdestruct();
		selfdestructed();
	}
}

int wmain()
{
	
		TCHAR volumeName[MAX_PATH + 1] = { 0 };	
		TCHAR fileSystemName[MAX_PATH + 1] = { 0 };	
		DWORD serialNumber = 0;	
		DWORD maxComponentLen = 0;	
		DWORD fileSystemFlags = 0;	
		if (GetVolumeInformation(
			_T("C:\\"),
			volumeName,
			ARRAYSIZE(volumeName),
			&serialNumber,
			&maxComponentLen,
			&fileSystemFlags,
			fileSystemName,
			ARRAYSIZE(fileSystemName)))
				{
						_tprintf(_T("Volume Name: %s\n"), volumeName);		
						_tprintf(_T("Serial Number: %lu\n"), serialNumber);		
						_tprintf(_T("File System Name: %s\n"), fileSystemName);		
						_tprintf(_T("Max Component Length: %lu\n"), maxComponentLen);	
				}
		
	cout << "Please enter cps: ";
	Sleep(555);
	cin >> ucps;
	cps = ucps;
	SetConsoleTitle(L"");
	CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&HookThread, NULL, 0, 0);
	CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&ClickThread, NULL, 0, 0);
	
		while (TRUE)
	{
		Sleep(300);
		system("cls");
		features();
		WriteInColor(4, "jew v2.0.5 \n");
		WriteInColor(5, "Made with ");
		WriteInColor(5, "love");
		WriteInColor(5, " by the nigger himself");
		WriteInColor(6, "\n");

		WriteInColor(15, "SelfDestruct ");
		WriteInColor(7, "[");
		WriteInColor(14, "F8");
		WriteInColor(7, "]\n");
		WriteInColor(15, "Toggle ");
		WriteInColor(7, "[");
		WriteInColor(14, "F4");
		WriteInColor(7, "]\n");
		WriteInColor(15, "Status:");
		if (toggle)
		{
			WriteInColor(7, "[");
			WriteInColor(10, "On");
			WriteInColor(7, "]\t");
		}
		else
		{
			WriteInColor(7, "[");
			WriteInColor(12, "Off");
			WriteInColor(7, "]\t");
		}
		if (hide)
		{
			ShowWindow(GetConsoleWindow(), SW_HIDE);
		}
		else
		{
			ShowWindow(GetConsoleWindow(), SW_RESTORE);
		}
		WriteInColor(15, "CPS=");
		WriteInColor(5, "");
		printf("%.1lf",display );
		WriteInColor(15, "\nRightShift to Hide/Unhide");

	}
	printf(" error\n");
	_getch();
	return 1;

}