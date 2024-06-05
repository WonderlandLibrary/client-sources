/*
 * Decompiled with CFR 0_132.
 */
package me.superskidder.lune.utils.math;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.potion.Potion;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;

public class MathUtil {
    public static Random random = new Random();

    public static double toDecimalLength(double in, int places) {
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static double round(double in, int places) {
        places = (int) MathHelper.clamp_double(places, 0.0, 2.147483647E9);
        return Double.parseDouble(String.format("%." + places + "f", in));
    }

    public static String removeColorCode(String text) {
        String finalText = text;
        if (text.contains("§")) {
            for (int i = 0; i < finalText.length(); ++i) {
                if (Character.toString(finalText.charAt(i)).equals("§")) {
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

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569F * (float) c.getRed();
        float g = 0.003921569F * (float) c.getGreen();
        float b = 0.003921569F * (float) c.getBlue();
        return (new Color(r, g, b, alpha)).getRGB();
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
        } catch (NumberFormatException e) {
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

    public static double getBaseMovementSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (double) (amplifier + 1);
        }
        return baseSpeed;
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

    public static float randomFloat(float min, float max) {
        return (min + random.nextFloat() * (max - min));
    }

    public static double getIncremental(double val, double inc) {
        double one = 1 / inc;
        return Math.round(val * one) / one;
    }


    public static double randomNumber(double max, double min) {
        return Math.random() * (max - min) + min;
    }

}



