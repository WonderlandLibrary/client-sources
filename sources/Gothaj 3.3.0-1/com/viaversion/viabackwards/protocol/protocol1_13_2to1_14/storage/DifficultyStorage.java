package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class DifficultyStorage implements StorableObject {
   private byte difficulty;

   public byte getDifficulty() {
      return this.difficulty;
   }

   public void setDifficulty(byte difficulty) {
      this.difficulty = difficulty;
   }
}
