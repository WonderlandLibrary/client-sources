#include "mapper.hpp"
#include "settings.hpp"
#include <unordered_map>
#include <fstream>
#include <zip.h>

static std::unordered_map<const char*, unsigned short> g_Mappings;
void mapper::initialize_mapper()
{
	switch(settings->game_ver)
	{
	case CASUAL_1_7_10: {
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 132},
			{"theWorld", 124},
			{"currentScreen", 160},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 208},
			{"boundingBox", 284},
			{"maxHurtResistantTime", 396},
			{"playerEntities", 84},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 254},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 263},
			{"inventory", 652},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 184},
			{"typeOfHit", 28},
			{"BLOCK", 108},
			{"item", 24},
			{"ticksExisted", 196},
			{"metadata", 20}, //itemstack
			{"gameSettings", 188},
			{"gammaSetting", 116},
			{"displayWidth",  12},
			{"displayHeight", 48},
			{"timer",  116},
			{"timerSpeed", 64},
			{"moveForward", 476},
			{"moveStrafing", 472},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case CASUAL_1_8: {
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 168},
			{"theWorld", 148},
			{"currentScreen", 196},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 200},
			{"boundingBox", 272},
			{"maxHurtResistantTime", 388},
			{"playerEntities", 92},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 242},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 251},
			{"inventory", 648},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 220},
			{"typeOfHit", 16},
			{"BLOCK", 108},
			{"item", 28},
			{"ticksExisted", 188},
			{"metadata", 20}, //itemstack
			{"gameSettings", 224},
			{"gammaSetting", 108},
			{"displayWidth",  12},
			{"displayHeight", 64},
			{"timer",  140},
			{"timerSpeed", 64},
			{"moveForward", 468},
			{"moveStrafing", 472},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case FORGE_1_7_10: {
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 132},
			{"theWorld", 124},
			{"currentScreen", 160},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 208},
			{"boundingBox", 284},
			{"maxHurtResistantTime", 412},
			{"playerEntities", 88},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 254},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 263},
			{"inventory", 680},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 184},
			{"typeOfHit", 32},
			{"BLOCK", 108},
			{"item", 24},
			{"ticksExisted", 196},
			{"metadata", 20}, //itemstack
			{"gameSettings", 188},
			{"gammaSetting", 116},
			{"displayWidth",  12},
			{"displayHeight", 48},
			{"timer",  116},
			{"timerSpeed", 64},
			{"moveForward", 492},
			{"moveStrafing", 488},
			{"renderPartialTicks", 60},
		};
		break;
 	}
	case FORGE_1_8: {
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 168},
			{"theWorld", 148},
			{"currentScreen", 196},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 200},
			{"boundingBox", 272},
			{"maxHurtResistantTime", 404},
			{"playerEntities", 92},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 242},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 251},
			{"inventory", 676},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 220},
			{"typeOfHit", 20},
			{"BLOCK", 108},
			{"item", 28},
			{"ticksExisted", 188},
			{"metadata", 20}, //itemstack
			{"gameSettings", 224},
			{"gammaSetting", 108},
			{"displayWidth",  12},
			{"displayHeight", 64},
			{"timer", 140},
			{"timerSpeed", 64},
			{"moveForward", 484},
			{"moveStrafing", 480},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case BADLION_1_7_10: {
		g_Mappings = {
			{ "theMinecraft", 120},
			{ "thePlayer", 164 },
			{ "theWorld", 156 },
			{ "currentScreen", 192 },
			{ "posX", 48},
			{ "posY", 56},
			{ "posZ", 64},
			{ "motionX", 72},
			{ "motionY", 80},
			{ "motionZ", 88},
			{ "rotationYaw", 136},
			{ "rotationPitch", 140},
			{ "prevRotationYaw", 144},
			{ "prevRotationPitch", 148},
			{ "hurtResistantTime", 208},
			{ "boundingBox", 284},
			{ "maxHurtResistantTime", 412},
			{ "playerEntities", 84},
			{ "minX", 16},
			{ "minY", 24},
			{ "minZ", 32},
			{ "maxX", 40},
			{ "maxY", 48},
			{ "maxZ", 56},
			{ "isOnGround", 254},
			{ "prevPosX", 24},
			{ "prevPosY", 32},
			{ "prevPosZ", 40},
			{ "isInWater", 263},
			{ "inventory", 672},
			{ "mainInventory", 20},
			{ "currentItem", 12},
			{ "objectMouseOver", 216 },
			{ "typeOfHit", 28 },
			{ "BLOCK", 108 },
			{ "item", 24 },
			{"ticksExisted", 196},
			{"metadata", 20}, //itemstack
			{"gameSettings", 220},
			{"gammaSetting", 212},
			{"displayWidth",  12},
			{"displayHeight", 48},
			{"timer", 144},
			{"timerSpeed", 64},
			{"moveForward", 532},
			{"moveStrafing", 528},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case BADLION_1_8:{
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 192},
			{"theWorld", 172},
			{"currentScreen", 220},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 200},
			{"boundingBox", 272},
			{"maxHurtResistantTime", 428},
			{"playerEntities", 92},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 242},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 251},
			{"inventory", 692},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 244},
			{"typeOfHit", 16},
			{"BLOCK", 108},
			{"item", 28},
			{"ticksExisted", 188},
			{"metadata", 20}, //itemstack
			{"gameSettings", 248},
			{"gammaSetting", 108},
			{"displayWidth",  12},
			{"displayHeight", 72},
			{"timer", 160},
			{"timerSpeed", 64},
			{"moveForward", 496},
			{"moveStrafing", 492},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case FEATHER_1_8:{
		g_Mappings = {
			{"theMinecraft", 120},
			{"thePlayer", 168},
			{"theWorld", 148},
			{"currentScreen", 196},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 152},
			{"rotationPitch", 156},
			{"prevRotationYaw", 160},
			{"prevRotationPitch", 164},
			{"hurtResistantTime", 216},
			{"boundingBox", 292},
			{"maxHurtResistantTime", 428},
			{"playerEntities", 96},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 258},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 267},
			{"inventory", 712},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 220},
			{"typeOfHit", 20},
			{"BLOCK", 108},
			{"item", 28},
			{"ticksExisted", 204},
			{"metadata", 20}, //itemstack
			{"gameSettings", 224},
			{"gammaSetting", 108},
			{"displayWidth",  12},
			{"displayHeight", 64},
			{"timer", 140},
			{"timerSpeed", 64},
			{"moveForward", 508},
			{"moveStrafing", 504},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case LUNAR_1_7_10: {
		g_Mappings = {
			{"theMinecraft", 128},
			{"thePlayer", 140},
			{"theWorld", 132},
			{"currentScreen", 168},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 208},
			{"boundingBox", 284},
			{"maxHurtResistantTime", 400},
			{"playerEntities", 84},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 254},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 263},
			{"inventory", 656},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 192},
			{"typeOfHit", 32},
			{"BLOCK", 116},
			{"item", 24},
			{"ticksExisted", 196},
			{"metadata", 20}, //itemstack
			{"gameSettings", 196},
			{"gammaSetting", 212},
			{"displayWidth",  12},
			{"displayHeight", 56},
			{"timer", 124},
			{"timerSpeed", 64},
			{"moveForward", 480},
			{"moveStrafing", 476},
			{"renderPartialTicks", 60},
		};
		break;
	}
	case LUNAR_1_8: {
		g_Mappings = {
			{"theMinecraft", 128},
			{"thePlayer", 180},
			{"theWorld", 160},
			{"currentScreen", 208},
			{"posX", 48},
			{"posY", 56},
			{"posZ", 64},
			{"motionX", 72},
			{"motionY", 80},
			{"motionZ", 88},
			{"rotationYaw", 136},
			{"rotationPitch", 140},
			{"prevRotationYaw", 144},
			{"prevRotationPitch", 148},
			{"hurtResistantTime", 200},
			{"boundingBox", 272},
			{"maxHurtResistantTime", 392},
			{"playerEntities", 92},
			{"minX", 16},
			{"minY", 24},
			{"minZ", 32},
			{"maxX", 40},
			{"maxY", 48},
			{"maxZ", 56},
			{"isOnGround", 242},
			{"prevPosX", 24},
			{"prevPosY", 32},
			{"prevPosZ", 40},
			{"isInWater", 251},
			{"inventory", 652},
			{"mainInventory", 20},
			{"currentItem", 12},
			{"objectMouseOver", 232},
			{"typeOfHit", 20},
			{"BLOCK", 116},
			{"item", 28},
			{"ticksExisted", 188},
			{"metadata", 20}, //itemstack
			{"gameSettings", 236},
			{"gammaSetting", 108},
			{"displayWidth",  12},
			{"displayHeight", 72},
			{"timer", 152},
			{"timerSpeed", 64},
			{"moveForward", 472},
			{"moveStrafing", 468},
			{"renderPartialTicks", 60},
		};
		break;
	}

	}
}

unsigned short mapper::get_offset(const char* f)
{
	return g_Mappings[f];
}

std::string mapper::load_lunar_class(HANDLE javaw_handle, char* process_path)
{
	bool is1_7 = false;
	std::string fname = std::string(process_path);
	if (fname.find("1.7") != std::string::npos)
		is1_7 = true;

	std::string lunar_path = fname.substr(0, fname.find("jre"));
	lunar_path += "offline\\";
	lunar_path += is1_7 ? "1.7" : "1.8";
	lunar_path += "\\lunar-prod-optifine.jar";

	std::ifstream fin(lunar_path.data(), std::ios::binary | std::ios::ate);
	if (fin.fail()) {
		printf("an error occured while trying to grab the classes mappings.\n");
		return 0;
	}

	auto file_size = fin.tellg();
	if (!file_size) {
		printf("an error occured while trying to grab the classes mappings.\n");
		return 0;
	}

	BYTE* rawData = new BYTE[(UINT_PTR)file_size];
	if (!rawData) {
		printf("an error occured while trying to grab the classes mappings.\n");
		return 0;
	}

	fin.seekg(0, std::ios::beg);
	fin.read((char*)(rawData), file_size);
	fin.close();

	zip_error_t err;
	zip_source_t* src = zip_source_buffer_create(rawData, file_size, 1, &err);
	zip* z = zip_open_from_source(src, 0, &err);

	const char* name = is1_7 ? "patch/v1_7/mappings.txt" : "patch/v1_8/mappings.txt";
	struct zip_stat st;
	zip_stat_init(&st);
	zip_stat(z, name, 0, &st);

	char* contents = new char[st.size];

	zip_file* f = zip_fopen(z, name, 0);
	zip_fread(f, contents, st.size);
	zip_fclose(f);

	zip_source_keep(src);
	zip_close(z);

	std::string ret = std::string(contents);
	delete[] contents;
	return ret;
}