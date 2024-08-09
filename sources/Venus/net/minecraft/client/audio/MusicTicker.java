/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.BackgroundMusicSelector;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.math.MathHelper;

public class MusicTicker {
    private final Random random = new Random();
    private final Minecraft client;
    @Nullable
    private ISound currentMusic;
    private int timeUntilNextMusic = 100;

    public MusicTicker(Minecraft minecraft) {
        this.client = minecraft;
    }

    public void tick() {
        BackgroundMusicSelector backgroundMusicSelector = this.client.getBackgroundMusicSelector();
        if (this.currentMusic != null) {
            if (!backgroundMusicSelector.getSoundEvent().getName().equals(this.currentMusic.getSoundLocation()) && backgroundMusicSelector.shouldReplaceCurrentMusic()) {
                this.client.getSoundHandler().stop(this.currentMusic);
                this.timeUntilNextMusic = MathHelper.nextInt(this.random, 0, backgroundMusicSelector.getMinDelay() / 2);
            }
            if (!this.client.getSoundHandler().isPlaying(this.currentMusic)) {
                this.currentMusic = null;
                this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, MathHelper.nextInt(this.random, backgroundMusicSelector.getMinDelay(), backgroundMusicSelector.getMaxDelay()));
            }
        }
        this.timeUntilNextMusic = Math.min(this.timeUntilNextMusic, backgroundMusicSelector.getMaxDelay());
        if (this.currentMusic == null && this.timeUntilNextMusic-- <= 0) {
            this.selectRandomBackgroundMusic(backgroundMusicSelector);
        }
    }

    public void selectRandomBackgroundMusic(BackgroundMusicSelector backgroundMusicSelector) {
        this.currentMusic = SimpleSound.music(backgroundMusicSelector.getSoundEvent());
        if (this.currentMusic.getSound() != SoundHandler.MISSING_SOUND) {
            this.client.getSoundHandler().play(this.currentMusic);
        }
        this.timeUntilNextMusic = Integer.MAX_VALUE;
    }

    public void stop() {
        if (this.currentMusic != null) {
            this.client.getSoundHandler().stop(this.currentMusic);
            this.currentMusic = null;
        }
        this.timeUntilNextMusic += 100;
    }

    public boolean isBackgroundMusicPlaying(BackgroundMusicSelector backgroundMusicSelector) {
        return this.currentMusic == null ? false : backgroundMusicSelector.getSoundEvent().getName().equals(this.currentMusic.getSoundLocation());
    }
}

