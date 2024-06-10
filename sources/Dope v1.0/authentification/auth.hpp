#include <windows.h>
#include <string>
#include  "../../vendors/singleton.h"

class Authentification : public singleton<Authentification>
{
public:
	std::string get_hwid();
	bool login(std::string token, std::string hwid, bool& outStatus, std::string& outBuf);
};