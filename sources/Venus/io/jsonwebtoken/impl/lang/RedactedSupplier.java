/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Supplier;

public class RedactedSupplier<T>
implements Supplier<T> {
    public static final String REDACTED_VALUE = "<redacted>";
    private final T value;

    public RedactedSupplier(T t) {
        this.value = Assert.notNull(t, "value cannot be null.");
    }

    @Override
    public T get() {
        return this.value;
    }

    public int hashCode() {
        return Objects.nullSafeHashCode(this.value);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof RedactedSupplier) {
            object = ((RedactedSupplier)object).value;
        }
        return Objects.nullSafeEquals(this.value, object);
    }

    public String toString() {
        return REDACTED_VALUE;
    }
}

