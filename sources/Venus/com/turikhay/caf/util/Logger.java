/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.turikhay.caf.util;

import java.io.PrintStream;

public interface Logger {
    public void logMessage(String var1);

    public void logError(String var1, Throwable var2);

    public static class PrintLogger
    implements Logger {
        private final PrintStream messageStream;
        private final PrintStream errorStream;
        private static PrintLogger SYSTEM;

        public PrintLogger(PrintStream printStream, PrintStream printStream2) {
            this.messageStream = printStream;
            this.errorStream = printStream2;
        }

        @Override
        public void logMessage(String string) {
            this.messageStream.println(string);
        }

        @Override
        public void logError(String string, Throwable throwable) {
            this.errorStream.println(throwable);
            throwable.printStackTrace(this.errorStream);
        }

        public static PrintLogger ofSystem() {
            if (SYSTEM == null) {
                SYSTEM = new PrintLogger(System.out, System.err);
            }
            return SYSTEM;
        }
    }
}

