

#include "ImGui/imgui.h"
#include "ImGui/imgui_internal.h"
#include "ImGui/imgui_impl_dx11.h"
#include "Font1.h"
#include <windows.h>
#include <AclAPI.h>
#include <windows.h>
#include <stdio.h>
#include <algorithm>
#include <TlHelp32.h>
#include <iostream>
#include <strsafe.h>
#include <codecvt>

#include <iostream>
#include <stdexcept>
#include <stdio.h>
#include <string>

#include <cstdlib>
#include <d3d11.h>
#include <sstream>
#include <vector>
#include <random>
#include <thread>
#include <algorithm>
#include <string>
#include <Windows.h>
#include <stdio.h>
#include <winternl.h>
#include <Psapi.h>
#include <TlHelp32.h>
#include <DbgHelp.h>
#include <sddl.h>
#pragma comment (lib, "ntdll.lib")
#pragma comment(lib, "advapi32.lib")
#define MEMBLOCK 4096
#define nextbytes 30
#define BIG_MEM_BLOCK 10000000
#define SELF_REMOVE_STRING  TEXT("cmd.exe /C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del /f /q \"%s\"")
#define ImGui_FLAGS ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings
#define ImGui_FLAGS2 ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoMove | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings
#define OPENFLAGS THREAD_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE
#define SNAPFLAGS TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32

extern HWND hwnd;
extern HANDLE thread_handle_binds;
extern HANDLE thread_handle_clicker;
extern HANDLE thread_handle_reach;
extern HANDLE thread_handle_speed;
extern HANDLE thread_handle_jitter;
extern HANDLE thread_handle_inventory;
extern HANDLE thread_handle_velocity;

void thread_binds();
void thread_velocity();
void thread_clicker();
void thread_reach();
void thread_speed();
void thread_jitter();
void thread_inventory();

extern void cucklord_authenticate();
extern bool cucklord_logged;
extern int cucklord_version;
extern bool cucklord_destruct_clean_strings;

extern int cucklord_tab_main;
extern std::vector<const char*>cucklord_tab_main_vector;
extern int cucklord_tab_misc;

extern bool cucklord_serverside_mode;
extern float cucklord_clicker_value;
extern bool cucklord_clicker_enabled;
extern char cucklord_clicker_window[MAX_PATH];
extern float cucklord_clicker_jitter_value;
extern bool cucklord_clicker_inventory;
extern bool cucklord_clicker_minecraftonly;
extern bool cucklord_clicker_bind_pressed;
extern std::string cucklord_clicker_bind_text;
extern bool cucklord_inventory_status;


extern float cucklord_reach_value;
extern float cucklord_reach_chance_value;
extern bool cucklord_reach_enabled;
extern bool cucklord_reach_bind_pressed;
extern std::string cucklord_reach_bind_text;
extern bool cucklord_reach_used;

extern float cucklord_velocity_value;
extern float cucklord_velocity_chance_value;
extern bool cucklord_velocity_enabled;
extern bool cucklord_velocity_bind_pressed;
extern std::string cucklord_velocity_bind_text;
extern bool cucklord_velocity_used;

extern bool cucklord_speed_tpmode;
extern float cucklord_speed_value;
extern bool cucklord_speed_enabled;
extern bool cucklord_speed_bind_pressed;
extern std::string cucklord_speed_bind_text;
extern bool cucklord_speed_used;


extern int cucklord_priority_value;
std::string cucklord_get_hwid();

BOOL cucklord_get_privilege(HANDLE processhandle, std::string perm);
DWORD cucklord_get_processid(std::string processname);
DWORD cucklord_get_module_base(DWORD pid, std::string modulename);
int cucklord_random_int(int min, int max);
bool cucklord_compare_value(float one, float two);



float cucklord_random_float(float min, float max);

extern int totalremoved;


std::string cucklord_random_string(std::string::size_type length);

void cucklord_selfdestruct_function();
std::string cucklord_get_exe_name();
DWORD cucklord_get_service_processid(const char* serviceName);
std::string cucklord_get_exe_path();
extern bool cucklord_destruct_selfdelete;
void cucklord_selfdelete_function();


extern int cucklord_priority_sleeper;
void cucklord_get_window_position(int sizex, int sizey);
extern int cucklord_horizontal;
extern int cucklord_vertical;
extern HWND hwnd;

WCHAR cucklord_random_wchar(int min, int max);


extern bool cucklord_hide_bind_pressed;
extern std::string cucklord_hide_bind_text;
extern bool cucklord_hide_enabled;
extern std::vector<const char*>cucklord_destruct_options_text;
extern const char* cucklord_priority_value_char[3];