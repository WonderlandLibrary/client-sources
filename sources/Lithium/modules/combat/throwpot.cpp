#include "..\..\klynt.h"
#include "..\..\klynt.h"

int getbindcode(int lith_throwpot_slot)
{
	if (lith_throwpot_slot == 1) { return v::local_player::binds::slot1; }
	if (lith_throwpot_slot == 2) { return v::local_player::binds::slot2; }
	if (lith_throwpot_slot == 3) { return v::local_player::binds::slot3; }
	if (lith_throwpot_slot == 4) { return v::local_player::binds::slot4; }
	if (lith_throwpot_slot == 5) { return v::local_player::binds::slot5; }
	if (lith_throwpot_slot == 6) { return v::local_player::binds::slot6; }
	if (lith_throwpot_slot == 7) { return v::local_player::binds::slot7; }
	if (lith_throwpot_slot == 8) { return v::local_player::binds::slot8; }
	if (lith_throwpot_slot == 9) { return v::local_player::binds::slot9; }
	return 0;
}

bool thrown = false;
size_t position = 0;

void v::modules::throwpot::thread() {
    while (v::info::verified) {
        while (v::modules::throwpot::enabled) {
            if (GetAsyncKeyState(v::modules::throwpot::selected_bind) & 0x8000) {
                POINT P; GetCursorPos(&P);
                HWND window = WindowFromPoint(P);
                if (ScreenToClient(window, &P)) {
                    if (!v::local_player::inventory_open) {
                        if (FindWindow(((LPCWSTR)"LWJGL"), nullptr) == GetForegroundWindow()) {
                            while (!thrown) {
                                for (size_t i = 0; i < 9; ++i) {
                                    if (i == position) {
                                        if (v::modules::throwpot::slot[i]) {

                                            POINT pos1;
                                            GetCursorPos(&pos1);
                                            HWND window = WindowFromPoint(pos1);

                                            if (ScreenToClient(window, &pos1)) {

                                                keybd_event(getbindcode(i + 1), 0, 0, 0);
                                                keybd_event(getbindcode(i + 1), 0, KEYEVENTF_KEYUP, 0);
                                                v::local_player::current_slot = i + 1;

                                                SendMessage(window, WM_RBUTTONDOWN, MK_LBUTTON, MAKELPARAM(pos1.x, pos1.y));
                                                std::this_thread::sleep_for(std::chrono::milliseconds((int)v::utils::random_float(v::modules::throwpot::throw_delay * 0.75, v::modules::throwpot::throw_delay * 1.25)));
                                                SendMessage(window, WM_RBUTTONUP, MK_LBUTTON, MAKELPARAM(pos1.x, pos1.y));

                                                if (v::modules::throwpot::throw_item) {

                                                    keybd_event(v::local_player::binds::drop, 0, 0, 0);
                                                    keybd_event(v::local_player::binds::drop, 0, KEYEVENTF_KEYUP, 0);

                                                }

                                                keybd_event(getbindcode(v::modules::throwpot::return_pos), 0, 0, 0);
                                                keybd_event(getbindcode(v::modules::throwpot::return_pos), 0, KEYEVENTF_KEYUP, 0);
                                                v::local_player::current_slot = v::modules::throwpot::return_pos;

                                            }

                                            position++;
                                            thrown = true;
                                            break;

                                        }
                                        else {
                                            position++;
                                        }
                                    }
                                }
                                if (position >= 9) { position = 0; }
                                std::this_thread::sleep_for(std::chrono::milliseconds(1));
                            }
                            thrown = false;
                            while (GetAsyncKeyState(v::modules::throwpot::selected_bind) & 0x8000) { std::this_thread::sleep_for(std::chrono::milliseconds(1)); }
                        }
                    }
                }

            }
            std::this_thread::sleep_for(std::chrono::milliseconds(1));
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(10));
    }
}