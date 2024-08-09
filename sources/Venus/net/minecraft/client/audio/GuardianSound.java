/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;

public class GuardianSound
extends TickableSound {
    private final GuardianEntity guardian;

    public GuardianSound(GuardianEntity guardianEntity) {
        super(SoundEvents.ENTITY_GUARDIAN_ATTACK, SoundCategory.HOSTILE);
        this.guardian = guardianEntity;
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
    }

    @Override
    public boolean shouldPlaySound() {
        return !this.guardian.isSilent();
    }

    @Override
    public void tick() {
        if (!this.guardian.removed && this.guardian.getAttackTarget() == null) {
            this.x = (float)this.guardian.getPosX();
            this.y = (float)this.guardian.getPosY();
            this.z = (float)this.guardian.getPosZ();
            float f = this.guardian.getAttackAnimationScale(0.0f);
            this.volume = 0.0f + 1.0f * f * f;
            this.pitch = 0.7f + 0.5f * f;
        } else {
            this.finishPlaying();
        }
    }
}

