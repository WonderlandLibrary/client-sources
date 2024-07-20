/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.KeyImpl;
import com.viaversion.viaversion.libs.kyori.adventure.key.KeyPattern;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.key.Namespaced;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.stream.Stream;
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
    public static Key key(@NotNull @KeyPattern String string) {
        return Key.key(string, ':');
    }

    @NotNull
    public static Key key(@NotNull String string, char character) {
        int index = string.indexOf(character);
        String namespace = index >= 1 ? string.substring(0, index) : MINECRAFT_NAMESPACE;
        String value = index >= 0 ? string.substring(index + 1) : string;
        return Key.key(namespace, value);
    }

    @NotNull
    public static Key key(@NotNull Namespaced namespaced, @NotNull @KeyPattern.Value String value) {
        return Key.key(namespaced.namespace(), value);
    }

    @NotNull
    public static Key key(@NotNull @KeyPattern.Namespace String namespace, @NotNull @KeyPattern.Value String value) {
        return new KeyImpl(namespace, value);
    }

    @NotNull
    public static Comparator<? super Key> comparator() {
        return KeyImpl.COMPARATOR;
    }

    public static boolean parseable(@Nullable String string) {
        if (string == null) {
            return false;
        }
        int index = string.indexOf(58);
        String namespace = index >= 1 ? string.substring(0, index) : MINECRAFT_NAMESPACE;
        String value = index >= 0 ? string.substring(index + 1) : string;
        return Key.parseableNamespace(namespace) && Key.parseableValue(value);
    }

    public static boolean parseableNamespace(@NotNull String namespace) {
        return !Key.checkNamespace(namespace).isPresent();
    }

    @NotNull
    public static OptionalInt checkNamespace(@NotNull String namespace) {
        int length = namespace.length();
        for (int i = 0; i < length; ++i) {
            if (Key.allowedInNamespace(namespace.charAt(i))) continue;
            return OptionalInt.of(i);
        }
        return OptionalInt.empty();
    }

    public static boolean parseableValue(@NotNull String value) {
        return !Key.checkValue(value).isPresent();
    }

    @NotNull
    public static OptionalInt checkValue(@NotNull String value) {
        int length = value.length();
        for (int i = 0; i < length; ++i) {
            if (Key.allowedInValue(value.charAt(i))) continue;
            return OptionalInt.of(i);
        }
        return OptionalInt.empty();
    }

    public static boolean allowedInNamespace(char character) {
        return KeyImpl.allowedInNamespace(character);
    }

    public static boolean allowedInValue(char character) {
        return KeyImpl.allowedInValue(character);
    }

    @Override
    @NotNull
    @KeyPattern.Namespace
    public String namespace();

    @NotNull
    @KeyPattern.Value
    public String value();

    @NotNull
    public String asString();

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("namespace", this.namespace()), ExaminableProperty.of("value", this.value()));
    }

    @Override
    default public int compareTo(@NotNull Key that) {
        return Key.comparator().compare(this, that);
    }

    @Override
    @NotNull
    default public Key key() {
        return this;
    }
}

