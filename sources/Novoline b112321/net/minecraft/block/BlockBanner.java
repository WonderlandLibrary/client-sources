package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Random;
import net.iV;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
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
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBanner extends BlockContainer {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   public static final iV P = iV.a("rotation", 0, 15);

   protected BlockBanner() {
      super(Material.wood);
      float var1 = 0.25F;
      float var2 = 1.0F;
      this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.banner.white.name");
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.getSelectedBoundingBox(var1, var2);
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean func_181623_g() {
      return true;
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityBanner();
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.banner;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.banner;
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      TileEntity var6 = var1.getTileEntity(var2);
      if(var6 instanceof TileEntityBanner) {
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

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return !this.func_181087_e(var1, var2) && super.canPlaceBlockAt(var1, var2);
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if(var5 instanceof TileEntityBanner) {
         TileEntityBanner var6 = (TileEntityBanner)var5;
         ItemStack var7 = new ItemStack(Items.banner, 1, ((TileEntityBanner)var5).getBaseColor());
         NBTTagCompound var8 = new NBTTagCompound();
         TileEntityBanner.func_181020_a(var8, var6.getBaseColor(), var6.func_181021_d());
         var7.setTagInfo("BlockEntityTag", var8);
         spawnAsEntity(var1, var3, var7);
      } else {
         super.harvestBlock(var1, var2, var3, var4, (TileEntity)null);
      }

   }
}
