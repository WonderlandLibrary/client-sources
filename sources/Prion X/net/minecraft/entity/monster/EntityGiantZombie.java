package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.world.World;

public class EntityGiantZombie extends EntityMob
{
  private static final String __OBFID = "CL_00001690";
  
  public EntityGiantZombie(World worldIn)
  {
    super(worldIn);
    setSize(width * 6.0F, height * 6.0F);
  }
  
  public float getEyeHeight()
  {
    return 10.440001F;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0D);
  }
  
  public float func_180484_a(net.minecraft.util.BlockPos p_180484_1_)
  {
    return worldObj.getLightBrightness(p_180484_1_) - 0.5F;
  }
}
