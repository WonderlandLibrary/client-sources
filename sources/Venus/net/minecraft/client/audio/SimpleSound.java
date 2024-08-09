/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.LocatableSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SimpleSound
extends LocatableSound {
    public SimpleSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, BlockPos blockPos) {
        this(soundEvent, soundCategory, f, f2, (double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5);
    }

    public static SimpleSound master(SoundEvent soundEvent, float f) {
        return SimpleSound.master(soundEvent, f, 0.25f);
    }

    public static SimpleSound master(SoundEvent soundEvent, float f, float f2) {
        return new SimpleSound(soundEvent.getName(), SoundCategory.MASTER, f2, f, false, 0, ISound.AttenuationType.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSound music(SoundEvent soundEvent) {
        return new SimpleSound(soundEvent.getName(), SoundCategory.MUSIC, 1.0f, 1.0f, false, 0, ISound.AttenuationType.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSound record(SoundEvent soundEvent, double d, double d2, double d3) {
        return new SimpleSound(soundEvent, SoundCategory.RECORDS, 4.0f, 1.0f, false, 0, ISound.AttenuationType.LINEAR, d, d2, d3);
    }

    public static SimpleSound ambientWithoutAttenuation(SoundEvent soundEvent, float f, float f2) {
        return new SimpleSound(soundEvent.getName(), SoundCategory.AMBIENT, f2, f, false, 0, ISound.AttenuationType.NONE, 0.0, 0.0, 0.0, true);
    }

    public static SimpleSound ambient(SoundEvent soundEvent) {
        return SimpleSound.ambientWithoutAttenuation(soundEvent, 1.0f, 1.0f);
    }

    public static SimpleSound ambientWithAttenuation(SoundEvent soundEvent, double d, double d2, double d3) {
        return new SimpleSound(soundEvent, SoundCategory.AMBIENT, 1.0f, 1.0f, false, 0, ISound.AttenuationType.LINEAR, d, d2, d3);
    }

    public SimpleSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, double d, double d2, double d3) {
        this(soundEvent, soundCategory, f, f2, false, 0, ISound.AttenuationType.LINEAR, d, d2, d3);
    }

    private SimpleSound(SoundEvent soundEvent, SoundCategory soundCategory, float f, float f2, boolean bl, int n, ISound.AttenuationType attenuationType, double d, double d2, double d3) {
        this(soundEvent.getName(), soundCategory, f, f2, bl, n, attenuationType, d, d2, d3, false);
    }

    public SimpleSound(ResourceLocation resourceLocation, SoundCategory soundCategory, float f, float f2, boolean bl, int n, ISound.AttenuationType attenuationType, double d, double d2, double d3, boolean bl2) {
        super(resourceLocation, soundCategory);
        this.volume = f;
        this.pitch = f2;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.repeat = bl;
        this.repeatDelay = n;
        this.attenuationType = attenuationType;
        this.global = bl2;
    }
}

