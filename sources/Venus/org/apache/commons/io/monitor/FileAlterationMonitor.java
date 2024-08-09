/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.monitor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.io.monitor.FileAlterationObserver;

public final class FileAlterationMonitor
implements Runnable {
    private final long interval;
    private final List<FileAlterationObserver> observers = new CopyOnWriteArrayList<FileAlterationObserver>();
    private Thread thread = null;
    private ThreadFactory threadFactory;
    private volatile boolean running = false;

    public FileAlterationMonitor() {
        this(10000L);
    }

    public FileAlterationMonitor(long l) {
        this.interval = l;
    }

    public FileAlterationMonitor(long l, FileAlterationObserver ... fileAlterationObserverArray) {
        this(l);
        if (fileAlterationObserverArray != null) {
            for (FileAlterationObserver fileAlterationObserver : fileAlterationObserverArray) {
                this.addObserver(fileAlterationObserver);
            }
        }
    }

    public long getInterval() {
        return this.interval;
    }

    public synchronized void setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
    }

    public void addObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            this.observers.add(fileAlterationObserver);
        }
    }

    public void removeObserver(FileAlterationObserver fileAlterationObserver) {
        if (fileAlterationObserver != null) {
            while (this.observers.remove(fileAlterationObserver)) {
            }
        }
    }

    public Iterable<FileAlterationObserver> getObservers() {
        return this.observers;
    }

    public synchronized void start() throws Exception {
        if (this.running) {
            throw new IllegalStateException("Monitor is already running");
        }
        for (FileAlterationObserver fileAlterationObserver : this.observers) {
            fileAlterationObserver.initialize();
        }
        this.running = true;
        this.thread = this.threadFactory != null ? this.threadFactory.newThread(this) : new Thread(this);
        this.thread.start();
    }

    public synchronized void stop() throws Exception {
        this.stop(this.interval);
    }

    public synchronized void stop(long l) throws Exception {
        if (!this.running) {
            throw new IllegalStateException("Monitor is not running");
        }
        this.running = false;
        try {
            this.thread.join(l);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
        for (FileAlterationObserver fileAlterationObserver : this.observers) {
            fileAlterationObserver.destroy();
        }
    }

    @Override
    public void run() {
        while (this.running) {
            for (FileAlterationObserver fileAlterationObserver : this.observers) {
                fileAlterationObserver.checkAndNotify();
            }
            if (!this.running) break;
            try {
                Thread.sleep(this.interval);
            } catch (InterruptedException interruptedException) {}
        }
    }
}

