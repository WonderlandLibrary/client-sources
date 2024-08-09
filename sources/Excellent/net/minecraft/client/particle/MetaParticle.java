package net.minecraft.client.particle;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.mojang.blaze3d.vertex.IVertexBuilder;

public class MetaParticle extends Particle
{
    protected MetaParticle(ClientWorld world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    protected MetaParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ)
    {
        super(world, x, y, z, motionX, motionY, motionZ);
    }

    public final void renderParticle(IVertexBuilder buffer, ActiveRenderInfo renderInfo, float partialTicks)
    {
    }

    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.NO_RENDER;
    }
}
