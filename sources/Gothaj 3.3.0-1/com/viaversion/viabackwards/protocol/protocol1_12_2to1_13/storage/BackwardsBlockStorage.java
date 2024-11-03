package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BackwardsBlockStorage implements StorableObject {
   private static final IntSet WHITELIST = new IntOpenHashSet(779);
   private final Map<Position, Integer> blocks = new ConcurrentHashMap<>();

   public void checkAndStore(Position position, int block) {
      if (!WHITELIST.contains(block)) {
         this.blocks.remove(position);
      } else {
         this.blocks.put(position, block);
      }
   }

   public boolean isWelcome(int block) {
      return WHITELIST.contains(block);
   }

   public Integer get(Position position) {
      return this.blocks.get(position);
   }

   public int remove(Position position) {
      return this.blocks.remove(position);
   }

   public void clear() {
      this.blocks.clear();
   }

   public Map<Position, Integer> getBlocks() {
      return this.blocks;
   }

   static {
      for (int i = 5265; i <= 5286; i++) {
         WHITELIST.add(i);
      }

      for (int i = 0; i < 256; i++) {
         WHITELIST.add(748 + i);
      }

      for (int i = 6854; i <= 7173; i++) {
         WHITELIST.add(i);
      }

      WHITELIST.add(1647);

      for (int i = 5447; i <= 5566; i++) {
         WHITELIST.add(i);
      }

      for (int i = 1028; i <= 1039; i++) {
         WHITELIST.add(i);
      }

      for (int i = 1047; i <= 1082; i++) {
         WHITELIST.add(i);
      }

      for (int i = 1099; i <= 1110; i++) {
         WHITELIST.add(i);
      }
   }
}
