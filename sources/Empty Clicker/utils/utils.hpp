#pragma once

class utils
{
private:
	auto bind_map( size_t ) -> std::string;
	auto bind_loop( int, bool, std::string ) -> std::tuple<int, bool, std::string>;

public:
	static auto toggle( ) -> void;
	static auto bind( ) -> void;

};

inline auto util = utils( );