#pragma once

#include <windows.h>
#include <memory>
#include <string>
#include <vector>

#include "mapper.hpp"

class c_jvm_base;
struct c_settings {
	c_jvm_base* env;
	
	void* minecraft_clazz;
	void* itemsword_clazz;
	void* itemaxe_clazz;
	void* gui_inventory;

	c_version game_ver;
	DWORD game_pid;

	bool destruct{ false };

	HWND hWnd{ NULL };

	struct
	{
		std::vector<unsigned long long>thread_hearthbeats = { GetTickCount64(), GetTickCount64(), GetTickCount64(), GetTickCount64() };
		char x1{0x0};
		char x2{ 0x0 };
		char x3{ 0x0 };
	}flags;

	struct
	{
		std::string token;
		std::string name;
		std::string ptrn;
		uintptr_t ptrnOff;

		std::string authOutput;
	}user;

	struct
	{
		struct
		{
			bool enabled{ false };
			int bind{ NULL };

			double horizontal{ 100.0 };
			double vertical{ 100.0 };
			int chance{ 100 };
			int delay{ 0 };

			bool onlyweapon{ false };
			bool aironly{ false };
			bool moving{ false };
		}velocity;

		struct
		{
			bool enabled{ false };
			int bind{ NULL };

			int avg{ 14 };

			bool breakBlock{ true };
			bool onlyWeapon{ true };
			bool inInventory{ false };
		}leftclicker;

		struct
		{
			bool enabled{ false };
			int bind{ NULL };

			float distance{ 6.f };

			int fov{ 70 };
			int speed{ 40 };

			bool onlyweapon{ true };
			bool mousemove{ false };
			bool onlyclicking{ false };
			bool antibot{ false };
		}aimassist;

		struct
		{
			bool enabled{ false };
			int bind{ NULL };

			double value{ 3.f };
			float hitbox{ 0.0f };

			bool onlyweapon{ false };
			bool onground{ false };
			bool liquidcheck{ false };
		}reach;
	}combat;

	struct
	{
		struct  
		{
			bool enabled{ false };
			int bind{ NULL };

			float value{ 1.f };
			bool moving{ false };
			bool onlyweapon{ false };
		}timer;

		struct  
		{
			bool enabled{ false };
			int bind{ NULL };

			float power{ 1.0f };
			bool liquidCheck{ false };
		}bhop;
	}movement;

	struct  
	{
		struct
		{
			bool enabled{ false };
			int bind{ NULL };
		}fullbright;
	}visuals;

	struct  
	{
		struct  
		{
			bool enabled{ false };
			int bind{ NULL };

			int switchDelay{ 50 }; // min: 25
			int throwDelay{ 50 }; // min: 50

			struct
			{
				int mode_selected{ 0 }; // 0 = one pot, 1 = double pot
			}mode;
		}throwpot;
	}misc;
	
	struct
	{
		struct
		{
			bool enabled{ false };
			int bind{ NULL };
		}hide;

		struct
		{
			bool enabled{ false };
			int bind{ NULL };
		}streamproof;
	}settings;
};

inline std::unique_ptr<c_settings> settings;

inline void settings_init()
{
	settings = std::make_unique<c_settings>();
}