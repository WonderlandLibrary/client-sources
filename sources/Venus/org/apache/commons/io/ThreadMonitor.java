/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io;

class ThreadMonitor
implements Runnable {
    private final Thread thread;
    private final long timeout;

    public static Thread start(long l) {
        return ThreadMonitor.start(Thread.currentThread(), l);
    }

    public static Thread start(Thread thread2, long l) {
        Thread thread3 = null;
        if (l > 0L) {
            ThreadMonitor threadMonitor = new ThreadMonitor(thread2, l);
            thread3 = new Thread((Runnable)threadMonitor, ThreadMonitor.class.getSimpleName());
            thread3.setDaemon(false);
            thread3.start();
        }
        return thread3;
    }

    public static void stop(Thread thread2) {
        if (thread2 != null) {
            thread2.interrupt();
        }
    }

    private ThreadMonitor(Thread thread2, long l) {
        this.thread = thread2;
        this.timeout = l;
    }

    @Override
    public void run() {
        try {
            ThreadMonitor.sleep(this.timeout);
            this.thread.interrupt();
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    private static void sleep(long l) throws InterruptedException {
        long l2 = System.currentTimeMillis() + l;
        long l3 = l;
        do {
            Thread.sleep(l3);
        } while ((l3 = l2 - System.currentTimeMillis()) > 0L);
    }
}

