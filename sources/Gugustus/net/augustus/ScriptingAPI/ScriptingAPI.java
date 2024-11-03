package net.augustus.ScriptingAPI;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public interface ScriptingAPI {

	//basic scripting api features
	String getContentFromURL(String url);
	void log(String message);
	void setBinding(String binding);
	String getLoadedScriptByName(String name);
	void executeScript(String name);
	
	
	//movement functions
	void jump();
	void setMotionX(double motion);
	void setMotionY(double motion);
	void setMotionZ(double motion);
	void addMotionX(double motion);
	void addMotionY(double motion);
	void addMotionZ(double motion);
	void setSprinting(boolean state);
	void setSneaking(boolean state);
	void setPosition(double x, double y, double z);
	void addX(double pos);
	void addY(double pos);
	void addZ(double pos);
	
	//combat functions
	
	//player controller
	void attackEntity(Entity e);
	
	//mouse clicks
	void leftClickMouse();
	void rightClickMouse();
	void midClickMouse();
	
	//get
	Vec3 getMotion();
	Vec3 getPosition();
	Vec3 getPrevPosition();
	
	//entity
	Entity getNearestEntity(double range);
	double getEntityHealth(Entity e);
	double getMaxEntityHealth(Entity e);
	String getName(Entity e);
	String getCustomName(Entity e);
	Vec3 getPosition(Entity e);
	Vec3 getPrevPosition(Entity e);
	Vec3 getMotion(Entity e);
	float getRotationYaw(Entity e);
	float getRotationPitch(Entity e);
	float getPrevRotationYaw(Entity e);
	float getPrevRotationPitch(Entity e);
	boolean isCollided(Entity e);
	boolean isOnGround(Entity e);
	boolean isCollidedHorizontally(Entity e);
	boolean isCollidedVertically(Entity e);
	boolean isInWeb(Entity e);
	boolean noClip(Entity e);
	int getTickExisted(Entity e);
	boolean isInWater(Entity e);
	Vec3i getChunkCoords(Entity e);
	Vec3 getServerPos(Entity e);
	int getDimension(Entity e);
	

}
