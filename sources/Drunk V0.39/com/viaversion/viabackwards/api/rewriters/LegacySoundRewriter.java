/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Iterator;

@Deprecated
public abstract class LegacySoundRewriter<T extends BackwardsProtocol>
extends RewriterBase<T> {
    protected final Int2ObjectMap<SoundData> soundRewrites = new Int2ObjectOpenHashMap<SoundData>(64);

    protected LegacySoundRewriter(T protocol) {
        super(protocol);
    }

    public SoundData added(int id, int replacement) {
        return this.added(id, replacement, -1.0f);
    }

    public SoundData added(int id, int replacement, float newPitch) {
        SoundData data = new SoundData(replacement, true, newPitch, true);
        this.soundRewrites.put(id, data);
        return data;
    }

    public SoundData removed(int id) {
        SoundData data = new SoundData(-1, false, -1.0f, false);
        this.soundRewrites.put(id, data);
        return data;
    }

    public int handleSounds(int soundId) {
        int newSoundId = soundId;
        SoundData data = (SoundData)this.soundRewrites.get(soundId);
        if (data != null) {
            return data.getReplacementSound();
        }
        Iterator iterator = this.soundRewrites.int2ObjectEntrySet().iterator();
        while (iterator.hasNext()) {
            Int2ObjectMap.Entry entry = (Int2ObjectMap.Entry)iterator.next();
            if (soundId <= entry.getIntKey()) continue;
            if (((SoundData)entry.getValue()).isAdded()) {
                --newSoundId;
                continue;
            }
            ++newSoundId;
        }
        return newSoundId;
    }

    public boolean hasPitch(int soundId) {
        SoundData data = (SoundData)this.soundRewrites.get(soundId);
        if (data == null) return false;
        if (!data.isChangePitch()) return false;
        return true;
    }

    public float handlePitch(int soundId) {
        SoundData data = (SoundData)this.soundRewrites.get(soundId);
        if (data == null) return 1.0f;
        float f = data.getNewPitch();
        return f;
    }

    public static final class SoundData {
        private final int replacementSound;
        private final boolean changePitch;
        private final float newPitch;
        private final boolean added;

        public SoundData(int replacementSound, boolean changePitch, float newPitch, boolean added) {
            this.replacementSound = replacementSound;
            this.changePitch = changePitch;
            this.newPitch = newPitch;
            this.added = added;
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

