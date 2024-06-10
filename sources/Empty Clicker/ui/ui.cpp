#include "..\includes.hpp"

extern IMGUI_IMPL_API LRESULT ImGui_ImplWin32_WndProcHandler( HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam );

auto gui::render( HWND hwnd, int width, int height ) -> void
{
	ImGui::SetNextWindowSize( ImVec2( width, height ), ImGuiCond_Always );
	ImGui::SetNextWindowPos( ImVec2( 0, 0 ), ImGuiCond_Always );
	ImGui::Begin( "##imgui_begin", nullptr, ImGuiWindowFlags_NoTitleBar | ImGuiWindowFlags_NoResize | ImGuiWindowFlags_NoMove );
	{
		auto draw = ImGui::GetWindowDrawList( );

		if ( ImGui::IsMouseDragging( ImGuiMouseButton_Left ) && location.second >= 0 && location.second <= ( ImGui::GetTextLineHeight( ) + ImGui::GetStyle( ).FramePadding.y * 2 ) )
		{
			POINT point;
			GetCursorPos ( &point );
			SetWindowPos ( hwnd, nullptr, point.x - location.first, point.y - location.second, 0, 0, SWP_NOSIZE | SWP_NOZORDER );

			std::this_thread::sleep_for( std::chrono::milliseconds( 10 ) );
		}

		ImGui::SetCursorPos( { 9, 6 } );
		ImGui::Text( "Empty Clicker" );
		if ( ImGui::IsItemHovered( 0 ) )
		{
			ImGui::PushStyleColor( ImGuiCol_Border, static_cast< ImVec4 > ( ImColor( 17, 17, 17 ) ) );
			ImGui::SetTooltip( "https://www.empty.wtf/" );
			ImGui::PopStyleColor( );
		}

		ImGui::PushFont( tahoma_bold );

		ImGui::SetCursorPos( { 183, 6 } );
		ImGui::Text( "X" );
		if ( ImGui::IsItemClicked( 0 ) )
			std::exit( 0 );

		ImGui::PopFont( );

		draw->AddLine( { 10, 27 }, { 75, 27 }, ImColor( 255, 255, 255 ), 1 );

		if ( vars.right_tab )
		{
			ImGui::SetCursorPos( { 9, 40 } );
			ImGui::Checkbox( "Right Clicker", &vars.right_clicker );

			ImGui::PushFont( this->tahoma_min );

			ImGui::SetCursorPos( { 125, 40 } );
			if ( ImGui::Button( vars.key_right.c_str( ), { 54, 18 } ) )
				vars.pressed_bind_right = true, vars.key_right = "press a key";

			ImGui::PopFont( );

			ImGui::PushItemWidth( 170 );

			ImGui::SetCursorPos( { 9, 65 } );
			ImGui::SliderInt( "###right_clicker_slider", &vars.right_value, 1, 25 );

			ImGui::PopItemWidth( );
		}
		else
		{
			ImGui::SetCursorPos( { 9, 40 } );
			ImGui::Checkbox( "Left Clicker", &vars.left_clicker );

			ImGui::PushFont( this->tahoma_min );

			ImGui::SetCursorPos( { 125, 40 } );
			if ( ImGui::Button( vars.key_left.c_str( ), { 54, 18 } ) )
				vars.pressed_bind_left = true, vars.key_left = "press a key";

			ImGui::PopFont( );

			ImGui::PushItemWidth( 170 );

			ImGui::SetCursorPos( { 9, 65 } );
			ImGui::SliderInt( "###left_clicker_slider", &vars.left_value, 1, 25 );

			ImGui::PopItemWidth( );
		}

		ImGui::SetCursorPos( { 9, 94 } );
		ImGui::Checkbox( "Hide Window", &vars.hide_window );

		if ( vars.hide_window )
		{
			if ( GetKeyState( 0x2D ) & 0x8000 && !vars.pressed_hide_window )
				vars.pressed_hide_window = true;

			if ( !( GetKeyState( 0x2D ) & 0x8000 ) && vars.pressed_hide_window )
				vars.is_hide_pressed = !vars.is_hide_pressed, ( vars.is_hide_pressed ) ? ShowWindow( hwnd, SW_HIDE ) : ShowWindow( hwnd, SW_SHOW ), vars.pressed_hide_window = false;
		}

		ImGui::SetCursorPos( { 115, 93 } );
		ImGui::Text( "(?)" );
		if ( ImGui::IsItemHovered( 0 ) )
		{
			ImGui::PushFont( this->tahoma_min ); ImGui::PushStyleColor( ImGuiCol_Border, static_cast< ImVec4 > ( ImColor( 17, 17, 17 ) ) );
			ImGui::SetTooltip( "press insert to hide" );
			ImGui::PopStyleColor( ); ImGui::PopFont( );
		}

		ImGui::SetCursorPos( { 175, 125 } );
		ImGui::Checkbox( "###right_tab", &vars.right_tab );

	}
	ImGui::End( );
}

auto gui::wndproc( HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam ) -> LRESULT __stdcall
{
	if ( ImGui_ImplWin32_WndProcHandler( hWnd, msg, wParam, lParam ) )
		return true;

	if ( msg == WM_DESTROY )
	{
		PostQuitMessage ( 0 );
		return 0;
	}

	return DefWindowProcA( hWnd, msg, wParam, lParam );
}

auto gui::create( int width, int height ) -> bool
{
	WNDCLASSEX wc =
	{
		sizeof( WNDCLASSEX ),
		CS_CLASSDC,
		( WNDPROC ) wndproc,
		0L,
		0L,
		GetModuleHandle( NULL ),
		NULL,
		NULL,
		NULL,
		NULL,
		_T( " " ),
		NULL
	};

	RegisterClassExA( &wc );

	auto hwnd = CreateWindow
	(
		wc.lpszClassName,
		_T( " " ),
		WS_POPUP,
		100,
		100,
		width,
		height,
		NULL,
		NULL,
		wc.hInstance,
		NULL
	);

	if ( !ui.create_device_d3d( hwnd ) )
	{
		ui.cleanup_device_d3d( );
		UnregisterClass( wc.lpszClassName, wc.hInstance );
		return false;
	}

	ui.set_window_in_center( hwnd );

	ShowWindow( hwnd, SW_SHOWDEFAULT );
	UpdateWindow( hwnd );

	ImGui::CreateContext( );

	ui.setup( );

	ImGui_ImplWin32_Init( hwnd );
	ImGui_ImplDX9_Init( g_pd3dDevice );

	ImVec4 clear_color = static_cast< ImVec4 >( ImColor( 0, 0, 0, 0 ) );

	if ( 1000.f / ImGui::GetIO( ).Framerate < 1000.f / 60 )
		std::this_thread::sleep_for( std::chrono::milliseconds( ( long long ) ( 1000.f / 60 ) ) );

	MSG msg {};
	std::memset( &msg, 0, sizeof( msg ) );

	while ( msg.message != WM_QUIT )
	{
		if ( PeekMessage( &msg, NULL, 0U, 0U, PM_REMOVE ) )
		{
			TranslateMessage( &msg );
			DispatchMessage( &msg );
			continue;
		}

		ImGui_ImplDX9_NewFrame( );
		ImGui_ImplWin32_NewFrame( );

		ImGui::NewFrame( );

		if ( ImGui::IsMouseClicked( ImGuiMouseButton_::ImGuiMouseButton_Left ) )
		{
			POINT point; RECT rect;

			GetCursorPos( &point );
			GetWindowRect( hwnd, &rect );

			location.first = point.x - rect.left;
			location.second = point.y - rect.top;
		}

		ui.render( hwnd, width, height );

		ImGui::EndFrame( );

		g_pd3dDevice->SetRenderState( D3DRS_ZENABLE, FALSE );
		g_pd3dDevice->SetRenderState( D3DRS_ALPHABLENDENABLE, TRUE );
		g_pd3dDevice->SetRenderState( D3DRS_SCISSORTESTENABLE, FALSE );

		D3DCOLOR clear_col_dx = D3DCOLOR_RGBA
		(
			( int ) ( clear_color.x * 255.0f ),
			( int ) ( clear_color.y * 255.0f ),
			( int ) ( clear_color.z * 255.0f ),
			( int ) ( clear_color.w * 255.0f )
		);

		g_pd3dDevice->Clear( 0, NULL, D3DCLEAR_TARGET | D3DCLEAR_ZBUFFER, clear_col_dx, 1.0f, 0 );

		if ( g_pd3dDevice->BeginScene( ) >= 0 )
		{
			ImGui::Render( );
			ImGui_ImplDX9_RenderDrawData( ImGui::GetDrawData( ) );
			g_pd3dDevice->EndScene( );
		}

		HRESULT result = g_pd3dDevice->Present( NULL, NULL, NULL, NULL );

		if ( result == D3DERR_DEVICELOST && g_pd3dDevice->TestCooperativeLevel( ) == D3DERR_DEVICENOTRESET )
		{
			ImGui_ImplDX9_InvalidateDeviceObjects( );

			HRESULT hr = g_pd3dDevice->Reset( &g_d3dpp );
			if ( hr == D3DERR_INVALIDCALL )
				IM_ASSERT( 0 );

			ImGui_ImplDX9_CreateDeviceObjects( );
		}
	}

	ImGui_ImplDX9_Shutdown( );
	ImGui_ImplWin32_Shutdown( );
	ImGui::DestroyContext( );

	ui.cleanup_device_d3d( );
	DestroyWindow( hwnd );
	UnregisterClass( wc.lpszClassName, wc.hInstance );

	return true;
}
