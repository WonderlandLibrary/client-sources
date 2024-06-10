#include "imgui.h"
#include "imgui_impl_win32.h"
#include "imgui_impl_dx11.h"
#include <d3d11.h>
#define DIRECTINPUT_VERSION 0x0800
#include <stdio.h>
#include <algorithm>
#include <TlHelp32.h>
#include <iostream>
#include <strsafe.h>
#include <codecvt>
#include <stdexcept>
#include <string>
#include <cstdlib>
#include <sstream>
#include <vector>
#include <random>
#include <thread>
#include <string>
#include <Windows.h>
#include <sddl.h>
#include <tchar.h>
#include <unordered_map>
#include <mmsystem.h>
#include <shellapi.h>
#define SELF_REMOVE_STRING  TEXT("cmd.exe /C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del /f /q \"%s\"")
#define MEMBLOCK 4096

namespace gui
{
	extern int tab;
	extern bool rgb;
	extern ImVec4 clear_col;

	extern bool r;
	extern bool g;
	extern bool b;

	extern int red;
	extern int green;
	extern int blue;
}

namespace client
{
	void destruct();
	extern bool cleanstrings;
	extern bool selfdelete;

	extern std::string version;
	DWORD get_pid(std::string processname);
	BOOL get_priv(HANDLE processhandle, std::string perm);
}

namespace modules
{
	namespace clicker
	{
		extern bool enabled;
		extern float cps;
		extern float chance;
		extern bool sounds;
		extern char soundfile[128];

		extern int bind;
		extern bool bind_edit;
		extern std::string bind_text;
	}

	namespace reach
	{
		extern bool enabled;
		extern float blocks;

		extern int bind;
		extern bool bind_edit;
		extern std::string bind_text;
	}

	namespace velocity
	{
		extern bool enabled;
		extern float amount;

		extern int bind;
		extern bool bind_edit;
		extern std::string bind_text;
	}
}

namespace thread
{
	void clicker();
	void reach();
	void velo();
	void color();
	void binds();
}