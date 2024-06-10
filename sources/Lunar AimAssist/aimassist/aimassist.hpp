#include <windows.h>
#include <memory>
#include <unordered_map>
#include <iostream>
#include <mutex>
#include <random>

#include <jni.h>
#include <jvmti.h>

#include <gl/GL.h>

#include "../sdk/imgui/imgui.h"
#include "../sdk/imgui/imgui_impl_opengl2.h"
#include "../sdk/imgui/imgui_impl_win32.h"

#include "../sdk/minHook/MinHook.h"

#include "../sdk/minecraft/minecraft.hpp"

#define DEBUG false
#define M_PI       3.14159265358979323846   // pi

namespace aimassist
{
	class c_aimassist
	{
	private:
		JNIEnv* env;
		jvmtiEnv* jvmtiEnv;
		JavaVM* vm;

		std::unordered_map<std::string, jclass> klasses;
	public:
		bool menuOpen = true;
		bool loop = true;

		bool enabled = false;
		bool requireClick = true;
		float speed = 5;
		float fov = 30;
		double distance = 6;
		bool teams = false;
		bool itemWhitelist = false;
		bool checkInvisibles = true;
		float randomAmount = 4;

		bool attach();
		
		void getKlasses();

		void hook();
		
		void run();

		void unload();
	};

	namespace hooks
	{
		using swapBuffersFunction = int(__stdcall*)(HDC);

		inline swapBuffersFunction oswapBuffers = nullptr;

		int __stdcall swapBuffersHook(HDC);

		inline WNDPROC originalWndproc = nullptr;

		long __stdcall wndproc(const HWND hwnd, UINT usermsg, WPARAM wparam, LPARAM lparam);
	}


		

		__forceinline float _rand_val(float min, float max) {

			++max;


			std::random_device dev;
			std::mt19937 rng(dev());
			std::uniform_real_distribution<float> dist(min, max);
			return dist(rng);


		}
	

	extern std::unique_ptr<c_aimassist> instance;
}