#define WIN32_LEAN_AND_MEAN
#include "../windows.h"
#include <iostream>
#include <string>
#include <winioctl.h>
#include <cstdio>

#ifndef _KEINTERFACE
#define _KEINTERFACE

#define IO_VERIFY_REQUEST CTL_CODE(FILE_DEVICE_UNKNOWN, 0x60242224, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)
#define IO_HANDLE_REQUEST CTL_CODE(FILE_DEVICE_UNKNOWN, 0x96465982, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)
#define IO_UNPROTECT_PROCESS CTL_CODE(FILE_DEVICE_UNKNOWN, 0x56668455, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)
#define IO_PROTECT_PROCESS CTL_CODE(FILE_DEVICE_UNKNOWN, 0x87598446, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)
#define IO_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME CTL_CODE(FILE_DEVICE_UNKNOWN, 0x63748629, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)
#define IO_GET_THE_SAVED_PROCESS CTL_CODE(FILE_DEVICE_UNKNOWN, 0x35978969, METHOD_BUFFERED, FILE_SPECIAL_ACCESS)

typedef struct _KERNEL_VERIFY_REQUEST
{
	PVOID Signature;
	ULONG SignatureSize;
} KERNEL_VERIFY_REQUEST, *PKERNEL_VERIFY_REQUEST;
typedef struct _KERNEL_HANDLE_REQUEST
{
	ULONG ProcessId;
	ACCESS_MASK AccessMask;
	HANDLE Handle;
} KERNEL_HANDLE_REQUEST, *PKERNEL_HANDLE_REQUEST;
typedef struct _KERNEL_UNPROTECT_PROCESS
{
	ULONG ProcessId;
	int Success;
	int OldLevel;
} KERNEL_UNPROTECT_PROCESS, *PKERNEL_UNPROTECT_PROCESS;
typedef struct _KERNEL_PROTECT_PROCESS
{
	ULONG ProcessId;
	int Level;
	int Success;
} KERNEL_PROTECT_PROCESS, *PKERNEL_PROTECT_PROCESS;
typedef struct _KERNEL_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME
{
	int ProcessId;
	int LSASSId;
} KERNEL_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME, *PKERNEL_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME;
typedef struct _KERNEL_GET_THE_SAVED_PROCESS
{
	int ProcessId;
} KERNEL_GET_THE_SAVED_PROCESS, *PKERNEL_GET_THE_SAVED_PROCESS;

// Interface for our driver
class KernelInterface {
public:
	bool Alive = false;
	SC_HANDLE ServiceManager;
	HANDLE hDriver; // Handle to driver
	FILE* file;
	char lol[MAX_PATH];

	KernelInterface() { Alive = false; ServiceManager = OpenSCManager(NULL, NULL, SC_MANAGER_ALL_ACCESS); }
	~KernelInterface() { CloseServiceHandle(ServiceManager); }

	bool Verify(PUCHAR signature, ULONG signatureSize);

	int Start(LPCSTR RegistryPath);
	int Stop();

	int Unprotect(ULONG ProcessId, int &old);
	int Protect(ULONG ProcessId, int Level);

	void SetObProtected(int PID, int LSPID);
	int GetObProtected();

	int DeleteService(const char *name);
};

#endif
