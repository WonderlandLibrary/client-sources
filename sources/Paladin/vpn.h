#pragma once

#include <iostream>
#include <string>
#include <curl/curl.h>
#include <WS2tcpip.h>
#include <Windows.h>
#include <iphlpapi.h>
#include <IcmpAPI.h>

#pragma comment(lib, "Iphlpapi.lib")
#pragma comment(lib, "Ws2_32.lib")

namespace vpn_check {

	size_t WriteCallback(char *contents, size_t size, size_t nmemb, void *userp)
	{
		((std::string*)userp)->append((char*)contents, size * nmemb);
		return size * nmemb;
	}

	inline bool is_using_vpn()
	{
		VMProtectBeginMutation("is_using_vpn");
		bool test_1 = false;

		CURL* easyhandle = curl_easy_init();
		std::string readBuffer;

		curl_easy_setopt(easyhandle, CURLOPT_URL, "http://plain-text-ip.com/");
		curl_easy_setopt(easyhandle, CURLOPT_WRITEFUNCTION, WriteCallback);
		curl_easy_setopt(easyhandle, CURLOPT_WRITEDATA, &readBuffer);

		curl_easy_perform(easyhandle);

		CURL* easyhandle2 = curl_easy_init();

		std::string vpnReadBuffer;

		std::string url = xors("http://check.getipintel.net/check.php?ip=") + readBuffer + xors("&contact=admin@monelite.cc&flags=m");

		curl_easy_setopt(easyhandle2, CURLOPT_URL, url.c_str());
		curl_easy_setopt(easyhandle2, CURLOPT_WRITEFUNCTION, WriteCallback);
		curl_easy_setopt(easyhandle2, CURLOPT_WRITEDATA, &vpnReadBuffer);

		curl_easy_perform(easyhandle2);

		if (strcmp(vpnReadBuffer.c_str(), "1") == 0)
			return true;

		HANDLE icmp_handle = IcmpCreateFile();
		if (icmp_handle == INVALID_HANDLE_VALUE) {
			return false;
		}

		// Parse the destination IP address.
		IN_ADDR dest_ip{};
		if (1 != InetPtonA(AF_INET, readBuffer.c_str(), &dest_ip)) {
			return false;
		}

		// Payload to send.
		constexpr WORD payload_size = 1;
		unsigned char payload[payload_size]{ 42 };

		// Reply buffer for exactly 1 echo reply, payload data, and 8 bytes for ICMP error message.
		constexpr DWORD reply_buf_size = sizeof(ICMP_ECHO_REPLY) + payload_size + 8;
		unsigned char reply_buf[reply_buf_size]{};

		// Make the echo request.
		DWORD reply_count = IcmpSendEcho(icmp_handle, dest_ip.S_un.S_addr,
			payload, payload_size, NULL, reply_buf, reply_buf_size, 10000);

		// Return value of 0 indicates failure, try to get error info.
		if (reply_count == 0) {
			auto e = GetLastError();
			DWORD buf_size = 1000;
			WCHAR buf[1000];
			GetIpErrorString(e, buf, &buf_size);
			return false;
		}

		const ICMP_ECHO_REPLY *r = (const ICMP_ECHO_REPLY *)reply_buf;
		struct in_addr addr;
		addr.s_addr = r->Address;
		char *s_ip = inet_ntoa(addr);

		if (r->RoundTripTime > 5) {
			return true;
		}

		// Close ICMP context.
		IcmpCloseHandle(icmp_handle);

		return false;
		VMProtectEnd();
	}
}
