
#include "features/features.hpp"

auto gui::ui::draw_list() -> __int8
{
	static gui::widgets::title_bar title_bar(this, "VECTOR", "1.0");

	gui::draw::buffering = !gui::draw::buffering;

	title_bar.render();

	gui::draw::buffering = !gui::draw::buffering;

	this->render_space = { 0., 40. };

	static auto tab_index = 0;

	static gui::widgets::nav_tab lf_navigation_tab(this, &tab_index,
	{
		"COMBAT",
		"VISUAL",
		"WORLD",
		"MISC",
		"CONFIGS"
	});

	lf_navigation_tab.render({ 150., 760. });

	auto prv_space = gui::vec2
	{
		this->render_space.x - 20.,
		this->render_space.y - 20.
	};

	auto gui_free_space = (750. - this->render_space.x - 550.) * .5;

	this->render_space =
	{
		this->render_space.x + gui_free_space - 10.,
		this->render_space.y + 5.
	};

	//draw tabs modules

	static auto last_tab = tab_index;

	if (tab_index == 0)
	{
		static gui::widgets::scroll	scroll(this);

		if (last_tab != tab_index)
		{
			features::render::combat::
				reset = true;
		}

		scroll.handle(prv_space, { 750., 800. });

		auto initial_render = this->render_space;

		features::render::combat::column_1(
			this /*render the column number 1*/);

		this->render_space =
		{
			initial_render.x + 300.,
			initial_render.y
		};

		features::render::combat::column_2(
			this /*render the column number 2*/);

		features::render::combat::
			reset = false /*set after animated*/;
	}

	if (tab_index == 1)
	{
		static gui::widgets::scroll	scroll(this);

		if (last_tab != tab_index)
		{
			features::render::visual::
				reset = true;
		}

		scroll.handle(prv_space, { 750., 800. });

		auto initial_render = this->render_space;

		features::render::visual::column_1(
			this /*render the column number 1*/);

		this->render_space =
		{
			initial_render.x + 300.,
			initial_render.y
		};

		features::render::visual::column_2(
			this /*render the column number 2*/);

		features::render::visual::
			reset = false /*set after animated*/;
	}

	if (tab_index == 2)
	{
		static gui::widgets::scroll	scroll(this);

		if (last_tab != tab_index)
		{
			features::render::world::
				reset = true;
		}

		scroll.handle(prv_space, { 750., 800. });

		auto initial_render = this->render_space;

		features::render::world::column_1(
			this /*render the column number 1*/);

		this->render_space =
		{
			initial_render.x + 300.,
			initial_render.y
		};

		features::render::world::column_2(
			this /*render the column number 2*/);

		features::render::world::
			reset = false /*set after animated*/;
	}

	if (tab_index == 3)
	{
		static gui::widgets::scroll	scroll(this);

		if (last_tab != tab_index)
		{
			features::render::misc::
				reset = true;
		}

		scroll.handle(prv_space, { 750., 800. });

		auto initial_render = this->render_space;

		features::render::misc::column_1(
			this /*render the column number 1*/);

		this->render_space =
		{
			initial_render.x + 300.,
			initial_render.y
		};

		features::render::misc::column_2(
			this /*render the column number 2*/);

		features::render::misc::
			reset = false /*set after animated*/;
	}

	last_tab = tab_index;

	gui::draw::buffering = !gui::draw::buffering;

	gui::draw::quad_simple(
		{ 0., 0. }, { 750., 800. }, { .3, .3, .3, 1. } /*borders*/);

	gui::draw::buffering = !gui::draw::buffering;

	return 0;
}