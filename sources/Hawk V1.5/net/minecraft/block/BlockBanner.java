package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {
   public static final PropertyDirection FACING_PROP;
   public static final PropertyInteger ROTATION_PROP;
   private static final String __OBFID = "CL_00002143";

   public boolean isOpaqueCube() {
      return false;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityBanner();
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      TileEntity var6 = var1.getTileEntity(var2);
      if (var6 instanceof TileEntityBanner) {
         ItemStack var7 = new ItemStack(Items.banner, 1, ((TileEntityBanner)var6).getBaseColor());
         NBTTagCompound var8 = new NBTTagCompound();
         var6.writeToNBT(var8);
         var8.removeTag("x");
         var8.removeTag("y");
         var8.removeTag("z");
         var8.removeTag("id");
         var7.setTagInfo("BlockEntityTag", var8);
         spawnAsEntity(var1, var2, var7);
      } else {
         super.dropBlockAsItemWithChance(var1, var2, var3, var4, var5);
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.banner;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   static {
      FACING_PROP = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      ROTATION_PROP = PropertyInteger.create("rotation", 0, 15);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.banner;
   }

   public boolean isFullCube() {
      return false;
   }

   protected BlockBanner() {
      super(Material.wood);
      float var1 = 0.25F;
      float var2 = 1.0F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (var5 instanceof TileEntityBanner) {
         ItemStack var6 = new ItemStack(Items.banner, 1, ((TileEntityBanner)var5).getBaseColor());
         NBTTagCompound var7 = new NBTTagCompound();
         var5.writeToNBT(var7);
         var7.removeTag("x");
         var7.removeTag("y");
         var7.removeTag("z");
         var7.removeTag("id");
         var6.setTagInfo("BlockEntityTag", var7);
         spawnAsEntity(var1, var3, var6);
      } else {
         super.harvestBlock(var1, var2, var3, var4, (TileEntity)null);
      }

   }

   static final class SwitchEnumFacing {
      static final int[] SWITCH_MAP = new int[EnumFacing.values().length];
      private static final String __OBFID = "CL_00002142";

      static {
         try {
            SWITCH_MAP[EnumFacing.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            SWITCH_MAP[EnumFacing.SOUTH.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            SWITCH_MAP[EnumFacing.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            SWITCH_MAP[EnumFacing.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }

   public static class BlockBannerHanging extends BlockBanner {
      private static final String __OBFID = "CL_00002140";

      public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
         EnumFacing var3 = (EnumFacing)var1.getBlockState(var2).getValue(FACING_PROP);
         float var4 = 0.0F;
         float var5 = 0.78125F;
         float var6 = 0.0F;
         float var7 = 1.0F;
         float var8 = 0.125F;
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         switch(BlockBanner.SwitchEnumFacing.SWITCH_MAP[var3.ordinal()]) {
         case 1:
         default:
            this.setBlockBounds(var6, var4, 1.0F - var8, var7, var5, 1.0F);
            break;
         case 2:
            this.setBlockBounds(var6, var4, 0.0F, var7, var5, var8);
            break;
         case 3:
            this.setBlockBounds(1.0F - var8, var4, var6, 1.0F, var5, var7);
            break;
         case 4:
            this.setBlockBounds(0.0F, var4, var6, var8, var5, var7);
         }

      }

      protected BlockState createBlockState() {
         return new BlockState(this, new IProperty[]{FACING_PROP});
      }

      public BlockBannerHanging() {
         this.setDefaultState(this.blockState.getBaseState().withProperty(FACING_PROP, EnumFacing.NORTH));
      }

      public int getMetaFromState(IBlockState var1) {
         return ((EnumFacing)var1.getValue(FACING_PROP)).getIndex();
      }

      public IBlockState getStateFromMeta(int var1) {
         EnumFacing var2 = EnumFacing.getFront(var1);
         if (var2.getAxis() == EnumFacing.Axis.Y) {
            var2 = EnumFacing.NORTH;
         }

         return this.getDefaultState().withProperty(FACING_PROP, var2);
      }

      public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING_PROP);
         if (!var1.getBlockState(var2.offset(var5.getOpposite())).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }

         super.onNeighborBlockChange(var1, var2, var3, var4);
      }
   }

   public static class BlockBannerStanding extends BlockBanner {
      private static final String __OBFID = "CL_00002141";

      public int getMetaFromState(IBlockState var1) {
         return (Integer)var1.getValue(ROTATION_PROP);
      }

      public IBlockState getStateFromMeta(int var1) {
         return this.getDefaultState().withProperty(ROTATION_PROP, var1);
      }

      public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
         if (!var1.getBlockState(var2.offsetDown()).getBlock().getMaterial().isSolid()) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }

         super.onNeighborBlockChange(var1, var2, var3, var4);
      }

      public BlockBannerStanding() {
         this.setDefaultState(this.blockState.getBaseState().withProperty(ROTATION_PROP, 0));
      }

      protected BlockState createBlockState() {
         return new BlockState(this, new IProperty[]{ROTATION_PROP});
      }
   }
}
