package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom$1;
import net.minecraft.block.BlockHugeMushroom$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockHugeMushroom extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockHugeMushroom$EnumType.class);
   private final Block smallBlock;

   public BlockHugeMushroom(Material var1, MapColor var2, Block var3) {
      super(var1, var2);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockHugeMushroom$EnumType.ALL_OUTSIDE));
      this.smallBlock = var3;
   }

   public int quantityDropped(Random var1) {
      return Math.max(0, var1.nextInt(10) - 7);
   }

   public MapColor getMapColor(IBlockState var1) {
      switch(BlockHugeMushroom$1.$SwitchMap$net$minecraft$block$BlockHugeMushroom$EnumType[((BlockHugeMushroom$EnumType)var1.getValue(VARIANT)).ordinal()]) {
      case 1:
         return MapColor.clothColor;
      case 2:
         return MapColor.sandColor;
      case 3:
         return MapColor.sandColor;
      default:
         return super.getMapColor(var1);
      }
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(this.smallBlock);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(this.smallBlock);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.getDefaultState();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockHugeMushroom$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockHugeMushroom$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
