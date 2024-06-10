#include "velocity.hpp"
#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

void modules::velocity::thread()
{
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->combat.velocity.bind) & 0x8000) {
			settings->combat.velocity.enabled = !settings->combat.velocity.enabled;
			Sleep(200);
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr)) {
			Sleep(1);
			continue;
		}

		if (!settings->combat.velocity.enabled) {
			Sleep(1);
			continue;
		}

		auto mc = std::make_unique<c_minecraft>();
		if (mc->get_curscreen_obj()) {
			Sleep(1);
			continue;
		}

		auto thePlayer = std::make_unique<c_player>(mc->get_theplayer_obj());

		if (rand() % 100 <= settings->combat.velocity.chance)
		{
			if (settings->combat.velocity.aironly && thePlayer->is_on_ground()) {
				Sleep(1);
				continue;
			}

			vec3 local_prev = { thePlayer->get_prev_pos_x(), thePlayer->get_prev_pos_y(), thePlayer->get_prev_pos_z() };
			vec3 local_pos = { thePlayer->get_pos_x(), thePlayer->get_pos_y(), thePlayer->get_pos_z() };
			if (settings->combat.velocity.moving && (local_prev.x == local_pos.x && local_prev.y == local_pos.y && local_prev.z == local_pos.z))
			{
				Sleep(1);
				continue;
			}

			auto itemStack = std::make_unique<c_inventoryplayer>(thePlayer->get_inventory());
			auto itemstack_p = std::make_unique<c_itemstack>(settings->env->get_obj_array_elem(itemStack->get_main_inventory(), itemStack->get_current()));
			if (settings->combat.velocity.onlyweapon && !itemstack_p->is_weapon()) {
				Sleep(1);
				continue;
			}

			int target_change = thePlayer->get_max_hurt_resistant_time() - settings->combat.velocity.delay;
			int hurt_resistant_time = thePlayer->get_hurt_resistant_time();
			if ((settings->game_ver == c_version::LUNAR_1_7_10 
					|| settings->game_ver == c_version::CASUAL_1_7_10 
					|| settings->game_ver == c_version::FORGE_1_7_10
					|| settings->game_ver == c_version::BADLION_1_7_10) 
				&& hurt_resistant_time > 0) // hurt resistant time on lunar 1.7 (might be for all 1.7 versions) seems to never hit 20 (which is max hurt resistant time)
				hurt_resistant_time += 1;

			if (hurt_resistant_time == target_change) {
				double horizontal = settings->combat.velocity.horizontal / 100;
				double vertical = settings->combat.velocity.vertical / 100;

				thePlayer->set_motion_x(thePlayer->get_motion_x() * horizontal);
				thePlayer->set_motion_y(thePlayer->get_motion_y() * vertical);
				thePlayer->set_motion_z(thePlayer->get_motion_z() * horizontal);
			}
		}
		Sleep(1);
	}
}
