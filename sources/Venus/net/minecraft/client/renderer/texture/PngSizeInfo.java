/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import org.lwjgl.stb.STBIEOFCallback;
import org.lwjgl.stb.STBIIOCallbacks;
import org.lwjgl.stb.STBIReadCallback;
import org.lwjgl.stb.STBISkipCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class PngSizeInfo {
    public final int width;
    public final int height;
    private static final Object STATIC_MONITOR = new Object();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PngSizeInfo(String string, InputStream inputStream) throws IOException {
        Object object = STATIC_MONITOR;
        synchronized (object) {
            try (MemoryStack memoryStack = MemoryStack.stackPush();
                 Reader reader = PngSizeInfo.func_195695_a(inputStream);
                 STBIReadCallback sTBIReadCallback = STBIReadCallback.create(reader::func_195682_a);
                 STBISkipCallback sTBISkipCallback = STBISkipCallback.create(reader::func_195686_a);
                 STBIEOFCallback sTBIEOFCallback = STBIEOFCallback.create(reader::func_195685_a);){
                STBIIOCallbacks sTBIIOCallbacks = STBIIOCallbacks.mallocStack(memoryStack);
                sTBIIOCallbacks.read(sTBIReadCallback);
                sTBIIOCallbacks.skip(sTBISkipCallback);
                sTBIIOCallbacks.eof(sTBIEOFCallback);
                IntBuffer intBuffer = memoryStack.mallocInt(1);
                IntBuffer intBuffer2 = memoryStack.mallocInt(1);
                IntBuffer intBuffer3 = memoryStack.mallocInt(1);
                if (!STBImage.stbi_info_from_callbacks(sTBIIOCallbacks, 0L, intBuffer, intBuffer2, intBuffer3)) {
                    throw new IOException("Could not read info from the PNG file " + string + " " + STBImage.stbi_failure_reason());
                }
                this.width = intBuffer.get(0);
                this.height = intBuffer2.get(0);
            }
        }
    }

    public String toString() {
        return this.width + " x " + this.height;
    }

    private static Reader func_195695_a(InputStream inputStream) {
        return inputStream instanceof FileInputStream ? new ReaderSeekable(((FileInputStream)inputStream).getChannel()) : new ReaderBuffer(Channels.newChannel(inputStream));
    }

    static abstract class Reader
    implements AutoCloseable {
        protected boolean field_195687_a;

        private Reader() {
        }

        int func_195682_a(long l, long l2, int n) {
            try {
                return this.func_195683_b(l2, n);
            } catch (IOException iOException) {
                this.field_195687_a = true;
                return 1;
            }
        }

        void func_195686_a(long l, int n) {
            try {
                this.func_195684_a(n);
            } catch (IOException iOException) {
                this.field_195687_a = true;
            }
        }

        int func_195685_a(long l) {
            return this.field_195687_a ? 1 : 0;
        }

        protected abstract int func_195683_b(long var1, int var3) throws IOException;

        protected abstract void func_195684_a(int var1) throws IOException;

        @Override
        public abstract void close() throws IOException;
    }

    static class ReaderSeekable
    extends Reader {
        private final SeekableByteChannel channel;

        private ReaderSeekable(SeekableByteChannel seekableByteChannel) {
            this.channel = seekableByteChannel;
        }

        @Override
        public int func_195683_b(long l, int n) throws IOException {
            ByteBuffer byteBuffer = MemoryUtil.memByteBuffer(l, n);
            return this.channel.read(byteBuffer);
        }

        @Override
        public void func_195684_a(int n) throws IOException {
            this.channel.position(this.channel.position() + (long)n);
        }

        @Override
        public int func_195685_a(long l) {
            return super.func_195685_a(l) != 0 && this.channel.isOpen() ? 1 : 0;
        }

        @Override
        public void close() throws IOException {
            this.channel.close();
        }
    }

    static class ReaderBuffer
    extends Reader {
        private final ReadableByteChannel channel;
        private long field_195690_c = MemoryUtil.nmemAlloc(128L);
        private int field_195691_d = 128;
        private int field_195692_e;
        private int field_195693_f;

        private ReaderBuffer(ReadableByteChannel readableByteChannel) {
            this.channel = readableByteChannel;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void func_195688_b(int n) throws IOException {
            ByteBuffer byteBuffer = MemoryUtil.memByteBuffer(this.field_195690_c, this.field_195691_d);
            if (n + this.field_195693_f > this.field_195691_d) {
                this.field_195691_d = n + this.field_195693_f;
                byteBuffer = MemoryUtil.memRealloc(byteBuffer, this.field_195691_d);
                this.field_195690_c = MemoryUtil.memAddress(byteBuffer);
            }
            byteBuffer.position(this.field_195692_e);
            while (n + this.field_195693_f > this.field_195692_e) {
                try {
                    int n2 = this.channel.read(byteBuffer);
                    if (n2 != -1) continue;
                    break;
                } finally {
                    this.field_195692_e = byteBuffer.position();
                }
            }
        }

        @Override
        public int func_195683_b(long l, int n) throws IOException {
            this.func_195688_b(n);
            if (n + this.field_195693_f > this.field_195692_e) {
                n = this.field_195692_e - this.field_195693_f;
            }
            MemoryUtil.memCopy(this.field_195690_c + (long)this.field_195693_f, l, n);
            this.field_195693_f += n;
            return n;
        }

        @Override
        public void func_195684_a(int n) throws IOException {
            if (n > 0) {
                this.func_195688_b(n);
                if (n + this.field_195693_f > this.field_195692_e) {
                    throw new EOFException("Can't skip past the EOF.");
                }
            }
            if (this.field_195693_f + n < 0) {
                throw new IOException("Can't seek before the beginning: " + (this.field_195693_f + n));
            }
            this.field_195693_f += n;
        }

        @Override
        public void close() throws IOException {
            MemoryUtil.nmemFree(this.field_195690_c);
            this.channel.close();
        }
    }
}

