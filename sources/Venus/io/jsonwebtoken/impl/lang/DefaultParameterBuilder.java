/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Converters;
import io.jsonwebtoken.impl.lang.DefaultParameter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.ParameterBuilder;
import io.jsonwebtoken.impl.lang.RedactedValueConverter;
import io.jsonwebtoken.lang.Assert;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DefaultParameterBuilder<T>
implements ParameterBuilder<T> {
    private String id;
    private String name;
    private boolean secret;
    private final Class<T> type;
    private Converter<T, ?> converter;
    private Class<? extends Collection<T>> collectionType;

    public DefaultParameterBuilder(Class<T> clazz) {
        this.type = Assert.notNull(clazz, "Type cannot be null.");
    }

    @Override
    public ParameterBuilder<T> setId(String string) {
        this.id = string;
        return this;
    }

    @Override
    public ParameterBuilder<T> setName(String string) {
        this.name = string;
        return this;
    }

    @Override
    public ParameterBuilder<T> setSecret(boolean bl) {
        this.secret = bl;
        return this;
    }

    @Override
    public ParameterBuilder<List<T>> list() {
        Class<List> clazz = List.class;
        this.collectionType = clazz;
        return this;
    }

    @Override
    public ParameterBuilder<Set<T>> set() {
        Class<Set> clazz = Set.class;
        this.collectionType = clazz;
        return this;
    }

    @Override
    public ParameterBuilder<T> setConverter(Converter<T, ?> converter) {
        this.converter = converter;
        return this;
    }

    @Override
    public Parameter<T> build() {
        Assert.notNull(this.type, "Type must be set.");
        Converter<Object, Object> converter = this.converter;
        if (converter == null) {
            converter = Converters.forType(this.type);
        }
        if (this.collectionType != null) {
            Converter<Collection<T>, Object> converter2 = converter = List.class.isAssignableFrom(this.collectionType) ? Converters.forList(converter) : Converters.forSet(converter);
        }
        if (this.secret) {
            converter = new RedactedValueConverter<T>(converter);
        }
        return new DefaultParameter<T>(this.id, this.name, this.secret, this.type, this.collectionType, converter);
    }

    @Override
    public Object build() {
        return this.build();
    }
}

