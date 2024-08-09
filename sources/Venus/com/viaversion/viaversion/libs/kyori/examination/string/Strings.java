/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.examination.string;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class Strings {
    private Strings() {
    }

    @NotNull
    static String withSuffix(String string, char c) {
        return string + c;
    }

    @NotNull
    static String wrapIn(String string, char c) {
        return c + string + c;
    }

    static int maxLength(Stream<String> stream) {
        return stream.mapToInt(String::length).max().orElse(0);
    }

    @NotNull
    static String repeat(@NotNull String string, int n) {
        if (n == 0) {
            return "";
        }
        if (n == 1) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(string.length() * n);
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    @NotNull
    static String padEnd(@NotNull String string, int n, char c) {
        return string.length() >= n ? string : String.format("%-" + n + "s", Character.valueOf(c));
    }
}

