/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.potion;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Potion {
    private final String baseName;
    private final ImmutableList<EffectInstance> effects;

    public static Potion getPotionTypeForName(String string) {
        return Registry.POTION.getOrDefault(ResourceLocation.tryCreate(string));
    }

    public Potion(EffectInstance ... effectInstanceArray) {
        this((String)null, effectInstanceArray);
    }

    public Potion(@Nullable String string, EffectInstance ... effectInstanceArray) {
        this.baseName = string;
        this.effects = ImmutableList.copyOf(effectInstanceArray);
    }

    public String getNamePrefixed(String string) {
        return string + (this.baseName == null ? Registry.POTION.getKey(this).getPath() : this.baseName);
    }

    public List<EffectInstance> getEffects() {
        return this.effects;
    }

    public boolean hasInstantEffect() {
        if (!this.effects.isEmpty()) {
            for (EffectInstance effectInstance : this.effects) {
                if (!effectInstance.getPotion().isInstant()) continue;
                return false;
            }
        }
        return true;
    }
}

