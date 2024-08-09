/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.AbstractTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.Integers;

@Plugin(name="TimeBasedTriggeringPolicy", category="Core", printObject=true)
public final class TimeBasedTriggeringPolicy
extends AbstractTriggeringPolicy {
    private long nextRolloverMillis;
    private final int interval;
    private final boolean modulate;
    private RollingFileManager manager;

    private TimeBasedTriggeringPolicy(int n, boolean bl) {
        this.interval = n;
        this.modulate = bl;
    }

    public int getInterval() {
        return this.interval;
    }

    public long getNextRolloverMillis() {
        return this.nextRolloverMillis;
    }

    @Override
    public void initialize(RollingFileManager rollingFileManager) {
        this.manager = rollingFileManager;
        rollingFileManager.getPatternProcessor().getNextTime(rollingFileManager.getFileTime(), this.interval, this.modulate);
        this.nextRolloverMillis = rollingFileManager.getPatternProcessor().getNextTime(rollingFileManager.getFileTime(), this.interval, this.modulate);
    }

    @Override
    public boolean isTriggeringEvent(LogEvent logEvent) {
        if (this.manager.getFileSize() == 0L) {
            return true;
        }
        long l = logEvent.getTimeMillis();
        if (l >= this.nextRolloverMillis) {
            this.nextRolloverMillis = this.manager.getPatternProcessor().getNextTime(l, this.interval, this.modulate);
            return false;
        }
        return true;
    }

    @PluginFactory
    public static TimeBasedTriggeringPolicy createPolicy(@PluginAttribute(value="interval") String string, @PluginAttribute(value="modulate") String string2) {
        int n = Integers.parseInt(string, 1);
        boolean bl = Boolean.parseBoolean(string2);
        return new TimeBasedTriggeringPolicy(n, bl);
    }

    public String toString() {
        return "TimeBasedTriggeringPolicy(nextRolloverMillis=" + this.nextRolloverMillis + ", interval=" + this.interval + ", modulate=" + this.modulate + ")";
    }
}

