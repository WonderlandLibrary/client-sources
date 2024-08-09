/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteProcessor;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

@Beta
@GwtIncompatible
public final class ByteStreams {
    private static final int ZERO_COPY_CHUNK_SIZE = 524288;
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream(){

        @Override
        public void write(int n) {
        }

        @Override
        public void write(byte[] byArray) {
            Preconditions.checkNotNull(byArray);
        }

        @Override
        public void write(byte[] byArray, int n, int n2) {
            Preconditions.checkNotNull(byArray);
        }

        public String toString() {
            return "ByteStreams.nullOutputStream()";
        }
    };

    static byte[] createBuffer() {
        return new byte[8192];
    }

    private ByteStreams() {
    }

    @CanIgnoreReturnValue
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(outputStream);
        byte[] byArray = ByteStreams.createBuffer();
        long l = 0L;
        while ((n = inputStream.read(byArray)) != -1) {
            outputStream.write(byArray, 0, n);
            l += (long)n;
        }
        return l;
    }

    @CanIgnoreReturnValue
    public static long copy(ReadableByteChannel readableByteChannel, WritableByteChannel writableByteChannel) throws IOException {
        Preconditions.checkNotNull(readableByteChannel);
        Preconditions.checkNotNull(writableByteChannel);
        if (readableByteChannel instanceof FileChannel) {
            long l;
            long l2;
            FileChannel fileChannel = (FileChannel)readableByteChannel;
            long l3 = l2 = fileChannel.position();
            do {
                l = fileChannel.transferTo(l3, 524288L, writableByteChannel);
                fileChannel.position(l3 += l);
            } while (l > 0L || l3 < fileChannel.size());
            return l3 - l2;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(ByteStreams.createBuffer());
        long l = 0L;
        while (readableByteChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                l += (long)writableByteChannel.write(byteBuffer);
            }
            byteBuffer.clear();
        }
        return l;
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(Math.max(32, inputStream.available()));
        ByteStreams.copy(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    static byte[] toByteArray(InputStream inputStream, int n) throws IOException {
        int n2;
        int n3;
        byte[] byArray = new byte[n];
        for (int i = n; i > 0; i -= n3) {
            n2 = n - i;
            n3 = inputStream.read(byArray, n2, i);
            if (n3 != -1) continue;
            return Arrays.copyOf(byArray, n2);
        }
        n2 = inputStream.read();
        if (n2 == -1) {
            return byArray;
        }
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream(null);
        fastByteArrayOutputStream.write(n2);
        ByteStreams.copy(inputStream, fastByteArrayOutputStream);
        byte[] byArray2 = new byte[byArray.length + fastByteArrayOutputStream.size()];
        System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
        fastByteArrayOutputStream.writeTo(byArray2, byArray.length);
        return byArray2;
    }

    @CanIgnoreReturnValue
    public static long exhaust(InputStream inputStream) throws IOException {
        long l;
        long l2 = 0L;
        byte[] byArray = ByteStreams.createBuffer();
        while ((l = (long)inputStream.read(byArray)) != -1L) {
            l2 += l;
        }
        return l2;
    }

    public static ByteArrayDataInput newDataInput(byte[] byArray) {
        return ByteStreams.newDataInput(new ByteArrayInputStream(byArray));
    }

    public static ByteArrayDataInput newDataInput(byte[] byArray, int n) {
        Preconditions.checkPositionIndex(n, byArray.length);
        return ByteStreams.newDataInput(new ByteArrayInputStream(byArray, n, byArray.length - n));
    }

    public static ByteArrayDataInput newDataInput(ByteArrayInputStream byteArrayInputStream) {
        return new ByteArrayDataInputStream(Preconditions.checkNotNull(byteArrayInputStream));
    }

    public static ByteArrayDataOutput newDataOutput() {
        return ByteStreams.newDataOutput(new ByteArrayOutputStream());
    }

    public static ByteArrayDataOutput newDataOutput(int n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("Invalid size: %s", n));
        }
        return ByteStreams.newDataOutput(new ByteArrayOutputStream(n));
    }

    public static ByteArrayDataOutput newDataOutput(ByteArrayOutputStream byteArrayOutputStream) {
        return new ByteArrayDataOutputStream(Preconditions.checkNotNull(byteArrayOutputStream));
    }

    public static OutputStream nullOutputStream() {
        return NULL_OUTPUT_STREAM;
    }

    public static InputStream limit(InputStream inputStream, long l) {
        return new LimitedInputStream(inputStream, l);
    }

    public static void readFully(InputStream inputStream, byte[] byArray) throws IOException {
        ByteStreams.readFully(inputStream, byArray, 0, byArray.length);
    }

    public static void readFully(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3 = ByteStreams.read(inputStream, byArray, n, n2);
        if (n3 != n2) {
            throw new EOFException("reached end of stream after reading " + n3 + " bytes; " + n2 + " bytes expected");
        }
    }

    public static void skipFully(InputStream inputStream, long l) throws IOException {
        long l2 = ByteStreams.skipUpTo(inputStream, l);
        if (l2 < l) {
            throw new EOFException("reached end of stream after skipping " + l2 + " bytes; " + l + " bytes expected");
        }
    }

    static long skipUpTo(InputStream inputStream, long l) throws IOException {
        int n;
        long l2;
        long l3;
        long l4;
        byte[] byArray = ByteStreams.createBuffer();
        for (l3 = 0L; l3 < l && ((l4 = ByteStreams.skipSafely(inputStream, l2 = l - l3)) != 0L || (l4 = (long)inputStream.read(byArray, 0, n = (int)Math.min(l2, (long)byArray.length))) != -1L); l3 += l4) {
        }
        return l3;
    }

    private static long skipSafely(InputStream inputStream, long l) throws IOException {
        int n = inputStream.available();
        return n == 0 ? 0L : inputStream.skip(Math.min((long)n, l));
    }

    @CanIgnoreReturnValue
    public static <T> T readBytes(InputStream inputStream, ByteProcessor<T> byteProcessor) throws IOException {
        int n;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byteProcessor);
        byte[] byArray = ByteStreams.createBuffer();
        while ((n = inputStream.read(byArray)) != -1 && byteProcessor.processBytes(byArray, 0, n)) {
        }
        return byteProcessor.getResult();
    }

    @CanIgnoreReturnValue
    public static int read(InputStream inputStream, byte[] byArray, int n, int n2) throws IOException {
        int n3;
        int n4;
        Preconditions.checkNotNull(inputStream);
        Preconditions.checkNotNull(byArray);
        if (n2 < 0) {
            throw new IndexOutOfBoundsException("len is negative");
        }
        for (n3 = 0; n3 < n2 && (n4 = inputStream.read(byArray, n + n3, n2 - n3)) != -1; n3 += n4) {
        }
        return n3;
    }

    private static final class LimitedInputStream
    extends FilterInputStream {
        private long left;
        private long mark = -1L;

        LimitedInputStream(InputStream inputStream, long l) {
            super(inputStream);
            Preconditions.checkNotNull(inputStream);
            Preconditions.checkArgument(l >= 0L, "limit must be non-negative");
            this.left = l;
        }

        @Override
        public int available() throws IOException {
            return (int)Math.min((long)this.in.available(), this.left);
        }

        @Override
        public synchronized void mark(int n) {
            this.in.mark(n);
            this.mark = this.left;
        }

        @Override
        public int read() throws IOException {
            if (this.left == 0L) {
                return 1;
            }
            int n = this.in.read();
            if (n != -1) {
                --this.left;
            }
            return n;
        }

        @Override
        public int read(byte[] byArray, int n, int n2) throws IOException {
            if (this.left == 0L) {
                return 1;
            }
            int n3 = this.in.read(byArray, n, n2 = (int)Math.min((long)n2, this.left));
            if (n3 != -1) {
                this.left -= (long)n3;
            }
            return n3;
        }

        @Override
        public synchronized void reset() throws IOException {
            if (!this.in.markSupported()) {
                throw new IOException("Mark not supported");
            }
            if (this.mark == -1L) {
                throw new IOException("Mark not set");
            }
            this.in.reset();
            this.left = this.mark;
        }

        @Override
        public long skip(long l) throws IOException {
            l = Math.min(l, this.left);
            long l2 = this.in.skip(l);
            this.left -= l2;
            return l2;
        }
    }

    private static class ByteArrayDataOutputStream
    implements ByteArrayDataOutput {
        final DataOutput output;
        final ByteArrayOutputStream byteArrayOutputSteam;

        ByteArrayDataOutputStream(ByteArrayOutputStream byteArrayOutputStream) {
            this.byteArrayOutputSteam = byteArrayOutputStream;
            this.output = new DataOutputStream(byteArrayOutputStream);
        }

        @Override
        public void write(int n) {
            try {
                this.output.write(n);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void write(byte[] byArray) {
            try {
                this.output.write(byArray);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void write(byte[] byArray, int n, int n2) {
            try {
                this.output.write(byArray, n, n2);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeBoolean(boolean bl) {
            try {
                this.output.writeBoolean(bl);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeByte(int n) {
            try {
                this.output.writeByte(n);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeBytes(String string) {
            try {
                this.output.writeBytes(string);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeChar(int n) {
            try {
                this.output.writeChar(n);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeChars(String string) {
            try {
                this.output.writeChars(string);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeDouble(double d) {
            try {
                this.output.writeDouble(d);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeFloat(float f) {
            try {
                this.output.writeFloat(f);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeInt(int n) {
            try {
                this.output.writeInt(n);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeLong(long l) {
            try {
                this.output.writeLong(l);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeShort(int n) {
            try {
                this.output.writeShort(n);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public void writeUTF(String string) {
            try {
                this.output.writeUTF(string);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public byte[] toByteArray() {
            return this.byteArrayOutputSteam.toByteArray();
        }
    }

    private static class ByteArrayDataInputStream
    implements ByteArrayDataInput {
        final DataInput input;

        ByteArrayDataInputStream(ByteArrayInputStream byteArrayInputStream) {
            this.input = new DataInputStream(byteArrayInputStream);
        }

        @Override
        public void readFully(byte[] byArray) {
            try {
                this.input.readFully(byArray);
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public void readFully(byte[] byArray, int n, int n2) {
            try {
                this.input.readFully(byArray, n, n2);
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int skipBytes(int n) {
            try {
                return this.input.skipBytes(n);
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public boolean readBoolean() {
            try {
                return this.input.readBoolean();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public byte readByte() {
            try {
                return this.input.readByte();
            } catch (EOFException eOFException) {
                throw new IllegalStateException(eOFException);
            } catch (IOException iOException) {
                throw new AssertionError((Object)iOException);
            }
        }

        @Override
        public int readUnsignedByte() {
            try {
                return this.input.readUnsignedByte();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public short readShort() {
            try {
                return this.input.readShort();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int readUnsignedShort() {
            try {
                return this.input.readUnsignedShort();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public char readChar() {
            try {
                return this.input.readChar();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public int readInt() {
            try {
                return this.input.readInt();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public long readLong() {
            try {
                return this.input.readLong();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public float readFloat() {
            try {
                return this.input.readFloat();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public double readDouble() {
            try {
                return this.input.readDouble();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public String readLine() {
            try {
                return this.input.readLine();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }

        @Override
        public String readUTF() {
            try {
                return this.input.readUTF();
            } catch (IOException iOException) {
                throw new IllegalStateException(iOException);
            }
        }
    }

    private static final class FastByteArrayOutputStream
    extends ByteArrayOutputStream {
        private FastByteArrayOutputStream() {
        }

        void writeTo(byte[] byArray, int n) {
            System.arraycopy(this.buf, 0, byArray, n, this.count);
        }

        FastByteArrayOutputStream(1 var1_1) {
            this();
        }
    }
}

