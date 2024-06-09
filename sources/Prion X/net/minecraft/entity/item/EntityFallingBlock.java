package net.minecraft.entity.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class EntityFallingBlock extends Entity
{
  private IBlockState field_175132_d;
  public int fallTime;
  public boolean shouldDropItem = true;
  private boolean field_145808_f;
  private boolean hurtEntities;
  private int fallHurtMax = 40;
  private float fallHurtAmount = 2.0F;
  public NBTTagCompound tileEntityData;
  private static final String __OBFID = "CL_00001668";
  
  public EntityFallingBlock(World worldIn)
  {
    super(worldIn);
  }
  
  public EntityFallingBlock(World worldIn, double p_i45848_2_, double p_i45848_4_, double p_i45848_6_, IBlockState p_i45848_8_)
  {
    super(worldIn);
    field_175132_d = p_i45848_8_;
    preventEntitySpawning = true;
    setSize(0.98F, 0.98F);
    setPosition(p_i45848_2_, p_i45848_4_, p_i45848_6_);
    motionX = 0.0D;
    motionY = 0.0D;
    motionZ = 0.0D;
    prevPosX = p_i45848_2_;
    prevPosY = p_i45848_4_;
    prevPosZ = p_i45848_6_;
  }
  




  protected boolean canTriggerWalking()
  {
    return false;
  }
  


  protected void entityInit() {}
  

  public boolean canBeCollidedWith()
  {
    return !isDead;
  }
  



  public void onUpdate()
  {
    Block var1 = field_175132_d.getBlock();
    
    if (var1.getMaterial() == Material.air)
    {
      setDead();
    }
    else
    {
      prevPosX = posX;
      prevPosY = posY;
      prevPosZ = posZ;
      

      if (fallTime++ == 0)
      {
        BlockPos var2 = new BlockPos(this);
        
        if (worldObj.getBlockState(var2).getBlock() == var1)
        {
          worldObj.setBlockToAir(var2);
        }
        else if (!worldObj.isRemote)
        {
          setDead();
          return;
        }
      }
      
      motionY -= 0.03999999910593033D;
      moveEntity(motionX, motionY, motionZ);
      motionX *= 0.9800000190734863D;
      motionY *= 0.9800000190734863D;
      motionZ *= 0.9800000190734863D;
      
      if (!worldObj.isRemote)
      {
        BlockPos var2 = new BlockPos(this);
        
        if (onGround)
        {
          motionX *= 0.699999988079071D;
          motionZ *= 0.699999988079071D;
          motionY *= -0.5D;
          
          if (worldObj.getBlockState(var2).getBlock() != Blocks.piston_extension)
          {
            setDead();
            
            if ((!field_145808_f) && (worldObj.canBlockBePlaced(var1, var2, true, net.minecraft.util.EnumFacing.UP, null, null)) && (!BlockFalling.canFallInto(worldObj, var2.offsetDown())) && (worldObj.setBlockState(var2, field_175132_d, 3)))
            {
              if ((var1 instanceof BlockFalling))
              {
                ((BlockFalling)var1).onEndFalling(worldObj, var2);
              }
              
              if ((tileEntityData != null) && ((var1 instanceof net.minecraft.block.ITileEntityProvider)))
              {
                TileEntity var3 = worldObj.getTileEntity(var2);
                
                if (var3 != null)
                {
                  NBTTagCompound var4 = new NBTTagCompound();
                  var3.writeToNBT(var4);
                  Iterator var5 = tileEntityData.getKeySet().iterator();
                  
                  while (var5.hasNext())
                  {
                    String var6 = (String)var5.next();
                    NBTBase var7 = tileEntityData.getTag(var6);
                    
                    if ((!var6.equals("x")) && (!var6.equals("y")) && (!var6.equals("z")))
                    {
                      var4.setTag(var6, var7.copy());
                    }
                  }
                  
                  var3.readFromNBT(var4);
                  var3.markDirty();
                }
              }
            }
            else if ((shouldDropItem) && (!field_145808_f) && (worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")))
            {
              entityDropItem(new ItemStack(var1, 1, var1.damageDropped(field_175132_d)), 0.0F);
            }
          }
        }
        else if (((fallTime > 100) && (!worldObj.isRemote) && ((var2.getY() < 1) || (var2.getY() > 256))) || (fallTime > 600))
        {
          if ((shouldDropItem) && (worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")))
          {
            entityDropItem(new ItemStack(var1, 1, var1.damageDropped(field_175132_d)), 0.0F);
          }
          
          setDead();
        }
      }
    }
  }
  
  public void fall(float distance, float damageMultiplier)
  {
    Block var3 = field_175132_d.getBlock();
    
    if (hurtEntities)
    {
      int var4 = MathHelper.ceiling_float_int(distance - 1.0F);
      
      if (var4 > 0)
      {
        ArrayList var5 = com.google.common.collect.Lists.newArrayList(worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()));
        boolean var6 = var3 == Blocks.anvil;
        DamageSource var7 = var6 ? DamageSource.anvil : DamageSource.fallingBlock;
        Iterator var8 = var5.iterator();
        
        while (var8.hasNext())
        {
          Entity var9 = (Entity)var8.next();
          var9.attackEntityFrom(var7, Math.min(MathHelper.floor_float(var4 * fallHurtAmount), fallHurtMax));
        }
        
        if ((var6) && (rand.nextFloat() < 0.05000000074505806D + var4 * 0.05D))
        {
          int var10 = ((Integer)field_175132_d.getValue(BlockAnvil.DAMAGE)).intValue();
          var10++;
          
          if (var10 > 2)
          {
            field_145808_f = true;
          }
          else
          {
            field_175132_d = field_175132_d.withProperty(BlockAnvil.DAMAGE, Integer.valueOf(var10));
          }
        }
      }
    }
  }
  



  protected void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    Block var2 = field_175132_d != null ? field_175132_d.getBlock() : Blocks.air;
    ResourceLocation var3 = (ResourceLocation)Block.blockRegistry.getNameForObject(var2);
    tagCompound.setString("Block", var3 == null ? "" : var3.toString());
    tagCompound.setByte("Data", (byte)var2.getMetaFromState(field_175132_d));
    tagCompound.setByte("Time", (byte)fallTime);
    tagCompound.setBoolean("DropItem", shouldDropItem);
    tagCompound.setBoolean("HurtEntities", hurtEntities);
    tagCompound.setFloat("FallHurtAmount", fallHurtAmount);
    tagCompound.setInteger("FallHurtMax", fallHurtMax);
    
    if (tileEntityData != null)
    {
      tagCompound.setTag("TileEntityData", tileEntityData);
    }
  }
  



  protected void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    int var2 = tagCompund.getByte("Data") & 0xFF;
    
    if (tagCompund.hasKey("Block", 8))
    {
      field_175132_d = Block.getBlockFromName(tagCompund.getString("Block")).getStateFromMeta(var2);
    }
    else if (tagCompund.hasKey("TileID", 99))
    {
      field_175132_d = Block.getBlockById(tagCompund.getInteger("TileID")).getStateFromMeta(var2);
    }
    else
    {
      field_175132_d = Block.getBlockById(tagCompund.getByte("Tile") & 0xFF).getStateFromMeta(var2);
    }
    
    fallTime = (tagCompund.getByte("Time") & 0xFF);
    Block var3 = field_175132_d.getBlock();
    
    if (tagCompund.hasKey("HurtEntities", 99))
    {
      hurtEntities = tagCompund.getBoolean("HurtEntities");
      fallHurtAmount = tagCompund.getFloat("FallHurtAmount");
      fallHurtMax = tagCompund.getInteger("FallHurtMax");
    }
    else if (var3 == Blocks.anvil)
    {
      hurtEntities = true;
    }
    
    if (tagCompund.hasKey("DropItem", 99))
    {
      shouldDropItem = tagCompund.getBoolean("DropItem");
    }
    
    if (tagCompund.hasKey("TileEntityData", 10))
    {
      tileEntityData = tagCompund.getCompoundTag("TileEntityData");
    }
    
    if ((var3 == null) || (var3.getMaterial() == Material.air))
    {
      field_175132_d = Blocks.sand.getDefaultState();
    }
  }
  
  public World getWorldObj()
  {
    return worldObj;
  }
  
  public void setHurtEntities(boolean p_145806_1_)
  {
    hurtEntities = p_145806_1_;
  }
  



  public boolean canRenderOnFire()
  {
    return false;
  }
  
  public void addEntityCrashInfo(CrashReportCategory category)
  {
    super.addEntityCrashInfo(category);
    
    if (field_175132_d != null)
    {
      Block var2 = field_175132_d.getBlock();
      category.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(var2)));
      category.addCrashSection("Immitating block data", Integer.valueOf(var2.getMetaFromState(field_175132_d)));
    }
  }
  
  public IBlockState getBlock()
  {
    return field_175132_d;
  }
}
