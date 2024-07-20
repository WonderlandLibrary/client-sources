/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.HttpResponse;

public interface ConnectionBackoffStrategy {
    public boolean shouldBackoff(Throwable var1);

    public boolean shouldBackoff(HttpResponse var1);
}

