/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.impl.conn.DefaultRoutePlanner;
import org.apache.http.protocol.HttpContext;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class SystemDefaultRoutePlanner
extends DefaultRoutePlanner {
    private final ProxySelector proxySelector;

    public SystemDefaultRoutePlanner(SchemePortResolver schemePortResolver, ProxySelector proxySelector) {
        super(schemePortResolver);
        this.proxySelector = proxySelector;
    }

    public SystemDefaultRoutePlanner(ProxySelector proxySelector) {
        this(null, proxySelector);
    }

    @Override
    protected HttpHost determineProxy(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws HttpException {
        URI uRI;
        try {
            uRI = new URI(httpHost.toURI());
        } catch (URISyntaxException uRISyntaxException) {
            throw new HttpException("Cannot convert host to URI: " + httpHost, uRISyntaxException);
        }
        ProxySelector proxySelector = this.proxySelector;
        if (proxySelector == null) {
            proxySelector = ProxySelector.getDefault();
        }
        if (proxySelector == null) {
            return null;
        }
        List<Proxy> list = proxySelector.select(uRI);
        Proxy proxy = this.chooseProxy(list);
        HttpHost httpHost2 = null;
        if (proxy.type() == Proxy.Type.HTTP) {
            if (!(proxy.address() instanceof InetSocketAddress)) {
                throw new HttpException("Unable to handle non-Inet proxy address: " + proxy.address());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)proxy.address();
            httpHost2 = new HttpHost(this.getHost(inetSocketAddress), inetSocketAddress.getPort());
        }
        return httpHost2;
    }

    private String getHost(InetSocketAddress inetSocketAddress) {
        return inetSocketAddress.isUnresolved() ? inetSocketAddress.getHostName() : inetSocketAddress.getAddress().getHostAddress();
    }

    private Proxy chooseProxy(List<Proxy> list) {
        Proxy proxy = null;
        block3: for (int i = 0; proxy == null && i < list.size(); ++i) {
            Proxy proxy2 = list.get(i);
            switch (1.$SwitchMap$java$net$Proxy$Type[proxy2.type().ordinal()]) {
                case 1: 
                case 2: {
                    proxy = proxy2;
                    continue block3;
                }
            }
        }
        if (proxy == null) {
            proxy = Proxy.NO_PROXY;
        }
        return proxy;
    }

    static class 1 {
        static final int[] $SwitchMap$java$net$Proxy$Type = new int[Proxy.Type.values().length];

        static {
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.DIRECT.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.HTTP.ordinal()] = 2;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                1.$SwitchMap$java$net$Proxy$Type[Proxy.Type.SOCKS.ordinal()] = 3;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }
}

