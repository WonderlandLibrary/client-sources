#include "timer.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

float c_timer::get_timer_speed()
{
	return settings->env->get_data_field<float>(obj, mapper::get_offset("timerSpeed"));
}

void c_timer::set_timer_speed(float v)
{
	settings->env->set_data_field<float>(obj, mapper::get_offset("timerSpeed"), v);
}

float c_timer::get_render_partial_ticks()
{
	return settings->env->get_data_field<float>(obj, mapper::get_offset("renderPartialTicks"));
}
