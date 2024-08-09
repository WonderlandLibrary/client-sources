package ru.FecuritySQ.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector4f;
import org.joml.Vector2d;
import org.lwjgl.opengl.GL11;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil {

    private static Minecraft mc = Minecraft.getInstance();

    public static boolean collided(float x, float y, float width, float height, int mouseX, int mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
    public static float clamp(float val, float min, float max) {
        if (val <= min) {
            val = min;
        }
        if (val >= max) {
            val = max;
        }
        return val;
    }
    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }
    public static double animate(double target, double current, double speed) {
         boolean larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        final double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        if (larger) {
            current += factor;
        } else {
            current -= factor;
        }
        return current;
    }
    public static float animation(float current, float targetAnimation, float speedTarget) {
        float da = (targetAnimation - current) / Minecraft.getInstance().debugFPS * 15.0f;
        if (da > 0.0f) {
            da = Math.max(speedTarget, da);
            da = Math.min(targetAnimation - current, da);
        } else if (da < 0.0f) {
            da = Math.min(-speedTarget, da);
            da = Math.max(targetAnimation - current, da);
        }
        return current + da;
    }

    public static double getSpeed(Entity entity) {
        return Math.abs(Math.hypot(entity.getPosX() - entity.prevPosX, entity.getPosZ() - entity.prevPosZ)) * 20;
    }


    public static Vector3d interpolate(Entity entity, float partialTicks) {
        double posX = interpolate(entity.getPosX(), entity.lastTickPosX, partialTicks);
        double posY = interpolate(entity.getPosY(), entity.lastTickPosY, partialTicks);
        double posZ = interpolate(entity.getPosZ(), entity.lastTickPosZ, partialTicks);
        return new Vector3d(posX, posY, posZ);
    }

    public static void setMotion(double motion) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            mc.player.getMotion().x = 0;
            mc.player.getMotion().z = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (float) (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            mc.player.getMotion().x = forward * motion * Math.cos(Math.toRadians(yaw + 90.0f))
                    + strafe * motion * Math.sin(Math.toRadians(yaw + 90.0f));
            mc.player.getMotion().z = forward * motion * Math.sin(Math.toRadians(yaw + 90.0f))
                    - strafe * motion * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }


    public static double interpolate(double now, double then, float partialTicks) {
        return then + (now - then) * partialTicks;
    }
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static BigDecimal round(float f, int times) {
        BigDecimal bd = new BigDecimal(Float.toString(f));
        bd = bd.setScale(times, RoundingMode.HALF_UP);
        return bd;
    }
}
