/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.lang3.text;

import java.util.Formattable;
import java.util.Formatter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

public class FormattableUtils {
    private static final String SIMPLEST_FORMAT = "%s";

    public static String toString(Formattable formattable) {
        return String.format(SIMPLEST_FORMAT, formattable);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int n, int n2, int n3) {
        return FormattableUtils.append(charSequence, formatter, n, n2, n3, ' ', null);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int n, int n2, int n3, char c) {
        return FormattableUtils.append(charSequence, formatter, n, n2, n3, c, null);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int n, int n2, int n3, CharSequence charSequence2) {
        return FormattableUtils.append(charSequence, formatter, n, n2, n3, ' ', charSequence2);
    }

    public static Formatter append(CharSequence charSequence, Formatter formatter, int n, int n2, int n3, char c, CharSequence charSequence2) {
        Validate.isTrue(charSequence2 == null || n3 < 0 || charSequence2.length() <= n3, "Specified ellipsis '%1$s' exceeds precision of %2$s", charSequence2, n3);
        StringBuilder stringBuilder = new StringBuilder(charSequence);
        if (n3 >= 0 && n3 < charSequence.length()) {
            CharSequence charSequence3 = ObjectUtils.defaultIfNull(charSequence2, "");
            stringBuilder.replace(n3 - charSequence3.length(), charSequence.length(), charSequence3.toString());
        }
        boolean bl = (n & 1) == 1;
        for (int i = stringBuilder.length(); i < n2; ++i) {
            stringBuilder.insert(bl ? i : 0, c);
        }
        formatter.format(stringBuilder.toString(), new Object[0]);
        return formatter;
    }
}

