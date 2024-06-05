package net.minecraft.block;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.block.BlockFlower$EnumFlowerColor;
import net.minecraft.util.IStringSerializable;

public enum BlockFlower$EnumFlowerType implements IStringSerializable {
   DANDELION(BlockFlower$EnumFlowerColor.YELLOW, 0, "dandelion"),
   POPPY(BlockFlower$EnumFlowerColor.RED, 0, "poppy"),
   BLUE_ORCHID(BlockFlower$EnumFlowerColor.RED, 1, "blue_orchid", "blueOrchid"),
   ALLIUM(BlockFlower$EnumFlowerColor.RED, 2, "allium"),
   HOUSTONIA(BlockFlower$EnumFlowerColor.RED, 3, "houstonia"),
   RED_TULIP(BlockFlower$EnumFlowerColor.RED, 4, "red_tulip", "tulipRed"),
   ORANGE_TULIP(BlockFlower$EnumFlowerColor.RED, 5, "orange_tulip", "tulipOrange"),
   WHITE_TULIP(BlockFlower$EnumFlowerColor.RED, 6, "white_tulip", "tulipWhite"),
   PINK_TULIP(BlockFlower$EnumFlowerColor.RED, 7, "pink_tulip", "tulipPink"),
   OXEYE_DAISY(BlockFlower$EnumFlowerColor.RED, 8, "oxeye_daisy", "oxeyeDaisy");

   private static final BlockFlower$EnumFlowerType[][] TYPES_FOR_BLOCK = new BlockFlower$EnumFlowerType[BlockFlower$EnumFlowerColor.values().length][];
   private final BlockFlower$EnumFlowerColor blockType;
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockFlower$EnumFlowerType[] $VALUES = new BlockFlower$EnumFlowerType[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY};

   private BlockFlower$EnumFlowerType(BlockFlower$EnumFlowerColor var3, int var4, String var5) {
      this(var3, var4, var5, var5);
   }

   private BlockFlower$EnumFlowerType(BlockFlower$EnumFlowerColor var3, int var4, String var5, String var6) {
      this.blockType = var3;
      this.meta = var4;
      this.name = var5;
      this.unlocalizedName = var6;
   }

   public BlockFlower$EnumFlowerColor getBlockType() {
      return this.blockType;
   }

   public int getMeta() {
      return this.meta;
   }

   public static BlockFlower$EnumFlowerType getType(BlockFlower$EnumFlowerColor var0, int var1) {
      BlockFlower$EnumFlowerType[] var2 = TYPES_FOR_BLOCK[var0.ordinal()];
      if(var1 >= var2.length) {
         var1 = 0;
      }

      return var2[var1];
   }

   public static BlockFlower$EnumFlowerType[] getTypes(BlockFlower$EnumFlowerColor var0) {
      return TYPES_FOR_BLOCK[var0.ordinal()];
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   private static boolean lambda$static$0(BlockFlower$EnumFlowerColor var0, BlockFlower$EnumFlowerType var1) {
      return var1.getBlockType() == var0;
   }

   static {
      for(BlockFlower$EnumFlowerColor var11 : BlockFlower$EnumFlowerColor.values()) {
         Collection var12 = Collections2.filter(Lists.newArrayList(values()), BlockFlower$EnumFlowerType::lambda$static$0);
         TYPES_FOR_BLOCK[var11.ordinal()] = (BlockFlower$EnumFlowerType[])var12.toArray(new BlockFlower$EnumFlowerType[var12.size()]);
      }

   }
}
