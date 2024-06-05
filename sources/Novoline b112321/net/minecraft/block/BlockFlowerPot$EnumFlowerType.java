package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockFlowerPot$EnumFlowerType implements IStringSerializable {
   EMPTY("empty"),
   POPPY("rose"),
   BLUE_ORCHID("blue_orchid"),
   ALLIUM("allium"),
   HOUSTONIA("houstonia"),
   RED_TULIP("red_tulip"),
   ORANGE_TULIP("orange_tulip"),
   WHITE_TULIP("white_tulip"),
   PINK_TULIP("pink_tulip"),
   OXEYE_DAISY("oxeye_daisy"),
   DANDELION("dandelion"),
   OAK_SAPLING("oak_sapling"),
   SPRUCE_SAPLING("spruce_sapling"),
   BIRCH_SAPLING("birch_sapling"),
   JUNGLE_SAPLING("jungle_sapling"),
   ACACIA_SAPLING("acacia_sapling"),
   DARK_OAK_SAPLING("dark_oak_sapling"),
   MUSHROOM_RED("mushroom_red"),
   MUSHROOM_BROWN("mushroom_brown"),
   DEAD_BUSH("dead_bush"),
   FERN("fern"),
   CACTUS("cactus");

   private final String name;
   private static final BlockFlowerPot$EnumFlowerType[] $VALUES = new BlockFlowerPot$EnumFlowerType[]{EMPTY, POPPY, BLUE_ORCHID, ALLIUM, HOUSTONIA, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, DANDELION, OAK_SAPLING, SPRUCE_SAPLING, BIRCH_SAPLING, JUNGLE_SAPLING, ACACIA_SAPLING, DARK_OAK_SAPLING, MUSHROOM_RED, MUSHROOM_BROWN, DEAD_BUSH, FERN, CACTUS};

   private BlockFlowerPot$EnumFlowerType(String var3) {
      this.name = var3;
   }

   public String toString() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }
}
