
#include "../features.hpp"

auto features::render::visual::player_esp(void* menu_inst) -> void
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

	toggle_button.reset(features::render::visual::reset),
		toggle_button.render({ 65., 23. });

	gui->render_space.x += 125.;
	gui->render_space.y -= 33.;

	static gui::widgets::button	bind_button(gui, "Bind", &enabled);

	bind_button.reset(features::render::visual::reset),
		bind_button.render({ 125., 23. });

	gui->render_space.x -= 125.;

	static gui::widgets::combo_box combo_0(gui,
		"Mode",
		{
			true,
			false
		},
		{
			"2D",
			"3D",
		},
		1 /*1 active item only*/
	);

	combo_0.reset(features::render::visual::reset),
		combo_0.render();

	static gui::vec4 fill_color_2d
	{
		255. / 255.,
		255. / 255.,
		255. / 255.,
		200. / 255.
	};

	if (combo_0.items[0] == true)
	{
		static gui::widgets::color_picker pkr_0(gui,
			"Fill color",
			&fill_color_2d
		);

		pkr_0.reset(features::render::visual::reset),
			pkr_0.render();

		static gui::vec4 outline_color_2d
		{
			0.,
			0.,
			0.,
			200. / 255.
		};

		static gui::widgets::color_picker pkr_1(gui,
			"Outline color",
			&outline_color_2d
		);

		pkr_1.reset(features::render::visual::reset),
			pkr_1.render();

		static auto draw_health = true;

		static gui::widgets::check_box check_0(gui,
			"Draw health",
			&draw_health
		);

		check_0.reset(features::render::visual::reset),
			check_0.render();

		static auto draw_corners = true;

		static gui::widgets::check_box check_1(gui,
			"Draw corners",
			&draw_corners
		);

		check_1.reset(features::render::visual::reset),
			check_1.render();
	}

	if (combo_0.items[1] == true)
	{
		static gui::vec4 fill_color_3d
		{
			128. / 255.,
			147. / 255.,
			241. / 255.,
			255. / 255.
		};

		static gui::widgets::color_picker pkr_2(gui,
			"Fill color",
			&fill_color_3d
		);

		pkr_2.reset(features::render::visual::reset),
			pkr_2.render();

		static gui::vec4 outline_color_3d
		{
			108. / 255.,
			127. / 255.,
			255. / 255.,
			255. / 255.
		};

		static gui::widgets::color_picker pkr_3(gui,
			"Outline color",
			&outline_color_3d
		);

		pkr_3.reset(features::render::visual::reset),
			pkr_3.render();

		static auto draw_expanded = false;

		static gui::widgets::check_box check_2(gui,
			"Draw expanded",
			&draw_expanded
		);

		check_2.reset(features::render::visual::reset),
			check_2.render();
	}

	static auto draw_invisibles = false;

	static gui::widgets::check_box check_3(gui,
		"Draw invisibles",
		&draw_invisibles
	);

	check_3.reset(features::render::visual::reset),
		check_3.render();

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

	static auto txt_0 = "Player esp";

	static auto txt_0_vec = gui::misc::text_vector(txt_0);

	gui::draw::text_simple(txt_0,
	{
		init_render_space.x + 10.,
		init_render_space.y + txt_0_vec.y * .75 - 10.
	},
	{ .5, .5, .5, 1. });

	gui->render_space.y += 30.;
}