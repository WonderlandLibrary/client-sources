package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomizeUtil implements MCUtil {
	public static Random random = new Random();
    public static double toDecimalLength(double in, int places) {
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static double round(double in, int places) {
        places = (int) MathHelper.clamp_double(places, 0.0, 2.147483647E9);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }
    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * (float)c.getRed();
        float g = 0.003921569F * (float)c.getGreen();
        float b = 0.003921569F * (float)c.getBlue();
        return (new Color(r, g, b, alpha)).getRGB();
    }
    public static String removeColorCode(String text) {
        String finalText = text;
        if (text.contains("��")) {
            for(int i = 0; i < finalText.length(); ++i) {
                if (Character.toString(finalText.charAt(i)).equals("��")) {
                    try {
                        String part1 = finalText.substring(0, i);
                        String part2 = finalText.substring(Math.min(i + 2, finalText.length()), finalText.length());
                        finalText = part1 + part2;
                    } catch (Exception var5) {
                        ;
                    }
                }
            }
        }

        return finalText;
    }
    public static boolean parsable(String s, byte type) {
        try {
            switch (type) {
                case 0: {
                    Short.parseShort(s);
                    break;
                }
                case 1: {
                    Byte.parseByte(s);
                    break;
                }
                case 2: {
                    Integer.parseInt(s);
                    break;
                }
                case 3: {
                    Float.parseFloat(s);
                    break;
                }
                case 4: {
                    Double.parseDouble(s);
                    break;
                }
                case 5: {
                    Long.parseLong(s);
                }
                default: {
                    break;
                }
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static double square(double in) {
        return in * in;
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
    public  static float[] getFacePos(Vec3d vec) {
        Minecraft.getMinecraft();
        double diffX = vec.xCoord + 0.5 - Minecraft.getMinecraft().player.posX;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        double diffY = vec.yCoord + 0.5 - (Minecraft.getMinecraft().player.posY + (double)Minecraft.getMinecraft().player.getEyeHeight());
        Minecraft.getMinecraft();
        double diffZ = vec.zCoord + 0.5 - Minecraft.getMinecraft().player.posZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0 / 3.141592653589793) - 90.0f;
        float pitch = (float)((- Math.atan2(diffY, dist)) * 180.0 / 3.141592653589793);
        float[] arrf = new float[2];
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        arrf[0] = Minecraft.getMinecraft().player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - Minecraft.getMinecraft().player.rotationYaw);
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        arrf[1] = Minecraft.getMinecraft().player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - Minecraft.getMinecraft().player.rotationPitch);
        return arrf;
    }
    public static double getBaseMovementSpeed() {
        double baseSpeed = 0.2873;
        if (mc.player.isPotionActive(Potion.getPotionById(1))) {
            int amplifier = mc.player.getActivePotionEffect(Potion.getPotionById(1)).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double)(amplifier + 1);
        }
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
            int n2 = 0;
            while (n2 < n) {
                int offset = arrn2[n2];
                if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().offset(mc.player.motionX * (double)offset, i, mc.player.motionZ * (double)offset)).size() > 0) {
                    return i - 0.01;
                }
                ++n2;
            }
            i += 0.01;
        }
        return max;
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
            if (cls == Long.class) {
                return 5;
            }
            return -1;
        }
    }

}
