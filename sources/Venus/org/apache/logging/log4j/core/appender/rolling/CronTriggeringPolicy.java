/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rolling;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rolling.AbstractTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.RollingFileManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.config.CronScheduledFuture;
import org.apache.logging.log4j.core.config.Scheduled;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.CronExpression;

@Plugin(name="CronTriggeringPolicy", category="Core", printObject=true)
@Scheduled
public final class CronTriggeringPolicy
extends AbstractTriggeringPolicy {
    private static final String defaultSchedule = "0 0 0 * * ?";
    private RollingFileManager manager;
    private final CronExpression cronExpression;
    private final Configuration configuration;
    private final boolean checkOnStartup;
    private volatile Date lastRollDate;
    private CronScheduledFuture<?> future;

    private CronTriggeringPolicy(CronExpression cronExpression, boolean bl, Configuration configuration) {
        this.cronExpression = Objects.requireNonNull(cronExpression, "schedule");
        this.configuration = Objects.requireNonNull(configuration, "configuration");
        this.checkOnStartup = bl;
    }

    @Override
    public void initialize(RollingFileManager rollingFileManager) {
        ConfigurationScheduler configurationScheduler;
        this.manager = rollingFileManager;
        Date date = new Date();
        Date date2 = this.cronExpression.getPrevFireTime(new Date(this.manager.getFileTime()));
        Date date3 = this.cronExpression.getPrevFireTime(new Date());
        rollingFileManager.getPatternProcessor().setCurrentFileTime(date3.getTime());
        LOGGER.debug("LastRollForFile {}, LastRegularRole {}", (Object)date2, (Object)date3);
        rollingFileManager.getPatternProcessor().setPrevFileTime(date3.getTime());
        if (this.checkOnStartup && date2 != null && date3 != null && date2.before(date3)) {
            this.lastRollDate = date2;
            this.rollover();
        }
        if (!(configurationScheduler = this.configuration.getScheduler()).isExecutorServiceSet()) {
            configurationScheduler.incrementScheduledItems();
        }
        if (!configurationScheduler.isStarted()) {
            configurationScheduler.start();
        }
        this.lastRollDate = date3;
        this.future = configurationScheduler.scheduleWithCron(this.cronExpression, date, new CronTrigger(this, null));
        LOGGER.debug(configurationScheduler.toString());
    }

    @Override
    public boolean isTriggeringEvent(LogEvent logEvent) {
        return true;
    }

    public CronExpression getCronExpression() {
        return this.cronExpression;
    }

    @PluginFactory
    public static CronTriggeringPolicy createPolicy(@PluginConfiguration Configuration configuration, @PluginAttribute(value="evaluateOnStartup") String string, @PluginAttribute(value="schedule") String string2) {
        CronExpression cronExpression;
        boolean bl = Boolean.parseBoolean(string);
        if (string2 == null) {
            LOGGER.info("No schedule specified, defaulting to Daily");
            cronExpression = CronTriggeringPolicy.getSchedule(defaultSchedule);
        } else {
            cronExpression = CronTriggeringPolicy.getSchedule(string2);
            if (cronExpression == null) {
                LOGGER.error("Invalid expression specified. Defaulting to Daily");
                cronExpression = CronTriggeringPolicy.getSchedule(defaultSchedule);
            }
        }
        return new CronTriggeringPolicy(cronExpression, bl, configuration);
    }

    private static CronExpression getSchedule(String string) {
        try {
            return new CronExpression(string);
        } catch (ParseException parseException) {
            LOGGER.error("Invalid cron expression - " + string, (Throwable)parseException);
            return null;
        }
    }

    private void rollover() {
        this.manager.getPatternProcessor().setPrevFileTime(this.lastRollDate.getTime());
        Date date = this.cronExpression.getPrevFireTime(new Date());
        this.manager.getPatternProcessor().setCurrentFileTime(date.getTime());
        this.manager.rollover();
        if (this.future != null) {
            this.lastRollDate = this.future.getFireTime();
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = this.stop(this.future);
        this.setStopped();
        return bl;
    }

    public String toString() {
        return "CronTriggeringPolicy(schedule=" + this.cronExpression.getCronExpression() + ")";
    }

    static void access$100(CronTriggeringPolicy cronTriggeringPolicy) {
        cronTriggeringPolicy.rollover();
    }

    static class 1 {
    }

    private class CronTrigger
    implements Runnable {
        final CronTriggeringPolicy this$0;

        private CronTrigger(CronTriggeringPolicy cronTriggeringPolicy) {
            this.this$0 = cronTriggeringPolicy;
        }

        @Override
        public void run() {
            CronTriggeringPolicy.access$100(this.this$0);
        }

        CronTrigger(CronTriggeringPolicy cronTriggeringPolicy, 1 var2_2) {
            this(cronTriggeringPolicy);
        }
    }
}

