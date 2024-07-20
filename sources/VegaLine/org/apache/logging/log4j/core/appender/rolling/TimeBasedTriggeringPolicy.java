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

    private TimeBasedTriggeringPolicy(int interval, boolean modulate) {
        this.interval = interval;
        this.modulate = modulate;
    }

    public int getInterval() {
        return this.interval;
    }

    public long getNextRolloverMillis() {
        return this.nextRolloverMillis;
    }

    @Override
    public void initialize(RollingFileManager aManager) {
        this.manager = aManager;
        aManager.getPatternProcessor().getNextTime(aManager.getFileTime(), this.interval, this.modulate);
        this.nextRolloverMillis = aManager.getPatternProcessor().getNextTime(aManager.getFileTime(), this.interval, this.modulate);
    }

    @Override
    public boolean isTriggeringEvent(LogEvent event) {
        if (this.manager.getFileSize() == 0L) {
            return false;
        }
        long nowMillis = event.getTimeMillis();
        if (nowMillis >= this.nextRolloverMillis) {
            this.nextRolloverMillis = this.manager.getPatternProcessor().getNextTime(nowMillis, this.interval, this.modulate);
            return true;
        }
        return false;
    }

    @PluginFactory
    public static TimeBasedTriggeringPolicy createPolicy(@PluginAttribute(value="interval") String interval, @PluginAttribute(value="modulate") String modulate) {
        int increment = Integers.parseInt(interval, 1);
        boolean mod = Boolean.parseBoolean(modulate);
        return new TimeBasedTriggeringPolicy(increment, mod);
    }

    public String toString() {
        return "TimeBasedTriggeringPolicy(nextRolloverMillis=" + this.nextRolloverMillis + ", interval=" + this.interval + ", modulate=" + this.modulate + ")";
    }
}

