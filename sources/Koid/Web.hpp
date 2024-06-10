#include "conector.h"
#include <Windows.h>
#include <WinINet.h>
#include <iostream>
#include <vector>

using namespace std;

string DownloadURL2(string URL) {
	HINTERNET interwebs = InternetOpenA("Mozilla/5.0", INTERNET_OPEN_TYPE_DIRECT, NULL, NULL, NULL);
	HINTERNET urlFile;
	string rtn; // is done
	if (interwebs) {
		urlFile = InternetOpenUrlA(interwebs, URL.c_str(), NULL, NULL, NULL, NULL);
		if (urlFile) {
			char buffer[2000];
			DWORD bytesRead;
			do {
				InternetReadFile(urlFile, buffer, 2000, &bytesRead);
				rtn.append(buffer, bytesRead);
				memset(buffer, 0, 2000);
			} while (bytesRead);
			InternetCloseHandle(interwebs);
			InternetCloseHandle(urlFile);
			return rtn;
		}
	}
	InternetCloseHandle(interwebs);
	return rtn;
}

bool CheckAccount() {
	std::string link = std::string("http://51.68.213.244/api/konk.php?hwid=");
	link = link + std::string(cucklord_get_hwid()) + std::string("&responsetype=plaintext");
	std::string webContents = DownloadURL2(link.c_str());
	if (webContents == "goodauth") { return true; }
	else { return false; }
}