package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX
{
  private float flameScale;
  private static final String __OBFID = "CL_00000907";
  
  protected EntityFlameFX(World worldIn, double p_i1209_2_, double p_i1209_4_, double p_i1209_6_, double p_i1209_8_, double p_i1209_10_, double p_i1209_12_)
  {
    super(worldIn, p_i1209_2_, p_i1209_4_, p_i1209_6_, p_i1209_8_, p_i1209_10_, p_i1209_12_);
    motionX = (motionX * 0.009999999776482582D + p_i1209_8_);
    motionY = (motionY * 0.009999999776482582D + p_i1209_10_);
    motionZ = (motionZ * 0.009999999776482582D + p_i1209_12_);
    double var10000 = p_i1209_2_ + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
    var10000 = p_i1209_4_ + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
    var10000 = p_i1209_6_ + (rand.nextFloat() - rand.nextFloat()) * 0.05F;
    flameScale = particleScale;
    particleRed = (this.particleGreen = this.particleBlue = 1.0F);
    particleMaxAge = ((int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4);
    noClip = true;
    setParticleTextureIndex(48);
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (particleAge + p_180434_3_) / particleMaxAge;
    particleScale = (flameScale * (1.0F - var9 * var9 * 0.5F));
    super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    float var2 = (particleAge + p_70070_1_) / particleMaxAge;
    var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
    int var3 = super.getBrightnessForRender(p_70070_1_);
    int var4 = var3 & 0xFF;
    int var5 = var3 >> 16 & 0xFF;
    var4 += (int)(var2 * 15.0F * 16.0F);
    
    if (var4 > 240)
    {
      var4 = 240;
    }
    
    return var4 | var5 << 16;
  }
  



  public float getBrightness(float p_70013_1_)
  {
    float var2 = (particleAge + p_70013_1_) / particleMaxAge;
    var2 = MathHelper.clamp_float(var2, 0.0F, 1.0F);
    float var3 = super.getBrightness(p_70013_1_);
    return var3 * var2 + (1.0F - var2);
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
    
    moveEntity(motionX, motionY, motionZ);
    motionX *= 0.9599999785423279D;
    motionY *= 0.9599999785423279D;
    motionZ *= 0.9599999785423279D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
    }
  }
  
  public static class Factory implements IParticleFactory {
    private static final String __OBFID = "CL_00002602";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
      return new EntityFlameFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
    }
  }
}
