
#include <windows.h>
#include <iostream>
#include <intrin.h>
#include <vector>
#include <map>
#include <chrono>
#include <fstream>
#include <TlHelp32.h>
#include <winternl.h>

#include <gl/GL.h>
#include <gl/GLU.h>
#include <GLFW/glfw3.h>
#include <GLFW/glfw3native.h>

#define FORCE_CRASH for (unsigned __int64 x = 0; x < 0xffffffffffffffff; ++x) { *(unsigned char*)x = 0; } return 0;