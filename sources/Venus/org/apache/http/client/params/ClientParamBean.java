/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.client.params;

import java.util.Collection;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.params.HttpAbstractParamBean;
import org.apache.http.params.HttpParams;

@Deprecated
public class ClientParamBean
extends HttpAbstractParamBean {
    public ClientParamBean(HttpParams httpParams) {
        super(httpParams);
    }

    @Deprecated
    public void setConnectionManagerFactoryClassName(String string) {
        this.params.setParameter("http.connection-manager.factory-class-name", string);
    }

    public void setHandleRedirects(boolean bl) {
        this.params.setBooleanParameter("http.protocol.handle-redirects", bl);
    }

    public void setRejectRelativeRedirect(boolean bl) {
        this.params.setBooleanParameter("http.protocol.reject-relative-redirect", bl);
    }

    public void setMaxRedirects(int n) {
        this.params.setIntParameter("http.protocol.max-redirects", n);
    }

    public void setAllowCircularRedirects(boolean bl) {
        this.params.setBooleanParameter("http.protocol.allow-circular-redirects", bl);
    }

    public void setHandleAuthentication(boolean bl) {
        this.params.setBooleanParameter("http.protocol.handle-authentication", bl);
    }

    public void setCookiePolicy(String string) {
        this.params.setParameter("http.protocol.cookie-policy", string);
    }

    public void setVirtualHost(HttpHost httpHost) {
        this.params.setParameter("http.virtual-host", httpHost);
    }

    public void setDefaultHeaders(Collection<Header> collection) {
        this.params.setParameter("http.default-headers", collection);
    }

    public void setDefaultHost(HttpHost httpHost) {
        this.params.setParameter("http.default-host", httpHost);
    }

    public void setConnectionManagerTimeout(long l) {
        this.params.setLongParameter("http.conn-manager.timeout", l);
    }
}

