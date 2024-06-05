package net.minecraft.block;

import java.util.List;
import java.util.Random;
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
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockSilverfish extends Block {
   public static final PropertyEnum VARIANT_PROP = PropertyEnum.create("variant", BlockSilverfish.EnumType.class);
   private static final String __OBFID = "CL_00000271";

   protected ItemStack createStackedBlock(IBlockState var1) {
      switch((BlockSilverfish.EnumType)var1.getValue(VARIANT_PROP)) {
      case COBBLESTONE:
         return new ItemStack(Blocks.cobblestone);
      case STONEBRICK:
         return new ItemStack(Blocks.stonebrick);
      case MOSSY_STONEBRICK:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetaFromState());
      case CRACKED_STONEBRICK:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetaFromState());
      case CHISELED_STONEBRICK:
         return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetaFromState());
      default:
         return new ItemStack(Blocks.stone);
      }
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public void dropBlockAsItemWithChance(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.isRemote && var1.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
         EntitySilverfish var6 = new EntitySilverfish(var1);
         var6.setLocationAndAngles((double)var2.getX() + 0.5D, (double)var2.getY(), (double)var2.getZ() + 0.5D, 0.0F, 0.0F);
         var1.spawnEntityInWorld(var6);
         var6.spawnExplosionParticle();
      }

   }

   public BlockSilverfish() {
      super(Material.clay);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT_PROP, BlockSilverfish.EnumType.STONE));
      this.setHardness(0.0F);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockSilverfish.EnumType)var1.getValue(VARIANT_PROP)).func_176881_a();
   }

   public static boolean func_176377_d(IBlockState var0) {
      Block var1 = var0.getBlock();
      return var0 == Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE) || var1 == Blocks.cobblestone || var1 == Blocks.stonebrick;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT_PROP});
   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock().getMetaFromState(var3);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockSilverfish.EnumType[] var4 = BlockSilverfish.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockSilverfish.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176881_a()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT_PROP, BlockSilverfish.EnumType.func_176879_a(var1));
   }

   public static enum EnumType implements IStringSerializable {
      private final int field_176893_h;
      private static final BlockSilverfish.EnumType[] $VALUES = new BlockSilverfish.EnumType[]{STONE, COBBLESTONE, STONEBRICK, MOSSY_STONEBRICK, CRACKED_STONEBRICK, CHISELED_STONEBRICK};
      MOSSY_STONEBRICK("MOSSY_STONEBRICK", 3, 3, "mossy_brick", "mossybrick", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002094";

         public IBlockState func_176883_d() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.MOSSY);
         }
      },
      COBBLESTONE("COBBLESTONE", 1, 1, "cobblestone", "cobble", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002096";

         public IBlockState func_176883_d() {
            return Blocks.cobblestone.getDefaultState();
         }
      };

      private static final BlockSilverfish.EnumType[] ENUM$VALUES = new BlockSilverfish.EnumType[]{STONE, COBBLESTONE, STONEBRICK, MOSSY_STONEBRICK, CRACKED_STONEBRICK, CHISELED_STONEBRICK};
      CRACKED_STONEBRICK("CRACKED_STONEBRICK", 4, 4, "cracked_brick", "crackedbrick", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002093";

         public IBlockState func_176883_d() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CRACKED);
         }
      },
      STONE("STONE", 0, 0, "stone", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002097";

         public IBlockState func_176883_d() {
            return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT_PROP, BlockStone.EnumType.STONE);
         }
      };

      private final String field_176891_j;
      private final String field_176894_i;
      private static final String __OBFID = "CL_00002098";
      STONEBRICK("STONEBRICK", 2, 2, "stone_brick", "brick", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002095";

         public IBlockState func_176883_d() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.DEFAULT);
         }
      },
      CHISELED_STONEBRICK("CHISELED_STONEBRICK", 5, 5, "chiseled_brick", "chiseledbrick", (BlockSilverfish.SwitchEnumType)null) {
         private static final String __OBFID = "CL_00002092";

         public IBlockState func_176883_d() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT_PROP, BlockStoneBrick.EnumType.CHISELED);
         }
      };

      private static final BlockSilverfish.EnumType[] field_176885_g = new BlockSilverfish.EnumType[values().length];

      public static BlockSilverfish.EnumType func_176879_a(int var0) {
         if (var0 < 0 || var0 >= field_176885_g.length) {
            var0 = 0;
         }

         return field_176885_g[var0];
      }

      public String toString() {
         return this.field_176894_i;
      }

      public String getName() {
         return this.field_176894_i;
      }

      public abstract IBlockState func_176883_d();

      EnumType(String var3, int var4, int var5, String var6, BlockSilverfish.SwitchEnumType var7, BlockSilverfish.EnumType var8) {
         this(var3, var4, var5, var6, var7);
      }

      private EnumType(String var3, int var4, int var5, String var6, BlockSilverfish.SwitchEnumType var7) {
         this(var3, var4, var5, var6);
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      public String func_176882_c() {
         return this.field_176891_j;
      }

      public int func_176881_a() {
         return this.field_176893_h;
      }

      public static BlockSilverfish.EnumType func_176878_a(IBlockState var0) {
         BlockSilverfish.EnumType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            BlockSilverfish.EnumType var4 = var1[var3];
            if (var0 == var4.func_176883_d()) {
               return var4;
            }
         }

         return STONE;
      }

      EnumType(String var3, int var4, int var5, String var6, String var7, BlockSilverfish.SwitchEnumType var8, BlockSilverfish.EnumType var9) {
         this(var3, var4, var5, var6, var7, var8);
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176893_h = var5;
         this.field_176894_i = var6;
         this.field_176891_j = var7;
      }

      static {
         BlockSilverfish.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockSilverfish.EnumType var3 = var0[var2];
            field_176885_g[var3.func_176881_a()] = var3;
         }

      }

      private EnumType(String var3, int var4, int var5, String var6, String var7, BlockSilverfish.SwitchEnumType var8) {
         this(var3, var4, var5, var6, var7);
      }
   }

   static final class SwitchEnumType {
      private static final String __OBFID = "CL_00002099";
      static final int[] field_180178_a = new int[BlockSilverfish.EnumType.values().length];

      static {
         try {
            field_180178_a[BlockSilverfish.EnumType.COBBLESTONE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180178_a[BlockSilverfish.EnumType.STONEBRICK.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180178_a[BlockSilverfish.EnumType.MOSSY_STONEBRICK.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180178_a[BlockSilverfish.EnumType.CRACKED_STONEBRICK.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180178_a[BlockSilverfish.EnumType.CHISELED_STONEBRICK.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
