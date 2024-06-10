// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.EntityHelper;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;

public class GunAura extends Module
{
    public final Value<Float> reach;
    private final Value<Boolean> silent;
    
    public GunAura() {
        super("GunAura", -57344, Category.COMBAT);
        this.reach = (Value<Float>)new ConstrainedValue("gunaura_Reach", "reach", 32.0f, 8.0f, 64.0f, this);
        this.silent = new Value<Boolean>("gunaura_Silent", "silent", false, this);
        this.setTag("Gun Aura");
    }
    
    private float[] getYawAndPitch2(final Entity paramEntityPlayer) {
        final double d1 = paramEntityPlayer.posX - GunAura.mc.thePlayer.posX;
        final double d2 = paramEntityPlayer.posZ - GunAura.mc.thePlayer.posZ;
        final double d3 = GunAura.mc.thePlayer.posY + 0.12 - (paramEntityPlayer.posY + 1.82);
        final double d4 = MathHelper.sqrt_double(d1 + d2);
        final float f1 = (float)(Math.atan2(d2, d1) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(Math.atan2(d3, d4) * 180.0 / 3.141592653589793);
        return new float[] { f1, f2 };
    }
    
    private float getDistanceBetweenAngles2(final float paramFloat) {
        float f = Math.abs(paramFloat - GunAura.mc.thePlayer.rotationYaw) % 360.0f;
        if (f > 180.0f) {
            f = 360.0f - f;
        }
        return f;
    }
    
    public boolean isValidTarget(final Entity entity) {
        boolean valid = false;
        if (entity == GunAura.mc.thePlayer.ridingEntity) {
            return false;
        }
        final KillAura aura = (KillAura)Client.getModuleManager().getModuleByName("killaura");
        if (aura.isValidTarget(entity) && aura.isEnabled()) {
            return false;
        }
        try {
            if (!GunAura.mc.thePlayer.canEntityBeSeen(entity)) {
                return false;
            }
        }
        catch (Exception ex) {}
        final float[] arrayOfFloat = this.getYawAndPitch2(entity);
        final double d2g = this.getDistanceBetweenAngles2(arrayOfFloat[0]);
        final double dooble = GunAura.mc.isSingleplayer() ? 180.0 : 40.0;
        final Teams teams = (Teams)Client.getModuleManager().getModuleByName("teams");
        if (entity.isInvisible()) {
            valid = true;
        }
        if (teams.isEnabled() && entity instanceof EntityPlayer && teams.getTabName((EntityPlayer)entity).length() > 2 && teams.getTabName(GunAura.mc.thePlayer).startsWith(teams.getTabName((EntityPlayer)entity).substring(0, 2))) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            valid = (entity != null && GunAura.mc.thePlayer.getDistanceToEntity(entity) <= this.reach.getValue() && entity != GunAura.mc.thePlayer && entity.isEntityAlive() && !Client.getFriendManager().isFriend(entity.getName()));
        }
        return valid;
    }
    
    public EntityLivingBase getCursorEntity() {
        EntityLivingBase poorEntity = null;
        double distanceToEntity = 1000.0;
        for (final Object o : GunAura.mc.theWorld.loadedEntityList) {
            if (o instanceof Entity) {
                final Entity targetEntity = (Entity)o;
                if (!(targetEntity instanceof EntityPlayer) || targetEntity == GunAura.mc.thePlayer || !GunAura.mc.thePlayer.canEntityBeSeen(targetEntity) || Client.getFriendManager().isFriend(targetEntity.getName()) || ((EntityLivingBase)targetEntity).deathTime > 0) {
                    continue;
                }
                if (poorEntity == null) {
                    poorEntity = (EntityLivingBase)targetEntity;
                }
                final double xDistance = targetEntity.posX - GunAura.mc.thePlayer.posX;
                final double zDistance = targetEntity.posZ - GunAura.mc.thePlayer.posZ;
                final double eyeDistance = GunAura.mc.thePlayer.posY + GunAura.mc.thePlayer.getEyeHeight() - targetEntity.posY;
                final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                final float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final float trajectoryTheta91 = (float)(Math.atan2(eyeDistance, trajectoryXZ) * 180.0 / 3.141592653589793);
                final double xAngleDistance = this.getDistanceBetweenAngles(trajectoryTheta90, GunAura.mc.thePlayer.rotationYaw % 360.0f);
                final double yAngleDistance = this.getDistanceBetweenAngles(trajectoryTheta91, GunAura.mc.thePlayer.rotationPitch % 360.0f);
                final double entityDistance = Math.sqrt(xAngleDistance * xAngleDistance + yAngleDistance * yAngleDistance);
                if (entityDistance >= distanceToEntity) {
                    continue;
                }
                poorEntity = (EntityLivingBase)targetEntity;
                distanceToEntity = entityDistance;
            }
        }
        return poorEntity;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof PreMotion) {
            final PreMotion pre = (PreMotion)event;
            final EntityLivingBase entity = this.getCursorEntity();
            if (GunAura.mc.thePlayer != null && entity != null && (GunAura.mc.gameSettings.keyBindUseItem.getIsKeyPressed() || GunAura.mc.gameSettings.keyBindPickBlock.getIsKeyPressed()) && this.isValidTarget(entity) && GunAura.mc.thePlayer.getHeldItem() != null && GunAura.mc.thePlayer.getHeldItem().getItem() != null) {
                final double xDistance = entity.posX - GunAura.mc.thePlayer.posX + (entity.posX - entity.lastTickPosX) * 8.0;
                final double zDistance = entity.posZ - GunAura.mc.thePlayer.posZ + (entity.posZ - entity.lastTickPosZ) * 8.0;
                final float[] rot = EntityHelper.getEntityRotations(GunAura.mc.thePlayer, entity);
                final float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final HealingBot healingBot = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
                if (!healingBot.isHealing()) {
                    pre.setYaw(trajectoryTheta90);
                    pre.setPitch(rot[1]);
                }
                if (!this.silent.getValue()) {
                    GunAura.mc.thePlayer.rotationYaw = trajectoryTheta90;
                    GunAura.mc.thePlayer.rotationPitch = rot[1];
                }
            }
        }
        else if (event instanceof SentPacket) {
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final EntityLivingBase entity = this.getCursorEntity();
                if (GunAura.mc.thePlayer != null && entity != null && (GunAura.mc.gameSettings.keyBindUseItem.getIsKeyPressed() || GunAura.mc.gameSettings.keyBindPickBlock.getIsKeyPressed()) && GunAura.mc.thePlayer.isUsingItem() && this.isValidTarget(entity) && GunAura.mc.thePlayer.getHeldItem() != null && GunAura.mc.thePlayer.getHeldItem().getItem() != null) {
                    final double xDistance = entity.posX - GunAura.mc.thePlayer.posX + (entity.posX - entity.lastTickPosX) * 8.0;
                    final double zDistance = entity.posZ - GunAura.mc.thePlayer.posZ + (entity.posZ - entity.lastTickPosZ) * 8.0;
                    final double eyeDistance = entity.posY + entity.getEyeHeight() - (GunAura.mc.thePlayer.posY + GunAura.mc.thePlayer.getEyeHeight());
                    final float trajectoryTheta91 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                    final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                    final float[] rot2 = EntityHelper.getEntityRotations(GunAura.mc.thePlayer, entity);
                    final HealingBot healingBot2 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
                    if (!healingBot2.isHealing() && player.rotating) {
                        player.yaw = trajectoryTheta91;
                        player.pitch = rot2[1];
                    }
                }
            }
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
    }
    
    private float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angleToEntity = Math.abs(angle1 - angle2) % 360.0f;
        if (angleToEntity > 180.0f) {
            angleToEntity = 360.0f - angleToEntity;
        }
        return angleToEntity;
    }
    
    private float getTrajectoryAngleSolutionLow(final float angleX, final float angleY, final float bowVelocity) {
        final float velocityIncrement = 0.006f;
        final float squareRootBow = bowVelocity * bowVelocity * bowVelocity * bowVelocity - velocityIncrement * (velocityIncrement * (angleX * angleX) + 2.0f * angleY * (bowVelocity * bowVelocity));
        return (float)Math.toDegrees(Math.atan((bowVelocity * bowVelocity - Math.sqrt(squareRootBow)) / (velocityIncrement * angleX)));
    }
}
