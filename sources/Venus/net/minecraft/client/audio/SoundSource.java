/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import javax.sound.sampled.AudioFormat;
import net.minecraft.client.audio.ALUtils;
import net.minecraft.client.audio.AudioStreamBuffer;
import net.minecraft.client.audio.IAudioStream;
import net.minecraft.util.math.vector.Vector3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL10;

public class SoundSource {
    private static final Logger LOGGER = LogManager.getLogger();
    private final int id;
    private final AtomicBoolean playing = new AtomicBoolean(true);
    private int defaultByteBufferCapacity = 16384;
    @Nullable
    private IAudioStream audioStream;

    @Nullable
    static SoundSource allocateNewSource() {
        int[] nArray = new int[1];
        AL10.alGenSources(nArray);
        return ALUtils.checkALError("Allocate new source") ? null : new SoundSource(nArray[0]);
    }

    private SoundSource(int n) {
        this.id = n;
    }

    public void close() {
        if (this.playing.compareAndSet(true, true)) {
            AL10.alSourceStop(this.id);
            ALUtils.checkALError("Stop");
            if (this.audioStream != null) {
                try {
                    this.audioStream.close();
                } catch (IOException iOException) {
                    LOGGER.error("Failed to close audio stream", (Throwable)iOException);
                }
                this.removeProcessedBuffers();
                this.audioStream = null;
            }
            AL10.alDeleteSources(new int[]{this.id});
            ALUtils.checkALError("Cleanup");
        }
    }

    public void play() {
        AL10.alSourcePlay(this.id);
    }

    private int getState() {
        return !this.playing.get() ? 4116 : AL10.alGetSourcei(this.id, 4112);
    }

    public void pause() {
        if (this.getState() == 4114) {
            AL10.alSourcePause(this.id);
        }
    }

    public void resume() {
        if (this.getState() == 4115) {
            AL10.alSourcePlay(this.id);
        }
    }

    public void stop() {
        if (this.playing.get()) {
            AL10.alSourceStop(this.id);
            ALUtils.checkALError("Stop");
        }
    }

    public boolean isStopped() {
        return this.getState() == 4116;
    }

    public void updateSource(Vector3d vector3d) {
        AL10.alSourcefv(this.id, 4100, new float[]{(float)vector3d.x, (float)vector3d.y, (float)vector3d.z});
    }

    public void setPitch(float f) {
        AL10.alSourcef(this.id, 4099, f);
    }

    public void setLooping(boolean bl) {
        AL10.alSourcei(this.id, 4103, bl ? 1 : 0);
    }

    public void setGain(float f) {
        AL10.alSourcef(this.id, 4106, f);
    }

    public void setNoAttenuation() {
        AL10.alSourcei(this.id, 53248, 0);
    }

    public void setLinearAttenuation(float f) {
        AL10.alSourcei(this.id, 53248, 53251);
        AL10.alSourcef(this.id, 4131, f);
        AL10.alSourcef(this.id, 4129, 1.0f);
        AL10.alSourcef(this.id, 4128, 0.0f);
    }

    public void setRelative(boolean bl) {
        AL10.alSourcei(this.id, 514, bl ? 1 : 0);
    }

    public void bindBuffer(AudioStreamBuffer audioStreamBuffer) {
        audioStreamBuffer.getBuffer().ifPresent(this::lambda$bindBuffer$0);
    }

    public void playStreamableSounds(IAudioStream iAudioStream) {
        this.audioStream = iAudioStream;
        AudioFormat audioFormat = iAudioStream.getAudioFormat();
        this.defaultByteBufferCapacity = SoundSource.getSampleSize(audioFormat, 1);
        this.readFromStream(4);
    }

    private static int getSampleSize(AudioFormat audioFormat, int n) {
        return (int)((float)(n * audioFormat.getSampleSizeInBits()) / 8.0f * (float)audioFormat.getChannels() * audioFormat.getSampleRate());
    }

    private void readFromStream(int n) {
        if (this.audioStream != null) {
            try {
                for (int i = 0; i < n; ++i) {
                    ByteBuffer byteBuffer = this.audioStream.readOggSoundWithCapacity(this.defaultByteBufferCapacity);
                    if (byteBuffer == null) continue;
                    new AudioStreamBuffer(byteBuffer, this.audioStream.getAudioFormat()).getUntrackedBuffer().ifPresent(this::lambda$readFromStream$1);
                }
            } catch (IOException iOException) {
                LOGGER.error("Failed to read from audio stream", (Throwable)iOException);
            }
        }
    }

    public void tick() {
        if (this.audioStream != null) {
            int n = this.removeProcessedBuffers();
            this.readFromStream(n);
        }
    }

    private int removeProcessedBuffers() {
        int n = AL10.alGetSourcei(this.id, 4118);
        if (n > 0) {
            int[] nArray = new int[n];
            AL10.alSourceUnqueueBuffers(this.id, nArray);
            ALUtils.checkALError("Unqueue buffers");
            AL10.alDeleteBuffers(nArray);
            ALUtils.checkALError("Remove processed buffers");
        }
        return n;
    }

    private void lambda$readFromStream$1(int n) {
        AL10.alSourceQueueBuffers(this.id, new int[]{n});
    }

    private void lambda$bindBuffer$0(int n) {
        AL10.alSourcei(this.id, 4105, n);
    }
}

