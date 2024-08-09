/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.lang.Builder;
import java.util.List;
import java.util.Set;

public interface ParameterBuilder<T>
extends Builder<Parameter<T>> {
    public ParameterBuilder<T> setId(String var1);

    public ParameterBuilder<T> setName(String var1);

    public ParameterBuilder<T> setSecret(boolean var1);

    public ParameterBuilder<List<T>> list();

    public ParameterBuilder<Set<T>> set();

    public ParameterBuilder<T> setConverter(Converter<T, ?> var1);
}

