#include "Auth.h"
#include <winternl.h>
#include <curl/curl.h>

using json = nlohmann::json;

Auth::Auth()
{
	curl_global_init(CURL_GLOBAL_ALL);
}
Auth::~Auth()
{
	curl_global_cleanup();
}

size_t WriteData(void *contents, size_t size, size_t nmemb, std::string *s) {
	size_t newLength = size * nmemb;
	size_t oldLength = s->size();
	try {
		s->resize(oldLength + newLength);
	}
	catch (std::bad_alloc &e) {
		UNREFERENCED_PARAMETER(e);
		return 0;
	}

	std::copy((char*)contents, (char*)contents + newLength, s->begin() + oldLength);
	return size * nmemb;
}

std::vector<char>* Auth::get_request(std::string *params, long *http_code)
{
	VMProtectBeginMutation("auth.get_request");
	std::vector<char> *buf = new std::vector<char>();

	CURL *curl;
	CURLcode res;
	std::string *response = new std::string();

	curl = curl_easy_init();
	if (curl)
	{
		curl_easy_setopt(curl, CURLOPT_URL, xors("https://api.paladin.ac/client/ss/"));
		curl_easy_setopt(curl, CURLOPT_POSTFIELDS, params->c_str());
		curl_easy_setopt(curl, CURLOPT_POSTFIELDSIZE, (long)params->length());
		curl_easy_setopt(curl, CURLOPT_HEADER, FALSE);
		curl_easy_setopt(curl, CURLOPT_HEADERDATA, FALSE);
		curl_easy_setopt(curl, CURLOPT_SSLVERSION, CURL_SSLVERSION_TLSv1_1);
		curl_easy_setopt(curl, CURLOPT_SSL_VERIFYPEER, 1L);
		curl_easy_setopt(curl, CURLOPT_SSL_VERIFYHOST, 1L);
		curl_easy_setopt(curl, CURLOPT_USERAGENT, xors("Paladin ") + Paladin::Version);
		curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteData);
		curl_easy_setopt(curl, CURLOPT_WRITEDATA, response);

		res = curl_easy_perform(curl);

		if (res == CURLE_OK)
		{
			curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, http_code);

			for (auto c : *response)
				buf->push_back(c);
			delete response;
		}

		curl_easy_cleanup(curl);
	}

	return buf;
	VMProtectEnd();
}

int Auth::send_results(std::string id, std::string jsonhex) 
{
	VMProtectBeginMutation("auth.send_results");
	std::string response;
	std::string *params = new std::string(xors("RID=") + id + xors("&RS=") + jsonhex + xors("&V=") + Paladin::Version);

	auto buf = get_request(params, nullptr);

	for (auto c : *buf)
		response.push_back(c);

	if (response.length() != 0)
	{
		if (response != xors("thankyou"))
			return ERR;
		else
			return AUTHENTICATED;
	}

	return ERR;
	VMProtectEnd();
}

int Auth::authenticate(std::string pin, std::string &id, json &j, std::string *key, std::string drive, std::string board, std::string cpu) {
	VMProtectBeginMutation("auth.authenticate");
	if (pin.length() != 6)
		return WRONGPIN;
	if (!is_int(pin))
		return WRONGPIN;

	std::string response;
	std::string *params = new std::string(xors("P=") + pin + xors("&V=") + Paladin::Version + xors("&D=") + drive + xors("&M=") + board + xors("&C=") + cpu);

	long response_code = 0;
	auto buf = get_request(params, &response_code);
	delete params;

	for (auto c : *buf)
		response.push_back(c);
	delete buf;

	if (response.length() != 0 && response_code == 200) 
	{
		if (response == xors("error"))
			return ERR;

		if (response == xors("update"))
			return OUTDATED;

		if (response == xors("maint"))
			return MAINTENANCE;

		response = xorr(hex2str(response), key->c_str(), key->size());

		if (response == xors("abuse"))
			return ABUSE;

		j = json::parse(response);
		std::string().swap(response);

		if (j.is_null())
			return ERR;

		if (!j[xors("ok")].is_null()) {
			if (j[xors("ok")] == xors("no"))
				return WRONGPIN;
			else if (j[xors("ok")] == xors("outdated"))
				return OUTDATED;
			else if (j[xors("ok")] == xors("yes")) {
				id = j[xors("id")];
				return AUTHENTICATED;
			}
		}

		return ERR;
	}
	return ERR;
	VMProtectEnd();
}

bool Auth::codeintegrityenabled()
{
	VMProtectBeginMutation("auth.codeintegrityenabled");
	typedef NTSTATUS(__stdcall* td_NtQuerySystemInformation)(
		ULONG           SystemInformationClass,
		PVOID           SystemInformation,
		ULONG           SystemInformationLength,
		PULONG          ReturnLength
		);

	struct SYSTEM_CODEINTEGRITY_INFORMATION {
		ULONG Length;
		ULONG CodeIntegrityOptions;
	};

	static td_NtQuerySystemInformation NtQuerySystemInformation = (td_NtQuerySystemInformation)GetProcAddress(GetModuleHandleA("ntdll.dll"), "NtQuerySystemInformation");
	
	SYSTEM_CODEINTEGRITY_INFORMATION Integrity = { sizeof(SYSTEM_CODEINTEGRITY_INFORMATION), 0 };
	NTSTATUS status = NtQuerySystemInformation(103, &Integrity, sizeof(Integrity), NULL);
	
	return (NT_SUCCESS(status) && (Integrity.CodeIntegrityOptions & 1));
	VMProtectEnd();
}
