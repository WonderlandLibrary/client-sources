/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.text2speech;

import com.google.common.collect.Queues;
import com.mojang.text2speech.Narrator;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorLinux
implements Narrator {
    private static boolean libraryFound = false;
    private static final Logger LOGGER = LogManager.getLogger();
    private final NarratorThread narratorThread = new NarratorThread(null);
    private boolean crashed = false;

    public NarratorLinux() {
        Thread thread2 = new Thread(this.narratorThread);
        thread2.setDaemon(false);
        thread2.setName("Narrator");
        thread2.start();
    }

    @Override
    public void say(String string, boolean bl) {
        if (this.crashed) {
            return;
        }
        try {
            this.narratorThread.add(string);
        } catch (Throwable throwable) {
            this.crashed = true;
            LOGGER.error(String.format("Narrator crashed : %s", throwable));
        }
    }

    @Override
    public void clear() {
        this.narratorThread.clear();
    }

    @Override
    public boolean active() {
        return libraryFound;
    }

    @Override
    public void destroy() {
    }

    static boolean access$100() {
        return libraryFound;
    }

    static {
        try {
            Native.register(FliteLibrary.class, NativeLibrary.getInstance("fliteWrapper"));
            FliteLibrary.init();
            libraryFound = true;
            LOGGER.info("Narrator library successfully loaded");
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            LOGGER.warn("ERROR : Couldn't load Narrator library : " + unsatisfiedLinkError.getMessage());
        } catch (Throwable throwable) {
            LOGGER.warn("ERROR : Generic error while loading narrator : " + throwable.getMessage());
        }
    }

    private static class NarratorThread
    implements Runnable {
        protected final Queue<String> msgs = Queues.newConcurrentLinkedQueue();

        private NarratorThread() {
        }

        @Override
        public void run() {
            while (true) {
                if (this.msgs.peek() != null) {
                    this.say(this.msgs.poll());
                }
                try {
                    Thread.sleep(100L);
                    continue;
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                    continue;
                }
                break;
            }
        }

        public void add(String string) {
            this.msgs.add(string);
        }

        public void clear() {
            this.msgs.clear();
        }

        private void say(String string) {
            if (NarratorLinux.access$100()) {
                FliteLibrary.say(string.replaceAll("[<>]", ""));
            }
        }

        NarratorThread(1 var1_1) {
            this();
        }
    }

    private static class FliteLibrary {
        private FliteLibrary() {
        }

        public static native int init();

        public static native float say(String var0);
    }
}

