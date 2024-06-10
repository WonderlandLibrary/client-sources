#include "functions.h"
/*
BOOL WINAPI DllMain(
_In_ HINSTANCE hinstDLL,
_In_ DWORD     fdwReason,
_In_ LPVOID    lpvReserved
);
*/

typedef jint(*hJNI_GetCreatedJavaVMs)(JavaVM** vmBuf, jsize bufLen, jsize* nVMs);

hJNI_GetCreatedJavaVMs oJNI_GetCreatedJavaVMs;

HMODULE jvmHandle;
FARPROC func_JNI_GetCreatedJavaVMs;
JavaVM *jvm;
JNIEnv *jenv;

using namespace std;
bool loopd = true;
bool sus = true;
int state;
LPCTSTR WindowName = "Minecraft 1.7.10";
POINT pt;
HMODULE MODULE;
void getMouseOver(float p_78473_1_, JNIEnv* env);

DWORD WINAPI LoopFunction(LPVOID lpParam)
{
	bool keep = false;
	jvmHandle = GetModuleHandleA("jvm.dll");
	func_JNI_GetCreatedJavaVMs = GetProcAddress(jvmHandle, "JNI_GetCreatedJavaVMs");
	oJNI_GetCreatedJavaVMs = (hJNI_GetCreatedJavaVMs)func_JNI_GetCreatedJavaVMs;
	jint returnOF = oJNI_GetCreatedJavaVMs(&jvm, 1, NULL);
	jint returnOf1 = jvm->AttachCurrentThread((void **)&jenv, NULL);
	if (jenv != nullptr)
	{
		postPreInit(jenv);
		while (1)
		{	
			while (sus = true) {
				HWND hwnd = FindWindowA(NULL, WindowName);
				if (GetKeyState(VK_F12) & 0x8000) {
					FreeLibraryAndExitThread(MODULE, 0);
				}
				loopd = true;
				HWND currentWindow = GetForegroundWindow();
				if (GetAsyncKeyState(VK_END)) {
					state = 0;
				}

				if (GetAsyncKeyState(VK_LBUTTON)) {
					state = 1;
				}

				while (GetAsyncKeyState(VK_UP)) {
					thePlayer = getPlayer(jenv);
					theWorld = getWorld(jenv);
					renderViewEntity = jenv->GetObjectField(mc, renderViewEntityf);
				}

				while (GetAsyncKeyState(VK_LBUTTON)) {
					if (currentWindow == hwnd) {
						GetCursorPos(&pt);
						SendMessage(hwnd, WM_LBUTTONDOWN, MK_LBUTTON, MAKELPARAM(pt.x, pt.y));
						if (theWorld != NULL) {
							if (thePlayer != NULL)
								if (jenv->CallBooleanMethod(thePlayer, isUsingItemf) == false) {
										if (jenv->GetObjectField(jenv->GetObjectField(mc, objectMouseOverf), entityHitf) != NULL && !keep)
											keep = true;
										double reach = 6;
										cout << reach << endl;
										if (keep) {
											getMouseOver(jenv->GetFloatField(jenv->GetObjectField(mc, timerf), renderPartialTicksf), jenv, reach);
											
										}
								}
						}
						SendMessage(hwnd, WM_LBUTTONUP, 0, MAKELPARAM(pt.x, pt.y));
					}
				}

				if (GetAsyncKeyState(0x58)) {
					while (loopd) {
						sus = false;
						if (GetAsyncKeyState(0x5A)) {
							sus = true;
							loopd = false;
						}
					}
				}
			}
		}
	Sleep(200);
	cleanup(jenv);
	}


	if (jenv->ExceptionCheck())
	{
		jenv->ExceptionDescribe();
	}
	jvm->DetachCurrentThread();
	//some CPU relief
	Sleep(200);
	return 0;
}

void init()
{
	CreateThread(NULL, 0, &LoopFunction, NULL, 0, NULL);
}



//
BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved)
{
	//AllocConsole();
	//freopen("CONOUT$", "w", stdout);
	switch (fdwReason)
	{
	case DLL_PROCESS_ATTACH:
		init();
		//case DLL_PROCESS_DETACH:
		//case DLL_THREAD_ATTACH:
		//case DLL_THREAD_DETACH:
	}
	return true;
}