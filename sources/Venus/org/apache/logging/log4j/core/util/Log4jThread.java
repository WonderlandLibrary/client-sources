/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import java.util.concurrent.atomic.AtomicLong;

public class Log4jThread
extends Thread {
    static final String PREFIX = "Log4j2-";
    private static final AtomicLong threadInitNumber = new AtomicLong();

    private static long nextThreadNum() {
        return threadInitNumber.getAndIncrement();
    }

    private static String toThreadName(Object object) {
        return PREFIX + object;
    }

    public Log4jThread() {
        super(Log4jThread.toThreadName(Log4jThread.nextThreadNum()));
    }

    public Log4jThread(Runnable runnable) {
        super(runnable, Log4jThread.toThreadName(Log4jThread.nextThreadNum()));
    }

    public Log4jThread(Runnable runnable, String string) {
        super(runnable, Log4jThread.toThreadName(string));
    }

    public Log4jThread(String string) {
        super(Log4jThread.toThreadName(string));
    }

    public Log4jThread(ThreadGroup threadGroup, Runnable runnable) {
        super(threadGroup, runnable, Log4jThread.toThreadName(Log4jThread.nextThreadNum()));
    }

    public Log4jThread(ThreadGroup threadGroup, Runnable runnable, String string) {
        super(threadGroup, runnable, Log4jThread.toThreadName(string));
    }

    public Log4jThread(ThreadGroup threadGroup, Runnable runnable, String string, long l) {
        super(threadGroup, runnable, Log4jThread.toThreadName(string), l);
    }

    public Log4jThread(ThreadGroup threadGroup, String string) {
        super(threadGroup, Log4jThread.toThreadName(string));
    }
}

