/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpInetSocketAddress;
import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SchemeSocketFactory;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class DefaultClientConnectionOperator
implements ClientConnectionOperator {
    private final Log log = LogFactory.getLog(this.getClass());
    protected final SchemeRegistry schemeRegistry;
    protected final DnsResolver dnsResolver;

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry) {
        Args.notNull(schemeRegistry, "Scheme registry");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = new SystemDefaultDnsResolver();
    }

    public DefaultClientConnectionOperator(SchemeRegistry schemeRegistry, DnsResolver dnsResolver) {
        Args.notNull(schemeRegistry, "Scheme registry");
        Args.notNull(dnsResolver, "DNS resolver");
        this.schemeRegistry = schemeRegistry;
        this.dnsResolver = dnsResolver;
    }

    @Override
    public OperatedClientConnection createConnection() {
        return new DefaultClientConnection();
    }

    private SchemeRegistry getSchemeRegistry(HttpContext httpContext) {
        SchemeRegistry schemeRegistry = (SchemeRegistry)httpContext.getAttribute("http.scheme-registry");
        if (schemeRegistry == null) {
            schemeRegistry = this.schemeRegistry;
        }
        return schemeRegistry;
    }

    @Override
    public void openConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, InetAddress inetAddress, HttpContext httpContext, HttpParams httpParams) throws IOException {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "HTTP parameters");
        Asserts.check(!operatedClientConnection.isOpen(), "Connection must not be open");
        SchemeRegistry schemeRegistry = this.getSchemeRegistry(httpContext);
        Scheme scheme = schemeRegistry.getScheme(httpHost.getSchemeName());
        SchemeSocketFactory schemeSocketFactory = scheme.getSchemeSocketFactory();
        InetAddress[] inetAddressArray = this.resolveHostname(httpHost.getHostName());
        int n = scheme.resolvePort(httpHost.getPort());
        for (int i = 0; i < inetAddressArray.length; ++i) {
            HttpInetSocketAddress httpInetSocketAddress;
            block8: {
                InetAddress inetAddress2 = inetAddressArray[i];
                boolean bl = i == inetAddressArray.length - 1;
                Socket socket = schemeSocketFactory.createSocket(httpParams);
                operatedClientConnection.opening(socket, httpHost);
                httpInetSocketAddress = new HttpInetSocketAddress(httpHost, inetAddress2, n);
                InetSocketAddress inetSocketAddress = null;
                if (inetAddress != null) {
                    inetSocketAddress = new InetSocketAddress(inetAddress, 0);
                }
                if (this.log.isDebugEnabled()) {
                    this.log.debug("Connecting to " + httpInetSocketAddress);
                }
                try {
                    Socket socket2 = schemeSocketFactory.connectSocket(socket, httpInetSocketAddress, inetSocketAddress, httpParams);
                    if (socket != socket2) {
                        socket = socket2;
                        operatedClientConnection.opening(socket, httpHost);
                    }
                    this.prepareSocket(socket, httpContext, httpParams);
                    operatedClientConnection.openCompleted(schemeSocketFactory.isSecure(socket), httpParams);
                    return;
                } catch (ConnectException connectException) {
                    if (bl) {
                        throw connectException;
                    }
                } catch (ConnectTimeoutException connectTimeoutException) {
                    if (!bl) break block8;
                    throw connectTimeoutException;
                }
            }
            if (!this.log.isDebugEnabled()) continue;
            this.log.debug("Connect to " + httpInetSocketAddress + " timed out. " + "Connection will be retried using another IP address");
        }
    }

    @Override
    public void updateSecureConnection(OperatedClientConnection operatedClientConnection, HttpHost httpHost, HttpContext httpContext, HttpParams httpParams) throws IOException {
        Args.notNull(operatedClientConnection, "Connection");
        Args.notNull(httpHost, "Target host");
        Args.notNull(httpParams, "Parameters");
        Asserts.check(operatedClientConnection.isOpen(), "Connection must be open");
        SchemeRegistry schemeRegistry = this.getSchemeRegistry(httpContext);
        Scheme scheme = schemeRegistry.getScheme(httpHost.getSchemeName());
        Asserts.check(scheme.getSchemeSocketFactory() instanceof SchemeLayeredSocketFactory, "Socket factory must implement SchemeLayeredSocketFactory");
        SchemeLayeredSocketFactory schemeLayeredSocketFactory = (SchemeLayeredSocketFactory)scheme.getSchemeSocketFactory();
        Socket socket = schemeLayeredSocketFactory.createLayeredSocket(operatedClientConnection.getSocket(), httpHost.getHostName(), scheme.resolvePort(httpHost.getPort()), httpParams);
        this.prepareSocket(socket, httpContext, httpParams);
        operatedClientConnection.update(socket, httpHost, schemeLayeredSocketFactory.isSecure(socket), httpParams);
    }

    protected void prepareSocket(Socket socket, HttpContext httpContext, HttpParams httpParams) throws IOException {
        socket.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(httpParams));
        socket.setSoTimeout(HttpConnectionParams.getSoTimeout(httpParams));
        int n = HttpConnectionParams.getLinger(httpParams);
        if (n >= 0) {
            socket.setSoLinger(n > 0, n);
        }
    }

    protected InetAddress[] resolveHostname(String string) throws UnknownHostException {
        return this.dnsResolver.resolve(string);
    }
}

