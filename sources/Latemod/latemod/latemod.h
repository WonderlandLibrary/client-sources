#pragma once

#include "util/includes.h"

#define LMDEBUG true

using createdvms_t = jint(JNICALL*)(JavaVM**, jsize, jsize*);
using wglSwapBuffer = bool(__stdcall*)(_In_ HDC);

namespace lm {
	class latemod {
	private:
		jvmtiEnv* jvmti;
		JNIEnv* env;
		JavaVM* vm;

		void* wgl_swap_buffers;
	public:
		bool b_running = true;
		HWND targetWindow;

		std::shared_ptr<hook_t<wglSwapBuffer>> swapBuffersHook;

		void log(const char* msg);

		latemod();
		void init();
		~latemod();
	};

}

extern std::shared_ptr<lm::latemod> cheat;
