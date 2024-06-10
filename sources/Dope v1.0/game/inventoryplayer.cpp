#include "inventoryplayer.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

void* c_inventoryplayer::get_main_inventory()
{
	return settings->env->get_obj_field(obj, mapper::get_offset("mainInventory"));
}

int c_inventoryplayer::get_current()
{
	return settings->env->get_data_field<int>(obj, mapper::get_offset("currentItem"));
}

void* c_inventoryplayer::get_current_item_stack()
{
	void* mainInventory = this->get_main_inventory();
	return settings->env->get_obj_array_elem(mainInventory, this->get_current());
}

void c_inventoryplayer::set_current(int v)
{
	settings->env->set_data_field<int>(obj, mapper::get_offset("currentItem"), v);
}
