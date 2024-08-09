/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.Consts;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.impl.conn.Wire;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionOutputBuffer;
import org.apache.http.util.CharArrayBuffer;

@Deprecated
@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class LoggingSessionOutputBuffer
implements SessionOutputBuffer {
    private final SessionOutputBuffer out;
    private final Wire wire;
    private final String charset;

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire, String string) {
        this.out = sessionOutputBuffer;
        this.wire = wire;
        this.charset = string != null ? string : Consts.ASCII.name();
    }

    public LoggingSessionOutputBuffer(SessionOutputBuffer sessionOutputBuffer, Wire wire) {
        this(sessionOutputBuffer, wire, null);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.out.write(byArray, n, n2);
        if (this.wire.enabled()) {
            this.wire.output(byArray, n, n2);
        }
    }

    @Override
    public void write(int n) throws IOException {
        this.out.write(n);
        if (this.wire.enabled()) {
            this.wire.output(n);
        }
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.out.write(byArray);
        if (this.wire.enabled()) {
            this.wire.output(byArray);
        }
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    @Override
    public void writeLine(CharArrayBuffer charArrayBuffer) throws IOException {
        this.out.writeLine(charArrayBuffer);
        if (this.wire.enabled()) {
            String string = new String(charArrayBuffer.buffer(), 0, charArrayBuffer.length());
            String string2 = string + "\r\n";
            this.wire.output(string2.getBytes(this.charset));
        }
    }

    @Override
    public void writeLine(String string) throws IOException {
        this.out.writeLine(string);
        if (this.wire.enabled()) {
            String string2 = string + "\r\n";
            this.wire.output(string2.getBytes(this.charset));
        }
    }

    @Override
    public HttpTransportMetrics getMetrics() {
        return this.out.getMetrics();
    }
}

