#include "..\..\klynt.h"

namespace v {
	namespace modules {
		namespace antiafk {
			void thread() {
			    while (v::info::verified) {
					if (GetForegroundWindow() == v::info::minecraft_window) {

						if (v::modules::antiafk::enabled) {

							if (v::local_player::inventory_open) {

								keybd_event(VK_ESCAPE, 0, 0, 0);
								std::this_thread::sleep_for(std::chrono::milliseconds(100));
								keybd_event(VK_ESCAPE, 0, KEYEVENTF_KEYUP, 0);

								keybd_event(v::local_player::binds::jump, 0, 0, 0);

								std::this_thread::sleep_for(std::chrono::milliseconds(100));

								keybd_event(v::local_player::binds::jump, 0, KEYEVENTF_KEYUP, 0);

							}
							else {

								keybd_event(v::local_player::binds::jump, 0, 0, 0);

								std::this_thread::sleep_for(std::chrono::milliseconds(100));

								keybd_event(v::local_player::binds::jump, 0, KEYEVENTF_KEYUP, 0);

							}

						}
					
						std::this_thread::sleep_for(std::chrono::seconds(5));
					
					}
					else {

						if (v::modules::antiafk::enabled) {
							SetForegroundWindow(FindWindow((LPCWSTR)("LWJGL"), nullptr));
						}

					}

					std::this_thread::sleep_for(std::chrono::milliseconds(100));

				}

			}

		}
	}
}