/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class LoggingSessionInputBuffer
implements SessionInputBuffer,
EofSensor {
    private final SessionInputBuffer in;
    private final EofSensor eofSensor;
    private final Wire wire;
    private final String charset;

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire, String string) {
        this.in = sessionInputBuffer;
        this.eofSensor = sessionInputBuffer instanceof EofSensor ? (EofSensor)((Object)sessionInputBuffer) : null;
        this.wire = wire;
        this.charset = string != null ? string : Consts.ASCII.name();
    }

    public LoggingSessionInputBuffer(SessionInputBuffer sessionInputBuffer, Wire wire) {
        this(sessionInputBuffer, wire, null);
    }

    @Override
    public boolean isDataAvailable(int n) throws IOException {
        return this.in.isDataAvailable(n);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        int n3 = this.in.read(byArray, n, n2);
        if (this.wire.enabled() && n3 > 0) {
            this.wire.input(byArray, n, n3);
        }
        return n3;
    }

    @Override
    public int read() throws IOException {
        int n = this.in.read();
        if (this.wire.enabled() && n != -1) {
            this.wire.input(n);
        }
        return n;
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        int n = this.in.read(byArray);
        if (this.wire.enabled() && n > 0) {
            this.wire.input(byArray, 0, n);
        }
        return n;
    }

    @Override
    public String readLine() throws IOException {
        String string = this.in.readLine();
        if (this.wire.enabled() && string != null) {
            String string2 = string + "\r\n";
            this.wire.input(string2.getBytes(this.charset));
        }
        return string;
    }

    @Override
    public int readLine(CharArrayBuffer charArrayBuffer) throws IOException {
        int n = this.in.readLine(charArrayBuffer);
        if (this.wire.enabled() && n >= 0) {
            int n2 = charArrayBuffer.length() - n;
            String string = new String(charArrayBuffer.buffer(), n2, n);
            String string2 = string + "\r\n";
            this.wire.input(string2.getBytes(this.charset));
        }
        return n;
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.in.getMetrics();
    }

    @Override
    public boolean isEof() {
        if (this.eofSensor != null) {
            return this.eofSensor.isEof();
        }
        return true;
    }
}

