#include "imgui.h"

#include "imgui_impl_dx9.h"
#include "imgui_impl_win32.h"
#include "SelfDestruct/Delete.h"
#include "Graphics/Font.h"
#include "Graphics/Graphics.h"
#include "Graphics/Color.h"
#include "xor.h"
#include <d3d9.h>
#define DIRECTINPUT_VERSION 0x0800
#define VC_EXTRALEAN
#define WIN32_LEAN_AND_MEAN
#include <dinput.h>
#include <time.h>
#include <Windows.h>
#include <tchar.h>
#include <vector>


// Data
static LPDIRECT3D9              g_pD3D = NULL;
static LPDIRECT3DDEVICE9        g_pd3dDevice = NULL;
static D3DPRESENT_PARAMETERS    g_d3dpp = {};

// Functions:
bool CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
void ResetDevice();
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
LRESULT CALLBACK MouseCallBack(int nCode, WPARAM wParam, LPARAM lParam);
DWORD WINAPI HookThread(LPVOID lParam);

void Reach();
void Longjump();
int random(int min, int max);
void madebyprivileged();
void clicker();
int GetButtonPressed();
void KeyBindButton(int *key);
//Variable:

DWORD pID;
HANDLE pHandle;

//autoclicker
static bool randomize = true;
static int cps = 12;
static int delay;
static bool mconly = true;
static int range = 0;
static int range2 = 0;
static bool switchkey = true;
static bool switchkey2 = true;
static bool breakblock = false;
static bool inv = false;
static bool state = false;
static bool mouse_down = false;
int keybindac = 0x73;
int staticcps = cps;
int simin = 1, simax = 2;
int lastcps, lastlastcps, rd, rd2, itogglekey;
int counter = 1;
int counter3 = 0;
bool counter2 = true;
bool clicking = false;
bool first = true;
int mincps = cps - 1;
int maxcps = cps + 1;


//Multi Clicker

static int doubleclick = 2;
static int chance = 50;
static bool state2;
static bool switchkey3 = true;

//reach
static int address = 0x02A0000C;
static bool re_ach = false;
static float value_R = 3.0;
static float legit = 2.125;
static float value_foundreach;
static float reachunlegit = 0;
static bool noLag = true;
static int contatorereach = 0;
static int addressvec[10000];

//longjump
static bool longjump = false;
static int value_longjump = 2;
static double longjumpunlegit = 0;
static double longjumplegit = 0.3;
static double longjumpV = 0;
static bool noLag2 = true;
static int addressvec2[1028];
static int addresslongjump = 0x02A00000;
static int contatorelongjump = 0;

//color
static bool buttoncolor = true;
static float colorbutton[] = { 0.70f, 0.09f, 0.09f, 1.00f };
static bool buttoncolor2 = false;
static float colorbutton2[] = { 0.10f, 0.09f, 0.12f, 1.00f };
static bool textcolor = false;
static float colortext[] = { 0.80f, 0.80f, 0.83f, 1.00f };
static bool backgroundcolor = false;
static float colorbackground[] = { 0.06f, 0.05f, 0.07f, 1.00f };
static bool rain;
static int Speed = 0;
static float r = 1.0f;
static float g = 0.f;
static float b = 0.f;

//misc
bool deleteonexit = true;
HWND hWnd = FindWindow(_T("LWJGL"), nullptr);
POINT pt;
HHOOK mouseHook;
HHOOK reach;

void multiclicker() {
    int random = 0;
    auto akclicker = []() {
        for (int g = 0; g <= doubleclick; g++) {
            GetCursorPos(&pt);
            SendMessage(hWnd, WM_LBUTTONDOWN, 0, MAKELPARAM(pt.x, pt.y));
            SendMessage(hWnd, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
        }
    };
    if ((rand() % 10 + 1) < (chance / 10)) {
        akclicker();
    }
}

// Main code
int main(int, char *argv[])
{
    // Create application window
    WNDCLASSEX wc = { sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, NULL, NULL, NULL, _T("Ettelen Client"), NULL };
    ::RegisterClassEx(&wc);
    HWND hwnd = ::CreateWindow(wc.lpszClassName, _T(""), WS_MINIMIZEBOX | WS_SYSMENU | WS_BORDER, 400, 400, 422, 350, NULL, NULL, wc.hInstance, NULL);

    // Initialize Direct3D
    if (!CreateDeviceD3D(hwnd))
    {
        CleanupDeviceD3D();
        ::UnregisterClass(wc.lpszClassName, wc.hInstance);
        return 1;
    }

    // Show the window
    ::ShowWindow(hwnd, SW_SHOWDEFAULT);
    ::UpdateWindow(hwnd);

    // Setup Dear ImGui context
    IMGUI_CHECKVERSION();
    ImGui::CreateContext();
    ImGuiIO& io = ImGui::GetIO(); (void)io;
    //io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls
    //io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad;      // Enable Gamepad Controls



    // Setup Platform/Renderer bindings
    ImGui_ImplWin32_Init(hwnd);
    ImGui_ImplDX9_Init(g_pd3dDevice);

    // Load Fonts
    // - If no fonts are loaded, dear imgui will use the default font. You can also load multiple fonts and use ImGui::PushFont()/PopFont() to select them.
    // - AddFontFromFileTTF() will return the ImFont* so you can store it if you need to select the font among multiple.
    // - If the file cannot be loaded, the function will return NULL. Please handle those errors in your application (e.g. use an assertion, or display an error and quit).
    // - The fonts will be rasterized at a given size (w/ oversampling) and stored into a texture when calling ImFontAtlas::Build()/GetTexDataAsXXXX(), which ImGui_ImplXXXX_NewFrame below will call.
    // - Read 'misc/fonts/README.txt' for more instructions and details.
    // - Remember that in C/C++ if you want to include a backslash \ in a string literal you need to write a double backslash \\ !
    //io.Fonts->AddFontDefault();
    //ImFont* font = io.Fonts->AddFontFromFileTTF("C:\\Windows\\Fonts\\Courier_New.ttf" , 15.0f)
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/Roboto-Medium.ttf", 15.0f);
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/Cousine-Regular.ttf", 15.0f);
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/Inconsolata-Regular.ttf", 15.0f);
    io.Fonts->AddFontFromMemoryCompressedTTF(&MyFont_compressed_data, sizeof MyFont_compressed_data, 15.0f);
    //ImGuiIO& io = ImGui::GetIO();
    //static const unsigned int MyFont_compressed_size = 63718;
    //ImFont* font = io.Fonts->AddFontFromFileTTF("c:\\Windows\\Fonts\\ArialUni.ttf", 18.0f, NULL, io.Fonts->GetGlyphRangesJapanese());
    //IM_ASSERT(font != NULL);

    // Our state
    ImVec4 clear_color = ImVec4(2.55f, 0.00f, 0.00f, 0.00f);
    io.IniFilename = NULL;
    srand(time(NULL));

	ImGuiStyle * style = &ImGui::GetStyle();
	StartColor();
    CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&HookThread, NULL, 0, 0);

    // Main loop
    MSG msg;
    ZeroMemory(&msg, sizeof(msg));
    while (msg.message != WM_QUIT)
    {
        // Poll and handle messages (inputs, window resize, etc.)
        // You can read the io.WantCaptureMouse, io.WantCaptureKeyboard flags to tell if dear imgui wants to use your inputs.
        // - When io.WantCaptureMouse is true, do not dispatch mouse input data to your main application.
        // - When io.WantCaptureKeyboard is true, do not dispatch keyboard input data to your main application.
        // Generally you may always pass all inputs to dear imgui, and hide them from your application based on those two flags.
        if (::PeekMessage(&msg, NULL, 0U, 0U, PM_REMOVE))
        {
            ::TranslateMessage(&msg);
            ::DispatchMessage(&msg);
            io.WantCaptureKeyboard = true;
            io.WantCaptureMouse = true;
            continue;
        }

        // Start the Dear ImGui frame
        ImGui_ImplDX9_NewFrame();
        ImGui_ImplWin32_NewFrame();
        ImGui::NewFrame();
        // 2. Show a simple window that we create ourselves. We use a Begin/End pair to created a named window.
        {

            ImGui::SetNextWindowPos(ImVec2(-2, -2), ImGuiCond_FirstUseEver);
            ImGui::Begin("Ettelen Client v2.1", NULL, ImVec2(412, 427), 2.55, ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove);
            //ImGui::SetWindowFontScale(1.08f);
            mincps = cps - 1;
            maxcps = cps + 1;


            ImGuiTabBarFlags tab_bar_flags = ImGuiTabBarFlags_None;
            if (ImGui::BeginTabBar("Menu", tab_bar_flags))
            {
                if (ImGui::BeginTabItem("Clicker"))
                {
                    ImGui::Text("Autoclicker");
                    ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.84);
                    ImGui::SliderInt("###cps", &cps, 8, 20);
                    ImGui::PopItemWidth();
                    ImGui::SameLine();
                    ImGui::Checkbox("###state", &state);
                    ImGui::BeginGroup();
                    ImGui::Checkbox("Randomizer", &randomize);
                    ImGui::Checkbox("Minecraft Only", &mconly);
                    ImGui::EndGroup();

                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();
                    ImGui::Spacing();
                    ImGui::SameLine();

                    ImGui::BeginGroup();
                    KeyBindButton(&keybindac);
                    ImGui::Checkbox("Break Block", &breakblock);
                    ImGui::EndGroup();
                    ImGui::EndTabItem();

                    line(colorbutton);


                    ImGui::BeginGroup();
                    ImGui::Text("Multi Clicker");
                    ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.50);
                    ImGui::SliderInt("###Double", &doubleclick, 2, 100);
                    ImGui::PopItemWidth();
                    ImGui::EndGroup();
                    ImGui::SameLine();
                    ImGui::BeginGroup();
                    ImGui::Text("Chance:");
                    ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.30);
                    ImGui::SliderInt("###chance", &chance, 30, 100);
                    ImGui::PopItemWidth();
                    ImGui::SameLine();
                    ImGui::Checkbox("###state", &state2);
                    ImGui::EndGroup();

                }
                if (ImGui::BeginTabItem("Reach")) {
                    ImGui::Text("Reach");
                    ImGui::Checkbox("###Reach", &re_ach);
                    if (re_ach != true) {
                        ImGui::SameLine();
                        ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.84);
                        ImGui::SliderFloat("###reachslider", &value_R, 3.0, 4.0,"%.2f");
                        ImGui::PopItemWidth();
                    }
                    else {
                        ImGui::SameLine();
                        ImGui::Text("%.2f", value_R);
                    }
                    if (value_R < 3.0) {
                        value_R = 3.0;
                    }
                    else if (value_R > 4.0) {
                        value_R = 4.0;
                    }
                    reachunlegit = ((value_R - 3) * 0.125) + 2.125;
                    if (re_ach == true && noLag == true) {
                        auto reach = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&Reach, NULL, 0, 0);
                        CloseHandle(reach);
                        noLag = false;
                    }
                    if (re_ach == false && noLag == false) {
                        for (int y = 0; y < contatorereach; y++) {
                            if (ReadProcessMemory(pHandle, (void*)addressvec[y], &value_foundreach, sizeof(float), 0))
                            {
                                if (value_foundreach == reachunlegit) {
                                    WriteProcessMemory(pHandle, (void*)addressvec[y], &legit, sizeof(float), 0);
                                }
                            }
                        }
                        noLag = true;
                    }
                    ImGui::EndTabItem();
                }
                if (ImGui::BeginTabItem("Long Jump"))
                {
                    ImGui::Text("LongJump");
                    ImGui::Checkbox("###LongJump", &longjump);
                    ImGui::SameLine();
                    if (longjump != true) {
                        ImGui::PushItemWidth(ImGui::GetWindowWidth() * 0.84);
                        ImGui::SliderInt("###longjumpinput", &value_longjump, 2, 20);
                        ImGui::PopItemWidth();
                    }
                    else {
                        ImGui::SameLine();
                        ImGui::Text("%d", value_longjump);
                    }
                    longjumpunlegit = 1.0 * value_longjump;
                    if (longjump == true && noLag2 == true) {
                        auto longjump = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)&Longjump, NULL, 0, 0);
                        CloseHandle(longjump);
                        noLag2 = false;
                    }
                    if (longjump == false && noLag2 == false) {
                        for (int y = 0; y < contatorelongjump; y++) {
                            if (ReadProcessMemory(pHandle, (void*)addressvec2[y], &longjumpV, sizeof(double), 0))
                            {
                                if (longjumpV == longjumpunlegit) {
                                    WriteProcessMemory(pHandle, (void*)addressvec2[y], &longjumplegit, sizeof(double), 0);
                                }
                            }
                        }
                        noLag2 = true;
                    }
                    ImGui::EndTabItem();
                }
                if (ImGui::BeginTabItem("Color")) {
                    ImGui::Text("Color");
                    ImGui::BeginGroup();
                    if (ImGui::Button("Button Color", ImVec2(140, 28))) {
                        buttoncolor = true;
                        buttoncolor2 = false;
                        backgroundcolor = false;
                        textcolor = false;
                    }
                    if (ImGui::Button("Button 2 Color", ImVec2(140, 28))) {
                        buttoncolor = false;
                        backgroundcolor = false;
                        textcolor = false;
                        buttoncolor2 = true;
                    }
                    if (ImGui::Button("Text Color", ImVec2(140, 28))) {
                        buttoncolor = false;
                        backgroundcolor = false;
                        textcolor = true;
                        buttoncolor2 = false;
                    }
                    if (ImGui::Button("Background Color", ImVec2(140, 28))) {
                        buttoncolor = false;
                        backgroundcolor = true;
                        textcolor = false;
                        buttoncolor2 = false;
                    }
                    if (buttoncolor == true) {
                        ImGui::Checkbox("Rainbow", &rain);
                    }

                    ImGui::EndGroup();

                    ImGui::SameLine();

                    ImGui::BeginGroup();
                    if (buttoncolor == true) {
                        ColorPicker("###ButtonColor", colorbutton);
                    }
                    if (buttoncolor2 == true) {
                        ColorPicker("###ButtonColor2", colorbutton2);
                    }
                    if (textcolor == true) {
                        ColorPicker("###ButtonColor3", colortext);
                    }
                    if (backgroundcolor == true) {
                        ColorPicker("###ButtonColor4", colorbackground);
                    }
                    ImGui::EndGroup();

                    ImGui::EndTabItem();
                }
                if (ImGui::BeginTabItem("Misc"))
                {
                    if (ImGui::Button("Self Destruct"))
                    {
                        if (deleteonexit)
                            DelMe();
                        else
                            exit(0);
                    }
                    ImGui::SameLine();
                    ImGui::Checkbox("Delete On Exit", &deleteonexit);
                    ImGui::EndTabItem();
                }

                ImGui::EndTabBar();
            }


            line(colorbutton);

            if (rain == true) {
                if (r == 1.f && g >= 0.f && b <= 0.f)
                {
                    g += 0.010f;
                    b = 0.f;
                }
                if (r <= 1.f && g >= 1.f && b == 0.f)
                {
                    g = 1.f;
                    r -= 0.010f;
                }
                if (r <= 0.f && g == 1.f && b >= 0.f)
                {
                    r = 0.f;
                    b += 0.010f;
                }
                if (r == 0.f && g <= 1.f && b >= 1.f)
                {
                    b = 1.f;
                    g -= 0.010f;
                }
                if (r >= 0.f && g <= 0.f && b == 1.f)
                {
                    g = 0.f;
                    r += 0.010f;
                }
                if (r >= 1.f && g >= 0.f && b <= 1.f)
                {
                    r = 1.f;
                    b -= 0.010f;
                }
                colorbutton[0] = r;
                colorbutton[1] = g;
                colorbutton[2] = b;
            }

            style->Colors[ImGuiCol_TabActive] = ImVec4(colorbutton[0], colorbutton[1], colorbutton[2], colorbutton[3]);
            style->Colors[ImGuiCol_CheckMark] = ImVec4(colorbutton[0], colorbutton[1], colorbutton[2], colorbutton[3]);
            style->Colors[ImGuiCol_SliderGrab] = ImVec4(colorbutton[0], colorbutton[1], colorbutton[2], colorbutton[3]);
            style->Colors[ImGuiCol_TitleBgActive] = ImVec4(colorbutton[0], colorbutton[1], colorbutton[2], colorbutton[3]);
            style->Colors[ImGuiCol_TabHovered] = ImVec4(colorbutton[0], colorbutton[1], colorbutton[2], colorbutton[3]);


            style->Colors[ImGuiCol_Text] = ImVec4(colortext[0], colortext[1], colortext[2], colortext[3]);

            style->Colors[ImGuiCol_WindowBg] = ImVec4(colorbackground[0], colorbackground[1], colorbackground[2], colorbackground[3]);
            style->Colors[ImGuiCol_ScrollbarGrabActive] = ImVec4(colorbackground[0], colorbackground[1], colorbackground[2], colorbackground[3]);
            style->Colors[ImGuiCol_HeaderActive] = ImVec4(colorbackground[0], colorbackground[1], colorbackground[2], colorbackground[3]);
            style->Colors[ImGuiCol_ResizeGripActive] = ImVec4(colorbackground[0], colorbackground[1], colorbackground[2], colorbackground[3]);

            style->Colors[ImGuiCol_Tab] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            style->Colors[ImGuiCol_FrameBg] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            style->Colors[ImGuiCol_MenuBarBg] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            style->Colors[ImGuiCol_ScrollbarBg] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            style->Colors[ImGuiCol_Button] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            style->Colors[ImGuiCol_Header] = ImVec4(colorbutton2[0], colorbutton2[1], colorbutton2[2], colorbutton2[3]);
            range2 = 1000 / (cps - 2);
            range = 1000 / (cps + 3);

            //autoclicker
            if (GetAsyncKeyState(keybindac) && switchkey == true) {
                state = !state;
                switchkey = false;
            }
            if (!GetAsyncKeyState(keybindac)) {
                switchkey = true;
            }

            HWND hWnd = FindWindow(_T("LWJGL"), NULL);
            HWND hwndMc = GetForegroundWindow();
            if (hwnd != hwndMc) {
                if (state2) {
                    if (hWnd == hwndMc) {
                        if (mouse_down && switchkey3) {
                            multiclicker();
                            switchkey3 = false;
                        }
                    }
                }
            }
            if (!mouse_down) {
                switchkey3 = true;
            }
            if (hwnd != hwndMc) {
                if (state == true) {
                    if (mconly == true) {
                        if (hWnd == hwndMc) {
                            if (breakblock == true) {
                                if (mouse_down) {
                                    if (randomize == true) {
                                        delay = (rand() % (range2 - range)) + range;
                                    }
                                    else {
                                        delay = 1000 / cps;
                                    }
                                    GetCursorPos(&pt);
                                    SendMessage(hWnd, WM_LBUTTONUP, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
                                    SendMessage(hWnd, WM_LBUTTONDOWN, 0, MAKELPARAM(pt.x, pt.y));
                                    Sleep(delay);
                                }
                            }
                            else {
                                clicker();
                            }
                        }
                    }
                    else{
                        clicker();
                    }
                }
            }

            ImGui::End();
        }
        // Rendering
        ImGui::EndFrame();
        g_pd3dDevice->SetRenderState(D3DRS_ZENABLE, false);
        g_pd3dDevice->SetRenderState(D3DRS_ALPHABLENDENABLE, false);
        g_pd3dDevice->SetRenderState(D3DRS_SCISSORTESTENABLE, false);
        D3DCOLOR clear_col_dx = D3DCOLOR_RGBA((int)(clear_color.x*255.0f), (int)(clear_color.y*255.0f), (int)(clear_color.z*255.0f), (int)(clear_color.w*255.0f));
        g_pd3dDevice->Clear(0, NULL, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, clear_col_dx, 1.0f, 0);
        if (g_pd3dDevice->BeginScene() >= 0)
        {
            ImGui::Render();
            ImGui_ImplDX9_RenderDrawData(ImGui::GetDrawData());
            g_pd3dDevice->EndScene();
        }
        HRESULT result = g_pd3dDevice->Present(NULL, NULL, NULL, NULL);

        // Handle loss of D3D9 device
        if (result == D3DERR_DEVICELOST && g_pd3dDevice->TestCooperativeLevel() == D3DERR_DEVICENOTRESET)
            ResetDevice();
    }

    ImGui_ImplDX9_Shutdown();
    ImGui_ImplWin32_Shutdown();
    ImGui::DestroyContext();

    CleanupDeviceD3D();
    ::DestroyWindow(hwnd);
    ::UnregisterClass(wc.lpszClassName, wc.hInstance);

    return 0;
}

// Helper functions

bool CreateDeviceD3D(HWND hWnd)
{
    if ((g_pD3D = Direct3DCreate9(D3D_SDK_VERSION)) == NULL)
        return false;

    // Create the D3DDevice
    ZeroMemory(&g_d3dpp, sizeof(g_d3dpp));
    g_d3dpp.Windowed = TRUE;
    g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
    g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
    g_d3dpp.EnableAutoDepthStencil = TRUE;
    g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
    g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE;           // Present with vsync
    //g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_IMMEDIATE;   // Present without vsync, maximum unthrottled framerate
    if (g_pD3D->CreateDevice(D3DADAPTER_DEFAULT, D3DDEVTYPE_HAL, hWnd, D3DCREATE_HARDWARE_VERTEXPROCESSING, &g_d3dpp, &g_pd3dDevice) < 0)
        return false;

    return true;
}

void CleanupDeviceD3D()
{
    if (g_pd3dDevice) { g_pd3dDevice->Release(); g_pd3dDevice = NULL; }
    if (g_pD3D) { g_pD3D->Release(); g_pD3D = NULL; }
}

void ResetDevice()
{
    ImGui_ImplDX9_InvalidateDeviceObjects();
    HRESULT hr = g_pd3dDevice->Reset(&g_d3dpp);
    if (hr == D3DERR_INVALIDCALL)
        IM_ASSERT(0);
    ImGui_ImplDX9_CreateDeviceObjects();
}

// Win32 message handler
extern LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
        return true;

    switch (msg)
    {
    case WM_SIZE:
        if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED)
        {
            g_d3dpp.BackBufferWidth = LOWORD(lParam);
            g_d3dpp.BackBufferHeight = HIWORD(lParam);
            ResetDevice();
        }
        return 0;
    case WM_SYSCOMMAND:
        if ((wParam & 0xfff0) == SC_KEYMENU) // Disable ALT application menu
            return 0;
        break;
    case WM_DESTROY:
        ::PostQuitMessage(0);
        return 0;
    }
    return ::DefWindowProc(hWnd, msg, wParam, lParam);
}


LRESULT CALLBACK MouseCallBack(int nCode, WPARAM wParam, LPARAM lParam)
{
    PMSLLHOOKSTRUCT pMouse = (PMSLLHOOKSTRUCT)lParam;
    if (NULL != pMouse)
    {
        if (WM_MOUSEMOVE != wParam)
        {
            if (0 == pMouse->flags)
            {
                switch (wParam)
                {
                case WM_LBUTTONDOWN:
                    mouse_down = TRUE;
                    break;
                case WM_LBUTTONUP:
                    mouse_down = FALSE;
                    break;
                }
            }
        }
    }
    return CallNextHookEx(mouseHook, nCode, wParam, lParam);
}


DWORD WINAPI HookThread(LPVOID lParam)
{
    mouseHook = SetWindowsHookEx(WH_MOUSE_LL, &MouseCallBack, nullptr, 0);

    MSG msg;
    while (GetMessage(&msg, NULL, 0, 0))
    {
        TranslateMessage(&msg);
        DispatchMessage(&msg);
    }

    UnhookWindowsHookEx(mouseHook);
    return 0;
}

void Reach()
{
	GetWindowThreadProcessId(hWnd, &pID);
	pHandle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pID);
	while (re_ach == true) {
		Sleep(1);
		for (int i = 0; i < 2000; i++)
		{
			if (address < 0x04FFFFFF) {
				if (ReadProcessMemory(pHandle, (void*)address, &value_foundreach, sizeof(float), 0))
				{
					if (value_foundreach == legit) {
						WriteProcessMemory(pHandle, (void*)(address), &reachunlegit, sizeof(float), 0);
						addressvec[contatorereach] = address;
						contatorereach++;
						Sleep(1);
					}
				}
				address += 0x00000008;
			}
		}
		if (address > 0x04FFFFFF) {
			address = 0x02A0000C;
		}
	}
}

void Longjump()
{
    GetWindowThreadProcessId(hWnd, &pID);
    pHandle = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pID);
    while (addresslongjump < 0x04FFFFFF)
    {
        if (ReadProcessMemory(pHandle, (void*)addresslongjump, &longjumpV, sizeof(double), 0))
        {
            if (longjumpV == longjumplegit) {
                WriteProcessMemory(pHandle, (void*)(addresslongjump), &longjumpunlegit, sizeof(double), 0);
                addressvec2[contatorelongjump] = addresslongjump;
                contatorelongjump++;
            }
        }
        addresslongjump += 0x00000008;
    }
    addresslongjump = 0x02A00000;
}

int random(int min, int max)
{
    srand((int)time*(time(NULL)));

    int r = (rand() % (max - min)) + min;

    return r;
}

void madebyprivileged() {
    auto akclicker = []() {
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

    auto timer = []() {
        static double start_time = GetTickCount();
        double current_time = GetTickCount() - start_time;

        int cps = randomize ? random(simin, simax) : staticcps;

        if (lastcps = cps && randomize)
        {
            rd = random(1, 10);
            if (rd % 2) {
                rd2 = random(1, 10);
                if (rd2 % 2)
                    cps += 2;
                else
                    cps -= 1;
            }

        }

        if (current_time >= (1000 / cps)) {
            start_time = GetTickCount();
            lastcps = cps;
            return true;
        }
        return false;
    };

    if (timer()) {

        if (mouse_down)
            akclicker();
        counter++;
        first = false;
    }
}

void clicker()
{

    if (mouse_down)
    {

        if (first)
        {
            Sleep(120);
        }
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

        madebyprivileged();
    }
    else
    {
        first = true;
        simax = maxcps;
        simin = mincps;
    }

}

int GetButtonPressed()
{
    for (int i = 0x05; i < 0x7B; i++)
    {
        if (GetAsyncKeyState(i))
            return i;
    }
    return -1;
}


void KeyBindButton(int *key)
{

    const char* text = "F4";
    static bool shouldListen = false;
    if (shouldListen)
    {
        text = "<press a key>";
        int keyPressed = GetButtonPressed();
        if (keyPressed != -1)
        {
            switchkey = false;
            *key = keyPressed;
            shouldListen = false;
        }
    }
    else {
        switch (*key) {
        case 0x05:
            text = "Mouse 4";
            break;
        case 0x06:
            text = "Mouse 5";
            break;
        case 0x08:
            text = "Backspace";
            break;
        case 0x0D:
            text = "ENTER";
            break;
        case 0x09:
            text = "TAB";
            break;
        case 0x10:
            text = "Shift";
            break;
        case 0x11:
            text = "CTRL";
            break;
        case 0x12:
            text = "ALT";
            break;
        case 0x13:
            text = "Pause";
            break;
        case 0x14:
            text = "Caps Lock";
            break;
        case 0x1B:
            text = "ESC";
            break;
        case 0x20:
            text = "Spacebar";
            break;
        case 0x21:
            text = "Page Up";
            break;
        case 0x22:
            text = "Page Down";
            break;
        case 0x23:
            text = "END";
            break;
        case 0x24:
            text = "Home";
            break;
        case 0x25:
            text = "Left Arrow";
            break;
        case 0x26:
            text = "Up Arrow";
            break;
        case 0x27:
            text = "Right Arrow";
            break;
        case 0x28:
            text = "Down Arrow";
            break;
        case 0x29:
            text = "Select";
            break;
        case 0x2A:
            text = "Print";
            break;
        case 0x2B:
            text = "Execute";
            break;
        case 0x2C:
            text = "Print Screen";
            break;
        case 0x2D:
            text = "INS";
            break;
        case 0x2E:
            text = "DEL";
            break;
        case 0x2F:
            text = "HELP";
            break;
        case 0x30:
        case 0x31:
        case 0x32:
        case 0x33:
        case 0x34:
        case 0x35:
        case 0x36:
        case 0x37:
        case 0x38:
        case 0x39:
        case 0x41:
        case 0x42:
        case 0x43:
        case 0x44:
        case 0x45:
        case 0x46:
        case 0x47:
        case 0x48:
        case 0x49:
        case 0x4A:
        case 0x4B:
        case 0x4C:
        case 0x4D:
        case 0x4E:
        case 0x4F:
        case 0x50:
        case 0x51:
        case 0x52:
        case 0x53:
        case 0x54:
        case 0x55:
        case 0x56:
        case 0x57:
        case 0x58:
        case 0x59:
        case 0x5A:
            text = (char*)key;
            break;
        case 0x60:
            text = "NUMPAD0";
            break;
        case 0x61:
            text = "NUMPAD1";
            break;
        case 0x62:
            text = "NUMPAD2";
            break;
        case 0x63:
            text = "NUMPAD3";
            break;
        case 0x64:
            text = "NUMPAD4";
            break;
        case 0x65:
            text = "NUMPAD5";
            break;
        case 0x66:
            text = "NUMPAD6";
            break;
        case 0x67:
            text = "NUMPAD7";
            break;
        case 0x68:
            text = "NUMPAD8";
            break;
        case 0x69:
            text = "NUMPAD9";
            break;
        case 0x6A:
            text = "Multiply";
            break;
        case 0x6B:
            text = "Add";
            break;
        case 0x6C:
            text = "Separator";
            break;
        case 0x6E:
            text = "Decimal";
            break;
        case 0x6F:
            text = "Divide";
            break;
        case 0x70:
            text = "F1";
            break;
        case 0x71:
            text = "F2";
            break;
        case 0x72:
            text = "F3";
            break;
        case 0x73:
            text = "F4";
            break;
        case 0x74:
            text = "F5";
            break;
        case 0x75:
            text = "F6";
            break;
        case 0x76:
            text = "F7";
            break;
        case 0x77:
            text = "F8";
            break;
        case 0x78:
            text = "F9";
            break;
        case 0x79:
            text = "F10";
            break;
        case 0x7A:
            text = "F11";
            break;
        case 0x7B:
            text = "F12";
            break;
        case 0x90:
            text = "NUM LOCK";
            break;

        }
    }
    if (ImGui::Button(text, ImVec2(160, 0)))
        shouldListen = true;
}