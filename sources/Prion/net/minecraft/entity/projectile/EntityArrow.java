package net.minecraft.entity.projectile;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityArrow extends Entity implements net.minecraft.entity.IProjectile
{
  private int field_145791_d = -1;
  private int field_145792_e = -1;
  private int field_145789_f = -1;
  
  private Block field_145790_g;
  
  private int inData;
  
  private boolean inGround;
  
  public int canBePickedUp;
  
  public int arrowShake;
  
  public Entity shootingEntity;
  private int ticksInGround;
  private int ticksInAir;
  private double damage = 2.0D;
  
  private int knockbackStrength;
  
  private static final String __OBFID = "CL_00001715";
  
  public EntityArrow(World worldIn)
  {
    super(worldIn);
    renderDistanceWeight = 10.0D;
    setSize(0.5F, 0.5F);
  }
  
  public EntityArrow(World worldIn, double p_i1754_2_, double p_i1754_4_, double p_i1754_6_)
  {
    super(worldIn);
    renderDistanceWeight = 10.0D;
    setSize(0.5F, 0.5F);
    setPosition(p_i1754_2_, p_i1754_4_, p_i1754_6_);
  }
  
  public EntityArrow(World worldIn, EntityLivingBase p_i1755_2_, EntityLivingBase p_i1755_3_, float p_i1755_4_, float p_i1755_5_)
  {
    super(worldIn);
    renderDistanceWeight = 10.0D;
    shootingEntity = p_i1755_2_;
    
    if ((p_i1755_2_ instanceof EntityPlayer))
    {
      canBePickedUp = 1;
    }
    
    posY = (posY + p_i1755_2_.getEyeHeight() - 0.10000000149011612D);
    double var6 = posX - posX;
    double var8 = getEntityBoundingBoxminY + height / 3.0F - posY;
    double var10 = posZ - posZ;
    double var12 = MathHelper.sqrt_double(var6 * var6 + var10 * var10);
    
    if (var12 >= 1.0E-7D)
    {
      float var14 = (float)(Math.atan2(var10, var6) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)-(Math.atan2(var8, var12) * 180.0D / 3.141592653589793D);
      double var16 = var6 / var12;
      double var18 = var10 / var12;
      setLocationAndAngles(posX + var16, posY, posZ + var18, var14, var15);
      float var20 = (float)(var12 * 0.20000000298023224D);
      setThrowableHeading(var6, var8 + var20, var10, p_i1755_4_, p_i1755_5_);
    }
  }
  
  public EntityArrow(World worldIn, EntityLivingBase p_i1756_2_, float p_i1756_3_)
  {
    super(worldIn);
    renderDistanceWeight = 10.0D;
    shootingEntity = p_i1756_2_;
    
    if ((p_i1756_2_ instanceof EntityPlayer))
    {
      canBePickedUp = 1;
    }
    
    setSize(0.5F, 0.5F);
    setLocationAndAngles(posX, posY + p_i1756_2_.getEyeHeight(), posZ, rotationYaw, rotationPitch);
    posX -= MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    posY -= 0.10000000149011612D;
    posZ -= MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(posX, posY, posZ);
    motionX = (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
    motionZ = (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F));
    motionY = (-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F));
    setThrowableHeading(motionX, motionY, motionZ, p_i1756_3_ * 1.5F, 1.0F);
  }
  
  protected void entityInit()
  {
    dataWatcher.addObject(16, Byte.valueOf((byte)0));
  }
  



  public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_)
  {
    float var9 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
    p_70186_1_ /= var9;
    p_70186_3_ /= var9;
    p_70186_5_ /= var9;
    p_70186_1_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
    p_70186_3_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
    p_70186_5_ += rand.nextGaussian() * (rand.nextBoolean() ? -1 : 1) * 0.007499999832361937D * p_70186_8_;
    p_70186_1_ *= p_70186_7_;
    p_70186_3_ *= p_70186_7_;
    p_70186_5_ *= p_70186_7_;
    motionX = p_70186_1_;
    motionY = p_70186_3_;
    motionZ = p_70186_5_;
    float var10 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
    prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / 3.141592653589793D));
    prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(p_70186_3_, var10) * 180.0D / 3.141592653589793D));
    ticksInGround = 0;
  }
  
  public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
  {
    setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
    setRotation(p_180426_7_, p_180426_8_);
  }
  



  public void setVelocity(double x, double y, double z)
  {
    motionX = x;
    motionY = y;
    motionZ = z;
    
    if ((prevRotationPitch == 0.0F) && (prevRotationYaw == 0.0F))
    {
      float var7 = MathHelper.sqrt_double(x * x + z * z);
      prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(x, z) * 180.0D / 3.141592653589793D));
      prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(y, var7) * 180.0D / 3.141592653589793D));
      prevRotationPitch = rotationPitch;
      prevRotationYaw = rotationYaw;
      setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
      ticksInGround = 0;
    }
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if ((prevRotationPitch == 0.0F) && (prevRotationYaw == 0.0F))
    {
      float var1 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
      prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D));
      prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(motionY, var1) * 180.0D / 3.141592653589793D));
    }
    
    BlockPos var18 = new BlockPos(field_145791_d, field_145792_e, field_145789_f);
    IBlockState var2 = worldObj.getBlockState(var18);
    Block var3 = var2.getBlock();
    
    if (var3.getMaterial() != Material.air)
    {
      var3.setBlockBoundsBasedOnState(worldObj, var18);
      AxisAlignedBB var4 = var3.getCollisionBoundingBox(worldObj, var18, var2);
      
      if ((var4 != null) && (var4.isVecInside(new Vec3(posX, posY, posZ))))
      {
        inGround = true;
      }
    }
    
    if (arrowShake > 0)
    {
      arrowShake -= 1;
    }
    
    if (inGround)
    {
      int var20 = var3.getMetaFromState(var2);
      
      if ((var3 == field_145790_g) && (var20 == inData))
      {
        ticksInGround += 1;
        
        if (ticksInGround >= 1200)
        {
          setDead();
        }
      }
      else
      {
        inGround = false;
        motionX *= rand.nextFloat() * 0.2F;
        motionY *= rand.nextFloat() * 0.2F;
        motionZ *= rand.nextFloat() * 0.2F;
        ticksInGround = 0;
        ticksInAir = 0;
      }
    }
    else
    {
      ticksInAir += 1;
      Vec3 var19 = new Vec3(posX, posY, posZ);
      Vec3 var5 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
      MovingObjectPosition var6 = worldObj.rayTraceBlocks(var19, var5, false, true, false);
      var19 = new Vec3(posX, posY, posZ);
      var5 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
      
      if (var6 != null)
      {
        var5 = new Vec3(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
      }
      
      Entity var7 = null;
      List var8 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
      double var9 = 0.0D;
      


      for (int var11 = 0; var11 < var8.size(); var11++)
      {
        Entity var12 = (Entity)var8.get(var11);
        
        if ((var12.canBeCollidedWith()) && ((var12 != shootingEntity) || (ticksInAir >= 5)))
        {
          float var13 = 0.3F;
          AxisAlignedBB var14 = var12.getEntityBoundingBox().expand(var13, var13, var13);
          MovingObjectPosition var15 = var14.calculateIntercept(var19, var5);
          
          if (var15 != null)
          {
            double var16 = var19.distanceTo(hitVec);
            
            if ((var16 < var9) || (var9 == 0.0D))
            {
              var7 = var12;
              var9 = var16;
            }
          }
        }
      }
      
      if (var7 != null)
      {
        var6 = new MovingObjectPosition(var7);
      }
      
      if ((var6 != null) && (entityHit != null) && ((entityHit instanceof EntityPlayer)))
      {
        EntityPlayer var21 = (EntityPlayer)entityHit;
        
        if ((capabilities.disableDamage) || (((shootingEntity instanceof EntityPlayer)) && (!((EntityPlayer)shootingEntity).canAttackPlayer(var21))))
        {
          var6 = null;
        }
      }
      




      if (var6 != null)
      {
        if (entityHit != null)
        {
          float var22 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
          int var24 = MathHelper.ceiling_double_int(var22 * damage);
          
          if (getIsCritical())
          {
            var24 += rand.nextInt(var24 / 2 + 2);
          }
          
          DamageSource var26;
          DamageSource var26;
          if (shootingEntity == null)
          {
            var26 = DamageSource.causeArrowDamage(this, this);
          }
          else
          {
            var26 = DamageSource.causeArrowDamage(this, shootingEntity);
          }
          
          if ((isBurning()) && (!(entityHit instanceof EntityEnderman)))
          {
            entityHit.setFire(5);
          }
          
          if (entityHit.attackEntityFrom(var26, var24))
          {
            if ((entityHit instanceof EntityLivingBase))
            {
              EntityLivingBase var27 = (EntityLivingBase)entityHit;
              
              if (!worldObj.isRemote)
              {
                var27.setArrowCountInEntity(var27.getArrowCountInEntity() + 1);
              }
              
              if (knockbackStrength > 0)
              {
                float var29 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
                
                if (var29 > 0.0F)
                {
                  entityHit.addVelocity(motionX * knockbackStrength * 0.6000000238418579D / var29, 0.1D, motionZ * knockbackStrength * 0.6000000238418579D / var29);
                }
              }
              
              if ((shootingEntity instanceof EntityLivingBase))
              {
                EnchantmentHelper.func_151384_a(var27, shootingEntity);
                EnchantmentHelper.func_151385_b((EntityLivingBase)shootingEntity, var27);
              }
              
              if ((shootingEntity != null) && (entityHit != shootingEntity) && ((entityHit instanceof EntityPlayer)) && ((shootingEntity instanceof EntityPlayerMP)))
              {
                shootingEntity).playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S2BPacketChangeGameState(6, 0.0F));
              }
            }
            
            playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
            
            if (!(entityHit instanceof EntityEnderman))
            {
              setDead();
            }
          }
          else
          {
            motionX *= -0.10000000149011612D;
            motionY *= -0.10000000149011612D;
            motionZ *= -0.10000000149011612D;
            rotationYaw += 180.0F;
            prevRotationYaw += 180.0F;
            ticksInAir = 0;
          }
        }
        else
        {
          BlockPos var23 = var6.func_178782_a();
          field_145791_d = var23.getX();
          field_145792_e = var23.getY();
          field_145789_f = var23.getZ();
          var2 = worldObj.getBlockState(var23);
          field_145790_g = var2.getBlock();
          inData = field_145790_g.getMetaFromState(var2);
          motionX = ((float)(hitVec.xCoord - posX));
          motionY = ((float)(hitVec.yCoord - posY));
          motionZ = ((float)(hitVec.zCoord - posZ));
          float var25 = MathHelper.sqrt_double(motionX * motionX + motionY * motionY + motionZ * motionZ);
          posX -= motionX / var25 * 0.05000000074505806D;
          posY -= motionY / var25 * 0.05000000074505806D;
          posZ -= motionZ / var25 * 0.05000000074505806D;
          playSound("random.bowhit", 1.0F, 1.2F / (rand.nextFloat() * 0.2F + 0.9F));
          inGround = true;
          arrowShake = 7;
          setIsCritical(false);
          
          if (field_145790_g.getMaterial() != Material.air)
          {
            field_145790_g.onEntityCollidedWithBlock(worldObj, var23, var2, this);
          }
        }
      }
      
      if (getIsCritical())
      {
        for (var11 = 0; var11 < 4; var11++)
        {
          worldObj.spawnParticle(EnumParticleTypes.CRIT, posX + motionX * var11 / 4.0D, posY + motionY * var11 / 4.0D, posZ + motionZ * var11 / 4.0D, -motionX, -motionY + 0.2D, -motionZ, new int[0]);
        }
      }
      
      posX += motionX;
      posY += motionY;
      posZ += motionZ;
      float var22 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
      rotationYaw = ((float)(Math.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D));
      
      for (rotationPitch = ((float)(Math.atan2(motionY, var22) * 180.0D / 3.141592653589793D)); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}
      



      while (rotationPitch - prevRotationPitch >= 180.0F)
      {
        prevRotationPitch += 360.0F;
      }
      
      while (rotationYaw - prevRotationYaw < -180.0F)
      {
        prevRotationYaw -= 360.0F;
      }
      
      while (rotationYaw - prevRotationYaw >= 180.0F)
      {
        prevRotationYaw += 360.0F;
      }
      
      rotationPitch = (prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F);
      rotationYaw = (prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F);
      float var25 = 0.99F;
      float var13 = 0.05F;
      
      if (isInWater())
      {
        for (int var28 = 0; var28 < 4; var28++)
        {
          float var29 = 0.25F;
          worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * var29, posY - motionY * var29, posZ - motionZ * var29, motionX, motionY, motionZ, new int[0]);
        }
        
        var25 = 0.6F;
      }
      
      if (isWet())
      {
        extinguish();
      }
      
      motionX *= var25;
      motionY *= var25;
      motionZ *= var25;
      motionY -= var13;
      setPosition(posX, posY, posZ);
      doBlockCollisions();
    }
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("xTile", (short)field_145791_d);
    tagCompound.setShort("yTile", (short)field_145792_e);
    tagCompound.setShort("zTile", (short)field_145789_f);
    tagCompound.setShort("life", (short)ticksInGround);
    ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(field_145790_g);
    tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
    tagCompound.setByte("inData", (byte)inData);
    tagCompound.setByte("shake", (byte)arrowShake);
    tagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
    tagCompound.setByte("pickup", (byte)canBePickedUp);
    tagCompound.setDouble("damage", damage);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    field_145791_d = tagCompund.getShort("xTile");
    field_145792_e = tagCompund.getShort("yTile");
    field_145789_f = tagCompund.getShort("zTile");
    ticksInGround = tagCompund.getShort("life");
    
    if (tagCompund.hasKey("inTile", 8))
    {
      field_145790_g = Block.getBlockFromName(tagCompund.getString("inTile"));
    }
    else
    {
      field_145790_g = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
    }
    
    inData = (tagCompund.getByte("inData") & 0xFF);
    arrowShake = (tagCompund.getByte("shake") & 0xFF);
    inGround = (tagCompund.getByte("inGround") == 1);
    
    if (tagCompund.hasKey("damage", 99))
    {
      damage = tagCompund.getDouble("damage");
    }
    
    if (tagCompund.hasKey("pickup", 99))
    {
      canBePickedUp = tagCompund.getByte("pickup");
    }
    else if (tagCompund.hasKey("player", 99))
    {
      canBePickedUp = (tagCompund.getBoolean("player") ? 1 : 0);
    }
  }
  



  public void onCollideWithPlayer(EntityPlayer entityIn)
  {
    if ((!worldObj.isRemote) && (inGround) && (arrowShake <= 0))
    {
      boolean var2 = (canBePickedUp == 1) || ((canBePickedUp == 2) && (capabilities.isCreativeMode));
      
      if ((canBePickedUp == 1) && (!inventory.addItemStackToInventory(new net.minecraft.item.ItemStack(net.minecraft.init.Items.arrow, 1))))
      {
        var2 = false;
      }
      
      if (var2)
      {
        playSound("random.pop", 0.2F, ((rand.nextFloat() - rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        entityIn.onItemPickup(this, 1);
        setDead();
      }
    }
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  
  public void setDamage(double p_70239_1_)
  {
    damage = p_70239_1_;
  }
  
  public double getDamage()
  {
    return damage;
  }
  



  public void setKnockbackStrength(int p_70240_1_)
  {
    knockbackStrength = p_70240_1_;
  }
  



  public boolean canAttackWithItem()
  {
    return false;
  }
  



  public void setIsCritical(boolean p_70243_1_)
  {
    byte var2 = dataWatcher.getWatchableObjectByte(16);
    
    if (p_70243_1_)
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 | 0x1)));
    }
    else
    {
      dataWatcher.updateObject(16, Byte.valueOf((byte)(var2 & 0xFFFFFFFE)));
    }
  }
  



  public boolean getIsCritical()
  {
    byte var1 = dataWatcher.getWatchableObjectByte(16);
    return (var1 & 0x1) != 0;
  }
}
