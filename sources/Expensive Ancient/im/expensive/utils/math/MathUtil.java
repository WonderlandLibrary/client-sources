package im.expensive.utils.math;

import com.mojang.blaze3d.systems.RenderSystem;
import im.expensive.utils.client.IMinecraft;
import lombok.experimental.UtilityClass;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import static java.lang.Math.abs;
import static java.lang.Math.signum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class MathUtil implements IMinecraft {

    public double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static void scaleElements(float xCenter, float yCenter, float scale, Runnable runnable) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(xCenter, yCenter, 0);
        RenderSystem.scalef(scale, scale, 1);
        RenderSystem.translatef(-xCenter, -yCenter, 0);
        runnable.run();
        RenderSystem.popMatrix();


    }

    public static void scaleElements(float xCenter, float yCenter, float scaleX, float scaleY, float scaleZ, Runnable runnable) {
        RenderSystem.pushMatrix();
        RenderSystem.translatef(xCenter, yCenter, 0);
        RenderSystem.scalef(scaleX, scaleY, scaleZ);
        RenderSystem.translatef(-xCenter, -yCenter, 0);
        runnable.run();
        RenderSystem.popMatrix();
    }

    // is hovered
    public boolean isInRegion(float mouseX, float mouseY, float x, float y, float width, float height) {

        return mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + height;
    }

    public static float calculateXPosition(float x, float width) {
        return x - width / 2f;
    }

    public static float calculateYPosition(float y, float height) {
        return y - height / 2;
    }

    public static double getRandom(double min, double max) {
        if (min == max) {
            return min;
        } else {
            if (min > max) {
                double d = min;
                min = max;
                max = d;
            }

            return ThreadLocalRandom.current().nextDouble(min, max);
        }
    }


    public Vector2f rotationToVec(Vector3d vec) {
        Vector3d eyesPos = mc.player.getEyePosition(1.0f);
        double diffX = vec != null ? vec.x - eyesPos.x : 0;
        double diffY = vec != null ? vec.y - (mc.player.getPosY() + (double) mc.player.getEyeHeight() + 0.5) : 0;
        double diffZ = vec != null ? vec.z - eyesPos.z : 0;

        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float) (Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0);
        float pitch = (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        yaw = mc.player.rotationYaw + MathHelper.wrapDegrees(yaw - mc.player.rotationYaw);
        pitch = mc.player.rotationPitch + MathHelper.wrapDegrees(pitch - mc.player.rotationPitch);
        pitch = MathHelper.clamp(pitch, -90.0f, 90.0f);

        return new Vector2f(yaw, pitch);
    }

    public static Vector2f rotationToEntity(Entity target) {
        Vector3d vector3d = target.getPositionVec().subtract(Minecraft.getInstance().player.getPositionVec());
        double magnitude = Math.hypot(vector3d.x, vector3d.z);
        return new Vector2f(
                (float) Math.toDegrees(Math.atan2(vector3d.z, vector3d.x)) - 90.0F,
                (float) (-Math.toDegrees(Math.atan2(vector3d.y, magnitude))));
    }

    public Vector2f rotationToVec(Vector2f rotationVector, Vector3d target) {
        double x = target.x - mc.player.getPosX();
        double y = target.y - mc.player.getEyePosition(1).y;
        double z = target.z - mc.player.getPosZ();
        double dst = Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
        float yaw = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(z, x)) - 90);
        float pitch = (float) (-Math.toDegrees(Math.atan2(y, dst)));
        float yawDelta = MathHelper.wrapDegrees(yaw - rotationVector.x);
        float pitchDelta = (pitch - rotationVector.y);

        if (abs(yawDelta) > 180)
            yawDelta -= signum(yawDelta) * 360;

        return new Vector2f(yawDelta, pitchDelta);
    }

    // round
    public double round(double num, double increment) {
        double v = (double) Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // distance
    public double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        double d0 = x1 - x2;
        double d1 = y1 - y2;
        double d2 = z1 - z2;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double distance(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return Math.sqrt(x * x + y * y);
    }

    public double deltaTime() {
        return mc.debugFPS > 0 ? (1.0000 / mc.debugFPS) : 1;
    }

    public float fast(float end, float start, float multiple) {
        return (1 - MathHelper.clamp((float) (deltaTime() * multiple), 0, 1)) * end
                + MathHelper.clamp((float) (deltaTime() * multiple), 0, 1) * start;
    }

    public Vector3d interpolate(Vector3d end, Vector3d start, float multiple) {
        return new Vector3d(
                interpolate(end.getX(), start.getX(), multiple),
                interpolate(end.getY(), start.getY(), multiple),
                interpolate(end.getZ(), start.getZ(), multiple));
    }

    public Vector3d fast(Vector3d end, Vector3d start, float multiple) {
        return new Vector3d(
                fast((float) end.getX(), (float) start.getX(), multiple),
                fast((float) end.getY(), (float) start.getY(), multiple),
                fast((float) end.getZ(), (float) start.getZ(), multiple));
    }

    public float lerp(float end, float start, float multiple) {
        return (float) (end + (start - end) * MathHelper.clamp(deltaTime() * multiple, 0, 1));
    }

    public double lerp(double end, double start, double multiple) {
        return (end + (start - end) * MathHelper.clamp(deltaTime() * multiple, 0, 1));
    }

    public static boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return false;
    }
}
