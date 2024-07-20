/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.intellij.lang.annotations.RegExp
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.InvalidKeyException;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.Stream;
import org.intellij.lang.annotations.RegExp;
import org.jetbrains.annotations.NotNull;

final class KeyImpl
implements Key {
    static final Comparator<? super Key> COMPARATOR = Comparator.comparing(Key::value).thenComparing(Key::namespace);
    @RegExp
    static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
    @RegExp
    static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
    private final String namespace;
    private final String value;

    KeyImpl(@NotNull String namespace, @NotNull String value) {
        KeyImpl.checkError("namespace", namespace, value, Key.checkNamespace(namespace));
        KeyImpl.checkError("value", namespace, value, Key.checkValue(value));
        this.namespace = Objects.requireNonNull(namespace, "namespace");
        this.value = Objects.requireNonNull(value, "value");
    }

    private static void checkError(String name, String namespace, String value, OptionalInt index) {
        if (index.isPresent()) {
            int indexValue = index.getAsInt();
            char character = value.charAt(indexValue);
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in %s of Key[%s] at index %d ('%s', bytes: %s)", name, KeyImpl.asString(namespace, value), indexValue, Character.valueOf(character), Arrays.toString(String.valueOf(character).getBytes(StandardCharsets.UTF_8))));
        }
    }

    static boolean allowedInNamespace(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }

    static boolean allowedInValue(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.' || character == '/';
    }

    @Override
    @NotNull
    public String namespace() {
        return this.namespace;
    }

    @Override
    @NotNull
    public String value() {
        return this.value;
    }

    @Override
    @NotNull
    public String asString() {
        return KeyImpl.asString(this.namespace, this.value);
    }

    @NotNull
    private static String asString(@NotNull String namespace, @NotNull String value) {
        return namespace + ':' + value;
    }

    @NotNull
    public String toString() {
        return this.asString();
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("namespace", this.namespace), ExaminableProperty.of("value", this.value));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Key)) {
            return false;
        }
        Key that = (Key)other;
        return Objects.equals(this.namespace, that.namespace()) && Objects.equals(this.value, that.value());
    }

    public int hashCode() {
        int result = this.namespace.hashCode();
        result = 31 * result + this.value.hashCode();
        return result;
    }

    @Override
    public int compareTo(@NotNull Key that) {
        return Key.super.compareTo(that);
    }
}

