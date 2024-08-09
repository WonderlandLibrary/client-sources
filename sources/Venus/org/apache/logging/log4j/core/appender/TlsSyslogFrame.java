/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.nio.charset.StandardCharsets;

public class TlsSyslogFrame {
    private final String message;
    private final int byteLength;

    public TlsSyslogFrame(String string) {
        this.message = string;
        byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
        this.byteLength = byArray.length;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return Integer.toString(this.byteLength) + ' ' + this.message;
    }

    public int hashCode() {
        int n = 31;
        int n2 = 1;
        n2 = 31 * n2 + (this.message == null ? 0 : this.message.hashCode());
        return n2;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object == null) {
            return true;
        }
        if (!(object instanceof TlsSyslogFrame)) {
            return true;
        }
        TlsSyslogFrame tlsSyslogFrame = (TlsSyslogFrame)object;
        return this.message == null ? tlsSyslogFrame.message != null : !this.message.equals(tlsSyslogFrame.message);
    }
}

