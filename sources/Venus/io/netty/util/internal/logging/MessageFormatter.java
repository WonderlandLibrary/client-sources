/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.util.internal.logging;

import io.netty.util.internal.logging.FormattingTuple;
import java.util.HashSet;
import java.util.Set;

final class MessageFormatter {
    private static final String DELIM_STR = "{}";
    private static final char ESCAPE_CHAR = '\\';

    static FormattingTuple format(String string, Object object) {
        return MessageFormatter.arrayFormat(string, new Object[]{object});
    }

    static FormattingTuple format(String string, Object object, Object object2) {
        return MessageFormatter.arrayFormat(string, new Object[]{object, object2});
    }

    static FormattingTuple arrayFormat(String string, Object[] objectArray) {
        Throwable throwable;
        if (objectArray == null || objectArray.length == 0) {
            return new FormattingTuple(string, null);
        }
        int n = objectArray.length - 1;
        Object object = objectArray[n];
        Throwable throwable2 = throwable = object instanceof Throwable ? (Throwable)object : null;
        if (string == null) {
            return new FormattingTuple(null, throwable);
        }
        int n2 = string.indexOf(DELIM_STR);
        if (n2 == -1) {
            return new FormattingTuple(string, throwable);
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() + 50);
        int n3 = 0;
        int n4 = 0;
        do {
            boolean bl;
            boolean bl2 = bl = n2 == 0 || string.charAt(n2 - 1) != '\\';
            if (bl) {
                stringBuilder.append(string, n3, n2);
            } else {
                stringBuilder.append(string, n3, n2 - 1);
                bl = n2 >= 2 && string.charAt(n2 - 2) == '\\';
            }
            n3 = n2 + 2;
            if (bl) {
                MessageFormatter.deeplyAppendParameter(stringBuilder, objectArray[n4], null);
                if (++n4 <= n) continue;
                break;
            }
            stringBuilder.append(DELIM_STR);
        } while ((n2 = string.indexOf(DELIM_STR, n3)) != -1);
        stringBuilder.append(string, n3, string.length());
        return new FormattingTuple(stringBuilder.toString(), n4 <= n ? throwable : null);
    }

    private static void deeplyAppendParameter(StringBuilder stringBuilder, Object object, Set<Object[]> set) {
        if (object == null) {
            stringBuilder.append("null");
            return;
        }
        Class<?> clazz = object.getClass();
        if (!clazz.isArray()) {
            if (Number.class.isAssignableFrom(clazz)) {
                if (clazz == Long.class) {
                    stringBuilder.append((Long)object);
                } else if (clazz == Integer.class || clazz == Short.class || clazz == Byte.class) {
                    stringBuilder.append(((Number)object).intValue());
                } else if (clazz == Double.class) {
                    stringBuilder.append((Double)object);
                } else if (clazz == Float.class) {
                    stringBuilder.append(((Float)object).floatValue());
                } else {
                    MessageFormatter.safeObjectAppend(stringBuilder, object);
                }
            } else {
                MessageFormatter.safeObjectAppend(stringBuilder, object);
            }
        } else {
            stringBuilder.append('[');
            if (clazz == boolean[].class) {
                MessageFormatter.booleanArrayAppend(stringBuilder, (boolean[])object);
            } else if (clazz == byte[].class) {
                MessageFormatter.byteArrayAppend(stringBuilder, (byte[])object);
            } else if (clazz == char[].class) {
                MessageFormatter.charArrayAppend(stringBuilder, (char[])object);
            } else if (clazz == short[].class) {
                MessageFormatter.shortArrayAppend(stringBuilder, (short[])object);
            } else if (clazz == int[].class) {
                MessageFormatter.intArrayAppend(stringBuilder, (int[])object);
            } else if (clazz == long[].class) {
                MessageFormatter.longArrayAppend(stringBuilder, (long[])object);
            } else if (clazz == float[].class) {
                MessageFormatter.floatArrayAppend(stringBuilder, (float[])object);
            } else if (clazz == double[].class) {
                MessageFormatter.doubleArrayAppend(stringBuilder, (double[])object);
            } else {
                MessageFormatter.objectArrayAppend(stringBuilder, (Object[])object, set);
            }
            stringBuilder.append(']');
        }
    }

    private static void safeObjectAppend(StringBuilder stringBuilder, Object object) {
        try {
            String string = object.toString();
            stringBuilder.append(string);
        } catch (Throwable throwable) {
            System.err.println("SLF4J: Failed toString() invocation on an object of type [" + object.getClass().getName() + ']');
            throwable.printStackTrace();
            stringBuilder.append("[FAILED toString()]");
        }
    }

    private static void objectArrayAppend(StringBuilder stringBuilder, Object[] objectArray, Set<Object[]> set) {
        if (objectArray.length == 0) {
            return;
        }
        if (set == null) {
            set = new HashSet<Object[]>(objectArray.length);
        }
        if (set.add(objectArray)) {
            MessageFormatter.deeplyAppendParameter(stringBuilder, objectArray[0], set);
            for (int i = 1; i < objectArray.length; ++i) {
                stringBuilder.append(", ");
                MessageFormatter.deeplyAppendParameter(stringBuilder, objectArray[i], set);
            }
            set.remove(objectArray);
        } else {
            stringBuilder.append("...");
        }
    }

    private static void booleanArrayAppend(StringBuilder stringBuilder, boolean[] blArray) {
        if (blArray.length == 0) {
            return;
        }
        stringBuilder.append(blArray[0]);
        for (int i = 1; i < blArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(blArray[i]);
        }
    }

    private static void byteArrayAppend(StringBuilder stringBuilder, byte[] byArray) {
        if (byArray.length == 0) {
            return;
        }
        stringBuilder.append(byArray[0]);
        for (int i = 1; i < byArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(byArray[i]);
        }
    }

    private static void charArrayAppend(StringBuilder stringBuilder, char[] cArray) {
        if (cArray.length == 0) {
            return;
        }
        stringBuilder.append(cArray[0]);
        for (int i = 1; i < cArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(cArray[i]);
        }
    }

    private static void shortArrayAppend(StringBuilder stringBuilder, short[] sArray) {
        if (sArray.length == 0) {
            return;
        }
        stringBuilder.append(sArray[0]);
        for (int i = 1; i < sArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(sArray[i]);
        }
    }

    private static void intArrayAppend(StringBuilder stringBuilder, int[] nArray) {
        if (nArray.length == 0) {
            return;
        }
        stringBuilder.append(nArray[0]);
        for (int i = 1; i < nArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(nArray[i]);
        }
    }

    private static void longArrayAppend(StringBuilder stringBuilder, long[] lArray) {
        if (lArray.length == 0) {
            return;
        }
        stringBuilder.append(lArray[0]);
        for (int i = 1; i < lArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(lArray[i]);
        }
    }

    private static void floatArrayAppend(StringBuilder stringBuilder, float[] fArray) {
        if (fArray.length == 0) {
            return;
        }
        stringBuilder.append(fArray[0]);
        for (int i = 1; i < fArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(fArray[i]);
        }
    }

    private static void doubleArrayAppend(StringBuilder stringBuilder, double[] dArray) {
        if (dArray.length == 0) {
            return;
        }
        stringBuilder.append(dArray[0]);
        for (int i = 1; i < dArray.length; ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(dArray[i]);
        }
    }

    private MessageFormatter() {
    }
}

