package net.minecraft.entity.boss;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityDragon
  extends EntityLiving implements IBossDisplayData, IEntityMultiPart, IMob
{
  public double targetX;
  public double targetY;
  public double targetZ;
  public double[][] ringBuffer = new double[64][3];
  



  public int ringBufferIndex = -1;
  

  public EntityDragonPart[] dragonPartArray;
  

  public EntityDragonPart dragonPartHead;
  

  public EntityDragonPart dragonPartBody;
  
  public EntityDragonPart dragonPartTail1;
  
  public EntityDragonPart dragonPartTail2;
  
  public EntityDragonPart dragonPartTail3;
  
  public EntityDragonPart dragonPartWing1;
  
  public EntityDragonPart dragonPartWing2;
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean forceNewTarget;
  
  public boolean slowed;
  
  private Entity target;
  
  public int deathTicks;
  
  public EntityEnderCrystal healingEnderCrystal;
  
  private static final String __OBFID = "CL_00001659";
  

  public EntityDragon(World worldIn)
  {
    super(worldIn); EntityDragonPart[] 
      tmp27_24 = new EntityDragonPart[7]; void tmp44_41 = new EntityDragonPart(this, "head", 6.0F, 6.0F);dragonPartHead = tmp44_41;tmp27_24[0] = tmp44_41; EntityDragonPart[] tmp49_27 = tmp27_24; void tmp66_63 = new EntityDragonPart(this, "body", 8.0F, 8.0F);dragonPartBody = tmp66_63;tmp49_27[1] = tmp66_63; EntityDragonPart[] tmp71_49 = tmp49_27; void tmp88_85 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);dragonPartTail1 = tmp88_85;tmp71_49[2] = tmp88_85; EntityDragonPart[] tmp93_71 = tmp71_49; void tmp110_107 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);dragonPartTail2 = tmp110_107;tmp93_71[3] = tmp110_107; EntityDragonPart[] tmp115_93 = tmp93_71; void tmp132_129 = new EntityDragonPart(this, "tail", 4.0F, 4.0F);dragonPartTail3 = tmp132_129;tmp115_93[4] = tmp132_129; EntityDragonPart[] tmp137_115 = tmp115_93; void tmp154_151 = new EntityDragonPart(this, "wing", 4.0F, 4.0F);dragonPartWing1 = tmp154_151;tmp137_115[5] = tmp154_151; EntityDragonPart[] tmp159_137 = tmp137_115; void tmp177_174 = new EntityDragonPart(this, "wing", 4.0F, 4.0F);dragonPartWing2 = tmp177_174;tmp159_137[6] = tmp177_174;dragonPartArray = tmp159_137;
    setHealth(getMaxHealth());
    setSize(16.0F, 8.0F);
    noClip = true;
    isImmuneToFire = true;
    targetY = 100.0D;
    ignoreFrustumCheck = true;
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200.0D);
  }
  
  protected void entityInit()
  {
    super.entityInit();
  }
  




  public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_)
  {
    if (getHealth() <= 0.0F)
    {
      p_70974_2_ = 0.0F;
    }
    
    p_70974_2_ = 1.0F - p_70974_2_;
    int var3 = ringBufferIndex - p_70974_1_ * 1 & 0x3F;
    int var4 = ringBufferIndex - p_70974_1_ * 1 - 1 & 0x3F;
    double[] var5 = new double[3];
    double var6 = ringBuffer[var3][0];
    double var8 = MathHelper.wrapAngleTo180_double(ringBuffer[var4][0] - var6);
    var5[0] = (var6 + var8 * p_70974_2_);
    var6 = ringBuffer[var3][1];
    var8 = ringBuffer[var4][1] - var6;
    var5[1] = (var6 + var8 * p_70974_2_);
    var5[2] = (ringBuffer[var3][2] + (ringBuffer[var4][2] - ringBuffer[var3][2]) * p_70974_2_);
    return var5;
  }
  







  public void onLivingUpdate()
  {
    if (worldObj.isRemote)
    {
      float var1 = MathHelper.cos(animTime * 3.1415927F * 2.0F);
      float var2 = MathHelper.cos(prevAnimTime * 3.1415927F * 2.0F);
      
      if ((var2 <= -0.3F) && (var1 >= -0.3F) && (!isSlient()))
      {
        worldObj.playSound(posX, posY, posZ, "mob.enderdragon.wings", 5.0F, 0.8F + rand.nextFloat() * 0.3F, false);
      }
    }
    
    prevAnimTime = animTime;
    

    if (getHealth() <= 0.0F)
    {
      float var1 = (rand.nextFloat() - 0.5F) * 8.0F;
      float var2 = (rand.nextFloat() - 0.5F) * 4.0F;
      float var3 = (rand.nextFloat() - 0.5F) * 8.0F;
      worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX + var1, posY + 2.0D + var2, posZ + var3, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    else
    {
      updateDragonEnderCrystal();
      float var1 = 0.2F / (MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 10.0F + 1.0F);
      var1 *= (float)Math.pow(2.0D, motionY);
      
      if (slowed)
      {
        animTime += var1 * 0.5F;
      }
      else
      {
        animTime += var1;
      }
      
      rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
      
      if (ringBufferIndex < 0)
      {
        for (int var27 = 0; var27 < ringBuffer.length; var27++)
        {
          ringBuffer[var27][0] = rotationYaw;
          ringBuffer[var27][1] = posY;
        }
      }
      
      if (++ringBufferIndex == ringBuffer.length)
      {
        ringBufferIndex = 0;
      }
      
      ringBuffer[ringBufferIndex][0] = rotationYaw;
      ringBuffer[ringBufferIndex][1] = posY;
      





      if (worldObj.isRemote)
      {
        if (newPosRotationIncrements > 0)
        {
          double var28 = posX + (newPosX - posX) / newPosRotationIncrements;
          double var4 = posY + (newPosY - posY) / newPosRotationIncrements;
          double var6 = posZ + (newPosZ - posZ) / newPosRotationIncrements;
          double var8 = MathHelper.wrapAngleTo180_double(newRotationYaw - rotationYaw);
          rotationYaw = ((float)(rotationYaw + var8 / newPosRotationIncrements));
          rotationPitch = ((float)(rotationPitch + (newRotationPitch - rotationPitch) / newPosRotationIncrements));
          newPosRotationIncrements -= 1;
          setPosition(var28, var4, var6);
          setRotation(rotationYaw, rotationPitch);
        }
      }
      else
      {
        double var28 = targetX - posX;
        double var4 = targetY - posY;
        double var6 = targetZ - posZ;
        double var8 = var28 * var28 + var4 * var4 + var6 * var6;
        

        if (target != null)
        {
          targetX = target.posX;
          targetZ = target.posZ;
          double var10 = targetX - posX;
          double var12 = targetZ - posZ;
          double var14 = Math.sqrt(var10 * var10 + var12 * var12);
          double var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;
          
          if (var16 > 10.0D)
          {
            var16 = 10.0D;
          }
          
          targetY = (target.getEntityBoundingBox().minY + var16);
        }
        else
        {
          targetX += rand.nextGaussian() * 2.0D;
          targetZ += rand.nextGaussian() * 2.0D;
        }
        
        if ((forceNewTarget) || (var8 < 100.0D) || (var8 > 22500.0D) || (isCollidedHorizontally) || (isCollidedVertically))
        {
          setNewTarget();
        }
        
        var4 /= MathHelper.sqrt_double(var28 * var28 + var6 * var6);
        float var33 = 0.6F;
        var4 = MathHelper.clamp_double(var4, -var33, var33);
        motionY += var4 * 0.10000000149011612D;
        rotationYaw = MathHelper.wrapAngleTo180_float(rotationYaw);
        double var11 = 180.0D - Math.atan2(var28, var6) * 180.0D / 3.141592653589793D;
        double var13 = MathHelper.wrapAngleTo180_double(var11 - rotationYaw);
        
        if (var13 > 50.0D)
        {
          var13 = 50.0D;
        }
        
        if (var13 < -50.0D)
        {
          var13 = -50.0D;
        }
        
        Vec3 var15 = new Vec3(targetX - posX, targetY - posY, targetZ - posZ).normalize();
        double var16 = -MathHelper.cos(rotationYaw * 3.1415927F / 180.0F);
        Vec3 var18 = new Vec3(MathHelper.sin(rotationYaw * 3.1415927F / 180.0F), motionY, var16).normalize();
        float var19 = ((float)var18.dotProduct(var15) + 0.5F) / 1.5F;
        
        if (var19 < 0.0F)
        {
          var19 = 0.0F;
        }
        
        randomYawVelocity *= 0.8F;
        float var20 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ) * 1.0F + 1.0F;
        double var21 = Math.sqrt(motionX * motionX + motionZ * motionZ) * 1.0D + 1.0D;
        
        if (var21 > 40.0D)
        {
          var21 = 40.0D;
        }
        
        randomYawVelocity = ((float)(randomYawVelocity + var13 * (0.699999988079071D / var21 / var20)));
        rotationYaw += randomYawVelocity * 0.1F;
        float var23 = (float)(2.0D / (var21 + 1.0D));
        float var24 = 0.06F;
        moveFlying(0.0F, -1.0F, var24 * (var19 * var23 + (1.0F - var23)));
        
        if (slowed)
        {
          moveEntity(motionX * 0.800000011920929D, motionY * 0.800000011920929D, motionZ * 0.800000011920929D);
        }
        else
        {
          moveEntity(motionX, motionY, motionZ);
        }
        
        Vec3 var25 = new Vec3(motionX, motionY, motionZ).normalize();
        float var26 = ((float)var25.dotProduct(var18) + 1.0F) / 2.0F;
        var26 = 0.8F + 0.15F * var26;
        motionX *= var26;
        motionZ *= var26;
        motionY *= 0.9100000262260437D;
      }
      
      renderYawOffset = rotationYaw;
      dragonPartHead.width = (dragonPartHead.height = 3.0F);
      dragonPartTail1.width = (dragonPartTail1.height = 2.0F);
      dragonPartTail2.width = (dragonPartTail2.height = 2.0F);
      dragonPartTail3.width = (dragonPartTail3.height = 2.0F);
      dragonPartBody.height = 3.0F;
      dragonPartBody.width = 5.0F;
      dragonPartWing1.height = 2.0F;
      dragonPartWing1.width = 4.0F;
      dragonPartWing2.height = 3.0F;
      dragonPartWing2.width = 4.0F;
      float var2 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
      float var3 = MathHelper.cos(var2);
      float var29 = -MathHelper.sin(var2);
      float var5 = rotationYaw * 3.1415927F / 180.0F;
      float var30 = MathHelper.sin(var5);
      float var7 = MathHelper.cos(var5);
      dragonPartBody.onUpdate();
      dragonPartBody.setLocationAndAngles(posX + var30 * 0.5F, posY, posZ - var7 * 0.5F, 0.0F, 0.0F);
      dragonPartWing1.onUpdate();
      dragonPartWing1.setLocationAndAngles(posX + var7 * 4.5F, posY + 2.0D, posZ + var30 * 4.5F, 0.0F, 0.0F);
      dragonPartWing2.onUpdate();
      dragonPartWing2.setLocationAndAngles(posX - var7 * 4.5F, posY + 2.0D, posZ - var30 * 4.5F, 0.0F, 0.0F);
      
      if ((!worldObj.isRemote) && (hurtTime == 0))
      {
        collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
        collideWithEntities(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
        attackEntitiesInList(worldObj.getEntitiesWithinAABBExcludingEntity(this, dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
      }
      
      double[] var31 = getMovementOffsets(5, 1.0F);
      double[] var9 = getMovementOffsets(0, 1.0F);
      float var33 = MathHelper.sin(rotationYaw * 3.1415927F / 180.0F - randomYawVelocity * 0.01F);
      float var35 = MathHelper.cos(rotationYaw * 3.1415927F / 180.0F - randomYawVelocity * 0.01F);
      dragonPartHead.onUpdate();
      dragonPartHead.setLocationAndAngles(posX + var33 * 5.5F * var3, posY + (var9[1] - var31[1]) * 1.0D + var29 * 5.5F, posZ - var35 * 5.5F * var3, 0.0F, 0.0F);
      
      for (int var32 = 0; var32 < 3; var32++)
      {
        EntityDragonPart var34 = null;
        
        if (var32 == 0)
        {
          var34 = dragonPartTail1;
        }
        
        if (var32 == 1)
        {
          var34 = dragonPartTail2;
        }
        
        if (var32 == 2)
        {
          var34 = dragonPartTail3;
        }
        
        double[] var36 = getMovementOffsets(12 + var32 * 2, 1.0F);
        float var37 = rotationYaw * 3.1415927F / 180.0F + simplifyAngle(var36[0] - var31[0]) * 3.1415927F / 180.0F * 1.0F;
        float var38 = MathHelper.sin(var37);
        float var39 = MathHelper.cos(var37);
        float var40 = 1.5F;
        float var41 = (var32 + 1) * 2.0F;
        var34.onUpdate();
        var34.setLocationAndAngles(posX - (var30 * var40 + var38 * var41) * var3, posY + (var36[1] - var31[1]) * 1.0D - (var41 + var40) * var29 + 1.5D, posZ + (var7 * var40 + var39 * var41) * var3, 0.0F, 0.0F);
      }
      
      if (!worldObj.isRemote)
      {
        slowed = (destroyBlocksInAABB(dragonPartHead.getEntityBoundingBox()) | destroyBlocksInAABB(dragonPartBody.getEntityBoundingBox()));
      }
    }
  }
  



  private void updateDragonEnderCrystal()
  {
    if (healingEnderCrystal != null)
    {
      if (healingEnderCrystal.isDead)
      {
        if (!worldObj.isRemote)
        {
          attackEntityFromPart(dragonPartHead, DamageSource.setExplosionSource(null), 10.0F);
        }
        
        healingEnderCrystal = null;
      }
      else if ((ticksExisted % 10 == 0) && (getHealth() < getMaxHealth()))
      {
        setHealth(getHealth() + 1.0F);
      }
    }
    
    if (rand.nextInt(10) == 0)
    {
      float var1 = 32.0F;
      List var2 = worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().expand(var1, var1, var1));
      EntityEnderCrystal var3 = null;
      double var4 = Double.MAX_VALUE;
      Iterator var6 = var2.iterator();
      
      while (var6.hasNext())
      {
        EntityEnderCrystal var7 = (EntityEnderCrystal)var6.next();
        double var8 = var7.getDistanceSqToEntity(this);
        
        if (var8 < var4)
        {
          var4 = var8;
          var3 = var7;
        }
      }
      
      healingEnderCrystal = var3;
    }
  }
  



  private void collideWithEntities(List p_70970_1_)
  {
    double var2 = (dragonPartBody.getEntityBoundingBox().minX + dragonPartBody.getEntityBoundingBox().maxX) / 2.0D;
    double var4 = (dragonPartBody.getEntityBoundingBox().minZ + dragonPartBody.getEntityBoundingBox().maxZ) / 2.0D;
    Iterator var6 = p_70970_1_.iterator();
    
    while (var6.hasNext())
    {
      Entity var7 = (Entity)var6.next();
      
      if ((var7 instanceof EntityLivingBase))
      {
        double var8 = posX - var2;
        double var10 = posZ - var4;
        double var12 = var8 * var8 + var10 * var10;
        var7.addVelocity(var8 / var12 * 4.0D, 0.20000000298023224D, var10 / var12 * 4.0D);
      }
    }
  }
  



  private void attackEntitiesInList(List p_70971_1_)
  {
    for (int var2 = 0; var2 < p_70971_1_.size(); var2++)
    {
      Entity var3 = (Entity)p_70971_1_.get(var2);
      
      if ((var3 instanceof EntityLivingBase))
      {
        var3.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
        func_174815_a(this, var3);
      }
    }
  }
  



  private void setNewTarget()
  {
    forceNewTarget = false;
    ArrayList var1 = Lists.newArrayList(worldObj.playerEntities);
    Iterator var2 = var1.iterator();
    
    while (var2.hasNext())
    {
      if (((EntityPlayer)var2.next()).func_175149_v())
      {
        var2.remove();
      }
    }
    
    if ((rand.nextInt(2) == 0) && (!var1.isEmpty()))
    {
      target = ((Entity)var1.get(rand.nextInt(var1.size())));
    }
    else
    {
      boolean var3;
      
      do
      {
        targetX = 0.0D;
        targetY = (70.0F + rand.nextFloat() * 50.0F);
        targetZ = 0.0D;
        targetX += rand.nextFloat() * 120.0F - 60.0F;
        targetZ += rand.nextFloat() * 120.0F - 60.0F;
        double var4 = posX - targetX;
        double var6 = posY - targetY;
        double var8 = posZ - targetZ;
        var3 = var4 * var4 + var6 * var6 + var8 * var8 > 100.0D;
      }
      while (!var3);
      
      target = null;
    }
  }
  



  private float simplifyAngle(double p_70973_1_)
  {
    return (float)MathHelper.wrapAngleTo180_double(p_70973_1_);
  }
  



  private boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
  {
    int var2 = MathHelper.floor_double(minX);
    int var3 = MathHelper.floor_double(minY);
    int var4 = MathHelper.floor_double(minZ);
    int var5 = MathHelper.floor_double(maxX);
    int var6 = MathHelper.floor_double(maxY);
    int var7 = MathHelper.floor_double(maxZ);
    boolean var8 = false;
    boolean var9 = false;
    
    for (int var10 = var2; var10 <= var5; var10++)
    {
      for (int var11 = var3; var11 <= var6; var11++)
      {
        for (int var12 = var4; var12 <= var7; var12++)
        {
          Block var13 = worldObj.getBlockState(new BlockPos(var10, var11, var12)).getBlock();
          
          if (var13.getMaterial() != Material.air)
          {
            if ((var13 != Blocks.barrier) && (var13 != Blocks.obsidian) && (var13 != Blocks.end_stone) && (var13 != Blocks.bedrock) && (var13 != Blocks.command_block) && (worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing")))
            {
              var9 = (worldObj.setBlockToAir(new BlockPos(var10, var11, var12))) || (var9);
            }
            else
            {
              var8 = true;
            }
          }
        }
      }
    }
    
    if (var9)
    {
      double var16 = minX + (maxX - minX) * rand.nextFloat();
      double var17 = minY + (maxY - minY) * rand.nextFloat();
      double var14 = minZ + (maxZ - minZ) * rand.nextFloat();
      worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var16, var17, var14, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    
    return var8;
  }
  
  public boolean attackEntityFromPart(EntityDragonPart p_70965_1_, DamageSource p_70965_2_, float p_70965_3_)
  {
    if (p_70965_1_ != dragonPartHead)
    {
      p_70965_3_ = p_70965_3_ / 4.0F + 1.0F;
    }
    
    float var4 = rotationYaw * 3.1415927F / 180.0F;
    float var5 = MathHelper.sin(var4);
    float var6 = MathHelper.cos(var4);
    targetX = (posX + var5 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F);
    targetY = (posY + rand.nextFloat() * 3.0F + 1.0D);
    targetZ = (posZ - var6 * 5.0F + (rand.nextFloat() - 0.5F) * 2.0F);
    target = null;
    
    if (((p_70965_2_.getEntity() instanceof EntityPlayer)) || (p_70965_2_.isExplosion()))
    {
      func_82195_e(p_70965_2_, p_70965_3_);
    }
    
    return true;
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (((source instanceof EntityDamageSource)) && (((EntityDamageSource)source).func_180139_w()))
    {
      func_82195_e(source, amount);
    }
    
    return false;
  }
  
  protected boolean func_82195_e(DamageSource p_82195_1_, float p_82195_2_)
  {
    return super.attackEntityFrom(p_82195_1_, p_82195_2_);
  }
  
  public void func_174812_G()
  {
    setDead();
  }
  



  protected void onDeathUpdate()
  {
    deathTicks += 1;
    
    if ((deathTicks >= 180) && (deathTicks <= 200))
    {
      float var1 = (rand.nextFloat() - 0.5F) * 8.0F;
      float var2 = (rand.nextFloat() - 0.5F) * 4.0F;
      float var3 = (rand.nextFloat() - 0.5F) * 8.0F;
      worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, posX + var1, posY + 2.0D + var2, posZ + var3, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    



    if (!worldObj.isRemote)
    {
      if ((deathTicks > 150) && (deathTicks % 5 == 0) && (worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
      {
        int var4 = 1000;
        
        while (var4 > 0)
        {
          int var5 = EntityXPOrb.getXPSplit(var4);
          var4 -= var5;
          worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
        }
      }
      
      if (deathTicks == 1)
      {
        worldObj.func_175669_a(1018, new BlockPos(this), 0);
      }
    }
    
    moveEntity(0.0D, 0.10000000149011612D, 0.0D);
    renderYawOffset = (this.rotationYaw += 20.0F);
    
    if ((deathTicks == 200) && (!worldObj.isRemote))
    {
      int var4 = 2000;
      
      while (var4 > 0)
      {
        int var5 = EntityXPOrb.getXPSplit(var4);
        var4 -= var5;
        worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY, posZ, var5));
      }
      
      func_175499_a(new BlockPos(posX, 64.0D, posZ));
      setDead();
    }
  }
  
  private void func_175499_a(BlockPos p_175499_1_)
  {
    boolean var2 = true;
    double var3 = 12.25D;
    double var5 = 6.25D;
    
    for (int var7 = -1; var7 <= 32; var7++)
    {
      for (int var8 = -4; var8 <= 4; var8++)
      {
        for (int var9 = -4; var9 <= 4; var9++)
        {
          double var10 = var8 * var8 + var9 * var9;
          
          if (var10 <= 12.25D)
          {
            BlockPos var12 = p_175499_1_.add(var8, var7, var9);
            
            if (var7 < 0)
            {
              if (var10 <= 6.25D)
              {
                worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
              }
            }
            else if (var7 > 0)
            {
              worldObj.setBlockState(var12, Blocks.air.getDefaultState());
            }
            else if (var10 > 6.25D)
            {
              worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
            }
            else
            {
              worldObj.setBlockState(var12, Blocks.end_portal.getDefaultState());
            }
          }
        }
      }
    }
    
    worldObj.setBlockState(p_175499_1_, Blocks.bedrock.getDefaultState());
    worldObj.setBlockState(p_175499_1_.offsetUp(), Blocks.bedrock.getDefaultState());
    BlockPos var13 = p_175499_1_.offsetUp(2);
    worldObj.setBlockState(var13, Blocks.bedrock.getDefaultState());
    worldObj.setBlockState(var13.offsetWest(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST));
    worldObj.setBlockState(var13.offsetEast(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST));
    worldObj.setBlockState(var13.offsetNorth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH));
    worldObj.setBlockState(var13.offsetSouth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH));
    worldObj.setBlockState(p_175499_1_.offsetUp(3), Blocks.bedrock.getDefaultState());
    worldObj.setBlockState(p_175499_1_.offsetUp(4), Blocks.dragon_egg.getDefaultState());
  }
  



  protected void despawnEntity() {}
  



  public Entity[] getParts()
  {
    return dragonPartArray;
  }
  



  public boolean canBeCollidedWith()
  {
    return false;
  }
  
  public World func_82194_d()
  {
    return worldObj;
  }
  



  protected String getLivingSound()
  {
    return "mob.enderdragon.growl";
  }
  



  protected String getHurtSound()
  {
    return "mob.enderdragon.hit";
  }
  



  protected float getSoundVolume()
  {
    return 5.0F;
  }
}
