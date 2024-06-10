#pragma once

/* -> defines <- */

#define NOMINMAX
#define WIN32_LEAN_AND_MEAN
#define delay std::chrono::milliseconds( 1 )

/* -> header files <- */

#include <functional>
#include <windows.h>
#include <algorithm>
#include <strsafe.h>
#include <exception>
#include <iostream>
#include <tchar.h>
#include <vector>
#include <random>
#include <thread>
#include <string>
#include <thread>
#include <array> 

/* -> imgui files <- */

#include <d3d9.h>
#include <d3dx9.h>
#include <imgui.h>
#include <imgui_stdlib.h>
#include <imgui_impl_dx9.h>
#include <imgui_impl_win32.h>

/* -> external files <- */

#include "ui\ui.hpp"
#include "utils\vars.hpp"
#include "utils\utils.hpp"
#include "funcs\funcs.hpp"