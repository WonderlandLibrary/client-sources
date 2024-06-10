#include <vector>
#include "securityhelper.hpp"

#include  "nt.h"
#include "crc32.h"

// hook checks
#include "../authentification/auth.hpp"
#include "../utilities/requests.hpp"

#include "../../vendors/minhook/MinHook.h"

#ifndef _DEBUG
#include <ThemidaSDK.h>
#endif

namespace SecurityHelper::Integrity {
	__forceinline int check_remote_debugger_present_api()
	{
		auto dbg_present = 0;

		CheckRemoteDebuggerPresent(GetCurrentProcess(), &dbg_present);

		return dbg_present;
	}

	__forceinline int nt_query_information_process_debug_flags()
	{
		const auto debug_flags = 0x1f;

		const auto query_info_process = reinterpret_cast<NtQueryInformationProcessTypedef>(GetProcAddress(
			GetModuleHandleW(L"ntdll.dll"), "NtQueryInformationProcess"));

		auto debug_inherit = 0;

		const auto status = query_info_process(GetCurrentProcess(), debug_flags, &debug_inherit,
			sizeof(DWORD),
			nullptr);

		if (status == 0x00000000 && debug_inherit == 0)
		{
			return 1;
		}

		return 0;
	}

	__forceinline int nt_query_information_process_debug_object()
	{
		const auto debug_object_handle = 0x1e;

		const auto query_info_process = reinterpret_cast<NtQueryInformationProcessTypedef>(GetProcAddress(
			GetModuleHandleW(L"ntdll.dll"), "NtQueryInformationProcess"));

		HANDLE debug_object = nullptr;

		const auto information_length = sizeof(ULONG) * 2;

		const auto status = query_info_process(GetCurrentProcess(), debug_object_handle, &debug_object,
			information_length,
			nullptr);

		if (status == 0x00000000 && debug_object)
		{
			return 1;
		}

		return 0;
	}

	__forceinline int titanhide()
	{
		const auto module = GetModuleHandleW(L"ntdll.dll");

		const auto information = reinterpret_cast<NtQuerySystemInformationTypedef>(GetProcAddress(module, "NtQuerySystemInformation"));

		SYSTEM_CODEINTEGRITY_INFORMATION sci;

		sci.Length = sizeof sci;

		information(SystemCodeIntegrityInformation, &sci, sizeof sci, nullptr);

		const auto ret = sci.CodeIntegrityOptions & CODEINTEGRITY_OPTION_TESTSIGN || sci.CodeIntegrityOptions &
			CODEINTEGRITY_OPTION_DEBUGMODE_ENABLED;

		return ret;
	}

	__forceinline bool heapDebuggerFlags() // https://anti-debug.checkpoint.com/techniques/debug-flags.html#manual-checks-heap-protection
	{
		PPEB pPeb = (PPEB)__readgsqword(0x60);
		PVOID pHeapBase = (PVOID)(*(PDWORD_PTR)((PBYTE)pPeb + 0x30));
		DWORD dwHeapFlagsOffset = 0x70;
		DWORD dwHeapForceFlagsOffset = 0x74;

		PDWORD pdwHeapFlags = (PDWORD)((PBYTE)pHeapBase + dwHeapFlagsOffset);
		PDWORD pdwHeapForceFlags = (PDWORD)((PBYTE)pHeapBase + dwHeapForceFlagsOffset);
		return (*pdwHeapFlags & ~HEAP_GROWABLE) || (*pdwHeapForceFlags != 0);
	}

	__forceinline bool heapProtectionFlag()
	{
		PROCESS_HEAP_ENTRY HeapEntry = { 0 };
		do
		{
			if (!HeapWalk(GetProcessHeap(), &HeapEntry))
				return false;
		} while (HeapEntry.wFlags != PROCESS_HEAP_ENTRY_BUSY);

		PVOID pOverlapped = (PBYTE)HeapEntry.lpData + HeapEntry.cbData;
		return ((DWORD)(*(PDWORD)pOverlapped) == 0xABABABAB);
	}

	__forceinline bool pebBeingDebugged()
	{
		PPEB pPeb = (PPEB)__readgsqword(0x60);
		return pPeb->BeingDebugged;
	}

	__forceinline bool IsDebuggerPresentPatched() // https://github.com/grossekette/artemis/blob/main/Artemis.cpp
	{
		HMODULE hKernel32 = GetModuleHandleA("kernel32.dll");
		if (!hKernel32)
			return false;

		FARPROC pIsDebuggerPresent = GetProcAddress(hKernel32, "IsDebuggerPresent");
		if (!pIsDebuggerPresent)
			return false;

		HANDLE hSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
		if (INVALID_HANDLE_VALUE == hSnapshot)
			return false;

		PROCESSENTRY32W ProcessEntry;
		ProcessEntry.dwSize = sizeof(PROCESSENTRY32W);

		if (!Process32FirstW(hSnapshot, &ProcessEntry))
			return false;

		bool bDebuggerPresent = false;
		HANDLE hProcess = NULL;
		DWORD dwFuncBytes = 0;
		const DWORD dwCurrentPID = GetCurrentProcessId();
		do
		{
			__try
			{
				if (dwCurrentPID == ProcessEntry.th32ProcessID)
					__leave;

				hProcess = OpenProcess(PROCESS_ALL_ACCESS, FALSE, ProcessEntry.th32ProcessID);
				if (NULL == hProcess)
					__leave;

				if (!ReadProcessMemory(hProcess, pIsDebuggerPresent, &dwFuncBytes, sizeof(DWORD), NULL))
					__leave;

				if (dwFuncBytes != *(PDWORD)pIsDebuggerPresent)
				{
					bDebuggerPresent = true;
					__leave;
				}
			}
			__finally
			{
				if (hProcess)
					CloseHandle(hProcess);
				else
				{

				}
			}
		} while (Process32NextW(hSnapshot, &ProcessEntry));

		if (hSnapshot)
			CloseHandle(hSnapshot);

		return bDebuggerPresent;
	}

	__forceinline void PatchRemoteBreakin() // https://github.com/grossekette/artemis/blob/main/Artemis.cpp
	{
		HMODULE hNtdll = GetModuleHandleA("ntdll.dll");
		if (!hNtdll)
			return;

		FARPROC pDbgUiRemoteBreakin = GetProcAddress(hNtdll, "DbgUiRemoteBreakin");
		if (!pDbgUiRemoteBreakin)
			return;

		HMODULE hKernel32 = GetModuleHandleA("kernel32.dll");
		if (!hKernel32)
			return;

		FARPROC pTerminateProcess = GetProcAddress(hKernel32, "TerminateProcess");
		if (!pTerminateProcess)
			return;

		DbgUiRemoteBreakinPatch patch = { 0 };
		patch.push_0 = '\x6A\x00';
		patch.push = '\x68';
		patch.CurrentPorcessHandle = 0xFFFFFFFF;
		patch.mov_eax = '\xB8';
		patch.TerminateProcess = (DWORD)pTerminateProcess;
		patch.call_eax = '\xFF\xD0';

		DWORD dwOldProtect;
		if (!VirtualProtect(pDbgUiRemoteBreakin, sizeof(DbgUiRemoteBreakinPatch), PAGE_READWRITE, &dwOldProtect))
			return;

		::memcpy_s(pDbgUiRemoteBreakin, sizeof(DbgUiRemoteBreakinPatch),
			&patch, sizeof(DbgUiRemoteBreakinPatch));
		VirtualProtect(pDbgUiRemoteBreakin, sizeof(DbgUiRemoteBreakinPatch), dwOldProtect, &dwOldProtect);
	}

	static std::vector<PVOID> fnList = { &MessageBoxA, &GetProcAddress, &VirtualProtect, &GetModuleHandleA, &FindWindowA, &RegOpenKeyA, &RegQueryValueExA };
	__forceinline bool FoundBP(BYTE Byte)
	{
		for (PVOID function : fnList)
		{
			PBYTE pBytes = (PBYTE)function;
			for (SIZE_T i = 0; i <= SIZE_T(function); i++)
			{
				if (((0 > 0) && (i >= 0)) ||
					(pBytes[i] == 0xC3))
					break;

				if (pBytes[i] == Byte)
					return true;
			}
			return false;
		}
	}

	__forceinline bool CheckHardwareBP()
	{
		CONTEXT ctx;
		ZeroMemory(&ctx, sizeof(CONTEXT));
		ctx.ContextFlags = CONTEXT_DEBUG_REGISTERS;

		if (!GetThreadContext(GetCurrentThread(), &ctx))
			return false;

		return ctx.Dr0 || ctx.Dr1 || ctx.Dr2 || ctx.Dr3;
	}
	
	bool is_being_debugged()
	{
		return check_remote_debugger_present_api() || nt_query_information_process_debug_flags() || nt_query_information_process_debug_object() || heapDebuggerFlags() || heapProtectionFlag() || pebBeingDebugged() || IsDebuggerPresentPatched();
	}

	bool has_hooks()
	{
#ifndef _DEBUG
		VM_LION_BLACK_START
#endif 
			auto fc = &Authentification::login;
			auto fc2 = &Authentification::get_hwid;
			auto fc3 = &RequestHelper::send_request;
			auto fc4 = &is_being_debugged;

			std::vector<void*> addys{
				(void*)GetProcAddress(GetModuleHandleW(L"kernel32.dll"), "GetModuleHandleA"),		//
				(void*)GetProcAddress(GetModuleHandleW(L"user32.dll"), "FindWindowA"),				//
				(void*)GetProcAddress(GetModuleHandleW(L"Advapi32.dll"), "RegOpenKeyA"),			//
				(void*)GetProcAddress(GetModuleHandleW(L"Advapi32.dll"), "RegQueryValueExA"),		//  THEMIDIE
				(void*)GetProcAddress(GetModuleHandleW(L"ntdll.dll"), "NtSetInformationThread"),	//
				(void*)GetProcAddress(GetModuleHandleW(L"ntdll.dll"), "NtQueryVirtualMemory"),		//
				(void*)GetProcAddress(GetModuleHandleW(L"ws2_32.dll"), "recv"),		
				(void*)GetProcAddress(GetModuleHandleW(L"kernel32.dll"), "GetVolumeInformationA"),
				(void*)GetProcAddress(GetModuleHandleW(L"kernel32.dll"), "TerminateProcess"),
				(void*)GetProcAddress(GetModuleHandleW(L"ntdll.dll"), "NtQuerySystemInformation"),
				(void*&)fc,
				(void*&)fc2,
				(void*&)fc3,
				(void*&)fc4,
		};

		for (auto address : addys) {
			if (address) {
				while (*(BYTE*)(address) == 0x90) { // while instruction == NOP 
					address = (void*)((uintptr_t)address + 0x1);
					Sleep(1);
				}

				if (*(BYTE*)address == 0xE9 || *(BYTE*)address == 0xC3) { // JMP / RET
					return true;
				}
			}
		}

#ifndef _DEBUG
		VM_LION_BLACK_END
#endif 
		return false;
	}

	std::vector<CODE_CRC32>  _codeCrc32;
	void initialize_codesection()
	{
		PIMAGE_DOS_HEADER	dosHead;
		PIMAGE_NT_HEADERS	ntHead;
		PIMAGE_SECTION_HEADER secHead;
		CODE_CRC32	codeSection;

		const auto _moduleHandle = GetModuleHandle(NULL);

		if (IsBadReadPtr(_moduleHandle, sizeof(void*)) == 0)
		{
			dosHead = (PIMAGE_DOS_HEADER)_moduleHandle;

			if (dosHead == NULL || dosHead->e_magic != IMAGE_DOS_SIGNATURE)
			{
				return;
			}

			ntHead = ImageNtHeader(dosHead);
			if (ntHead == NULL || ntHead->Signature != IMAGE_NT_SIGNATURE)
			{
				return;
			}

			secHead = IMAGE_FIRST_SECTION(ntHead);
			_codeCrc32.clear();

			for (size_t Index = 0; Index < ntHead->FileHeader.NumberOfSections; Index++)
			{
				if ((secHead->Characteristics & IMAGE_SCN_MEM_READ) &&
					!(secHead->Characteristics & IMAGE_SCN_MEM_WRITE))
				{
					codeSection.m_va = (PVOID)((DWORD_PTR)_moduleHandle + secHead->VirtualAddress);
					codeSection.m_size = secHead->Misc.VirtualSize;
					codeSection.m_crc32 = crc32(codeSection.m_va, codeSection.m_size);
					_codeCrc32.push_back(codeSection);
				}
				secHead++;
			}
		}
	}

	bool has_codesection_changed()
	{
		for (size_t i = 0; i < _codeCrc32.size(); i++)
		{
			if (crc32(_codeCrc32[i].m_va, _codeCrc32[i].m_size) != _codeCrc32[i].m_crc32)
			{
				return true;
			}
		}

		return false;
	}

	void clear_handles()
	{
		_NtQuerySystemInformation NtQuerySystemInformation =
			reinterpret_cast<_NtQuerySystemInformation>(GetProcAddress(GetModuleHandleA("ntdll.dll"), "NtQuerySystemInformation"));
		_NtDuplicateObject NtDuplicateObject =
			reinterpret_cast<_NtDuplicateObject>(GetProcAddress(GetModuleHandleA("ntdll.dll"), "NtDuplicateObject"));
		_NtQueryObject NtQueryObject =
			reinterpret_cast<_NtQueryObject>(GetProcAddress(GetModuleHandleA("ntdll.dll"), "NtQueryObject"));

		NTSTATUS status;
		PSYSTEM_HANDLE_INFORMATION handleInfo;
		ULONG handleInfoSize = 0x10000;
		ULONG i;

		handleInfo = (PSYSTEM_HANDLE_INFORMATION)malloc(handleInfoSize);

		while ((status = NtQuerySystemInformation(16 /*system handle informations*/, handleInfo, handleInfoSize, NULL)) == 0xc0000004/*STATUS_INFO_LENGTH_MISMATCH*/) {
			handleInfo = (PSYSTEM_HANDLE_INFORMATION)realloc(handleInfo, handleInfoSize *= 2);
			Sleep(1);
		}

		if (!NT_SUCCESS(status)) {
			return;
		}

		for (i = 0; i < handleInfo->HandleCount; i++)
		{
			SYSTEM_HANDLE handle = handleInfo->Handles[i];

			if (handle.ProcessId == GetCurrentProcessId()) continue;

			HANDLE dup_hnd = OpenProcess(PROCESS_DUP_HANDLE, false, handle.ProcessId);
			if (dup_hnd) {
				HANDLE hproc = INVALID_HANDLE_VALUE;
				if (DuplicateHandle(dup_hnd, reinterpret_cast<HANDLE>(handle.Handle), GetCurrentProcess(), &hproc, PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, FALSE, 0)) {
					unsigned int dwTarget = GetProcessId(hproc);
					if (dwTarget == GetCurrentProcessId()) {
						CloseHandle(hproc);
						DuplicateHandle(dup_hnd, reinterpret_cast<HANDLE>(handle.Handle), NULL, NULL, 0, FALSE, DUPLICATE_CLOSE_SOURCE);
					}
					else {
						CloseHandle(hproc);
					}
				}
				CloseHandle(dup_hnd);
			}

			Sleep(1);
		}

		free(handleInfo);
	}
}