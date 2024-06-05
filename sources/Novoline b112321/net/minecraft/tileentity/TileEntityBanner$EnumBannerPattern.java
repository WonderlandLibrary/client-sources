package net.minecraft.tileentity;

import net.minecraft.block.BlockFlower$EnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public enum TileEntityBanner$EnumBannerPattern {
   BASE("base", "b"),
   SQUARE_BOTTOM_LEFT("square_bottom_left", "bl", "   ", "   ", "#  "),
   SQUARE_BOTTOM_RIGHT("square_bottom_right", "br", "   ", "   ", "  #"),
   SQUARE_TOP_LEFT("square_top_left", "tl", "#  ", "   ", "   "),
   SQUARE_TOP_RIGHT("square_top_right", "tr", "  #", "   ", "   "),
   STRIPE_BOTTOM("stripe_bottom", "bs", "   ", "   ", "###"),
   STRIPE_TOP("stripe_top", "ts", "###", "   ", "   "),
   STRIPE_LEFT("stripe_left", "ls", "#  ", "#  ", "#  "),
   STRIPE_RIGHT("stripe_right", "rs", "  #", "  #", "  #"),
   STRIPE_CENTER("stripe_center", "cs", " # ", " # ", " # "),
   STRIPE_MIDDLE("stripe_middle", "ms", "   ", "###", "   "),
   STRIPE_DOWNRIGHT("stripe_downright", "drs", "#  ", " # ", "  #"),
   STRIPE_DOWNLEFT("stripe_downleft", "dls", "  #", " # ", "#  "),
   STRIPE_SMALL("small_stripes", "ss", "# #", "# #", "   "),
   CROSS("cross", "cr", "# #", " # ", "# #"),
   STRAIGHT_CROSS("straight_cross", "sc", " # ", "###", " # "),
   TRIANGLE_BOTTOM("triangle_bottom", "bt", "   ", " # ", "# #"),
   TRIANGLE_TOP("triangle_top", "tt", "# #", " # ", "   "),
   TRIANGLES_BOTTOM("triangles_bottom", "bts", "   ", "# #", " # "),
   TRIANGLES_TOP("triangles_top", "tts", " # ", "# #", "   "),
   DIAGONAL_LEFT("diagonal_left", "ld", "## ", "#  ", "   "),
   DIAGONAL_RIGHT("diagonal_up_right", "rd", "   ", "  #", " ##"),
   DIAGONAL_LEFT_MIRROR("diagonal_up_left", "lud", "   ", "#  ", "## "),
   DIAGONAL_RIGHT_MIRROR("diagonal_right", "rud", " ##", "  #", "   "),
   CIRCLE_MIDDLE("circle", "mc", "   ", " # ", "   "),
   RHOMBUS_MIDDLE("rhombus", "mr", " # ", "# #", " # "),
   HALF_VERTICAL("half_vertical", "vh", "## ", "## ", "## "),
   HALF_HORIZONTAL("half_horizontal", "hh", "###", "###", "   "),
   HALF_VERTICAL_MIRROR("half_vertical_right", "vhr", " ##", " ##", " ##"),
   HALF_HORIZONTAL_MIRROR("half_horizontal_bottom", "hhb", "   ", "###", "###"),
   BORDER("border", "bo", "###", "# #", "###"),
   CURLY_BORDER("curly_border", "cbo", new ItemStack(Blocks.vine)),
   CREEPER("creeper", "cre", new ItemStack(Items.skull, 1, 4)),
   GRADIENT("gradient", "gra", "# #", " # ", " # "),
   GRADIENT_UP("gradient_up", "gru", " # ", " # ", "# #"),
   BRICKS("bricks", "bri", new ItemStack(Blocks.brick_block)),
   SKULL("skull", "sku", new ItemStack(Items.skull, 1, 1)),
   FLOWER("flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower$EnumFlowerType.OXEYE_DAISY.getMeta())),
   MOJANG("mojang", "moj", new ItemStack(Items.golden_apple, 1, 1));

   private String patternName;
   private String patternID;
   private String[] craftingLayers;
   private ItemStack patternCraftingStack;
   private static final TileEntityBanner$EnumBannerPattern[] $VALUES = new TileEntityBanner$EnumBannerPattern[]{BASE, SQUARE_BOTTOM_LEFT, SQUARE_BOTTOM_RIGHT, SQUARE_TOP_LEFT, SQUARE_TOP_RIGHT, STRIPE_BOTTOM, STRIPE_TOP, STRIPE_LEFT, STRIPE_RIGHT, STRIPE_CENTER, STRIPE_MIDDLE, STRIPE_DOWNRIGHT, STRIPE_DOWNLEFT, STRIPE_SMALL, CROSS, STRAIGHT_CROSS, TRIANGLE_BOTTOM, TRIANGLE_TOP, TRIANGLES_BOTTOM, TRIANGLES_TOP, DIAGONAL_LEFT, DIAGONAL_RIGHT, DIAGONAL_LEFT_MIRROR, DIAGONAL_RIGHT_MIRROR, CIRCLE_MIDDLE, RHOMBUS_MIDDLE, HALF_VERTICAL, HALF_HORIZONTAL, HALF_VERTICAL_MIRROR, HALF_HORIZONTAL_MIRROR, BORDER, CURLY_BORDER, CREEPER, GRADIENT, GRADIENT_UP, BRICKS, SKULL, FLOWER, MOJANG};

   private TileEntityBanner$EnumBannerPattern(String var3, String var4) {
      this.craftingLayers = new String[3];
      this.patternName = var3;
      this.patternID = var4;
   }

   private TileEntityBanner$EnumBannerPattern(String var3, String var4, ItemStack var5) {
      this(var3, var4);
      this.patternCraftingStack = var5;
   }

   private TileEntityBanner$EnumBannerPattern(String var3, String var4, String var5, String var6, String var7) {
      this(var3, var4);
      this.craftingLayers[0] = var5;
      this.craftingLayers[1] = var6;
      this.craftingLayers[2] = var7;
   }

   public String getPatternName() {
      return this.patternName;
   }

   public String getPatternID() {
      return this.patternID;
   }

   public String[] getCraftingLayers() {
      return this.craftingLayers;
   }

   public boolean hasValidCrafting() {
      return this.patternCraftingStack != null || this.craftingLayers[0] != null;
   }

   public boolean hasCraftingStack() {
      return this.patternCraftingStack != null;
   }

   public ItemStack getCraftingStack() {
      return this.patternCraftingStack;
   }

   public static TileEntityBanner$EnumBannerPattern getPatternByID(String var0) {
      for(TileEntityBanner$EnumBannerPattern var4 : values()) {
         if(var4.patternID.equals(var0)) {
            return var4;
         }
      }

      return null;
   }
}
