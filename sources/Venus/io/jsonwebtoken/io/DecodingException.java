/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.CodecException;

public class DecodingException
extends CodecException {
    public DecodingException(String string) {
        super(string);
    }

    public DecodingException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

