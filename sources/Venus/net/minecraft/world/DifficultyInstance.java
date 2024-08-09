/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;

@Immutable
public class DifficultyInstance {
    private final Difficulty worldDifficulty;
    private final float additionalDifficulty;

    public DifficultyInstance(Difficulty difficulty, long l, long l2, float f) {
        this.worldDifficulty = difficulty;
        this.additionalDifficulty = this.calculateAdditionalDifficulty(difficulty, l, l2, f);
    }

    public Difficulty getDifficulty() {
        return this.worldDifficulty;
    }

    public float getAdditionalDifficulty() {
        return this.additionalDifficulty;
    }

    public boolean isHarderThan(float f) {
        return this.additionalDifficulty > f;
    }

    public float getClampedAdditionalDifficulty() {
        if (this.additionalDifficulty < 2.0f) {
            return 0.0f;
        }
        return this.additionalDifficulty > 4.0f ? 1.0f : (this.additionalDifficulty - 2.0f) / 2.0f;
    }

    private float calculateAdditionalDifficulty(Difficulty difficulty, long l, long l2, float f) {
        if (difficulty == Difficulty.PEACEFUL) {
            return 0.0f;
        }
        boolean bl = difficulty == Difficulty.HARD;
        float f2 = 0.75f;
        float f3 = MathHelper.clamp(((float)l + -72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        f2 += f3;
        float f4 = 0.0f;
        f4 += MathHelper.clamp((float)l2 / 3600000.0f, 0.0f, 1.0f) * (bl ? 1.0f : 0.75f);
        f4 += MathHelper.clamp(f * 0.25f, 0.0f, f3);
        if (difficulty == Difficulty.EASY) {
            f4 *= 0.5f;
        }
        return (float)difficulty.getId() * (f2 += f4);
    }
}

