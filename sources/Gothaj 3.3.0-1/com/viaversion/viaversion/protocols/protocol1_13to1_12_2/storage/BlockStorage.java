package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import com.viaversion.viaversion.libs.flare.SyncMap;
import java.util.Map;

public class BlockStorage implements StorableObject {
   private static final IntSet WHITELIST = new IntOpenHashSet(46, 0.99F);
   private final Map<Position, BlockStorage.ReplacementData> blocks = SyncMap.hashmap();

   public void store(Position position, int block) {
      this.store(position, block, -1);
   }

   public void store(Position position, int block, int replacementId) {
      if (WHITELIST.contains(block)) {
         this.blocks.put(position, new BlockStorage.ReplacementData(block, replacementId));
      }
   }

   public boolean isWelcome(int block) {
      return WHITELIST.contains(block);
   }

   public boolean contains(Position position) {
      return this.blocks.containsKey(position);
   }

   public BlockStorage.ReplacementData get(Position position) {
      return this.blocks.get(position);
   }

   public BlockStorage.ReplacementData remove(Position position) {
      return this.blocks.remove(position);
   }

   static {
      WHITELIST.add(5266);

      for (int i = 0; i < 16; i++) {
         WHITELIST.add(972 + i);
      }

      for (int i = 0; i < 20; i++) {
         WHITELIST.add(6854 + i);
      }

      for (int i = 0; i < 4; i++) {
         WHITELIST.add(7110 + i);
      }

      for (int i = 0; i < 5; i++) {
         WHITELIST.add(5447 + i);
      }
   }

   public static final class ReplacementData {
      private final int original;
      private int replacement;

      public ReplacementData(int original, int replacement) {
         this.original = original;
         this.replacement = replacement;
      }

      public int getOriginal() {
         return this.original;
      }

      public int getReplacement() {
         return this.replacement;
      }

      public void setReplacement(int replacement) {
         this.replacement = replacement;
      }
   }
}
