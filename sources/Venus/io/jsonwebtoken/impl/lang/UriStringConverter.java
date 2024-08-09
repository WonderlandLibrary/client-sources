/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;
import java.net.URI;

public class UriStringConverter
implements Converter<URI, CharSequence> {
    @Override
    public String applyTo(URI uRI) {
        Assert.notNull(uRI, "URI cannot be null.");
        return uRI.toString();
    }

    @Override
    public URI applyFrom(CharSequence charSequence) {
        Assert.hasText(charSequence, "URI string cannot be null or empty.");
        try {
            return URI.create(charSequence.toString());
        } catch (Exception exception) {
            String string = "Unable to convert String value '" + charSequence + "' to URI instance: " + exception.getMessage();
            throw new IllegalArgumentException(string, exception);
        }
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom((CharSequence)object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((URI)object);
    }
}

