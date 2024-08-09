/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.pool;

import java.io.IOException;

public interface ConnFactory<T, C> {
    public C create(T var1) throws IOException;
}

