/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.io;

import java.io.InputStream;
import java.io.Reader;

public interface Parser<T> {
    public T parse(CharSequence var1);

    public T parse(CharSequence var1, int var2, int var3);

    public T parse(Reader var1);

    public T parse(InputStream var1);
}

