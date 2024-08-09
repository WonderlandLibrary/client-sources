/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.FilteredInputStream;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.lang.Assert;
import java.io.InputStream;

public class DecodingInputStream
extends FilteredInputStream {
    private final String codecName;
    private final String name;

    public DecodingInputStream(InputStream inputStream, String string, String string2) {
        super(inputStream);
        this.codecName = Assert.hasText(string, "codecName cannot be null or empty.");
        this.name = Assert.hasText(string2, "Name cannot be null or empty.");
    }

    @Override
    protected void onThrowable(Throwable throwable) {
        String string = "Unable to " + this.codecName + "-decode " + this.name + ": " + throwable.getMessage();
        throw new DecodingException(string, throwable);
    }
}

