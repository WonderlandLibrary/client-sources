/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;

@Deprecated
public abstract class LegacySoundRewriter<T extends BackwardsProtocol<?, ?, ?, ?>>
extends RewriterBase<T> {
    protected final Int2ObjectMap<SoundData> soundRewrites = new Int2ObjectOpenHashMap<SoundData>(64);

    protected LegacySoundRewriter(T t) {
        super(t);
    }

    public SoundData added(int n, int n2) {
        return this.added(n, n2, -1.0f);
    }

    public SoundData added(int n, int n2, float f) {
        SoundData soundData = new SoundData(n2, true, f, true);
        this.soundRewrites.put(n, soundData);
        return soundData;
    }

    public SoundData removed(int n) {
        SoundData soundData = new SoundData(-1, false, -1.0f, false);
        this.soundRewrites.put(n, soundData);
        return soundData;
    }

    public int handleSounds(int n) {
        int n2 = n;
        SoundData soundData = (SoundData)this.soundRewrites.get(n);
        if (soundData != null) {
            return soundData.getReplacementSound();
        }
        for (Int2ObjectMap.Entry entry : this.soundRewrites.int2ObjectEntrySet()) {
            if (n <= entry.getIntKey()) continue;
            if (((SoundData)entry.getValue()).isAdded()) {
                --n2;
                continue;
            }
            ++n2;
        }
        return n2;
    }

    public boolean hasPitch(int n) {
        SoundData soundData = (SoundData)this.soundRewrites.get(n);
        return soundData != null && soundData.isChangePitch();
    }

    public float handlePitch(int n) {
        SoundData soundData = (SoundData)this.soundRewrites.get(n);
        return soundData != null ? soundData.getNewPitch() : 1.0f;
    }

    public static final class SoundData {
        private final int replacementSound;
        private final boolean changePitch;
        private final float newPitch;
        private final boolean added;

        public SoundData(int n, boolean bl, float f, boolean bl2) {
            this.replacementSound = n;
            this.changePitch = bl;
            this.newPitch = f;
            this.added = bl2;
        }

        public int getReplacementSound() {
            return this.replacementSound;
        }

        public boolean isChangePitch() {
            return this.changePitch;
        }

        public float getNewPitch() {
            return this.newPitch;
        }

        public boolean isAdded() {
            return this.added;
        }
    }
}

