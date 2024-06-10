
#include "internal/internal.hpp"

namespace gui
{
	struct ui
	{
		ui(const char* name = "");
		~
		ui();
	
		GLFW__* glfw = nullptr;
		HWND__* hwnd = nullptr;

		__int8 active = 0;

		double delay = 0.;

		__int32 index = 0;
		__int32 count = 0;
		
		gui::vec2 render_space;

		gui::cursor mouse;
		gui::cursor click;
		gui::cursor last_mouse;

		static inline
			WNDPROC proc = nullptr;

		static inline char u_input;

		static auto hwnd_proc(
			HWND__* hwnd,
			UINT msg,
			WPARAM w_param,
			LPARAM l_param
		) -> __int64 __stdcall;

		static inline gui::vec2 sc;

		static auto hook(
			GLFW__* glfw,
			const double x,
			const double y
		) -> void;

		std::vector<
		gui::widgets::button*> key;

		auto new_frame() -> __int8;
		auto draw_list() -> __int8;
	};
}