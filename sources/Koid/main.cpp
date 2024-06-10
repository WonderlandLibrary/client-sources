#include "conector.h"
#include "xor.h"
#include <iomanip>

HWND hwnd;
static ID3D11Device* g_pd3dDevice = NULL;
static ID3D11DeviceContext* g_pd3dDeviceContext = NULL;
static IDXGISwapChain* g_pSwapChain = NULL;
static ID3D11RenderTargetView* g_mainRenderTargetView = NULL;
void CreateRenderTarget();
void CleanupRenderTarget();
HRESULT CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);

int main()
{
	ShowWindow(GetConsoleWindow(), 1);
	cucklord_authenticate();

	if (cucklord_logged)
	{
		ShowWindow(GetConsoleWindow(), 0);
		cucklord_get_privilege(GetCurrentProcess(), SE_SECURITY_NAME);
		cucklord_get_privilege(GetCurrentProcess(), SE_DEBUG_NAME);
		std::atexit(cucklord_selfdestruct_function);
		thread_handle_binds = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_binds, nullptr, 0, 0);
		thread_handle_clicker = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_clicker, nullptr, 0, 0);
		thread_handle_reach = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_reach, nullptr, 0, 0);
		thread_handle_speed = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_speed, nullptr, 0, 0);
		thread_handle_jitter = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_jitter, nullptr, 0, 0);
		thread_handle_inventory = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_inventory, nullptr, 0, 0);
		thread_handle_velocity = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)&thread_velocity, nullptr, 0, 0);


		WNDCLASSEX wc = {
			sizeof(WNDCLASSEX), CS_CLASSDC, WndProc, 0L, 0L, GetModuleHandle(NULL), NULL, LoadCursor(NULL, IDC_ARROW),
			NULL, NULL, (" "), NULL
		};
		RegisterClassEx(&wc);
		cucklord_get_window_position(415, 215);
		hwnd = CreateWindow((" "), (" "), WS_OVERLAPPEDWINDOW ^ WS_CAPTION ^ WS_THICKFRAME ^ WS_MAXIMIZEBOX,
		                    cucklord_horizontal, cucklord_vertical, 750, 410, NULL, NULL, wc.hInstance, NULL);
		if (CreateDeviceD3D(hwnd) < 0)
		{
			CleanupDeviceD3D();
			UnregisterClass((" "), wc.hInstance);
			return 1;
		}
		ShowWindow(hwnd, SW_SHOWDEFAULT);
		UpdateWindow(hwnd);
		ImGuiIO io = ImGui::GetIO();
		ImFont* cucklord_font = io.Fonts->AddFontFromMemoryCompressedTTF(
			fontData, 16, 16.0f, NULL, io.Fonts->GetGlyphRangesDefault());
		ImFont* cucklord_font_small = io.Fonts->AddFontFromMemoryCompressedTTF(
			fontData, 16, 16.0f, NULL, io.Fonts->GetGlyphRangesDefault());
		ImFont* cucklord_font_big = io.Fonts->AddFontFromMemoryCompressedTTF(
			fontData, 27, 27.0f, NULL, io.Fonts->GetGlyphRangesDefault());
		ImFont* cucklord_font1 = io.Fonts->AddFontFromMemoryCompressedTTF(
			font_compressed_data, 30, 30.0f, NULL, io.Fonts->GetGlyphRangesDefault());
		ImGui_ImplDX11_Init(hwnd, g_pd3dDevice, g_pd3dDeviceContext);
		ImGui::StyleColorsDark();
		ImVec4 clear_color = ImVec4(0.07f, 0.07f, 0.07f, 1.00f);
		ImGuiStyle& style = ImGui::GetStyle();
		MSG msg;
		ZeroMemory(&msg, sizeof(msg));
		io.IniFilename = NULL;
		while (msg.message != WM_QUIT)
		{
			if (PeekMessage(&msg, NULL, 0U, 0U, PM_REMOVE))
			{
				TranslateMessage(&msg);
				DispatchMessage(&msg);
				continue;
			}
			ImGui_ImplDX11_NewFrame();
			std::this_thread::sleep_for(std::chrono::milliseconds(5));
			//

			{

				ImGui::Begin("LOGO", NULL, ImGui_FLAGS); // can u make it the same purple as the sliders ye
				ImGui::SetWindowPos(ImVec2(60, 10)); ImGui::SetWindowSize(ImVec2(110, 60));
				ImGui::PushFont(cucklord_font_big); ImGui::PushStyleColor(ImGuiCol_Text, (ImVec4)ImColor(0.52f, 0.16f, 1.00f, 0.85f));
				ImGui::Text("CUCKLORD"); 
				ImGui::PopStyleColor(1);  ImGui::PopFont(); 
				ImGui::Text("      VERSION 1.4");
				ImGui::End();

				//cucklord_tab_main
				ImGui::Begin("TABS", NULL, ImGui_FLAGS);
				ImGui::SetWindowPos(ImVec2(3, 70));
				ImGui::SetWindowSize(ImVec2(6, 200));
				if (cucklord_tab_main == 1) {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				}
				else {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.15f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.10f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.05f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				} ImGui::Spacing();
				if (cucklord_tab_main == 2) {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				}
				else {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.15f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.10f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.05f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				} ImGui::End();

				ImGui::Begin("TABS2", NULL, ImGui_FLAGS);
				ImGui::SetWindowPos(ImVec2(3, 337));
				ImGui::SetWindowSize(ImVec2(6, 200));
				if (cucklord_tab_main == 3) {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.25f, 0.15f, 0.45f, 1.00f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				}
				else {
					ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.15f));
					ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.10f));
					ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.05f));
					ImGui::Button("  ", ImVec2(20, 30));
					ImGui::PopStyleColor(3);
				} ImGui::End();

				ImGui::Begin("TABS3", NULL, ImGui_FLAGS);
				ImGui::SetWindowPos(ImVec2(10, 70));
				ImGui::SetWindowSize(ImVec2(196, 200));
				ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.15f));
				ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.10f));
				ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.05f));
				if (ImGui::Button(cucklord_tab_main_vector[0], ImVec2(200, 30))) { cucklord_tab_main = 1; } ImGui::Spacing();
				if (ImGui::Button(cucklord_tab_main_vector[1], ImVec2(200, 30))) { cucklord_tab_main = 2; }ImGui::Spacing();
				ImGui::PopStyleColor(3);
				ImGui::End();

				ImGui::Begin("TABS4", NULL, ImGui_FLAGS);
				ImGui::SetWindowPos(ImVec2(10, 337));
				ImGui::SetWindowSize(ImVec2(196, 200));
				ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.15f));
				ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.10f));
				ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.15f, 0.15f, 0.15f, 0.05f));
				if (ImGui::Button(cucklord_tab_main_vector[2], ImVec2(200, 30))) { cucklord_tab_main = 3; }
				ImGui::PopStyleColor(3);
				ImGui::End();

				ImGui::Begin("LINE", NULL, ImGui_FLAGS);
				ImGui::SetWindowPos(ImVec2(205, -1));
				ImGui::SetWindowSize(ImVec2(3, 550));
				ImGui::PushStyleColor(ImGuiCol_Button, (ImVec4)ImColor(0.00f, 0.00f, 0.00f, 0.50f));
				ImGui::PushStyleColor(ImGuiCol_ButtonHovered, (ImVec4)ImColor(0.00f, 0.00f, 0.00f, 0.50f));
				ImGui::PushStyleColor(ImGuiCol_ButtonActive, (ImVec4)ImColor(0.00f, 0.00f, 0.00f, 0.50f));
				ImGui::Button("###", ImVec2(2, 550));
				ImGui::PopStyleColor(3);
				ImGui::End();
			}

			{
				if (cucklord_tab_main == 1)
				{
					ImGui::Begin("CLICKERind", NULL, ImGui_FLAGS);
					ImGui::SetWindowPos(ImVec2(220, 10));
					ImGui::SetWindowSize(ImVec2(350, 95));
					{
						if (cucklord_clicker_enabled) {
						}
						else {

						}
					}ImGui::End();

					ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(5, 0));
					ImGui::PushStyleColor(ImGuiCol_Border, (ImVec4)ImColor(1.00f, 1.00f, 1.00f, 0.05f));
					ImGui::Begin("AUTO CLICKER", NULL, ImGui_FLAGS2);
					ImGui::SetWindowPos(ImVec2(220, 10));
					ImGui::SetWindowSize(ImVec2(380, 137));
					{
						ImGui::Spacing();
						ImGui::PushItemWidth(300);
						ImGui::SliderFloat("  CPS", &cucklord_clicker_value, 5, 20, "%.1f"); ImGui::Spacing();
						ImGui::SliderFloat("  JITTER", &cucklord_clicker_jitter_value, 0, 15, "%.1f");
						ImGui::Spacing();
						ImGui::PushStyleColor(ImGuiCol_Text, (ImVec4)ImColor(0.05f, 0.05f, 0.05f, 1.00f));
						if (!cucklord_clicker_enabled)
						{
							if (ImGui::Button(xor_string("TOGGLE ON"), ImVec2(149, 24)))
							{
								cucklord_clicker_enabled = !cucklord_clicker_enabled;
							}
						}
						else
						{
							if (ImGui::Button(xor_string("TOGGLE OFF"), ImVec2(149, 24)))
							{
								cucklord_clicker_enabled = !cucklord_clicker_enabled;
							}
						}
						ImGui::SameLine();
						ImGui::Text(" ");
						ImGui::SameLine();
						if (ImGui::Button(xor_string(cucklord_clicker_bind_text.c_str()), ImVec2(149, 24)))
						{
							cucklord_clicker_bind_text = "?";
							cucklord_clicker_bind_pressed = true;
						}
						ImGui::Spacing();
						ImGui::PopStyleColor(1);
						ImGui::Checkbox("  MC ONLY", &cucklord_clicker_minecraftonly);
						ImGui::Spacing();
						ImGui::Checkbox("  INVENTORY", &cucklord_clicker_inventory);
					}
					ImGui::End();
					ImGui::PopStyleColor(1);
					ImGui::PopStyleVar(1);

					ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(5, 0));
					ImGui::PushStyleColor(ImGuiCol_Border, (ImVec4)ImColor(1.00f, 1.00f, 1.00f, 0.05f));
					ImGui::Begin("REACH", NULL, ImGui_FLAGS2);
					ImGui::SetWindowPos(ImVec2(220, 160));
					ImGui::SetWindowSize(ImVec2(380, 75));
					{
						ImGui::Spacing();
						ImGui::PushItemWidth(300);
						ImGui::SliderFloat(" RANGE", &cucklord_reach_value, 3, 6, "%.2f");
						ImGui::Spacing();
						ImGui::PushStyleColor(ImGuiCol_Text, (ImVec4)ImColor(0.05f, 0.05f, 0.05f, 1.00f));
						if (!cucklord_reach_enabled)
						{
							if (ImGui::Button(xor_string("TOGGLE ON"), ImVec2(149, 24)))
							{
								cucklord_reach_enabled = !cucklord_reach_enabled;
							}
						}
						else
						{
							if (ImGui::Button(xor_string("TOGGLE OFF"), ImVec2(149, 24)))
							{
								cucklord_reach_enabled = !cucklord_reach_enabled;
							}
						}
						ImGui::SameLine();
						ImGui::Text(" ");
						ImGui::SameLine();
						if (ImGui::Button(xor_string(cucklord_reach_bind_text.c_str()), ImVec2(149, 24)))
						{
							cucklord_reach_bind_text = "?";
							cucklord_reach_bind_pressed = true;
						}
						ImGui::Spacing();
						ImGui::PopStyleColor(1);
					}
					ImGui::End();
					ImGui::PopStyleColor(1);
					ImGui::PopStyleVar(1);

					ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(5, 0));
					ImGui::PushStyleColor(ImGuiCol_Border, (ImVec4)ImColor(1.00f, 1.00f, 1.00f, 0.05f));
					ImGui::Begin("VELOCITY", NULL, ImGui_FLAGS2);
					ImGui::SetWindowPos(ImVec2(220, 248));
					ImGui::SetWindowSize(ImVec2(380, 75));
					{
						ImGui::Spacing();
						ImGui::PushItemWidth(300);
						ImGui::SliderFloat("  X & Z", &cucklord_velocity_value, 1, 100, "%.2f");
						ImGui::Spacing();
						ImGui::PushStyleColor(ImGuiCol_Text, (ImVec4)ImColor(0.05f, 0.05f, 0.05f, 1.00f));
						if (!cucklord_velocity_enabled)
						{
							if (ImGui::Button(xor_string("TOGGLE ON"), ImVec2(149, 24)))
							{
								cucklord_velocity_enabled = !cucklord_velocity_enabled;
							}
						}
						else
						{
							if (ImGui::Button(xor_string("TOGGLE OFF"), ImVec2(149, 24)))
							{
								cucklord_velocity_enabled = !cucklord_velocity_enabled;
							}
						}
						ImGui::SameLine();
						ImGui::Text(" ");
						ImGui::SameLine();
						if (ImGui::Button(xor_string(cucklord_velocity_bind_text.c_str()), ImVec2(149, 24)))
						{
							cucklord_velocity_bind_text = "?";
							cucklord_velocity_bind_pressed = true;
						}
						ImGui::Spacing();
						ImGui::PopStyleColor(1);
					}
					ImGui::End();
					ImGui::PopStyleColor(1);
					ImGui::PopStyleVar(1);
				}

				if (cucklord_tab_main == 2)
				{
					ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(5, 0));
					ImGui::PushStyleColor(ImGuiCol_Border, (ImVec4)ImColor(1.00f, 1.00f, 1.00f, 0.05f));
					ImGui::Begin("TIMER", NULL, ImGui_FLAGS2);
					ImGui::SetWindowPos(ImVec2(220, 10));
					ImGui::SetWindowSize(ImVec2(380, 75));
					{
						ImGui::Spacing();
						ImGui::PushItemWidth(300);
						ImGui::SliderFloat("  SPEED", &cucklord_speed_value, -1, 1, "%.2f");
						ImGui::Spacing();
						ImGui::PushStyleColor(ImGuiCol_Text, (ImVec4)ImColor(0.05f, 0.05f, 0.05f, 1.00f));
						if (!cucklord_speed_enabled)
						{
							if (ImGui::Button(xor_string("TOGGLE ON"), ImVec2(149, 24)))
							{
								cucklord_speed_enabled = !cucklord_speed_enabled;
							}
						}
						else
						{
							if (ImGui::Button(xor_string("TOGGLE OFF"), ImVec2(149, 24)))
							{
								cucklord_speed_enabled = !cucklord_speed_enabled;
							}
						}
						ImGui::SameLine();
						ImGui::Text(" ");
						ImGui::SameLine();
						if (ImGui::Button(xor_string(cucklord_speed_bind_text.c_str()), ImVec2(149, 24)))
						{
							cucklord_speed_bind_text = "?";
							cucklord_speed_bind_pressed = true;
						}
						ImGui::Spacing();
						ImGui::PopStyleColor(1);
					}
					ImGui::End();
					ImGui::PopStyleColor(1);
					ImGui::PopStyleVar(1);
				}
				if (cucklord_tab_main == 3)
				{
					ImGui::PushStyleVar(ImGuiStyleVar_WindowPadding, ImVec2(5, 0));
					ImGui::PushStyleColor(ImGuiCol_Border, (ImVec4)ImColor(1.00f, 1.00f, 1.00f, 0.05f));
					ImGui::Begin("DESTRUCT", NULL, ImGui_FLAGS2);
					ImGui::SetWindowPos(ImVec2(220, 10));
					ImGui::SetWindowSize(ImVec2(380, 69));
					{
						ImGui::Spacing();
						ImGui::Checkbox(cucklord_destruct_options_text[0], &cucklord_destruct_clean_strings);
						ImGui::Spacing();
						ImGui::Checkbox(cucklord_destruct_options_text[1], &cucklord_destruct_selfdelete);
					}
					ImGui::End();
					ImGui::PopStyleColor(1);
					ImGui::PopStyleVar(1);

				}
			}

			g_pd3dDeviceContext->OMSetRenderTargets(1, &g_mainRenderTargetView, NULL);
			g_pd3dDeviceContext->ClearRenderTargetView(g_mainRenderTargetView, (float*)&clear_color);
			ImGui::Render();
			g_pSwapChain->Present(0, 0);
		}
		ImGui_ImplDX11_Shutdown();
		CleanupDeviceD3D();
		UnregisterClass((" "), wc.hInstance);
		return 0;
	}
	else
	{
		std::cout << "Auth error, hwid has been copied to your clipboard, make a ticket to get whitelisted." << std::endl;
		std::cin.get();
		exit(EXIT_SUCCESS);
	}
}

void CreateRenderTarget()
{
	DXGI_SWAP_CHAIN_DESC sd;
	g_pSwapChain->GetDesc(&sd);

	// Create the render target
	ID3D11Texture2D* pBackBuffer;
	D3D11_RENDER_TARGET_VIEW_DESC render_target_view_desc;
	ZeroMemory(&render_target_view_desc, sizeof(render_target_view_desc));
	render_target_view_desc.Format = sd.BufferDesc.Format;
	render_target_view_desc.ViewDimension = D3D11_RTV_DIMENSION_TEXTURE2D;
	g_pSwapChain->GetBuffer(0, __uuidof(ID3D11Texture2D), (LPVOID*)&pBackBuffer);
	g_pd3dDevice->CreateRenderTargetView(pBackBuffer, &render_target_view_desc, &g_mainRenderTargetView);
	pBackBuffer->Release();
}

void CleanupRenderTarget()
{
	if (g_mainRenderTargetView)
	{
		g_mainRenderTargetView->Release();
		g_mainRenderTargetView = NULL;
	}
}

HRESULT CreateDeviceD3D(HWND hWnd)
{
	// Setup swap chain
	DXGI_SWAP_CHAIN_DESC sd;
	{
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
	}

	UINT createDeviceFlags = 0;
	//createDeviceFlags |= D3D11_CREATE_DEVICE_DEBUG;
	D3D_FEATURE_LEVEL featureLevel;
	const D3D_FEATURE_LEVEL featureLevelArray[2] = {D3D_FEATURE_LEVEL_11_0, D3D_FEATURE_LEVEL_10_0,};
	if (D3D11CreateDeviceAndSwapChain(NULL, D3D_DRIVER_TYPE_HARDWARE, NULL, createDeviceFlags, featureLevelArray, 2,
	                                  D3D11_SDK_VERSION, &sd, &g_pSwapChain, &g_pd3dDevice, &featureLevel,
	                                  &g_pd3dDeviceContext) != S_OK)
		return E_FAIL;

	CreateRenderTarget();

	return S_OK;
}

void CleanupDeviceD3D()
{
	CleanupRenderTarget();
	if (g_pSwapChain)
	{
		g_pSwapChain->Release();
		g_pSwapChain = NULL;
	}
	if (g_pd3dDeviceContext)
	{
		g_pd3dDeviceContext->Release();
		g_pd3dDeviceContext = NULL;
	}
	if (g_pd3dDevice)
	{
		g_pd3dDevice->Release();
		g_pd3dDevice = NULL;
	}
}

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
			ImGui_ImplDX11_InvalidateDeviceObjects();
			CleanupRenderTarget();
			g_pSwapChain->ResizeBuffers(0, (UINT)LOWORD(lParam), (UINT)HIWORD(lParam), DXGI_FORMAT_UNKNOWN, 0);
			CreateRenderTarget();
			ImGui_ImplDX11_CreateDeviceObjects();
		}
		return 0;
	case WM_SYSCOMMAND:
		if ((wParam & 0xfff0) == SC_KEYMENU) // Disable ALT application menu
			return 0;
		break;
	case WM_DESTROY:
		PostQuitMessage(0);
		return 0;
	}
	return DefWindowProc(hWnd, msg, wParam, lParam);
}
