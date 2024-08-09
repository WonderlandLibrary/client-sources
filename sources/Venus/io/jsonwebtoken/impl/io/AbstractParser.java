/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.CharSequenceReader;
import io.jsonwebtoken.impl.io.Streams;
import io.jsonwebtoken.io.Parser;
import io.jsonwebtoken.lang.Assert;
import java.io.InputStream;
import java.io.Reader;

public abstract class AbstractParser<T>
implements Parser<T> {
    @Override
    public final T parse(CharSequence charSequence) {
        Assert.hasText(charSequence, "CharSequence cannot be null or empty.");
        return this.parse(charSequence, 0, charSequence.length());
    }

    @Override
    public T parse(CharSequence charSequence, int n, int n2) {
        Assert.hasText(charSequence, "CharSequence cannot be null or empty.");
        CharSequenceReader charSequenceReader = new CharSequenceReader(charSequence, n, n2);
        return this.parse(charSequenceReader);
    }

    @Override
    public final T parse(InputStream inputStream) {
        Assert.notNull(inputStream, "InputStream cannot be null.");
        Reader reader = Streams.reader(inputStream);
        return this.parse(reader);
    }
}

