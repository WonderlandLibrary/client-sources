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

    public KeyStoreConfiguration(String string, String string2, String string3, String string4) throws StoreConfigurationException {
        super(string, string2, string3);
        this.keyManagerFactoryAlgorithm = string4 == null ? KeyManagerFactory.getDefaultAlgorithm() : string4;
    }

    @PluginFactory
    public static KeyStoreConfiguration createKeyStoreConfiguration(@PluginAttribute(value="location") String string, @PluginAttribute(value="password", sensitive=true) String string2, @PluginAttribute(value="type") String string3, @PluginAttribute(value="keyManagerFactoryAlgorithm") String string4) throws StoreConfigurationException {
        return new KeyStoreConfiguration(string, string2, string3, string4);
    }

    public KeyManagerFactory initKeyManagerFactory() throws NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(this.keyManagerFactoryAlgorithm);
        keyManagerFactory.init(this.getKeyStore(), this.getPasswordAsCharArray());
        return keyManagerFactory;
    }

    @Override
    public int hashCode() {
        int n = 31;
        int n2 = super.hashCode();
        n2 = 31 * n2 + (this.keyManagerFactoryAlgorithm == null ? 0 : this.keyManagerFactoryAlgorithm.hashCode());
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
        KeyStoreConfiguration keyStoreConfiguration = (KeyStoreConfiguration)object;
        return this.keyManagerFactoryAlgorithm == null ? keyStoreConfiguration.keyManagerFactoryAlgorithm != null : !this.keyManagerFactoryAlgorithm.equals(keyStoreConfiguration.keyManagerFactoryAlgorithm);
    }
}

