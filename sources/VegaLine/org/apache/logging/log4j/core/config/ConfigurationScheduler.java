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
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        if (this.isExecutorServiceSet()) {
            LOGGER.debug("{} shutting down threads in {}", (Object)SIMPLE_NAME, (Object)this.getExecutorService());
            this.executorService.shutdown();
            try {
                this.executorService.awaitTermination(timeout, timeUnit);
            } catch (InterruptedException ie) {
                this.executorService.shutdownNow();
                try {
                    this.executorService.awaitTermination(timeout, timeUnit);
                } catch (InterruptedException inner) {
                    LOGGER.warn("ConfigurationScheduler stopped but some scheduled services may not have completed.");
                }
                Thread.currentThread().interrupt();
            }
        }
        this.setStopped();
        return true;
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

    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return this.getExecutorService().schedule(callable, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return this.getExecutorService().schedule(command, delay, unit);
    }

    public CronScheduledFuture<?> scheduleWithCron(CronExpression cronExpression, Runnable command) {
        return this.scheduleWithCron(cronExpression, new Date(), command);
    }

    public CronScheduledFuture<?> scheduleWithCron(CronExpression cronExpression, Date startDate, Runnable command) {
        Date fireDate = cronExpression.getNextValidTimeAfter(startDate == null ? new Date() : startDate);
        CronRunnable runnable = new CronRunnable(command, cronExpression);
        ScheduledFuture<?> future = this.schedule(runnable, this.nextFireInterval(fireDate), TimeUnit.MILLISECONDS);
        CronScheduledFuture cronScheduledFuture = new CronScheduledFuture(future, fireDate);
        runnable.setScheduledFuture(cronScheduledFuture);
        LOGGER.debug("Scheduled cron expression {} to fire at {}", (Object)cronExpression.getCronExpression(), (Object)fireDate);
        return cronScheduledFuture;
    }

    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return this.getExecutorService().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return this.getExecutorService().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    public long nextFireInterval(Date fireDate) {
        return fireDate.getTime() - new Date().getTime();
    }

    private ScheduledExecutorService getExecutorService() {
        if (this.executorService == null) {
            if (this.scheduledItems > 0) {
                LOGGER.debug("{} starting {} threads", (Object)SIMPLE_NAME, (Object)this.scheduledItems);
                this.scheduledItems = Math.min(this.scheduledItems, 5);
                ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(this.scheduledItems, Log4jThreadFactory.createDaemonThreadFactory("Scheduled"));
                executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
                executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
                this.executorService = executor;
            } else {
                LOGGER.debug("{}: No scheduled items", (Object)SIMPLE_NAME);
            }
        }
        return this.executorService;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConfigurationScheduler {");
        BlockingQueue<Runnable> queue = ((ScheduledThreadPoolExecutor)this.executorService).getQueue();
        boolean first = true;
        for (Runnable runnable : queue) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(runnable.toString());
            first = false;
        }
        sb.append("}");
        return sb.toString();
    }

    public class CronRunnable
    implements Runnable {
        private final CronExpression cronExpression;
        private final Runnable runnable;
        private CronScheduledFuture<?> scheduledFuture;

        public CronRunnable(Runnable runnable, CronExpression cronExpression) {
            this.cronExpression = cronExpression;
            this.runnable = runnable;
        }

        public void setScheduledFuture(CronScheduledFuture<?> future) {
            this.scheduledFuture = future;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            try {
                long millis = this.scheduledFuture.getFireTime().getTime() - System.currentTimeMillis();
                if (millis > 0L) {
                    LOGGER.debug("Cron thread woke up {} millis early. Sleeping", (Object)millis);
                    try {
                        Thread.sleep(millis);
                    } catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                }
                this.runnable.run();
            } catch (Throwable ex) {
                LOGGER.error("{} caught error running command", (Object)SIMPLE_NAME, (Object)ex);
            } finally {
                Date fireDate = this.cronExpression.getNextValidTimeAfter(new Date());
                ScheduledFuture<?> future = ConfigurationScheduler.this.schedule(this, ConfigurationScheduler.this.nextFireInterval(fireDate), TimeUnit.MILLISECONDS);
                LOGGER.debug("Cron expression {} scheduled to fire again at {}", (Object)this.cronExpression.getCronExpression(), (Object)fireDate);
                this.scheduledFuture.reset(future, fireDate);
            }
        }

        public String toString() {
            return "CronRunnable{" + this.cronExpression.getCronExpression() + " - " + this.scheduledFuture.getFireTime();
        }
    }
}

