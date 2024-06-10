#pragma once
#include <windows.h>
#include <string>

enum c_version {
	UNKNOWN,
	CASUAL_1_7_10,
	CASUAL_1_8,
	FORGE_1_7_10,
	FORGE_1_8,
	BADLION_1_7_10,
	BADLION_1_8,
	FEATHER_1_8,
	LUNAR_1_7_10,
	LUNAR_1_8
};

namespace mapper {
	void initialize_mapper();
	unsigned short get_offset(const char* f);
	std::string load_lunar_class(HANDLE javaw_handle, char* process_path);
}