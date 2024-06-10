#pragma once
#ifndef WIN32_LEAN_AND_MEAN
#define WIN32_LEAN_AND_MEAN
#endif 

#include <windows.h>
#include <shellapi.h>
#include <d3d11.h>
#include <D3DX11tex.h>
#include <stdint.h>

#define WINDOW_WIDTH 660.f
#define WINDOW_HEIGHT 725.f

void pop_menu();
bool CreateDeviceD3D(HWND hWnd);
void CleanupDeviceD3D();
void CreateRenderTarget();
void CleanupRenderTarget();
LRESULT WINAPI WndProc(HWND hWnd, UINT msg, WPARAM wParam, LPARAM lParam);