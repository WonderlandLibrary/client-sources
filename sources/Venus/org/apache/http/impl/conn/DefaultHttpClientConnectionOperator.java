/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Lookup;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpClientConnectionOperator;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.SchemePortResolver;
import org.apache.http.conn.UnsupportedSchemeException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE_CONDITIONAL)
public class DefaultHttpClientConnectionOperator
implements HttpClientConnectionOperator {
    static final String SOCKET_FACTORY_REGISTRY = "http.socket-factory-registry";
    private final Log log = LogFactory.getLog(this.getClass());
    private final Lookup<ConnectionSocketFactory> socketFactoryRegistry;
    private final SchemePortResolver schemePortResolver;
    private final DnsResolver dnsResolver;

    public DefaultHttpClientConnectionOperator(Lookup<ConnectionSocketFactory> lookup, SchemePortResolver schemePortResolver, DnsResolver dnsResolver) {
        Args.notNull(lookup, "Socket factory registry");
        this.socketFactoryRegistry = lookup;
        this.schemePortResolver = schemePortResolver != null ? schemePortResolver : DefaultSchemePortResolver.INSTANCE;
        this.dnsResolver = dnsResolver != null ? dnsResolver : SystemDefaultDnsResolver.INSTANCE;
    }

    private Lookup<ConnectionSocketFactory> getSocketFactoryRegistry(HttpContext httpContext) {
        Lookup<ConnectionSocketFactory> lookup = (Lookup<ConnectionSocketFactory>)httpContext.getAttribute(SOCKET_FACTORY_REGISTRY);
        if (lookup == null) {
            lookup = this.socketFactoryRegistry;
        }
        return lookup;
    }

    @Override
    public void connect(ManagedHttpClientConnection managedHttpClientConnection, HttpHost httpHost, InetSocketAddress inetSocketAddress, int n, SocketConfig socketConfig, HttpContext httpContext) throws IOException {
        InetAddress[] inetAddressArray;
        Lookup<ConnectionSocketFactory> lookup = this.getSocketFactoryRegistry(httpContext);
        ConnectionSocketFactory connectionSocketFactory = lookup.lookup(httpHost.getSchemeName());
        if (connectionSocketFactory == null) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol is not supported");
        }
        if (httpHost.getAddress() != null) {
            InetAddress[] inetAddressArray2 = new InetAddress[1];
            inetAddressArray = inetAddressArray2;
            inetAddressArray2[0] = httpHost.getAddress();
        } else {
            inetAddressArray = this.dnsResolver.resolve(httpHost.getHostName());
        }
        InetAddress[] inetAddressArray3 = inetAddressArray;
        int n2 = this.schemePortResolver.resolve(httpHost);
        for (int i = 0; i < inetAddressArray3.length; ++i) {
            InetSocketAddress inetSocketAddress2;
            block15: {
                int n3;
                InetAddress inetAddress = inetAddressArray3[i];
                boolean bl = i == inetAddressArray3.length - 1;
                Socket socket = connectionSocketFactory.createSocket(httpContext);
                socket.setSoTimeout(socketConfig.getSoTimeout());
                socket.setReuseAddress(socketConfig.isSoReuseAddress());
                socket.setTcpNoDelay(socketConfig.isTcpNoDelay());
                socket.setKeepAlive(socketConfig.isSoKeepAlive());
                if (socketConfig.getRcvBufSize() > 0) {
                    socket.setReceiveBufferSize(socketConfig.getRcvBufSize());
                }
                if (socketConfig.getSndBufSize() > 0) {
                    socket.setSendBufferSize(socketConfig.getSndBufSize());
                }
                if ((n3 = socketConfig.getSoLinger()) >= 0) {
                    socket.setSoLinger(true, n3);
                }
                managedHttpClientConnection.bind(socket);
                inetSocketAddress2 = new InetSocketAddress(inetAddress, n2);
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Connecting to " + inetSocketAddress2);
                }
                try {
                    socket = connectionSocketFactory.connectSocket(n, socket, httpHost, inetSocketAddress2, inetSocketAddress, httpContext);
                    managedHttpClientConnection.bind(socket);
                    if (this.log.isDebugEnabled()) {
                        this.log.debug("Connection established " + managedHttpClientConnection);
                    }
                    return;
                } catch (SocketTimeoutException socketTimeoutException) {
                    if (bl) {
                        throw new ConnectTimeoutException(socketTimeoutException, httpHost, inetAddressArray3);
                    }
                } catch (ConnectException connectException) {
                    if (bl) {
                        String string = connectException.getMessage();
                        throw "Connection timed out".equals(string) ? new ConnectTimeoutException(connectException, httpHost, inetAddressArray3) : new HttpHostConnectException(connectException, httpHost, inetAddressArray3);
                    }
                } catch (NoRouteToHostException noRouteToHostException) {
                    if (!bl) break block15;
                    throw noRouteToHostException;
                }
            }
            if (!this.log.isDebugEnabled()) continue;
            this.log.debug("Connect to " + inetSocketAddress2 + " timed out. " + "Connection will be retried using another IP address");
        }
    }

    @Override
    public void upgrade(ManagedHttpClientConnection managedHttpClientConnection, HttpHost httpHost, HttpContext httpContext) throws IOException {
        HttpClientContext httpClientContext = HttpClientContext.adapt(httpContext);
        Lookup<ConnectionSocketFactory> lookup = this.getSocketFactoryRegistry(httpClientContext);
        ConnectionSocketFactory connectionSocketFactory = lookup.lookup(httpHost.getSchemeName());
        if (connectionSocketFactory == null) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol is not supported");
        }
        if (!(connectionSocketFactory instanceof LayeredConnectionSocketFactory)) {
            throw new UnsupportedSchemeException(httpHost.getSchemeName() + " protocol does not support connection upgrade");
        }
        LayeredConnectionSocketFactory layeredConnectionSocketFactory = (LayeredConnectionSocketFactory)connectionSocketFactory;
        Socket socket = managedHttpClientConnection.getSocket();
        int n = this.schemePortResolver.resolve(httpHost);
        socket = layeredConnectionSocketFactory.createLayeredSocket(socket, httpHost.getHostName(), n, httpContext);
        managedHttpClientConnection.bind(socket);
    }
}

