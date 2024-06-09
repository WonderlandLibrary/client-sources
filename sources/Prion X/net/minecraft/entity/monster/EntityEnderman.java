package net.minecraft.entity.monster;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityEnderman extends EntityMob
{
  private static final UUID attackingSpeedBoostModifierUUID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
  private static final AttributeModifier attackingSpeedBoostModifier = new AttributeModifier(attackingSpeedBoostModifierUUID, "Attacking speed boost", 0.15000000596046448D, 0).setSaved(false);
  private static final Set carriableBlocks = com.google.common.collect.Sets.newIdentityHashSet();
  private boolean isAggressive;
  private static final String __OBFID = "CL_00001685";
  
  public EntityEnderman(World worldIn)
  {
    super(worldIn);
    setSize(0.6F, 2.9F);
    stepHeight = 1.0F;
    tasks.addTask(0, new net.minecraft.entity.ai.EntityAISwimming(this));
    tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, false));
    tasks.addTask(7, new EntityAIWander(this, 1.0D));
    tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    tasks.addTask(8, new net.minecraft.entity.ai.EntityAILookIdle(this));
    tasks.addTask(10, new AIPlaceBlock());
    tasks.addTask(11, new AITakeBlock());
    targetTasks.addTask(1, new net.minecraft.entity.ai.EntityAIHurtByTarget(this, false, new Class[0]));
    targetTasks.addTask(2, new AIFindPlayer());
    targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new com.google.common.base.Predicate()
    {
      private static final String __OBFID = "CL_00002223";
      
      public boolean func_179948_a(EntityEndermite p_179948_1_) {
        return p_179948_1_.isSpawnedByPlayer();
      }
      
      public boolean apply(Object p_apply_1_) {
        return func_179948_a((EntityEndermite)p_apply_1_);
      }
    }));
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
    getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
    getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
  }
  
  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, new Short((short)0));
    dataWatcher.addObject(17, new Byte((byte)0));
    dataWatcher.addObject(18, new Byte((byte)0));
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    IBlockState var2 = func_175489_ck();
    tagCompound.setShort("carried", (short)Block.getIdFromBlock(var2.getBlock()));
    tagCompound.setShort("carriedData", (short)var2.getBlock().getMetaFromState(var2));
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    IBlockState var2;
    IBlockState var2;
    if (tagCompund.hasKey("carried", 8))
    {
      var2 = Block.getBlockFromName(tagCompund.getString("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
    }
    else
    {
      var2 = Block.getBlockById(tagCompund.getShort("carried")).getStateFromMeta(tagCompund.getShort("carriedData") & 0xFFFF);
    }
    
    func_175490_a(var2);
  }
  



  private boolean shouldAttackPlayer(EntityPlayer p_70821_1_)
  {
    ItemStack var2 = inventory.armorInventory[3];
    
    if ((var2 != null) && (var2.getItem() == Item.getItemFromBlock(Blocks.pumpkin)))
    {
      return false;
    }
    

    Vec3 var3 = p_70821_1_.getLook(1.0F).normalize();
    Vec3 var4 = new Vec3(posX - posX, getEntityBoundingBoxminY + height / 2.0F - (posY + p_70821_1_.getEyeHeight()), posZ - posZ);
    double var5 = var4.lengthVector();
    var4 = var4.normalize();
    double var7 = var3.dotProduct(var4);
    return var7 > 1.0D - 0.025D / var5 ? p_70821_1_.canEntityBeSeen(this) : false;
  }
  

  public float getEyeHeight()
  {
    return 2.55F;
  }
  




  public void onLivingUpdate()
  {
    if (worldObj.isRemote)
    {
      for (int var1 = 0; var1 < 2; var1++)
      {
        worldObj.spawnParticle(EnumParticleTypes.PORTAL, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 2.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
      }
    }
    
    isJumping = false;
    super.onLivingUpdate();
  }
  
  protected void updateAITasks()
  {
    if (isWet())
    {
      attackEntityFrom(DamageSource.drown, 1.0F);
    }
    
    if ((isScreaming()) && (!isAggressive) && (rand.nextInt(100) == 0))
    {
      setScreaming(false);
    }
    
    if (worldObj.isDaytime())
    {
      float var1 = getBrightness(1.0F);
      
      if ((var1 > 0.5F) && (worldObj.isAgainstSky(new BlockPos(this))) && (rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F))
      {
        setAttackTarget(null);
        setScreaming(false);
        isAggressive = false;
        teleportRandomly();
      }
    }
    
    super.updateAITasks();
  }
  



  protected boolean teleportRandomly()
  {
    double var1 = posX + (rand.nextDouble() - 0.5D) * 64.0D;
    double var3 = posY + (rand.nextInt(64) - 32);
    double var5 = posZ + (rand.nextDouble() - 0.5D) * 64.0D;
    return teleportTo(var1, var3, var5);
  }
  



  protected boolean teleportToEntity(Entity p_70816_1_)
  {
    Vec3 var2 = new Vec3(posX - posX, getEntityBoundingBoxminY + height / 2.0F - posY + p_70816_1_.getEyeHeight(), posZ - posZ);
    var2 = var2.normalize();
    double var3 = 16.0D;
    double var5 = posX + (rand.nextDouble() - 0.5D) * 8.0D - xCoord * var3;
    double var7 = posY + (rand.nextInt(16) - 8) - yCoord * var3;
    double var9 = posZ + (rand.nextDouble() - 0.5D) * 8.0D - zCoord * var3;
    return teleportTo(var5, var7, var9);
  }
  



  protected boolean teleportTo(double p_70825_1_, double p_70825_3_, double p_70825_5_)
  {
    double var7 = posX;
    double var9 = posY;
    double var11 = posZ;
    posX = p_70825_1_;
    posY = p_70825_3_;
    posZ = p_70825_5_;
    boolean var13 = false;
    BlockPos var14 = new BlockPos(posX, posY, posZ);
    
    if (worldObj.isBlockLoaded(var14))
    {
      boolean var15 = false;
      
      while ((!var15) && (var14.getY() > 0))
      {
        BlockPos var16 = var14.offsetDown();
        Block var17 = worldObj.getBlockState(var16).getBlock();
        
        if (var17.getMaterial().blocksMovement())
        {
          var15 = true;
        }
        else
        {
          posY -= 1.0D;
          var14 = var16;
        }
      }
      
      if (var15)
      {
        super.setPositionAndUpdate(posX, posY, posZ);
        
        if ((worldObj.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) && (!worldObj.isAnyLiquid(getEntityBoundingBox())))
        {
          var13 = true;
        }
      }
    }
    
    if (!var13)
    {
      setPosition(var7, var9, var11);
      return false;
    }
    

    short var28 = 128;
    
    for (int var29 = 0; var29 < var28; var29++)
    {
      double var30 = var29 / (var28 - 1.0D);
      float var19 = (rand.nextFloat() - 0.5F) * 0.2F;
      float var20 = (rand.nextFloat() - 0.5F) * 0.2F;
      float var21 = (rand.nextFloat() - 0.5F) * 0.2F;
      double var22 = var7 + (posX - var7) * var30 + (rand.nextDouble() - 0.5D) * width * 2.0D;
      double var24 = var9 + (posY - var9) * var30 + rand.nextDouble() * height;
      double var26 = var11 + (posZ - var11) * var30 + (rand.nextDouble() - 0.5D) * width * 2.0D;
      worldObj.spawnParticle(EnumParticleTypes.PORTAL, var22, var24, var26, var19, var20, var21, new int[0]);
    }
    
    worldObj.playSoundEffect(var7, var9, var11, "mob.endermen.portal", 1.0F, 1.0F);
    playSound("mob.endermen.portal", 1.0F, 1.0F);
    return true;
  }
  




  protected String getLivingSound()
  {
    return isScreaming() ? "mob.endermen.scream" : "mob.endermen.idle";
  }
  



  protected String getHurtSound()
  {
    return "mob.endermen.hit";
  }
  



  protected String getDeathSound()
  {
    return "mob.endermen.death";
  }
  
  protected Item getDropItem()
  {
    return net.minecraft.init.Items.ender_pearl;
  }
  



  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_)
  {
    Item var3 = getDropItem();
    
    if (var3 != null)
    {
      int var4 = rand.nextInt(2 + p_70628_2_);
      
      for (int var5 = 0; var5 < var4; var5++)
      {
        dropItem(var3, 1);
      }
    }
  }
  
  public void func_175490_a(IBlockState p_175490_1_)
  {
    dataWatcher.updateObject(16, Short.valueOf((short)(Block.getStateId(p_175490_1_) & 0xFFFF)));
  }
  
  public IBlockState func_175489_ck()
  {
    return Block.getStateById(dataWatcher.getWatchableObjectShort(16) & 0xFFFF);
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    if ((source.getEntity() == null) || (!(source.getEntity() instanceof EntityEndermite)))
    {
      if (!worldObj.isRemote)
      {
        setScreaming(true);
      }
      
      if (((source instanceof EntityDamageSource)) && ((source.getEntity() instanceof EntityPlayer)))
      {
        if (((source.getEntity() instanceof EntityPlayerMP)) && (getEntitytheItemInWorldManager.isCreative()))
        {
          setScreaming(false);
        }
        else
        {
          isAggressive = true;
        }
      }
      
      if ((source instanceof EntityDamageSourceIndirect))
      {
        isAggressive = false;
        
        for (int var4 = 0; var4 < 64; var4++)
        {
          if (teleportRandomly())
          {
            return true;
          }
        }
        
        return false;
      }
    }
    
    boolean var3 = super.attackEntityFrom(source, amount);
    
    if ((source.isUnblockable()) && (rand.nextInt(10) != 0))
    {
      teleportRandomly();
    }
    
    return var3;
  }
  

  public boolean isScreaming()
  {
    return dataWatcher.getWatchableObjectByte(18) > 0;
  }
  
  public void setScreaming(boolean p_70819_1_)
  {
    dataWatcher.updateObject(18, Byte.valueOf((byte)(p_70819_1_ ? 1 : 0)));
  }
  
  static
  {
    carriableBlocks.add(Blocks.grass);
    carriableBlocks.add(Blocks.dirt);
    carriableBlocks.add(Blocks.sand);
    carriableBlocks.add(Blocks.gravel);
    carriableBlocks.add(Blocks.yellow_flower);
    carriableBlocks.add(Blocks.red_flower);
    carriableBlocks.add(Blocks.brown_mushroom);
    carriableBlocks.add(Blocks.red_mushroom);
    carriableBlocks.add(Blocks.tnt);
    carriableBlocks.add(Blocks.cactus);
    carriableBlocks.add(Blocks.clay);
    carriableBlocks.add(Blocks.pumpkin);
    carriableBlocks.add(Blocks.melon_block);
    carriableBlocks.add(Blocks.mycelium);
  }
  
  class AIFindPlayer extends EntityAINearestAttackableTarget
  {
    private EntityPlayer field_179448_g;
    private int field_179450_h;
    private int field_179451_i;
    private EntityEnderman field_179449_j = EntityEnderman.this;
    private static final String __OBFID = "CL_00002221";
    
    public AIFindPlayer()
    {
      super(EntityPlayer.class, true);
    }
    
    public boolean shouldExecute()
    {
      double var1 = getTargetDistance();
      List var3 = taskOwner.worldObj.func_175647_a(EntityPlayer.class, taskOwner.getEntityBoundingBox().expand(var1, 4.0D, var1), targetEntitySelector);
      Collections.sort(var3, theNearestAttackableTargetSorter);
      
      if (var3.isEmpty())
      {
        return false;
      }
      

      field_179448_g = ((EntityPlayer)var3.get(0));
      return true;
    }
    

    public void startExecuting()
    {
      field_179450_h = 5;
      field_179451_i = 0;
    }
    
    public void resetTask()
    {
      field_179448_g = null;
      field_179449_j.setScreaming(false);
      IAttributeInstance var1 = field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
      var1.removeModifier(EntityEnderman.attackingSpeedBoostModifier);
      super.resetTask();
    }
    
    public boolean continueExecuting()
    {
      if (field_179448_g != null)
      {
        if (!field_179449_j.shouldAttackPlayer(field_179448_g))
        {
          return false;
        }
        

        field_179449_j.isAggressive = true;
        field_179449_j.faceEntity(field_179448_g, 10.0F, 10.0F);
        return true;
      }
      


      return super.continueExecuting();
    }
    

    public void updateTask()
    {
      if (field_179448_g != null)
      {
        if (--field_179450_h <= 0)
        {
          targetEntity = field_179448_g;
          field_179448_g = null;
          super.startExecuting();
          field_179449_j.playSound("mob.endermen.stare", 1.0F, 1.0F);
          field_179449_j.setScreaming(true);
          IAttributeInstance var1 = field_179449_j.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
          var1.applyModifier(EntityEnderman.attackingSpeedBoostModifier);
        }
      }
      else
      {
        if (targetEntity != null)
        {
          if (((targetEntity instanceof EntityPlayer)) && (field_179449_j.shouldAttackPlayer((EntityPlayer)targetEntity)))
          {
            if (targetEntity.getDistanceSqToEntity(field_179449_j) < 16.0D)
            {
              field_179449_j.teleportRandomly();
            }
            
            field_179451_i = 0;
          }
          else if ((targetEntity.getDistanceSqToEntity(field_179449_j) > 256.0D) && (field_179451_i++ >= 30) && (field_179449_j.teleportToEntity(targetEntity)))
          {
            field_179451_i = 0;
          }
        }
        
        super.updateTask();
      }
    }
  }
  
  class AIPlaceBlock extends EntityAIBase
  {
    private EntityEnderman field_179475_a = EntityEnderman.this;
    private static final String __OBFID = "CL_00002222";
    
    AIPlaceBlock() {}
    
    public boolean shouldExecute() { return field_179475_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"); }
    

    public void updateTask()
    {
      Random var1 = field_179475_a.getRNG();
      World var2 = field_179475_a.worldObj;
      int var3 = MathHelper.floor_double(field_179475_a.posX - 1.0D + var1.nextDouble() * 2.0D);
      int var4 = MathHelper.floor_double(field_179475_a.posY + var1.nextDouble() * 2.0D);
      int var5 = MathHelper.floor_double(field_179475_a.posZ - 1.0D + var1.nextDouble() * 2.0D);
      BlockPos var6 = new BlockPos(var3, var4, var5);
      Block var7 = var2.getBlockState(var6).getBlock();
      Block var8 = var2.getBlockState(var6.offsetDown()).getBlock();
      
      if (func_179474_a(var2, var6, field_179475_a.func_175489_ck().getBlock(), var7, var8))
      {
        var2.setBlockState(var6, field_179475_a.func_175489_ck(), 3);
        field_179475_a.func_175490_a(Blocks.air.getDefaultState());
      }
    }
    
    private boolean func_179474_a(World worldIn, BlockPos p_179474_2_, Block p_179474_3_, Block p_179474_4_, Block p_179474_5_)
    {
      return p_179474_5_.getMaterial() == Material.air ? false : p_179474_4_.getMaterial() != Material.air ? false : !p_179474_3_.canPlaceBlockAt(worldIn, p_179474_2_) ? false : p_179474_5_.isFullCube();
    }
  }
  
  class AITakeBlock extends EntityAIBase
  {
    private EntityEnderman field_179473_a = EntityEnderman.this;
    private static final String __OBFID = "CL_00002220";
    
    AITakeBlock() {}
    
    public boolean shouldExecute() { return field_179473_a.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"); }
    

    public void updateTask()
    {
      Random var1 = field_179473_a.getRNG();
      World var2 = field_179473_a.worldObj;
      int var3 = MathHelper.floor_double(field_179473_a.posX - 2.0D + var1.nextDouble() * 4.0D);
      int var4 = MathHelper.floor_double(field_179473_a.posY + var1.nextDouble() * 3.0D);
      int var5 = MathHelper.floor_double(field_179473_a.posZ - 2.0D + var1.nextDouble() * 4.0D);
      BlockPos var6 = new BlockPos(var3, var4, var5);
      IBlockState var7 = var2.getBlockState(var6);
      Block var8 = var7.getBlock();
      
      if (EntityEnderman.carriableBlocks.contains(var8))
      {
        field_179473_a.func_175490_a(var7);
        var2.setBlockState(var6, Blocks.air.getDefaultState());
      }
    }
  }
}
