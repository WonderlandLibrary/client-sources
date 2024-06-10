#include "player.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

double c_player::get_pos_x()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("posX"));
}

double c_player::get_pos_y()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("posY"));
}

double c_player::get_pos_z()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("posZ"));
}

double c_player::get_prev_pos_x()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("prevPosX"));
}

double c_player::get_prev_pos_y()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("prevPosY"));
}

double c_player::get_prev_pos_z()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("prevPosZ"));
}

double c_player::get_motion_x()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("motionX"));
}

double c_player::get_motion_y()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("motionY"));
}

double c_player::get_motion_z()
{
	return settings->env->get_data_field<double>(this->obj, mapper::get_offset("motionZ"));
}

int c_player::get_tick_existed()
{
	return settings->env->get_data_field<int>(this->obj, mapper::get_offset("ticksExisted"));
}

int c_player::get_hurt_resistant_time()
{
	return settings->env->get_data_field<int>(this->obj, mapper::get_offset("hurtResistantTime"));
}

int c_player::get_max_hurt_resistant_time()
{
	return settings->env->get_data_field<int>(this->obj, mapper::get_offset("maxHurtResistantTime"));
}

float c_player::get_yaw()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("rotationYaw"));
}

float c_player::get_previous_yaw()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("prevRotationYaw"));
}

float c_player::get_pitch()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("rotationPitch"));
}

float c_player::get_previous_pitch()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("prevRotationPitch"));
}

std::shared_ptr<c_axisaligned> c_player::get_bounding_box()
{
	void* myboundingbox = settings->env->get_obj_field(this->obj, mapper::get_offset("boundingBox"));
	return std::make_shared<c_axisaligned>(myboundingbox);
}

char c_player::is_on_ground()
{
	return settings->env->get_data_field<char>(this->obj, mapper::get_offset("isOnGround"));
}

char c_player::is_in_water()
{
	return settings->env->get_data_field<char>(this->obj, mapper::get_offset("isInWater"));
}

void* c_player::get_inventory()
{
	return settings->env->get_obj_field(this->obj, mapper::get_offset("inventory"));
}

float c_player::get_move_forward()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("moveForward"));
}

float c_player::get_move_strafing()
{
	return settings->env->get_data_field<float>(this->obj, mapper::get_offset("moveStrafing"));
}

void c_player::set_pos_x(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("posX"), v);
}

void c_player::set_pos_y(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("posY"), v);
}

void c_player::set_pos_z(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("posZ"), v);
}

void c_player::set_motion_x(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("motionX"), v);
}

void c_player::set_motion_y(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("motionY"), v);
}

void c_player::set_motion_z(double v)
{
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("motionZ"), v);
}

void c_player::set_previous_pitch(float v)
{
	settings->env->set_data_field<float>(this->obj, mapper::get_offset("prevRotationPitch"), v);
}

void c_player::set_pitch(float v)
{
	settings->env->set_data_field<float>(this->obj, mapper::get_offset("rotationPitch"), v);
}

void c_player::set_yaw(float v)
{
	settings->env->set_data_field<float>(this->obj, mapper::get_offset("rotationYaw"), v);
}

void c_player::set_previous_yaw(float v)
{
	settings->env->set_data_field<float>(this->obj, mapper::get_offset("prevRotationYaw"), v);
}
