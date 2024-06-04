package net.minecraft.entity.monster;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityGuardian extends EntityMob
{
  private float field_175482_b;
  private float field_175484_c;
  private float field_175483_bk;
  private float field_175485_bl;
  private float field_175486_bm;
  private EntityLivingBase field_175478_bn;
  private int field_175479_bo;
  private boolean field_175480_bp;
  private EntityAIWander field_175481_bq;
  private static final String __OBFID = "CL_00002213";
  
  public EntityGuardian(World worldIn)
  {
    super(worldIn);
    experienceValue = 10;
    setSize(0.85F, 0.85F);
    tasks.addTask(4, new AIGuardianAttack());
    EntityAIMoveTowardsRestriction var2;
    tasks.addTask(5, var2 = new EntityAIMoveTowardsRestriction(this, 1.0D));
    tasks.addTask(7, this.field_175481_bq = new EntityAIWander(this, 1.0D, 80));
    tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(8, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0F, 0.01F));
    tasks.addTask(9, new net.minecraft.entity.ai.EntityAILookIdle(this));
    field_175481_bq.setMutexBits(3);
    var2.setMutexBits(3);
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector()));
    moveHelper = new GuardianMoveHelper();
    field_175484_c = (this.field_175482_b = rand.nextFloat());
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0D);
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    func_175467_a(tagCompund.getBoolean("Elder"));
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setBoolean("Elder", func_175461_cl());
  }
  
  protected PathNavigate func_175447_b(World worldIn)
  {
    return new net.minecraft.pathfinding.PathNavigateSwimmer(this, worldIn);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, Integer.valueOf(0));
    dataWatcher.addObject(17, Integer.valueOf(0));
  }
  
  private boolean func_175468_a(int p_175468_1_)
  {
    return (dataWatcher.getWatchableObjectInt(16) & p_175468_1_) != 0;
  }
  
  private void func_175473_a(int p_175473_1_, boolean p_175473_2_)
  {
    int var3 = dataWatcher.getWatchableObjectInt(16);
    
    if (p_175473_2_)
    {
      dataWatcher.updateObject(16, Integer.valueOf(var3 | p_175473_1_));
    }
    else
    {
      dataWatcher.updateObject(16, Integer.valueOf(var3 & (p_175473_1_ ^ 0xFFFFFFFF)));
    }
  }
  
  public boolean func_175472_n()
  {
    return func_175468_a(2);
  }
  
  private void func_175476_l(boolean p_175476_1_)
  {
    func_175473_a(2, p_175476_1_);
  }
  
  public int func_175464_ck()
  {
    return func_175461_cl() ? 60 : 80;
  }
  
  public boolean func_175461_cl()
  {
    return func_175468_a(4);
  }
  
  public void func_175467_a(boolean p_175467_1_)
  {
    func_175473_a(4, p_175467_1_);
    
    if (p_175467_1_)
    {
      setSize(1.9975F, 1.9975F);
      getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
      getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
      enablePersistence();
      field_175481_bq.func_179479_b(400);
    }
  }
  
  public void func_175465_cm()
  {
    func_175467_a(true);
    field_175486_bm = (this.field_175485_bl = 1.0F);
  }
  
  private void func_175463_b(int p_175463_1_)
  {
    dataWatcher.updateObject(17, Integer.valueOf(p_175463_1_));
  }
  
  public boolean func_175474_cn()
  {
    return dataWatcher.getWatchableObjectInt(17) != 0;
  }
  
  public EntityLivingBase func_175466_co()
  {
    if (!func_175474_cn())
    {
      return null;
    }
    if (worldObj.isRemote)
    {
      if (field_175478_bn != null)
      {
        return field_175478_bn;
      }
      

      Entity var1 = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(17));
      
      if ((var1 instanceof EntityLivingBase))
      {
        field_175478_bn = ((EntityLivingBase)var1);
        return field_175478_bn;
      }
      

      return null;
    }
    



    return getAttackTarget();
  }
  

  public void func_145781_i(int p_145781_1_)
  {
    super.func_145781_i(p_145781_1_);
    
    if (p_145781_1_ == 16)
    {
      if ((func_175461_cl()) && (width < 1.0F))
      {
        setSize(1.9975F, 1.9975F);
      }
    }
    else if (p_145781_1_ == 17)
    {
      field_175479_bo = 0;
      field_175478_bn = null;
    }
  }
  



  public int getTalkInterval()
  {
    return 160;
  }
  



  protected String getLivingSound()
  {
    return func_175461_cl() ? "mob.guardian.elder.idle" : !isInWater() ? "mob.guardian.land.idle" : "mob.guardian.idle";
  }
  



  protected String getHurtSound()
  {
    return func_175461_cl() ? "mob.guardian.elder.hit" : !isInWater() ? "mob.guardian.land.hit" : "mob.guardian.hit";
  }
  



  protected String getDeathSound()
  {
    return func_175461_cl() ? "mob.guardian.elder.death" : !isInWater() ? "mob.guardian.land.death" : "mob.guardian.death";
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  public float getEyeHeight()
  {
    return height * 0.5F;
  }
  
  public float func_180484_a(BlockPos p_180484_1_)
  {
    return worldObj.getBlockState(p_180484_1_).getBlock().getMaterial() == Material.water ? 10.0F + worldObj.getLightBrightness(p_180484_1_) - 0.5F : super.func_180484_a(p_180484_1_);
  }
  




  public void onLivingUpdate()
  {
    if (worldObj.isRemote)
    {
      field_175484_c = field_175482_b;
      
      if (!isInWater())
      {
        field_175483_bk = 2.0F;
        
        if ((motionY > 0.0D) && (field_175480_bp) && (!isSlient()))
        {
          worldObj.playSound(posX, posY, posZ, "mob.guardian.flop", 1.0F, 1.0F, false);
        }
        
        field_175480_bp = ((motionY < 0.0D) && (worldObj.func_175677_d(new BlockPos(this).offsetDown(), false)));
      }
      else if (func_175472_n())
      {
        if (field_175483_bk < 0.5F)
        {
          field_175483_bk = 4.0F;
        }
        else
        {
          field_175483_bk += (0.5F - field_175483_bk) * 0.1F;
        }
      }
      else
      {
        field_175483_bk += (0.125F - field_175483_bk) * 0.2F;
      }
      
      field_175482_b += field_175483_bk;
      field_175486_bm = field_175485_bl;
      
      if (!isInWater())
      {
        field_175485_bl = rand.nextFloat();
      }
      else if (func_175472_n())
      {
        field_175485_bl += (0.0F - field_175485_bl) * 0.25F;
      }
      else
      {
        field_175485_bl += (1.0F - field_175485_bl) * 0.06F;
      }
      
      if ((func_175472_n()) && (isInWater()))
      {
        Vec3 var1 = getLook(0.0F);
        
        for (int var2 = 0; var2 < 2; var2++)
        {
          worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + (rand.nextDouble() - 0.5D) * width - xCoord * 1.5D, posY + rand.nextDouble() * height - yCoord * 1.5D, posZ + (rand.nextDouble() - 0.5D) * width - zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
        }
      }
      
      if (func_175474_cn())
      {
        if (field_175479_bo < func_175464_ck())
        {
          field_175479_bo += 1;
        }
        
        EntityLivingBase var14 = func_175466_co();
        
        if (var14 != null)
        {
          getLookHelper().setLookPositionWithEntity(var14, 90.0F, 90.0F);
          getLookHelper().onUpdateLook();
          double var15 = func_175477_p(0.0F);
          double var4 = posX - posX;
          double var6 = posY + height * 0.5F - (posY + getEyeHeight());
          double var8 = posZ - posZ;
          double var10 = Math.sqrt(var4 * var4 + var6 * var6 + var8 * var8);
          var4 /= var10;
          var6 /= var10;
          var8 /= var10;
          double var12 = rand.nextDouble();
          
          while (var12 < var10)
          {
            var12 += 1.8D - var15 + rand.nextDouble() * (1.7D - var15);
            worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX + var4 * var12, posY + var6 * var12 + getEyeHeight(), posZ + var8 * var12, 0.0D, 0.0D, 0.0D, new int[0]);
          }
        }
      }
    }
    
    if (inWater)
    {
      setAir(300);
    }
    else if (onGround)
    {
      motionY += 0.5D;
      motionX += (rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
      motionZ += (rand.nextFloat() * 2.0F - 1.0F) * 0.4F;
      rotationYaw = (rand.nextFloat() * 360.0F);
      onGround = false;
      isAirBorne = true;
    }
    
    if (func_175474_cn())
    {
      rotationYaw = rotationYawHead;
    }
    
    super.onLivingUpdate();
  }
  
  public float func_175471_a(float p_175471_1_)
  {
    return field_175484_c + (field_175482_b - field_175484_c) * p_175471_1_;
  }
  
  public float func_175469_o(float p_175469_1_)
  {
    return field_175486_bm + (field_175485_bl - field_175486_bm) * p_175469_1_;
  }
  
  public float func_175477_p(float p_175477_1_)
  {
    return (field_175479_bo + p_175477_1_) / func_175464_ck();
  }
  
  protected void updateAITasks()
  {
    super.updateAITasks();
    
    if (func_175461_cl())
    {
      boolean var1 = true;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      
      if ((ticksExisted + getEntityId()) % 1200 == 0)
      {
        Potion var5 = Potion.digSlowdown;
        List var6 = worldObj.func_175661_b(EntityPlayerMP.class, new Predicate()
        {
          private static final String __OBFID = "CL_00002212";
          
          public boolean func_179913_a(EntityPlayerMP p_179913_1_) {
            return (getDistanceSqToEntity(p_179913_1_) < 2500.0D) && (theItemInWorldManager.func_180239_c());
          }
          
          public boolean apply(Object p_apply_1_) {
            return func_179913_a((EntityPlayerMP)p_apply_1_);
          }
        });
        Iterator var7 = var6.iterator();
        
        while (var7.hasNext())
        {
          EntityPlayerMP var8 = (EntityPlayerMP)var7.next();
          
          if ((!var8.isPotionActive(var5)) || (var8.getActivePotionEffect(var5).getAmplifier() < 2) || (var8.getActivePotionEffect(var5).getDuration() < 1200))
          {
            playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(10, 0.0F));
            var8.addPotionEffect(new PotionEffect(id, 6000, 2));
          }
        }
      }
      
      if (!hasHome())
      {
        func_175449_a(new BlockPos(this), 16);
      }
    }
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    int var3 = rand.nextInt(3) + rand.nextInt(p_70628_2_ + 1);
    
    if (var3 > 0)
    {
      entityDropItem(new ItemStack(Items.prismarine_shard, var3, 0), 1.0F);
    }
    
    if (rand.nextInt(3 + p_70628_2_) > 1)
    {
      entityDropItem(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 1.0F);
    }
    else if (rand.nextInt(3 + p_70628_2_) > 1)
    {
      entityDropItem(new ItemStack(Items.prismarine_crystals, 1, 0), 1.0F);
    }
    
    if ((p_70628_1_) && (func_175461_cl()))
    {
      entityDropItem(new ItemStack(net.minecraft.init.Blocks.sponge, 1, 1), 1.0F);
    }
  }
  



  protected void addRandomArmor()
  {
    ItemStack var1 = ((WeightedRandomFishable)WeightedRandom.getRandomItem(rand, net.minecraft.entity.projectile.EntityFishHook.func_174855_j())).getItemStack(rand);
    entityDropItem(var1, 1.0F);
  }
  



  protected boolean isValidLightLevel()
  {
    return true;
  }
  



  public boolean handleLavaMovement()
  {
    return (worldObj.checkNoEntityCollision(getEntityBoundingBox(), this)) && (worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty());
  }
  



  public boolean getCanSpawnHere()
  {
    return ((rand.nextInt(20) == 0) || (!worldObj.canBlockSeeSky(new BlockPos(this)))) && (super.getCanSpawnHere());
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if ((!func_175472_n()) && (!source.isMagicDamage()) && ((source.getSourceOfDamage() instanceof EntityLivingBase)))
    {
      EntityLivingBase var3 = (EntityLivingBase)source.getSourceOfDamage();
      
      if (!source.isExplosion())
      {
        var3.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
        var3.playSound("damage.thorns", 0.5F, 1.0F);
      }
    }
    
    field_175481_bq.func_179480_f();
    return super.attackEntityFrom(source, amount);
  }
  




  public int getVerticalFaceSpeed()
  {
    return 180;
  }
  



  public void moveEntityWithHeading(float p_70612_1_, float p_70612_2_)
  {
    if (isServerWorld())
    {
      if (isInWater())
      {
        moveFlying(p_70612_1_, p_70612_2_, 0.1F);
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.8999999761581421D;
        motionY *= 0.8999999761581421D;
        motionZ *= 0.8999999761581421D;
        
        if ((!func_175472_n()) && (getAttackTarget() == null))
        {
          motionY -= 0.005D;
        }
      }
      else
      {
        super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
      }
      
    }
    else {
      super.moveEntityWithHeading(p_70612_1_, p_70612_2_);
    }
  }
  
  class AIGuardianAttack extends EntityAIBase
  {
    private EntityGuardian field_179456_a = EntityGuardian.this;
    private int field_179455_b;
    private static final String __OBFID = "CL_00002211";
    
    public AIGuardianAttack()
    {
      setMutexBits(3);
    }
    
    public boolean shouldExecute()
    {
      EntityLivingBase var1 = field_179456_a.getAttackTarget();
      return (var1 != null) && (var1.isEntityAlive());
    }
    
    public boolean continueExecuting()
    {
      return (super.continueExecuting()) && ((field_179456_a.func_175461_cl()) || (field_179456_a.getDistanceSqToEntity(field_179456_a.getAttackTarget()) > 9.0D));
    }
    
    public void startExecuting()
    {
      field_179455_b = -10;
      field_179456_a.getNavigator().clearPathEntity();
      field_179456_a.getLookHelper().setLookPositionWithEntity(field_179456_a.getAttackTarget(), 90.0F, 90.0F);
      field_179456_a.isAirBorne = true;
    }
    
    public void resetTask()
    {
      field_179456_a.func_175463_b(0);
      field_179456_a.setAttackTarget(null);
      field_179456_a.field_175481_bq.func_179480_f();
    }
    
    public void updateTask()
    {
      EntityLivingBase var1 = field_179456_a.getAttackTarget();
      field_179456_a.getNavigator().clearPathEntity();
      field_179456_a.getLookHelper().setLookPositionWithEntity(var1, 90.0F, 90.0F);
      
      if (!field_179456_a.canEntityBeSeen(var1))
      {
        field_179456_a.setAttackTarget(null);
      }
      else
      {
        field_179455_b += 1;
        
        if (field_179455_b == 0)
        {
          field_179456_a.func_175463_b(field_179456_a.getAttackTarget().getEntityId());
          field_179456_a.worldObj.setEntityState(field_179456_a, (byte)21);
        }
        else if (field_179455_b >= field_179456_a.func_175464_ck())
        {
          float var2 = 1.0F;
          
          if (field_179456_a.worldObj.getDifficulty() == EnumDifficulty.HARD)
          {
            var2 += 2.0F;
          }
          
          if (field_179456_a.func_175461_cl())
          {
            var2 += 2.0F;
          }
          
          var1.attackEntityFrom(DamageSource.causeIndirectMagicDamage(field_179456_a, field_179456_a), var2);
          var1.attackEntityFrom(DamageSource.causeMobDamage(field_179456_a), (float)field_179456_a.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
          field_179456_a.setAttackTarget(null);
        }
        else if ((field_179455_b < 60) || (field_179455_b % 20 != 0)) {}
        



        super.updateTask();
      }
    }
  }
  
  class GuardianMoveHelper extends EntityMoveHelper
  {
    private EntityGuardian field_179930_g = EntityGuardian.this;
    private static final String __OBFID = "CL_00002209";
    
    public GuardianMoveHelper()
    {
      super();
    }
    
    public void onUpdateMoveHelper()
    {
      if ((update) && (!field_179930_g.getNavigator().noPath()))
      {
        double var1 = posX - field_179930_g.posX;
        double var3 = posY - field_179930_g.posY;
        double var5 = posZ - field_179930_g.posZ;
        double var7 = var1 * var1 + var3 * var3 + var5 * var5;
        var7 = net.minecraft.util.MathHelper.sqrt_double(var7);
        var3 /= var7;
        float var9 = (float)(Math.atan2(var5, var1) * 180.0D / 3.141592653589793D) - 90.0F;
        field_179930_g.rotationYaw = limitAngle(field_179930_g.rotationYaw, var9, 30.0F);
        field_179930_g.renderYawOffset = field_179930_g.rotationYaw;
        float var10 = (float)(speed * field_179930_g.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
        field_179930_g.setAIMoveSpeed(field_179930_g.getAIMoveSpeed() + (var10 - field_179930_g.getAIMoveSpeed()) * 0.125F);
        double var11 = Math.sin((field_179930_g.ticksExisted + field_179930_g.getEntityId()) * 0.5D) * 0.05D;
        double var13 = Math.cos(field_179930_g.rotationYaw * 3.1415927F / 180.0F);
        double var15 = Math.sin(field_179930_g.rotationYaw * 3.1415927F / 180.0F);
        field_179930_g.motionX += var11 * var13;
        field_179930_g.motionZ += var11 * var15;
        var11 = Math.sin((field_179930_g.ticksExisted + field_179930_g.getEntityId()) * 0.75D) * 0.05D;
        field_179930_g.motionY += var11 * (var15 + var13) * 0.25D;
        field_179930_g.motionY += field_179930_g.getAIMoveSpeed() * var3 * 0.1D;
        EntityLookHelper var17 = field_179930_g.getLookHelper();
        double var18 = field_179930_g.posX + var1 / var7 * 2.0D;
        double var20 = field_179930_g.getEyeHeight() + field_179930_g.posY + var3 / var7 * 1.0D;
        double var22 = field_179930_g.posZ + var5 / var7 * 2.0D;
        double var24 = var17.func_180423_e();
        double var26 = var17.func_180422_f();
        double var28 = var17.func_180421_g();
        
        if (!var17.func_180424_b())
        {
          var24 = var18;
          var26 = var20;
          var28 = var22;
        }
        
        field_179930_g.getLookHelper().setLookPosition(var24 + (var18 - var24) * 0.125D, var26 + (var20 - var26) * 0.125D, var28 + (var22 - var28) * 0.125D, 10.0F, 40.0F);
        field_179930_g.func_175476_l(true);
      }
      else
      {
        field_179930_g.setAIMoveSpeed(0.0F);
        field_179930_g.func_175476_l(false);
      }
    }
  }
  
  class GuardianTargetSelector implements Predicate
  {
    private EntityGuardian field_179916_a = EntityGuardian.this;
    private static final String __OBFID = "CL_00002210";
    
    GuardianTargetSelector() {}
    
    public boolean func_179915_a(EntityLivingBase p_179915_1_) { return (((p_179915_1_ instanceof EntityPlayer)) || ((p_179915_1_ instanceof EntitySquid))) && (p_179915_1_.getDistanceSqToEntity(field_179916_a) > 9.0D); }
    

    public boolean apply(Object p_apply_1_)
    {
      return func_179915_a((EntityLivingBase)p_apply_1_);
    }
  }
}
