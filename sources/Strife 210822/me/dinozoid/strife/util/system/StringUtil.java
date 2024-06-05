package me.dinozoid.strife.util.system;

import net.minecraft.client.gui.GuiScreen;

import java.util.regex.Pattern;

public final class StringUtil {

    private static final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        return pattern.matcher(strNum).matches();
    }

    public static String upperSnakeCaseToPascal(String s) {
        if (s == null) return null;
        if (s.length() == 1) return Character.toString(s.charAt(0));
        return s.charAt(0) + s.substring(1).toLowerCase();
    }

    public static String getTrimmedClipboardContents() {
        String data = GuiScreen.getClipboardString();
        data = data.trim();
        if (data.indexOf('\n') != -1)
            data = data.replace("\n", "");
        return data;
    }

}
