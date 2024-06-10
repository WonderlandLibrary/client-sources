#include <thread>
#include <functional>
#include <atomic>
#include "menu/menu.hpp"

#include "game/jvm/jvm_16.hpp"
#include "game/jvm/jvm_8.hpp"

#include "utilities/memory.hpp"
#include "utilities/requests.hpp"

#include "security/securityhelper.hpp"

#include "modules/settings.hpp"
#include "modules/mapper.hpp"
#include "modules/destruct/selfdestruct.hpp"

#include "game/minecraft.hpp"

#ifndef _DEBUG
#include <ThemidaSDK.h>
#endif

#include "authentification/auth.hpp"

// I feel like its a bit messy, gotta change a few things

bool get_max_perms(HANDLE processhandle, std::string perm)
{
	const char* permchar = perm.c_str();
	HANDLE tokenhandle;
	LUID permissionidentifier;
	TOKEN_PRIVILEGES tokenpriv;
	if (OpenProcessToken(processhandle, TOKEN_ADJUST_PRIVILEGES | TOKEN_QUERY, &tokenhandle))
	{
		if (LookupPrivilegeValue(NULL, permchar, &permissionidentifier))
		{
			tokenpriv.PrivilegeCount = 1;
			tokenpriv.Privileges[0].Luid = permissionidentifier;
			tokenpriv.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
			if (AdjustTokenPrivileges(tokenhandle, false, &tokenpriv, sizeof(tokenpriv), NULL, NULL)) { return true; }
			else { return false; }
		}
		else { return false; }
	}
	else { return false; }
	CloseHandle(tokenhandle);
}

int main()
{
#ifndef _DEBUG
	VM_LION_BLACK_START
#endif 

	auto show_logo = []() {
		HANDLE cout_handle = GetStdHandle(STD_OUTPUT_HANDLE);
		SetConsoleTextAttribute(cout_handle, FOREGROUND_BLUE);
		printf("\n\n\n                                                       _/\n                                                      (/()/)(-\n                                                         /\n");
		SetConsoleTextAttribute(cout_handle, 15);
		printf("                                                                1.0.0\n\n\n\n\n");
	};

	get_max_perms(GetCurrentProcess(), SE_SECURITY_NAME);
	get_max_perms(GetCurrentProcess(), SE_DEBUG_NAME);

	settings_init();

	std::thread([&] {
		while (true)
		{
			SecurityHelper::Integrity::anti_attach();
			if (SecurityHelper::Integrity::is_being_debugged())
			{
#ifndef _DEBUG
				_exit(-1);
#endif
			}

			settings->flags.thread_hearthbeats.at(0) = GetTickCount64();
			Sleep(50);
		}
	}).detach();

	std::thread([&] {
		while (true)
		{
			SecurityHelper::Integrity::clear_handles();

			settings->flags.thread_hearthbeats.at(1) = GetTickCount64();
			Sleep(50);
		}
	}).detach();

	std::thread([&] {
		while (true)
		{
			if (SecurityHelper::Integrity::has_hooks()) {
#ifndef _DEBUG
				_exit(-1);
#endif
			}

			settings->flags.thread_hearthbeats.at(2) = GetTickCount64();
			Sleep(50);
		}
	}).detach();

	std::thread([&] {
		SecurityHelper::Integrity::initialize_codesection();
		while (true)
		{
			if (SecurityHelper::Integrity::has_codesection_changed()) {
#ifndef _DEBUG
				_exit(-1);
#endif
			}

			settings->flags.thread_hearthbeats.at(3) = GetTickCount64();
			Sleep(50);
		}
	}).detach();

	HWND mc_window = FindWindow("lwjgl", nullptr);

	show_logo();
	if(!mc_window)
		printf("[+] Please launch your game.\n");

	while (!settings->game_pid) {
		if(!mc_window)
			mc_window = FindWindow("lwjgl", nullptr);

		GetWindowThreadProcessId(mc_window, &settings->game_pid);
		Sleep(500);
	}

#ifdef _DEBUG
	settings->user.token = "f125874546464514d";
#else
	TCHAR filePath[MAX_PATH] = { 0 };
	GetModuleFileName(NULL, filePath, MAX_PATH);

	std::ifstream fileStream(filePath, std::ios::binary);
	fileStream.seekg(0, std::ios::end);
	size_t size = fileStream.tellg();
	fileStream.seekg(0, std::ios::beg);
	char* buffer = new char[size];
	fileStream.read(buffer, size);

	std::string token(buffer + size - 16, 16);

	for (int i = 0; i < token.size(); i++)
		token[i] = token[i] ^ 0xd3adc0de;

	if (token.size() != 40)
		exit(EXIT_SUCCESS);

	delete[] buffer;
	settings->user.token = token.data();
#endif
	
	char* windowtitle = new char[128];
	GetWindowTextA(mc_window, windowtitle, 128);
	std::string winTitle(windowtitle);
	bool is1_7 = winTitle.find("1.7") != std::string::npos;

	if (winTitle.find("Lunar Client") != std::string::npos) {
		settings->game_ver = is1_7 ? c_version::LUNAR_1_7_10 : c_version::LUNAR_1_8;
	}
	else if (winTitle.find("Badlion Minecraft Client") != std::string::npos)
	{
		settings->game_ver = is1_7 ? c_version::BADLION_1_7_10 : c_version::BADLION_1_8;
	}
	else {
		settings->game_ver = is1_7 ? c_version::CASUAL_1_7_10 : c_version::CASUAL_1_8;
	}
	delete[] windowtitle;

	auto create_thread = [](auto thread)
	{
		if (auto handle = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)thread, nullptr, 0, nullptr); handle != NULL)
			CloseHandle(handle);
	};

	create_thread(pop_menu);

	while (!settings->destruct) {
		Sleep(100);
	}

	modules::selfdestruct::close();

#ifndef _DEBUG
	VM_LION_BLACK_END
#endif 

	//	Sleep(10000);
	//while (true)
	//{
	//	auto mc = std::make_unique<c_minecraft>();
	//	void* mcObj = mc->get_minecraft_obj();
	//	printf("mcObj %p\n", mcObj);

	//	// net.minecraft.v1_7.apshaahepeesaehepaapeeeps bbr
	//	//  public apshaahepeesaehepaapeeeps eeephsheeahaaeepasasahpas;

	//	//void* clazz = settings->env->find_class("net/minecraft/client/renderer/ActiveRenderInfo");
	//	//printf("clazz %p\n", clazz);
	//	printf("game version: %i\n", settings->game_ver);
	//	unsigned short field = settings->env->get_field(settings->minecraft_clazz, "theWorld", "Lnet/minecraft/client/multiplayer/WorldClient;");
	//	printf("field %i\n", field);

	//	//void* clazz = settings->env->find_class("net/minecraft/v1_8/spaaeeasshaasheepsapaassh");
	//	//printf("clazz %p\n", clazz);
	//	//unsigned short field = settings->env->get_field(clazz, "field_74333_Y", "F");
	//	//printf("field %i\n", field);
	//	Sleep(1);
	//}
}