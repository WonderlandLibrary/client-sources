#pragma once

class gui 
{
private:
	std::pair<int, int> location;
	int tabs = 0;

	LPDIRECT3D9 g_pD3D {};
	LPDIRECT3DDEVICE9 g_pd3dDevice {};
	D3DPRESENT_PARAMETERS g_d3dpp {};

	ImFont *tahoma, *tahoma_bold, *tahoma_min;

	auto setup( ) -> void
	{
		auto &style = ImGui::GetStyle( );
		ImGuiIO &io = ImGui::GetIO( );

		/* -> .init file <- */

		io.IniFilename = nullptr;

		/* -> clicker styles <- */

		style.WindowPadding = { 0, 0 };
		style.WindowBorderSize = 1;

		/* -> clicker colors <- */

		style.Colors[ ImGuiCol_WindowBg ] = ImColor( 25, 30, 35 );
		style.Colors[ ImGuiCol_Border ] = ImColor( 112, 209, 237 );

		style.Colors[ ImGuiCol_SliderGrab ] = ImColor( 112, 209, 237 );
		style.Colors[ ImGuiCol_SliderGrabActive ] = ImColor( 112, 209, 237 );

		style.Colors[ ImGuiCol_FrameBg ] = ImColor( 22, 27, 31 );
		style.Colors[ ImGuiCol_FrameBgActive ] = ImColor( 22, 27, 31 );
		style.Colors[ ImGuiCol_FrameBgHovered ] = ImColor( 22, 27, 31 );

		style.Colors[ ImGuiCol_CheckMark ] = ImColor( 112, 209, 237 );

		style.Colors[ ImGuiCol_Button ] = ImColor( 112, 209, 237 );
		style.Colors[ ImGuiCol_ButtonActive ] = ImColor( 75, 175, 204 );
		style.Colors[ ImGuiCol_ButtonHovered ] = ImColor( 75, 175, 204 );

		/* -> imgui fonts <- */

		this->tahoma = io.Fonts->AddFontFromFileTTF( R"(C:\Windows\Fonts\Tahoma.ttf)", 16 );
		this->tahoma_min = io.Fonts->AddFontFromFileTTF( R"(C:\Windows\Fonts\Tahoma.ttf)", 11 );
		this->tahoma_bold = io.Fonts->AddFontFromFileTTF( R"(C:\Windows\Fonts\Tahomabd.ttf)", 15 );
	}

	auto render( HWND hwnd, int width, int height ) -> void;

public:
	static auto wndproc( HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam ) -> LRESULT __stdcall;

	auto create( int width, int height ) -> bool;

	auto set_window_in_center( HWND hwnd ) -> void
	{
		RECT rc;
		GetWindowRect( hwnd, &rc );

		int x = ( GetSystemMetrics( SM_CXSCREEN ) - rc.right ) / 2;
		int y = ( GetSystemMetrics( SM_CYSCREEN ) - rc.bottom ) / 2;

		SetWindowPos( hwnd, nullptr, x, y, 0, 0, SWP_NOZORDER | SWP_NOSIZE );
	}

	auto create_device_d3d( HWND hWnd ) -> bool
	{
		if ( ( g_pD3D = Direct3DCreate9( D3D_SDK_VERSION ) ) == NULL )
			return false;
		
		std::memset( &g_d3dpp, 0, sizeof( g_d3dpp ) );
		g_d3dpp.Windowed = TRUE;
		g_d3dpp.SwapEffect = D3DSWAPEFFECT_DISCARD;
		g_d3dpp.BackBufferFormat = D3DFMT_UNKNOWN;
		g_d3dpp.EnableAutoDepthStencil = TRUE;
		g_d3dpp.AutoDepthStencilFormat = D3DFMT_D16;
		g_d3dpp.PresentationInterval = D3DPRESENT_INTERVAL_ONE;

		if ( g_pD3D->CreateDevice( D3DADAPTER_DEFAULT, D3DDEVTYPE_HAL, hWnd, D3DCREATE_HARDWARE_VERTEXPROCESSING, &g_d3dpp, &g_pd3dDevice ) < 0 )
			return false;

		return true;
	}

	auto cleanup_device_d3d( ) -> void
	{
		if ( g_pd3dDevice )
			g_pd3dDevice->Release( ), g_pd3dDevice = NULL;

		if ( g_pD3D )
			g_pD3D->Release( ), g_pD3D = NULL;
	}
};

inline auto ui = gui( );