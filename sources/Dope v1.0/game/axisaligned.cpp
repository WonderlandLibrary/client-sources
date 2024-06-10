#include "axisaligned.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

axisalignedbb c_axisaligned::get_bounding_box()
{
	axisalignedbb bb{};
	if (!this->obj)
		return bb;

	bb.minX = settings->env->get_data_field<double>(this->obj, mapper::get_offset("minX"));
	bb.minY = settings->env->get_data_field<double>(this->obj, mapper::get_offset("minY"));
	bb.minZ = settings->env->get_data_field<double>(this->obj, mapper::get_offset("minZ"));
	bb.maxX = settings->env->get_data_field<double>(this->obj, mapper::get_offset("maxX"));
	bb.maxY = settings->env->get_data_field<double>(this->obj, mapper::get_offset("maxY"));
	bb.maxZ = settings->env->get_data_field<double>(this->obj, mapper::get_offset("maxZ"));

	return bb;
}

void c_axisaligned::set_bounding_box(axisalignedbb v)
{
	if (!this->obj)
		return;

	settings->env->set_data_field<double>(this->obj, mapper::get_offset("minX"), v.minX);
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("minY"), v.minY);
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("minZ"), v.minZ);
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("maxX"), v.maxX);
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("maxY"), v.maxY);
	settings->env->set_data_field<double>(this->obj, mapper::get_offset("maxZ"), v.maxZ);
}
