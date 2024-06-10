
#include "../features.hpp"

auto features::render::misc::friends(void* menu_inst) -> void
{
	auto gui = (gui::ui*)menu_inst;

	//draw background

	static auto back_min = gui::vec2{ -1.,  -1. };
	static auto back_max = gui::vec2{ -1.,  -1. };

	gui::draw::quad_filled(back_min, back_max,
		{ .12, .12, .12, 1. });

	//save current space

	auto init_render_space = gui->render_space;

	gui->render_space.y +=
		gui::misc::get_text_height() + 5.;

	//widgets

	static gui::widgets::input_text inp_txt(gui, "Name");

	inp_txt.reset(features::render::misc::reset),
		inp_txt.render();

	static gui::widgets::combo_box combo_0(gui,
		"List",
		{},
		{}
	);
	
	combo_0.reset(features::render::misc::reset),
		combo_0.render();

	if (inp_txt.input.length() > 2 && GetAsyncKeyState(VK_RETURN) & 0x8000)
	{
		combo_0.items.emplace_back(true);
		combo_0.names.emplace_back(inp_txt.input);

		combo_0.prvs.resize(combo_0.prvs.size() + 1);
		combo_0.vals.resize(combo_0.vals.size() + 1);

		inp_txt.input.clear();
	}

	//borders and module name

	back_min =
	{
		init_render_space.x - 10.,
		init_render_space.y
	};

	back_max =
	{
		gui->render_space.x + 260.,
		gui->render_space.y + 10.
	};

	gui::draw::quad_simple(back_min, back_max,
		{ .17, .17, .17, 1. });

	static auto txt_0 = "Friends list";

	static auto txt_0_vec = gui::misc::text_vector(txt_0);

	gui::draw::text_simple(txt_0,
	{
		init_render_space.x + 10.,
		init_render_space.y + txt_0_vec.y * .75 - 10.
	},
	{ .5, .5, .5, 1. });

	gui->render_space.y += 30.;
}