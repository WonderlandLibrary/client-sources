/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender;

import java.nio.charset.StandardCharsets;

public class TlsSyslogFrame {
    private final String message;
    private final int byteLength;

    public TlsSyslogFrame(String message) {
        this.message = message;
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        this.byteLength = messageBytes.length;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return Integer.toString(this.byteLength) + ' ' + this.message;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.message == null ? 0 : this.message.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TlsSyslogFrame)) {
            return false;
        }
        TlsSyslogFrame other = (TlsSyslogFrame)obj;
        return !(this.message == null ? other.message != null : !this.message.equals(other.message));
    }
}

