/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class StrUtils {
    public static boolean equalsMask(String string, String string2, char c, char c2) {
        if (string2 != null && string != null) {
            String string3;
            if (string2.indexOf(c) < 0) {
                return string2.indexOf(c2) < 0 ? string2.equals(string) : StrUtils.equalsMaskSingle(string, string2, c2);
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            String string4 = "" + c;
            if (string2.startsWith(string4)) {
                arrayList.add("");
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string2, string4);
            while (stringTokenizer.hasMoreElements()) {
                arrayList.add(stringTokenizer.nextToken());
            }
            if (string2.endsWith(string4)) {
                arrayList.add("");
            }
            if (!StrUtils.startsWithMaskSingle(string, string3 = (String)arrayList.get(0), c2)) {
                return true;
            }
            String string5 = (String)arrayList.get(arrayList.size() - 1);
            if (!StrUtils.endsWithMaskSingle(string, string5, c2)) {
                return true;
            }
            int n = 0;
            for (int i = 0; i < arrayList.size(); ++i) {
                String string6 = (String)arrayList.get(i);
                if (string6.length() <= 0) continue;
                int n2 = StrUtils.indexOfMaskSingle(string, string6, n, c2);
                if (n2 < 0) {
                    return true;
                }
                n = n2 + string6.length();
            }
            return false;
        }
        return string2 == string;
    }

    private static boolean equalsMaskSingle(String string, String string2, char c) {
        if (string != null && string2 != null) {
            if (string.length() != string2.length()) {
                return true;
            }
            for (int i = 0; i < string2.length(); ++i) {
                char c2 = string2.charAt(i);
                if (c2 == c || string.charAt(i) == c2) continue;
                return true;
            }
            return false;
        }
        return string == string2;
    }

    private static int indexOfMaskSingle(String string, String string2, int n, char c) {
        if (string != null && string2 != null) {
            if (n >= 0 && n <= string.length()) {
                if (string.length() < n + string2.length()) {
                    return 1;
                }
                int n2 = n;
                while (n2 + string2.length() <= string.length()) {
                    String string3 = string.substring(n2, n2 + string2.length());
                    if (StrUtils.equalsMaskSingle(string3, string2, c)) {
                        return n2;
                    }
                    ++n2;
                }
                return 1;
            }
            return 1;
        }
        return 1;
    }

    private static boolean endsWithMaskSingle(String string, String string2, char c) {
        if (string != null && string2 != null) {
            if (string.length() < string2.length()) {
                return true;
            }
            String string3 = string.substring(string.length() - string2.length(), string.length());
            return StrUtils.equalsMaskSingle(string3, string2, c);
        }
        return string == string2;
    }

    private static boolean startsWithMaskSingle(String string, String string2, char c) {
        if (string != null && string2 != null) {
            if (string.length() < string2.length()) {
                return true;
            }
            String string3 = string.substring(0, string2.length());
            return StrUtils.equalsMaskSingle(string3, string2, c);
        }
        return string == string2;
    }

    public static boolean equalsMask(String string, String[] stringArray, char c) {
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (!StrUtils.equalsMask(string, string2, c)) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsMask(String string, String string2, char c) {
        if (string2 != null && string != null) {
            String string3;
            if (string2.indexOf(c) < 0) {
                return string2.equals(string);
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            String string4 = "" + c;
            if (string2.startsWith(string4)) {
                arrayList.add("");
            }
            StringTokenizer stringTokenizer = new StringTokenizer(string2, string4);
            while (stringTokenizer.hasMoreElements()) {
                arrayList.add(stringTokenizer.nextToken());
            }
            if (string2.endsWith(string4)) {
                arrayList.add("");
            }
            if (!string.startsWith(string3 = (String)arrayList.get(0))) {
                return true;
            }
            String string5 = (String)arrayList.get(arrayList.size() - 1);
            if (!string.endsWith(string5)) {
                return true;
            }
            int n = 0;
            for (int i = 0; i < arrayList.size(); ++i) {
                String string6 = (String)arrayList.get(i);
                if (string6.length() <= 0) continue;
                int n2 = string.indexOf(string6, n);
                if (n2 < 0) {
                    return true;
                }
                n = n2 + string6.length();
            }
            return false;
        }
        return string2 == string;
    }

    public static String[] split(String string, String string2) {
        if (string != null && string.length() > 0) {
            if (string2 == null) {
                return new String[]{string};
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            int n = 0;
            for (int i = 0; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (!StrUtils.equals(c, string2)) continue;
                arrayList.add(string.substring(n, i));
                n = i + 1;
            }
            arrayList.add(string.substring(n, string.length()));
            return arrayList.toArray(new String[arrayList.size()]);
        }
        return new String[0];
    }

    private static boolean equals(char c, String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != c) continue;
            return false;
        }
        return true;
    }

    public static boolean equalsTrim(String string, String string2) {
        if (string != null) {
            string = string.trim();
        }
        if (string2 != null) {
            string2 = string2.trim();
        }
        return StrUtils.equals(string, (Object)string2);
    }

    public static boolean isEmpty(String string) {
        if (string == null) {
            return false;
        }
        return string.trim().length() <= 0;
    }

    public static String stringInc(String string) {
        String string2;
        int n = StrUtils.parseInt(string, -1);
        if (n == -1) {
            return "";
        }
        return (string2 = "" + ++n).length() > string.length() ? "" : StrUtils.fillLeft("" + n, string.length(), '0');
    }

    public static int parseInt(String string, int n) {
        if (string == null) {
            return n;
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static boolean isFilled(String string) {
        return !StrUtils.isEmpty(string);
    }

    public static String addIfNotContains(String object, String string) {
        for (int i = 0; i < string.length(); ++i) {
            if (((String)object).indexOf(string.charAt(i)) >= 0) continue;
            object = (String)object + string.charAt(i);
        }
        return object;
    }

    public static String fillLeft(String string, int n, char c) {
        if (string == null) {
            string = "";
        }
        if (string.length() >= n) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = n - string.length();
        while (stringBuilder.length() < n2) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString() + string;
    }

    public static String fillRight(String string, int n, char c) {
        if (string == null) {
            string = "";
        }
        if (string.length() >= n) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length() < n) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static boolean equals(Object object, Object object2) {
        if (object == object2) {
            return false;
        }
        if (object != null && object.equals(object2)) {
            return false;
        }
        return object2 != null && object2.equals(object);
    }

    public static boolean startsWith(String string, String[] stringArray) {
        if (string == null) {
            return true;
        }
        if (stringArray == null) {
            return true;
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (!string.startsWith(string2)) continue;
            return false;
        }
        return true;
    }

    public static boolean endsWith(String string, String[] stringArray) {
        if (string == null) {
            return true;
        }
        if (stringArray == null) {
            return true;
        }
        for (int i = 0; i < stringArray.length; ++i) {
            String string2 = stringArray[i];
            if (!string.endsWith(string2)) continue;
            return false;
        }
        return true;
    }

    public static String removePrefix(String string, String string2) {
        if (string != null && string2 != null) {
            if (string.startsWith(string2)) {
                string = string.substring(string2.length());
            }
            return string;
        }
        return string;
    }

    public static String removeSuffix(String string, String string2) {
        if (string != null && string2 != null) {
            if (string.endsWith(string2)) {
                string = string.substring(0, string.length() - string2.length());
            }
            return string;
        }
        return string;
    }

    public static String replaceSuffix(String string, String string2, String string3) {
        if (string != null && string2 != null) {
            if (!string.endsWith(string2)) {
                return string;
            }
            if (string3 == null) {
                string3 = "";
            }
            string = string.substring(0, string.length() - string2.length());
            return string + string3;
        }
        return string;
    }

    public static String replacePrefix(String string, String string2, String string3) {
        if (string != null && string2 != null) {
            if (!string.startsWith(string2)) {
                return string;
            }
            if (string3 == null) {
                string3 = "";
            }
            string = string.substring(string2.length());
            return string3 + string;
        }
        return string;
    }

    public static int findPrefix(String[] stringArray, String string) {
        if (stringArray != null && string != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string2 = stringArray[i];
                if (!string2.startsWith(string)) continue;
                return i;
            }
            return 1;
        }
        return 1;
    }

    public static int findSuffix(String[] stringArray, String string) {
        if (stringArray != null && string != null) {
            for (int i = 0; i < stringArray.length; ++i) {
                String string2 = stringArray[i];
                if (!string2.endsWith(string)) continue;
                return i;
            }
            return 1;
        }
        return 1;
    }

    public static String[] remove(String[] stringArray, int n, int n2) {
        if (stringArray == null) {
            return stringArray;
        }
        if (n2 > 0 && n < stringArray.length) {
            if (n >= n2) {
                return stringArray;
            }
            ArrayList<String> arrayList = new ArrayList<String>(stringArray.length);
            for (int i = 0; i < stringArray.length; ++i) {
                String string = stringArray[i];
                if (i >= n && i < n2) continue;
                arrayList.add(string);
            }
            return arrayList.toArray(new String[arrayList.size()]);
        }
        return stringArray;
    }

    public static String removeSuffix(String string, String[] stringArray) {
        if (string != null && stringArray != null) {
            String string2;
            int n = string.length();
            for (int i = 0; i < stringArray.length && (string = StrUtils.removeSuffix(string, string2 = stringArray[i])).length() == n; ++i) {
            }
            return string;
        }
        return string;
    }

    public static String removePrefix(String string, String[] stringArray) {
        if (string != null && stringArray != null) {
            String string2;
            int n = string.length();
            for (int i = 0; i < stringArray.length && (string = StrUtils.removePrefix(string, string2 = stringArray[i])).length() == n; ++i) {
            }
            return string;
        }
        return string;
    }

    public static String removePrefixSuffix(String string, String[] stringArray, String[] stringArray2) {
        string = StrUtils.removePrefix(string, stringArray);
        return StrUtils.removeSuffix(string, stringArray2);
    }

    public static String removePrefixSuffix(String string, String string2, String string3) {
        return StrUtils.removePrefixSuffix(string, new String[]{string2}, new String[]{string3});
    }

    public static String getSegment(String string, String string2, String string3) {
        if (string != null && string2 != null && string3 != null) {
            int n = string.indexOf(string2);
            if (n < 0) {
                return null;
            }
            int n2 = string.indexOf(string3, n);
            return n2 < 0 ? null : string.substring(n, n2 + string3.length());
        }
        return null;
    }

    public static String addSuffixCheck(String string, String string2) {
        if (string != null && string2 != null) {
            return string.endsWith(string2) ? string : string + string2;
        }
        return string;
    }

    public static String addPrefixCheck(String string, String string2) {
        if (string != null && string2 != null) {
            return string.endsWith(string2) ? string : string2 + string;
        }
        return string;
    }

    public static String trim(String string, String string2) {
        if (string != null && string2 != null) {
            string = StrUtils.trimLeading(string, string2);
            return StrUtils.trimTrailing(string, string2);
        }
        return string;
    }

    public static String trimLeading(String string, String string2) {
        if (string != null && string2 != null) {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                if (string2.indexOf(c) >= 0) continue;
                return string.substring(i);
            }
            return "";
        }
        return string;
    }

    public static String trimTrailing(String string, String string2) {
        if (string != null && string2 != null) {
            int n;
            char c;
            int n2;
            for (n2 = n = string.length(); n2 > 0 && string2.indexOf(c = string.charAt(n2 - 1)) >= 0; --n2) {
            }
            return n2 == n ? string : string.substring(0, n2);
        }
        return string;
    }

    public static String replaceChar(String string, char c, char c2) {
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < stringBuilder.length(); ++i) {
            char c3 = stringBuilder.charAt(i);
            if (c3 != c) continue;
            stringBuilder.setCharAt(i, c2);
        }
        return stringBuilder.toString();
    }

    public static String replaceString(String string, String string2, String string3) {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        do {
            n = n2;
            if ((n2 = string.indexOf(string2, n2)) < 0) continue;
            stringBuilder.append(string.substring(n, n2));
            stringBuilder.append(string3);
            n2 += string2.length();
        } while (n2 >= 0);
        stringBuilder.append(string.substring(n));
        return stringBuilder.toString();
    }

    public static String replaceStrings(String string, String[] stringArray, String[] stringArray2) {
        int n;
        CharSequence charSequence;
        if (stringArray.length != stringArray2.length) {
            throw new IllegalArgumentException("Search and replace string arrays have different lengths: findStrs=" + stringArray.length + ", substStrs=" + stringArray2.length);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stringArray.length; ++i) {
            charSequence = stringArray[i];
            if (((String)charSequence).length() <= 0 || StrUtils.indexOf(stringBuilder, (char)(n = ((String)charSequence).charAt(0))) >= 0) continue;
            stringBuilder.append((char)n);
        }
        String string2 = stringBuilder.toString();
        charSequence = new StringBuilder();
        n = 0;
        while (n < string.length()) {
            boolean bl = false;
            char c = string.charAt(n);
            if (string2.indexOf(c) >= 0) {
                for (int i = 0; i < stringArray.length; ++i) {
                    if (!string.startsWith(stringArray[i], n)) continue;
                    ((StringBuilder)charSequence).append(stringArray2[i]);
                    bl = true;
                    n += stringArray[i].length();
                    break;
                }
            }
            if (bl) continue;
            ((StringBuilder)charSequence).append(string.charAt(n));
            ++n;
        }
        return ((StringBuilder)charSequence).toString();
    }

    private static int indexOf(StringBuilder stringBuilder, char c) {
        for (int i = 0; i < stringBuilder.length(); ++i) {
            char c2 = stringBuilder.charAt(i);
            if (c2 != c) continue;
            return i;
        }
        return 1;
    }
}

