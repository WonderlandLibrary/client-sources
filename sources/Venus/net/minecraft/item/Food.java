/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.potion.EffectInstance;

public class Food {
    private final int value;
    private final float saturation;
    private final boolean meat;
    private final boolean canEatWhenFull;
    private final boolean fastToEat;
    private final List<Pair<EffectInstance, Float>> effects;

    private Food(int n, float f, boolean bl, boolean bl2, boolean bl3, List<Pair<EffectInstance, Float>> list) {
        this.value = n;
        this.saturation = f;
        this.meat = bl;
        this.canEatWhenFull = bl2;
        this.fastToEat = bl3;
        this.effects = list;
    }

    public int getHealing() {
        return this.value;
    }

    public float getSaturation() {
        return this.saturation;
    }

    public boolean isMeat() {
        return this.meat;
    }

    public boolean canEatWhenFull() {
        return this.canEatWhenFull;
    }

    public boolean isFastEating() {
        return this.fastToEat;
    }

    public List<Pair<EffectInstance, Float>> getEffects() {
        return this.effects;
    }

    public static class Builder {
        private int value;
        private float saturation;
        private boolean meat;
        private boolean alwaysEdible;
        private boolean fastToEat;
        private final List<Pair<EffectInstance, Float>> effects = Lists.newArrayList();

        public Builder hunger(int n) {
            this.value = n;
            return this;
        }

        public Builder saturation(float f) {
            this.saturation = f;
            return this;
        }

        public Builder meat() {
            this.meat = true;
            return this;
        }

        public Builder setAlwaysEdible() {
            this.alwaysEdible = true;
            return this;
        }

        public Builder fastToEat() {
            this.fastToEat = true;
            return this;
        }

        public Builder effect(EffectInstance effectInstance, float f) {
            this.effects.add(Pair.of(effectInstance, Float.valueOf(f)));
            return this;
        }

        public Food build() {
            return new Food(this.value, this.saturation, this.meat, this.alwaysEdible, this.fastToEat, this.effects);
        }
    }
}

