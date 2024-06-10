#pragma once
#include <windows.h>
#include <TlHelp32.h>
#include <winternl.h>
#include <ImageHlp.h>
#include <string>

namespace SecurityHelper
{
	namespace Integrity {
		bool is_being_debugged();
		bool has_hooks();

		void initialize_codesection();
		bool has_codesection_changed();

		void clear_handles();

		__forceinline void anti_attach()
		{
			HMODULE hNtdll = GetModuleHandleA("ntdll.dll");
			if (!hNtdll)
				return;

			FARPROC pDbgBreakPoint = GetProcAddress(hNtdll, "DbgBreakPoint");
			if (!pDbgBreakPoint)
				return;

			DWORD dwOldProtect;
			if (!VirtualProtect(pDbgBreakPoint, 1, PAGE_EXECUTE_READWRITE, &dwOldProtect))
				return;

			*(PBYTE)pDbgBreakPoint = (BYTE)0xC3; // 0xC3 == RET
		}
	}
}