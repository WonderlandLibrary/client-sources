/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.key;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.KeyedValue;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class KeyedValueImpl<T>
implements Examinable,
KeyedValue<T> {
    private final Key key;
    private final T value;

    KeyedValueImpl(Key key, T t) {
        this.key = key;
        this.value = t;
    }

    @Override
    @NotNull
    public Key key() {
        return this.key;
    }

    @Override
    @NotNull
    public T value() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        KeyedValueImpl keyedValueImpl = (KeyedValueImpl)object;
        return this.key.equals(keyedValueImpl.key) && this.value.equals(keyedValueImpl.value);
    }

    public int hashCode() {
        int n = this.key.hashCode();
        n = 31 * n + this.value.hashCode();
        return n;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("key", this.key), ExaminableProperty.of("value", this.value));
    }

    public String toString() {
        return this.examine(StringExaminer.simpleEscaping());
    }
}

