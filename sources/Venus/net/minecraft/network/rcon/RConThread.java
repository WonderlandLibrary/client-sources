/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.rcon;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.util.DefaultWithNameUncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RConThread
implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicInteger THREAD_ID = new AtomicInteger(0);
    protected volatile boolean running;
    protected final String threadName;
    @Nullable
    protected Thread rconThread;

    protected RConThread(String string) {
        this.threadName = string;
    }

    public synchronized boolean func_241832_a() {
        if (this.running) {
            return false;
        }
        this.running = true;
        this.rconThread = new Thread((Runnable)this, this.threadName + " #" + THREAD_ID.incrementAndGet());
        this.rconThread.setUncaughtExceptionHandler(new DefaultWithNameUncaughtExceptionHandler(LOGGER));
        this.rconThread.start();
        LOGGER.info("Thread {} started", (Object)this.threadName);
        return false;
    }

    public synchronized void func_219591_b() {
        this.running = false;
        if (null != this.rconThread) {
            int n = 0;
            while (this.rconThread.isAlive()) {
                try {
                    this.rconThread.join(1000L);
                    if (++n >= 5) {
                        LOGGER.warn("Waited {} seconds attempting force stop!", (Object)n);
                        continue;
                    }
                    if (!this.rconThread.isAlive()) continue;
                    LOGGER.warn("Thread {} ({}) failed to exit after {} second(s)", (Object)this, (Object)this.rconThread.getState(), (Object)n, (Object)new Exception("Stack:"));
                    this.rconThread.interrupt();
                } catch (InterruptedException interruptedException) {}
            }
            LOGGER.info("Thread {} stopped", (Object)this.threadName);
            this.rconThread = null;
        }
    }

    public boolean isRunning() {
        return this.running;
    }
}

