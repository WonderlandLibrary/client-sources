#include "..\includes.hpp"

auto utils::bind_map( size_t id ) -> std::string
{
	static std::unordered_map<int, std::string> key_names
	{
		{ 0, "None" },
		{ VK_LBUTTON, "Mouse 1" },
		{ VK_RBUTTON, "Mouse 2" },
		{ VK_MBUTTON, "Mouse 3" },
		{ VK_XBUTTON1, "Mouse 4" },
		{ VK_XBUTTON2, "Mouse 5" },
		{ VK_BACK, "Back" },
		{ VK_TAB, "Tab" },
		{ VK_CLEAR, "Clear" },
		{ VK_RETURN, "Enter" },
		{ VK_SHIFT, "Shift" },
		{ VK_CONTROL, "Ctrl" },
		{ VK_MENU, "Alt" },
		{ VK_PAUSE, "Pause" },
		{ VK_CAPITAL, "Caps Lock" },
		{ VK_ESCAPE, "Escape" },
		{ VK_SPACE, "Space" },
		{ VK_PRIOR, "Page Up" },
		{ VK_NEXT, "Page Down" },
		{ VK_END, "End" },
		{ VK_HOME, "Home" },
		{ VK_LEFT, "Left Key" },
		{ VK_UP, "Up Key" },
		{ VK_RIGHT, "Right Key" },
		{ VK_DOWN, "Down Key" },
		{ VK_SELECT, "Select" },
		{ VK_PRINT, "Print Screen" },
		{ VK_INSERT, "Insert" },
		{ VK_DELETE, "Delete" },
		{ VK_HELP, "Help" },
		{ VK_SLEEP, "Sleep" },
		{ VK_MULTIPLY, "*" },
		{ VK_ADD, "+" },
		{ VK_SUBTRACT, "-" },
		{ VK_DECIMAL, "." },
		{ VK_DIVIDE, "/" },
		{ VK_NUMLOCK, "Num Lock" },
		{ VK_SCROLL, "Scroll" },
		{ VK_LSHIFT, "Left Shift" },
		{ VK_RSHIFT, "Right Shift" },
		{ VK_LCONTROL, "Left Ctrl" },
		{ VK_RCONTROL, "Right Ctrl" },
		{ VK_LMENU, "Left Alt" },
		{ VK_RMENU, "Right Alt" },
		{ VK_OEM_3, "Grave" },
	};

	if ( id >= 0x30 && id <= 0x5A )
		return std::string( 1, static_cast< char >( id ) );

	if ( id >= 0x60 && id <= 0x69 )
		return "Num " + std::to_string( id - 0x60 );

	if ( id >= 0x70 && id <= 0x87 )
		return "F" + std::to_string( ( id - 0x70 ) + 1 );

	return key_names[ id ];
}

auto utils::bind_loop( int key, bool enable, std::string key_str ) -> std::tuple<int, bool, std::string>
{
	do
	{
		for ( auto i = 1; i < 256; i++ )
		{
			if ( !( GetAsyncKeyState( i ) & 0x8000 && ( i != 12 ) ) ) continue;

			key = i == VK_ESCAPE ? 0 : i;
			( key == 0 ) ? key_str = "Not Bound" : key_str = bind_map( i );
			enable = false;
		}

		std::this_thread::sleep_for( std::chrono::milliseconds( delay ) );
	} while ( enable );

	return std::make_tuple( key, enable, key_str );
}

auto utils::bind( ) -> void
{
	do
	{
		if ( vars.pressed_bind_left )
			std::tie( vars.bind_key_left, vars.pressed_bind_left, vars.key_left ) = util.bind_loop( vars.bind_key_left, vars.pressed_bind_left, vars.key_left );

		if ( vars.pressed_bind_right )
			std::tie( vars.bind_key_right, vars.pressed_bind_right, vars.key_right ) = util.bind_loop( vars.bind_key_right, vars.pressed_bind_right, vars.key_right );

		std::this_thread::sleep_for( delay );
	} while ( true );
}

auto utils::toggle( ) -> void
{
	do
	{
		/* -> left clicker toggle <- */

		if ( GetKeyState( vars.bind_key_left ) & 0x8000 && !vars.left_turn_on )
			vars.left_turn_on = true;

		if ( !( GetKeyState( vars.bind_key_left ) & 0x8000 ) && vars.left_turn_on )
			vars.left_clicker = !vars.left_clicker, vars.left_turn_on = false;

		/* -> right clicker toggle <- */

		if ( GetKeyState( vars.bind_key_right ) & 0x8000 && !vars.right_turn_on )
			vars.right_turn_on = true;

		if ( !( GetKeyState( vars.bind_key_right ) & 0x8000 ) && vars.right_turn_on )
			vars.right_clicker = !vars.right_clicker, vars.right_turn_on = false;

		std::this_thread::sleep_for( delay );
	} while ( true );
}
