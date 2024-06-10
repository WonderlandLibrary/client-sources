#pragma once

#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_itemstack
{
private:
	void* obj;
public:
	c_itemstack(void* obj) {
		this->obj = obj;
	}

	auto get_itemstack_obj() {
		return this->obj;
	}
public:
	void* get_item();
	bool is_weapon();
	int get_metadata();
};