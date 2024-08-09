/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.text2speech;

import com.google.common.collect.Queues;
import com.mojang.text2speech.Narrator;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;
import com.sun.jna.WString;
import java.util.Queue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NarratorWindows
implements Narrator {
    private static boolean libraryFound = false;
    private static final Logger LOGGER = LogManager.getLogger();
    private static long voice;
    private static boolean stopping;
    private final NarratorThread narratorThread = new NarratorThread(null);
    private boolean crashed = false;

    public NarratorWindows() {
        Thread thread2 = new Thread(this.narratorThread);
        thread2.setName("Narrator");
        thread2.start();
    }

    @Override
    public void say(String string, boolean bl) {
        if (this.crashed) {
            return;
        }
        try {
            this.narratorThread.add(new Message(this, string, bl, null));
        } catch (Throwable throwable) {
            this.crashed = true;
            LOGGER.error(String.format("Narrator crashed : %s", throwable));
        }
    }

    @Override
    public void clear() {
        this.narratorThread.clear();
        this.narratorThread.add(new Message(this, "", true, null));
    }

    @Override
    public boolean active() {
        return libraryFound;
    }

    @Override
    public void destroy() {
        stopping = true;
        try {
            this.narratorThread.join();
        } catch (InterruptedException interruptedException) {
            // empty catch block
        }
        SAPIWrapperSolutionDLL.uninit(voice);
    }

    static boolean access$100() {
        return stopping;
    }

    static long access$200() {
        return voice;
    }

    static {
        String string = "";
        try {
            Native.register(SAPIWrapperSolutionDLL.class, NativeLibrary.getInstance("SAPIWrapper_x64"));
            libraryFound = true;
            LOGGER.info("Narrator library for x64 successfully loaded");
            voice = SAPIWrapperSolutionDLL.init();
            if (voice == 0L) {
                string = string + "ERROR : Couldn't create a voice\n";
            }
        } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
            string = string + "ERROR : Couldn't load Narrator library : " + unsatisfiedLinkError.getMessage() + "\n";
        } catch (Throwable throwable) {
            string = string + "ERROR : Generic error while loading narrator : " + throwable.getMessage() + "\n";
        }
        if (!libraryFound) {
            try {
                Native.register(SAPIWrapperSolutionDLL.class, NativeLibrary.getInstance("SAPIWrapper_x86"));
                libraryFound = true;
                LOGGER.info("Narrator library for x86 successfully loaded");
                voice = SAPIWrapperSolutionDLL.init();
                if (voice == 0L) {
                    string = string + "ERROR : Couldn't create a voice\n";
                }
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {
                string = string + "ERROR : Couldn't load Narrator library : " + unsatisfiedLinkError.getMessage() + "\n";
            } catch (Throwable throwable) {
                string = string + "ERROR : Generic error while loading narrator : " + throwable.getMessage() + "\n";
            }
        }
        if (!libraryFound) {
            LOGGER.warn(string);
        }
    }

    private class Message {
        final String text;
        final boolean interrupt;
        final NarratorWindows this$0;

        private Message(NarratorWindows narratorWindows, String string, boolean bl) {
            this.this$0 = narratorWindows;
            this.text = string;
            this.interrupt = bl;
        }

        public void apply() {
            SAPIWrapperSolutionDLL.queue(NarratorWindows.access$200(), new WString(this.text.replaceAll("[<>]", "")), this.interrupt);
        }

        Message(NarratorWindows narratorWindows, String string, boolean bl, 1 var4_4) {
            this(narratorWindows, string, bl);
        }
    }

    private static class NarratorThread
    extends Thread {
        protected final Queue<Message> msgs = Queues.newConcurrentLinkedQueue();

        private NarratorThread() {
        }

        @Override
        public void run() {
            while (!NarratorWindows.access$100()) {
                if (this.msgs.peek() != null) {
                    this.msgs.poll().apply();
                }
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        public void add(Message message) {
            this.msgs.add(message);
        }

        public void clear() {
            this.msgs.clear();
        }

        NarratorThread(1 var1_1) {
            this();
        }
    }

    private static class SAPIWrapperSolutionDLL {
        private SAPIWrapperSolutionDLL() {
        }

        public static native long init();

        public static native void uninit(long var0);

        public static native void queue(long var0, WString var2, boolean var3);
    }
}

