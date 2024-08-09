/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class MetaParticle
extends Particle {
    protected MetaParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
    }

    protected MetaParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
    }

    @Override
    public final void renderParticle(IVertexBuilder iVertexBuilder, ActiveRenderInfo activeRenderInfo, float f) {
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.NO_RENDER;
    }
}

