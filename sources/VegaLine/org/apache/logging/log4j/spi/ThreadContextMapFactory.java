/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.CopyOnWriteSortedArrayThreadContextMap;
import org.apache.logging.log4j.spi.DefaultThreadContextMap;
import org.apache.logging.log4j.spi.GarbageFreeSortedArrayThreadContextMap;
import org.apache.logging.log4j.spi.Provider;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.Constants;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.apache.logging.log4j.util.ProviderUtil;

public final class ThreadContextMapFactory {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String THREAD_CONTEXT_KEY = "log4j2.threadContextMap";
    private static final String GC_FREE_THREAD_CONTEXT_KEY = "log4j2.garbagefree.threadContextMap";

    private ThreadContextMapFactory() {
    }

    public static ThreadContextMap createThreadContextMap() {
        PropertiesUtil managerProps = PropertiesUtil.getProperties();
        String threadContextMapName = managerProps.getStringProperty(THREAD_CONTEXT_KEY);
        ClassLoader cl = ProviderUtil.findClassLoader();
        ThreadContextMap result = null;
        if (threadContextMapName != null) {
            try {
                Class<?> clazz = cl.loadClass(threadContextMapName);
                if (ThreadContextMap.class.isAssignableFrom(clazz)) {
                    result = (ThreadContextMap)clazz.newInstance();
                }
            } catch (ClassNotFoundException cnfe) {
                LOGGER.error("Unable to locate configured ThreadContextMap {}", (Object)threadContextMapName);
            } catch (Exception ex) {
                LOGGER.error("Unable to create configured ThreadContextMap {}", (Object)threadContextMapName, (Object)ex);
            }
        }
        if (result == null && ProviderUtil.hasProviders() && LogManager.getFactory() != null) {
            String factoryClassName = LogManager.getFactory().getClass().getName();
            for (Provider provider : ProviderUtil.getProviders()) {
                Class<? extends ThreadContextMap> clazz;
                if (!factoryClassName.equals(provider.getClassName()) || (clazz = provider.loadThreadContextMap()) == null) continue;
                try {
                    result = clazz.newInstance();
                    break;
                } catch (Exception e) {
                    LOGGER.error("Unable to locate or load configured ThreadContextMap {}", (Object)provider.getThreadContextMap(), (Object)e);
                    result = ThreadContextMapFactory.createDefaultThreadContextMap();
                }
            }
        }
        if (result == null) {
            result = ThreadContextMapFactory.createDefaultThreadContextMap();
        }
        return result;
    }

    private static ThreadContextMap createDefaultThreadContextMap() {
        if (Constants.ENABLE_THREADLOCALS) {
            if (PropertiesUtil.getProperties().getBooleanProperty(GC_FREE_THREAD_CONTEXT_KEY)) {
                return new GarbageFreeSortedArrayThreadContextMap();
            }
            return new CopyOnWriteSortedArrayThreadContextMap();
        }
        return new DefaultThreadContextMap(true);
    }
}

