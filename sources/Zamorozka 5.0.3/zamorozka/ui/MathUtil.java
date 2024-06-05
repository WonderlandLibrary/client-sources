package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public final class MathUtil {
    private static final Random random = new Random();

    public static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    public static double getRandomInRange(double max, double min) {
        return min + (max - min) * random.nextDouble();
    }

    public static int getRandomInRange(int max, int min) {
        return (int) (min + (max - min) * random.nextDouble());
    }
    
	public static double getMiddle(double d, double e) {
		return (d + e) / 2;
	}

	public static float getMiddle(float i, float i1) {
		return (i + i1) / 2;
	}

	public static double getMiddleint(double d, double e) {
		return (d + e) / 2;
	}

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static double square(double input) {
        return (input * input);
    }

    public static double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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

    public static float[] calcAngle(Vec3d from, Vec3d to) {
        final double difX = to.xCoord - from.xCoord;
        final double difY = (to.yCoord - from.yCoord) * -1.0F;
        final double difZ = to.zCoord - from.zCoord;
        final double dist = MathHelper.sqrt(difX * difX + difZ * difZ);
        return new float[]{(float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difZ, difX)) - 90.0f), (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(difY, dist)))};
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static int randomize(int max, int min) {
        return -min + (int) (Math.random() * ((max - (-min)) + 1));
    }

    public static double getIncremental(double val, double inc) {
        double one = 1 / inc;
        return Math.round(val * one) / one;
    }

    public static boolean isInteger(Double variable) {
        return (variable == Math.floor(variable)) && !Double.isInfinite(variable);
    }

    public static float[] constrainAngle(final float[] vector) {
        vector[0] %= 360.0f;
        vector[1] %= 360.0f;
        while (vector[0] <= -180.0f) {
            vector[0] += 360.0f;
        }
        while (vector[1] <= -180.0f) {
            vector[1] += 360.0f;
        }
        while (vector[0] > 180.0f) {
            vector[0] -= 360.0f;
        }
        while (vector[1] > 180.0f) {
            vector[1] -= 360.0f;
        }
        return vector;
    }

    public static boolean getChance(double percent) {
        return !(percent <= 0) && (percent >= 100 || ((Math.random() * 100) - percent) < 0);
    }

    public static Vec3d interpolateEntity(Entity entity, float renderPartialTicks) {
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) renderPartialTicks, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) renderPartialTicks, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) renderPartialTicks);
    }

    public static Vec3d interpolateEntity(Entity entity) {
        double partialTicks = Minecraft.getMinecraft().getRenderPartialTicks();
        return new Vec3d(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks,
                entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks,
                entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks);
    }
}