/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.security.MessageDigest;
import org.apache.logging.log4j.util.Strings;

public final class NameUtil {
    private static final int MASK = 255;

    private NameUtil() {
    }

    public static String getSubName(String string) {
        if (Strings.isEmpty(string)) {
            return null;
        }
        int n = string.lastIndexOf(46);
        return n > 0 ? string.substring(0, n) : "";
    }

    public static String md5(String string) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(string.getBytes());
            byte[] byArray = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte by : byArray) {
                String string2 = Integer.toHexString(0xFF & by);
                if (string2.length() == 1) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(string2);
            }
            return stringBuilder.toString();
        } catch (Exception exception) {
            return string;
        }
    }
}

