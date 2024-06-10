#include "itemstack.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

void* c_itemstack::get_item()
{
	return settings->env->get_obj_field(obj, mapper::get_offset("item"));
}

bool c_itemstack::is_weapon()
{
	void* holdItem = this->get_item();
	void* holdItemClazz = settings->env->get_object_class(holdItem);
	return settings->env->is_instance_of(settings->itemsword_clazz, holdItem) || settings->env->is_instance_of(settings->itemaxe_clazz, holdItem);
}

int c_itemstack::get_metadata()
{
	return settings->env->get_data_field<int>(obj, mapper::get_offset("metadata"));
}
