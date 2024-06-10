#include "../aimassist.hpp"

#include "../../sdk/imgui/imgui_internal.h"
#include "../../sdk/imgui/ico_font.h"
#include "../../sdk/imgui/segue_font.h"

std::once_flag flag;

HWND window;

float color_edit4[4] = { 1.00f, 1.00f, 1.00f, 1.000f };

float accent_color[4] = { 0.745f, 0.151f, 0.151f, 1.000f };

static int select_count = 0;

bool active = false;

float size_child = 0;

bool menu = false;
ImFont* ico = nullptr;
ImFont* ico_combo = nullptr;
ImFont* ico_button = nullptr;
ImFont* ico_grande = nullptr;
ImFont* segu = nullptr;
ImFont* default_segu = nullptr;
ImFont* bold_segu = nullptr;

int tab_count = 0, tabs = 0;

int __stdcall aimassist::hooks::swapBuffersHook(HDC hdc)
{
	HWND mcWindow = FindWindow(L"LWJGL", nullptr);
	RECT windowRect;
	GetWindowRect(mcWindow, &windowRect);

	std::call_once(flag, [&]() {
		window = FindWindow(L"LWJGL", nullptr);
		ImGui::CreateContext();
		ImGuiIO& io = ImGui::GetIO(); (void)io;
		ImGuiContext& g = *GImGui;
		io.Fonts->AddFontFromMemoryTTF(&seguoe, sizeof seguoe, 22, NULL, io.Fonts->GetGlyphRangesCyrillic());

		default_segu = io.Fonts->AddFontFromMemoryTTF(&seguoe, sizeof seguoe, 22, NULL, io.Fonts->GetGlyphRangesCyrillic());

		segu = io.Fonts->AddFontFromMemoryTTF(&seguoe, sizeof seguoe, 40, NULL, io.Fonts->GetGlyphRangesCyrillic());

		bold_segu = io.Fonts->AddFontFromMemoryTTF(&bold_segue, sizeof bold_segue, 40, NULL, io.Fonts->GetGlyphRangesCyrillic());

		ico = io.Fonts->AddFontFromMemoryTTF(&icon, sizeof icon, 24, NULL, io.Fonts->GetGlyphRangesCyrillic());

		ico_combo = io.Fonts->AddFontFromMemoryTTF(&icon, sizeof icon, 19, NULL, io.Fonts->GetGlyphRangesCyrillic());

		ico_button = io.Fonts->AddFontFromMemoryTTF(&icon, sizeof icon, 25, NULL, io.Fonts->GetGlyphRangesCyrillic());

		ico_grande = io.Fonts->AddFontFromMemoryTTF(&icon, sizeof icon, 40, NULL, io.Fonts->GetGlyphRangesCyrillic());

		ImGui::StyleColorsDark();
		ImGui_ImplWin32_Init(mcWindow);
		ImGui_ImplOpenGL2_Init();
		return aimassist::hooks::oswapBuffers;
		});

	HWND _new = FindWindow(L"LWJGL", nullptr);

	if (window != _new)
	{
		window = _new;
		//ImGui::GetOverlayDrawList()->Clear();
		//ImGui::GetOverlayDrawList()->ClearFreeMemory();
		ImGui_ImplOpenGL2_Shutdown();
		ImGui_ImplWin32_Shutdown();

		ImGui_ImplWin32_Init(window);
		ImGui_ImplOpenGL2_Init();

		SetWindowLongPtrA(window, GWLP_WNDPROC, reinterpret_cast<LONG_PTR>(hooks::wndproc));
	}

	

	ImGuiIO& io = ImGui::GetIO();
	

    ImGui_ImplOpenGL2_NewFrame();
    ImGui_ImplWin32_NewFrame();

    ImGui::SetNextWindowPos(ImVec2(200, 200));

    if (aimassist::instance->menuOpen)
    {
        io.MouseDrawCursor = true;

        ImGui::NewFrame();
        {

            ImGui::SetNextWindowSize(ImVec2(905, 624));
            DWORD window_flags = ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoScrollWithMouse | ImGuiWindowFlags_NoBackground;


            ImGui::Begin("Hello!", (bool*)true, window_flags);
            {
                ImVec2 P1, P2;
                ImDrawList* pDrawList;
                const auto& p = ImGui::GetWindowPos();
                const auto& pWindowDrawList = ImGui::GetWindowDrawList();
                const auto& pBackgroundDrawList = ImGui::GetBackgroundDrawList();
                const auto& pForegroundDrawList = ImGui::GetForegroundDrawList();

                //DrawBackgroundBlur(pWindowDrawList, g_pd3dDevice);



                //ImGui::GetBackgroundDrawList()->AddImage(scene, ImVec2(0, 0), ImVec2(1920, 1080), ImVec2(0, 0), ImVec2(1, 1), ImColor(color_edit4[0], color_edit4[1], color_edit4[2], color_edit4[3]));

                pBackgroundDrawList->AddRectFilled(ImVec2(0.000f + p.x, 0.000f + p.y), ImVec2(905 + p.x, 624 + p.y), ImColor(9, 9, 9, 180), 10); // Background

                pWindowDrawList->AddRectFilled(ImVec2(189.000f + p.x, 75.000f + p.y), ImVec2(903 + p.x, 76 + p.y), ImColor(25, 25, 25, 180), 10); // bar line

                ImGui::SetCursorPos(ImVec2(800, 21));

                if (ImGui::OptButton("L", ImVec2(30, 30), false));

                ImGui::SameLine(840);

                if (ImGui::OptButton("B", ImVec2(30, 30), true));

                pWindowDrawList->AddRectFilled(ImVec2(0.000f + p.x, 0.000f + p.y), ImVec2(190 + p.x, 624 + p.y), ImGui::GetColorU32(ImGuiCol_ChildBg), 10, ImDrawFlags_RoundCornersLeft); // bar line

                const int vtx_idx_1 = pWindowDrawList->VtxBuffer.Size;

                pWindowDrawList->AddText(ico_grande, 40.f, ImVec2(20.000f + p.x, 20.000f + p.y), ImColor(0.60f, 0.60f, 0.60f, 0.70f), "U");

                pWindowDrawList->AddText(bold_segu, 40.f, ImVec2(70.000f + p.x, 15.000f + p.y), ImColor(0.60f, 0.60f, 0.60f, 0.70f), "Striker");

                pWindowDrawList->AddRectFilled(ImVec2(70.000f + p.x, 51.000f + p.y), ImVec2(163 + p.x, 52 + p.y), ImColor(0.60f, 0.60f, 0.60f, 0.70f), 10); // bar line

                const int vtx_idx_2 = pWindowDrawList->VtxBuffer.Size;

                ImGui::ShadeVertsLinearColorGradientKeepAlpha(pWindowDrawList, vtx_idx_1, vtx_idx_2, ImVec2(0 + p.x, 0 + p.y), ImVec2(200 + p.x, 20 + p.y), ImColor(0.25f, 0.25f, 0.25f, 0.50f), ImColor(0.60f, 0.60f, 0.60f, 1.00f));

                ImGui::SetCursorPosY(80);

                if (ImGui::TabButton("P", "AimAssist", ImVec2(190, 40)) && tab_count != 0) {
                    tab_count = 0;
                    active = true;
                }


                if (active) {
                    if (size_child <= 10) size_child += 1 / ImGui::GetIO().Framerate * 60.f;
                    else { active = false; tabs = tab_count; };
                }
                else {
                    if (size_child >= 0) size_child -= 1 / ImGui::GetIO().Framerate * 60.f;
                }

                pWindowDrawList->AddCircleFilled(ImVec2(57.000f + p.x, 570.000f + p.y), 25.000f, ImColor(10, 9, 10, 255), 30);

                pWindowDrawList->AddCircle(ImVec2(57.000f + p.x, 570.000f + p.y), 27.000f, ImColor(20, 19, 20, 255), 30, 4.000f);

                pWindowDrawList->AddText(segu, 40.f, ImVec2(51.000f + p.x, 548.000f + p.y), ImColor(0.60f, 0.60f, 0.60f, 0.50f), "?");



                const int vtx_idx_3 = pWindowDrawList->VtxBuffer.Size;

                pWindowDrawList->AddText(segu, 22.f, ImVec2(97.000f + p.x, 547.000f + p.y), ImColor(0.40f, 0.40f, 0.40f, 0.50f), "Username\nlifetime");

                const int vtx_idx_4 = pWindowDrawList->VtxBuffer.Size;

                ImGui::ShadeVertsLinearColorGradientKeepAlpha(pWindowDrawList, vtx_idx_3, vtx_idx_4, ImVec2(97.000f + p.x, 547.000f + p.y), ImVec2(200.000f + p.x, 567.000f + p.y), ImColor(0.35f, 0.35f, 0.35f, 0.50f), ImColor(0.90f, 0.90f, 0.90f, 1.00f));



                ImGui::SetCursorPos(ImVec2(203, 88 - size_child));

                switch (tabs) {

                case 0:

                    ImGui::BeginChild("Tab one", ImVec2(339, 253), true); {

                        ImGui::Checkbox("Enabled", &aimassist::instance->enabled);
                        ImGui::Checkbox("Require Click", &aimassist::instance->requireClick);
                        ImGui::SliderFloat("Speed", &aimassist::instance->speed, 1, 100, "%3.f");
                        ImGui::SliderFloat("FOV", &aimassist::instance->fov, 1, 360, "%3.f");
                        float distance = aimassist::instance->distance;
                        ImGui::SliderFloat("distance", &distance, 1.0, 8.0, "%3.1f");
                        aimassist::instance->distance = distance;
                        ImGui::Checkbox("Invisible", &aimassist::instance->checkInvisibles);
                        ImGui::Checkbox("Item Whitelist", &aimassist::instance->itemWhitelist);
                        ImGui::Checkbox("Teams", &aimassist::instance->teams);
                        ImGui::SliderFloat("Random Amount", &aimassist::instance->randomAmount, 0, 10, "%.3f");

                    }ImGui::EndChild();

                    ImGui::SetCursorPos(ImVec2(555, 88 - size_child));

                    ImGui::BeginChild("Tab two", ImVec2(339, 210), true); {

                        ImGui::ColorEdit4("Background Color", (float*)&color_edit4, ImGuiColorEditFlags_NoSidePreview | ImGuiColorEditFlags_AlphaBar | ImGuiColorEditFlags_NoInputs);

                        ImGui::ColorEdit4("Widget Color", (float*)&accent_color, ImGuiColorEditFlags_NoSidePreview | ImGuiColorEditFlags_AlphaBar | ImGuiColorEditFlags_NoInputs);

                    }ImGui::EndChild();

                    ImGui::SetCursorPos(ImVec2(203, 353 - size_child));

                    ImGui::BeginChild("Tab three", ImVec2(339, 258), true); {

                        static int selectedItem = 0;
                        static const char* items[]{ "Always", "Toggle" };
                        ImGui::Combo("Aimbot Mode", &selectedItem, items, IM_ARRAYSIZE(items), 5);

                        static char buf[64] = { "" };
                        ImGui::InputText("InputText", buf, 64);

                    }ImGui::EndChild();


                    ImGui::SetCursorPos(ImVec2(555, 313 - size_child));

                    ImGui::BeginChild("Tab four", ImVec2(339, 298), true); {

                        static int k, m = 1;
                        ImGui::Keybind("Keybind", &k, &m);

                    }ImGui::EndChild();

                    break;

                case 8:

                    ImGui::BeginChild("Configuration", ImVec2(339, 253), true); {


                    }ImGui::EndChild();

                    break;

                }
            }
            ImGui::End();
        }
        ImGui::EndFrame();

        ImGui::Render();

        glViewport(0, 0, windowRect.right, windowRect.top);
        ImGui_ImplOpenGL2_RenderDrawData(ImGui::GetDrawData());
    }
    

	

	

	io.MouseDrawCursor = false;

	return aimassist::hooks::oswapBuffers(hdc);
}