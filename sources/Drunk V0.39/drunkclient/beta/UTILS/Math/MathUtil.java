/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.UTILS.Math;

import drunkclient.beta.UTILS.helper.Helper;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class MathUtil {
    public static Random random = new Random();

    public static double toDecimalLength(double in, int places) {
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static double round(double in, int places) {
        places = (int)MathHelper.clamp_double(places, 0.0, 2.147483647E9);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static boolean parsable(String s, byte type) {
        try {
            switch (type) {
                case 0: {
                    Short.parseShort(s);
                    return true;
                }
                case 1: {
                    Byte.parseByte(s);
                    return true;
                }
                case 2: {
                    Integer.parseInt(s);
                    return true;
                }
                case 3: {
                    Float.parseFloat(s);
                    return true;
                }
                case 4: {
                    Double.parseDouble(s);
                    return true;
                }
                case 5: {
                    Long.parseLong(s);
                    return true;
                }
            }
            return true;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double square(double in) {
        return in * in;
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double getBaseMovementSpeed() {
        double baseSpeed = 0.2873;
        if (!Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) return baseSpeed;
        int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
        baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        return baseSpeed;
    }

    public static double getHighestOffset(double max) {
        double i = 0.0;
        while (i < max) {
            int[] arrn = new int[5];
            arrn[0] = -2;
            arrn[1] = -1;
            arrn[3] = 1;
            arrn[4] = 2;
            int[] arrn2 = arrn;
            int n = arrn.length;
            for (int n2 = 0; n2 < n; ++n2) {
                int offset = arrn2[n2];
                if (Helper.mc.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(Minecraft.thePlayer.motionX * (double)offset, i, Minecraft.thePlayer.motionZ * (double)offset)).size() <= 0) continue;
                return i - 0.01;
            }
            i += 0.01;
        }
        return max;
    }

    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

    public static class NumberType {
        public static final byte SHORT = 0;
        public static final byte BYTE = 1;
        public static final byte INT = 2;
        public static final byte FLOAT = 3;
        public static final byte DOUBLE = 4;
        public static final byte LONG = 5;

        public static byte getByType(Class cls) {
            if (cls == Short.class) {
                return 0;
            }
            if (cls == Byte.class) {
                return 1;
            }
            if (cls == Integer.class) {
                return 2;
            }
            if (cls == Float.class) {
                return 3;
            }
            if (cls == Double.class) {
                return 4;
            }
            if (cls != Long.class) return -1;
            return 5;
        }
    }
}

