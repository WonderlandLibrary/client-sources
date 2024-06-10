
#include "conector.h"
#include "hash.h"
#define CURL_STATICLIB
#include <curl\curl.h>

CURL* cucklord_curl; CURLcode cucklord_res;
std::string cucklord_answer; const char* cucklord_auth_url = "http://51.68.213.244/temp/ghost/authentication.php";
size_t curl_write(void* ptr, size_t size, size_t nmemb, void* stream)
{
	cucklord_answer.append((char*)ptr, size * nmemb);
	return size * nmemb;
}

void cucklord_authenticate() {
	cucklord_answer.clear();
	std::string cucklord_hwid_string = cucklord_get_hwid(); cucklord_hwid_string = picosha2::hash256_hex_string(cucklord_hwid_string);
	std::string cucklord_auth_query_string = "HWID=" + cucklord_hwid_string;
	const char* cucklord_auth_query_char = cucklord_auth_query_string.c_str(); //this is the query sent to the server
	curl_global_init(CURL_GLOBAL_ALL); cucklord_curl = curl_easy_init();
	if (cucklord_curl) {
		curl_easy_setopt(cucklord_curl, CURLOPT_URL, cucklord_auth_url);
		curl_easy_setopt(cucklord_curl, CURLOPT_POSTFIELDS, cucklord_auth_query_char);
		curl_easy_setopt(cucklord_curl, CURLOPT_WRITEFUNCTION, curl_write);
		cucklord_res = curl_easy_perform(cucklord_curl);
		if (cucklord_res != CURLE_OK) {
			fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(cucklord_res));
			exit(EXIT_SUCCESS);
		}
		curl_easy_cleanup(cucklord_curl);
	}
	curl_global_cleanup();
	if (cucklord_answer.find("correctauth") != std::string::npos) 
	{ 
		cucklord_logged = true;
	} 
	else
	{
		const size_t len = cucklord_hwid_string.size() + 1;
		HGLOBAL hMem = GlobalAlloc(GMEM_MOVEABLE, len);
		memcpy(GlobalLock(hMem), cucklord_hwid_string.c_str(), len);
		GlobalUnlock(hMem); OpenClipboard(0); EmptyClipboard(); SetClipboardData(CF_TEXT, hMem);
		CloseClipboard();
		cucklord_logged = false;
	}
}