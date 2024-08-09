package wtf.shiyeno.proxy;

public enum ProxyType {
    DIRECT,
    SOCKS4,
    SOCKS5,
    HTTP;

    private ProxyType() {
    }
}