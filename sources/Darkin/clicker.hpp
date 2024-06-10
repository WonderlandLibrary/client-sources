#pragma once

// defining stuff we need
#define WIN32_LEAN_AND_MEAN
#define NOMINMAX

// includes
#include <windows.h>
#include <iostream>
#include <shared_mutex>
#include <vector>
#include <random>
#include <unordered_map>
#include <string>

#include "imgui.h"
#include "imgui_impl_dx9.h"
#include "imgui_impl_win32.h"
#include "xorstr.hpp"

namespace clicker
{
	DWORD __stdcall work(LPVOID lParam);
}