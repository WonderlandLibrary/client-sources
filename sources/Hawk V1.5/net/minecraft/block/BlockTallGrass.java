package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTallGrass extends BlockBush implements IGrowable {
   private static final String __OBFID = "CL_00000321";
   public static final PropertyEnum field_176497_a = PropertyEnum.create("type", BlockTallGrass.EnumType.class);

   public boolean isReplaceable(World var1, BlockPos var2) {
      return true;
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockTallGrass.EnumType)var1.getValue(field_176497_a)).func_177044_a();
   }

   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XYZ;
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.isRemote && var2.getCurrentEquippedItem() != null && var2.getCurrentEquippedItem().getItem() == Items.shears) {
         var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
         spawnAsEntity(var1, var3, new ItemStack(Blocks.tallgrass, 1, ((BlockTallGrass.EnumType)var4.getValue(field_176497_a)).func_177044_a()));
      } else {
         super.harvestBlock(var1, var2, var3, var4, var5);
      }

   }

   protected BlockTallGrass() {
      super(Material.vine);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176497_a, BlockTallGrass.EnumType.DEAD_BUSH));
      float var1 = 0.4F;
      this.setBlockBounds(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.8F, 0.5F + var1);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176497_a, BlockTallGrass.EnumType.func_177045_a(var1));
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(int var4 = 1; var4 < 3; ++var4) {
         var3.add(new ItemStack(var1, 1, var4));
      }

   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return var2.nextInt(8) == 0 ? Items.wheat_seeds : null;
   }

   public int getRenderColor(IBlockState var1) {
      if (var1.getBlock() != this) {
         return super.getRenderColor(var1);
      } else {
         BlockTallGrass.EnumType var2 = (BlockTallGrass.EnumType)var1.getValue(field_176497_a);
         return var2 == BlockTallGrass.EnumType.DEAD_BUSH ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
      }
   }

   public int getBlockColor() {
      return ColorizerGrass.getGrassColor(0.5D, 1.0D);
   }

   public int quantityDroppedWithBonus(int var1, Random var2) {
      return 1 + var2.nextInt(var1 * 2 + 1);
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.getBiomeGenForCoords(var2).func_180627_b(var2);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176497_a});
   }

   public boolean canUseBonemeal(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   public void grow(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockDoublePlant.EnumPlantType var5 = BlockDoublePlant.EnumPlantType.GRASS;
      if (var4.getValue(field_176497_a) == BlockTallGrass.EnumType.FERN) {
         var5 = BlockDoublePlant.EnumPlantType.FERN;
      }

      if (Blocks.double_plant.canPlaceBlockAt(var1, var3)) {
         Blocks.double_plant.func_176491_a(var1, var3, var5, 2);
      }

   }

   public boolean isStillGrowing(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var3.getValue(field_176497_a) != BlockTallGrass.EnumType.DEAD_BUSH;
   }

   public boolean canBlockStay(World var1, BlockPos var2, IBlockState var3) {
      return this.canPlaceBlockOn(var1.getBlockState(var2.offsetDown()).getBlock());
   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock().getMetaFromState(var3);
   }

   public static enum EnumType implements IStringSerializable {
      FERN("FERN", 2, 2, "fern");

      private static final BlockTallGrass.EnumType[] ENUM$VALUES = new BlockTallGrass.EnumType[]{DEAD_BUSH, GRASS, FERN};
      private static final BlockTallGrass.EnumType[] field_177048_d = new BlockTallGrass.EnumType[values().length];
      DEAD_BUSH("DEAD_BUSH", 0, 0, "dead_bush");

      private final int field_177049_e;
      GRASS("GRASS", 1, 1, "tall_grass");

      private static final String __OBFID = "CL_00002055";
      private static final BlockTallGrass.EnumType[] $VALUES = new BlockTallGrass.EnumType[]{DEAD_BUSH, GRASS, FERN};
      private final String field_177046_f;

      public String getName() {
         return this.field_177046_f;
      }

      public String toString() {
         return this.field_177046_f;
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this.field_177049_e = var5;
         this.field_177046_f = var6;
      }

      public int func_177044_a() {
         return this.field_177049_e;
      }

      static {
         BlockTallGrass.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockTallGrass.EnumType var3 = var0[var2];
            field_177048_d[var3.func_177044_a()] = var3;
         }

      }

      public static BlockTallGrass.EnumType func_177045_a(int var0) {
         if (var0 < 0 || var0 >= field_177048_d.length) {
            var0 = 0;
         }

         return field_177048_d[var0];
      }
   }
}
