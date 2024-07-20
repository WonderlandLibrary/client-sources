/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import org.apache.http.conn.routing.HttpRoute;

public interface BackoffManager {
    public void backOff(HttpRoute var1);

    public void probe(HttpRoute var1);
}

