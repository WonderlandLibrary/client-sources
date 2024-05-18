package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityFireworkSparkFX extends EntityFX
{
  private int baseTextureIndex = 160;
  private boolean field_92054_ax;
  private boolean field_92048_ay;
  private final EffectRenderer field_92047_az;
  private float fadeColourRed;
  private float fadeColourGreen;
  private float fadeColourBlue;
  private boolean hasFadeColour;
  private static final String __OBFID = "CL_00000905";
  
  public EntityFireworkSparkFX(World worldIn, double p_i46356_2_, double p_i46356_4_, double p_i46356_6_, double p_i46356_8_, double p_i46356_10_, double p_i46356_12_, EffectRenderer p_i46356_14_)
  {
    super(worldIn, p_i46356_2_, p_i46356_4_, p_i46356_6_);
    motionX = p_i46356_8_;
    motionY = p_i46356_10_;
    motionZ = p_i46356_12_;
    field_92047_az = p_i46356_14_;
    particleScale *= 0.75F;
    particleMaxAge = (48 + rand.nextInt(12));
    noClip = false;
  }
  
  public void setTrail(boolean p_92045_1_)
  {
    field_92054_ax = p_92045_1_;
  }
  
  public void setTwinkle(boolean p_92043_1_)
  {
    field_92048_ay = p_92043_1_;
  }
  
  public void setColour(int p_92044_1_)
  {
    float var2 = ((p_92044_1_ & 0xFF0000) >> 16) / 255.0F;
    float var3 = ((p_92044_1_ & 0xFF00) >> 8) / 255.0F;
    float var4 = ((p_92044_1_ & 0xFF) >> 0) / 255.0F;
    float var5 = 1.0F;
    setRBGColorF(var2 * var5, var3 * var5, var4 * var5);
  }
  
  public void setFadeColour(int p_92046_1_)
  {
    fadeColourRed = (((p_92046_1_ & 0xFF0000) >> 16) / 255.0F);
    fadeColourGreen = (((p_92046_1_ & 0xFF00) >> 8) / 255.0F);
    fadeColourBlue = (((p_92046_1_ & 0xFF) >> 0) / 255.0F);
    hasFadeColour = true;
  }
  



  public AxisAlignedBB getBoundingBox()
  {
    return null;
  }
  



  public boolean canBePushed()
  {
    return false;
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    if ((!field_92048_ay) || (particleAge < particleMaxAge / 3) || ((particleAge + particleMaxAge) / 3 % 2 == 0))
    {
      super.func_180434_a(p_180434_1_, p_180434_2_, p_180434_3_, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
    }
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
    
    if (particleAge > particleMaxAge / 2)
    {
      setAlphaF(1.0F - (particleAge - particleMaxAge / 2) / particleMaxAge);
      
      if (hasFadeColour)
      {
        particleRed += (fadeColourRed - particleRed) * 0.2F;
        particleGreen += (fadeColourGreen - particleGreen) * 0.2F;
        particleBlue += (fadeColourBlue - particleBlue) * 0.2F;
      }
    }
    
    setParticleTextureIndex(baseTextureIndex + (7 - particleAge * 8 / particleMaxAge));
    motionY -= 0.004D;
    moveEntity(motionX, motionY, motionZ);
    motionX *= 0.9100000262260437D;
    motionY *= 0.9100000262260437D;
    motionZ *= 0.9100000262260437D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
    }
    
    if ((field_92054_ax) && (particleAge < particleMaxAge / 2) && ((particleAge + particleMaxAge) % 2 == 0))
    {
      EntityFireworkSparkFX var1 = new EntityFireworkSparkFX(worldObj, posX, posY, posZ, 0.0D, 0.0D, 0.0D, field_92047_az);
      var1.setAlphaF(0.99F);
      var1.setRBGColorF(particleRed, particleGreen, particleBlue);
      particleAge = (particleMaxAge / 2);
      
      if (hasFadeColour)
      {
        hasFadeColour = true;
        fadeColourRed = fadeColourRed;
        fadeColourGreen = fadeColourGreen;
        fadeColourBlue = fadeColourBlue;
      }
      
      field_92048_ay = field_92048_ay;
      field_92047_az.addEffect(var1);
    }
  }
  
  public int getBrightnessForRender(float p_70070_1_)
  {
    return 15728880;
  }
  



  public float getBrightness(float p_70013_1_)
  {
    return 1.0F;
  }
}
