/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.auth.AuthScheme;
import org.apache.http.client.AuthCache;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.SAFE)
public class BasicAuthCache
implements AuthCache {
    private final Log log = LogFactory.getLog(this.getClass());
    private final Map<HttpHost, byte[]> map = new ConcurrentHashMap<HttpHost, byte[]>();
    private final SchemePortResolver schemePortResolver;

    public BasicAuthCache(SchemePortResolver schemePortResolver) {
        this.schemePortResolver = schemePortResolver != null ? schemePortResolver : DefaultSchemePortResolver.INSTANCE;
    }

    public BasicAuthCache() {
        this(null);
    }

    protected HttpHost getKey(HttpHost httpHost) {
        if (httpHost.getPort() <= 0) {
            int n;
            try {
                n = this.schemePortResolver.resolve(httpHost);
            } catch (UnsupportedSchemeException unsupportedSchemeException) {
                return httpHost;
            }
            return new HttpHost(httpHost.getHostName(), n, httpHost.getSchemeName());
        }
        return httpHost;
    }

    @Override
    public void put(HttpHost httpHost, AuthScheme authScheme) {
        Args.notNull(httpHost, "HTTP host");
        if (authScheme == null) {
            return;
        }
        if (authScheme instanceof Serializable) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(authScheme);
                objectOutputStream.close();
                this.map.put(this.getKey(httpHost), byteArrayOutputStream.toByteArray());
            } catch (IOException iOException) {
                if (this.log.isWarnEnabled()) {
                    this.log.warn("Unexpected I/O error while serializing auth scheme", iOException);
                }
            }
        } else if (this.log.isDebugEnabled()) {
            this.log.debug("Auth scheme " + authScheme.getClass() + " is not serializable");
        }
    }

    @Override
    public AuthScheme get(HttpHost httpHost) {
        block5: {
            Args.notNull(httpHost, "HTTP host");
            byte[] byArray = this.map.get(this.getKey(httpHost));
            if (byArray != null) {
                try {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    AuthScheme authScheme = (AuthScheme)objectInputStream.readObject();
                    objectInputStream.close();
                    return authScheme;
                } catch (IOException iOException) {
                    if (this.log.isWarnEnabled()) {
                        this.log.warn("Unexpected I/O error while de-serializing auth scheme", iOException);
                    }
                } catch (ClassNotFoundException classNotFoundException) {
                    if (!this.log.isWarnEnabled()) break block5;
                    this.log.warn("Unexpected error while de-serializing auth scheme", classNotFoundException);
                }
            }
        }
        return null;
    }

    @Override
    public void remove(HttpHost httpHost) {
        Args.notNull(httpHost, "HTTP host");
        this.map.remove(this.getKey(httpHost));
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }
}

