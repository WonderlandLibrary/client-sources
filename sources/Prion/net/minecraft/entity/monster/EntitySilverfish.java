package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSilverfish.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntitySilverfish extends EntityMob
{
  private AISummonSilverfish field_175460_b;
  private static final String __OBFID = "CL_00001696";
  
  public EntitySilverfish(World worldIn)
  {
    super(worldIn);
    setSize(0.4F, 0.3F);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(3, this.field_175460_b = new AISummonSilverfish());
    tasks.addTask(4, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
    tasks.addTask(5, new AIHideInStone());
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, true, new Class[0]));
    targetTasks.addTask(2, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
  }
  
  public float getEyeHeight()
  {
    return 0.1F;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  



  protected String getLivingSound()
  {
    return "mob.silverfish.say";
  }
  



  protected String getHurtSound()
  {
    return "mob.silverfish.hit";
  }
  



  protected String getDeathSound()
  {
    return "mob.silverfish.kill";
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    if (((source instanceof EntityDamageSource)) || (source == DamageSource.magic))
    {
      field_175460_b.func_179462_f();
    }
    
    return super.attackEntityFrom(source, amount);
  }
  

  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.silverfish.step", 0.15F, 1.0F);
  }
  
  protected Item getDropItem()
  {
    return null;
  }
  



  public void onUpdate()
  {
    renderYawOffset = rotationYaw;
    super.onUpdate();
  }
  
  public float func_180484_a(BlockPos p_180484_1_)
  {
    return worldObj.getBlockState(p_180484_1_.offsetDown()).getBlock() == Blocks.stone ? 10.0F : super.func_180484_a(p_180484_1_);
  }
  



  protected boolean isValidLightLevel()
  {
    return true;
  }
  



  public boolean getCanSpawnHere()
  {
    if (super.getCanSpawnHere())
    {
      EntityPlayer var1 = worldObj.getClosestPlayerToEntity(this, 5.0D);
      return var1 == null;
    }
    

    return false;
  }
  




  public EnumCreatureAttribute getCreatureAttribute()
  {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  class AIHideInStone extends EntityAIWander
  {
    private EnumFacing field_179483_b;
    private boolean field_179484_c;
    private static final String __OBFID = "CL_00002205";
    
    public AIHideInStone()
    {
      super(1.0D, 10);
      setMutexBits(1);
    }
    
    public boolean shouldExecute()
    {
      if (getAttackTarget() != null)
      {
        return false;
      }
      if (!getNavigator().noPath())
      {
        return false;
      }
      

      Random var1 = getRNG();
      
      if (var1.nextInt(10) == 0)
      {
        field_179483_b = EnumFacing.random(var1);
        BlockPos var2 = new BlockPos(posX, posY + 0.5D, posZ).offset(field_179483_b);
        IBlockState var3 = worldObj.getBlockState(var2);
        
        if (BlockSilverfish.func_176377_d(var3))
        {
          field_179484_c = true;
          return true;
        }
      }
      
      field_179484_c = false;
      return super.shouldExecute();
    }
    

    public boolean continueExecuting()
    {
      return field_179484_c ? false : super.continueExecuting();
    }
    
    public void startExecuting()
    {
      if (!field_179484_c)
      {
        super.startExecuting();
      }
      else
      {
        World var1 = worldObj;
        BlockPos var2 = new BlockPos(posX, posY + 0.5D, posZ).offset(field_179483_b);
        IBlockState var3 = var1.getBlockState(var2);
        
        if (BlockSilverfish.func_176377_d(var3))
        {
          var1.setBlockState(var2, Blocks.monster_egg.getDefaultState().withProperty(BlockSilverfish.VARIANT_PROP, BlockSilverfish.EnumType.func_176878_a(var3)), 3);
          spawnExplosionParticle();
          setDead();
        }
      }
    }
  }
  
  class AISummonSilverfish extends net.minecraft.entity.ai.EntityAIBase
  {
    private EntitySilverfish field_179464_a = EntitySilverfish.this;
    private int field_179463_b;
    private static final String __OBFID = "CL_00002204";
    
    AISummonSilverfish() {}
    
    public void func_179462_f() { if (field_179463_b == 0)
      {
        field_179463_b = 20;
      }
    }
    
    public boolean shouldExecute()
    {
      return field_179463_b > 0;
    }
    
    public void updateTask()
    {
      field_179463_b -= 1;
      
      if (field_179463_b <= 0)
      {
        World var1 = field_179464_a.worldObj;
        Random var2 = field_179464_a.getRNG();
        BlockPos var3 = new BlockPos(field_179464_a);
        
        for (int var4 = 0; (var4 <= 5) && (var4 >= -5); var4 = var4 <= 0 ? 1 - var4 : 0 - var4)
        {
          for (int var5 = 0; (var5 <= 10) && (var5 >= -10); var5 = var5 <= 0 ? 1 - var5 : 0 - var5)
          {
            for (int var6 = 0; (var6 <= 10) && (var6 >= -10); var6 = var6 <= 0 ? 1 - var6 : 0 - var6)
            {
              BlockPos var7 = var3.add(var5, var4, var6);
              IBlockState var8 = var1.getBlockState(var7);
              
              if (var8.getBlock() == Blocks.monster_egg)
              {
                if (var1.getGameRules().getGameRuleBooleanValue("mobGriefing"))
                {
                  var1.destroyBlock(var7, true);
                }
                else
                {
                  var1.setBlockState(var7, ((BlockSilverfish.EnumType)var8.getValue(BlockSilverfish.VARIANT_PROP)).func_176883_d(), 3);
                }
                
                if (var2.nextBoolean())
                {
                  return;
                }
              }
            }
          }
        }
      }
    }
  }
}
