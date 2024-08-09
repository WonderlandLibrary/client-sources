/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import net.java.games.input.DummyWindow;
import net.java.games.input.RawDevice;
import net.java.games.input.RawDeviceInfo;

final class RawInputEventQueue {
    private final Object monitor = new Object();
    private List devices;

    RawInputEventQueue() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void start(List list) throws IOException {
        this.devices = list;
        QueueThread queueThread = new QueueThread(this);
        Object object = this.monitor;
        synchronized (object) {
            queueThread.start();
            while (!queueThread.isInitialized()) {
                try {
                    this.monitor.wait();
                } catch (InterruptedException interruptedException) {}
            }
        }
        if (queueThread.getException() != null) {
            throw queueThread.getException();
        }
    }

    private final RawDevice lookupDevice(long l) {
        for (int i = 0; i < this.devices.size(); ++i) {
            RawDevice rawDevice = (RawDevice)this.devices.get(i);
            if (rawDevice.getHandle() != l) continue;
            return rawDevice;
        }
        return null;
    }

    private final void addMouseEvent(long l, long l2, int n, int n2, int n3, long l3, long l4, long l5, long l6) {
        RawDevice rawDevice = this.lookupDevice(l);
        if (rawDevice == null) {
            return;
        }
        rawDevice.addMouseEvent(l2, n, n2, n3, l3, l4, l5, l6);
    }

    private final void addKeyboardEvent(long l, long l2, int n, int n2, int n3, int n4, long l3) {
        RawDevice rawDevice = this.lookupDevice(l);
        if (rawDevice == null) {
            return;
        }
        rawDevice.addKeyboardEvent(l2, n, n2, n3, n4, l3);
    }

    private final void poll(DummyWindow dummyWindow) throws IOException {
        this.nPoll(dummyWindow.getHwnd());
    }

    private final native void nPoll(long var1) throws IOException;

    private static final void registerDevices(DummyWindow dummyWindow, RawDeviceInfo[] rawDeviceInfoArray) throws IOException {
        RawInputEventQueue.nRegisterDevices(0, dummyWindow.getHwnd(), rawDeviceInfoArray);
    }

    private static final native void nRegisterDevices(int var0, long var1, RawDeviceInfo[] var3) throws IOException;

    static Object access$000(RawInputEventQueue rawInputEventQueue) {
        return rawInputEventQueue.monitor;
    }

    static List access$100(RawInputEventQueue rawInputEventQueue) {
        return rawInputEventQueue.devices;
    }

    static void access$200(DummyWindow dummyWindow, RawDeviceInfo[] rawDeviceInfoArray) throws IOException {
        RawInputEventQueue.registerDevices(dummyWindow, rawDeviceInfoArray);
    }

    static void access$300(RawInputEventQueue rawInputEventQueue, DummyWindow dummyWindow) throws IOException {
        rawInputEventQueue.poll(dummyWindow);
    }

    private final class QueueThread
    extends Thread {
        private boolean initialized;
        private DummyWindow window;
        private IOException exception;
        private final RawInputEventQueue this$0;

        public QueueThread(RawInputEventQueue rawInputEventQueue) {
            this.this$0 = rawInputEventQueue;
            this.setDaemon(false);
        }

        public final boolean isInitialized() {
            return this.initialized;
        }

        public final IOException getException() {
            return this.exception;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        public final void run() {
            try {
                this.window = new DummyWindow();
            } catch (IOException iOException) {
                this.exception = iOException;
            }
            this.initialized = true;
            HashSet<RawDeviceInfo> hashSet = RawInputEventQueue.access$000(this.this$0);
            synchronized (hashSet) {
                RawInputEventQueue.access$000(this.this$0).notify();
            }
            if (this.exception != null) {
                return;
            }
            hashSet = new HashSet<RawDeviceInfo>();
            try {
                for (int i = 0; i < RawInputEventQueue.access$100(this.this$0).size(); ++i) {
                    RawDevice rawDevice = (RawDevice)RawInputEventQueue.access$100(this.this$0).get(i);
                    hashSet.add(rawDevice.getInfo());
                }
                RawDeviceInfo[] rawDeviceInfoArray = new RawDeviceInfo[hashSet.size()];
                hashSet.toArray(rawDeviceInfoArray);
                try {
                    RawInputEventQueue.access$200(this.window, rawDeviceInfoArray);
                    while (!this.isInterrupted()) {
                        RawInputEventQueue.access$300(this.this$0, this.window);
                    }
                } finally {
                    this.window.destroy();
                }
            } catch (IOException iOException) {
                this.exception = iOException;
            }
        }
    }
}

