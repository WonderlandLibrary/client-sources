#include "minecraft.hpp"

std::unique_ptr<sdk::c_minecraft> sdk::instance;

sdk::c_minecraft::c_minecraft(JNIEnv* env, std::unordered_map<std::string, jclass> klasses)
{
	this->env = env;
	this->klasses = klasses;
}

sdk::c_minecraft::~c_minecraft()
{
	
}

jclass sdk::c_minecraft::klass()
{
	return findClass("net.minecraft.client.Minecraft");
}

jobject sdk::c_minecraft::getMinecraft()
{
	// No need to create extra objects because we aren't alowed to delete klasses
	jfieldID theMinecraftField = env->GetStaticFieldID(klass(), "theMinecraft", "Lnet/minecraft/client/Minecraft;");
	return env->GetStaticObjectField(klass(), theMinecraftField);
}

jobject sdk::c_minecraft::getPlayer()
{
	jobject theMinecraft = getMinecraft();
	
	jfieldID thePlayerField = env->GetFieldID(klass(), "thePlayer", "Lnet/minecraft/client/entity/EntityPlayerClientMP;");
	jobject thePlayer = env->GetObjectField(theMinecraft, thePlayerField);
	
	env->DeleteLocalRef(theMinecraft);

	return thePlayer;
}

jobject sdk::c_minecraft::getWorld()
{
	jobject theMinecraft = getMinecraft();

	jfieldID theWorldField = env->GetFieldID(klass(), "theWorld", "Lnet/minecraft/client/multiplayer/WorldClient;");
	jobject theWorld = env->GetObjectField(theMinecraft, theWorldField);

	env->DeleteLocalRef(theMinecraft);

	return theWorld;
}