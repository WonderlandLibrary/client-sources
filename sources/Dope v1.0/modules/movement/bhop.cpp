#include <numbers>
#include "bhop.hpp"
#include "../../game/inventoryplayer.hpp"
#include "../../game/itemstack.hpp"
#include "../../game/minecraft.hpp"
#include "../../game/player.hpp"
#include "../../game/world.hpp"
#include "../../game/timer.hpp"

#include "../../game/jvm/jvm_base.hpp"
#include "../settings.hpp"

void modules::bhop::thread()
{
	unsigned long long timer = GetTickCount64();
	double playerSpeed = 0.0;
	bool slowDown{ false };
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->movement.bhop.bind) & 0x8000) {
			settings->movement.bhop.enabled = !settings->movement.bhop.enabled;
			Sleep(200);
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr)) {
			Sleep(1);
			continue;
		}

		if (!settings->movement.bhop.enabled) {
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
		if (!theWorld || !thePlayer) {
			Sleep(10);
			continue;
		}

		auto timer_obj = std::make_unique<c_timer>(mc->get_timer_obj());
		if (!timer_obj) {
			Sleep(10);
			continue;
		}

		if (settings->movement.bhop.enabled)
		{
			if (settings->movement.bhop.liquidCheck && thePlayer->is_in_water()) {
				Sleep(25);
				continue;
			}

			if (thePlayer->is_on_ground() && (thePlayer->get_move_forward() != 0 || thePlayer->get_move_strafing() != 0) && GetTickCount64() - timer > 300)
			{
				timer = GetTickCount64();

				vec3 myMotion = {thePlayer->get_motion_x(), thePlayer->get_motion_y(), thePlayer->get_motion_z()};
				thePlayer->set_motion_y(0.41);

				playerSpeed = ((settings->movement.bhop.power / 1.45) * 0.2873) * 1.90 /*0.9 si la collision est un liquid*/;
				slowDown = true;
			}
			else {
				if (playerSpeed != 0.0) {
					if (slowDown) {
						playerSpeed -= 0.7 /*0.4 si la collision est un liquid*/ * (playerSpeed = 0.2873);
						slowDown = false;
					}
					else {
						playerSpeed -= playerSpeed / 159.0;
					}
				}
			}

			playerSpeed = max(playerSpeed, ((settings->movement.bhop.power / 1.45) * 0.2873));
			auto forward = [&thePlayer, &timer_obj](float speed)
			{
				float forward = thePlayer->get_move_forward();
				float side = thePlayer->get_move_strafing();
				float yaw = thePlayer->get_previous_yaw() + (thePlayer->get_yaw() - thePlayer->get_previous_yaw()) * timer_obj->get_render_partial_ticks();
				if (forward != 0.0f)
				{
					if (side > 0.0f) {
						yaw += ((forward > 0.0f) ? -45 : 45);
					}
					else if (side < 0.0f) {
						yaw += ((forward > 0.0f) ? 45 : -45);
					}
					side = 0.0f;
					if (forward > 0.0f) {
						forward = 1.0f;
					}
					else if (forward < 0.0f) {
						forward = -1.0f;
					}
				}

				float sin = sinf(((yaw + 90.f) * std::numbers::pi / 180.f));
				float cos = cosf(((yaw + 90.f) * std::numbers::pi / 180.f));
				double posX = (double)(forward * speed * cos + side * speed * sin);
				double posZ = (double)(forward * speed * sin - side * speed * cos);
				return vec3(posX, 0, posZ);
			};

			vec3 dir = forward((float)playerSpeed);			
			if (abs(dir.x) < 10.0 && abs(dir.z) < 10.0) { // lil check just incase
				thePlayer->set_motion_x(dir.x);
				thePlayer->set_motion_z(dir.z);
			}
		}

		Sleep(1);
	}
}
