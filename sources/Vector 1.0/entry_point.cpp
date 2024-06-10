
#include "vector/gui/gui.hpp"

auto main(void) -> __int32
{
	FreeConsole();

	for (gui::ui menu("menu"); menu.new_frame() == 1;)
	{

	}

	return EXIT_SUCCESS;
}