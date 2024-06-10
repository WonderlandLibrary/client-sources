#pragma once
#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_movingobjectposition
{
private:
	void* obj;
public:
	c_movingobjectposition(void* obj) {
		this->obj = obj;
	}

	auto get_movingobjectposition_obj() {
		return this->obj;
	}
public:
	int is_hit_block();
};