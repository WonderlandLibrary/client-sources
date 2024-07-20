/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpContext;

public interface HttpRequestInterceptor {
    public void process(HttpRequest var1, HttpContext var2) throws HttpException, IOException;
}

