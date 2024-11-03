package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.Pair;
import java.util.Objects;

public class SpawnPositionStorage implements StorableObject {
   public static final Pair<Position, Float> DEFAULT_SPAWN_POSITION = Pair.of(new Position(8, 64, 8), 0.0F);
   private Pair<Position, Float> spawnPosition;
   private String dimension;

   public Pair<Position, Float> getSpawnPosition() {
      return this.spawnPosition;
   }

   public void setSpawnPosition(Pair<Position, Float> spawnPosition) {
      this.spawnPosition = spawnPosition;
   }

   public void setDimension(String dimension) {
      boolean changed = !Objects.equals(this.dimension, dimension);
      this.dimension = dimension;
      if (changed) {
         this.spawnPosition = DEFAULT_SPAWN_POSITION;
      }
   }
}
