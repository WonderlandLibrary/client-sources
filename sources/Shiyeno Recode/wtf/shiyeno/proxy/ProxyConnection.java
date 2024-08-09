package wtf.shiyeno.proxy;

import java.net.InetSocketAddress;

public class ProxyConnection {
    private ProxyType proxyType;
    private InetSocketAddress proxyAddr;

    public ProxyConnection() {
        this.proxyType = ProxyType.DIRECT;
        this.proxyAddr = null;
    }

    public void setup(ProxyType proxyType, InetSocketAddress proxyAddr) {
        this.proxyType = proxyType;
        this.proxyAddr = proxyAddr;
    }

    public void reset() {
        this.proxyType = ProxyType.DIRECT;
        this.proxyAddr = null;
    }

    public ProxyType getProxyType() {
        return this.proxyType;
    }

    public InetSocketAddress getProxyAddr() {
        return this.proxyAddr;
    }
}