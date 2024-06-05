package tk.rektsky.Utils;

import java.util.*;

public class RektSkyUtil
{
    public static String genRandomString(final int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final Random random = new Random();
        final StringBuilder output = new StringBuilder();
        for (int i = 0; i != len; ++i) {
            output.append(chars.charAt(random.nextInt(chars.length())));
        }
        return output.toString();
    }
}
