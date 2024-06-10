#include "conector.h"

void thread_inventory() {
	while (true) {
		CURSORINFO cucklord_mouse_info = { sizeof(CURSORINFO) };
		if (GetCursorInfo(&cucklord_mouse_info)) {
			HCURSOR cucklord_mouse_handle = cucklord_mouse_info.hCursor;
			int cucklord_mouse_handle_int = (int)cucklord_mouse_handle;
			if (cucklord_mouse_handle_int > 50000 & cucklord_mouse_handle_int < 100000) {
				cucklord_inventory_status = true;
				//std::cout << "open" << std::endl;
			} else { 
				//std::cout << "close" << std::endl;
				cucklord_inventory_status = false; 
			}
			//std::cout << cucklord_mouse_handle_int << std::endl;
		}
		std::this_thread::sleep_for(std::chrono::milliseconds(10));
	}
}