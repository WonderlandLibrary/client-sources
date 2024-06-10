
#include "../gui.hpp"

gui::widgets::check_box::check_box(void* menu_inst, std::string txt, void* value_ptr, const __int8 inpt)
{
	this->menu_inst = menu_inst, this->txt = txt, this->value_ptr = value_ptr, this->inpt = inpt;
	
	this->prv = (*(bool*)this->value_ptr ? 1. : 0.);

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::check_box::~check_box()
{

}

auto gui::widgets::check_box::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	gui->render_space.x += 250. - size.x;
	this->fixed_space.x += 250. - size.x;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto clicked = gui::misc::is_inside(gui->click.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto pressed = gui->index == 0 && gui->mouse.active != 0 && this->inpt == 1 && hovered == 1 && clicked == 1;

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

	auto txt_vec = gui::misc::text_vector(this->txt);

	gui::draw::text_simple(this->txt,
	{ 
		gui->render_space.x + size.x - 250.,
		gui->render_space.y + size.y * .5 + txt_vec.y * .25,
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->backgr_color);

	gui::draw::quad_filled(
	{
		gui->render_space.x + (size.x + 2.) * .5 - (size.x - 2.) * .5 * this->val,
		gui->render_space.y + (size.y + 2.) * .5 - (size.y - 2.) * .5 * this->val
	},
	{
		gui->render_space.x + (size.x - 2.) * .5 + (size.x - 2.) * .5 * this->val,
		gui->render_space.y + (size.y - 2.) * .5 + (size.y - 2.) * .5 * this->val
	},
	{ 128. / 255., 147. / 255., 241. / 255., this->val });

	gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->border_color);

	gui->render_space.x -= 250. - size.x, this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;
}