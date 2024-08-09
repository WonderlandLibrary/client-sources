/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class BooleanUtils {
    public static Boolean negate(Boolean bl) {
        if (bl == null) {
            return null;
        }
        return bl != false ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean isTrue(Boolean bl) {
        return Boolean.TRUE.equals(bl);
    }

    public static boolean isNotTrue(Boolean bl) {
        return !BooleanUtils.isTrue(bl);
    }

    public static boolean isFalse(Boolean bl) {
        return Boolean.FALSE.equals(bl);
    }

    public static boolean isNotFalse(Boolean bl) {
        return !BooleanUtils.isFalse(bl);
    }

    public static boolean toBoolean(Boolean bl) {
        return bl != null && bl != false;
    }

    public static boolean toBooleanDefaultIfNull(Boolean bl, boolean bl2) {
        if (bl == null) {
            return bl2;
        }
        return bl;
    }

    public static boolean toBoolean(int n) {
        return n != 0;
    }

    public static Boolean toBooleanObject(int n) {
        return n == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Boolean toBooleanObject(Integer n) {
        if (n == null) {
            return null;
        }
        return n == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static boolean toBoolean(int n, int n2, int n3) {
        if (n == n2) {
            return false;
        }
        if (n == n3) {
            return true;
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }

    public static boolean toBoolean(Integer n, Integer n2, Integer n3) {
        if (n == null) {
            if (n2 == null) {
                return false;
            }
            if (n3 == null) {
                return true;
            }
        } else {
            if (n.equals(n2)) {
                return false;
            }
            if (n.equals(n3)) {
                return true;
            }
        }
        throw new IllegalArgumentException("The Integer did not match either specified value");
    }

    public static Boolean toBooleanObject(int n, int n2, int n3, int n4) {
        if (n == n2) {
            return Boolean.TRUE;
        }
        if (n == n3) {
            return Boolean.FALSE;
        }
        if (n == n4) {
            return null;
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }

    public static Boolean toBooleanObject(Integer n, Integer n2, Integer n3, Integer n4) {
        if (n == null) {
            if (n2 == null) {
                return Boolean.TRUE;
            }
            if (n3 == null) {
                return Boolean.FALSE;
            }
            if (n4 == null) {
                return null;
            }
        } else {
            if (n.equals(n2)) {
                return Boolean.TRUE;
            }
            if (n.equals(n3)) {
                return Boolean.FALSE;
            }
            if (n.equals(n4)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The Integer did not match any specified value");
    }

    public static int toInteger(boolean bl) {
        return bl ? 1 : 0;
    }

    public static Integer toIntegerObject(boolean bl) {
        return bl ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
    }

    public static Integer toIntegerObject(Boolean bl) {
        if (bl == null) {
            return null;
        }
        return bl != false ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
    }

    public static int toInteger(boolean bl, int n, int n2) {
        return bl ? n : n2;
    }

    public static int toInteger(Boolean bl, int n, int n2, int n3) {
        if (bl == null) {
            return n3;
        }
        return bl != false ? n : n2;
    }

    public static Integer toIntegerObject(boolean bl, Integer n, Integer n2) {
        return bl ? n : n2;
    }

    public static Integer toIntegerObject(Boolean bl, Integer n, Integer n2, Integer n3) {
        if (bl == null) {
            return n3;
        }
        return bl != false ? n : n2;
    }

    public static Boolean toBooleanObject(String string) {
        if (string == "true") {
            return Boolean.TRUE;
        }
        if (string == null) {
            return null;
        }
        switch (string.length()) {
            case 1: {
                char c = string.charAt(0);
                if (c == 'y' || c == 'Y' || c == 't' || c == 'T') {
                    return Boolean.TRUE;
                }
                if (c != 'n' && c != 'N' && c != 'f' && c != 'F') break;
                return Boolean.FALSE;
            }
            case 2: {
                char c = string.charAt(0);
                char c2 = string.charAt(1);
                if (!(c != 'o' && c != 'O' || c2 != 'n' && c2 != 'N')) {
                    return Boolean.TRUE;
                }
                if (c != 'n' && c != 'N' || c2 != 'o' && c2 != 'O') break;
                return Boolean.FALSE;
            }
            case 3: {
                char c = string.charAt(0);
                char c3 = string.charAt(1);
                char c4 = string.charAt(2);
                if (!(c != 'y' && c != 'Y' || c3 != 'e' && c3 != 'E' || c4 != 's' && c4 != 'S')) {
                    return Boolean.TRUE;
                }
                if (c != 'o' && c != 'O' || c3 != 'f' && c3 != 'F' || c4 != 'f' && c4 != 'F') break;
                return Boolean.FALSE;
            }
            case 4: {
                char c = string.charAt(0);
                char c5 = string.charAt(1);
                char c6 = string.charAt(2);
                char c7 = string.charAt(3);
                if (c != 't' && c != 'T' || c5 != 'r' && c5 != 'R' || c6 != 'u' && c6 != 'U' || c7 != 'e' && c7 != 'E') break;
                return Boolean.TRUE;
            }
            case 5: {
                char c = string.charAt(0);
                char c8 = string.charAt(1);
                char c9 = string.charAt(2);
                char c10 = string.charAt(3);
                char c11 = string.charAt(4);
                if (c != 'f' && c != 'F' || c8 != 'a' && c8 != 'A' || c9 != 'l' && c9 != 'L' || c10 != 's' && c10 != 'S' || c11 != 'e' && c11 != 'E') break;
                return Boolean.FALSE;
            }
        }
        return null;
    }

    public static Boolean toBooleanObject(String string, String string2, String string3, String string4) {
        if (string == null) {
            if (string2 == null) {
                return Boolean.TRUE;
            }
            if (string3 == null) {
                return Boolean.FALSE;
            }
            if (string4 == null) {
                return null;
            }
        } else {
            if (string.equals(string2)) {
                return Boolean.TRUE;
            }
            if (string.equals(string3)) {
                return Boolean.FALSE;
            }
            if (string.equals(string4)) {
                return null;
            }
        }
        throw new IllegalArgumentException("The String did not match any specified value");
    }

    public static boolean toBoolean(String string) {
        return BooleanUtils.toBooleanObject(string) == Boolean.TRUE;
    }

    public static boolean toBoolean(String string, String string2, String string3) {
        if (string == string2) {
            return false;
        }
        if (string == string3) {
            return true;
        }
        if (string != null) {
            if (string.equals(string2)) {
                return false;
            }
            if (string.equals(string3)) {
                return true;
            }
        }
        throw new IllegalArgumentException("The String did not match either specified value");
    }

    public static String toStringTrueFalse(Boolean bl) {
        return BooleanUtils.toString(bl, "true", "false", null);
    }

    public static String toStringOnOff(Boolean bl) {
        return BooleanUtils.toString(bl, "on", "off", null);
    }

    public static String toStringYesNo(Boolean bl) {
        return BooleanUtils.toString(bl, "yes", "no", null);
    }

    public static String toString(Boolean bl, String string, String string2, String string3) {
        if (bl == null) {
            return string3;
        }
        return bl != false ? string : string2;
    }

    public static String toStringTrueFalse(boolean bl) {
        return BooleanUtils.toString(bl, "true", "false");
    }

    public static String toStringOnOff(boolean bl) {
        return BooleanUtils.toString(bl, "on", "off");
    }

    public static String toStringYesNo(boolean bl) {
        return BooleanUtils.toString(bl, "yes", "no");
    }

    public static String toString(boolean bl, String string, String string2) {
        return bl ? string : string2;
    }

    public static boolean and(boolean ... blArray) {
        if (blArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (blArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        for (boolean bl : blArray) {
            if (bl) continue;
            return true;
        }
        return false;
    }

    public static Boolean and(Boolean ... booleanArray) {
        if (booleanArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (booleanArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        try {
            boolean[] blArray = ArrayUtils.toPrimitive(booleanArray);
            return BooleanUtils.and(blArray) ? Boolean.TRUE : Boolean.FALSE;
        } catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("The array must not contain any null elements");
        }
    }

    public static boolean or(boolean ... blArray) {
        if (blArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (blArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        for (boolean bl : blArray) {
            if (!bl) continue;
            return false;
        }
        return true;
    }

    public static Boolean or(Boolean ... booleanArray) {
        if (booleanArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (booleanArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        try {
            boolean[] blArray = ArrayUtils.toPrimitive(booleanArray);
            return BooleanUtils.or(blArray) ? Boolean.TRUE : Boolean.FALSE;
        } catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("The array must not contain any null elements");
        }
    }

    public static boolean xor(boolean ... blArray) {
        if (blArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (blArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        boolean bl = false;
        for (boolean bl2 : blArray) {
            bl ^= bl2;
        }
        return bl;
    }

    public static Boolean xor(Boolean ... booleanArray) {
        if (booleanArray == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        if (booleanArray.length == 0) {
            throw new IllegalArgumentException("Array is empty");
        }
        try {
            boolean[] blArray = ArrayUtils.toPrimitive(booleanArray);
            return BooleanUtils.xor(blArray) ? Boolean.TRUE : Boolean.FALSE;
        } catch (NullPointerException nullPointerException) {
            throw new IllegalArgumentException("The array must not contain any null elements");
        }
    }

    public static int compare(boolean bl, boolean bl2) {
        if (bl == bl2) {
            return 1;
        }
        return bl ? 1 : -1;
    }
}

