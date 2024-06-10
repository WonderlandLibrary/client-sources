// Aim Assist.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <Windows.h>
#include <iostream>
#include <iomanip>
#include <exception>
#include <cstdint>
#include <vector>
#include <sstream>
#include <fstream>
#include <algorithm>
#include <set>
#include <chrono>
#include <regex>
#include <unordered_map>
#include <tlhelp32.h>
#include <psapi.h>
#include <unordered_map>
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <thread>
#include <time.h>
#include <chrono>
#include <windows.h>
#include <string>
#include <functional>
#include <map>
#include <future>

int extraOffset = 0;
double range = 7;
double fov = 90;

double overallSpeed = 1;

double sensSpeed = 2 * (overallSpeed + 0.2);

double speed = 6 * (overallSpeed + 0.2) * (sensSpeed * 2);
bool yaxis = true;

int startMouseSpeed = 0;
double currentMouseSpeed = 0;

static double radtodeg(double x) {
	return x * 180.0 / 3.14159265359;
}

static double degtorad(double x) {
	return x * 3.14159265359 / 180.0;
}

static double distance(double x, double y) {
	return sqrt(pow(x, 2) + pow(y, 2));
}

static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
	return distance(y1 - y2, distance(x1 - x2, z1 - z2));
}

static double direction(double x1, double y1, double x2, double y2) {
	return radtodeg(atan2(y2 - y1, x2 - x1));
}

static double* direction(double x1, double y1, double z1, double x2, double y2, double z2) {
	double dx = x2 - x1;
	double dy = y2 - y1;
	double dz = z2 - z1;
	const auto hypothenuse = sqrt(dx * dx + dz * dz);
	auto yaw = radtodeg(atan2(dz, dx)) - 90.f;
	auto pitch = radtodeg(-atan2(dy, hypothenuse));
	return new double[2] { yaw, pitch };
}

HANDLE proc;

std::string mc_version;
int version_offset;

bool is_handle_valid(HANDLE handle) {
	return handle && handle != INVALID_HANDLE_VALUE;
}

class AutoHandle {
	HANDLE handle;
public:
	AutoHandle(HANDLE handle) : handle(handle) {}
	~AutoHandle() {
		if (is_handle_valid(this->handle))
			CloseHandle(this->handle);
	}
};

template <typename T>
void zero_struct(T& mem) {
	memset(&mem, 0, sizeof(mem));
}

struct memory_region {
	std::uint64_t start,
		size;
	MEMORY_BASIC_INFORMATION info;
};

std::uint64_t _GetProcessId(WCHAR* szProcName)
{
	PROCESSENTRY32 pe32;
	HANDLE hHandle;	hHandle = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);
	pe32.dwSize = sizeof(PROCESSENTRY32);	if (!Process32First(hHandle, &pe32))
		return 0;	while (Process32Next(hHandle, &pe32))
	{
		if (wcscmp(szProcName, pe32.szExeFile) == 0)
		{
			CloseHandle(hHandle);
			return pe32.th32ProcessID;
		}
	}
	CloseHandle(hHandle);	return 0;
}

void exit_check(size_t check, HANDLE phandle = (HANDLE)99) {

	if (!check) {

		if (phandle != (HANDLE)99) {

			CloseHandle(phandle);

		}

		exit(EXIT_SUCCESS);

	}

}

std::string minecraft_version(HANDLE phandle) {

	MEMORY_BASIC_INFORMATION info;
	std::string index = "--assetIndex";

	for (size_t address = 0; VirtualQueryEx(phandle, (LPVOID)address, &info, sizeof(info)); address += info.RegionSize) {

		if (address >= 0x1ffffff) break;
		if (info.State != MEM_COMMIT &&
			info.Protect != PAGE_EXECUTE_READWRITE ||
			info.Protect != PAGE_READWRITE) continue;

		std::string memory; memory.resize(info.RegionSize);
		if (!ReadProcessMemory(phandle, (LPVOID)address, &memory[0], info.RegionSize, 0)) continue;

		size_t pos = memory.find(index);
		if (pos != std::string::npos) {

			std::string version;
			pos += index.length();

			size_t max_size = info.RegionSize - pos;
			for (size_t x = 0; x < max_size; x++) {

				if (memory[pos + x] == '-') break;

				if (memory[pos + x] >= 33 && memory[pos + x] <= 126)
					version.push_back(memory[pos + x]);

			}

			if (version[0] != '1' || version[1] != '.') continue;

			exit_check(version.length(), phandle);
			return version;

		}

	}

	exit_check(0, phandle);
	return "";

}


byte f189 =
0x20;

byte f1892 =
0xF8;

byte lunar =
0x50;

byte f1710[4] = {
	0x72, 0x9F, 0x06, 0x20 };

byte j[7] = {
0x66, 0xA6, 0xAB, 0x43, 00, 00, 00

//0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00, 0x01, 0xF4, 0x00, 0x00, 0x00, 0x00
};

byte j2[7] = {
	0xA8, 0x44, 0x06, 0x83, 0x01, 0x00, 0x00
};

byte g[8] = {
	//0xCA, 0x9E, 0x06, 0x20
	//0x30, 0xAD, 0x0A, 0x20
	//0x74, 0xAC, 0x0A, 0x20
	//0x15, 0x8B, 0x03, 0x20
	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x24, 0x40
};

byte gg[8] = {
	//0xCA, 0x9E, 0x06, 0x20
	//0x30, 0xAD, 0x0A, 0x20
	//0x74, 0xAC, 0x0A, 0x20
	//0x15, 0x8B, 0x03, 0x20
	0x00, 0x00, 0x00, 0x00, 0x00, 0x50, 0x25, 0x40
};

byte h[8] = {
	0x14, 0x00, 0x00, 0x00, 0xEC, 0xFF, 0xFF, 0xFF
};

static std::map<std::uint64_t, byte> themCoordAddressesOther;
static std::map<std::uint64_t, byte> meRotAddressesOther;

static std::map<std::uint64_t, byte> themCoordAddresses;
static std::map<std::uint64_t, byte> meRotAddresses;

static std::vector<double> myCoords;
std::uint64_t myCoordsAddress;

void scanForMyLocation() {
	std::vector<memory_region> regions;

	for (std::uint64_t address = 0x20000000; address < 0x40000000;) {
		MEMORY_BASIC_INFORMATION mbi;
		zero_struct(mbi);
		auto bytes = VirtualQueryEx(proc, (LPCVOID)address, &mbi, sizeof(mbi));
		if (!bytes) {
			address += 4096;
			continue;
		}
		if (mbi.State == MEM_COMMIT && (mbi.Protect & PAGE_GUARD) != PAGE_GUARD)
			regions.push_back(memory_region{ (std::uint64_t)mbi.BaseAddress, mbi.RegionSize, mbi });

		address += mbi.RegionSize;
	}

	for (auto& region : regions) {
		std::vector<char> buffer(region.size);
		SIZE_T read;
		if (!ReadProcessMemory(proc, (LPCVOID)region.start, &buffer[0], buffer.size(), &read)) {
			auto error = GetLastError();
			if (error != ERROR_PARTIAL_COPY) {
				std::cout << "ERROR!";
				return;
			}
		}

		if (read < region.size) {
#if 1
			/*std::cerr << "Warning: region starting at 0x";
			print_address(std::cerr, region.start);
			std::cerr << " has size " << region.size << ", but only " << read
				<< " bytes could be read by ReadProcessMemory().\n";*/
#endif
			memset(&buffer[read], 0, buffer.size() - read);

		}


		for (size_t i = 0; i < (buffer.size() - sizeof(byte[4])); i = i + 4)
		{
			if (memcmp((void*)&j, &buffer[i], sizeof(j)) == 0)
			{
				extraOffset = 0;

				char* floatbuffer = (char*)malloc(sizeof(float) + 1);
				ReadProcessMemory(proc, (void*)((region.start + i + 72)), floatbuffer, sizeof(float), NULL);
				float x = *((float*)floatbuffer);

				ReadProcessMemory(proc, (void*)((region.start + i + 76)), floatbuffer, sizeof(float), NULL);
				float y = *((float*)floatbuffer);

				ReadProcessMemory(proc, (void*)((region.start + i + 80)), floatbuffer, sizeof(float), NULL);
				float z = *((float*)floatbuffer);

				if ((x < 0.01 && x > -0.01 || y < 0.01 && y > -0.01 || z < 0.01 && z > -0.01) || isnan(x) || isnan(y) || isnan(z) || x > 100000 || y > 100000 || z > 100000 || x < -100000 || y < -100000 || z < -100000) {

				}
				else {
					myCoordsAddress = region.start + i;


					myCoords = { x, y, z };
					return;
				}
			}
		}
	}
}

void updateMyLocation() {
	char* floatbuffer = (char*)malloc(sizeof(float) + 1);
	ReadProcessMemory(proc, (void*)((myCoordsAddress + 72)), floatbuffer, sizeof(float), NULL);
	float x = *((float*)floatbuffer);

	ReadProcessMemory(proc, (void*)((myCoordsAddress + 76)), floatbuffer, sizeof(float), NULL);
	float y = *((float*)floatbuffer);

	ReadProcessMemory(proc, (void*)((myCoordsAddress + 80)), floatbuffer, sizeof(float), NULL);
	float z = *((float*)floatbuffer);

	if ((x < 0.01 && x > -0.01 || y < 0.01 && y > -0.01 || z < 0.01 && z > -0.01) || isnan(x) || isnan(y) || isnan(z) || x > 100000 || y > 100000 || z > 100000 || x < -100000 || y < -100000 || z < -100000) {

	}
	else {
		myCoords = { x, y, z };
		return;
	}
}

long mycmp(const unsigned char* cmp1, const unsigned char* cmp2, unsigned long length) {
	if (length >= 4) {
		long difference = *(unsigned long*)cmp1 - *(unsigned long*)cmp2;
		if (difference)
			return difference;
	}

	return memcmp(cmp1, cmp2, length);
}

void scanForLocations() {
	std::vector<memory_region> regions;
	int counter = 0;
	for (std::uint64_t address = 0x700000000; address < 0x9fffffffff;) {
		MEMORY_BASIC_INFORMATION mbi;
		zero_struct(mbi);
		auto bytes = VirtualQueryEx(proc, (LPCVOID)address, &mbi, sizeof(mbi));
		if (!bytes) {
			address += 4096;
			continue;
		}
		if (mbi.State == MEM_COMMIT && (mbi.Protect & PAGE_GUARD) != PAGE_GUARD)
			regions.push_back(memory_region{ (std::uint64_t)mbi.BaseAddress, mbi.RegionSize, mbi });

		address += mbi.RegionSize;
	}

	for (std::uint64_t address = 0x70000000; address < 0x9ffffffff;) {
		MEMORY_BASIC_INFORMATION mbi;
		zero_struct(mbi);
		auto bytes = VirtualQueryEx(proc, (LPCVOID)address, &mbi, sizeof(mbi));
		if (!bytes) {
			address += 4096;
			continue;
		}
		if (mbi.State == MEM_COMMIT && (mbi.Protect & PAGE_GUARD) != PAGE_GUARD)
			regions.push_back(memory_region{ (std::uint64_t)mbi.BaseAddress, mbi.RegionSize, mbi });

		address += mbi.RegionSize;
	}

	for (auto& region : regions) {

		char* buffer = (char*)malloc(region.size + 1);

		if (region.size > 2000000000) {
			region.size = 2000000000;
		}

		if (region.size < 100000000) {
			region.size = 4096;
		}

		//std::cout << std::endl << region.size << std::endl;

		//std::vector<char> buffer(region.size);
		SIZE_T read;
		if (!ReadProcessMemory(proc, (LPCVOID)region.start, &buffer[0], region.size, &read)) {
			auto error = GetLastError();
			if (error != ERROR_PARTIAL_COPY) {
				return;
			}
		}

		if (read < region.size) {
#if 1
			/*std::cerr << "Warning: region starting at 0x";
			print_address(std::cerr, region.start);
			std::cerr << " has size " << region.size << ", but only " << read
				<< " bytes could be read by ReadProcessMemory().\n";*/
#endif
			memset(&buffer[read], 0, region.size - read);

		}


		for (size_t i = 0; i < (region.size - sizeof(byte[4])); i = i + 8)
		{
			if (mycmp((const unsigned char*)&g, (const unsigned char*)&buffer[i], sizeof(g)) == 0/* || memcmp((void*)&gg, &buffer[i], sizeof(gg)) == 0*/)
			{

				//std::stringstream stream;
				//stream << std::hex << (std::uint64_t)(void*)((region.start + i));
				//std::string result(stream.str());

				//std::cout << result << std::endl;
				if (mycmp((const unsigned char*)&f189, (const unsigned char*)&buffer[i - 5], sizeof(f189)) == 0 || mycmp((const unsigned char*)&f1892, (const unsigned char*)&buffer[i - 5], sizeof(f1892)) == 0/* || memcmp((void*)&lunar, &buffer[i - 3], sizeof(lunar)) == 0*/)
				{

					counter++;


					char* buffer = (char*)malloc(sizeof(int) + 1);

					ReadProcessMemory(proc, (void*)((region.start + i + version_offset)), buffer, sizeof(byte), NULL);
					int c = *((int*)buffer);


					ReadProcessMemory(proc, (void*)((region.start + i + 180)), buffer, sizeof(byte), NULL);
					byte d = *((byte*)buffer);





					themCoordAddressesOther.insert({ (std::uint64_t)((void*)((region.start + i))), c });
				}
			}

			if (mycmp((const unsigned char*)&h, (const unsigned char*)&buffer[i], sizeof(h)) == 0)
			{
				char* buffer = (char*)malloc(sizeof(byte) + 1);


				ReadProcessMemory(proc, (void*)((region.start + i - 4)), buffer, sizeof(byte), NULL);
				byte c = *((byte*)buffer);


				meRotAddressesOther.insert({ (std::uint64_t)((void*)((region.start + i))), c });
			}
		}
		free((char*)buffer);
	}
	//std::cout << counter;
}

void checkValueValidity() {
	std::map<std::uint64_t, byte> themCoordAddressesNew;
	for (auto& it : themCoordAddressesOther)
	{
		char* buffer = (char*)malloc(sizeof(byte) + 1);
		ReadProcessMemory(proc, (void*)((it.first + version_offset)), buffer, sizeof(byte), NULL);
		byte c = *((byte*)buffer);

		char* buffer2 = (char*)malloc(sizeof(double) + 1);
		ReadProcessMemory(proc, (void*)((it.first)), buffer2, sizeof(double), NULL);
		double d = *((double*)buffer2);

		//std::cout << d;

		if (c == it.second || d != 10) {

		}
		else {
			//std::cout << "Before: " << static_cast<int>(it.second) << ", After: " << static_cast<int>(c) << std::endl;



			it.second = c;
			themCoordAddressesNew.insert({ it.first, c });
		}
	}
	themCoordAddresses = themCoordAddressesNew;

	std::map<std::uint64_t, byte> meRotAddressesNew;
	for (auto& it : meRotAddressesOther)
	{
		char* buffer = (char*)malloc(sizeof(byte) + 1);
		ReadProcessMemory(proc, (void*)((it.first - 4)), buffer, sizeof(byte), NULL);
		byte c = *((byte*)buffer);

		if (c == it.second) {

		}
		else {
			it.second = c;
			meRotAddressesNew.insert({ it.first, c });
		}
	}

	meRotAddresses = meRotAddressesNew;
}

bool threadOpen = false;


void getLocationsAndValidity() {
	threadOpen = true;
	scanForLocations();
	checkValueValidity();
	threadOpen = false;
}

std::vector<std::vector<double>> allPlayersCoords;
std::vector<float> myRotation;


void updateFoundValues() {
	allPlayersCoords.clear();

	std::map<std::uint64_t, byte> themCoordAddressesNew = themCoordAddresses;

	for (auto it : themCoordAddressesNew)
	{
		char* doublebuffer = (char*)malloc(sizeof(double) + 1);
		ReadProcessMemory(proc, (void*)((it.first + 8)), doublebuffer, sizeof(double), NULL);
		double x = *((double*)doublebuffer);

		ReadProcessMemory(proc, (void*)((it.first + 16)), doublebuffer, sizeof(double), NULL);
		double y = *((double*)doublebuffer);

		ReadProcessMemory(proc, (void*)((it.first + 24)), doublebuffer, sizeof(double), NULL);
		double z = *((double*)doublebuffer);

		std::stringstream stream;
		stream << std::hex << (it.first);
		std::string result(stream.str());

		if ((x < 0.01 && x > -0.01 || y < 0.01 && y > -0.01 || z < 0.01 && z > -0.01) || isnan(x) || isnan(y) || isnan(z) || x > 100000 || y > 100000 || z > 100000 || x < -100000 || y < -100000 || z < -100000) {
		}
		else {
			//std::cout << y << std::endl;

			allPlayersCoords.push_back({ x, y, z });
			//std::cout << "Debug: " << result << std::endl;
		}
	}


	std::map<std::uint64_t, byte> meRotAddressesNew = meRotAddresses;

	try {
		for (auto it : meRotAddressesNew)
		{

			char* floatbuffer = (char*)malloc(sizeof(float) + 1);

			ReadProcessMemory(proc, (void*)((it.first - 52)), floatbuffer, sizeof(float), NULL);

			float pitch = *((float*)floatbuffer);

			ReadProcessMemory(proc, (void*)((it.first - 56)), floatbuffer, sizeof(float), NULL);

			float yaw = *((float*)floatbuffer);


			if (pitch == pitch && yaw == yaw) {

				if ((pitch < 0.01 || pitch > -0.01) && pitch <= 90 && pitch >= -90 && yaw <= 48000 && yaw >= -48000) {
					while (yaw >= 180) {
						yaw = yaw - 360;
					}
					while (yaw <= -180) {
						yaw = yaw + 360;
					}
					if ((yaw > 0.01 || yaw < -0.01) && yaw <= 180 && yaw >= -180) {

						myRotation = { yaw, pitch };

						//std::cout << yaw << "/" << pitch << std::endl;
					}
				}
			}
		}
	}
	catch (EXCEPINFO) {
	}
}


float distance(float local_pos_x, float local_pos_y, float local_pos_z, float entity_pos_x, float entity_pos_y, float entity_pos_z) {
	float outx, outy, outz;
	outx = local_pos_x - entity_pos_x;
	outy = local_pos_y - entity_pos_y;
	outz = local_pos_z - entity_pos_z;
	return sqrt(outx * outx + outy * outy + outz * outz);
}

std::vector<double> getClosestPlayerCoords(std::vector<std::vector<double>> otherPlayers, std::vector<double> player) {
	bool underRange = false;

	if (!(otherPlayers.empty() || player.empty())) {
		std::vector<double> distances;
		for (std::vector<double> loc : otherPlayers) {
			double distancea = distance(loc[0], loc[1], loc[2], player[0], player[1], player[2]);

			distances.push_back(distancea);
			if (distancea < range + 1) {
				underRange = true;
			}
		}
		if (underRange == true)
			return otherPlayers[distance(distances.begin(), std::find(distances.begin(), distances.end(), *std::min_element(distances.begin(), distances.end())))];
		else
			return {};
	}
	return {};
}


double respo1;
double respo2;
void getRotationsNeeded(double x1, double y1, double z1, double x2, double y2, double z2, double playerYaw, double playerPitch) {
	double diffX = x2 - x1;
	double diffY = y2 - y1;
	double diffZ = z2 - z1;

	double dist = sqrtf((diffX * diffX) + (diffZ * diffZ));

	double yaw = (double)(atan2f(diffZ, diffX) * 180 / 3.141592653589793) - 90;

	double pitch = (double)(-atan2f(diffY, dist) * 180 / 3.141592653589793);

	respo1 = playerYaw + (yaw - playerYaw);

	respo2 = playerPitch + (pitch - playerPitch);
}

float wrapAngleTo180_float(float p_76142_0_)
{
	p_76142_0_ = fmod(p_76142_0_, 360);

	if (p_76142_0_ >= 180)
	{
		p_76142_0_ -= 360;
	}

	if (p_76142_0_ < -180)
	{
		p_76142_0_ += 360;
	}

	return p_76142_0_;
}

void glideInDirectionX(double amount, int time) {
	for (int i = time; i > 0; i--) {
		mouse_event(MOUSEEVENTF_MOVE, (amount * speed) / time, 0, NULL, NULL);
		Sleep(1);
	}
}

void glideInDirectionY(double amount, int time) {
	for (int i = time; i > 0; i--) {
		mouse_event(MOUSEEVENTF_MOVE, 0, (amount * speed) / time, NULL, NULL);
		Sleep(1);
	}
}

double fRand(double fMin, double fMax)
{
	double f = (double)rand() / RAND_MAX;
	return fMin + f * (fMax - fMin);
}

int iY = 150;
bool droppingY = false;

double getPlaceOnBodyY() {
	if (droppingY) {
		if (iY <= 30) {
			droppingY = false;
		}
		iY -= 3;

	}
	else {
		if (iY >= 150) {
			droppingY = true;
		}
		iY += 3;
	}

	return (double)iY / (double)200;
}

int iX = 150;
bool droppingX = false;

double getPlaceOnBodyX() {
	if (droppingX) {
		if (iX <= 30) {
			droppingX = false;
		}
		iX -= 3;

	}
	else {
		if (iX >= 150) {
			droppingX = true;
		}
		iX += 3;
	}

	return ((double)iX / (double)500);
}


int doAimAssist() {
	getchar();

	DWORD pid = _GetProcessId((WCHAR*)L"javaw.exe");

	proc = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_OPERATION | PROCESS_VM_READ | PROCESS_VM_WRITE, FALSE, pid);

	scanForMyLocation();



	std::cout << "Obtaining Minecraft version..." << std::endl;
	mc_version = minecraft_version(proc);
	std::cout << "Found " << mc_version << "!" << std::endl << std::endl;

	// lunar ? version_offset = 136;

	if (mc_version == "1.8.9") {
		version_offset = 180;
	}
	else {
		version_offset = 172;
	}

	std::cout << "Enter range in blocks:\n";
	std::cin >> range;
	std::cout << "\nEnter fov in degrees:\n";
	std::cin >> fov;
	std::cout << "\nEnter strength from 0 to 1 (eg. 0.3):\n";
	std::cin >> overallSpeed;


	sensSpeed = 2 * (overallSpeed + 0.2);

	speed = 6 * (overallSpeed + 0.2) * (sensSpeed * 2);
	std::cout << std::fixed;
	std::cout.precision(2);

	int counter = 400;

	while (true) {
		HWND clicker_hwnd;
		POINT pt;
		GetCursorPos(&pt); clicker_hwnd = WindowFromPoint(pt);

		if (pt.y > 20 && FindWindow(L"LWJGL", nullptr) == GetForegroundWindow()) {

			if (counter == 400) {
				counter = 0;
				if (!threadOpen) {
					//std::cout << ".";

					std::thread thread = std::thread(getLocationsAndValidity);
					thread.detach();
				}
				else {
					//std::cout << threadOpen;
				}
			}
			if (counter % 10 == 0) {
				std::thread thread = std::thread(checkValueValidity);
				thread.detach();
			}


			counter++;
			Sleep(5);

			updateFoundValues();
			updateMyLocation();
			if (!myCoords.empty())
				std::cout << "Your coords: " << myCoords[0] << ", " << myCoords[1] << ", " << myCoords[2] << std::endl;
			if (!myRotation.empty())
				std::cout << "Your rot: " << myRotation[0] << "/" << myRotation[1] << std::endl;

			/*for (std::vector<double> location : allPlayersCoords) {
				std::cout << "Their coords: " << location[0] << ", " << location[1] << ", " << location[2] << std::endl;
			}*/




			std::vector<double> closestPlayer = getClosestPlayerCoords(allPlayersCoords, myCoords);
			if (!closestPlayer.empty()) {
				std::cout << "Closest Coords: " << closestPlayer[0] << ", " << closestPlayer[1] << ", " << closestPlayer[2] << std::endl;
			}

			std::cout << std::endl;
			try {
				if (!closestPlayer.empty() && !myRotation.empty()) {
					double yawTo = wrapAngleTo180_float(direction(myCoords[0], myCoords[1], myCoords[2], closestPlayer[0] + getPlaceOnBodyX(), closestPlayer[1], closestPlayer[2] + getPlaceOnBodyX())[0]);
					double pitchTo = wrapAngleTo180_float(direction(myCoords[0], myCoords[1], myCoords[2], closestPlayer[0], closestPlayer[1] + getPlaceOnBodyY() + 0.7, closestPlayer[2])[1]);
					//std::cout << wrapAngleTo180_float(yawTo - myRotation[0]) << std::endl;
					//std::cout << wrapAngleTo180_float(pitchTo - myRotation[1]) << std::endl;

					double toMoveX = wrapAngleTo180_float(yawTo - myRotation[0]);
					double toMoveY = wrapAngleTo180_float(pitchTo - myRotation[1]);

					if ((toMoveX >= -(fov / 2) && toMoveX <= (fov / 2)) /*&& (toMoveY >= -(fov / 2) && toMoveY <= (fov / 2))*/) {
						if (ScreenToClient(clicker_hwnd, &pt)) {
							std::thread threadx = std::thread(glideInDirectionX, wrapAngleTo180_float(yawTo - myRotation[0]), 5);
							threadx.detach();

							if (yaxis) {
								std::thread thready = std::thread(glideInDirectionY, wrapAngleTo180_float(pitchTo - myRotation[1]), 5);
								thready.detach();
							}


							if (currentMouseSpeed != startMouseSpeed / sensSpeed)
							{
								currentMouseSpeed = startMouseSpeed / sensSpeed;
								// Change the mouse speed to the new value.
								SystemParametersInfo(
									SPI_SETMOUSESPEED,
									0,
									(LPVOID)(int)currentMouseSpeed,
									SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
								);
							}
						}
						else {
							if (currentMouseSpeed != startMouseSpeed)
							{
								currentMouseSpeed = startMouseSpeed;
								// Change the mouse speed to the new value.
								SystemParametersInfo(
									SPI_SETMOUSESPEED,
									0,
									(LPVOID)(int)currentMouseSpeed,
									SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
								);
							}
						}
					}
					else {
						if (currentMouseSpeed != startMouseSpeed)
						{
							currentMouseSpeed = startMouseSpeed;
							// Change the mouse speed to the new value.
							SystemParametersInfo(
								SPI_SETMOUSESPEED,
								0,
								(LPVOID)(int)currentMouseSpeed,
								SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
							);
						}
					}

				}
				else {
					if (currentMouseSpeed != startMouseSpeed)
					{
						currentMouseSpeed = startMouseSpeed;
						// Change the mouse speed to the new value.
						SystemParametersInfo(
							SPI_SETMOUSESPEED,
							0,
							(LPVOID)(int)currentMouseSpeed,
							SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
						);
					}
				}
			}
			catch (EXCEPINFO) {
				if (currentMouseSpeed != startMouseSpeed)
				{
					currentMouseSpeed = startMouseSpeed;
					// Change the mouse speed to the new value.
					SystemParametersInfo(
						SPI_SETMOUSESPEED,
						0,
						(LPVOID)(int)currentMouseSpeed,
						SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
					);
				}
			}
			//std::cout << std::endl;
			Sleep(10);
		}
		else {
			if (currentMouseSpeed != startMouseSpeed)
			{
				currentMouseSpeed = startMouseSpeed;
				// Change the mouse speed to the new value.
				SystemParametersInfo(
					SPI_SETMOUSESPEED,
					0,
					(LPVOID)(int)currentMouseSpeed,
					SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
				);
			}
		}
	}
}


int main()
{

	SetPriorityClass(GetCurrentProcess(), BELOW_NORMAL_PRIORITY_CLASS);

	std::cout << "**************************************************************************************************\n          \n                \n                              \n**************************************************************************************************\n\n PRESS ENTER TO START" << std::endl;

	// Get the current mouse speed.         
	SystemParametersInfo(SPI_GETMOUSESPEED,   // Get mouse information
		0,              // Not used
		&startMouseSpeed,    // Holds mouse information
		0);             // Not used           


	currentMouseSpeed = startMouseSpeed * 2;
	// Change the mouse speed to the new value.
	/*SystemParametersInfo(
		SPI_SETMOUSESPEED,
		0,
		(LPVOID)(int)currentMouseSpeed,
		SPIF_UPDATEINIFILE | SPIF_SENDCHANGE | SPIF_SENDWININICHANGE
	);*/
	std::thread thread = std::thread(doAimAssist);
	thread.detach();


	while (true) {

		Sleep(100);
	}
}