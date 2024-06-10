#include "entity.hpp"

c_world::c_world(JNIEnv* env, jobject world)
{
	this->env = env;
	this->world = world;
}

c_world::~c_world()
{
	env->DeleteLocalRef(world);
}

std::vector<std::shared_ptr<c_entity_player>> c_world::getPlayers()
{
	std::vector<std::shared_ptr<c_entity_player>> res;

	if (!world)
		return res;

	jclass worldKlass = env->GetObjectClass(world);

	jfieldID playerEntitiesField = env->GetFieldID(worldKlass, "playerEntities", "Ljava/util/List;");
	jobject playerEntities = env->GetObjectField(world, playerEntitiesField);

	if (!playerEntities)
	{
		env->DeleteLocalRef(playerEntities);
		env->DeleteLocalRef(worldKlass);

		return res;
	}

	jclass listKlass = env->FindClass("java/util/List");
	jmethodID toArrayMID = env->GetMethodID(listKlass, "toArray", "()[Ljava/lang/Object;");
	env->DeleteLocalRef(listKlass);

	jobjectArray playerList = (jobjectArray)env->CallObjectMethod(playerEntities, toArrayMID);

	if (!playerList)
	{
		env->DeleteLocalRef(playerList);
		env->DeleteLocalRef(playerEntities);
		env->DeleteLocalRef(worldKlass);

		return res;
	}

	size_t length = env->GetArrayLength(playerList);
	// Don't initialize length inside the loop because it would slow down everything(idk if it would be by alot) because you would have to do it for every player in the game instead of 1 time
	for (int i = 0; i < length; i++)
	{
		jobject player = env->GetObjectArrayElement(playerList, i);
		res.push_back(std::make_shared<c_entity_player>(env, player));
	}

	env->DeleteLocalRef(playerList);
	env->DeleteLocalRef(playerEntities);
	env->DeleteLocalRef(worldKlass);

	return res;
}

std::vector<std::shared_ptr<c_entity>> c_world::getEntities()
{
	std::vector<std::shared_ptr<c_entity>> res;

	if (!world)
		return res;

	jclass worldKlass = env->GetObjectClass(world);

	jfieldID loadedEntityListField = env->GetFieldID(worldKlass, "loadedEntityList", "Ljava/util/List;");
	jobject loadedEntityList = env->GetObjectField(world, loadedEntityListField);

	if (!loadedEntityList)
	{
		env->DeleteLocalRef(loadedEntityList);
		env->DeleteLocalRef(worldKlass);

		return res;
	}

	jclass listKlass = env->FindClass("java/util/List");
	jmethodID toArrayMID = env->GetMethodID(listKlass, "toArray", "()[Ljava/lang/Object;");
	env->DeleteLocalRef(listKlass);

	jobjectArray entityList = (jobjectArray)env->CallObjectMethod(loadedEntityList, toArrayMID);

	if (!entityList)
	{
		env->DeleteLocalRef(entityList);
		env->DeleteLocalRef(loadedEntityList);
		env->DeleteLocalRef(worldKlass);

		return res;
	}

	size_t length = env->GetArrayLength(entityList);
	// Don't initialize length inside the loop because it would slow down everything(idk if it would be by alot) because you would have to do it for every entity in the game instead of 1 time
	for (int i = 0; i < length; i++)
	{
		jobject entity = env->GetObjectArrayElement(entityList, i);
		res.push_back(std::make_shared<c_entity>(env, entity));
	}

	env->DeleteLocalRef(entityList);
	env->DeleteLocalRef(loadedEntityList);
	env->DeleteLocalRef(worldKlass);

	return res;
}

c_entity::c_entity()
{

}

c_entity::c_entity(JNIEnv* env, jobject entity)
{
	this->env = env;
	this->entity = entity;
}

c_entity::~c_entity()
{
	env->DeleteLocalRef(entity);
}

double c_entity::getPosX()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID posXField = env->GetFieldID(playerKlass, "posX", "D");
	double posX = env->GetDoubleField(entity, posXField);
	
	env->DeleteLocalRef(playerKlass);

	return posX;
}

double c_entity::getPosY()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID posYField = env->GetFieldID(playerKlass, "posY", "D");
	double posY = env->GetDoubleField(entity, posYField);

	env->DeleteLocalRef(playerKlass);

	return posY - 1.62;
}

double c_entity::getPosZ()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID posZField = env->GetFieldID(playerKlass, "posZ", "D");
	double posZ = env->GetDoubleField(entity, posZField);

	env->DeleteLocalRef(playerKlass);

	return posZ;
}

float c_entity::getRotationPitch()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID rotationPitchField = env->GetFieldID(playerKlass, "rotationPitch", "F");
	float rotationPitch = env->GetFloatField(entity, rotationPitchField);

	env->DeleteLocalRef(playerKlass);

	return rotationPitch;
}

float c_entity::getRotationYaw()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID rotationYawField = env->GetFieldID(playerKlass, "rotationYaw", "F");
	float rotationYaw = env->GetFloatField(entity, rotationYawField);

	env->DeleteLocalRef(playerKlass);

	return rotationYaw;
}

void c_entity::setRotationPitch(float pitch)
{
	if (!entity)
		return;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID rotationPitchField = env->GetFieldID(playerKlass, "rotationPitch", "F");
	env->SetFloatField(entity, rotationPitchField, pitch);

	env->DeleteLocalRef(playerKlass);
}

void c_entity::setRotationYaw(float yaw)
{
	if (!entity)
		return;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID rotationYawField = env->GetFieldID(playerKlass, "rotationYaw", "F");
	env->SetFloatField(entity, rotationYawField, yaw);

	env->DeleteLocalRef(playerKlass);
}

float c_entity::getPrevRotationPitch()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID prevRotationPitchField = env->GetFieldID(playerKlass, "prevRotationPitchField", "F");
	float rotationPitch = env->GetFloatField(entity, prevRotationPitchField);

	env->DeleteLocalRef(playerKlass);

	return rotationPitch;
}

float c_entity::getPrevRotationYaw()
{
	if (!entity)
		return 0;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID prevRotationYawField = env->GetFieldID(playerKlass, "prevRotationYaw", "F");
	float rotationYaw = env->GetFloatField(entity, prevRotationYawField);

	env->DeleteLocalRef(playerKlass);

	return rotationYaw;
}

void c_entity::setPrevRotationPitch(float pitch)
{
	if (!entity)
		return;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID prevRotationPitchField = env->GetFieldID(playerKlass, "prevRotationPitch", "F");
	env->SetFloatField(entity, prevRotationPitchField, pitch);

	env->DeleteLocalRef(playerKlass);
}

void c_entity::setPrevRotationYaw(float yaw)
{
	if (!entity)
		return;

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID prevRotationYawField = env->GetFieldID(playerKlass, "prevRotationYaw", "F");
	env->SetFloatField(entity, prevRotationYawField, yaw);

	env->DeleteLocalRef(playerKlass);
}

bool c_entity::isInvisible()
{
	if (!entity)
		return false;

	jclass playerKlass = env->GetObjectClass(entity);

	jmethodID isInvisibleMID = env->GetMethodID(playerKlass, "isInvisible", "()Z");
	bool isInvisible = env->CallBooleanMethod(entity, isInvisibleMID);

	env->DeleteLocalRef(playerKlass);

	return isInvisible;
}

std::string c_entity_player::getCurrentItem()
{
	if (!entity)
		return "";

	jclass playerKlass = env->GetObjectClass(entity);

	jfieldID inventoryField = env->GetFieldID(playerKlass, "inventory", "Lnet/minecraft/entity/player/InventoryPlayer;");
	jobject inventory = env->GetObjectField(entity, inventoryField);

	env->DeleteLocalRef(playerKlass);

	jclass inventoryPlayerKlass = env->GetObjectClass(inventory);

	jmethodID getCurrentItemMID = env->GetMethodID(inventoryPlayerKlass, "getCurrentItem", "()Lnet/minecraft/item/ItemStack;");
	jobject getCurrentItem = env->CallObjectMethod(inventory, getCurrentItemMID);

	env->DeleteLocalRef(inventoryPlayerKlass);

	if (!getCurrentItem)
	{
		env->DeleteLocalRef(inventory);
		env->DeleteLocalRef(inventoryPlayerKlass);
		env->DeleteLocalRef(getCurrentItem);
		return "";
	}
	jclass itemStackKlass = env->GetObjectClass(getCurrentItem);

	jmethodID getUnlocalizedNameMID = env->GetMethodID(itemStackKlass, "getUnlocalizedName", "()Ljava/lang/String;");
	jstring getUnlocalizedName = (jstring)env->CallObjectMethod(getCurrentItem, getUnlocalizedNameMID);

	env->DeleteLocalRef(inventory);
	env->DeleteLocalRef(getCurrentItem);
	env->DeleteLocalRef(itemStackKlass);

	const char* str = env->GetStringUTFChars(getUnlocalizedName, 0);
	std::string ret(str);
	env->ReleaseStringUTFChars(getUnlocalizedName, str);

	return ret;
}

jobject c_entity_player::getTeam()
{
	jclass playerClass = env->GetObjectClass(entity);

	jmethodID getTeamMID = env->GetMethodID(playerClass, "getTeam", "()Lnet/minecraft/scoreboard/Team;");
	jobject getTeam = env->CallObjectMethod(entity, getTeamMID);

	env->DeleteLocalRef(playerClass);

	return getTeam;
}

bool c_entity_player::isSameTeam(jobject team)
{
	jobject localTeam = getTeam();
	if (!team || !localTeam)
	{
		env->DeleteLocalRef(localTeam);
		env->DeleteLocalRef(team);

		return false;
	}

	jclass teamKlass = env->GetObjectClass(localTeam);

	jmethodID isSameTeamMID = env->GetMethodID(teamKlass, "isSameTeam", "(Lnet/minecraft/scoreboard/Team;)Z");
	bool isSameTeam = env->CallBooleanMethod(localTeam, isSameTeamMID, team);

	env->DeleteLocalRef(teamKlass);
	env->DeleteLocalRef(localTeam);
	env->DeleteLocalRef(team);

	return isSameTeam;
}

c_entity_player::c_entity_player(JNIEnv* env, jobject player)
{
	this->env = env;
	this->entity = player;
}

c_entity_player::~c_entity_player()
{
	env->DeleteLocalRef(entity);
}