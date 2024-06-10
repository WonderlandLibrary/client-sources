#include "hooks.h"

#include "../latemod.h"

bool hooks::wgl_swap_buffers(HDC hdc)
{
	/* we're now hooked onto the swap buffers function, we can draw whatever we want :) */

	return cheat->swapBuffersHook->get_original()(hdc);
}
