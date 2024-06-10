#pragma once
#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_world
{
private:
	void* obj;
public:
	c_world(void* obj) {
		this->obj = obj;
	}

	auto get_world_obj() {
		return this->obj;
	}
public:
	std::vector<void*> get_player_list();
};