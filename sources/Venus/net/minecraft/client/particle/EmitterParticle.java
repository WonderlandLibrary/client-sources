/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.MetaParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.vector.Vector3d;

public class EmitterParticle
extends MetaParticle {
    private final Entity attachedEntity;
    private int age;
    private final int lifetime;
    private final IParticleData particleTypes;

    public EmitterParticle(ClientWorld clientWorld, Entity entity2, IParticleData iParticleData) {
        this(clientWorld, entity2, iParticleData, 3);
    }

    public EmitterParticle(ClientWorld clientWorld, Entity entity2, IParticleData iParticleData, int n) {
        this(clientWorld, entity2, iParticleData, n, entity2.getMotion());
    }

    private EmitterParticle(ClientWorld clientWorld, Entity entity2, IParticleData iParticleData, int n, Vector3d vector3d) {
        super(clientWorld, entity2.getPosX(), entity2.getPosYHeight(0.5), entity2.getPosZ(), vector3d.x, vector3d.y, vector3d.z);
        this.attachedEntity = entity2;
        this.lifetime = n;
        this.particleTypes = iParticleData;
        this.tick();
    }

    @Override
    public void tick() {
        for (int i = 0; i < 16; ++i) {
            double d;
            double d2;
            double d3 = this.rand.nextFloat() * 2.0f - 1.0f;
            if (d3 * d3 + (d2 = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * d2 + (d = (double)(this.rand.nextFloat() * 2.0f - 1.0f)) * d > 1.0) continue;
            double d4 = this.attachedEntity.getPosXWidth(d3 / 4.0);
            double d5 = this.attachedEntity.getPosYHeight(0.5 + d2 / 4.0);
            double d6 = this.attachedEntity.getPosZWidth(d / 4.0);
            this.world.addParticle(this.particleTypes, false, d4, d5, d6, d3, d2 + 0.2, d);
        }
        ++this.age;
        if (this.age >= this.lifetime) {
            this.setExpired();
        }
    }
}

