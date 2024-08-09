/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.net.ssl.KeyStoreConfiguration;
import org.apache.logging.log4j.core.net.ssl.KeyStoreConfigurationException;
import org.apache.logging.log4j.core.net.ssl.TrustStoreConfiguration;
import org.apache.logging.log4j.core.net.ssl.TrustStoreConfigurationException;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="Ssl", category="Core", printObject=true)
public class SslConfiguration {
    private static final StatusLogger LOGGER = StatusLogger.getLogger();
    private final KeyStoreConfiguration keyStoreConfig;
    private final TrustStoreConfiguration trustStoreConfig;
    private final SSLContext sslContext;
    private final String protocol;

    private SslConfiguration(String string, KeyStoreConfiguration keyStoreConfiguration, TrustStoreConfiguration trustStoreConfiguration) {
        this.keyStoreConfig = keyStoreConfiguration;
        this.trustStoreConfig = trustStoreConfiguration;
        this.protocol = string == null ? "SSL" : string;
        this.sslContext = this.createSslContext();
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslContext.getSocketFactory();
    }

    public SSLServerSocketFactory getSslServerSocketFactory() {
        return this.sslContext.getServerSocketFactory();
    }

    private SSLContext createSslContext() {
        SSLContext sSLContext = null;
        try {
            sSLContext = this.createSslContextBasedOnConfiguration();
            LOGGER.debug("Creating SSLContext with the given parameters");
        } catch (TrustStoreConfigurationException trustStoreConfigurationException) {
            sSLContext = this.createSslContextWithTrustStoreFailure();
        } catch (KeyStoreConfigurationException keyStoreConfigurationException) {
            sSLContext = this.createSslContextWithKeyStoreFailure();
        }
        return sSLContext;
    }

    private SSLContext createSslContextWithTrustStoreFailure() {
        SSLContext sSLContext;
        try {
            sSLContext = this.createSslContextWithDefaultTrustManagerFactory();
            LOGGER.debug("Creating SSLContext with default truststore");
        } catch (KeyStoreConfigurationException keyStoreConfigurationException) {
            sSLContext = this.createDefaultSslContext();
            LOGGER.debug("Creating SSLContext with default configuration");
        }
        return sSLContext;
    }

    private SSLContext createSslContextWithKeyStoreFailure() {
        SSLContext sSLContext;
        try {
            sSLContext = this.createSslContextWithDefaultKeyManagerFactory();
            LOGGER.debug("Creating SSLContext with default keystore");
        } catch (TrustStoreConfigurationException trustStoreConfigurationException) {
            sSLContext = this.createDefaultSslContext();
            LOGGER.debug("Creating SSLContext with default configuration");
        }
        return sSLContext;
    }

    private SSLContext createSslContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        return this.createSslContext(false, false);
    }

    private SSLContext createSslContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
        try {
            return this.createSslContext(true, false);
        } catch (KeyStoreConfigurationException keyStoreConfigurationException) {
            LOGGER.debug("Exception occured while using default keystore. This should be a BUG");
            return null;
        }
    }

    private SSLContext createSslContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException {
        try {
            return this.createSslContext(false, true);
        } catch (TrustStoreConfigurationException trustStoreConfigurationException) {
            LOGGER.debug("Exception occured while using default truststore. This should be a BUG");
            return null;
        }
    }

    private SSLContext createDefaultSslContext() {
        try {
            return SSLContext.getDefault();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("Failed to create an SSLContext with default configuration", (Throwable)noSuchAlgorithmException);
            return null;
        }
    }

    private SSLContext createSslContext(boolean bl, boolean bl2) throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        try {
            Object object;
            KeyManager[] keyManagerArray = null;
            TrustManager[] trustManagerArray = null;
            SSLContext sSLContext = SSLContext.getInstance(this.protocol);
            if (!bl) {
                object = this.loadKeyManagerFactory();
                keyManagerArray = ((KeyManagerFactory)object).getKeyManagers();
            }
            if (!bl2) {
                object = this.loadTrustManagerFactory();
                trustManagerArray = ((TrustManagerFactory)object).getTrustManagers();
            }
            sSLContext.init(keyManagerArray, trustManagerArray, null);
            return sSLContext;
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("No Provider supports a TrustManagerFactorySpi implementation for the specified protocol", (Throwable)noSuchAlgorithmException);
            throw new TrustStoreConfigurationException(noSuchAlgorithmException);
        } catch (KeyManagementException keyManagementException) {
            LOGGER.error("Failed to initialize the SSLContext", (Throwable)keyManagementException);
            throw new KeyStoreConfigurationException(keyManagementException);
        }
    }

    private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
        if (this.trustStoreConfig == null) {
            throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
        }
        try {
            return this.trustStoreConfig.initTrustManagerFactory();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("The specified algorithm is not available from the specified provider", (Throwable)noSuchAlgorithmException);
            throw new TrustStoreConfigurationException(noSuchAlgorithmException);
        } catch (KeyStoreException keyStoreException) {
            LOGGER.error("Failed to initialize the TrustManagerFactory", (Throwable)keyStoreException);
            throw new TrustStoreConfigurationException(keyStoreException);
        }
    }

    private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
        if (this.keyStoreConfig == null) {
            throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
        }
        try {
            return this.keyStoreConfig.initKeyManagerFactory();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("The specified algorithm is not available from the specified provider", (Throwable)noSuchAlgorithmException);
            throw new KeyStoreConfigurationException(noSuchAlgorithmException);
        } catch (KeyStoreException keyStoreException) {
            LOGGER.error("Failed to initialize the TrustManagerFactory", (Throwable)keyStoreException);
            throw new KeyStoreConfigurationException(keyStoreException);
        } catch (UnrecoverableKeyException unrecoverableKeyException) {
            LOGGER.error("The key cannot be recovered (e.g. the given password is wrong)", (Throwable)unrecoverableKeyException);
            throw new KeyStoreConfigurationException(unrecoverableKeyException);
        }
    }

    @PluginFactory
    public static SslConfiguration createSSLConfiguration(@PluginAttribute(value="protocol") String string, @PluginElement(value="KeyStore") KeyStoreConfiguration keyStoreConfiguration, @PluginElement(value="TrustStore") TrustStoreConfiguration trustStoreConfiguration) {
        return new SslConfiguration(string, keyStoreConfiguration, trustStoreConfiguration);
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.keyStoreConfig == null ? 0 : this.keyStoreConfig.hashCode());
        n2 = 31 * n2 + (this.protocol == null ? 0 : this.protocol.hashCode());
        n2 = 31 * n2 + (this.sslContext == null ? 0 : this.sslContext.hashCode());
        n2 = 31 * n2 + (this.trustStoreConfig == null ? 0 : this.trustStoreConfig.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        SslConfiguration sslConfiguration = (SslConfiguration)object;
        if (this.keyStoreConfig == null ? sslConfiguration.keyStoreConfig != null : !this.keyStoreConfig.equals(sslConfiguration.keyStoreConfig)) {
            return true;
        }
        if (this.protocol == null ? sslConfiguration.protocol != null : !this.protocol.equals(sslConfiguration.protocol)) {
            return true;
        }
        if (this.sslContext == null ? sslConfiguration.sslContext != null : !this.sslContext.equals(sslConfiguration.sslContext)) {
            return true;
        }
        return this.trustStoreConfig == null ? sslConfiguration.trustStoreConfig != null : !this.trustStoreConfig.equals(sslConfiguration.trustStoreConfig);
    }
}

