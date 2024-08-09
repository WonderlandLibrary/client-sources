/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.TrustManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.net.ssl.AbstractKeyStoreConfiguration;
import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;

@Plugin(name="TrustStore", category="Core", printObject=true)
public class TrustStoreConfiguration
extends AbstractKeyStoreConfiguration {
    private final String trustManagerFactoryAlgorithm;

    public TrustStoreConfiguration(String string, String string2, String string3, String string4) throws StoreConfigurationException {
        super(string, string2, string3);
        this.trustManagerFactoryAlgorithm = string4 == null ? TrustManagerFactory.getDefaultAlgorithm() : string4;
    }

    @PluginFactory
    public static TrustStoreConfiguration createKeyStoreConfiguration(@PluginAttribute(value="location") String string, @PluginAttribute(value="password", sensitive=true) String string2, @PluginAttribute(value="type") String string3, @PluginAttribute(value="trustManagerFactoryAlgorithm") String string4) throws StoreConfigurationException {
        return new TrustStoreConfiguration(string, string2, string3, string4);
    }

    public TrustManagerFactory initTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(this.trustManagerFactoryAlgorithm);
        trustManagerFactory.init(this.getKeyStore());
        return trustManagerFactory;
    }

    @Override
    public int hashCode() {
        int n = 31;
        int n2 = super.hashCode();
        n2 = 31 * n2 + (this.trustManagerFactoryAlgorithm == null ? 0 : this.trustManagerFactoryAlgorithm.hashCode());
        return n2;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!super.equals(object)) {
            return true;
        }
        if (this.getClass() != object.getClass()) {
            return true;
        }
        TrustStoreConfiguration trustStoreConfiguration = (TrustStoreConfiguration)object;
        return this.trustManagerFactoryAlgorithm == null ? trustStoreConfiguration.trustManagerFactoryAlgorithm != null : !this.trustManagerFactoryAlgorithm.equals(trustStoreConfiguration.trustManagerFactoryAlgorithm);
    }
}

