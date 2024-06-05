package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.ITileEntityProvider;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFallingBlock extends Entity {
   private IBlockState fallTile;
   public int fallTime;
   public boolean shouldDropItem = true;
   private boolean canSetAsBlock;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public NBTTagCompound tileEntityData;

   public EntityFallingBlock(World var1) {
      super(var1);
   }

   public EntityFallingBlock(World var1, double var2, double var4, double var6, IBlockState var8) {
      super(var1);
      this.fallTile = var8;
      this.preventEntitySpawning = true;
      this.setSize(0.98F, 0.98F);
      this.setPosition(var2, var4, var6);
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      this.prevPosX = var2;
      this.prevPosY = var4;
      this.prevPosZ = var6;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
   }

   public boolean canBeCollidedWith() {
      return !this.isDead;
   }

   public void onUpdate() {
      Block var1 = this.fallTile.getBlock();
      if(var1.getMaterial() == Material.air) {
         this.setDead();
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         if(this.fallTime++ == 0) {
            BlockPos var2 = new BlockPos(this);
            if(this.worldObj.getBlockState(var2).getBlock() == var1) {
               this.worldObj.setBlockToAir(var2);
            } else if(!this.worldObj.isRemote) {
               this.setDead();
               return;
            }
         }

         this.motionY -= 0.03999999910593033D;
         this.moveEntity(this.motionX, this.motionY, this.motionZ);
         this.motionX *= 0.9800000190734863D;
         this.motionY *= 0.9800000190734863D;
         this.motionZ *= 0.9800000190734863D;
         if(!this.worldObj.isRemote) {
            BlockPos var8 = new BlockPos(this);
            if(this.onGround) {
               this.motionX *= 0.699999988079071D;
               this.motionZ *= 0.699999988079071D;
               this.motionY *= -0.5D;
               if(this.worldObj.getBlockState(var8).getBlock() != Blocks.piston_extension) {
                  this.setDead();
                  if(!this.canSetAsBlock) {
                     if(this.worldObj.canBlockBePlaced(var1, var8, true, EnumFacing.UP, (Entity)null, (ItemStack)null) && !BlockFalling.canFallInto(this.worldObj, var8.down()) && this.worldObj.setBlockState(var8, this.fallTile, 3)) {
                        if(var1 instanceof BlockFalling) {
                           ((BlockFalling)var1).onEndFalling(this.worldObj, var8);
                        }

                        if(this.tileEntityData != null && var1 instanceof ITileEntityProvider) {
                           TileEntity var3 = this.worldObj.getTileEntity(var8);
                           NBTTagCompound var4 = new NBTTagCompound();
                           var3.writeToNBT(var4);

                           for(String var6 : this.tileEntityData.getKeySet()) {
                              NBTBase var7 = this.tileEntityData.getTag(var6);
                              if(!var6.equals("x") && !var6.equals("y") && !var6.equals("z")) {
                                 var4.setTag(var6, var7.copy());
                              }
                           }

                           var3.readFromNBT(var4);
                           var3.markDirty();
                        }
                     } else if(this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                        this.entityDropItem(new ItemStack(var1, 1, var1.damageDropped(this.fallTile)), 0.0F);
                     }
                  }
               }
            } else if(this.fallTime > 100 && !this.worldObj.isRemote && (var8.getY() < 1 || var8.getY() > 256) || this.fallTime > 600) {
               if(this.shouldDropItem && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
                  this.entityDropItem(new ItemStack(var1, 1, var1.damageDropped(this.fallTile)), 0.0F);
               }

               this.setDead();
            }
         }
      }

   }

   public void fall(float var1, float var2) {
      Block var3 = this.fallTile.getBlock();
      if(this.hurtEntities) {
         int var4 = MathHelper.ceiling_float_int(var1 - 1.0F);
         ArrayList var5 = Lists.newArrayList(this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox()));
         boolean var6 = var3 == Blocks.anvil;
         DamageSource var7 = DamageSource.anvil;

         for(Entity var9 : var5) {
            var9.attackEntityFrom(var7, (float)Math.min(MathHelper.floor_float((float)var4 * this.fallHurtAmount), this.fallHurtMax));
         }

         if((double)this.rand.nextFloat() < 0.05000000074505806D + (double)var4 * 0.05D) {
            int var10 = ((Integer)this.fallTile.getValue(BlockAnvil.Q)).intValue();
            ++var10;
            if(var10 > 2) {
               this.canSetAsBlock = true;
            } else {
               this.fallTile = this.fallTile.withProperty(BlockAnvil.Q, Integer.valueOf(var10));
            }
         }
      }

   }

   protected void writeEntityToNBT(NBTTagCompound var1) {
      Block var2 = this.fallTile != null?this.fallTile.getBlock():Blocks.air;
      ResourceLocation var3 = (ResourceLocation)Block.blockRegistry.getNameForObject(var2);
      var1.setString("Block", "");
      var1.setByte("Data", (byte)var2.getMetaFromState(this.fallTile));
      var1.setByte("Time", (byte)this.fallTime);
      var1.setBoolean("DropItem", this.shouldDropItem);
      var1.setBoolean("HurtEntities", this.hurtEntities);
      var1.setFloat("FallHurtAmount", this.fallHurtAmount);
      var1.setInteger("FallHurtMax", this.fallHurtMax);
      if(this.tileEntityData != null) {
         var1.setTag("TileEntityData", this.tileEntityData);
      }

   }

   protected void readEntityFromNBT(NBTTagCompound var1) {
      int var2 = var1.getByte("Data") & 255;
      if(var1.hasKey("Block", 8)) {
         this.fallTile = Block.getBlockFromName(var1.getString("Block")).getStateFromMeta(var2);
      } else if(var1.hasKey("TileID", 99)) {
         this.fallTile = Block.getBlockById(var1.getInteger("TileID")).getStateFromMeta(var2);
      } else {
         this.fallTile = Block.getBlockById(var1.getByte("Tile") & 255).getStateFromMeta(var2);
      }

      this.fallTime = var1.getByte("Time") & 255;
      Block var3 = this.fallTile.getBlock();
      if(var1.hasKey("HurtEntities", 99)) {
         this.hurtEntities = var1.getBoolean("HurtEntities");
         this.fallHurtAmount = var1.getFloat("FallHurtAmount");
         this.fallHurtMax = var1.getInteger("FallHurtMax");
      } else if(var3 == Blocks.anvil) {
         this.hurtEntities = true;
      }

      if(var1.hasKey("DropItem", 99)) {
         this.shouldDropItem = var1.getBoolean("DropItem");
      }

      if(var1.hasKey("TileEntityData", 10)) {
         this.tileEntityData = var1.getCompoundTag("TileEntityData");
      }

      if(var3.getMaterial() == Material.air) {
         this.fallTile = Blocks.sand.getDefaultState();
      }

   }

   public World getWorldObj() {
      return this.worldObj;
   }

   public void setHurtEntities(boolean var1) {
      this.hurtEntities = var1;
   }

   public boolean canRenderOnFire() {
      return false;
   }

   public void addEntityCrashInfo(CrashReportCategory var1) {
      super.addEntityCrashInfo(var1);
      if(this.fallTile != null) {
         Block var2 = this.fallTile.getBlock();
         var1.addCrashSection("Immitating block ID", Integer.valueOf(Block.getIdFromBlock(var2)));
         var1.addCrashSection("Immitating block data", Integer.valueOf(var2.getMetaFromState(this.fallTile)));
      }

   }

   public IBlockState getBlock() {
      return this.fallTile;
   }
}
