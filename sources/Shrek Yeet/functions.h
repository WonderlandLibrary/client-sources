#pragma once
#include <Windows.h>
#include "jni.h"
#include <iostream>
#include <string>
#include <thread>
jobject mc;
jobject thePlayer;
jobject theWorld;
jobject renderViewEntity;
jobject pointedEntity;
jobject boundingBox;
jobject hitVec;
jobject ridingEntity;

jdouble xCoord;
jdouble yCoord;
jdouble zCoord;

jfieldID thePlayerf;
jfieldID renderViewEntityf;
jfieldID pointedEntityf;
jfieldID objectMouseOverf;
jfieldID boundingBoxf;
jfieldID hitVecf;
jfieldID ridingEntityf;
jfieldID xCoordf;
jfieldID yCoordf;
jfieldID zCoordf;
jfieldID renderPartialTicksf;
jfieldID timerf;
jfieldID playerControllerf;
jfieldID entityHitf;

jmethodID rayTracef;
jmethodID getPositionf;
jmethodID getLookf;
jmethodID addVectorf;
jmethodID getEntitiesWithinAABBExcludingEntityf;
jmethodID sizef;
jmethodID getf;
jmethodID canBeCollidedWithf;
jmethodID getCollisionBorderSizef;
jmethodID expandf;
jmethodID calculateInterceptf;
jmethodID isVecInsidef;
jmethodID distanceTof;
jmethodID addCoordf;
jmethodID attackEntityf;
jmethodID movingObjectPositionc;
jmethodID isUsingItemf;

jclass world;
jclass Vec3;
jclass entityLivingBaseClass;
jclass Entity;
jclass List;
jclass AxisAlignedBB;
jclass MovingObjectPosition;
jclass EntityRenderer;
jclass PlayerControllerMP;
jclass Timer;
jclass EntityPlayer;

using namespace std;
jobject getClassLoader(JNIEnv* env)
{
	jclass launch = env->FindClass("net/minecraft/launchwrapper/Launch");
	cout << launch << endl;
	jfieldID sfid = env->GetStaticFieldID(launch, "classLoader", "Lnet/minecraft/launchwrapper/LaunchClassLoader;");
	cout << sfid << endl;
	jobject classLoader = env->GetStaticObjectField(launch, sfid);
	cout << classLoader << endl;

	return classLoader;
}

jclass getObject(JNIEnv* env, const char* className)
{
	jstring name = env->NewStringUTF(className);
	jobject classLoader = getClassLoader(env);
	jmethodID mid = env->GetMethodID(env->GetObjectClass(classLoader), "findClass", "(Ljava/lang/String;)Ljava/lang/Class;");
	return (jclass)env->CallObjectMethod(classLoader, mid, name);
	env->DeleteLocalRef(name);

}

jobject getMC(JNIEnv* env)
{
	jclass mcClass = getObject(env, "net.minecraft.client.Minecraft");
	cout << "MCClass: " << mcClass << endl;
	jmethodID smid = env->GetStaticMethodID(mcClass, "func_71410_x", "()Lnet/minecraft/client/Minecraft;");
	cout << "SMID: " << smid << endl;
	return env->CallStaticObjectMethod(mcClass, smid);
}

int getDH(JNIEnv* env)
{
	cout << "Minecraft instance: " << endl;
	jfieldID fid = env->GetFieldID(env->GetObjectClass(mc), "field_71440_d", "I");
	return env->GetIntField(mc, fid);
}

jobject getPlayer(JNIEnv* env)
{
	thePlayerf = env->GetFieldID(env->GetObjectClass(mc), "field_71439_g", "Lnet/minecraft/client/entity/EntityClientPlayerMP;");
	return env->GetObjectField(mc, thePlayerf);
}

jobject getWorld(JNIEnv* env)
{
	jfieldID fid = env->GetFieldID(env->GetObjectClass(mc), "field_71441_e", "Lnet/minecraft/client/multiplayer/WorldClient;");
	return env->GetObjectField(mc, fid);
}

void getMouseOver(float p_78473_1_, JNIEnv* env, double reach)
{
	if (renderViewEntity != NULL)
	{
		if (theWorld != NULL)
		{
			//double var2 = (double)this.mc.playerController.getBlockReachDistance();
			double var2 = reach;
			//objectMouseOver = renderViewEntity.rayTrace(var2, p_78473_1_);
			////env->SetObjectField(mc, objectMouseOverf, env->CallObjectMethod(renderViewEntity, rayTracef, var2, p_78473_1_));
			double var4 = var2;
			//Vec3 var6 = this.mc.renderViewEntity.getPosition(p_78473_1_);
			jobject var6 = env->CallObjectMethod(renderViewEntity, getPositionf, p_78473_1_);
			/*if (this.mc.playerController.extendedReach()) creative check, don't need it
			{
				var2 = 6.0D;
				var4 = 6.0D;
			}*/


			//Vec3 var7 = this.mc.renderViewEntity.getLook(p_78473_1_);
			jobject var7 = env->CallObjectMethod(renderViewEntity, getLookf, p_78473_1_);
			xCoord = env->GetDoubleField(var7, xCoordf);
			yCoord = env->GetDoubleField(var7, yCoordf);
			zCoord = env->GetDoubleField(var7, zCoordf);
			//Vec3 var8 = var6.addVector(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2);
			jobject var8 = env->CallObjectMethod(var6, addVectorf, xCoord * var2, yCoord * var2, zCoord * var2);
			//this.pointedEntity = NULL;
			env->SetObjectField(EntityRenderer, pointedEntityf, NULL);
			jobject var9 = NULL;
			float var10 = 1.0F;
			boundingBox = env->GetObjectField(renderViewEntity, boundingBoxf);
			//List var11 = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(var7.xCoord * var2, var7.yCoord * var2, var7.zCoord * var2).expand((double)var10, (double)var10, (double)var10));
			jobject var11 = env->CallObjectMethod(theWorld, getEntitiesWithinAABBExcludingEntityf, renderViewEntity, env->CallObjectMethod(env->CallObjectMethod(boundingBox, addCoordf, xCoord * var2, yCoord * var2, zCoord * var2), expandf, (double)var10, (double)var10, (double)var10));
			double var12 = var4;
			//for (int var14 = 0; var14 < var11.size(); ++var14)
			for (int var14 = 0; var14 < env->CallIntMethod(var11, sizef); ++var14)
			{
				//Entity var15 = (Entity)var11.get(var14);
 				jobject var15 = env->CallObjectMethod(var11, getf, var14);

				//if (var15.canBeCollidedWith())
				if (env->CallBooleanMethod(var15, canBeCollidedWithf))
				{
					//float var16 = var15.getCollisionBorderSize();
					float var16 = env->CallFloatMethod(var15, getCollisionBorderSizef);
					//AxisAlignedBB var17 = var15.boundingBox.expand((double)var16, (double)var16, (double)var16);
					jobject var17 = env->CallObjectMethod(env->GetObjectField(var15, boundingBoxf), expandf, (double)var16, (double)var16, (double)var16);
					//MovingObjectPosition var18 = var17.calculateIntercept(var6, var8);
					jobject var18 = env->CallObjectMethod(var17, calculateInterceptf, var6, var8);
					if(var18 != NULL)
						hitVec = env->GetObjectField(var18, hitVecf);
					//if (var17.isVecInside(var6))
					if(env->CallBooleanMethod(var17, isVecInsidef, var6))
					{
						if (0.0 < var12 || var12 == 0.0)
						{
							//this.pointedEntity = var15;
							env->SetObjectField(EntityRenderer, pointedEntityf, var15);
							//var9 = var18 == null ? var6 : var18.hitVec;
							var9 = var18 == NULL ? var6 : hitVec;
							var12 = 0.0;
						}
					}
					else if (var18 != NULL)
					{
						//double var19 = var6.distanceTo(var18.hitVec);
						double var19 = env->CallDoubleMethod(var6, distanceTof, hitVec);
						if (var19 < var12 || var12 == 0.0)
						{
							ridingEntity = env->GetObjectField(renderViewEntity, ridingEntityf);
							//if (var15 == this.mc.renderViewEntity.ridingEntity)
							if(var15 == ridingEntity)
							{
								if (var12 == 0.0)
								{
									//pointedEntity = var15;
									env->SetObjectField(EntityRenderer, pointedEntityf, var15);
									//var9 = var18.hitVec;
									var9 = hitVec;
								}
							}
							else
							{
								//pointedEntity = var15;
								env->SetObjectField(EntityRenderer, pointedEntityf, var15);
								//var9 = var18.hitVec;
								var9 = hitVec;
								var12 = var19;
							}
						}
					}
				}
			}

			//if (this.pointedEntity != null && (var12 < var4 || this.mc.objectMouseOver == null))
			if (env->GetObjectField(EntityRenderer,pointedEntityf) != NULL && var12 > 3.0f)
			{
				//this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var9);
				env->SetObjectField(mc,objectMouseOverf,env->NewObject(MovingObjectPosition, movingObjectPositionc, env->GetObjectField(EntityRenderer, pointedEntityf), var9));
				////jobject playerController = env->GetObjectField(mc, playerControllerf);
				////env->CallVoidMethod(playerController, attackEntityf, thePlayer, ent);
				/*if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame)
				{
						this.mc.pointedEntity = this.pointedEntity;
				}*/
			}
		}
	}
}

void postPreInit(JNIEnv* env)
{
	mc = getMC(env);
	mc = env->NewGlobalRef(mc);
	thePlayer = getPlayer(env);
	thePlayer = env->NewGlobalRef(thePlayer);
	theWorld = getWorld(env);
	theWorld = env->NewGlobalRef(theWorld);

	//classes
	entityLivingBaseClass = getObject(env, "net.minecraft.entity.EntityLivingBase");
	Vec3 = getObject(env, "net.minecraft.util.Vec3");
	world = getObject(env, "net.minecraft.world.World");
	List = getObject(env, "java.util.List");
	Entity = getObject(env, "net.minecraft.entity.Entity");
	EntityPlayer = getObject(env, "net.minecraft.entity.player.EntityPlayer");
	AxisAlignedBB = getObject(env, "net.minecraft.util.AxisAlignedBB");
	MovingObjectPosition = getObject(env, "net.minecraft.util.MovingObjectPosition");
	EntityRenderer = getObject(env, "net.minecraft.client.renderer.EntityRenderer");
	PlayerControllerMP = getObject(env, "net.minecraft.client.multiplayer.PlayerControllerMP");
	Timer = getObject(env, "net.minecraft.util.Timer");

	cout << "world: " << world << endl;
	cout << "List: " << List << endl;
	cout << "Vec3: " << Vec3 << endl;
	cout << "entityLivingBaseClass: " << entityLivingBaseClass << endl;
	cout << "AxisAlignedBB: " << AxisAlignedBB << endl;
	cout << "MovingObjectPosition: " << MovingObjectPosition << endl;
	cout << "Entity: " << Entity << endl;
	cout << "EntityRenderer: " << EntityRenderer << endl;
	
	jclass mcClass = getObject(env, "net.minecraft.client.Minecraft");
	//fields
	//examplef = env->GetMethodID(containingclass, "fieldname", "signature");
	renderViewEntityf = env->GetFieldID(mcClass, "field_71451_h", "Lnet/minecraft/entity/EntityLivingBase;");
	pointedEntityf = env->GetFieldID(EntityRenderer, "field_78528_u", "Lnet/minecraft/entity/Entity;");
	objectMouseOverf = env->GetFieldID(mcClass, "field_71476_x", "Lnet/minecraft/util/MovingObjectPosition;");
	boundingBoxf = env->GetFieldID(Entity, "field_70121_D", "Lnet/minecraft/util/AxisAlignedBB;");
	hitVecf = env->GetFieldID(MovingObjectPosition, "field_72307_f", "Lnet/minecraft/util/Vec3;");
	ridingEntityf = env->GetFieldID(Entity, "field_70154_o", "Lnet/minecraft/entity/Entity;");
	xCoordf = env->GetFieldID(Vec3, "field_72450_a", "D");
	yCoordf = env->GetFieldID(Vec3, "field_72448_b", "D");
	zCoordf = env->GetFieldID(Vec3, "field_72449_c", "D");
	renderPartialTicksf = env->GetFieldID(Timer, "field_74281_c", "F");
	timerf = env->GetFieldID(mcClass, "field_71428_T", "Lnet/minecraft/util/Timer;");
	playerControllerf = env->GetFieldID(mcClass, "field_71442_b", "Lnet/minecraft/client/multiplayer/PlayerControllerMP;");
	entityHitf = env->GetFieldID(MovingObjectPosition, "field_72308_g", "Lnet/minecraft/entity/Entity;");
	
	//objects
	renderViewEntity = env->GetObjectField(mc, renderViewEntityf);

	//methods
	//examplef = env->GetMethodID(containingclass, "funcname", "(input)output");
	rayTracef = env->GetMethodID(entityLivingBaseClass, "func_70614_a", "(DF)Lnet/minecraft/util/MovingObjectPosition;");
	getPositionf = env->GetMethodID(entityLivingBaseClass, "func_70666_h", "(F)Lnet/minecraft/util/Vec3;");
	getLookf = env->GetMethodID(entityLivingBaseClass, "func_70676_i", "(F)Lnet/minecraft/util/Vec3;");
	addVectorf = env->GetMethodID(Vec3, "func_72441_c", "(DDD)Lnet/minecraft/util/Vec3;");
	getEntitiesWithinAABBExcludingEntityf = env->GetMethodID(world, "func_72839_b", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/AxisAlignedBB;)Ljava/util/List;");
	sizef = env->GetMethodID(List, "size", "()I");
	getf = env->GetMethodID(List, "get", "(I)Ljava/lang/Object;");
	canBeCollidedWithf = env->GetMethodID(Entity, "func_70067_L", "()Z");
	getCollisionBorderSizef = env->GetMethodID(Entity, "func_70111_Y", "()F");
	expandf = env->GetMethodID(AxisAlignedBB, "func_72314_b", "(DDD)Lnet/minecraft/util/AxisAlignedBB;");
	addCoordf = env->GetMethodID(AxisAlignedBB, "func_72321_a", "(DDD)Lnet/minecraft/util/AxisAlignedBB;");
	calculateInterceptf = env->GetMethodID(AxisAlignedBB, "func_72327_a", "(Lnet/minecraft/util/Vec3;Lnet/minecraft/util/Vec3;)Lnet/minecraft/util/MovingObjectPosition;");
	isVecInsidef = env->GetMethodID(AxisAlignedBB, "func_72318_a", "(Lnet/minecraft/util/Vec3;)Z");
	distanceTof = env->GetMethodID(Vec3, "func_72438_d", "(Lnet/minecraft/util/Vec3;)D");
	attackEntityf = env->GetMethodID(PlayerControllerMP, "func_78764_a", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/entity/Entity;)V");
	isUsingItemf = env->GetMethodID(EntityPlayer, "func_71039_bw", "()Z");
	movingObjectPositionc = env->GetMethodID(MovingObjectPosition, "<init>", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/Vec3;)V");

	cout << "renderViewEntityf: " << renderViewEntityf << endl;
	cout << "pointedEntityf: " << pointedEntityf << endl;
	cout << "objectMouseOverf: " << objectMouseOverf << endl;
	cout << "rayTracef: " << rayTracef << endl;
	cout << "getPositionf: " << getPositionf << endl;
	cout << "getLookf: " << getLookf << endl;
	cout << "addVectorf: " << addVectorf << endl;
	cout << "getEntitiesWithinAABBExcludingEntityf: " << getEntitiesWithinAABBExcludingEntityf << endl;
	cout << "sizef: " << sizef << endl;
	cout << "getf: " << getf << endl;
	cout << "canBeCollidedWithf: " << canBeCollidedWithf << endl;
	cout << "getCollisionBorderSizef: " << getCollisionBorderSizef << endl;
	cout << "boundingBoxf: " << boundingBoxf << endl;
	cout << "expandf: " << expandf << endl;
	cout << "calculateInterceptf: " << calculateInterceptf << endl;
	cout << "isVecInsidef: " << isVecInsidef << endl;
	cout << "distanceTof: " << distanceTof << endl;
	cout << "ridingEntityf: " << ridingEntityf << endl;
}

void cleanup(JNIEnv* env)
{
	env->DeleteGlobalRef(mc);
	env->DeleteGlobalRef(thePlayer);
	env->DeleteGlobalRef(theWorld);
}