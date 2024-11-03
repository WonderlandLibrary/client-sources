package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockConnectionStorage;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketBlockConnectionProvider extends BlockConnectionProvider {
   @Override
   public void storeBlock(UserConnection connection, int x, int y, int z, int blockState) {
      connection.get(BlockConnectionStorage.class).store(x, y, z, blockState);
   }

   @Override
   public void removeBlock(UserConnection connection, int x, int y, int z) {
      connection.get(BlockConnectionStorage.class).remove(x, y, z);
   }

   @Override
   public int getBlockData(UserConnection connection, int x, int y, int z) {
      return connection.get(BlockConnectionStorage.class).get(x, y, z);
   }

   @Override
   public void clearStorage(UserConnection connection) {
      connection.get(BlockConnectionStorage.class).clear();
   }

   @Override
   public void modifiedBlock(UserConnection connection, Position position) {
      connection.get(BlockConnectionStorage.class).markModified(position);
   }

   @Override
   public void unloadChunk(UserConnection connection, int x, int z) {
      connection.get(BlockConnectionStorage.class).unloadChunk(x, z);
   }

   @Override
   public void unloadChunkSection(UserConnection connection, int chunkX, int chunkY, int chunkZ) {
      connection.get(BlockConnectionStorage.class).unloadSection(chunkX, chunkY, chunkZ);
   }

   @Override
   public boolean storesBlocks(UserConnection connection, @Nullable Position pos) {
      return pos != null && connection != null ? !connection.get(BlockConnectionStorage.class).recentlyModified(pos) : true;
   }

   @Override
   public UserBlockData forUser(UserConnection connection) {
      BlockConnectionStorage storage = connection.get(BlockConnectionStorage.class);
      return (x, y, z) -> storage.get(x, y, z);
   }
}
