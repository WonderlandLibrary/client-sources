/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.RouteInfo;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.LangUtils;

public final class RouteTracker
implements RouteInfo,
Cloneable {
    private final HttpHost targetHost;
    private final InetAddress localAddress;
    private boolean connected;
    private HttpHost[] proxyChain;
    private RouteInfo.TunnelType tunnelled;
    private RouteInfo.LayerType layered;
    private boolean secure;

    public RouteTracker(HttpHost httpHost, InetAddress inetAddress) {
        Args.notNull(httpHost, "Target host");
        this.targetHost = httpHost;
        this.localAddress = inetAddress;
        this.tunnelled = RouteInfo.TunnelType.PLAIN;
        this.layered = RouteInfo.LayerType.PLAIN;
    }

    public void reset() {
        this.connected = false;
        this.proxyChain = null;
        this.tunnelled = RouteInfo.TunnelType.PLAIN;
        this.layered = RouteInfo.LayerType.PLAIN;
        this.secure = false;
    }

    public RouteTracker(HttpRoute httpRoute) {
        this(httpRoute.getTargetHost(), httpRoute.getLocalAddress());
    }

    public void connectTarget(boolean bl) {
        Asserts.check(!this.connected, "Already connected");
        this.connected = true;
        this.secure = bl;
    }

    public void connectProxy(HttpHost httpHost, boolean bl) {
        Args.notNull(httpHost, "Proxy host");
        Asserts.check(!this.connected, "Already connected");
        this.connected = true;
        this.proxyChain = new HttpHost[]{httpHost};
        this.secure = bl;
    }

    public void tunnelTarget(boolean bl) {
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
        this.secure = bl;
    }

    public void tunnelProxy(HttpHost httpHost, boolean bl) {
        Args.notNull(httpHost, "Proxy host");
        Asserts.check(this.connected, "No tunnel unless connected");
        Asserts.notNull(this.proxyChain, "No tunnel without proxy");
        HttpHost[] httpHostArray = new HttpHost[this.proxyChain.length + 1];
        System.arraycopy(this.proxyChain, 0, httpHostArray, 0, this.proxyChain.length);
        httpHostArray[httpHostArray.length - 1] = httpHost;
        this.proxyChain = httpHostArray;
        this.secure = bl;
    }

    public void layerProtocol(boolean bl) {
        Asserts.check(this.connected, "No layered protocol unless connected");
        this.layered = RouteInfo.LayerType.LAYERED;
        this.secure = bl;
    }

    @Override
    public HttpHost getTargetHost() {
        return this.targetHost;
    }

    @Override
    public InetAddress getLocalAddress() {
        return this.localAddress;
    }

    @Override
    public int getHopCount() {
        int n = 0;
        if (this.connected) {
            n = this.proxyChain == null ? 1 : this.proxyChain.length + 1;
        }
        return n;
    }

    @Override
    public HttpHost getHopTarget(int n) {
        Args.notNegative(n, "Hop index");
        int n2 = this.getHopCount();
        Args.check(n < n2, "Hop index exceeds tracked route length");
        HttpHost httpHost = null;
        httpHost = n < n2 - 1 ? this.proxyChain[n] : this.targetHost;
        return httpHost;
    }

    @Override
    public HttpHost getProxyHost() {
        return this.proxyChain == null ? null : this.proxyChain[0];
    }

    public boolean isConnected() {
        return this.connected;
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

    public HttpRoute toRoute() {
        return !this.connected ? null : new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof RouteTracker)) {
            return true;
        }
        RouteTracker routeTracker = (RouteTracker)object;
        return this.connected == routeTracker.connected && this.secure == routeTracker.secure && this.tunnelled == routeTracker.tunnelled && this.layered == routeTracker.layered && LangUtils.equals(this.targetHost, routeTracker.targetHost) && LangUtils.equals(this.localAddress, routeTracker.localAddress) && LangUtils.equals(this.proxyChain, routeTracker.proxyChain);
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
        n = LangUtils.hashCode(n, this.connected);
        n = LangUtils.hashCode(n, this.secure);
        n = LangUtils.hashCode(n, (Object)this.tunnelled);
        n = LangUtils.hashCode(n, (Object)this.layered);
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(50 + this.getHopCount() * 30);
        stringBuilder.append("RouteTracker[");
        if (this.localAddress != null) {
            stringBuilder.append(this.localAddress);
            stringBuilder.append("->");
        }
        stringBuilder.append('{');
        if (this.connected) {
            stringBuilder.append('c');
        }
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
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

