/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client;

import java.io.IOException;
import org.apache.http.protocol.HttpContext;

public interface HttpRequestRetryHandler {
    public boolean retryRequest(IOException var1, int var2, HttpContext var3);
}

