/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 *  org.lwjgl.input.Mouse
 */
package cc.diablo.module.impl.ghost;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import cc.diablo.setting.impl.BooleanSetting;
import cc.diablo.setting.impl.NumberSetting;
import com.google.common.eventbus.Subscribe;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class AimAssistance
extends Module {
    private EntityLivingBase target;
    public NumberSetting speedAiming = new NumberSetting("Speed", 2.0, 0.5, 6.0, 0.1);
    public NumberSetting jitterRandom = new NumberSetting("RandomVal", 1.5, 0.0, 3.0, 0.25);
    public NumberSetting aimingDistance = new NumberSetting("Distance", 5.0, 0.5, 10.0, 0.5);
    public BooleanSetting rot_pitch = new BooleanSetting("Pitch", true);
    public BooleanSetting rot_yaw = new BooleanSetting("Yaw", true);
    public BooleanSetting lcCheck = new BooleanSetting("Click Aim", true);

    public AimAssistance() {
        super("AimAssist", "Made by Lxve <3", 0, Category.Combat);
        this.addSettings(this.speedAiming, this.jitterRandom, this.aimingDistance, this.rot_pitch, this.rot_yaw, this.lcCheck);
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        float random;
        this.setDisplayName("AimAssist\u00a77");
        this.target = KillAuraHelper.getClosestEntity(this.aimingDistance.getVal());
        float f = random = this.jitterRandom.getVal() != 0.0 ? AimAssistance.getRandomInRange((float)(-this.jitterRandom.getVal()), (float)this.jitterRandom.getVal()) : 0.0f;
        if ((!this.lcCheck.isChecked() || Mouse.isButtonDown((int)0)) && this.target != null && AimAssistance.mc.currentScreen == null) {
            if (this.rot_yaw.isChecked()) {
                AimAssistance.mc.thePlayer.rotationYaw = AimAssistance.getRotationsAimA(this.target, (float)(this.speedAiming.getVal() * 3.0) + random)[0] + random;
            }
            if (this.rot_pitch.isChecked()) {
                AimAssistance.mc.thePlayer.rotationPitch = AimAssistance.getRotationsAimA(this.target, (float)(this.speedAiming.getVal() * 3.0) + random)[1] + random;
            }
        }
    }

    public static float[] getRotationsAimA(EntityLivingBase entityIn, float speed) {
        float yaw = AimAssistance.updateRotation(Minecraft.getMinecraft().thePlayer.rotationYaw, AimAssistance.getNeededRotations(entityIn)[0], speed);
        float pitch = AimAssistance.updateRotation(Minecraft.getMinecraft().thePlayer.rotationPitch, AimAssistance.getNeededRotations(entityIn)[1], speed);
        return new float[]{yaw, pitch};
    }

    private static float updateRotation(float currentRotation, float intendedRotation, float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment) {
            f = increment;
        }
        if (f < -increment) {
            f = -increment;
        }
        return currentRotation + f;
    }

    public float getAngleChange(EntityLivingBase entityIn) {
        float yaw = AimAssistance.getNeededRotations(entityIn)[0];
        float pitch = AimAssistance.getNeededRotations(entityIn)[1];
        float playerYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        float playerPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        if (playerYaw < 0.0f) {
            playerYaw += 360.0f;
        }
        if (playerPitch < 0.0f) {
            playerPitch += 360.0f;
        }
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        if (pitch < 0.0f) {
            pitch += 360.0f;
        }
        float yawChange = Math.max(playerYaw, yaw) - Math.min(playerYaw, yaw);
        float pitchChange = Math.max(playerPitch, pitch) - Math.min(playerPitch, pitch);
        return yawChange + pitchChange;
    }

    public static float getRandomInRange(float min, float max) {
        Random random = new Random();
        float range = max - min;
        float scaled = random.nextFloat() * range;
        return scaled + min;
    }

    public static float[] getNeededRotations(EntityLivingBase entityIn) {
        double d0 = entityIn.posX - Minecraft.getMinecraft().thePlayer.posX;
        double d1 = entityIn.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double d2 = entityIn.posY + (double)entityIn.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY + (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY - Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY));
        double d3 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
        float f = (float)(MathHelper.func_181159_b(d1, d0) * 180.0 / Math.PI) - 90.0f;
        float f1 = (float)(-(MathHelper.func_181159_b(d2, d3) * 180.0 / Math.PI));
        return new float[]{f, f1};
    }
}

