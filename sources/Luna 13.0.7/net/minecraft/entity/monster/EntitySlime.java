package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;

public class EntitySlime
  extends EntityLiving
  implements IMob
{
  public float squishAmount;
  public float squishFactor;
  public float prevSquishFactor;
  private boolean field_175452_bi;
  private static final String __OBFID = "CL_00001698";
  
  public EntitySlime(World worldIn)
  {
    super(worldIn);
    this.moveHelper = new SlimeMoveHelper();
    this.tasks.addTask(1, new AISlimeFloat());
    this.tasks.addTask(2, new AISlimeAttack());
    this.tasks.addTask(3, new AISlimeFaceRandom());
    this.tasks.addTask(5, new AISlimeHop());
    this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    this.targetTasks.addTask(3, new EntityAIFindEntityNearest(this, EntityIronGolem.class));
  }
  
  protected void entityInit()
  {
    super.entityInit();
    this.dataWatcher.addObject(16, Byte.valueOf((byte)1));
  }
  
  protected void setSlimeSize(int p_70799_1_)
  {
    this.dataWatcher.updateObject(16, Byte.valueOf((byte)p_70799_1_));
    setSize(0.51000005F * p_70799_1_, 0.51000005F * p_70799_1_);
    setPosition(this.posX, this.posY, this.posZ);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(p_70799_1_ * p_70799_1_);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F + 0.1F * p_70799_1_);
    setHealth(getMaxHealth());
    this.experienceValue = p_70799_1_;
  }
  
  public int getSlimeSize()
  {
    return this.dataWatcher.getWatchableObjectByte(16);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Size", getSlimeSize() - 1);
    tagCompound.setBoolean("wasOnGround", this.field_175452_bi);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    int var2 = tagCompund.getInteger("Size");
    if (var2 < 0) {
      var2 = 0;
    }
    setSlimeSize(var2 + 1);
    this.field_175452_bi = tagCompund.getBoolean("wasOnGround");
  }
  
  protected EnumParticleTypes func_180487_n()
  {
    return EnumParticleTypes.SLIME;
  }
  
  protected String getJumpSound()
  {
    return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
  }
  
  public void onUpdate()
  {
    if ((!this.worldObj.isRemote) && (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) && (getSlimeSize() > 0)) {
      this.isDead = true;
    }
    this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
    this.prevSquishFactor = this.squishFactor;
    super.onUpdate();
    if ((this.onGround) && (!this.field_175452_bi))
    {
      int var1 = getSlimeSize();
      for (int var2 = 0; var2 < var1 * 8; var2++)
      {
        float var3 = this.rand.nextFloat() * 3.1415927F * 2.0F;
        float var4 = this.rand.nextFloat() * 0.5F + 0.5F;
        float var5 = MathHelper.sin(var3) * var1 * 0.5F * var4;
        float var6 = MathHelper.cos(var3) * var1 * 0.5F * var4;
        World var10000 = this.worldObj;
        EnumParticleTypes var10001 = func_180487_n();
        double var10002 = this.posX + var5;
        double var10004 = this.posZ + var6;
        var10000.spawnParticle(var10001, var10002, getEntityBoundingBox().minY, var10004, 0.0D, 0.0D, 0.0D, new int[0]);
      }
      if (makesSoundOnLand()) {
        playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
      }
      this.squishAmount = -0.5F;
    }
    else if ((!this.onGround) && (this.field_175452_bi))
    {
      this.squishAmount = 1.0F;
    }
    this.field_175452_bi = this.onGround;
    alterSquishAmount();
  }
  
  protected void alterSquishAmount()
  {
    this.squishAmount *= 0.6F;
  }
  
  protected int getJumpDelay()
  {
    return this.rand.nextInt(20) + 10;
  }
  
  protected EntitySlime createInstance()
  {
    return new EntitySlime(this.worldObj);
  }
  
  public void func_145781_i(int p_145781_1_)
  {
    if (p_145781_1_ == 16)
    {
      int var2 = getSlimeSize();
      setSize(0.51000005F * var2, 0.51000005F * var2);
      this.rotationYaw = this.rotationYawHead;
      this.renderYawOffset = this.rotationYawHead;
      if ((isInWater()) && (this.rand.nextInt(20) == 0)) {
        resetHeight();
      }
    }
    super.func_145781_i(p_145781_1_);
  }
  
  public void setDead()
  {
    int var1 = getSlimeSize();
    if ((!this.worldObj.isRemote) && (var1 > 1) && (getHealth() <= 0.0F))
    {
      int var2 = 2 + this.rand.nextInt(3);
      for (int var3 = 0; var3 < var2; var3++)
      {
        float var4 = (var3 % 2 - 0.5F) * var1 / 4.0F;
        float var5 = (var3 / 2 - 0.5F) * var1 / 4.0F;
        EntitySlime var6 = createInstance();
        if (hasCustomName()) {
          var6.setCustomNameTag(getCustomNameTag());
        }
        if (isNoDespawnRequired()) {
          var6.enablePersistence();
        }
        var6.setSlimeSize(var1 / 2);
        var6.setLocationAndAngles(this.posX + var4, this.posY + 0.5D, this.posZ + var5, this.rand.nextFloat() * 360.0F, 0.0F);
        this.worldObj.spawnEntityInWorld(var6);
      }
    }
    super.setDead();
  }
  
  public void applyEntityCollision(Entity entityIn)
  {
    super.applyEntityCollision(entityIn);
    if (((entityIn instanceof EntityIronGolem)) && (canDamagePlayer())) {
      func_175451_e((EntityLivingBase)entityIn);
    }
  }
  
  public void onCollideWithPlayer(EntityPlayer entityIn)
  {
    if (canDamagePlayer()) {
      func_175451_e(entityIn);
    }
  }
  
  protected void func_175451_e(EntityLivingBase p_175451_1_)
  {
    int var2 = getSlimeSize();
    if ((canEntityBeSeen(p_175451_1_)) && (getDistanceSqToEntity(p_175451_1_) < 0.6D * var2 * 0.6D * var2) && (p_175451_1_.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength())))
    {
      playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      func_174815_a(this, p_175451_1_);
    }
  }
  
  public float getEyeHeight()
  {
    return 0.625F * this.height;
  }
  
  protected boolean canDamagePlayer()
  {
    return getSlimeSize() > 1;
  }
  
  protected int getAttackStrength()
  {
    return getSlimeSize();
  }
  
  protected String getHurtSound()
  {
    return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
  }
  
  protected String getDeathSound()
  {
    return "mob.slime." + (getSlimeSize() > 1 ? "big" : "small");
  }
  
  protected Item getDropItem()
  {
    return getSlimeSize() == 1 ? Items.slime_ball : null;
  }
  
  public boolean getCanSpawnHere()
  {
    Chunk var1 = this.worldObj.getChunkFromBlockCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
    if ((this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT) && (this.rand.nextInt(4) != 1)) {
      return false;
    }
    if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)
    {
      BiomeGenBase var2 = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
      if ((var2 == BiomeGenBase.swampland) && (this.posY > 50.0D) && (this.posY < 70.0D) && (this.rand.nextFloat() < 0.5F) && (this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor()) && (this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(8))) {
        return super.getCanSpawnHere();
      }
      if ((this.rand.nextInt(10) == 0) && (var1.getRandomWithSeed(987234911L).nextInt(10) == 0) && (this.posY < 40.0D)) {
        return super.getCanSpawnHere();
      }
    }
    return false;
  }
  
  protected float getSoundVolume()
  {
    return 0.4F * getSlimeSize();
  }
  
  public int getVerticalFaceSpeed()
  {
    return 0;
  }
  
  protected boolean makesSoundOnJump()
  {
    return getSlimeSize() > 0;
  }
  
  protected boolean makesSoundOnLand()
  {
    return getSlimeSize() > 2;
  }
  
  protected void jump()
  {
    this.motionY = 0.41999998688697815D;
    this.isAirBorne = true;
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    int var3 = this.rand.nextInt(3);
    if ((var3 < 2) && (this.rand.nextFloat() < 0.5F * p_180482_1_.func_180170_c())) {
      var3++;
    }
    int var4 = 1 << var3;
    setSlimeSize(var4);
    return super.func_180482_a(p_180482_1_, p_180482_2_);
  }
  
  class AISlimeAttack
    extends EntityAIBase
  {
    private EntitySlime field_179466_a = EntitySlime.this;
    private int field_179465_b;
    private static final String __OBFID = "CL_00002202";
    
    public AISlimeAttack()
    {
      setMutexBits(2);
    }
    
    public boolean shouldExecute()
    {
      EntityLivingBase var1 = this.field_179466_a.getAttackTarget();
      return var1 == null ? false : var1.isEntityAlive();
    }
    
    public void startExecuting()
    {
      this.field_179465_b = 300;
      super.startExecuting();
    }
    
    public boolean continueExecuting()
    {
      EntityLivingBase var1 = this.field_179466_a.getAttackTarget();
      return var1 != null;
    }
    
    public void updateTask()
    {
      this.field_179466_a.faceEntity(this.field_179466_a.getAttackTarget(), 10.0F, 10.0F);
      ((EntitySlime.SlimeMoveHelper)this.field_179466_a.getMoveHelper()).func_179920_a(this.field_179466_a.rotationYaw, this.field_179466_a.canDamagePlayer());
    }
  }
  
  class AISlimeFaceRandom
    extends EntityAIBase
  {
    private EntitySlime field_179461_a = EntitySlime.this;
    private float field_179459_b;
    private int field_179460_c;
    private static final String __OBFID = "CL_00002198";
    
    public AISlimeFaceRandom()
    {
      setMutexBits(2);
    }
    
    public boolean shouldExecute()
    {
      return (this.field_179461_a.getAttackTarget() == null) && ((this.field_179461_a.onGround) || (this.field_179461_a.isInWater()) || (this.field_179461_a.func_180799_ab()));
    }
    
    public void updateTask()
    {
      if (--this.field_179460_c <= 0)
      {
        this.field_179460_c = (40 + this.field_179461_a.getRNG().nextInt(60));
        this.field_179459_b = this.field_179461_a.getRNG().nextInt(360);
      }
      ((EntitySlime.SlimeMoveHelper)this.field_179461_a.getMoveHelper()).func_179920_a(this.field_179459_b, false);
    }
  }
  
  class AISlimeFloat
    extends EntityAIBase
  {
    private EntitySlime field_179457_a = EntitySlime.this;
    private static final String __OBFID = "CL_00002201";
    
    public AISlimeFloat()
    {
      setMutexBits(5);
      ((PathNavigateGround)EntitySlime.this.getNavigator()).func_179693_d(true);
    }
    
    public boolean shouldExecute()
    {
      return (this.field_179457_a.isInWater()) || (this.field_179457_a.func_180799_ab());
    }
    
    public void updateTask()
    {
      if (this.field_179457_a.getRNG().nextFloat() < 0.8F) {
        this.field_179457_a.getJumpHelper().setJumping();
      }
      ((EntitySlime.SlimeMoveHelper)this.field_179457_a.getMoveHelper()).func_179921_a(1.2D);
    }
  }
  
  class AISlimeHop
    extends EntityAIBase
  {
    private EntitySlime field_179458_a = EntitySlime.this;
    private static final String __OBFID = "CL_00002200";
    
    public AISlimeHop()
    {
      setMutexBits(5);
    }
    
    public boolean shouldExecute()
    {
      return true;
    }
    
    public void updateTask()
    {
      ((EntitySlime.SlimeMoveHelper)this.field_179458_a.getMoveHelper()).func_179921_a(1.0D);
    }
  }
  
  class SlimeMoveHelper
    extends EntityMoveHelper
  {
    private float field_179922_g;
    private int field_179924_h;
    private EntitySlime field_179925_i = EntitySlime.this;
    private boolean field_179923_j;
    private static final String __OBFID = "CL_00002199";
    
    public SlimeMoveHelper()
    {
      super();
    }
    
    public void func_179920_a(float p_179920_1_, boolean p_179920_2_)
    {
      this.field_179922_g = p_179920_1_;
      this.field_179923_j = p_179920_2_;
    }
    
    public void func_179921_a(double p_179921_1_)
    {
      this.speed = p_179921_1_;
      this.update = true;
    }
    
    public void onUpdateMoveHelper()
    {
      this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0F);
      this.entity.rotationYawHead = this.entity.rotationYaw;
      this.entity.renderYawOffset = this.entity.rotationYaw;
      if (!this.update)
      {
        this.entity.setMoveForward(0.0F);
      }
      else
      {
        this.update = false;
        if (this.entity.onGround)
        {
          this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
          if (this.field_179924_h-- <= 0)
          {
            this.field_179924_h = this.field_179925_i.getJumpDelay();
            if (this.field_179923_j) {
              this.field_179924_h /= 3;
            }
            this.field_179925_i.getJumpHelper().setJumping();
            if (this.field_179925_i.makesSoundOnJump()) {
              this.field_179925_i.playSound(this.field_179925_i.getJumpSound(), this.field_179925_i.getSoundVolume(), ((this.field_179925_i.getRNG().nextFloat() - this.field_179925_i.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }
          }
          else
          {
            this.field_179925_i.moveStrafing = (this.field_179925_i.moveForward = 0.0F);
            this.entity.setAIMoveSpeed(0.0F);
          }
        }
        else
        {
          this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
        }
      }
    }
  }
}
