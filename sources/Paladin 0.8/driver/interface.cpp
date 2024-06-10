#include "interface.h"
#include "../Main.h"
#include "driverdata.h"
#include "driverdata7.h"

#include "../includes.h"

int KernelInterface::DeleteService(const char *name)
{
	SERVICE_STATUS ServiceStatus;

	SC_HANDLE ServiceHandle = OpenService(ServiceManager, name, SERVICE_STOP | DELETE);
	if (GetLastError() == ERROR_SERVICE_DOES_NOT_EXIST)
		return 1;
	if (!ServiceHandle) return -1;

	if (!ControlService(ServiceHandle, SERVICE_CONTROL_STOP, &ServiceStatus))
		if (GetLastError() != ERROR_SERVICE_NOT_ACTIVE)
			return -2;

	Sleep(100);

	if (!::DeleteService(ServiceHandle)) return -4;
	CloseServiceHandle(ServiceHandle);
	Sleep(50);
	return 1;
}

int KernelInterface::Start(LPCSTR RegistryPath)
{
	VMProtectBeginUltra("kernel.start");
	if (!ServiceManager)
		return -1;

	int deleted = DeleteService(xors("PaladinDriver"));
	if (deleted != 1 && deleted != -1)
		return -2;

	GetTempPathA(MAX_PATH, lol);
	std::string fuckyou = lol;
	fuckyou.append(xors("PaladinDriver.sys"));
	strncpy(lol, fuckyou.c_str(), MAX_PATH);

	if (std::filesystem::exists(lol))
	{
		if (std::filesystem::is_regular_file(lol))
			std::filesystem::remove(lol);
		else if (std::filesystem::is_directory(lol))
			std::filesystem::remove_all(lol);
		else
			return -10;
	}
	Sleep(10);
	if (std::filesystem::exists(lol))
		return -11;

	file = fopen(lol, "wb");

	auto ver = WindowsVersion();
	if (ver.dwMajorVersion >= 10 && ver.dwBuildNumber >= 14393)
	{
		fwrite(DriverData, 1, sizeof(DriverData), file);
	}
	else
	{
		fwrite(OldDriverData, 1, sizeof(OldDriverData), file);
	}
	fclose(file);

	// Create service
	SC_HANDLE ServiceHandle;
	ServiceHandle = CreateService(ServiceManager, xors("PaladinDriver"), xors("Paladin Kernel Driver Service"), SERVICE_START | DELETE | SERVICE_STOP, SERVICE_KERNEL_DRIVER, SERVICE_DEMAND_START, SERVICE_ERROR_IGNORE, lol, NULL, NULL, NULL, NULL, NULL);
	if (!ServiceHandle)
	{
		DeleteService(xors("PaladinDriver"));
		ServiceHandle = CreateService(ServiceManager, xors("PaladinDriver"), xors("Paladin Kernel Driver Service"), SERVICE_START | DELETE | SERVICE_STOP, SERVICE_KERNEL_DRIVER, SERVICE_DEMAND_START, SERVICE_ERROR_IGNORE, lol, NULL, NULL, NULL, NULL, NULL);
		if (!ServiceHandle)
			return -20;
	}

	if (!StartServiceA(ServiceHandle, NULL, NULL))
		return -44;

	CloseServiceHandle(ServiceHandle);

	Sleep(100);

	int tries = 0;

	while (!hDriver && tries <= 5)
	{
		hDriver = CreateFileA(RegistryPath, GENERIC_READ | GENERIC_WRITE, FILE_SHARE_READ | FILE_SHARE_WRITE, 0, OPEN_EXISTING, 0, 0);
		Sleep(50);
		if (!hDriver)
			Sleep(250);
		tries++;
	}

	if (!hDriver)
		return -5;

	Alive = true;

	return 0;
}
int KernelInterface::Stop()
{
	if (hDriver != NULL)
		CloseHandle(hDriver);

	Sleep(150);

	DeleteService(xors("PaladinDriver"));

	Sleep(150);

	if (strlen(lol) != 0)
		std::filesystem::remove(lol);

	Alive = false;
	return 0;
}

bool KernelInterface::Verify(PUCHAR signature, ULONG signatureSize)
{
	VMProtectBeginMutation("kernel.verify");
	if (!Alive)
	{
		MessageBox(NULL, xors("Driver not alive"), xors("Paladin"), NULL);
		return false;
	}

	if (hDriver == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, xors("INVALID_HANDLE_VALUE"), xors("INVALID_HANDLE_VALUE"), NULL);
		return false;
	}

	KERNEL_VERIFY_REQUEST VerifyRequest;
	VerifyRequest.Signature = signature;
	VerifyRequest.SignatureSize = signatureSize;

	LPDWORD k = new DWORD(0);
	LPOVERLAPPED kk = new OVERLAPPED();
	
	if (DeviceIoControl(hDriver, IO_VERIFY_REQUEST, &VerifyRequest, sizeof(VerifyRequest), &VerifyRequest, sizeof(VerifyRequest), k, kk))
	{
		return true;
	}
	else
	{
		DWORD lasterror = GetLastError();

		if (lasterror == 2148073478)
			MessageBoxA(NULL, "Failed to verify signature, please redownload.", "Paladin", NULL);
		else
		{
			std::string msg = "F1 " + std::to_string(lasterror);
			MessageBox(NULL, GetLastErrorAsString().c_str(), msg.c_str(), NULL);
		}

		return false;
	}
	VMProtectEnd();
}

int KernelInterface::Unprotect(ULONG ProcessId, int &old)
{
	VMProtectBeginMutation("kernel.unprotect");
	auto ver = WindowsVersion();
	if (ver.dwMajorVersion != 10 && (ver.dwMajorVersion != 6 && ver.dwMinorVersion != 3)) // Windows 8.1 and higher
	{
		return NULL;
	}
	if (!Alive)
	{
		MessageBox(NULL, xors("Driver not alive"), xors("Paladin"), NULL);
		return NULL;
	}
	if (hDriver == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, xors("INVALID_HANDLE_VALUE"), xors("INVALID_HANDLE_VALUE"), NULL);
		return NULL;
	}

	KERNEL_UNPROTECT_PROCESS UnprotectRequest;
	UnprotectRequest.ProcessId = ProcessId;

	LPDWORD k = new DWORD(0);
	LPOVERLAPPED kk = new OVERLAPPED();
	if (DeviceIoControl(hDriver, IO_UNPROTECT_PROCESS, &UnprotectRequest, sizeof(UnprotectRequest), &UnprotectRequest, sizeof(UnprotectRequest), k, kk)) {
		old = UnprotectRequest.OldLevel;
		return UnprotectRequest.Success;
	}
	else {
		std::string msg = "F2 " + std::to_string(GetLastError());
		MessageBox(NULL, GetLastErrorAsString().c_str(), msg.c_str(), NULL);
		return 0;
	}
	VMProtectEnd();
}
int KernelInterface::Protect(ULONG ProcessId, int Level)
{
	VMProtectBeginMutation("kernel.protect");
	auto ver = WindowsVersion();

	if (ver.dwMajorVersion != 10 && (ver.dwMajorVersion != 6 && ver.dwMinorVersion != 3)) // Windows 8.1 and higher
	{
		return NULL;
	}

	if (!Alive)
	{
		MessageBox(NULL, xors("Driver not alive"), xors("Paladin"), NULL);
		return NULL;
	}

	if (hDriver == INVALID_HANDLE_VALUE) 
	{
		MessageBox(NULL, xors("INVALID_HANDLE_VALUE"), xors("INVALID_HANDLE_VALUE"), NULL);
		return NULL;
	}

	KERNEL_PROTECT_PROCESS ProtectRequest;

	ProtectRequest.ProcessId = ProcessId;
	ProtectRequest.Level = Level;

	LPDWORD k = new DWORD(0);
	LPOVERLAPPED kk = new OVERLAPPED();

	if (DeviceIoControl(hDriver, IO_PROTECT_PROCESS, &ProtectRequest, sizeof(ProtectRequest), &ProtectRequest, sizeof(ProtectRequest), k, kk)) 
	{
		return ProtectRequest.Success;
	}
	else 
	{
		std::string msg = "F3 " + std::to_string(GetLastError());
		MessageBox(NULL, GetLastErrorAsString().c_str(), msg.c_str(), NULL);
		return 0;
	}
	VMProtectEnd();
}

void KernelInterface::SetObProtected(int PID, int LSPID)
{
	VMProtectBeginMutation("kernel.setob");
	if (!Alive)
	{
		MessageBox(NULL, xors("Driver not alive"), xors("Paladin"), NULL);
		return;
	}

	if (hDriver == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, xors("INVALID_HANDLE_VALUE"), xors("INVALID_HANDLE_VALUE"), NULL);
		return;
	}

	KERNEL_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME SetRequest;

	SetRequest.ProcessId = PID;
	SetRequest.LSASSId = LSPID;

	LPDWORD k = new DWORD(0);
	LPOVERLAPPED kk = new OVERLAPPED();

	if (DeviceIoControl(hDriver, IO_SAVE_ME_FROM_THE_NOTHING_IVE_BECOME, &SetRequest, sizeof(SetRequest), &SetRequest, sizeof(SetRequest), k, kk)) 
	{
		return;
	}
	else 
	{
		std::string msg = "F4 " + std::to_string(GetLastError());
		MessageBox(NULL, GetLastErrorAsString().c_str(), msg.c_str(), NULL);
	}
	VMProtectEnd();
}
int KernelInterface::GetObProtected()
{
	if (!Alive)
	{
		MessageBox(NULL, xors("Driver not alive"), xors("Paladin"), NULL);
		return NULL;
	}

	if (hDriver == INVALID_HANDLE_VALUE) {
		MessageBox(NULL, xors("INVALID_HANDLE_VALUE"), xors("INVALID_HANDLE_VALUE"), NULL);
		return NULL;
	}

	KERNEL_GET_THE_SAVED_PROCESS GetRequest;
	GetRequest.ProcessId = 0;

	LPDWORD k = new DWORD(0);
	LPOVERLAPPED kk = new OVERLAPPED();

	if (DeviceIoControl(hDriver, IO_GET_THE_SAVED_PROCESS, &GetRequest, sizeof(GetRequest), &GetRequest, sizeof(GetRequest), k, kk)) 
	{
		return GetRequest.ProcessId;
	}
	else 
	{
		std::string msg = "F5 " + std::to_string(GetLastError());
		MessageBox(NULL, GetLastErrorAsString().c_str(), msg.c_str(), NULL);
		return 0;
	}
}
