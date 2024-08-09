/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.security;

import io.jsonwebtoken.impl.io.ConvertingParser;
import io.jsonwebtoken.impl.security.AbstractJwkParserBuilder;
import io.jsonwebtoken.impl.security.JwkBuilderSupplier;
import io.jsonwebtoken.impl.security.JwkSetConverter;
import io.jsonwebtoken.impl.security.JwkSetDeserializer;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.security.JwkSet;
import io.jsonwebtoken.security.JwkSetParserBuilder;

public class DefaultJwkSetParserBuilder
extends AbstractJwkParserBuilder<JwkSet, JwkSetParserBuilder>
implements JwkSetParserBuilder {
    private boolean ignoreUnsupported = true;

    @Override
    public JwkSetParserBuilder ignoreUnsupported(boolean bl) {
        this.ignoreUnsupported = bl;
        return this;
    }

    @Override
    public Parser<JwkSet> doBuild() {
        JwkSetDeserializer jwkSetDeserializer = new JwkSetDeserializer(this.deserializer);
        JwkBuilderSupplier jwkBuilderSupplier = new JwkBuilderSupplier(this.provider, this.operationPolicy);
        JwkSetConverter jwkSetConverter = new JwkSetConverter(jwkBuilderSupplier, this.ignoreUnsupported);
        return new ConvertingParser<JwkSet>(jwkSetDeserializer, jwkSetConverter);
    }
}

