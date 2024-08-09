/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.message;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.util.StringBuilderFormattable;

final class ParameterFormatter {
    static final String RECURSION_PREFIX = "[...";
    static final String RECURSION_SUFFIX = "...]";
    static final String ERROR_PREFIX = "[!!!";
    static final String ERROR_SEPARATOR = "=>";
    static final String ERROR_MSG_SEPARATOR = ":";
    static final String ERROR_SUFFIX = "!!!]";
    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    private static final char ESCAPE_CHAR = '\\';
    private static ThreadLocal<SimpleDateFormat> threadLocalSimpleDateFormat = new ThreadLocal();

    private ParameterFormatter() {
    }

    static int countArgumentPlaceholders(String string) {
        if (string == null) {
            return 1;
        }
        int n = string.length();
        int n2 = 0;
        boolean bl = false;
        for (int i = 0; i < n - 1; ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
                bl = !bl;
                continue;
            }
            if (c == '{') {
                if (!bl && string.charAt(i + 1) == '}') {
                    ++n2;
                    ++i;
                }
                bl = false;
                continue;
            }
            bl = false;
        }
        return n2;
    }

    static int countArgumentPlaceholders2(String string, int[] nArray) {
        if (string == null) {
            return 1;
        }
        int n = string.length();
        int n2 = 0;
        boolean bl = false;
        for (int i = 0; i < n - 1; ++i) {
            char c = string.charAt(i);
            if (c == '\\') {
                bl = !bl;
                nArray[0] = -1;
                ++n2;
                continue;
            }
            if (c == '{') {
                if (!bl && string.charAt(i + 1) == '}') {
                    nArray[n2] = i++;
                    ++n2;
                }
                bl = false;
                continue;
            }
            bl = false;
        }
        return n2;
    }

    static int countArgumentPlaceholders3(char[] cArray, int n, int[] nArray) {
        int n2 = 0;
        boolean bl = false;
        for (int i = 0; i < n - 1; ++i) {
            char c = cArray[i];
            if (c == '\\') {
                bl = !bl;
                continue;
            }
            if (c == '{') {
                if (!bl && cArray[i + 1] == '}') {
                    nArray[n2] = i++;
                    ++n2;
                }
                bl = false;
                continue;
            }
            bl = false;
        }
        return n2;
    }

    static String format(String string, Object[] objectArray) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = objectArray == null ? 0 : objectArray.length;
        ParameterFormatter.formatMessage(stringBuilder, string, objectArray, n);
        return stringBuilder.toString();
    }

    static void formatMessage2(StringBuilder stringBuilder, String string, Object[] objectArray, int n, int[] nArray) {
        if (string == null || objectArray == null || n == 0) {
            stringBuilder.append(string);
            return;
        }
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string, n2, nArray[i]);
            n2 = nArray[i] + 2;
            ParameterFormatter.recursiveDeepToString(objectArray[i], stringBuilder, null);
        }
        stringBuilder.append(string, n2, string.length());
    }

    static void formatMessage3(StringBuilder stringBuilder, char[] cArray, int n, Object[] objectArray, int n2, int[] nArray) {
        if (cArray == null) {
            return;
        }
        if (objectArray == null || n2 == 0) {
            stringBuilder.append(cArray);
            return;
        }
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            stringBuilder.append(cArray, n3, nArray[i]);
            n3 = nArray[i] + 2;
            ParameterFormatter.recursiveDeepToString(objectArray[i], stringBuilder, null);
        }
        stringBuilder.append(cArray, n3, n);
    }

    static void formatMessage(StringBuilder stringBuilder, String string, Object[] objectArray, int n) {
        int n2;
        if (string == null || objectArray == null || n == 0) {
            stringBuilder.append(string);
            return;
        }
        int n3 = 0;
        int n4 = 0;
        int n5 = string.length();
        for (n2 = 0; n2 < n5 - 1; ++n2) {
            char c = string.charAt(n2);
            if (c == '\\') {
                ++n3;
                continue;
            }
            if (ParameterFormatter.isDelimPair(c, string, n2)) {
                ++n2;
                ParameterFormatter.writeEscapedEscapeChars(n3, stringBuilder);
                if (ParameterFormatter.isOdd(n3)) {
                    ParameterFormatter.writeDelimPair(stringBuilder);
                } else {
                    ParameterFormatter.writeArgOrDelimPair(objectArray, n, n4, stringBuilder);
                    ++n4;
                }
            } else {
                ParameterFormatter.handleLiteralChar(stringBuilder, n3, c);
            }
            n3 = 0;
        }
        ParameterFormatter.handleRemainingCharIfAny(string, n5, stringBuilder, n3, n2);
    }

    private static boolean isDelimPair(char c, String string, int n) {
        return c == '{' && string.charAt(n + 1) == '}';
    }

    private static void handleRemainingCharIfAny(String string, int n, StringBuilder stringBuilder, int n2, int n3) {
        if (n3 == n - 1) {
            char c = string.charAt(n3);
            ParameterFormatter.handleLastChar(stringBuilder, n2, c);
        }
    }

    private static void handleLastChar(StringBuilder stringBuilder, int n, char c) {
        if (c == '\\') {
            ParameterFormatter.writeUnescapedEscapeChars(n + 1, stringBuilder);
        } else {
            ParameterFormatter.handleLiteralChar(stringBuilder, n, c);
        }
    }

    private static void handleLiteralChar(StringBuilder stringBuilder, int n, char c) {
        ParameterFormatter.writeUnescapedEscapeChars(n, stringBuilder);
        stringBuilder.append(c);
    }

    private static void writeDelimPair(StringBuilder stringBuilder) {
        stringBuilder.append('{');
        stringBuilder.append('}');
    }

    private static boolean isOdd(int n) {
        return (n & 1) == 1;
    }

    private static void writeEscapedEscapeChars(int n, StringBuilder stringBuilder) {
        int n2 = n >> 1;
        ParameterFormatter.writeUnescapedEscapeChars(n2, stringBuilder);
    }

    private static void writeUnescapedEscapeChars(int n, StringBuilder stringBuilder) {
        while (n > 0) {
            stringBuilder.append('\\');
            --n;
        }
    }

    private static void writeArgOrDelimPair(Object[] objectArray, int n, int n2, StringBuilder stringBuilder) {
        if (n2 < n) {
            ParameterFormatter.recursiveDeepToString(objectArray[n2], stringBuilder, null);
        } else {
            ParameterFormatter.writeDelimPair(stringBuilder);
        }
    }

    static String deepToString(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof String) {
            return (String)object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        HashSet<String> hashSet = new HashSet<String>();
        ParameterFormatter.recursiveDeepToString(object, stringBuilder, hashSet);
        return stringBuilder.toString();
    }

    private static void recursiveDeepToString(Object object, StringBuilder stringBuilder, Set<String> set) {
        if (ParameterFormatter.appendSpecialTypes(object, stringBuilder)) {
            return;
        }
        if (ParameterFormatter.isMaybeRecursive(object)) {
            ParameterFormatter.appendPotentiallyRecursiveValue(object, stringBuilder, set);
        } else {
            ParameterFormatter.tryObjectToString(object, stringBuilder);
        }
    }

    private static boolean appendSpecialTypes(Object object, StringBuilder stringBuilder) {
        if (object == null || object instanceof String) {
            stringBuilder.append((String)object);
            return false;
        }
        if (object instanceof CharSequence) {
            stringBuilder.append((CharSequence)object);
            return false;
        }
        if (object instanceof StringBuilderFormattable) {
            ((StringBuilderFormattable)object).formatTo(stringBuilder);
            return false;
        }
        if (object instanceof Integer) {
            stringBuilder.append((Integer)object);
            return false;
        }
        if (object instanceof Long) {
            stringBuilder.append((Long)object);
            return false;
        }
        if (object instanceof Double) {
            stringBuilder.append((Double)object);
            return false;
        }
        if (object instanceof Boolean) {
            stringBuilder.append((Boolean)object);
            return false;
        }
        if (object instanceof Character) {
            stringBuilder.append(((Character)object).charValue());
            return false;
        }
        if (object instanceof Short) {
            stringBuilder.append(((Short)object).shortValue());
            return false;
        }
        if (object instanceof Float) {
            stringBuilder.append(((Float)object).floatValue());
            return false;
        }
        return ParameterFormatter.appendDate(object, stringBuilder);
    }

    private static boolean appendDate(Object object, StringBuilder stringBuilder) {
        if (!(object instanceof Date)) {
            return true;
        }
        Date date = (Date)object;
        SimpleDateFormat simpleDateFormat = ParameterFormatter.getSimpleDateFormat();
        stringBuilder.append(simpleDateFormat.format(date));
        return false;
    }

    private static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = threadLocalSimpleDateFormat.get();
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            threadLocalSimpleDateFormat.set(simpleDateFormat);
        }
        return simpleDateFormat;
    }

    private static boolean isMaybeRecursive(Object object) {
        return object.getClass().isArray() || object instanceof Map || object instanceof Collection;
    }

    private static void appendPotentiallyRecursiveValue(Object object, StringBuilder stringBuilder, Set<String> set) {
        Class<?> clazz = object.getClass();
        if (clazz.isArray()) {
            ParameterFormatter.appendArray(object, stringBuilder, set, clazz);
        } else if (object instanceof Map) {
            ParameterFormatter.appendMap(object, stringBuilder, set);
        } else if (object instanceof Collection) {
            ParameterFormatter.appendCollection(object, stringBuilder, set);
        }
    }

    private static void appendArray(Object object, StringBuilder stringBuilder, Set<String> set, Class<?> clazz) {
        if (clazz == byte[].class) {
            stringBuilder.append(Arrays.toString((byte[])object));
        } else if (clazz == short[].class) {
            stringBuilder.append(Arrays.toString((short[])object));
        } else if (clazz == int[].class) {
            stringBuilder.append(Arrays.toString((int[])object));
        } else if (clazz == long[].class) {
            stringBuilder.append(Arrays.toString((long[])object));
        } else if (clazz == float[].class) {
            stringBuilder.append(Arrays.toString((float[])object));
        } else if (clazz == double[].class) {
            stringBuilder.append(Arrays.toString((double[])object));
        } else if (clazz == boolean[].class) {
            stringBuilder.append(Arrays.toString((boolean[])object));
        } else if (clazz == char[].class) {
            stringBuilder.append(Arrays.toString((char[])object));
        } else {
            String string;
            if (set == null) {
                set = new HashSet<String>();
            }
            if (set.contains(string = ParameterFormatter.identityToString(object))) {
                stringBuilder.append(RECURSION_PREFIX).append(string).append(RECURSION_SUFFIX);
            } else {
                set.add(string);
                Object[] objectArray = (Object[])object;
                stringBuilder.append('[');
                boolean bl = true;
                for (Object object2 : objectArray) {
                    if (bl) {
                        bl = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    ParameterFormatter.recursiveDeepToString(object2, stringBuilder, new HashSet<String>(set));
                }
                stringBuilder.append(']');
            }
        }
    }

    private static void appendMap(Object object, StringBuilder stringBuilder, Set<String> set) {
        String string;
        if (set == null) {
            set = new HashSet<String>();
        }
        if (set.contains(string = ParameterFormatter.identityToString(object))) {
            stringBuilder.append(RECURSION_PREFIX).append(string).append(RECURSION_SUFFIX);
        } else {
            set.add(string);
            Map map = (Map)object;
            stringBuilder.append('{');
            boolean bl = true;
            Iterator iterator2 = map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry entry;
                Map.Entry entry2 = entry = iterator2.next();
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(", ");
                }
                Object k = entry2.getKey();
                Object v = entry2.getValue();
                ParameterFormatter.recursiveDeepToString(k, stringBuilder, new HashSet<String>(set));
                stringBuilder.append('=');
                ParameterFormatter.recursiveDeepToString(v, stringBuilder, new HashSet<String>(set));
            }
            stringBuilder.append('}');
        }
    }

    private static void appendCollection(Object object, StringBuilder stringBuilder, Set<String> set) {
        String string;
        if (set == null) {
            set = new HashSet<String>();
        }
        if (set.contains(string = ParameterFormatter.identityToString(object))) {
            stringBuilder.append(RECURSION_PREFIX).append(string).append(RECURSION_SUFFIX);
        } else {
            set.add(string);
            Collection collection = (Collection)object;
            stringBuilder.append('[');
            boolean bl = true;
            for (Object e : collection) {
                if (bl) {
                    bl = false;
                } else {
                    stringBuilder.append(", ");
                }
                ParameterFormatter.recursiveDeepToString(e, stringBuilder, new HashSet<String>(set));
            }
            stringBuilder.append(']');
        }
    }

    private static void tryObjectToString(Object object, StringBuilder stringBuilder) {
        try {
            stringBuilder.append(object.toString());
        } catch (Throwable throwable) {
            ParameterFormatter.handleErrorInObjectToString(object, stringBuilder, throwable);
        }
    }

    private static void handleErrorInObjectToString(Object object, StringBuilder stringBuilder, Throwable throwable) {
        stringBuilder.append(ERROR_PREFIX);
        stringBuilder.append(ParameterFormatter.identityToString(object));
        stringBuilder.append(ERROR_SEPARATOR);
        String string = throwable.getMessage();
        String string2 = throwable.getClass().getName();
        stringBuilder.append(string2);
        if (!string2.equals(string)) {
            stringBuilder.append(ERROR_MSG_SEPARATOR);
            stringBuilder.append(string);
        }
        stringBuilder.append(ERROR_SUFFIX);
    }

    static String identityToString(Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(object));
    }
}

