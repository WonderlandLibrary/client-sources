
#include "../gui.hpp"

gui::widgets::nav_tab::nav_tab(void* menu_inst, void* ptr, const std::vector<std::string> names)
{
	this->menu_inst = menu_inst, this->value_ptr = ptr, this->names = names;

	this->prvs.resize(this->names.size());
	this->vals.resize(this->names.size());

	this->index = ++((gui::ui*)this->menu_inst)->count;
}

gui::widgets::nav_tab::~nav_tab()
{

}

auto gui::widgets::nav_tab::render(const gui::vec2 size, const double size_y) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	auto resize = 40. + (size.x - 40.) * this->val;

	auto hovered = gui::misc::is_inside(gui->mouse.position, this->fixed_space, { this->fixed_space.x + resize, this->fixed_space.y + size.y });

	if (gui->index == this->index && gui->mouse.active == 0 && hovered == 0)
	{
		gui->index = -1;

		gui->click.position.x = -1;
		gui->click.position.y = -1;
	}

	if (gui->index == 0 && hovered == 1)
		gui->index = this->index;

	this->prv = (gui->index == this->index ? 1. : 0.);

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

	//if (this->backgr_color.x < (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
	//	this->backgr_color.increase(.00125 * gui->delay / 7.);

	//if (this->backgr_color.x > (gui->index == this->index ? .1 : (hovered == 1 ? .12 : .14)))
	//	this->backgr_color.decrease(.00125 * gui->delay / 7.);

	gui::draw::quad_filled(gui->render_space, { gui->render_space.x + resize,		gui->render_space.y + size.y },			this->backgr_color);

	if (gui->index == this->index)
	{
		if (gui->mouse.active != 0 && gui->mouse.position.y < size_y + size_y * this->names.size())
		{
			*(__int32*)this->value_ptr = (gui->mouse.position.y - gui->render_space.y) / size_y;
		}
		else
		{
			*(__int32*)this->value_ptr -= gui::ui::sc.y;
		}
	
		if (*(__int32*)this->value_ptr < 0)
			*(__int32*)this->value_ptr = 0;

		if (*(__int32*)this->value_ptr > this->names.size() - 1)
			*(__int32*)this->value_ptr = this->names.size() - 1;
	}

	for (unsigned __int64 x = 0, s = this->names.size(); x < s; ++x)
	{
		auto _hovered = gui::misc::is_inside(gui->mouse.position,
		{
			gui->render_space.x,
			gui->render_space.y + size_y * x
		},
		{
			gui->render_space.x + size.x,
			gui->render_space.y + size_y * (x + 1)
		});

		if (*(__int32*)this->value_ptr == x)
		{
			this->prvs[x] = (_hovered ? .7 : .9);
		}
		else
		{
			this->prvs[x] = (_hovered ? .6 : .4);
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
			gui->render_space.y + size_y * x
		},
		{
			gui->render_space.x + resize,
			gui->render_space.y + size_y * (x + 1)
		},
		{ .12, .12, .12, 1. });
	
		auto txt_vec = gui::misc::text_vector(this->names[x]);

		gui::draw::text_simple(this->names[x],
		{ 
			gui->render_space.x + resize * .5 - txt_vec.x * .5,
			gui->render_space.y + (size_y * x) + size_y * .5 + txt_vec.y * .25
		},
		{ 
			this->border_color.x + .2,
			this->border_color.y + .2,
			this->border_color.z + .2,
			this->border_color.a * this->val * this->vals[x]
		});

		gui::draw::line_simple(
		{
			gui->render_space.x,
			gui->render_space.y + size_y * (x + 1) - 1.
		},
		{
			gui->render_space.x + resize,
			gui->render_space.y + size_y * (x + 1) - 1.
		},
		{ this->border_color.x, this->border_color.y, this->border_color.z, this->border_color.a });
	}

	this->prv_pos = size_y * (*(__int32*)this->value_ptr);

	if (this->val_pos > this->prv_pos)
	{
		gui::misc::limit(this->prv_pos, 0., this->names.size() * size_y);

		this->val_pos -= (this->val_pos - this->prv_pos) * .1 * gui->delay / 7.;

		gui::misc::limit(this->val_pos, 0., this->names.size()* size_y);
	}

	if (this->val_pos < this->prv_pos)
	{
		gui::misc::limit(this->prv_pos, 0., this->names.size() * size_y);

		this->val_pos += (this->prv_pos - this->val_pos) * .1 * gui->delay / 7.;

		gui::misc::limit(this->val_pos, 0., this->names.size()* size_y);
	}

	this->val_pos = round(this->val_pos * 10000.) / 10000.;

	gui::draw::quad_filled(
	{
		gui->render_space.x + 7.,
		gui->render_space.y + this->val_pos + 7.
	},
	{
		gui->render_space.x + 9.,
		gui->render_space.y + this->val_pos + size_y - 7.
	},
	{ 128. / 255., 147. / 255., 241. / 255., this->vals[*(__int32*)this->value_ptr] * this->val });

	gui::draw::line_simple(
	{ gui->render_space.x + resize, gui->render_space.y },
	{ gui->render_space.x + resize, gui->render_space.y + size.y },	this->border_color);

	this->fixed_space = gui->render_space, gui->render_space = { gui->render_space.x + resize + 20., gui->render_space.y + 20. };
}