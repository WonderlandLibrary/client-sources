package club.strifeclient.util.system;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Arrays;

public final class StringUtil {
    public static void copyToClipboard(String contents) {
        StringSelection selection = new StringSelection(contents);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
    public static String concatStringsWithDashes(String... strings) {
        StringBuilder stringBuilder = new StringBuilder(strings[0]);
        Arrays.stream(strings).skip(1).forEach((string) -> stringBuilder.append("-").append(string));
        return stringBuilder.toString();
    }
    public static Enum<?> getEnumPrimitiveFromString(Enum<?>[] constants, String string) {
        return Arrays.stream(constants).filter(value -> String.valueOf(value).equalsIgnoreCase(string)).findFirst().orElse(null);
    }
}
