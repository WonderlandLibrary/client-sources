/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Callback;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class CallbackThreadInitializer {
    private boolean daemon;
    private boolean detach;
    private String name;
    private ThreadGroup group;

    public CallbackThreadInitializer() {
        this(true);
    }

    public CallbackThreadInitializer(boolean daemon) {
        this(daemon, false);
    }

    public CallbackThreadInitializer(boolean daemon, boolean detach) {
        this(daemon, detach, null);
    }

    public CallbackThreadInitializer(boolean daemon, boolean detach, String name) {
        this(daemon, detach, name, null);
    }

    public CallbackThreadInitializer(boolean daemon, boolean detach, String name, ThreadGroup group) {
        this.daemon = daemon;
        this.detach = detach;
        this.name = name;
        this.group = group;
    }

    public String getName(Callback cb) {
        return this.name;
    }

    public ThreadGroup getThreadGroup(Callback cb) {
        return this.group;
    }

    public boolean isDaemon(Callback cb) {
        return this.daemon;
    }

    public boolean detach(Callback cb) {
        return this.detach;
    }
}

