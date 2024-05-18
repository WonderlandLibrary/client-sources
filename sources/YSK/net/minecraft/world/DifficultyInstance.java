package net.minecraft.world;

import net.minecraft.util.*;

public class DifficultyInstance
{
    private final float additionalDifficulty;
    private final EnumDifficulty worldDifficulty;
    
    public float getClampedAdditionalDifficulty() {
        float n;
        if (this.additionalDifficulty < 2.0f) {
            n = 0.0f;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else if (this.additionalDifficulty > 4.0f) {
            n = 1.0f;
            "".length();
            if (3 == 2) {
                throw null;
            }
        }
        else {
            n = (this.additionalDifficulty - 2.0f) / 2.0f;
        }
        return n;
    }
    
    public float getAdditionalDifficulty() {
        return this.additionalDifficulty;
    }
    
    private float calculateAdditionalDifficulty(final EnumDifficulty enumDifficulty, final long n, final long n2, final float n3) {
        if (enumDifficulty == EnumDifficulty.PEACEFUL) {
            return 0.0f;
        }
        int n4;
        if (enumDifficulty == EnumDifficulty.HARD) {
            n4 = " ".length();
            "".length();
            if (1 == -1) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        final int n5 = n4;
        final float n6 = 0.75f;
        final float n7 = MathHelper.clamp_float((n - 72000.0f) / 1440000.0f, 0.0f, 1.0f) * 0.25f;
        final float n8 = n6 + n7;
        final float n9 = 0.0f;
        final float clamp_float = MathHelper.clamp_float(n2 / 3600000.0f, 0.0f, 1.0f);
        float n10;
        if (n5 != 0) {
            n10 = 1.0f;
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n10 = 0.75f;
        }
        float n11 = n9 + clamp_float * n10 + MathHelper.clamp_float(n3 * 0.25f, 0.0f, n7);
        if (enumDifficulty == EnumDifficulty.EASY) {
            n11 *= 0.5f;
        }
        return enumDifficulty.getDifficultyId() * (n8 + n11);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public DifficultyInstance(final EnumDifficulty worldDifficulty, final long n, final long n2, final float n3) {
        this.worldDifficulty = worldDifficulty;
        this.additionalDifficulty = this.calculateAdditionalDifficulty(worldDifficulty, n, n2, n3);
    }
}
