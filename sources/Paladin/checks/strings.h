#pragma once
#define WIN32_LEAN_AND_MEAN
#include "../windows.h"
#include <iostream>
#include "../util/json.hpp"
#include <comutil.h>
#include <winternl.h>
#include <TlHelp32.h>
#include "../VMProtectSDK.h"
using json = nlohmann::json;

DWORD get_svc_host(const wchar_t *type);

static DWORD FindProcessId(const std::string& processName)
{
	PROCESSENTRY32 processInfo;
	processInfo.dwSize = sizeof(processInfo);

	HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
	if (processesSnapshot == INVALID_HANDLE_VALUE)
		return 0;

	Process32First(processesSnapshot, &processInfo);
	if (!processName.compare(processInfo.szExeFile))
	{
		CloseHandle(processesSnapshot);
		return processInfo.th32ProcessID;
	}

	while (Process32Next(processesSnapshot, &processInfo))
	{
		if (!processName.compare(processInfo.szExeFile))
		{
			CloseHandle(processesSnapshot);
			return processInfo.th32ProcessID;
		}
	}

	CloseHandle(processesSnapshot);
	return 0;
}

static DWORD FindProcessParentId(DWORD pid)
{
	VMProtectBeginMutation("findprocessparentid");
	PROCESSENTRY32 processInfo;
	processInfo.dwSize = sizeof(processInfo);

	HANDLE processesSnapshot = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, NULL);
	if (processesSnapshot == INVALID_HANDLE_VALUE)
		return 0;

	Process32First(processesSnapshot, &processInfo);
	if (processInfo.th32ProcessID == pid)
	{
		CloseHandle(processesSnapshot);
		return processInfo.th32ParentProcessID;
	}

	while (Process32Next(processesSnapshot, &processInfo))
	{
		if (processInfo.th32ProcessID == pid)
		{
			CloseHandle(processesSnapshot);
			return processInfo.th32ParentProcessID;
		}
	}

	CloseHandle(processesSnapshot);
	return 0;
	VMProtectEnd();
}

#ifndef _SETPRIV
#define _SETPRIV

static BOOL SetPrivilege2(
	HANDLE hToken,          // access token handle
	LPCTSTR lpszPrivilege,  // name of privilege to enable/disable
	BOOL bEnablePrivilege   // to enable or disable privilege
) {

	TOKEN_PRIVILEGES tp;
	LUID luid;

	if (!LookupPrivilegeValue(
		NULL,            // lookup privilege on local system
		lpszPrivilege,   // privilege to lookup 
		&luid))        // receives LUID of privilege
	{
		printf("LookupPrivilegeValue error: %u\n", GetLastError());
		return FALSE;
	}

	tp.PrivilegeCount = 1;
	tp.Privileges[0].Luid = luid;
	if (bEnablePrivilege)
		tp.Privileges[0].Attributes = SE_PRIVILEGE_ENABLED;
	else
		tp.Privileges[0].Attributes = 0;

	// Enable the privilege or disable all privileges.
	if (!AdjustTokenPrivileges(
		hToken,
		FALSE,
		&tp,
		sizeof(TOKEN_PRIVILEGES),
		(PTOKEN_PRIVILEGES)NULL,
		(PDWORD)NULL)) {
		printf("AdjustTokenPrivileges error: %u\n", GetLastError());
		return FALSE;
	}

	if (GetLastError() == ERROR_NOT_ALL_ASSIGNED) {
		printf("The token does not have the specified privilege. \n");
		return FALSE;
	}

	return TRUE;
}

#endif

typedef std::tuple<PVOID, PVOID, SIZE_T, DWORD, DWORD, DWORD> PAGETUPLE;

class Scanner
{
public:
	HANDLE h_token = NULL;

	Scanner();
	~Scanner();
	bool run(HANDLE handle, std::vector<std::tuple<std::string, std::string>> *strings, std::vector<std::tuple<std::string, std::string>> *results, int extra = 0);
	std::vector<PAGETUPLE> find_pages(HANDLE handle, int extra = 0);
private:
	bool search(std::vector<std::tuple<std::string, std::string>> *strings, std::vector<std::tuple<std::string, std::string>> *results, HANDLE proc, ULONG minLen, ULONG maxLen, int extra);
};
