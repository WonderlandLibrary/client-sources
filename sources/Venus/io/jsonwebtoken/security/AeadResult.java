/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.security;

import java.io.OutputStream;

public interface AeadResult {
    public OutputStream getOutputStream();

    public AeadResult setTag(byte[] var1);

    public AeadResult setIv(byte[] var1);
}

