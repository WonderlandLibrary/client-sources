#pragma once

#include "../../vendors/singleton.h"
#include <memory>
#include <optional>

struct vec3
{
	double x, y, z;
};

class c_jvm;
class c_minecraft
{
public:
	c_minecraft();
public:
	void* get_minecraft_obj();
	void* get_theplayer_obj();
	void* get_theworld_obj();
	void* get_curscreen_obj();
	void* get_objectmouseover_obj();
	void* get_gamesettings_obj();
	void* get_timer_obj();
};