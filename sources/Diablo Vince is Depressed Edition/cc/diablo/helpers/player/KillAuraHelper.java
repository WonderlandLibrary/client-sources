/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.helpers.player;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.manager.friend.FriendManager;
import cc.diablo.manager.module.ModuleManager;
import cc.diablo.module.impl.combat.KillAura;
import cc.diablo.module.impl.player.Scaffold;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class KillAuraHelper {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static EntityLivingBase getClosestEntity(double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (Entity entity : Minecraft.theWorld.loadedEntityList) {
            double currentDist;
            EntityLivingBase player;
            if (!(entity instanceof EntityLivingBase) || !KillAuraHelper.canAttack(player = (EntityLivingBase)entity) || FriendManager.isFriend(entity.getName()) || !((currentDist = (double)KillAuraHelper.mc.thePlayer.getDistanceToEntity(player)) <= dist)) continue;
            dist = currentDist;
            target = player;
        }
        return target;
    }

    public static EntityLivingBase getClosestPlayerEntity(double range) {
        double dist = range;
        EntityPlayer target = null;
        for (EntityPlayer object : Minecraft.theWorld.playerEntities) {
            double currentDist;
            if (!KillAuraHelper.canAttack(object) || FriendManager.isFriend(object.getName()) || !((currentDist = (double)KillAuraHelper.mc.thePlayer.getDistanceToEntity(object)) <= dist)) continue;
            dist = currentDist;
            target = object;
        }
        return target;
    }

    public static EntityLivingBase getHealth(double range) {
        double health = range;
        EntityLivingBase target = null;
        for (Entity object : Minecraft.theWorld.loadedEntityList) {
            double currentHealth;
            EntityLivingBase player;
            if (!(object instanceof EntityLivingBase) || !KillAuraHelper.canAttack(player = (EntityLivingBase)object) || FriendManager.isFriend(object.getName()) || !((currentHealth = (double)player.getHealth()) <= health)) continue;
            health = currentHealth;
            target = player;
        }
        return target;
    }

    public static float[] getAngles(Entity e) {
        return new float[]{KillAuraHelper.getYawChangeToEntity(e) + KillAuraHelper.mc.thePlayer.rotationYaw, KillAuraHelper.getPitchChangeToEntity(e) + KillAuraHelper.mc.thePlayer.rotationPitch};
    }

    public static float getYawChangeToEntity(Entity entity) {
        double deltaX = entity.posX - KillAuraHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - KillAuraHelper.mc.thePlayer.posZ;
        double yawToEntity = deltaZ < 0.0 && deltaX < 0.0 ? 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : (deltaZ < 0.0 && deltaX > 0.0 ? -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX)) : Math.toDegrees(-Math.atan(deltaX / deltaZ)));
        return Double.isNaN((double)KillAuraHelper.mc.thePlayer.rotationYaw - yawToEntity) ? 0.0f : MathHelper.wrapAngleTo180_float(-(KillAuraHelper.mc.thePlayer.rotationYaw - (float)yawToEntity));
    }

    public static float getPitchChangeToEntity(Entity entity) {
        double distanceXZ;
        double deltaX = entity.posX - KillAuraHelper.mc.thePlayer.posX;
        double deltaZ = entity.posZ - KillAuraHelper.mc.thePlayer.posZ;
        double deltaY = entity.posY - 1.6 + (double)entity.getEyeHeight() - KillAuraHelper.mc.thePlayer.posY;
        double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / (distanceXZ = (double)MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ))));
        return Double.isNaN((double)KillAuraHelper.mc.thePlayer.rotationPitch - pitchToEntity) ? 0.0f : -MathHelper.wrapAngleTo180_float(KillAuraHelper.mc.thePlayer.rotationPitch - (float)pitchToEntity);
    }

    public static void setRotations(UpdateEvent e, float yaw, float pitch) {
        e.setRotationYaw(yaw);
        e.setRotationPitch(pitch);
        KillAuraHelper.mc.thePlayer.rotationYawHead = yaw;
        KillAuraHelper.mc.thePlayer.rotationPitchHead = pitch;
        KillAuraHelper.mc.thePlayer.renderYawOffset = yaw;
    }

    public static void setYaw(UpdateEvent e, float yaw) {
        e.setRotationYaw(yaw);
        KillAuraHelper.mc.thePlayer.rotationYawHead = yaw;
    }

    public static void setPitch(UpdateEvent e, float pitch) {
        e.setRotationPitch(pitch);
        KillAuraHelper.mc.thePlayer.rotationPitchHead = pitch;
    }

    public static void setRotationsPacket(float yaw, float pitch) {
        KillAuraHelper.mc.thePlayer.rotationYawHead = yaw;
        KillAuraHelper.mc.thePlayer.rotationPitchHead = pitch;
        KillAuraHelper.mc.thePlayer.renderYawOffset = yaw;
        PacketHelper.sendPacketNoEvent(new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, KillAuraHelper.mc.thePlayer.onGround));
    }

    public static EntityLivingBase getTarget() {
        return KillAura.target;
    }

    public static boolean canAttack(Entity entity) {
        if (!(entity.isInvisible() && !KillAura.targetInvis.isChecked() || ModuleManager.getModule(Scaffold.class).isToggled())) {
            return entity != KillAuraHelper.mc.thePlayer && entity.isEntityAlive() && KillAuraHelper.mc.thePlayer != null && Minecraft.theWorld != null && KillAuraHelper.mc.thePlayer.ticksExisted > 30 && entity.ticksExisted > 15;
        }
        return false;
    }

    public static float[] getPredictedRotations(EntityLivingBase ent) {
        double x = ent.posX + (ent.posX - ent.lastTickPosX);
        double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
        double y = ent.posY + (double)(ent.getEyeHeight() / 2.0f);
        return KillAuraHelper.getRotationFromPosition(x, z, y);
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / Math.PI));
        return new float[]{yaw, pitch};
    }

    public static float getSensitivityMultiplier() {
        float f = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        return f * f * f * 8.0f * 0.15f;
    }
}

