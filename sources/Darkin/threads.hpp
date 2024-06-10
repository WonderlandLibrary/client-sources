#pragma once
#include "config.hpp"
#include "includes.hpp"
//hayden was here


namespace thread
{
	namespace fix
	{
		
		void slider( )
		{
			do
			{
				if ( cfg::ac::min_cps > cfg::ac::max_cps )
					cfg::ac::max_cps++;

				if ( cfg::ac::max_cps >= 20 )
					cfg::ac::max_cps = 20;

				if ( cfg::ac::min_cps >= 20 )
					cfg::ac::min_cps = 20;

				if ( cfg::ac::max_cps <= 1 )
					cfg::ac::max_cps = 1;

				if ( cfg::ac::min_cps <= 1 )
					cfg::ac::min_cps = 1;

				std::this_thread::sleep_for( std::chrono::milliseconds( 1 ) );
			} while ( 1 );
		}

	}

	namespace ac
	{
		void keybind_check( )
		{
			do
			{
				if ( GetAsyncKeyState( cfg::ac::acbind ) )
				{
					Sleep( 100 );
					cfg::ac::enabled = !cfg::ac::enabled;
				}

				Sleep( 1 );
			} while ( 1 );
		}
	}
}