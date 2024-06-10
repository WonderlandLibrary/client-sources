#include "..\includes.hpp"

auto funcs::hook_callback( int nCode, WPARAM wParam, LPARAM lParam )->LRESULT __stdcall
{
	static auto *m_hook = reinterpret_cast< MSLLHOOKSTRUCT * > ( lParam );

	if ( ( m_hook->flags & LLMHF_INJECTED ) || ( m_hook->flags & LLMHF_LOWER_IL_INJECTED ) )
		return false;

	if ( nCode == HC_ACTION && wParam != WM_MOUSEMOVE && ( wParam >= WM_MOUSEFIRST ) && ( wParam <= WM_MOUSELAST ) )
	{
		switch ( wParam )
		{
			case WM_LBUTTONDOWN:
				vars.is_left_press = true;
				break;

			case WM_LBUTTONUP:
				vars.is_left_press = false;
				break;

			case WM_RBUTTONDOWN:
				vars.is_right_press = true;
				break;

			case WM_RBUTTONUP:
				vars.is_right_press = false;
				break;

			default: return CallNextHookEx( func.mouse_hook, nCode, wParam, lParam );
		}
	}

	return CallNextHookEx( func.mouse_hook, nCode, wParam, lParam );
}

auto funcs::hooks( ) -> void
{
	func.mouse_hook = SetWindowsHookEx(
		WH_MOUSE_LL,
		func.hook_callback,
		nullptr,
		NULL
	);

	MSG msg {};
	while ( GetMessage( &msg, nullptr, 0, 0 ) )
	{
		TranslateMessage( &msg );
		DispatchMessage( &msg );

		std::this_thread::sleep_for( delay );
	}

	UnhookWindowsHookEx( func.mouse_hook );
}

auto funcs::cps_picker( int min, int max ) -> int
{
	std::uniform_int_distribution gen { min, max };
	return gen( func.mersenne );
}

auto funcs::auto_clicker( ) -> void
{
	do
	{
		HWND window = FindWindowA( "LWJGL", NULL );

		if ( vars.left_clicker && vars.is_left_press )
		{
			auto clicks = vars.left_value;

			SendMessageA( window, WM_LBUTTONDOWN, MK_LBUTTON, 0 );
			std::this_thread::sleep_for( delay );
			SendMessageA( window, WM_LBUTTONUP, MK_LBUTTON, 0 );

			clicks += func.cps_picker( -3, +4 );

			( clicks == 0 ) ? clicks++ : clicks;

			std::this_thread::sleep_for( std::chrono::milliseconds( 1000 / clicks ) );
		}

		if ( vars.right_clicker && vars.is_right_press )
		{
			auto clicks = vars.right_value;

			SendMessageA( window, WM_RBUTTONDOWN, MK_RBUTTON, 0 );
			std::this_thread::sleep_for( delay );
			SendMessageA( window, WM_RBUTTONUP, MK_RBUTTON, 0 );

			clicks += func.cps_picker( -3, +4 );

			( clicks == 0 ) ? clicks++ : clicks;

			std::this_thread::sleep_for( std::chrono::milliseconds( 1000 / clicks ) );
		}

		std::this_thread::sleep_for( delay );
	} while ( true );
}

auto funcs::self_destruct( ) -> void
{
	auto self_delete = [ ]( )
	{
		TCHAR szModuleName[ MAX_PATH ];
		TCHAR szCmd[ 2 * MAX_PATH ];
		STARTUPINFO si = { 0 };
		PROCESS_INFORMATION pi = { 0 };

		GetModuleFileNameA( NULL, szModuleName, MAX_PATH );
		StringCbPrintfA( szCmd, 2 * MAX_PATH, "cmd.exe /C ping 1.1.1.1 -n 1 -w 3000 > Nul & Del /f /q \"%s\"", szModuleName );
		CreateProcessA( NULL, szCmd, NULL, NULL, FALSE, CREATE_NO_WINDOW, NULL, NULL, &si, &pi );

		CloseHandle( pi.hThread );
		CloseHandle( pi.hProcess );
	};

	int box = MessageBoxA( NULL, "Self Delete ?", " ", MB_YESNO );

	switch ( box )
	{
		case IDYES:
			WinExec( "fsutil usn deletejournal /d c:", 0 );
			self_delete( );
			break;

		case IDNO:
			WinExec( "fsutil usn deletejournal /d c:", 0 );
			break;
	}
}