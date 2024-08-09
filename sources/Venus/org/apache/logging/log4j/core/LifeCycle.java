/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core;

public interface LifeCycle {
    public State getState();

    public void initialize();

    public void start();

    public void stop();

    public boolean isStarted();

    public boolean isStopped();

    public static enum State {
        INITIALIZING,
        INITIALIZED,
        STARTING,
        STARTED,
        STOPPING,
        STOPPED;

    }
}

