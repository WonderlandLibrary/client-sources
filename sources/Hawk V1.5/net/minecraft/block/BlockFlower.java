package net.minecraft.block;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public abstract class BlockFlower extends BlockBush {
   protected PropertyEnum field_176496_a;
   private static final String __OBFID = "CL_00000246";

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{this.func_176494_l()});
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockFlower.EnumFlowerType[] var4 = BlockFlower.EnumFlowerType.func_176966_a(this.func_176495_j());
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockFlower.EnumFlowerType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176968_b()));
      }

   }

   public Block.EnumOffsetType getOffsetType() {
      return Block.EnumOffsetType.XZ;
   }

   public abstract BlockFlower.EnumFlowerColor func_176495_j();

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(this.func_176494_l(), BlockFlower.EnumFlowerType.func_176967_a(this.func_176495_j(), var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockFlower.EnumFlowerType)var1.getValue(this.func_176494_l())).func_176968_b();
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockFlower.EnumFlowerType)var1.getValue(this.func_176494_l())).func_176968_b();
   }

   protected BlockFlower() {
      super(Material.plants);
      this.setDefaultState(this.blockState.getBaseState().withProperty(this.func_176494_l(), this.func_176495_j() == BlockFlower.EnumFlowerColor.RED ? BlockFlower.EnumFlowerType.POPPY : BlockFlower.EnumFlowerType.DANDELION));
   }

   public IProperty func_176494_l() {
      if (this.field_176496_a == null) {
         this.field_176496_a = PropertyEnum.create("type", BlockFlower.EnumFlowerType.class, new Predicate(this) {
            final BlockFlower this$0;
            private static final String __OBFID = "CL_00002120";

            public boolean apply(Object var1) {
               return this.func_180354_a((BlockFlower.EnumFlowerType)var1);
            }

            public boolean func_180354_a(BlockFlower.EnumFlowerType var1) {
               return var1.func_176964_a() == this.this$0.func_176495_j();
            }

            {
               this.this$0 = var1;
            }
         });
      }

      return this.field_176496_a;
   }

   public static enum EnumFlowerType implements IStringSerializable {
      private final int field_176979_m;
      private final String field_176977_o;
      private static final BlockFlower.EnumFlowerType[][] field_176981_k = new BlockFlower.EnumFlowerType[BlockFlower.EnumFlowerColor.values().length][];
      ALLIUM("ALLIUM", 3, BlockFlower.EnumFlowerColor.RED, 2, "allium"),
      BLUE_ORCHID("BLUE_ORCHID", 2, BlockFlower.EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"),
      DANDELION("DANDELION", 0, BlockFlower.EnumFlowerColor.YELLOW, 0, "dandelion"),
      RED_TULIP("RED_TULIP", 5, BlockFlower.EnumFlowerColor.RED, 4, "red_tulip", "tulipRed");

      private static final String __OBFID = "CL_00002119";
      private final BlockFlower.EnumFlowerColor field_176978_l;
      WHITE_TULIP("WHITE_TULIP", 7, BlockFlower.EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite");

      private final String field_176976_n;
      HOUSTONIA("HOUSTONIA", 4, BlockFlower.EnumFlowerColor.RED, 3, "houstonia"),
      POPPY("POPPY", 1, BlockFlower.EnumFlowerColor.RED, 0, "poppy"),
      OXEYE_DAISY("OXEYE_DAISY", 9, BlockFlower.EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");

      private static final BlockFlower.EnumFlowerType[] $VALUES = new BlockFlower.EnumFlowerType[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY};
      PINK_TULIP("PINK_TULIP", 8, BlockFlower.EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink");

      private static final BlockFlower.EnumFlowerType[] ENUM$VALUES = new BlockFlower.EnumFlowerType[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY};
      ORANGE_TULIP("ORANGE_TULIP", 6, BlockFlower.EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange");

      public static BlockFlower.EnumFlowerType[] func_176966_a(BlockFlower.EnumFlowerColor var0) {
         return field_176981_k[var0.ordinal()];
      }

      public String getName() {
         return this.field_176976_n;
      }

      public static BlockFlower.EnumFlowerType func_176967_a(BlockFlower.EnumFlowerColor var0, int var1) {
         BlockFlower.EnumFlowerType[] var2 = field_176981_k[var0.ordinal()];
         if (var1 < 0 || var1 >= var2.length) {
            var1 = 0;
         }

         return var2[var1];
      }

      public String func_176963_d() {
         return this.field_176977_o;
      }

      private EnumFlowerType(String var3, int var4, BlockFlower.EnumFlowerColor var5, int var6, String var7) {
         this(var3, var4, var5, var6, var7, var7);
      }

      public String toString() {
         return this.field_176976_n;
      }

      public int func_176968_b() {
         return this.field_176979_m;
      }

      private EnumFlowerType(String var3, int var4, BlockFlower.EnumFlowerColor var5, int var6, String var7, String var8) {
         this.field_176978_l = var5;
         this.field_176979_m = var6;
         this.field_176976_n = var7;
         this.field_176977_o = var8;
      }

      public BlockFlower.EnumFlowerColor func_176964_a() {
         return this.field_176978_l;
      }

      static {
         BlockFlower.EnumFlowerColor[] var0 = BlockFlower.EnumFlowerColor.values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockFlower.EnumFlowerColor var3 = var0[var2];
            Collection var4 = Collections2.filter(Lists.newArrayList(values()), new Predicate(var3) {
               private final BlockFlower.EnumFlowerColor val$var3;
               private static final String __OBFID = "CL_00002118";

               public boolean apply(Object var1) {
                  return this.func_180350_a((BlockFlower.EnumFlowerType)var1);
               }

               public boolean func_180350_a(BlockFlower.EnumFlowerType var1) {
                  return var1.func_176964_a() == this.val$var3;
               }

               {
                  this.val$var3 = var1;
               }
            });
            field_176981_k[var3.ordinal()] = (BlockFlower.EnumFlowerType[])var4.toArray(new BlockFlower.EnumFlowerType[var4.size()]);
         }

      }
   }

   public static enum EnumFlowerColor {
      RED("RED", 1);

      private static final BlockFlower.EnumFlowerColor[] ENUM$VALUES = new BlockFlower.EnumFlowerColor[]{YELLOW, RED};
      private static final String __OBFID = "CL_00002117";
      private static final BlockFlower.EnumFlowerColor[] $VALUES = new BlockFlower.EnumFlowerColor[]{YELLOW, RED};
      YELLOW("YELLOW", 0);

      private EnumFlowerColor(String var3, int var4) {
      }

      public BlockFlower func_180346_a() {
         return this == YELLOW ? Blocks.yellow_flower : Blocks.red_flower;
      }
   }
}
