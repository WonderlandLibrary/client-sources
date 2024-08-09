/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.pack200;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.jar.JarInputStream;
import java.util.jar.Pack200;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.pack200.Pack200Strategy;
import org.apache.commons.compress.compressors.pack200.StreamBridge;
import org.apache.commons.compress.utils.IOUtils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Pack200CompressorOutputStream
extends CompressorOutputStream {
    private boolean finished = false;
    private final OutputStream originalOutput;
    private final StreamBridge streamBridge;
    private final Map<String, String> properties;

    public Pack200CompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, Pack200Strategy.IN_MEMORY);
    }

    public Pack200CompressorOutputStream(OutputStream outputStream, Pack200Strategy pack200Strategy) throws IOException {
        this(outputStream, pack200Strategy, null);
    }

    public Pack200CompressorOutputStream(OutputStream outputStream, Map<String, String> map) throws IOException {
        this(outputStream, Pack200Strategy.IN_MEMORY, map);
    }

    public Pack200CompressorOutputStream(OutputStream outputStream, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this.originalOutput = outputStream;
        this.streamBridge = pack200Strategy.newStreamBridge();
        this.properties = map;
    }

    @Override
    public void write(int n) throws IOException {
        this.streamBridge.write(n);
    }

    @Override
    public void write(byte[] byArray) throws IOException {
        this.streamBridge.write(byArray);
    }

    @Override
    public void write(byte[] byArray, int n, int n2) throws IOException {
        this.streamBridge.write(byArray, n, n2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        this.finish();
        try {
            this.streamBridge.stop();
        } finally {
            this.originalOutput.close();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void finish() throws IOException {
        if (this.finished) return;
        this.finished = true;
        Pack200.Packer packer = Pack200.newPacker();
        if (this.properties != null) {
            packer.properties().putAll(this.properties);
        }
        JarInputStream jarInputStream = null;
        boolean bl = false;
        try {
            jarInputStream = new JarInputStream(this.streamBridge.getInput());
            packer.pack(jarInputStream, this.originalOutput);
            return;
        } catch (Throwable throwable) {
            if (bl) throw throwable;
            IOUtils.closeQuietly(jarInputStream);
            throw throwable;
        }
    }
}

