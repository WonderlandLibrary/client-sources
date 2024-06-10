
#include "../gui.hpp"

gui::widgets::button::button(void* menu_inst, std::string txt, void* value_ptr, const __int8 behv, const __int8 hide)
{
	this->menu_inst = menu_inst, this->txt = txt, this->value_ptr = value_ptr, this->behv = behv, this->hide = hide;

	this->bind = this->txt == "Bind";

	if (this->bind)
	{
		((gui::ui*)this->menu_inst)->key.emplace_back(this);
	}

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::button::~button()
{

}

auto gui::widgets::button::render(const gui::vec2 size) -> bool
{
	auto gui = (gui::ui*)this->menu_inst;

	auto ret_value = false;

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
			if (this->bind == 1)
			{
				this->txt = "...", this->key = 0, this->updt = 1;
			}

			this->prv = 1., this->val = 0., this->mode = 1;

			ret_value = (this->behv == 0 ? true : false /*button released*/);
		}
	}

	if (this->val >= .99 && this->mode == 1)
	{
		this->prv = 0., this->mode = 0;

		ret_value = (this->behv == 1 ? true : false /*animation completed*/);
	}

	if (this->bind == 1 && this->txt == "...")
	{
		for (auto x = 7; x < 256; ++x)
		{
			if (GetAsyncKeyState(x) & 0x8000)
				this->key = x;
		}

		if (this->key != 0)
		{
			if (this->key == VK_ESCAPE)
			{
				this->key = 0, this->txt = "Bind";
			}
			else
			{
				char name[1024];

				GetKeyNameTextA(
					(MapVirtualKeyA(this->key, MAPVK_VK_TO_VSC) << 16),
					name, 1024);

				this->txt = std::string(name);
			}

			if (this->txt.length() == 0 || !(this->txt[0] >= 32 && this->txt[0] < 128))
				this->txt = std::to_string(this->key);
		}
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

	if (this->hide == 0)
	{
		gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },		this->backgr_color);

		gui::draw::quad_filled(
		{
			gui->render_space.x + (size.x + 2.) * .5 - (size.x - 2.) * .5 * this->val,
			(size.x == size.y ?
				gui->render_space.y + (size.y + 2.) * .5 - (size.y - 2.) * .5 * this->val :
				gui->render_space.y + 2.
			)
		},
		{
			gui->render_space.x + (size.x - 2.) * .5 + (size.x - 2.) * .5 * this->val,
			(size.x == size.y ?
				gui->render_space.y + (size.y - 2.) * .5 + (size.y - 2.) * .5 * this->val :
				gui->render_space.y + size.y - 2.
			)
		},
		{ 128. / 255., 147. / 255., 241. / 255., this->val });
	}

	auto txt_tmp = this->txt;

	auto txt_vec = gui::misc::text_vector(this->txt);

	auto shorted = false;

	for (;
		txt_vec.x >= size.x - 4.;
		txt_vec = gui::misc::text_vector((this->txt + "...")))
	{
		shorted = true;
		this->txt.pop_back();
	}

	if (shorted)
	{
		this->txt += "...";
	}

	if (this->txt == "-")
	{
		gui::draw::line_simple(
		{ 
			gui->render_space.x + 8.,
			gui->render_space.y + size.y * .5
		},
		{
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + size.y * .5
		},
		{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

		gui::draw::line_simple(
		{ 
			gui->render_space.x + 8.,
			gui->render_space.y + size.y * .5
		},
		{
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + size.y * .5
		},
		{ 1. - this->val, 1. - this->val, 1. - this->val, this->val });
	}

	if (this->txt == "x")
	{
		gui::draw::line_smooth(
		{ 
			gui->render_space.x + 8.,
			gui->render_space.y + 8.
		},
		{
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + size.y - 8.
		},
		{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

		gui::draw::line_smooth(
		{ 
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + 8.
		},
		{
			gui->render_space.x + 8.,
			gui->render_space.y + size.y - 8.
		},
		{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

		gui::draw::line_smooth(
		{ 
			gui->render_space.x + 8.,
			gui->render_space.y + 8.
		},
		{
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + size.y - 8.
		},
		{ 1. - this->val, 1. - this->val, 1. - this->val, this->val });

		gui::draw::line_smooth(
		{ 
			gui->render_space.x + size.x - 8.,
			gui->render_space.y + 8.
		},
		{
			gui->render_space.x + 8.,
			gui->render_space.y + size.y - 8.
		},
		{ 1. - this->val, 1. - this->val, 1. - this->val, this->val });
	}
	
	if (this->txt != "-" && this->txt != "x")
	{
		gui::draw::text_simple(this->txt,
		{ 
			gui->render_space.x + size.x * .5 - txt_vec.x * .5,
			gui->render_space.y + size.y * .5 + txt_vec.y * .25 + 1.
		},
		{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });
	
		gui::draw::text_simple(this->txt,
		{ 
			gui->render_space.x + size.x * .5 - txt_vec.x * .5,
			gui->render_space.y + size.y * .5 + txt_vec.y * .25 + 1.
		},
		{ 1. - this->val, 1. - this->val, 1. - this->val, this->val });
	}

	this->txt = txt_tmp;

	if (this->hide == 0)
	{
		gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },		this->border_color);
	}

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + 10.;

	return ret_value;
}