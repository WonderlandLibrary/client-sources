/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.examination.string;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class Strings {
    private Strings() {
    }

    @NotNull
    static String withSuffix(String string, char suffix) {
        return string + suffix;
    }

    @NotNull
    static String wrapIn(String string, char wrap) {
        return wrap + string + wrap;
    }

    static int maxLength(Stream<String> strings) {
        return strings.mapToInt(String::length).max().orElse(0);
    }

    @NotNull
    static String repeat(@NotNull String string, int count) {
        if (count == 0) {
            return "";
        }
        if (count == 1) {
            return string;
        }
        StringBuilder sb = new StringBuilder(string.length() * count);
        int i = 0;
        while (i < count) {
            sb.append(string);
            ++i;
        }
        return sb.toString();
    }

    @NotNull
    static String padEnd(@NotNull String string, int minLength, char padding) {
        String string2;
        if (string.length() >= minLength) {
            string2 = string;
            return string2;
        }
        string2 = String.format("%-" + minLength + "s", Character.valueOf(padding));
        return string2;
    }
}

