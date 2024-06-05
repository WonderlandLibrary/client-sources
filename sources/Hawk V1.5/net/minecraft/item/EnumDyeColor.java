package net.minecraft.item;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IStringSerializable;

public enum EnumDyeColor implements IStringSerializable {
   LIGHT_BLUE("LIGHT_BLUE", 3, 3, 12, "light_blue", "lightBlue", MapColor.lightBlueColor, EnumChatFormatting.BLUE);

   private final MapColor field_176784_w;
   RED("RED", 14, 14, 1, "red", "red", MapColor.redColor, EnumChatFormatting.DARK_RED),
   GRAY("GRAY", 7, 7, 8, "gray", "gray", MapColor.grayColor, EnumChatFormatting.DARK_GRAY),
   BROWN("BROWN", 12, 12, 3, "brown", "brown", MapColor.brownColor, EnumChatFormatting.GOLD),
   MAGENTA("MAGENTA", 2, 2, 13, "magenta", "magenta", MapColor.magentaColor, EnumChatFormatting.AQUA);

   private static final EnumDyeColor[] ENUM$VALUES = new EnumDyeColor[]{WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, SILVER, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK};
   private final String field_176785_v;
   BLUE("BLUE", 11, 11, 4, "blue", "blue", MapColor.blueColor, EnumChatFormatting.DARK_BLUE),
   GREEN("GREEN", 13, 13, 2, "green", "green", MapColor.greenColor, EnumChatFormatting.DARK_GREEN),
   LIME("LIME", 5, 5, 10, "lime", "lime", MapColor.limeColor, EnumChatFormatting.GREEN),
   WHITE("WHITE", 0, 0, 15, "white", "white", MapColor.snowColor, EnumChatFormatting.WHITE),
   PURPLE("PURPLE", 10, 10, 5, "purple", "purple", MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE),
   YELLOW("YELLOW", 4, 4, 11, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW);

   private static final EnumDyeColor[] $VALUES = new EnumDyeColor[]{WHITE, ORANGE, MAGENTA, LIGHT_BLUE, YELLOW, LIME, PINK, GRAY, SILVER, CYAN, PURPLE, BLUE, BROWN, GREEN, RED, BLACK};
   private static final EnumDyeColor[] field_176789_r = new EnumDyeColor[values().length];
   CYAN("CYAN", 9, 9, 6, "cyan", "cyan", MapColor.cyanColor, EnumChatFormatting.DARK_AQUA),
   BLACK("BLACK", 15, 15, 0, "black", "black", MapColor.blackColor, EnumChatFormatting.BLACK);

   private final EnumChatFormatting field_176793_x;
   ORANGE("ORANGE", 1, 1, 14, "orange", "orange", MapColor.adobeColor, EnumChatFormatting.GOLD),
   SILVER("SILVER", 8, 8, 7, "silver", "silver", MapColor.silverColor, EnumChatFormatting.GRAY),
   PINK("PINK", 6, 6, 9, "pink", "pink", MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE);

   private static final EnumDyeColor[] field_176790_q = new EnumDyeColor[values().length];
   private final int field_176788_s;
   private static final String __OBFID = "CL_00002180";
   private final int field_176787_t;
   private final String field_176786_u;

   private EnumDyeColor(String var3, int var4, int var5, int var6, String var7, String var8, MapColor var9, EnumChatFormatting var10) {
      this.field_176788_s = var5;
      this.field_176787_t = var6;
      this.field_176786_u = var7;
      this.field_176785_v = var8;
      this.field_176784_w = var9;
      this.field_176793_x = var10;
   }

   public static EnumDyeColor func_176764_b(int var0) {
      if (var0 < 0 || var0 >= field_176790_q.length) {
         var0 = 0;
      }

      return field_176790_q[var0];
   }

   public MapColor func_176768_e() {
      return this.field_176784_w;
   }

   public int getDyeColorDamage() {
      return this.field_176787_t;
   }

   public static EnumDyeColor func_176766_a(int var0) {
      if (var0 < 0 || var0 >= field_176789_r.length) {
         var0 = 0;
      }

      return field_176789_r[var0];
   }

   public String func_176762_d() {
      return this.field_176785_v;
   }

   public int func_176765_a() {
      return this.field_176788_s;
   }

   static {
      EnumDyeColor[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumDyeColor var3 = var0[var2];
         field_176790_q[var3.func_176765_a()] = var3;
         field_176789_r[var3.getDyeColorDamage()] = var3;
      }

   }

   public String getName() {
      return this.field_176786_u;
   }

   public String toString() {
      return this.field_176785_v;
   }
}
