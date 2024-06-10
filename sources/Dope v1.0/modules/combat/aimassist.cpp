#include "aimassist.hpp"
#include <numbers>

#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"
#include "../../game/world.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

void modules::aimassist::thread()
{
	unsigned long long timer = GetTickCount64();
	std::shared_ptr<c_player> target = nullptr;
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->combat.aimassist.bind) & 0x8000) {
			settings->combat.aimassist.enabled = !settings->combat.aimassist.enabled;
			Sleep(200);
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr)) {
			Sleep(1);
			continue;
		}
		
		if (!settings->combat.aimassist.enabled) {
			Sleep(1);
			continue;
		}

		auto mc = std::make_unique<c_minecraft>();
		if (mc->get_curscreen_obj()) {
			Sleep(10);
			continue;
		}

		if (settings->combat.aimassist.onlyclicking && !(GetAsyncKeyState(VK_LBUTTON) & 0x8000)) {
			Sleep(10);
			continue;
		}

		auto theWorld = std::make_unique<c_world>(mc->get_theworld_obj());
		auto thePlayer = std::make_unique<c_player>(mc->get_theplayer_obj());

		if (settings->combat.aimassist.onlyweapon) {
			auto itemStack = std::make_unique<c_inventoryplayer>(thePlayer->get_inventory());
			auto itemstack_p = std::make_unique<c_itemstack>(itemStack->get_current_item_stack());
			
			if (!itemstack_p->is_weapon()) {
				Sleep(1);
				continue;
			}
		}

		vec3 local_pos = { thePlayer->get_pos_x(), thePlayer->get_pos_y(), thePlayer->get_pos_z() };
		
		if (target == nullptr || GetTickCount64() - timer > 75) {
			double dist = (std::numeric_limits<double>::max)();

			for (const auto& pl : theWorld->get_player_list())
			{
				auto player = std::make_shared<c_player>(pl);
				if (pl == mc->get_theplayer_obj())
					continue;

				if (settings->combat.aimassist.antibot && player->get_tick_existed() < 25 && !player->is_on_ground())
					continue;

				vec3 pl_pos = { player->get_pos_x(), player->get_pos_y(), player->get_pos_z() };

				double dist_bet = sqrt(pow(local_pos.x - pl_pos.x, 2.0) + pow(local_pos.y - pl_pos.y, 2.0) + pow(local_pos.z - pl_pos.z, 2.0));
				if (dist_bet <= dist && dist_bet <= settings->combat.aimassist.distance)
				{
					dist = dist_bet;
					target = player;
				}
			}
		}

		auto sort_vec = [](std::vector<double>& v)
		{
			for (size_t i = 0; i < v.size(); i++)
			{
				for (size_t j = i + 1; j < v.size(); j++)
				{
					if (v[i] > v[j])
					{
						double x = v[i];
						v[i] = v[j];
						v[j] = x;
					}
				}
			}
		};
		auto is_in_range = [](vec3 v) {
			if (v.z > v.y && (v.x > v.z || v.x < v.y) ||
				v.y > v.z && (v.x > v.y || v.x < v.z))
				return false;

			return true;
		};

		auto wrapTo180 = [](float value)
		{
			if (value >= 180.f)
				value -= 360.f;
			if (value < -180.f)
				value += 360.f;
			return value;
		};

		auto rand_float = [](float min, float max)
		{
			float f = (float)rand() / RAND_MAX;
			return min + f * (max - min);
		};

		if (target != nullptr) {
			vec3 target_pos = { target->get_pos_x(), target->get_pos_y(), target->get_pos_z() };
			double dist_bet = sqrt(pow(local_pos.x - target_pos.x, 2.0) + pow(local_pos.y - target_pos.y, 2.0) + pow(local_pos.z - target_pos.z, 2.0));

			if (dist_bet <= settings->combat.aimassist.distance)
			{
				float lastYaw = thePlayer->get_previous_yaw();
				float yaw = thePlayer->get_yaw();
				float lastPitch = thePlayer->get_previous_pitch();
				float pitch = thePlayer->get_pitch();

				double distance_x = target_pos.x - local_pos.x;
				double distance_z = target_pos.z - local_pos.z;

				{
					std::vector<vec3> corner
					{
						{ target_pos.x - 0.30f, target_pos.y, target_pos.z + 0.30f },
						{ target_pos.x - 0.30f, target_pos.y, target_pos.z - 0.30f },
						{ target_pos.x + 0.30f, target_pos.y, target_pos.z - 0.30f },
						{ target_pos.x + 0.30f, target_pos.y, target_pos.z + 0.30f },
					};

					double distance = sqrt(pow(target_pos.x - local_pos.x, 2) + pow(target_pos.y - local_pos.y, 2) + pow(target_pos.z - local_pos.z, 2));

					double distance_c_1 = sqrt(pow(corner[0].x - local_pos.x, 2) + pow(corner[0].y - local_pos.y, 2) + pow(corner[0].z - local_pos.z, 2));
					double distance_c_2 = sqrt(pow(corner[1].x - local_pos.x, 2) + pow(corner[1].y - local_pos.y, 2) + pow(corner[1].z - local_pos.z, 2));
					double distance_c_3 = sqrt(pow(corner[2].x - local_pos.x, 2) + pow(corner[2].y - local_pos.y, 2) + pow(corner[2].z - local_pos.z, 2));
					double distance_c_4 = sqrt(pow(corner[3].x - local_pos.x, 2) + pow(corner[3].y - local_pos.y, 2) + pow(corner[3].z - local_pos.z, 2));

					std::vector<double> closest_crn{ distance_c_1, distance_c_2, distance_c_3, distance_c_4};
					sort_vec(closest_crn);

					vec3 closest_crnr_1 = corner[0];
					if (closest_crn[0] == distance_c_1) closest_crnr_1 = corner[0];
					if (closest_crn[0] == distance_c_2) closest_crnr_1 = corner[1];
					if (closest_crn[0] == distance_c_3) closest_crnr_1 = corner[2];
					if (closest_crn[0] == distance_c_3) closest_crnr_1 = corner[3];

					distance_x = closest_crnr_1.x - local_pos.x;
					distance_z = closest_crnr_1.z - local_pos.z;
				}

				float fov = (float)atan2((float)distance_z, (float)distance_x) * 180.f / std::numbers::pi - 90.f;
				fov = wrapTo180(fmodf(fov - yaw, 360.f));
				if (abs(fov) <= settings->combat.aimassist.fov && abs(fov) > 2.f)
				{
					fov *= (pow((0.5f /*todo change behind by sensivity*/ * 0.6f + 0.2f), 3.f) * 8.f) * (settings->combat.aimassist.speed + rand_float(1.f, 5.f));

					float new_yaw = yaw + (int)fov * rand_float(0.00100f, 0.00050f);
					float new_previous_yaw = lastYaw + (int)fov * rand_float(0.00100f, 0.00050f);

					thePlayer->set_yaw(new_yaw);
					thePlayer->set_previous_yaw(new_previous_yaw);

					/**/
					//float newRand = rand_float(0.0005f, 0.0008f);
					//thePlayer->set_pitch(pitch + newRand);
					//thePlayer->set_previous_pitch(lastPitch + newRand);
					/**/
				}
			}
		}

		Sleep(1);
	}
}
