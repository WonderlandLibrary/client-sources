/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import io.jsonwebtoken.Identifiable;
import java.io.InputStream;
import java.io.OutputStream;

public interface CompressionAlgorithm
extends Identifiable {
    public OutputStream compress(OutputStream var1);

    public InputStream decompress(InputStream var1);
}

