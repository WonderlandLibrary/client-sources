package net.minecraft.client.particle;

import club.bluezenith.BlueZenith;
import club.bluezenith.module.modules.render.MoreParticles;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

public class EntityParticleEmitter extends EntityFX
{
    private Entity attachedEntity;
    private int age;
    private int lifetime;
    private EnumParticleTypes particleTypes;

    public EntityParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes particleTypesIn)
    {
        super(worldIn, p_i46279_2_.posX, p_i46279_2_.getEntityBoundingBox().minY + (double)(p_i46279_2_.height / 2.0F), p_i46279_2_.posZ, p_i46279_2_.motionX, p_i46279_2_.motionY, p_i46279_2_.motionZ);
        this.attachedEntity = p_i46279_2_;
        this.lifetime = 3;
        this.particleTypes = particleTypesIn;
        this.onUpdate();
    }

    /**
     * Renders the particle
     *  
     * @param worldRendererIn The WorldRenderer instance
     */
    public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
    {
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        final MoreParticles particles = BlueZenith.getBlueZenith().getModuleManager().getAndCast(MoreParticles.class);
        for (int i = 0; i < 16; ++i)
        {
            double d0 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            double d1 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);
            double d2 = (double)(this.rand.nextFloat() * 2.0F - 1.0F);

            if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D)
            {
                double d3 = this.attachedEntity.posX + d0 * (double)this.attachedEntity.width / 4.0D;
                double d4 = this.attachedEntity.getEntityBoundingBox().minY + (double)(this.attachedEntity.height / 2.0F) + d1 * (double)this.attachedEntity.height / 4.0D;
                double d5 = this.attachedEntity.posZ + d2 * (double)this.attachedEntity.width / 4.0D;
                if(particles.getState() && particles.multiplier.get() > 1) {
                    if(
                            this.particleTypes == EnumParticleTypes.CRIT
                            && particles.types.getOptionState("Criticals")
                            || this.particleTypes == EnumParticleTypes.CRIT_MAGIC
                            && particles.types.getOptionState("Enchant")
                    ) {
                        final Random r = new Random(); //randomize the positions a little, if you don't theyre gonna appear on the same spot
                        for (int ii = 0; ii < particles.multiplier.get(); ii++) {
                            final float x = r.nextFloat();
                            final float y = r.nextFloat();
                            final float z = r.nextFloat();
                            this.worldObj.spawnParticle(this.particleTypes, false, d3 + x, d4 + y, d5 + z, d0, d1 + 0.2D, d2);
                        }
                    } else {
                        this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2);
                    }
                } else {
                    this.worldObj.spawnParticle(this.particleTypes, false, d3, d4, d5, d0, d1 + 0.2D, d2);
                }
            }
        }

        ++this.age;

        if (this.age >= this.lifetime)
        {
            this.setDead();
        }
    }

    public int getFXLayer()
    {
        return 3;
    }
}
