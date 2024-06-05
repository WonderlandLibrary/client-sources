package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

final class TranslatableRewriter1_16$ChatColor {
   private final String colorName;
   private final int rgb;
   private final int r;
   private final int g;
   private final int b;

   TranslatableRewriter1_16$ChatColor(String var1, int var2) {
      this.colorName = var1;
      this.rgb = var2;
      this.r = var2 >> 16 & 255;
      this.g = var2 >> 8 & 255;
      this.b = var2 & 255;
   }

   static int access$000(TranslatableRewriter1_16$ChatColor var0) {
      return var0.rgb;
   }

   static String access$100(TranslatableRewriter1_16$ChatColor var0) {
      return var0.colorName;
   }

   static int access$200(TranslatableRewriter1_16$ChatColor var0) {
      return var0.r;
   }

   static int access$300(TranslatableRewriter1_16$ChatColor var0) {
      return var0.g;
   }

   static int access$400(TranslatableRewriter1_16$ChatColor var0) {
      return var0.b;
   }
}
