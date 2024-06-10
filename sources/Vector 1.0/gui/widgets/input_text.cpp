
#include "../gui.hpp"

gui::widgets::input_text::input_text(void* menu_inst, std::string txt)
{
	this->menu_inst = menu_inst, this->txt = txt;

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::input_text::~input_text()
{

}

auto gui::widgets::input_text::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	gui->render_space.y += gui::misc::get_text_height() + 2.;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto clicked = gui::misc::is_inside(gui->click.position, this->fixed_space, { this->fixed_space.x + size.x, this->fixed_space.y + size.y });

	auto pressed = gui->index == 0 && gui->mouse.active != 0 && hovered == 1 && clicked == 1;

	if (pressed == 1)
	{
		this->inpt = 1;
	}

	if (this->inpt == 1 && gui->mouse.active != 0 && hovered == 0)
		this->inpt = 0;

	auto inp_vec = gui::misc::text_vector(this->input);

	this->prv = inp_vec.x;

	if (this->val > this->prv)
	{
		gui::misc::limit(this->prv, 0., size.x);

		this->val -= (this->val - this->prv) * .15 * gui->delay / 7.;

		gui::misc::limit(this->val, 0., size.x);
	}

	if (this->val < this->prv)
	{
		gui::misc::limit(this->prv, 0., size.x);

		this->val += (this->prv - this->val) * .15 * gui->delay / 7.;

		gui::misc::limit(this->val, 0., size.x);
	}

	this->val = round(this->val * 10000.) / 10000.;

	if (this->inpt == 1)
	{
		if (gui::ui::u_input == VK_BACK)
		{
			if (this->input.length() > 0)
				this->input.pop_back();
		}
		else if (gui::ui::u_input != -1 && isprint(gui::ui::u_input))
			this->input.push_back(gui::ui::u_input);
	}

	if (this->border_color.x < (this->inpt == 1 ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.increase(.00275 * gui->delay / 7.);

	if (this->border_color.x > (this->inpt == 1 ? .4 : (hovered == 1 ? .35 : .3)))
		this->border_color.decrease(.00275 * gui->delay / 7.);

	if (this->backgr_color.x < (this->inpt == 1 ? .1 : (hovered == 1 ? .12 : .14)))
		this->backgr_color.increase(.00125 * gui->delay / 7.);

	if (this->backgr_color.x > (this->inpt == 1 ? .1 : (hovered == 1 ? .12 : .14)))
		this->backgr_color.decrease(.00125 * gui->delay / 7.);

	auto txt_vec = gui::misc::text_vector(this->txt);

	gui::draw::text_simple(this->txt,
	{ 
		gui->render_space.x,
		gui->render_space.y - gui::misc::get_text_height() + txt_vec.y * .75 - 1.
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->backgr_color);

	gui::draw::text_simple(this->input,
	{ 
		gui->render_space.x + 4.,
		gui->render_space.y + size.y * .5 + inp_vec.y * .25
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	if (this->inpt == 1)
	{
		gui::draw::line_simple(
		{ 
			gui->render_space.x + 4. + this->val,
			gui->render_space.y + 4.
		},
		{ 
			gui->render_space.x + 4. + this->val,
			gui->render_space.y + size.y - 4.
		},
		{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });
	}

	gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->border_color);

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;
}