/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.pack200;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.pack200.Pack200Strategy;
import org.apache.commons.compress.compressors.pack200.StreamBridge;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class Pack200CompressorInputStream
extends CompressorInputStream {
    private final InputStream originalInput;
    private final StreamBridge streamBridge;
    private static final byte[] CAFE_DOOD = new byte[]{-54, -2, -48, 13};
    private static final int SIG_LENGTH = CAFE_DOOD.length;

    public Pack200CompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY);
    }

    public Pack200CompressorInputStream(InputStream inputStream, Pack200Strategy pack200Strategy) throws IOException {
        this(inputStream, null, pack200Strategy, null);
    }

    public Pack200CompressorInputStream(InputStream inputStream, Map<String, String> map) throws IOException {
        this(inputStream, Pack200Strategy.IN_MEMORY, map);
    }

    public Pack200CompressorInputStream(InputStream inputStream, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this(inputStream, null, pack200Strategy, map);
    }

    public Pack200CompressorInputStream(File file) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY);
    }

    public Pack200CompressorInputStream(File file, Pack200Strategy pack200Strategy) throws IOException {
        this(null, file, pack200Strategy, null);
    }

    public Pack200CompressorInputStream(File file, Map<String, String> map) throws IOException {
        this(file, Pack200Strategy.IN_MEMORY, map);
    }

    public Pack200CompressorInputStream(File file, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this(null, file, pack200Strategy, map);
    }

    private Pack200CompressorInputStream(InputStream inputStream, File file, Pack200Strategy pack200Strategy, Map<String, String> map) throws IOException {
        this.originalInput = inputStream;
        this.streamBridge = pack200Strategy.newStreamBridge();
        JarOutputStream jarOutputStream = new JarOutputStream(this.streamBridge);
        Pack200.Unpacker unpacker = Pack200.newUnpacker();
        if (map != null) {
            unpacker.properties().putAll(map);
        }
        if (file == null) {
            unpacker.unpack(new FilterInputStream(this, inputStream){
                final Pack200CompressorInputStream this$0;
                {
                    this.this$0 = pack200CompressorInputStream;
                    super(inputStream);
                }

                public void close() {
                }
            }, jarOutputStream);
        } else {
            unpacker.unpack(file, jarOutputStream);
        }
        jarOutputStream.close();
    }

    @Override
    public int read() throws IOException {
        return this.streamBridge.getInput().read();
    }

    @Override
    public int read(byte[] byArray) throws IOException {
        return this.streamBridge.getInput().read(byArray);
    }

    @Override
    public int read(byte[] byArray, int n, int n2) throws IOException {
        return this.streamBridge.getInput().read(byArray, n, n2);
    }

    @Override
    public int available() throws IOException {
        return this.streamBridge.getInput().available();
    }

    @Override
    public boolean markSupported() {
        try {
            return this.streamBridge.getInput().markSupported();
        } catch (IOException iOException) {
            return true;
        }
    }

    @Override
    public void mark(int n) {
        try {
            this.streamBridge.getInput().mark(n);
        } catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    @Override
    public void reset() throws IOException {
        this.streamBridge.getInput().reset();
    }

    @Override
    public long skip(long l) throws IOException {
        return this.streamBridge.getInput().skip(l);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() throws IOException {
        try {
            this.streamBridge.stop();
        } finally {
            if (this.originalInput != null) {
                this.originalInput.close();
            }
        }
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < SIG_LENGTH) {
            return true;
        }
        for (int i = 0; i < SIG_LENGTH; ++i) {
            if (byArray[i] == CAFE_DOOD[i]) continue;
            return true;
        }
        return false;
    }
}

