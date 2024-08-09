/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.commons.compress.compressors.gzip;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.CRC32;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import org.apache.commons.compress.compressors.CompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

public class GzipCompressorInputStream
extends CompressorInputStream {
    private static final int FHCRC = 2;
    private static final int FEXTRA = 4;
    private static final int FNAME = 8;
    private static final int FCOMMENT = 16;
    private static final int FRESERVED = 224;
    private final InputStream in;
    private final boolean decompressConcatenated;
    private final byte[] buf = new byte[8192];
    private int bufUsed = 0;
    private Inflater inf = new Inflater(true);
    private final CRC32 crc = new CRC32();
    private int memberSize;
    private boolean endReached = false;
    private final byte[] oneByte = new byte[1];
    private final GzipParameters parameters = new GzipParameters();
    static final boolean $assertionsDisabled = !GzipCompressorInputStream.class.desiredAssertionStatus();

    public GzipCompressorInputStream(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public GzipCompressorInputStream(InputStream inputStream, boolean bl) throws IOException {
        this.in = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream);
        this.decompressConcatenated = bl;
        this.init(true);
    }

    public GzipParameters getMetaData() {
        return this.parameters;
    }

    private boolean init(boolean bl) throws IOException {
        if (!($assertionsDisabled || bl || this.decompressConcatenated)) {
            throw new AssertionError();
        }
        int n = this.in.read();
        int n2 = this.in.read();
        if (n == -1 && !bl) {
            return true;
        }
        if (n != 31 || n2 != 139) {
            throw new IOException(bl ? "Input is not in the .gz format" : "Garbage after a valid .gz stream");
        }
        DataInputStream dataInputStream = new DataInputStream(this.in);
        int n3 = dataInputStream.readUnsignedByte();
        if (n3 != 8) {
            throw new IOException("Unsupported compression method " + n3 + " in the .gz header");
        }
        int n4 = dataInputStream.readUnsignedByte();
        if ((n4 & 0xE0) != 0) {
            throw new IOException("Reserved flags are set in the .gz header");
        }
        this.parameters.setModificationTime(this.readLittleEndianInt(dataInputStream) * 1000);
        switch (dataInputStream.readUnsignedByte()) {
            case 2: {
                this.parameters.setCompressionLevel(9);
                break;
            }
            case 4: {
                this.parameters.setCompressionLevel(1);
                break;
            }
        }
        this.parameters.setOperatingSystem(dataInputStream.readUnsignedByte());
        if ((n4 & 4) != 0) {
            int n5 = dataInputStream.readUnsignedByte();
            n5 |= dataInputStream.readUnsignedByte() << 8;
            while (n5-- > 0) {
                dataInputStream.readUnsignedByte();
            }
        }
        if ((n4 & 8) != 0) {
            this.parameters.setFilename(new String(this.readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((n4 & 0x10) != 0) {
            this.parameters.setComment(new String(this.readToNull(dataInputStream), "ISO-8859-1"));
        }
        if ((n4 & 2) != 0) {
            dataInputStream.readShort();
        }
        this.inf.reset();
        this.crc.reset();
        this.memberSize = 0;
        return false;
    }

    private byte[] readToNull(DataInputStream dataInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n = 0;
        while ((n = dataInputStream.readUnsignedByte()) != 0) {
            byteArrayOutputStream.write(n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private int readLittleEndianInt(DataInputStream dataInputStream) throws IOException {
        return dataInputStream.readUnsignedByte() | dataInputStream.readUnsignedByte() << 8 | dataInputStream.readUnsignedByte() << 16 | dataInputStream.readUnsignedByte() << 24;
    }

    public int read() throws IOException {
        return this.read(this.oneByte, 0, 1) == -1 ? -1 : this.oneByte[0] & 0xFF;
    }

    public int read(byte[] byArray, int n, int n2) throws IOException {
        if (this.endReached) {
            return 1;
        }
        int n3 = 0;
        while (n2 > 0) {
            int n4;
            int n5;
            if (this.inf.needsInput()) {
                this.in.mark(this.buf.length);
                this.bufUsed = this.in.read(this.buf);
                if (this.bufUsed == -1) {
                    throw new EOFException();
                }
                this.inf.setInput(this.buf, 0, this.bufUsed);
            }
            try {
                n5 = this.inf.inflate(byArray, n, n2);
            } catch (DataFormatException dataFormatException) {
                throw new IOException("Gzip-compressed data is corrupt");
            }
            this.crc.update(byArray, n, n5);
            this.memberSize += n5;
            n += n5;
            n2 -= n5;
            n3 += n5;
            this.count(n5);
            if (!this.inf.finished()) continue;
            this.in.reset();
            int n6 = this.bufUsed - this.inf.getRemaining();
            if (this.in.skip(n6) != (long)n6) {
                throw new IOException();
            }
            this.bufUsed = 0;
            DataInputStream dataInputStream = new DataInputStream(this.in);
            long l = 0L;
            for (n4 = 0; n4 < 4; ++n4) {
                l |= (long)dataInputStream.readUnsignedByte() << n4 * 8;
            }
            if (l != this.crc.getValue()) {
                throw new IOException("Gzip-compressed data is corrupt (CRC32 error)");
            }
            n4 = 0;
            for (int i = 0; i < 4; ++i) {
                n4 |= dataInputStream.readUnsignedByte() << i * 8;
            }
            if (n4 != this.memberSize) {
                throw new IOException("Gzip-compressed data is corrupt(uncompressed size mismatch)");
            }
            if (this.decompressConcatenated && this.init(false)) continue;
            this.inf.end();
            this.inf = null;
            this.endReached = true;
            return n3 == 0 ? -1 : n3;
        }
        return n3;
    }

    public static boolean matches(byte[] byArray, int n) {
        if (n < 2) {
            return true;
        }
        if (byArray[0] != 31) {
            return true;
        }
        return byArray[1] != -117;
    }

    public void close() throws IOException {
        if (this.inf != null) {
            this.inf.end();
            this.inf = null;
        }
        if (this.in != System.in) {
            this.in.close();
        }
    }
}

