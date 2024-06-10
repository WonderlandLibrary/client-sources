
#include "../features.hpp"

auto features::render::combat::aim_assist(void* menu_inst) -> void
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

	static auto enabled = false;

	static gui::widgets::toggle_button toggle_button(gui, &enabled);

	toggle_button.reset(features::render::combat::reset),
		toggle_button.render({ 65., 23. });

	gui->render_space.x += 125.;
	gui->render_space.y -= 33.;

	static gui::widgets::button	bind_button(gui, "Bind", &enabled);

	bind_button.reset(features::render::combat::reset),
		bind_button.render({ 125., 23. });

	gui->render_space.x -= 125.;

	static auto aim_speed = 15.;

	static gui::widgets::slider	slider_0(gui,
		"Aim speed",
		&aim_speed,
		5.,
		20.
	);

	slider_0.reset(features::render::combat::reset),
		slider_0.render();

	static auto aim_fov = 110.;

	static gui::widgets::slider	slider_1(gui,
		"Aim fov",
		&aim_fov,
		30.,
		180.
	);

	slider_1.reset(features::render::combat::reset),
		slider_1.render();

	static auto aim_distance = 5.;

	static gui::widgets::slider	slider_2(gui,
		"Aim distance",
		&aim_distance,
		2.,
		7.
	);

	slider_2.reset(features::render::combat::reset),
		slider_2.render();

	static gui::widgets::combo_box combo_0(gui,
		"Item whitelist",
		{
			false,
			false
		},
		{
			"Axes",
			"Swords",
		}
	);

	combo_0.reset(features::render::combat::reset),
		combo_0.render();

	static auto clicking_only = true;

	static gui::widgets::check_box check_0(gui,
		"Clicking only",
		&clicking_only
	);

	check_0.reset(features::render::combat::reset),
		check_0.render();

	static auto break_blocks = true;

	static gui::widgets::check_box check_1(gui,
		"Break blocks",
		&break_blocks
	);

	check_1.reset(features::render::combat::reset),
		check_1.render();

	static auto lock_target = true;

	static gui::widgets::check_box check_2(gui,
		"Lock target",
		&lock_target
	);

	check_2.reset(features::render::combat::reset),
		check_2.render();

	static auto through_walls = false;

	static gui::widgets::check_box check_3(gui,
		"Through walls",
		&through_walls
	);

	check_3.reset(features::render::combat::reset),
		check_3.render();

	static auto ignore_nakeds = false;

	static gui::widgets::check_box check_4(gui,
		"Ignore nakeds",
		&ignore_nakeds
	);

	check_4.reset(features::render::combat::reset),
		check_4.render();

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

	static auto txt_0 = "Aim assist";

	static auto txt_0_vec = gui::misc::text_vector(txt_0);

	gui::draw::text_simple(txt_0,
	{
		init_render_space.x + 10.,
		init_render_space.y + txt_0_vec.y * .75 - 10.
	},
	{ .5, .5, .5, 1. });

	gui->render_space.y += 30.;
}