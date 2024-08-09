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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class AbstractKeyStoreConfiguration
extends StoreConfiguration<KeyStore> {
    private final KeyStore keyStore;
    private final String keyStoreType;

    public AbstractKeyStoreConfiguration(String string, String string2, String string3) throws StoreConfigurationException {
        super(string, string2);
        this.keyStoreType = string3 == null ? "JKS" : string3;
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
            try (FileInputStream fileInputStream = new FileInputStream(this.getLocation());){
                KeyStore keyStore = KeyStore.getInstance(this.keyStoreType);
                keyStore.load(fileInputStream, this.getPasswordAsCharArray());
                LOGGER.debug("Keystore successfully loaded with params(location={})", (Object)this.getLocation());
                KeyStore keyStore2 = keyStore;
                return keyStore2;
            }
        } catch (CertificateException certificateException) {
            LOGGER.error("No Provider supports a KeyStoreSpi implementation for the specified type" + this.keyStoreType, (Throwable)certificateException);
            throw new StoreConfigurationException(certificateException);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            LOGGER.error("The algorithm used to check the integrity of the keystore cannot be found", (Throwable)noSuchAlgorithmException);
            throw new StoreConfigurationException(noSuchAlgorithmException);
        } catch (KeyStoreException keyStoreException) {
            LOGGER.error(keyStoreException);
            throw new StoreConfigurationException(keyStoreException);
        } catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("The keystore file(" + this.getLocation() + ") is not found", (Throwable)fileNotFoundException);
            throw new StoreConfigurationException(fileNotFoundException);
        } catch (IOException iOException) {
            LOGGER.error("Something is wrong with the format of the keystore or the given password", (Throwable)iOException);
            throw new StoreConfigurationException(iOException);
        }
    }

    public KeyStore getKeyStore() {
        return this.keyStore;
    }

    @Override
    public int hashCode() {
        int n = 31;
        int n2 = super.hashCode();
        n2 = 31 * n2 + (this.keyStore == null ? 0 : this.keyStore.hashCode());
        n2 = 31 * n2 + (this.keyStoreType == null ? 0 : this.keyStoreType.hashCode());
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
        AbstractKeyStoreConfiguration abstractKeyStoreConfiguration = (AbstractKeyStoreConfiguration)object;
        if (this.keyStore == null ? abstractKeyStoreConfiguration.keyStore != null : !this.keyStore.equals(abstractKeyStoreConfiguration.keyStore)) {
            return true;
        }
        return this.keyStoreType == null ? abstractKeyStoreConfiguration.keyStoreType != null : !this.keyStoreType.equals(abstractKeyStoreConfiguration.keyStoreType);
    }

    @Override
    protected Object load() throws StoreConfigurationException {
        return this.load();
    }
}

