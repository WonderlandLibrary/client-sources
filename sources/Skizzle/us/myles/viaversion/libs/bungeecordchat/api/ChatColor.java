/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Preconditions
 */
package us.myles.viaversion.libs.bungeecordchat.api;

import com.google.common.base.Preconditions;
import java.awt.Color;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public final class ChatColor {
    public static final char COLOR_CHAR = '\u00a7';
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    public static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-ORX]");
    private static final Map<Character, ChatColor> BY_CHAR = new HashMap<Character, ChatColor>();
    private static final Map<String, ChatColor> BY_NAME = new HashMap<String, ChatColor>();
    public static final ChatColor BLACK = new ChatColor('0', "black", new Color(0));
    public static final ChatColor DARK_BLUE = new ChatColor('1', "dark_blue", new Color(170));
    public static final ChatColor DARK_GREEN = new ChatColor('2', "dark_green", new Color(43520));
    public static final ChatColor DARK_AQUA = new ChatColor('3', "dark_aqua", new Color(43690));
    public static final ChatColor DARK_RED = new ChatColor('4', "dark_red", new Color(0xAA0000));
    public static final ChatColor DARK_PURPLE = new ChatColor('5', "dark_purple", new Color(0xAA00AA));
    public static final ChatColor GOLD = new ChatColor('6', "gold", new Color(0xFFAA00));
    public static final ChatColor GRAY = new ChatColor('7', "gray", new Color(0xAAAAAA));
    public static final ChatColor DARK_GRAY = new ChatColor('8', "dark_gray", new Color(0x555555));
    public static final ChatColor BLUE = new ChatColor('9', "blue", new Color(0x5555FF));
    public static final ChatColor GREEN = new ChatColor('a', "green", new Color(0x55FF55));
    public static final ChatColor AQUA = new ChatColor('b', "aqua", new Color(0x55FFFF));
    public static final ChatColor RED = new ChatColor('c', "red", new Color(0xFF5555));
    public static final ChatColor LIGHT_PURPLE = new ChatColor('d', "light_purple", new Color(0xFF55FF));
    public static final ChatColor YELLOW = new ChatColor('e', "yellow", new Color(0xFFFF55));
    public static final ChatColor WHITE = new ChatColor('f', "white", new Color(0xFFFFFF));
    public static final ChatColor MAGIC = new ChatColor('k', "obfuscated");
    public static final ChatColor BOLD = new ChatColor('l', "bold");
    public static final ChatColor STRIKETHROUGH = new ChatColor('m', "strikethrough");
    public static final ChatColor UNDERLINE = new ChatColor('n', "underline");
    public static final ChatColor ITALIC = new ChatColor('o', "italic");
    public static final ChatColor RESET = new ChatColor('r', "reset");
    private static int count = 0;
    private final String toString;
    private final String name;
    private final int ordinal;
    private final Color color;

    private ChatColor(char code, String name) {
        this(code, name, null);
    }

    private ChatColor(char code, String name, Color color) {
        this.name = name;
        this.toString = new String(new char[]{'\u00a7', code});
        this.ordinal = count++;
        this.color = color;
        BY_CHAR.put(Character.valueOf(code), this);
        BY_NAME.put(name.toUpperCase(Locale.ROOT), this);
    }

    private ChatColor(String name, String toString, int rgb) {
        this.name = name;
        this.toString = toString;
        this.ordinal = -1;
        this.color = new Color(rgb);
    }

    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.toString);
        return hash;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ChatColor other = (ChatColor)obj;
        return Objects.equals(this.toString, other.toString);
    }

    public String toString() {
        return this.toString;
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
            if (b[i] != altColorChar || ALL_CODES.indexOf(b[i + 1]) <= -1) continue;
            b[i] = 167;
            b[i + 1] = Character.toLowerCase(b[i + 1]);
        }
        return new String(b);
    }

    public static ChatColor getByChar(char code) {
        return BY_CHAR.get(Character.valueOf(code));
    }

    public static ChatColor of(Color color) {
        return ChatColor.of("#" + String.format("%08x", color.getRGB()).substring(2));
    }

    public static ChatColor of(String string) {
        Preconditions.checkArgument((string != null ? 1 : 0) != 0, (Object)"string cannot be null");
        if (string.startsWith("#") && string.length() == 7) {
            int rgb;
            try {
                rgb = Integer.parseInt(string.substring(1), 16);
            }
            catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Illegal hex string " + string);
            }
            StringBuilder magic = new StringBuilder("\u00a7x");
            for (char c : string.substring(1).toCharArray()) {
                magic.append('\u00a7').append(c);
            }
            return new ChatColor(string, magic.toString(), rgb);
        }
        ChatColor defined = BY_NAME.get(string.toUpperCase(Locale.ROOT));
        if (defined != null) {
            return defined;
        }
        throw new IllegalArgumentException("Could not parse ChatColor " + string);
    }

    @Deprecated
    public static ChatColor valueOf(String name) {
        Preconditions.checkNotNull((Object)name, (Object)"Name is null");
        ChatColor defined = BY_NAME.get(name);
        Preconditions.checkArgument((defined != null ? 1 : 0) != 0, (Object)("No enum constant " + ChatColor.class.getName() + "." + name));
        return defined;
    }

    @Deprecated
    public static ChatColor[] values() {
        return BY_CHAR.values().toArray(new ChatColor[BY_CHAR.values().size()]);
    }

    @Deprecated
    public String name() {
        return this.getName().toUpperCase(Locale.ROOT);
    }

    @Deprecated
    public int ordinal() {
        Preconditions.checkArgument((this.ordinal >= 0 ? 1 : 0) != 0, (Object)"Cannot get ordinal of hex color");
        return this.ordinal;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }
}

