#include <thread>
#include "throwpot.hpp"
#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"
#include "../../game/world.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

static void sendClick(HWND window, int delay, bool isRight = false)
{
	POINT p;
	GetCursorPos(&p);

	SendMessageW(window, isRight ? 0x204 : 0x201, MK_LBUTTON, MAKELPARAM(0, 0));
	std::this_thread::sleep_for(std::chrono::milliseconds(delay));
	SendMessageW(window, isRight ? 0x205 : 0x202, MK_LBUTTON, MAKELPARAM(0, 0));
	std::this_thread::sleep_for(std::chrono::milliseconds(delay));
}

void modules::throwpot::thread()
{
	bool is_healed = false;
	int throwd = 0;
	while (!settings->destruct) {
		if (!settings->misc.throwpot.enabled) {
			Sleep(1);
			continue;
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr)) {
			Sleep(1);
			continue;
		}

		auto mc = std::make_unique<c_minecraft>();
		if (mc->get_curscreen_obj()) {
			Sleep(10);
			continue;
		}

		auto theWorld = std::make_unique<c_world>(mc->get_theworld_obj());
		auto thePlayer = std::make_unique<c_player>(mc->get_theplayer_obj());

		if (settings->combat.reach.liquidcheck && thePlayer->is_in_water()) {
			Sleep(1);
			continue;
		}

		void* inventory = thePlayer->get_inventory();
		auto inventory_obj = std::make_unique<c_inventoryplayer>(inventory);

		if (GetAsyncKeyState(settings->misc.throwpot.bind) & 0x8000)
		{
			if (is_healed)
			{
				continue;
				Sleep(25);
			}

			int initial_slot = inventory_obj->get_current();
			for (int i = 0; i < 9 && !is_healed; i++)
			{
				void* mainInventory = inventory_obj->get_main_inventory();
				void* item_obj = settings->env->get_obj_array_elem(mainInventory, i);
				auto itemstack = std::make_unique<c_itemstack>(item_obj);
				if (!item_obj)
					continue;

				int metadata = itemstack->get_metadata();
				if (metadata == 16421 /*instant health 2*/)
				{
					Sleep(settings->misc.throwpot.switchDelay);
					inventory_obj->set_current(i);

					sendClick(FindWindowA("LWJGL", nullptr), settings->misc.throwpot.throwDelay / 2, true);

					if (settings->misc.throwpot.mode.mode_selected == 0)
						is_healed = true;
					else if (settings->misc.throwpot.mode.mode_selected == 1) {
						throwd++;

						if (throwd == 2)
							is_healed = true;
					}

					if (is_healed) {
						Sleep(settings->misc.throwpot.switchDelay);
						inventory_obj->set_current(initial_slot);
					}
				}

				if (i == 8 && inventory_obj->get_current() != initial_slot)
				{
					Sleep(settings->misc.throwpot.switchDelay);
					inventory_obj->set_current(initial_slot);
				}
			}

			is_healed = false;
			throwd = 0;
		}

		Sleep(1);
	}
}
