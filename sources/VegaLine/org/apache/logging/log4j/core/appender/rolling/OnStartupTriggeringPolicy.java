/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.lang.reflect.Method;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.AbstractTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="OnStartupTriggeringPolicy", category="Core", printObject=true)
public class OnStartupTriggeringPolicy
extends AbstractTriggeringPolicy {
    private static final long JVM_START_TIME = OnStartupTriggeringPolicy.initStartTime();
    private final long minSize;

    private OnStartupTriggeringPolicy(long minSize) {
        this.minSize = minSize;
    }

    private static long initStartTime() {
        try {
            Class<?> factoryClass = Loader.loadSystemClass("java.lang.management.ManagementFactory");
            Method getRuntimeMXBean = factoryClass.getMethod("getRuntimeMXBean", new Class[0]);
            Object runtimeMXBean = getRuntimeMXBean.invoke(null, new Object[0]);
            Class<?> runtimeMXBeanClass = Loader.loadSystemClass("java.lang.management.RuntimeMXBean");
            Method getStartTime = runtimeMXBeanClass.getMethod("getStartTime", new Class[0]);
            Long result = (Long)getStartTime.invoke(runtimeMXBean, new Object[0]);
            return result;
        } catch (Throwable t) {
            StatusLogger.getLogger().error("Unable to call ManagementFactory.getRuntimeMXBean().getStartTime(), using system time for OnStartupTriggeringPolicy", t);
            return System.currentTimeMillis();
        }
    }

    @Override
    public void initialize(RollingFileManager manager) {
        if (manager.getFileTime() < JVM_START_TIME && manager.getFileSize() >= this.minSize) {
            if (this.minSize == 0L) {
                manager.setRenameEmptyFiles(true);
            }
            manager.skipFooter(true);
            manager.rollover();
            manager.skipFooter(false);
        }
    }

    @Override
    public boolean isTriggeringEvent(LogEvent event) {
        return false;
    }

    public String toString() {
        return "OnStartupTriggeringPolicy";
    }

    @PluginFactory
    public static OnStartupTriggeringPolicy createPolicy(@PluginAttribute(value="minSize", defaultLong=1L) long minSize) {
        return new OnStartupTriggeringPolicy(minSize);
    }
}

