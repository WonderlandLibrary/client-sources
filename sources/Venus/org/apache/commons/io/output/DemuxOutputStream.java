/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.output;

import java.io.IOException;
import java.io.OutputStream;

public class DemuxOutputStream
extends OutputStream {
    private final InheritableThreadLocal<OutputStream> outputStreamThreadLocal = new InheritableThreadLocal();

    public OutputStream bindStream(OutputStream outputStream) {
        OutputStream outputStream2 = (OutputStream)this.outputStreamThreadLocal.get();
        this.outputStreamThreadLocal.set(outputStream);
        return outputStream2;
    }

    @Override
    public void close() throws IOException {
        OutputStream outputStream = (OutputStream)this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.close();
        }
    }

    @Override
    public void flush() throws IOException {
        OutputStream outputStream = (OutputStream)this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.flush();
        }
    }

    @Override
    public void write(int n) throws IOException {
        OutputStream outputStream = (OutputStream)this.outputStreamThreadLocal.get();
        if (null != outputStream) {
            outputStream.write(n);
        }
    }
}

