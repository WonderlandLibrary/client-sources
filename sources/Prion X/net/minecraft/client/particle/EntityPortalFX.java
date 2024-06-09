package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX
{
  private float portalParticleScale;
  private double portalPosX;
  private double portalPosY;
  private double portalPosZ;
  private static final String __OBFID = "CL_00000921";
  
  protected EntityPortalFX(World worldIn, double p_i46351_2_, double p_i46351_4_, double p_i46351_6_, double p_i46351_8_, double p_i46351_10_, double p_i46351_12_)
  {
    super(worldIn, p_i46351_2_, p_i46351_4_, p_i46351_6_, p_i46351_8_, p_i46351_10_, p_i46351_12_);
    motionX = p_i46351_8_;
    motionY = p_i46351_10_;
    motionZ = p_i46351_12_;
    portalPosX = (this.posX = p_i46351_2_);
    portalPosY = (this.posY = p_i46351_4_);
    portalPosZ = (this.posZ = p_i46351_6_);
    float var14 = rand.nextFloat() * 0.6F + 0.4F;
    portalParticleScale = (this.particleScale = rand.nextFloat() * 0.2F + 0.5F);
    particleRed = (this.particleGreen = this.particleBlue = 1.0F * var14);
    particleGreen *= 0.3F;
    particleRed *= 0.9F;
    particleMaxAge = ((int)(Math.random() * 10.0D) + 40);
    noClip = true;
    setParticleTextureIndex((int)(Math.random() * 8.0D));
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, net.minecraft.entity.Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (particleAge + p_180434_3_) / particleMaxAge;
    var9 = 1.0F - var9;
    var9 *= var9;
    var9 = 1.0F - var9;
    particleScale = (portalParticleScale * var9);
    super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    int var2 = super.getBrightnessForRender(p_70070_1_);
    float var3 = particleAge / particleMaxAge;
    var3 *= var3;
    var3 *= var3;
    int var4 = var2 & 0xFF;
    int var5 = var2 >> 16 & 0xFF;
    var5 += (int)(var3 * 15.0F * 16.0F);
    
    if (var5 > 240)
    {
      var5 = 240;
    }
    
    return var4 | var5 << 16;
  }
  



  public float getBrightness(float p_70013_1_)
  {
    float var2 = super.getBrightness(p_70013_1_);
    float var3 = particleAge / particleMaxAge;
    var3 = var3 * var3 * var3 * var3;
    return var2 * (1.0F - var3) + var3;
  }
  



  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    float var1 = particleAge / particleMaxAge;
    float var2 = var1;
    var1 = -var1 + var1 * var1 * 2.0F;
    var1 = 1.0F - var1;
    posX = (portalPosX + motionX * var1);
    posY = (portalPosY + motionY * var1 + (1.0F - var2));
    posZ = (portalPosZ + motionZ * var1);
    
    if (particleAge++ >= particleMaxAge)
    {
      setDead();
    }
  }
  
  public static class Factory implements IParticleFactory {
    private static final String __OBFID = "CL_00002590";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
      return new EntityPortalFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
    }
  }
}
