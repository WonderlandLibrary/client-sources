/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class DemuxInputStream
extends InputStream {
    private final InheritableThreadLocal<InputStream> m_streams = new InheritableThreadLocal();

    public InputStream bindStream(InputStream inputStream) {
        InputStream inputStream2 = (InputStream)this.m_streams.get();
        this.m_streams.set(inputStream);
        return inputStream2;
    }

    @Override
    public void close() throws IOException {
        InputStream inputStream = (InputStream)this.m_streams.get();
        if (null != inputStream) {
            inputStream.close();
        }
    }

    @Override
    public int read() throws IOException {
        InputStream inputStream = (InputStream)this.m_streams.get();
        if (null != inputStream) {
            return inputStream.read();
        }
        return 1;
    }
}

