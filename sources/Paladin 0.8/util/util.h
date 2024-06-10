#pragma once

#include <windows.h>
#include <stdio.h>
#include <iostream>
#include <vector>
#include <string>
#include <filesystem>
#include "util.h"
#include "../auth.h"

#include <d3d9.h>
#include <dinput.h>
#include <tchar.h>
#include <winternl.h>

#define STATUS_INFO_LENGTH_MISMATCH 0xc0000004
#define SystemHandleInformation 16
#define ObjectBasicInformation 0
#define ObjectNameInformation 1
#define ObjectTypeInformation 2
typedef struct _SYSTEM_HANDLE {
	ULONG ProcessId;
	BYTE ObjectTypeNumber;
	BYTE Flags;
	USHORT Handle;
	PVOID Object;
	ACCESS_MASK GrantedAccess;
} SYSTEM_HANDLE, *PSYSTEM_HANDLE;
typedef struct _SYSTEM_HANDLE_INFORMATION {
	ULONG HandleCount;
	SYSTEM_HANDLE Handles[1];
} SYSTEM_HANDLE_INFORMATION, *PSYSTEM_HANDLE_INFORMATION;
typedef NTSTATUS(NTAPI *_NtDuplicateObject)(HANDLE SourceProcessHandle, HANDLE SourceHandle, HANDLE TargetProcessHandle, PHANDLE TargetHandle, ACCESS_MASK DesiredAccess, ULONG Attributes, ULONG Options);

namespace util {

	struct window_enum
	{
		unsigned long pid;
		HWND out;
	};

	namespace // Private
	{
		static BOOL CALLBACK FindWindowImpl(HWND hwnd, LPARAM lParam)
		{
			const DWORD TITLE_SIZE = 1024;
			char windowTitle[TITLE_SIZE];
			GetWindowText(hwnd, windowTitle, TITLE_SIZE);

			int length = ::GetWindowTextLength(hwnd);
			std::string title(&windowTitle[0]);

			if (!IsWindowVisible(hwnd) || length == 0 || title == xors("Program Manager"))
				return true;

			std::stringstream finaltitle;
			std::regex_replace(std::ostream_iterator<char>(finaltitle), title.begin(), title.end(), allowed_window_chars, "");

			std::vector<std::pair<HWND, std::string>>& windows = *reinterpret_cast<std::vector<std::pair<HWND, std::string>>*>(lParam);
			windows.push_back(std::make_pair(hwnd, finaltitle.str()));
			return true;
		}
		static BOOL CALLBACK FindWindowImpl2(HWND hwnd, LPARAM lParam)
		{
			window_enum& we = *(window_enum*)lParam;
			unsigned long pid = 0;
			GetWindowThreadProcessId(hwnd, &pid);
			if (pid != we.pid)
				return true;
			we.out = hwnd;
			return false;
		}
		static BOOL CALLBACK FindWindowImpl3(HWND hwnd, LPARAM lParam)
		{
			std::pair<std::string, std::vector<std::pair<DWORD, HWND>>*>& wtf = *reinterpret_cast<std::pair<std::string, std::vector<std::pair<DWORD, HWND>>*>*>(lParam);

			char clazz[MAX_PATH];
			GetClassName(hwnd, clazz, MAX_PATH);
			if (strcmp(clazz, wtf.first.c_str()) == 0)
			{
				DWORD pid = 0;
				GetWindowThreadProcessId(hwnd, &pid);
				wtf.second->push_back({ pid, hwnd });
			}

			return true;
		}
	}

	static std::wstring_convert<std::codecvt_utf8<wchar_t>, wchar_t> converter;
	static std::wstring s2ws(const std::string& str)
	{
		return converter.from_bytes(str);
	}
	static std::string ws2s(const std::wstring& wstr)
	{
		return converter.to_bytes(wstr);
	}

	inline std::string get_full_path(HANDLE hVol, DWORDLONG fileRefNum)
	{
		VMProtectBeginMutation("get_full_path");
		FILE_ID_DESCRIPTOR fid;

		ZeroMemory(&fid, sizeof fid);

		fid.dwSize = sizeof fid;
		fid.Type = FileIdType;
		fid.FileId.QuadPart = fileRefNum;

		auto handle = OpenFileById(hVol, &fid, 0, FILE_SHARE_READ | FILE_SHARE_WRITE | FILE_SHARE_DELETE, nullptr, FILE_FLAG_BACKUP_SEMANTICS);

		if (handle == INVALID_HANDLE_VALUE)
			return  std::to_string(GetLastError());

		char buffer[1024];

		GetFinalPathNameByHandleA(handle, &buffer[0], 1024, 0);

		return std::string(&buffer[0]);
		VMProtectEnd();
	}

	static std::vector<std::pair<std::string, HWND>> findwindowstarting(std::string k) {
		std::vector<std::pair<HWND, std::string>> windows;
		std::vector<std::pair<std::string, HWND>> new_windows;

		EnumWindows(FindWindowImpl, reinterpret_cast<LPARAM>(&windows));

		for (const auto& window : windows)
			if (window.second.find(k) != std::string::npos)
				new_windows.push_back({ window.second, window.first });

		return new_windows;
	}
	static HWND findwindowbypid(unsigned long pid)
	{
		window_enum we;
		we.pid = pid;
		we.out = 0;
		EnumWindows(FindWindowImpl2, (LPARAM)&we);
		return we.out;
	}

	static std::vector<DWORD> findprocessesbyname(const std::string& processName)
	{
		std::vector<DWORD> processes;

		PROCESSENTRY32 processInfo;
		processInfo.dwSize = sizeof(processInfo);

		HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
		if (processesSnapshot == INVALID_HANDLE_VALUE)
			return processes;

		Process32First(processesSnapshot, &processInfo);
		if (!processName.compare(processInfo.szExeFile))
		{
			processes.push_back(processInfo.th32ProcessID);
		}

		while (Process32Next(processesSnapshot, &processInfo))
		{
			if (!processName.compare(processInfo.szExeFile))
			{
				processes.push_back(processInfo.th32ProcessID);
			}
		}

		CloseHandle(processesSnapshot);
		return processes;
	}

	static std::string getprocesspath(DWORD pid)
	{
		char path[MAX_PATH];
		DWORD size = MAX_PATH;

		HANDLE k = OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION, false, pid);
		auto winver = WindowsVersion();
		if (winver.dwMajorVersion == 6 && winver.dwMinorVersion == 1) // Windows 7
			QueryFullProcessImageNameA(k, 0, path, &size);
		else
			GetModuleFileNameExA(k, NULL, path, MAX_PATH);

		if (k)
			CloseHandle(k);
		return path;
	}

	static bool verifyembeddedsignature(LPCWSTR pwszSourceFile)
	{
		VMProtectBeginMutation("verifyembeddedsignature");
		LONG lStatus;

		// Initialize the WINTRUST_FILE_INFO structure.

		WINTRUST_FILE_INFO FileData;

		memset(&FileData, 0, sizeof(FileData));

		FileData.cbStruct = sizeof(WINTRUST_FILE_INFO);
		FileData.pcwszFilePath = pwszSourceFile;
		FileData.hFile = NULL;
		FileData.pgKnownSubject = NULL;

		/*
		WVTPolicyGUID specifies the policy to apply on the file
		WINTRUST_ACTION_GENERIC_VERIFY_V2 policy checks:

		1) The certificate used to sign the file chains up to a root
		certificate located in the trusted root certificate store. This
		implies that the identity of the publisher has been verified by
		a certification authority.

		2) In cases where user interface is displayed (which this example
		does not do), WinVerifyTrust will check for whether the
		end entity certificate is stored in the trusted publisher store,
		implying that the user trusts content from this publisher.

		3) The end entity certificate has sufficient permission to sign
		code, as indicated by the presence of a code signing EKU or no
		EKU.
		*/

		GUID WVTPolicyGUID = WINTRUST_ACTION_GENERIC_VERIFY_V2;
		WINTRUST_DATA WinTrustData;

		// Initialize the WinVerifyTrust input data structure.

		// Default all fields to 0.
		memset(&WinTrustData, 0, sizeof(WinTrustData));

		WinTrustData.cbStruct = sizeof(WinTrustData);

		// Use default code signing EKU.
		WinTrustData.pPolicyCallbackData = NULL;

		// No data to pass to SIP.
		WinTrustData.pSIPClientData = NULL;

		// Disable WVT UI.
		WinTrustData.dwUIChoice = WTD_UI_NONE;

		// No revocation checking.
		WinTrustData.fdwRevocationChecks = WTD_REVOKE_NONE;

		// Verify an embedded signature on a file.
		WinTrustData.dwUnionChoice = WTD_CHOICE_FILE;

		// Verify action.
		WinTrustData.dwStateAction = WTD_STATEACTION_VERIFY;

		// Verification sets this value.
		WinTrustData.hWVTStateData = NULL;

		// Not used.
		WinTrustData.pwszURLReference = NULL;

		// This is not applicable if there is no UI because it changes 
		// the UI to accommodate running applications instead of 
		// installing applications.
		WinTrustData.dwUIContext = 0;

		// Set pFile.
		WinTrustData.pFile = &FileData;

		// WinVerifyTrust verifies signatures as specified by the GUID 
		// and Wintrust_Data.
		lStatus = WinVerifyTrust(
			NULL,
			&WVTPolicyGUID,
			&WinTrustData);

		bool good = false;

		switch (lStatus) {
		case ERROR_SUCCESS:
			/*
			Signed file:
			- Hash that represents the subject is trusted.

			- Trusted publisher without any verification errors.
			*/
			good = true;
			break;
		default:
			// The UI was disabled in dwUIChoice or the admin policy 
			// has disabled user trust. lStatus contains the 
			// publisher or time stamp chain error.
			wprintf_s(L"Error is: 0x%x.\n", lStatus);
			break;
		}

		// Any hWVTStateData must be released by a call with close.
		WinTrustData.dwStateAction = WTD_STATEACTION_CLOSE;

		lStatus = WinVerifyTrust(
			NULL,
			&WVTPolicyGUID,
			&WinTrustData);

		return good;
		VMProtectEnd();
	}

	static std::vector<std::pair<DWORD, std::pair<std::string, ACCESS_MASK>>> findhandlestoprocess(DWORD pid)
	{
		std::vector<std::pair<DWORD, std::pair<std::string, ACCESS_MASK>>> keks;

		auto winver = WindowsVersion();
		DWORD pathSize = MAX_PATH;

		std::string defsvchost = getenv(xors("SystemDrive"));
		std::string defconhost = getenv(xors("SystemDrive"));
		std::string deflsass = getenv(xors("SystemDrive"));
		std::string defexplorer = getenv(xors("SystemDrive"));
		std::string defcsrss = getenv(xors("SystemDrive"));
		std::string defwininit = getenv(xors("SystemDrive"));
		std::string defwinlogon = getenv(xors("SystemDrive"));
		defsvchost.append(xors("\\Windows\\System32\\svchost.exe"));
		defconhost.append(xors("\\Windows\\System32\\conhost.exe"));
		deflsass.append(xors("\\Windows\\System32\\lsass.exe"));
		defexplorer.append(xors("\\Windows\\explorer.exe"));
		defcsrss.append(xors("\\Windows\\System32\\csrss.exe"));
		defwininit.append(xors("\\Windows\\System32\\wininit.exe"));
		defwinlogon.append(xors("\\Windows\\System32\\winlogon.exe"));

		wchar_t thispath[MAX_PATH];
		GetModuleFileNameW(NULL, thispath, MAX_PATH);

		PSYSTEM_HANDLE_INFORMATION handleInfo;
		ULONG handleInfoSize = 0x10000;
		handleInfo = (PSYSTEM_HANDLE_INFORMATION)malloc(handleInfoSize);

		NTSTATUS status;
		_NtDuplicateObject NtDuplicateObject = (_NtDuplicateObject)GetProcAddress(GetModuleHandleA(xors("ntdll.dll")), xors("NtDuplicateObject"));

		while ((status = NtQuerySystemInformation((SYSTEM_INFORMATION_CLASS)SystemHandleInformation, handleInfo, handleInfoSize, NULL)) == STATUS_INFO_LENGTH_MISMATCH)
			handleInfo = (PSYSTEM_HANDLE_INFORMATION)realloc(handleInfo, handleInfoSize *= 2);

		for (ULONG i = 0; i < handleInfo->HandleCount; i++)
		{
			SYSTEM_HANDLE handle = handleInfo->Handles[i];
			HANDLE dupHandle = NULL;
			
			if ((int)handle.ObjectTypeNumber != 7) // Process
				continue;
			
			HANDLE processHandle = OpenProcess(PROCESS_QUERY_LIMITED_INFORMATION | PROCESS_DUP_HANDLE, false, handle.ProcessId);
			NTSTATUS succ = NtDuplicateObject(processHandle, (HANDLE)handle.Handle, GetCurrentProcess(), &dupHandle, PROCESS_QUERY_LIMITED_INFORMATION, 0, 0);
			if (NT_SUCCESS(succ))
			{
				wchar_t modulePath[MAX_PATH];

				if (winver.dwMajorVersion == 6 && winver.dwMinorVersion == 1) // Win 7
					QueryFullProcessImageNameW(dupHandle, 0, modulePath, &pathSize);
				else
					GetModuleFileNameExW(dupHandle, NULL, modulePath, MAX_PATH);
				std::wstring mod(modulePath);
				
				if (mod == thispath)
				{
					char badPath[MAX_PATH];
					
					if (winver.dwMajorVersion == 6 && winver.dwMinorVersion == 1) // Win 7
						QueryFullProcessImageNameA(processHandle, 0, badPath, &pathSize);
					else
						GetModuleFileNameExA(processHandle, NULL, badPath, MAX_PATH);
					
					if (_stricmp(badPath, defsvchost.c_str()) == 0 || _stricmp(badPath, deflsass.c_str()) == 0 || _stricmp(badPath, defexplorer.c_str()) == 0 || _stricmp(badPath, defcsrss.c_str()) == 0 || _stricmp(badPath, defwininit.c_str()) == 0)
					{
						if (winver.dwMajorVersion == 10) // why the fuck is nothing signed in windows 7 or 8?
						{
							if (!verifyembeddedsignature(s2ws(badPath).c_str()))
								goto yup;
						}
					}
					else if (_stricmp(badPath, defconhost.c_str()) != 0 && _stricmp(badPath, defwinlogon.c_str()) != 0) // conhost & winlogon are unsigned
						goto yup;
					goto kys;
					yup:
					keks.push_back({ handle.ProcessId, { badPath, handle.GrantedAccess } });
				}
			}
			kys:
			CloseHandle(processHandle);
			CloseHandle(dupHandle);
		}
		free(handleInfo);

		return keks;
	}

	static std::vector<std::pair<DWORD, HWND>> findprocessesbywindowclass(const std::string& clazz)
	{
		std::vector<std::pair<DWORD, HWND>> processes;
		
		std::pair<std::string, std::vector<std::pair<DWORD, HWND>>*> wtf;
		wtf.first = clazz; wtf.second = &processes;
		EnumWindows(FindWindowImpl3, reinterpret_cast<LPARAM>(&wtf));

		return processes;
	}
}