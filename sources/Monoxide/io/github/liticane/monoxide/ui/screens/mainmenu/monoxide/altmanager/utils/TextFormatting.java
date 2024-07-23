package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.altmanager.utils;

@SuppressWarnings("unused")
public enum TextFormatting {
    BLACK('0', -16777216),
    DARK_BLUE('1', -16777046),
    DARK_GREEN('2', -16733696),
    DARK_AQUA('3', -16733526),
    DARK_RED('4', -5636096),
    DARK_PURPLE('5', -5635926),
    GOLD('6', -22016),
    GRAY('7', -5592406),
    DARK_GRAY('8', -11184811),
    BLUE('9', -11184641),
    GREEN('a', -11141291),
    AQUA('b', -11141121),
    RED('c', -43691),
    LIGHT_PURPLE('d', -43521),
    YELLOW('e', -171),
    WHITE('f', -1),
    MAGIC('k', 0),
    BOLD('l', 0),
    STRIKETHROUGH('m', 0),
    UNDERLINE('n', 0),
    ITALIC('o', 0),
    RESET('r', 0);

    private final String toString;
    private final int rgb;

    public static final char COLOR_CHAR = '§';

    TextFormatting(char code, int rgb) {
        this.rgb = rgb;
        this.toString = new String(new char[]{COLOR_CHAR, code});
    }

    public int getRGB() {
        return this.rgb;
    }

    public static String translate(String text) {
        char[] b = text.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = TextFormatting.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    @Override
    public String toString() {
        return this.toString;
    }
}