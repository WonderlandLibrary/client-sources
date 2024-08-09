/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.concurrent;

public interface FutureCallback<T> {
    public void completed(T var1);

    public void failed(Exception var1);

    public void cancelled();
}

