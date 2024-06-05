package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import java.util.Optional;
import net.amt;

public enum SoundSource {
   MASTER("master", 0),
   MUSIC("music", 1),
   RECORD("record", 2),
   WEATHER("weather", 3),
   BLOCK("block", 4),
   HOSTILE("hostile", 5),
   NEUTRAL("neutral", 6),
   PLAYER("player", 7),
   AMBIENT("ambient", 8),
   VOICE("voice", 9);

   private final String name;
   private final int id;

   private SoundSource(String var3, int var4) {
      this.name = var3;
      this.id = var4;
   }

   public static Optional findBySource(String var0) {
      amt.c();
      SoundSource[] var2 = values();
      int var3 = var2.length;
      int var4 = 0;
      if(var4 < var3) {
         SoundSource var5 = var2[var4];
         if(var5.name.equalsIgnoreCase(var0)) {
            return Optional.of(var5);
         }

         ++var4;
      }

      return Optional.empty();
   }

   public String getName() {
      return this.name;
   }

   public int getId() {
      return this.id;
   }
}
