#include "includes.hpp"

auto WinMain( _In_ HINSTANCE hInstance, _In_opt_ HINSTANCE hPrevInstance, _In_ LPSTR lpCmdLine, _In_ int nShowCmd ) -> int __stdcall
{
	/* -> threads <- */

	std::vector<LPVOID> threads
	{
		func.auto_clicker, func.hooks,
		util.bind, util.toggle
	};

	/* -> self destruct <- */

	std::atexit( func.self_destruct );

	/* -> thread pooling <- */

	for ( auto &funcs : threads )
		CreateThread( nullptr, 0, reinterpret_cast< LPTHREAD_START_ROUTINE >( funcs ), nullptr, 0, nullptr );

	/* -> clicker gui <- */

	if ( !ui.create( 200, 150 ) )
		return EXIT_FAILURE;

	return EXIT_SUCCESS;
}