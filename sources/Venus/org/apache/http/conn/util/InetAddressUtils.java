/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.util;

import java.util.regex.Pattern;

public class InetAddressUtils {
    private static final String IPV4_BASIC_PATTERN_STRING = "(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])";
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    private static final Pattern IPV4_MAPPED_IPV6_PATTERN = Pattern.compile("^::[fF]{4}:(([1-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){1}(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){2}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    private static final Pattern IPV6_STD_PATTERN = Pattern.compile("^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$");
    private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)::(([0-9A-Fa-f]{1,4}(:[0-9A-Fa-f]{1,4}){0,5})?)$");
    private static final char COLON_CHAR = ':';
    private static final int MAX_COLON_COUNT = 7;

    private InetAddressUtils() {
    }

    public static boolean isIPv4Address(String string) {
        return IPV4_PATTERN.matcher(string).matches();
    }

    public static boolean isIPv4MappedIPv64Address(String string) {
        return IPV4_MAPPED_IPV6_PATTERN.matcher(string).matches();
    }

    public static boolean isIPv6StdAddress(String string) {
        return IPV6_STD_PATTERN.matcher(string).matches();
    }

    public static boolean isIPv6HexCompressedAddress(String string) {
        int n = 0;
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != ':') continue;
            ++n;
        }
        return n <= 7 && IPV6_HEX_COMPRESSED_PATTERN.matcher(string).matches();
    }

    public static boolean isIPv6Address(String string) {
        return InetAddressUtils.isIPv6StdAddress(string) || InetAddressUtils.isIPv6HexCompressedAddress(string);
    }
}

