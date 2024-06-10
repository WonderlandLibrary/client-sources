#include "configmaker.hpp"
#include "settings.hpp"

#include "../utilities/requests.hpp"

#include "../../vendors/json.h"
#include  "../../vendors/xor.h"

std::string ConfigMaker::CreateData(std::string name)
{
	nlohmann::json j;

#pragma region json_struct
	j["name"] = name.data();
	j["id"] = "-1";

	/**/
	j["struct"]["combat"]["velocity"]["enabled"] = settings->combat.velocity.enabled;
	j["struct"]["combat"]["velocity"]["bind"] = settings->combat.velocity.bind;
	j["struct"]["combat"]["velocity"]["horizontal"] = settings->combat.velocity.horizontal;
	j["struct"]["combat"]["velocity"]["vertical"] = settings->combat.velocity.vertical;
	j["struct"]["combat"]["velocity"]["chance"] = settings->combat.velocity.chance;
	j["struct"]["combat"]["velocity"]["delay"] = settings->combat.velocity.delay;
	j["struct"]["combat"]["velocity"]["onlyweapon"] = settings->combat.velocity.onlyweapon;
	j["struct"]["combat"]["velocity"]["aironly"] = settings->combat.velocity.aironly;
	j["struct"]["combat"]["velocity"]["moving"] = settings->combat.velocity.moving;

	j["struct"]["combat"]["leftclicker"]["enabled"] = settings->combat.leftclicker.enabled;
	j["struct"]["combat"]["leftclicker"]["bind"] = settings->combat.leftclicker.bind;
	j["struct"]["combat"]["leftclicker"]["avg"] = settings->combat.leftclicker.avg;
	j["struct"]["combat"]["leftclicker"]["breakBlock"] = settings->combat.leftclicker.breakBlock;
	j["struct"]["combat"]["leftclicker"]["onlyWeapon"] = settings->combat.leftclicker.onlyWeapon;
	j["struct"]["combat"]["leftclicker"]["inInventory"] = settings->combat.leftclicker.inInventory;

	j["struct"]["combat"]["aimassist"]["enabled"] = settings->combat.aimassist.enabled;
	j["struct"]["combat"]["aimassist"]["bind"] = settings->combat.aimassist.bind;
	j["struct"]["combat"]["aimassist"]["distance"] = settings->combat.aimassist.distance;
	j["struct"]["combat"]["aimassist"]["fov"] = settings->combat.aimassist.fov;
	j["struct"]["combat"]["aimassist"]["speed"] = settings->combat.aimassist.speed;
	j["struct"]["combat"]["aimassist"]["onlyweapon"] = settings->combat.aimassist.onlyweapon;
	j["struct"]["combat"]["aimassist"]["mousemove"] = settings->combat.aimassist.mousemove;
	j["struct"]["combat"]["aimassist"]["onlyclicking"] = settings->combat.aimassist.onlyclicking;
	j["struct"]["combat"]["aimassist"]["antibot"] = settings->combat.aimassist.antibot;

	j["struct"]["combat"]["reach"]["enabled"] = settings->combat.reach.enabled;
	j["struct"]["combat"]["reach"]["bind"] = settings->combat.reach.bind;
	j["struct"]["combat"]["reach"]["value"] = settings->combat.reach.value;
	j["struct"]["combat"]["reach"]["hitbox"] = settings->combat.reach.hitbox;
	j["struct"]["combat"]["reach"]["onlyweapon"] = settings->combat.reach.onlyweapon;
	j["struct"]["combat"]["reach"]["onground"] = settings->combat.reach.onground;
	j["struct"]["combat"]["reach"]["liquidcheck"] = settings->combat.reach.liquidcheck;

	/**/
	j["struct"]["movement"]["timer"]["enabled"] = settings->movement.timer.enabled;
	j["struct"]["movement"]["timer"]["bind"] = settings->movement.timer.bind;
	j["struct"]["movement"]["timer"]["value"] = settings->movement.timer.value;
	j["struct"]["movement"]["timer"]["moving"] = settings->movement.timer.moving;
	j["struct"]["movement"]["timer"]["onlyweapon"] = settings->movement.timer.onlyweapon;

	j["struct"]["movement"]["bhop"]["enabled"] = settings->movement.bhop.enabled;
	j["struct"]["movement"]["bhop"]["bind"] = settings->movement.bhop.bind;
	j["struct"]["movement"]["bhop"]["power"] = settings->movement.bhop.power;
	j["struct"]["movement"]["bhop"]["liquidCheck"] = settings->movement.bhop.liquidCheck;


	/**/
	j["struct"]["visuals"]["fullbright"]["enabled"] = settings->visuals.fullbright.enabled;
	j["struct"]["visuals"]["fullbright"]["bind"] = settings->visuals.fullbright.bind;

	/**/
	j["struct"]["settings"]["hide"]["enabled"] = settings->settings.hide.enabled;
	j["struct"]["settings"]["hide"]["bind"] = settings->settings.hide.bind;

	j["struct"]["settings"]["streamproof"]["enabled"] = settings->settings.streamproof.enabled;
	j["struct"]["settings"]["streamproof"]["bind"] = settings->settings.streamproof.bind;


	/**/
	j["struct"]["misc"]["throwpot"]["enabled"] = settings->misc.throwpot.enabled;
	j["struct"]["misc"]["throwpot"]["bind"] = settings->misc.throwpot.bind;
	j["struct"]["misc"]["throwpot"]["switchDelay"] = settings->misc.throwpot.switchDelay;
	j["struct"]["misc"]["throwpot"]["throwDelay"] = settings->misc.throwpot.throwDelay;
	j["struct"]["misc"]["throwpot"]["mode"]["mode_selected"] = settings->misc.throwpot.mode.mode_selected;

#pragma endregion
	return j.dump();
}

void ConfigMaker::Create(std::string data)
{
	char* szQuery = new char[data.size() + 126];
	sprintf(szQuery, _("action=create&creator_id=%s&data=%s"), std::to_string(41282).data(), data.data());

	RequestHelper::get().send_request(_("http://193.26.14.16/cloud_configs/"), szQuery, strlen(szQuery));
	delete[] szQuery;
}

void ConfigMaker::Delete(int ID)
{
	char* szQuery = new char[256];
	sprintf(szQuery, _("action=delete&creator_id=%s&config_id=%s"), std::to_string(41282).data(), std::to_string(ID).data());

	RequestHelper::get().send_request(_("http://193.26.14.16/cloud_configs/"), szQuery, strlen(szQuery));
	delete[] szQuery;
}

void ConfigMaker::Load(int ID, std::vector<std::string>& out)
{
#pragma region json_struct
	std::string askedConfig;
	for (const auto& config : out)
	{
		nlohmann::json j = nlohmann::json::parse(config);
		if (j["id"] == std::to_string(ID))
		{
			askedConfig = config;
		}
	}

	nlohmann::json j = nlohmann::json::parse(askedConfig);

	/**/
	if (!j["struct"]["combat"]["velocity"]["enabled"].is_null())
		settings->combat.velocity.enabled = j["struct"]["combat"]["velocity"]["enabled"].get<bool>();

	if (!j["struct"]["combat"]["velocity"]["bind"].is_null())
		settings->combat.velocity.bind = j["struct"]["combat"]["velocity"]["bind"].get<int>();

	if (!j["struct"]["combat"]["velocity"]["horizontal"].is_null())
		settings->combat.velocity.horizontal = j["struct"]["combat"]["velocity"]["horizontal"].get<double>();

	if (!j["struct"]["combat"]["velocity"]["vertical"].is_null())
		settings->combat.velocity.vertical = j["struct"]["combat"]["velocity"]["vertical"].get<double>();

	if (!j["struct"]["combat"]["velocity"]["chance"].is_null())
		settings->combat.velocity.chance = j["struct"]["combat"]["velocity"]["chance"].get<int>();

	if (!j["struct"]["combat"]["velocity"]["delay"].is_null())
		settings->combat.velocity.delay = j["struct"]["combat"]["velocity"]["delay"].get<int>();

	if (!j["struct"]["combat"]["velocity"]["onlyweapon"].is_null())
		settings->combat.velocity.onlyweapon = j["struct"]["combat"]["velocity"]["onlyweapon"].get<bool>();

	if (!j["struct"]["combat"]["velocity"]["moving"].is_null())
		settings->combat.velocity.moving = j["struct"]["combat"]["velocity"]["moving"].get<bool>();

	/**/
	if (!j["struct"]["combat"]["leftclicker"]["enabled"].is_null())
		settings->combat.leftclicker.enabled = j["struct"]["combat"]["leftclicker"]["enabled"].get<bool>();

	if (!j["struct"]["combat"]["leftclicker"]["bind"].is_null())
		settings->combat.leftclicker.bind = j["struct"]["combat"]["leftclicker"]["bind"].get<int>();

	if (!j["struct"]["combat"]["leftclicker"]["avg"].is_null())
		settings->combat.leftclicker.avg = j["struct"]["combat"]["leftclicker"]["avg"].get<int>();

	if (!j["struct"]["combat"]["leftclicker"]["breakBlock"].is_null())
		settings->combat.leftclicker.breakBlock = j["struct"]["combat"]["leftclicker"]["breakBlock"].get<bool>();

	if (!j["struct"]["combat"]["leftclicker"]["onlyWeapon"].is_null())
		settings->combat.leftclicker.onlyWeapon = j["struct"]["combat"]["leftclicker"]["onlyWeapon"].get<bool>();

	if (!j["struct"]["combat"]["leftclicker"]["inInventory"].is_null())
		settings->combat.leftclicker.inInventory = j["struct"]["combat"]["leftclicker"]["inInventory"].get<bool>();

	/**/
	if (!j["struct"]["combat"]["aimassist"]["enabled"].is_null())
		settings->combat.aimassist.enabled = j["struct"]["combat"]["aimassist"]["enabled"].get<bool>();

	if (!j["struct"]["combat"]["aimassist"]["bind"].is_null())
		settings->combat.aimassist.bind = j["struct"]["combat"]["aimassist"]["bind"].get<int>();

	if (!j["struct"]["combat"]["aimassist"]["distance"].is_null())
		settings->combat.aimassist.distance = j["struct"]["combat"]["aimassist"]["distance"].get<float>();

	if (!j["struct"]["combat"]["aimassist"]["fov"].is_null())
		settings->combat.aimassist.fov = j["struct"]["combat"]["aimassist"]["fov"].get<int>();

	if (!j["struct"]["combat"]["aimassist"]["speed"].is_null())
		settings->combat.aimassist.speed = j["struct"]["combat"]["aimassist"]["speed"].get<int>();

	if (!j["struct"]["combat"]["aimassist"]["onlyweapon"].is_null())
		settings->combat.aimassist.onlyweapon = j["struct"]["combat"]["aimassist"]["onlyweapon"].get<bool>();

	if (!j["struct"]["combat"]["aimassist"]["mousemove"].is_null())
		settings->combat.aimassist.mousemove = j["struct"]["combat"]["aimassist"]["mousemove"].get<bool>();

	if (!j["struct"]["combat"]["aimassist"]["onlyclicking"].is_null())
		settings->combat.aimassist.onlyclicking = j["struct"]["combat"]["aimassist"]["onlyclicking"].get<bool>();

	if (!j["struct"]["combat"]["aimassist"]["antibot"].is_null())
		settings->combat.aimassist.antibot = j["struct"]["combat"]["aimassist"]["antibot"].get<bool>();

	/**/
	if (!j["struct"]["combat"]["reach"]["enabled"].is_null())
		settings->combat.reach.enabled = j["struct"]["combat"]["reach"]["enabled"].get<bool>();

	if (!j["struct"]["combat"]["reach"]["bind"].is_null())
		settings->combat.reach.bind = j["struct"]["combat"]["reach"]["bind"].get<int>();

	if (!j["struct"]["combat"]["reach"]["value"].is_null())
		settings->combat.reach.value = j["struct"]["combat"]["reach"]["value"].get<double>();

	if (!j["struct"]["combat"]["reach"]["hitbox"].is_null())
		settings->combat.reach.hitbox = j["struct"]["combat"]["reach"]["hitbox"].get<float>();

	if (!j["struct"]["combat"]["reach"]["onlyweapon"].is_null())
		settings->combat.reach.onlyweapon = j["struct"]["combat"]["reach"]["onlyweapon"].get<bool>();

	if (!j["struct"]["combat"]["reach"]["onground"].is_null())
		settings->combat.reach.onground = j["struct"]["combat"]["reach"]["onground"].get<bool>();

	if (!j["struct"]["combat"]["reach"]["liquidcheck"].is_null())
		settings->combat.reach.liquidcheck = j["struct"]["combat"]["reach"]["liquidcheck"].get<bool>();

	/**/
	if (!j["struct"]["movement"]["timer"]["enabled"].is_null())
		settings->movement.timer.enabled = j["struct"]["movement"]["timer"]["enabled"].get<bool>();

	if (!j["struct"]["movement"]["timer"]["bind"].is_null())
		settings->movement.timer.bind = j["struct"]["movement"]["timer"]["bind"].get<int>();

	if (!j["struct"]["movement"]["timer"]["value"].is_null())
		settings->movement.timer.value = j["struct"]["movement"]["timer"]["value"].get<float>();

	if (!j["struct"]["movement"]["timer"]["moving"].is_null())
		settings->movement.timer.moving = j["struct"]["movement"]["timer"]["moving"].get<bool>();

	if (!j["struct"]["movement"]["timer"]["onlyweapon"].is_null())
		settings->movement.timer.onlyweapon = j["struct"]["movement"]["timer"]["onlyweapon"].get<bool>();

	/**/
	if (!j["struct"]["movement"]["bhop"]["enabled"].is_null())
		settings->movement.bhop.enabled = j["struct"]["movement"]["bhop"]["enabled"].get<bool>();

	if (!j["struct"]["movement"]["bhop"]["bind"].is_null())
		settings->movement.bhop.bind = j["struct"]["movement"]["bhop"]["bind"].get<int>();

	if (!j["struct"]["movement"]["bhop"]["power"].is_null())
		settings->movement.bhop.power = j["struct"]["movement"]["bhop"]["power"].get<float>();

	if (!j["struct"]["movement"]["bhop"]["liquidCheck"].is_null())
		settings->movement.bhop.liquidCheck = j["struct"]["movement"]["bhop"]["liquidCheck"].get<bool>();

	/**/
	if (!j["struct"]["visuals"]["fullbright"]["enabled"].is_null())
		settings->visuals.fullbright.enabled = j["struct"]["visuals"]["fullbright"]["enabled"].get<bool>();

	if (!j["struct"]["visuals"]["fullbright"]["bind"].is_null())
		settings->visuals.fullbright.bind = j["struct"]["visuals"]["fullbright"]["bind"].get<int>();

	/**/
	if (!j["struct"]["settings"]["hide"]["enabled"].is_null())
		settings->settings.hide.enabled = j["struct"]["settings"]["hide"]["enabled"].get<bool>();

	if (!j["struct"]["settings"]["hide"]["bind"].is_null())
		settings->settings.hide.bind = j["struct"]["settings"]["hide"]["bind"].get<int>();

	/**/
	if (!j["struct"]["settings"]["streamproof"]["enabled"].is_null())
		settings->settings.streamproof.enabled = j["struct"]["settings"]["hide"]["enabled"].get<bool>();

	if (!j["struct"]["settings"]["streamproof"]["bind"].is_null())
		settings->settings.streamproof.bind = j["struct"]["settings"]["streamproof"]["bind"].get<int>();


	/**/
	if (!j["struct"]["misc"]["throwpot"]["enabled"].is_null())
		settings->misc.throwpot.enabled = j["struct"]["misc"]["throwpot"]["enabled"].get<bool>();

	if (!j["struct"]["misc"]["throwpot"]["bind"].is_null())
		settings->misc.throwpot.bind = j["struct"]["misc"]["throwpot"]["bind"].get<int>();

	if (!j["struct"]["misc"]["throwpot"]["switchDelay"].is_null())
		settings->misc.throwpot.switchDelay = j["struct"]["misc"]["throwpot"]["switchDelay"].get<int>();

	if (!j["struct"]["misc"]["throwpot"]["throwDelay"].is_null())
		settings->misc.throwpot.throwDelay = j["struct"]["misc"]["throwpot"]["throwDelay"].get<int>();

	if (!j["struct"]["misc"]["throwpot"]["mode"]["mode_selected"].is_null())
		settings->misc.throwpot.mode.mode_selected = j["struct"]["misc"]["throwpot"]["mode"]["mode_selected"].get<int>();

#pragma endregion
}

void ConfigMaker::Reload(std::vector<std::string>& out)
{
	char* szQuery = new char[126];
	sprintf(szQuery, _("action=get&creator_id=%s"), std::to_string(41282).data());

	std::string reqRes = RequestHelper::get().send_request(_("http://193.26.14.16/cloud_configs/"), szQuery, strlen(szQuery));

	if (reqRes.size() > 0)
	{
		nlohmann::json j = nlohmann::json::parse(reqRes);

		out.clear();
		for (auto it = j.begin(); it != j.end(); ++it)
		{
			out.push_back(it.value().dump());
		}
	}

	delete[] szQuery;
}
