/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.AbstractLifeCycle;
import org.apache.logging.log4j.core.config.ConfigurationScheduler;
import org.apache.logging.log4j.core.util.FileWatcher;
import org.apache.logging.log4j.status.StatusLogger;

public class WatchManager
extends AbstractLifeCycle {
    private static Logger logger = StatusLogger.getLogger();
    private final ConcurrentMap<File, FileMonitor> watchers = new ConcurrentHashMap<File, FileMonitor>();
    private int intervalSeconds = 0;
    private ScheduledFuture<?> future;
    private final ConfigurationScheduler scheduler;

    public WatchManager(ConfigurationScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void setIntervalSeconds(int intervalSeconds) {
        if (!this.isStarted()) {
            if (this.intervalSeconds > 0 && intervalSeconds == 0) {
                this.scheduler.decrementScheduledItems();
            } else if (this.intervalSeconds == 0 && intervalSeconds > 0) {
                this.scheduler.incrementScheduledItems();
            }
            this.intervalSeconds = intervalSeconds;
        }
    }

    public int getIntervalSeconds() {
        return this.intervalSeconds;
    }

    @Override
    public void start() {
        super.start();
        if (this.intervalSeconds > 0) {
            this.future = this.scheduler.scheduleWithFixedDelay(new WatchRunnable(), this.intervalSeconds, this.intervalSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean stop(long timeout, TimeUnit timeUnit) {
        this.setStopping();
        boolean stopped = this.stop(this.future);
        this.setStopped();
        return stopped;
    }

    public void watchFile(File file, FileWatcher watcher) {
        this.watchers.put(file, new FileMonitor(file.lastModified(), watcher));
    }

    public Map<File, FileWatcher> getWatchers() {
        HashMap<File, FileWatcher> map = new HashMap<File, FileWatcher>();
        for (Map.Entry entry : this.watchers.entrySet()) {
            map.put((File)entry.getKey(), ((FileMonitor)entry.getValue()).fileWatcher);
        }
        return map;
    }

    private class FileMonitor {
        private final FileWatcher fileWatcher;
        private long lastModified;

        public FileMonitor(long lastModified, FileWatcher fileWatcher) {
            this.fileWatcher = fileWatcher;
            this.lastModified = lastModified;
        }
    }

    private class WatchRunnable
    implements Runnable {
        private WatchRunnable() {
        }

        @Override
        public void run() {
            for (Map.Entry entry : WatchManager.this.watchers.entrySet()) {
                long lastModfied;
                File file = (File)entry.getKey();
                FileMonitor fileMonitor = (FileMonitor)entry.getValue();
                if (!this.fileModified(fileMonitor, lastModfied = file.lastModified())) continue;
                logger.info("File {} was modified on {}, previous modification was {}", (Object)file, (Object)lastModfied, (Object)fileMonitor.lastModified);
                fileMonitor.lastModified = lastModfied;
                fileMonitor.fileWatcher.fileModified(file);
            }
        }

        private boolean fileModified(FileMonitor fileMonitor, long lastModfied) {
            return lastModfied != fileMonitor.lastModified;
        }
    }
}

