package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileEntityBanner extends TileEntity {
   private String field_175121_j;
   private NBTTagList field_175118_f;
   private List field_175122_h;
   private boolean field_175119_g;
   private int baseColor;
   private List field_175123_i;
   private static final String __OBFID = "CL_00002044";

   public void writeToNBT(NBTTagCompound var1) {
      super.writeToNBT(var1);
      var1.setInteger("Base", this.baseColor);
      if (this.field_175118_f != null) {
         var1.setTag("Patterns", this.field_175118_f);
      }

   }

   public String func_175116_e() {
      this.func_175109_g();
      return this.field_175121_j;
   }

   public int getBaseColor() {
      return this.baseColor;
   }

   public static int func_175113_c(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      return var1 != null && var1.hasKey("Patterns") ? var1.getTagList("Patterns", 10).tagCount() : 0;
   }

   public Packet getDescriptionPacket() {
      NBTTagCompound var1 = new NBTTagCompound();
      this.writeToNBT(var1);
      return new S35PacketUpdateTileEntity(this.pos, 6, var1);
   }

   public List func_175114_c() {
      this.func_175109_g();
      return this.field_175122_h;
   }

   private void func_175109_g() {
      if (this.field_175122_h == null || this.field_175123_i == null || this.field_175121_j == null) {
         if (!this.field_175119_g) {
            this.field_175121_j = "";
         } else {
            this.field_175122_h = Lists.newArrayList();
            this.field_175123_i = Lists.newArrayList();
            this.field_175122_h.add(TileEntityBanner.EnumBannerPattern.BASE);
            this.field_175123_i.add(EnumDyeColor.func_176766_a(this.baseColor));
            this.field_175121_j = String.valueOf((new StringBuilder("b")).append(this.baseColor));
            if (this.field_175118_f != null) {
               for(int var1 = 0; var1 < this.field_175118_f.tagCount(); ++var1) {
                  NBTTagCompound var2 = this.field_175118_f.getCompoundTagAt(var1);
                  TileEntityBanner.EnumBannerPattern var3 = TileEntityBanner.EnumBannerPattern.func_177268_a(var2.getString("Pattern"));
                  if (var3 != null) {
                     this.field_175122_h.add(var3);
                     int var4 = var2.getInteger("Color");
                     this.field_175123_i.add(EnumDyeColor.func_176766_a(var4));
                     this.field_175121_j = String.valueOf((new StringBuilder(String.valueOf(this.field_175121_j))).append(var3.func_177273_b()).append(var4));
                  }
               }
            }
         }
      }

   }

   public static void func_175117_e(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      if (var1 != null && var1.hasKey("Patterns", 9)) {
         NBTTagList var2 = var1.getTagList("Patterns", 10);
         if (var2.tagCount() > 0) {
            var2.removeTag(var2.tagCount() - 1);
            if (var2.hasNoTags()) {
               var0.getTagCompound().removeTag("BlockEntityTag");
               if (var0.getTagCompound().hasNoTags()) {
                  var0.setTagCompound((NBTTagCompound)null);
               }
            }
         }
      }

   }

   public void setItemValues(ItemStack var1) {
      this.field_175118_f = null;
      if (var1.hasTagCompound() && var1.getTagCompound().hasKey("BlockEntityTag", 10)) {
         NBTTagCompound var2 = var1.getTagCompound().getCompoundTag("BlockEntityTag");
         if (var2.hasKey("Patterns")) {
            this.field_175118_f = (NBTTagList)var2.getTagList("Patterns", 10).copy();
         }

         if (var2.hasKey("Base", 99)) {
            this.baseColor = var2.getInteger("Base");
         } else {
            this.baseColor = var1.getMetadata() & 15;
         }
      } else {
         this.baseColor = var1.getMetadata() & 15;
      }

      this.field_175122_h = null;
      this.field_175123_i = null;
      this.field_175121_j = "";
      this.field_175119_g = true;
   }

   public void readFromNBT(NBTTagCompound var1) {
      super.readFromNBT(var1);
      this.baseColor = var1.getInteger("Base");
      this.field_175118_f = var1.getTagList("Patterns", 10);
      this.field_175122_h = null;
      this.field_175123_i = null;
      this.field_175121_j = null;
      this.field_175119_g = true;
   }

   public List func_175110_d() {
      this.func_175109_g();
      return this.field_175123_i;
   }

   public static int getBaseColor(ItemStack var0) {
      NBTTagCompound var1 = var0.getSubCompound("BlockEntityTag", false);
      return var1 != null && var1.hasKey("Base") ? var1.getInteger("Base") : var0.getMetadata();
   }

   public static enum EnumBannerPattern {
      private String field_177284_N;
      DIAGONAL_RIGHT_MIRROR("DIAGONAL_RIGHT_MIRROR", 23, "diagonal_right", "rud", " ##", "  #", "   ");

      private String[] field_177291_P;
      private static final TileEntityBanner.EnumBannerPattern[] $VALUES = new TileEntityBanner.EnumBannerPattern[]{BASE, SQUARE_BOTTOM_LEFT, SQUARE_BOTTOM_RIGHT, SQUARE_TOP_LEFT, SQUARE_TOP_RIGHT, STRIPE_BOTTOM, STRIPE_TOP, STRIPE_LEFT, STRIPE_RIGHT, STRIPE_CENTER, STRIPE_MIDDLE, STRIPE_DOWNRIGHT, STRIPE_DOWNLEFT, STRIPE_SMALL, CROSS, STRAIGHT_CROSS, TRIANGLE_BOTTOM, TRIANGLE_TOP, TRIANGLES_BOTTOM, TRIANGLES_TOP, DIAGONAL_LEFT, DIAGONAL_RIGHT, DIAGONAL_LEFT_MIRROR, DIAGONAL_RIGHT_MIRROR, CIRCLE_MIDDLE, RHOMBUS_MIDDLE, HALF_VERTICAL, HALF_HORIZONTAL, HALF_VERTICAL_MIRROR, HALF_HORIZONTAL_MIRROR, BORDER, CURLY_BORDER, CREEPER, GRADIENT, GRADIENT_UP, BRICKS, SKULL, FLOWER, MOJANG};
      STRIPE_LEFT("STRIPE_LEFT", 7, "stripe_left", "ls", "#  ", "#  ", "#  "),
      HALF_HORIZONTAL_MIRROR("HALF_HORIZONTAL_MIRROR", 29, "half_horizontal_bottom", "hhb", "   ", "###", "###"),
      HALF_VERTICAL("HALF_VERTICAL", 26, "half_vertical", "vh", "## ", "## ", "## ");

      private ItemStack field_177290_Q;
      SQUARE_BOTTOM_LEFT("SQUARE_BOTTOM_LEFT", 1, "square_bottom_left", "bl", "   ", "   ", "#  "),
      DIAGONAL_LEFT("DIAGONAL_LEFT", 20, "diagonal_left", "ld", "## ", "#  ", "   "),
      STRAIGHT_CROSS("STRAIGHT_CROSS", 15, "straight_cross", "sc", " # ", "###", " # ");

      private static final TileEntityBanner.EnumBannerPattern[] ENUM$VALUES = new TileEntityBanner.EnumBannerPattern[]{BASE, SQUARE_BOTTOM_LEFT, SQUARE_BOTTOM_RIGHT, SQUARE_TOP_LEFT, SQUARE_TOP_RIGHT, STRIPE_BOTTOM, STRIPE_TOP, STRIPE_LEFT, STRIPE_RIGHT, STRIPE_CENTER, STRIPE_MIDDLE, STRIPE_DOWNRIGHT, STRIPE_DOWNLEFT, STRIPE_SMALL, CROSS, STRAIGHT_CROSS, TRIANGLE_BOTTOM, TRIANGLE_TOP, TRIANGLES_BOTTOM, TRIANGLES_TOP, DIAGONAL_LEFT, DIAGONAL_RIGHT, DIAGONAL_LEFT_MIRROR, DIAGONAL_RIGHT_MIRROR, CIRCLE_MIDDLE, RHOMBUS_MIDDLE, HALF_VERTICAL, HALF_HORIZONTAL, HALF_VERTICAL_MIRROR, HALF_HORIZONTAL_MIRROR, BORDER, CURLY_BORDER, CREEPER, GRADIENT, GRADIENT_UP, BRICKS, SKULL, FLOWER, MOJANG};
      TRIANGLE_BOTTOM("TRIANGLE_BOTTOM", 16, "triangle_bottom", "bt", "   ", " # ", "# #"),
      GRADIENT_UP("GRADIENT_UP", 34, "gradient_up", "gru", " # ", " # ", "# #"),
      TRIANGLES_TOP("TRIANGLES_TOP", 19, "triangles_top", "tts", " # ", "# #", "   "),
      STRIPE_RIGHT("STRIPE_RIGHT", 8, "stripe_right", "rs", "  #", "  #", "  #"),
      SKULL("SKULL", 36, "skull", "sku", new ItemStack(Items.skull, 1, 1)),
      STRIPE_DOWNLEFT("STRIPE_DOWNLEFT", 12, "stripe_downleft", "dls", "  #", " # ", "#  "),
      HALF_VERTICAL_MIRROR("HALF_VERTICAL_MIRROR", 28, "half_vertical_right", "vhr", " ##", " ##", " ##"),
      STRIPE_SMALL("STRIPE_SMALL", 13, "small_stripes", "ss", "# #", "# #", "   "),
      CIRCLE_MIDDLE("CIRCLE_MIDDLE", 24, "circle", "mc", "   ", " # ", "   "),
      RHOMBUS_MIDDLE("RHOMBUS_MIDDLE", 25, "rhombus", "mr", " # ", "# #", " # "),
      SQUARE_TOP_RIGHT("SQUARE_TOP_RIGHT", 4, "square_top_right", "tr", "  #", "   ", "   "),
      SQUARE_BOTTOM_RIGHT("SQUARE_BOTTOM_RIGHT", 2, "square_bottom_right", "br", "   ", "   ", "  #"),
      TRIANGLE_TOP("TRIANGLE_TOP", 17, "triangle_top", "tt", "# #", " # ", "   "),
      SQUARE_TOP_LEFT("SQUARE_TOP_LEFT", 3, "square_top_left", "tl", "#  ", "   ", "   "),
      BORDER("BORDER", 30, "border", "bo", "###", "# #", "###"),
      TRIANGLES_BOTTOM("TRIANGLES_BOTTOM", 18, "triangles_bottom", "bts", "   ", "# #", " # "),
      DIAGONAL_RIGHT("DIAGONAL_RIGHT", 21, "diagonal_up_right", "rd", "   ", "  #", " ##"),
      CURLY_BORDER("CURLY_BORDER", 31, "curly_border", "cbo", new ItemStack(Blocks.vine)),
      FLOWER("FLOWER", 37, "flower", "flo", new ItemStack(Blocks.red_flower, 1, BlockFlower.EnumFlowerType.OXEYE_DAISY.func_176968_b())),
      HALF_HORIZONTAL("HALF_HORIZONTAL", 27, "half_horizontal", "hh", "###", "###", "   "),
      CREEPER("CREEPER", 32, "creeper", "cre", new ItemStack(Items.skull, 1, 4)),
      STRIPE_TOP("STRIPE_TOP", 6, "stripe_top", "ts", "###", "   ", "   "),
      DIAGONAL_LEFT_MIRROR("DIAGONAL_LEFT_MIRROR", 22, "diagonal_up_left", "lud", "   ", "#  ", "## "),
      BRICKS("BRICKS", 35, "bricks", "bri", new ItemStack(Blocks.brick_block)),
      CROSS("CROSS", 14, "cross", "cr", "# #", " # ", "# #");

      private static final String __OBFID = "CL_00002043";
      STRIPE_BOTTOM("STRIPE_BOTTOM", 5, "stripe_bottom", "bs", "   ", "   ", "###");

      private String field_177285_O;
      STRIPE_CENTER("STRIPE_CENTER", 9, "stripe_center", "cs", " # ", " # ", " # "),
      MOJANG("MOJANG", 38, "mojang", "moj", new ItemStack(Items.golden_apple, 1, 1)),
      STRIPE_MIDDLE("STRIPE_MIDDLE", 10, "stripe_middle", "ms", "   ", "###", "   "),
      BASE("BASE", 0, "base", "b"),
      GRADIENT("GRADIENT", 33, "gradient", "gra", "# #", " # ", " # "),
      STRIPE_DOWNRIGHT("STRIPE_DOWNRIGHT", 11, "stripe_downright", "drs", "#  ", " # ", "  #");

      public String func_177273_b() {
         return this.field_177285_O;
      }

      public String[] func_177267_c() {
         return this.field_177291_P;
      }

      public ItemStack func_177272_f() {
         return this.field_177290_Q;
      }

      private EnumBannerPattern(String var3, int var4, String var5, String var6) {
         this.field_177291_P = new String[3];
         this.field_177284_N = var5;
         this.field_177285_O = var6;
      }

      public boolean func_177269_e() {
         return this.field_177290_Q != null;
      }

      private EnumBannerPattern(String var3, int var4, String var5, String var6, String var7, String var8, String var9) {
         this(var3, var4, var5, var6);
         this.field_177291_P[0] = var7;
         this.field_177291_P[1] = var8;
         this.field_177291_P[2] = var9;
      }

      private EnumBannerPattern(String var3, int var4, String var5, String var6, ItemStack var7) {
         this(var3, var4, var5, var6);
         this.field_177290_Q = var7;
      }

      public String func_177271_a() {
         return this.field_177284_N;
      }

      public static TileEntityBanner.EnumBannerPattern func_177268_a(String var0) {
         TileEntityBanner.EnumBannerPattern[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            TileEntityBanner.EnumBannerPattern var4 = var1[var3];
            if (var4.field_177285_O.equals(var0)) {
               return var4;
            }
         }

         return null;
      }

      public boolean func_177270_d() {
         return this.field_177290_Q != null || this.field_177291_P[0] != null;
      }
   }
}
