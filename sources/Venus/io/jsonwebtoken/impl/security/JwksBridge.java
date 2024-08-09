/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.NamedSerializer;
import io.jsonwebtoken.impl.lang.Services;
import io.jsonwebtoken.io.Serializer;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import io.jsonwebtoken.security.Jwk;
import java.io.ByteArrayOutputStream;

public final class JwksBridge {
    private JwksBridge() {
    }

    public static String UNSAFE_JSON(Jwk<?> jwk) {
        Serializer serializer = Services.loadFirst(Serializer.class);
        Assert.stateNotNull(serializer, "Serializer lookup failed. Ensure JSON impl .jar is in the runtime classpath.");
        NamedSerializer namedSerializer = new NamedSerializer("JWK", serializer);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        namedSerializer.serialize(jwk, byteArrayOutputStream);
        return Strings.utf8(byteArrayOutputStream.toByteArray());
    }
}

