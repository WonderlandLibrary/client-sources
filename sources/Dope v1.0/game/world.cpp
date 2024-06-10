#include "World.hpp"
#include "jvm/jvm_base.hpp"

#include "../modules/settings.hpp"
#include "../modules/mapper.hpp"

std::vector<void*> c_world::get_player_list()
{
	std::vector<void*> ret;
	void* playerList = settings->env->get_obj_field(this->obj, mapper::get_offset("playerEntities"));
	if (!playerList) {
		return ret;
	}

	int size = settings->env->get_data_field<int>(playerList, 16);
	void* arrayFid = settings->env->get_obj_field(playerList, 20);
	if (!arrayFid || !size)
		return ret;

	for (int i = 0; i < size; i++) {
		void* firstEntity = settings->env->get_obj_array_elem(arrayFid, i);

		if (firstEntity)
			ret.push_back((void*)firstEntity);
	}

	return ret;
}
