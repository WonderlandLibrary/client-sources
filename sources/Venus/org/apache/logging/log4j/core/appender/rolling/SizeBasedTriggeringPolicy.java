/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.AbstractTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.FileSize;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="SizeBasedTriggeringPolicy", category="Core", printObject=true)
public class SizeBasedTriggeringPolicy
extends AbstractTriggeringPolicy {
    private static final long MAX_FILE_SIZE = 0xA00000L;
    private final long maxFileSize;
    private RollingFileManager manager;

    protected SizeBasedTriggeringPolicy() {
        this.maxFileSize = 0xA00000L;
    }

    protected SizeBasedTriggeringPolicy(long l) {
        this.maxFileSize = l;
    }

    public long getMaxFileSize() {
        return this.maxFileSize;
    }

    @Override
    public void initialize(RollingFileManager rollingFileManager) {
        this.manager = rollingFileManager;
    }

    @Override
    public boolean isTriggeringEvent(LogEvent logEvent) {
        boolean bl;
        boolean bl2 = bl = this.manager.getFileSize() > this.maxFileSize;
        if (bl) {
            this.manager.getPatternProcessor().updateTime();
        }
        return bl;
    }

    public String toString() {
        return "SizeBasedTriggeringPolicy(size=" + this.maxFileSize + ')';
    }

    @PluginFactory
    public static SizeBasedTriggeringPolicy createPolicy(@PluginAttribute(value="size") String string) {
        long l = string == null ? 0xA00000L : FileSize.parse(string, 0xA00000L);
        return new SizeBasedTriggeringPolicy(l);
    }
}

