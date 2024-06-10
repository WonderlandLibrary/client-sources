#pragma once
#pragma comment( lib, "ws2_32" )
#include <stdio.h>
#include <stdlib.h>
#include <string>
#include <iostream>

class signature
{
	public:
	static int get(std::string msg, char *buf, int bufsize, bool send_only)
	{
		VMProtectBeginMutation("signature");
		WSADATA wsd;
		SOCKET client;
		int ret;
		int code = 1;
		sockaddr_in server_s;

		if (WSAStartup(MAKEWORD(2, 2), &wsd) != 0)
		{
			return -1;
		}

		client = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
		if (client == INVALID_SOCKET)
		{
			return -2;
		}

		server_s.sin_family = AF_INET;
		server_s.sin_port = htons(10000);
		server_s.sin_addr.s_addr = inet_addr(xors("145.239.136.134"));

		u_long block = 1;
		if (ioctlsocket(client, FIONBIO, &block) == SOCKET_ERROR)
		{
			closesocket(client);
			return -31;
		}
		if (connect(client, (struct sockaddr*)&server_s, sizeof(server_s)) == SOCKET_ERROR)
		{
			if (WSAGetLastError() != WSAEWOULDBLOCK)
			{
				// connection failed
				closesocket(client);
				return -32;
			}

			// connection pending

			fd_set setW, setE;
			FD_ZERO(&setW);
			FD_SET(client, &setW);
			FD_ZERO(&setE);
			FD_SET(client, &setE);

			timeval time_out = { 0 };
			time_out.tv_sec = 5;
			time_out.tv_usec = 0;

			int ret = select(0, NULL, &setW, &setE, &time_out);
			if (ret <= 0)
			{
				// select() failed or connection timed out
				closesocket(client);
				if (ret == 0)
					WSASetLastError(WSAETIMEDOUT);
				return -33;
			}

			if (FD_ISSET(client, &setE))
			{
				// connection failed
				getsockopt(client, SOL_SOCKET, SO_ERROR, NULL, NULL);
				closesocket(client);
				return -34;
			}
		}

		block = 0;
		if (ioctlsocket(client, FIONBIO, &block) == SOCKET_ERROR)
		{
			closesocket(client);
			return -35;
		}

		bool yes = false;
		for (int i = 0; i < 1; i++)
		{
			ret = send(client, msg.c_str(), (int)msg.length(), 0);
			if (ret == 0)
				code = -4;
			else if (ret == SOCKET_ERROR)
				code = -5;

			if (code != 1)
				goto exit;

			if (!send_only)
			{
				ret = recv(client, buf, bufsize, 0);
				if (ret == 0)
					code = -6;
				else if (ret == SOCKET_ERROR)
					code = -7;
			}
		}

		exit:
		closesocket(client);
		WSACleanup();
		return code;
		VMProtectEnd();
	}
};
