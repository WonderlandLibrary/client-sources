
//As the older source has been leaked, I will be giving this one away
//Clearly credits are needed as Epic doesn't know how to stay away from peoples work

/*
 * Credits to Syn for Epic pasting Paladin in Lithium
 * Credits to Diego for Koid source (This is like 85% Koid)
 * Credits to LevensLes for Cucklord Ghost (Only used Cucklord in earlier versions)
 * ALL CODE IS WRITTEN BY EPIC (Prism only modified some code so it worked better and added some stuff)
 */

#include "klynt.h"
void changecolor(float col[4]);

void replacestr(std::string& s, const std::string& search, const std::string& replace) {
    for (size_t pos = 0;; pos += replace.length()) {
        pos = s.find(search, pos);
        if (pos == std::string::npos) break;
        s.erase(pos, search.length());
        s.insert(pos, replace);
    }
}

int main() {
    SetConsoleTitle(L"sus");






    std::string dsaasddsasad = "lithium";

    if (IsDebuggerPresent()) {
        //exit(0);
    }


    v::info::verified = true;
    std::this_thread::sleep_for(std::chrono::seconds(3));


    v::info::minecraft_window = FindWindowA(xorstr("LWJGL"), nullptr);

    GetWindowThreadProcessId(v::info::minecraft_window, &v::info::minecraft_pid);

    v::info::minecraft_handle = OpenProcess(PROCESS_ALL_ACCESS, false, v::info::minecraft_pid);

    if (!v::info::minecraft_handle || !v::info::minecraft_window || !v::info::minecraft_pid) {
        std::cout << xorstr("ERROR - Failed to load Lithium") << std::endl;
        std::cout << xorstr("Minecraft not found") << std::endl;
        std::this_thread::sleep_for(std::chrono::seconds(3));

    }



    ShowWindow(GetConsoleWindow(), 0);
    RECT rect;
    int x = 500;
    int y = 300;
    if (GetWindowRect(GetDesktopWindow(), &rect)) {

        x = rect.right - rect.left;
        y = rect.bottom - rect.top;
    }

    const std::string title = "soos";
    const char* f = title.c_str();
    WNDCLASSEX wc = { sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(nullptr), nullptr, nullptr,
                     nullptr, nullptr, (LPCWSTR)f, nullptr };
    ::RegisterClassEx(&wc);
    v::gui::hwnd = ::CreateWindow(wc.lpszClassName, (LPCWSTR)f, WS_POPUP, x / 2 - 167, y / 2 - 89,
        250,250, nullptr, nullptr,
        wc.hInstance, nullptr);

    if (!CreateDeviceD3D(v::gui::hwnd)) {
        CleanupDeviceD3D();
        ::UnregisterClass(wc.lpszClassName, wc.hInstance);
        return 1;
    }

    const int width = 720;
    const int height = 530;

    ::SetWindowPos(v::gui::hwnd, NULL, v::utils::random_int(50, 500), v::utils::random_int(50, 500), width, height,
        SWP_FRAMECHANGED);

    ::ShowWindow(v::gui::hwnd, SW_SHOWDEFAULT);
    ::UpdateWindow(v::gui::hwnd);

    IMGUI_CHECKVERSION();
    ImGuiContext* cx = ImGui::CreateContext();
    ImGui::SetCurrentContext(cx);
    ImGuiIO& io = ImGui::GetIO();
    (void)io;
    auto oxygenregular = io.Fonts->AddFontFromMemoryCompressedTTF(roboto_compressed_data, 14, 14.f, NULL,
        io.Fonts->GetGlyphRangesDefault());

    ImFontConfig config;
    config.MergeMode = true;
    config.PixelSnapH = true;

    static const ImWchar ranges[] =
    {
            0xf000,
            0xf976,
            0
    };

    auto fa = io.Fonts->AddFontFromMemoryCompressedTTF(fa_compressed_data, fa_compressed_size, 15, &config, ranges);

    ImGui::StyleColorsDark();

    ImGui_ImplWin32_Init(v::gui::hwnd);
    ImGui_ImplDX11_Init(g_pd3dDevice, g_pd3dDeviceContext);
    ImVec4 clear_color = ImColor(0, 0, 0);
    if (!v::info::minecraft_handle || !v::info::minecraft_window || !v::info::minecraft_pid) {
        std::cout << xorstr("ERROR - Failed to load Lithium") << std::endl;
        std::cout << xorstr("Minecraft not found") << std::endl;
        std::this_thread::sleep_for(std::chrono::seconds(3));
        std::exit(EXIT_SUCCESS);
    }
    MSG msg;
    ZeroMemory(&msg, sizeof(msg));
    io.IniFilename = NULL;
    v::info::verified = true;
    v::launch::all_threads();
    std::string dasdassadjyughj = "lithium";
    bool exiting = false;
    while (msg.message != WM_QUIT) {
        if (::PeekMessage(&msg, NULL, 0U, 0U, PM_REMOVE)) {
            ::TranslateMessage(&msg);
            ::DispatchMessage(&msg);
            continue;
        }

        ImGui_ImplDX11_NewFrame();
        ImGui_ImplWin32_NewFrame();
        ImGui::NewFrame();

        const auto style = &ImGui::GetStyle();
        const auto colors = style->Colors;

        style->WindowBorderSize = 1.f;
        style->WindowRounding = 0.f;
        style->WindowTitleAlign = ImVec2(0.5, 0.5);
        style->FrameRounding = 0.f;
        style->GrabRounding = 0.f;
        style->ChildRounding = 0.f;
        style->ItemSpacing = ImVec2(5, 10);
        style->FramePadding = ImVec2(1, 1);
        style->PopupRounding = 3.f;

        //colors[ImGuiCol_Text] = ImColor(133, 130, 132);
        //colors[ImGuiCol_TextDisabled] = ImVec4(1.00f, 1.00f, 1.00f, 0.25f);
        //colors[ImGuiCol_WindowBg] = ImColor(28, 28, 28);
        //colors[ImGuiCol_ChildBg] = ImVec4(0.05f, 0.05f, 0.05f, 1.00f);
        //colors[ImGuiCol_PopupBg] = ImVec4(0.08f, 0.08f, 0.08f, 1.00f);

        colors[ImGuiCol_Border] = ImColor(51, 51, 51);
        colors[ImGuiCol_BorderShadow] = ImVec4(0.00f, 0.00f, 0.00f, 0.00f);

        colors[ImGuiCol_TitleBg] = ImVec4(0.09f, 0.09f, 0.09f, 1.00f);
        colors[ImGuiCol_TitleBgActive] = ImVec4(0.09f, 0.09f, 0.09f, 1.00f);
        colors[ImGuiCol_TitleBgCollapsed] = ImVec4(0.09f, 0.09f, 0.09f, 1.00f);

        colors[ImGuiCol_MenuBarBg] = ImVec4(0.08f, 0.08f, 0.08f, 0.00f);
        colors[ImGuiCol_ScrollbarBg] = ImVec4(0.15f, 0.15f, 0.15f, 0.00f);
        colors[ImGuiCol_ScrollbarGrab] = ImVec4(0.10f, 0.10f, 0.10f, 0.00f);
        colors[ImGuiCol_ScrollbarGrabHovered] = ImVec4(0.10f, 0.10f, 0.10f, 0.00f);
        colors[ImGuiCol_ScrollbarGrabActive] = ImVec4(0.10f, 0.10f, 0.15f, 0.00f);
        colors[ImGuiCol_CheckMark] = ImVec4(v::gui::color1);

        colors[ImGuiCol_SliderGrab] = ImVec4(v::gui::color1); //used for sliders
        colors[ImGuiCol_SliderGrabActive] = ImVec4(v::gui::color1); //used for sliders

        colors[ImGuiCol_Button] = ImColor(20, 20, 20);
        colors[ImGuiCol_ButtonHovered] = ImColor(20, 20, 20);
        colors[ImGuiCol_ButtonActive] = ImColor(20, 20, 20);

        colors[ImGuiCol_Header] = ImVec4(0.20f, 0.20f, 0.20f, 1.00f);
        colors[ImGuiCol_HeaderHovered] = ImVec4(0.13f, 0.13f, 0.13f, 1.00f);
        colors[ImGuiCol_HeaderActive] = ImVec4(0.12f, 0.12f, 0.12f, 1.00f);

        colors[ImGuiCol_Separator] = ImVec4(v::gui::color3);
        colors[ImGuiCol_SeparatorHovered] = ImVec4(v::gui::color3);
        colors[ImGuiCol_SeparatorActive] = ImVec4(v::gui::color3);

        colors[ImGuiCol_ResizeGrip] = ImVec4(0.30f, 0.30f, 0.30f, 1.00f);
        colors[ImGuiCol_ResizeGripActive] = ImVec4(0.09f, 0.09f, 0.09f, 1.00f);
        colors[ImGuiCol_PlotLines] = ImVec4(0.40f, 0.12f, 0.99f, 0.90f);
        colors[ImGuiCol_PlotLinesHovered] = ImVec4(0.40f, 0.12f, 0.99f, 0.90f);
        colors[ImGuiCol_PlotHistogram] = ImVec4(0.40f, 0.12f, 0.99f, 0.90f);
        colors[ImGuiCol_PlotHistogramHovered] = ImVec4(0.40f, 0.12f, 0.99f, 0.90f);
        colors[ImGuiCol_TextSelectedBg] = ImVec4(0.09f, 0.09f, 0.09f, 1.00f);

        colors[ImGuiCol_ResizeGripHovered] = ImVec4(0.00f, 0.00f, 0.00f, 1.05f);
        colors[ImGuiCol_DragDropTarget] = ImVec4(0.00f, 0.00f, 0.00f, 0.40f);

        colors[ImGuiCol_WindowBg] = ImColor(28, 28, 28);
        colors[ImGuiCol_TitleBgActive] = ImColor(20, 20, 20);
        colors[ImGuiCol_FrameBg] = ImColor(45, 45, 45);
        colors[ImGuiCol_FrameBgHovered] = ImColor(45, 45, 45);
        colors[ImGuiCol_FrameBgActive] = ImColor(45, 45, 45);
        colors[ImGuiCol_ChildBg] = ImColor(23, 23, 23);

        {
            ImGui::SetNextWindowSize(ImVec2(width, height));
            ImGui::SetNextWindowPos(ImVec2(0, 0));

            ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(0, 0));

            ImGui::Begin(("##main"), NULL,
                ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoMove |
                ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoScrollbar);

            if (!v::gui::item_being_used) {

                if (ImGui::IsItemActive()) {

                    v::gui::window_being_dragged = true;

                }
                else {

                    v::gui::window_being_dragged = false;

                }

            }

            ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(19, 120));

            ImGui::SetCursorPos(ImVec2(0, 0));
            ImGui::BeginChild("##tabs", ImVec2(75, height), true);
            {

                //ImGui::GetWindowDrawList()->AddRectFilled(ImVec2(0, 0), ImVec2(75, height), ImColor(23, 23, 23));

                ImGui::PopStyleVar();

                ImGui::NewLine();

                //ImGui::SetCursorPos(ImVec2(40, 35));

                ImGui::PushFont(fa);

                ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(23, 23, 23));
                ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(23, 23, 23));
                ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(23, 23, 23));

                if (v::gui::tab_main != 1) {
                    ImGui::Button(u8"\uf05b", ImVec2(40, 40), false);
                }
                else {
                    ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(v::gui::color1));
                    ImGui::Button(u8"\uf05b", ImVec2(40, 40), false);
                    ImGui::PopStyleColor();
                }
                if (ImGui::IsItemClicked()) {
                    v::gui::tab_main = 1;
                    v::gui::lithgui = 'A';
                }

                //ImGui::SetCursorPos(ImVec2(40, 125));

                if (v::gui::tab_main != 2) {
                    ImGui::Button(u8"\uf554", ImVec2(40, 40), false);
                }
                else {
                    ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(v::gui::color1));
                    ImGui::Button(u8"\uf554", ImVec2(40, 40), false);
                    ImGui::PopStyleColor();
                }
                if (ImGui::IsItemClicked()) {
                    v::gui::tab_main = 2;
                    v::gui::lithgui = 'B';
                }

                //ImGui::SetCursorPos(ImVec2(40, 215));

                if (v::gui::tab_main != 3) {
                    ImGui::Button(u8"\uf06e", ImVec2(40, 40), false);
                }
                else {
                    ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(v::gui::color1));
                    ImGui::Button(u8"\uf06e", ImVec2(40, 40), false);
                    ImGui::PopStyleColor();
                }
                if (ImGui::IsItemClicked()) {
                    v::gui::tab_main = 3;
                    v::gui::lithgui = 'C';
                }

                //ImGui::SetCursorPos(ImVec2(40, 305));

                if (v::gui::tab_main != 4) {
                    ImGui::Button(u8"\uf0c9", ImVec2(40, 40), false);
                }
                else {
                    ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(v::gui::color1));
                    ImGui::Button(u8"\uf0c9", ImVec2(40, 40), false);
                    ImGui::PopStyleColor();
                }
                if (ImGui::IsItemClicked()) {
                    v::gui::tab_main = 4;
                    v::gui::lithgui = 'D';
                }

                //ImGui::SetCursorPos(ImVec2(40, 400));

                if (v::gui::tab_main != 5) {
                    ImGui::Button(u8"\uf057", ImVec2(40, 40), false);
                }
                else {
                    ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(v::gui::color1));
                    ImGui::Button(u8"\uf057", ImVec2(40, 40), false);
                    ImGui::PopStyleColor();
                }
                if (ImGui::IsItemClicked()) {
                    v::gui::tab_main = 5;
                    v::gui::lithgui = 'E';
                }

                ImGui::PopStyleColor(3);

                ImGui::PopFont();

                ImGui::EndChild();
            }
            ImGui::PopStyleVar();
            ImGui::SetCursorPosY(15);
            ImGui::Spacing();
            ImGui::SameLine(90);
            switch (v::gui::lithgui) {
            case 'A': {
                ImGui::BeginChild(("##left_clicker_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Clicking for you"));
                    }
                    else {
                        ImGui::Text(("AutoClicker"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::clicker::left::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::clicker::left::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::clicker::left::bind_pressed = true;
                        v::modules::clicker::left::bind = "[...]";
                    }

                    ImGui::Text(("Average CPS"));
                    ImGui::SliderFloat(("##CPS"), &v::modules::clicker::left::cps, 5.f, 20.f, "%.1f");

                    ImGui::Text("Blockhit Delay");
                    ImGui::SliderFloat(("##Blockhit delay"), &v::modules::clicker::left::blockhit_delay, 500.f, 1000.f,
                        "%.0f");

                    ImGui::Checkbox(("Inventory"), &v::modules::clicker::left::inventory);
                    ImGui::Checkbox(("Blockhit"), &v::modules::clicker::left::blockhit);
                    ImGui::Checkbox(("RMB Lock"), &v::modules::clicker::left::rmb_lock);

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##right_clicker_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Clicking for you 2"));
                    }
                    else {
                        ImGui::Text(("AutoClicker RMB"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::clicker::right::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::clicker::right::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::clicker::right::bind_pressed = true;
                        v::modules::clicker::right::bind = "[...]";
                    }

                    ImGui::Text(("Average CPS"));

                    ImGui::SliderFloat(("##CPS"), &v::modules::clicker::right::cps, 5.f, 20.f, "%.1f");

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##reach_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Longer arms"));
                    }
                    else {
                        ImGui::Text(("Reach"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::reach::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::reach::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::reach::bind_pressed = true;
                        v::modules::reach::bind = "[...]";
                    }

                    if (v::modules::reach::minvalue > v::modules::reach::maxvalue) {

                        v::modules::reach::minvalue = v::modules::reach::maxvalue;

                    }

                    ImGui::Text(("Minimum Reach"));

                    ImGui::SliderFloat(("##Minimum"), &v::modules::reach::minvalue, 3.f, 6.f, "%.2f");

                    if (v::modules::reach::maxvalue < v::modules::reach::minvalue) {

                        v::modules::reach::maxvalue = v::modules::reach::minvalue;

                    }

                    ImGui::Text(("Maximum Reach"));

                    ImGui::SliderFloat(("##Maximum"), &v::modules::reach::maxvalue, 3.f, 6.f, "%.2f");

                    ImGui::Checkbox(("Only while moving"), &v::modules::reach::while_moving);

                    ImGui::EndChild();
                }

                ImGui::Spacing();
                ImGui::SameLine(90);

                ImGui::BeginChild(("##velocity_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Not taking Knockback"));
                    }
                    else {
                        ImGui::Text(("Velocity"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::velocity::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::velocity::bind.c_str());

                    if (ImGui::IsItemClicked()) {
                        v::modules::velocity::bind_pressed = true;
                        v::modules::velocity::bind = "[...]";
                    }

                    if (v::modules::velocity::min > v::modules::velocity::max)
                        v::modules::velocity::min = v::modules::velocity::max;

                    ImGui::Text(("Minimum Velocity"));

                    ImGui::SliderFloat(("##Minimum"), &v::modules::velocity::min, 1.f, 100.f, "%.2f");

                    if (v::modules::velocity::max < v::modules::velocity::min)
                        v::modules::velocity::max = v::modules::velocity::min;

                    ImGui::Text(("Maximum Velocity"));

                    ImGui::SliderFloat(("##Maximum"), &v::modules::velocity::max, 1.f, 100.f, "%.2f");

                    ImGui::Checkbox(("Only while moving"), &v::modules::velocity::while_moving);
                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##throwpot_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Throwing pots for you"));
                    }
                    else {
                        ImGui::Text(("Throwpot"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::throwpot::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::throwpot::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::throwpot::bind_pressed = true;
                        v::modules::throwpot::bind = "[...]";
                    }

                    ImGui::Text(("Potion Slots"));

                    ImGui::PushStyleVar(ImGuiStyleVar_ItemSpacing, ImVec2(5, 1));

                    for (int i = 0; i < 9; i++) {

                        ImGui::Checkbox(std::string("##slot" + std::to_string(i)).c_str(),
                            &v::modules::throwpot::slot[i]);

                        ImGui::SameLine();
                    }

                    ImGui::PopStyleVar();

                    ImGui::NewLine();

                    ImGui::Text("Throw Delay");

                    ImGui::SliderFloat(("##Delay"), &v::modules::throwpot::throw_delay, 100.f, 500.f, "%.0f");

                    ImGui::Text("Return Slot");

                    ImGui::SliderFloat(("##Return"), &v::modules::throwpot::return_pos, 1.f, 9.f, "%.0f");

                    ImGui::Checkbox(("Drop item"), &v::modules::throwpot::throw_item);

                    ImGui::EndChild();
                }
            }
                    break;
            case 'B': {
                ImGui::BeginChild(("##timer_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Faster game"));
                    }
                    else {
                        ImGui::Text(("Timer"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::timer::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::timer::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::timer::bind_pressed = true;
                        v::modules::timer::bind = "[...]";
                    }

                    ImGui::Text("Multiplier");

                    ImGui::SliderFloat(("##MultiplierTimer"), &v::modules::timer::multiplier, 1.f, 5.f, "%.2f");

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##bhop_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Faster moving"));
                    }
                    else {
                        ImGui::Text(("BHop"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::bhop::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::bhop::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::bhop::bind_pressed = true;
                        v::modules::bhop::bind = "[...]";
                    }

                    ImGui::Text("Multiplier");

                    ImGui::SliderFloat(("##MultiplierBHop"), &v::modules::bhop::speed, 1.f, 100.f, "%.2f");

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##speed_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Moving faster not hopping"));
                    }
                    else {
                        ImGui::Text(("Speed"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::speed::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::speed::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::speed::bind_pressed = true;
                        v::modules::speed::bind = "[...]";
                    }

                    ImGui::Text("Multiplier");

                    ImGui::SliderFloat(("##MultiplierSpeed"), &v::modules::speed::multiplier, 1.f, 20.f, "%.2f");

                    ImGui::EndChild();
                }

                ImGui::Spacing();
                ImGui::SameLine(90);

                ImGui::BeginChild(("##fly_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Glide"));
                    }
                    else {
                        ImGui::Text(("Fly"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::fly::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::fly::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::fly::bind_pressed = true;
                        v::modules::fly::bind = "[...]";
                    }

                    ImGui::EndChild();
                }

                //ImGui::SameLine(90);
                //ImGui::Spacing();

                ImGui::SetCursorPos(ImVec2(295, 265));
                ImGui::BeginChild(("##antiafk_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Lazy asf"));
                    }
                    else {
                        ImGui::Text(("AntiAFK"));
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::antiafk::enabled);

                    ImGui::EndChild();
                }
            }
                    break;
            case 'C': {
                ImGui::BeginChild(("##esp_child"), ImVec2(200, 240), true);
                {
                    ImGui::Text(("PlayerESP"));

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::esp::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::esp::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::esp::bind_pressed = true;
                        v::modules::esp::bind = "[...]";
                    }

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##cavefinder_child"), ImVec2(200, 240), true);
                {
                    ImGui::Text(("CaveFinder"));

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::cavefinder::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::cavefinder::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::cavefinder::bind_pressed = true;
                        v::modules::cavefinder::bind = "[...]";
                    }

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##nametags_child"), ImVec2(200, 240), true);
                {
                    ImGui::Text(("Nametags"));

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Checkbox(("Enabled"), &v::modules::nametags::enabled);

                    ImGui::SameLine();
                    ImGui::TextDisabled(v::modules::nametags::bind.c_str());

                    if (ImGui::IsItemClicked()) {

                        v::modules::nametags::bind_pressed = true;
                        v::modules::nametags::bind = "[...]";
                    }

                    ImGui::Text("Multiplier");

                    ImGui::SliderFloat(("##MultiplierNametags"), &v::modules::nametags::multiplier, 1.f, 3.f, "%.2f");

                    ImGui::EndChild();
                }
            }
                    break;
            case 'D': {
                ImGui::BeginChild(("##gui_child"), ImVec2(200, 220), true);
                {
                    ImGui::Text(("GUI"));

                    ImGui::PushItemWidth(170);

                    ImGui::ColorPicker4(("##colorpicker"), (float*)&v::gui::color1,
                        ImGuiColorEditFlags_NoSidePreview | ImGuiColorEditFlags_NoSmallPreview |
                        ImGuiColorEditFlags_NoInputs);

                    ImGui::Checkbox(("Rainbow mode"), &v::gui::rainbow_mode);

                    ImGui::EndChild();
                }

                ImGui::SameLine();

                ImGui::BeginChild(("##info_child"), ImVec2(200, 220), true);
                {
                    ImGui::PushItemWidth(170);

                    ImGui::Checkbox(("Use Memes: "), &v::gui::memeversion);

                    std::string sdahjdsa = xorstr("Version: ") + v::info::version;
                    ImGui::Text(sdahjdsa.c_str());

                    ImGui::Text("Detected: false (3/20/2021)");
                    ImGui::Text("Current users online: {0}");

                    ImGui::EndChild();
                }
            }
                    break;
            case 'E': {
                ImGui::BeginChild(("##destruct_child"), ImVec2(200, 240), true);
                {
                    if (v::gui::memeversion) {
                        ImGui::Text(("Self-Destruct"));
                    }
                    else {
                        ImGui::Text(("Self-Destruct"));
                    }
                    ImGui::Checkbox(("Clean PcaClient"), &v::modules::destruct::pcaclean);
                    if (v::gui::memeversion) {
                        ImGui::Checkbox(("Leave a lot of detections"), &v::modules::destruct::traceless);
                    }
                    else {
                        ImGui::Checkbox(("Traceless"), &v::modules::destruct::traceless);
                    }

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    if (ImGui::Button("Begin", ImVec2(90, 22))) {
                        v::modules::destruct::execute();
                    }

                    ImGui::SameLine();

                    ImGui::Button(v::modules::destruct::bind.c_str(), ImVec2(90, 22));

                    if (ImGui::IsItemClicked()) {

                        v::modules::destruct::bind_pressed = true;
                        v::modules::destruct::bind = "[...]";
                    }

                    ImGui::EndChild();
                }

                ImGui::Spacing();
                ImGui::SameLine(90);

                ImGui::BeginChild(("##hide_child"), ImVec2(200, 240), true);
                {
                    ImGui::Text(("Hide"));

                    ImGui::PushItemWidth(185);

                    //ImGui::Spacing();

                    ImGui::Button(v::modules::hide::bind.c_str(), ImVec2(185, 22));

                    if (ImGui::IsItemClicked()) {

                        v::modules::hide::bind_pressed = true;
                        v::modules::hide::bind = "[...]";
                    }

                    ImGui::EndChild();
                }
            }
                    break;
            }
            ImGui::End();
            if (!v::gui::item_being_used) {

                if (!v::gui::window_being_dragged) {
                    std::this_thread::sleep_for(std::chrono::milliseconds(5));
                }
            }
            else {
                std::this_thread::sleep_for(std::chrono::milliseconds(1));
            }

            changecolor((float*)&v::gui::color1);
            v::gui::item_being_used = false;
        }
        ImGui::EndFrame();
        ImGui::Render();

        g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
        g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, (float*)&clear_color);
        ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());

        g_pSwapChain->Present(0, 0); // Present without vsync

    }

        ImGui_ImplDX11_Shutdown();
        ImGui_ImplWin32_Shutdown();
        ImGui::DestroyContext();

        CleanupDeviceD3D();
        ::DestroyWindow(v::gui::hwnd);
        ::UnregisterClass(wc.lpszClassName, wc.hInstance);

        v::modules::destruct::execute();

        return 0;
    
}
bool CreateDeviceD3D(HWND
    hWnd) {

    DXGI_SWAP_CHAIN_DESC sd;
    ZeroMemory(&sd,
        sizeof(sd));
    sd.
        BufferCount = 2;
    sd.BufferDesc.
        Width = 0;
    sd.BufferDesc.
        Height = 0;
    sd.BufferDesc.
        Format = DXGI_FORMAT_R8G8B8A8_UNORM;
    sd.BufferDesc.RefreshRate.
        Numerator = 60;
    sd.BufferDesc.RefreshRate.
        Denominator = 1;
    sd.
        Flags = DXGI_SWAP_CHAIN_FLAG_ALLOW_MODE_SWITCH;
    sd.
        BufferUsage = DXGI_USAGE_RENDER_TARGET_OUTPUT;
    sd.
        OutputWindow = hWnd;
    sd.SampleDesc.
        Count = 1;
    sd.SampleDesc.
        Quality = 0;
    sd.
        Windowed = TRUE;
    sd.
        SwapEffect = DXGI_SWAP_EFFECT_DISCARD;

    UINT createDeviceFlags = 0;
    D3D_FEATURE_LEVEL featureLevel;
    const D3D_FEATURE_LEVEL featureLevelArray[2] = { D3D_FEATURE_LEVEL_11_0, D3D_FEATURE_LEVEL_10_0, };
    if (
        D3D11CreateDeviceAndSwapChain(NULL, D3D_DRIVER_TYPE_HARDWARE, NULL, createDeviceFlags, featureLevelArray,
            2,
            D3D11_SDK_VERSION, &sd, &g_pSwapChain, &g_pd3dDevice, &featureLevel,
            &g_pd3dDeviceContext) != S_OK)
        return false;

    CreateRenderTarget();

    return true;

}

void CleanupDeviceD3D() {

    CleanupRenderTarget();
    if (g_pSwapChain) {
        g_pSwapChain->Release();
        g_pSwapChain = NULL;
    }
    if (g_pd3dDeviceContext) {
        g_pd3dDeviceContext->Release();
        g_pd3dDeviceContext = NULL;
    }
    if (g_pd3dDevice) {
        g_pd3dDevice->Release();
        g_pd3dDevice = NULL;
    }

}

void CreateRenderTarget() {

    ID3D11Texture2D* pBackBuffer;
    g_pSwapChain->GetBuffer(0, IID_PPV_ARGS(&pBackBuffer));
    g_pd3dDevice->CreateRenderTargetView(pBackBuffer, NULL, &g_mainRenderTargetView);
    pBackBuffer->Release();

}

void CleanupRenderTarget() {

    if (g_mainRenderTargetView) {
        g_mainRenderTargetView->Release();
        g_mainRenderTargetView = NULL;
    }

}

extern IMGUI_IMPL_API LRESULT
ImGui_ImplWin32_WndProcHandler(HWND
    hWnd,
    UINT msg, WPARAM
    wParam,
    LPARAM lParam
);

LRESULT WINAPI
WndProc(HWND
    hWnd,
    UINT msg, WPARAM
    wParam,
    LPARAM lParam
) {

    if (
        ImGui_ImplWin32_WndProcHandler(hWnd, msg, wParam, lParam
        ))
        return true;

    switch (msg) {
    case WM_SIZE:
        if (g_pd3dDevice !=
            NULL && wParam
            != SIZE_MINIMIZED) {
            CleanupRenderTarget();

            g_pSwapChain->ResizeBuffers(0, (UINT)
                LOWORD(lParam), (UINT)
                HIWORD(lParam), DXGI_FORMAT_UNKNOWN,
                0);

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

    return
        ::DefWindowProc(hWnd, msg, wParam, lParam
        );

}

void changecolor(float col[4]) {

    if (v::gui::rainbow_mode) {

        float add = 0.04f;

        if (v::gui::item_being_used) {

            add = 0.003f;

        }

        if (v::gui::window_being_dragged) {

            add = 0.0003;

        }

        static size_t turn = 0;

        if (turn == 0) {

            col[0] += add;

            if (col[0] >= 1.00f) {

                col[0] = 1.00f;
                turn++;

            }

        }

        if (turn == 1) {

            col[1] += add;

            if (col[1] >= 1.00f) {

                col[1] = 1.00f;
                turn++;

            }

        }

        if (turn == 2) {

            col[0] -= add;

            if (col[0] <= 0.00f) {

                col[0] = 0.00f;
                turn++;

            }

        }

        if (turn == 3) {

            col[2] += add;

            if (col[2] >= 1.00f) {

                col[2] = 1.00f;
                turn++;

            }

        }

        if (turn == 4) {

            col[1] -= add;

            if (col[1] <= 0.00f) {

                col[1] = 0.00f;
                turn++;

            }

        }

        if (turn == 5) {

            col[0] += add;

            if (col[0] >= 1.00f) {

                col[0] = 1.00f;
                turn++;

            }

        }

        if (turn == 6) {

            col[2] -= add;

            if (col[2] <= 0.00f) {

                col[2] = 0.00f;
                turn = 0;

            }

        }
    }

    v::gui::color2 = ImVec4(col[0], col[1], col[2], col[3] - 0.40f);

}

void v::gui::move_window() {
    while (v::info::verified) {
        if (v::gui::window_being_dragged) {
            if (!v::gui::item_being_used) {
                POINT t;
                if (GetCursorPos(&t)) {
                    if (HWND window = WindowFromPoint(t)) {
                        if (ScreenToClient(window, &t)) {
                            while (v::gui::window_being_dragged) {
                                POINT p;
                                if (GetCursorPos(&p)) {
                                    if (v::info::verified) {
                                        SetWindowPos(v::gui::hwnd, NULL, p.x - t.x, p.y - t.y, 720, 530,
                                            SWP_FRAMECHANGED);
                                    }
                                }
                                std::this_thread::sleep_for(std::chrono::milliseconds(1));
                            }
                        }
                    }
                }
            }
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}

void v::launch::all_threads() {
    HANDLE one = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::gui::move_window,
        nullptr, 0, nullptr);
    HANDLE two = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::scanner::thread,
        nullptr, 0, nullptr);
    HANDLE three = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::status::thread,
        nullptr, 0, 0);
    HANDLE four = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::binds::thread,
        nullptr, 0, 0);
    HANDLE five = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::clicker::left::thread,
        nullptr, 0, nullptr);
    HANDLE six = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::clicker::left::jitter::thread,
        nullptr, 0, nullptr);
    HANDLE seven = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::clicker::right::thread,
        nullptr, 0, nullptr);
    HANDLE eight = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::reach::thread,
        nullptr, 0, nullptr);
    HANDLE nine = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::throwpot::thread,
        nullptr, 0, nullptr);
    HANDLE ten = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::fly::thread,
        nullptr, 0, nullptr);
    HANDLE one2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::nametags::thread,
        nullptr, 0, nullptr);
    HANDLE two2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::esp::thread,
        nullptr, 0, nullptr);
    HANDLE three2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::cavefinder::thread,
        nullptr, 0, nullptr);
    HANDLE four2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::timer::thread,
        nullptr, 0, nullptr);
    HANDLE five2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::bhop::thread,
        nullptr, 0, nullptr);
    HANDLE six2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::antiafk::thread,
        nullptr, 0, nullptr);
    HANDLE seven2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::velocity::thread,
        nullptr, 0, nullptr);
    HANDLE eight2 = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&v::modules::speed::thread,
        nullptr, 0, nullptr);
    CloseHandle(one);
    CloseHandle(two);
    CloseHandle(three);
    CloseHandle(four);
    CloseHandle(five);
    CloseHandle(six);
    CloseHandle(seven);
    CloseHandle(eight);
    CloseHandle(nine);
    CloseHandle(ten);
    CloseHandle(one2);
    CloseHandle(two2);
    CloseHandle(three2);
    CloseHandle(four2);
    CloseHandle(five2);
    CloseHandle(six2);
    CloseHandle(seven2);
    CloseHandle(eight2);
}
