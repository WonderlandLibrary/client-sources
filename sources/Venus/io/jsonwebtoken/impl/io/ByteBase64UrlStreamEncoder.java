/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.FilteredOutputStream;
import io.jsonwebtoken.io.Encoder;
import io.jsonwebtoken.io.EncodingException;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteBase64UrlStreamEncoder
implements Encoder<OutputStream, OutputStream> {
    private final Encoder<byte[], String> delegate;

    public ByteBase64UrlStreamEncoder(Encoder<byte[], String> encoder) {
        this.delegate = Assert.notNull(encoder, "delegate cannot be null.");
    }

    @Override
    public OutputStream encode(OutputStream outputStream) throws EncodingException {
        Assert.notNull(outputStream, "outputStream cannot be null.");
        return new TranslatingOutputStream(outputStream, this.delegate);
    }

    @Override
    public Object encode(Object object) throws EncodingException {
        return this.encode((OutputStream)object);
    }

    private static class TranslatingOutputStream
    extends FilteredOutputStream {
        private final OutputStream dst;
        private final Encoder<byte[], String> delegate;

        public TranslatingOutputStream(OutputStream outputStream, Encoder<byte[], String> encoder) {
            super(new ByteArrayOutputStream());
            this.dst = outputStream;
            this.delegate = encoder;
        }

        @Override
        public void close() throws IOException {
            byte[] byArray = ((ByteArrayOutputStream)this.out).toByteArray();
            String string = this.delegate.encode(byArray);
            this.dst.write(Strings.utf8(string));
            this.dst.flush();
            this.dst.close();
        }
    }
}

