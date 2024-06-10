#include "movingobjectposition.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

int c_movingobjectposition::is_hit_block()
{
	void* type = settings->env->get_obj_field(obj, mapper::get_offset("typeOfHit"));
	void* st = settings->env->get_static_obj_field(settings->env->get_object_class(type), mapper::get_offset("BLOCK"));
	return type == st;
}
