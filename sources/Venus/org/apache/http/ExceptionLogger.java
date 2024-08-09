/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http;

public interface ExceptionLogger {
    public static final ExceptionLogger NO_OP = new ExceptionLogger(){

        @Override
        public void log(Exception exception) {
        }
    };
    public static final ExceptionLogger STD_ERR = new ExceptionLogger(){

        @Override
        public void log(Exception exception) {
            exception.printStackTrace();
        }
    };

    public void log(Exception var1);
}

