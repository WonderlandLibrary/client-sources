package net.minecraft.client.particle;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.IParticleData;

import javax.annotation.Nullable;

public interface IParticleFactory<T extends IParticleData>
{
    @Nullable
    Particle makeParticle(T typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed);
}
