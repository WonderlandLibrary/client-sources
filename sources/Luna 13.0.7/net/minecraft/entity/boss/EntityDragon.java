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
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityDragon
  extends EntityLiving
  implements IBossDisplayData, IEntityMultiPart, IMob
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
    super(worldIn);
    this.dragonPartArray = new EntityDragonPart[] { this.dragonPartHead = new EntityDragonPart(this, "head", 6.0F, 6.0F), this.dragonPartBody = new EntityDragonPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new EntityDragonPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new EntityDragonPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new EntityDragonPart(this, "wing", 4.0F, 4.0F) };
    setHealth(getMaxHealth());
    setSize(16.0F, 8.0F);
    this.noClip = true;
    this.isImmuneToFire = true;
    this.targetY = 100.0D;
    this.ignoreFrustumCheck = true;
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
    if (getHealth() <= 0.0F) {
      p_70974_2_ = 0.0F;
    }
    p_70974_2_ = 1.0F - p_70974_2_;
    int var3 = this.ringBufferIndex - p_70974_1_ * 1 & 0x3F;
    int var4 = this.ringBufferIndex - p_70974_1_ * 1 - 1 & 0x3F;
    double[] var5 = new double[3];
    double var6 = this.ringBuffer[var3][0];
    double var8 = MathHelper.wrapAngleTo180_double(this.ringBuffer[var4][0] - var6);
    var5[0] = (var6 + var8 * p_70974_2_);
    var6 = this.ringBuffer[var3][1];
    var8 = this.ringBuffer[var4][1] - var6;
    var5[1] = (var6 + var8 * p_70974_2_);
    var5[2] = (this.ringBuffer[var3][2] + (this.ringBuffer[var4][2] - this.ringBuffer[var3][2]) * p_70974_2_);
    return var5;
  }
  
  public void onLivingUpdate()
  {
    if (this.worldObj.isRemote)
    {
      float var1 = MathHelper.cos(this.animTime * 3.1415927F * 2.0F);
      float var2 = MathHelper.cos(this.prevAnimTime * 3.1415927F * 2.0F);
      if ((var2 <= -0.3F) && (var1 >= -0.3F) && (!isSlient())) {
        this.worldObj.playSound(this.posX, this.posY, this.posZ, "mob.enderdragon.wings", 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false);
      }
    }
    this.prevAnimTime = this.animTime;
    if (getHealth() <= 0.0F)
    {
      float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    else
    {
      updateDragonEnderCrystal();
      float var1 = 0.2F / (MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      var1 *= (float)Math.pow(2.0D, this.motionY);
      if (this.slowed) {
        this.animTime += var1 * 0.5F;
      } else {
        this.animTime += var1;
      }
      this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
      if (this.ringBufferIndex < 0) {
        for (int var27 = 0; var27 < this.ringBuffer.length; var27++)
        {
          this.ringBuffer[var27][0] = this.rotationYaw;
          this.ringBuffer[var27][1] = this.posY;
        }
      }
      if (++this.ringBufferIndex == this.ringBuffer.length) {
        this.ringBufferIndex = 0;
      }
      this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
      this.ringBuffer[this.ringBufferIndex][1] = this.posY;
      if (this.worldObj.isRemote)
      {
        if (this.newPosRotationIncrements > 0)
        {
          double var28 = this.posX + (this.newPosX - this.posX) / this.newPosRotationIncrements;
          double var4 = this.posY + (this.newPosY - this.posY) / this.newPosRotationIncrements;
          double var6 = this.posZ + (this.newPosZ - this.posZ) / this.newPosRotationIncrements;
          double var8 = MathHelper.wrapAngleTo180_double(this.newRotationYaw - this.rotationYaw);
          this.rotationYaw = ((float)(this.rotationYaw + var8 / this.newPosRotationIncrements));
          this.rotationPitch = ((float)(this.rotationPitch + (this.newRotationPitch - this.rotationPitch) / this.newPosRotationIncrements));
          this.newPosRotationIncrements -= 1;
          setPosition(var28, var4, var6);
          setRotation(this.rotationYaw, this.rotationPitch);
        }
      }
      else
      {
        double var28 = this.targetX - this.posX;
        double var4 = this.targetY - this.posY;
        double var6 = this.targetZ - this.posZ;
        double var8 = var28 * var28 + var4 * var4 + var6 * var6;
        if (this.target != null)
        {
          this.targetX = this.target.posX;
          this.targetZ = this.target.posZ;
          double var10 = this.targetX - this.posX;
          double var12 = this.targetZ - this.posZ;
          double var14 = Math.sqrt(var10 * var10 + var12 * var12);
          double var16 = 0.4000000059604645D + var14 / 80.0D - 1.0D;
          if (var16 > 10.0D) {
            var16 = 10.0D;
          }
          this.targetY = (this.target.getEntityBoundingBox().minY + var16);
        }
        else
        {
          this.targetX += this.rand.nextGaussian() * 2.0D;
          this.targetZ += this.rand.nextGaussian() * 2.0D;
        }
        if ((this.forceNewTarget) || (var8 < 100.0D) || (var8 > 22500.0D) || (this.isCollidedHorizontally) || (this.isCollidedVertically)) {
          setNewTarget();
        }
        var4 /= MathHelper.sqrt_double(var28 * var28 + var6 * var6);
        float var33 = 0.6F;
        var4 = MathHelper.clamp_double(var4, -var33, var33);
        this.motionY += var4 * 0.10000000149011612D;
        this.rotationYaw = MathHelper.wrapAngleTo180_float(this.rotationYaw);
        double var11 = 180.0D - Math.atan2(var28, var6) * 180.0D / 3.141592653589793D;
        double var13 = MathHelper.wrapAngleTo180_double(var11 - this.rotationYaw);
        if (var13 > 50.0D) {
          var13 = 50.0D;
        }
        if (var13 < -50.0D) {
          var13 = -50.0D;
        }
        Vec3 var15 = new Vec3(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ).normalize();
        double var16 = -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
        Vec3 var18 = new Vec3(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, var16).normalize();
        float var19 = ((float)var18.dotProduct(var15) + 0.5F) / 1.5F;
        if (var19 < 0.0F) {
          var19 = 0.0F;
        }
        this.randomYawVelocity *= 0.8F;
        float var20 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
        double var21 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
        if (var21 > 40.0D) {
          var21 = 40.0D;
        }
        this.randomYawVelocity = ((float)(this.randomYawVelocity + var13 * (0.699999988079071D / var21 / var20)));
        this.rotationYaw += this.randomYawVelocity * 0.1F;
        float var23 = (float)(2.0D / (var21 + 1.0D));
        float var24 = 0.06F;
        moveFlying(0.0F, -1.0F, var24 * (var19 * var23 + (1.0F - var23)));
        if (this.slowed) {
          moveEntity(this.motionX * 0.800000011920929D, this.motionY * 0.800000011920929D, this.motionZ * 0.800000011920929D);
        } else {
          moveEntity(this.motionX, this.motionY, this.motionZ);
        }
        Vec3 var25 = new Vec3(this.motionX, this.motionY, this.motionZ).normalize();
        float var26 = ((float)var25.dotProduct(var18) + 1.0F) / 2.0F;
        var26 = 0.8F + 0.15F * var26;
        this.motionX *= var26;
        this.motionZ *= var26;
        this.motionY *= 0.9100000262260437D;
      }
      this.renderYawOffset = this.rotationYaw;
      this.dragonPartHead.width = (this.dragonPartHead.height = 3.0F);
      this.dragonPartTail1.width = (this.dragonPartTail1.height = 2.0F);
      this.dragonPartTail2.width = (this.dragonPartTail2.height = 2.0F);
      this.dragonPartTail3.width = (this.dragonPartTail3.height = 2.0F);
      this.dragonPartBody.height = 3.0F;
      this.dragonPartBody.width = 5.0F;
      this.dragonPartWing1.height = 2.0F;
      this.dragonPartWing1.width = 4.0F;
      this.dragonPartWing2.height = 3.0F;
      this.dragonPartWing2.width = 4.0F;
      float var2 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F / 180.0F * 3.1415927F;
      float var3 = MathHelper.cos(var2);
      float var29 = -MathHelper.sin(var2);
      float var5 = this.rotationYaw * 3.1415927F / 180.0F;
      float var30 = MathHelper.sin(var5);
      float var7 = MathHelper.cos(var5);
      this.dragonPartBody.onUpdate();
      this.dragonPartBody.setLocationAndAngles(this.posX + var30 * 0.5F, this.posY, this.posZ - var7 * 0.5F, 0.0F, 0.0F);
      this.dragonPartWing1.onUpdate();
      this.dragonPartWing1.setLocationAndAngles(this.posX + var7 * 4.5F, this.posY + 2.0D, this.posZ + var30 * 4.5F, 0.0F, 0.0F);
      this.dragonPartWing2.onUpdate();
      this.dragonPartWing2.setLocationAndAngles(this.posX - var7 * 4.5F, this.posY + 2.0D, this.posZ - var30 * 4.5F, 0.0F, 0.0F);
      if ((!this.worldObj.isRemote) && (this.hurtTime == 0))
      {
        collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing1.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
        collideWithEntities(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartWing2.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D).offset(0.0D, -2.0D, 0.0D)));
        attackEntitiesInList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.dragonPartHead.getEntityBoundingBox().expand(1.0D, 1.0D, 1.0D)));
      }
      double[] var31 = getMovementOffsets(5, 1.0F);
      double[] var9 = getMovementOffsets(0, 1.0F);
      float var33 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
      float var35 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F - this.randomYawVelocity * 0.01F);
      this.dragonPartHead.onUpdate();
      this.dragonPartHead.setLocationAndAngles(this.posX + var33 * 5.5F * var3, this.posY + (var9[1] - var31[1]) * 1.0D + var29 * 5.5F, this.posZ - var35 * 5.5F * var3, 0.0F, 0.0F);
      for (int var32 = 0; var32 < 3; var32++)
      {
        EntityDragonPart var34 = null;
        if (var32 == 0) {
          var34 = this.dragonPartTail1;
        }
        if (var32 == 1) {
          var34 = this.dragonPartTail2;
        }
        if (var32 == 2) {
          var34 = this.dragonPartTail3;
        }
        double[] var36 = getMovementOffsets(12 + var32 * 2, 1.0F);
        float var37 = this.rotationYaw * 3.1415927F / 180.0F + simplifyAngle(var36[0] - var31[0]) * 3.1415927F / 180.0F * 1.0F;
        float var38 = MathHelper.sin(var37);
        float var39 = MathHelper.cos(var37);
        float var40 = 1.5F;
        float var41 = (var32 + 1) * 2.0F;
        var34.onUpdate();
        var34.setLocationAndAngles(this.posX - (var30 * var40 + var38 * var41) * var3, this.posY + (var36[1] - var31[1]) * 1.0D - (var41 + var40) * var29 + 1.5D, this.posZ + (var7 * var40 + var39 * var41) * var3, 0.0F, 0.0F);
      }
      if (!this.worldObj.isRemote) {
        this.slowed = (destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox()) | destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox()));
      }
    }
  }
  
  private void updateDragonEnderCrystal()
  {
    if (this.healingEnderCrystal != null) {
      if (this.healingEnderCrystal.isDead)
      {
        if (!this.worldObj.isRemote) {
          attackEntityFromPart(this.dragonPartHead, DamageSource.setExplosionSource((Explosion)null), 10.0F);
        }
        this.healingEnderCrystal = null;
      }
      else if ((this.ticksExisted % 10 == 0) && (getHealth() < getMaxHealth()))
      {
        setHealth(getHealth() + 1.0F);
      }
    }
    if (this.rand.nextInt(10) == 0)
    {
      float var1 = 32.0F;
      List var2 = this.worldObj.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().expand(var1, var1, var1));
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
      this.healingEnderCrystal = var3;
    }
  }
  
  private void collideWithEntities(List p_70970_1_)
  {
    double var2 = (this.dragonPartBody.getEntityBoundingBox().minX + this.dragonPartBody.getEntityBoundingBox().maxX) / 2.0D;
    double var4 = (this.dragonPartBody.getEntityBoundingBox().minZ + this.dragonPartBody.getEntityBoundingBox().maxZ) / 2.0D;
    Iterator var6 = p_70970_1_.iterator();
    while (var6.hasNext())
    {
      Entity var7 = (Entity)var6.next();
      if ((var7 instanceof EntityLivingBase))
      {
        double var8 = var7.posX - var2;
        double var10 = var7.posZ - var4;
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
    this.forceNewTarget = false;
    ArrayList var1 = Lists.newArrayList(this.worldObj.playerEntities);
    Iterator var2 = var1.iterator();
    while (var2.hasNext()) {
      if (((EntityPlayer)var2.next()).func_175149_v()) {
        var2.remove();
      }
    }
    if ((this.rand.nextInt(2) == 0) && (!var1.isEmpty()))
    {
      this.target = ((Entity)var1.get(this.rand.nextInt(var1.size())));
    }
    else
    {
      boolean var3;
      do
      {
        this.targetX = 0.0D;
        this.targetY = (70.0F + this.rand.nextFloat() * 50.0F);
        this.targetZ = 0.0D;
        this.targetX += this.rand.nextFloat() * 120.0F - 60.0F;
        this.targetZ += this.rand.nextFloat() * 120.0F - 60.0F;
        double var4 = this.posX - this.targetX;
        double var6 = this.posY - this.targetY;
        double var8 = this.posZ - this.targetZ;
        var3 = var4 * var4 + var6 * var6 + var8 * var8 > 100.0D;
      } while (!var3);
      this.target = null;
    }
  }
  
  private float simplifyAngle(double p_70973_1_)
  {
    return (float)MathHelper.wrapAngleTo180_double(p_70973_1_);
  }
  
  private boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
  {
    int var2 = MathHelper.floor_double(p_70972_1_.minX);
    int var3 = MathHelper.floor_double(p_70972_1_.minY);
    int var4 = MathHelper.floor_double(p_70972_1_.minZ);
    int var5 = MathHelper.floor_double(p_70972_1_.maxX);
    int var6 = MathHelper.floor_double(p_70972_1_.maxY);
    int var7 = MathHelper.floor_double(p_70972_1_.maxZ);
    boolean var8 = false;
    boolean var9 = false;
    for (int var10 = var2; var10 <= var5; var10++) {
      for (int var11 = var3; var11 <= var6; var11++) {
        for (int var12 = var4; var12 <= var7; var12++)
        {
          Block var13 = this.worldObj.getBlockState(new BlockPos(var10, var11, var12)).getBlock();
          if (var13.getMaterial() != Material.air) {
            if ((var13 != Blocks.barrier) && (var13 != Blocks.obsidian) && (var13 != Blocks.end_stone) && (var13 != Blocks.bedrock) && (var13 != Blocks.command_block) && (this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"))) {
              var9 = (this.worldObj.setBlockToAir(new BlockPos(var10, var11, var12))) || (var9);
            } else {
              var8 = true;
            }
          }
        }
      }
    }
    if (var9)
    {
      double var16 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * this.rand.nextFloat();
      double var17 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * this.rand.nextFloat();
      double var14 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * this.rand.nextFloat();
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, var16, var17, var14, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    return var8;
  }
  
  public boolean attackEntityFromPart(EntityDragonPart p_70965_1_, DamageSource p_70965_2_, float p_70965_3_)
  {
    if (p_70965_1_ != this.dragonPartHead) {
      p_70965_3_ = p_70965_3_ / 4.0F + 1.0F;
    }
    float var4 = this.rotationYaw * 3.1415927F / 180.0F;
    float var5 = MathHelper.sin(var4);
    float var6 = MathHelper.cos(var4);
    this.targetX = (this.posX + var5 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F);
    this.targetY = (this.posY + this.rand.nextFloat() * 3.0F + 1.0D);
    this.targetZ = (this.posZ - var6 * 5.0F + (this.rand.nextFloat() - 0.5F) * 2.0F);
    this.target = null;
    if (((p_70965_2_.getEntity() instanceof EntityPlayer)) || (p_70965_2_.isExplosion())) {
      func_82195_e(p_70965_2_, p_70965_3_);
    }
    return true;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (((source instanceof EntityDamageSource)) && (((EntityDamageSource)source).func_180139_w())) {
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
    this.deathTicks += 1;
    if ((this.deathTicks >= 180) && (this.deathTicks <= 200))
    {
      float var1 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float var2 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float var3 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + var1, this.posY + 2.0D + var2, this.posZ + var3, 0.0D, 0.0D, 0.0D, new int[0]);
    }
    if (!this.worldObj.isRemote)
    {
      if ((this.deathTicks > 150) && (this.deathTicks % 5 == 0) && (this.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")))
      {
        int var4 = 1000;
        while (var4 > 0)
        {
          int var5 = EntityXPOrb.getXPSplit(var4);
          var4 -= var5;
          this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
        }
      }
      if (this.deathTicks == 1) {
        this.worldObj.func_175669_a(1018, new BlockPos(this), 0);
      }
    }
    moveEntity(0.0D, 0.10000000149011612D, 0.0D);
    this.renderYawOffset = (this.rotationYaw += 20.0F);
    if ((this.deathTicks == 200) && (!this.worldObj.isRemote))
    {
      int var4 = 2000;
      while (var4 > 0)
      {
        int var5 = EntityXPOrb.getXPSplit(var4);
        var4 -= var5;
        this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY, this.posZ, var5));
      }
      func_175499_a(new BlockPos(this.posX, 64.0D, this.posZ));
      setDead();
    }
  }
  
  private void func_175499_a(BlockPos p_175499_1_)
  {
    boolean var2 = true;
    double var3 = 12.25D;
    double var5 = 6.25D;
    for (int var7 = -1; var7 <= 32; var7++) {
      for (int var8 = -4; var8 <= 4; var8++) {
        for (int var9 = -4; var9 <= 4; var9++)
        {
          double var10 = var8 * var8 + var9 * var9;
          if (var10 <= 12.25D)
          {
            BlockPos var12 = p_175499_1_.add(var8, var7, var9);
            if (var7 < 0)
            {
              if (var10 <= 6.25D) {
                this.worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
              }
            }
            else if (var7 > 0) {
              this.worldObj.setBlockState(var12, Blocks.air.getDefaultState());
            } else if (var10 > 6.25D) {
              this.worldObj.setBlockState(var12, Blocks.bedrock.getDefaultState());
            } else {
              this.worldObj.setBlockState(var12, Blocks.end_portal.getDefaultState());
            }
          }
        }
      }
    }
    this.worldObj.setBlockState(p_175499_1_, Blocks.bedrock.getDefaultState());
    this.worldObj.setBlockState(p_175499_1_.offsetUp(), Blocks.bedrock.getDefaultState());
    BlockPos var13 = p_175499_1_.offsetUp(2);
    this.worldObj.setBlockState(var13, Blocks.bedrock.getDefaultState());
    this.worldObj.setBlockState(var13.offsetWest(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.EAST));
    this.worldObj.setBlockState(var13.offsetEast(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.WEST));
    this.worldObj.setBlockState(var13.offsetNorth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.SOUTH));
    this.worldObj.setBlockState(var13.offsetSouth(), Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING_PROP, EnumFacing.NORTH));
    this.worldObj.setBlockState(p_175499_1_.offsetUp(3), Blocks.bedrock.getDefaultState());
    this.worldObj.setBlockState(p_175499_1_.offsetUp(4), Blocks.dragon_egg.getDefaultState());
  }
  
  protected void despawnEntity() {}
  
  public Entity[] getParts()
  {
    return this.dragonPartArray;
  }
  
  public boolean canBeCollidedWith()
  {
    return false;
  }
  
  public World func_82194_d()
  {
    return this.worldObj;
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
