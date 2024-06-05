package com.viaversion.viaversion.protocols.protocol1_9to1_8.sounds;

public enum SoundCategory {
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

   private SoundCategory(String var3, int var4) {
      this.name = var3;
      this.id = var4;
   }

   public int getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }
}
