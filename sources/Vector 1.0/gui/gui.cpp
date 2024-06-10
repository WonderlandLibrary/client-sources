
#include "gui.hpp"

gui::ui::ui(const char* name)
{
	if (glfwInit() != GLFW_TRUE)
		return;

	glfwWindowHint(GLFW_DECORATED, GL_FALSE /*remove default bar*/);

	glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE /*disable resizing*/);

	this->glfw = glfwCreateWindow(750, 800, name, nullptr, nullptr);

	if (this->glfw == nullptr)
		return;

	this->hwnd = glfwGetWin32Window(this->glfw /*get native hwnd*/);

	if (this->hwnd == nullptr)
		return;

	gui::ui::proc = (WNDPROC)SetWindowLongPtrA(
		this->hwnd, -4, (LONG_PTR)gui::ui::hwnd_proc /*proc hook*/);

	tagRECT rect;

	GetWindowRect(GetDesktopWindow(), &rect);

	glfwSetWindowPos(this->glfw,
		rect.right / 2 - 375,
		rect.bottom / 2 - 400 /*window position in screen center*/);

	glfwMakeContextCurrent(this->glfw), glfwSwapInterval(1 /*vsc*/);

	glfwSetScrollCallback(this->glfw, gui::ui::hook /*scroll clb*/);
}

gui::ui::~ui()
{
	glfwDestroyWindow(this->glfw), glfwTerminate(/*delete window*/);
}

auto gui::ui::hwnd_proc(HWND__* hwnd,
	UINT u_msg, WPARAM w_param, LPARAM l_param) -> __int64 __stdcall
{
	if (u_msg == WM_CHAR)
	{
		gui::ui::u_input = (char)w_param;
	}

	return CallWindowProcA(
		gui::ui::proc, hwnd, u_msg, w_param, l_param /*call proc*/);
}

auto gui::ui::hook(
	GLFW__* glfw, const double x, const double y /*scroll*/) -> void
{
	gui::ui::sc = { x, y };
}

auto gui::ui::new_frame() -> __int8
{
	if (glfwWindowShouldClose(this->glfw))
		return 0;

	glfwPollEvents(/*handle prv events*/);

	static auto set_ortho = []() -> __int8
	{
		glLoadIdentity();
		glOrtho(0., 750., 800., 0., 0., 1. /*set menu projection*/);
		return 0;
	};

	static auto set_on_init = set_ortho();

	glClearColor(.07, .07, .07, 1. /*set the ui background color*/);

	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT /*ui color*/);

	//pre render
	{
		this->active = this->hwnd == GetForegroundWindow(/*focus*/);

		static auto p_0 = std::chrono::high_resolution_clock::now();
		static auto p_1 = std::chrono::high_resolution_clock::now();

		p_1 = std::chrono::high_resolution_clock::now();

		this->delay = (std::chrono::duration_cast
			<std::chrono::microseconds>(p_1 - p_0).count() / 1000.);

		if (this->delay > 100.)
			this->delay = 7.;

		p_0 = std::chrono::high_resolution_clock::now();

		if (this->index < 0)
			this->index = 0;

		this->render_space.x = 0.;
		this->render_space.y = 0. /*reset initial render position*/;

		tagPOINT pos = { -1., -1. };

		if (this->active)
		{
			GetCursorPos(&pos), ScreenToClient(this->hwnd, &pos);
		}

		this->mouse.position.x = pos.x;
		this->mouse.position.y = pos.y;

		this->mouse.active =
			GetAsyncKeyState(VK_LBUTTON) & 0x8000 /*l_button down*/;

		if (this->mouse.active != 0 && this->last_mouse.active == 0)
			this->click = this->mouse;

		this->last_mouse = this->mouse /*save previous mouse data*/;
	}

	this->draw_list(/*render*/);

	//post render
	{
		gui::draw::render_buffered_items(/*buffered items on top*/);

		auto code = -1;

		for (auto x = 7; x < 256; ++x /*find the pressed key code*/)
		{
			if (GetAsyncKeyState(x) & 0x8000)
				code = x;
		}

		for (const auto& button : this->key /*handle button binds*/)
		{
			if (button->value_ptr == nullptr)
				continue;

			if (button->held == 1 && button->key != code)
			{
				button->held = 0;

				if (button->updt == 1)
				{
					button->updt = 0;
				}
				else
				{
					*(bool*)button->value_ptr
						= !*(bool*)button->value_ptr;
				}

				continue;
			}

			if (code != -1 && button->key == code /*change status*/)
				button->held = true;
		}

		gui::ui::u_input = -1;

		gui::ui::sc.x = 0., gui::ui::sc.y = 0.;
	}

	glfwMakeContextCurrent(this->glfw), glfwSwapBuffers(this->glfw);

	return 1;
}