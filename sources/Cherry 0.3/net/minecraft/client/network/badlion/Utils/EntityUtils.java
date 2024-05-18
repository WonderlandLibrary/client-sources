// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import java.util.Iterator;
import net.minecraft.Badlion;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.potion.Potion;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.client.Minecraft;

public class EntityUtils
{
    public static Minecraft mc;
    
    static {
        EntityUtils.mc = Minecraft.getMinecraft();
    }
    
    public static void blinkToPos(final double[] startPos, final BlockPos endPos, final double slack, final double[] pOffset) {
        double curX = startPos[0];
        double curY = startPos[1];
        double curZ = startPos[2];
        final double endX = endPos.getX();
        final double endY = endPos.getY();
        final double endZ = endPos.getZ();
        double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
        int count = 0;
        while (distance > slack) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if (count > 120) {
                break;
            }
            final boolean next = false;
            final double diffX = curX - endX;
            final double diffY = curY - endY;
            final double diffZ = curZ - endZ;
            final double offset = ((count & 0x1) == 0x0) ? pOffset[0] : pOffset[1];
            if (diffX < 0.0) {
                if (Math.abs(diffX) > offset) {
                    curX += offset;
                }
                else {
                    curX += Math.abs(diffX);
                }
            }
            if (diffX > 0.0) {
                if (Math.abs(diffX) > offset) {
                    curX -= offset;
                }
                else {
                    curX -= Math.abs(diffX);
                }
            }
            if (diffY < 0.0) {
                if (Math.abs(diffY) > 0.25) {
                    curY += 0.25;
                }
                else {
                    curY += Math.abs(diffY);
                }
            }
            if (diffY > 0.0) {
                if (Math.abs(diffY) > 0.25) {
                    curY -= 0.25;
                }
                else {
                    curY -= Math.abs(diffY);
                }
            }
            if (diffZ < 0.0) {
                if (Math.abs(diffZ) > offset) {
                    curZ += offset;
                }
                else {
                    curZ += Math.abs(diffZ);
                }
            }
            if (diffZ > 0.0) {
                if (Math.abs(diffZ) > offset) {
                    curZ -= offset;
                }
                else {
                    curZ -= Math.abs(diffZ);
                }
            }
            NetUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
            ++count;
        }
    }
    
    public static void critical() {
        final double posY = Minecraft.getMinecraft().thePlayer.posY;
        EntityUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityUtils.mc.thePlayer.posX, EntityUtils.mc.thePlayer.posY + 0.0625, EntityUtils.mc.thePlayer.posZ, false));
        EntityUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityUtils.mc.thePlayer.posX, EntityUtils.mc.thePlayer.posY, EntityUtils.mc.thePlayer.posZ, false));
        EntityUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityUtils.mc.thePlayer.posX, EntityUtils.mc.thePlayer.posY + 0.001, EntityUtils.mc.thePlayer.posZ, false));
        EntityUtils.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(EntityUtils.mc.thePlayer.posX, EntityUtils.mc.thePlayer.posY, EntityUtils.mc.thePlayer.posZ, false));
    }
    
    public static void attackEntity(final EntityLivingBase entity, final boolean crit) {
        Minecraft.getMinecraft().thePlayer.swingItem();
        if (crit) {
            critical();
        }
        final float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.getMinecraft().thePlayer.getHeldItem(), entity.getCreatureAttribute());
        final boolean vanillaCrit = Minecraft.getMinecraft().thePlayer.fallDistance > 0.0f && !Minecraft.getMinecraft().thePlayer.onGround && !Minecraft.getMinecraft().thePlayer.isOnLadder() && !Minecraft.getMinecraft().thePlayer.isInWater() && !Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.blindness) && Minecraft.getMinecraft().thePlayer.ridingEntity == null;
        NetUtils.sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
        if (crit || vanillaCrit) {
            Minecraft.getMinecraft().thePlayer.onCriticalHit(entity);
        }
        if (sharpLevel > 0.0f) {
            Minecraft.getMinecraft().thePlayer.onEnchantmentCritical(entity);
        }
    }
    
    public static void damagePlayer(int damage) {
        if (damage < 1) {
            damage = 1;
        }
        if (damage > MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getMaxHealth())) {
            damage = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getMaxHealth());
        }
        final double offset = 0.0625;
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().getNetHandler() != null && Minecraft.getMinecraft().thePlayer.onGround) {
            for (int i = 0; i <= (3 + damage) / 0.0625; ++i) {
                NetUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.0625, Minecraft.getMinecraft().thePlayer.posZ, false));
                NetUtils.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY, Minecraft.getMinecraft().thePlayer.posZ, i == (3 + damage) / 0.0625));
            }
        }
    }
    
    public static EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase en = (EntityLivingBase)o;
                if (o instanceof EntityPlayerSP || en.isDead || en.getHealth() <= 0.0f || en.getName().equals(Minecraft.getMinecraft().thePlayer.getName()) || Badlion.getWinter().friendUtils.isFriend(en.getName())) {
                    continue;
                }
                if (!ModeUtils.isValidForAura(en)) {
                    continue;
                }
                if (closestEntity != null && Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) >= Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity)) {
                    continue;
                }
                closestEntity = en;
            }
        }
        return closestEntity;
    }
    
    public static EntityLivingBase getSwitchEntity() {
        EntityLivingBase closestEntity = null;
        for (final Object o : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            if (o instanceof EntityLivingBase) {
                final EntityLivingBase en = (EntityLivingBase)o;
                if (o instanceof EntityPlayerSP || en.isDead || en.getHealth() <= 0.0f || en.getName().equals(Minecraft.getMinecraft().thePlayer.getName()) || Badlion.getWinter().friendUtils.isFriend(en.getName())) {
                    continue;
                }
                if (!ModeUtils.isValidForAura(en)) {
                    continue;
                }
                if (closestEntity != null && Minecraft.getMinecraft().thePlayer.getDistanceToEntity(en) >= Minecraft.getMinecraft().thePlayer.getDistanceToEntity(closestEntity)) {
                    continue;
                }
                closestEntity = en;
            }
        }
        return closestEntity;
    }
    
    public static EntityLivingBase findClosestEntity() {
        double distance = Double.MAX_VALUE;
        EntityLivingBase entity = null;
        for (final Object object : EntityUtils.mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase)object;
                if (e.getDistanceToEntity(EntityUtils.mc.thePlayer) >= distance) {
                    continue;
                }
                if (!isValid(e)) {
                    continue;
                }
                entity = e;
                distance = e.getDistanceToEntity(EntityUtils.mc.thePlayer);
            }
        }
        return entity;
    }
    
    public static EntityLivingBase findTarget() {
        double distance = Double.MAX_VALUE;
        EntityLivingBase entity = null;
        for (final Object object : EntityUtils.mc.theWorld.loadedEntityList) {
            if (object instanceof EntityLivingBase) {
                final EntityLivingBase e = (EntityLivingBase)object;
                if (e.getDistanceToEntity(EntityUtils.mc.thePlayer) >= distance) {
                    continue;
                }
                if (!isValid(e)) {
                    continue;
                }
                entity = e;
                distance = e.getDistanceToEntity(EntityUtils.mc.thePlayer);
            }
        }
        return entity;
    }
    
    public static boolean isValid(final EntityLivingBase entity) {
        return entity != null && entity != EntityUtils.mc.thePlayer && entity.getDistanceToEntity(EntityUtils.mc.thePlayer) <= 10000.0f && entity.isEntityAlive() && (!entity.isInvisible() || entity.getTotalArmorValue() != 0) && !Badlion.getWinter().friendUtils.isFriend(entity.getName());
    }
    
    public static boolean isMemed(final EntityLivingBase entity) {
        return entity != null && entity != EntityUtils.mc.thePlayer && entity.getDistanceToEntity(EntityUtils.mc.thePlayer) <= 8.0f && entity.isEntityAlive() && (!entity.isInvisible() || entity.getTotalArmorValue() != 0) && !Badlion.getWinter().friendUtils.isFriend(entity.getName());
    }
}
