/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.gzip;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

public class GzipCompressorOutputStream
extends CompressorOutputStream {
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private final OutputStream out;
    private final Deflater deflater;
    private final byte[] deflateBuffer = new byte[512];
    private boolean closed;
    private final CRC32 crc = new CRC32();

    public GzipCompressorOutputStream(OutputStream outputStream) throws IOException {
        this(outputStream, new GzipParameters());
    }

    public GzipCompressorOutputStream(OutputStream outputStream, GzipParameters gzipParameters) throws IOException {
        this.out = outputStream;
        this.deflater = new Deflater(gzipParameters.getCompressionLevel(), true);
        this.writeHeader(gzipParameters);
    }

    private void writeHeader(GzipParameters gzipParameters) throws IOException {
        String string = gzipParameters.getFilename();
        String string2 = gzipParameters.getComment();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putShort((short)-29921);
        byteBuffer.put((byte)8);
        byteBuffer.put((byte)((string != null ? 8 : 0) | (string2 != null ? 16 : 0)));
        byteBuffer.putInt((int)(gzipParameters.getModificationTime() / 1000L));
        int n = gzipParameters.getCompressionLevel();
        if (n == 9) {
            byteBuffer.put((byte)2);
        } else if (n == 1) {
            byteBuffer.put((byte)4);
        } else {
            byteBuffer.put((byte)0);
        }
        byteBuffer.put((byte)gzipParameters.getOperatingSystem());
        this.out.write(byteBuffer.array());
        if (string != null) {
            this.out.write(string.getBytes("ISO-8859-1"));
            this.out.write(0);
        }
        if (string2 != null) {
            this.out.write(string2.getBytes("ISO-8859-1"));
            this.out.write(0);
        }
    }

    private void writeTrailer() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt((int)this.crc.getValue());
        byteBuffer.putInt(this.deflater.getTotalIn());
        this.out.write(byteBuffer.array());
    }

    public void write(int n) throws IOException {
        this.write(new byte[]{(byte)(n & 0xFF)}, 0, 1);
    }

    public void write(byte[] byArray) throws IOException {
        this.write(byArray, 0, byArray.length);
    }

    public void write(byte[] byArray, int n, int n2) throws IOException {
        if (this.deflater.finished()) {
            throw new IOException("Cannot write more data, the end of the compressed data stream has been reached");
        }
        if (n2 > 0) {
            this.deflater.setInput(byArray, n, n2);
            while (!this.deflater.needsInput()) {
                this.deflate();
            }
            this.crc.update(byArray, n, n2);
        }
    }

    private void deflate() throws IOException {
        int n = this.deflater.deflate(this.deflateBuffer, 0, this.deflateBuffer.length);
        if (n > 0) {
            this.out.write(this.deflateBuffer, 0, n);
        }
    }

    public void finish() throws IOException {
        if (!this.deflater.finished()) {
            this.deflater.finish();
            while (!this.deflater.finished()) {
                this.deflate();
            }
            this.writeTrailer();
        }
    }

    public void flush() throws IOException {
        this.out.flush();
    }

    public void close() throws IOException {
        if (!this.closed) {
            this.finish();
            this.deflater.end();
            this.out.close();
            this.closed = true;
        }
    }
}

