/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.lang.Builder;
import java.security.Provider;
import java.util.Map;

public interface ParserBuilder<T, B extends ParserBuilder<T, B>>
extends Builder<Parser<T>> {
    public B provider(Provider var1);

    public B json(Deserializer<Map<String, ?>> var1);
}

