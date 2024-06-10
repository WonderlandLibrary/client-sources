#include "auth.hpp"

#ifndef _DEBUG
#include <ThemidaSDK.h>
#endif

#include <time.h>
#include <fstream>
#include <regex>
#include <sstream>

#include "../utilities/requests.hpp"
#include "../modules/settings.hpp"
#include "../security/securityhelper.hpp"

#include "../../vendors/encryption/aes.h"
#include  "../../vendors/encryption/caesar.h"

#pragma optimize("", off)

std::string Authentification::get_hwid()
{
#ifndef _DEBUG
	VM_EAGLE_BLACK_START
#endif
	DWORD VolumeSerialNumber = 0;
	GetVolumeInformation("c:\\", NULL, NULL, &VolumeSerialNumber, NULL, NULL, NULL, NULL);

	std::ostringstream hwid;
	hwid << std::hex << std::uppercase << VolumeSerialNumber;
	
	return hwid.str();
#ifndef _DEBUG
	VM_EAGLE_BLACK_END
#endif
}

bool Authentification::login(std::string token, std::string hwid, bool& outStatus, std::string& outBuf)
{
	if (!token.compare("") || outStatus == true)
	{
		exit(EXIT_SUCCESS);
	}

#ifndef _DEBUG
	VM_EAGLE_BLACK_START
#endif

	ULONGLONG timer = GetTickCount64();
	srand((unsigned)time(NULL));
	
	uint8_t* key = new uint8_t[32];
	for (unsigned i = 0; i < 32; i++)
		*(uint8_t*)(key + i) = rand() % 0xff + 1;

	uint8_t* iv = new uint8_t[16];
	for (unsigned i = 0; i < 16; i++)
		*(uint8_t*)(iv + i) = rand() % 0xff + 1;

	uint8_t* xorKey = new uint8_t[16];
	for (unsigned i = 0; i < 16; i++)
		*(uint8_t*)(xorKey + i) = rand() % 0xff + 1;

	AES_ctx context;

#ifndef _DEBUG
	VM_EAGLE_BLACK_END
#endif
	AES_init_ctx_iv(&context, key, iv);
#ifndef _DEBUG
	VM_EAGLE_BLACK_START
#endif

	char* szQuery = new char[520];
	sprintf(szQuery, "token=%s&hwid=%s&ver=%s&clientTime=%i&isVerified=%i", token.data(), hwid.data(), "1.0.0", time(NULL), 0);
	/**/
	uint8_t* query = (uint8_t*)szQuery;
	size_t querySize = strlen(szQuery);
	if (querySize % 16 != 0)
	{
		size_t oldSize = querySize;
		unsigned toFill = (16 - querySize % 16);
		querySize = oldSize + toFill;
		query = new uint8_t[querySize];
		memcpy(query, szQuery, oldSize);
		memset(query + oldSize, 0, toFill);
	}

#ifndef _DEBUG
	VM_EAGLE_BLACK_END
#endif

	/**/

	AES_CBC_encrypt_buffer(&context, query, (uint32_t)querySize);
#ifndef _DEBUG
	VM_EAGLE_BLACK_START
#endif
	for (unsigned i = 0; i < querySize; i++)
		query[i] ^= xorKey[i % 16];

	size_t requestBodySize = 32 + 16 + 16 + querySize;
	uint8_t* requestBody = new uint8_t[requestBodySize];
	memcpy(requestBody, key, 32);
	memcpy(requestBody + 32, iv, 16);
	memcpy(requestBody + 32 + 16, xorKey, 16);
	memcpy(requestBody + 32 + 16 + 16, query, querySize);

	if (GetTickCount64() - timer > 10000) {
		outStatus = false;
		return true;
	}

	std::string readBuffer = RequestHelper::get().send_request("http://193.26.14.16/ghost_auth/", (char*)requestBody, requestBodySize);
	delete[] szQuery;
	delete[] requestBody;
	timer = GetTickCount64();

	/**/
	for (unsigned i = 0; i < readBuffer.size(); i++)
		readBuffer[i] ^= xorKey[i % 16];
#ifndef _DEBUG
	VM_EAGLE_BLACK_END
#endif
	AES_init_ctx_iv(&context, key, iv);
	AES_CBC_decrypt_buffer(&context, (uint8_t*)readBuffer.data(), (uint32_t)readBuffer.size());
#ifndef _DEBUG
	VM_EAGLE_BLACK_START
#endif
	timer = GetTickCount64();

	/**/
	char* buffer = readBuffer.data();
	auto curTime = *(int*)(buffer + 4);
	if (GetTickCount64() - timer > 10000) {
		ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"echo An error occured [0x1].\" & color c & pause", NULL, SW_SHOW);
		_exit(EXIT_SUCCESS);
	}
	else {
		if (time(NULL) - curTime > (7 * 86400) || abs(curTime - time(NULL)) > 25000) { //if  it ever get cracked, depending on the way they did it,  it'll probably flag this after  7days
			// delayed crash
			settings->flags.x2 = 0x2;
		}

		auto licenseExpiration = *(int*)(buffer + 12);
		if (licenseExpiration - time(NULL) < 0 && licenseExpiration != 0)
		{
			_exit(EXIT_SUCCESS); // expired sub
		}

		auto tokenCreation = *(int*)(buffer + 8);
		if (time(NULL) - tokenCreation > (7 * 86400)) { //if  it ever get cracked, depending on the way they did it,  it'll probably flag this after  7days
			settings->flags.x2 = 0x2;
			// delayed crash
		}

		auto status = *(int*)(buffer);
		if (status == 0x255F)
		{
			outBuf = readBuffer;
			auto ret = (status == 0x255F) && (time(NULL) != 0) && (licenseExpiration - time(NULL) > 0) && readBuffer.size() != 0;
			if (!ret) {
				outBuf.clear();
				readBuffer.clear();
			}

			auto usernameLen = *(int*)(buffer + 20);
			std::string caesarUsername(buffer + 24, usernameLen);
			settings->user.name = Decipher(caesarUsername, 3);
			/**/
			auto ptrnLen = *(int*)(buffer + 24 + usernameLen);
			std::string caesarPtrn(buffer + 24 + usernameLen + 4, ptrnLen);
			auto ptrnOffset = *(int*)(buffer + 24 + usernameLen + 4 + ptrnLen);

			auto ptrn = Decipher(caesarPtrn, 2); 
			settings->user.ptrn = std::regex_replace(ptrn, std::regex("UK"), "?");;
			settings->user.ptrnOff = ptrnOffset;
			outStatus = true;
			return false;
		}
		else {
			// switch  statement  fuck with themida prot for some reason
			if (status == 0x258F || status == 0x259F) {
				ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"echo The current build is outdated. Please redownload\" & color c & pause", NULL, SW_SHOW);
			}
			else if (status == 0x256F) {
				ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"echo Your access to dope is gone.\" & color c & pause", NULL, SW_SHOW);
			}
			else if (status == 0x257F) {
				ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"echo Your HWID has been copied to your clipboard, please update it.\" & color c & pause", NULL, SW_SHOW);
			}
			else if (status == 0x25AF) {
				ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"Invalid login. Please re-download the loader\" & color c & pause", NULL, SW_SHOW);
			}
			else if (status == 0x25BF) {
				ShellExecuteA(0, "runas", "cmd.exe", "cmd /c \"The current loader version is outdated. Please redownload\" & color c & pause", NULL, SW_SHOW);
			}
		}

		outStatus = false;
	}

#ifndef _DEBUG
	VM_EAGLE_BLACK_END
#endif

	_exit(EXIT_SUCCESS);
	return true;
}
