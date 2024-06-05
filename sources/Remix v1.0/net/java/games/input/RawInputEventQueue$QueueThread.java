package net.java.games.input;

import java.io.*;
import java.util.*;

private final class QueueThread extends Thread
{
    private boolean initialized;
    private DummyWindow window;
    private IOException exception;
    
    public QueueThread() {
        this.setDaemon(true);
    }
    
    public final boolean isInitialized() {
        return this.initialized;
    }
    
    public final IOException getException() {
        return this.exception;
    }
    
    public final void run() {
        try {
            this.window = new DummyWindow();
        }
        catch (IOException e) {
            this.exception = e;
        }
        this.initialized = true;
        synchronized (RawInputEventQueue.access$000(RawInputEventQueue.this)) {
            RawInputEventQueue.access$000(RawInputEventQueue.this).notify();
        }
        if (this.exception != null) {
            return;
        }
        final Set active_infos = new HashSet();
        try {
            for (int i = 0; i < RawInputEventQueue.access$100(RawInputEventQueue.this).size(); ++i) {
                final RawDevice device = RawInputEventQueue.access$100(RawInputEventQueue.this).get(i);
                active_infos.add(device.getInfo());
            }
            final RawDeviceInfo[] active_infos_array = new RawDeviceInfo[active_infos.size()];
            active_infos.toArray(active_infos_array);
            try {
                RawInputEventQueue.access$200(this.window, active_infos_array);
                while (!this.isInterrupted()) {
                    RawInputEventQueue.access$300(RawInputEventQueue.this, this.window);
                }
            }
            finally {
                this.window.destroy();
            }
        }
        catch (IOException e2) {
            this.exception = e2;
        }
    }
}
