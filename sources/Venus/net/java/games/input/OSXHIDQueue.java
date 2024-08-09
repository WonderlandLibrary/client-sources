/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.java.games.input.OSXComponent;
import net.java.games.input.OSXEvent;
import net.java.games.input.OSXHIDElement;

final class OSXHIDQueue {
    private final Map map = new HashMap();
    private final long queue_address;
    private boolean released;

    public OSXHIDQueue(long l, int n) throws IOException {
        this.queue_address = l;
        try {
            this.createQueue(n);
        } catch (IOException iOException) {
            this.release();
            throw iOException;
        }
    }

    public final synchronized void setQueueDepth(int n) throws IOException {
        this.checkReleased();
        this.stop();
        this.close();
        this.createQueue(n);
    }

    private final void createQueue(int n) throws IOException {
        this.open(n);
        try {
            this.start();
        } catch (IOException iOException) {
            this.close();
            throw iOException;
        }
    }

    public final OSXComponent mapEvent(OSXEvent oSXEvent) {
        return (OSXComponent)this.map.get(new Long(oSXEvent.getCookie()));
    }

    private final void open(int n) throws IOException {
        OSXHIDQueue.nOpen(this.queue_address, n);
    }

    private static final native void nOpen(long var0, int var2) throws IOException;

    private final void close() throws IOException {
        OSXHIDQueue.nClose(this.queue_address);
    }

    private static final native void nClose(long var0) throws IOException;

    private final void start() throws IOException {
        OSXHIDQueue.nStart(this.queue_address);
    }

    private static final native void nStart(long var0) throws IOException;

    private final void stop() throws IOException {
        OSXHIDQueue.nStop(this.queue_address);
    }

    private static final native void nStop(long var0) throws IOException;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void release() throws IOException {
        if (!this.released) {
            this.released = true;
            try {
                this.stop();
                this.close();
            } finally {
                OSXHIDQueue.nReleaseQueue(this.queue_address);
            }
        }
    }

    private static final native void nReleaseQueue(long var0) throws IOException;

    public final void addElement(OSXHIDElement oSXHIDElement, OSXComponent oSXComponent) throws IOException {
        OSXHIDQueue.nAddElement(this.queue_address, oSXHIDElement.getCookie());
        this.map.put(new Long(oSXHIDElement.getCookie()), oSXComponent);
    }

    private static final native void nAddElement(long var0, long var2) throws IOException;

    public final void removeElement(OSXHIDElement oSXHIDElement) throws IOException {
        OSXHIDQueue.nRemoveElement(this.queue_address, oSXHIDElement.getCookie());
        this.map.remove(new Long(oSXHIDElement.getCookie()));
    }

    private static final native void nRemoveElement(long var0, long var2) throws IOException;

    public final synchronized boolean getNextEvent(OSXEvent oSXEvent) throws IOException {
        this.checkReleased();
        return OSXHIDQueue.nGetNextEvent(this.queue_address, oSXEvent);
    }

    private static final native boolean nGetNextEvent(long var0, OSXEvent var2) throws IOException;

    private final void checkReleased() throws IOException {
        if (this.released) {
            throw new IOException("Queue is released");
        }
    }
}

