/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.util.Map;
import org.apache.logging.log4j.util.StringBuilderFormattable;

public final class StringBuilders {
    private StringBuilders() {
    }

    public static StringBuilder appendDqValue(StringBuilder sb, Object value) {
        return sb.append('\"').append(value).append('\"');
    }

    public static StringBuilder appendKeyDqValue(StringBuilder sb, Map.Entry<String, String> entry) {
        return StringBuilders.appendKeyDqValue(sb, entry.getKey(), entry.getValue());
    }

    public static StringBuilder appendKeyDqValue(StringBuilder sb, String key, Object value) {
        return sb.append(key).append('=').append('\"').append(value).append('\"');
    }

    public static void appendValue(StringBuilder stringBuilder, Object obj) {
        if (obj == null || obj instanceof String) {
            stringBuilder.append((String)obj);
        } else if (obj instanceof StringBuilderFormattable) {
            ((StringBuilderFormattable)obj).formatTo(stringBuilder);
        } else if (obj instanceof CharSequence) {
            stringBuilder.append((CharSequence)obj);
        } else if (obj instanceof Integer) {
            stringBuilder.append((Integer)obj);
        } else if (obj instanceof Long) {
            stringBuilder.append((Long)obj);
        } else if (obj instanceof Double) {
            stringBuilder.append((Double)obj);
        } else if (obj instanceof Boolean) {
            stringBuilder.append((Boolean)obj);
        } else if (obj instanceof Character) {
            stringBuilder.append(((Character)obj).charValue());
        } else if (obj instanceof Short) {
            stringBuilder.append(((Short)obj).shortValue());
        } else if (obj instanceof Float) {
            stringBuilder.append(((Float)obj).floatValue());
        } else {
            stringBuilder.append(obj);
        }
    }

    public static boolean equals(CharSequence left, int leftOffset, int leftLength, CharSequence right, int rightOffset, int rightLength) {
        if (leftLength == rightLength) {
            for (int i = 0; i < rightLength; ++i) {
                if (left.charAt(i + leftOffset) == right.charAt(i + rightOffset)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public static boolean equalsIgnoreCase(CharSequence left, int leftOffset, int leftLength, CharSequence right, int rightOffset, int rightLength) {
        if (leftLength == rightLength) {
            for (int i = 0; i < rightLength; ++i) {
                if (Character.toLowerCase(left.charAt(i + leftOffset)) == Character.toLowerCase(right.charAt(i + rightOffset))) continue;
                return false;
            }
            return true;
        }
        return false;
    }
}

