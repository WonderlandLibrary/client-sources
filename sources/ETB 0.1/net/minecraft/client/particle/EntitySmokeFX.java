package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySmokeFX extends EntityFX
{
  float smokeParticleScale;
  private static final String __OBFID = "CL_00000924";
  
  private EntitySmokeFX(World worldIn, double p_i46347_2_, double p_i46347_4_, double p_i46347_6_, double p_i46347_8_, double p_i46347_10_, double p_i46347_12_)
  {
    this(worldIn, p_i46347_2_, p_i46347_4_, p_i46347_6_, p_i46347_8_, p_i46347_10_, p_i46347_12_, 1.0F);
  }
  
  protected EntitySmokeFX(World worldIn, double p_i46348_2_, double p_i46348_4_, double p_i46348_6_, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_, float p_i46348_14_)
  {
    super(worldIn, p_i46348_2_, p_i46348_4_, p_i46348_6_, 0.0D, 0.0D, 0.0D);
    motionX *= 0.10000000149011612D;
    motionY *= 0.10000000149011612D;
    motionZ *= 0.10000000149011612D;
    motionX += p_i46348_8_;
    motionY += p_i46348_10_;
    motionZ += p_i46348_12_;
    particleRed = (this.particleGreen = this.particleBlue = (float)(Math.random() * 0.30000001192092896D));
    particleScale *= 0.75F;
    particleScale *= p_i46348_14_;
    smokeParticleScale = particleScale;
    particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)));
    particleMaxAge = ((int)(particleMaxAge * p_i46348_14_));
    noClip = false;
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (particleAge + p_180434_3_) / particleMaxAge * 32.0F;
    var9 = MathHelper.clamp_float(var9, 0.0F, 1.0F);
    particleScale = (smokeParticleScale * var9);
    super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
  }
  



  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    
    if (particleAge++ >= particleMaxAge)
    {
      setDead();
    }
    
    setParticleTextureIndex(7 - particleAge * 8 / particleMaxAge);
    motionY += 0.004D;
    moveEntity(motionX, motionY, motionZ);
    
    if (posY == prevPosY)
    {
      motionX *= 1.1D;
      motionZ *= 1.1D;
    }
    
    motionX *= 0.9599999785423279D;
    motionY *= 0.9599999785423279D;
    motionZ *= 0.9599999785423279D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
    }
  }
  
  EntitySmokeFX(World p_i46282_1_, double p_i46282_2_, double p_i46282_4_, double p_i46282_6_, double p_i46282_8_, double p_i46282_10_, double p_i46282_12_, Object p_i46282_14_)
  {
    this(p_i46282_1_, p_i46282_2_, p_i46282_4_, p_i46282_6_, p_i46282_8_, p_i46282_10_, p_i46282_12_);
  }
  
  public static class Factory implements IParticleFactory {
    private static final String __OBFID = "CL_00002587";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
      return new EntitySmokeFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_, null);
    }
  }
}
