/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.Identifiable;
import io.jsonwebtoken.impl.lang.Converter;

public interface Parameter<T>
extends Identifiable,
Converter<T, Object> {
    public String getName();

    public boolean supports(Object var1);

    public T cast(Object var1);

    public boolean isSecret();
}

