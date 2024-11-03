package com.viaversion.viaversion.protocols.protocol1_9to1_8.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.util.Pair;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class CommandBlockStorage implements StorableObject {
   private final Map<Pair<Integer, Integer>, Map<Position, CompoundTag>> storedCommandBlocks = new ConcurrentHashMap<>();
   private boolean permissions;

   public void unloadChunk(int x, int z) {
      Pair<Integer, Integer> chunkPos = new Pair<>(x, z);
      this.storedCommandBlocks.remove(chunkPos);
   }

   public void addOrUpdateBlock(Position position, CompoundTag tag) {
      Pair<Integer, Integer> chunkPos = this.getChunkCoords(position);
      if (!this.storedCommandBlocks.containsKey(chunkPos)) {
         this.storedCommandBlocks.put(chunkPos, new ConcurrentHashMap<>());
      }

      Map<Position, CompoundTag> blocks = this.storedCommandBlocks.get(chunkPos);
      if (!blocks.containsKey(position) || !blocks.get(position).equals(tag)) {
         blocks.put(position, tag);
      }
   }

   private Pair<Integer, Integer> getChunkCoords(Position position) {
      int chunkX = Math.floorDiv(position.x(), 16);
      int chunkZ = Math.floorDiv(position.z(), 16);
      return new Pair<>(chunkX, chunkZ);
   }

   public Optional<CompoundTag> getCommandBlock(Position position) {
      Pair<Integer, Integer> chunkCoords = this.getChunkCoords(position);
      Map<Position, CompoundTag> blocks = this.storedCommandBlocks.get(chunkCoords);
      if (blocks == null) {
         return Optional.empty();
      } else {
         CompoundTag tag = blocks.get(position);
         if (tag == null) {
            return Optional.empty();
         } else {
            tag = tag.copy();
            tag.put("powered", new ByteTag((byte)0));
            tag.put("auto", new ByteTag((byte)0));
            tag.put("conditionMet", new ByteTag((byte)0));
            return Optional.of(tag);
         }
      }
   }

   public void unloadChunks() {
      this.storedCommandBlocks.clear();
   }

   public boolean isPermissions() {
      return this.permissions;
   }

   public void setPermissions(boolean permissions) {
      this.permissions = permissions;
   }
}
