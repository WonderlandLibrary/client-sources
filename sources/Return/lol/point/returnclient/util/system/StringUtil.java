package lol.point.returnclient.util.system;

import java.util.Random;

public final class StringUtil {

    public static String getColorCodeFromString(String input) {
        switch (input.toLowerCase()) {
            case "white" -> {
                return "§f";
            }
            case "light gray" -> {
                return "§7";
            }
            case "dark gray" -> {
                return "§8";
            }
            case "black" -> {
                return "§0";
            }
            default -> {
                return "";
            }
        }
    }

    public static String generateRandomString(String prefix, int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(prefix);

        for (int i = prefix.length(); i < length; i++) {
            int randomNumber = random.nextInt(length);
            stringBuilder.append(randomNumber);
        }

        return stringBuilder.toString();
    }
}
