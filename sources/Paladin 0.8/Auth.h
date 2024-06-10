#pragma once
#include "Main.h"

#include "includes.h"

class Auth
{
public:
	std::vector<char>* get_request(std::string *params, long *http_code);
	bool codeintegrityenabled();
	int authenticate(std::string pin, std::string &id, json &j, std::string *key, std::string drive, std::string board, std::string cpu);
	int send_results(std::string id, std::string jsonhex);

	Auth();
	~Auth();

	//HANDLE web_handle;

	enum {
		AUTHENTICATED,
		WRONGPIN,
		ERR,
		MAINTENANCE,
		OUTDATED,
		ABUSE
	};
};

