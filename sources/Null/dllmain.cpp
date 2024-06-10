#include "funcs.h"

DWORD WINAPI six()
{

	AllocConsole();
	freopen("CONIN$", "r", stdin);
	freopen("CONOUT$", "w", stdout);
	SetConsoleTitle("");
	CONSOLE_SCREEN_BUFFER_INFO csbi;
	GetConsoleScreenBufferInfo(GetStdHandle(STD_OUTPUT_HANDLE), &csbi);
	COORD scrollbar = {
		csbi.srWindow.Right - csbi.srWindow.Left + 1,
		csbi.srWindow.Bottom - csbi.srWindow.Top + 1
	};
	SetConsoleScreenBufferSize(GetStdHandle(STD_OUTPUT_HANDLE), scrollbar);
	SetWindowLong(GetConsoleWindow(), GWL_STYLE, GetWindowLong(GetConsoleWindow(), GWL_STYLE) & ~WS_MAXIMIZEBOX & ~WS_SIZEBOX);
	DeleteKey(); //delete some traces
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	printf(xor (" \n"));
	printf(xor ("                        88b 88 88   88 88     88         \n"));
	printf(xor ("                        88Yb88 88   88 88     88         \n"));
	printf(xor ("                        88 Y88 Y8   8P 88  .o 88  .o     \n"));
	printf(xor ("                        88  Y8 `YbodP' 88ood8 88ood8     \n"));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 4 | FOREGROUND_INTENSITY);
	printf(xor("                                            by six#0404   \n\n\n"));
	while (!FindWindow(_T(xor("LWJGL")), nullptr))
	{
		SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
		printf(xor("\rWaiting for minecraft process..."));
		std::this_thread::sleep_for(std::chrono::milliseconds(1));

	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 2 | FOREGROUND_INTENSITY);
	printf(xor("\nMinecraft process found.\n"));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf(xor("\nSet autoclicker toggle key (It's can be a mouse button) -> "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	while (keyac == 0) { /* keybind choose system */
		for (int i = 3; i < 256; i++)
		{
			if (GetAsyncKeyState((i)& SHRT_MAX) && keyac == 0)
			{
				keyac = i;
				printf("0x"+ keyac);
				std::this_thread::sleep_for(std::chrono::milliseconds(1000));
			}
		}
	}
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf(xor("\nCPS min -> ") );
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 14 | FOREGROUND_INTENSITY);
	std::cin >> min_cps;
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf(xor("\nCPS max -> "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 2 | FOREGROUND_INTENSITY);
	std::cin >> max_cps;
	auto autoc_thread = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)autoc, nullptr, 0, nullptr); /* auto clicker thread */
	CloseHandle(autoc_thread);
	printf(xor("\n"));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
	printf(xor("Choose a reach value (between 3.0 - 4.0)-> "));
	SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 2 | FOREGROUND_INTENSITY);
	std::cin >> r;
	#pragma region ReachChoose
		if (r == 3.0f)
		{
			x = 2.125f;
		}
		else if (r == 3.1f)
		{
			x = 2.137499809f;
		}
		else if (r == 3.2f)
		{
			x = 2.149999857f;
		}
		else if (r == 3.3f)
		{
			x = 2.162499905f;
		}
		else if (r == 3.4f)
		{
			x = 2.174999952f;
		}
		else if (r == 3.5f)
		{
			x = 2.1875f;
		}
		else if (r == 3.6f)
		{
			x = 2.199999809f;
		}
		else if (r == 3.7f)
		{
			x = 2.212499857f;
		}
		else if (r == 3.8f)
		{
			x = 2.224999905f;
		}
		else if (r == 3.9f)
		{
			x = 2.237499952f;
		}
		else if (r == 4.0f)
		{
			x = 2.25f;
		}
		else
		{
			SetConsoleTextAttribute(GetStdHandle(STD_OUTPUT_HANDLE), 15 | FOREGROUND_INTENSITY);
			printf(xor("Type a valid value\n"));
			printf(xor ("Press any key to return"));
			std::cin.get();
			exit(0);
		}
#pragma endregion /* this is pretty stupid, but works */
	auto r_thread = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)reachtest, nullptr, 0, nullptr); /* reach thread */
	CloseHandle(r_thread);
	auto m_h_thread = CreateThread(nullptr, 0, (LPTHREAD_START_ROUTINE)mouse_h, nullptr, 0, nullptr); /* mouse hook thread */
	CloseHandle(m_h_thread);
	system("cls"); // ik this is very deprecated but yeah
	menu();
	return 0;
}
bool __stdcall DllMain(HINSTANCE H_instance, unsigned long rsn) {
	DisableThreadLibraryCalls(H_instance);
	switch (rsn) {
	case DLL_PROCESS_ATTACH: {
		CreateThread(0, 0, (LPTHREAD_START_ROUTINE)six, 0, 0, 0);

	} break;

	}

	return true;
}

