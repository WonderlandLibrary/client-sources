#include "gametimer.hpp"
#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"
#include "../../game/world.hpp"
#include "../../game/timer.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

void modules::game_timer::thread()
{
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->movement.timer.bind) & 0x8000) {
			settings->movement.timer.enabled = !settings->movement.timer.enabled;
			Sleep(200);
		}

		auto mc = std::make_unique<c_minecraft>();
		if (!mc) {
			Sleep(10);
			continue;
		}

		auto theWorld = std::make_unique<c_world>(mc->get_theworld_obj());
		auto thePlayer = std::make_unique<c_player>(mc->get_theplayer_obj());
		if (!theWorld || !thePlayer) {
			Sleep(10);
			continue;
		}

		auto timer_obj = std::make_unique<c_timer>(mc->get_timer_obj());
		if (!timer_obj) {
			Sleep(10);
			continue;
		}

		if (!settings->movement.timer.enabled && timer_obj->get_timer_speed() != 1.0f) {
			timer_obj->set_timer_speed(1.f);
		}

		auto itemStack = std::make_unique<c_inventoryplayer>(thePlayer->get_inventory());
		auto itemstack_p = std::make_unique<c_itemstack>(settings->env->get_obj_array_elem(itemStack->get_main_inventory(), itemStack->get_current()));
		if (settings->movement.timer.onlyweapon && !itemstack_p->is_weapon()) {
			Sleep(1);
			continue;
		}

		vec3 local_prev = { thePlayer->get_prev_pos_x(), thePlayer->get_prev_pos_y(), thePlayer->get_prev_pos_z() };
		vec3 local_pos = { thePlayer->get_pos_x(), thePlayer->get_pos_y(), thePlayer->get_pos_z() };
		if (settings->movement.timer.moving && (local_prev.x == local_pos.x && local_prev.y == local_pos.y && local_prev.z == local_pos.z))
		{
			Sleep(1);
			continue;
		}
		
		timer_obj->set_timer_speed(settings->movement.timer.value);
		Sleep(1);
	}
}