#include <windows.h>
#include "misc.hpp"
#include "../settings.hpp"

void modules::misc_settings::thread() // kinda messy
{
	static bool is_hidden = false;
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->settings.hide.bind) & 0x8000 && settings->settings.hide.enabled) {
			is_hidden = !is_hidden;
			
			long style = GetWindowLong(settings->hWnd, GWL_STYLE);
			if (is_hidden)
				style &= ~(WS_VISIBLE);
			else
				style |= (WS_VISIBLE);

			ShowWindow(settings->hWnd, is_hidden ? SW_HIDE : SW_SHOW);
			SetWindowLong(settings->hWnd, GWL_STYLE, style);
			Sleep(200);
		}

		if (GetAsyncKeyState(settings->settings.streamproof.bind) & 0x8000) {
			settings->settings.streamproof.enabled = !settings->settings.streamproof.enabled;
			Sleep(200);
		}

		//streamproof
		SetWindowDisplayAffinity(settings->hWnd, settings->settings.streamproof.enabled ? WDA_EXCLUDEFROMCAPTURE : WDA_NONE);

		Sleep(1);
	}
}
