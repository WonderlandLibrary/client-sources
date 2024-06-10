
#include "misc/misc.hpp"

namespace gui::widgets
{
	struct button
	{
		button(void* menu_inst, std::string txt, void* value_ptr = nullptr, const __int8 behv = 0, const __int8 hide = 0);
		~
		button();

		void* menu_inst = nullptr;

		std::string txt = "";

		void* value_ptr = nullptr;

		__int8 behv = 0;
		__int8 hide = 0;
		
		__int8 bind = 0;
		__int16 key = 0;
		
		__int8 held = 0;
		__int8 updt = 0;

		double prv = 0.;
		double val = 0.;
		
		__int8 mode = 0;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 250., 21. }) -> bool;
	};

	struct check_box
	{
		check_box(void* menu_inst, std::string txt, void* value_ptr, const __int8 inpt = 1);
		~
		check_box();

		void* menu_inst = nullptr;

		std::string txt = "";

		void* value_ptr = nullptr;

		__int8 inpt = 1;

		double prv = 0.;
		double val = 0.;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 22., 22. }) -> void;
	};

	struct color_picker
	{
		color_picker(void* menu_inst, std::string txt, void* value_ptr);
		~
		color_picker();

		void* menu_inst = nullptr;

		std::string txt = "";

		void* value_ptr = nullptr;

		double prv = 0.;
		double val = 0.;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 35., 22. }) -> void;
	};

	struct combo_box
	{
		combo_box(void* menu_inst, std::string txt, const std::vector<bool>& items, const std::vector<std::string>& names, const __int8 behv = 0);
		~
		combo_box();

		void* menu_inst = nullptr;

		std::string txt = "";

		std::vector<bool> items;
		std::vector<std::string> names;

		std::vector<double> prvs;
		std::vector<double> vals;

		__int8 behv = 0;

		double prv = 0.;
		double val = 0.;

		__int8 mode = 0;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;

				for (unsigned __int64 x = 0, s = this->vals.size(); x < s; ++x)
					this->vals[x] = 0.;
			}
		}

		auto render(const gui::vec2 size = { 250., 22. }) -> void;
	};

	struct input_text
	{
		input_text(void* menu_inst, std::string txt);
		~
		input_text();

		void* menu_inst = nullptr;

		std::string txt = "";

		std::string input = "";

		__int8 inpt = 0;

		double prv = 0.;
		double val = 0.;
		
		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 250., 22. }) -> void;
	};

	struct nav_tab
	{
		nav_tab(void* menu_inst, void* ptr, const std::vector<std::string> names);
		~
		nav_tab();

		void* menu_inst = nullptr;

		void* value_ptr = nullptr;

		std::vector<std::string> names;

		double prv = 0.;
		double val = 0.;

		double prv_pos = 0.;
		double val_pos = 0.;

		std::vector<double> prvs;
		std::vector<double> vals;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 250., 500. }, const double size_y = 40.) -> void;
	};

	struct scroll
	{
		scroll(void* menu_inst);
		~
		scroll();

		void* menu_inst = nullptr;

		double lst = 0.;
		double prv = 0.;
		double val = 0.;

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto handle(gui::vec2 x, gui::vec2 y) -> void;
	};

	struct slider
	{
		slider(void* menu_inst, std::string txt, void* ptr, const double min, const double max, const __int8 inpt = 1);
		~
		slider();

		void* menu_inst = nullptr;

		std::string txt = "";

		void* value_ptr = nullptr;

		double min = 0.;
		double max = 0.;

		__int8 inpt = 1;

		double prv = 0.;
		double val = 0.;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 250., 20. }) -> void;
	};

	struct title_bar
	{
		title_bar(void* menu_inst, std::string txt, std::string sub);
		~
		title_bar();

		void* menu_inst = nullptr;

		std::string txt = "";
		std::string sub = "";

		__int32 index = 0 /*wid*/;

		gui::widgets::button* mini;
		gui::widgets::button* exit;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->mini->val = 0.;
				this->exit->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 750., 40. }) -> void;
	};

	struct toggle_button
	{
		toggle_button(void* menu_inst, void* ptr);
		~
		toggle_button();

		void* menu_inst = nullptr;

		void* value_ptr = nullptr;

		double prv = 0.;
		double val = 0.;

		__int32 index = 0 /*wid*/;

		gui::vec2 fixed_space = { DBL_MIN, DBL_MIN };

		gui::vec4 backgr_color = { .14, .14, .14, 1. };
		gui::vec4 border_color = { .35, .35, .35, 1. };

		auto reset(bool reset) -> void
		{
			if (reset)
			{
				this->val = 0.;
			}
		}

		auto render(const gui::vec2 size = { 150., 21. }) -> void;
	};
}

typedef struct GLFWwindow GLFW__;

namespace gui::glfw
{
	extern auto get_position(GLFW__* glfw) -> gui::vec2;

	extern auto set_position(GLFW__* glfw, const gui::vec2 pos) -> void;

	extern auto set_interval(const __int32 interval) -> void;
}

namespace gui::misc
{
	extern auto is_inside(const gui::vec2 point, const gui::vec2 x, const gui::vec2 y) -> __int8;

	extern auto get_baked() -> void*;

	extern auto get_text_height() -> double;

	extern auto text_vector(std::string txt) -> gui::vec2;
}

namespace gui::draw
{
	extern auto line_simple(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void;
	extern auto line_smooth(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void;

	extern auto quad_simple(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void;
	extern auto quad_filled(const gui::vec2 x, const gui::vec2 y, const gui::vec4 color) -> void;

	extern auto text_simple(std::string txt, const gui::vec2 pos, const gui::vec4 color) -> void;

	struct line
	{
		bool smooth = false;

		gui::vec2 x;
		gui::vec2 y;
		gui::vec4 col;

		auto render() -> void
		{
			(this->smooth ?
				gui::draw::line_smooth(this->x, this->y, this->col) :
				gui::draw::line_simple(this->x, this->y, this->col)
			);
		}
	};

	struct quad
	{
		bool filled = false;

		gui::vec2 x;
		gui::vec2 y;
		gui::vec4 col;

		auto render() -> void
		{
			(this->filled ?
				gui::draw::quad_filled(this->x, this->y, this->col) :
				gui::draw::quad_simple(this->x, this->y, this->col)
			);
		}
	};

	struct text
	{
		std::string txt = "";

		gui::vec2 pos;
		gui::vec4 col;

		auto render() -> void
		{
			gui::draw::text_simple(this->txt, this->pos, this->col);
		}
	};

	struct render_item
	{
		gui::draw::line line;
		gui::draw::quad quad;
		gui::draw::text text;

		__int8 type = 0;
	};

	inline auto buffering = false;

	inline std::vector<gui::draw::render_item> buffered_items;

	extern auto render_buffered_items(/*render buffered items from gui::ui::new_frame*/) -> void;
}