/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.InvalidKeyException;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class KeyImpl
implements Key {
    static final Comparator<? super Key> COMPARATOR = Comparator.comparing(Key::value).thenComparing(Key::namespace);
    static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
    static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
    private final String namespace;
    private final String value;

    KeyImpl(@NotNull String string, @NotNull String string2) {
        if (!Key.parseableNamespace(string)) {
            throw new InvalidKeyException(string, string2, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", KeyImpl.asString(string, string2)));
        }
        if (!Key.parseableValue(string2)) {
            throw new InvalidKeyException(string, string2, String.format("Non [a-z0-9/._-] character in value of Key[%s]", KeyImpl.asString(string, string2)));
        }
        this.namespace = Objects.requireNonNull(string, "namespace");
        this.value = Objects.requireNonNull(string2, "value");
    }

    static boolean allowedInNamespace(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.';
    }

    static boolean allowedInValue(char c) {
        return c == '_' || c == '-' || c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '.' || c == '/';
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
    private static String asString(@NotNull String string, @NotNull String string2) {
        return string + ':' + string2;
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

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof Key)) {
            return true;
        }
        Key key = (Key)object;
        return Objects.equals(this.namespace, key.namespace()) && Objects.equals(this.value, key.value());
    }

    public int hashCode() {
        int n = this.namespace.hashCode();
        n = 31 * n + this.value.hashCode();
        return n;
    }

    @Override
    public int compareTo(@NotNull Key key) {
        return Key.super.compareTo(key);
    }

    @Override
    public int compareTo(@NotNull Object object) {
        return this.compareTo((Key)object);
    }
}

