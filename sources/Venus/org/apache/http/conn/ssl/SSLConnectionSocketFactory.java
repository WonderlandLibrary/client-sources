/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.conn.ssl;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.auth.x500.X500Principal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpHost;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.Args;
import org.apache.http.util.TextUtils;

@Contract(threading=ThreadingBehavior.SAFE)
public class SSLConnectionSocketFactory
implements LayeredConnectionSocketFactory {
    public static final String TLS = "TLS";
    public static final String SSL = "SSL";
    public static final String SSLV2 = "SSLv2";
    @Deprecated
    public static final X509HostnameVerifier ALLOW_ALL_HOSTNAME_VERIFIER = AllowAllHostnameVerifier.INSTANCE;
    @Deprecated
    public static final X509HostnameVerifier BROWSER_COMPATIBLE_HOSTNAME_VERIFIER = BrowserCompatHostnameVerifier.INSTANCE;
    @Deprecated
    public static final X509HostnameVerifier STRICT_HOSTNAME_VERIFIER = StrictHostnameVerifier.INSTANCE;
    private static final String WEAK_KEY_EXCHANGES = "^(TLS|SSL)_(NULL|ECDH_anon|DH_anon|DH_anon_EXPORT|DHE_RSA_EXPORT|DHE_DSS_EXPORT|DSS_EXPORT|DH_DSS_EXPORT|DH_RSA_EXPORT|RSA_EXPORT|KRB5_EXPORT)_(.*)";
    private static final String WEAK_CIPHERS = "^(TLS|SSL)_(.*)_WITH_(NULL|DES_CBC|DES40_CBC|DES_CBC_40|3DES_EDE_CBC|RC4_128|RC4_40|RC2_CBC_40)_(.*)";
    private static final List<Pattern> WEAK_CIPHER_SUITE_PATTERNS = Collections.unmodifiableList(Arrays.asList(Pattern.compile("^(TLS|SSL)_(NULL|ECDH_anon|DH_anon|DH_anon_EXPORT|DHE_RSA_EXPORT|DHE_DSS_EXPORT|DSS_EXPORT|DH_DSS_EXPORT|DH_RSA_EXPORT|RSA_EXPORT|KRB5_EXPORT)_(.*)", 2), Pattern.compile("^(TLS|SSL)_(.*)_WITH_(NULL|DES_CBC|DES40_CBC|DES_CBC_40|3DES_EDE_CBC|RC4_128|RC4_40|RC2_CBC_40)_(.*)", 2)));
    private final Log log = LogFactory.getLog(this.getClass());
    private final SSLSocketFactory socketfactory;
    private final HostnameVerifier hostnameVerifier;
    private final String[] supportedProtocols;
    private final String[] supportedCipherSuites;

    public static HostnameVerifier getDefaultHostnameVerifier() {
        return new DefaultHostnameVerifier(PublicSuffixMatcherLoader.getDefault());
    }

    public static SSLConnectionSocketFactory getSocketFactory() throws SSLInitializationException {
        return new SSLConnectionSocketFactory(SSLContexts.createDefault(), SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    static boolean isWeakCipherSuite(String string) {
        for (Pattern pattern : WEAK_CIPHER_SUITE_PATTERNS) {
            if (!pattern.matcher(string).matches()) continue;
            return false;
        }
        return true;
    }

    private static String[] split(String string) {
        if (TextUtils.isBlank(string)) {
            return null;
        }
        return string.split(" *, *");
    }

    public static SSLConnectionSocketFactory getSystemSocketFactory() throws SSLInitializationException {
        return new SSLConnectionSocketFactory((SSLSocketFactory)SSLSocketFactory.getDefault(), SSLConnectionSocketFactory.split(System.getProperty("https.protocols")), SSLConnectionSocketFactory.split(System.getProperty("https.cipherSuites")), SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext) {
        this(sSLContext, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLContext sSLContext, X509HostnameVerifier x509HostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), null, null, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLContext sSLContext, String[] stringArray, String[] stringArray2, X509HostnameVerifier x509HostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), stringArray, stringArray2, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, null, null, x509HostnameVerifier);
    }

    @Deprecated
    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, String[] stringArray, String[] stringArray2, X509HostnameVerifier x509HostnameVerifier) {
        this(sSLSocketFactory, stringArray, stringArray2, (HostnameVerifier)x509HostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext, HostnameVerifier hostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), null, null, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLContext sSLContext, String[] stringArray, String[] stringArray2, HostnameVerifier hostnameVerifier) {
        this(Args.notNull(sSLContext, "SSL context").getSocketFactory(), stringArray, stringArray2, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, HostnameVerifier hostnameVerifier) {
        this(sSLSocketFactory, null, null, hostnameVerifier);
    }

    public SSLConnectionSocketFactory(SSLSocketFactory sSLSocketFactory, String[] stringArray, String[] stringArray2, HostnameVerifier hostnameVerifier) {
        this.socketfactory = Args.notNull(sSLSocketFactory, "SSL socket factory");
        this.supportedProtocols = stringArray;
        this.supportedCipherSuites = stringArray2;
        this.hostnameVerifier = hostnameVerifier != null ? hostnameVerifier : SSLConnectionSocketFactory.getDefaultHostnameVerifier();
    }

    protected void prepareSocket(SSLSocket sSLSocket) throws IOException {
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
            if (n > 0 && socket2.getSoTimeout() == 0) {
                socket2.setSoTimeout(n);
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Connecting socket to " + inetSocketAddress + " with timeout " + n);
            }
            socket2.connect(inetSocketAddress, n);
        } catch (IOException iOException) {
            try {
                socket2.close();
            } catch (IOException iOException2) {
                // empty catch block
            }
            throw iOException;
        }
        if (socket2 instanceof SSLSocket) {
            SSLSocket sSLSocket = (SSLSocket)socket2;
            this.log.debug("Starting handshake");
            sSLSocket.startHandshake();
            this.verifyHostname(sSLSocket, httpHost.getHostName());
            return socket2;
        }
        return this.createLayeredSocket(socket2, httpHost.getHostName(), inetSocketAddress.getPort(), httpContext);
    }

    @Override
    public Socket createLayeredSocket(Socket socket, String string, int n, HttpContext httpContext) throws IOException {
        ArrayList<String> arrayList;
        String[] stringArray;
        SSLSocket sSLSocket = (SSLSocket)this.socketfactory.createSocket(socket, string, n, false);
        if (this.supportedProtocols != null) {
            sSLSocket.setEnabledProtocols(this.supportedProtocols);
        } else {
            stringArray = sSLSocket.getEnabledProtocols();
            arrayList = new ArrayList<String>(stringArray.length);
            for (String string2 : stringArray) {
                if (string2.startsWith(SSL)) continue;
                arrayList.add(string2);
            }
            if (!arrayList.isEmpty()) {
                sSLSocket.setEnabledProtocols(arrayList.toArray(new String[arrayList.size()]));
            }
        }
        if (this.supportedCipherSuites != null) {
            sSLSocket.setEnabledCipherSuites(this.supportedCipherSuites);
        } else {
            stringArray = sSLSocket.getEnabledCipherSuites();
            arrayList = new ArrayList(stringArray.length);
            for (String string2 : stringArray) {
                if (SSLConnectionSocketFactory.isWeakCipherSuite(string2)) continue;
                arrayList.add(string2);
            }
            if (!arrayList.isEmpty()) {
                sSLSocket.setEnabledCipherSuites(arrayList.toArray(new String[arrayList.size()]));
            }
        }
        if (this.log.isDebugEnabled()) {
            this.log.debug("Enabled protocols: " + Arrays.asList(sSLSocket.getEnabledProtocols()));
            this.log.debug("Enabled cipher suites:" + Arrays.asList(sSLSocket.getEnabledCipherSuites()));
        }
        this.prepareSocket(sSLSocket);
        this.log.debug("Starting handshake");
        sSLSocket.startHandshake();
        this.verifyHostname(sSLSocket, string);
        return sSLSocket;
    }

    private void verifyHostname(SSLSocket sSLSocket, String string) throws IOException {
        try {
            Object object;
            X509Certificate x509Certificate;
            Certificate[] certificateArray;
            SSLSession sSLSession = sSLSocket.getSession();
            if (sSLSession == null) {
                certificateArray = sSLSocket.getInputStream();
                certificateArray.available();
                sSLSession = sSLSocket.getSession();
                if (sSLSession == null) {
                    sSLSocket.startHandshake();
                    sSLSession = sSLSocket.getSession();
                }
            }
            if (sSLSession == null) {
                throw new SSLHandshakeException("SSL session not available");
            }
            if (this.log.isDebugEnabled()) {
                this.log.debug("Secure session established");
                this.log.debug(" negotiated protocol: " + sSLSession.getProtocol());
                this.log.debug(" negotiated cipher suite: " + sSLSession.getCipherSuite());
                try {
                    Serializable serializable;
                    certificateArray = sSLSession.getPeerCertificates();
                    x509Certificate = (X509Certificate)certificateArray[0];
                    object = x509Certificate.getSubjectX500Principal();
                    this.log.debug(" peer principal: " + ((X500Principal)object).toString());
                    Collection<List<?>> collection = x509Certificate.getSubjectAlternativeNames();
                    if (collection != null) {
                        serializable = new ArrayList();
                        for (List<?> list : collection) {
                            if (list.isEmpty()) continue;
                            serializable.add((String)list.get(1));
                        }
                        this.log.debug(" peer alternative names: " + serializable);
                    }
                    serializable = x509Certificate.getIssuerX500Principal();
                    this.log.debug(" issuer principal: " + ((X500Principal)serializable).toString());
                    Collection<List<?>> collection2 = x509Certificate.getIssuerAlternativeNames();
                    if (collection2 != null) {
                        List<?> list;
                        list = new ArrayList();
                        Iterator iterator2 = collection2.iterator();
                        while (iterator2.hasNext()) {
                            List list2 = (List)iterator2.next();
                            if (list2.isEmpty()) continue;
                            list.add((String)list2.get(1));
                        }
                        this.log.debug(" issuer alternative names: " + list);
                    }
                } catch (Exception exception) {
                    // empty catch block
                }
            }
            if (!this.hostnameVerifier.verify(string, sSLSession)) {
                certificateArray = sSLSession.getPeerCertificates();
                x509Certificate = (X509Certificate)certificateArray[0];
                object = DefaultHostnameVerifier.getSubjectAltNames(x509Certificate);
                throw new SSLPeerUnverifiedException("Certificate for <" + string + "> doesn't match any " + "of the subject alternative names: " + object);
            }
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

