#include <windows.h>
#include <thread>

#include "aimassist/aimassist.hpp"

void run()
{
	std::this_thread::sleep_for(std::chrono::milliseconds(50));

#if DEBUG
    AllocConsole();
    SetConsoleCtrlHandler(NULL, true);
    FILE* fIn;
    FILE* fOut;
    freopen_s(&fIn, "conin$", "r", stdin);
    freopen_s(&fOut, "conout$", "w", stdout);
    freopen_s(&fOut, "conout$", "w", stderr);


#endif

    aimassist::instance = std::make_unique<aimassist::c_aimassist>();
	
    if (!aimassist::instance->attach())
        return;

    aimassist::instance->run();

    std::this_thread::sleep_for(std::chrono::milliseconds(50));

    aimassist::instance->unload();
}

BOOL APIENTRY DllMain(HMODULE hModule, DWORD  reasonForCall, LPVOID lpReserved)
{
    if(reasonForCall != DLL_PROCESS_ATTACH)
		return FALSE;

	CreateThread(0, 0, (LPTHREAD_START_ROUTINE)run, 0, 0, 0);

    return TRUE;
}

