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

    public TrustStoreConfiguration(String location, String password, String keyStoreType, String trustManagerFactoryAlgorithm) throws StoreConfigurationException {
        super(location, password, keyStoreType);
        this.trustManagerFactoryAlgorithm = trustManagerFactoryAlgorithm == null ? TrustManagerFactory.getDefaultAlgorithm() : trustManagerFactoryAlgorithm;
    }

    @PluginFactory
    public static TrustStoreConfiguration createKeyStoreConfiguration(@PluginAttribute(value="location") String location, @PluginAttribute(value="password", sensitive=true) String password, @PluginAttribute(value="type") String keyStoreType, @PluginAttribute(value="trustManagerFactoryAlgorithm") String trustManagerFactoryAlgorithm) throws StoreConfigurationException {
        return new TrustStoreConfiguration(location, password, keyStoreType, trustManagerFactoryAlgorithm);
    }

    public TrustManagerFactory initTrustManagerFactory() throws NoSuchAlgorithmException, KeyStoreException {
        TrustManagerFactory tmFactory = TrustManagerFactory.getInstance(this.trustManagerFactoryAlgorithm);
        tmFactory.init(this.getKeyStore());
        return tmFactory;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.trustManagerFactoryAlgorithm == null ? 0 : this.trustManagerFactoryAlgorithm.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        TrustStoreConfiguration other = (TrustStoreConfiguration)obj;
        return !(this.trustManagerFactoryAlgorithm == null ? other.trustManagerFactoryAlgorithm != null : !this.trustManagerFactoryAlgorithm.equals(other.trustManagerFactoryAlgorithm));
    }
}

