/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl.util;

import io.netty.handler.ssl.util.X509TrustManagerWrapper;
import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.internal.PlatformDependent;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;

public abstract class SimpleTrustManagerFactory
extends TrustManagerFactory {
    private static final Provider PROVIDER = new Provider("", 0.0, ""){
        private static final long serialVersionUID = -2680540247105807895L;
    };
    private static final FastThreadLocal<SimpleTrustManagerFactorySpi> CURRENT_SPI = new FastThreadLocal<SimpleTrustManagerFactorySpi>(){

        @Override
        protected SimpleTrustManagerFactorySpi initialValue() {
            return new SimpleTrustManagerFactorySpi();
        }

        @Override
        protected Object initialValue() throws Exception {
            return this.initialValue();
        }
    };

    protected SimpleTrustManagerFactory() {
        this("");
    }

    protected SimpleTrustManagerFactory(String string) {
        super(CURRENT_SPI.get(), PROVIDER, string);
        CURRENT_SPI.get().init(this);
        CURRENT_SPI.remove();
        if (string == null) {
            throw new NullPointerException("name");
        }
    }

    protected abstract void engineInit(KeyStore var1) throws Exception;

    protected abstract void engineInit(ManagerFactoryParameters var1) throws Exception;

    protected abstract TrustManager[] engineGetTrustManagers();

    static final class SimpleTrustManagerFactorySpi
    extends TrustManagerFactorySpi {
        private SimpleTrustManagerFactory parent;
        private volatile TrustManager[] trustManagers;

        SimpleTrustManagerFactorySpi() {
        }

        void init(SimpleTrustManagerFactory simpleTrustManagerFactory) {
            this.parent = simpleTrustManagerFactory;
        }

        @Override
        protected void engineInit(KeyStore keyStore) throws KeyStoreException {
            try {
                this.parent.engineInit(keyStore);
            } catch (KeyStoreException keyStoreException) {
                throw keyStoreException;
            } catch (Exception exception) {
                throw new KeyStoreException(exception);
            }
        }

        @Override
        protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
            try {
                this.parent.engineInit(managerFactoryParameters);
            } catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                throw invalidAlgorithmParameterException;
            } catch (Exception exception) {
                throw new InvalidAlgorithmParameterException(exception);
            }
        }

        @Override
        protected TrustManager[] engineGetTrustManagers() {
            TrustManager[] trustManagerArray = this.trustManagers;
            if (trustManagerArray == null) {
                trustManagerArray = this.parent.engineGetTrustManagers();
                if (PlatformDependent.javaVersion() >= 7) {
                    for (int i = 0; i < trustManagerArray.length; ++i) {
                        TrustManager trustManager = trustManagerArray[i];
                        if (!(trustManager instanceof X509TrustManager) || trustManager instanceof X509ExtendedTrustManager) continue;
                        trustManagerArray[i] = new X509TrustManagerWrapper((X509TrustManager)trustManager);
                    }
                }
                this.trustManagers = trustManagerArray;
            }
            return (TrustManager[])trustManagerArray.clone();
        }
    }
}

