/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.audio.IAudioStream;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisAlloc;
import org.lwjgl.stb.STBVorbisInfo;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class OggAudioStream
implements IAudioStream {
    private long pointer;
    private final AudioFormat format;
    private final InputStream stream;
    private ByteBuffer buffer = MemoryUtil.memAlloc(8192);

    public OggAudioStream(InputStream inputStream) throws IOException {
        this.stream = inputStream;
        this.buffer.limit(0);
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            while (this.pointer == 0L) {
                if (!this.readToBuffer()) {
                    throw new IOException("Failed to find Ogg header");
                }
                int n = this.buffer.position();
                this.buffer.position(0);
                this.pointer = STBVorbis.stb_vorbis_open_pushdata(this.buffer, intBuffer, intBuffer2, (STBVorbisAlloc)null);
                this.buffer.position(n);
                int n2 = intBuffer2.get(0);
                if (n2 == 1) {
                    this.clearInputBuffer();
                    continue;
                }
                if (n2 == 0) continue;
                throw new IOException("Failed to read Ogg file " + n2);
            }
            this.buffer.position(this.buffer.position() + intBuffer.get(0));
            STBVorbisInfo sTBVorbisInfo = STBVorbisInfo.mallocStack(memoryStack);
            STBVorbis.stb_vorbis_get_info(this.pointer, sTBVorbisInfo);
            this.format = new AudioFormat(sTBVorbisInfo.sample_rate(), 16, sTBVorbisInfo.channels(), true, false);
        }
    }

    private boolean readToBuffer() throws IOException {
        int n = this.buffer.limit();
        int n2 = this.buffer.capacity() - n;
        if (n2 == 0) {
            return false;
        }
        byte[] byArray = new byte[n2];
        int n3 = this.stream.read(byArray);
        if (n3 == -1) {
            return true;
        }
        int n4 = this.buffer.position();
        this.buffer.limit(n + n3);
        this.buffer.position(n);
        this.buffer.put(byArray, 0, n3);
        this.buffer.position(n4);
        return false;
    }

    private void clearInputBuffer() {
        boolean bl;
        boolean bl2 = this.buffer.position() == 0;
        boolean bl3 = bl = this.buffer.position() == this.buffer.limit();
        if (bl && !bl2) {
            this.buffer.position(0);
            this.buffer.limit(0);
        } else {
            ByteBuffer byteBuffer = MemoryUtil.memAlloc(bl2 ? 2 * this.buffer.capacity() : this.buffer.capacity());
            byteBuffer.put(this.buffer);
            MemoryUtil.memFree(this.buffer);
            byteBuffer.flip();
            this.buffer = byteBuffer;
        }
    }

    private boolean readOgg(Buffer buffer) throws IOException {
        if (this.pointer == 0L) {
            return true;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            PointerBuffer pointerBuffer = memoryStack.mallocPointer(1);
            IntBuffer intBuffer = memoryStack.mallocInt(1);
            IntBuffer intBuffer2 = memoryStack.mallocInt(1);
            while (true) {
                int n;
                int n2 = STBVorbis.stb_vorbis_decode_frame_pushdata(this.pointer, this.buffer, intBuffer, pointerBuffer, intBuffer2);
                this.buffer.position(this.buffer.position() + n2);
                int n3 = STBVorbis.stb_vorbis_get_error(this.pointer);
                if (n3 == 1) {
                    this.clearInputBuffer();
                    if (this.readToBuffer()) continue;
                    n = 0;
                    return n != 0;
                }
                if (n3 != 0) {
                    throw new IOException("Failed to read Ogg file " + n3);
                }
                n = intBuffer2.get(0);
                if (n == 0) continue;
                int n4 = intBuffer.get(0);
                PointerBuffer pointerBuffer2 = pointerBuffer.getPointerBuffer(n4);
                if (n4 != 1) {
                    if (n4 == 2) {
                        this.copyFromDualChannels(pointerBuffer2.getFloatBuffer(0, n), pointerBuffer2.getFloatBuffer(1, n), buffer);
                        boolean bl = true;
                        return bl;
                    }
                    throw new IllegalStateException("Invalid number of channels: " + n4);
                }
                this.copyFromSingleChannel(pointerBuffer2.getFloatBuffer(0, n), buffer);
                boolean bl = true;
                return bl;
            }
        }
    }

    private void copyFromSingleChannel(FloatBuffer floatBuffer, Buffer buffer) {
        while (floatBuffer.hasRemaining()) {
            buffer.appendOggAudioBytes(floatBuffer.get());
        }
    }

    private void copyFromDualChannels(FloatBuffer floatBuffer, FloatBuffer floatBuffer2, Buffer buffer) {
        while (floatBuffer.hasRemaining() && floatBuffer2.hasRemaining()) {
            buffer.appendOggAudioBytes(floatBuffer.get());
            buffer.appendOggAudioBytes(floatBuffer2.get());
        }
    }

    @Override
    public void close() throws IOException {
        if (this.pointer != 0L) {
            STBVorbis.stb_vorbis_close(this.pointer);
            this.pointer = 0L;
        }
        MemoryUtil.memFree(this.buffer);
        this.stream.close();
    }

    @Override
    public AudioFormat getAudioFormat() {
        return this.format;
    }

    @Override
    public ByteBuffer readOggSoundWithCapacity(int n) throws IOException {
        Buffer buffer = new Buffer(n + 8192);
        while (this.readOgg(buffer) && buffer.filledBytes < n) {
        }
        return buffer.mergeBuffers();
    }

    public ByteBuffer readOggSound() throws IOException {
        Buffer buffer = new Buffer(16384);
        while (this.readOgg(buffer)) {
        }
        return buffer.mergeBuffers();
    }

    static class Buffer {
        private final List<ByteBuffer> storedBuffers = Lists.newArrayList();
        private final int bufferCapacity;
        private int filledBytes;
        private ByteBuffer currentBuffer;

        public Buffer(int n) {
            this.bufferCapacity = n + 1 & 0xFFFFFFFE;
            this.createBuffer();
        }

        private void createBuffer() {
            this.currentBuffer = BufferUtils.createByteBuffer(this.bufferCapacity);
        }

        public void appendOggAudioBytes(float f) {
            if (this.currentBuffer.remaining() == 0) {
                this.currentBuffer.flip();
                this.storedBuffers.add(this.currentBuffer);
                this.createBuffer();
            }
            int n = MathHelper.clamp((int)(f * 32767.5f - 0.5f), Short.MIN_VALUE, Short.MAX_VALUE);
            this.currentBuffer.putShort((short)n);
            this.filledBytes += 2;
        }

        public ByteBuffer mergeBuffers() {
            this.currentBuffer.flip();
            if (this.storedBuffers.isEmpty()) {
                return this.currentBuffer;
            }
            ByteBuffer byteBuffer = BufferUtils.createByteBuffer(this.filledBytes);
            this.storedBuffers.forEach(byteBuffer::put);
            byteBuffer.put(this.currentBuffer);
            byteBuffer.flip();
            return byteBuffer;
        }
    }
}

