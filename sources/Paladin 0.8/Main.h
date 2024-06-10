#pragma once
#define WIN32_LEAN_AND_MEAN
#include <iostream>
#include "windows.h"
#include "util/json.hpp"
#include "checks/strings.h"
#include <codecvt>
#include <VersionHelpers.h>
#include <fstream>
#include <sddl.h>
#include <lmcons.h>
#include "util/xor.h"
#include "VMProtectSDK.h"

#ifndef DEBUG
#include "driver/interface.h"
#endif

#ifndef PLDMAIN
#define PLDMAIN

namespace Paladin {
	static std::string Version = "1.0";
	static std::vector<std::pair<int, std::string>> gErrors;
	static bool Delete = false;
}

#define PROCESSPROTECTION 2
#define DIRECTINPUT_VERSION 0x0800

#include <regex>
const std::regex allowed_window_chars(xors("[^A-Za-z0-9_.|() ]"));

static void CopyToClipboard(HWND hwnd, std::string s) {
	OpenClipboard(hwnd);
	EmptyClipboard();

	HGLOBAL hg = GlobalAlloc(GMEM_MOVEABLE, s.size() + 1);
	if (!hg) {
		CloseClipboard();
		return;
	}

	memcpy(GlobalLock(hg), s.c_str(), s.size() + 1);
	GlobalUnlock(hg);
	SetClipboardData(CF_TEXT, hg);
	CloseClipboard();
	GlobalFree(hg);
}

typedef LONG NTSTATUS, *PNTSTATUS;
#define STATUS_SUCCESS (0x00000000)

typedef NTSTATUS(WINAPI* RtlGetVersionPtr)(PRTL_OSVERSIONINFOW);

static RTL_OSVERSIONINFOW GetRealOSVersion() {
	VMProtectBeginMutation("getrealosversion");
	HMODULE hMod = ::GetModuleHandleW(L"ntdll.dll");
	if (hMod) {
		RtlGetVersionPtr fxPtr = (RtlGetVersionPtr)::GetProcAddress(hMod, xors("RtlGetVersion"));
		if (fxPtr != nullptr) {
			RTL_OSVERSIONINFOW rovi = { 0 };
			rovi.dwOSVersionInfoSize = sizeof(rovi);
			if (STATUS_SUCCESS == fxPtr(&rovi)) {
				return rovi;
			}
		}
	}
	RTL_OSVERSIONINFOW rovi = { 0 };
	return rovi;
	VMProtectEnd();
}

static std::string WindowsUsername()
{
	char username[UNLEN + 1];
	DWORD username_len = UNLEN + 1;
	GetUserName(username, &username_len);
	return username;
}
static std::string WindowsNetbios()
{
	char name[MAX_COMPUTERNAME_LENGTH + 1];
	DWORD name_len = MAX_COMPUTERNAME_LENGTH + 1;
	GetComputerName(name, &name_len);
	return name;
}
static RTL_OSVERSIONINFOW WindowsVersion()
{
	RTL_OSVERSIONINFOW i = GetRealOSVersion();
	return i;
}
static std::string WindowsVersionString();

static std::vector<std::string> AccountList()
{
	std::vector<std::string> accs;

	std::string path = getenv(xors("APPDATA"));
	path.append(xors("\\.minecraft\\launcher_profiles.json"));

	std::ifstream stream;
	stream.open(path.c_str());
	if (!stream.is_open())
	{
		Paladin::gErrors.push_back({ 2, xors("Couldn't open Minecraft profiles") });
		return accs;
	}
	else
	{
		json j;
		stream >> j;

		for (auto& element : j[xors("authenticationDatabase")])
		{
			for (auto& element2 : element[xors("profiles")])
			{
				if (std::find(accs.begin(), accs.end(), element2[xors("displayName")]) == accs.end())
					accs.push_back(element2[xors("displayName")]);
			}
		}
	}

	return accs;
}

static std::string randstr(int len, std::string str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")
{
	std::string newstr;
	int pos;
	while (newstr.size() != len) {
		pos = ((rand() % (str.size() - 1)));
		newstr += str.substr(pos, 1);
	}
	return newstr;
}

static std::string xorr(std::string toEncrypt, const char *key, size_t key_len)
{
	std::string output = toEncrypt;
	for (int i = 0; i < toEncrypt.size(); i++)
		output[i] = toEncrypt[i] ^ key[i % key_len];
	return output;
}
static std::string hex2str(std::string hex)
{
	int len = (int)hex.length();
	std::string s;
	for (int i = 0; i < len; i += 2)
	{
		std::string byte = hex.substr(i, 2);
		char chr = (char)(int)strtol(byte.c_str(), NULL, 16);
		s.push_back(chr);
	}
	return s;
}
static std::string str2hex(const std::string& input)
{
	static const char* const lut = "0123456789ABCDEF";
	size_t len = input.length();

	std::string output;
	output.reserve(2 * len);
	for (size_t i = 0; i < len; ++i)
	{
		const unsigned char c = input[i];
		output.push_back(lut[c >> 4]);
		output.push_back(lut[c & 15]);
	}
	return output;
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

static bool is_int(std::string k)
{
	for (int i = 0; i < k.length(); i++)
	{
		if (!isdigit(k[i]))
			return false;
	}
	return true;
}

static int recyclebin()
{
	const auto filetime_to_time_t = [](FILETIME const& ft) -> time_t
	{
		ULARGE_INTEGER ull;
		ull.LowPart = ft.dwLowDateTime;
		ull.HighPart = ft.dwHighDateTime;
		return ull.QuadPart / 10000000ULL - 11644473600ULL;
	};

	HANDLE token;
	OpenProcessToken(GetCurrentProcess(), TOKEN_QUERY, &token);

	PTOKEN_USER token_user = static_cast<PTOKEN_USER>(malloc(sizeof _TOKEN_USER));
	unsigned long size = 0;
	if (!GetTokenInformation(token, TokenUser, nullptr, size, &size))
		if (GetLastError() == ERROR_INSUFFICIENT_BUFFER)
			token_user = static_cast<PTOKEN_USER>(malloc(size));

	GetTokenInformation(token, TokenUser, token_user, size, &size);

	LPSTR lpz_str = nullptr;

	ConvertSidToStringSidA(token_user->User.Sid, &lpz_str);

	std::string path(getenv(xors("SystemDrive")));
	path.append(xors("\\$Recycle.Bin\\"));
	path.append(lpz_str);

	WIN32_FIND_DATAA data;
	void* h = FindFirstFileA(path.c_str(), &data);
	FindClose(h);
	free(token_user);

	auto time = filetime_to_time_t(data.ftLastWriteTime);
	return (int)std::difftime(std::time(nullptr), time);
}

//Returns the last Win32 error, in string format. Returns an empty string if there is no error.
static std::string GetLastErrorAsString()
{
	//Get the error message, if any.
	DWORD errorMessageID = ::GetLastError();
	if (errorMessageID == 0)
		return std::string(); //No error message has been recorded

	LPSTR messageBuffer = nullptr;
	size_t size = FormatMessageA(FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
		NULL, errorMessageID, MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT), (LPSTR)&messageBuffer, 0, NULL);

	std::string message(messageBuffer, size);

	//Free the buffer.
	LocalFree(messageBuffer);

	return message;
}

static const std::vector<std::string> explode(const std::string& s, const char& c)
{
	std::string buff{ "" };
	std::vector<std::string> v;

	for (auto n : s)
	{
		if (n != c) buff += n; else
			if (n == c && buff != "") { v.push_back(buff); buff = ""; }
	}
	if (buff != "") v.push_back(buff);

	return v;
}

#endif
