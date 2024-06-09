/*
 * Decompiled with CFR 0.145.
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
            for (int i2 = 0; i2 < tokens.size(); ++i2) {
                String token = (String)tokens.get(i2);
                if (token.length() <= 0) continue;
                int foundPos = StrUtils.indexOfMaskSingle(str, token, currPos, wildCharSingle);
                if (foundPos < 0) {
                    return false;
                }
                currPos = foundPos + token.length();
            }
            return true;
        }
        return mask == str;
    }

    private static boolean equalsMaskSingle(String str, String mask, char wildCharSingle) {
        if (str != null && mask != null) {
            if (str.length() != mask.length()) {
                return false;
            }
            for (int i2 = 0; i2 < mask.length(); ++i2) {
                char maskChar = mask.charAt(i2);
                if (maskChar == wildCharSingle || str.charAt(i2) == maskChar) continue;
                return false;
            }
            return true;
        }
        return str == mask;
    }

    private static int indexOfMaskSingle(String str, String mask, int startPos, char wildCharSingle) {
        if (str != null && mask != null) {
            if (startPos >= 0 && startPos <= str.length()) {
                if (str.length() < startPos + mask.length()) {
                    return -1;
                }
                int i2 = startPos;
                while (i2 + mask.length() <= str.length()) {
                    String subStr = str.substring(i2, i2 + mask.length());
                    if (StrUtils.equalsMaskSingle(subStr, mask, wildCharSingle)) {
                        return i2;
                    }
                    ++i2;
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
        return str == mask;
    }

    private static boolean startsWithMaskSingle(String str, String mask, char wildCharSingle) {
        if (str != null && mask != null) {
            if (str.length() < mask.length()) {
                return false;
            }
            String subStr = str.substring(0, mask.length());
            return StrUtils.equalsMaskSingle(subStr, mask, wildCharSingle);
        }
        return str == mask;
    }

    public static boolean equalsMask(String str, String[] masks, char wildChar) {
        for (int i2 = 0; i2 < masks.length; ++i2) {
            String mask = masks[i2];
            if (!StrUtils.equalsMask(str, mask, wildChar)) continue;
            return true;
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
            for (int i2 = 0; i2 < tokens.size(); ++i2) {
                String token = (String)tokens.get(i2);
                if (token.length() <= 0) continue;
                int foundPos = str.indexOf(token, currPos);
                if (foundPos < 0) {
                    return false;
                }
                currPos = foundPos + token.length();
            }
            return true;
        }
        return mask == str;
    }

    public static String[] split(String str, String separators) {
        if (str != null && str.length() > 0) {
            if (separators == null) {
                return new String[]{str};
            }
            ArrayList<String> tokens = new ArrayList<String>();
            int startPos = 0;
            for (int i2 = 0; i2 < str.length(); ++i2) {
                char ch = str.charAt(i2);
                if (!StrUtils.equals(ch, separators)) continue;
                tokens.add(str.substring(startPos, i2));
                startPos = i2 + 1;
            }
            tokens.add(str.substring(startPos, str.length()));
            return tokens.toArray(new String[tokens.size()]);
        }
        return new String[0];
    }

    private static boolean equals(char ch, String matches) {
        for (int i2 = 0; i2 < matches.length(); ++i2) {
            if (matches.charAt(i2) != ch) continue;
            return true;
        }
        return false;
    }

    public static boolean equalsTrim(String a2, String b2) {
        if (a2 != null) {
            a2 = a2.trim();
        }
        if (b2 != null) {
            b2 = b2.trim();
        }
        return StrUtils.equals(a2, (Object)b2);
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

    public static int parseInt(String s2, int defVal) {
        if (s2 == null) {
            return defVal;
        }
        try {
            return Integer.parseInt(s2);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static boolean isFilled(String string) {
        return !StrUtils.isEmpty(string);
    }

    public static String addIfNotContains(String target, String source) {
        for (int i2 = 0; i2 < source.length(); ++i2) {
            if (target.indexOf(source.charAt(i2)) >= 0) continue;
            target = String.valueOf(target) + source.charAt(i2);
        }
        return target;
    }

    public static String fillLeft(String s2, int len, char fillChar) {
        if (s2 == null) {
            s2 = "";
        }
        if (s2.length() >= len) {
            return s2;
        }
        StringBuffer buf = new StringBuffer(s2);
        while (buf.length() < len) {
            buf.insert(0, fillChar);
        }
        return buf.toString();
    }

    public static String fillRight(String s2, int len, char fillChar) {
        if (s2 == null) {
            s2 = "";
        }
        if (s2.length() >= len) {
            return s2;
        }
        StringBuffer buf = new StringBuffer(s2);
        while (buf.length() < len) {
            buf.append(fillChar);
        }
        return buf.toString();
    }

    public static boolean equals(Object a2, Object b2) {
        return a2 == b2 ? true : (a2 != null && a2.equals(b2) ? true : b2 != null && b2.equals(a2));
    }

    public static boolean startsWith(String str, String[] prefixes) {
        if (str == null) {
            return false;
        }
        if (prefixes == null) {
            return false;
        }
        for (int i2 = 0; i2 < prefixes.length; ++i2) {
            String prefix = prefixes[i2];
            if (!str.startsWith(prefix)) continue;
            return true;
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
        for (int i2 = 0; i2 < suffixes.length; ++i2) {
            String suffix = suffixes[i2];
            if (!str.endsWith(suffix)) continue;
            return true;
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
            for (int i2 = 0; i2 < strs.length; ++i2) {
                String str = strs[i2];
                if (!str.startsWith(prefix)) continue;
                return i2;
            }
            return -1;
        }
        return -1;
    }

    public static int findSuffix(String[] strs, String suffix) {
        if (strs != null && suffix != null) {
            for (int i2 = 0; i2 < strs.length; ++i2) {
                String str = strs[i2];
                if (!str.endsWith(suffix)) continue;
                return i2;
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
            for (int strsNew = 0; strsNew < strs.length; ++strsNew) {
                String str = strs[strsNew];
                if (strsNew >= start && strsNew < end) continue;
                list.add(str);
            }
            String[] var6 = list.toArray(new String[list.size()]);
            return var6;
        }
        return strs;
    }

    public static String removeSuffix(String str, String[] suffixes) {
        if (str != null && suffixes != null) {
            int strLen = str.length();
            for (int i2 = 0; i2 < suffixes.length; ++i2) {
                String suffix = suffixes[i2];
                if ((str = StrUtils.removeSuffix(str, suffix)).length() != strLen) break;
            }
            return str;
        }
        return str;
    }

    public static String removePrefix(String str, String[] prefixes) {
        if (str != null && prefixes != null) {
            int strLen = str.length();
            for (int i2 = 0; i2 < prefixes.length; ++i2) {
                String prefix = prefixes[i2];
                if ((str = StrUtils.removePrefix(str, prefix)).length() != strLen) break;
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

