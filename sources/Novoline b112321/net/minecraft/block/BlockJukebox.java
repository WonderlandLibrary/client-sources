package net.minecraft.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockJukebox$TileEntityJukebox;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockJukebox extends BlockContainer {
   public static final PropertyBool HAS_RECORD = PropertyBool.create("has_record");

   protected BlockJukebox() {
      super(Material.wood, MapColor.dirtColor);
      this.setDefaultState(this.blockState.getBaseState().withProperty(HAS_RECORD, Boolean.FALSE));
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(((Boolean)var3.getValue(HAS_RECORD)).booleanValue()) {
         this.dropRecord(var1, var2, var3);
         var3 = var3.withProperty(HAS_RECORD, Boolean.FALSE);
         var1.setBlockState(var2, var3, 2);
         return true;
      } else {
         return false;
      }
   }

   public void insertRecord(World var1, BlockPos var2, IBlockState var3, ItemStack var4) {
      if(!var1.isRemote) {
         TileEntity var5 = var1.getTileEntity(var2);
         if(var5 instanceof BlockJukebox$TileEntityJukebox) {
            ((BlockJukebox$TileEntityJukebox)var5).setRecord(new ItemStack(var4.getItem(), 1, var4.getMetadata()));
            var1.setBlockState(var2, var3.withProperty(HAS_RECORD, Boolean.TRUE), 2);
         }
      }

   }

   private void dropRecord(World var1, BlockPos var2, IBlockState var3) {
      if(!var1.isRemote) {
         TileEntity var4 = var1.getTileEntity(var2);
         if(var4 instanceof BlockJukebox$TileEntityJukebox) {
            BlockJukebox$TileEntityJukebox var5 = (BlockJukebox$TileEntityJukebox)var4;
            ItemStack var6 = var5.getRecord();
            var1.playAuxSFX(1005, var2, 0);
            var1.playRecord(var2, (String)null);
            var5.setRecord((ItemStack)null);
            float var7 = 0.7F;
            double var8 = (double)(var1.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            double var10 = (double)(var1.rand.nextFloat() * 0.7F) + 0.06000000238418579D + 0.6D;
            double var12 = (double)(var1.rand.nextFloat() * 0.7F) + 0.15000000596046448D;
            ItemStack var14 = var6.copy();
            EntityItem var15 = new EntityItem(var1, (double)var2.getX() + var8, (double)var2.getY() + var10, (double)var2.getZ() + var12, var14);
            var15.setDefaultPickupDelay();
            var1.spawnEntityInWorld(var15);
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      this.dropRecord(var1, var2, var3);
      super.breakBlock(var1, var2, var3);
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote) {
         super.dropBlockAsItemWithChance(var1, var2, var3, var4, 0);
      }

   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new BlockJukebox$TileEntityJukebox();
   }

   public boolean hasComparatorInputOverride() {
      return true;
   }

   public int getComparatorInputOverride(World var1, BlockPos var2) {
      TileEntity var3 = var1.getTileEntity(var2);
      if(var3 instanceof BlockJukebox$TileEntityJukebox) {
         ItemStack var4 = ((BlockJukebox$TileEntityJukebox)var3).getRecord();
         return Item.b(var4.getItem()) + 1 - Item.b(Items.record_13);
      } else {
         return 0;
      }
   }

   public int getRenderType() {
      return 3;
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(HAS_RECORD, Boolean.valueOf(true));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Boolean)var1.getValue(HAS_RECORD)).booleanValue()?1:0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{HAS_RECORD});
   }
}
