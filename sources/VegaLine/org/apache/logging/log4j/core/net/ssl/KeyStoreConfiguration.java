/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import javax.net.ssl.KeyManagerFactory;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.net.ssl.AbstractKeyStoreConfiguration;
import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;

@Plugin(name="KeyStore", category="Core", printObject=true)
public class KeyStoreConfiguration
extends AbstractKeyStoreConfiguration {
    private final String keyManagerFactoryAlgorithm;

    public KeyStoreConfiguration(String location, String password, String keyStoreType, String keyManagerFactoryAlgorithm) throws StoreConfigurationException {
        super(location, password, keyStoreType);
        this.keyManagerFactoryAlgorithm = keyManagerFactoryAlgorithm == null ? KeyManagerFactory.getDefaultAlgorithm() : keyManagerFactoryAlgorithm;
    }

    @PluginFactory
    public static KeyStoreConfiguration createKeyStoreConfiguration(@PluginAttribute(value="location") String location, @PluginAttribute(value="password", sensitive=true) String password, @PluginAttribute(value="type") String keyStoreType, @PluginAttribute(value="keyManagerFactoryAlgorithm") String keyManagerFactoryAlgorithm) throws StoreConfigurationException {
        return new KeyStoreConfiguration(location, password, keyStoreType, keyManagerFactoryAlgorithm);
    }

    public KeyManagerFactory initKeyManagerFactory() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory kmFactory = KeyManagerFactory.getInstance(this.keyManagerFactoryAlgorithm);
        kmFactory.init(this.getKeyStore(), this.getPasswordAsCharArray());
        return kmFactory;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.keyManagerFactoryAlgorithm == null ? 0 : this.keyManagerFactoryAlgorithm.hashCode());
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
        KeyStoreConfiguration other = (KeyStoreConfiguration)obj;
        return !(this.keyManagerFactoryAlgorithm == null ? other.keyManagerFactoryAlgorithm != null : !this.keyManagerFactoryAlgorithm.equals(other.keyManagerFactoryAlgorithm));
    }
}

