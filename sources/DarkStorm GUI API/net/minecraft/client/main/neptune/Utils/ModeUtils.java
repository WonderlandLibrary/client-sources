package net.minecraft.client.main.neptune.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class ModeUtils {
	public static String speedMode = "bhop";
	public static String phaseMode = "latest";
	public static String auramode = "New";
	public static boolean espP = true;
	public static boolean espM = false;
	public static boolean espA = false;
	public static boolean tracerP = true;
	public static boolean tracerM = false;
	public static String longJumpMode = "long";
	public static boolean tracerA = false;
	public static boolean bHit = true;
	public static boolean auraP = true;
	public static boolean auraM = false;
	public static boolean auraA = false;

	public static boolean isValidForESP(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			return espP;
		}
		if (entity instanceof EntityMob || entity instanceof EntitySlime) {
			return espM;
		}
		return (entity instanceof EntityCreature || entity instanceof EntitySquid || entity instanceof EntityBat
				|| entity instanceof EntityVillager) && espA;
	}

	public static boolean isValidForTracers(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer) {
			return tracerP;
		}
		if (entity instanceof EntityMob || entity instanceof EntitySlime) {
			return tracerM;
		}
		return (entity instanceof EntityCreature || entity instanceof EntitySquid || entity instanceof EntityBat
				|| entity instanceof EntityVillager) && tracerA;
	}

	
    public static boolean isValidForAura(final Entity entity) {
        if (entity == Minecraft.getMinecraft().thePlayer || Neptune.getWinter().friendUtils.isFriend(entity.getName()) || entity.isInvisible() || !entity.isEntityAlive()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            return ModeUtils.auraP;
        }
        if (entity instanceof EntityMob || entity instanceof EntitySlime) {
            return ModeUtils.auraM;
        }
        return (entity instanceof EntityCreature || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager) && ModeUtils.auraA;
    }
}
