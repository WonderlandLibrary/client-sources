package com.viaversion.viaversion.api.legacy.bossbar;

public enum BossFlag {
   DARKEN_SKY(1),
   PLAY_BOSS_MUSIC(2);

   private final int id;

   private BossFlag(int var3) {
      this.id = var3;
   }

   public int getId() {
      return this.id;
   }
}
