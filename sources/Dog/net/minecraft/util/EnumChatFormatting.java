package net.minecraft.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public enum EnumChatFormatting
{
    BLACK("BLACK", '0', 0, new Color(0,0,0)),
    DARK_BLUE("DARK_BLUE", '1', 1, new Color(0, 0, 170)),
    DARK_GREEN("DARK_GREEN", '2', 2, new Color(0, 170, 0)),
    DARK_AQUA("DARK_AQUA", '3', 3, new Color(0, 170, 170)),
    DARK_RED("DARK_RED", '4', 4, new Color(170, 0, 0)),
    DARK_PURPLE("DARK_PURPLE", '5', 5, new Color(170, 0, 170)),
    GOLD("GOLD", '6', 6, new Color(255, 170, 0)),
    GRAY("GRAY", '7', 7, new Color(170, 170, 170)),
    DARK_GRAY("DARK_GRAY", '8', 8, new Color(85, 85, 85)),
    BLUE("BLUE", '9', 9, new Color(85, 85, 255)),
    GREEN("GREEN", 'a', 10, new Color(85, 255, 85)),
    AQUA("AQUA", 'b', 11, new Color(85, 255, 255)),
    RED("RED", 'c', 12, new Color(255, 85, 85)),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, new Color(255, 85, 255)),
    YELLOW("YELLOW", 'e', 14, new Color(255, 255, 85)),
    WHITE("WHITE", 'f', 15, new Color(255,255,255)),
    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),
    RESET("RESET", 'r', -1, new Color(0,0,0)),
    LEGITCOLOR("LEGITCOL", 'z', 69, new Color(0,0,0));

    private static final Map<String, EnumChatFormatting> nameMapping = Maps.<String, EnumChatFormatting>newHashMap();

    /**
     * Matches formatting codes that indicate that the client should treat the following text as bold, recolored,
     * obfuscated, etc.
     */
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");

    /** The name of this color/formatting */
    private final String name;

    public final Color color;

    /** The formatting code that produces this format. */
    public final char formattingCode;
    private final boolean fancyStyling;

    /**
     * The control string (section sign + formatting code) that can be inserted into client-side text to display
     * subsequent text in this format.
     */
    private final String controlString;

    /** The numerical index that represents this color */
    private final int colorIndex;

    private static String func_175745_c(String p_175745_0_)
    {
        return p_175745_0_.toLowerCase().replaceAll("[^a-z]", "");
    }

    private EnumChatFormatting(String formattingName, char formattingCodeIn, int colorIndex, Color color)
    {
        this(formattingName, formattingCodeIn, false, colorIndex, color);
    }

    private EnumChatFormatting(String formattingName, char formattingCodeIn, boolean fancyStylingIn)
    {
        this(formattingName, formattingCodeIn, fancyStylingIn, -1, new Color(0,0,0));
    }

    private EnumChatFormatting(String formattingName, char formattingCodeIn, boolean fancyStylingIn, int colorIndex, Color color)
    {
        this.name = formattingName;
        this.formattingCode = formattingCodeIn;
        this.fancyStyling = fancyStylingIn;
        this.colorIndex = colorIndex;
        this.controlString = "§" + formattingCodeIn;
        this.color = color;
    }

    /**
     * Returns the numerical color index that represents this formatting
     */
    public int getColorIndex()
    {
        return this.colorIndex;
    }

    /**
     * False if this is just changing the color or resetting; true otherwise.
     */
    public boolean isFancyStyling()
    {
        return this.fancyStyling;
    }

    /**
     * Checks if this is a color code.
     */
    public boolean isColor()
    {
        return !this.fancyStyling && this != RESET;
    }

    /**
     * Gets the friendly name of this value.
     */
    public String getFriendlyName()
    {
        return this.name().toLowerCase();
    }

    public String toString()
    {
        return this.controlString;
    }

    /**
     * Returns a copy of the given string, with formatting codes stripped away.
     */
    public static String getTextWithoutFormattingCodes(String text)
    {
        return text == null ? null : formattingCodePattern.matcher(text).replaceAll("");
    }

    /**
     * Gets a value by its friendly name; null if the given name does not map to a defined value.
     */
    public static EnumChatFormatting getValueByName(String friendlyName)
    {
        return friendlyName == null ? null : (EnumChatFormatting)nameMapping.get(func_175745_c(friendlyName));
    }

    public static EnumChatFormatting func_175744_a(int p_175744_0_)
    {
        if (p_175744_0_ < 0)
        {
            return RESET;
        }
        else
        {
            for (EnumChatFormatting enumchatformatting : values())
            {
                if (enumchatformatting.getColorIndex() == p_175744_0_)
                {
                    return enumchatformatting;
                }
            }

            return null;
        }
    }

    public static Collection<String> getValidValues(boolean p_96296_0_, boolean p_96296_1_)
    {
        List<String> list = Lists.<String>newArrayList();

        for (EnumChatFormatting enumchatformatting : values())
        {
            if ((!enumchatformatting.isColor() || p_96296_0_) && (!enumchatformatting.isFancyStyling() || p_96296_1_))
            {
                list.add(enumchatformatting.getFriendlyName());
            }
        }

        return list;
    }

    static {
        for (EnumChatFormatting enumchatformatting : values())
        {
            nameMapping.put(func_175745_c(enumchatformatting.name), enumchatformatting);
        }
    }
}
