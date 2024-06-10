// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.item.ItemBow;

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
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.Entity;

public class BowAimbot extends Module
{
    public final Value<Float> reach;
    private final Value<Boolean> silent;
    private final Value<Boolean> players;
    private final Value<Boolean> animals;
    private final Value<Boolean> mobs;
    
    public BowAimbot() {
        super("BowAimbot", -2263808, Category.COMBAT);
        this.reach = (Value<Float>)new ConstrainedValue("bowaimbot_Reach", "reach", 32.0f, 8.0f, 64.0f, this);
        this.silent = new Value<Boolean>("bowaimbot_Silent", "silent", false, this);
        this.players = new Value<Boolean>("bowaimbot_Players", "players", true, this);
        this.animals = new Value<Boolean>("bowaimbot_Animals", "animals", false, this);
        this.mobs = new Value<Boolean>("bowaimbot_Mobs", "mobs", false, this);
        this.setTag("Bow Aimbot");
    }
    
    private float[] getYawAndPitch2(final Entity paramEntityPlayer) {
        final double d1 = paramEntityPlayer.posX - BowAimbot.mc.thePlayer.posX;
        final double d2 = paramEntityPlayer.posZ - BowAimbot.mc.thePlayer.posZ;
        final double d3 = BowAimbot.mc.thePlayer.posY + 0.12 - (paramEntityPlayer.posY + 1.82);
        final double d4 = MathHelper.sqrt_double(d1 + d2);
        final float f1 = (float)(Math.atan2(d2, d1) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(Math.atan2(d3, d4) * 180.0 / 3.141592653589793);
        return new float[] { f1, f2 };
    }
    
    private float getDistanceBetweenAngles2(final float paramFloat) {
        float f = Math.abs(paramFloat - BowAimbot.mc.thePlayer.rotationYaw) % 360.0f;
        if (f > 180.0f) {
            f = 360.0f - f;
        }
        return f;
    }
    
    public boolean isValidTarget(final Entity entity) {
        boolean valid = false;
        if (entity == BowAimbot.mc.thePlayer.ridingEntity) {
            return false;
        }
        try {
            if (!BowAimbot.mc.thePlayer.canEntityBeSeen(entity)) {
                return false;
            }
        }
        catch (Exception ex) {}
        final float[] arrayOfFloat = this.getYawAndPitch2(entity);
        final double d2g = this.getDistanceBetweenAngles2(arrayOfFloat[0]);
        final double dooble = BowAimbot.mc.isSingleplayer() ? 180.0 : 40.0;
        final Teams teams = (Teams)Client.getModuleManager().getModuleByName("teams");
        if (entity.isInvisible()) {
            valid = true;
        }
        if (teams.isEnabled() && entity instanceof EntityPlayer && teams.getTabName((EntityPlayer)entity).length() > 2 && teams.getTabName(BowAimbot.mc.thePlayer).startsWith(teams.getTabName((EntityPlayer)entity).substring(0, 2))) {
            return false;
        }
        if (entity instanceof EntityPlayer && this.players.getValue()) {
            valid = (entity != null && BowAimbot.mc.thePlayer.getDistanceToEntity(entity) <= this.reach.getValue() && entity != BowAimbot.mc.thePlayer && entity.isEntityAlive() && !Client.getFriendManager().isFriend(entity.getName()));
        }
        else if (entity instanceof IMob && this.mobs.getValue()) {
            valid = (entity != null && BowAimbot.mc.thePlayer.getDistanceToEntity(entity) <= this.reach.getValue() && entity.isEntityAlive());
        }
        else if (entity instanceof IAnimals && !(entity instanceof IMob) && this.animals.getValue()) {
            valid = (entity != null && BowAimbot.mc.thePlayer.getDistanceToEntity(entity) <= this.reach.getValue() && entity.isEntityAlive());
        }
        return valid;
    }
    
    public EntityLivingBase getCursorEntity() {
        EntityLivingBase poorEntity = null;
        double distanceToEntity = 1000.0;
        for (final Object o : BowAimbot.mc.theWorld.loadedEntityList) {
            if (o instanceof Entity) {
                final Entity targetEntity = (Entity)o;
                if (!(targetEntity instanceof EntityPlayer) || targetEntity == BowAimbot.mc.thePlayer || !BowAimbot.mc.thePlayer.canEntityBeSeen(targetEntity) || Client.getFriendManager().isFriend(targetEntity.getName()) || ((EntityLivingBase)targetEntity).deathTime > 0) {
                    continue;
                }
                if (poorEntity == null) {
                    poorEntity = (EntityLivingBase)targetEntity;
                }
                final double xDistance = targetEntity.posX - BowAimbot.mc.thePlayer.posX;
                final double zDistance = targetEntity.posZ - BowAimbot.mc.thePlayer.posZ;
                final double eyeDistance = BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight() - targetEntity.posY;
                final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                final float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final float trajectoryTheta91 = (float)(Math.atan2(eyeDistance, trajectoryXZ) * 180.0 / 3.141592653589793);
                final double xAngleDistance = this.getDistanceBetweenAngles(trajectoryTheta90, BowAimbot.mc.thePlayer.rotationYaw % 360.0f);
                final double yAngleDistance = this.getDistanceBetweenAngles(trajectoryTheta91, BowAimbot.mc.thePlayer.rotationPitch % 360.0f);
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
            if (BowAimbot.mc.thePlayer != null && entity != null && this.isValidTarget(entity) && BowAimbot.mc.thePlayer.isUsingItem() && BowAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() != null && BowAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                final int bowCurrentCharge = BowAimbot.mc.thePlayer.getItemInUseDuration();
                float bowVelocity = bowCurrentCharge / 20.0f;
                bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
                if (bowVelocity < 0.1) {
                    return;
                }
                if (bowVelocity > 1.0f) {
                    bowVelocity = 1.0f;
                }
                final double xDistance = entity.posX - BowAimbot.mc.thePlayer.posX + (entity.posX - entity.lastTickPosX) * ((float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity) * 10.0f);
                final double zDistance = entity.posZ - BowAimbot.mc.thePlayer.posZ + (entity.posZ - entity.lastTickPosZ) * ((float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity) * 10.0f);
                final double eyeDistance = entity.posY + entity.getEyeHeight() - (BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight() + 0.6000000238418579);
                final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                final double eyeTrajectoryXZ = Math.sqrt(trajectoryXZ * trajectoryXZ + eyeDistance * eyeDistance);
                final float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                final float bowTrajectory = -this.getTrajectoryAngleSolutionLow((float)trajectoryXZ, (float)eyeDistance, (float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity));
                final HealingBot healingBot = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
                if (!healingBot.isHealing()) {
                    pre.setYaw(trajectoryTheta90);
                    pre.setPitch(bowTrajectory);
                }
                if (!this.silent.getValue()) {
                    BowAimbot.mc.thePlayer.rotationYaw = trajectoryTheta90;
                    BowAimbot.mc.thePlayer.rotationPitch = bowTrajectory;
                }
            }
        }
        else if (event instanceof SentPacket) {
            final SentPacket sent = (SentPacket)event;
            if (sent.getPacket() instanceof C03PacketPlayer) {
                final EntityLivingBase entity = this.getCursorEntity();
                if (BowAimbot.mc.thePlayer != null && entity != null && this.isValidTarget(entity) && BowAimbot.mc.thePlayer.isUsingItem() && BowAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() != null && BowAimbot.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                    final int bowCurrentCharge = BowAimbot.mc.thePlayer.getItemInUseDuration();
                    float bowVelocity = bowCurrentCharge / 20.0f;
                    bowVelocity = (bowVelocity * bowVelocity + bowVelocity * 2.0f) / 3.0f;
                    if (bowVelocity < 0.1) {
                        return;
                    }
                    if (bowVelocity > 1.0f) {
                        bowVelocity = 1.0f;
                    }
                    final double xDistance = entity.posX - BowAimbot.mc.thePlayer.posX + (entity.posX - entity.lastTickPosX) * ((float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity) * 10.0f);
                    final double zDistance = entity.posZ - BowAimbot.mc.thePlayer.posZ + (entity.posZ - entity.lastTickPosZ) * ((float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity) * 10.0f);
                    final double eyeDistance = entity.posY + entity.getEyeHeight() - (BowAimbot.mc.thePlayer.posY + BowAimbot.mc.thePlayer.getEyeHeight() + 0.6000000238418579);
                    final double trajectoryXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
                    final double eyeTrajectoryXZ = Math.sqrt(trajectoryXZ * trajectoryXZ + eyeDistance * eyeDistance);
                    final float trajectoryTheta90 = (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
                    final float bowTrajectory = -this.getTrajectoryAngleSolutionLow((float)trajectoryXZ, (float)eyeDistance, (float)(Client.getModuleManager().getModuleByName("instantuse").isEnabled() ? 1.0 : bowVelocity));
                    final C03PacketPlayer player = (C03PacketPlayer)sent.getPacket();
                    final HealingBot healingBot2 = (HealingBot)Client.getModuleManager().getModuleByName("healingbot");
                    if (!healingBot2.isHealing() && player.rotating) {
                        player.yaw = trajectoryTheta90;
                        player.pitch = bowTrajectory;
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
