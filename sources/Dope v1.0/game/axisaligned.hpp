#pragma once
#include "../../vendors/singleton.h"
#include <memory>
#include <optional>
#include <vector>

struct axisalignedbb {
	double minX, minY, minZ,
		maxX, maxY, maxZ;
};

class c_jvm;
class c_axisaligned
{
private:
	void* obj;
public:
	c_axisaligned(void* obj) {
		this->obj = obj;
	}

	auto get_axisaligned_obj() {
		return this->obj;
	}
public:
	axisalignedbb get_bounding_box();
	void set_bounding_box(axisalignedbb v);
};