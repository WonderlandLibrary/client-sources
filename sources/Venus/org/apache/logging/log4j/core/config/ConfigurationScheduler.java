/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.config.CronScheduledFuture;
import org.apache.logging.log4j.core.util.CronExpression;
import org.apache.logging.log4j.core.util.Log4jThreadFactory;
import org.apache.logging.log4j.status.StatusLogger;

public class ConfigurationScheduler
extends AbstractLifeCycle {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final String SIMPLE_NAME = "Log4j2 " + ConfigurationScheduler.class.getSimpleName();
    private static final int MAX_SCHEDULED_ITEMS = 5;
    private ScheduledExecutorService executorService;
    private int scheduledItems = 0;

    @Override
    public void start() {
        super.start();
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        if (this.isExecutorServiceSet()) {
            LOGGER.debug("{} shutting down threads in {}", (Object)SIMPLE_NAME, (Object)this.getExecutorService());
            this.executorService.shutdown();
            try {
                this.executorService.awaitTermination(l, timeUnit);
            } catch (InterruptedException interruptedException) {
                this.executorService.shutdownNow();
                try {
                    this.executorService.awaitTermination(l, timeUnit);
                } catch (InterruptedException interruptedException2) {
                    LOGGER.warn("ConfigurationScheduler stopped but some scheduled services may not have completed.");
                }
                Thread.currentThread().interrupt();
            }
        }
        this.setStopped();
        return false;
    }

    public boolean isExecutorServiceSet() {
        return this.executorService != null;
    }

    public void incrementScheduledItems() {
        if (this.isExecutorServiceSet()) {
            LOGGER.error("{} attempted to increment scheduled items after start", (Object)SIMPLE_NAME);
        } else {
            ++this.scheduledItems;
        }
    }

    public void decrementScheduledItems() {
        if (!this.isStarted() && this.scheduledItems > 0) {
            --this.scheduledItems;
        }
    }

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long l, TimeUnit timeUnit) {
        return this.getExecutorService().schedule(callable, l, timeUnit);
    }

    public ScheduledFuture<?> schedule(Runnable runnable, long l, TimeUnit timeUnit) {
        return this.getExecutorService().schedule(runnable, l, timeUnit);
    }

    public CronScheduledFuture<?> scheduleWithCron(CronExpression cronExpression, Runnable runnable) {
        return this.scheduleWithCron(cronExpression, new Date(), runnable);
    }

    public CronScheduledFuture<?> scheduleWithCron(CronExpression cronExpression, Date date, Runnable runnable) {
        Date date2 = cronExpression.getNextValidTimeAfter(date == null ? new Date() : date);
        CronRunnable cronRunnable = new CronRunnable(this, runnable, cronExpression);
        ScheduledFuture<?> scheduledFuture = this.schedule(cronRunnable, this.nextFireInterval(date2), TimeUnit.MILLISECONDS);
        CronScheduledFuture cronScheduledFuture = new CronScheduledFuture(scheduledFuture, date2);
        cronRunnable.setScheduledFuture(cronScheduledFuture);
        LOGGER.debug("Scheduled cron expression {} to fire at {}", (Object)cronExpression.getCronExpression(), (Object)date2);
        return cronScheduledFuture;
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.getExecutorService().scheduleAtFixedRate(runnable, l, l2, timeUnit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        return this.getExecutorService().scheduleWithFixedDelay(runnable, l, l2, timeUnit);
    }

    public long nextFireInterval(Date date) {
        return date.getTime() - new Date().getTime();
    }

    private ScheduledExecutorService getExecutorService() {
        if (this.executorService == null) {
            if (this.scheduledItems > 0) {
                LOGGER.debug("{} starting {} threads", (Object)SIMPLE_NAME, (Object)this.scheduledItems);
                this.scheduledItems = Math.min(this.scheduledItems, 5);
                ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(this.scheduledItems, Log4jThreadFactory.createDaemonThreadFactory("Scheduled"));
                scheduledThreadPoolExecutor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
                scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(true);
                this.executorService = scheduledThreadPoolExecutor;
            } else {
                LOGGER.debug("{}: No scheduled items", (Object)SIMPLE_NAME);
            }
        }
        return this.executorService;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ConfigurationScheduler {");
        BlockingQueue<Runnable> blockingQueue = ((ScheduledThreadPoolExecutor)this.executorService).getQueue();
        boolean bl = true;
        for (Runnable runnable : blockingQueue) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(runnable.toString());
            bl = false;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static Logger access$000() {
        return LOGGER;
    }

    static String access$100() {
        return SIMPLE_NAME;
    }

    public class CronRunnable
    implements Runnable {
        private final CronExpression cronExpression;
        private final Runnable runnable;
        private CronScheduledFuture<?> scheduledFuture;
        final ConfigurationScheduler this$0;

        public CronRunnable(ConfigurationScheduler configurationScheduler, Runnable runnable, CronExpression cronExpression) {
            this.this$0 = configurationScheduler;
            this.cronExpression = cronExpression;
            this.runnable = runnable;
        }

        public void setScheduledFuture(CronScheduledFuture<?> cronScheduledFuture) {
            this.scheduledFuture = cronScheduledFuture;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                long l = this.scheduledFuture.getFireTime().getTime() - System.currentTimeMillis();
                if (l > 0L) {
                    ConfigurationScheduler.access$000().debug("Cron thread woke up {} millis early. Sleeping", (Object)l);
                    try {
                        Thread.sleep(l);
                    } catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                this.runnable.run();
            } catch (Throwable throwable) {
                ConfigurationScheduler.access$000().error("{} caught error running command", (Object)ConfigurationScheduler.access$100(), (Object)throwable);
            } finally {
                Date date = this.cronExpression.getNextValidTimeAfter(new Date());
                ScheduledFuture<?> scheduledFuture = this.this$0.schedule(this, this.this$0.nextFireInterval(date), TimeUnit.MILLISECONDS);
                ConfigurationScheduler.access$000().debug("Cron expression {} scheduled to fire again at {}", (Object)this.cronExpression.getCronExpression(), (Object)date);
                this.scheduledFuture.reset(scheduledFuture, date);
            }
        }

        public String toString() {
            return "CronRunnable{" + this.cronExpression.getCronExpression() + " - " + this.scheduledFuture.getFireTime();
        }
    }
}

