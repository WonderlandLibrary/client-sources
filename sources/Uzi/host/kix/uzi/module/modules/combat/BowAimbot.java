package host.kix.uzi.module.modules.combat;

import com.darkmagician6.eventapi.SubscribeEvent;

import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Created by Kix on 5/29/2017.
 * Made for the eclipse project.
 */
public class BowAimbot extends Module {

    public BowAimbot() {
        super("BowAimbot", 0, Category.COMBAT);
    }

    @SubscribeEvent
    public void onUpdate(UpdateEvent event) {
        if (event.type != EventType.POST) {

            if (mc.thePlayer.getItemInUseDuration() == 0)
                return;

            if (mc.thePlayer.getHeldItem() == null || !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow))
                return;

            int use = mc.thePlayer.getItemInUseDuration();

            float progress = use / 20.0F;
            progress = (progress * progress + progress * 2.0F) / 3.0F;

            progress = MathHelper.clamp_float(progress, 0, 1);

            double v = progress * 3.0F;
            // Static MC gravity
            double g = 0.05F;

            EntityLivingBase target = getClosestToCrosshair();

            if (target == null)
                return;

            float pitch = (float) -Math.toDegrees(getLaunchAngle(target, v, g));

            if (Double.isNaN(pitch))
                return;

            Vec3 pos = predictPos(target, 10);

            double difX = pos.xCoord - mc.thePlayer.posX, difZ = pos.zCoord - mc.thePlayer.posZ;
            float yaw = (float) (Math.atan2(difZ, difX) * 180 / Math.PI) - 90;
            mc.thePlayer.rotationYaw = yaw;
            mc.thePlayer.rotationPitch = pitch;
        }
    }


    private static Vec3 lerp(Vec3 pos, Vec3 prev, float time) {
        double x = pos.xCoord + ((pos.xCoord - prev.xCoord) * time);
        double y = pos.yCoord + ((pos.yCoord - prev.yCoord) * time);
        double z = pos.zCoord + ((pos.zCoord - prev.zCoord) * time);

        return new Vec3(x, y, z);
    }

    public static Vec3 predictPos(Entity entity, float time) {
        return lerp(new Vec3(entity.posX, entity.posY, entity.posZ), new Vec3(entity.prevPosX, entity.prevPosY, entity.prevPosZ), time);
    }

    public static float[] getRotation(final Entity ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.boundingBox.maxY;
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getRotations(final Entity entity) {
        final double positionX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double positionZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double positionY = entity.posY + entity.getEyeHeight() / 1.3 - (Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight());
        final double positions = MathHelper.sqrt_double(positionX * positionX + positionZ * positionZ);
        final float yaw = (float) (Math.atan2(positionZ, positionX) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(positionY, positions) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[]{yaw, pitch};
    }

    public static float getTrajAngleSolutionLow(final float d3, final float d1, final float velocity) {
        final float g = 0.006f;
        final float sqrt = velocity * velocity * velocity * velocity - g * (g * d3 * d3 + 2.0f * d1 * velocity * velocity);
        return (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(sqrt)) / (g * d3)));
    }

    public static float getNewAngle(float angle) {
        angle %= 360.0f;
        if (angle >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static float getDistanceBetweenAngles(final float angle1, final float angle2) {
        float angle3 = Math.abs(angle1 - angle2) % 360.0f;
        if (angle3 > 180.0f) {
            angle3 = 360.0f - angle3;
        }
        return angle3;
    }

    private static Random rng = new Random();

    public static boolean isInteger(String num) {
        try {
            Integer.parseInt(num);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isFloat(String num) {
        try {
            Float.parseFloat(num);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isLong(String num) {
        try {
            Long.parseLong(num);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Random getRng() {
        return rng;
    }

    public static int getMid(int x1, int x2) {
        return ((x1 + x2) / 2);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static float getAngleDifference(float alpha, float beta) {
        float phi = Math.abs(beta - alpha) % 360;
        float distance = phi > 180 ? 360 - phi : phi;
        return distance;
    }

    public static float getRandom() {
        return rng.nextFloat();
    }

    public static int getRandom(int cap) {
        return rng.nextInt(cap);
    }

    public static int getRandom(int floor, int cap) {
        return floor + rng.nextInt(cap - floor + 1);
    }

    public static int randInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static double clampValue(double value, double floor, double cap) {
        if (value < floor) {
            return floor;
        }
        if (value > cap) {
            return cap;
        }
        return value;
    }

    private EntityLivingBase getClosestToCrosshair() {
        float dist = Float.MAX_VALUE;
        EntityLivingBase target = null;

        for (Object object : mc.theWorld.loadedEntityList) {
            if (!(object instanceof EntityLivingBase))
                continue;

            EntityLivingBase entity = (EntityLivingBase) object;

            if (!(entity.isEntityAlive() && !entity.isInvisible() && entity != mc.thePlayer
                    && mc.thePlayer.canEntityBeSeen(entity)))
                continue;

            float yaw = getRotations(entity)[0];
            float pitch = getRotations(entity)[1];

            float dif = (float) Math.sqrt(getAngleDifference(yaw, mc.thePlayer.rotationYaw)
                    * getAngleDifference(yaw, mc.thePlayer.rotationYaw)
                    + getAngleDifference(pitch, mc.thePlayer.rotationPitch)
                    * getAngleDifference(pitch, mc.thePlayer.rotationPitch));

            if (dif < dist) {
                dist = dif;
                target = entity;
            }
        }

        return target;
    }

    /**
     * Gets launch angle required to hit a target with the specified velocity
     * and gravity
     *
     * @param targetEntity Target entity
     * @param v            Projectile velocity
     * @param g            World gravity
     * @return
     */
    private float getLaunchAngle(EntityLivingBase targetEntity, double v, double g) {
        double yDif = ((targetEntity.posY + (targetEntity.getEyeHeight() / 2))
                - (mc.thePlayer.posY + mc.thePlayer.getEyeHeight()));
        double xDif = (targetEntity.posX - mc.thePlayer.posX);
        double zDif = (targetEntity.posZ - mc.thePlayer.posZ);

        /**
         * Pythagorean theorem to merge x/z /| / | xCoord / | zDif / | / |
         * /_____| (player) xDif
         */
        double xCoord = Math.sqrt((xDif * xDif) + (zDif * zDif));

        return theta(v, g, xCoord, yDif);
    }

    /**
     * Calculates launch angle to hit a specified point based on supplied
     * parameters
     *
     * @param v Projectile velocity
     * @param g World gravity
     * @param x x-coordinate
     * @param y y-coordinate
     * @return angle of launch required to hit point x,y
     * <p/>
     * Whoa there! You just supplied us with a method to hit a 2D point,
     * but Minecraft is a 3D game!
     * <p/>
     * Yeah. Unfortunately this is 100x easier to do than write a method
     * to find the 3D point, so we can just merge the x/z axis of
     * Minecraft into one (using the pythagorean theorem). Have a look
     * at getLaunchAngle to see how that's done
     */
    private float theta(double v, double g, double x, double y) {
        double yv = 2 * y * (v * v);
        double gx = g * (x * x);
        double g2 = g * (gx + yv);
        double insqrt = (v * v * v * v) - g2;
        double sqrt = Math.sqrt(insqrt);

        double numerator = (v * v) + sqrt;
        double numerator2 = (v * v) - sqrt;

        double atan1 = Math.atan2(numerator, g * x);
        double atan2 = Math.atan2(numerator2, g * x);

        /**
         * Ever heard of a quadratic equation? We're gonna have to have two
         * different results here, duh! It's probably best to launch at the
         * smaller angle because that will decrease the total flight time, thus
         * leaving less room for error. If you're just trying to impress your
         * friends you could probably fire it at the maximum angle, but for the
         * sake of simplicity, we'll use the smaller one here.
         */
        return (float) Math.min(atan1, atan2);
    }


}
