package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob
{
  private static final String __OBFID = "CL_00001650";
  
  public EntitySnowman(World worldIn)
  {
    super(worldIn);
    setSize(0.7F, 1.9F);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    tasks.addTask(1, new net.minecraft.entity.ai.EntityAIArrowAttack(this, 1.25D, 20, 10.0F));
    tasks.addTask(2, new EntityAIWander(this, 1.0D));
    tasks.addTask(3, new EntityAIWatchClosest(this, net.minecraft.entity.player.EntityPlayer.class, 6.0F));
    tasks.addTask(4, new net.minecraft.entity.ai.EntityAILookIdle(this));
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, net.minecraft.entity.EntityLiving.class, 10, true, false, IMob.mobSelector));
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
  }
  




  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    
    if (!worldObj.isRemote)
    {
      int var1 = MathHelper.floor_double(posX);
      int var2 = MathHelper.floor_double(posY);
      int var3 = MathHelper.floor_double(posZ);
      
      if (isWet())
      {
        attackEntityFrom(DamageSource.drown, 1.0F);
      }
      
      if (worldObj.getBiomeGenForCoords(new BlockPos(var1, 0, var3)).func_180626_a(new BlockPos(var1, var2, var3)) > 1.0F)
      {
        attackEntityFrom(DamageSource.onFire, 1.0F);
      }
      
      for (int var4 = 0; var4 < 4; var4++)
      {
        var1 = MathHelper.floor_double(posX + (var4 % 2 * 2 - 1) * 0.25F);
        var2 = MathHelper.floor_double(posY);
        var3 = MathHelper.floor_double(posZ + (var4 / 2 % 2 * 2 - 1) * 0.25F);
        
        if ((worldObj.getBlockState(new BlockPos(var1, var2, var3)).getBlock().getMaterial() == net.minecraft.block.material.Material.air) && (worldObj.getBiomeGenForCoords(new BlockPos(var1, 0, var3)).func_180626_a(new BlockPos(var1, var2, var3)) < 0.8F) && (Blocks.snow_layer.canPlaceBlockAt(worldObj, new BlockPos(var1, var2, var3))))
        {
          worldObj.setBlockState(new BlockPos(var1, var2, var3), Blocks.snow_layer.getDefaultState());
        }
      }
    }
  }
  
  protected Item getDropItem()
  {
    return Items.snowball;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(16);
    
    for (int var4 = 0; var4 < var3; var4++)
    {
      dropItem(Items.snowball, 1);
    }
  }
  



  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_)
  {
    EntitySnowball var3 = new EntitySnowball(worldObj, this);
    double var4 = posY + p_82196_1_.getEyeHeight() - 1.100000023841858D;
    double var6 = posX - posX;
    double var8 = var4 - posY;
    double var10 = posZ - posZ;
    float var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10) * 0.2F;
    var3.setThrowableHeading(var6, var8 + var12, var10, 1.6F, 12.0F);
    playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
    worldObj.spawnEntityInWorld(var3);
  }
  
  public float getEyeHeight()
  {
    return 1.7F;
  }
}
