/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

public abstract class ICUNotifier {
    private final Object notifyLock = new Object();
    private NotifyThread notifyThread;
    private List<EventListener> listeners;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addListener(EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        if (this.acceptsListener(eventListener)) {
            Object object = this.notifyLock;
            synchronized (object) {
                if (this.listeners == null) {
                    this.listeners = new ArrayList<EventListener>();
                } else {
                    for (EventListener eventListener2 : this.listeners) {
                        if (eventListener2 != eventListener) continue;
                        return;
                    }
                }
                this.listeners.add(eventListener);
            }
        } else {
            throw new IllegalStateException("Listener invalid for this notifier.");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void removeListener(EventListener eventListener) {
        if (eventListener == null) {
            throw new NullPointerException();
        }
        Object object = this.notifyLock;
        synchronized (object) {
            if (this.listeners != null) {
                Iterator<EventListener> iterator2 = this.listeners.iterator();
                while (iterator2.hasNext()) {
                    if (iterator2.next() != eventListener) continue;
                    iterator2.remove();
                    if (this.listeners.size() == 0) {
                        this.listeners = null;
                    }
                    return;
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyChanged() {
        Object object = this.notifyLock;
        synchronized (object) {
            if (this.listeners != null) {
                if (this.notifyThread == null) {
                    this.notifyThread = new NotifyThread(this);
                    this.notifyThread.setDaemon(false);
                    this.notifyThread.start();
                }
                this.notifyThread.queue(this.listeners.toArray(new EventListener[this.listeners.size()]));
            }
        }
    }

    protected abstract boolean acceptsListener(EventListener var1);

    protected abstract void notifyListener(EventListener var1);

    private static class NotifyThread
    extends Thread {
        private final ICUNotifier notifier;
        private final List<EventListener[]> queue = new ArrayList<EventListener[]>();

        NotifyThread(ICUNotifier iCUNotifier) {
            this.notifier = iCUNotifier;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public void queue(EventListener[] eventListenerArray) {
            NotifyThread notifyThread = this;
            synchronized (notifyThread) {
                this.queue.add(eventListenerArray);
                this.notify();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    block6: while (true) {
                        EventListener[] eventListenerArray;
                        NotifyThread notifyThread = this;
                        synchronized (notifyThread) {
                            while (this.queue.isEmpty()) {
                                this.wait();
                            }
                            eventListenerArray = this.queue.remove(0);
                        }
                        int n = 0;
                        while (true) {
                            if (n >= eventListenerArray.length) continue block6;
                            this.notifier.notifyListener(eventListenerArray[n]);
                            ++n;
                        }
                        break;
                    }
                } catch (InterruptedException interruptedException) {
                    continue;
                }
                break;
            }
        }
    }
}

