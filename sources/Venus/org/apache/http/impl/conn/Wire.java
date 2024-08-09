/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.http.impl.conn;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.logging.Log;
import org.apache.http.annotation.Contract;
import org.apache.http.annotation.ThreadingBehavior;
import org.apache.http.util.Args;

@Contract(threading=ThreadingBehavior.IMMUTABLE)
public class Wire {
    private final Log log;
    private final String id;

    public Wire(Log log2, String string) {
        this.log = log2;
        this.id = string;
    }

    public Wire(Log log2) {
        this(log2, "");
    }

    private void wire(String string, InputStream inputStream) throws IOException {
        int n;
        StringBuilder stringBuilder = new StringBuilder();
        while ((n = inputStream.read()) != -1) {
            if (n == 13) {
                stringBuilder.append("[\\r]");
                continue;
            }
            if (n == 10) {
                stringBuilder.append("[\\n]\"");
                stringBuilder.insert(0, "\"");
                stringBuilder.insert(0, string);
                this.log.debug(this.id + " " + stringBuilder.toString());
                stringBuilder.setLength(0);
                continue;
            }
            if (n < 32 || n > 127) {
                stringBuilder.append("[0x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append("]");
                continue;
            }
            stringBuilder.append((char)n);
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.append('\"');
            stringBuilder.insert(0, '\"');
            stringBuilder.insert(0, string);
            this.log.debug(this.id + " " + stringBuilder.toString());
        }
    }

    public boolean enabled() {
        return this.log.isDebugEnabled();
    }

    public void output(InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Output");
        this.wire(">> ", inputStream);
    }

    public void input(InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "Input");
        this.wire("<< ", inputStream);
    }

    public void output(byte[] byArray, int n, int n2) throws IOException {
        Args.notNull(byArray, "Output");
        this.wire(">> ", new ByteArrayInputStream(byArray, n, n2));
    }

    public void input(byte[] byArray, int n, int n2) throws IOException {
        Args.notNull(byArray, "Input");
        this.wire("<< ", new ByteArrayInputStream(byArray, n, n2));
    }

    public void output(byte[] byArray) throws IOException {
        Args.notNull(byArray, "Output");
        this.wire(">> ", new ByteArrayInputStream(byArray));
    }

    public void input(byte[] byArray) throws IOException {
        Args.notNull(byArray, "Input");
        this.wire("<< ", new ByteArrayInputStream(byArray));
    }

    public void output(int n) throws IOException {
        this.output(new byte[]{(byte)n});
    }

    public void input(int n) throws IOException {
        this.input(new byte[]{(byte)n});
    }

    public void output(String string) throws IOException {
        Args.notNull(string, "Output");
        this.output(string.getBytes());
    }

    public void input(String string) throws IOException {
        Args.notNull(string, "Input");
        this.input(string.getBytes());
    }
}

