/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.SoundEvent;

public class BackgroundMusicSelector {
    public static final Codec<BackgroundMusicSelector> CODEC = RecordCodecBuilder.create(BackgroundMusicSelector::lambda$static$4);
    private final SoundEvent soundEvent;
    private final int minDelay;
    private final int maxDelay;
    private final boolean replaceCurrentMusic;

    public BackgroundMusicSelector(SoundEvent soundEvent, int n, int n2, boolean bl) {
        this.soundEvent = soundEvent;
        this.minDelay = n;
        this.maxDelay = n2;
        this.replaceCurrentMusic = bl;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public int getMinDelay() {
        return this.minDelay;
    }

    public int getMaxDelay() {
        return this.maxDelay;
    }

    public boolean shouldReplaceCurrentMusic() {
        return this.replaceCurrentMusic;
    }

    private static App lambda$static$4(RecordCodecBuilder.Instance instance) {
        return instance.group(((MapCodec)SoundEvent.CODEC.fieldOf("sound")).forGetter(BackgroundMusicSelector::lambda$static$0), ((MapCodec)Codec.INT.fieldOf("min_delay")).forGetter(BackgroundMusicSelector::lambda$static$1), ((MapCodec)Codec.INT.fieldOf("max_delay")).forGetter(BackgroundMusicSelector::lambda$static$2), ((MapCodec)Codec.BOOL.fieldOf("replace_current_music")).forGetter(BackgroundMusicSelector::lambda$static$3)).apply(instance, BackgroundMusicSelector::new);
    }

    private static Boolean lambda$static$3(BackgroundMusicSelector backgroundMusicSelector) {
        return backgroundMusicSelector.replaceCurrentMusic;
    }

    private static Integer lambda$static$2(BackgroundMusicSelector backgroundMusicSelector) {
        return backgroundMusicSelector.maxDelay;
    }

    private static Integer lambda$static$1(BackgroundMusicSelector backgroundMusicSelector) {
        return backgroundMusicSelector.minDelay;
    }

    private static SoundEvent lambda$static$0(BackgroundMusicSelector backgroundMusicSelector) {
        return backgroundMusicSelector.soundEvent;
    }
}

