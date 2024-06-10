#pragma once
#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_gamesettings
{
private:
	void* obj;
public:
	c_gamesettings(void* obj) {
		this->obj = obj;
	}

	auto get_gamesettings_obj() {
		return this->obj;
	}
public:
	float get_gamma();
	void set_gamma(float v);
};