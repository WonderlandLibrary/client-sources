/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.opennbt;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.limiter.TagLimiter;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class NBTIO {
    public static CompoundTag readFile(String string) throws IOException {
        return NBTIO.readFile(new File(string));
    }

    public static CompoundTag readFile(File file) throws IOException {
        return NBTIO.readFile(file, true, false);
    }

    public static CompoundTag readFile(String string, boolean bl, boolean bl2) throws IOException {
        return NBTIO.readFile(new File(string), bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static CompoundTag readFile(File file, boolean bl, boolean bl2) throws IOException {
        try (InputStream inputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);){
            CompoundTag compoundTag;
            if (bl) {
                inputStream = new GZIPInputStream(inputStream);
            }
            if (!((compoundTag = NBTIO.readTag(inputStream, bl2)) instanceof CompoundTag)) {
                throw new IOException("Root tag is not a CompoundTag!");
            }
            CompoundTag compoundTag2 = compoundTag;
            return compoundTag2;
        }
    }

    public static void writeFile(CompoundTag compoundTag, String string) throws IOException {
        NBTIO.writeFile(compoundTag, new File(string));
    }

    public static void writeFile(CompoundTag compoundTag, File file) throws IOException {
        NBTIO.writeFile(compoundTag, file, true, false);
    }

    public static void writeFile(CompoundTag compoundTag, String string, boolean bl, boolean bl2) throws IOException {
        NBTIO.writeFile(compoundTag, new File(string), bl, bl2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeFile(CompoundTag compoundTag, File file, boolean bl, boolean bl2) throws IOException {
        if (!file.exists()) {
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        }
        try (OutputStream outputStream = Files.newOutputStream(file.toPath(), new OpenOption[0]);){
            if (bl) {
                outputStream = new GZIPOutputStream(outputStream);
            }
            NBTIO.writeTag(outputStream, compoundTag, bl2);
        }
    }

    public static CompoundTag readTag(InputStream inputStream) throws IOException {
        return NBTIO.readTag(inputStream, TagLimiter.noop());
    }

    public static CompoundTag readTag(InputStream inputStream, TagLimiter tagLimiter) throws IOException {
        return NBTIO.readTag(new DataInputStream(inputStream), tagLimiter);
    }

    public static CompoundTag readTag(InputStream inputStream, boolean bl) throws IOException {
        return NBTIO.readTag((DataInput)((Object)(bl ? new LittleEndianDataInputStream(inputStream, null) : new DataInputStream(inputStream))));
    }

    public static CompoundTag readTag(DataInput dataInput) throws IOException {
        return NBTIO.readTag(dataInput, TagLimiter.noop());
    }

    public static CompoundTag readTag(DataInput dataInput, TagLimiter tagLimiter) throws IOException {
        byte by = dataInput.readByte();
        if (by != 10) {
            throw new IOException(String.format("Expected root tag to be a CompoundTag, was %s", by));
        }
        dataInput.skipBytes(dataInput.readUnsignedShort());
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.read(dataInput, tagLimiter);
        return compoundTag;
    }

    public static void writeTag(OutputStream outputStream, CompoundTag compoundTag) throws IOException {
        NBTIO.writeTag(outputStream, compoundTag, false);
    }

    public static void writeTag(OutputStream outputStream, CompoundTag compoundTag, boolean bl) throws IOException {
        NBTIO.writeTag((DataOutput)((Object)(bl ? new LittleEndianDataOutputStream(outputStream, null) : new DataOutputStream(outputStream))), compoundTag);
    }

    public static void writeTag(DataOutput dataOutput, CompoundTag compoundTag) throws IOException {
        dataOutput.writeByte(10);
        dataOutput.writeUTF("");
        compoundTag.write(dataOutput);
    }

    private static final class LittleEndianDataOutputStream
    extends FilterOutputStream
    implements DataOutput {
        private LittleEndianDataOutputStream(OutputStream outputStream) {
            super(outputStream);
        }

        @Override
        public synchronized void write(int n) throws IOException {
            this.out.write(n);
        }

        @Override
        public synchronized void write(byte[] byArray, int n, int n2) throws IOException {
            this.out.write(byArray, n, n2);
        }

        @Override
        public void flush() throws IOException {
            this.out.flush();
        }

        @Override
        public void writeBoolean(boolean bl) throws IOException {
            this.out.write(bl ? 1 : 0);
        }

        @Override
        public void writeByte(int n) throws IOException {
            this.out.write(n);
        }

        @Override
        public void writeShort(int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
        }

        @Override
        public void writeChar(int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
        }

        @Override
        public void writeInt(int n) throws IOException {
            this.out.write(n & 0xFF);
            this.out.write(n >>> 8 & 0xFF);
            this.out.write(n >>> 16 & 0xFF);
            this.out.write(n >>> 24 & 0xFF);
        }

        @Override
        public void writeLong(long l) throws IOException {
            this.out.write((int)(l & 0xFFL));
            this.out.write((int)(l >>> 8 & 0xFFL));
            this.out.write((int)(l >>> 16 & 0xFFL));
            this.out.write((int)(l >>> 24 & 0xFFL));
            this.out.write((int)(l >>> 32 & 0xFFL));
            this.out.write((int)(l >>> 40 & 0xFFL));
            this.out.write((int)(l >>> 48 & 0xFFL));
            this.out.write((int)(l >>> 56 & 0xFFL));
        }

        @Override
        public void writeFloat(float f) throws IOException {
            this.writeInt(Float.floatToIntBits(f));
        }

        @Override
        public void writeDouble(double d) throws IOException {
            this.writeLong(Double.doubleToLongBits(d));
        }

        @Override
        public void writeBytes(String string) throws IOException {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                this.out.write((byte)string.charAt(i));
            }
        }

        @Override
        public void writeChars(String string) throws IOException {
            int n = string.length();
            for (int i = 0; i < n; ++i) {
                char c = string.charAt(i);
                this.out.write(c & 0xFF);
                this.out.write(c >>> 8 & 0xFF);
            }
        }

        @Override
        public void writeUTF(String string) throws IOException {
            byte[] byArray = string.getBytes(StandardCharsets.UTF_8);
            this.writeShort(byArray.length);
            this.write(byArray);
        }

        LittleEndianDataOutputStream(OutputStream outputStream, 1 var2_2) {
            this(outputStream);
        }
    }

    private static final class LittleEndianDataInputStream
    extends FilterInputStream
    implements DataInput {
        private LittleEndianDataInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public int read(byte[] byArray) throws IOException {
            return this.in.read(byArray, 0, byArray.length);
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            return this.in.read(byArray, n, n2);
        }

        @Override
        public void readFully(byte[] byArray) throws IOException {
            this.readFully(byArray, 0, byArray.length);
        }

        @Override
        public void readFully(byte[] byArray, int n, int n2) throws IOException {
            int n3;
            if (n2 < 0) {
                throw new IndexOutOfBoundsException();
            }
            for (int i = 0; i < n2; i += n3) {
                n3 = this.in.read(byArray, n + i, n2 - i);
                if (n3 >= 0) continue;
                throw new EOFException();
            }
        }

        @Override
        public int skipBytes(int n) throws IOException {
            int n2;
            int n3 = 0;
            for (n2 = 0; n2 < n && (n3 = (int)this.in.skip(n - n2)) > 0; n2 += n3) {
            }
            return n2;
        }

        @Override
        public boolean readBoolean() throws IOException {
            int n = this.in.read();
            if (n < 0) {
                throw new EOFException();
            }
            return n != 0;
        }

        @Override
        public byte readByte() throws IOException {
            int n = this.in.read();
            if (n < 0) {
                throw new EOFException();
            }
            return (byte)n;
        }

        @Override
        public int readUnsignedByte() throws IOException {
            int n = this.in.read();
            if (n < 0) {
                throw new EOFException();
            }
            return n;
        }

        @Override
        public short readShort() throws IOException {
            int n;
            int n2 = this.in.read();
            if ((n2 | (n = this.in.read())) < 0) {
                throw new EOFException();
            }
            return (short)(n2 | n << 8);
        }

        @Override
        public int readUnsignedShort() throws IOException {
            int n;
            int n2 = this.in.read();
            if ((n2 | (n = this.in.read())) < 0) {
                throw new EOFException();
            }
            return n2 | n << 8;
        }

        @Override
        public char readChar() throws IOException {
            int n;
            int n2 = this.in.read();
            if ((n2 | (n = this.in.read())) < 0) {
                throw new EOFException();
            }
            return (char)(n2 | n << 8);
        }

        @Override
        public int readInt() throws IOException {
            int n;
            int n2;
            int n3;
            int n4 = this.in.read();
            if ((n4 | (n3 = this.in.read()) | (n2 = this.in.read()) | (n = this.in.read())) < 0) {
                throw new EOFException();
            }
            return n4 | n3 << 8 | n2 << 16 | n << 24;
        }

        @Override
        public long readLong() throws IOException {
            long l;
            long l2;
            long l3;
            long l4;
            long l5;
            long l6;
            long l7;
            long l8 = this.in.read();
            if ((l8 | (l7 = (long)this.in.read()) | (l6 = (long)this.in.read()) | (l5 = (long)this.in.read()) | (l4 = (long)this.in.read()) | (l3 = (long)this.in.read()) | (l2 = (long)this.in.read()) | (l = (long)this.in.read())) < 0L) {
                throw new EOFException();
            }
            return l8 | l7 << 8 | l6 << 16 | l5 << 24 | l4 << 32 | l3 << 40 | l2 << 48 | l << 56;
        }

        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readInt());
        }

        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readLong());
        }

        @Override
        public String readLine() throws IOException {
            throw new UnsupportedOperationException("Use readUTF.");
        }

        @Override
        public String readUTF() throws IOException {
            byte[] byArray = new byte[this.readUnsignedShort()];
            this.readFully(byArray);
            return new String(byArray, StandardCharsets.UTF_8);
        }

        LittleEndianDataInputStream(InputStream inputStream, 1 var2_2) {
            this(inputStream);
        }
    }
}

