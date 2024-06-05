package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.aSL;
import net.aXp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.BlockStoneBrick$EnumType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSilverfish extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", aXp.class);

   public BlockSilverfish() {
      super(Material.clay);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, aXp.STONE));
      this.setHardness(0.0F);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public static boolean canContainSilverfish(IBlockState var0) {
      Block var1 = var0.getBlock();
      return var0 == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone$EnumType.STONE) || var1 == Blocks.cobblestone || var1 == Blocks.stonebrick;
   }

   protected ItemStack createStackedBlock(IBlockState var1) {
      switch(aSL.a[((aXp)var1.getValue(VARIANT)).ordinal()]) {
      case 1:
         return new ItemStack(Blocks.cobblestone);
      case 2:
         return new ItemStack(Blocks.stonebrick);
      case 3:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick$EnumType.MOSSY.getMetadata());
      case 4:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick$EnumType.CRACKED.getMetadata());
      case 5:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick$EnumType.CHISELED.getMetadata());
      default:
         return new ItemStack(Blocks.stone);
      }
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if(!var1.isRemote && var1.getGameRules().getBoolean("doTileDrops")) {
         EntitySilverfish var6 = new EntitySilverfish(var1);
         var6.setLocationAndAngles((double)var2.getX() + 0.5D, (double)var2.getY(), (double)var2.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var6);
         var6.spawnExplosionParticle();
      }

   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock().getMetaFromState(var3);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(aXp var7 : aXp.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, aXp.a(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((aXp)var1.getValue(VARIANT)).a();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
