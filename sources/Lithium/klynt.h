#pragma once

#include "imgui/imgui.h"
#include "imgui/imgui_impl_win32.h"
#include "imgui/imgui_impl_dx11.h"
#include <d3d11.h>

#define DIRECTINPUT_VERSION 0x0800

#include "imgui/fonts/fa.h"
#include "imgui/fonts/roboto.h"


#define lith_host xorstr("IP/api/q5TpvKV2DPqLnWXU?")
#define lith_security_host xorstr("IP/api/security?")
#pragma comment(lib, "d3dcompiler.lib")
#pragma comment(lib, "d3d11.lib")
#pragma comment(lib, "dxgi.lib")


static ID3D11Device* g_pd3dDevice = nullptr;
static ID3D11DeviceContext* g_pd3dDeviceContext = nullptr;
static IDXGISwapChain* g_pSwapChain = nullptr;
static ID3D11RenderTargetView* g_mainRenderTargetView = nullptr;
bool CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
void CreateRenderTarget();
void CleanupRenderTarget();
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

#include <TlHelp32.h>
#include <iostream>
#include <vector>
#include <chrono>
#include <thread>
#include <algorithm>
#include <random>
#include <string>
#include <fstream>
#include <sstream>
#include "xorstr.h"
#include "ntdll.h"

namespace v {



    namespace info {
        inline DWORD minecraft_pid;
        inline HANDLE minecraft_handle;
        inline HWND minecraft_window;
        inline bool verified = true;
        inline std::string version = xorstr("2.0.2");
    }

    namespace launch {
        extern void all_threads();
    }

    namespace status {
        extern void thread();
    }

    namespace binds {
        extern void thread();
    }

    namespace gui {
        inline HWND hwnd;
        inline ImVec4 color1 = ImColor(0.9f, 0.24f, 0.24f);
        inline ImVec4 color2 = ImVec4(0.56f, 0.00f, 1.00f, 0.50f);
        inline ImVec4 color3 = ImVec4(1.00f, 1.00f, 1.00f, 0.15f);
        inline bool rainbow_mode = false;
        inline bool window_being_dragged = false;
        inline bool item_being_used = false;
        inline int tab_main = 1;
        inline char lithgui = 'A';
        inline bool memeversion = false;
        extern void move_window();
    }

    namespace local_player {
        inline bool moving = false;
        inline bool sprinting = false;
        inline bool jumping = false;
        inline bool inventory_open = false;
        inline int current_slot = 1;

        namespace binds {
            inline int slot1 = 0x31;
            inline int slot2 = 0x32;
            inline int slot3 = 0x33;
            inline int slot4 = 0x34;
            inline int slot5 = 0x35;
            inline int slot6 = 0x36;
            inline int slot7 = 0x37;
            inline int slot8 = 0x38;
            inline int slot9 = 0x39;

            inline int w = 'w';
            inline int a = 'a';
            inline int s = 's';
            inline int d = 'd';

            inline int jump = VK_SPACE;
            inline int sprint = VK_LCONTROL;
            inline int drop = 'q';
            inline int inventory = 'e';
        }
    }

    namespace scanner {
        extern void thread();
    }

    namespace modules {
        namespace clicker {
            namespace left {
                namespace jitter {
                    extern void thread();
                }

                extern void thread();

                inline bool enabled = false;
                inline float cps = 12.f;
                inline float blockhit_delay;
                inline float x_jitter;
                inline float y_jitter;
                inline bool inventory = false;
                inline bool blockhit = false;
                inline bool rmb_lock = true;
                inline bool slot[9] = { true, true, true, true, true, true, true, true, true };
                inline std::string bind = xorstr("[NONE]");
                inline bool bind_pressed = false;
                inline size_t selected_bind = 0;
            }

            namespace right {
                extern void thread();

                inline bool enabled;
                inline float cps = 12.f;
                inline bool slot[9] = { true, true, true, true, true, true, true, true, true };
                inline std::string bind = xorstr("[NONE]");
                inline bool bind_pressed;
                inline size_t selected_bind;
            }
        }

        namespace reach {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> unlimit;
            inline std::vector<size_t> floats;
            inline std::vector<size_t> doubles;
            inline bool enabled;
            inline float minvalue;
            inline float maxvalue;
            inline bool while_moving = true;
            inline std::string bind = xorstr("[NONE]");

            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace throwpot {
            extern void thread();

            inline bool enabled;
            inline bool throw_item;
            inline float return_pos;
            inline float throw_delay;
            inline bool slot[9];
            inline std::string bind = xorstr("[NONE]");
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace fly {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locationsfly;
            inline bool enabled;
            inline bool waiting;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace nametags {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations1;
            inline std::vector<size_t> locations2;
            inline bool enabled;
            inline float multiplier = 1.f;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace esp {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace cavefinder {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace timer {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline float multiplier = 1.f;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace speed {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline float multiplier = 1.f;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace velocity {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline float min;
            inline float max;
            inline bool while_moving = true;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace bhop {
            extern void thread();

            extern void disable();

            inline std::vector<size_t> locations;
            inline bool enabled;
            inline float speed = 1.f;
            inline std::string bind = xorstr("[NONE]");
            inline bool used;
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace antiafk {
            extern void thread();

            inline bool enabled;
        }

        namespace destruct {
            extern void execute();

            inline bool pcaclean = false;
            inline bool traceless = true;

            inline std::string bind = xorstr("[NONE]");
            inline bool bind_pressed;
            inline size_t selected_bind;
        }

        namespace hide {
            inline bool enabled;
            inline std::string bind = xorstr("[NONE]");
            inline bool bind_pressed;
            inline size_t selected_bind;
        }
    }
    namespace utils {
        inline int random_int(int min, int max) {
            static auto device = std::random_device();
            static auto engine = std::mt19937(device());
            const auto gen = std::uniform_int_distribution<int>(min, max);
            return gen(engine);
        }

        inline float random_float(float min, float max) {
            static auto device = std::random_device();
            static auto engine = std::mt19937(device());
            const auto gen = std::uniform_real_distribution<float>(min, max);
            return gen(engine);
        }

        inline DWORD get_pid(const std::wstring& process_name) {
            PROCESSENTRY32 processInfo;
            processInfo.dwSize = sizeof(processInfo);

            HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
            if (processesSnapshot == INVALID_HANDLE_VALUE) {
                return 0;
            }

            Process32First(processesSnapshot, &processInfo);
            if (!process_name.compare(processInfo.szExeFile))
            {
                CloseHandle(processesSnapshot);
                return processInfo.th32ProcessID;
            }

            while (Process32Next(processesSnapshot, &processInfo))
            {
                if (!process_name.compare(processInfo.szExeFile))
                {
                    CloseHandle(processesSnapshot);
                    return processInfo.th32ProcessID;
                }
            }

            CloseHandle(processesSnapshot);
            return 0;
        }

        inline DWORD get_service_pid(const std::string& service_name) {
            const char* f1 = service_name.c_str();
            SC_HANDLE scm = OpenSCManager(nullptr, nullptr, NULL);
            SC_HANDLE sc = OpenService(scm, (LPCWSTR)f1, SERVICE_QUERY_STATUS);

            SERVICE_STATUS_PROCESS ssp = { 0 };
            DWORD bytes_needed = 0;
            QueryServiceStatusEx(sc, SC_STATUS_PROCESS_INFO, reinterpret_cast<LPBYTE>(&ssp), sizeof(ssp),
                &bytes_needed);

            CloseServiceHandle(sc);
            CloseServiceHandle(scm);

            return ssp.dwProcessId;
        }
        inline void suspend_process(DWORD pid) {
            if (HANDLE hProcess = OpenProcess(PROCESS_TERMINATE, false, pid)) {
                BOOL result = SuspendThread(hProcess);
                CloseHandle(hProcess);
            }
        }

        inline void resume_process(DWORD pid) {
            if (HANDLE hProcess = OpenProcess(PROCESS_TERMINATE, false, pid)) {
                BOOL result = ResumeThread(hProcess);
                CloseHandle(hProcess);
            }
        }

        inline void terminate_process(DWORD pid) {
            if (HANDLE hProcess = OpenProcess(PROCESS_TERMINATE, false, pid)) {
                BOOL result = TerminateProcess(hProcess, 1);
                CloseHandle(hProcess);
            }
        }



        inline std::string random_string(std::size_t size) {

            const std::string chars = xorstr("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");

            std::random_device random_device;
            std::mt19937 generator(random_device());
            std::uniform_int_distribution<> distribution(0, chars.size() - 1);

            std::string random_string;

            for (std::size_t i = 0; i < size; ++i)
                random_string += chars[distribution(generator)];

            return random_string;
        }

        inline void copy_to_clipboard(HWND hwnd, const std::string& s) {

            OpenClipboard(hwnd);
            EmptyClipboard();
            HGLOBAL hg = GlobalAlloc(GMEM_MOVEABLE, s.size() + 1);
            if (!hg) {

                CloseClipboard();
                return;
            }
            memcpy(GlobalLock(hg), s.c_str(), s.size() + 1);
            GlobalUnlock(hg);
            SetClipboardData(CF_TEXT, hg);
            CloseClipboard();
            GlobalFree(hg);
        }
    }
}
