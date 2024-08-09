/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.routing;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.routing.PurgePolicy;
import org.apache.logging.log4j.core.appender.routing.RoutingAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name="IdlePurgePolicy", category="Core", printObject=true)
@Scheduled
public class IdlePurgePolicy
extends AbstractLifeCycle
implements PurgePolicy,
Runnable {
    private final long timeToLive;
    private final long checkInterval;
    private final ConcurrentMap<String, Long> appendersUsage = new ConcurrentHashMap<String, Long>();
    private RoutingAppender routingAppender;
    private final ConfigurationScheduler scheduler;
    private volatile ScheduledFuture<?> future;

    public IdlePurgePolicy(long l, long l2, ConfigurationScheduler configurationScheduler) {
        this.timeToLive = l;
        this.checkInterval = l2;
        this.scheduler = configurationScheduler;
    }

    @Override
    public void initialize(RoutingAppender routingAppender) {
        this.routingAppender = routingAppender;
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = this.stop(this.future);
        this.setStopped();
        return bl;
    }

    @Override
    public void purge() {
        long l = System.currentTimeMillis() - this.timeToLive;
        for (Map.Entry entry : this.appendersUsage.entrySet()) {
            if ((Long)entry.getValue() >= l) continue;
            LOGGER.debug("Removing appender " + (String)entry.getKey());
            if (!this.appendersUsage.remove(entry.getKey(), entry.getValue())) continue;
            this.routingAppender.deleteAppender((String)entry.getKey());
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void update(String string, LogEvent logEvent) {
        long l = System.currentTimeMillis();
        this.appendersUsage.put(string, l);
        if (this.future == null) {
            IdlePurgePolicy idlePurgePolicy = this;
            synchronized (idlePurgePolicy) {
                if (this.future == null) {
                    this.scheduleNext();
                }
            }
        }
    }

    @Override
    public void run() {
        this.purge();
        this.scheduleNext();
    }

    private void scheduleNext() {
        long l = Long.MAX_VALUE;
        for (Map.Entry entry : this.appendersUsage.entrySet()) {
            if ((Long)entry.getValue() >= l) continue;
            l = (Long)entry.getValue();
        }
        if (l < Long.MAX_VALUE) {
            long l2 = this.timeToLive - (System.currentTimeMillis() - l);
            this.future = this.scheduler.schedule(this, l2, TimeUnit.MILLISECONDS);
        } else {
            this.future = this.scheduler.schedule(this, this.checkInterval, TimeUnit.MILLISECONDS);
        }
    }

    @PluginFactory
    public static PurgePolicy createPurgePolicy(@PluginAttribute(value="timeToLive") String string, @PluginAttribute(value="checkInterval") String string2, @PluginAttribute(value="timeUnit") String string3, @PluginConfiguration Configuration configuration) {
        long l;
        TimeUnit timeUnit;
        if (string == null) {
            LOGGER.error("A timeToLive value is required");
            return null;
        }
        if (string3 == null) {
            timeUnit = TimeUnit.MINUTES;
        } else {
            try {
                timeUnit = TimeUnit.valueOf(string3.toUpperCase());
            } catch (Exception exception) {
                LOGGER.error("Invalid timeUnit value {}. timeUnit set to MINUTES", (Object)string3, (Object)exception);
                timeUnit = TimeUnit.MINUTES;
            }
        }
        long l2 = timeUnit.toMillis(Long.parseLong(string));
        if (l2 < 0L) {
            LOGGER.error("timeToLive must be positive. timeToLive set to 0");
            l2 = 0L;
        }
        if (string2 == null) {
            l = l2;
        } else {
            l = timeUnit.toMillis(Long.parseLong(string2));
            if (l < 0L) {
                LOGGER.error("checkInterval must be positive. checkInterval set equal to timeToLive = {}", (Object)l2);
                l = l2;
            }
        }
        return new IdlePurgePolicy(l2, l, configuration.getScheduler());
    }

    public String toString() {
        return "timeToLive=" + this.timeToLive;
    }
}

