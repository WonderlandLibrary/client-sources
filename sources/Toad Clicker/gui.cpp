#include "pch.h"
#include "Application.h"
#include "toad.h"
#include "imgui.h"
#include <imgui_internal.h>

//#define MAIN_COL ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f);

ImDrawList* draw;

int tab                 = 0;
int configTabBar        = 0;
int k                   = 0;
int selected_item       = 0;

bool showProcessList    = false;
bool showSoundsList     = false;
bool binding            = false;

std::once_flag flag;

void decorations() {
    ImGuiStyle* style = &ImGui::GetStyle();
    
    float h = 0, s = 0, v = 0;
    ImGui::ColorConvertRGBtoHSV(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], h, s, v);
    ImGui::ColorConvertHSVtoRGB(h, s, v - 0.1f, toad::theme::main_col_dark[0], toad::theme::main_col_dark[1], toad::theme::main_col_dark[2]);
    ImGui::ColorConvertHSVtoRGB(h, s, v - 0.2f, toad::theme::main_col_darker[0], toad::theme::main_col_darker[1], toad::theme::main_col_darker[2]);
    ImGui::ColorConvertHSVtoRGB(h, s, v + 0.1f, toad::theme::main_col_light[0], toad::theme::main_col_light[1], toad::theme::main_col_light[2]);

    style->FramePadding.y = 2.5f;
    style->TabRounding = 0;

    style->Colors[ImGuiCol_Text] = ImColor(188, 188, 188);

    style->Colors[ImGuiCol_ChildBg] = ImColor(20, 20, 20);
    style->Colors[ImGuiCol_Border] = ImColor(36, 36, 36);

    style->Colors[ImGuiCol_CheckMark] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f);
    style->Colors[ImGuiCol_SliderGrab] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f);
    style->Colors[ImGuiCol_SliderGrabActive] = ImColor(0, 60, 12);
    
    style->Colors[ImGuiCol_Button] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f);
   
    //style->Colors[ImGuiCol_ButtonHovered] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1] - 0.12f, toad::theme::main_col[2] - 0.5f, 1.f);
    style->Colors[ImGuiCol_ButtonHovered] = ImVec4(toad::theme::main_col_dark[0], toad::theme::main_col_dark[1], toad::theme::main_col_dark[2], 1.f);
    //style->Colors[ImGuiCol_ButtonActive] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1] - 0.10f, toad::theme::main_col[2], 1.f - 0.20f);
    style->Colors[ImGuiCol_ButtonActive] = ImVec4(toad::theme::main_col_darker[0], toad::theme::main_col_darker[1], toad::theme::main_col_darker[2], 1.f - 0.20f);

    style->Colors[ImGuiCol_FrameBg] = ImColor(16, 16, 16);
    style->Colors[ImGuiCol_FrameBgActive] = ImColor(16, 16, 16);
    style->Colors[ImGuiCol_FrameBgHovered] = ImColor(16, 16, 16);

    style->Colors[ImGuiCol_PopupBg] = ImColor(16, 16, 16);
    style->Colors[ImGuiCol_HeaderHovered] = ImColor(0, 0, 0, 0);

    style->Colors[ImGuiCol_Tab] = ImColor(36, 36, 36);
    style->Colors[ImGuiCol_TabHovered] = ImVec4(toad::theme::main_col_light[0], toad::theme::main_col_light[1], toad::theme::main_col_light[2], 1);

    style->Colors[ImGuiCol_TabActive] = ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f);

    style->Colors[ImGuiCol_Header] = ImVec4(0.04f, 0.04f, 0.04f, 1.00f);
	style->Colors[ImGuiCol_HeaderHovered] = ImVec4(0.04f, 0.04f, 0.04f, 1.00f);
	style->Colors[ImGuiCol_HeaderActive] = ImVec4(0.02f, 0.02f, 0.02f, 1.00f);

	style->Colors[ImGuiCol_TitleBg] = ImVec4(0.10f, 0.09f, 0.12f, 1.00f);
	style->Colors[ImGuiCol_TitleBgCollapsed] = ImVec4(1.00f, 0.98f, 0.95f, 0.75f);
	style->Colors[ImGuiCol_TitleBgActive] = ImVec4(0.07f, 0.07f, 0.09f, 1.00f);
}

// handles all hotkey presses
void toad::hotkey_handler(const HWND& window) {
    if (!binding) {
        //misc Hide and Unhide
        if ((GetAsyncKeyState(toad::misc::keycode) & 1) && (k == 0))
        {
            if (toad::misc::window_hidden) { toad::misc::show(window); toad::misc::window_hidden = false; }
            else if (!toad::misc::window_hidden) { toad::misc::hide(window); toad::misc::window_hidden = true; }
            k = 1;
        }
        else if (GetAsyncKeyState(toad::misc::keycode) == 0) k = 0;

        //Clicker L&R
        switch (toad::clicker::selectedEnableOption)
        {
        case 2:
        case 0:
            if (GetAsyncKeyState(toad::clicker::keycode) & 1) {
                if (toad::misc::beep_on_toggle) Beep(350, 100);
                toad::clicker::enabled = !toad::clicker::enabled;
            }
            break;
        default:
            break;
        }
        switch (toad::clicker::r::right_selectedEnableOption)
        {
        case 2:
        case 0:
            if (GetAsyncKeyState(toad::clicker::r::right_keycode) & 1) {
                if (toad::misc::beep_on_toggle) Beep(350, 100);
                toad::clicker::r::right_enabled = !toad::clicker::r::right_enabled;
            }
            break;
        default:
            break;
        }  

        //click recorder 
        if (GetAsyncKeyState(toad::clickrecorder::keycode) & 1) {
            if (toad::clickrecorder::enabled)
            {
                if (toad::clickrecorder::auto_unbind)
                {
                    toad::clickrecorder::keycode = 0;
                    toad::clickrecorder::key = "none";
                }
                toad::clickrecorder::click_start_point = 0;
                toad::clickrecorder::record_status = toad::clickrecorder::recordStatus::NOT_RECORDING;
            }
            else
            {
                toad::clickrecorder::total_clicks = 0;
                toad::clickrecorder::record_status = toad::clickrecorder::recordStatus::AWAITING_FOR_CLICK;
                p_clickRecorder.get()->reset();
            }

            toad::clickrecorder::enabled = !toad::clickrecorder::enabled;
        }

        //click playback
        if (GetAsyncKeyState(toad::clickrecorder::keycode_playback) & 1 && !toad::clickrecorder::click_delays.empty())
        {
            if (!toad::clickplayback_thread_exists) 
            {
                p_clickRecorder.get()->init_playback_thread();
            }

            toad::clickrecorder::playback_enabled = !toad::clickrecorder::playback_enabled;
        }

        if (GetAsyncKeyState(toad::double_clicker::keycode) & 1)
        {
            toad::double_clicker::enabled = !toad::double_clicker::enabled;

            if (toad::double_clicker::enabled)
            {
                log_debug("starting thread");
                p_doubleClicker->start_thread();
            }
            else
            {
                log_debug("stopping thread");
                p_doubleClicker->stop_thread();
            }
        }
    }

    // when binding to a button
    else if (binding) {
        // (1)lmb - (123)f12
        for (int i = 3; i < 123; i++) {
            if (toad::clicker::key == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::clicker::key = "none"; binding = false; toad::clicker::keycode = 0; }
                    else { toad::clicker::key = toad::keys[i - 1]; toad::clicker::keycode = i; }
                }
                if (toad::clicker::key != "..") binding = false;
            }
            else if (toad::clicker::r::right_key == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::clicker::r::right_key = "none"; binding = false; toad::clicker::r::right_keycode = 0; }
                    else { toad::clicker::r::right_key = toad::keys[i - 1]; toad::clicker::r::right_keycode = i; }
                }
                if (toad::clicker::r::right_key != "..") binding = false;
            }
            else if (toad::misc::hide_key == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::misc::hide_key = "none"; binding = false; toad::misc::keycode = 0; }
                    else { toad::misc::hide_key = toad::keys[i - 1]; toad::misc::keycode = i; }
                }
                if (toad::misc::hide_key != "..") binding = false;
            }
            else if (toad::clickrecorder::key == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::clickrecorder::key = "none"; binding = false; toad::clickrecorder::keycode = 0; }
                    else { toad::clickrecorder::key = toad::keys[i - 1]; toad::clickrecorder::keycode = i; }
                }
                if (toad::clickrecorder::key != "..") binding = false;
            }
            else if (toad::clickrecorder::key_playback == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::clickrecorder::key_playback = "none"; binding = false; toad::clickrecorder::keycode_playback = 0; }
                    else { toad::clickrecorder::key_playback = toad::keys[i - 1]; toad::clickrecorder::keycode_playback = i; }
                }
                if (toad::clickrecorder::key_playback != "..") binding = false;
            }
            else if (toad::double_clicker::key == "..") {
                if (GetAsyncKeyState(i) & 0x8000) {
                    if (i == VK_ESCAPE) { toad::double_clicker::key = "none"; binding = false; toad::double_clicker::keycode = 0; }
                    else { toad::double_clicker::key = toad::keys[i - 1]; toad::double_clicker::keycode = i; }
                }
                if (toad::double_clicker::key != "..") binding = false;
            }
        }
    }
}

void toad::renderUI(const HWND& hwnd) {

    toad::hotkey_handler(hwnd);

    ImGui::SetNextWindowSize(ImVec2(500, WINDOW_HEIGHT));
    ImGui::Begin("Toad", nullptr, ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoScrollWithMouse | ImGuiWindowFlags_NoNavInputs);

    decorations();

    ImGui::TextColored(ImVec4(toad::theme::main_col[0], toad::theme::main_col[1], toad::theme::main_col[2], 1.f), "toad");
    ImGui::SameLine();
    ImGui::TextColored(ImVec4(0.2f, 0.2f, 0.2f, 1), "minecraft");
    ImGui::SameLine();
    ImGui::TextColored(ImVec4(0.2f, 0.2f, 0.2f, 1), toad::APP_VER);
#ifdef _DEBUG
    ImGui::SameLine();
    ImGui::TextColored(ImVec4(0.7f, 0.7f, 0.7f, 1), "Debug Mode");
#endif
    if (ImGui::BeginTabBar("##tabbar"))
    {
        if (ImGui::BeginTabItem("  clicker  ", false))
        {
            tab = 0;
            ImGui::EndTabItem();
        }
        if (ImGui::BeginTabItem("  configs  ", false))
        {
            if (toad::misc::ConfigList.empty())
                toad::misc::ConfigList = toad::misc::GetAllToadConfigs(toad::misc::exePath);

            else
            {
                toad::misc::ConfigList.clear();
                toad::misc::ConfigList = toad::misc::GetAllToadConfigs(toad::misc::exePath);
            }

            tab = 1;
            ImGui::EndTabItem();
        }
        if (ImGui::BeginTabItem("  recorder  ", false))
        {
            tab = 2;
            ImGui::EndTabItem();
        } 
        if (ImGui::BeginTabItem("   misc    ", false))
        {
            tab = 3;
            ImGui::EndTabItem();
        }
        ImGui::EndTabBar();
    }

    ImGui::SetCursorPosX(20);
    ImGui::SetCursorPosY(60);

    //clicker
    if (tab == 0) {
        //LEFT CLICKER
        ImGui::BeginChild("left", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 300), true);

        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("left").x + 10);
        ImGui::TextColored(ImVec4(0.47f, 0.47f, 0.47f, 1.f), "left");

        ImGui::Separator();

        ImGui::Checkbox("enable", &toad::clicker::enabled); ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::clicker::key);
        if (ImGui::IsItemClicked()) { toad::clicker::key = ".."; binding = true; }

        ImGui::Combo("##EnableOptions", &toad::clicker::selectedEnableOption, toad::clicker::enable_options_c, IM_ARRAYSIZE(toad::clicker::enable_options_c));
        ImGui::Spacing();

        ImGui::Text("min");
        if (toad::clicker::blatant_mode) ImGui::SliderInt("##Min", &toad::clicker::mincps, 5, 100, "%dcps");
        else ImGui::SliderInt("##Min", &toad::clicker::mincps, 5, 20, "%dcps");

        ImGui::Spacing();

        ImGui::Text("max");

        if (toad::clicker::blatant_mode) ImGui::SliderInt("##Max", &toad::clicker::maxcps, 5, 100, "%dcps");
        else ImGui::SliderInt("##Max", &toad::clicker::maxcps, 5, 20, "%dcps");

        ImGui::Checkbox("rmb lock", &toad::clicker::rmb_lock);
        ImGui::Checkbox("inventory", &toad::clicker::inventory);
        ImGui::Checkbox("blatant mode", &toad::clicker::blatant_mode);
        if (ImGui::IsItemClicked())
        {
            if (toad::clicker::maxcps > 20) toad::clicker::maxcps = 20;
            if (toad::clicker::mincps > 20) toad::clicker::mincps = 20;
        }

        ImGui::Checkbox("prioritize higher cps", &toad::clicker::higher_cps);

        if (!toad::optionsFound)
        {
            ImGui::PushItemFlag(ImGuiItemFlags_Disabled, true);
            ImGui::PushStyleVar(ImGuiStyleVar_Alpha, ImGui::GetStyle().Alpha / 2);
        }
        ImGui::PushStyleVar(ImGuiStyleVar_ItemSpacing, ImVec2(1.f, 0.5f));
        ImGui::Checkbox("slot whitelist", &toad::clicker::slot_whitelist);
        if (toad::clicker::slot_whitelist) {
            ImGui::PushStyleVar(ImGuiStyleVar_ItemSpacing, ImVec2(0.7f, 1.f));
            ImGui::PushStyleVar(ImGuiStyleVar_FramePadding, ImVec2(1.2f, 3.f));
            ImGui::SetCursorPosX(10);
            ImGui::Checkbox("##slot1", &toad::clicker::whitelisted_slots[0]); ImGui::SameLine();
            ImGui::Checkbox("##slot2", &toad::clicker::whitelisted_slots[1]); ImGui::SameLine();
            ImGui::Checkbox("##slot3", &toad::clicker::whitelisted_slots[2]); ImGui::SameLine();
            ImGui::Checkbox("##slot4", &toad::clicker::whitelisted_slots[3]); ImGui::SameLine();
            ImGui::Checkbox("##slot5", &toad::clicker::whitelisted_slots[4]); ImGui::SameLine();
            ImGui::Checkbox("##slot6", &toad::clicker::whitelisted_slots[5]); ImGui::SameLine();
            ImGui::Checkbox("##slot7", &toad::clicker::whitelisted_slots[6]); ImGui::SameLine();
            ImGui::Checkbox("##slot8", &toad::clicker::whitelisted_slots[7]); ImGui::SameLine();
            ImGui::Checkbox("##slot9", &toad::clicker::whitelisted_slots[8]);
            ImGui::PopStyleVar();
            ImGui::PopStyleVar();
        }

        ImGui::PopStyleVar();

        if (!toad::optionsFound)
        {
            ImGui::PopItemFlag();
            ImGui::PopStyleVar();
        }

        if (toad::clicker::mincps > toad::clicker::maxcps) toad::clicker::mincps = toad::clicker::maxcps;
        if (toad::clicker::r::right_mincps > toad::clicker::r::right_maxcps) toad::clicker::r::right_mincps = toad::clicker::r::right_maxcps;

        ImGui::EndChild();

        ImGui::SameLine();

        //RIGHT CLICKER
        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::BeginChild("right", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 177), true);

        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("right").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "right");

        ImGui::Separator();

        ImGui::Checkbox("##Enable right", &toad::clicker::r::right_enabled); ImGui::SameLine(); ImGui::Text("enable"); ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::clicker::r::right_key);
        if (ImGui::IsItemClicked()) { toad::clicker::r::right_key = ".."; binding = true; }

        ImGui::Combo("##EnableOptionsRight", &toad::clicker::r::right_selectedEnableOption, toad::clicker::r::right_enableOptions_c, IM_ARRAYSIZE(toad::clicker::r::right_enableOptions_c));

        ImGui::Text("min");
        ImGui::SliderInt("##Min right", &toad::clicker::r::right_mincps, 5, 30, "%dcps");
        ImGui::Text("max");
        ImGui::SliderInt("##Max right", &toad::clicker::r::right_maxcps, 5, 30, "%dcps");
        ImGui::Checkbox("##Inventory right", &toad::clicker::r::right_inventory); ImGui::SameLine(); ImGui::Text("inventory");
        ImGui::Checkbox("##Only Inventory right", &toad::clicker::r::right_only_inventory); ImGui::SameLine(); ImGui::Text("only inventory");
        if (!toad::clicker::r::right_inventory && toad::clicker::r::right_only_inventory) toad::clicker::r::right_inventory = true;

        ImGui::EndChild();

        //CLICKSOUNDS & DOUBLECLICKER
        static int clickertabmisc = 0;
        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::SetCursorPosY(247);
        ImGui::BeginChild("##ClickerExtraOptions", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 113), true);

        if (ImGui::BeginTabBar("##ClickerOthers"))
        {
            if (ImGui::BeginTabItem("clicksounds")) { clickertabmisc = 0; ImGui::EndTabItem(); }
            if (ImGui::BeginTabItem("double clicker")) { clickertabmisc = 1; ImGui::EndTabItem(); }
            ImGui::EndTabBar();
        }
        // click sounds
        if (clickertabmisc == 0)
        {
            ImGui::Checkbox("enabled", &toad::misc::clicksounds);

            if (ImGui::Button("Select")) {
                toad::misc::soundslist.clear();
                toad::misc::soundslist = toad::getAllFilesExt(toad::misc::exePath, ".wav", true);
                showSoundsList = !showSoundsList;
            }
            ImGui::SameLine();
            ImGui::Text("%s", toad::misc::currclicksoundstr.c_str());
        }
        // double clicker
        else
        {
            if (ImGui::Checkbox("enabled", &toad::double_clicker::enabled))
            {
                if (toad::double_clicker::enabled)
                {
                    log_debug("starting thread");
                    p_doubleClicker->start_thread();
                }
                else
                {
                    log_debug("stopping thread");
                    p_doubleClicker->stop_thread();
                }
            }
            ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::double_clicker::key);
            if (ImGui::IsItemClicked()) { toad::double_clicker::key = ".."; binding = true; }
            ImGui::Text("delay");
            ImGui::SliderInt("##delay", &toad::double_clicker::delay, 0, 200, "%dms");
            ImGui::Text("chance");
            ImGui::SliderInt("##chance", &toad::double_clicker::chance, 1, 100, "%d%%");
        }
        ImGui::EndChild();
    }
  
    //configs
    else if (tab == 1) {
        ImGui::BeginChild("##Configs", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 300), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("configs").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), " configs");

        ImGui::Separator();

        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("ServerPresetsConfigs").x + 45);

        ImGui::Spacing();

        if (!toad::misc::ConfigList.empty())
        {
            ImGui::BeginChild("##ConfigBox", ImVec2(200, 100), true);

            static char buf[25];

            for (size_t i = 0; i < toad::misc::ConfigList.size(); i++)
            {
                std::call_once(flag, [&]()
                    {
                        toad::misc::selectedConfig = i;
                        std::strcpy(buf, toad::misc::ConfigList[i].c_str());
                    });
                const bool is_selected = (toad::misc::selectedConfig == i);
                if (ImGui::Selectable(toad::misc::ConfigList[i].c_str(), is_selected))
                {
                    toad::misc::selectedConfig = i;
                    std::strcpy(buf, toad::misc::ConfigList[i].c_str());
                }
            }

            ImGui::EndChild();

            ImGui::BeginChild("##ConfigBoxOptions", ImVec2(200, 150), false);

            ImGui::InputText("##SelectedConfig", buf, sizeof(buf) - 1);

            if (ImGui::Button("Refresh"))
            {
                toad::misc::ConfigList.clear();
                toad::misc::ConfigList = toad::misc::GetAllToadConfigs(toad::misc::exePath);
            }
            if (ImGui::Button("Load"))
            {
                std::string s = "\\";
                s.append(buf);
                s.append(".toad");
                toad::misc::loadConfig(toad::misc::exePath + s);
            }

            if (std::strcmp(buf, toad::misc::ConfigList[toad::misc::selectedConfig].c_str()) == 0)
            {
                ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(0.05f, 0.05f, 0.05f, 1));
                ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(0.05f, 0.05f, 0.05f, 1));
                ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(0.05f, 0.05f, 0.05f, 1));
                ImGui::Button("Create");
                ImGui::PopStyleColor();
                ImGui::PopStyleColor();
                ImGui::PopStyleColor();
            }
            
            if (ImGui::Button("Save"))
            {
                std::string s = "\\";
                s.append(buf);
                toad::misc::saveConfig(toad::misc::exePath + s);
            }
            ImGui::EndChild();
        }
        else
        {
            ImGui::TextColored(ImVec4(1.f, 0.f, 0.f, 1.f), "   No configs found,  ");
            ImGui::TextColored(ImVec4(1.f, 0.f, 0.f, 1.f), "    insert a name     ");
            ImGui::TextColored(ImVec4(1.f, 0.f, 0.f, 1.f), "   and press Create   ");
            static char buf[25];
            ImGui::InputText("##SelectedConfig", buf, 25);
            if (ImGui::Button("Create"))
            {
                std::string s(buf);
                toad::misc::saveConfig(s);

                toad::misc::ConfigList.clear();
                toad::misc::ConfigList = toad::misc::GetAllToadConfigs(toad::misc::exePath);
            }
            if (ImGui::Button("Refresh"))
            {
                toad::misc::ConfigList.clear();
                toad::misc::ConfigList = toad::misc::GetAllToadConfigs(toad::misc::exePath);
            }
        }

        ImGui::EndChild();

        ImGui::SameLine();

        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::BeginChild("##serverPresets", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 300), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("server presets").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "     server presets");

        ImGui::Separator();

        ImGui::ListBox("##ServerPresetsCombo", &toad::misc::selectedPreset, toad::misc::server_presets_c, IM_ARRAYSIZE(toad::misc::server_presets_c));
        if (ImGui::Button("load"))
            toad::misc::loadConfig(toad::misc::presets[toad::misc::selectedPreset]);

        ImGui::EndChild();
    }

    //recorder
    else if (tab == 2)
    {
        static char buf[25];
        static char buf2[15] = ".txt";

        ImGui::BeginChild("##recorderSettings", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 160), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("settings").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "  settings");

        ImGui::Separator();

        ImGui::Text("Bound to:");
        ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::clickrecorder::key);
        if (ImGui::IsItemClicked()) {
            toad::clickrecorder::key = ".."; 
            binding = true; 
            if (!toad::clickrecord_thread_exists) p_clickRecorder.get()->init_record_thread();
        }
        
        ImGui::Checkbox("unbind on toggle off", &toad::clickrecorder::auto_unbind);
        ImGui::Checkbox("custom file extension", &toad::clickrecorder::custom_extension);
        ImGui::Checkbox("skip delay after time", &toad::clickrecorder::skip_on_delay);
        if (toad::clickrecorder::skip_on_delay)
        {
            char* frmt;
            toad::clickrecorder::skip_delay == (int)toad::clickrecorder::skip_delay ? frmt = "%.0f" : frmt = "%.1f";
            ImGui::PushItemWidth(100);
            ImGui::InputDouble("##skipDelay", &toad::clickrecorder::skip_delay, 0.5, 1, frmt);
            ImGui::PopItemWidth();
            ImGui::SameLine();
            toad::clickrecorder::skip_delay == 1.0 ? ImGui::Text("second") : ImGui::Text("seconds");
            if (toad::clickrecorder::skip_delay < 0.1) toad::clickrecorder::skip_delay = 0.1;
        }
        ImGui::PushItemWidth(120);
        ImGui::InputText("##name", buf, 25);
        ImGui::PopItemWidth();
        if (toad::clickrecorder::custom_extension) {
            if (buf2[0] != '.') buf2[0] = '.';
            ImGui::SameLine();
            ImGui::SetCursorPosX(130);
            ImGui::PushItemWidth(ImGui::CalcTextSize(buf2).x + 5);
            ImGui::InputText("##extension", buf2, 15, ImGuiInputTextFlags_NoCursorFocus);
            ImGui::PopItemWidth();
        }
        else
        {
            ImGui::SameLine();
            ImGui::SetCursorPosX(130);
            ImGui::PushItemWidth(ImGui::CalcTextSize(".txt").x + 5);
            ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(0.1f, 0.1f, 0.1f, 1));
            ImGui::InputText("##defaultext", ".txt", 5, ImGuiInputTextFlags_ReadOnly);
            ImGui::PopStyleColor();
            ImGui::PopItemWidth();
        }
        if (ImGui::Button("load file"))
        {
            toad::clickrecorder::custom_extension ? p_clickRecorder.get()->load_file(buf, buf2) : p_clickRecorder.get()->load_file(buf);
        }
        if (!toad::clickrecorder::enabled && !toad::clickrecorder::click_delays.empty())
        {
            ImGui::SameLine();
            if (ImGui::Button("save file"))
            {
                toad::clickrecorder::custom_extension ? p_clickRecorder.get()->save_file(buf, buf2) : p_clickRecorder.get()->save_file(buf);
            }
        }
        
        ImGui::EndChild(); // end of recorderSettings child

        
        ImGui::SameLine();


        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::BeginChild("##recorderInfo", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 160),true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("info").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "info");

        ImGui::Separator();
        ImGui::Text("status: ");

        ImGui::SameLine();

        switch (toad::clickrecorder::record_status)
        {
        case toad::clickrecorder::recordStatus::AWAITING_FOR_CLICK:
            ImGui::TextColored(ImVec4(1, 1, 0, 1), "awaiting first click");
            break;
        case toad::clickrecorder::recordStatus::NOT_RECORDING:
            ImGui::TextColored(ImVec4(1, 0, 0, 1), "not recording");
            break;
        case toad::clickrecorder::recordStatus::RECORDING:
            ImGui::TextColored(ImVec4(0, 1, 0, 1), "recording");
            break;
        case toad::clickrecorder::recordStatus::SKIPPING_NEXT_CLICK:
            ImGui::TextColored(ImVec4(1, 1, 0, 1), "skipping click");
            break;
        }
        
        ImGui::Text("average cps: [%.2f]", toad::clickrecorder::average_cps);
        ImGui::Text("total clicks: [%i]", toad::clickrecorder::total_clicks);
        ImGui::Text("lines loaded: [%i]", toad::clickrecorder::click_delays.size());

        //ImGui::Text("Starting point: %i", );
        
        ImGui::PushItemWidth(50);
        if (toad::clickrecorder::click_delays.empty())
        {
            ImGui::PushItemFlag(ImGuiItemFlags_Disabled, true);
            ImGui::PushStyleVar(ImGuiStyleVar_Alpha, ImGui::GetStyle().Alpha / 2);
        }
        ImGui::Text("multiplier");
        ImGui::SameLine();
        if (ImGui::DragFloat("##ClickRecordermultiplier", &toad::clickrecorder::multiplier, 0, 1, 2, "%.2f", 1) && !toad::clickrecorder::click_delays.empty())
            p_clickRecorder->calcVars();
        if (toad::clickrecorder::click_delays.empty())
        {
            ImGui::PopItemFlag();
            ImGui::PopStyleVar();
        }
        ImGui::PopItemWidth();
        ImGui::EndChild(); // end of recorderInfo child

        ImGui::SetCursorPos(ImVec2(20, 230));
        ImGui::BeginChild("##recorderPlayback", ImVec2(ImGui::GetWindowSize().x - 40, 130), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("playback").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "  playback");

        ImGui::Separator();
        
        if (ImGui::Checkbox("enabled", &toad::clickrecorder::playback_enabled) && !toad::clickplayback_thread_exists)
        {
            p_clickRecorder.get()->init_playback_thread();
        }
        ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::clickrecorder::key_playback);
        if (ImGui::IsItemClicked()) { toad::clickrecorder::key_playback = ".."; binding = true; }
        if (toad::clickrecorder::click_delays.empty()) { ImGui::SameLine(); ImGui::TextColored(ImVec4(1, 0, 0, 1), "There are no clicks loaded"); }

        ImGui::Checkbox("inventory", &toad::clickrecorder::inventory);
        ImGui::Checkbox("randomize start", &toad::clickrecorder::randomize_start_point);
        ImGui::Text("starting point"); ImGui::SameLine();
        ImGui::InputInt("##Playbackstartpoint", &toad::clickrecorder::click_start_point, 2);
        if (toad::clickrecorder::click_start_point < 0 || toad::clickrecorder::click_start_point > toad::clickrecorder::click_delays.size()) toad::clickrecorder::click_start_point = 0;
        if (!toad::clickrecorder::is_start_point_valid()) { toad::clickrecorder::click_start_point++; }
        ImGui::EndChild(); // end of recorderPlayback child
    }

    //misc
    else if (tab == 3)
    {
        ImGui::BeginChild("misc", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 300), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("misc").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "misc");

        ImGui::Separator();

        ImGui::Checkbox("beep on toggle", &toad::misc::beep_on_toggle);
        if (ImGui::Checkbox("compatibility mode", &toad::misc::compatibility_mode))
            if (toad::misc::compatibility_mode) {
                std::this_thread::sleep_for(std::chrono::milliseconds(100));
                SetPriorityClass(GetCurrentProcess(), ABOVE_NORMAL_PRIORITY_CLASS);
            }
            else 
            {
                std::this_thread::sleep_for(std::chrono::milliseconds(100));
                SetPriorityClass(GetCurrentProcess(), NORMAL_PRIORITY_CLASS);
            }

        ImGui::Text("hide bind"); ImGui::SameLine(); ImGui::TextColored(ImColor(51, 51, 51), "[%s]", &toad::misc::hide_key);
        if (ImGui::IsItemClicked()) { toad::misc::hide_key = ".."; binding = true; }
        
        ImGui::ColorPicker3("##GuiCol", toad::theme::main_col);

        ImGui::EndChild();

        ImGui::SameLine();

        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::BeginChild("jitter", ImVec2(ImGui::GetWindowSize().x / 2 - 30, 160), true);
        ImGui::SetCursorPosX(ImGui::GetWindowSize().x / 2 - ImGui::CalcTextSize("jitter").x + 16);
        ImGui::TextColored(ImColor(122, 122, 122), "jitter");

        ImGui::Separator();

        ImGui::Checkbox("enable", &toad::jitter::enable);
        ImGui::Text("intensity x");
        ImGui::SliderInt("##intensity x", &toad::jitter::intensity_X, 1, 10);
        ImGui::Spacing();
        ImGui::Text("intensity y");
        ImGui::SliderInt("##Intensity y", &toad::jitter::intensity_Y, 1, 10);
        ImGui::Spacing();
        ImGui::Text("chance");
        ImGui::SliderInt("##Chance", &toad::jitter::chance, 1, 100, "%d%%");

        ImGui::EndChild();

        ImGui::SetCursorPosX(ImGui::GetWindowSize().x - ImVec2(ImGui::GetWindowSize().x / 2 - 30, 200).x - 20);
        ImGui::SetCursorPosY(230);
        ImGui::BeginChild("custom window",ImVec2(ImGui::GetWindowSize().x / 2 - 30, 130), true);
        ImGui::SetCursorPosX((ImGui::GetWindowSize().x / 2) - ImGui::CalcTextSize("custom window").x + 10);
        ImGui::TextColored(ImColor(122, 122, 122), "    custom window");

        ImGui::Separator();

        ImGui::Text("clicking window");
        ImGui::Combo("##ClickingWindow", &toad::misc::selectedClickWindow, toad::misc::window_options_c, IM_ARRAYSIZE(toad::misc::window_options_c));

        if (toad::misc::selectedClickWindow == 2)
        {
            ImGui::InputText("##CustomWindowName", toad::misc::custom_windowTitle, 50, ImGuiInputTextFlags_ReadOnly);
            ImGui::SameLine();
            if (ImGui::Button("..."))
            {
                toad::misc::procList.clear();
                toad::misc::get_window_list();
                showProcessList = !showProcessList;
            }
        }

        if (toad::clicking_window == NULL) ImGui::TextColored(ImVec4(205, 0, 0, 255), "no active window found");
        else ImGui::TextColored(ImVec4(0, 205, 0, 255), "Clicking on PID: %d", toad::misc::pid);

        ImGui::EndChild();
    }

    ImGui::SetCursorPosY(ImGui::GetWindowSize().y - 20);
    ImGui::TextColored(ImColor(51, 51, 51), "made by vierkant");

    if (showProcessList && toad::misc::selectedClickWindow == 2)
    {
        ImGui::Begin("Process List", &showProcessList, ImGuiWindowFlags_NoDocking);
        if (ImGui::Button("refresh"))
        {
            toad::misc::procList.clear();
            toad::misc::get_window_list();
        }
        for (size_t i = 0; i < toad::misc::procList.size(); i++) {
            const bool is_selected = (selected_item == i);
            if (ImGui::Selectable(toad::misc::procList[i].pname.c_str(), is_selected))
            {
                selected_item = i;
            }
            if (ImGui::IsItemFocused() && ImGui::IsMouseDoubleClicked(0))
            {
                std::strcpy(toad::misc::custom_windowTitle, toad::misc::procList[i].pname.c_str());
                toad::misc::pid = toad::misc::procList[i].pid;
                toad::clicking_window = toad::misc::procList[i].hwnd;
                showProcessList = false;
            }
            if (is_selected)
            {
                if (ImGui::Button("select as active"))
                {
                    std::strcpy(toad::misc::custom_windowTitle, toad::misc::procList[i].pname.c_str());
                    toad::misc::pid = toad::misc::procList[i].pid;
                    toad::clicking_window = toad::misc::procList[i].hwnd;
                    showProcessList = false;
                }
            }
        }
        ImGui::End();
    }
    if (showSoundsList)
    {
        ImGui::Begin("Sound List", &showSoundsList, ImGuiWindowFlags_NoDocking);
        if (ImGui::Button("Refresh"))
        {
            toad::misc::soundslist.clear();
            toad::misc::soundslist = toad::getAllFilesExt(toad::misc::exePath, ".wav", true);
        }
        if (toad::misc::soundslist.empty())
        {
            ImGui::TextColored(ImVec4(1, 0, 0, 1), "no files found, make sure the extension is .wav and in the same directory");
        }
        else
        {
            for (size_t i = 0; i < toad::misc::soundslist.size(); i++) {
                const bool is_selected = (selected_item == i);
                if (ImGui::Selectable(toad::misc::soundslist[i].c_str(), is_selected))
                {
                    selected_item = i;
                }
                if (ImGui::IsItemFocused() && ImGui::IsMouseDoubleClicked(0))
                {
                    std::wstringstream ws;
                    ws << toad::misc::soundslist[i].c_str();
                    toad::misc::currclicksound = ws.str().c_str();
                    toad::misc::currclicksoundstr = toad::misc::soundslist[i];
                    showSoundsList = !showSoundsList;
                }
            }
        }
      
        ImGui::End();
    }

    ImGui::SetCursorPosX(ImGui::GetWindowSize().x - 45);
    ImGui::SetCursorPosY(10);

    if (ImGui::Button("exit"))
    {
        toad::is_running = false;
    }

    ImGui::End();

}