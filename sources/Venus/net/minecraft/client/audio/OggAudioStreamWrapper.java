/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.audio.IAudioStream;

public class OggAudioStreamWrapper
implements IAudioStream {
    private final IFactory wrapperFactoryOGG;
    private IAudioStream audioStream;
    private final BufferedInputStream inputStream;

    public OggAudioStreamWrapper(IFactory iFactory, InputStream inputStream) throws IOException {
        this.wrapperFactoryOGG = iFactory;
        this.inputStream = new BufferedInputStream(inputStream);
        this.inputStream.mark(Integer.MAX_VALUE);
        this.audioStream = iFactory.create(new Stream(this.inputStream));
    }

    @Override
    public AudioFormat getAudioFormat() {
        return this.audioStream.getAudioFormat();
    }

    @Override
    public ByteBuffer readOggSoundWithCapacity(int n) throws IOException {
        ByteBuffer byteBuffer = this.audioStream.readOggSoundWithCapacity(n);
        if (!byteBuffer.hasRemaining()) {
            this.audioStream.close();
            this.inputStream.reset();
            this.audioStream = this.wrapperFactoryOGG.create(new Stream(this.inputStream));
            byteBuffer = this.audioStream.readOggSoundWithCapacity(n);
        }
        return byteBuffer;
    }

    @Override
    public void close() throws IOException {
        this.audioStream.close();
        this.inputStream.close();
    }

    @FunctionalInterface
    public static interface IFactory {
        public IAudioStream create(InputStream var1) throws IOException;
    }

    static class Stream
    extends FilterInputStream {
        private Stream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public void close() {
        }
    }
}

