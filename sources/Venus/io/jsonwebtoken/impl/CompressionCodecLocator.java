/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.CompressionCodecResolver;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Locator;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.CompressionAlgorithm;
import io.jsonwebtoken.lang.Assert;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class CompressionCodecLocator
implements Function<Header, CompressionAlgorithm>,
Locator<CompressionAlgorithm> {
    private final CompressionCodecResolver resolver;

    public CompressionCodecLocator(CompressionCodecResolver compressionCodecResolver) {
        this.resolver = Assert.notNull(compressionCodecResolver, "CompressionCodecResolver cannot be null.");
    }

    @Override
    public CompressionAlgorithm apply(Header header) {
        return this.locate(header);
    }

    @Override
    public CompressionAlgorithm locate(Header header) {
        return this.resolver.resolveCompressionCodec(header);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((Header)object);
    }

    @Override
    public Object locate(Header header) {
        return this.locate(header);
    }
}

