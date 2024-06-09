/*
 * Decompiled with CFR 0_118.
 */
package optifine;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StrUtils {
    public static boolean equalsMask(String str, String mask, char wildChar, char wildCharSingle) {
        if (mask != null && str != null) {
            String startTok;
            if (mask.indexOf(wildChar) < 0) {
                return mask.indexOf(wildCharSingle) < 0 ? mask.equals(str) : StrUtils.equalsMaskSingle(str, mask, wildCharSingle);
            }
            ArrayList<String> tokens = new ArrayList<String>();
            String wildCharStr = "" + wildChar;
            if (mask.startsWith(wildCharStr)) {
                tokens.add("");
            }
            StringTokenizer tok = new StringTokenizer(mask, wildCharStr);
            while (tok.hasMoreElements()) {
                tokens.add(tok.nextToken());
            }
            if (mask.endsWith(wildCharStr)) {
                tokens.add("");
            }
            if (!StrUtils.startsWithMaskSingle(str, startTok = (String)tokens.get(0), wildCharSingle)) {
                return false;
            }
            String endTok = (String)tokens.get(tokens.size() - 1);
            if (!StrUtils.endsWithMaskSingle(str, endTok, wildCharSingle)) {
                return false;
            }
            int currPos = 0;
            int i = 0;
            while (i < tokens.size()) {
                String token = (String)tokens.get(i);
                if (token.length() > 0) {
                    int foundPos = StrUtils.indexOfMaskSingle(str, token, currPos, wildCharSingle);
                    if (foundPos < 0) {
                        return false;
                    }
                    currPos = foundPos + token.length();
                }
                ++i;
            }
            return true;
        }
        if (mask == str) {
            return true;
        }
        return false;
    }

    private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
        if (str != null && mask != null) {
            if (str.length() != mask.length()) {
                return false;
            }
            int i = 0;
            while (i < mask.length()) {
                char maskChar = mask.charAt(i);
                if (maskChar != wildCharSingle && str.charAt(i) != maskChar) {
                    return false;
                }
                ++i;
            }
            return true;
        }
        if (str == mask) {
            return true;
        }
        return false;
    }

    private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
        if (str != null && mask != null) {
            if (startPos >= 0 && startPos <= str.length()) {
                if (str.length() < startPos + mask.length()) {
                    return -1;
                }
                int i = startPos;
                while (i + mask.length() <= str.length()) {
                    String subStr = str.substring(i, i + mask.length());
                    if (StrUtils.equalsMaskSingle(subStr, mask, wildCharSingle)) {
                        return i;
                    }
                    ++i;
                }
                return -1;
            }
            return -1;
        }
        return -1;
    }

    private static boolean endsWithMaskSingle(String str, String mask, char wildCharSingle) {
        if (str != null && mask != null) {
            if (str.length() < mask.length()) {
                return false;
            }
            String subStr = str.substring(str.length() - mask.length(), str.length());
            return StrUtils.equalsMaskSingle(subStr, mask, wildCharSingle);
        }
        if (str == mask) {
            return true;
        }
        return false;
    }

    private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
        if (str != null && mask != null) {
            if (str.length() < mask.length()) {
                return false;
            }
            String subStr = str.substring(0, mask.length());
            return StrUtils.equalsMaskSingle(subStr, mask, wildCharSingle);
        }
        if (str == mask) {
            return true;
        }
        return false;
    }

    public static boolean equalsMask(String str, String[] masks, char wildChar) {
        int i = 0;
        while (i < masks.length) {
            String mask = masks[i];
            if (StrUtils.equalsMask(str, mask, wildChar)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean equalsMask(String str, String mask, char wildChar) {
        if (mask != null && str != null) {
            String startTok;
            if (mask.indexOf(wildChar) < 0) {
                return mask.equals(str);
            }
            ArrayList<String> tokens = new ArrayList<String>();
            String wildCharStr = "" + wildChar;
            if (mask.startsWith(wildCharStr)) {
                tokens.add("");
            }
            StringTokenizer tok = new StringTokenizer(mask, wildCharStr);
            while (tok.hasMoreElements()) {
                tokens.add(tok.nextToken());
            }
            if (mask.endsWith(wildCharStr)) {
                tokens.add("");
            }
            if (!str.startsWith(startTok = (String)tokens.get(0))) {
                return false;
            }
            String endTok = (String)tokens.get(tokens.size() - 1);
            if (!str.endsWith(endTok)) {
                return false;
            }
            int currPos = 0;
            int i = 0;
            while (i < tokens.size()) {
                String token = (String)tokens.get(i);
                if (token.length() > 0) {
                    int foundPos = str.indexOf(token, currPos);
                    if (foundPos < 0) {
                        return false;
                    }
                    currPos = foundPos + token.length();
                }
                ++i;
            }
            return true;
        }
        if (mask == str) {
            return true;
        }
        return false;
    }

    public static String[] split(String str, String separators) {
        if (str != null && str.length() > 0) {
            if (separators == null) {
                return new String[]{str};
            }
            ArrayList<String> tokens = new ArrayList<String>();
            int startPos = 0;
            int i = 0;
            while (i < str.length()) {
                char ch = str.charAt(i);
                if (StrUtils.equals(ch, separators)) {
                    tokens.add(str.substring(startPos, i));
                    startPos = i + 1;
                }
                ++i;
            }
            tokens.add(str.substring(startPos, str.length()));
            return tokens.toArray(new String[tokens.size()]);
        }
        return new String[0];
    }

    private static boolean equals(char ch, String matches) {
        int i = 0;
        while (i < matches.length()) {
            if (matches.charAt(i) == ch) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean equalsTrim(String a, String b) {
        if (a != null) {
            a = a.trim();
        }
        if (b != null) {
            b = b.trim();
        }
        return StrUtils.equals(a, (Object)b);
    }

    public static boolean isEmpty(String string) {
        return string == null ? true : string.trim().length() <= 0;
    }

    public static String stringInc(String str) {
        String test;
        int val = StrUtils.parseInt(str, -1);
        if (val == -1) {
            return "";
        }
        return (test = "" + ++val).length() > str.length() ? "" : StrUtils.fillLeft("" + val, str.length(), '0');
    }

    public static int parseInt(String s, int defVal) {
        if (s == null) {
            return defVal;
        }
        try {
            return Integer.parseInt(s);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static boolean isFilled(String string) {
        return !StrUtils.isEmpty(string);
    }

    public static String addIfNotContains(String target, String source) {
        int i = 0;
        while (i < source.length()) {
            if (target.indexOf(source.charAt(i)) < 0) {
                target = String.valueOf(target) + source.charAt(i);
            }
            ++i;
        }
        return target;
    }

    public static String fillLeft(String s, int len, char fillChar) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= len) {
            return s;
        }
        StringBuffer buf = new StringBuffer(s);
        while (buf.length() < len) {
            buf.insert(0, fillChar);
        }
        return buf.toString();
    }

    public static String fillRight(String s, int len, char fillChar) {
        if (s == null) {
            s = "";
        }
        if (s.length() >= len) {
            return s;
        }
        StringBuffer buf = new StringBuffer(s);
        while (buf.length() < len) {
            buf.append(fillChar);
        }
        return buf.toString();
    }

    public static boolean equals(Object a, Object b) {
        return a == b ? true : (a != null && a.equals(b) ? true : b != null && b.equals(a));
    }

    public static boolean startsWith(String str, String[] prefixes) {
        if (str == null) {
            return false;
        }
        if (prefixes == null) {
            return false;
        }
        int i = 0;
        while (i < prefixes.length) {
            String prefix = prefixes[i];
            if (str.startsWith(prefix)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static boolean endsWith(String str, String[] suffixes) {
        if (str == null) {
            return false;
        }
        if (suffixes == null) {
            return false;
        }
        int i = 0;
        while (i < suffixes.length) {
            String suffix = suffixes[i];
            if (str.endsWith(suffix)) {
                return true;
            }
            ++i;
        }
        return false;
    }

    public static String removePrefix(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length());
            }
            return str;
        }
        return str;
    }

    public static String removeSuffix(String str, String suffix) {
        if (str != null && suffix != null) {
            if (str.endsWith(suffix)) {
                str = str.substring(0, str.length() - suffix.length());
            }
            return str;
        }
        return str;
    }

    public static String replaceSuffix(String str, String suffix, String suffixNew) {
        if (str != null && suffix != null) {
            if (suffixNew == null) {
                suffixNew = "";
            }
            if (str.endsWith(suffix)) {
                str = str.substring(0, str.length() - suffix.length());
            }
            return String.valueOf(str) + suffixNew;
        }
        return str;
    }

    public static int findPrefix(String[] strs, String prefix) {
        if (strs != null && prefix != null) {
            int i = 0;
            while (i < strs.length) {
                String str = strs[i];
                if (str.startsWith(prefix)) {
                    return i;
                }
                ++i;
            }
            return -1;
        }
        return -1;
    }

    public static int findSuffix(String[] strs, String suffix) {
        if (strs != null && suffix != null) {
            int i = 0;
            while (i < strs.length) {
                String str = strs[i];
                if (str.endsWith(suffix)) {
                    return i;
                }
                ++i;
            }
            return -1;
        }
        return -1;
    }

    public static String[] remove(String[] strs, int start, int end) {
        if (strs == null) {
            return strs;
        }
        if (end > 0 && start < strs.length) {
            if (start >= end) {
                return strs;
            }
            ArrayList<String> list = new ArrayList<String>(strs.length);
            int strsNew = 0;
            while (strsNew < strs.length) {
                String str = strs[strsNew];
                if (strsNew < start || strsNew >= end) {
                    list.add(str);
                }
                ++strsNew;
            }
            String[] var6 = list.toArray(new String[list.size()]);
            return var6;
        }
        return strs;
    }

    public static String removeSuffix(String str, String[] suffixes) {
        if (str != null && suffixes != null) {
            int strLen = str.length();
            int i = 0;
            while (i < suffixes.length) {
                String suffix = suffixes[i];
                if ((str = StrUtils.removeSuffix(str, suffix)).length() != strLen) break;
                ++i;
            }
            return str;
        }
        return str;
    }

    public static String removePrefix(String str, String[] prefixes) {
        if (str != null && prefixes != null) {
            int strLen = str.length();
            int i = 0;
            while (i < prefixes.length) {
                String prefix = prefixes[i];
                if ((str = StrUtils.removePrefix(str, prefix)).length() != strLen) break;
                ++i;
            }
            return str;
        }
        return str;
    }

    public static String removePrefixSuffix(String str, String[] prefixes, String[] suffixes) {
        str = StrUtils.removePrefix(str, prefixes);
        str = StrUtils.removeSuffix(str, suffixes);
        return str;
    }

    public static String removePrefixSuffix(String str, String prefix, String suffix) {
        return StrUtils.removePrefixSuffix(str, new String[]{prefix}, new String[]{suffix});
    }

    public static String getSegment(String str, String start, String end) {
        if (str != null && start != null && end != null) {
            int posStart = str.indexOf(start);
            if (posStart < 0) {
                return null;
            }
            int posEnd = str.indexOf(end, posStart);
            return posEnd < 0 ? null : str.substring(posStart, posEnd + end.length());
        }
        return null;
    }
}

