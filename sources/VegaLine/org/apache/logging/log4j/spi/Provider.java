/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.spi;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.spi.ThreadContextMap;
import org.apache.logging.log4j.status.StatusLogger;

public class Provider {
    public static final String FACTORY_PRIORITY = "FactoryPriority";
    public static final String THREAD_CONTEXT_MAP = "ThreadContextMap";
    public static final String LOGGER_CONTEXT_FACTORY = "LoggerContextFactory";
    private static final Integer DEFAULT_PRIORITY = -1;
    private static final Logger LOGGER = StatusLogger.getLogger();
    private final Integer priority;
    private final String className;
    private final String threadContextMap;
    private final URL url;
    private final WeakReference<ClassLoader> classLoader;

    public Provider(Properties props, URL url, ClassLoader classLoader) {
        this.url = url;
        this.classLoader = new WeakReference<ClassLoader>(classLoader);
        String weight = props.getProperty(FACTORY_PRIORITY);
        this.priority = weight == null ? DEFAULT_PRIORITY : Integer.valueOf(weight);
        this.className = props.getProperty(LOGGER_CONTEXT_FACTORY);
        this.threadContextMap = props.getProperty(THREAD_CONTEXT_MAP);
    }

    public Integer getPriority() {
        return this.priority;
    }

    public String getClassName() {
        return this.className;
    }

    public Class<? extends LoggerContextFactory> loadLoggerContextFactory() {
        if (this.className == null) {
            return null;
        }
        ClassLoader loader = (ClassLoader)this.classLoader.get();
        if (loader == null) {
            return null;
        }
        try {
            Class<?> clazz = loader.loadClass(this.className);
            if (LoggerContextFactory.class.isAssignableFrom(clazz)) {
                return clazz.asSubclass(LoggerContextFactory.class);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", (Object)this.className, (Object)this.url.toString(), (Object)e);
        }
        return null;
    }

    public String getThreadContextMap() {
        return this.threadContextMap;
    }

    public Class<? extends ThreadContextMap> loadThreadContextMap() {
        if (this.threadContextMap == null) {
            return null;
        }
        ClassLoader loader = (ClassLoader)this.classLoader.get();
        if (loader == null) {
            return null;
        }
        try {
            Class<?> clazz = loader.loadClass(this.threadContextMap);
            if (ThreadContextMap.class.isAssignableFrom(clazz)) {
                return clazz.asSubclass(ThreadContextMap.class);
            }
        } catch (Exception e) {
            LOGGER.error("Unable to create class {} specified in {}", (Object)this.threadContextMap, (Object)this.url.toString(), (Object)e);
        }
        return null;
    }

    public URL getUrl() {
        return this.url;
    }

    public String toString() {
        String result = "Provider[";
        if (!DEFAULT_PRIORITY.equals(this.priority)) {
            result = result + "priority=" + this.priority + ", ";
        }
        if (this.threadContextMap != null) {
            result = result + "threadContextMap=" + this.threadContextMap + ", ";
        }
        if (this.className != null) {
            result = result + "className=" + this.className + ", ";
        }
        result = result + "url=" + this.url;
        ClassLoader loader = (ClassLoader)this.classLoader.get();
        result = loader == null ? result + ", classLoader=null(not reachable)" : result + ", classLoader=" + loader;
        result = result + "]";
        return result;
    }
}

