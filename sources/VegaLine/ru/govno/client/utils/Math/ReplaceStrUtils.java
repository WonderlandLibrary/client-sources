/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Math;

import java.util.Arrays;

public class ReplaceStrUtils {
    private static final String[] DEFAULT_UPPER = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String[] DEFAULT_LOWER = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private static final String[] CUSTOM1_UPPER = new String[]{"\ud835\ude70", "\ud835\ude71", "\ud835\ude72", "\ud835\ude73", "\ud835\ude74", "\ud835\ude75", "\ud835\ude76", "\ud835\ude77", "\ud835\ude78", "\ud835\ude79", "\ud835\ude7a", "\ud835\ude7b", "\ud835\ude7c", "\ud835\ude7d", "\ud835\ude7e", "\ud835\ude7f", "\ud835\ude80", "\ud835\ude81", "\ud835\ude82", "\ud835\ude83", "\ud835\ude84", "\ud835\ude85", "\ud835\ude86", "\ud835\ude87", "\ud835\ude88", "\ud835\ude89"};
    private static final String[] CUSTOM1_LOWER = new String[]{"\ud835\ude8a", "\ud835\ude8b", "\ud835\ude8c", "\ud835\ude8d", "\ud835\ude8e", "\ud835\ude8f", "\ud835\ude90", "\ud835\ude91", "\ud835\ude92", "\ud835\ude93", "\ud835\ude94", "\ud835\ude95", "\ud835\ude96", "\ud835\ude97", "\ud835\ude98", "\ud835\ude99", "\ud835\ude9a", "\ud835\ude9b", "\ud835\ude9c", "\ud835\ude9d", "\ud835\ude9e", "\ud835\ude9f", "\ud835\udea0", "\ud835\udea1", "\ud835\udea2", "\ud835\udea3"};
    private static final String[] CUSTOM2_LOWER = new String[]{"\ud83c\udde6", "\ud83c\udde7", "\ud83c\udde8", "\ud83c\udde9", "\ud83c\uddea", "\ud83c\uddeb", "\ud83c\uddec", "\ud83c\udded", "\ud83c\uddee", "\ud83c\uddef", "\ud83c\uddf0", "\ud83c\uddf1", "\ud83c\uddf2", "\ud83c\uddf3", "\ud83c\uddf4", "\ud83c\uddf5", "\ud83c\uddf6", "\ud83c\uddf7", "\ud83c\uddf8", "\ud83c\uddf9", "\ud83c\uddfa", "\ud83c\uddfb", "\ud83c\uddfc", "\ud83c\uddfd", "\ud83c\uddfe", "\ud83c\uddff"};
    private static final String[] CUSTOM3_UPPER = new String[]{"\ud835\udcd0", "\ud835\udcd1", "\ud835\udcd2", "\ud835\udcd3", "\ud835\udcd4", "\ud835\udcd5", "\ud835\udcd6", "\ud835\udcd7", "\ud835\udcd8", "\ud835\udcd9", "\ud835\udcda", "\ud835\udcdb", "\ud835\udcdc", "\ud835\udcdd", "\ud835\udcde", "\ud835\udcdf", "\ud835\udce0", "\ud835\udce1", "\ud835\udce2", "\ud835\udce3", "\ud835\udce4", "\ud835\udce5", "\ud835\udce6", "\ud835\udce7", "\ud835\udce8", "\ud835\udce9"};
    private static final String[] CUSTOM3_LOWER = new String[]{"\ud835\udcea", "\ud835\udceb", "\ud835\udcec", "\ud835\udced", "\ud835\udcee", "\ud835\udcef", "\ud835\udcf0", "\ud835\udcf1", "\ud835\udcf2", "\ud835\udcf3", "\ud835\udcf4", "\ud835\udcf5", "\ud835\udcf6", "\ud835\udcf7", "\ud835\udcf8", "\ud835\udcf9", "\ud835\udcfa", "\ud835\udcfb", "\ud835\udcfc", "\ud835\udcfd", "\ud835\udcfe", "\ud835\udcff", "\ud835\udd00", "\ud835\udd01", "\ud835\udd02", "\ud835\udd03"};
    private static final String[] CUSTOM4_UPPER = new String[]{"\ud835\uddd4", "\ud835\uddd5", "\ud835\uddd6", "\ud835\uddd7", "\ud835\uddd8", "\ud835\uddd9", "\ud835\uddda", "\ud835\udddb", "\ud835\udddc", "\ud835\udddd", "\ud835\uddde", "\ud835\udddf", "\ud835\udde0", "\ud835\udde1", "\ud835\udde2", "\ud835\udde3", "\ud835\udde4", "\ud835\udde5", "\ud835\udde6", "\ud835\udde7", "\ud835\udde8", "\ud835\udde9", "\ud835\uddea", "\ud835\uddeb", "\ud835\uddec", "\ud835\udded"};
    private static final String[] CUSTOM4_LOWER = new String[]{"\ud835\uddee", "\ud835\uddef", "\ud835\uddf0", "\ud835\uddf1", "\ud835\uddf2", "\ud835\uddf3", "\ud835\uddf4", "\ud835\uddf5", "\ud835\uddf6", "\ud835\uddf7", "\ud835\uddf8", "\ud835\uddf9", "\ud835\uddfa", "\ud835\uddfb", "\ud835\uddfc", "\ud835\uddfd", "\ud835\uddfe", "\ud835\uddff", "\ud835\ude00", "\ud835\ude01", "\ud835\ude02", "\ud835\ude03", "\ud835\ude04", "\ud835\ude05", "\ud835\ude06", "\ud835\ude07"};
    private static final String[] CUSTOM5_UPPER = new String[]{"\ud835\ude3c", "\ud835\ude3d", "\ud835\ude3e", "\ud835\ude3f", "\ud835\ude40", "\ud835\ude41", "\ud835\ude42", "\ud835\ude43", "\ud835\ude44", "\ud835\ude45", "\ud835\ude46", "\ud835\ude47", "\ud835\ude48", "\ud835\ude49", "\ud835\ude4a", "\ud835\ude4b", "\ud835\ude4c", "\ud835\ude4d", "\ud835\ude4e", "\ud835\ude4f", "\ud835\ude50", "\ud835\ude51", "\ud835\ude52", "\ud835\ude53", "\ud835\ude54", "\ud835\ude55"};
    private static final String[] CUSTOM6_UPPER = new String[]{"\ud835\ude70", "\ud835\ude71", "\ud835\ude72", "\ud835\ude73", "\ud835\ude74", "\ud835\ude75", "\ud835\ude76", "\ud835\ude77", "\ud835\ude78", "\ud835\ude79", "\ud835\ude7a", "\ud835\ude7b", "\ud835\ude7c", "\ud835\ude7d", "\ud835\ude7e", "\ud835\ude7f", "\ud835\ude80", "\ud835\ude81", "\ud835\ude82", "\ud835\ude83", "\ud835\ude84", "\ud835\ude85", "\ud835\ude86", "\ud835\ude87", "\ud835\ude88", "\ud835\ude89"};
    private static final String[] CUSTOM6_LOWER = new String[]{"\ud835\ude8a", "\ud835\ude8b", "\ud835\ude8c", "\ud835\ude8d", "\ud835\ude8e", "\ud835\ude8f", "\ud835\ude90", "\ud835\ude91", "\ud835\ude92", "\ud835\ude93", "\ud835\ude94", "\ud835\ude95", "\ud835\ude96", "\ud835\ude97", "\ud835\ude98", "\ud835\ude99", "\ud835\ude9a", "\ud835\ude9b", "\ud835\ude9c", "\ud835\ude9d", "\ud835\ude9e", "\ud835\ude9f", "\ud835\udea0", "\ud835\udea1", "\ud835\udea2", "\ud835\udea3"};
    private static final String[] CUSTOM7_UPPER = new String[]{"\ud83c\udd50", "\ud83c\udd51", "\ud83c\udd52", "\ud83c\udd53", "\ud83c\udd54", "\ud83c\udd55", "\ud83c\udd56", "\ud83c\udd57", "\ud83c\udd58", "\ud83c\udd59", "\ud83c\udd5a", "\ud83c\udd5b", "\ud83c\udd5c", "\ud83c\udd5d", "\ud83c\udd5e", "\ud83c\udd5f", "\ud83c\udd60", "\ud83c\udd61", "\ud83c\udd62", "\ud83c\udd63", "\ud83c\udd64", "\ud83c\udd65", "\ud83c\udd66", "\ud83c\udd67", "\ud83c\udd68", "\ud83c\udd69"};
    private static final String[] CUSTOM8_UPPER = new String[]{"\ud835\udd38", "\ud835\udd39", "\u2102", "\ud835\udd3b", "\ud835\udd3c", "\ud835\udd3d", "\ud835\udd3e", "\u210d", "\ud835\udd40", "\ud835\udd41", "\ud835\udd42", "\ud835\udd43", "\ud835\udd44", "\u2115", "\ud835\udd46", "\u2119", "\u211a", "\u211d", "\ud835\udd4a", "\ud835\udd4b", "\ud835\udd4c", "\ud835\udd4d", "\ud835\udd4e", "\ud835\udd4f", "\ud835\udd50", "\u2124"};
    private static final String[] CUSTOM8_LOWER = new String[]{"\ud835\udd52", "\ud835\udd53", "\ud835\udd54", "\ud835\udd55", "\ud835\udd56", "\ud835\udd57", "\ud835\udd58", "\ud835\udd59", "\ud835\udd5a", "\ud835\udd5b", "\ud835\udd5c", "\ud835\udd5d", "\ud835\udd5e", "\ud835\udd5f", "\ud835\udd60", "\ud835\udd61", "\ud835\udd62", "\ud835\udd63", "\ud835\udd64", "\ud835\udd65", "\ud835\udd66", "\ud835\udd67", "\ud835\udd68", "\ud835\udd69", "\ud835\udd6a", "\ud835\udd6b"};
    private static final String[] CUSTOM9_LOWER = new String[]{"\u1d00", "\u0299", "\u1d04", "\u1d05", "\u1d07", "\u0493", "\u0262", "\u029c", "\u026a", "\u1d0a", "\u1d0b", "\u029f", "\u1d0d", "\u0274", "\u1d0f", "\u1d18", "\u01eb", "\u0280", "s", "\u1d1b", "\u1d1c", "\u1d20", "\u1d21", "x", "\u028f", "\u1d22"};
    private static final String[][] customStringsMassives = new String[][]{CUSTOM1_UPPER, CUSTOM1_LOWER, CUSTOM2_LOWER, CUSTOM3_UPPER, CUSTOM3_LOWER, CUSTOM4_UPPER, CUSTOM4_LOWER, CUSTOM5_UPPER, CUSTOM6_UPPER, CUSTOM6_LOWER, CUSTOM7_UPPER, CUSTOM8_UPPER, CUSTOM8_LOWER, CUSTOM9_LOWER};
    private static final String[][] defaultArrays = new String[][]{DEFAULT_UPPER, DEFAULT_LOWER, DEFAULT_LOWER, DEFAULT_UPPER, DEFAULT_LOWER, DEFAULT_UPPER, DEFAULT_LOWER, DEFAULT_UPPER, DEFAULT_UPPER, DEFAULT_LOWER, DEFAULT_UPPER, DEFAULT_UPPER, DEFAULT_LOWER, DEFAULT_LOWER};
    private static final String[] badFormats = new String[]{"k", "l", "m", "n", "o"};

    public static String remoteStringUTF(String text) {
        int index = 0;
        for (String[] stringsCustom : customStringsMassives) {
            int index2 = 0;
            for (String stringCustom : stringsCustom) {
                String replaceTo = String.valueOf(Arrays.stream((String[])Arrays.stream(defaultArrays).toArray()[index]).toArray()[index2]);
                text = text.replace(stringCustom, replaceTo);
                ++index2;
            }
            ++index;
        }
        return text;
    }

    public static String deformatString(String text) {
        for (String sample : badFormats) {
            text = text.replace("\u00a7" + sample, "");
        }
        return text;
    }

    public static String cutBackString(String text) {
        return text.replace("  ", " ").replace("\u25b7", ">").replace("\u25c1", "<").replace("\u25c0", "<").replace("\u25b6", ">").replace("\u25c5", "<").replace("\u27a2", ">").replace("[]", "").replace("\u00a7r", "");
    }

    public static String fixString(String text) {
        return ReplaceStrUtils.cutBackString(ReplaceStrUtils.deformatString(ReplaceStrUtils.remoteStringUTF(text)));
    }
}

