#include "reach.hpp"
#include <numbers>

#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"
#include "../../game/world.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

float angle(float ex, float ez, vec3 mypos)
{
	float x = ex - (float)mypos.x;
	float z = ez - (float)mypos.z;

	float y = (float)(-atanf(x / z) * 180.f / (float)std::numbers::pi);
	if (z < 0.0 && x < 0.0) {
		y = 90.f + (float)(atanf(z / x) * 180.f / (float)std::numbers::pi);
	}
	else if (z < 0.0 && x > 0.0) {
		y = -90.f + (float)(atanf(z / x) * 180.f / (float)std::numbers::pi);
	}
	return y;
}

void modules::reach::thread()
{
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->combat.reach.bind) & 0x8000) {
			settings->combat.reach.enabled = !settings->combat.reach.enabled;
			Sleep(200);
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr)) {
			Sleep(1);
			continue;
		}
		
		if (!settings->combat.reach.enabled) {
			Sleep(1);
			continue;
		}

		auto mc = std::make_unique<c_minecraft>();
		if (mc->get_curscreen_obj()) {
			Sleep(1);
			continue;
		}

		auto theWorld = std::make_unique<c_world>(mc->get_theworld_obj());
		auto thePlayer = std::make_unique<c_player>(mc->get_theplayer_obj());

		if (settings->combat.reach.liquidcheck && thePlayer->is_in_water()) {
			Sleep(1);
			continue;
		}

		if (settings->combat.reach.onground && !thePlayer->is_on_ground()) {
			Sleep(1);
			continue;
		}

		auto itemStack = std::make_unique<c_inventoryplayer>(thePlayer->get_inventory());
		auto itemstack_p = std::make_unique<c_itemstack>(settings->env->get_obj_array_elem(itemStack->get_main_inventory(), itemStack->get_current()));
		if (settings->combat.reach.onlyweapon && !itemstack_p->is_weapon()) {
			Sleep(1);
			continue;
		}

		vec3 local_pos = { thePlayer->get_pos_x(), thePlayer->get_pos_y(), thePlayer->get_pos_z() };
		std::shared_ptr<c_player> target = nullptr;
		double dist = (std::numeric_limits<double>::max)();
		for (const auto& pl : theWorld->get_player_list())
		{
			auto player = std::make_shared<c_player>(pl);
			if (pl == mc->get_theplayer_obj())
				continue;

			if (player->get_tick_existed() < 25)
				continue;

			vec3 pl_pos = { player->get_pos_x(), player->get_pos_y(), player->get_pos_z() };

			double dist_bet = sqrt(pow(local_pos.x - pl_pos.x, 2.0) + pow(local_pos.y - pl_pos.y, 2.0) + pow(local_pos.z - pl_pos.z, 2.0));
			if (dist_bet <= dist && dist_bet <= 6.0)
			{
				dist = dist_bet;
				target = player;
			}
		}
		dist = (std::numeric_limits<double>::max)();

		if (target != nullptr)
		{
			vec3 pl_pos = { target->get_pos_x(), target->get_pos_y(), target->get_pos_z() };

			double dist_bet = sqrt(pow(local_pos.x - pl_pos.x, 2.0) + pow(local_pos.y - pl_pos.y, 2.0) + pow(local_pos.z - pl_pos.z, 2.0));
			if (dist_bet > settings->combat.reach.value + 0.5 || dist_bet <= 3.000)
			{
				Sleep(25);
				continue;
			}

			float x = (float)pl_pos.x;
			float z = (float)pl_pos.z;

			float hypothenuse_distance = hypotf((float)(local_pos.x - pl_pos.x), (float)(local_pos.z - pl_pos.z));
			float dist = (float)settings->combat.reach.value - 3.f;

			while (dist_bet > 3.0 && (dist_bet < (dist + 3.0)) && dist > 0.05)
				dist -= 0.05f;

			if (hypothenuse_distance < dist)
				dist -= hypothenuse_distance;

			float r = angle(x, z, local_pos);
			float ax = cosf((float)((r + 90.f) * std::numbers::pi / 180.f));
			float b = sinf((float)((r + 90.f) * std::numbers::pi / 180.f));

			x = (float)pl_pos.x;
			z = (float)pl_pos.z;

			x -= ax * dist;
			z -= b * dist;

			float newWidth = (0.6f + settings->combat.reach.hitbox) /*entity width*/ / 2.0f;
			axisalignedbb curHitbox = target->get_bounding_box()->get_bounding_box();

			if (curHitbox.minX == 0 || curHitbox.minY == 0 || curHitbox.minZ == 0 || curHitbox.maxX == 0 || curHitbox.maxY == 0 || curHitbox.maxZ == 0) {
				Sleep(25);
				continue;
			}

			axisalignedbb bb{};
			bb.minX = x - newWidth;
			bb.minY = curHitbox.minY;
			bb.minZ = z - newWidth;

			bb.maxX = x + newWidth;
			bb.maxY = curHitbox.maxY;
			bb.maxZ = z + newWidth;

			target->get_bounding_box()->set_bounding_box(bb);
		}

		Sleep(1);
	}
}
