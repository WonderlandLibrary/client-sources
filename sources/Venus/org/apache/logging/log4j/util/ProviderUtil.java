/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.util;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.Provider;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ProviderUtil {
    protected static final String PROVIDER_RESOURCE = "META-INF/log4j-provider.properties";
    protected static final Collection<Provider> PROVIDERS = new HashSet<Provider>();
    protected static final Lock STARTUP_LOCK = new ReentrantLock();
    private static final String API_VERSION = "Log4jAPIVersion";
    private static final String[] COMPATIBLE_API_VERSIONS = new String[]{"2.0.0", "2.1.0", "2.2.0", "2.3.0", "2.4.0", "2.5.0", "2.6.0"};
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static volatile ProviderUtil instance;

    private ProviderUtil() {
        for (LoaderUtil.UrlResource urlResource : LoaderUtil.findUrlResources(PROVIDER_RESOURCE)) {
            ProviderUtil.loadProvider(urlResource.getUrl(), urlResource.getClassLoader());
        }
    }

    protected static void loadProvider(URL uRL, ClassLoader classLoader) {
        try {
            Properties properties = PropertiesUtil.loadClose(uRL.openStream(), uRL);
            if (ProviderUtil.validVersion(properties.getProperty(API_VERSION))) {
                Provider provider = new Provider(properties, uRL, classLoader);
                PROVIDERS.add(provider);
                LOGGER.debug("Loaded Provider {}", (Object)provider);
            }
        } catch (IOException iOException) {
            LOGGER.error("Unable to open {}", (Object)uRL, (Object)iOException);
        }
    }

    @Deprecated
    protected static void loadProviders(Enumeration<URL> enumeration, ClassLoader classLoader) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                ProviderUtil.loadProvider(enumeration.nextElement(), classLoader);
            }
        }
    }

    public static Iterable<Provider> getProviders() {
        ProviderUtil.lazyInit();
        return PROVIDERS;
    }

    public static boolean hasProviders() {
        ProviderUtil.lazyInit();
        return !PROVIDERS.isEmpty();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected static void lazyInit() {
        if (instance == null) {
            try {
                STARTUP_LOCK.lockInterruptibly();
                try {
                    if (instance == null) {
                        instance = new ProviderUtil();
                    }
                } finally {
                    STARTUP_LOCK.unlock();
                }
            } catch (InterruptedException interruptedException) {
                LOGGER.fatal("Interrupted before Log4j Providers could be loaded.", (Throwable)interruptedException);
                Thread.currentThread().interrupt();
            }
        }
    }

    public static ClassLoader findClassLoader() {
        return LoaderUtil.getThreadContextClassLoader();
    }

    private static boolean validVersion(String string) {
        for (String string2 : COMPATIBLE_API_VERSIONS) {
            if (!string.startsWith(string2)) continue;
            return false;
        }
        return true;
    }
}

