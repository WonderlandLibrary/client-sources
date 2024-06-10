//hayden was here

#pragma once

namespace cfg
{
	namespace ac
	{
		bool enabled = false;
		bool rightenabled = false;
		int acbind = 0;
		int min_cps = 0;
		int max_cps = 0;
		bool onlymc = false;
		char windowname[ 128 ];
	}

	namespace utils
	{
		int switchTab = 1;
		int acTab = 1;
		int rgb_2[ 3 ] = { 252, 252, 252 };
		int rgb[ 3 ] = { 255, 0, 0 };
		int orange[ 3 ] = { 255, 0, 0 };
	}
}