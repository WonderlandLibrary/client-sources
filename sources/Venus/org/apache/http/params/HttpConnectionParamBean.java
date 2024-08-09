/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

@Deprecated
public class HttpConnectionParamBean
extends HttpAbstractParamBean {
    public HttpConnectionParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setSoTimeout(int n) {
        HttpConnectionParams.setSoTimeout(this.params, n);
    }

    public void setTcpNoDelay(boolean bl) {
        HttpConnectionParams.setTcpNoDelay(this.params, bl);
    }

    public void setSocketBufferSize(int n) {
        HttpConnectionParams.setSocketBufferSize(this.params, n);
    }

    public void setLinger(int n) {
        HttpConnectionParams.setLinger(this.params, n);
    }

    public void setConnectionTimeout(int n) {
        HttpConnectionParams.setConnectionTimeout(this.params, n);
    }

    public void setStaleCheckingEnabled(boolean bl) {
        HttpConnectionParams.setStaleCheckingEnabled(this.params, bl);
    }
}

