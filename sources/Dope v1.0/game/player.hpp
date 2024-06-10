#pragma once

#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

#include "axisaligned.hpp"

class c_jvm;
class c_player
{
private:
	void* obj;
public:
	c_player(void* obj) {
		this->obj = obj;
	}

	auto get_player_obj() {
		return this->obj;
	}
public:
	double get_pos_x();
	double get_pos_y();
	double get_pos_z();
	double get_prev_pos_x();
	double get_prev_pos_y();
	double get_prev_pos_z();
	double get_motion_x();
	double get_motion_y();
	double get_motion_z();
	int get_tick_existed();
	int get_hurt_resistant_time();
	int get_max_hurt_resistant_time();
	float get_yaw();
	float get_previous_yaw();
	float get_pitch();
	float get_previous_pitch();
	std::shared_ptr<c_axisaligned> get_bounding_box();
	char is_on_ground();
	char is_in_water();
	void* get_inventory();
	float get_move_forward();
	float get_move_strafing();
public:
	void set_pos_x(double v);
	void set_pos_y(double v);
	void set_pos_z(double v);
	void set_motion_x(double v);
	void set_motion_y(double v);
	void set_motion_z(double v);
	void set_previous_pitch(float v);
	void set_pitch(float v);
	void set_yaw(float v);
	void set_previous_yaw(float v);
};