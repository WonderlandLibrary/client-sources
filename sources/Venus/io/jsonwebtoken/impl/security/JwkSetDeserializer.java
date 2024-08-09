/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.JsonObjectDeserializer;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.security.MalformedKeySetException;

public class JwkSetDeserializer
extends JsonObjectDeserializer {
    public JwkSetDeserializer(Deserializer<?> deserializer) {
        super(deserializer, "JWK Set");
    }

    @Override
    protected RuntimeException malformed(Throwable throwable) {
        String string = "Malformed JWK Set JSON: " + throwable.getMessage();
        throw new MalformedKeySetException(string, throwable);
    }
}

