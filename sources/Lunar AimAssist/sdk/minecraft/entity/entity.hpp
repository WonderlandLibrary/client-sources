#pragma once

#include <vector>
#include <memory>
#include <string>
#include <jni.h>

class c_entity
{
protected:
	JNIEnv* env;
	jobject entity;
public:
	c_entity();
	c_entity(JNIEnv* env, jobject entity);
	~c_entity();

	// FUNCTIONS EVERY ENTITY HAS
	// FUNCTIONS EVERY ENTITY HAS
	double getPosX();
	double getPosY();
	double getPosZ();

	float getRotationPitch();
	float getRotationYaw();
	void setRotationPitch(float pitch);
	void setRotationYaw(float yaw);

	float getPrevRotationPitch();
	float getPrevRotationYaw();
	void setPrevRotationPitch(float pitch);
	void setPrevRotationYaw(float yaw);
	
	bool isInvisible();
	// END
	// END
	

	jobject getEntity() { return entity; }
};

class c_entity_player : public c_entity
{
public:
	c_entity_player(JNIEnv* env, jobject player);
	~c_entity_player();

	std::string getCurrentItem();
	jobject getTeam();
	bool isSameTeam(jobject team);
};

class c_world
{
private:
	JNIEnv* env;
	jobject world;
public:
	c_world(JNIEnv* env, jobject world);
	~c_world();

	std::vector<std::shared_ptr<c_entity_player>> getPlayers();
	std::vector<std::shared_ptr<c_entity>> getEntities();

	jobject getWorld() { return world; }
};