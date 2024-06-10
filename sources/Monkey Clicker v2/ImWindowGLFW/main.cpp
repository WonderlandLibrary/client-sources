#include <Windows.h>
#include <stdio.h>
#include <conio.h>
#include <string>
#include <future>
#include <strsafe.h>
#include <codecvt>
#include "picosha2.h"
using namespace std;
#include <comdef.h>
#include <Wbemidl.h>
#include <bitset>
#pragma comment(lib, "wbemuuid.lib")
//#include <iterator>
#include <random>

#include <chrono>

#include <curl/curl.h>

#include "imgui.h"
#include "examples/imgui_impl_glfw.h"
#include "examples/imgui_impl_opengl2.h"
#include <GLFW/glfw3.h>
static void glfw_error_callback(int error, const char* description)
{
	char* buffer;

	snprintf(buffer, sizeof(buffer), "Glfw Error %d: %s\n", error, description);
	MessageBoxA(nullptr, LPCSTR(buffer), "", MB_OK | MB_ICONERROR);

}

static size_t WriteCallback(void* contents, size_t size, size_t nmemb, void* userp)
{
	((std::string*)userp)->append((char*)contents, size * nmemb);

	return size * nmemb;
}


bool enterpressionado;
bool loggedinglfw;
static int page = 0;
int jitterdelay = 50;
std::string confighash;
int slidernovo = 1;
const char* tipos_cps[] =
{
	"Old Slider",
	"New Slider",
	"Blatant"
};
std::string confighashtemp;
int beepstatus;
bool newslots[9] = { true };
bool slotpermitido = false;
bool controletotal = false;
//static inline ImVec2 operator+(const ImVec2& lhs, const ImVec2& rhs) { return ImVec2(lhs.x + rhs.x, lhs.y + rhs.y); }
//static inline ImVec2 operator-(const ImVec2& lhs, const ImVec2& rhs) { return ImVec2(lhs.x - rhs.x, lhs.y - rhs.y); }

bool jittertoggle = false;
bool logado = false;
bool soundfinish;
bool chatopen;
bool togglesprint = false;
bool issprinting = false;
bool beeptoggle;
bool hidewindowtoggle;
static int jitterclirk = 1;
bool autopotapply;


bool openinventory = false;

bool inventoryonly = false;
bool togglewtap;
int wtapdelaymin;


static int nigga1 = 7;
static int nigga2 = 12;
static float cps_min = 7;
string hwidserials;
static float cps_max = 12;
bool imguicontrole = true;
string stringsal = "Uq$783mye8zENF$^";
string hashhwid;
string username;
bool shouldiplaysound;
bool shouldiplaysoundright;
string password;
#include "encrypt.h"
HHOOK hookdoMouse;
HHOOK RIGHThookdoMouse;
HHOOK hookdoTeclado;
std::string niggatitlenigga;
bool ligado;
BOOLEAN mouseDown;
BOOLEAN RIGHTmouseDown;
BOOL controle;
int currentslot;
int textslot;;
bool filterslots;

bool cleanstrings = true;
bool cleanfiles = true;
char* soundstext[] =
{
	"G303",
	"G502",
	"G303 V2",
	"GPro",
	"HP Mouse",
	"Generic Microsoft Mouse",
	"Old Mouse",
	"Oof"
};
bool cleanitself;
bool soundstoggle;
bool inventoryjitter = false;
bool rightmouseligado;
bool destructconfig;
int refreshdelay = 500;
std::string janelaatual;

char janelaminecraftchar[200] = "Minecraft";

int errorcondition;

POINT mouse;
INPUT leftclick;
INPUT rightclick;

float cps_min_ms;
float cps_max_ms;
float delayy;


int delayupdown;
int cpsatual;


int slotmin = 2;
int slotmax = 9;
int slotsword = 1;
bool autopottoggle;
float autopotdelay = 100;

bool minecraftaberto = false;
bool debuginfo = false;
bool windowonly = false;

static int clickcounter;
bool blockhit = false;
bool randomizeblockhit = false;
int randomizationpercentageblockhit = 40;
int blockhitcurrentchance;

#include <windows.h>
#include "resource.h"


static char charusername[300];
static char charpassword[300];
bool randomize = true;
int clickerkeybind = VK_F4;
int autopotkeybind;
bool autopotkeybinddown;
int selectedsound;
std::string query;
std::string query2;
std::string query3;

char charquery[1000];
char charquery2[1000];
char charquery3[1000];

std::string mysqlusername;
std::string mysqlserver;
std::string mysqlpassword;
std::string mysqldatabase;
bool selfdeletetoggle;

//hwid

std::string base64_encode(unsigned char const*, unsigned int len);
std::string base64_decode(std::string const& s);
std::string XOR(std::string value, std::string key);

static inline bool is_base64(unsigned char c) {
	return (isalnum(c) || (c == '+') || (c == '/'));
}
static const std::string base64_chars =
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"abcdefghijklmnopqrstuvwxyz"
"0123456789+/";

std::string base64_encode(unsigned char const* bytes_to_encode, unsigned int in_len) {
	std::string ret;
	int i = 0;
	int j = 0;
	unsigned char char_array_3[3];
	unsigned char char_array_4[4];

	while (in_len--) {
		char_array_3[i++] = *(bytes_to_encode++);
		if (i == 3) {
			char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
			char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
			char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
			char_array_4[3] = char_array_3[2] & 0x3f;

			for (i = 0; (i < 4); i++)
				ret += base64_chars[char_array_4[i]];
			i = 0;
		}
	}

	if (i)
	{
		for (j = i; j < 3; j++)
			char_array_3[j] = '\0';

		char_array_4[0] = (char_array_3[0] & 0xfc) >> 2;
		char_array_4[1] = ((char_array_3[0] & 0x03) << 4) + ((char_array_3[1] & 0xf0) >> 4);
		char_array_4[2] = ((char_array_3[1] & 0x0f) << 2) + ((char_array_3[2] & 0xc0) >> 6);
		char_array_4[3] = char_array_3[2] & 0x3f;

		for (j = 0; (j < i + 1); j++)
			ret += base64_chars[char_array_4[j]];

		while ((i++ < 3))
			ret += '=';

	}

	return ret;

}


std::string XOR(std::string value, std::string key)
{
	std::string retval(value);
	long unsigned int klen = key.length();
	long unsigned int vlen = value.length();
	unsigned long int k = 0;
	unsigned long int v = 0;
	for (; v < vlen; v++) {
		retval[v] = value[v] ^ key[k];
		k = (++k < klen ? k : 0);
	}
	return retval;
}

void concatenateHwid(std::wstring queryy, std::wstring propNameOfResultObject)
{
	WmiQueryResult res;
	res = getWmiQueryResult(queryy, propNameOfResultObject);

	if (res.Error != WmiQueryError::None)
	{
		return; // Exitting function
	}

	for (const auto& item : res.ResultList)
	{
		hwidserials = hwidserials + ws2s(item);
	}
}

extern int controlehidewindow;
// Hooks

LRESULT __stdcall mouse_callback(int code, WPARAM wparam, LPARAM lparam)
{
	MSLLHOOKSTRUCT* hook = (MSLLHOOKSTRUCT*)lparam;


	if ((hook->flags == LLMHF_INJECTED) || (hook->flags == LLMHF_LOWER_IL_INJECTED))
		hook->flags = 0;
	else {
		if (wparam != WM_MOUSEMOVE)
		{

			if (logado)
			{
				switch (wparam)
				{
				case WM_MOUSEWHEEL:
					if (wparam == WM_MOUSEWHEEL)
					{
						if (HIWORD(hook->mouseData) == 120)
						{
							if (currentslot == 49)
							{
								currentslot = 57;
							}
							else
							{
								currentslot--;
							}
						}
						else
						{
							if (currentslot == 57)
							{
								currentslot = 49;
							}
							else
							{
								currentslot++;
							}
						}
					}
					break;
				case WM_LBUTTONDOWN:
					mouseDown = true;
					break;
				case WM_LBUTTONUP:
					mouseDown = false;
					break;


				case WM_RBUTTONDOWN:
					RIGHTmouseDown = true;
					break;
				case WM_RBUTTONUP:
					RIGHTmouseDown = false;
					break;
				}
			}
		}
	}


	try
	{
		// Pass modified struct down the chain?
		return CallNextHookEx(hookdoMouse, code, wparam, (LPARAM)hook);
	}
	catch (int e)
	{
		// Failed, return original untouched struct
		return CallNextHookEx(hookdoMouse, code, wparam, lparam);
	}
}

#include <iostream>
#include <fstream>
#include <cstdint>
#include <filesystem>
void rightdown()
{
	INPUT Input = { 0 };
	// left down
	shouldiplaysoundright = true;
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = MOUSEEVENTF_RIGHTDOWN;
	SendInput(1, &Input, sizeof(INPUT));
}

void rightup()
{

	// left up
	shouldiplaysoundright = false;
	INPUT Input = { 0 };
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = MOUSEEVENTF_RIGHTUP;
	SendInput(1, &Input, sizeof(INPUT));
}

bool is_file_exist(const char* fileName)
{
	std::ifstream infile(fileName);
	return infile.good();
}


struct ARGS
{
	int i;
	int d;
};

bool resetinventorykey;

int AutoPot(bool resetinventorytoggle) // thread dos clicks
{
	int slotatual;
	int slotatualint;

	INPUT numberinput;

	numberinput.type = INPUT_KEYBOARD;
	numberinput.ki.wScan = 0;
	numberinput.ki.time = 0;
	numberinput.ki.dwExtraInfo = 0;
	numberinput.ki.wVk = 0x53;
	slotatual = slotmin;

	Sleep(autopotdelay / 2);
	float autopotdelayrand = distribucaoint((autopotdelay * 0.75), (autopotdelay * 1.25));
	if (autopotapply)
	{
		slotatual = slotmin;
		autopotapply = false;
	}
	if (slotatual > slotmax)
	{
		slotatual = slotmin;
	}
	slotatualint = slotatual + 48;
	numberinput.ki.wVk = slotatualint;
	if (resetinventorykey)
	{
		slotatual = slotmin;
		resetinventorykey = false;
	}
	if (autopottoggle)
	{
		numberinput.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
		SendInput(1, &numberinput, sizeof(INPUT));

		numberinput.ki.dwFlags = KEYEVENTF_KEYUP; // there is no KEYEVENTF_KEYDOWN
		SendInput(1, &numberinput, sizeof(INPUT));

		Sleep(autopotdelayrand);
		rightdown();
		Sleep(distribucaoint(10, 20));
		rightup();

		Sleep(autopotdelayrand);

		numberinput.ki.wVk = slotsword + 48;

		numberinput.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
		SendInput(1, &numberinput, sizeof(INPUT));

		numberinput.ki.dwFlags = KEYEVENTF_KEYUP; // there is no KEYEVENTF_KEYDOWN
		SendInput(1, &numberinput, sizeof(INPUT));
		slotatual++;
		autopotkeybinddown = false;
	}

	return 0;
}
LRESULT CALLBACK testeTeclado(int nCode, WPARAM wParam, LPARAM lParam) // listener do teclado
{
	PKBDLLHOOKSTRUCT keyStruct = (PKBDLLHOOKSTRUCT)lParam;
	if (logado)
	{
		if (windowonly)
		{
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam) {
				//SELFDESTRUCT
				if (VK_F9 == keyStruct->vkCode)
				{
					imguicontrole = !imguicontrole;
				}
				//SELFDESTRUCT
				if (minecraftaberto)
				{
					// RETURN ESCAPE INVENTORY
					if (VK_RETURN == keyStruct->vkCode)
					{
						openinventory = false;
					}
					if ('E' == keyStruct->vkCode
						)
					{
						openinventory = !openinventory;
					}
					if (VK_ESCAPE == keyStruct->vkCode
						)
					{
						if ((openinventory == true))
						{
							openinventory = false;
						}
					}
					//RETURN ESCAPE INVENTORY

					// CHAT DETECTION
					if (chatopen)
					{
						if (VK_RETURN == keyStruct->vkCode)
						{
							chatopen = false;
						}
						if (VK_ESCAPE == keyStruct->vkCode)
						{
							chatopen = false;
						}
					}
					else
					{
						if (0x54 == keyStruct->vkCode)
						{
							chatopen = true;
						}
						if (clickerkeybind == keyStruct->vkCode)
						{
							if (ligado)
							{
								ligado = false;
								beepstatus = 2;
							}
							else
							{
								ligado = true;
								beepstatus = 3;
							}
						}
						if (('1' == keyStruct->vkCode || '2' == keyStruct->vkCode || '3' == keyStruct->vkCode || '4' ==
							keyStruct->vkCode || '5' == keyStruct->vkCode || '6' == keyStruct->vkCode || '7' ==
							keyStruct->vkCode || '8' == keyStruct->vkCode || '9' == keyStruct->vkCode))
						{
							currentslot = keyStruct->vkCode;
						}

						if (autopotkeybind == keyStruct->vkCode)
						{
							std::async(std::launch::async, AutoPot, true);

						}
						if ('E' == keyStruct->vkCode)
						{
							resetinventorykey = true;
						}
					}
					// CHAT DETECTION

					//TOGGLESPRINT
					if (nullptr != keyStruct
						&& WM_KEYDOWN == wParam
						&& 'W' == keyStruct->vkCode)
					{
						issprinting = true;
					}
					if (nullptr != keyStruct
						&& WM_KEYUP == wParam
						&& 'W' == keyStruct->vkCode)
					{
						issprinting = false;
					}
					//TOGGLESPRINT
				}
			}

		}
		else
		{
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam) {

			}

			//SELFDESTRUCT
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam
				&& VK_F9 == keyStruct->vkCode)
			{
				imguicontrole = !imguicontrole;
			}
			//SELFDESTRUCT

			// RETURN ESCAPE INVENTORY
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam
				&& VK_RETURN == keyStruct->vkCode)
			{
				openinventory = false;
			}
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam
				&& 'E' == keyStruct->vkCode
				)
			{
				openinventory = !openinventory;
			}
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam
				&& VK_ESCAPE == keyStruct->vkCode
				)
			{
				if ((openinventory == true))
				{
					openinventory = false;
				}
			}
			//RETURN ESCAPE INVENTORY

			// CHAT DETECTION
			if (chatopen)
			{
				if (nullptr != keyStruct
					&& WM_KEYUP == wParam
					&& VK_RETURN == keyStruct->vkCode)
				{
					chatopen = false;
				}
				if (nullptr != keyStruct
					&& WM_KEYUP == wParam
					&& VK_ESCAPE == keyStruct->vkCode)
				{
					chatopen = false;
				}
			}
			else
			{
				if (nullptr != keyStruct
					&& WM_KEYUP == wParam
					&& 0x54 == keyStruct->vkCode)
				{
					chatopen = true;
				}
				if (nullptr != keyStruct
					&& WM_KEYUP == wParam
					&& clickerkeybind == keyStruct->vkCode)
				{
					if (ligado)
					{
						ligado = false;
						beepstatus = 2;
					}
					else
					{
						ligado = true;
						beepstatus = 3;
					}
				}
				if (nullptr != keyStruct
					&& WM_KEYDOWN == wParam
					&& ('1' == keyStruct->vkCode || '2' == keyStruct->vkCode || '3' == keyStruct->vkCode || '4' ==
						keyStruct->vkCode || '5' == keyStruct->vkCode || '6' == keyStruct->vkCode || '7' == keyStruct->
						vkCode || '8' == keyStruct->vkCode || '9' == keyStruct->vkCode))
				{
					currentslot = keyStruct->vkCode;
				}

				if (nullptr != keyStruct
					&& WM_KEYDOWN == wParam
					&& autopotkeybind == keyStruct->vkCode)
				{
					autopotkeybinddown = true;
				}
				if (nullptr != keyStruct
					&& WM_KEYUP == wParam
					&& 'E' == keyStruct->vkCode)
				{
					resetinventorykey = true;
				}
			}
			// CHAT DETECTION

			//TOGGLESPRINT
			if (nullptr != keyStruct
				&& WM_KEYDOWN == wParam
				&& 'W' == keyStruct->vkCode)
			{
				issprinting = true;
			}
			if (nullptr != keyStruct
				&& WM_KEYUP == wParam
				&& 'W' == keyStruct->vkCode)
			{
				issprinting = false;
			}
			//TOGGLESPRINT
		}

		// HIDE WINDOW
		if (nullptr != keyStruct
			&& WM_KEYUP == wParam
			&& VK_END == keyStruct->vkCode)
		{
			hidewindowtoggle = !hidewindowtoggle;
			controlehidewindow = 1;
		}
	}

	else
	{
		if (nullptr != keyStruct
			&& WM_KEYUP == wParam
			&& VK_RETURN == keyStruct->vkCode)
		{
			enterpressionado = true;
		}
	}

	return  nCode < 0 ? CallNextHookEx(hookdoTeclado, nCode, wParam, lParam) : 0;
}

BOOL RegDelnodeRecurse(HKEY hKeyRoot, LPTSTR lpSubKey)
{
	LPTSTR lpEnd;
	LONG lResult;
	DWORD dwSize;
	TCHAR szName[MAX_PATH];
	HKEY hKey;
	FILETIME ftWrite;

	// First, see if we can delete the key without having
	// to recurse.

	lResult = RegDeleteKey(hKeyRoot, lpSubKey);

	if (lResult == ERROR_SUCCESS)
		return TRUE;

	lResult = RegOpenKeyEx(hKeyRoot, lpSubKey, 0, KEY_READ, &hKey);

	if (lResult != ERROR_SUCCESS)
	{
		if (lResult == ERROR_FILE_NOT_FOUND)
		{
			printf("Key not found.\n");
			return TRUE;
		}
		printf("Error opening key.\n");
		return FALSE;
	}

	// Check for an ending slash and add one if it is missing.

	lpEnd = lpSubKey + lstrlen(lpSubKey);

	if (*(lpEnd - 1) != TEXT('\\'))
	{
		*lpEnd = TEXT('\\');
		lpEnd++;
		*lpEnd = TEXT('\0');
	}

	// Enumerate the keys

	dwSize = MAX_PATH;
	lResult = RegEnumKeyEx(hKey, 0, szName, &dwSize, nullptr,
		nullptr, nullptr, &ftWrite);

	if (lResult == ERROR_SUCCESS)
	{
		do
		{
			*lpEnd = TEXT('\0');
			StringCchCat(lpSubKey, MAX_PATH * 2, szName);

			if (!RegDelnodeRecurse(hKeyRoot, lpSubKey))
			{
				break;
			}

			dwSize = MAX_PATH;

			lResult = RegEnumKeyEx(hKey, 0, szName, &dwSize, nullptr,
				nullptr, nullptr, &ftWrite);
		} while (lResult == ERROR_SUCCESS);
	}

	lpEnd--;
	*lpEnd = TEXT('\0');

	RegCloseKey(hKey);

	// Try again to delete the key.

	lResult = RegDeleteKey(hKeyRoot, lpSubKey);

	if (lResult == ERROR_SUCCESS)
		return TRUE;

	return FALSE;
}

BOOL RegDelnode(HKEY hKeyRoot, LPCTSTR lpSubKey)
{
	TCHAR szDelKey[MAX_PATH * 2];

	StringCchCopy(szDelKey, MAX_PATH * 2, lpSubKey);
	return RegDelnodeRecurse(hKeyRoot, szDelKey);
}

DWORD WINAPI HULK() // hooks do mouse e teclado
{
	hookdoMouse = SetWindowsHookEx(WH_MOUSE_LL, &mouse_callback, nullptr, 0);
	hookdoTeclado = SetWindowsHookEx(WH_KEYBOARD_LL, &testeTeclado, nullptr, 0);

	MSG msg;
	while (GetMessage(&msg, nullptr, 0, 0))
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

	UnhookWindowsHookEx(hookdoMouse);
	UnhookWindowsHookEx(hookdoTeclado);
	return 0;
}

int skipclicks;


void leftdown()
{
	INPUT Input = { 0 };

	// left down
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = MOUSEEVENTF_LEFTDOWN;
	SendInput(1, &Input, sizeof(INPUT));
	skipclicks++;
	shouldiplaysound = true;
}

int leftup()
{
	// left up

	INPUT Input = { 0 };
	Input.type = INPUT_MOUSE;
	Input.mi.dwFlags = MOUSEEVENTF_LEFTUP;
	SendInput(1, &Input, sizeof(INPUT));
	shouldiplaysound = false;
	return 0;
}


DWORD WINAPI SprintThread() // thread dos clicks
{

	while (controle == 1)
	{


		Sleep(50);
	}
	return 0;
}


bool vidaclicker;
int cps_atual;
float cps_atual_ms;
DWORD WINAPI TimerThread() {
	int delaywtap;
	wtapdelaymin = 100;

	INPUT wtapinput;

	wtapinput.type = INPUT_KEYBOARD;
	wtapinput.ki.wScan = 0;
	wtapinput.ki.time = 0;
	wtapinput.ki.dwExtraInfo = 0;
	wtapinput.ki.wVk = 'W';
	INPUT sprintinput;

	sprintinput.type = INPUT_KEYBOARD;
	sprintinput.ki.wScan = 0;
	sprintinput.ki.time = 0;
	sprintinput.ki.dwExtraInfo = 0;
	sprintinput.ki.wVk = VK_LCONTROL;

	auto timeNow = std::chrono::steady_clock::now();
	auto timeStartSound = timeNow;
	auto timeStartWtap = timeNow;
	auto timeStartCPS = timeNow;
	auto timeStartJitter = timeNow;

	while (imguicontrole)
	{

		delaywtap = distribucaoint(wtapdelaymin - (wtapdelaymin * 0.25), wtapdelaymin + (wtapdelaymin * 1.25));
		timeNow = std::chrono::steady_clock::now();
		auto timepassedSound = std::chrono::duration_cast<std::chrono::milliseconds>(timeNow - timeStartSound).count();
		auto timepassedWtap = std::chrono::duration_cast<std::chrono::milliseconds>(timeNow - timeStartSound).count();
		auto timepassedCPS = std::chrono::duration_cast<std::chrono::milliseconds>(timeNow - timeStartCPS).count();
		auto timePassedJitter = std::chrono::duration_cast<std::chrono::milliseconds>(timeNow - timeStartJitter).count();

		if (togglesprint)
		{
			if (issprinting)
			{
				sprintinput.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
				SendInput(1, &sprintinput, sizeof(INPUT));

			}
			else {
				sprintinput.ki.dwFlags = KEYEVENTF_KEYUP; // there is no KEYEVENTF_KEYDOWN
				SendInput(1, &sprintinput, sizeof(INPUT));
			}

		}
		if ((int)timePassedJitter >= jitterdelay) {
			janelaatual = GetActiveWindowTitle();
			GetCursorPos(&mouse);
			if (ligado && jittertoggle && mouseDown && !(niggatitlenigga == janelaatual))
			{
				int jitterez;
				jitterez = distribucaoint(1, 8);

				if (windowonly)
				{
					if (minecraftaberto)
					{
						if (inventoryjitter)
						{
							if (!openinventory)
							{
								switch (jitterez)
								{
								case 1:
									MouseMove((mouse.x + jitterclirk), (mouse.y));
									break;
								case 2:
									MouseMove((mouse.x), (mouse.y + jitterclirk));
									break;
								case 3:
									MouseMove((mouse.x), (mouse.y - jitterclirk));
									break;
								case 4:
									MouseMove((mouse.x - jitterclirk), (mouse.y));
									break;
								case 5:
									MouseMove((mouse.x - jitterclirk), (mouse.y + jitterclirk));
									break;
								case 6:
									MouseMove((mouse.x + jitterclirk), (mouse.y - jitterclirk));
									break;
								case 7:
									MouseMove((mouse.x + jitterclirk), (mouse.y + jitterclirk));
									break;
								case 8:
									MouseMove((mouse.x - jitterclirk), (mouse.y - jitterclirk));
									break;
								}
							}
						}
						else
						{
							switch (jitterez)
							{
							case 1:
								MouseMove((mouse.x + jitterclirk), (mouse.y));
								break;
							case 2:
								MouseMove((mouse.x), (mouse.y + jitterclirk));
								break;
							case 3:
								MouseMove((mouse.x), (mouse.y - jitterclirk));
								break;
							case 4:
								MouseMove((mouse.x - jitterclirk), (mouse.y));
								break;
							case 5:
								MouseMove((mouse.x - jitterclirk), (mouse.y + jitterclirk));
								break;
							case 6:
								MouseMove((mouse.x + jitterclirk), (mouse.y - jitterclirk));
								break;
							case 7:
								MouseMove((mouse.x + jitterclirk), (mouse.y + jitterclirk));
								break;
							case 8:
								MouseMove((mouse.x - jitterclirk), (mouse.y - jitterclirk));
								break;
							}
						}
					}
				}
				else
				{
					if (inventoryjitter)
					{
						if (!openinventory)
						{
							switch (jitterez)
							{
							case 1:
								MouseMove((mouse.x + jitterclirk), (mouse.y));
								break;
							case 2:
								MouseMove((mouse.x), (mouse.y + jitterclirk));
								break;
							case 3:
								MouseMove((mouse.x), (mouse.y - jitterclirk));
								break;
							case 4:
								MouseMove((mouse.x - jitterclirk), (mouse.y));
								break;
							case 5:
								MouseMove((mouse.x - jitterclirk), (mouse.y + jitterclirk));
								break;
							case 6:
								MouseMove((mouse.x + jitterclirk), (mouse.y - jitterclirk));
								break;
							case 7:
								MouseMove((mouse.x + jitterclirk), (mouse.y + jitterclirk));
								break;
							case 8:
								MouseMove((mouse.x - jitterclirk), (mouse.y - jitterclirk));
								break;
							}
						}
					}
					else
					{
						switch (jitterez)
						{
						case 1:
							MouseMove((mouse.x + jitterclirk), (mouse.y));
							break;
						case 2:
							MouseMove((mouse.x), (mouse.y + jitterclirk));
							break;
						case 3:
							MouseMove((mouse.x), (mouse.y - jitterclirk));
							break;
						case 4:
							MouseMove((mouse.x - jitterclirk), (mouse.y));
							break;
						case 5:
							MouseMove((mouse.x - jitterclirk), (mouse.y + jitterclirk));
							break;
						case 6:
							MouseMove((mouse.x + jitterclirk), (mouse.y - jitterclirk));
							break;
						case 7:
							MouseMove((mouse.x + jitterclirk), (mouse.y + jitterclirk));
							break;
						case 8:
							MouseMove((mouse.x - jitterclirk), (mouse.y - jitterclirk));
							break;
						}
					}
				}
			}
			timeStartJitter = std::chrono::steady_clock::now();

		}
		if ((int)timepassedCPS >= refreshdelay) {

			if (slidernovo == 1)
			{
				try
				{
					if (randomize)
					{
						cps_min = nigga1 - 4;
						cps_max = nigga1 + 2;
					}
					else
					{
						cps_min = nigga1 - 1;
						cps_max = nigga1;
					}
				}
				catch (int e)
				{
					MessageBoxA(nullptr, LPCSTR(to_string(e).c_str()), "", MB_OK | MB_ICONERROR);
				}
			}
			else if (slidernovo == 2)
			{
				try
				{
					cps_min = nigga1 - 1;
					cps_max = nigga1;
				}
				catch (int e)
				{
					MessageBoxA(nullptr, LPCSTR(to_string(e).c_str()), "", MB_OK | MB_ICONERROR);
				}
			}
			else
			{
				try
				{
					if (nigga1 > nigga2) nigga1 = nigga2 - 1;
					cps_min = nigga1;
					cps_max = nigga2;
				}
				catch (int e)
				{
					MessageBoxA(nullptr, LPCSTR(to_string(e).c_str()), "", MB_OK | MB_ICONERROR);
				}
			}
			cps_min_ms = ((1 / (float)cps_min) * 1000);
			cps_max_ms = ((1 / (float)cps_max) * 1000);
			delayy = distribucaoint(cps_max_ms, cps_min_ms);
			timeStartCPS = std::chrono::steady_clock::now();
		}
		if (((float)timepassedSound >= delayy) && soundstoggle && shouldiplaysound) {
			switch (selectedsound)
			{
			case 0: {
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE1), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			}

			case 1:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE2), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 2:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE3), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 3:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE4), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 4:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE5), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 5:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE6), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 6:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE7), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			case 7:
				std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE9), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
				break;
			}
			timeStartSound = std::chrono::steady_clock::now();

		}
		if (((float)timepassedWtap >= delaywtap) && togglewtap && ligado && mouseDown && !(niggatitlenigga == janelaatual))
		{
			wtapinput.ki.dwFlags = 0; // there is no KEYEVENTF_KEYDOWN
			SendInput(1, &wtapinput, sizeof(INPUT));

			wtapinput.ki.dwFlags = KEYEVENTF_KEYUP; // there is no KEYEVENTF_KEYDOWN
			SendInput(1, &wtapinput, sizeof(INPUT));
		}

		if (newslots[currentslot - 49])
		{
			slotpermitido = true;
		}
		else
		{
			slotpermitido = false;
		}
		std::vector<int> v{ jitterdelay, refreshdelay, (int)delayy, delaywtap };
		Sleep(2);
	}
	return 0;
}

#include <cstdlib>
#include <cstring>




std::uniform_int_distribution<int> distanciaupdown(15, 30); //1 to 10, inclusive
std::uniform_int_distribution<int> blockhitrandomization(0, 100);

int updowncount;
int skipclickcondition;

std::chrono::milliseconds timepassed;
auto timeStart = std::chrono::steady_clock::now();
void clickSound() {
	timepassed = std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::steady_clock::now() - timeStart);

	if (((float)timepassed.count() >= delayy) && soundstoggle && shouldiplaysound) {
		switch (selectedsound)
		{
		case 0: {
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE1), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		}

		case 1:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE2), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 2:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE3), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 3:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE4), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 4:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE5), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 5:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE6), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 6:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE7), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		case 7:
			std::async(std::launch::async, PlaySound, MAKEINTRESOURCE(IDR_WAVE9), GetModuleHandle(nullptr), SND_RESOURCE | SND_ASYNC);
			break;
		}
		timeStart = std::chrono::steady_clock::now();
	}
}


DWORD WINAPI ClickThread() // thread dos clicks
{


	while (imguicontrole)
	{
		switch (beepstatus)
		{
		case 2:
			if (beeptoggle)
			{
				Beep(300, 200);
			}

			beepstatus = 0;
			break;
		case 3:
			if (beeptoggle)
			{
				Beep(600, 200);
			}
			beepstatus = 0;
			break;
		}


		if (skipclicks >= skipclickcondition)
		{
			skipclicks = 0;
		}
		else
		{
			if (filterslots)
			{
				if (slotpermitido)
				{
					if (inventoryonly)
					{
						if (windowonly)
						{
							if (mouseDown && ligado && !(niggatitlenigga == janelaatual) && minecraftaberto)
							{
								leftdown();
								Sleep(delayupdown);
								updowncount++;
								leftup();

								if (blockhit)
								{
									if (randomizeblockhit)
									{
										if (blockhitcurrentchance <= randomizationpercentageblockhit)
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}
									else
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}

								clickcounter++;
							}
						}
						else
						{
							if (mouseDown && ligado && !(niggatitlenigga == janelaatual))
							{
								leftdown();
								Sleep(delayupdown);
								updowncount++;
								leftup();

								if (blockhit)
								{
									if (randomizeblockhit)
									{
										if (blockhitcurrentchance <= randomizationpercentageblockhit)
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}
									else
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}
								clickcounter++;
							}
						}
					}
					else
					{
						if (!openinventory)
						{
							if (windowonly)
							{
								if (mouseDown && ligado && !(niggatitlenigga == janelaatual) && minecraftaberto)
								{
									leftdown();
									Sleep(delayupdown);
									updowncount++;
									leftup();

									if (blockhit)
									{
										if (randomizeblockhit)
										{
											if (blockhitcurrentchance <= randomizationpercentageblockhit)
											{
												rightdown();
												Sleep(delayupdown);
												updowncount++;
												rightup();
											}
										}
										else
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}

									clickcounter++;
								}
							}
							else
							{
								if (mouseDown && ligado && !(niggatitlenigga == janelaatual))
								{
									leftdown();
									Sleep(delayupdown);
									updowncount++;
									leftup();

									if (blockhit)
									{
										if (randomizeblockhit)
										{
											if (blockhitcurrentchance <= randomizationpercentageblockhit)
											{
												rightdown();
												Sleep(delayupdown);
												updowncount++;
												rightup();
											}
										}
										else
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}
									clickcounter++;
								}
							}
						}
					}
				}
			}
			else
			{
				if (inventoryonly)
				{
					if (windowonly)
					{
						if (mouseDown && ligado && !(niggatitlenigga == janelaatual) && minecraftaberto)
						{
							leftdown();
							Sleep(delayupdown);
							updowncount++;
							leftup();

							if (blockhit)
							{
								if (randomizeblockhit)
								{
									if (blockhitcurrentchance <= randomizationpercentageblockhit)
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}
								else
								{
									rightdown();
									Sleep(delayupdown);
									updowncount++;
									rightup();
								}
							}

							clickcounter++;
						}
					}
					else
					{
						if (mouseDown && ligado && !(niggatitlenigga == janelaatual))
						{
							leftdown();
							Sleep(delayupdown);
							updowncount++;
							leftup();

							if (blockhit)
							{
								if (randomizeblockhit)
								{
									if (blockhitcurrentchance <= randomizationpercentageblockhit)
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}
								else
								{
									rightdown();
									Sleep(delayupdown);
									updowncount++;
									rightup();
								}
							}
							clickcounter++;
						}
					}
				}
				else
				{
					if (!openinventory)
					{
						if (windowonly)
						{
							if (mouseDown && ligado && !(niggatitlenigga == janelaatual) && minecraftaberto)
							{
								leftdown();
								Sleep(delayupdown);
								updowncount++;
								leftup();

								if (blockhit)
								{
									if (randomizeblockhit)
									{
										if (blockhitcurrentchance <= randomizationpercentageblockhit)
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}
									else
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}

								clickcounter++;
							}
						}
						else
						{
							if (mouseDown && ligado && !(niggatitlenigga == janelaatual))
							{
								leftdown();
								Sleep(delayupdown);
								updowncount++;
								leftup();

								if (blockhit)
								{
									if (randomizeblockhit)
									{
										if (blockhitcurrentchance <= randomizationpercentageblockhit)
										{
											rightdown();
											Sleep(delayupdown);
											updowncount++;
											rightup();
										}
									}
									else
									{
										rightdown();
										Sleep(delayupdown);
										updowncount++;
										rightup();
									}
								}
								clickcounter++;
							}
						}
					}
				}
			}
		}


		if (ligado && RIGHTmouseDown && rightmouseligado && !(niggatitlenigga == janelaatual))
		{
			rightdown();
			Sleep(delayupdown);
			updowncount++;
			rightup();
		}

		if ((delayy - (delayupdown * updowncount)) > 0)
		{
			Sleep(delayy - (delayupdown * updowncount));
		}

		updowncount = 0;
	}

	return 0;
}

DWORD WINAPI ClickThread2() // thread dos clicks
{
	wtapdelaymin = 100;


	while (imguicontrole == 1)
	{
		skipclickcondition = distribucaoint(10, 20);

		if (janelaatual.find(janelaminecraftchar) != std::string::npos)
		{
			minecraftaberto = true;
		}
		else
		{
			minecraftaberto = false;
		}
		blockhitcurrentchance = distribucaoint(0, 100);
		if (slidernovo == 2)
			delayupdown = 0;
		else
			delayupdown = distribucaoint(15, 30);

		cpsatual = (1000 / delayy);

		if ((delayy - (delayupdown * updowncount)) <= 0)
		{
		}
		else
		{
			Sleep(delayy - (delayupdown * updowncount));
		}

		updowncount = 0;
	}

	return 0;
}



void loadconfig()
{
	ImGuiStyle& style = ImGui::GetStyle();

	char* appdata = getenv("APPDATA");

	confighash = "";
	confighash = "\\" + confighashtemp;
	std::string localconfig = appdata + confighash + ".ini";

	if (is_file_exist(localconfig.c_str()))
	{
		string message = "Loaded config at " + localconfig;


		char strligado[100] = "";
		char strnigga1[100] = "";
		char strnigga2[100] = "";
		char strrandomize[100] = "";
		char strclickerkeybind[100] = "";
		char strrefreshdelay[100] = "";
		char strfilterslots[100] = "";
		char strtextslot[100] = "";
		char strsoundstoggle[100] = "";
		char strselectedsound[100] = "";
		char strbeepsound[100] = "";
		char strslidernovo[100] = "";

		char strjittertoggle[100] = "";
		char strjitterclirk[100] = "";
		char strjitterdelay[100] = "";
		char strinventoryjitter[100] = "";
		char strwindowonly[100] = "";
		char strjanelaminecraftchar[100] = "";
		char strrightmouseligado[100] = "";
		char strinventoryonly[100] = "";
		char strtogglesprint[100] = "";
		char strtogglewtap[100] = "";
		char strwtapdelaymin[100] = "";
		char strblockhit[100] = "";
		char strrandomizeblockhit[100] = "";
		char strrandomizationpercentageblockhit[100] = "";
		char strautopottoggle[100] = "";
		char strautopotkeybind[100] = "";

		char strslotsword[100] = "";
		char strautopotdelay[100] = "";
		char strslotmin[100] = "";
		char strslotmax[100] = "";

		char strcleanstrings[100] = "";
		char strcleanfiles[100] = "";
		char strcolor11[100] = "";
		char strcolor12[100] = "";
		char strcolor13[100] = "";
		char strcolor14[100] = "";
		char strcolor21[100] = "";
		char strcolor22[100] = "";
		char strcolor23[100] = "";
		char strcolor24[100] = "";

		char strcolor31[100] = "";
		char strcolor32[100] = "";
		char strcolor33[100] = "";
		char strcolor34[100] = "";

		ligado = atoi(strligado);
		nigga1 = atoi(strnigga1);
		nigga2 = atoi(strnigga2);
		randomize = atoi(strrandomize);
		clickerkeybind = atoi(strclickerkeybind);
		refreshdelay = atoi(strrefreshdelay);
		filterslots = atoi(strfilterslots);
		textslot = atoi(strtextslot);
		soundstoggle = atoi(strsoundstoggle);
		selectedsound = atoi(strselectedsound);
		beeptoggle = atoi(strbeepsound);
		slidernovo = atoi(strslidernovo);

		jittertoggle = atoi(strjittertoggle);
		jitterclirk = atoi(strjitterclirk);
		jitterdelay = atoi(strjitterdelay);
		inventoryjitter = atoi(strinventoryjitter);
		windowonly = atoi(strwindowonly);
		//janelaminecraftchar=strjanelaminecraftchar;
		rightmouseligado = atoi(strrightmouseligado);
		inventoryonly = atoi(strinventoryonly);
		togglesprint = atoi(strtogglesprint);
		togglewtap = atoi(strtogglewtap);
		wtapdelaymin = atoi(strwtapdelaymin);
		blockhit = atoi(strblockhit);
		randomizeblockhit = atoi(strrandomizeblockhit);
		randomizationpercentageblockhit = atoi(strrandomizationpercentageblockhit);
		//strautopottoggle=atoi(strautopottoggle);
		autopotkeybind = atoi(strautopotkeybind);

		slotsword = atoi(strslotsword);
		autopotdelay = atoi(strautopotdelay);
		slotmin = atoi(strslotmin);
		slotmax = atoi(strslotmax);

		cleanstrings = atoi(strcleanstrings);
		cleanfiles = atoi(strcleanfiles);
		style.Colors[ImGuiCol_Text].w = atof(strcolor11);
		style.Colors[ImGuiCol_Text].x = atof(strcolor12);
		style.Colors[ImGuiCol_Text].y = atof(strcolor13);
		style.Colors[ImGuiCol_Text].z = atof(strcolor14);
		style.Colors[ImGuiCol_ChildBg].w = atof(strcolor21);
		style.Colors[ImGuiCol_ChildBg].x = atof(strcolor22);
		style.Colors[ImGuiCol_ChildBg].y = atof(strcolor23);
		style.Colors[ImGuiCol_ChildBg].z = atof(strcolor24);
		style.Colors[ImGuiCol_Button].w = atof(strcolor31);
		style.Colors[ImGuiCol_Button].x = atof(strcolor32);
		style.Colors[ImGuiCol_Button].y = atof(strcolor33);
		style.Colors[ImGuiCol_Button].z = atof(strcolor34);

		GetPrivateProfileString(TEXT("clicker"), TEXT("clicker_toggle"), TEXT("0"), strligado, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("cps_min"), TEXT("7"), strnigga1, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("cps_max"), TEXT("12"), strnigga2, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("randomize_toggle"), TEXT("1"), strrandomize, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("clicker_keybind"), TEXT("115"), strclickerkeybind, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("refresh_delay"), TEXT("500"), strrefreshdelay, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("slotfilter_toggle"), TEXT("0"), strfilterslots, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("slotfilter_selectedslot"), TEXT("1"), strtextslot, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("clicksounds_toggle"), TEXT("0"), strsoundstoggle, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("clicksounds_selectedsound"), TEXT("0"), strselectedsound, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("beep_toggle"), TEXT("0"), strbeepsound, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("clicker"), TEXT("newslider_toggle"), TEXT("1"), strslidernovo, 100,
			(LPCSTR)localconfig.c_str());

		GetPrivateProfileString(TEXT("jitter"), TEXT("jitter_toggle"), TEXT("0"), strjittertoggle, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("jitter"), TEXT("jitter_strenght"), TEXT("1"), strjitterclirk, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("jitter"), TEXT("jitter_delay"), TEXT("50"), strjitterdelay, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("jitter"), TEXT("jitter_inventory"), TEXT("0"), strinventoryjitter, 100,
			(LPCSTR)localconfig.c_str());

		GetPrivateProfileString(TEXT("misc"), TEXT("misc_windowonly"), TEXT("0"), strwindowonly, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_specifiedwindow"), TEXT("Minecraft"), strjanelaminecraftchar,
			100, (LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_rightclicker_toggle"), TEXT("0"), strrightmouseligado, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_inventory_toggle"), TEXT("0"), strinventoryonly, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_togglesprinttoggle"), TEXT("0"), strtogglesprint, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_wtap_toggle"), TEXT("0"), strtogglewtap, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_wtap_delay"), TEXT("100"), strwtapdelaymin, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_blockhit_toggle"), TEXT("0"), strblockhit, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_blockhit_randomize"), TEXT("0"), strrandomizeblockhit, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_blockhit_randomizechance"), TEXT("40"),
			strrandomizationpercentageblockhit, 100, (LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_toggle"), TEXT("0"), strautopottoggle, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_keybind"), TEXT("0"), strautopotkeybind, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_swordslot"), TEXT("1"), strslotsword, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_delay"), TEXT("100"), strautopotdelay, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_minslots"), TEXT("2"), strslotmin, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("misc"), TEXT("misc_autopot_maxslots"), TEXT("9"), strslotmax, 100,
			(LPCSTR)localconfig.c_str());

		GetPrivateProfileString(TEXT("other"), TEXT("other_cleanstrings"), TEXT("1"), strcleanstrings, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_cleanfiles"), TEXT("1"), strcleanfiles, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color11"), TEXT("1"), strcolor11, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color12"), TEXT("0.8"), strcolor12, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color13"), TEXT("0.8"), strcolor13, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color14"), TEXT("0.8"), strcolor14, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color21"), TEXT("1"), strcolor21, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color22"), TEXT("0.07"), strcolor22, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color23"), TEXT("0.07"), strcolor23, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color24"), TEXT("0.09"), strcolor24, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color31"), TEXT("1"), strcolor31, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color32"), TEXT("0.1"), strcolor32, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color33"), TEXT("0.09"), strcolor33, 100,
			(LPCSTR)localconfig.c_str());
		GetPrivateProfileString(TEXT("other"), TEXT("other_color34"), TEXT("0.12"), strcolor34, 100,
			(LPCSTR)localconfig.c_str());
		ligado = atoi(strligado);
		nigga1 = atoi(strnigga1);
		nigga2 = atoi(strnigga2);
		randomize = atoi(strrandomize);
		clickerkeybind = atoi(strclickerkeybind);
		refreshdelay = atoi(strrefreshdelay);
		filterslots = atoi(strfilterslots);
		textslot = atoi(strtextslot);
		soundstoggle = atoi(strsoundstoggle);
		selectedsound = atoi(strselectedsound);
		beeptoggle = atoi(strbeepsound);
		slidernovo = atoi(strslidernovo);

		jittertoggle = atoi(strjittertoggle);
		jitterclirk = atoi(strjitterclirk);
		jitterdelay = atoi(strjitterdelay);
		inventoryjitter = atoi(strinventoryjitter);
		windowonly = atoi(strwindowonly);
		//janelaminecraftchar=strjanelaminecraftchar;
		rightmouseligado = atoi(strrightmouseligado);
		inventoryonly = atoi(strinventoryonly);
		togglesprint = atoi(strtogglesprint);
		togglewtap = atoi(strtogglewtap);
		wtapdelaymin = atoi(strwtapdelaymin);
		blockhit = atoi(strblockhit);
		randomizeblockhit = atoi(strrandomizeblockhit);
		randomizationpercentageblockhit = atoi(strrandomizationpercentageblockhit);
		//strautopottoggle=atoi(strautopottoggle);
		autopotkeybind = atoi(strautopotkeybind);

		slotsword = atoi(strslotsword);
		autopotdelay = atoi(strautopotdelay);
		slotmin = atoi(strslotmin);
		slotmax = atoi(strslotmax);

		cleanstrings = atoi(strcleanstrings);
		cleanfiles = atoi(strcleanfiles);
		style.Colors[ImGuiCol_Text].w = atof(strcolor11);
		style.Colors[ImGuiCol_Text].x = atof(strcolor12);
		style.Colors[ImGuiCol_Text].y = atof(strcolor13);
		style.Colors[ImGuiCol_Text].z = atof(strcolor14);
		style.Colors[ImGuiCol_ChildBg].w = atof(strcolor21);
		style.Colors[ImGuiCol_ChildBg].x = atof(strcolor22);
		style.Colors[ImGuiCol_ChildBg].y = atof(strcolor23);
		style.Colors[ImGuiCol_ChildBg].z = atof(strcolor24);
		style.Colors[ImGuiCol_Button].w = atof(strcolor31);
		style.Colors[ImGuiCol_Button].x = atof(strcolor32);
		style.Colors[ImGuiCol_Button].y = atof(strcolor33);
		style.Colors[ImGuiCol_Button].z = atof(strcolor34);
	}
}

void saveconfig()
{
	ImGuiStyle& style = ImGui::GetStyle();
	char* appdata = getenv("APPDATA");

	username = "";
	confighash = "\\" + confighash;
	std::string localconfig = appdata + confighash + ".ini";


	string message = "Saved config at " + localconfig;
	MessageBox(nullptr, message.c_str(), "", MB_OK);


	WritePrivateProfileStringA(TEXT("clicker"), TEXT("clicker_toggle"),
		TEXT(to_string(ligado).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("cps_min"), TEXT(to_string(nigga1).c_str()),
		(LPCSTR)localconfig.c_str());

	WritePrivateProfileStringA(TEXT("clicker"), TEXT("cps_max"), TEXT(to_string(nigga2).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("randomize_toggle"),
		TEXT(to_string(randomize).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("clicker_keybind"),
		TEXT(to_string(clickerkeybind).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("refresh_delay"),
		TEXT(to_string(refreshdelay).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("slotfilter_toggle"),
		TEXT(to_string(filterslots).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("slotfilter_selectedslot"),
		TEXT(to_string(textslot).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("clicksounds_toggle"),
		TEXT(to_string(soundstoggle).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("clicksounds_selectedsound"),
		TEXT(to_string(selectedsound).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("beep_toggle"),
		TEXT(to_string(beeptoggle).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("clicker"), TEXT("newslider_toggle"),
		TEXT(to_string(slidernovo).c_str()), (LPCSTR)localconfig.c_str());


	WritePrivateProfileStringA(TEXT("jitter"), TEXT("jitter_toggle"),
		TEXT(to_string(jittertoggle).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("jitter"), TEXT("jitter_strength"),
		TEXT(to_string(jitterclirk).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("jitter"), TEXT("jitter_delay"),
		TEXT(to_string(jitterdelay).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("jitter"), TEXT("jitter_inventory"),
		TEXT(to_string(inventoryjitter).c_str()),
		(LPCSTR)localconfig.c_str());


	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_windowonly"),
		TEXT(to_string(windowonly).c_str()), (LPCSTR)localconfig.c_str());

	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_specifiedwindow"),
		TEXT(string(janelaminecraftchar).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_rightclicker_toggle"),
		TEXT(to_string(rightmouseligado).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_inventorytoggle"),
		TEXT(to_string(inventoryonly).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_togglesprinttoggle"),
		TEXT(to_string(togglesprint).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_wtap_toggle"),
		TEXT(to_string(togglewtap).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_wtap_delay"),
		TEXT(to_string(wtapdelaymin).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_blockhit_toggle"),
		TEXT(to_string(blockhit).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_blockhit_randomize"),
		TEXT(to_string(randomizeblockhit).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_blockhit_randomizechance"),
		TEXT(to_string(randomizationpercentageblockhit).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_toggle"),
		TEXT(to_string(autopottoggle).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_keybind"),
		TEXT(to_string(autopotkeybind).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_swordslot"),
		TEXT(to_string(slotsword).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_delay"),
		TEXT(to_string(autopotdelay).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_minslots"),
		TEXT(to_string(slotmin).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("misc"), TEXT("misc_autopot_maxslots"),
		TEXT(to_string(slotmax).c_str()), (LPCSTR)localconfig.c_str());

	WritePrivateProfileStringA(TEXT("other"), TEXT("other_cleanstrings"),
		TEXT(to_string(cleanstrings).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_cleanfiles"),
		TEXT(to_string(cleanfiles).c_str()), (LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color11"),
		TEXT(to_string(style.Colors[ImGuiCol_Text].w).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color12"),
		TEXT(to_string(style.Colors[ImGuiCol_Text].x).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color13"),
		TEXT(to_string(style.Colors[ImGuiCol_Text].y).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color14"),
		TEXT(to_string(style.Colors[ImGuiCol_Text].z).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color21"),
		TEXT(to_string(style.Colors[ImGuiCol_ChildBg].w).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color22"),
		TEXT(to_string(style.Colors[ImGuiCol_ChildBg].x).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color23"),
		TEXT(to_string(style.Colors[ImGuiCol_ChildBg].y).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color24"),
		TEXT(to_string(style.Colors[ImGuiCol_ChildBg].z).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color31"),
		TEXT(to_string(style.Colors[ImGuiCol_Button].w).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color32"),
		TEXT(to_string(style.Colors[ImGuiCol_Button].x).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color33"),
		TEXT(to_string(style.Colors[ImGuiCol_Button].y).c_str()),
		(LPCSTR)localconfig.c_str());
	WritePrivateProfileStringA(TEXT("other"), TEXT("other_color34"),
		TEXT(to_string(style.Colors[ImGuiCol_Button].z).c_str()),
		(LPCSTR)localconfig.c_str());
}





static const char alphanum[] =
"0123456789"
"!@#$%^&*"
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"abcdefghijklmnopqrstuvwxyz";
int stringLength = sizeof(alphanum) - 1;

std::string random_string(size_t length)
{
	auto randchar = []() -> char
	{
		const char charset[] =
			"0123456789"
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			"abcdefghijklmnopqrstuvwxyz";
		const size_t max_index = (sizeof(charset) - 1);
		return charset[rand() % max_index];
	};
	std::string str(length, 0);
	std::generate_n(str.begin(), length, randchar);
	return str;
}

#include <windows.h>
#include <stdio.h>
#include <fcntl.h>
#include <io.h>
#include <iostream>
#include <fstream>
static const WORD MAX_CONSOLE_LINES = 500;

#include <string>
#include <iostream>
#include "dirent.h"
#include <Windows.h>
#include <sstream>
#include <vector>
#include <algorithm>
#include <iostream>





//main
std::string truncate(std::string str, size_t width, bool show_ellipsis = true)
{
	if (str.length() > width)
	{
		if (show_ellipsis)
			return str.substr(0, width) + "...";
		return str.substr(0, width);
	}
	return str;
}

int main(char* argv[])
{
	//Drive Serial
	ImGuiStyle& style = ImGui::GetStyle();

	char* appdata = getenv("APPDATA");

	username = "";
	confighash = "\\" + confighash;
	std::string localconfig = appdata + confighash + ".ini";
	for (static bool first = true; first; first = false)
	{
		int iii = 0;
		for (iii = 0; iii <= 8; iii++)
		{
			newslots[iii] = true;
		}
	}

	string message = "Loaded config at " + localconfig;


	loadconfig();
	//


	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&HULK, nullptr, 0, nullptr);
	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&ClickThread, nullptr, 0, nullptr);
	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&ClickThread2, nullptr, 0, nullptr);
	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&TimerThread, nullptr, 0, nullptr);

	CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&SprintThread, nullptr, 0, nullptr);


	std::string hwidfinal;
	std::string queryhwid;

	//nigga2 = atoi(nigga.c_str());

	//0x4539f0 (80): SELECT SerialNumber FROM Win32_BaseBoard
	concatenateHwid(L"SELECT ProcessorId FROM Win32_Processor", L"ProcessorId");
	//hd serial
	concatenateHwid(L"SELECT SerialNumber FROM Win32_BaseBoard", L"SerialNumber");

	// Get id of CPU


	std::string::iterator end_pos = std::remove(hwidserials.begin(), hwidserials.end(), ' ');

	hwidserials.erase(end_pos, hwidserials.end());

	hashhwid = picosha2::hash256_hex_string(hwidserials);

	char* charhashhwid = new char[hashhwid.length() + 1];
	strcpy(charhashhwid, hashhwid.c_str());
	confighash = truncate(hashhwid, 6, false);
	confighashtemp = confighash;
	controle = 1;

	// Setup window
	glfwSetErrorCallback(glfw_error_callback);
	if (!glfwInit())
		return 1;
	GLFWwindow* window = glfwCreateWindow(1280, 720, "Dear ImGui GLFW+OpenGL2 example", NULL, NULL);
	if (window == NULL)
		return 1;
	glfwMakeContextCurrent(window);
	glfwSwapInterval(1); // Enable vsync

	// Setup Dear ImGui context
	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO& io = ImGui::GetIO(); (void)io;

	// Setup Dear ImGui style
	ImGui::StyleColorsDark();
	//ImGui::StyleColorsClassic();

	// Setup Platform/Renderer bindings
	ImGui_ImplGlfw_InitForOpenGL(window, true);
	ImGui_ImplOpenGL2_Init();



	// Our state
	bool show_demo_window = true;
	bool show_another_window = false;
	ImVec4 clear_color = ImVec4(0.45f, 0.55f, 0.60f, 1.00f);
	bool controlemysql1 = true;
	bool conectado;
	bool sanidade = false;
	char* charhashpassword;
	int colorpage = 0;
	string hashpassword;
	int tentativaerro = 0;
	char* temppass;
	// Main loop
	while (!glfwWindowShouldClose(window) && imguicontrole)
	{
		if (logado)
		{
			int contagemtext = 0;
			contagemtext = 0;
			for (static bool first = true; first; first = false)
			{
				char* appdata = getenv("APPDATA");

				confighash = "";
				confighash = "\\" + confighashtemp;
				std::string localconfig = appdata + confighash + ".ini";

				if (is_file_exist(localconfig.c_str()))
				{
					string message = "Loaded config at " + localconfig;


					loadconfig();
				}
			}


			controle = 1;

			const char* tabs[] = {
				"Main",
				"Auto",
				"Misc",
				"Other"
			};
			ImGuiStyle& style = ImGui::GetStyle();
			ImGui::SameLine();
			if (ImGui::Button("MonkeyClicker 2.6"))
			{
				ShellExecute(nullptr, nullptr, (LPCSTR)"https://www.monkeyclicker.xyz/", nullptr,
					nullptr, SW_SHOWNORMAL);
			}

			for (int i = 0; i < IM_ARRAYSIZE(tabs); i++)
			{
				//ImGui::GetStyle().Colors[ImGuiCol_Button] = ImColor(54, 54, 54, 255);

				if (ImGui::Button(tabs[i], ImVec2(ImGui::GetWindowSize().x / IM_ARRAYSIZE(tabs) - 15, 0)))
					page = i;

				//ImGui::GetStyle().Colors[ImGuiCol_Button] = ImColor(54, 54, 54, 255);

				if (i < IM_ARRAYSIZE(tabs) - 1)
					ImGui::SameLine();
			}

			ImGui::Separator();
			ImGui::Spacing();
			switch (page)
			{
			case 0:

				ImGui::Checkbox("AutoClicker###ligado", &ligado);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("AutoClicker Main Toggle");

				ImGui::SameLine(0.0F, -200.0F);
				//ImGui::Hotkey("###clickerkeybind", &clickerkeybind);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Autoclicker Hotkey");

				ImGui::Separator();

				ImGui::Spacing();
				if (ligado)
				{
					if (slidernovo == 1)
					{
						ImGui::SliderInt("CPS", &nigga1, 6, 25);


						ImGui::SameLine();
						ImGui::Checkbox("Randomize", &randomize);
						if (ImGui::IsItemHovered())
							ImGui::SetTooltip("Randomize the CPS the clicker will work at");
					}
					else if (slidernovo == 2)
					{
						ImGui::SliderInt("CPS", &nigga1, 6, 100);
					}
					else
					{
						ImGui::SliderInt("CPS Min", &nigga1, 3, 25);
						ImGui::SliderInt("CPS Max", &nigga2, 3, 25);
					}
					//ImGui::Spacing();
					//ImGui::InputInt("CPS Refresh Delay###refreshdelay", &refreshdelay, 100);
				}
				ImGui::Combo("CPS Mode", &slidernovo, tipos_cps, IM_ARRAYSIZE(tipos_cps));
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip(
						"Choose your Clicker Mode: \n  Old Mode:\n  2 sliders for Minimum \n  and Max CPS\n\n  New Mode:  1 randomized \n  Slider \n\n  Blatant:\n  Extremely Fast \n  Clicking with no \n  randomization");

				if (ligado)

				{
					ImGui::Spacing();
					ImGui::Separator();
				}
				ImGui::Spacing();
				ImGui::Checkbox("Only Weapon###filterslots", &filterslots);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Makes the autoclicker \nonly work on certain slots");
				int indexslots;
				int ind;

				int conditiontext;
				for (ind = 0; ind <= 8; ind++)
				{
					if (newslots[ind])
					{
						contagemtext++;
					}
					else
					{
						contagemtext--;
					}
					if (contagemtext == 9) conditiontext = 2;
					else if (contagemtext == -9) conditiontext = 3;
					else conditiontext = 1;
				}
				if (filterslots)
				{
					if (!(conditiontext == 3))
					{
						ImGui::Text("Allowed Slots");
					}


					for (indexslots = 0; indexslots <= 8; indexslots++)
					{
						if (newslots[indexslots] == true)
						{
							std::string s = std::to_string(indexslots + 1);

							if (ImGui::Button(s.c_str()))
							{
								newslots[indexslots] = !(newslots[indexslots]);
							}

							if (indexslots != 8)
							{
								ImGui::SameLine();
							}
						}
					}


					ImGui::Text("");
					if (!(conditiontext == 2)) ImGui::Text("Blocked Slots");
					for (indexslots = 0; indexslots <= 8; indexslots++)
					{
						if (newslots[indexslots] == false)
						{
							std::string s = std::to_string(indexslots + 1);

							if (ImGui::Button(s.c_str()))
							{
								newslots[indexslots] = !(newslots[indexslots]);
							}

							if (indexslots != 8)
							{
								ImGui::SameLine();
							}
						}
					}
				}

				ImGui::Text("");
				/*ImGui::SameLine();
				ImGui::InputInt("Slot###textslot", &textslot);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Slot which the autoclicker \nwill work if Only Weapon is activated");*/

				ImGui::Spacing();
				ImGui::Checkbox("Click Sounds###soundstoggle", &soundstoggle);
				ImGui::SameLine();
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Clicker will play mouse-sounds \ntogether with the clicking");

				ImGui::Combo("Sound###selectedsound", &selectedsound, soundstext, IM_ARRAYSIZE(soundstext));
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Sound the Click Sounds feature will play");


				break;

			case 1:
				ImGui::Checkbox("Right-clicker###rightmouseligado", &rightmouseligado);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will RightClick when user \n holds down right-mouse");
				ImGui::SameLine();
				ImGui::Checkbox("WTap###togglewtap", &togglewtap);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will cancel movement while autoclicking");

				ImGui::SliderInt("Delay###wtapdelaymin", &wtapdelaymin, 10, 1000);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Delay of WTap Cycle");

				ImGui::Spacing();
				ImGui::Separator();
				ImGui::Spacing();
				ImGui::Text("BlockHitting");
				ImGui::Checkbox("Enable###blockhit", &blockhit);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will RightClick together \nwith Autoclicker");
				if (blockhit)
				{
					ImGui::Checkbox("Randomize###randomizeblockhit", &randomizeblockhit);
					if (ImGui::IsItemHovered())
						ImGui::SetTooltip("Randomize blockhitting");
				}

				ImGui::Spacing();
				ImGui::Separator();
				ImGui::Spacing();
				if (blockhit && randomizeblockhit)
				{
					ImGui::SliderInt("Chance###randomizationpercentageblockhit", &randomizationpercentageblockhit,
						5, 100);
					if (ImGui::IsItemHovered())
						ImGui::SetTooltip("Chance of blockhit randomization");
				}


				ImGui::Checkbox("AutoPot###autopottoggle", &autopottoggle);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will Throw Pots / Eat Soup \n when user presses key");
				ImGui::SameLine();
				//ImGui::Hotkey("###autopotkeybind", &autopotkeybind);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Key which will \n activate AutoPot");

				ImGui::InputInt("Sword Slot ###slotsword", &slotsword);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Slot which your sword is in");
				ImGui::InputFloat("Delay###autopotdelay", &autopotdelay);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Delay which AutoPot will work on");
				ImGui::InputInt("Start ###slotmin", &slotmin);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Start of array of items \n you want to throw/eat");
				ImGui::InputInt("End ###slotmax", &slotmax);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("End of array of itens \n you want to throw/eat");
				if (ImGui::Button("Apply"))
				{
					autopotapply = true;
				}
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Update Values");
				ImGui::Spacing();


				break;

			case 2:

				ImGui::Checkbox("Window Only###windowonly", &windowonly);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Make clicker and keybinds\n only work on the \n specified window");

				ImGui::SameLine();
				ImGui::InputText("Name###janelaminecraftchar", janelaminecraftchar, 64);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Name of the window");


				ImGui::Checkbox("Allow Inventory###inventoryonly", &inventoryonly);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Allow autoclicking on inventory");
				ImGui::SameLine();
				ImGui::Checkbox("ToggleSprint###togglesprint", &togglesprint);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will sprint automatically for the user");


				ImGui::Spacing();

				ImGui::Separator();
				ImGui::Spacing();
				ImGui::Checkbox("Jitter ###jittertoggle", &jittertoggle);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Toggles Jitter");
				if (jittertoggle)
				{
					ImGui::SliderInt("Jitter Strength###jitterclirk", &jitterclirk, 1, 10);
					if (ImGui::IsItemHovered())
						ImGui::SetTooltip("The amount of pixels jitter \n will move each cycle");
					ImGui::InputInt("Jitter Delay###jitterdelay", &jitterdelay, 25, 100);
					if (ImGui::IsItemHovered())
						ImGui::SetTooltip("Duration of jitter cycles");
					ImGui::Checkbox("Disable Jitter on Inventory###inventoryjitter", &inventoryjitter);
					if (ImGui::IsItemHovered())
						ImGui::SetTooltip("If enabled, jitter will not\n not jitter on inventory");
				}
				ImGui::Spacing();
				ImGui::Separator();
				ImGui::Spacing();
				ImGui::Checkbox("Beep on Toggle###beeptoggle", &beeptoggle);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Autoclicker will beep when \nthe toggle hotkey is pressed");
				//ImGui::SameLine();
				//ImGui::Checkbox("New Slider###slidernovo", &slidernovo);

				if (ImGui::IsItemHovered())
					ImGui::SetTooltip(
						"If this is enabled the autoclicker \nwill choose your CPS range \nautomatically (still need to set CPS)");


				break;

			case 3:

				if (ImGui::Button("Self-Destruct (F9)"))
				{
					imguicontrole = false;
				}
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will Self-Destruct Autoclicker");

				ImGui::Checkbox("Clean Strings", &cleanstrings);

				ImGui::SameLine(0.0F, -200.0F);
				ImGui::Checkbox("Delete Traces", &cleanfiles);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will delete \nRegistry traces\nPrefetch Traces\n also will delete itself");
				ImGui::Checkbox("Delete Configs", &destructconfig);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will delete your configs");


				ImGui::SameLine();

				ImGui::Checkbox("Self-Delete", &selfdeletetoggle);
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Will make the clicker delete \nitself after it's self-destructed");


				ImGui::Spacing();

				if (ImGui::Button("Hide (END)"))

				{
					hidewindowtoggle = !hidewindowtoggle;
					controlehidewindow = 1;
				}
				ImGui::SameLine();
				if (ImGui::Button("Save Config"))
				{
					saveconfig();
				}
				ImGui::SameLine();
				if (ImGui::Button("Load Config"))
				{
					char* appdata = getenv("APPDATA");

					confighash = "";
					confighash = "\\" + confighashtemp;
					std::string localconfig = appdata + confighash + ".ini";

					if (is_file_exist(localconfig.c_str()))
					{
						string message = "Loaded config at " + localconfig;
						loadconfig();


						MessageBox(nullptr, message.c_str(), "", MB_OK);
					}
					else
					{
						MessageBox(nullptr, "Config not Found!", "", MB_OK);
					}
				}
				ImGui::SameLine();
				if (ImGui::Button("Edit Config"))
				{
					char* appdata = getenv("APPDATA");

					confighash = "";
					confighash = "\\" + confighashtemp;
					std::string localconfig = appdata + confighash + ".ini";
					ShellExecute(nullptr, "open", localconfig.c_str(), nullptr, nullptr, SW_SHOWDEFAULT);
				}
				if (ImGui::IsItemHovered())
					ImGui::SetTooltip("Hide Autoclicker Window\nDon't forget to self-destruct\nShortcut: END");

				if (colorpage == 0)
				{
					ImGui::Text("Edit Text Color");
					ImGui::ColorEdit3("", &style.Colors[ImGuiCol_Text].x);


					ImGui::Spacing();
					if (ImGui::Button("Text Color"))
					{
						colorpage = 0;
					}
					if (ImGui::Button("Background Color"))
					{
						colorpage = 1;
					}
					if (ImGui::Button("Button Color"))
					{
						colorpage = 2;
					}
				}
				else
				{
					if (colorpage == 1)
					{
						ImGui::Text("Edit Background Color");
						ImGui::ColorEdit3("", &style.Colors[ImGuiCol_ChildBg].x);


						ImGui::Spacing();
						if (ImGui::Button("Text Color"))
						{
							colorpage = 0;
						}
						if (ImGui::Button("Background Color"))
						{
							colorpage = 1;
						}
						if (ImGui::Button("Button Color"))
						{
							colorpage = 2;
						}
					}
					else
					{
						if (colorpage == 2)
						{
							ImGui::Text("Edit Button Color");
							ImGui::ColorEdit3("", &style.Colors[ImGuiCol_Button].x);


							ImGui::Spacing();
							if (ImGui::Button("Text Color"))
							{
								colorpage = 0;
							}
							if (ImGui::Button("Background Color"))
							{
								colorpage = 1;
							}
							if (ImGui::Button("Button Color"))
							{
								colorpage = 2;
							}
						}
					}
				}
				ImGui::Spacing();


				//ImGui::ColorEdit4("Text Color", &style.Colors[ImGuiCol_Text].x, true);
				//ImGui::ColorEdit4("Background Color", &style.Colors[ImGuiCol_ChildBg].x, true);
				//ImGui::ColorEdit4("Button Color", &style.Colors[ImGuiCol_Button].x, true);
			}
			ImGui::Spacing();
			ImGui::Spacing();
			ImGui::Spacing();
			ImGui::Spacing();
			ImGui::Spacing();
			if (ImGui::Button("Toggle Debug Info"))
			{
				debuginfo = !debuginfo;
			}
			if (ImGui::IsItemHovered())
				ImGui::SetTooltip("Will toggle debug info \n for the autoclicker");
			ImGui::Spacing();
			if (debuginfo)
			{
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Current CPS: %d", cpsatual);
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "DownUp Count: %d", updowncount);
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Current Delay : %f", delayy);

				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Max Delay: %f", cps_min_ms);

				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Min Delay: %f", cps_max_ms);
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Beep Status: %d", beepstatus);
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Is the Chat open: %s",
					chatopen ? "yes!" : "no");

				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Mouse Down: %s", mouseDown ? "yes" : "no");

				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Current Slot: %d", currentslot - 48);
				if (windowonly)
				{
					ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Is Minecraft Window Active? %s",
						minecraftaberto ? "yes!" : "no");
				}
				if (inventoryonly)
				{
					ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Is the inventory Open? %s",
						openinventory ? "yes!" : "no");
				}
				if (togglesprint)
				{
					ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Is the player walking? %s",
						issprinting ? "yes!" : "no");
				}
				if (jittertoggle)
				{
					ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Mouse Position X: %d", mouse.x);
					ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Mouse Position Y: %d", mouse.y);
				}
				ImGui::TextColored(ImVec4(0.5f, 0.5f, 0.5f, 1.0f), "Total Clicks: %d", clickcounter);
			}
		}
		else
		{
			for (static bool first = true; first; first = false)
			{
				loadconfig();
			}


			for (static bool first = true; first; first = false)
			{
				char* appdata = getenv("APPDATA");

				confighash = "";
				confighash = "\\" + confighashtemp;
				std::string localconfig = appdata + confighash + ".ini";


				GetPrivateProfileString(TEXT("login"), TEXT("username"), TEXT(""), charusername, 64,
					(LPCSTR)localconfig.c_str());
				GetPrivateProfileString(TEXT("login"), TEXT("password"), TEXT(""), charpassword, 64,
					(LPCSTR)localconfig.c_str());
			}


			if (imguicontrole)
			{

				conectado = true;



				ImGui::Text("Login/Register");


				ImGui::InputText("Username", charusername, 64);
				ImGui::InputText("Password", charpassword, 64, ImGuiInputTextFlags_Password);

				if ((ImGui::Button("Login")) && conectado)
				{
					enterpressionado = false;
					password = charpassword + stringsal;

					hashpassword = picosha2::hash256_hex_string(password);
					username = charusername;

					charhashpassword = &hashpassword[0u];





					//login
<<<<<<< HEAD
					char* url = "http://192.99.120.56/api/token ";
=======
					char* url = "http://192.99.120.56/api/token";
>>>>>>> 6a52e18f22849c48cb59fe08d85540d3a75d444e
					std::string responseData;

					CURL* curl;
					CURLcode res;
					curl = curl_easy_init();
					if (curl) {
						curl_easy_setopt(curl, CURLOPT_URL, url);
						curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteCallback);
						curl_easy_setopt(curl, CURLOPT_WRITEDATA, &responseData);
						curl_easy_setopt(curl, CURLOPT_POSTFIELDS, "username=aa&password=aa&hwid=aa");
						curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1L);

						res = curl_easy_perform(curl);
						if (res != CURLE_OK) {
							MessageBoxA(nullptr, LPCSTR(std::to_string(res).c_str()), "", MB_OK | MB_ICONERROR);
							curl_easy_cleanup(curl);

						}
						long response_code;
						curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, &response_code);


						MessageBoxA(nullptr, LPCSTR(responseData.c_str()), "", MB_OK | MB_ICONERROR);

						curl_easy_cleanup(curl);

						/* always cleanup */
						logado = true;
						loggedinglfw = true;
					}

					if (logado)
					{

						char* appdata = getenv("APPDATA");

						username = "";
						confighash = "\\" + confighash;
						std::string localconfig = appdata + confighash + ".ini";


						WritePrivateProfileStringA(TEXT("login"), TEXT("username"), TEXT(charusername),
							(LPCSTR)localconfig.c_str());
						WritePrivateProfileStringA(TEXT("login"), TEXT("password"), TEXT(charpassword),
							(LPCSTR)localconfig.c_str());
					}
					sanidade = true;
				}

				if (ImGui::Button("Exit"))
				{
					imguicontrole = false;
				}
				if (tentativaerro = true && sanidade)
				{
					ImGui::Text("Invalid // Disabled Account");
				}

			}
		}
	}
	RegDelnode(HKEY_CURRENT_USER, TEXT("Software\\Microsoft\\DirectInput"));
	RegDelnode(HKEY_LOCAL_MACHINE, TEXT("SYSTEM\\ControlSet001\\Services\\bam\\UserSettings"));

	if (destructconfig)
	{
		char* appdata = getenv("APPDATA");
		std::string localconfig = appdata + confighash + ".ini";
		DeleteFileA(localconfig.c_str());
		remove(localconfig.c_str());
	}
	// Cleanup
	ImGui_ImplOpenGL2_Shutdown();
	ImGui_ImplGlfw_Shutdown();
	ImGui::DestroyContext();

	glfwDestroyWindow(window);
	glfwTerminate();

	return 0;
}
