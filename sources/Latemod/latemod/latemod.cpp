#include "latemod.h"

#include "hooks/hooks.h"

using namespace lm;

void latemod::log(const char* msg)
{
#if LMDEBUG true
	std::cout << msg << std::endl;
#endif
	return;
}

latemod::latemod()
{
	AllocConsole();
	SetConsoleCtrlHandler(nullptr, true);
	FILE* fIn;
	FILE* fOut;
	freopen_s(&fIn, "conin$", "r", stdin);
	freopen_s(&fOut, "conout$", "w", stdout);
	freopen_s(&fOut, "conout$", "w", stderr);
}

void latemod::init()
{

	auto jvm = GetModuleHandleA(xorstr("jvm.dll").crypt_get());

	if (jvm == nullptr) {
		this->log(xorstr("Couldn't retrieve JVM").crypt_get());
		return;
	}

	auto createdvms_fn = GetProcAddress(jvm, xorstr("JNI_GetCreatedJavaVMs").crypt_get());

	auto get_created_vms = reinterpret_cast<createdvms_t>(createdvms_fn);

	jsize result;
	if (get_created_vms(&this->vm, 1, &result) != JNI_OK || result == 0) {
		this->log(xorstr("Couldn't get created VMs").crypt_get());
		return;
	}

	if (this->vm->AttachCurrentThread(reinterpret_cast<void**>(&this->env), nullptr) != JNI_OK) {
		this->log(xorstr("Couldn't attach to thread").crypt_get());
		return;
	}

	if (this->vm->GetEnv(reinterpret_cast<void**>(&this->jvmti), JVMTI_VERSION_1_2) != JNI_OK) {
		this->log(xorstr("Couldn't retrieve JVMTI enviroment").crypt_get());
		return;
	}

	auto opengl = GetModuleHandleA(xorstr("opengl32.dll").crypt_get());
	if (opengl == nullptr) {
		this->log(xorstr("Couldn't get opengl32.dll handle").crypt_get());
		return;
	}

	this->wgl_swap_buffers = GetProcAddress(opengl, xorstr("wglSwapBuffers").crypt_get());

	/* initialize hooks */
	MH_Initialize();

	this->swapBuffersHook = std::make_shared<hook_t<wglSwapBuffer>>(this->wgl_swap_buffers, hooks::wgl_swap_buffers);
	this->swapBuffersHook->enable();

	this->targetWindow = FindWindowA(nullptr, xorstr("Minecraft 1.7.10").crypt_get());

	/* initialize window hook */

	this->log(xorstr("Attached").crypt_get());

	while (this->b_running)
	{
		std::this_thread::sleep_for(std::chrono::milliseconds(1));

		if (GetAsyncKeyState(VK_HOME))
		{
			this->b_running = false;
			continue;
		}

		/* do latemod stuff */
	}
}

latemod::~latemod()
{
	this->swapBuffersHook->disable();
	MH_Uninitialize();

	jvmti->DisposeEnvironment();
	vm->DetachCurrentThread();
}
