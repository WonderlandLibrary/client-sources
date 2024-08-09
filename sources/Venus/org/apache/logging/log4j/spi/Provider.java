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

    public Provider(Properties properties, URL uRL, ClassLoader classLoader) {
        this.url = uRL;
        this.classLoader = new WeakReference<ClassLoader>(classLoader);
        String string = properties.getProperty(FACTORY_PRIORITY);
        this.priority = string == null ? DEFAULT_PRIORITY : Integer.valueOf(string);
        this.className = properties.getProperty(LOGGER_CONTEXT_FACTORY);
        this.threadContextMap = properties.getProperty(THREAD_CONTEXT_MAP);
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
        ClassLoader classLoader = (ClassLoader)this.classLoader.get();
        if (classLoader == null) {
            return null;
        }
        try {
            Class<?> clazz = classLoader.loadClass(this.className);
            if (LoggerContextFactory.class.isAssignableFrom(clazz)) {
                return clazz.asSubclass(LoggerContextFactory.class);
            }
        } catch (Exception exception) {
            LOGGER.error("Unable to create class {} specified in {}", (Object)this.className, (Object)this.url.toString(), (Object)exception);
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
        ClassLoader classLoader = (ClassLoader)this.classLoader.get();
        if (classLoader == null) {
            return null;
        }
        try {
            Class<?> clazz = classLoader.loadClass(this.threadContextMap);
            if (ThreadContextMap.class.isAssignableFrom(clazz)) {
                return clazz.asSubclass(ThreadContextMap.class);
            }
        } catch (Exception exception) {
            LOGGER.error("Unable to create class {} specified in {}", (Object)this.threadContextMap, (Object)this.url.toString(), (Object)exception);
        }
        return null;
    }

    public URL getUrl() {
        return this.url;
    }

    public String toString() {
        String string = "Provider[";
        if (!DEFAULT_PRIORITY.equals(this.priority)) {
            string = string + "priority=" + this.priority + ", ";
        }
        if (this.threadContextMap != null) {
            string = string + "threadContextMap=" + this.threadContextMap + ", ";
        }
        if (this.className != null) {
            string = string + "className=" + this.className + ", ";
        }
        string = string + "url=" + this.url;
        ClassLoader classLoader = (ClassLoader)this.classLoader.get();
        string = classLoader == null ? string + ", classLoader=null(not reachable)" : string + ", classLoader=" + classLoader;
        string = string + "]";
        return string;
    }
}

