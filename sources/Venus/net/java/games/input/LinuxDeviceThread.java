/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.java.games.input.LinuxDeviceTask;

final class LinuxDeviceThread
extends Thread {
    private final List tasks = new ArrayList();

    public LinuxDeviceThread() {
        this.setDaemon(false);
        this.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final synchronized void run() {
        while (true) {
            if (!this.tasks.isEmpty()) {
                LinuxDeviceTask linuxDeviceTask = (LinuxDeviceTask)this.tasks.remove(0);
                linuxDeviceTask.doExecute();
                LinuxDeviceTask linuxDeviceTask2 = linuxDeviceTask;
                synchronized (linuxDeviceTask2) {
                    linuxDeviceTask.notify();
                }
            }
            try {
                this.wait();
            } catch (InterruptedException interruptedException) {
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Object execute(LinuxDeviceTask linuxDeviceTask) throws IOException {
        Object object = this;
        synchronized (object) {
            this.tasks.add(linuxDeviceTask);
            this.notify();
        }
        object = linuxDeviceTask;
        synchronized (object) {
            while (linuxDeviceTask.getState() == 1) {
                try {
                    linuxDeviceTask.wait();
                } catch (InterruptedException interruptedException) {}
            }
        }
        switch (linuxDeviceTask.getState()) {
            case 2: {
                return linuxDeviceTask.getResult();
            }
            case 3: {
                throw linuxDeviceTask.getException();
            }
        }
        throw new RuntimeException("Invalid task state: " + linuxDeviceTask.getState());
    }
}

