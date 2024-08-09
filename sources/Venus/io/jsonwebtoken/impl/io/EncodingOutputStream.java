/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.impl.io.FilteredOutputStream;
import io.jsonwebtoken.io.EncodingException;
import io.jsonwebtoken.lang.Assert;
import java.io.OutputStream;

public class EncodingOutputStream
extends FilteredOutputStream {
    private final String codecName;
    private final String name;

    public EncodingOutputStream(OutputStream outputStream, String string, String string2) {
        super(outputStream);
        this.codecName = Assert.hasText(string, "codecName cannot be null or empty.");
        this.name = Assert.hasText(string2, "name cannot be null or empty.");
    }

    @Override
    protected void onThrowable(Throwable throwable) {
        String string = "Unable to " + this.codecName + "-encode " + this.name + ": " + throwable.getMessage();
        throw new EncodingException(string, throwable);
    }
}

