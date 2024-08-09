/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.AbstractParser;
import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.lang.Assert;
import java.io.Reader;
import java.util.Map;

public class ConvertingParser<T>
extends AbstractParser<T> {
    private final Function<Reader, Map<String, ?>> deserializer;
    private final Converter<T, Object> converter;

    public ConvertingParser(Function<Reader, Map<String, ?>> function, Converter<T, Object> converter) {
        this.deserializer = Assert.notNull(function, "Deserializer function cannot be null.");
        this.converter = Assert.notNull(converter, "Converter cannot be null.");
    }

    @Override
    public final T parse(Reader reader) {
        Assert.notNull(reader, "Reader cannot be null.");
        Map<String, ?> map = this.deserializer.apply(reader);
        return this.converter.applyFrom(map);
    }
}

