#include "fullbright.hpp"
#include "../../game/gamesettings.hpp"
#include "../../game/minecraft.hpp"

#include "../../game/jvm/jvm_base.hpp"

#include "../settings.hpp"

void modules::fullbright::thread()
{
	float real_gamma = 0.f;
	while (!settings->destruct) {
		if (GetAsyncKeyState(settings->visuals.fullbright.bind) & 0x8000) {
			settings->visuals.fullbright.enabled = !settings->visuals.fullbright.enabled;
			Sleep(200);
		}

		auto mc = std::make_unique<c_minecraft>();
		auto gamesettings = std::make_unique<c_gamesettings>(mc->get_gamesettings_obj());
		if (!gamesettings)
		{
			Sleep(10);
			continue;
		}

		float cur_gamma = gamesettings->get_gamma();
		if (cur_gamma != 1000.f)
			real_gamma = cur_gamma;

		if (!settings->visuals.fullbright.enabled && cur_gamma != real_gamma) {
			gamesettings->set_gamma(real_gamma);
		}
		else if (settings->visuals.fullbright.enabled)
		{
			gamesettings->set_gamma(1000.f);
		}

		Sleep(1);
	}
}
