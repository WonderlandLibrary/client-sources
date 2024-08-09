/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import net.java.games.input.Event;

public final class EventQueue {
    private final Event[] queue;
    private int head;
    private int tail;

    public EventQueue(int n) {
        this.queue = new Event[n + 1];
        for (int i = 0; i < this.queue.length; ++i) {
            this.queue[i] = new Event();
        }
    }

    final synchronized void add(Event event) {
        this.queue[this.tail].set(event);
        this.tail = this.increase(this.tail);
    }

    final synchronized boolean isFull() {
        return this.increase(this.tail) == this.head;
    }

    private final int increase(int n) {
        return (n + 1) % this.queue.length;
    }

    public final synchronized boolean getNextEvent(Event event) {
        if (this.head == this.tail) {
            return true;
        }
        event.set(this.queue[this.head]);
        this.head = this.increase(this.head);
        return false;
    }
}

