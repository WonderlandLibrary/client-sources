/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.particles;

import com.mojang.serialization.Codec;
import net.minecraft.particles.IParticleData;

public abstract class ParticleType<T extends IParticleData> {
    private final boolean alwaysShow;
    private final IParticleData.IDeserializer<T> deserializer;

    protected ParticleType(boolean bl, IParticleData.IDeserializer<T> iDeserializer) {
        this.alwaysShow = bl;
        this.deserializer = iDeserializer;
    }

    public boolean getAlwaysShow() {
        return this.alwaysShow;
    }

    public IParticleData.IDeserializer<T> getDeserializer() {
        return this.deserializer;
    }

    public abstract Codec<T> func_230522_e_();
}

