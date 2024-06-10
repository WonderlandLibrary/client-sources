#pragma once
#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

class c_jvm;
class c_timer
{
private:
	void* obj;
public:
	c_timer(void* obj) {
		this->obj = obj;
	}

	auto get_timer_obj() {
		return this->obj;
	}
public:
	float get_timer_speed();
	void set_timer_speed(float v);

	float get_render_partial_ticks();
};