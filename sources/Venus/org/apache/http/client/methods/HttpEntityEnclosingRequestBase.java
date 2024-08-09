/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.methods;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.CloneUtils;

public abstract class HttpEntityEnclosingRequestBase
extends HttpRequestBase
implements HttpEntityEnclosingRequest {
    private HttpEntity entity;

    @Override
    public HttpEntity getEntity() {
        return this.entity;
    }

    @Override
    public void setEntity(HttpEntity httpEntity) {
        this.entity = httpEntity;
    }

    @Override
    public boolean expectContinue() {
        Header header = this.getFirstHeader("Expect");
        return header != null && "100-continue".equalsIgnoreCase(header.getValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase)super.clone();
        if (this.entity != null) {
            httpEntityEnclosingRequestBase.entity = CloneUtils.cloneObject(this.entity);
        }
        return httpEntityEnclosingRequestBase;
    }
}

