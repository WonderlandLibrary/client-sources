/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.util.Map;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public final class StringBuilders {
    private StringBuilders() {
    }

    public static StringBuilder appendDqValue(StringBuilder stringBuilder, Object object) {
        return stringBuilder.append('\"').append(object).append('\"');
    }

    public static StringBuilder appendKeyDqValue(StringBuilder stringBuilder, Map.Entry<String, String> entry) {
        return StringBuilders.appendKeyDqValue(stringBuilder, entry.getKey(), entry.getValue());
    }

    public static StringBuilder appendKeyDqValue(StringBuilder stringBuilder, String string, Object object) {
        return stringBuilder.append(string).append('=').append('\"').append(object).append('\"');
    }

    public static void appendValue(StringBuilder stringBuilder, Object object) {
        if (object == null || object instanceof String) {
            stringBuilder.append((String)object);
        } else if (object instanceof StringBuilderFormattable) {
            ((StringBuilderFormattable)object).formatTo(stringBuilder);
        } else if (object instanceof CharSequence) {
            stringBuilder.append((CharSequence)object);
        } else if (object instanceof Integer) {
            stringBuilder.append((Integer)object);
        } else if (object instanceof Long) {
            stringBuilder.append((Long)object);
        } else if (object instanceof Double) {
            stringBuilder.append((Double)object);
        } else if (object instanceof Boolean) {
            stringBuilder.append((Boolean)object);
        } else if (object instanceof Character) {
            stringBuilder.append(((Character)object).charValue());
        } else if (object instanceof Short) {
            stringBuilder.append(((Short)object).shortValue());
        } else if (object instanceof Float) {
            stringBuilder.append(((Float)object).floatValue());
        } else {
            stringBuilder.append(object);
        }
    }

    public static boolean equals(CharSequence charSequence, int n, int n2, CharSequence charSequence2, int n3, int n4) {
        if (n2 == n4) {
            for (int i = 0; i < n4; ++i) {
                if (charSequence.charAt(i + n) == charSequence2.charAt(i + n3)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public static boolean equalsIgnoreCase(CharSequence charSequence, int n, int n2, CharSequence charSequence2, int n3, int n4) {
        if (n2 == n4) {
            for (int i = 0; i < n4; ++i) {
                if (Character.toLowerCase(charSequence.charAt(i + n)) == Character.toLowerCase(charSequence2.charAt(i + n3))) continue;
                return true;
            }
            return false;
        }
        return true;
    }
}

