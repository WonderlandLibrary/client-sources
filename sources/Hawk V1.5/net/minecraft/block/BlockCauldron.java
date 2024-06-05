package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron extends Block {
   private static final String __OBFID = "CL_00000213";
   public static final PropertyInteger field_176591_a = PropertyInteger.create("level", 0, 3);

   public void fillWithRain(World var1, BlockPos var2) {
      if (var1.rand.nextInt(20) == 1) {
         IBlockState var3 = var1.getBlockState(var2);
         if ((Integer)var3.getValue(field_176591_a) < 3) {
            var1.setBlockState(var2, var3.cycleProperty(field_176591_a), 2);
         }
      }

   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(field_176591_a);
   }

   public BlockCauldron() {
      super(Material.iron);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176591_a, 0));
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176591_a, var1);
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      int var5 = (Integer)var3.getValue(field_176591_a);
      float var6 = (float)var2.getY() + (6.0F + (float)(3 * var5)) / 16.0F;
      if (!var1.isRemote && var4.isBurning() && var5 > 0 && var4.getEntityBoundingBox().minY <= (double)var6) {
         var4.extinguish();
         this.func_176590_a(var1, var2, var3, var5 - 1);
      }

   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.isRemote) {
         return true;
      } else {
         ItemStack var9 = var4.inventory.getCurrentItem();
         if (var9 == null) {
            return true;
         } else {
            int var10 = (Integer)var3.getValue(field_176591_a);
            Item var11 = var9.getItem();
            if (var11 == Items.water_bucket) {
               if (var10 < 3) {
                  if (!var4.capabilities.isCreativeMode) {
                     var4.inventory.setInventorySlotContents(var4.inventory.currentItem, new ItemStack(Items.bucket));
                  }

                  this.func_176590_a(var1, var2, var3, 3);
               }

               return true;
            } else {
               ItemStack var12;
               if (var11 == Items.glass_bottle) {
                  if (var10 > 0) {
                     if (!var4.capabilities.isCreativeMode) {
                        var12 = new ItemStack(Items.potionitem, 1, 0);
                        if (!var4.inventory.addItemStackToInventory(var12)) {
                           var1.spawnEntityInWorld(new EntityItem(var1, (double)var2.getX() + 0.5D, (double)var2.getY() + 1.5D, (double)var2.getZ() + 0.5D, var12));
                        } else if (var4 instanceof EntityPlayerMP) {
                           ((EntityPlayerMP)var4).sendContainerToPlayer(var4.inventoryContainer);
                        }

                        --var9.stackSize;
                        if (var9.stackSize <= 0) {
                           var4.inventory.setInventorySlotContents(var4.inventory.currentItem, (ItemStack)null);
                        }
                     }

                     this.func_176590_a(var1, var2, var3, var10 - 1);
                  }

                  return true;
               } else {
                  if (var10 > 0 && var11 instanceof ItemArmor) {
                     ItemArmor var13 = (ItemArmor)var11;
                     if (var13.getArmorMaterial() == ItemArmor.ArmorMaterial.LEATHER && var13.hasColor(var9)) {
                        var13.removeColor(var9);
                        this.func_176590_a(var1, var2, var3, var10 - 1);
                        return true;
                     }
                  }

                  if (var10 > 0 && var11 instanceof ItemBanner && TileEntityBanner.func_175113_c(var9) > 0) {
                     var12 = var9.copy();
                     var12.stackSize = 1;
                     TileEntityBanner.func_175117_e(var12);
                     if (var9.stackSize <= 1 && !var4.capabilities.isCreativeMode) {
                        var4.inventory.setInventorySlotContents(var4.inventory.currentItem, var12);
                     } else {
                        if (!var4.inventory.addItemStackToInventory(var12)) {
                           var1.spawnEntityInWorld(new EntityItem(var1, (double)var2.getX() + 0.5D, (double)var2.getY() + 1.5D, (double)var2.getZ() + 0.5D, var12));
                        } else if (var4 instanceof EntityPlayerMP) {
                           ((EntityPlayerMP)var4).sendContainerToPlayer(var4.inventoryContainer);
                        }

                        if (!var4.capabilities.isCreativeMode) {
                           --var9.stackSize;
                        }
                     }

                     if (!var4.capabilities.isCreativeMode) {
                        this.func_176590_a(var1, var2, var3, var10 - 1);
                     }

                     return true;
                  } else {
                     return false;
                  }
               }
            }
         }
      }
   }

   public void func_176590_a(World var1, BlockPos var2, IBlockState var3, int var4) {
      var1.setBlockState(var2, var3.withProperty(field_176591_a, MathHelper.clamp_int(var4, 0, 3)), 2);
      var1.updateComparatorOutputLevel(var2, this);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.cauldron;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBoundsForItemRender();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.cauldron;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176591_a});
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      return (Integer)var1.getBlockState(var2).getValue(field_176591_a);
   }

   public void setBlockBoundsForItemRender() {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }
}
