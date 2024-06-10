
#include "../gui.hpp"

gui::widgets::toggle_button::toggle_button(void* menu_inst, void* ptr)
{
	this->menu_inst = menu_inst, this->value_ptr = ptr;

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::toggle_button::~toggle_button()
{

}

auto gui::widgets::toggle_button::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto clicked = gui::misc::is_inside(gui->click.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto pressed = gui->index == 0 && gui->mouse.active != 0 && hovered == 1 && clicked == 1;

	if (pressed == 1)
	{
		gui->index = this->index;
	}

	if (gui->active == 1 && gui->index == this->index && gui->mouse.active == 0)
	{
		gui->index = -1;

		gui->click.position.x = -1;
		gui->click.position.y = -1;

		if (hovered == 1)
		{
			*(bool*)this->value_ptr = !*(bool*)this->value_ptr;
		}
	}

	this->prv = (*(bool*)this->value_ptr ? 1. : 0.);

	if (this->val > this->prv)
	{
		gui::misc::limit(this->prv, 0., 1.);

		this->val -= (this->val - this->prv) * .1 * gui->delay / 7.;

		gui::misc::limit(this->val, 0., 1.);
	}

	if (this->val < this->prv)
	{
		gui::misc::limit(this->prv, 0., 1.);

		this->val += (this->prv - this->val) * .1 * gui->delay / 7.;

		gui::misc::limit(this->val, 0., 1.);
	}

	this->val = round(this->val * 10000.) / 10000.;

	if (this->border_color.x < (gui->index == this->index ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.increase(.00275 * gui->delay / 7.);

	if (this->border_color.x > (gui->index == this->index ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.decrease(.00275 * gui->delay / 7.);

	if (this->backgr_color.x < (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
		this->backgr_color.increase(.00125 * gui->delay / 7.);

	if (this->backgr_color.x > (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
		this->backgr_color.decrease(.00125 * gui->delay / 7.);

	gui::draw::quad_filled(gui->render_space,				{ gui->render_space.x + size.x, gui->render_space.y + size.y }, this->backgr_color);

	gui::draw::quad_filled(
	{ 
		gui->render_space.x + 2.,
		gui->render_space.y + 2.
	},
	{
		gui->render_space.x + size.x * this->val - 2.,
		gui->render_space.y + size.y - 2.
	},
	{ 128. / 255., 147. / 255., 241. / 255., this->val });

	gui::draw::quad_filled(
	{ 
		gui->render_space.x + (size.x - size.y) * this->val + 3.,
		gui->render_space.y + 3.
	},
	{ 
		gui->render_space.x + size.y + (size.x - size.y) * this->val - 3.,
		gui->render_space.y + size.y - 3.
	},
	{ .65, .65, .65, 1. });

	gui::draw::quad_filled(
	{
		gui->render_space.x + (size.x - size.y) * this->val + 3.,
		gui->render_space.y + 3.
	},
	{
		gui->render_space.x + size.y + (size.x - size.y) * this->val - 3.,
		gui->render_space.y + size.y - 3.
	},
	{ 1.14 - this->val, 1.14 - this->val, 1.14 - this->val, this->val });

	gui::draw::quad_simple(gui->render_space,				{ gui->render_space.x + size.x, gui->render_space.y + size.y }, this->border_color);

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;
}