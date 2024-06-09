/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod.mods;

import Monix.Category.Category;
import Monix.Event.EventTarget;
import Monix.Event.events.EventUpdate;
import Monix.Mod.Mod;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class AimBot
extends Mod {
    private int ticks = 0;

    public AimBot() {
        super("AimBot", "AimBot", 0, Category.COMBAT);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (this.isToggled()) {
            ++this.ticks;
            if (this.ticks >= 20 - this.speed()) {
                this.ticks = 0;
                for (Object theObject : AimBot.mc.theWorld.loadedEntityList) {
                    EntityLivingBase entity;
                    if (!(theObject instanceof EntityLivingBase) || (entity = (EntityLivingBase)theObject) instanceof EntityPlayerSP || AimBot.mc.thePlayer.getDistanceToEntity(entity) > 30.0f) continue;
                    if (entity.isInvisible()) break;
                    if (!entity.isEntityAlive()) continue;
                    AimBot.faceEntity(entity);
                }
            }
        }
    }

    public static synchronized void faceEntity(EntityLivingBase entity) {
        float[] rotations = AimBot.getRotationsNeeded(entity);
        if (rotations != null) {
            AimBot.mc.thePlayer.rotationYaw = rotations[0];
            AimBot.mc.thePlayer.rotationPitch = rotations[1] + 8.0f;
        }
    }

    private static float[] getRotationsNeeded(EntityLivingBase entity) {
        double diffY;
        if (entity == null) {
            return null;
        }
        double diffX = entity.posX - AimBot.mc.thePlayer.posX;
        double diffZ = entity.posZ - AimBot.mc.thePlayer.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = entity;
            diffY = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() - (AimBot.mc.thePlayer.posY + (double)AimBot.mc.thePlayer.getEyeHeight());
        } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0 - (AimBot.mc.thePlayer.posY + (double)AimBot.mc.thePlayer.getEyeHeight());
        }
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)(- Math.atan2(diffY, dist) * 180.0 / 3.141592653589793);
        return new float[]{AimBot.mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - AimBot.mc.thePlayer.rotationYaw), AimBot.mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - AimBot.mc.thePlayer.rotationPitch)};
    }

    private int speed() {
        return 18;
    }
}

