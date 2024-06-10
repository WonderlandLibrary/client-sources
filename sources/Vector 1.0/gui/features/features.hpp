
#include "../gui.hpp"

namespace features::render::combat
{
	inline auto reset = false;

	extern auto auto_clicker(void* menu_inst) -> void;
	extern auto aim_assist(void* menu_inst) -> void;
	extern auto auto_pot(void* menu_inst) -> void;
	extern auto reach(void* menu_inst) -> void;
	extern auto velocity(void* menu_inst) -> void;

	__forceinline auto column_1(void* menu_inst) -> void
	{
		features::render::combat::auto_clicker(menu_inst);
		features::render::combat::aim_assist(menu_inst);
		features::render::combat::auto_pot(menu_inst);
	}

	__forceinline auto column_2(void* menu_inst) -> void
	{
		features::render::combat::reach(menu_inst);
		features::render::combat::velocity(menu_inst);
	}
}

namespace features::render::visual
{
	inline auto reset = false;

	extern auto player_esp(void* menu_inst) -> void;
	extern auto chams(void* menu_inst) -> void;
	extern auto tracers(void* menu_inst) -> void;
	extern auto nametags(void* menu_inst) -> void;
	extern auto block_esp(void* menu_inst) -> void;

	__forceinline auto column_1(void* menu_inst) -> void
	{
		features::render::visual::player_esp(menu_inst);
		features::render::visual::chams(menu_inst);
	}

	__forceinline auto column_2(void* menu_inst) -> void
	{
		features::render::visual::tracers(menu_inst);
		features::render::visual::nametags(menu_inst);
		features::render::visual::block_esp(menu_inst);
	}
}

namespace features::render::world
{
	inline auto reset = false;

	extern auto bridge_assist(void* menu_inst) -> void;

	__forceinline auto column_1(void* menu_inst) -> void
	{
		features::render::world::bridge_assist(menu_inst);
	}

	__forceinline auto column_2(void* menu_inst) -> void
	{

	}
}

namespace features::render::misc
{
	inline auto reset = false;

	extern auto friends(void* menu_inst) -> void;

	__forceinline auto column_1(void* menu_inst) -> void
	{
		features::render::misc::friends(menu_inst);
	}

	__forceinline auto column_2(void* menu_inst) -> void
	{

	}
}

namespace features::render::config
{

}