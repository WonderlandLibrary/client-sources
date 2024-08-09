/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum TextFormatting {
    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 0xAA0000),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 0xAA00AA),
    GOLD("GOLD", '6', 6, 0xFFAA00),
    GRAY("GRAY", '7', 7, 0xAAAAAA),
    DARK_GRAY("DARK_GRAY", '8', 8, 0x555555),
    BLUE("BLUE", '9', 9, 0x5555FF),
    GREEN("GREEN", 'a', 10, 0x55FF55),
    AQUA("AQUA", 'b', 11, 0x55FFFF),
    RED("RED", 'c', 12, 0xFF5555),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 0xFF55FF),
    YELLOW("YELLOW", 'e', 14, 0xFFFF55),
    WHITE("WHITE", 'f', 15, 0xFFFFFF),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, null);

    private static final Map<String, TextFormatting> NAME_MAPPING;
    private static final Pattern FORMATTING_CODE_PATTERN;
    private final String name;
    private final char formattingCode;
    private final boolean fancyStyling;
    private final String controlString;
    private final int colorIndex;
    @Nullable
    private final Integer color;

    private static String lowercaseAlpha(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private TextFormatting(String string2, @Nullable char c, int n2, Integer n3) {
        this(string2, c, false, n2, n3);
    }

    private TextFormatting(String string2, char c, boolean bl) {
        this(string2, c, bl, -1, null);
    }

    private TextFormatting(String string2, char c, @Nullable boolean bl, int n2, Integer n3) {
        this.name = string2;
        this.formattingCode = c;
        this.fancyStyling = bl;
        this.colorIndex = n2;
        this.color = n3;
        this.controlString = "\u00a7" + c;
    }

    public int getColorIndex() {
        return this.colorIndex;
    }

    public boolean isFancyStyling() {
        return this.fancyStyling;
    }

    public boolean isColor() {
        return !this.fancyStyling && this != RESET;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    public String getFriendlyName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.controlString;
    }

    @Nullable
    public static String getTextWithoutFormattingCodes(@Nullable String string) {
        return string == null ? null : FORMATTING_CODE_PATTERN.matcher(string).replaceAll("");
    }

    @Nullable
    public static TextFormatting getValueByName(@Nullable String string) {
        return string == null ? null : NAME_MAPPING.get(TextFormatting.lowercaseAlpha(string));
    }

    @Nullable
    public static TextFormatting fromColorIndex(int n) {
        if (n < 0) {
            return RESET;
        }
        for (TextFormatting textFormatting : TextFormatting.values()) {
            if (textFormatting.getColorIndex() != n) continue;
            return textFormatting;
        }
        return null;
    }

    @Nullable
    public static TextFormatting fromFormattingCode(char c) {
        char c2 = Character.toString(c).toLowerCase(Locale.ROOT).charAt(0);
        for (TextFormatting textFormatting : TextFormatting.values()) {
            if (textFormatting.formattingCode != c2) continue;
            return textFormatting;
        }
        return null;
    }

    public static Collection<String> getValidValues(boolean bl, boolean bl2) {
        ArrayList<String> arrayList = Lists.newArrayList();
        for (TextFormatting textFormatting : TextFormatting.values()) {
            if (textFormatting.isColor() && !bl || textFormatting.isFancyStyling() && !bl2) continue;
            arrayList.add(textFormatting.getFriendlyName());
        }
        return arrayList;
    }

    private static TextFormatting lambda$static$1(TextFormatting textFormatting) {
        return textFormatting;
    }

    private static String lambda$static$0(TextFormatting textFormatting) {
        return TextFormatting.lowercaseAlpha(textFormatting.name);
    }

    static {
        NAME_MAPPING = Arrays.stream(TextFormatting.values()).collect(Collectors.toMap(TextFormatting::lambda$static$0, TextFormatting::lambda$static$1));
        FORMATTING_CODE_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
    }
}

