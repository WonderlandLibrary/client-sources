/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.util.Strings;

public final class Transform {
    private static final String CDATA_START = "<![CDATA[";
    private static final String CDATA_END = "]]>";
    private static final String CDATA_PSEUDO_END = "]]&gt;";
    private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
    private static final int CDATA_END_LEN = 3;

    private Transform() {
    }

    public static String escapeHtmlTags(String string) {
        if (Strings.isEmpty(string) || string.indexOf(34) == -1 && string.indexOf(38) == -1 && string.indexOf(60) == -1 && string.indexOf(62) == -1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 6);
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c > '>') {
                stringBuilder.append(c);
                continue;
            }
            if (c == '<') {
                stringBuilder.append("&lt;");
                continue;
            }
            if (c == '>') {
                stringBuilder.append("&gt;");
                continue;
            }
            if (c == '&') {
                stringBuilder.append("&amp;");
                continue;
            }
            if (c == '\"') {
                stringBuilder.append("&quot;");
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static void appendEscapingCData(StringBuilder stringBuilder, String string) {
        if (string != null) {
            int n = string.indexOf(CDATA_END);
            if (n < 0) {
                stringBuilder.append(string);
            } else {
                int n2 = 0;
                while (n > -1) {
                    stringBuilder.append(string.substring(n2, n));
                    stringBuilder.append(CDATA_EMBEDED_END);
                    n2 = n + CDATA_END_LEN;
                    if (n2 < string.length()) {
                        n = string.indexOf(CDATA_END, n2);
                        continue;
                    }
                    return;
                }
                stringBuilder.append(string.substring(n2));
            }
        }
    }

    public static String escapeJsonControlCharacters(String string) {
        if (Strings.isEmpty(string) || string.indexOf(34) == -1 && string.indexOf(92) == -1 && string.indexOf(47) == -1 && string.indexOf(8) == -1 && string.indexOf(12) == -1 && string.indexOf(10) == -1 && string.indexOf(13) == -1 && string.indexOf(9) == -1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 6);
        int n = string.length();
        block10: for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            String string2 = "\\";
            switch (c) {
                case '\"': {
                    stringBuilder.append("\\");
                    stringBuilder.append(c);
                    continue block10;
                }
                case '\\': {
                    stringBuilder.append("\\");
                    stringBuilder.append(c);
                    continue block10;
                }
                case '/': {
                    stringBuilder.append("\\");
                    stringBuilder.append(c);
                    continue block10;
                }
                case '\b': {
                    stringBuilder.append("\\");
                    stringBuilder.append('b');
                    continue block10;
                }
                case '\f': {
                    stringBuilder.append("\\");
                    stringBuilder.append('f');
                    continue block10;
                }
                case '\n': {
                    stringBuilder.append("\\");
                    stringBuilder.append('n');
                    continue block10;
                }
                case '\r': {
                    stringBuilder.append("\\");
                    stringBuilder.append('r');
                    continue block10;
                }
                case '\t': {
                    stringBuilder.append("\\");
                    stringBuilder.append('t');
                    continue block10;
                }
                default: {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }
}

