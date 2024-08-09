/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.ConvertingParser;
import io.jsonwebtoken.impl.security.AbstractJwkParserBuilder;
import io.jsonwebtoken.impl.security.JwkBuilderSupplier;
import io.jsonwebtoken.impl.security.JwkConverter;
import io.jsonwebtoken.impl.security.JwkDeserializer;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.security.Jwk;
import io.jsonwebtoken.security.JwkParserBuilder;

public class DefaultJwkParserBuilder
extends AbstractJwkParserBuilder<Jwk<?>, JwkParserBuilder>
implements JwkParserBuilder {
    @Override
    public Parser<Jwk<?>> doBuild() {
        JwkDeserializer jwkDeserializer = new JwkDeserializer(this.deserializer);
        JwkBuilderSupplier jwkBuilderSupplier = new JwkBuilderSupplier(this.provider, this.operationPolicy);
        JwkConverter jwkConverter = new JwkConverter(jwkBuilderSupplier);
        return new ConvertingParser(jwkDeserializer, jwkConverter);
    }
}

