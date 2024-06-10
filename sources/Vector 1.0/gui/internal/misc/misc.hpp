
#include "../../../../helper/sdk/sdk.hpp"

namespace gui
{
	struct vec2
	{
		double x = 0.;
		double y = 0.;
	};

	struct vec3
	{
		double x = 0.;
		double y = 0.;
		double z = 0.;
	};

	struct vec4
	{
		double x = 0.;
		double y = 0.;
		double z = 0.;
		double a = 0.;

		auto decrease(double val)
		{
			this->x -= val;
			this->y -= val;
			this->z -= val;
		}

		auto increase(double val)
		{
			this->x += val;
			this->y += val;
			this->z += val;
		}
	};

	struct cursor
	{
		gui::vec2 position;
		__int32 active = 0;
	};

	namespace misc
	{
		__forceinline auto limit(double& val, const double min, const double max) -> void
		{
			if (val < min)
				val = min;

			if (val > max)
				val = max;
		}
	}
}