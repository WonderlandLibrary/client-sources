/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public interface ConnectionReuseStrategy {
    public boolean keepAlive(HttpResponse var1, HttpContext var2);
}

