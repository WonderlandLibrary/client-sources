/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import java.io.InputStream;

@Deprecated
public class DelegateStringDecoder
implements Decoder<InputStream, InputStream> {
    private final Decoder<CharSequence, byte[]> delegate;

    public DelegateStringDecoder(Decoder<CharSequence, byte[]> decoder) {
        this.delegate = Assert.notNull(decoder, "delegate cannot be null.");
    }

    @Override
    public InputStream decode(InputStream inputStream) throws DecodingException {
        try {
            byte[] byArray = Streams.bytes(inputStream, "Unable to Base64URL-decode input.");
            byArray = this.delegate.decode(Strings.utf8(byArray));
            return Streams.of(byArray);
        } catch (Throwable throwable) {
            String string = "Unable to Base64Url-decode InputStream: " + throwable.getMessage();
            throw new DecodingException(string, throwable);
        }
    }

    @Override
    public Object decode(Object object) throws DecodingException {
        return this.decode((InputStream)object);
    }
}

