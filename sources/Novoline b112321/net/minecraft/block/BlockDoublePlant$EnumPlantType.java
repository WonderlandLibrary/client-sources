package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockDoublePlant$EnumPlantType implements IStringSerializable {
   SUNFLOWER(0, "sunflower"),
   SYRINGA(1, "syringa"),
   GRASS(2, "double_grass", "grass"),
   FERN(3, "double_fern", "fern"),
   ROSE(4, "double_rose", "rose"),
   PAEONIA(5, "paeonia");

   private static final BlockDoublePlant$EnumPlantType[] META_LOOKUP = new BlockDoublePlant$EnumPlantType[values().length];
   private final int meta;
   private final String name;
   private final String unlocalizedName;
   private static final BlockDoublePlant$EnumPlantType[] $VALUES = new BlockDoublePlant$EnumPlantType[]{SUNFLOWER, SYRINGA, GRASS, FERN, ROSE, PAEONIA};

   private BlockDoublePlant$EnumPlantType(int var3, String var4) {
      this(var3, var4, var4);
   }

   private BlockDoublePlant$EnumPlantType(int var3, String var4, String var5) {
      this.meta = var3;
      this.name = var4;
      this.unlocalizedName = var5;
   }

   public int getMeta() {
      return this.meta;
   }

   public String toString() {
      return this.name;
   }

   public static BlockDoublePlant$EnumPlantType byMetadata(int var0) {
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
      for(BlockDoublePlant$EnumPlantType var11 : values()) {
         META_LOOKUP[var11.getMeta()] = var11;
      }

   }
}
