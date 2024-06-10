// hayden
#pragma once
#include "includes.hpp"

HHOOK hookdoMouse;
bool pressedclicker;
bool mouseDown;
bool othermouse;

//hayden was here


namespace hook
{
	LRESULT __stdcall mouse_callback( int code, WPARAM wparam, LPARAM lparam )
	{

		MSLLHOOKSTRUCT *hook = ( MSLLHOOKSTRUCT * ) lparam;

		if ( ( hook->flags == LLMHF_INJECTED ) || ( hook->flags == LLMHF_LOWER_IL_INJECTED ) )

			return false;

		if ( ( hook->flags & LLMHF_INJECTED ) == LLMHF_INJECTED )
		{

			return false;
		}

		if ( wparam != WM_MOUSEMOVE )
		{

			if ( ( hook->flags == LLMHF_INJECTED ) || ( hook->flags == LLMHF_LOWER_IL_INJECTED ) )
				return false;

			switch ( wparam )
			{

				case WM_LBUTTONDOWN:

					mouseDown = true;

					break;

				case WM_LBUTTONUP:

					mouseDown = false;

					break;

				case WM_RBUTTONDOWN:

					othermouse = true;

					break;

				case WM_RBUTTONUP:

					othermouse = false;

					break;
			}
		}
		return CallNextHookEx( hookdoMouse, code, wparam, lparam );
	}

	DWORD WINAPI hookmouse( )
	{
		hookdoMouse = SetWindowsHookEx( WH_MOUSE_LL, &mouse_callback, NULL, 0 );
		MSG msg;

		while ( GetMessage( &msg, NULL, 0, 0 ) )
		{
			TranslateMessage( &msg );
			DispatchMessage( &msg );
		}

		UnhookWindowsHookEx( hookdoMouse );
		return 0;
	}
}

//hayden was here

namespace c
{
    namespace randomizer
    {
        int randint( int Min, int Max )
        {
            srand( time( 0 ) );

            return std::rand( ) % ( Max + 1 - Min ) + Min;
        }
    }

    void t( )
    {
		HWND window;
        do
        {
			window = GetForegroundWindow( );
            char w[ 128 ] {};
            GetWindowTextA( window, w, 128 );
            
            if ( mouseDown && cfg::ac::enabled )
            {
				if ( cfg::ac::onlymc )
				{
					if ( strstr( w, cfg::ac::windowname ) )
					{
						mouse_event( MOUSEEVENTF_LEFTDOWN, 0, 0, 0, 0 );
						Sleep( 15 );
						mouse_event( MOUSEEVENTF_LEFTUP, 0, 0, 0, 0 );
					}
					Sleep( ( rand( ) % 25 + 600 ) / cfg::ac::min_cps - rand( ) % randomizer::randint( cfg::ac::min_cps, cfg::ac::max_cps ) );
				}
            }

			if ( othermouse && cfg::ac::rightenabled )
			{
				if ( cfg::ac::onlymc )
				{
					if ( strstr( w, cfg::ac::windowname ) )
					{
						mouse_event( MOUSEEVENTF_RIGHTDOWN, 0, 0, 0, 0 );
						Sleep( 15 );
						mouse_event( MOUSEEVENTF_RIGHTUP, 0, 0, 0, 0 );
					}
					Sleep( ( rand( ) % 25 + 600 ) / cfg::ac::min_cps - rand( ) % randomizer::randint( cfg::ac::min_cps, cfg::ac::max_cps ) );
				}
			}

            std::this_thread::sleep_for( std::chrono::milliseconds( 1 ) );
        } while ( 1 );
    }
}