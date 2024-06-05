package cc.novoline.utils.fonts.impl;

import cc.novoline.utils.fonts.impl.SimpleFontRenderer$1;

class SimpleFontRenderer$CharData {
   private int width;
   private int height;
   private int storedX;
   private int storedY;

   private SimpleFontRenderer$CharData() {
   }

   SimpleFontRenderer$CharData(SimpleFontRenderer$1 var1) {
      this();
   }

   static int access$102(SimpleFontRenderer$CharData var0, int var1) {
      return var0.width = var1;
   }

   static int access$202(SimpleFontRenderer$CharData var0, int var1) {
      return var0.height = var1;
   }

   static int access$100(SimpleFontRenderer$CharData var0) {
      return var0.width;
   }

   static int access$200(SimpleFontRenderer$CharData var0) {
      return var0.height;
   }

   static int access$302(SimpleFontRenderer$CharData var0, int var1) {
      return var0.storedX = var1;
   }

   static int access$402(SimpleFontRenderer$CharData var0, int var1) {
      return var0.storedY = var1;
   }

   static int access$300(SimpleFontRenderer$CharData var0) {
      return var0.storedX;
   }

   static int access$400(SimpleFontRenderer$CharData var0) {
      return var0.storedY;
   }
}
