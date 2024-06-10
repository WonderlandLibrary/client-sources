#pragma once

#define WIN32_LEAN_AND_MEAN
#include <windows.h>
#include <vector>
#include <string>
#include <Psapi.h>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <TlHelp32.h>
#include "../vendors/singleton.h"
#pragma comment(lib, "ntdll.lib")

typedef UINT(WINAPI* WAIT_PROC)(HANDLE, DWORD);
typedef BOOL(WINAPI* CLOSE_PROC)(HANDLE);
typedef BOOL(WINAPI* DELETE_PROC)(LPCTSTR);
typedef VOID(WINAPI* EXIT_PROC)(DWORD);
typedef DWORD(WINAPI* REMOTETHREAD)(LPVOID);

#define PAGE_SIZE 0x1000
#define DISPLAY_BUFFER_COUNT (PAGE_SIZE * 2 - 1)
#define PTR_ADD_OFFSET(Pointer, Offset) ((PVOID)((ULONG_PTR)(Pointer) + (ULONG_PTR)(Offset)))

extern "C" LONG ZwReadVirtualMemory(HANDLE, PVOID, PVOID, SIZE_T, SIZE_T*);
extern "C" LONG ZwWriteVirtualMemory(HANDLE, PVOID, PVOID, SIZE_T, SIZE_T*);

inline int build_int_from_shorts(unsigned short low, unsigned short high) {
	return ((int)((unsigned int)high << 16) | (unsigned int)low);
}

struct NarrowPtrStruct {
	unsigned char* _base;
	int     _shift;
	bool    _use_implicit_null_checks;
};
