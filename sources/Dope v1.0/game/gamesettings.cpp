#include "gamesettings.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

float c_gamesettings::get_gamma()
{
	return settings->env->get_data_field<float>(obj, mapper::get_offset("gammaSetting"));
}

void c_gamesettings::set_gamma(float v)
{
	settings->env->set_data_field<float>(obj, mapper::get_offset("gammaSetting"), v);
}
