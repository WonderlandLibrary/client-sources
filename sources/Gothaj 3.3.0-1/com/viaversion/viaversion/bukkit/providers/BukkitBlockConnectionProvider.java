package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.providers.BlockConnectionProvider;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class BukkitBlockConnectionProvider extends BlockConnectionProvider {
   private Chunk lastChunk;

   @Override
   public int getWorldBlockData(UserConnection user, int bx, int by, int bz) {
      UUID uuid = user.getProtocolInfo().getUuid();
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
         World world = player.getWorld();
         int x = bx >> 4;
         int z = bz >> 4;
         if (world.isChunkLoaded(x, z)) {
            Chunk c = this.getChunk(world, x, z);
            Block b = c.getBlock(bx, by, bz);
            return b.getTypeId() << 4 | b.getData();
         }
      }

      return 0;
   }

   public Chunk getChunk(World world, int x, int z) {
      return this.lastChunk != null && this.lastChunk.getWorld().equals(world) && this.lastChunk.getX() == x && this.lastChunk.getZ() == z
         ? this.lastChunk
         : (this.lastChunk = world.getChunkAt(x, z));
   }
}
