/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.Args;
import org.apache.http.util.LangUtils;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public final class HttpRoute
implements RouteInfo,
Cloneable {
    private final HttpHost targetHost;
    private final InetAddress localAddress;
    private final List<HttpHost> proxyChain;
    private final RouteInfo.TunnelType tunnelled;
    private final RouteInfo.LayerType layered;
    private final boolean secure;

    private HttpRoute(HttpHost httpHost, InetAddress inetAddress, List<HttpHost> list, boolean bl, RouteInfo.TunnelType tunnelType, RouteInfo.LayerType layerType) {
        Args.notNull(httpHost, "Target host");
        this.targetHost = HttpRoute.normalize(httpHost);
        this.localAddress = inetAddress;
        this.proxyChain = list != null && !list.isEmpty() ? new ArrayList<HttpHost>(list) : null;
        if (tunnelType == RouteInfo.TunnelType.TUNNELLED) {
            Args.check(this.proxyChain != null, "Proxy required if tunnelled");
        }
        this.secure = bl;
        this.tunnelled = tunnelType != null ? tunnelType : RouteInfo.TunnelType.PLAIN;
        this.layered = layerType != null ? layerType : RouteInfo.LayerType.PLAIN;
    }

    private static int getDefaultPort(String string) {
        if ("http".equalsIgnoreCase(string)) {
            return 1;
        }
        if ("https".equalsIgnoreCase(string)) {
            return 0;
        }
        return 1;
    }

    private static HttpHost normalize(HttpHost httpHost) {
        if (httpHost.getPort() >= 0) {
            return httpHost;
        }
        InetAddress inetAddress = httpHost.getAddress();
        String string = httpHost.getSchemeName();
        return inetAddress != null ? new HttpHost(inetAddress, HttpRoute.getDefaultPort(string), string) : new HttpHost(httpHost.getHostName(), HttpRoute.getDefaultPort(string), string);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost[] httpHostArray, boolean bl, RouteInfo.TunnelType tunnelType, RouteInfo.LayerType layerType) {
        this(httpHost, inetAddress, httpHostArray != null ? Arrays.asList(httpHostArray) : null, bl, tunnelType, layerType);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean bl, RouteInfo.TunnelType tunnelType, RouteInfo.LayerType layerType) {
        this(httpHost, inetAddress, httpHost2 != null ? Collections.singletonList(httpHost2) : null, bl, tunnelType, layerType);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, boolean bl) {
        this(httpHost, inetAddress, Collections.emptyList(), bl, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost) {
        this(httpHost, null, Collections.emptyList(), false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost, InetAddress inetAddress, HttpHost httpHost2, boolean bl) {
        this(httpHost, inetAddress, Collections.singletonList(Args.notNull(httpHost2, "Proxy host")), bl, bl ? RouteInfo.TunnelType.TUNNELLED : RouteInfo.TunnelType.PLAIN, bl ? RouteInfo.LayerType.LAYERED : RouteInfo.LayerType.PLAIN);
    }

    public HttpRoute(HttpHost httpHost, HttpHost httpHost2) {
        this(httpHost, null, httpHost2, false);
    }

    @Override
    public HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override
    public InetAddress getLocalAddress() {
        return this.localAddress;
    }

    public InetSocketAddress getLocalSocketAddress() {
        return this.localAddress != null ? new InetSocketAddress(this.localAddress, 0) : null;
    }

    @Override
    public int getHopCount() {
        return this.proxyChain != null ? this.proxyChain.size() + 1 : 1;
    }

    @Override
    public HttpHost getHopTarget(int n) {
        Args.notNegative(n, "Hop index");
        int n2 = this.getHopCount();
        Args.check(n < n2, "Hop index exceeds tracked route length");
        return n < n2 - 1 ? this.proxyChain.get(n) : this.targetHost;
    }

    @Override
    public HttpHost getProxyHost() {
        return this.proxyChain != null && !this.proxyChain.isEmpty() ? this.proxyChain.get(0) : null;
    }

    @Override
    public RouteInfo.TunnelType getTunnelType() {
        return this.tunnelled;
    }

    @Override
    public boolean isTunnelled() {
        return this.tunnelled == RouteInfo.TunnelType.TUNNELLED;
    }

    @Override
    public RouteInfo.LayerType getLayerType() {
        return this.layered;
    }

    @Override
    public boolean isLayered() {
        return this.layered == RouteInfo.LayerType.LAYERED;
    }

    @Override
    public boolean isSecure() {
        return this.secure;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof HttpRoute) {
            HttpRoute httpRoute = (HttpRoute)object;
            return this.secure == httpRoute.secure && this.tunnelled == httpRoute.tunnelled && this.layered == httpRoute.layered && LangUtils.equals(this.targetHost, httpRoute.targetHost) && LangUtils.equals(this.localAddress, httpRoute.localAddress) && LangUtils.equals(this.proxyChain, httpRoute.proxyChain);
        }
        return true;
    }

    public int hashCode() {
        int n = 17;
        n = LangUtils.hashCode(n, this.targetHost);
        n = LangUtils.hashCode(n, this.localAddress);
        if (this.proxyChain != null) {
            for (HttpHost httpHost : this.proxyChain) {
                n = LangUtils.hashCode(n, httpHost);
            }
        }
        n = LangUtils.hashCode(n, this.secure);
        n = LangUtils.hashCode(n, (Object)this.tunnelled);
        n = LangUtils.hashCode(n, (Object)this.layered);
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(50 + this.getHopCount() * 30);
        if (this.localAddress != null) {
            stringBuilder.append(this.localAddress);
            stringBuilder.append("->");
        }
        stringBuilder.append('{');
        if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
            stringBuilder.append('t');
        }
        if (this.layered == RouteInfo.LayerType.LAYERED) {
            stringBuilder.append('l');
        }
        if (this.secure) {
            stringBuilder.append('s');
        }
        stringBuilder.append("}->");
        if (this.proxyChain != null) {
            for (HttpHost httpHost : this.proxyChain) {
                stringBuilder.append(httpHost);
                stringBuilder.append("->");
            }
        }
        stringBuilder.append(this.targetHost);
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

