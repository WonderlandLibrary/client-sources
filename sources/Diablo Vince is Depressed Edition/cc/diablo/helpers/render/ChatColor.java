/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  org.apache.commons.lang3.Validate
 */
package cc.diablo.helpers.render;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;

public enum ChatColor {
    BLACK("BLACK", 0, '0', 0),
    DARK_BLUE("DARK_BLUE", 1, '1', 1),
    DARK_GREEN("DARK_GREEN", 2, '2', 2),
    DARK_AQUA("DARK_AQUA", 3, '3', 3),
    DARK_RED("DARK_RED", 4, '4', 4),
    DARK_PURPLE("DARK_PURPLE", 5, '5', 5),
    GOLD("GOLD", 6, '6', 6),
    GRAY("GRAY", 7, '7', 7),
    DARK_GRAY("DARK_GRAY", 8, '8', 8),
    BLUE("BLUE", 9, '9', 9),
    GREEN("GREEN", 10, 'a', 10),
    AQUA("AQUA", 11, 'b', 11),
    RED("RED", 12, 'c', 12),
    LIGHT_PURPLE("LIGHT_PURPLE", 13, 'd', 13),
    YELLOW("YELLOW", 14, 'e', 14),
    WHITE("WHITE", 15, 'f', 15),
    MAGIC("MAGIC", 16, 'k', 16, true),
    BOLD("BOLD", 17, 'l', 17, true),
    STRIKETHROUGH("STRIKETHROUGH", 18, 'm', 18, true),
    UNDERLINE("UNDERLINE", 19, 'n', 19, true),
    ITALIC("ITALIC", 20, 'o', 20, true),
    RESET("RESET", 21, 'r', 21);

    public static final char COLOR_CHAR = '\u00a7';
    private static final Pattern STRIP_COLOR_PATTERN;
    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final Map<Integer, ChatColor> BY_ID;
    private static final Map<Character, ChatColor> BY_CHAR;

    private ChatColor(String s, int n2, char code, int intCode) {
        this(s, n2, code, intCode, false);
    }

    private ChatColor(String s, int n2, char code, int intCode, boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[]{'\u00a7', code});
    }

    public int getIntCode() {
        return this.intCode;
    }

    public char getChar() {
        return this.code;
    }

    public String toString() {
        return this.toString;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }

    public static ChatColor getByChar(char code) {
        return BY_CHAR.get(Character.valueOf(code));
    }

    public static ChatColor getByChar(String code) {
        Validate.notNull((Object)code, (String)"Code cannot be null", (Object[])new Object[0]);
        Validate.isTrue((code.length() > 0 ? 1 : 0) != 0, (String)"Code must have at least one char", (Object[])new Object[0]);
        return BY_CHAR.get(Character.valueOf(code.charAt(0)));
    }

    public static String stripColor(String input) {
        if (input == null) {
            return null;
        }
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] != altColorChar || "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) <= -1) continue;
            b[i] = 167;
            b[i + 1] = Character.toLowerCase(b[i + 1]);
        }
        return new String(b);
    }

    public static String getLastColors(String input) {
        String result = "";
        int length = input.length();
        for (int index = length - 1; index > -1; --index) {
            char c;
            ChatColor color;
            char section = input.charAt(index);
            if (section != '\u00a7' || index >= length - 1 || (color = ChatColor.getByChar(c = input.charAt(index + 1))) == null) continue;
            result = color.toString() + result;
            if (color.isColor() || color.equals((Object)RESET)) break;
        }
        return result;
    }

    static {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");
        BY_ID = Maps.newHashMap();
        BY_CHAR = Maps.newHashMap();
        for (ChatColor color : ChatColor.values()) {
            BY_ID.put(color.intCode, color);
            BY_CHAR.put(Character.valueOf(color.code), color);
        }
    }
}

