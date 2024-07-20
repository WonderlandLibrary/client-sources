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

    private SslConfiguration(String protocol, KeyStoreConfiguration keyStoreConfig, TrustStoreConfiguration trustStoreConfig) {
        this.keyStoreConfig = keyStoreConfig;
        this.trustStoreConfig = trustStoreConfig;
        this.protocol = protocol == null ? "SSL" : protocol;
        this.sslContext = this.createSslContext();
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslContext.getSocketFactory();
    }

    public SSLServerSocketFactory getSslServerSocketFactory() {
        return this.sslContext.getServerSocketFactory();
    }

    private SSLContext createSslContext() {
        SSLContext context = null;
        try {
            context = this.createSslContextBasedOnConfiguration();
            LOGGER.debug("Creating SSLContext with the given parameters");
        } catch (TrustStoreConfigurationException e) {
            context = this.createSslContextWithTrustStoreFailure();
        } catch (KeyStoreConfigurationException e) {
            context = this.createSslContextWithKeyStoreFailure();
        }
        return context;
    }

    private SSLContext createSslContextWithTrustStoreFailure() {
        SSLContext context;
        try {
            context = this.createSslContextWithDefaultTrustManagerFactory();
            LOGGER.debug("Creating SSLContext with default truststore");
        } catch (KeyStoreConfigurationException e) {
            context = this.createDefaultSslContext();
            LOGGER.debug("Creating SSLContext with default configuration");
        }
        return context;
    }

    private SSLContext createSslContextWithKeyStoreFailure() {
        SSLContext context;
        try {
            context = this.createSslContextWithDefaultKeyManagerFactory();
            LOGGER.debug("Creating SSLContext with default keystore");
        } catch (TrustStoreConfigurationException e) {
            context = this.createDefaultSslContext();
            LOGGER.debug("Creating SSLContext with default configuration");
        }
        return context;
    }

    private SSLContext createSslContextBasedOnConfiguration() throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        return this.createSslContext(false, false);
    }

    private SSLContext createSslContextWithDefaultKeyManagerFactory() throws TrustStoreConfigurationException {
        try {
            return this.createSslContext(true, false);
        } catch (KeyStoreConfigurationException dummy) {
            LOGGER.debug("Exception occured while using default keystore. This should be a BUG");
            return null;
        }
    }

    private SSLContext createSslContextWithDefaultTrustManagerFactory() throws KeyStoreConfigurationException {
        try {
            return this.createSslContext(false, true);
        } catch (TrustStoreConfigurationException dummy) {
            LOGGER.debug("Exception occured while using default truststore. This should be a BUG");
            return null;
        }
    }

    private SSLContext createDefaultSslContext() {
        try {
            return SSLContext.getDefault();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Failed to create an SSLContext with default configuration", (Throwable)e);
            return null;
        }
    }

    private SSLContext createSslContext(boolean loadDefaultKeyManagerFactory, boolean loadDefaultTrustManagerFactory) throws KeyStoreConfigurationException, TrustStoreConfigurationException {
        try {
            KeyManager[] kManagers = null;
            TrustManager[] tManagers = null;
            SSLContext newSslContext = SSLContext.getInstance(this.protocol);
            if (!loadDefaultKeyManagerFactory) {
                KeyManagerFactory kmFactory = this.loadKeyManagerFactory();
                kManagers = kmFactory.getKeyManagers();
            }
            if (!loadDefaultTrustManagerFactory) {
                TrustManagerFactory tmFactory = this.loadTrustManagerFactory();
                tManagers = tmFactory.getTrustManagers();
            }
            newSslContext.init(kManagers, tManagers, null);
            return newSslContext;
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No Provider supports a TrustManagerFactorySpi implementation for the specified protocol", (Throwable)e);
            throw new TrustStoreConfigurationException(e);
        } catch (KeyManagementException e) {
            LOGGER.error("Failed to initialize the SSLContext", (Throwable)e);
            throw new KeyStoreConfigurationException(e);
        }
    }

    private TrustManagerFactory loadTrustManagerFactory() throws TrustStoreConfigurationException {
        if (this.trustStoreConfig == null) {
            throw new TrustStoreConfigurationException(new Exception("The trustStoreConfiguration is null"));
        }
        try {
            return this.trustStoreConfig.initTrustManagerFactory();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("The specified algorithm is not available from the specified provider", (Throwable)e);
            throw new TrustStoreConfigurationException(e);
        } catch (KeyStoreException e) {
            LOGGER.error("Failed to initialize the TrustManagerFactory", (Throwable)e);
            throw new TrustStoreConfigurationException(e);
        }
    }

    private KeyManagerFactory loadKeyManagerFactory() throws KeyStoreConfigurationException {
        if (this.keyStoreConfig == null) {
            throw new KeyStoreConfigurationException(new Exception("The keyStoreConfiguration is null"));
        }
        try {
            return this.keyStoreConfig.initKeyManagerFactory();
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("The specified algorithm is not available from the specified provider", (Throwable)e);
            throw new KeyStoreConfigurationException(e);
        } catch (KeyStoreException e) {
            LOGGER.error("Failed to initialize the TrustManagerFactory", (Throwable)e);
            throw new KeyStoreConfigurationException(e);
        } catch (UnrecoverableKeyException e) {
            LOGGER.error("The key cannot be recovered (e.g. the given password is wrong)", (Throwable)e);
            throw new KeyStoreConfigurationException(e);
        }
    }

    @PluginFactory
    public static SslConfiguration createSSLConfiguration(@PluginAttribute(value="protocol") String protocol, @PluginElement(value="KeyStore") KeyStoreConfiguration keyStoreConfig, @PluginElement(value="TrustStore") TrustStoreConfiguration trustStoreConfig) {
        return new SslConfiguration(protocol, keyStoreConfig, trustStoreConfig);
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.keyStoreConfig == null ? 0 : this.keyStoreConfig.hashCode());
        result = 31 * result + (this.protocol == null ? 0 : this.protocol.hashCode());
        result = 31 * result + (this.sslContext == null ? 0 : this.sslContext.hashCode());
        result = 31 * result + (this.trustStoreConfig == null ? 0 : this.trustStoreConfig.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        SslConfiguration other = (SslConfiguration)obj;
        if (this.keyStoreConfig == null ? other.keyStoreConfig != null : !this.keyStoreConfig.equals(other.keyStoreConfig)) {
            return false;
        }
        if (this.protocol == null ? other.protocol != null : !this.protocol.equals(other.protocol)) {
            return false;
        }
        if (this.sslContext == null ? other.sslContext != null : !this.sslContext.equals(other.sslContext)) {
            return false;
        }
        return !(this.trustStoreConfig == null ? other.trustStoreConfig != null : !this.trustStoreConfig.equals(other.trustStoreConfig));
    }
}

