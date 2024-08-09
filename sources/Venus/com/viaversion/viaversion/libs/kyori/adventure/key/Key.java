/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.KeyImpl;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.key.Namespaced;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Comparator;
import java.util.stream.Stream;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Key
extends Comparable<Key>,
Examinable,
Namespaced,
Keyed {
    public static final String MINECRAFT_NAMESPACE = "minecraft";
    public static final char DEFAULT_SEPARATOR = ':';

    @NotNull
    public static Key key(@NotNull @Pattern(value="([a-z0-9_\\-.]+:)?[a-z0-9_\\-./]+") String string) {
        return Key.key(string, ':');
    }

    @NotNull
    public static Key key(@NotNull String string, char c) {
        int n = string.indexOf(c);
        String string2 = n >= 1 ? string.substring(0, n) : MINECRAFT_NAMESPACE;
        String string3 = n >= 0 ? string.substring(n + 1) : string;
        return Key.key(string2, string3);
    }

    @NotNull
    public static Key key(@NotNull Namespaced namespaced, @NotNull @Pattern(value="[a-z0-9_\\-./]+") String string) {
        return Key.key(namespaced.namespace(), string);
    }

    @NotNull
    public static Key key(@NotNull @Pattern(value="[a-z0-9_\\-.]+") String string, @NotNull @Pattern(value="[a-z0-9_\\-./]+") String string2) {
        return new KeyImpl(string, string2);
    }

    @NotNull
    public static Comparator<? super Key> comparator() {
        return KeyImpl.COMPARATOR;
    }

    public static boolean parseable(@Nullable String string) {
        if (string == null) {
            return true;
        }
        int n = string.indexOf(58);
        String string2 = n >= 1 ? string.substring(0, n) : MINECRAFT_NAMESPACE;
        String string3 = n >= 0 ? string.substring(n + 1) : string;
        return Key.parseableNamespace(string2) && Key.parseableValue(string3);
    }

    public static boolean parseableNamespace(@NotNull String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (Key.allowedInNamespace(string.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean parseableValue(@NotNull String string) {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            if (Key.allowedInValue(string.charAt(i))) continue;
            return true;
        }
        return false;
    }

    public static boolean allowedInNamespace(char c) {
        return KeyImpl.allowedInNamespace(c);
    }

    public static boolean allowedInValue(char c) {
        return KeyImpl.allowedInValue(c);
    }

    @Override
    @NotNull
    public String namespace();

    @NotNull
    public String value();

    @NotNull
    public String asString();

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("namespace", this.namespace()), ExaminableProperty.of("value", this.value()));
    }

    @Override
    default public int compareTo(@NotNull Key key) {
        return Key.comparator().compare(this, key);
    }

    @Override
    @NotNull
    default public Key key() {
        return this;
    }

    @Override
    default public int compareTo(@NotNull Object object) {
        return this.compareTo((Key)object);
    }
}

