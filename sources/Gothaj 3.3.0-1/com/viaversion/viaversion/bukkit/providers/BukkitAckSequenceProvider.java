package com.viaversion.viaversion.bukkit.providers;

import com.viaversion.viaversion.ViaVersionPlugin;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.bukkit.tasks.protocol1_19to1_18_2.AckSequenceTask;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.provider.AckSequenceProvider;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage.SequenceStorage;

public final class BukkitAckSequenceProvider extends AckSequenceProvider {
   private final ViaVersionPlugin plugin;

   public BukkitAckSequenceProvider(ViaVersionPlugin plugin) {
      this.plugin = plugin;
   }

   @Override
   public void handleSequence(UserConnection connection, int sequence) {
      SequenceStorage sequenceStorage = connection.get(SequenceStorage.class);
      int previousSequence = sequenceStorage.setSequenceId(sequence);
      if (previousSequence == -1) {
         int serverProtocolVersion = connection.getProtocolInfo().getServerProtocolVersion();
         long delay = serverProtocolVersion > ProtocolVersion.v1_8.getVersion() && serverProtocolVersion < ProtocolVersion.v1_14.getVersion() ? 2L : 1L;
         if (this.plugin.isEnabled()) {
            this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new AckSequenceTask(connection, sequenceStorage), delay);
         }
      }
   }
}
