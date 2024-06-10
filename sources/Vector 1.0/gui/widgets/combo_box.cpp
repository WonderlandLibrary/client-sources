
#include "../gui.hpp"

gui::widgets::combo_box::combo_box(void* menu_inst, std::string txt, const std::vector<bool>& items, const std::vector<std::string>& names, const __int8 behv)
{
	this->menu_inst = menu_inst, this->txt = txt, this->items = items, this->names = names, this->behv = behv;

	this->prvs.resize(this->items.size());
	this->vals.resize(this->items.size());

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::combo_box::~combo_box()
{

}

auto gui::widgets::combo_box::render(const gui::vec2 size) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	gui->render_space.y += gui::misc::get_text_height() + 2.;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space,
		{ this->fixed_space.x + size.x, this->fixed_space.y + size.y + (this->mode == 1 ? size.y * this->items.size() : 0. /*add if shown*/) });

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
			this->mode = (this->mode == 0 ? 1 : 0), this->prv = (this->mode == 1 ? 1. : 0.);
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

	for (unsigned __int64 x = 0, s = this->items.size(); x < s; ++x)
	{
		auto _hovered = gui::misc::is_inside(gui->mouse.position,
		{
			gui->render_space.x,
			gui->render_space.y + size.y + size.y * x * this->val
		},
		{
			gui->render_space.x + size.x,
			gui->render_space.y + size.y + size.y * (x + 1) * this->val
		});

		auto _clicked = gui::misc::is_inside(gui->click.position,
		{
			gui->render_space.x,
			gui->render_space.y + size.y + size.y * x * this->val
		},
		{
			gui->render_space.x + size.x,
			gui->render_space.y + size.y + size.y * (x + 1) * this->val
		});

		if (gui->mouse.active == 0 && _clicked == 1)
		{
			gui->click.position.x = -1;
			gui->click.position.y = -1;

			if (_hovered == 1)
			{
				this->items[x] = !this->items[x];
			
				if (this->behv == 1)
				{
					auto all_false = true;

					for (unsigned __int64 y = 0; y < s; ++y)
					{
						if (this->items[y])
						{
							all_false = false;
						}

						if (y != x)
						{
							this->items[y] = false;
						}
					}

					if (all_false)
					{
						this->items[x] = !this->items[x];
					}
				}
			}
		}

		if (this->items[x])
		{
			this->prvs[x] = (_clicked ? .7 : (_hovered ? .8 : .9));
		}
		else
		{
			this->prvs[x] = (_clicked ? .6 : (_hovered ? .5 : .4));
		}

		if (this->vals[x] > this->prvs[x])
		{
			gui::misc::limit(this->prvs[x], 0., 1.);

			this->vals[x] -= (this->vals[x] - this->prvs[x]) * .1 * gui->delay / 7.;

			gui::misc::limit(this->vals[x], 0., 1.);
		}

		if (this->vals[x] < this->prvs[x])
		{
			gui::misc::limit(this->prvs[x], 0., 1.);

			this->vals[x] += (this->prvs[x] - this->vals[x]) * .1 * gui->delay / 7.;

			gui::misc::limit(this->vals[x], 0., 1.);
		}

		gui::draw::quad_filled(
		{
			gui->render_space.x,
			gui->render_space.y + size.y + size.y * x * this->val
		},
		{
			gui->render_space.x + size.x,
			gui->render_space.y + size.y + size.y * (x + 1) * this->val
		},
		(x % 2 == 0 ? gui::vec4{ .12, .12, .12, 1. * this->val } : gui::vec4{ .1, .1, .1, 1. * this->val }) /*edit pair lines color darkness*/);

		auto sub_vec = gui::misc::text_vector(this->names[x]);

		gui::draw::text_simple(this->names[x],
		{
			gui->render_space.x + 4.,
			gui->render_space.y + size.y + (size.y * (x + 1) - size.y * .5 + sub_vec.y * .25) * this->val,
		},
		{ 128. / 255., 147. / 255., 241. / 255., this->vals[x] * this->val });

		if (x == s - 1)
			break;

		gui::draw::line_simple(
		{
			gui->render_space.x,
			gui->render_space.y + size.y + size.y * (x + 1) * this->val - 1.
		},
		{
			gui->render_space.x + size.x,
			gui->render_space.y + size.y + size.y * (x + 1) * this->val - 1.
		},
		{ this->border_color.x, this->border_color.y, this->border_color.z, this->border_color.a * this->val });
	}

	gui::draw::quad_simple(
	{ 
		gui->render_space.x,
		gui->render_space.y + size.y - 1.
	},
	{ 
		gui->render_space.x + size.x,
		gui->render_space.y + size.y + size.y * this->items.size() * this->val
	},
	{ this->border_color.x, this->border_color.y, this->border_color.z, this->border_color.a * this->val });

	auto txt_vec = gui::misc::text_vector(this->txt);

	gui::draw::text_simple(this->txt,
	{ 
		gui->render_space.x,
		gui->render_space.y - gui::misc::get_text_height() + txt_vec.y * .75 - 1.
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->backgr_color);

	gui::draw::quad_filled(
	{ 
		gui->render_space.x + size.x - 24.,
		gui->render_space.y
	},
	{
		gui->render_space.x + size.x,
		gui->render_space.y + size.y
	},
	{ .08, .08, .08, 1. });

	gui::draw::line_simple(
	{ 
		gui->render_space.x + size.x - 24.,
		gui->render_space.y
	},
	{
		gui->render_space.x + size.x - 24.,
		gui->render_space.y + size.y
	},
	this->border_color);

	gui::draw::line_simple(
	{ 
		gui->render_space.x + size.x - 19.,
		gui->render_space.y + size.y * .5 - 2.
	},
	{
		gui->render_space.x + size.x - 6.,
		gui->render_space.y + size.y * .5 - 2.
	},
	this->border_color);

	gui::draw::line_simple(
	{ 
		gui->render_space.x + size.x - 19.,
		gui->render_space.y + size.y * .5 + 2.
	},
	{
		gui->render_space.x + size.x - 6.,
		gui->render_space.y + size.y * .5 + 2.
	},
	this->border_color);

	gui::draw::line_simple(
	{ 
		gui->render_space.x + size.x - 19.,
		gui->render_space.y + size.y * .5 - 2.
	},
	{
		gui->render_space.x + size.x - 6.,
		gui->render_space.y + size.y * .5 - 2.
	},
	{ 128. / 255., 147. / 255., 241. / 255., this->val });

	gui::draw::line_simple(
	{ 
		gui->render_space.x + size.x - 19.,
		gui->render_space.y + size.y * .5 + 2.
	},
	{
		gui->render_space.x + size.x - 6.,
		gui->render_space.y + size.y * .5 + 2.
	},
	{ 128. / 255., 147. / 255., 241. / 255., this->val });

	std::string display_txt = "";

	for (unsigned __int64 x = 0, s = this->items.size(); x < s; ++x)
	{
		if (this->items[x] == true)
		{
			display_txt += this->names[x], display_txt += ", ";
		}
	}

	if (display_txt.length() != 0)
	{
		display_txt.pop_back();
		display_txt.pop_back();

		auto shorted = false;

		for (auto
			display_vec = gui::misc::text_vector(display_txt); 
			display_vec.x >= size.x - 28.;
			display_vec = gui::misc::text_vector((display_txt + "...")))
		{
			shorted = true;
			display_txt.pop_back();
		}

		if (shorted)
		{
			display_txt += "...";
		}
	}

	gui::draw::text_simple(display_txt,
	{
		gui->render_space.x + 4.,
		gui->render_space.y + size.y * .5 + txt_vec.y * .25,
	},
	{ this->border_color.x + .2, this->border_color.y + .2, this->border_color.z + .2, this->border_color.a });

	gui::draw::quad_simple(gui->render_space, { gui->render_space.x + size.x,		gui->render_space.y + size.y },			this->border_color);

	this->fixed_space = gui->render_space, gui->render_space.y += size.y + (size.y * this->items.size() * this->val) + 10.;
}