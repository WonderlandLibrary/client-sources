// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.Badlion;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;

public class ModeUtils
{
    public static boolean espP;
    public static boolean tracerP;
    public static boolean bHit;
    public static boolean auraP;
    public static boolean auraM;
    public static boolean auraA;
    
    static {
        ModeUtils.espP = true;
        ModeUtils.tracerP = true;
        ModeUtils.bHit = true;
        ModeUtils.auraP = true;
        ModeUtils.auraM = true;
        ModeUtils.auraA = true;
    }
    
    public static boolean isValidForESP(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return ModeUtils.espP;
        }
        return entity instanceof EntityCreature || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager;
    }
    
    public static boolean isValidForTracers(final EntityLivingBase entity) {
        if (entity instanceof EntityPlayer) {
            return ModeUtils.tracerP;
        }
        return entity instanceof EntityCreature || entity instanceof EntitySquid || entity instanceof EntityBat || entity instanceof EntityVillager;
    }
    
    public static boolean isValidForAura(final Entity entity) {
        if (entity == Minecraft.getMinecraft().thePlayer || Badlion.getWinter().friendUtils.isFriend(entity.getName()) || entity.isInvisible() || !entity.isEntityAlive()) {
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
