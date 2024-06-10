#include <string>
#include <vector>
#include "../../vendors/singleton.h"

class ConfigMaker : public singleton<ConfigMaker>
{
public:
	std::string CreateData(std::string name);
	void Create(std::string data);
	void Delete(int ID);
	void Load(int ID, std::vector<std::string>& out);
	void Reload(std::vector<std::string>& out);
};