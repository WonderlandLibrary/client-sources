package client.metaware.impl.utils.render;

import net.minecraft.client.gui.GuiScreen;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

public final class StringUtils {

    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)" + "§" + "[0-9A-FK-OR]");
    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        return pattern.matcher(strNum).matches();
    }

    public static int removeDecimals(final double number) {
        final DecimalFormat decimalFormat = new DecimalFormat("####################################");
        return Integer.parseInt(decimalFormat.format(number));
    }

    public static String upperSnakeCaseToPascal(String s) {
        if (s == null) return null;
        if (s.length() == 1) return Character.toString(s.charAt(0));
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public static String stripFormatting(String input) {
        return input == null ? null : STRIP_FORMATTING_PATTERN.matcher(input).replaceAll("");
    }


    public static String translateAlternateColorCodes(char character, String string) {
        return string.replaceAll(String.valueOf(character), "\u00A7");
    }

    public static String getTrimmedClipboardContents() {
        String data = GuiScreen.getClipboardString();
        data = data.trim();
        if (data.indexOf('\n') != -1)
            data = data.replace("\n", "");
        return data;
    }

}