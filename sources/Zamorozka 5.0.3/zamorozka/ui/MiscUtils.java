package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MiscUtils implements MCUtil {


    public static void showURL(final String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (final IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTeams(final EntityPlayer otherEntity) {
        boolean b = false;
        TextFormatting TextFormatting = null;
        TextFormatting TextFormatting2 = null;
        if (otherEntity != null) {
            for (final TextFormatting TextFormatting3 : net.minecraft.util.text.TextFormatting.values()) {
                if (TextFormatting3 != net.minecraft.util.text.TextFormatting.RESET) {
                    if (Minecraft.player.getDisplayName().getFormattedText().contains(TextFormatting3.toString()) && TextFormatting == null) {
                        TextFormatting = TextFormatting3;
                    }
                    if (otherEntity.getDisplayName().getFormattedText().contains(TextFormatting3.toString()) && TextFormatting2 == null) {
                        TextFormatting2 = TextFormatting3;
                    }
                }
            }
            try {
                if (TextFormatting != null && TextFormatting2 != null) {
                    b = (TextFormatting != TextFormatting2);
                } else if (Minecraft.player.getTeam() != null) {
                    b = !Minecraft.player.isOnSameTeam(otherEntity);
                } else if (Minecraft.player.inventory.armorInventory.get(3).getItem() instanceof ItemBlock) {
                    b = !ItemStack.areItemStacksEqual(Minecraft.player.inventory.armorInventory.get(3), otherEntity.inventory.armorInventory.get(3));
                }
            } catch (Exception ignored) {
            }
        }
        return b;
    }

    public static double getDoubleRandom(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static int random(int min, int max) {
        int range = max - min;
        int result = min + new Random().nextInt(range + 1);
        return result;
    }

    public static int randomNumber(int max, int min) {
        return Math.round(min + (float) Math.random() * ((max - min)));
    }

    public static double Random1(final double n, final double n2) {
        double n3 = new Random().nextDouble() * (n2 - n);
        if (n3 > n2) {
            n3 = n2;
        }
        double n4 = n3 + n;
        if (n4 > n2) {
            n4 = n2;
        }
        return n4;
    }

    public static int Random2(final int n, final int n2) {
        return new Random().nextInt(n2 - n + 1) + n;
    }

    public static float Random3(final float n, final float n2) {
        float n3 = new Random().nextFloat() * (n2 - n);
        if (n3 > n2) {
            n3 = n2;
        }
        float n4 = n3 + n;
        if (n4 > n2) {
            n4 = n2;
        }
        return n4;
    }

    public static String randomNumber(final int length) {
        return random(length, "123456789");
    }

    public static String randomString(final int length) {
        return random(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    public static String random(final int length, final String chars) {
        return random(length, chars.toCharArray());
    }

    public static String random(final int length, final char[] chars) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++)
            stringBuilder.append(chars[new Random().nextInt(chars.length)]);
        return stringBuilder.toString();
    }

    public static double round(final double n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException();
        }
        return new BigDecimal(n).setScale(n2, RoundingMode.HALF_UP).doubleValue();
    }
}
