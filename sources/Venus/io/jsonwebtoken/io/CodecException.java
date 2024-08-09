/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.io.IOException;

public class CodecException
extends IOException {
    public CodecException(String string) {
        super(string);
    }

    public CodecException(String string, Throwable throwable) {
        super(string, throwable);
    }
}

