/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Movement;

import net.minecraft.client.Minecraft;
import net.minecraft.init.MobEffects;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;

public class MovementHelper {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isMoving() {
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_0 = Minecraft.player.movementInput;
        if (MovementInput.moveForward != 0.0f) return true;
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_1 = Minecraft.player.movementInput;
        if (MovementInput.moveStrafe == 0.0f) return false;
        return true;
    }

    public static float getBaseMoveSpeed() {
        Minecraft mc = Minecraft.getMinecraft();
        float baseSpeed = 0.2873f;
        if (Minecraft.player != null) {
            if (Minecraft.player.isPotionActive(MobEffects.SPEED)) {
                int amplifier = Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier();
                baseSpeed = (float)((double)baseSpeed * (1.0 + 0.2 * (double)(amplifier + 1)));
            }
        }
        return baseSpeed;
    }

    public static void strafe() {
        if (!Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()) {
            MovementHelper.strafe(MovementHelper.getSpeed());
        }
    }

    public static float getSpeeds() {
        Minecraft.getMinecraft();
        double d = Minecraft.player.motionX;
        Minecraft.getMinecraft();
        double d2 = d * Minecraft.player.motionX;
        Minecraft.getMinecraft();
        double d3 = Minecraft.player.motionZ;
        Minecraft.getMinecraft();
        return (float)Math.sqrt(d2 + d3 * Minecraft.player.motionZ);
    }

    public static void strafe(float speed) {
        if (MovementHelper.isMoving()) {
            double yaw = MovementHelper.getDirection();
            Minecraft.getMinecraft();
            Minecraft.player.motionX = -Math.sin(yaw) * (double)speed;
            Minecraft.getMinecraft();
            Minecraft.player.motionZ = Math.cos(yaw) * (double)speed;
        }
    }

    public static float getDirection() {
        Minecraft.getMinecraft();
        float rotationYaw = Minecraft.player.rotationYaw;
        float factor = 1.0f;
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_0 = Minecraft.player.movementInput;
        if (MovementInput.moveForward > 0.0f) {
            factor = 1.0f;
        }
        Minecraft.getMinecraft();
        MovementInput cfr_ignored_1 = Minecraft.player.movementInput;
        if (MovementInput.moveForward < 0.0f) {
            factor = -1.0f;
        }
        if (factor == 0.0f) {
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_2 = Minecraft.player.movementInput;
            if (MovementInput.moveStrafe > 0.0f) {
                rotationYaw -= 90.0f;
            }
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_3 = Minecraft.player.movementInput;
            if (MovementInput.moveStrafe < 0.0f) {
                rotationYaw += 90.0f;
            }
        } else if (Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() || Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown()) {
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_4 = Minecraft.player.movementInput;
            if (MovementInput.moveStrafe > 0.0f) {
                rotationYaw -= Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() ? 45.0f * factor : -(45.0f * factor);
            }
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_5 = Minecraft.player.movementInput;
            if (MovementInput.moveStrafe < 0.0f) {
                rotationYaw += Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown() ? 45.0f * factor : -(45.0f * factor);
            }
        } else {
            float f;
            Minecraft.getMinecraft();
            MovementInput cfr_ignored_6 = Minecraft.player.movementInput;
            if (MovementInput.moveStrafe > 0.0f) {
                f = -90.0f;
            } else {
                Minecraft.getMinecraft();
                MovementInput cfr_ignored_7 = Minecraft.player.movementInput;
                f = MovementInput.moveStrafe < 0.0f ? 90.0f : 0.0f;
            }
            rotationYaw += f;
        }
        if (factor < 0.0f) {
            rotationYaw -= 180.0f;
        }
        return (float)Math.toRadians(rotationYaw);
    }

    public static float getDirection2() {
        Minecraft mc = Minecraft.getMinecraft();
        float var1 = Minecraft.player.rotationYaw;
        if (Minecraft.player.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (Minecraft.player.moveForward < 0.0f) {
            forward = -50.5f;
        } else if (Minecraft.player.moveForward > 0.0f) {
            forward = 50.5f;
        }
        if (Minecraft.player.moveStrafing > 0.0f) {
            var1 -= 22.0f * forward;
        }
        if (Minecraft.player.moveStrafing < 0.0f) {
            var1 += 22.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    public static int getSpeedEffect() {
        Minecraft mc = Minecraft.getMinecraft();
        if (Minecraft.player.isPotionActive(MobEffects.SPEED)) {
            return Minecraft.player.getActivePotionEffect(MobEffects.SPEED).getAmplifier() + 1;
        }
        return 0;
    }

    public static float getMoveDirection() {
        Minecraft mc = Minecraft.getMinecraft();
        double motionX = Minecraft.player.motionX;
        double motionZ = Minecraft.player.motionZ;
        float direction = (float)(Math.atan2(motionX, motionZ) / Math.PI * 180.0);
        return -direction;
    }

    public static boolean isBlockAboveHead() {
        Minecraft mc = Minecraft.getMinecraft();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(Minecraft.player.posX - 0.3, Minecraft.player.posY + (double)Minecraft.player.getEyeHeight(), Minecraft.player.posZ + 0.3, Minecraft.player.posX + 0.3, Minecraft.player.posY + (!Minecraft.player.onGround ? 1.5 : 2.5), Minecraft.player.posZ - 0.3);
        return mc.world.getCollisionBoxes(Minecraft.player, axisAlignedBB).isEmpty();
    }

    public static float getSpeed() {
        Minecraft mc = Minecraft.getMinecraft();
        return (float)Math.sqrt(Minecraft.player.motionX * Minecraft.player.motionX + Minecraft.player.motionZ * Minecraft.player.motionZ);
    }

    public static void setSpeed(float speed, float arrf, int direction, double d) {
        Minecraft mc = Minecraft.getMinecraft();
        float yaw = Minecraft.player.rotationYaw;
        MovementInput cfr_ignored_0 = Minecraft.player.movementInput;
        float forward = MovementInput.moveForward;
        MovementInput cfr_ignored_1 = Minecraft.player.movementInput;
        float strafe = MovementInput.moveStrafe;
        if (forward != 0.0f) {
            if (strafe > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (strafe < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            strafe = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        Minecraft.player.motionX = (double)(forward * speed) * Math.cos(Math.toRadians(yaw + 90.0f)) + (double)(strafe * speed) * Math.sin(Math.toRadians(yaw + 90.0f));
        Minecraft.player.motionZ = (double)(forward * speed) * Math.sin(Math.toRadians(yaw + 90.0f)) - (double)(strafe * speed) * Math.cos(Math.toRadians(yaw + 90.0f));
    }

    public static double getDirectionAll() {
        Minecraft mc = Minecraft.getMinecraft();
        float rotationYaw = Minecraft.player.rotationYaw;
        float forward = 1.0f;
        if (Minecraft.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        if (Minecraft.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (Minecraft.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (Minecraft.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (Minecraft.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }

    public static void strafePlayer(float speed) {
        Minecraft mc = Minecraft.getMinecraft();
        double yaw = MovementHelper.getDirectionAll();
        float getSpeed = speed == 0.0f ? MovementHelper.getSpeed() : speed;
        Minecraft.player.motionX = -Math.sin(yaw) * (double)getSpeed;
        Minecraft.player.motionZ = Math.cos(yaw) * (double)getSpeed;
    }
}

