package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockStoneSlab$EnumType implements IStringSerializable {
   STONE(0, MapColor.stoneColor, "stone"),
   SAND(1, MapColor.sandColor, "sandstone", "sand"),
   WOOD(2, MapColor.woodColor, "wood_old", "wood"),
   COBBLESTONE(3, MapColor.stoneColor, "cobblestone", "cobble"),
   BRICK(4, MapColor.redColor, "brick"),
   SMOOTHBRICK(5, MapColor.stoneColor, "stone_brick", "smoothStoneBrick"),
   NETHERBRICK(6, MapColor.netherrackColor, "nether_brick", "netherBrick"),
   QUARTZ(7, MapColor.quartzColor, "quartz");

   private static final BlockStoneSlab$EnumType[] META_LOOKUP = new BlockStoneSlab$EnumType[values().length];
   private final int meta;
   private final MapColor field_181075_k;
   private final String name;
   private final String unlocalizedName;
   private static final BlockStoneSlab$EnumType[] $VALUES = new BlockStoneSlab$EnumType[]{STONE, SAND, WOOD, COBBLESTONE, BRICK, SMOOTHBRICK, NETHERBRICK, QUARTZ};

   private BlockStoneSlab$EnumType(int var3, MapColor var4, String var5) {
      this(var3, var4, var5, var5);
   }

   private BlockStoneSlab$EnumType(int var3, MapColor var4, String var5, String var6) {
      this.meta = var3;
      this.field_181075_k = var4;
      this.name = var5;
      this.unlocalizedName = var6;
   }

   public int getMetadata() {
      return this.meta;
   }

   public MapColor func_181074_c() {
      return this.field_181075_k;
   }

   public String toString() {
      return this.name;
   }

   public static BlockStoneSlab$EnumType byMetadata(int var0) {
      if(var0 >= META_LOOKUP.length) {
         var0 = 0;
      }

      return META_LOOKUP[var0];
   }

   public String getName() {
      return this.name;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   static {
      for(BlockStoneSlab$EnumType var11 : values()) {
         META_LOOKUP[var11.getMetadata()] = var11;
      }

   }
}
