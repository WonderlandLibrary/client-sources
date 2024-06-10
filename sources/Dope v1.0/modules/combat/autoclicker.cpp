#include <windows.h>
#include <random>
#include <thread>

#include "autoclicker.hpp"
#include "../../game/movingobjectposition.hpp"
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

static int randomint(int min, int max)
{
	std::random_device rdd;
	std::mt19937 rnd(rdd());
	std::uniform_int_distribution<int> distribution(min, max);
	return distribution(rnd);
}

static ULONGLONG timer = 0;
static INT low = 0, high = 0;
static int rndDelay(int avg)
{
	srand((unsigned int)time_t(NULL));

	if (avg <= 4)
		avg = 5;

	int delay = 0;

	if (GetTickCount64() - timer > 2500 && rand() % 100 < 47 || timer == 0 || low > 0 && low < 8) {
		delay = (randomint(randomint(479, 544), randomint(577, 634)) / randomint(avg - randomint(2, randomint(4, 5)), avg));

		if (delay > 600)
			low = low + 2;
		else
			low++;

		if (low >= 7)
			timer = GetTickCount64();
	}
	else {
		if (GetTickCount64() - timer > 2500 && rand() % 100 < 35 || high > 0 && high < 4) {

			delay = (randomint(randomint(375, randomint(410, 437)), randomint(475, randomint(543, 585))) / randomint(avg, avg + randomint(randomint(2, 3), randomint(3, 5))));

			if (delay < 435)
				high = high + 2;
			else
				high++;

			if (high >= 4)
				timer = GetTickCount64();
		}
		else
			delay = (randomint(randomint(randomint(396, 436), randomint(445, 479)), randomint(randomint(500, 534), randomint(578, 610))) / randomint(avg - randomint(1, 3), avg));
	}

	delay -= randomint(randomint(-7, -3), randomint(3, 7));
	if (rand() % 100 < rand() % 60)
		delay -= randomint(randomint(-5, -2), randomint(2, 5));

	return delay;
}

void modules::autoclicker::thread()
{
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->combat.leftclicker.bind) & 0x8000) {
			settings->combat.leftclicker.enabled = !settings->combat.leftclicker.enabled;
			Sleep(200);
		}
		
		int delay = rndDelay(settings->combat.leftclicker.avg);
		if (!settings->combat.leftclicker.enabled) {
			Sleep(1);
			continue;
		}

		if (GetForegroundWindow() != FindWindowA("LWJGL", nullptr))
		{
			Sleep(1);
			continue;
		}

		if (!(GetAsyncKeyState(VK_LBUTTON) & 0x8000))
		{
			Sleep(1);
			continue;
		}

		auto mc = std::make_unique<c_minecraft>();
		void* cur_screen = mc->get_curscreen_obj();
		void* the_world_obj = mc->get_theworld_obj();
		void* the_player_obj = mc->get_theplayer_obj();
		if (!the_world_obj || !the_player_obj) {
			Sleep(1);
			continue;
		}

		bool isInInventory = false;
		if (cur_screen) {
			isInInventory = settings->env->is_instance_of(settings->gui_inventory, cur_screen);

			if (isInInventory && !settings->combat.leftclicker.inInventory || !isInInventory) {
				Sleep(1);
				continue;
			}
		}

		auto theWorld = std::make_unique<c_world>(the_world_obj);
		auto thePlayer = std::make_unique<c_player>(the_player_obj);

		auto itemStack = std::make_unique<c_inventoryplayer>(thePlayer->get_inventory());
		auto itemstack_p = std::make_unique<c_itemstack>(itemStack->get_current_item_stack());
		if (settings->combat.leftclicker.onlyWeapon && !itemstack_p->is_weapon()) {
			if (!settings->combat.leftclicker.inInventory || !isInInventory && !cur_screen && settings->combat.leftclicker.inInventory) {
				Sleep(1);
				continue;
			}
		}

		auto ob = std::make_unique<c_movingobjectposition>(mc->get_objectmouseover_obj());
		if (settings->combat.leftclicker.breakBlock && ob->is_hit_block()) {
			Sleep(1);
			continue;
		}

		sendClick(FindWindowA("LWJGL", nullptr), delay - 15);
		Sleep(1);
	}
}
