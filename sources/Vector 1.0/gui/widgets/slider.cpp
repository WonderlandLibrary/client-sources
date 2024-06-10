
#include "../gui.hpp"

gui::widgets::slider::slider(void* menu_inst, std::string txt, void* value_ptr, const double min, const double max, const __int8 inpt)
{
	this->menu_inst = menu_inst, this->txt = txt, this->value_ptr = value_ptr, this->min = min, this->max = max, this->inpt = inpt;

	this->prv = (*(double*)this->value_ptr - this->min) / (this->max - this->min);

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::slider::~slider()
{

}

auto gui::widgets::slider::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	gui->render_space.y += gui::misc::get_text_height() + 2.;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto clicked = gui::misc::is_inside(gui->click.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto pressed = gui->index == 0 && gui->mouse.active != 0 && this->inpt == 1 && hovered == 1 && clicked == 1;

	if (gui->index == this->index && gui->mouse.active == 0)
	{
		gui->index = -1;

		gui->click.position.x = -1;
		gui->click.position.y = -1;
	}

	this->prv = (*(double*)this->value_ptr - this->min) / (this->max - this->min);

	if (gui->active == 1 && (gui->index == this->index || pressed == 1))
	{
		gui->index = this->index;
		
		this->prv = (gui->mouse.position.x - gui->render_space.x) / size.x;
	
		if (gui->mouse.position.x < gui->render_space.x)
			this->prv = 0.;

		if (gui->mouse.position.x > gui->render_space.x + size.x)
			this->prv = 1.;

		*(double*)this->value_ptr = (this->max - this->min) * this->prv + this->min;
	}

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
		gui->render_space.x,
		gui->render_space.y - gui::misc::get_text_height() + txt_vec.y * .75 - 1.
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	auto val_str = std::to_string(this->min + ((this->max - this->min) * this->prv)), substr_val_str = val_str.substr(0, val_str.find('.') + 3);

	auto sub_vec = gui::misc::text_vector(substr_val_str);

	gui::draw::text_simple(substr_val_str,
	{
		gui->render_space.x + size.x - sub_vec.x,
		gui->render_space.y - gui::misc::get_text_height() + txt_vec.y * .75 - 1.
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->backgr_color);

	gui::draw::quad_filled(
	{ 
		gui->render_space.x + 2.,
		gui->render_space.y + 2.
	},
	{ 
		gui->render_space.x + (this->val * size.x < 2 ? 2 : (this->val * size.x > size.x - 2. ? size.x - 2. : this->val * size.x)),
		gui->render_space.y - 2. + size.y 
	},
	{ 128. / 255., 147. / 255., 241. / 255., 1. });

	gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->border_color);

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;
}