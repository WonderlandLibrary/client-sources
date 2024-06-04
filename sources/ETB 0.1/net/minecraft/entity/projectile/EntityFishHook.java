package net.minecraft.entity.projectile;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityFishHook extends Entity
{
  private static final List JUNK = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.leather_boots), 10).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.leather), 10), new WeightedRandomFishable(new ItemStack(Items.bone), 10), new WeightedRandomFishable(new ItemStack(Items.potionitem), 10), new WeightedRandomFishable(new ItemStack(Items.string), 5), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 2).setMaxDamagePercent(0.9F), new WeightedRandomFishable(new ItemStack(Items.bowl), 10), new WeightedRandomFishable(new ItemStack(Items.stick), 5), new WeightedRandomFishable(new ItemStack(Items.dye, 10, EnumDyeColor.BLACK.getDyeColorDamage()), 1), new WeightedRandomFishable(new ItemStack(Blocks.tripwire_hook), 10), new WeightedRandomFishable(new ItemStack(Items.rotten_flesh), 10) });
  private static final List VALUABLES = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Blocks.waterlily), 1), new WeightedRandomFishable(new ItemStack(Items.name_tag), 1), new WeightedRandomFishable(new ItemStack(Items.saddle), 1), new WeightedRandomFishable(new ItemStack(Items.bow), 1).setMaxDamagePercent(0.25F).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.fishing_rod), 1).setMaxDamagePercent(0.25F).setEnchantable(), new WeightedRandomFishable(new ItemStack(Items.book), 1).setEnchantable() });
  private static final List FISH = Arrays.asList(new WeightedRandomFishable[] { new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.COD.getItemDamage()), 60), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.SALMON.getItemDamage()), 25), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.CLOWNFISH.getItemDamage()), 2), new WeightedRandomFishable(new ItemStack(Items.fish, 1, ItemFishFood.FishType.PUFFERFISH.getItemDamage()), 13) });
  private int xTile;
  private int yTile;
  private int zTile;
  private Block inTile;
  private boolean inGround;
  public int shake;
  public EntityPlayer angler;
  private int ticksInGround;
  private int ticksInAir;
  private int ticksCatchable;
  private int ticksCaughtDelay;
  private int ticksCatchableDelay;
  private float fishApproachAngle;
  public Entity caughtEntity;
  private int fishPosRotationIncrements;
  private double fishX;
  private double fishY;
  private double fishZ;
  private double fishYaw;
  private double fishPitch;
  private double clientMotionX;
  private double clientMotionY;
  private double clientMotionZ;
  private static final String __OBFID = "CL_00001663";
  
  public static List func_174855_j()
  {
    return FISH;
  }
  
  public EntityFishHook(World worldIn)
  {
    super(worldIn);
    xTile = -1;
    yTile = -1;
    zTile = -1;
    setSize(0.25F, 0.25F);
    ignoreFrustumCheck = true;
  }
  
  public EntityFishHook(World worldIn, double p_i1765_2_, double p_i1765_4_, double p_i1765_6_, EntityPlayer p_i1765_8_)
  {
    this(worldIn);
    setPosition(p_i1765_2_, p_i1765_4_, p_i1765_6_);
    ignoreFrustumCheck = true;
    angler = p_i1765_8_;
    fishEntity = this;
  }
  
  public EntityFishHook(World worldIn, EntityPlayer p_i1766_2_)
  {
    super(worldIn);
    xTile = -1;
    yTile = -1;
    zTile = -1;
    ignoreFrustumCheck = true;
    angler = p_i1766_2_;
    angler.fishEntity = this;
    setSize(0.25F, 0.25F);
    setLocationAndAngles(posX, posY + p_i1766_2_.getEyeHeight(), posZ, rotationYaw, rotationPitch);
    posX -= MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    posY -= 0.10000000149011612D;
    posZ -= MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * 0.16F;
    setPosition(posX, posY, posZ);
    float var3 = 0.4F;
    motionX = (-MathHelper.sin(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * var3);
    motionZ = (MathHelper.cos(rotationYaw / 180.0F * 3.1415927F) * MathHelper.cos(rotationPitch / 180.0F * 3.1415927F) * var3);
    motionY = (-MathHelper.sin(rotationPitch / 180.0F * 3.1415927F) * var3);
    handleHookCasting(motionX, motionY, motionZ, 1.5F, 1.0F);
  }
  


  protected void entityInit() {}
  


  public boolean isInRangeToRenderDist(double distance)
  {
    double var3 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
    var3 *= 64.0D;
    return distance < var3 * var3;
  }
  
  public void handleHookCasting(double p_146035_1_, double p_146035_3_, double p_146035_5_, float p_146035_7_, float p_146035_8_)
  {
    float var9 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_3_ * p_146035_3_ + p_146035_5_ * p_146035_5_);
    p_146035_1_ /= var9;
    p_146035_3_ /= var9;
    p_146035_5_ /= var9;
    p_146035_1_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
    p_146035_3_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
    p_146035_5_ += rand.nextGaussian() * 0.007499999832361937D * p_146035_8_;
    p_146035_1_ *= p_146035_7_;
    p_146035_3_ *= p_146035_7_;
    p_146035_5_ *= p_146035_7_;
    motionX = p_146035_1_;
    motionY = p_146035_3_;
    motionZ = p_146035_5_;
    float var10 = MathHelper.sqrt_double(p_146035_1_ * p_146035_1_ + p_146035_5_ * p_146035_5_);
    prevRotationYaw = (this.rotationYaw = (float)(Math.atan2(p_146035_1_, p_146035_5_) * 180.0D / 3.141592653589793D));
    prevRotationPitch = (this.rotationPitch = (float)(Math.atan2(p_146035_3_, var10) * 180.0D / 3.141592653589793D));
    ticksInGround = 0;
  }
  
  public void func_180426_a(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_, int p_180426_9_, boolean p_180426_10_)
  {
    fishX = p_180426_1_;
    fishY = p_180426_3_;
    fishZ = p_180426_5_;
    fishYaw = p_180426_7_;
    fishPitch = p_180426_8_;
    fishPosRotationIncrements = p_180426_9_;
    motionX = clientMotionX;
    motionY = clientMotionY;
    motionZ = clientMotionZ;
  }
  



  public void setVelocity(double x, double y, double z)
  {
    clientMotionX = (this.motionX = x);
    clientMotionY = (this.motionY = y);
    clientMotionZ = (this.motionZ = z);
  }
  



  public void onUpdate()
  {
    super.onUpdate();
    
    if (fishPosRotationIncrements > 0)
    {
      double var28 = posX + (fishX - posX) / fishPosRotationIncrements;
      double var29 = posY + (fishY - posY) / fishPosRotationIncrements;
      double var30 = posZ + (fishZ - posZ) / fishPosRotationIncrements;
      double var7 = MathHelper.wrapAngleTo180_double(fishYaw - rotationYaw);
      rotationYaw = ((float)(rotationYaw + var7 / fishPosRotationIncrements));
      rotationPitch = ((float)(rotationPitch + (fishPitch - rotationPitch) / fishPosRotationIncrements));
      fishPosRotationIncrements -= 1;
      setPosition(var28, var29, var30);
      setRotation(rotationYaw, rotationPitch);
    }
    else
    {
      if (!worldObj.isRemote)
      {
        ItemStack var1 = angler.getCurrentEquippedItem();
        
        if ((angler.isDead) || (!angler.isEntityAlive()) || (var1 == null) || (var1.getItem() != Items.fishing_rod) || (getDistanceSqToEntity(angler) > 1024.0D))
        {
          setDead();
          angler.fishEntity = null;
          return;
        }
        
        if (caughtEntity != null)
        {
          if (!caughtEntity.isDead)
          {
            posX = caughtEntity.posX;
            double var10002 = caughtEntity.height;
            posY = (caughtEntity.getEntityBoundingBox().minY + var10002 * 0.8D);
            posZ = caughtEntity.posZ;
            return;
          }
          
          caughtEntity = null;
        }
      }
      
      if (shake > 0)
      {
        shake -= 1;
      }
      
      if (inGround)
      {
        if (worldObj.getBlockState(new BlockPos(xTile, yTile, zTile)).getBlock() == inTile)
        {
          ticksInGround += 1;
          
          if (ticksInGround == 1200)
          {
            setDead();
          }
          
          return;
        }
        
        inGround = false;
        motionX *= rand.nextFloat() * 0.2F;
        motionY *= rand.nextFloat() * 0.2F;
        motionZ *= rand.nextFloat() * 0.2F;
        ticksInGround = 0;
        ticksInAir = 0;
      }
      else
      {
        ticksInAir += 1;
      }
      
      Vec3 var27 = new Vec3(posX, posY, posZ);
      Vec3 var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
      MovingObjectPosition var3 = worldObj.rayTraceBlocks(var27, var2);
      var27 = new Vec3(posX, posY, posZ);
      var2 = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
      
      if (var3 != null)
      {
        var2 = new Vec3(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
      }
      
      Entity var4 = null;
      List var5 = worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D));
      double var6 = 0.0D;
      

      for (int var8 = 0; var8 < var5.size(); var8++)
      {
        Entity var9 = (Entity)var5.get(var8);
        
        if ((var9.canBeCollidedWith()) && ((var9 != angler) || (ticksInAir >= 5)))
        {
          float var10 = 0.3F;
          AxisAlignedBB var11 = var9.getEntityBoundingBox().expand(var10, var10, var10);
          MovingObjectPosition var12 = var11.calculateIntercept(var27, var2);
          
          if (var12 != null)
          {
            double var13 = var27.distanceTo(hitVec);
            
            if ((var13 < var6) || (var6 == 0.0D))
            {
              var4 = var9;
              var6 = var13;
            }
          }
        }
      }
      
      if (var4 != null)
      {
        var3 = new MovingObjectPosition(var4);
      }
      
      if (var3 != null)
      {
        if (entityHit != null)
        {
          if (entityHit.attackEntityFrom(net.minecraft.util.DamageSource.causeThrownDamage(this, angler), 0.0F))
          {
            caughtEntity = entityHit;
          }
          
        }
        else {
          inGround = true;
        }
      }
      
      if (!inGround)
      {
        moveEntity(motionX, motionY, motionZ);
        float var31 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        rotationYaw = ((float)(Math.atan2(motionX, motionZ) * 180.0D / 3.141592653589793D));
        
        for (rotationPitch = ((float)(Math.atan2(motionY, var31) * 180.0D / 3.141592653589793D)); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F) {}
        



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
        float var32 = 0.92F;
        
        if ((onGround) || (isCollidedHorizontally))
        {
          var32 = 0.5F;
        }
        
        byte var33 = 5;
        double var34 = 0.0D;
        

        for (int var35 = 0; var35 < var33; var35++)
        {
          AxisAlignedBB var14 = getEntityBoundingBox();
          double var15 = maxY - minY;
          double var17 = minY + var15 * var35 / var33;
          double var19 = minY + var15 * (var35 + 1) / var33;
          AxisAlignedBB var21 = new AxisAlignedBB(minX, var17, minZ, maxX, var19, maxZ);
          
          if (worldObj.isAABBInMaterial(var21, net.minecraft.block.material.Material.water))
          {
            var34 += 1.0D / var33;
          }
        }
        
        if ((!worldObj.isRemote) && (var34 > 0.0D))
        {
          WorldServer var36 = (WorldServer)worldObj;
          int var37 = 1;
          BlockPos var38 = new BlockPos(this).offsetUp();
          
          if ((rand.nextFloat() < 0.25F) && (worldObj.func_175727_C(var38)))
          {
            var37 = 2;
          }
          
          if ((rand.nextFloat() < 0.5F) && (!worldObj.isAgainstSky(var38)))
          {
            var37--;
          }
          
          if (ticksCatchable > 0)
          {
            ticksCatchable -= 1;
            
            if (ticksCatchable <= 0)
            {
              ticksCaughtDelay = 0;
              ticksCatchableDelay = 0;



            }
            




          }
          else if (ticksCatchableDelay > 0)
          {
            ticksCatchableDelay -= var37;
            
            if (ticksCatchableDelay <= 0)
            {
              motionY -= 0.20000000298023224D;
              playSound("random.splash", 0.25F, 1.0F + (rand.nextFloat() - rand.nextFloat()) * 0.4F);
              float var16 = MathHelper.floor_double(getEntityBoundingBoxminY);
              var36.func_175739_a(EnumParticleTypes.WATER_BUBBLE, posX, var16 + 1.0F, posZ, (int)(1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D, new int[0]);
              var36.func_175739_a(EnumParticleTypes.WATER_WAKE, posX, var16 + 1.0F, posZ, (int)(1.0F + width * 20.0F), width, 0.0D, width, 0.20000000298023224D, new int[0]);
              ticksCatchable = MathHelper.getRandomIntegerInRange(rand, 10, 30);
            }
            else
            {
              fishApproachAngle = ((float)(fishApproachAngle + rand.nextGaussian() * 4.0D));
              float var16 = fishApproachAngle * 0.017453292F;
              float var39 = MathHelper.sin(var16);
              float var18 = MathHelper.cos(var16);
              double var19 = posX + var39 * ticksCatchableDelay * 0.1F;
              double var40 = MathHelper.floor_double(getEntityBoundingBoxminY) + 1.0F;
              double var23 = posZ + var18 * ticksCatchableDelay * 0.1F;
              
              if (rand.nextFloat() < 0.15F)
              {
                var36.func_175739_a(EnumParticleTypes.WATER_BUBBLE, var19, var40 - 0.10000000149011612D, var23, 1, var39, 0.1D, var18, 0.0D, new int[0]);
              }
              
              float var25 = var39 * 0.04F;
              float var26 = var18 * 0.04F;
              var36.func_175739_a(EnumParticleTypes.WATER_WAKE, var19, var40, var23, 0, var26, 0.01D, -var25, 1.0D, new int[0]);
              var36.func_175739_a(EnumParticleTypes.WATER_WAKE, var19, var40, var23, 0, -var26, 0.01D, var25, 1.0D, new int[0]);
            }
          }
          else if (ticksCaughtDelay > 0)
          {
            ticksCaughtDelay -= var37;
            float var16 = 0.15F;
            
            if (ticksCaughtDelay < 20)
            {
              var16 = (float)(var16 + (20 - ticksCaughtDelay) * 0.05D);
            }
            else if (ticksCaughtDelay < 40)
            {
              var16 = (float)(var16 + (40 - ticksCaughtDelay) * 0.02D);
            }
            else if (ticksCaughtDelay < 60)
            {
              var16 = (float)(var16 + (60 - ticksCaughtDelay) * 0.01D);
            }
            
            if (rand.nextFloat() < var16)
            {
              float var39 = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F) * 0.017453292F;
              float var18 = MathHelper.randomFloatClamp(rand, 25.0F, 60.0F);
              double var19 = posX + MathHelper.sin(var39) * var18 * 0.1F;
              double var40 = MathHelper.floor_double(getEntityBoundingBoxminY) + 1.0F;
              double var23 = posZ + MathHelper.cos(var39) * var18 * 0.1F;
              var36.func_175739_a(EnumParticleTypes.WATER_SPLASH, var19, var40, var23, 2 + rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
            }
            
            if (ticksCaughtDelay <= 0)
            {
              fishApproachAngle = MathHelper.randomFloatClamp(rand, 0.0F, 360.0F);
              ticksCatchableDelay = MathHelper.getRandomIntegerInRange(rand, 20, 80);
            }
          }
          else
          {
            ticksCaughtDelay = MathHelper.getRandomIntegerInRange(rand, 100, 900);
            ticksCaughtDelay -= EnchantmentHelper.func_151387_h(angler) * 20 * 5;
          }
          

          if (ticksCatchable > 0)
          {
            motionY -= rand.nextFloat() * rand.nextFloat() * rand.nextFloat() * 0.2D;
          }
        }
        
        double var13 = var34 * 2.0D - 1.0D;
        motionY += 0.03999999910593033D * var13;
        
        if (var34 > 0.0D)
        {
          var32 = (float)(var32 * 0.9D);
          motionY *= 0.8D;
        }
        
        motionX *= var32;
        motionY *= var32;
        motionZ *= var32;
        setPosition(posX, posY, posZ);
      }
    }
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    tagCompound.setShort("xTile", (short)xTile);
    tagCompound.setShort("yTile", (short)yTile);
    tagCompound.setShort("zTile", (short)zTile);
    ResourceLocation var2 = (ResourceLocation)Block.blockRegistry.getNameForObject(inTile);
    tagCompound.setString("inTile", var2 == null ? "" : var2.toString());
    tagCompound.setByte("shake", (byte)shake);
    tagCompound.setByte("inGround", (byte)(inGround ? 1 : 0));
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    xTile = tagCompund.getShort("xTile");
    yTile = tagCompund.getShort("yTile");
    zTile = tagCompund.getShort("zTile");
    
    if (tagCompund.hasKey("inTile", 8))
    {
      inTile = Block.getBlockFromName(tagCompund.getString("inTile"));
    }
    else
    {
      inTile = Block.getBlockById(tagCompund.getByte("inTile") & 0xFF);
    }
    
    shake = (tagCompund.getByte("shake") & 0xFF);
    inGround = (tagCompund.getByte("inGround") == 1);
  }
  
  public int handleHookRetraction()
  {
    if (worldObj.isRemote)
    {
      return 0;
    }
    

    byte var1 = 0;
    
    if (caughtEntity != null)
    {
      double var2 = angler.posX - posX;
      double var4 = angler.posY - posY;
      double var6 = angler.posZ - posZ;
      double var8 = MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
      double var10 = 0.1D;
      caughtEntity.motionX += var2 * var10;
      caughtEntity.motionY += var4 * var10 + MathHelper.sqrt_double(var8) * 0.08D;
      caughtEntity.motionZ += var6 * var10;
      var1 = 3;
    }
    else if (ticksCatchable > 0)
    {
      EntityItem var13 = new EntityItem(worldObj, posX, posY, posZ, func_146033_f());
      double var3 = angler.posX - posX;
      double var5 = angler.posY - posY;
      double var7 = angler.posZ - posZ;
      double var9 = MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
      double var11 = 0.1D;
      motionX = (var3 * var11);
      motionY = (var5 * var11 + MathHelper.sqrt_double(var9) * 0.08D);
      motionZ = (var7 * var11);
      worldObj.spawnEntityInWorld(var13);
      angler.worldObj.spawnEntityInWorld(new net.minecraft.entity.item.EntityXPOrb(angler.worldObj, angler.posX, angler.posY + 0.5D, angler.posZ + 0.5D, rand.nextInt(6) + 1));
      var1 = 1;
    }
    
    if (inGround)
    {
      var1 = 2;
    }
    
    setDead();
    angler.fishEntity = null;
    return var1;
  }
  

  private ItemStack func_146033_f()
  {
    float var1 = worldObj.rand.nextFloat();
    int var2 = EnchantmentHelper.func_151386_g(angler);
    int var3 = EnchantmentHelper.func_151387_h(angler);
    float var4 = 0.1F - var2 * 0.025F - var3 * 0.01F;
    float var5 = 0.05F + var2 * 0.01F - var3 * 0.01F;
    var4 = MathHelper.clamp_float(var4, 0.0F, 1.0F);
    var5 = MathHelper.clamp_float(var5, 0.0F, 1.0F);
    
    if (var1 < var4)
    {
      angler.triggerAchievement(StatList.junkFishedStat);
      return ((WeightedRandomFishable)WeightedRandom.getRandomItem(rand, JUNK)).getItemStack(rand);
    }
    

    var1 -= var4;
    
    if (var1 < var5)
    {
      angler.triggerAchievement(StatList.treasureFishedStat);
      return ((WeightedRandomFishable)WeightedRandom.getRandomItem(rand, VALUABLES)).getItemStack(rand);
    }
    

    float var10000 = var1 - var5;
    angler.triggerAchievement(StatList.fishCaughtStat);
    return ((WeightedRandomFishable)WeightedRandom.getRandomItem(rand, FISH)).getItemStack(rand);
  }
  





  public void setDead()
  {
    super.setDead();
    
    if (angler != null)
    {
      angler.fishEntity = null;
    }
  }
}
