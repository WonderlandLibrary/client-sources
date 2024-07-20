/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.sun.jna;

import com.sun.jna.Platform;

/*
 * Multiple versions of this class in jar - see https://www.benf.org/other/cfr/multi-version-jar.html
 */
public class LastErrorException
extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int errorCode;

    private static String formatMessage(int code) {
        return Platform.isWindows() ? "GetLastError() returned " + code : "errno was " + code;
    }

    private static String parseMessage(String m) {
        try {
            return LastErrorException.formatMessage(Integer.parseInt(m));
        } catch (NumberFormatException e) {
            return m;
        }
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public LastErrorException(String msg) {
        super(LastErrorException.parseMessage(msg.trim()));
        try {
            if (msg.startsWith("[")) {
                msg = msg.substring(1, msg.indexOf("]"));
            }
            this.errorCode = Integer.parseInt(msg);
        } catch (NumberFormatException e) {
            this.errorCode = -1;
        }
    }

    public LastErrorException(int code) {
        this(code, LastErrorException.formatMessage(code));
    }

    protected LastErrorException(int code, String msg) {
        super(msg);
        this.errorCode = code;
    }
}

