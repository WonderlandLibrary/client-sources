/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class PositionedSoundRecord
extends PositionedSound {
    public PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, BlockPos pos) {
        this(soundIn, categoryIn, volumeIn, pitchIn, (float)pos.getX() + 0.5f, (float)pos.getY() + 0.5f, (float)pos.getZ() + 0.5f);
    }

    public static PositionedSoundRecord getMasterRecord(SoundEvent soundIn, float pitchIn) {
        return PositionedSoundRecord.func_194007_a(soundIn, pitchIn, 0.25f);
    }

    public static PositionedSoundRecord func_194007_a(SoundEvent p_194007_0_, float p_194007_1_, float p_194007_2_) {
        return new PositionedSoundRecord(p_194007_0_, SoundCategory.MASTER, p_194007_2_, p_194007_1_, false, 0, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }

    public static PositionedSoundRecord getMusicRecord(SoundEvent soundIn) {
        return new PositionedSoundRecord(soundIn, SoundCategory.MUSIC, 1.0f, 1.0f, false, 0, ISound.AttenuationType.NONE, 0.0f, 0.0f, 0.0f);
    }

    public static PositionedSoundRecord getRecordSoundRecord(SoundEvent soundIn, float xIn, float yIn, float zIn) {
        return new PositionedSoundRecord(soundIn, SoundCategory.RECORDS, 4.0f, 1.0f, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
    }

    public PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, float xIn, float yIn, float zIn) {
        this(soundIn, categoryIn, volumeIn, pitchIn, false, 0, ISound.AttenuationType.LINEAR, xIn, yIn, zIn);
    }

    private PositionedSoundRecord(SoundEvent soundIn, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, ISound.AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
        this(soundIn.getSoundName(), categoryIn, volumeIn, pitchIn, repeatIn, repeatDelayIn, attenuationTypeIn, xIn, yIn, zIn);
    }

    public PositionedSoundRecord(ResourceLocation soundId, SoundCategory categoryIn, float volumeIn, float pitchIn, boolean repeatIn, int repeatDelayIn, ISound.AttenuationType attenuationTypeIn, float xIn, float yIn, float zIn) {
        super(soundId, categoryIn);
        this.volume = volumeIn;
        this.pitch = pitchIn;
        this.xPosF = xIn;
        this.yPosF = yIn;
        this.zPosF = zIn;
        this.repeat = repeatIn;
        this.repeatDelay = repeatDelayIn;
        this.attenuationType = attenuationTypeIn;
    }
}

