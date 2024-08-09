/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.JsonObjectDeserializer;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.security.MalformedKeyException;

public class JwkDeserializer
extends JsonObjectDeserializer {
    public JwkDeserializer(Deserializer<?> deserializer) {
        super(deserializer, "JWK");
    }

    @Override
    protected RuntimeException malformed(Throwable throwable) {
        String string = "Malformed JWK JSON: " + throwable.getMessage();
        return new MalformedKeyException(string);
    }
}

