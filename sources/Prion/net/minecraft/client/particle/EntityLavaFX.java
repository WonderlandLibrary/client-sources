package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX
{
  private float lavaParticleScale;
  private static final String __OBFID = "CL_00000912";
  
  protected EntityLavaFX(World worldIn, double p_i1215_2_, double p_i1215_4_, double p_i1215_6_)
  {
    super(worldIn, p_i1215_2_, p_i1215_4_, p_i1215_6_, 0.0D, 0.0D, 0.0D);
    motionX *= 0.800000011920929D;
    motionY *= 0.800000011920929D;
    motionZ *= 0.800000011920929D;
    motionY = (rand.nextFloat() * 0.4F + 0.05F);
    particleRed = (this.particleGreen = this.particleBlue = 1.0F);
    particleScale *= (rand.nextFloat() * 2.0F + 0.2F);
    lavaParticleScale = particleScale;
    particleMaxAge = ((int)(16.0D / (Math.random() * 0.8D + 0.2D)));
    noClip = false;
    setParticleTextureIndex(49);
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    float var2 = (particleAge + p_70070_1_) / particleMaxAge;
    var2 = net.minecraft.util.MathHelper.clamp_float(var2, 0.0F, 1.0F);
    int var3 = super.getBrightnessForRender(p_70070_1_);
    short var4 = 240;
    int var5 = var3 >> 16 & 0xFF;
    return var4 | var5 << 16;
  }
  



  public float getBrightness(float p_70013_1_)
  {
    return 1.0F;
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (particleAge + p_180434_3_) / particleMaxAge;
    particleScale = (lavaParticleScale * (1.0F - var9 * var9));
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
    
    float var1 = particleAge / particleMaxAge;
    
    if (rand.nextFloat() > var1)
    {
      worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX, posY, posZ, motionX, motionY, motionZ, new int[0]);
    }
    
    motionY -= 0.03D;
    moveEntity(motionX, motionY, motionZ);
    motionX *= 0.9990000128746033D;
    motionY *= 0.9990000128746033D;
    motionZ *= 0.9990000128746033D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
    }
  }
  
  public static class Factory implements IParticleFactory {
    private static final String __OBFID = "CL_00002595";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
      return new EntityLavaFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_);
    }
  }
}
