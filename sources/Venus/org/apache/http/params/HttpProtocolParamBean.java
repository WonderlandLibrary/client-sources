/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.params;

import org.apache.http.HttpVersion;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

@Deprecated
public class HttpProtocolParamBean
extends HttpAbstractParamBean {
    public HttpProtocolParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setHttpElementCharset(String string) {
        HttpProtocolParams.setHttpElementCharset(this.params, string);
    }

    public void setContentCharset(String string) {
        HttpProtocolParams.setContentCharset(this.params, string);
    }

    public void setVersion(HttpVersion httpVersion) {
        HttpProtocolParams.setVersion(this.params, httpVersion);
    }

    public void setUserAgent(String string) {
        HttpProtocolParams.setUserAgent(this.params, string);
    }

    public void setUseExpectContinue(boolean bl) {
        HttpProtocolParams.setUseExpectContinue(this.params, bl);
    }
}

