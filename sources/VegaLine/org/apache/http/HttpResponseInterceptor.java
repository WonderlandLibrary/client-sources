/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.protocol.HttpContext;

public interface HttpResponseInterceptor {
    public void process(HttpResponse var1, HttpContext var2) throws HttpException, IOException;
}

