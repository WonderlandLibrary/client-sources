#include "latemod/latemod.h"

std::shared_ptr<lm::latemod> cheat;

void init()
{
	cheat = std::make_shared<lm::latemod>();
	cheat->init();

	while (cheat->b_running)
		std::this_thread::sleep_for(std::chrono::milliseconds(500));

	cheat.reset();

	return;
}

long __stdcall DllMain(HINSTANCE hinst, long fdwReason, void* lpvReserved)
{
	DisableThreadLibraryCalls(hinst);

	if (fdwReason != DLL_PROCESS_ATTACH)
		return false;

	std::thread thread(init);
	thread.detach();

	return true;
}