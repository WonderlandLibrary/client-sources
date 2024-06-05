/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.InvalidKeyException;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.VisibleForTesting;

final class KeyImpl
implements Key {
    static final String NAMESPACE_PATTERN = "[a-z0-9_\\-.]+";
    static final String VALUE_PATTERN = "[a-z0-9_\\-./]+";
    private static final IntPredicate NAMESPACE_PREDICATE = value -> {
        if (value == 95) return true;
        if (value == 45) return true;
        if (value >= 97) {
            if (value <= 122) return true;
        }
        if (value >= 48) {
            if (value <= 57) return true;
        }
        if (value == 46) return true;
        return false;
    };
    private static final IntPredicate VALUE_PREDICATE = value -> {
        if (value == 95) return true;
        if (value == 45) return true;
        if (value >= 97) {
            if (value <= 122) return true;
        }
        if (value >= 48) {
            if (value <= 57) return true;
        }
        if (value == 47) return true;
        if (value == 46) return true;
        return false;
    };
    private final String namespace;
    private final String value;

    KeyImpl(@NotNull String namespace, @NotNull String value) {
        if (!KeyImpl.namespaceValid(namespace)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9_.-] character in namespace of Key[%s]", KeyImpl.asString(namespace, value)));
        }
        if (!KeyImpl.valueValid(value)) {
            throw new InvalidKeyException(namespace, value, String.format("Non [a-z0-9/._-] character in value of Key[%s]", KeyImpl.asString(namespace, value)));
        }
        this.namespace = Objects.requireNonNull(namespace, "namespace");
        this.value = Objects.requireNonNull(value, "value");
    }

    @VisibleForTesting
    static boolean namespaceValid(@NotNull String namespace) {
        int i = 0;
        int length = namespace.length();
        while (i < length) {
            if (!NAMESPACE_PREDICATE.test(namespace.charAt(i))) {
                return false;
            }
            ++i;
        }
        return true;
    }

    @VisibleForTesting
    static boolean valueValid(@NotNull String value) {
        int i = 0;
        int length = value.length();
        while (i < length) {
            if (!VALUE_PREDICATE.test(value.charAt(i))) {
                return false;
            }
            ++i;
        }
        return true;
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
        if (!Objects.equals(this.namespace, that.namespace())) return false;
        if (!Objects.equals(this.value, that.value())) return false;
        return true;
    }

    public int hashCode() {
        int result = this.namespace.hashCode();
        return 31 * result + this.value.hashCode();
    }

    @Override
    public int compareTo(@NotNull Key that) {
        return Key.super.compareTo(that);
    }

    static int clampCompare(int value) {
        if (value < 0) {
            return -1;
        }
        if (value <= 0) return value;
        return 1;
    }
}

