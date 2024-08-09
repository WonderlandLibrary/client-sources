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

    public WatchManager(ConfigurationScheduler configurationScheduler) {
        this.scheduler = configurationScheduler;
    }

    public void setIntervalSeconds(int n) {
        if (!this.isStarted()) {
            if (this.intervalSeconds > 0 && n == 0) {
                this.scheduler.decrementScheduledItems();
            } else if (this.intervalSeconds == 0 && n > 0) {
                this.scheduler.incrementScheduledItems();
            }
            this.intervalSeconds = n;
        }
    }

    public int getIntervalSeconds() {
        return this.intervalSeconds;
    }

    @Override
    public void start() {
        super.start();
        if (this.intervalSeconds > 0) {
            this.future = this.scheduler.scheduleWithFixedDelay(new WatchRunnable(this, null), this.intervalSeconds, this.intervalSeconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public boolean stop(long l, TimeUnit timeUnit) {
        this.setStopping();
        boolean bl = this.stop(this.future);
        this.setStopped();
        return bl;
    }

    public void watchFile(File file, FileWatcher fileWatcher) {
        this.watchers.put(file, new FileMonitor(this, file.lastModified(), fileWatcher));
    }

    public Map<File, FileWatcher> getWatchers() {
        HashMap<File, FileWatcher> hashMap = new HashMap<File, FileWatcher>();
        for (Map.Entry entry : this.watchers.entrySet()) {
            hashMap.put((File)entry.getKey(), FileMonitor.access$100((FileMonitor)entry.getValue()));
        }
        return hashMap;
    }

    static ConcurrentMap access$200(WatchManager watchManager) {
        return watchManager.watchers;
    }

    static Logger access$400() {
        return logger;
    }

    static class 1 {
    }

    private class FileMonitor {
        private final FileWatcher fileWatcher;
        private long lastModified;
        final WatchManager this$0;

        public FileMonitor(WatchManager watchManager, long l, FileWatcher fileWatcher) {
            this.this$0 = watchManager;
            this.fileWatcher = fileWatcher;
            this.lastModified = l;
        }

        static FileWatcher access$100(FileMonitor fileMonitor) {
            return fileMonitor.fileWatcher;
        }

        static long access$300(FileMonitor fileMonitor) {
            return fileMonitor.lastModified;
        }

        static long access$302(FileMonitor fileMonitor, long l) {
            fileMonitor.lastModified = l;
            return fileMonitor.lastModified;
        }
    }

    private class WatchRunnable
    implements Runnable {
        final WatchManager this$0;

        private WatchRunnable(WatchManager watchManager) {
            this.this$0 = watchManager;
        }

        @Override
        public void run() {
            for (Map.Entry entry : WatchManager.access$200(this.this$0).entrySet()) {
                long l;
                File file = (File)entry.getKey();
                FileMonitor fileMonitor = (FileMonitor)entry.getValue();
                if (!this.fileModified(fileMonitor, l = file.lastModified())) continue;
                WatchManager.access$400().info("File {} was modified on {}, previous modification was {}", (Object)file, (Object)l, (Object)FileMonitor.access$300(fileMonitor));
                FileMonitor.access$302(fileMonitor, l);
                FileMonitor.access$100(fileMonitor).fileModified(file);
            }
        }

        private boolean fileModified(FileMonitor fileMonitor, long l) {
            return l != FileMonitor.access$300(fileMonitor);
        }

        WatchRunnable(WatchManager watchManager, 1 var2_2) {
            this(watchManager);
        }
    }
}

