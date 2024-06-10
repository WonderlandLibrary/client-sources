#pragma once

class funcs 
{
private:
	HHOOK mouse_hook {};

	std::mt19937 mersenne { static_cast< std::mt19937::result_type >( std::time( nullptr ) ) };
	static auto hook_callback( int nCode, WPARAM wParam, LPARAM lParam ) -> LRESULT __stdcall;

	auto cps_picker( int min, int max ) -> int;

public:
	static auto hooks( ) -> void;
	static auto auto_clicker( ) -> void;
	static auto self_destruct( ) -> void;
};

inline auto func = funcs( );