#pragma once
#define WIN32_LEAN_AND_MEAN
#include <Windows.h>
#include <winternl.h>
#pragma comment(lib, "ntdll.lib")

typedef enum _MEMORY_INFORMATION_CLASS {
    MemoryBasicInformation
} MEMORY_INFORMATION_CLASS;
extern "C" NTSTATUS ZwQueryVirtualMemory(HANDLE, void*, MEMORY_INFORMATION_CLASS, void*, SIZE_T, SIZE_T*);
extern "C" NTSTATUS ZwReadVirtualMemory(HANDLE, void*, void*, SIZE_T, SIZE_T*);
extern "C" NTSTATUS ZwWriteVirtualMemory(HANDLE, void*, void*, SIZE_T, SIZE_T*);