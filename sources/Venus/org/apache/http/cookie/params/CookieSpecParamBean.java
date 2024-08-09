/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.cookie.params;

import java.util.Collection;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class CookieSpecParamBean
extends HttpAbstractParamBean {
    public CookieSpecParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    public void setDatePatterns(Collection<String> collection) {
        this.params.setParameter("http.protocol.cookie-datepatterns", collection);
    }

    public void setSingleHeader(boolean bl) {
        this.params.setBooleanParameter("http.protocol.single-cookie-header", bl);
    }
}

