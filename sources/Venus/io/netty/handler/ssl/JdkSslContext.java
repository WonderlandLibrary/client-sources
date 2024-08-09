/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.ssl.ApplicationProtocolConfig;
import io.netty.handler.ssl.ApplicationProtocolNegotiator;
import io.netty.handler.ssl.CipherSuiteFilter;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.IdentityCipherSuiteFilter;
import io.netty.handler.ssl.JdkAlpnApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkDefaultApplicationProtocolNegotiator;
import io.netty.handler.ssl.JdkNpnApplicationProtocolNegotiator;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSessionContext;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class JdkSslContext
extends SslContext {
    private static final InternalLogger logger;
    static final String PROTOCOL = "TLS";
    private static final String[] DEFAULT_PROTOCOLS;
    private static final List<String> DEFAULT_CIPHERS;
    private static final Set<String> SUPPORTED_CIPHERS;
    private final String[] protocols;
    private final String[] cipherSuites;
    private final List<String> unmodifiableCipherSuites;
    private final JdkApplicationProtocolNegotiator apn;
    private final ClientAuth clientAuth;
    private final SSLContext sslContext;
    private final boolean isClient;

    public JdkSslContext(SSLContext sSLContext, boolean bl, ClientAuth clientAuth) {
        this(sSLContext, bl, null, IdentityCipherSuiteFilter.INSTANCE, JdkDefaultApplicationProtocolNegotiator.INSTANCE, clientAuth, null, false);
    }

    public JdkSslContext(SSLContext sSLContext, boolean bl, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, ApplicationProtocolConfig applicationProtocolConfig, ClientAuth clientAuth) {
        this(sSLContext, bl, iterable, cipherSuiteFilter, JdkSslContext.toNegotiator(applicationProtocolConfig, !bl), clientAuth, null, false);
    }

    JdkSslContext(SSLContext sSLContext, boolean bl, Iterable<String> iterable, CipherSuiteFilter cipherSuiteFilter, JdkApplicationProtocolNegotiator jdkApplicationProtocolNegotiator, ClientAuth clientAuth, String[] stringArray, boolean bl2) {
        super(bl2);
        this.apn = ObjectUtil.checkNotNull(jdkApplicationProtocolNegotiator, "apn");
        this.clientAuth = ObjectUtil.checkNotNull(clientAuth, "clientAuth");
        this.cipherSuites = ObjectUtil.checkNotNull(cipherSuiteFilter, "cipherFilter").filterCipherSuites(iterable, DEFAULT_CIPHERS, SUPPORTED_CIPHERS);
        this.protocols = stringArray == null ? DEFAULT_PROTOCOLS : stringArray;
        this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
        this.sslContext = ObjectUtil.checkNotNull(sSLContext, "sslContext");
        this.isClient = bl;
    }

    public final SSLContext context() {
        return this.sslContext;
    }

    @Override
    public final boolean isClient() {
        return this.isClient;
    }

    @Override
    public final SSLSessionContext sessionContext() {
        if (this.isServer()) {
            return this.context().getServerSessionContext();
        }
        return this.context().getClientSessionContext();
    }

    @Override
    public final List<String> cipherSuites() {
        return this.unmodifiableCipherSuites;
    }

    @Override
    public final long sessionCacheSize() {
        return this.sessionContext().getSessionCacheSize();
    }

    @Override
    public final long sessionTimeout() {
        return this.sessionContext().getSessionTimeout();
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator) {
        return this.configureAndWrapEngine(this.context().createSSLEngine(), byteBufAllocator);
    }

    @Override
    public final SSLEngine newEngine(ByteBufAllocator byteBufAllocator, String string, int n) {
        return this.configureAndWrapEngine(this.context().createSSLEngine(string, n), byteBufAllocator);
    }

    private SSLEngine configureAndWrapEngine(SSLEngine sSLEngine, ByteBufAllocator byteBufAllocator) {
        JdkApplicationProtocolNegotiator.SslEngineWrapperFactory sslEngineWrapperFactory;
        sSLEngine.setEnabledCipherSuites(this.cipherSuites);
        sSLEngine.setEnabledProtocols(this.protocols);
        sSLEngine.setUseClientMode(this.isClient());
        if (this.isServer()) {
            switch (this.clientAuth) {
                case OPTIONAL: {
                    sSLEngine.setWantClientAuth(false);
                    break;
                }
                case REQUIRE: {
                    sSLEngine.setNeedClientAuth(false);
                    break;
                }
                case NONE: {
                    break;
                }
                default: {
                    throw new Error("Unknown auth " + (Object)((Object)this.clientAuth));
                }
            }
        }
        if ((sslEngineWrapperFactory = this.apn.wrapperFactory()) instanceof JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory) {
            return ((JdkApplicationProtocolNegotiator.AllocatorAwareSslEngineWrapperFactory)sslEngineWrapperFactory).wrapSslEngine(sSLEngine, byteBufAllocator, this.apn, this.isServer());
        }
        return sslEngineWrapperFactory.wrapSslEngine(sSLEngine, this.apn, this.isServer());
    }

    @Override
    public final JdkApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.apn;
    }

    static JdkApplicationProtocolNegotiator toNegotiator(ApplicationProtocolConfig applicationProtocolConfig, boolean bl) {
        if (applicationProtocolConfig == null) {
            return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
        }
        switch (applicationProtocolConfig.protocol()) {
            case NONE: {
                return JdkDefaultApplicationProtocolNegotiator.INSTANCE;
            }
            case ALPN: {
                if (bl) {
                    switch (applicationProtocolConfig.selectorFailureBehavior()) {
                        case FATAL_ALERT: {
                            return new JdkAlpnApplicationProtocolNegotiator(true, applicationProtocolConfig.supportedProtocols());
                        }
                        case NO_ADVERTISE: {
                            return new JdkAlpnApplicationProtocolNegotiator(false, applicationProtocolConfig.supportedProtocols());
                        }
                    }
                    throw new UnsupportedOperationException("JDK provider does not support " + (Object)((Object)applicationProtocolConfig.selectorFailureBehavior()) + " failure behavior");
                }
                switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                    case ACCEPT: {
                        return new JdkAlpnApplicationProtocolNegotiator(false, applicationProtocolConfig.supportedProtocols());
                    }
                    case FATAL_ALERT: {
                        return new JdkAlpnApplicationProtocolNegotiator(true, applicationProtocolConfig.supportedProtocols());
                    }
                }
                throw new UnsupportedOperationException("JDK provider does not support " + (Object)((Object)applicationProtocolConfig.selectedListenerFailureBehavior()) + " failure behavior");
            }
            case NPN: {
                if (bl) {
                    switch (applicationProtocolConfig.selectedListenerFailureBehavior()) {
                        case ACCEPT: {
                            return new JdkNpnApplicationProtocolNegotiator(false, applicationProtocolConfig.supportedProtocols());
                        }
                        case FATAL_ALERT: {
                            return new JdkNpnApplicationProtocolNegotiator(true, applicationProtocolConfig.supportedProtocols());
                        }
                    }
                    throw new UnsupportedOperationException("JDK provider does not support " + (Object)((Object)applicationProtocolConfig.selectedListenerFailureBehavior()) + " failure behavior");
                }
                switch (applicationProtocolConfig.selectorFailureBehavior()) {
                    case FATAL_ALERT: {
                        return new JdkNpnApplicationProtocolNegotiator(true, applicationProtocolConfig.supportedProtocols());
                    }
                    case NO_ADVERTISE: {
                        return new JdkNpnApplicationProtocolNegotiator(false, applicationProtocolConfig.supportedProtocols());
                    }
                }
                throw new UnsupportedOperationException("JDK provider does not support " + (Object)((Object)applicationProtocolConfig.selectorFailureBehavior()) + " failure behavior");
            }
        }
        throw new UnsupportedOperationException("JDK provider does not support " + (Object)((Object)applicationProtocolConfig.protocol()) + " protocol");
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, File file2, String string, KeyManagerFactory keyManagerFactory) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, CertificateException, KeyException, IOException {
        String string2 = Security.getProperty("ssl.KeyManagerFactory.algorithm");
        if (string2 == null) {
            string2 = "SunX509";
        }
        return JdkSslContext.buildKeyManagerFactory(file, string2, file2, string, keyManagerFactory);
    }

    @Deprecated
    protected static KeyManagerFactory buildKeyManagerFactory(File file, String string, File file2, String string2, KeyManagerFactory keyManagerFactory) throws KeyStoreException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyException, UnrecoverableKeyException {
        return JdkSslContext.buildKeyManagerFactory(JdkSslContext.toX509Certificates(file), string, JdkSslContext.toPrivateKey(file2, string2), string2, keyManagerFactory);
    }

    @Override
    public ApplicationProtocolNegotiator applicationProtocolNegotiator() {
        return this.applicationProtocolNegotiator();
    }

    static {
        Object object;
        int n;
        SSLContext sSLContext;
        logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
        try {
            sSLContext = SSLContext.getInstance(PROTOCOL);
            sSLContext.init(null, null, null);
        } catch (Exception exception) {
            throw new Error("failed to initialize the default SSL context", exception);
        }
        SSLEngine sSLEngine = sSLContext.createSSLEngine();
        String[] stringArray = sSLEngine.getSupportedProtocols();
        HashSet<String> hashSet = new HashSet<String>(stringArray.length);
        for (n = 0; n < stringArray.length; ++n) {
            hashSet.add(stringArray[n]);
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        SslUtils.addIfSupported(hashSet, arrayList, "TLSv1.2", "TLSv1.1", "TLSv1");
        DEFAULT_PROTOCOLS = !arrayList.isEmpty() ? arrayList.toArray(new String[arrayList.size()]) : sSLEngine.getEnabledProtocols();
        String[] stringArray2 = sSLEngine.getSupportedCipherSuites();
        SUPPORTED_CIPHERS = new HashSet<String>(stringArray2.length);
        for (n = 0; n < stringArray2.length; ++n) {
            object = stringArray2[n];
            SUPPORTED_CIPHERS.add((String)object);
            if (!((String)object).startsWith("SSL_")) continue;
            String string = "TLS_" + ((String)object).substring(4);
            try {
                sSLEngine.setEnabledCipherSuites(new String[]{string});
                SUPPORTED_CIPHERS.add(string);
                continue;
            } catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        object = new ArrayList();
        SslUtils.addIfSupported(SUPPORTED_CIPHERS, (List<String>)object, SslUtils.DEFAULT_CIPHER_SUITES);
        SslUtils.useFallbackCiphersIfDefaultIsEmpty((List<String>)object, sSLEngine.getEnabledCipherSuites());
        DEFAULT_CIPHERS = Collections.unmodifiableList(object);
        if (logger.isDebugEnabled()) {
            logger.debug("Default protocols (JDK): {} ", (Object)Arrays.asList(DEFAULT_PROTOCOLS));
            logger.debug("Default cipher suites (JDK): {}", (Object)DEFAULT_CIPHERS);
        }
    }
}

