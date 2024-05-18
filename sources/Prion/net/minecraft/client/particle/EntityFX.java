package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;













public class EntityFX
  extends Entity
{
  protected int particleTextureIndexX;
  protected int particleTextureIndexY;
  protected float particleTextureJitterX;
  protected float particleTextureJitterY;
  protected int particleAge;
  protected int particleMaxAge;
  protected float particleScale;
  protected float particleGravity;
  protected float particleRed;
  protected float particleGreen;
  protected float particleBlue;
  protected float particleAlpha;
  protected TextureAtlasSprite particleIcon;
  public static double interpPosX;
  public static double interpPosY;
  public static double interpPosZ;
  private static final String __OBFID = "CL_00000914";
  
  protected EntityFX(World worldIn, double p_i46352_2_, double p_i46352_4_, double p_i46352_6_)
  {
    super(worldIn);
    particleAlpha = 1.0F;
    setSize(0.2F, 0.2F);
    setPosition(p_i46352_2_, p_i46352_4_, p_i46352_6_);
    lastTickPosX = p_i46352_2_;
    lastTickPosY = p_i46352_4_;
    lastTickPosZ = p_i46352_6_;
    particleRed = (this.particleGreen = this.particleBlue = 1.0F);
    particleTextureJitterX = (rand.nextFloat() * 3.0F);
    particleTextureJitterY = (rand.nextFloat() * 3.0F);
    particleScale = ((rand.nextFloat() * 0.5F + 0.5F) * 2.0F);
    particleMaxAge = ((int)(4.0F / (rand.nextFloat() * 0.9F + 0.1F)));
    particleAge = 0;
  }
  
  public EntityFX(World worldIn, double p_i1219_2_, double p_i1219_4_, double p_i1219_6_, double p_i1219_8_, double p_i1219_10_, double p_i1219_12_)
  {
    this(worldIn, p_i1219_2_, p_i1219_4_, p_i1219_6_);
    motionX = (p_i1219_8_ + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D);
    motionY = (p_i1219_10_ + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D);
    motionZ = (p_i1219_12_ + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D);
    float var14 = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
    float var15 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
    motionX = (motionX / var15 * var14 * 0.4000000059604645D);
    motionY = (motionY / var15 * var14 * 0.4000000059604645D + 0.10000000149011612D);
    motionZ = (motionZ / var15 * var14 * 0.4000000059604645D);
  }
  
  public EntityFX multiplyVelocity(float p_70543_1_)
  {
    motionX *= p_70543_1_;
    motionY = ((motionY - 0.10000000149011612D) * p_70543_1_ + 0.10000000149011612D);
    motionZ *= p_70543_1_;
    return this;
  }
  
  public EntityFX multipleParticleScaleBy(float p_70541_1_)
  {
    setSize(0.2F * p_70541_1_, 0.2F * p_70541_1_);
    particleScale *= p_70541_1_;
    return this;
  }
  
  public void setRBGColorF(float p_70538_1_, float p_70538_2_, float p_70538_3_)
  {
    particleRed = p_70538_1_;
    particleGreen = p_70538_2_;
    particleBlue = p_70538_3_;
  }
  



  public void setAlphaF(float p_82338_1_)
  {
    if ((particleAlpha == 1.0F) && (p_82338_1_ < 1.0F))
    {
      getMinecrafteffectRenderer.func_178928_b(this);
    }
    else if ((particleAlpha < 1.0F) && (p_82338_1_ == 1.0F))
    {
      getMinecrafteffectRenderer.func_178931_c(this);
    }
    
    particleAlpha = p_82338_1_;
  }
  
  public float getRedColorF()
  {
    return particleRed;
  }
  
  public float getGreenColorF()
  {
    return particleGreen;
  }
  
  public float getBlueColorF()
  {
    return particleBlue;
  }
  
  public float func_174838_j()
  {
    return particleAlpha;
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  


  protected void entityInit() {}
  

  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    
    if (particleAge++ >= particleMaxAge)
    {
      setDead();
    }
    
    motionY -= 0.04D * particleGravity;
    moveEntity(motionX, motionY, motionZ);
    motionX *= 0.9800000190734863D;
    motionY *= 0.9800000190734863D;
    motionZ *= 0.9800000190734863D;
    
    if (onGround)
    {
      motionX *= 0.699999988079071D;
      motionZ *= 0.699999988079071D;
    }
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = particleTextureIndexX / 16.0F;
    float var10 = var9 + 0.0624375F;
    float var11 = particleTextureIndexY / 16.0F;
    float var12 = var11 + 0.0624375F;
    float var13 = 0.1F * particleScale;
    
    if (particleIcon != null)
    {
      var9 = particleIcon.getMinU();
      var10 = particleIcon.getMaxU();
      var11 = particleIcon.getMinV();
      var12 = particleIcon.getMaxV();
    }
    
    float var14 = (float)(prevPosX + (posX - prevPosX) * p_180434_3_ - interpPosX);
    float var15 = (float)(prevPosY + (posY - prevPosY) * p_180434_3_ - interpPosY);
    float var16 = (float)(prevPosZ + (posZ - prevPosZ) * p_180434_3_ - interpPosZ);
    p_180434_1_.func_178960_a(particleRed, particleGreen, particleBlue, particleAlpha);
    p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 - p_180434_6_ * var13 - p_180434_8_ * var13, var10, var12);
    p_180434_1_.addVertexWithUV(var14 - p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 - p_180434_6_ * var13 + p_180434_8_ * var13, var10, var11);
    p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 + p_180434_7_ * var13, var15 + p_180434_5_ * var13, var16 + p_180434_6_ * var13 + p_180434_8_ * var13, var9, var11);
    p_180434_1_.addVertexWithUV(var14 + p_180434_4_ * var13 - p_180434_7_ * var13, var15 - p_180434_5_ * var13, var16 + p_180434_6_ * var13 - p_180434_8_ * var13, var9, var12);
  }
  
  public int getFXLayer()
  {
    return 0;
  }
  


  public void writeEntityToNBT(NBTTagCompound tagCompound) {}
  


  public void readEntityFromNBT(NBTTagCompound tagCompund) {}
  


  public void func_180435_a(TextureAtlasSprite p_180435_1_)
  {
    int var2 = getFXLayer();
    
    if (var2 == 1)
    {
      particleIcon = p_180435_1_;
    }
    else
    {
      throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
    }
  }
  



  public void setParticleTextureIndex(int p_70536_1_)
  {
    if (getFXLayer() != 0)
    {
      throw new RuntimeException("Invalid call to Particle.setMiscTex");
    }
    

    particleTextureIndexX = (p_70536_1_ % 16);
    particleTextureIndexY = (p_70536_1_ / 16);
  }
  

  public void nextTextureIndexX()
  {
    particleTextureIndexX += 1;
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
  
  public String toString()
  {
    return getClass().getSimpleName() + ", Pos (" + posX + "," + posY + "," + posZ + "), RGBA (" + particleRed + "," + particleGreen + "," + particleBlue + "," + particleAlpha + "), Age " + particleAge;
  }
}
