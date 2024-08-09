/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpInetSocketAddress;
import org.apache.http.conn.scheme.HostNameResolver;
import org.apache.http.conn.scheme.LayeredSchemeSocketFactory;
import org.apache.http.conn.scheme.LayeredSocketFactory;
import org.apache.http.conn.scheme.SchemeLayeredSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.Args;
import org.apache.http.util.Asserts;
import org.apache.http.util.TextUtils;

@Deprecated
@Contract(threading=ThreadingBehavior.SAFE_CONDITIONAL)
public class SSLSocketFactory
implements LayeredConnectionSocketFactory,
SchemeLayeredSocketFactory,
LayeredSchemeSocketFactory,
LayeredSocketFactory {
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = new AllowAllHostnameVerifier();
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = new BrowserCompatHostnameVerifier();
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = new StrictHostnameVerifier();
    private final javax.net.ssl.SSLSocketFactory socketfactory;
    private final HostNameResolver nameResolver;
    private volatile X509HostnameVerifier hostnameVerifier;
    private final String[] supportedProtocols;
    private final String[] supportedCipherSuites;

    public static SSLSocketFactory getSocketFactory() throws SSLInitializationException {
        return new SSLSocketFactory(SSLContexts.createDefault(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    private static String[] split(String string) {
        if (TextUtils.isBlank(string)) {
            return null;
        }
        return string.split(" *, *");
    }

    public static SSLSocketFactory getSystemSocketFactory() throws SSLInitializationException {
        return new SSLSocketFactory((javax.net.ssl.SSLSocketFactory)javax.net.ssl.SSLSocketFactory.getDefault(), SSLSocketFactory.split(System.getProperty("https.protocols")), SSLSocketFactory.split(System.getProperty("https.cipherSuites")), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(String string, KeyStore keyStore, String string2, KeyStore keyStore2, SecureRandom secureRandom, HostNameResolver hostNameResolver) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(string).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, string2 != null ? string2.toCharArray() : null).loadTrustMaterial(keyStore2).build(), hostNameResolver);
    }

    public SSLSocketFactory(String string, KeyStore keyStore, String string2, KeyStore keyStore2, SecureRandom secureRandom, TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(string).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, string2 != null ? string2.toCharArray() : null).loadTrustMaterial(keyStore2, trustStrategy).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(String string, KeyStore keyStore, String string2, KeyStore keyStore2, SecureRandom secureRandom, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().useProtocol(string).setSecureRandom(secureRandom).loadKeyMaterial(keyStore, string2 != null ? string2.toCharArray() : null).loadTrustMaterial(keyStore2).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(KeyStore keyStore, String string, KeyStore keyStore2) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, string != null ? string.toCharArray() : null).loadTrustMaterial(keyStore2).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore, String string) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadKeyMaterial(keyStore, string != null ? string.toCharArray() : null).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(KeyStore keyStore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(keyStore).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy, X509HostnameVerifier x509HostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), x509HostnameVerifier);
    }

    public SSLSocketFactory(TrustStrategy trustStrategy) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        this(SSLContexts.custom().loadTrustMaterial(null, trustStrategy).build(), BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext) {
        this(sSLContext, BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    }

    public SSLSocketFactory(SSLContext sSLContext, HostNameResolver hostNameResolver) {
        this.socketfactory = sSLContext.getSocketFactory();
        this.hostnameVerifier = BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.nameResolver = hostNameResolver;
        this.supportedProtocols = null;
        this.supportedCipherSuites = null;
    }

    public SSLSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), null, null, x509HostnameVerifier);
    }

    public SSLSocketFactory(SSLContext sSLContext, String[] stringArray, String[] stringArray2, X509HostnameVerifier x509HostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), stringArray, stringArray2, x509HostnameVerifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, null, null, x509HostnameVerifier);
    }

    public SSLSocketFactory(javax.net.ssl.SSLSocketFactory sSLSocketFactory, String[] stringArray, String[] stringArray2, X509HostnameVerifier x509HostnameVerifier) {
        this.socketfactory = Args.notNull(sSLSocketFactory, "SSL socket factory");
        this.supportedProtocols = stringArray;
        this.supportedCipherSuites = stringArray2;
        this.hostnameVerifier = x509HostnameVerifier != null ? x509HostnameVerifier : BROWSER_COMPATIBLE_HOSTNAME_VERIFIER;
        this.nameResolver = null;
    }

    @Override
    public Socket createSocket(HttpParams httpParams) throws IOException {
        return this.createSocket((HttpContext)null);
    }

    @Override
    public Socket createSocket() throws IOException {
        return this.createSocket((HttpContext)null);
    }

    @Override
    public Socket connectSocket(Socket socket, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        Args.notNull(inetSocketAddress, "Remote address");
        Args.notNull(httpParams, "HTTP parameters");
        HttpHost httpHost = inetSocketAddress instanceof HttpInetSocketAddress ? ((HttpInetSocketAddress)inetSocketAddress).getHttpHost() : new HttpHost(inetSocketAddress.getHostName(), inetSocketAddress.getPort(), "https");
        int n = HttpConnectionParams.getSoTimeout(httpParams);
        int n2 = HttpConnectionParams.getConnectionTimeout(httpParams);
        socket.setSoTimeout(n);
        return this.connectSocket(n2, socket, httpHost, inetSocketAddress, inetSocketAddress2, null);
    }

    @Override
    public boolean isSecure(Socket socket) throws IllegalArgumentException {
        Args.notNull(socket, "Socket");
        Asserts.check(socket instanceof SSLSocket, "Socket not created by this factory");
        Asserts.check(!socket.isClosed(), "Socket is closed");
        return false;
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string, int n, HttpParams httpParams) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, string, n, (HttpContext)null);
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string, int n, boolean bl) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, string, n, (HttpContext)null);
    }

    public void setHostnameVerifier(X509HostnameVerifier x509HostnameVerifier) {
        Args.notNull(x509HostnameVerifier, "Hostname verifier");
        this.hostnameVerifier = x509HostnameVerifier;
    }

    public X509HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override
    public Socket connectSocket(Socket socket, String string, int n, InetAddress inetAddress, int n2, HttpParams httpParams) throws IOException, UnknownHostException, ConnectTimeoutException {
        InetAddress inetAddress2 = this.nameResolver != null ? this.nameResolver.resolve(string) : InetAddress.getByName(string);
        InetSocketAddress inetSocketAddress = null;
        if (inetAddress != null || n2 > 0) {
            inetSocketAddress = new InetSocketAddress(inetAddress, n2 > 0 ? n2 : 0);
        }
        HttpInetSocketAddress httpInetSocketAddress = new HttpInetSocketAddress(new HttpHost(string, n), inetAddress2, n);
        return this.connectSocket(socket, httpInetSocketAddress, inetSocketAddress, httpParams);
    }

    @Override
    public Socket createSocket(Socket socket, String string, int n, boolean bl) throws IOException, UnknownHostException {
        return this.createLayeredSocket(socket, string, n, bl);
    }

    protected void prepareSocket(SSLSocket sSLSocket) throws IOException {
    }

    private void internalPrepareSocket(SSLSocket sSLSocket) throws IOException {
        if (this.supportedProtocols != null) {
            sSLSocket.setEnabledProtocols(this.supportedProtocols);
        }
        if (this.supportedCipherSuites != null) {
            sSLSocket.setEnabledCipherSuites(this.supportedCipherSuites);
        }
        this.prepareSocket(sSLSocket);
    }

    @Override
    public Socket createSocket(HttpContext httpContext) throws IOException {
        return SocketFactory.getDefault().createSocket();
    }

    @Override
    public Socket connectSocket(int n, Socket socket, HttpHost httpHost, InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, HttpContext httpContext) throws IOException {
        Socket socket2;
        Args.notNull(httpHost, "HTTP host");
        Args.notNull(inetSocketAddress, "Remote address");
        Socket socket3 = socket2 = socket != null ? socket : this.createSocket(httpContext);
        if (inetSocketAddress2 != null) {
            socket2.bind(inetSocketAddress2);
        }
        try {
            socket2.connect(inetSocketAddress, n);
        } catch (SocketTimeoutException socketTimeoutException) {
            throw new ConnectTimeoutException("Connect to " + inetSocketAddress + " timed out");
        }
        if (socket2 instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket)socket2;
            sSLSocket.startHandshake();
            this.verifyHostname(sSLSocket, httpHost.getHostName());
            return socket2;
        }
        return this.createLayeredSocket(socket2, httpHost.getHostName(), inetSocketAddress.getPort(), httpContext);
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string, int n, HttpContext httpContext) throws IOException {
        SSLSocket sSLSocket = (SSLSocket)this.socketfactory.createSocket(socket, string, n, false);
        this.internalPrepareSocket(sSLSocket);
        sSLSocket.startHandshake();
        this.verifyHostname(sSLSocket, string);
        return sSLSocket;
    }

    private void verifyHostname(SSLSocket sSLSocket, String string) throws IOException {
        try {
            this.hostnameVerifier.verify(string, sSLSocket);
        } catch (IOException iOException) {
            try {
                sSLSocket.close();
            } catch (Exception exception) {
                // empty catch block
            }
            throw iOException;
        }
    }
}

