
#include "../gui.hpp"

gui::widgets::scroll::scroll(void* menu_inst)
{
	this->menu_inst = menu_inst;
}

gui::widgets::scroll::~scroll()
{

}

auto gui::widgets::scroll::handle(gui::vec2 x, gui::vec2 y) -> void
{
	auto gui = (gui::ui*)this->menu_inst;

	if (gui::misc::is_inside(gui->mouse.position, x, y) == 1)
		this->prv += gui::ui::sc.y * 100.;

	if (this->prv > 0.)
		this->prv = 0.;

	if (this->val > this->prv)
	{
		this->val -= (this->val - this->prv) * .1 * gui->delay / 7.;
	}

	if (this->val < this->prv)
	{
		this->val += (this->prv - this->val) * .1 * gui->delay / 7.;
	}

	this->val = round(this->val * 10000.) / 10000.;

	gui->render_space.y += (double)(__int32)this->val;
}