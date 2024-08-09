/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package kotlin.text;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 9, 0}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0010\f\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0000\u00a8\u0006\u0003"}, d2={"titlecaseImpl", "", "", "kotlin-stdlib"})
public final class _OneToManyTitlecaseMappingsKt {
    @NotNull
    public static final String titlecaseImpl(char c) {
        String string = String.valueOf(c);
        Intrinsics.checkNotNull(string, "null cannot be cast to non-null type java.lang.String");
        String string2 = string.toUpperCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        String string3 = string2;
        if (string3.length() > 1) {
            String string4;
            if (c == '\u0149') {
                string4 = string3;
            } else {
                char c2 = string3.charAt(0);
                String string5 = string3;
                int n = 1;
                Intrinsics.checkNotNull(string5, "null cannot be cast to non-null type java.lang.String");
                String string6 = string5.substring(n);
                Intrinsics.checkNotNullExpressionValue(string6, "this as java.lang.String).substring(startIndex)");
                string5 = string6;
                Intrinsics.checkNotNull(string5, "null cannot be cast to non-null type java.lang.String");
                String string7 = string5.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                string5 = string7;
                string4 = c2 + string5;
            }
            return string4;
        }
        return String.valueOf(Character.toTitleCase(c));
    }
}

