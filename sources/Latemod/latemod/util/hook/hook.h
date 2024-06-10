#pragma once

#include "MinHook.h"

/* make life easier */

template <typename T>
struct hook_t {
	void* original;
	void* target;
	void* stub;

	hook_t(void* target, void* stub) : target(target), stub(stub) {
		MH_CreateHook(this->target, this->stub, &this->original);
	}

	void disable()
	{
		MH_DisableHook(this->target);
	}

	void enable()
	{
		MH_EnableHook(this->target);
	}

	T get_original()
	{
		return reinterpret_cast<T>(this->original);
	}
};