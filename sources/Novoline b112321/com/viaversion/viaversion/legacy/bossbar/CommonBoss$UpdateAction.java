package com.viaversion.viaversion.legacy.bossbar;

enum CommonBoss$UpdateAction {
   ADD(0),
   REMOVE(1),
   UPDATE_HEALTH(2),
   UPDATE_TITLE(3),
   UPDATE_STYLE(4),
   UPDATE_FLAGS(5);

   private final int id;
   private static final CommonBoss$UpdateAction[] $VALUES = new CommonBoss$UpdateAction[]{ADD, REMOVE, UPDATE_HEALTH, UPDATE_TITLE, UPDATE_STYLE, UPDATE_FLAGS};

   private CommonBoss$UpdateAction(int var3) {
      this.id = var3;
   }

   public int getId() {
      return this.id;
   }
}
