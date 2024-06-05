package com.viaversion.viaversion.protocols.protocol1_9to1_8.chat;

public enum GameMode {
   SURVIVAL(0, "Survival Mode"),
   CREATIVE(1, "Creative Mode"),
   ADVENTURE(2, "Adventure Mode"),
   SPECTATOR(3, "Spectator Mode");

   private final int id;
   private final String text;
   private static int[] b;

   private GameMode(int var3, String var4) {
      this.id = var3;
      this.text = var4;
   }

   public int getId() {
      return this.id;
   }

   public String getText() {
      return this.text;
   }

   public static GameMode getById(int var0) {
      b();
      GameMode[] var2 = values();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         GameMode var5 = var2[var4];
         if(var5.getId() == var0) {
            return var5;
         }

         ++var4;
      }

      return null;
   }

   static {
      b(new int[3]);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }
}
