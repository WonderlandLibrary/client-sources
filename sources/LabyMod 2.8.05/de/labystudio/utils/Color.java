package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;

public class Color
{
    public static String c = "\u00a7";

    public static String cl(String color)
    {
        return c + color + getExtraColor();
    }

    public static String clc(String color)
    {
        return c + color;
    }

    public static String getExtraColor()
    {
        String s = "";

        if (ConfigManager.settings.bold)
        {
            s = s + c + "l";
        }

        if (ConfigManager.settings.underline)
        {
            s = s + c + "n";
        }

        if (ConfigManager.settings.italic)
        {
            s = s + c + "o";
        }

        return s;
    }

    public static String removeColor(String input)
    {
        return input.replace(c, "&").replaceAll("&[a-z0-9]", "");
    }

    public static String c(int i)
    {
        return i == 1 ? ConfigManager.settings.color1 + getExtraColor() : (i == 2 ? ConfigManager.settings.color2 + getExtraColor() : (i == 3 ? ConfigManager.settings.color3 + getExtraColor() : (i == 4 ? ConfigManager.settings.color4 + getExtraColor() : (i == 5 ? ConfigManager.settings.color5 + getExtraColor() : (i == 6 ? ConfigManager.settings.color6 + getExtraColor() : (i == 7 ? ConfigManager.settings.color7 + getExtraColor() : (i == 8 ? ConfigManager.settings.color8 + getExtraColor() : (i == 9 ? ConfigManager.settings.color9 + getExtraColor() : (i == 10 ? ConfigManager.settings.color10 + getExtraColor() : "")))))))));
    }

    public static String cc(int i)
    {
        return i == 1 ? ConfigManager.settings.color1 : (i == 2 ? ConfigManager.settings.color2 : (i == 3 ? ConfigManager.settings.color3 : (i == 4 ? ConfigManager.settings.color4 : (i == 5 ? ConfigManager.settings.color5 : (i == 6 ? ConfigManager.settings.color6 : (i == 7 ? ConfigManager.settings.color7 : (i == 7 ? ConfigManager.settings.color8 : (i == 9 ? ConfigManager.settings.color9 : (i == 10 ? ConfigManager.settings.color10 : "")))))))));
    }

    public static int colorToID(String s)
    {
        try
        {
            s = s.replace("" + c + "", "");
            return s.equals("a") ? 10 : (s.equals("b") ? 11 : (s.equals("c") ? 12 : (s.equals("d") ? 13 : (s.equals("e") ? 14 : (s.equals("f") ? 15 : Integer.parseInt(s))))));
        }
        catch (Exception var2)
        {
            return 0;
        }
    }

    public static String getC()
    {
        return c;
    }

    public static String fix(String i)
    {
        return i.replace("\u00c2", "");
    }

    public static String IDToColor(int i)
    {
        return fix(IDToColorOld(i));
    }

    public static String booleanToColor(Boolean b)
    {
        return b.booleanValue() ? fix("" + c + "a") : fix("" + c + "c");
    }

    public static String IDToColorOld(int i)
    {
        return i == 10 ? "" + c + "a" : (i == 11 ? "" + c + "b" : (i == 12 ? "" + c + "c" : (i == 13 ? "" + c + "d" : (i == 14 ? "" + c + "e" : (i == 15 ? "" + c + "f" : "" + c + "" + i)))));
    }

    public static int toRGB(int r, int g, int b, int a)
    {
        return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255) << 0;
    }
}
