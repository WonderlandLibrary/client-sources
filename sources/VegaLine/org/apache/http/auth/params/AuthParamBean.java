/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.auth.params;

import org.apache.http.auth.params.AuthParams;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class AuthParamBean
extends HttpAbstractParamBean {
    public AuthParamBean(HttpParams params) {
        super(params);
    }

    public void setCredentialCharset(String charset) {
        AuthParams.setCredentialCharset(this.params, charset);
    }
}

