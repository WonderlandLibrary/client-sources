package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleFallingDust extends Particle
{
    float field_190018_a;
    final float field_190019_b;

    protected ParticleFallingDust(World p_i47135_1_, double p_i47135_2_, double p_i47135_4_, double p_i47135_6_, float p_i47135_8_, float p_i47135_9_, float p_i47135_10_)
    {
        super(p_i47135_1_, p_i47135_2_, p_i47135_4_, p_i47135_6_, 0.0D, 0.0D, 0.0D);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.particleRed = p_i47135_8_;
        this.particleGreen = p_i47135_9_;
        this.particleBlue = p_i47135_10_;
        float f = 0.9F;
        this.particleScale *= 0.75F;
        this.particleScale *= 0.9F;
        this.field_190018_a = this.particleScale;
        this.particleMaxAge = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int)((float)this.particleMaxAge * 0.9F);
        this.field_190019_b = ((float)Math.random() - 0.5F) * 0.1F;
        this.field_190014_F = (float)Math.random() * ((float)Math.PI * 2F);
    }

    /**
     * Renders the particle
     */
    public void renderParticle(VertexBuffer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;
        f = MathHelper.clamp_float(f, 0.0F, 1.0F);
        this.particleScale = this.field_190018_a * f;
        super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        this.field_190015_G = this.field_190014_F;
        this.field_190014_F += (float)Math.PI * this.field_190019_b * 2.0F;

        if (this.isCollided)
        {
            this.field_190015_G = this.field_190014_F = 0.0F;
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY -= 0.003000000026077032D;
        this.motionY = Math.max(this.motionY, -0.14000000059604645D);
    }

    public static class Factory implements IParticleFactory
    {
        public Particle getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
        {
            IBlockState iblockstate = Block.getStateById(p_178902_15_[0]);

            if (iblockstate.getBlock() != Blocks.AIR && iblockstate.getRenderType() == EnumBlockRenderType.INVISIBLE)
            {
                return null;
            }
            else
            {
                int i = Minecraft.getMinecraft().getBlockColors().func_189991_a(iblockstate);

                if (iblockstate.getBlock() instanceof BlockFalling)
                {
                    i = ((BlockFalling)iblockstate.getBlock()).func_189876_x(iblockstate);
                }

                float f = (float)(i >> 16 & 255) / 255.0F;
                float f1 = (float)(i >> 8 & 255) / 255.0F;
                float f2 = (float)(i & 255) / 255.0F;
                return new ParticleFallingDust(worldIn, xCoordIn, yCoordIn, zCoordIn, f, f1, f2);
            }
        }
    }
}
