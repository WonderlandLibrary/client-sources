/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.lang.Services;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.io.ParserBuilder;
import java.security.Provider;
import java.util.Map;

public abstract class AbstractParserBuilder<T, B extends ParserBuilder<T, B>>
implements ParserBuilder<T, B> {
    protected Provider provider;
    protected Deserializer<Map<String, ?>> deserializer;

    protected final B self() {
        return (B)this;
    }

    @Override
    public B provider(Provider provider) {
        this.provider = provider;
        return this.self();
    }

    @Override
    public B json(Deserializer<Map<String, ?>> deserializer) {
        this.deserializer = deserializer;
        return this.self();
    }

    @Override
    public final Parser<T> build() {
        if (this.deserializer == null) {
            this.deserializer = Services.loadFirst(Deserializer.class);
        }
        return this.doBuild();
    }

    protected abstract Parser<T> doBuild();

    @Override
    public Object build() {
        return this.build();
    }
}

