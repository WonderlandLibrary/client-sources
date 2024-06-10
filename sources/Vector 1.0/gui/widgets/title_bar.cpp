
#include "../gui.hpp"

gui::widgets::title_bar::title_bar(void* menu_inst, std::string txt, std::string sub)
{
	this->menu_inst = menu_inst, this->txt = txt, this->sub = sub;

	this->mini = new gui::widgets::button(this->menu_inst, "-", nullptr, 1);
	this->exit = new gui::widgets::button(this->menu_inst, "x", nullptr, 1);

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::title_bar::~title_bar()
{
	delete mini;
	delete exit;
}

auto gui::widgets::title_bar::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->backgr_color);

	//render buttons

	auto prv_render_space = gui->render_space;

	gui->render_space = { size.x - 64., (size.y - 26.) * .5 };

	if (this->mini->render({ 26., 26. }))
	{
		PostMessageA(gui->hwnd, WM_SYSCOMMAND, 0xF020, 0);
	}

	gui->render_space = { size.x - 34., (size.y - 26.) * .5 };

	if (this->exit->render({ 26., 26. }))
	{
		PostMessageA(gui->hwnd, WM_SYSCOMMAND, 0xF060, 0);
	}

	gui->render_space = prv_render_space;

	//handle and render title bar

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto clicked = gui::misc::is_inside(gui->click.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto pressed = gui->index == 0 && gui->mouse.active != 0 && hovered == 1 && clicked == 1;

	if (gui->index == this->index && gui->mouse.active == 0)
		gui->index = -1;

	if (gui->active == 1 && (gui->index == this->index || pressed == 1))
	{
		gui->index = this->index;

		gui::glfw::set_interval(0);
		
		tagPOINT point;

		GetCursorPos(&point);

		gui::glfw::set_position(gui->glfw, { point.x - gui->click.position.x, point.y - gui->click.position.y });
	}
	else
	{
		gui::glfw::set_interval(1);
	}

	if (this->border_color.x < (gui->index == this->index ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.increase(.00275 * gui->delay / 7.);

	if (this->border_color.x > (gui->index == this->index ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.decrease(.00275 * gui->delay / 7.);

	//if (this->backgr_color.x < (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
	//	this->backgr_color.increase(.00125 * gui->delay / 7.);

	//if (this->backgr_color.x > (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
	//	this->backgr_color.decrease(.00125 * gui->delay / 7.);

	auto txt_vec = gui::misc::text_vector(this->txt);

	gui::draw::text_simple(this->txt,
	{ 
		gui->render_space.x + 10.,
		gui->render_space.y + size.y * .5 + txt_vec.y * .25,
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a - .2 });

	auto sub_vec = gui::misc::text_vector(this->txt);

	gui::draw::text_simple(this->sub,
	{
		gui->render_space.x + 10. + txt_vec.x + 4.,
		gui->render_space.y + size.y * .5 + sub_vec.y * .25,
	},
	{ 128. / 255., 147. / 255., 241. / 255., 1. });

	gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->border_color);

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;
}