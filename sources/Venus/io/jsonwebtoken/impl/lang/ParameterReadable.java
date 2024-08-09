/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Parameter;

public interface ParameterReadable {
    public <T> T get(Parameter<T> var1);
}

