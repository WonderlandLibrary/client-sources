package org.jsoup.helper;

import java.util.*;
import java.net.*;

public final class StringUtil
{
    private static final String[] padding;
    
    public static String join(final Collection strings, final String sep) {
        return join(strings.iterator(), sep);
    }
    
    public static String join(final Iterator strings, final String sep) {
        if (!strings.hasNext()) {
            return "";
        }
        final String start = strings.next().toString();
        if (!strings.hasNext()) {
            return start;
        }
        final StringBuilder sb = new StringBuilder(64).append(start);
        while (strings.hasNext()) {
            sb.append(sep);
            sb.append(strings.next());
        }
        return sb.toString();
    }
    
    public static String padding(final int width) {
        if (width < 0) {
            throw new IllegalArgumentException("width must be > 0");
        }
        if (width < StringUtil.padding.length) {
            return StringUtil.padding[width];
        }
        final char[] out = new char[width];
        for (int i = 0; i < width; ++i) {
            out[i] = ' ';
        }
        return String.valueOf(out);
    }
    
    public static boolean isBlank(final String string) {
        if (string == null || string.length() == 0) {
            return true;
        }
        for (int l = string.length(), i = 0; i < l; ++i) {
            if (!isWhitespace(string.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isNumeric(final String string) {
        if (string == null || string.length() == 0) {
            return false;
        }
        for (int l = string.length(), i = 0; i < l; ++i) {
            if (!Character.isDigit(string.codePointAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isWhitespace(final int c) {
        return c == 32 || c == 9 || c == 10 || c == 12 || c == 13;
    }
    
    public static String normaliseWhitespace(final String string) {
        final StringBuilder sb = new StringBuilder(string.length());
        appendNormalisedWhitespace(sb, string, false);
        return sb.toString();
    }
    
    public static void appendNormalisedWhitespace(final StringBuilder accum, final String string, final boolean stripLeading) {
        boolean lastWasWhite = false;
        boolean reachedNonWhite = false;
        int c;
        for (int len = string.length(), i = 0; i < len; i += Character.charCount(c)) {
            c = string.codePointAt(i);
            if (isWhitespace(c)) {
                if (!stripLeading || reachedNonWhite) {
                    if (!lastWasWhite) {
                        accum.append(' ');
                        lastWasWhite = true;
                    }
                }
            }
            else {
                accum.appendCodePoint(c);
                lastWasWhite = false;
                reachedNonWhite = true;
            }
        }
    }
    
    public static boolean in(final String needle, final String... haystack) {
        for (final String hay : haystack) {
            if (hay.equals(needle)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean inSorted(final String needle, final String[] haystack) {
        return Arrays.binarySearch(haystack, needle) >= 0;
    }
    
    public static URL resolve(URL base, String relUrl) throws MalformedURLException {
        if (relUrl.startsWith("?")) {
            relUrl = base.getPath() + relUrl;
        }
        if (relUrl.indexOf(46) == 0 && base.getFile().indexOf(47) != 0) {
            base = new URL(base.getProtocol(), base.getHost(), base.getPort(), "/" + base.getFile());
        }
        return new URL(base, relUrl);
    }
    
    public static String resolve(final String baseUrl, final String relUrl) {
        try {
            URL base;
            try {
                base = new URL(baseUrl);
            }
            catch (MalformedURLException e) {
                final URL abs = new URL(relUrl);
                return abs.toExternalForm();
            }
            return resolve(base, relUrl).toExternalForm();
        }
        catch (MalformedURLException e) {
            return "";
        }
    }
    
    static {
        padding = new String[] { "", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          " };
    }
}
