/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.protocol;

import java.util.List;
import org.apache.http.HttpResponseInterceptor;

@Deprecated
public interface HttpResponseInterceptorList {
    public void addResponseInterceptor(HttpResponseInterceptor var1);

    public void addResponseInterceptor(HttpResponseInterceptor var1, int var2);

    public int getResponseInterceptorCount();

    public HttpResponseInterceptor getResponseInterceptor(int var1);

    public void clearResponseInterceptors();

    public void removeResponseInterceptorByClass(Class<? extends HttpResponseInterceptor> var1);

    public void setInterceptors(List<?> var1);
}

