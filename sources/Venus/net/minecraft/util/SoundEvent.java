/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.ResourceLocation;

public class SoundEvent {
    public static final Codec<SoundEvent> CODEC = ResourceLocation.CODEC.xmap(SoundEvent::new, SoundEvent::lambda$static$0);
    private final ResourceLocation name;

    public SoundEvent(ResourceLocation resourceLocation) {
        this.name = resourceLocation;
    }

    public ResourceLocation getName() {
        return this.name;
    }

    private static ResourceLocation lambda$static$0(SoundEvent soundEvent) {
        return soundEvent.name;
    }
}

