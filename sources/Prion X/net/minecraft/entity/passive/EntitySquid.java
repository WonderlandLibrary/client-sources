package net.minecraft.entity.passive;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;








public class EntitySquid
  extends EntityWaterMob
{
  public float squidPitch;
  public float prevSquidPitch;
  public float squidYaw;
  public float prevSquidYaw;
  public float squidRotation;
  public float prevSquidRotation;
  public float tentacleAngle;
  public float lastTentacleAngle;
  private float randomMotionSpeed;
  private float rotationVelocity;
  private float field_70871_bB;
  private float randomMotionVecX;
  private float randomMotionVecY;
  private float randomMotionVecZ;
  private static final String __OBFID = "CL_00001651";
  
  public EntitySquid(World worldIn)
  {
    super(worldIn);
    setSize(0.95F, 0.95F);
    rand.setSeed(1 + getEntityId());
    rotationVelocity = (1.0F / (rand.nextFloat() + 1.0F) * 0.2F);
    tasks.addTask(0, new AIMoveRandom());
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
  }
  
  public float getEyeHeight()
  {
    return height * 0.5F;
  }
  



  protected String getLivingSound()
  {
    return null;
  }
  



  protected String getHurtSound()
  {
    return null;
  }
  



  protected String getDeathSound()
  {
    return null;
  }
  



  protected float getSoundVolume()
  {
    return 0.4F;
  }
  
  protected Item getDropItem()
  {
    return null;
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(3 + p_70628_2_) + 1;
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      entityDropItem(new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), 0.0F);
    }
  }
  




  public boolean isInWater()
  {
    return worldObj.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.6000000238418579D, 0.0D), Material.water, this);
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    prevSquidPitch = squidPitch;
    prevSquidYaw = squidYaw;
    prevSquidRotation = squidRotation;
    lastTentacleAngle = tentacleAngle;
    squidRotation += rotationVelocity;
    
    if (squidRotation > 6.283185307179586D)
    {
      if (worldObj.isRemote)
      {
        squidRotation = 6.2831855F;
      }
      else
      {
        squidRotation = ((float)(squidRotation - 6.283185307179586D));
        
        if (rand.nextInt(10) == 0)
        {
          rotationVelocity = (1.0F / (rand.nextFloat() + 1.0F) * 0.2F);
        }
        
        worldObj.setEntityState(this, (byte)19);
      }
    }
    
    if (inWater)
    {


      if (squidRotation < 3.1415927F)
      {
        float var1 = squidRotation / 3.1415927F;
        tentacleAngle = (MathHelper.sin(var1 * var1 * 3.1415927F) * 3.1415927F * 0.25F);
        
        if (var1 > 0.75D)
        {
          randomMotionSpeed = 1.0F;
          field_70871_bB = 1.0F;
        }
        else
        {
          field_70871_bB *= 0.8F;
        }
      }
      else
      {
        tentacleAngle = 0.0F;
        randomMotionSpeed *= 0.9F;
        field_70871_bB *= 0.99F;
      }
      
      if (!worldObj.isRemote)
      {
        motionX = (randomMotionVecX * randomMotionSpeed);
        motionY = (randomMotionVecY * randomMotionSpeed);
        motionZ = (randomMotionVecZ * randomMotionSpeed);
      }
      
      float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
      renderYawOffset += (-(float)Math.atan2(motionX, motionZ) * 180.0F / 3.1415927F - renderYawOffset) * 0.1F;
      rotationYaw = renderYawOffset;
      squidYaw = ((float)(squidYaw + 3.141592653589793D * field_70871_bB * 1.5D));
      squidPitch += (-(float)Math.atan2(var1, motionY) * 180.0F / 3.1415927F - squidPitch) * 0.1F;
    }
    else
    {
      tentacleAngle = (MathHelper.abs(MathHelper.sin(squidRotation)) * 3.1415927F * 0.25F);
      
      if (!worldObj.isRemote)
      {
        motionX = 0.0D;
        motionY -= 0.08D;
        motionY *= 0.9800000190734863D;
        motionZ = 0.0D;
      }
      
      squidPitch = ((float)(squidPitch + (-90.0F - squidPitch) * 0.02D));
    }
  }
  



  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
  {
    moveEntity(motionX, motionY, motionZ);
  }
  



  public boolean getCanSpawnHere()
  {
    return (posY > 45.0D) && (posY < 63.0D) && (super.getCanSpawnHere());
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 19)
    {
      squidRotation = 0.0F;
    }
    else
    {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  
  public void func_175568_b(float p_175568_1_, float p_175568_2_, float p_175568_3_)
  {
    randomMotionVecX = p_175568_1_;
    randomMotionVecY = p_175568_2_;
    randomMotionVecZ = p_175568_3_;
  }
  
  public boolean func_175567_n()
  {
    return (randomMotionVecX != 0.0F) || (randomMotionVecY != 0.0F) || (randomMotionVecZ != 0.0F);
  }
  
  class AIMoveRandom extends EntityAIBase
  {
    private EntitySquid field_179476_a = EntitySquid.this;
    private static final String __OBFID = "CL_00002232";
    
    AIMoveRandom() {}
    
    public boolean shouldExecute() { return true; }
    

    public void updateTask()
    {
      int var1 = field_179476_a.getAge();
      
      if (var1 > 100)
      {
        field_179476_a.func_175568_b(0.0F, 0.0F, 0.0F);
      }
      else if ((field_179476_a.getRNG().nextInt(50) == 0) || (!field_179476_a.inWater) || (!field_179476_a.func_175567_n()))
      {
        float var2 = field_179476_a.getRNG().nextFloat() * 3.1415927F * 2.0F;
        float var3 = MathHelper.cos(var2) * 0.2F;
        float var4 = -0.1F + field_179476_a.getRNG().nextFloat() * 0.2F;
        float var5 = MathHelper.sin(var2) * 0.2F;
        field_179476_a.func_175568_b(var3, var4, var5);
      }
    }
  }
}
