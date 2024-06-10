#pragma once
#include <windows.h>
#include <string>
#include "../../vendors/singleton.h"

class RequestHelper : public singleton<RequestHelper>
{
public:
	std::string send_request(const char* link, char* requestBody, size_t requestBodySize);
};