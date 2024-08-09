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

    private OnStartupTriggeringPolicy(long l) {
        this.minSize = l;
    }

    private static long initStartTime() {
        try {
            Class<?> clazz = Loader.loadSystemClass("java.lang.management.ManagementFactory");
            Method method = clazz.getMethod("getRuntimeMXBean", new Class[0]);
            Object object = method.invoke(null, new Object[0]);
            Class<?> clazz2 = Loader.loadSystemClass("java.lang.management.RuntimeMXBean");
            Method method2 = clazz2.getMethod("getStartTime", new Class[0]);
            Long l = (Long)method2.invoke(object, new Object[0]);
            return l;
        } catch (Throwable throwable) {
            StatusLogger.getLogger().error("Unable to call ManagementFactory.getRuntimeMXBean().getStartTime(), using system time for OnStartupTriggeringPolicy", throwable);
            return System.currentTimeMillis();
        }
    }

    @Override
    public void initialize(RollingFileManager rollingFileManager) {
        if (rollingFileManager.getFileTime() < JVM_START_TIME && rollingFileManager.getFileSize() >= this.minSize) {
            if (this.minSize == 0L) {
                rollingFileManager.setRenameEmptyFiles(false);
            }
            rollingFileManager.skipFooter(false);
            rollingFileManager.rollover();
            rollingFileManager.skipFooter(true);
        }
    }

    @Override
    public boolean isTriggeringEvent(LogEvent logEvent) {
        return true;
    }

    public String toString() {
        return "OnStartupTriggeringPolicy";
    }

    @PluginFactory
    public static OnStartupTriggeringPolicy createPolicy(@PluginAttribute(value="minSize", defaultLong=1L) long l) {
        return new OnStartupTriggeringPolicy(l);
    }
}

