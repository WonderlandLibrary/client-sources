#include "main.h"

// Data
static ID3D11Device*            g_pd3dDevice = NULL;
static ID3D11DeviceContext*     g_pd3dDeviceContext = NULL;
static IDXGISwapChain*          g_pSwapChain = NULL;
static ID3D11RenderTargetView*  g_mainRenderTargetView = NULL;

// Forward declarations of helper functions
bool CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
void CreateRenderTarget();
void CleanupRenderTarget();
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

void ChromaticTab(char* label, int& tabhandler, int tab)
{
    if (tabhandler == tab)
    {
        ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(35, 35, 35)));
        ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(35, 35, 35)));
        ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(35, 35, 35)));
        //ImGui::PushStyleColor(ImGuiCol_Text, gui::clear_col);
        if (ImGui::Button(label, ImVec2(150, 40))) { tabhandler = tab; }
        ImGui::PopStyleColor(3);
    }
    else
    {
        ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(25, 25, 25)));
        ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(25, 25, 25)));
        ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(25, 25, 25)));
        if (ImGui::Button(label, ImVec2(150, 40))) { tabhandler = tab; }
        ImGui::PopStyleColor(3);
    }
}

// Main code
int main(int, char**)
{
    client::get_priv(GetCurrentProcess(), SE_SECURITY_NAME);
    client::get_priv(GetCurrentProcess(), SE_DEBUG_NAME);

    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread::clicker, nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread::reach, nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread::velo, nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread::color, nullptr, 0, nullptr);
    CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread::binds, nullptr, 0, nullptr);

    ShowWindow(GetConsoleWindow(), SW_HIDE);

    int width = 650; //550 350
    int height = 400;

    WNDCLASSEX wc = { sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, NULL, NULL, NULL, _T(" "), NULL };
    ::RegisterClassEx(&wc);
    HWND hwnd = ::CreateWindow(wc.lpszClassName, NULL, WS_OVERLAPPEDWINDOW ^ WS_CAPTION ^ WS_THICKFRAME ^ WS_MAXIMIZEBOX, 100, 100, width, height, NULL, NULL, wc.hInstance, NULL);

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
    ImGuiStyle* Style = &ImGui::GetStyle();
    //io.ConfigFlags |= ImGuiConfigFlags_NavEnableKeyboard;     // Enable Keyboard Controls
    //io.ConfigFlags |= ImGuiConfigFlags_NavEnableGamepad;      // Enable Gamepad Controls

    // Setup Dear ImGui style
    ImGui::StyleColorsDark();
    //ImGui::StyleColorsClassic();

    // Setup Platform/Renderer backends
    ImGui_ImplWin32_Init(hwnd);
    ImGui_ImplDX11_Init(g_pd3dDevice, g_pd3dDeviceContext);

    // Load Fonts
    // - If no fonts are loaded, dear imgui will use the default font. You can also load multiple fonts and use ImGui::PushFont()/PopFont() to select them.
    // - AddFontFromFileTTF() will return the ImFont* so you can store it if you need to select the font among multiple.
    // - If the file cannot be loaded, the function will return NULL. Please handle those errors in your application (e.g. use an assertion, or display an error and quit).
    // - The fonts will be rasterized at a given size (w/ oversampling) and stored into a texture when calling ImFontAtlas::Build()/GetTexDataAsXXXX(), which ImGui_ImplXXXX_NewFrame below will call.
    // - Read 'docs/FONTS.md' for more instructions and details.
    // - Remember that in C/C++ if you want to include a backslash \ in a string literal you need to write a double backslash \\ !
    //io.Fonts->AddFontDefault();
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/Roboto-Medium.ttf", 16.0f);
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/Cousine-Regular.ttf", 15.0f);
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/DroidSans.ttf", 16.0f);
    //io.Fonts->AddFontFromFileTTF("../../misc/fonts/ProggyTiny.ttf", 10.0f);
    //ImFont* font = io.Fonts->AddFontFromFileTTF("c:\\Windows\\Fonts\\ArialUni.ttf", 18.0f, NULL, io.Fonts->GetGlyphRangesJapanese());
    //IM_ASSERT(font != NULL);

    // Our state
    ImFont* mainfont = io.Fonts->AddFontFromFileTTF("C:\\Windows\\Fonts\\Bahnschrift.ttf", 20);
    ImVec4 clear_color = ImVec4(0.45f, 0.55f, 0.60f, 1.00f);
    Style->WindowRounding = 0;
    Style->WindowBorderSize = 0;
    Style->GrabRounding = 2;
    Style->FrameRounding = 2;
    Style->ChildRounding = 0;
    Style->FrameBorderSize = 0;
    Style->Colors[ImGuiCol_WindowBg] = ImColor(0, 0, 0, 0);
    Style->Colors[ImGuiCol_ChildBg] = ImColor(25, 25, 25);
    Style->Colors[ImGuiCol_Button] = ImColor(35, 35, 35);
    Style->Colors[ImGuiCol_ButtonHovered] = ImColor(gui::clear_col);
    Style->Colors[ImGuiCol_ButtonActive] = ImColor(35, 35, 35);
    Style->Colors[ImGuiCol_CheckMark] = ImColor(gui::clear_col);
    Style->Colors[ImGuiCol_FrameBg] = ImColor(35, 35, 35);
    Style->Colors[ImGuiCol_FrameBgActive] = ImColor(35, 35, 35);
    Style->Colors[ImGuiCol_FrameBgHovered] = ImColor(35, 35, 35);
    Style->Colors[ImGuiCol_SliderGrab] = ImColor(gui::clear_col);
    Style->Colors[ImGuiCol_SliderGrabActive] = ImColor(gui::clear_col);

    ImGui::GetStyle().ScrollbarRounding = 5.0f;
    Style->GrabMinSize = 15.0f;
    Style->ScrollbarSize = 150.0f;
    Style->Colors[ImGuiCol_Text] = ImColor(255, 255, 255);

    io.IniFilename = NULL; // GET RID OF IMGUI.INI

    // Main loop
    bool done = false;
    while (!done)
    {
        // Poll and handle messages (inputs, window resize, etc.)
        // You can read the io.WantCaptureMouse, io.WantCaptureKeyboard flags to tell if dear imgui wants to use your inputs.
        // - When io.WantCaptureMouse is true, do not dispatch mouse input data to your main application.
        // - When io.WantCaptureKeyboard is true, do not dispatch keyboard input data to your main application.
        // Generally you may always pass all inputs to dear imgui, and hide them from your application based on those two flags.
        MSG msg;
        while (::PeekMessage(&msg, NULL, 0U, 0U, PM_REMOVE))
        {
            ::TranslateMessage(&msg);
            ::DispatchMessage(&msg);
            if (msg.message == WM_QUIT)
                done = true;
        }
        if (done)
            break;

        Style->Colors[ImGuiCol_SliderGrab] = ImColor(gui::clear_col);
        Style->Colors[ImGuiCol_SliderGrabActive] = ImColor(gui::clear_col);
        Style->Colors[ImGuiCol_CheckMark] = ImColor(gui::clear_col);
        Style->Colors[ImGuiCol_ButtonHovered] = ImColor(gui::clear_col);

        // Start the Dear ImGui frame
        ImGui_ImplDX11_NewFrame();
        ImGui_ImplWin32_NewFrame();
        ImGui::NewFrame();

        ImGui::SetNextWindowPos(ImVec2(0, 0));
        ImGui::SetNextWindowSize(ImVec2(width - 16, height - 39));

        ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0, 0));

        ImGui::Begin("##Menu", NULL, ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoMove); ImGui::PopStyleVar();
        {
            ImGui::BeginChild("##Tabs", ImVec2(150, height - 39), false, ImGuiWindowFlags_NoScrollbar);
            {
                ImGui::SetCursorPosY(9);
                ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(25, 25, 25)));
                ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(25, 25, 25)));
                ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(25, 25, 25)));
                ImGui::PushStyleColor(ImGuiCol_Text, gui::clear_col);
                ImGui::Button("Chromatic", ImVec2(150, 30));
                ImGui::PopStyleColor(4);

                ImGui::SetCursorPos(ImVec2(67, 30));
                ImGui::TextDisabled(client::version.c_str());

                ImGui::SetCursorPosY(60);
                ChromaticTab("Main", gui::tab, 1);
                ChromaticTab("Misc", gui::tab, 2);
            }
            ImGui::EndChild(); ImGui::SameLine();

            ImGui::SetCursorPosX(ImGui::GetCursorPosX() - 8);
            ImGui::PushStyleColor(ImGuiCol_ChildBg, ImVec4(ImColor(35, 35, 35)));
            ImGui::BeginChild("##TabContent", ImVec2((width - 16) - 150, height - 39), false, ImGuiWindowFlags_NoScrollbar); ImGui::PopStyleColor();
            {
                if (gui::tab == 1) // main
                {
                    ImGui::SetCursorPos(ImVec2(8, 8));
                    ImGui::BeginChild("##Clicker", ImVec2(((width - 16) - 150) - 16, 167), true, ImGuiWindowFlags_NoScrollbar);
                    {
                        ImGui::Checkbox("Clicker", &modules::clicker::enabled); ImGui::SameLine();

                        ImGui::TextDisabled(modules::clicker::bind_text.c_str());
                        if (ImGui::IsItemClicked())
                        {
                            modules::clicker::bind_text = "[...]";
                            modules::clicker::bind_edit = true;
                        }

                        ImGui::PushItemWidth(390);
                        ImGui::SliderFloat("CPS", &modules::clicker::cps, 1, 25, "%.1f");
                        ImGui::PushItemWidth(390);
                        ImGui::SliderFloat("Chance", &modules::clicker::chance, 1, 100, "%.0f");

                        ImGui::Spacing();

                        ImGui::Checkbox("Sounds", &modules::clicker::sounds);
                        ImGui::PushItemWidth(390);
                        ImGui::InputText("Path", modules::clicker::soundfile, IM_ARRAYSIZE(modules::clicker::soundfile));
                    }
                    ImGui::EndChild();

                    ImGui::SetCursorPosX(8);
                    ImGui::BeginChild("##Reach", ImVec2(((width - 16) - 150) - 16, 85), true, ImGuiWindowFlags_NoScrollbar);
                    {
                        ImGui::Checkbox("Reach", &modules::reach::enabled); ImGui::SameLine();

                        ImGui::TextDisabled(modules::reach::bind_text.c_str());
                        if (ImGui::IsItemClicked())
                        {
                            modules::reach::bind_text = "[...]";
                            modules::reach::bind_edit = true;
                        }

                        ImGui::PushItemWidth(390);
                        ImGui::SliderFloat("Blocks", &modules::reach::blocks, 3, 6, "%.2f");
                    }
                    ImGui::EndChild();

                    ImGui::SetCursorPosX(8);
                    ImGui::BeginChild("##Velocity", ImVec2(((width - 16) - 150) - 16, 85), true, ImGuiWindowFlags_NoScrollbar);
                    {
                        ImGui::Checkbox("Velocity", &modules::velocity::enabled); ImGui::SameLine();

                        ImGui::TextDisabled(modules::velocity::bind_text.c_str());
                        if (ImGui::IsItemClicked())
                        {
                            modules::velocity::bind_text = "[...]";
                            modules::velocity::bind_edit = true;
                        }

                        ImGui::PushItemWidth(390);
                        ImGui::SliderFloat("Percent", &modules::velocity::amount, 0, 100, "%.0f");
                    }
                    ImGui::EndChild();
                }

                if (gui::tab == 2) // misc
                {
                    ImGui::SetCursorPos(ImVec2(8, 8));
                    ImGui::BeginChild("##Color", ImVec2(((width - 16) - 150) - 16, 167), true, ImGuiWindowFlags_NoScrollbar);
                    {
                        ImGui::SetCursorPosX(8);
                        ImGui::PushItemWidth(150);
                        if (ImGui::ColorPicker3("##ColorPicker", (float*)&gui::clear_col, ImGuiColorEditFlags_NoInputs | ImGuiColorEditFlags_NoAlpha | ImGuiColorEditFlags_NoSidePreview))
                        {
                            Style->Colors[ImGuiCol_SliderGrab] = ImColor(gui::clear_col);
                            Style->Colors[ImGuiCol_SliderGrabActive] = ImColor(gui::clear_col);
                            Style->Colors[ImGuiCol_CheckMark] = ImColor(gui::clear_col);
                        }
                        ImGui::SetCursorPosX(8);
                        ImGui::Checkbox("Chroma", &gui::rgb);

                        ImGui::SetCursorPos(ImVec2(162, 8));
                        if (ImGui::Button("Discord", ImVec2(297, 33)))
                        {
                            ShellExecute(0, 0, "https://discord.gg/mFAFR3vh9f", 0, 0, SW_SHOW);
                        }
                    }
                    ImGui::EndChild();

                    ImGui::SetCursorPosX(8);
                    ImGui::BeginChild("##Destruct", ImVec2(((width - 16) - 150) - 16, 125), true, ImGuiWindowFlags_NoScrollbar);
                    {
                        if (ImGui::Button("Self Destruct", ImVec2(150, 30)))
                        {
                            client::destruct();
                        }

                        ImGui::Checkbox("Clean Strings", &client::cleanstrings);
                        ImGui::Checkbox("Self Delete", &client::selfdelete);
                    }
                    ImGui::EndChild();
                }
            }
            ImGui::EndChild();
        }
        ImGui::End();

        // Rendering
        ImGui::Render();
        const float clear_color_with_alpha[4] = { clear_color.x * clear_color.w, clear_color.y * clear_color.w, clear_color.z * clear_color.w, clear_color.w };
        g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
        g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, clear_color_with_alpha);
        ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());

        g_pSwapChain->Present(1, 0); // Present with vsync
        //g_pSwapChain->Present(0, 0); // Present without vsync
    }

    // Cleanup
    ImGui_ImplDX11_Shutdown();
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
    // Setup swap chain
    DXGI_SWAP_CHAIN_DESC sd;
    ZeroMemory(&sd, sizeof(sd));
    sd.BufferCount = 2;
    sd.BufferDesc.Width = 0;
    sd.BufferDesc.Height = 0;
    sd.BufferDesc.Format = DXGI_FORMAT_R8G8B8A8_UNORM;
    sd.BufferDesc.RefreshRate.Numerator = 60;
    sd.BufferDesc.RefreshRate.Denominator = 1;
    sd.Flags = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH;
    sd.BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
    sd.OutputWindow = hWnd;
    sd.SampleDesc.Count = 1;
    sd.SampleDesc.Quality = 0;
    sd.Windowed = TRUE;
    sd.SwapEffect = DXGI_SWAP_EFFECT_DISCARD;

    UINT createDeviceFlags = 0;
    //createDeviceFlags |= D3D11_CREATE_DEVICE_DEBUG;
    D3D_FEATURE_LEVEL featureLevel;
    const D3D_FEATURE_LEVEL featureLevelArray[2] = { D3D_FEATURE_LEVEL_11_0, D3D_FEATURE_LEVEL_10_0, };
    if (D3D11CreateDeviceAndSwapChain(NULL, D3D_DRIVER_TYPE_HARDWARE, NULL, createDeviceFlags, featureLevelArray, 2, D3D11_SDK_VERSION, &sd, &g_pSwapChain, &g_pd3dDevice, &featureLevel, &g_pd3dDeviceContext) != S_OK)
        return false;

    CreateRenderTarget();
    return true;
}

void CleanupDeviceD3D()
{
    CleanupRenderTarget();
    if (g_pSwapChain) { g_pSwapChain->Release(); g_pSwapChain = NULL; }
    if (g_pd3dDeviceContext) { g_pd3dDeviceContext->Release(); g_pd3dDeviceContext = NULL; }
    if (g_pd3dDevice) { g_pd3dDevice->Release(); g_pd3dDevice = NULL; }
}

void CreateRenderTarget()
{
    ID3D11Texture2D* pBackBuffer;
    g_pSwapChain->GetBuffer(0, IID_PPV_ARGS(&pBackBuffer));
    g_pd3dDevice->CreateRenderTargetView(pBackBuffer, NULL, &g_mainRenderTargetView);
    pBackBuffer->Release();
}

void CleanupRenderTarget()
{
    if (g_mainRenderTargetView) { g_mainRenderTargetView->Release(); g_mainRenderTargetView = NULL; }
}

// Forward declare message handler from imgui_impl_win32.cpp
extern IMGUI_IMPL_API LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

// Win32 message handler
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
    if (ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam))
        return true;

    switch (msg)
    {
    case WM_SIZE:
        if (g_pd3dDevice != NULL && wParam != SIZE_MINIMIZED)
        {
            CleanupRenderTarget();
            g_pSwapChain->ResizeBuffers(0, (UINT)LOWORD(lParam), (UINT)HIWORD(lParam), DXGI_FORMAT_UNKNOWN, 0);
            CreateRenderTarget();
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
