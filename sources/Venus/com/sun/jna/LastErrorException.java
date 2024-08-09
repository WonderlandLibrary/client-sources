/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Platform;

public class LastErrorException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int errorCode;

    private static String formatMessage(int n) {
        return Platform.isWindows() ? "GetLastError() returned " + n : "errno was " + n;
    }

    private static String parseMessage(String string) {
        try {
            return LastErrorException.formatMessage(Integer.parseInt(string));
        } catch (NumberFormatException numberFormatException) {
            return string;
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public LastErrorException(String string) {
        super(LastErrorException.parseMessage(string.trim()));
        try {
            if (string.startsWith("[")) {
                string = string.substring(1, string.indexOf("]"));
            }
            this.errorCode = Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            this.errorCode = -1;
        }
    }

    public LastErrorException(int n) {
        this(n, LastErrorException.formatMessage(n));
    }

    protected LastErrorException(int n, String string) {
        super(string);
        this.errorCode = n;
    }
}

