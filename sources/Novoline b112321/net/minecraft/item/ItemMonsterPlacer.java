package net.minecraft.item;

import java.util.List;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList$EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition$MovingObjectType;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMonsterPlacer extends Item {
   public ItemMonsterPlacer() {
      this.setHasSubtypes(true);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public String getItemStackDisplayName(ItemStack var1) {
      String var2 = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
      String var3 = EntityList.getStringFromID(var1.getMetadata());
      var2 = var2 + " " + StatCollector.translateToLocal("entity." + var3 + ".name");
      return var2;
   }

   public int getColorFromItemStack(ItemStack var1, int var2) {
      EntityList$EntityEggInfo var3 = (EntityList$EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(var1.getMetadata()));
      return var3.primaryColor;
   }

   public boolean onItemUse(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if(var3.isRemote) {
         return true;
      } else if(!var2.a(var4.offset(var5), var5, var1)) {
         return false;
      } else {
         IBlockState var9 = var3.getBlockState(var4);
         if(var9.getBlock() == Blocks.mob_spawner) {
            TileEntity var10 = var3.getTileEntity(var4);
            if(var10 instanceof TileEntityMobSpawner) {
               MobSpawnerBaseLogic var11 = ((TileEntityMobSpawner)var10).getSpawnerBaseLogic();
               var11.setEntityName(EntityList.getStringFromID(var1.getMetadata()));
               var10.markDirty();
               var3.markBlockForUpdate(var4);
               if(!var2.abilities.isCreative()) {
                  --var1.stackSize;
               }

               return true;
            }
         }

         var4 = var4.offset(var5);
         double var14 = 0.0D;
         if(var5 == EnumFacing.UP && var9 instanceof BlockFence) {
            var14 = 0.5D;
         }

         Entity var12 = spawnCreature(var3, var1.getMetadata(), (double)var4.getX() + 0.5D, (double)var4.getY() + var14, (double)var4.getZ() + 0.5D);
         if(var12 instanceof EntityLivingBase && var1.hasDisplayName()) {
            var12.setCustomNameTag(var1.getDisplayName());
         }

         if(!var2.abilities.isCreative()) {
            --var1.stackSize;
         }

         return true;
      }
   }

   public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
      if(var2.isRemote) {
         return var1;
      } else {
         MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(var2, var3, true);
         if(var4.typeOfHit == MovingObjectPosition$MovingObjectType.BLOCK) {
            BlockPos var5 = var4.getBlockPos();
            if(!var2.isBlockModifiable(var3, var5)) {
               return var1;
            }

            if(!var3.a(var5, var4.facing, var1)) {
               return var1;
            }

            if(var2.getBlockState(var5).getBlock() instanceof BlockLiquid) {
               Entity var6 = spawnCreature(var2, var1.getMetadata(), (double)var5.getX() + 0.5D, (double)var5.getY() + 0.5D, (double)var5.getZ() + 0.5D);
               if(var6 instanceof EntityLivingBase && var1.hasDisplayName()) {
                  var6.setCustomNameTag(var1.getDisplayName());
               }

               if(!var3.abilities.isCreative()) {
                  --var1.stackSize;
               }

               var3.triggerAchievement(StatList.objectUseStats[Item.b(this)]);
            }
         }

         return var1;
      }
   }

   public static Entity spawnCreature(World var0, int var1, double var2, double var4, double var6) {
      if(!EntityList.entityEggs.containsKey(Integer.valueOf(var1))) {
         return null;
      } else {
         Entity var8 = null;

         for(int var9 = 0; var9 < 1; ++var9) {
            var8 = EntityList.createEntityByID(var1, var0);
            if(var8 instanceof EntityLivingBase) {
               EntityLiving var10 = (EntityLiving)var8;
               var8.setLocationAndAngles(var2, var4, var6, MathHelper.wrapAngleTo180_float(var0.rand.nextFloat() * 360.0F), 0.0F);
               var10.rotationYawHead = var10.rotationYaw;
               var10.renderYawOffset = var10.rotationYaw;
               var10.onInitialSpawn(var0.getDifficultyForLocation(new BlockPos(var10)), (IEntityLivingData)null);
               var0.spawnEntityInWorld(var8);
               var10.playLivingSound();
            }
         }

         return var8;
      }
   }

   public void getSubItems(Item var1, CreativeTabs var2, List var3) {
      for(EntityList$EntityEggInfo var5 : EntityList.entityEggs.values()) {
         var3.add(new ItemStack(var1, 1, var5.spawnedID));
      }

   }
}
