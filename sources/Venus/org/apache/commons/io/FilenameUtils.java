/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;
import org.apache.commons.io.IOCase;

public class FilenameUtils {
    private static final int NOT_FOUND = -1;
    public static final char EXTENSION_SEPARATOR = '.';
    public static final String EXTENSION_SEPARATOR_STR = Character.toString('.');
    private static final char UNIX_SEPARATOR = '/';
    private static final char WINDOWS_SEPARATOR = '\\';
    private static final char SYSTEM_SEPARATOR = File.separatorChar;
    private static final char OTHER_SEPARATOR = FilenameUtils.isSystemWindows() ? (char)47 : (char)92;

    static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == '\\';
    }

    private static boolean isSeparator(char c) {
        return c == '/' || c == '\\';
    }

    public static String normalize(String string) {
        return FilenameUtils.doNormalize(string, SYSTEM_SEPARATOR, true);
    }

    public static String normalize(String string, boolean bl) {
        char c = bl ? (char)'/' : '\\';
        return FilenameUtils.doNormalize(string, c, true);
    }

    public static String normalizeNoEndSeparator(String string) {
        return FilenameUtils.doNormalize(string, SYSTEM_SEPARATOR, false);
    }

    public static String normalizeNoEndSeparator(String string, boolean bl) {
        char c = bl ? (char)'/' : '\\';
        return FilenameUtils.doNormalize(string, c, false);
    }

    private static String doNormalize(String string, char c, boolean bl) {
        int n;
        int n2;
        if (string == null) {
            return null;
        }
        FilenameUtils.failIfNullBytePresent(string);
        int n3 = string.length();
        if (n3 == 0) {
            return string;
        }
        int n4 = FilenameUtils.getPrefixLength(string);
        if (n4 < 0) {
            return null;
        }
        char[] cArray = new char[n3 + 2];
        string.getChars(0, string.length(), cArray, 0);
        char c2 = c == SYSTEM_SEPARATOR ? OTHER_SEPARATOR : SYSTEM_SEPARATOR;
        for (n2 = 0; n2 < cArray.length; ++n2) {
            if (cArray[n2] != c2) continue;
            cArray[n2] = c;
        }
        n2 = 1;
        if (cArray[n3 - 1] != c) {
            cArray[n3++] = c;
            n2 = 0;
        }
        for (n = n4 + 1; n < n3; ++n) {
            if (cArray[n] != c || cArray[n - 1] != c) continue;
            System.arraycopy(cArray, n, cArray, n - 1, n3 - n);
            --n3;
            --n;
        }
        for (n = n4 + 1; n < n3; ++n) {
            if (cArray[n] != c || cArray[n - 1] != '.' || n != n4 + 1 && cArray[n - 2] != c) continue;
            if (n == n3 - 1) {
                n2 = 1;
            }
            System.arraycopy(cArray, n + 1, cArray, n - 1, n3 - n);
            n3 -= 2;
            --n;
        }
        block3: for (n = n4 + 2; n < n3; ++n) {
            if (cArray[n] != c || cArray[n - 1] != '.' || cArray[n - 2] != '.' || n != n4 + 2 && cArray[n - 3] != c) continue;
            if (n == n4 + 2) {
                return null;
            }
            if (n == n3 - 1) {
                n2 = 1;
            }
            for (int i = n - 4; i >= n4; --i) {
                if (cArray[i] != c) continue;
                System.arraycopy(cArray, n + 1, cArray, i + 1, n3 - n);
                n3 -= n - i;
                n = i + 1;
                continue block3;
            }
            System.arraycopy(cArray, n + 1, cArray, n4, n3 - n);
            n3 -= n + 1 - n4;
            n = n4 + 1;
        }
        if (n3 <= 0) {
            return "";
        }
        if (n3 <= n4) {
            return new String(cArray, 0, n3);
        }
        if (n2 != 0 && bl) {
            return new String(cArray, 0, n3);
        }
        return new String(cArray, 0, n3 - 1);
    }

    public static String concat(String string, String string2) {
        int n = FilenameUtils.getPrefixLength(string2);
        if (n < 0) {
            return null;
        }
        if (n > 0) {
            return FilenameUtils.normalize(string2);
        }
        if (string == null) {
            return null;
        }
        int n2 = string.length();
        if (n2 == 0) {
            return FilenameUtils.normalize(string2);
        }
        char c = string.charAt(n2 - 1);
        if (FilenameUtils.isSeparator(c)) {
            return FilenameUtils.normalize(string + string2);
        }
        return FilenameUtils.normalize(string + '/' + string2);
    }

    public static boolean directoryContains(String string, String string2) throws IOException {
        if (string == null) {
            throw new IllegalArgumentException("Directory must not be null");
        }
        if (string2 == null) {
            return true;
        }
        if (IOCase.SYSTEM.checkEquals(string, string2)) {
            return true;
        }
        return IOCase.SYSTEM.checkStartsWith(string2, string);
    }

    public static String separatorsToUnix(String string) {
        if (string == null || string.indexOf(92) == -1) {
            return string;
        }
        return string.replace('\\', '/');
    }

    public static String separatorsToWindows(String string) {
        if (string == null || string.indexOf(47) == -1) {
            return string;
        }
        return string.replace('/', '\\');
    }

    public static String separatorsToSystem(String string) {
        if (string == null) {
            return null;
        }
        if (FilenameUtils.isSystemWindows()) {
            return FilenameUtils.separatorsToWindows(string);
        }
        return FilenameUtils.separatorsToUnix(string);
    }

    public static int getPrefixLength(String string) {
        if (string == null) {
            return 1;
        }
        int n = string.length();
        if (n == 0) {
            return 1;
        }
        char c = string.charAt(0);
        if (c == ':') {
            return 1;
        }
        if (n == 1) {
            if (c == '~') {
                return 1;
            }
            return FilenameUtils.isSeparator(c) ? 1 : 0;
        }
        if (c == '~') {
            int n2 = string.indexOf(47, 1);
            int n3 = string.indexOf(92, 1);
            if (n2 == -1 && n3 == -1) {
                return n + 1;
            }
            n2 = n2 == -1 ? n3 : n2;
            n3 = n3 == -1 ? n2 : n3;
            return Math.min(n2, n3) + 1;
        }
        char c2 = string.charAt(1);
        if (c2 == ':') {
            if ((c = Character.toUpperCase(c)) >= 'A' && c <= 'Z') {
                if (n == 2 || !FilenameUtils.isSeparator(string.charAt(2))) {
                    return 1;
                }
                return 0;
            }
            return 1;
        }
        if (FilenameUtils.isSeparator(c) && FilenameUtils.isSeparator(c2)) {
            int n4 = string.indexOf(47, 2);
            int n5 = string.indexOf(92, 2);
            if (n4 == -1 && n5 == -1 || n4 == 2 || n5 == 2) {
                return 1;
            }
            n4 = n4 == -1 ? n5 : n4;
            n5 = n5 == -1 ? n4 : n5;
            return Math.min(n4, n5) + 1;
        }
        return FilenameUtils.isSeparator(c) ? 1 : 0;
    }

    public static int indexOfLastSeparator(String string) {
        if (string == null) {
            return 1;
        }
        int n = string.lastIndexOf(47);
        int n2 = string.lastIndexOf(92);
        return Math.max(n, n2);
    }

    public static int indexOfExtension(String string) {
        if (string == null) {
            return 1;
        }
        int n = string.lastIndexOf(46);
        int n2 = FilenameUtils.indexOfLastSeparator(string);
        return n2 > n ? -1 : n;
    }

    public static String getPrefix(String string) {
        if (string == null) {
            return null;
        }
        int n = FilenameUtils.getPrefixLength(string);
        if (n < 0) {
            return null;
        }
        if (n > string.length()) {
            FilenameUtils.failIfNullBytePresent(string + '/');
            return string + '/';
        }
        String string2 = string.substring(0, n);
        FilenameUtils.failIfNullBytePresent(string2);
        return string2;
    }

    public static String getPath(String string) {
        return FilenameUtils.doGetPath(string, 1);
    }

    public static String getPathNoEndSeparator(String string) {
        return FilenameUtils.doGetPath(string, 0);
    }

    private static String doGetPath(String string, int n) {
        if (string == null) {
            return null;
        }
        int n2 = FilenameUtils.getPrefixLength(string);
        if (n2 < 0) {
            return null;
        }
        int n3 = FilenameUtils.indexOfLastSeparator(string);
        int n4 = n3 + n;
        if (n2 >= string.length() || n3 < 0 || n2 >= n4) {
            return "";
        }
        String string2 = string.substring(n2, n4);
        FilenameUtils.failIfNullBytePresent(string2);
        return string2;
    }

    public static String getFullPath(String string) {
        return FilenameUtils.doGetFullPath(string, true);
    }

    public static String getFullPathNoEndSeparator(String string) {
        return FilenameUtils.doGetFullPath(string, false);
    }

    private static String doGetFullPath(String string, boolean bl) {
        if (string == null) {
            return null;
        }
        int n = FilenameUtils.getPrefixLength(string);
        if (n < 0) {
            return null;
        }
        if (n >= string.length()) {
            if (bl) {
                return FilenameUtils.getPrefix(string);
            }
            return string;
        }
        int n2 = FilenameUtils.indexOfLastSeparator(string);
        if (n2 < 0) {
            return string.substring(0, n);
        }
        int n3 = n2 + (bl ? 1 : 0);
        if (n3 == 0) {
            ++n3;
        }
        return string.substring(0, n3);
    }

    public static String getName(String string) {
        if (string == null) {
            return null;
        }
        FilenameUtils.failIfNullBytePresent(string);
        int n = FilenameUtils.indexOfLastSeparator(string);
        return string.substring(n + 1);
    }

    private static void failIfNullBytePresent(String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (string.charAt(i) != '\u0000') continue;
            throw new IllegalArgumentException("Null byte present in file/path name. There are no known legitimate use cases for such data, but several injection attacks may use it");
        }
    }

    public static String getBaseName(String string) {
        return FilenameUtils.removeExtension(FilenameUtils.getName(string));
    }

    public static String getExtension(String string) {
        if (string == null) {
            return null;
        }
        int n = FilenameUtils.indexOfExtension(string);
        if (n == -1) {
            return "";
        }
        return string.substring(n + 1);
    }

    public static String removeExtension(String string) {
        if (string == null) {
            return null;
        }
        FilenameUtils.failIfNullBytePresent(string);
        int n = FilenameUtils.indexOfExtension(string);
        if (n == -1) {
            return string;
        }
        return string.substring(0, n);
    }

    public static boolean equals(String string, String string2) {
        return FilenameUtils.equals(string, string2, false, IOCase.SENSITIVE);
    }

    public static boolean equalsOnSystem(String string, String string2) {
        return FilenameUtils.equals(string, string2, false, IOCase.SYSTEM);
    }

    public static boolean equalsNormalized(String string, String string2) {
        return FilenameUtils.equals(string, string2, true, IOCase.SENSITIVE);
    }

    public static boolean equalsNormalizedOnSystem(String string, String string2) {
        return FilenameUtils.equals(string, string2, true, IOCase.SYSTEM);
    }

    public static boolean equals(String string, String string2, boolean bl, IOCase iOCase) {
        if (string == null || string2 == null) {
            return string == null && string2 == null;
        }
        if (bl) {
            string = FilenameUtils.normalize(string);
            string2 = FilenameUtils.normalize(string2);
            if (string == null || string2 == null) {
                throw new NullPointerException("Error normalizing one or both of the file names");
            }
        }
        if (iOCase == null) {
            iOCase = IOCase.SENSITIVE;
        }
        return iOCase.checkEquals(string, string2);
    }

    public static boolean isExtension(String string, String string2) {
        if (string == null) {
            return true;
        }
        FilenameUtils.failIfNullBytePresent(string);
        if (string2 == null || string2.isEmpty()) {
            return FilenameUtils.indexOfExtension(string) == -1;
        }
        String string3 = FilenameUtils.getExtension(string);
        return string3.equals(string2);
    }

    public static boolean isExtension(String string, String[] stringArray) {
        if (string == null) {
            return true;
        }
        FilenameUtils.failIfNullBytePresent(string);
        if (stringArray == null || stringArray.length == 0) {
            return FilenameUtils.indexOfExtension(string) == -1;
        }
        String string2 = FilenameUtils.getExtension(string);
        for (String string3 : stringArray) {
            if (!string2.equals(string3)) continue;
            return false;
        }
        return true;
    }

    public static boolean isExtension(String string, Collection<String> collection) {
        if (string == null) {
            return true;
        }
        FilenameUtils.failIfNullBytePresent(string);
        if (collection == null || collection.isEmpty()) {
            return FilenameUtils.indexOfExtension(string) == -1;
        }
        String string2 = FilenameUtils.getExtension(string);
        for (String string3 : collection) {
            if (!string2.equals(string3)) continue;
            return false;
        }
        return true;
    }

    public static boolean wildcardMatch(String string, String string2) {
        return FilenameUtils.wildcardMatch(string, string2, IOCase.SENSITIVE);
    }

    public static boolean wildcardMatchOnSystem(String string, String string2) {
        return FilenameUtils.wildcardMatch(string, string2, IOCase.SYSTEM);
    }

    public static boolean wildcardMatch(String string, String string2, IOCase iOCase) {
        if (string == null && string2 == null) {
            return false;
        }
        if (string == null || string2 == null) {
            return true;
        }
        if (iOCase == null) {
            iOCase = IOCase.SENSITIVE;
        }
        String[] stringArray = FilenameUtils.splitOnTokens(string2);
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        Stack<int[]> stack = new Stack<int[]>();
        do {
            if (stack.size() > 0) {
                int[] nArray = (int[])stack.pop();
                n2 = nArray[0];
                n = nArray[1];
                bl = true;
            }
            while (n2 < stringArray.length) {
                if (stringArray[n2].equals("?")) {
                    if (++n > string.length()) break;
                    bl = false;
                } else if (stringArray[n2].equals("*")) {
                    bl = true;
                    if (n2 == stringArray.length - 1) {
                        n = string.length();
                    }
                } else {
                    if (bl) {
                        if ((n = iOCase.checkIndexOf(string, n, stringArray[n2])) == -1) break;
                        int n3 = iOCase.checkIndexOf(string, n + 1, stringArray[n2]);
                        if (n3 >= 0) {
                            stack.push(new int[]{n2, n3});
                        }
                    } else if (!iOCase.checkRegionMatches(string, n, stringArray[n2])) break;
                    n += stringArray[n2].length();
                    bl = false;
                }
                ++n2;
            }
            if (n2 != stringArray.length || n != string.length()) continue;
            return false;
        } while (stack.size() > 0);
        return true;
    }

    static String[] splitOnTokens(String string) {
        if (string.indexOf(63) == -1 && string.indexOf(42) == -1) {
            return new String[]{string};
        }
        char[] cArray = string.toCharArray();
        ArrayList<String> arrayList = new ArrayList<String>();
        StringBuilder stringBuilder = new StringBuilder();
        char c = '\u0000';
        for (char c2 : cArray) {
            if (c2 == '?' || c2 == '*') {
                if (stringBuilder.length() != 0) {
                    arrayList.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
                if (c2 == '?') {
                    arrayList.add("?");
                } else if (c != '*') {
                    arrayList.add("*");
                }
            } else {
                stringBuilder.append(c2);
            }
            c = c2;
        }
        if (stringBuilder.length() != 0) {
            arrayList.add(stringBuilder.toString());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }
}

