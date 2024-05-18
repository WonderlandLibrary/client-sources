package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;

public class EntitySuspendFX extends EntityFX
{
  private static final String __OBFID = "CL_00000928";
  
  protected EntitySuspendFX(World worldIn, double p_i1231_2_, double p_i1231_4_, double p_i1231_6_, double p_i1231_8_, double p_i1231_10_, double p_i1231_12_)
  {
    super(worldIn, p_i1231_2_, p_i1231_4_ - 0.125D, p_i1231_6_, p_i1231_8_, p_i1231_10_, p_i1231_12_);
    particleRed = 0.4F;
    particleGreen = 0.4F;
    particleBlue = 0.7F;
    setParticleTextureIndex(0);
    setSize(0.01F, 0.01F);
    particleScale *= (rand.nextFloat() * 0.6F + 0.2F);
    motionX = (p_i1231_8_ * 0.0D);
    motionY = (p_i1231_10_ * 0.0D);
    motionZ = (p_i1231_12_ * 0.0D);
    particleMaxAge = ((int)(16.0D / (Math.random() * 0.8D + 0.2D)));
  }
  



  public void onUpdate()
  {
    prevPosX = posX;
    prevPosY = posY;
    prevPosZ = posZ;
    moveEntity(motionX, motionY, motionZ);
    
    if (worldObj.getBlockState(new net.minecraft.util.BlockPos(this)).getBlock().getMaterial() != net.minecraft.block.material.Material.water)
    {
      setDead();
    }
    
    if (particleMaxAge-- <= 0)
    {
      setDead();
    }
  }
  
  public static class Factory implements IParticleFactory {
    private static final String __OBFID = "CL_00002579";
    
    public Factory() {}
    
    public EntityFX func_178902_a(int p_178902_1_, World worldIn, double p_178902_3_, double p_178902_5_, double p_178902_7_, double p_178902_9_, double p_178902_11_, double p_178902_13_, int... p_178902_15_) {
      return new EntitySuspendFX(worldIn, p_178902_3_, p_178902_5_, p_178902_7_, p_178902_9_, p_178902_11_, p_178902_13_);
    }
  }
}
