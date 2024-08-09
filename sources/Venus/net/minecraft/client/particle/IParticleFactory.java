/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import javax.annotation.Nullable;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

public interface IParticleFactory<T extends IParticleData> {
    @Nullable
    public Particle makeParticle(T var1, ClientWorld var2, double var3, double var5, double var7, double var9, double var11, double var13);
}

