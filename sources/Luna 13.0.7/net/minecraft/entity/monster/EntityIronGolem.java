package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower.EnumFlowerType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIDefendVillage;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.World;

public class EntityIronGolem
  extends EntityGolem
{
  private int homeCheckTimer;
  Village villageObj;
  private int attackTimer;
  private int holdRoseTick;
  private static final String __OBFID = "CL_00001652";
  
  public EntityIronGolem(World worldIn)
  {
    super(worldIn);
    setSize(1.4F, 2.9F);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
    this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
    this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
    this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
    this.tasks.addTask(5, new EntityAILookAtVillager(this));
    this.tasks.addTask(6, new EntityAIWander(this, 0.6D));
    this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    this.tasks.addTask(8, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
    this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    this.targetTasks.addTask(3, new AINearestAttackableTargetNonCreeper(this, EntityLiving.class, 10, false, true, IMob.field_175450_e));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  
  protected void updateAITasks()
  {
    if (--this.homeCheckTimer <= 0)
    {
      this.homeCheckTimer = (70 + this.rand.nextInt(50));
      this.villageObj = this.worldObj.getVillageCollection().func_176056_a(new BlockPos(this), 32);
      if (this.villageObj == null)
      {
        detachHome();
      }
      else
      {
        BlockPos var1 = this.villageObj.func_180608_a();
        func_175449_a(var1, (int)(this.villageObj.getVillageRadius() * 0.6F));
      }
    }
    super.updateAITasks();
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
  }
  
  protected int decreaseAirSupply(int p_70682_1_)
  {
    return p_70682_1_;
  }
  
  protected void collideWithEntity(Entity p_82167_1_)
  {
    if (((p_82167_1_ instanceof IMob)) && (getRNG().nextInt(20) == 0)) {
      setAttackTarget((EntityLivingBase)p_82167_1_);
    }
    super.collideWithEntity(p_82167_1_);
  }
  
  public void onLivingUpdate()
  {
    super.onLivingUpdate();
    if (this.attackTimer > 0) {
      this.attackTimer -= 1;
    }
    if (this.holdRoseTick > 0) {
      this.holdRoseTick -= 1;
    }
    if ((this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D) && (this.rand.nextInt(5) == 0))
    {
      int var1 = MathHelper.floor_double(this.posX);
      int var2 = MathHelper.floor_double(this.posY - 0.20000000298023224D);
      int var3 = MathHelper.floor_double(this.posZ);
      IBlockState var4 = this.worldObj.getBlockState(new BlockPos(var1, var2, var3));
      Block var5 = var4.getBlock();
      if (var5.getMaterial() != Material.air) {
        this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, getEntityBoundingBox().minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(var4) });
      }
    }
  }
  
  public boolean canAttackClass(Class p_70686_1_)
  {
    return (isPlayerCreated()) && (EntityPlayer.class.isAssignableFrom(p_70686_1_)) ? false : super.canAttackClass(p_70686_1_);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("PlayerCreated", isPlayerCreated());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setPlayerCreated(tagCompund.getBoolean("PlayerCreated"));
  }
  
  public boolean attackEntityAsMob(Entity p_70652_1_)
  {
    this.attackTimer = 10;
    this.worldObj.setEntityState(this, (byte)4);
    boolean var2 = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), 7 + this.rand.nextInt(15));
    if (var2)
    {
      p_70652_1_.motionY += 0.4000000059604645D;
      func_174815_a(this, p_70652_1_);
    }
    playSound("mob.irongolem.throw", 1.0F, 1.0F);
    return var2;
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 4)
    {
      this.attackTimer = 10;
      playSound("mob.irongolem.throw", 1.0F, 1.0F);
    }
    else if (p_70103_1_ == 11)
    {
      this.holdRoseTick = 400;
    }
    else
    {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  
  public Village getVillage()
  {
    return this.villageObj;
  }
  
  public int getAttackTimer()
  {
    return this.attackTimer;
  }
  
  public void setHoldingRose(boolean p_70851_1_)
  {
    this.holdRoseTick = (p_70851_1_ ? 400 : 0);
    this.worldObj.setEntityState(this, (byte)11);
  }
  
  protected String getHurtSound()
  {
    return "mob.irongolem.hit";
  }
  
  protected String getDeathSound()
  {
    return "mob.irongolem.death";
  }
  
  protected void func_180429_a(BlockPos p_180429_1_, Block p_180429_2_)
  {
    playSound("mob.irongolem.walk", 1.0F, 1.0F);
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = this.rand.nextInt(3);
    for (int var4 = 0; var4 < var3; var4++) {
      dropItemWithOffset(Item.getItemFromBlock(Blocks.red_flower), 1, BlockFlower.EnumFlowerType.POPPY.func_176968_b());
    }
    var4 = 3 + this.rand.nextInt(3);
    for (int var5 = 0; var5 < var4; var5++) {
      dropItem(Items.iron_ingot, 1);
    }
  }
  
  public int getHoldRoseTick()
  {
    return this.holdRoseTick;
  }
  
  public boolean isPlayerCreated()
  {
    return (this.dataWatcher.getWatchableObjectByte(16) & 0x1) != 0;
  }
  
  public void setPlayerCreated(boolean p_70849_1_)
  {
    byte var2 = this.dataWatcher.getWatchableObjectByte(16);
    if (p_70849_1_) {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
    } else {
      this.dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
    }
  }
  
  public void onDeath(DamageSource cause)
  {
    if ((!isPlayerCreated()) && (this.attackingPlayer != null) && (this.villageObj != null)) {
      this.villageObj.setReputationForPlayer(this.attackingPlayer.getName(), -5);
    }
    super.onDeath(cause);
  }
  
  static class AINearestAttackableTargetNonCreeper
    extends EntityAINearestAttackableTarget
  {
    private static final String __OBFID = "CL_00002231";
    
    public AINearestAttackableTargetNonCreeper(final EntityCreature p_i45858_1_, Class p_i45858_2_, int p_i45858_3_, boolean p_i45858_4_, boolean p_i45858_5_, final Predicate p_i45858_6_)
    {
      super(p_i45858_2_, p_i45858_3_, p_i45858_4_, p_i45858_5_, p_i45858_6_);
      this.targetEntitySelector = new Predicate()
      {
        private static final String __OBFID = "CL_00002230";
        
        public boolean func_180096_a(EntityLivingBase p_180096_1_)
        {
          if ((p_i45858_6_ != null) && (!p_i45858_6_.apply(p_180096_1_))) {
            return false;
          }
          if ((p_180096_1_ instanceof EntityCreeper)) {
            return false;
          }
          if ((p_180096_1_ instanceof EntityPlayer))
          {
            double var2 = EntityIronGolem.AINearestAttackableTargetNonCreeper.this.getTargetDistance();
            if (p_180096_1_.isSneaking()) {
              var2 *= 0.800000011920929D;
            }
            if (p_180096_1_.isInvisible())
            {
              float var4 = ((EntityPlayer)p_180096_1_).getArmorVisibility();
              if (var4 < 0.1F) {
                var4 = 0.1F;
              }
              var2 *= 0.7F * var4;
            }
            if (p_180096_1_.getDistanceToEntity(p_i45858_1_) > var2) {
              return false;
            }
          }
          return EntityIronGolem.AINearestAttackableTargetNonCreeper.this.isSuitableTarget(p_180096_1_, false);
        }
        
        public boolean apply(Object p_apply_1_)
        {
          return func_180096_a((EntityLivingBase)p_apply_1_);
        }
      };
    }
  }
}
