/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.net.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import org.apache.logging.log4j.core.net.ssl.StoreConfiguration;
import org.apache.logging.log4j.core.net.ssl.StoreConfigurationException;

public class AbstractKeyStoreConfiguration
extends StoreConfiguration<KeyStore> {
    private final KeyStore keyStore;
    private final String keyStoreType;

    public AbstractKeyStoreConfiguration(String location, String password, String keyStoreType) throws StoreConfigurationException {
        super(location, password);
        this.keyStoreType = keyStoreType == null ? "JKS" : keyStoreType;
        this.keyStore = this.load();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected KeyStore load() throws StoreConfigurationException {
        LOGGER.debug("Loading keystore from file with params(location={})", (Object)this.getLocation());
        try {
            if (this.getLocation() == null) {
                throw new IOException("The location is null");
            }
            try (FileInputStream fin = new FileInputStream(this.getLocation());){
                KeyStore ks = KeyStore.getInstance(this.keyStoreType);
                ks.load(fin, this.getPasswordAsCharArray());
                LOGGER.debug("Keystore successfully loaded with params(location={})", (Object)this.getLocation());
                KeyStore keyStore = ks;
                return keyStore;
            }
        } catch (CertificateException e) {
            LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type" + this.keyStoreType, (Throwable)e);
            throw new StoreConfigurationException(e);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found", (Throwable)e);
            throw new StoreConfigurationException(e);
        } catch (KeyStoreException e) {
            LOGGER.error(e);
            throw new StoreConfigurationException(e);
        } catch (FileNotFoundException e) {
            LOGGER.error("The keystore file(" + this.getLocation() + ") is not found", (Throwable)e);
            throw new StoreConfigurationException(e);
        } catch (IOException e) {
            LOGGER.error("Something is wrong with the format of the keystore or the given password", (Throwable)e);
            throw new StoreConfigurationException(e);
        }
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = 31 * result + (this.keyStore == null ? 0 : this.keyStore.hashCode());
        result = 31 * result + (this.keyStoreType == null ? 0 : this.keyStoreType.hashCode());
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
        AbstractKeyStoreConfiguration other = (AbstractKeyStoreConfiguration)obj;
        if (this.keyStore == null ? other.keyStore != null : !this.keyStore.equals(other.keyStore)) {
            return false;
        }
        return !(this.keyStoreType == null ? other.keyStoreType != null : !this.keyStoreType.equals(other.keyStoreType));
    }
}

