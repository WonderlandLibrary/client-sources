/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import ru.govno.client.Client;
import ru.govno.client.event.EventTarget;
import ru.govno.client.event.events.EventPlayerMotionUpdate;
import ru.govno.client.module.Module;
import ru.govno.client.module.modules.AWP;
import ru.govno.client.module.modules.AntiBot;
import ru.govno.client.module.modules.HitAura;
import ru.govno.client.module.settings.Settings;
import ru.govno.client.utils.Combat.GCDFix;
import ru.govno.client.utils.Math.MathUtils;
import ru.govno.client.utils.RandomUtils;

public class BowAimbot
extends Module {
    public static BowAimbot get;
    Settings ShotTo = new Settings("ShotTo", "Chestplate", (Module)this, new String[]{"Boots", "Leggings", "Chestplate", "Helmet"});
    Settings Range;
    Settings Walls;
    public static float yaw;
    public static float pitch;
    private boolean doRotate = false;
    public static EntityLivingBase target;

    public BowAimbot() {
        super("BowAimbot", 0, Module.Category.COMBAT);
        this.settings.add(this.ShotTo);
        this.Range = new Settings("Range", 25.0f, 50.0f, 3.0f, this);
        this.settings.add(this.Range);
        this.Walls = new Settings("Walls", false, (Module)this);
        this.settings.add(this.Walls);
        get = this;
    }

    private float theta(double v, double g, double x, double y) {
        double yv = 2.0 * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = v * v * v * v - g2;
        double sqrt = Math.sqrt(insqrt);
        double numerator = v * v + sqrt;
        double numerator2 = v * v - sqrt;
        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);
        return (float)Math.min(atan1, atan2);
    }

    private float getLaunchAngle(EntityLivingBase entity, double v, double g) {
        String mode = this.ShotTo.currentMode;
        float pc = mode.equalsIgnoreCase("Boots") ? entity.getEyeHeight() / 8.0f : (mode.equalsIgnoreCase("Leggings") ? entity.getEyeHeight() / 3.0f : (mode.equalsIgnoreCase("Chestplate") ? entity.getEyeHeight() / 1.85f : (mode.equalsIgnoreCase("Helmet") ? entity.getEyeHeight() / 1.2f : entity.getEyeHeight())));
        double yDiff = entity.posY + (double)pc - (BowAimbot.getMe().posY + (double)BowAimbot.getMe().getEyeHeight());
        double xDiff = entity.posX - BowAimbot.getMe().posX;
        double zDiff = entity.posZ - BowAimbot.getMe().posZ;
        double xCoord = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        return this.theta(v + 2.0, g, xCoord, yDiff);
    }

    private float[] getYawPitch(EntityLivingBase entity) {
        float akb = (float)BowAimbot.getMe().getItemInUseMaxCount() / 20.0f;
        akb = (akb * akb + akb * 2.0f) / 3.0f;
        akb = MathHelper.clamp_float(akb, 0.0f, 1.0f);
        double v = akb * 3.0f;
        double g = 0.05f;
        if (akb > 1.0f) {
            akb = 1.0f;
        }
        float bowTr = (float)((double)((float)(-Math.toDegrees(this.getLaunchAngle(entity, v, g)))) - 3.8);
        float tickPlus = 0.5f;
        if (Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID()) != null) {
            NetworkPlayerInfo net = Minecraft.player.connection.getPlayerInfo(Minecraft.player.getUniqueID());
            tickPlus += net.getResponseTime() < 37 ? 1.0f : (float)net.getResponseTime() / 50.0f;
        }
        double diffX = entity.posX + (entity.lastTickPosX - entity.posX) * -((double)tickPlus / 2.35) - BowAimbot.getMe().posX;
        double diffZ = entity.posZ + (entity.lastTickPosX - entity.posX) * -((double)tickPlus / 2.35) - BowAimbot.getMe().posZ;
        float tThetaYaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / Math.PI - 90.0);
        tThetaYaw = Minecraft.player.rotationYaw + GCDFix.getFixedRotation(MathHelper.wrapAngleTo180_float(tThetaYaw - Minecraft.player.rotationYaw));
        return new float[]{tThetaYaw, bowTr};
    }

    private static EntityPlayer getMe() {
        return Minecraft.player;
    }

    private boolean entityIsCurrentToFilter(EntityLivingBase entity) {
        EntityPlayer player;
        return !(entity == null || entity.getHealth() == 0.0f || entity instanceof EntityPlayerSP || entity instanceof EntityArmorStand || !this.Walls.bValue && !BowAimbot.getMe().canEntityBeSeen(entity) || entity instanceof EntityPlayer && (player = (EntityPlayer)entity).isCreative() || AntiBot.entityIsBotAdded(entity) || !(BowAimbot.getMe().getDistanceToEntity(entity) <= this.Range.fValue) || Client.friendManager.isFriend(entity.getName()));
    }

    public final EntityLivingBase getCurrentTarget() {
        return BowAimbot.getMe().isBowing() && (HitAura.TARGET_ROTS == null || !HitAura.get.actived || HitAura.get.Rotation.currentMode.equalsIgnoreCase("None")) ? (EntityLivingBase)BowAimbot.mc.world.getLoadedEntityList().stream().map(Entity::getLivingBaseOf).filter(Objects::nonNull).filter(e -> this.entityIsCurrentToFilter((EntityLivingBase)e)).findFirst().orElse(null) : null;
    }

    public final EntityLivingBase getTarget() {
        return target;
    }

    public static float[] getVirt() {
        return new float[]{yaw, AWP.get.actived ? -1.0f : pitch};
    }

    private void virtRotate(EventPlayerMotionUpdate e, EntityLivingBase entity) {
        if (BowAimbot.getMe().isBowing() && this.entityIsCurrentToFilter(entity) && MathUtils.getDifferenceOf(Minecraft.player.rotationPitch, 0.0f) < 60.0) {
            this.doRotate = true;
            yaw += MathUtils.clamp(this.getYawPitch(entity)[0] - yaw, -45.0f, 45.0f);
            pitch += MathUtils.clamp(this.getYawPitch(entity)[1] - pitch, -15.0f, 15.0f);
            float f = BowAimbot.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float gcd = f * f * f * 1.2f + (float)RandomUtils.randomNumber((int)f, (int)(-f));
            yaw -= yaw % gcd % gcd;
            pitch -= pitch % gcd % gcd;
        } else if (MathUtils.getDifferenceOf(yaw, e.getYaw()) >= 1.0 && MathUtils.getDifferenceOf(pitch, e.getPitch()) >= 1.0 && this.doRotate) {
            yaw += MathUtils.clamp(e.getYaw() - yaw, -45.0f, 45.0f);
            pitch += MathUtils.clamp(e.getPitch() - pitch, -15.0f, 15.0f);
            float f = BowAimbot.mc.gameSettings.mouseSensitivity * 0.6f + 0.2f;
            float gcd = f * f * f * 1.2f + (float)RandomUtils.randomNumber((int)f, (int)(-f));
            yaw -= yaw % gcd % gcd;
            pitch -= pitch % gcd % gcd;
        } else {
            this.doRotate = false;
            yaw = e.getYaw();
            pitch = e.getPitch();
        }
    }

    private void rotate(EventPlayerMotionUpdate e) {
        e.setYaw(yaw);
        e.setPitch(pitch);
        BowAimbot.getMe().rotationYawHead = yaw;
        BowAimbot.getMe().renderYawOffset = yaw;
        BowAimbot.getMe().rotationPitchHead = pitch;
    }

    @EventTarget
    public void onPlayerMotionUpdate(EventPlayerMotionUpdate e) {
        if (!this.actived || BowAimbot.mc.world == null || BowAimbot.getMe() == null) {
            return;
        }
        target = this.getCurrentTarget();
        this.virtRotate(e, target);
        if (this.doRotate) {
            this.rotate(e);
        }
    }

    @Override
    public void onToggled(boolean actived) {
        target = null;
        super.onToggled(actived);
    }

    static {
        target = null;
    }
}

