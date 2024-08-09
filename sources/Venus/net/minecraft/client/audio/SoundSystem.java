/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Sets;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.audio.ALUtils;
import net.minecraft.client.audio.Listener;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;
import org.lwjgl.system.MemoryStack;

public class SoundSystem {
    private static final Logger LOGGER = LogManager.getLogger();
    private long device;
    private long context;
    private static final IHandler DUMMY_HANDLER = new IHandler(){

        @Override
        @Nullable
        public SoundSource getSource() {
            return null;
        }

        @Override
        public boolean freeSource(SoundSource soundSource) {
            return true;
        }

        @Override
        public void unload() {
        }

        @Override
        public int getMaxSoundSources() {
            return 1;
        }

        @Override
        public int getActiveSoundSourceCount() {
            return 1;
        }
    };
    private IHandler staticHandler = DUMMY_HANDLER;
    private IHandler streamingHandler = DUMMY_HANDLER;
    private final Listener listener = new Listener();

    public void init() {
        this.device = SoundSystem.openDevice();
        ALCCapabilities aLCCapabilities = ALC.createCapabilities(this.device);
        if (ALUtils.checkALCError(this.device, "Get capabilities")) {
            throw new IllegalStateException("Failed to get OpenAL capabilities");
        }
        if (!aLCCapabilities.OpenALC11) {
            throw new IllegalStateException("OpenAL 1.1 not supported");
        }
        this.context = ALC10.alcCreateContext(this.device, (IntBuffer)null);
        ALC10.alcMakeContextCurrent(this.context);
        int n = this.getMaxChannels();
        int n2 = MathHelper.clamp((int)MathHelper.sqrt(n), 2, 8);
        int n3 = MathHelper.clamp(n - n2, 8, 255);
        this.staticHandler = new HandlerImpl(n3);
        this.streamingHandler = new HandlerImpl(n2);
        ALCapabilities aLCapabilities = AL.createCapabilities(aLCCapabilities);
        ALUtils.checkALError("Initialization");
        if (!aLCapabilities.AL_EXT_source_distance_model) {
            throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
        }
        AL10.alEnable(512);
        if (!aLCapabilities.AL_EXT_LINEAR_DISTANCE) {
            throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
        }
        ALUtils.checkALError("Enable per-source distance models");
        LOGGER.info("OpenAL initialized.");
    }

    private int getMaxChannels() {
        int n;
        try (MemoryStack memoryStack = MemoryStack.stackPush();){
            int n2;
            int n3;
            int n4 = ALC10.alcGetInteger(this.device, 4098);
            if (ALUtils.checkALCError(this.device, "Get attributes size")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            IntBuffer intBuffer = memoryStack.mallocInt(n4);
            ALC10.alcGetIntegerv(this.device, 4099, intBuffer);
            if (ALUtils.checkALCError(this.device, "Get attributes")) {
                throw new IllegalStateException("Failed to get OpenAL attributes");
            }
            int n5 = 0;
            do {
                if (n5 >= n4) {
                    int n6 = 30;
                    return n6;
                }
                if ((n3 = intBuffer.get(n5++)) == 0) {
                    int n7 = 30;
                    return n7;
                }
                n2 = intBuffer.get(n5++);
            } while (n3 != 4112);
            n = n2;
        }
        return n;
    }

    private static long openDevice() {
        for (int i = 0; i < 3; ++i) {
            long l = ALC10.alcOpenDevice((ByteBuffer)null);
            if (l == 0L || ALUtils.checkALCError(l, "Open device")) continue;
            return l;
        }
        throw new IllegalStateException("Failed to open OpenAL device");
    }

    public void unload() {
        this.staticHandler.unload();
        this.streamingHandler.unload();
        ALC10.alcDestroyContext(this.context);
        if (this.device != 0L) {
            ALC10.alcCloseDevice(this.device);
        }
    }

    public Listener getListener() {
        return this.listener;
    }

    @Nullable
    public SoundSource getSource(Mode mode) {
        return (mode == Mode.STREAMING ? this.streamingHandler : this.staticHandler).getSource();
    }

    public void release(SoundSource soundSource) {
        if (!this.staticHandler.freeSource(soundSource) && !this.streamingHandler.freeSource(soundSource)) {
            throw new IllegalStateException("Tried to release unknown channel");
        }
    }

    public String getDebugString() {
        return String.format("Sounds: %d/%d + %d/%d", this.staticHandler.getActiveSoundSourceCount(), this.staticHandler.getMaxSoundSources(), this.streamingHandler.getActiveSoundSourceCount(), this.streamingHandler.getMaxSoundSources());
    }

    static interface IHandler {
        @Nullable
        public SoundSource getSource();

        public boolean freeSource(SoundSource var1);

        public void unload();

        public int getMaxSoundSources();

        public int getActiveSoundSourceCount();
    }

    static class HandlerImpl
    implements IHandler {
        private final int maxSoundSources;
        private final Set<SoundSource> activeSoundSources = Sets.newIdentityHashSet();

        public HandlerImpl(int n) {
            this.maxSoundSources = n;
        }

        @Override
        @Nullable
        public SoundSource getSource() {
            if (this.activeSoundSources.size() >= this.maxSoundSources) {
                LOGGER.warn("Maximum sound pool size {} reached", (Object)this.maxSoundSources);
                return null;
            }
            SoundSource soundSource = SoundSource.allocateNewSource();
            if (soundSource != null) {
                this.activeSoundSources.add(soundSource);
            }
            return soundSource;
        }

        @Override
        public boolean freeSource(SoundSource soundSource) {
            if (!this.activeSoundSources.remove(soundSource)) {
                return true;
            }
            soundSource.close();
            return false;
        }

        @Override
        public void unload() {
            this.activeSoundSources.forEach(SoundSource::close);
            this.activeSoundSources.clear();
        }

        @Override
        public int getMaxSoundSources() {
            return this.maxSoundSources;
        }

        @Override
        public int getActiveSoundSourceCount() {
            return this.activeSoundSources.size();
        }
    }

    public static enum Mode {
        STATIC,
        STREAMING;

    }
}

