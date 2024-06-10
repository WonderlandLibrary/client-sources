#pragma once

#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_inventoryplayer
{
private:
	void* obj;
public:
	c_inventoryplayer(void* obj) {
		this->obj = obj;
	}

	auto get_inventoryplayer_obj() {
		return this->obj;
	}
public:
	void* get_main_inventory();
	int get_current();
	void* get_current_item_stack();

	void set_current(int v);
};