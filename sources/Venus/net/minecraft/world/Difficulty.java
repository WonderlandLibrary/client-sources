/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public enum Difficulty {
    PEACEFUL(0, "peaceful"),
    EASY(1, "easy"),
    NORMAL(2, "normal"),
    HARD(3, "hard");

    private static final Difficulty[] ID_MAPPING;
    private final int id;
    private final String translationKey;

    private Difficulty(int n2, String string2) {
        this.id = n2;
        this.translationKey = string2;
    }

    public int getId() {
        return this.id;
    }

    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("options.difficulty." + this.translationKey);
    }

    public static Difficulty byId(int n) {
        return ID_MAPPING[n % ID_MAPPING.length];
    }

    @Nullable
    public static Difficulty byName(String string) {
        for (Difficulty difficulty : Difficulty.values()) {
            if (!difficulty.translationKey.equals(string)) continue;
            return difficulty;
        }
        return null;
    }

    public String getTranslationKey() {
        return this.translationKey;
    }

    public Difficulty getNextDifficulty() {
        return ID_MAPPING[(this.id + 1) % ID_MAPPING.length];
    }

    private static Difficulty[] lambda$static$0(int n) {
        return new Difficulty[n];
    }

    static {
        ID_MAPPING = (Difficulty[])Arrays.stream(Difficulty.values()).sorted(Comparator.comparingInt(Difficulty::getId)).toArray(Difficulty::lambda$static$0);
    }
}

