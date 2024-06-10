#include <dwmapi.h>
#include <mutex>
#include <map>

#include "menu.hpp"

#include "../modules/configmaker.hpp"
#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

#include "../modules/combat/velocity.hpp"
#include "../modules/combat/aimassist.hpp"
#include "../modules/combat/reach.hpp"
#include "../modules/combat/autoclicker.hpp"
#include "../modules/misc/throwpot.hpp"
#include "../modules/misc/misc.hpp"
#include "../modules/visuals/fullbright.hpp"
#include "../modules/movement/gametimer.hpp"
#include "../modules/movement/bhop.hpp"

#include "../game/jvm/jvm_16.hpp"
#include "../game/jvm/jvm_8.hpp"

#include "../utilities/memory.hpp"

#include  "../authentification/auth.hpp"

#include "../../vendors/imgui/imgui.h"
#include "../../vendors/imgui/imgui_custom.hpp"
#include "../../vendors/imgui/imgui_internal.h"
#include "../../vendors/imgui/imgui_impl_win32.h"
#include "../../vendors/imgui/imgui_impl_dx11.h"
#include "../../vendors/imgui/fonts.hpp"
#include "../../vendors/imgui/img.hpp"
#include "../../vendors/json.h"

#ifndef _DEBUG
#include <ThemidaSDK.h>
#endif

static ID3D11Device* g_pd3dDevice = NULL;
static ID3D11DeviceContext* g_pd3dDeviceContext = NULL;
static IDXGISwapChain* g_pSwapChain = NULL;
static ID3D11RenderTargetView* g_mainRenderTargetView = NULL;

void pop_menu()
{
#ifndef _DEBUG
	VM_TIGER_BLACK_START
#endif 
	WNDCLASSEX wc = { sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, NULL, NULL, NULL, "#w", NULL };
	::RegisterClassEx(&wc);

	settings->hWnd = CreateWindowExA(0L, wc.lpszClassName, "", WS_POPUP, ((GetSystemMetrics(SM_CXSCREEN) - 660.f) / 2.f), ((GetSystemMetrics(SM_CYSCREEN) - 725.f) / 2.f), 660.f, 725.f, NULL, NULL, wc.hInstance, NULL);

	MARGINS margins = { -1 };
	DwmExtendFrameIntoClientArea(settings->hWnd, &margins); //make transparent


	if (!CreateDeviceD3D(settings->hWnd))
	{
		CleanupDeviceD3D();
		::UnregisterClass(wc.lpszClassName, wc.hInstance);
		return;
	}

	::ShowWindow(settings->hWnd, SW_SHOWDEFAULT);
	::UpdateWindow(settings->hWnd);

	IMGUI_CHECKVERSION();
	ImGui::CreateContext();
	ImGuiIO& io = ImGui::GetIO(); (void)io;
	io.IniFilename = nullptr;
	ImGuiContext& g = *GImGui;

	ImGui::StyleColorsDark();

	ImGui_ImplWin32_Init(settings->hWnd);
	ImGui_ImplDX11_Init(g_pd3dDevice, g_pd3dDeviceContext);

	ImVec4 clear_color = ImVec4(0.f, 0.f, 0.f, 0.f);

	ID3D11ShaderResourceView* Image = nullptr;
	D3DX11_IMAGE_LOAD_INFO info;
	ID3DX11ThreadPump* pump{ nullptr };
	D3DX11CreateShaderResourceViewFromMemory(g_pd3dDevice, imgs::rawData, sizeof(imgs::rawData), &info,
		pump, &Image, 0);

	//

	ImFont* txt = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 15.f);
	ImFont* roboto = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::roboto_compressed_data, fonts::roboto_compressed_size, 22.5f);
	ImFont* opensan = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 20.f);
	ImFont* close_open = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 17.5f);
	ImFont* featuresName = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 20.f);
	ImFont* bigName = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 35.f);
	ImFont* featuresStuff = io.Fonts->AddFontFromMemoryCompressedTTF(fonts::opensans_med_compressed_data, fonts::opensans_med_compressed_size, 17.5f);

	int selected_category = 0;
	int next_category = 0;
	//

	int menu_movement_x = 0;
	int menu_movement_y = 0;

	bool titleHovered = false;
	bool bDraging = false;
	bool bConfigsList = false;

	std::vector<std::string> userConfigs;

	constexpr auto easeOutSine = [](float t)
	{
		return abs(sin((t * 3.1415926f) / 2));
	};

	bool loaded = false;
	std::vector<std::string> throwpot_items{ "One Throw", "Double Throw" };

	bool logged = false;
	while (!settings->destruct)
	{
		MSG msg;
		while (::PeekMessage(&msg, NULL, 0U, 0U, PM_REMOVE))
		{
			::TranslateMessage(&msg);
			::DispatchMessage(&msg);

			if (msg.message == WM_QUIT) {
				settings->destruct = true;
			}
			else if (settings->flags.x1 == 0x1 || settings->flags.x2 == 0x2 || settings->flags.x3 == 0x3) {
				settings->destruct = true;
			}
			/*else {
				for (const auto& timer : settings->flags.thread_hearthbeats) {
					if (GetTickCount64() - timer > 2500) {
						settings->destruct = true;
					}
				}
			}*/
		}
		if (settings->destruct)
			break;

		ImGui_ImplDX11_NewFrame();
		ImGui_ImplWin32_NewFrame();
		ImGui::NewFrame();

		ImGui::SetNextWindowPos({ 0.F, 0.f });
		{
			ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2{ 0.f, 0.f });
			ImGui::PushStyleVar(ImGuiStyleVar_WindowBorderSize, 0.f);

			if (!loaded || !logged) {
				ImGui::SetNextWindowSize({ 660.f, 500.f });
				ImGui::Begin("##loadingWindow", nullptr, ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoMove |
					ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoScrollWithMouse);
				{
					ImGui::SetCursorPos({ 0.f, 0.f });

					ImGui::BeginChild("##titleChild", { 660.f, 35.f }, false);
					{
						ImGui::PushFont(roboto);
						{
							ImGui::SetCursorPos({ 7.5f, (35.f / 2) - 25.f / 2 });
							//ImGui::Image(Image, { 25.f, 25.f });
						}
						ImGui::PopFont();

						ImGui::PushFont(close_open);
						{
							ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(122, 122, 126)));

							{
								auto size = ImGui::CalcTextSize("_");
								ImGui::SetCursorPos({ 625.f, ((35.f / 2) - size.y / 2) - 2.5f });
								ImGui::Text("_");
								if (ImGui::IsItemHovered())
								{
									ImGui::SetMouseCursor(ImGuiMouseCursor_Hand);
									if (ImGui::IsMouseClicked(ImGuiMouseButton_Left))
										ShowWindow(settings->hWnd, SW_MINIMIZE);
								}
							}
							{
								auto size = ImGui::CalcTextSize("x");
								ImGui::SetCursorPos({ 642.5f, (35.f / 2) - size.y / 2 });
								ImGui::Text("x");
								if (ImGui::IsItemHovered())
								{
									ImGui::SetMouseCursor(ImGuiMouseCursor_Hand);
									if (ImGui::IsMouseClicked(ImGuiMouseButton_Left))
										settings->destruct = true;
								}
							}

							ImGui::PopStyleColor(1);
						}
						ImGui::PopFont();
					}
					ImGui::EndChild();

					auto mousePos = ImGui::GetMousePos();
					titleHovered = mousePos.y <= 35.f;

					static bool isLogoLoading{ true };
					static bool isLineLoading{ true };

					static float loadingTime{ 0.0f };
					static float lineLodingTime{ 0.0f };

					if (isLogoLoading)
					{
						if (loadingTime >= 2.f)
							loadingTime = 0.f;

						if (loadingTime < 1.f)
							loadingTime += 2.25f * io.DeltaTime;

						if (loadingTime >= 1.f)
							isLogoLoading = false;
					}

					ImGui::SetCursorPos({ (float)(((660.f - 288.f) / 2) * easeOutSine(loadingTime)), ((500.f - 35.f) - 116.4375f) / 2 });
					ImGui::Image(Image, { 288.f , 116.4375f });

					//				
					static int curStage{ 0 };
					static int oldStage{ curStage };
					static float fraction{ 0.0f };
					static float time{ 0.0f };
					static float newFraction{ curStage / (float)100.f };
					static bool changing{ false };
					static auto timer{ GetTickCount64() };
					static bool loginPerformed{ false };
					//

					if (oldStage != curStage)
					{
						oldStage = curStage;
						time = 0.f;
						newFraction = curStage / (float)100.f;
						changing = true;
					}

					if (changing && time < 1.f)
					{
						time += 0.005f;
						fraction += (newFraction - fraction) * /* easeOutExpo */ (1.f - pow(1.f - time, 3.f));
					}
					else if (time >= 1.f)
					{
						changing = false;
						time = 0.f;
					}

					if (!isLogoLoading && curStage == 0) {

						Authentification::get().login(settings->user.token, Authentification::get().get_hwid(), logged, settings->user.authOutput);
						curStage += 50;
					}
					else if (curStage == 50)
					{
						char process_path[MAX_PATH];
						auto is1_7 = settings->game_ver == c_version::BADLION_1_7_10
							|| settings->game_ver == c_version::LUNAR_1_7_10
							|| settings->game_ver == c_version::CASUAL_1_7_10
							|| settings->game_ver == c_version::FORGE_1_7_10;

						auto mod = MemoryHelper::get().get_module_entry(settings->game_pid, "jvm.dll");
						if (settings->game_ver == LUNAR_1_7_10 || settings->game_ver == LUNAR_1_8)
							settings->env = new c_jvm16(mod, OpenProcess(PROCESS_ALL_ACCESS, FALSE, settings->game_pid));
						else
							settings->env = new c_jvm8(mod, OpenProcess(PROCESS_ALL_ACCESS, FALSE, settings->game_pid));

						settings->env->load_classes();
						if (/*settings->game_ver == c_version::LUNAR_1_7_10 ||*/ settings->game_ver == c_version::LUNAR_1_8) {
							GetModuleFileNameEx(settings->env->get_handle(), NULL, process_path, MAX_PATH);
							std::istringstream iss(mapper::load_lunar_class(settings->env->get_handle(), process_path));
							for (std::string line; std::getline(iss, line);)
							{
								std::replace(line.begin(), line.end(), '.', '/');
								std::string mapping = line.substr(0, 44);
								std::string o_mapping = line.substr(line.find(" ") + 1);
								if (o_mapping == (is1_7 ? "bao" : "ave")) {
									settings->minecraft_clazz = settings->env->find_class(mapping.data());
								}
								else if (o_mapping == (is1_7 ? "aeh" : "aay")) {
									settings->itemsword_clazz = settings->env->find_class(mapping.data());
								}
								else if (o_mapping == (is1_7 ? "abf" : "yl")) {
									settings->itemaxe_clazz = settings->env->find_class(mapping.data());
								}
								else if (o_mapping == (is1_7 ? "bfu" : "azc")) {
									settings->gui_inventory = settings->env->find_class(mapping.data());
								}
							}
						}
						else if (settings->game_ver == c_version::LUNAR_1_7_10) {
							settings->minecraft_clazz = settings->env->find_class("net/minecraft/client/Minecraft");
							settings->itemsword_clazz = settings->env->find_class("net/minecraft/item/ItemSword");
							settings->itemaxe_clazz = settings->env->find_class("net/minecraft/item/ItemAxe");
							settings->gui_inventory = settings->env->find_class("net/minecraft/client/gui/inventory/GuiInventory");
						}
						else {
							settings->minecraft_clazz = settings->env->find_class(is1_7 ? "bao" : "ave");
							if (settings->minecraft_clazz) {
								settings->itemsword_clazz = settings->env->find_class(is1_7 ? "aeh" : "aay");
								settings->itemaxe_clazz = settings->env->find_class(is1_7 ? "abf" : "yl");
								settings->gui_inventory = settings->env->find_class(is1_7 ? "bfu" : "azc");
							}
							else {
								settings->game_ver = is1_7 ? c_version::FORGE_1_7_10 : c_version::FORGE_1_8;
								settings->minecraft_clazz = settings->env->find_class("net/minecraft/client/Minecraft");
								settings->itemsword_clazz = settings->env->find_class("net/minecraft/item/ItemSword");
								settings->itemaxe_clazz = settings->env->find_class("net/minecraft/item/ItemAxe");
								settings->gui_inventory = settings->env->find_class("net/minecraft/client/gui/inventory/GuiInventory");
							}

							if (settings->env->find_class("net/digitalingot/featheropt/FeatherCoreMod"))
								settings->game_ver = c_version::FEATHER_1_8;
						}

						if (!settings->minecraft_clazz)
							MessageBoxA(NULL, "An error occured while initializing dope.", "", MB_OK | MB_ICONERROR);
						
						mapper::initialize_mapper();
						curStage += 40;
					}
					else if (curStage == 90)
					{
						auto create_thread = [](auto thread)
						{
							if (auto handle = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread, nullptr, 0, nullptr); handle != NULL)
								CloseHandle(handle);
						};

						create_thread(modules::velocity::thread);
						create_thread(modules::aimassist::thread);
						create_thread(modules::reach::thread);
						create_thread(modules::autoclicker::thread);
						create_thread(modules::throwpot::thread);
						create_thread(modules::fullbright::thread);
						create_thread(modules::misc_settings::thread);
						create_thread(modules::game_timer::thread);
						create_thread(modules::bhop::thread);

						curStage += 10;
					}
					else if (curStage == 100 && GetTickCount64() - timer > rand() % 500 + 750)
					{
						loaded = true;
					}

					ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1, 1, 1, 1));
					ImGui::SetCursorPos({ 10.f, 500.f - 30.f });
					ImGui::ProgressBar(fraction, { 660.f - 20.f, 20.f });
					ImGui::PopStyleColor(1);
				}
				ImGui::End();
			}
			else {
				ImGui::SetNextWindowSize({ 660.f, 725.f });

				ImGui::Begin("##mainWindow", nullptr, ImGuiWindowFlags_NoCollapse | ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoMove |
					ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoSavedSettings | ImGuiWindowFlags_NoScrollbar | ImGuiWindowFlags_NoScrollWithMouse);
				{
					ImGui::SetCursorPos({ 0.f, 0.f });
					ImGui::BeginChild("##titleChild", { 660.f, 35.f }, false);
					{
						ImGui::PushFont(roboto);
						{
							ImGui::SetCursorPos({ 7.5f, (35.f / 2) - 25.f / 2 });
							//ImGui::Image(Image, { 25.f, 25.f });
						}
						ImGui::PopFont();

						ImGui::PushFont(close_open);
						{
							ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(122, 122, 126)));

							{
								auto size = ImGui::CalcTextSize("_");
								ImGui::SetCursorPos({ 625.f, ((35.f / 2) - size.y / 2) - 2.5f });
								ImGui::Text("_");
								if (ImGui::IsItemHovered())
								{
									ImGui::SetMouseCursor(ImGuiMouseCursor_Hand);
									if (ImGui::IsMouseClicked(ImGuiMouseButton_Left))
										ShowWindow(settings->hWnd, SW_MINIMIZE);
								}
							}
							{
								auto size = ImGui::CalcTextSize("x");
								ImGui::SetCursorPos({ 642.5f, (35.f / 2) - size.y / 2 });
								ImGui::Text("x");
								if (ImGui::IsItemHovered())
								{
									ImGui::SetMouseCursor(ImGuiMouseCursor_Hand);
									if (ImGui::IsMouseClicked(ImGuiMouseButton_Left))
										settings->destruct = true;
								}
							}

							ImGui::PopStyleColor(1);
						}
						ImGui::PopFont();
					}
					ImGui::EndChild();
					auto mousePos = ImGui::GetMousePos();
					titleHovered = mousePos.y <= 35.f;


					ImGui::SetCursorPos({ 0.f, 35.f });
					ImGui::BeginChild("##categoriesChild", { 660.f, 60.f }, false);
					{
						ImGui::PushFont(opensan);
						{
							static float latestSize = 0.f;
							static bool isClicking = false;
							static float clickingAlphaTime = 0.f;

							static float startX = 0.f;
							static float endX = 0.f;

							auto create_category = [&opensan, &selected_category, &next_category, &g](const char* name, int idx)
							{
								auto text_size = ImGui::CalcTextSize(name);
								auto category_width = 660.f / 5.f;
								auto btn_pos = category_width * idx;

								if (idx == 0) { latestSize = 0.f; }

								if (isClicking)
									clickingAlphaTime += 4.f * g.IO.DeltaTime;

								auto has_passed = selected_category < next_category ? (int)startX >= (next_category * category_width) : (int)startX <= (next_category * category_width);
								if (isClicking && has_passed) {
									isClicking = false;
									selected_category = next_category;
								}

								ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(26, 28, 30)));
								ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(32, 35, 38)));
								ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(32, 35, 38)));
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImGui::SetCursorPos({ btn_pos, 0.f });
								if (ImGui::Button(name, { category_width, 60.f }))
								{
									if (idx != selected_category) {
										clickingAlphaTime = 0.f;
										isClicking = true;
										next_category = idx;
									}
								}
								ImGui::PopStyleColor(4);

								startX = isClicking ?
									(selected_category < next_category ? (startX + clickingAlphaTime) : startX - clickingAlphaTime)
									:
									(category_width * next_category);

								endX = startX + category_width;
								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(startX, 92.5f), ImVec2(endX, 95.f), ImGui::GetColorU32(ImVec4(ImColor(59, 120, 254, 255))), 10.f);
							};

							create_category("Combat", 0);
							create_category("Visuals", 1);
							create_category("Movement", 2);
							create_category("Misc", 3);
							create_category("Settings", 4);
						}
						ImGui::PopFont();
					}
					ImGui::EndChild();

					ImGui::SetCursorPos({ 0.f, 95.f });
					ImGui::BeginChild("##featuresChild", { 660.f, 655.f }, false);
					{
						ImGui::SetCursorPos({ 40.f, 40.f });
						switch (selected_category)
						{
						case 0:
							ImGui::PushStyleVar(ImGuiStyleVar_ChildBorderSize, 1.5f);
							ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 2.5f);
							ImGui::BeginChild("##aim", { 275.f, 305.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Aim Assist");

								ImGui::SetCursorPos({ cursorPos.x + 77.5f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##aimbind", &settings->combat.aimassist.bind, { 125.f, 25.f });


								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##aimenabled", &settings->combat.aimassist.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderInt("Horizontal Speed", &settings->combat.aimassist.speed, 25, 100);
								ImGui::SliderInt("Field of View", &settings->combat.aimassist.fov, 0, 180);
								ImGui::SliderFloat("Range", &settings->combat.aimassist.distance, 1.0f, 10.0f, "%.2f");
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Weapon Only", &settings->combat.aimassist.onlyweapon);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Only on mouse move", &settings->combat.aimassist.mousemove);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Only while clicking", &settings->combat.aimassist.onlyclicking);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Anti BOT", &settings->combat.aimassist.antibot);

								ImGui::PopFont();
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::SameLine(335.f);
							ImGui::BeginChild("##reach", { 275.f, 235.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Reach");

								ImGui::SetCursorPos({ cursorPos.x + 50.f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##reachbind", &settings->combat.reach.bind, { 125.f, 25.f });


								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##reachenabled", &settings->combat.reach.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderDouble("Distance", &settings->combat.reach.value, 3.0, 6.0, "%.2f");
								ImGui::SliderFloat("Hitbox", &settings->combat.reach.hitbox, 0.0, 1.0, "%.2f");
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Weapon Only", &settings->combat.reach.onlyweapon);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("On Ground Only", &settings->combat.reach.onground);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Liquid Check", &settings->combat.reach.liquidcheck);

								ImGui::PopFont();
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::SetCursorPos({ 40.f , 40.f + 315.f });
							ImGui::BeginChild("##autoclicker", { 275.f, 190.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Autoclicker");

								ImGui::SetCursorPos({ cursorPos.x + 85.f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##clickerbind", &settings->combat.leftclicker.bind, { 125.f, 25.f });


								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##clickerenabled", &settings->combat.leftclicker.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderInt("Average CPS", &settings->combat.leftclicker.avg, 5, 25);
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Weapon Only", &settings->combat.leftclicker.onlyWeapon);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Break Block", &settings->combat.leftclicker.breakBlock);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Inventory Fill", &settings->combat.leftclicker.inInventory);

								ImGui::PopFont();
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::SetCursorPos({ 335.f, 40.f + 245.f });
							ImGui::BeginChild("##velocity", { 275.f, 320.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Velocity");

								ImGui::SetCursorPos({ cursorPos.x + 65.f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##velocitybind", &settings->combat.velocity.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##velocityenabled", &settings->combat.velocity.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderDouble("Horizontal", &settings->combat.velocity.horizontal, 0.0, 100.0, "%.2f");
								ImGui::SliderDouble("Vertical", &settings->combat.velocity.vertical, 0.0, 100.0, "%.2f");
								ImGui::SliderInt("Chance", &settings->combat.velocity.chance, 0, 100);
								ImGui::SliderInt("Delay", &settings->combat.velocity.delay, 0, 3);
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Weapon Only", &settings->combat.velocity.onlyweapon);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("In Air Only", &settings->combat.velocity.aironly);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("While Moving Only", &settings->combat.velocity.moving);

								ImGui::PopFont();
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::PopStyleVar(2);
							break;
						case 1:
							ImGui::PushStyleVar(ImGuiStyleVar_ChildBorderSize, 1.5f);
							ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 2.5f);
							ImGui::BeginChild("##fullbright", { 275.f, 35.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Full Bright");

								ImGui::SetCursorPos({ cursorPos.x + 77.5f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##fullbrightbind", &settings->visuals.fullbright.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##fullbrightenabled", &settings->visuals.fullbright.enabled);
								ImGui::PopFont();

								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();
							ImGui::PopStyleVar(2);
							break;
						case 2:
							ImGui::PushStyleVar(ImGuiStyleVar_ChildBorderSize, 1.5f);
							ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 2.5f);
							ImGui::BeginChild("##timer", { 275.f, 160.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Timer");

								ImGui::SetCursorPos({ cursorPos.x + 47.5f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##timerbind", &settings->movement.timer.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##timerenabled", &settings->movement.timer.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderFloat("Value", &settings->movement.timer.value, 1.f, 10.f,  "%.2f");
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Weapon Only", &settings->movement.timer.onlyweapon);
								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Only while Moving", &settings->movement.timer.moving);

								ImGui::PopFont();

								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::SameLine(335.f);
							ImGui::BeginChild("##bhop", { 275.f, 135.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Bhop");

								ImGui::SetCursorPos({ cursorPos.x + 45.f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##bhopbind", &settings->movement.bhop.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##bhopenabled", &settings->movement.bhop.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderFloat("Power", &settings->movement.bhop.power, 1.f, 10.f, "%.2f");
								ImGui::Spacing();

								ImGui::SetCursorPosX(12.f);
								ImGui::Checkbox2("Disable in Liquid", &settings->movement.bhop.liquidCheck);

								ImGui::PopFont();

								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();



							ImGui::PopStyleVar(2);
							break;
						case 3:
							ImGui::PushStyleVar(ImGuiStyleVar_ChildBorderSize, 1.5f);
							ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 2.5f);
							ImGui::BeginChild("##throwpot", { 275.f, 200.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Throw Pots");

								ImGui::SetCursorPos({ cursorPos.x + 87.5f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##throwpotbind", &settings->misc.throwpot.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##throwpotenabled", &settings->misc.throwpot.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));

								ImGui::NewLine();
								ImGui::PushFont(featuresStuff);
								ImGui::SliderInt("Throw Delay", &settings->misc.throwpot.throwDelay, 25, 100);
								ImGui::SliderInt("Switch Delay", &settings->misc.throwpot.switchDelay, 0, 180);
								ImGui::Spacing();

								ImGui::SetCursorPosX(10.f);
								ImGui::CustomCombo1("Mode", throwpot_items, throwpot_items.size(), &settings->misc.throwpot.mode.mode_selected);

								ImGui::PopFont();
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();
							ImGui::PopStyleVar(2);
							break;
						case 4:
							ImGui::PushStyleVar(ImGuiStyleVar_ChildBorderSize, 1.5f);
							ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 2.5f);
							ImGui::BeginChild("##hide", { 275.f, 35.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Hide");

								ImGui::SetCursorPos({ cursorPos.x + 40.f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##hidebind", &settings->settings.hide.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								if (ImGui::Checkbox("##hideenabled", &settings->settings.hide.enabled)) {
									if (settings->settings.streamproof.bind == NULL)
										settings->settings.streamproof.enabled = false;
								}
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::SameLine(335.f);
							ImGui::BeginChild("##streamproof", { 275.f, 35.f }, true);
							{
								ImGui::PushFont(featuresName);
								ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(1.f, 1.f, 1.f, 0.85f));

								ImVec2 cursorPos = ImGui::GetCursorPos();
								ImGui::SetCursorPos({ cursorPos.x + 10.f, cursorPos.y + 7.5f });
								ImGui::Text("Stream Proof");

								ImGui::SetCursorPos({ cursorPos.x + 102.5f, cursorPos.y + 4.5f });
								ImGui::Hotkey("##streamproofbind", &settings->settings.streamproof.bind, { 125.f, 25.f });

								ImGui::SetCursorPos({ 225.f, cursorPos.y + 5.f });
								ImGui::Checkbox("##streamproofenabled", &settings->settings.streamproof.enabled);
								ImGui::PopFont();

								static float x1 = ImGui::GetCurrentWindow()->Pos.x;
								if (g.GroupStack.Size > 0 && g.GroupStack.back().WindowID == ImGui::GetCurrentWindow()->ID)
									x1 += ImGui::GetCurrentWindow()->DC.Indent.x;

								ImGui::GetCurrentWindow()->DrawList->AddRectFilled(ImVec2(x1 + 10.f, ImGui::GetCurrentWindow()->DC.CursorPos.y + 2.f), ImVec2((ImGui::GetCurrentWindow()->Pos.x + 265.f), ImGui::GetCurrentWindow()->DC.CursorPos.y + 4.f), ImGui::GetColorU32(ImGuiCol_Border));
								ImGui::PopStyleColor(1);
							}
							ImGui::EndChild();

							ImGui::Spacing();

							{
								ImGui::PushFont(featuresName);
								ImGui::SetCursorPosX(40.f);
								ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(26, 28, 30)));
								ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(32, 35, 38)));
								ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(32, 35, 38)));
								if (ImGui::Button("Configs", { 570.f, 30.f })) {
									bConfigsList = true;
								}
								ImGui::PopStyleColor(3);
								ImGui::PopFont();
							}

							ImGui::PopStyleVar(2);
							break;
						default:
							break;
						}
					}
					ImGui::EndChild();
				}
				ImGui::End();
			}

			/**/
			{
				static unsigned long long initialized = -1; // a bit messy but works ez

				if (bConfigsList) {
					static std::once_flag flag;
					std::call_once(flag, [&userConfigs]() 
						{ 
							ConfigMaker::get().Reload(userConfigs);
						});

					if (initialized == -1)
						initialized = GetTickCount64();

					ImGui::PushStyleColor(ImGuiCol_WindowBg, ImVec4{ 17.f / 255.f, 19.f / 255.f, 21.f / 255.f, 0.9f });
					ImGui::SetNextWindowPos({ 0.f, 0.f });
					ImGui::SetNextWindowSize({ WINDOW_WIDTH, WINDOW_HEIGHT });

					ImGui::Begin("##ConfigsList", nullptr, ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize);
					{
						ImGui::SetCursorPos({ WINDOW_WIDTH * 0.1f, WINDOW_HEIGHT * 0.1f });
						ImGui::PopStyleColor(1);

						ImGui::PushStyleColor(ImGuiCol_ChildBg, ImVec4(17.f / 255.f, 19.f / 255.f, 21.f / 255.f, 1.f));
						{
							ImGui::BeginChild("##settingschild", { WINDOW_WIDTH * 0.8f, WINDOW_HEIGHT * 0.8f }, true, ImGuiWindowFlags_NoScrollbar);
							{
								ImGui::SetCursorPos({5.f, 5.f});

								ImGui::PushFont(featuresName);
								static char configName[MAX_PATH];
								ImGui::InputTextEx("##configname", "Ex: Best PvP Config", configName, MAX_PATH, ImVec2{ ((WINDOW_WIDTH * 0.8f) / 2.f) - 10.f, 30.f}, ImGuiInputTextFlags_None);

								ImGui::SameLine();

								ImGui::PushStyleColor(ImGuiCol_Button, ImVec4(ImColor(26, 28, 30)));
								ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImVec4(ImColor(32, 35, 38)));
								ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImVec4(ImColor(32, 35, 38)));
								if (ImGui::Button("Create", { ((WINDOW_WIDTH * 0.8f) / 2.f) - 10.f, 30.f })) {
									if (strlen(configName) > 26 || strlen(configName) < 3)
										MessageBoxA(NULL, "Please use a shorter/or bigger name.", "", MB_ICONEXCLAMATION);
									else {
										ConfigMaker::get().Create(ConfigMaker::get().CreateData(configName));
										ConfigMaker::get().Reload(userConfigs);
										memset(configName, NULL, MAX_PATH);
									}
								}
								ImGui::PopStyleColor(3);
								ImGui::PopFont();

								auto createConfig = [&](std::string name, int ID)
								{
									ImGui::PushStyleColor(ImGuiCol_ChildBg, ImVec4(ImColor(26, 28, 30)));
									ImGui::PushStyleVar(ImGuiStyleVar_ChildRounding, 3.f);
									ImGui::BeginChild(std::string("##config" + std::to_string(ID)).data(), { 160.f, 140.f }, false, ImGuiWindowFlags_NoScrollbar);
									{
										ImGui::SetCursorPos({ ImGui::GetCursorPosX() + 5.f, ImGui::GetCursorPosY() + 2.5f });
										ImGui::Text(name.data());

										ImGui::PushStyleColor(ImGuiCol_Button, ImGui::GetColorU32(ImGuiCol_FrameBg));
										ImGui::PushStyleColor(ImGuiCol_ButtonActive, ImGui::GetColorU32(ImGuiCol_FrameBg));
										ImGui::PushStyleColor(ImGuiCol_ButtonHovered, ImGui::GetColorU32(ImGuiCol_FrameBg));

										ImGui::SetCursorPos({ ImGui::GetCursorPosX() + 2.5f, ImGui::GetCursorPosY() + 85.f });
										ImGui::PushStyleColor(ImGuiCol_Text, ImGui::GetColorU32(ImGuiCol_CheckMark));
										if (ImGui::Button("Load")) {
											ConfigMaker::get().Load(ID, userConfigs);
											ConfigMaker::get().Reload(userConfigs);

											bConfigsList = false;
											//success
										}
										ImGui::PopStyleColor(1);

										if (ImGui::IsItemHovered())
											g.MouseCursor = ImGuiMouseCursor_Hand;


										ImGui::SameLine(105.f);
										ImGui::PushStyleColor(ImGuiCol_Text, ImVec4(ImColor(124, 24, 23)));
										if (ImGui::Button("Delete")) {
											ConfigMaker::get().Delete(ID);
											ConfigMaker::get().Reload(userConfigs);
										}
										ImGui::PopStyleColor(1);

										if (ImGui::IsItemHovered())
											g.MouseCursor = ImGuiMouseCursor_Hand;

										ImGui::PopStyleColor(3);
									}
									ImGui::EndChild();
									ImGui::PopStyleVar(1);
									ImGui::PopStyleColor(1);

									ImGui::SameLine();
									if (ImGui::GetCursorPosX() >= 504)
										ImGui::Spacing();

									if (ImGui::GetCursorPosX() < 25)
										ImGui::SetCursorPosX(ImGui::GetCursorPosX() + 70.f);
								};

								ImGui::Spacing();

								ImGui::PushFont(featuresName);

								ImGui::SetCursorPosX(15.f);

								for (const auto& configJson : userConfigs)
								{
									if (!configJson.empty()) {
										nlohmann::json j = nlohmann::json::parse(configJson);
										createConfig(j["name"].get<std::string>(), std::stoi(j["id"].get<std::string>()));
									}
								}

								ImGui::PopFont();
							}
							ImGui::EndChild();

							auto mousePos = ImGui::GetMousePos();
							if (ImGui::IsMouseClicked(0) && (
								(mousePos.x < WINDOW_WIDTH * 0.1f || mousePos.x > WINDOW_WIDTH * 0.9f) ||
								(mousePos.y < WINDOW_HEIGHT * 0.1f || mousePos.y > WINDOW_HEIGHT * 0.9f)
								)
								&& (GetTickCount64() - initialized) > 250) {

								bConfigsList = false;
							}
						}
						ImGui::PopStyleColor(1);
					}

					ImGui::End();
				}
				else {
					initialized = -1;
				}
			}
			/**/

			if (!bDraging)
				bDraging = titleHovered && ImGui::IsMouseDragging(ImGuiMouseButton_Left);

			if (bDraging && !ImGui::IsMouseDragging(ImGuiMouseButton_Left))
				bDraging = false;

			if (ImGui::IsMouseClicked(0))
			{
				POINT CursorPosition;
				RECT MenuPosition;

				GetCursorPos(&CursorPosition);
				GetWindowRect(settings->hWnd, &MenuPosition);

				menu_movement_x = CursorPosition.x - MenuPosition.left;
				menu_movement_y = CursorPosition.y - MenuPosition.top;
			}

			if (bDraging)
			{
				POINT cursor_position;

				GetCursorPos(&cursor_position);
				SetWindowPos(settings->hWnd, nullptr, cursor_position.x - menu_movement_x, cursor_position.y - menu_movement_y, 0, 0, SWP_NOSIZE);
			}

			ImGui::PopStyleVar(2);
		}

		// Rendering
		ImGui::Render();
		const float clear_color_with_alpha[4] = { clear_color.x * clear_color.w, clear_color.y * clear_color.w, clear_color.z * clear_color.w, clear_color.w };
		g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
		g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, clear_color_with_alpha);
		ImGui_ImplDX11_RenderDrawData(ImGui::GetDrawData());
		g_pSwapChain->Present(1, 0);


	}

	ImGui_ImplDX11_Shutdown();
	ImGui_ImplWin32_Shutdown();
	ImGui::DestroyContext();

	CleanupDeviceD3D();
	::DestroyWindow(settings->hWnd);
	::UnregisterClass(wc.lpszClassName, wc.hInstance);

#ifndef _DEBUG
	VM_TIGER_BLACK_END
#endif 
}

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

extern IMGUI_IMPL_API LRESULT ImGui_ImplWin32_WndProcHandler(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);
LRESULT __stdcall WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam)
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
