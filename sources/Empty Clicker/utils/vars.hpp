#pragma once

struct variables 
{
	bool left_clicker, right_clicker, hide_window;
	bool is_left_press, is_right_press, is_hide_pressed;
	bool pressed_bind_left, pressed_bind_right, pressed_hide_window;
	bool left_turn_on, right_turn_on, right_tab;

	bool pressed_to_work_l, pressed_to_work_r;
	int left_value = 1, right_value = 1;

	int bind_key_left, bind_key_right;
	std::string key_left = "Not Bound", key_right = "Not Bound";
};

inline auto vars = variables( );