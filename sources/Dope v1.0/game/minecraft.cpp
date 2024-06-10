#include "Minecraft.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

c_minecraft::c_minecraft()
{
}

void* c_minecraft::get_minecraft_obj()
{
	return settings->env->get_static_obj_field(settings->minecraft_clazz, mapper::get_offset("theMinecraft"));
}

void* c_minecraft::get_theplayer_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("thePlayer"));
}

void* c_minecraft::get_theworld_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("theWorld"));
}

void* c_minecraft::get_curscreen_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("currentScreen"));
}

void* c_minecraft::get_objectmouseover_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("objectMouseOver"));
}

void* c_minecraft::get_gamesettings_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("gameSettings"));
}

void* c_minecraft::get_timer_obj()
{
	return settings->env->get_obj_field(this->get_minecraft_obj(), mapper::get_offset("timer"));
}
