package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.task;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorderEmulator;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import java.util.logging.Level;

public class WorldBorderUpdateTask implements Runnable {
   public static final int VIEW_DISTANCE = 16;

   @Override
   public void run() {
      for (UserConnection connection : Via.getManager().getConnectionManager().getConnections()) {
         WorldBorderEmulator worldBorderEmulatorTracker = connection.get(WorldBorderEmulator.class);
         if (worldBorderEmulatorTracker.isInit()) {
            PlayerSessionStorage playerSession = connection.get(PlayerSessionStorage.class);
            double radius = worldBorderEmulatorTracker.getSize() / 2.0;

            for (WorldBorderEmulator.Side side : WorldBorderEmulator.Side.values()) {
               double d;
               double pos;
               double center;
               if (side.modX != 0) {
                  pos = playerSession.getPosZ();
                  center = worldBorderEmulatorTracker.getZ();
                  d = Math.abs(worldBorderEmulatorTracker.getX() + radius * (double)side.modX - playerSession.getPosX());
               } else {
                  center = worldBorderEmulatorTracker.getX();
                  pos = playerSession.getPosX();
                  d = Math.abs(worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ - playerSession.getPosZ());
               }

               if (!(d >= 16.0)) {
                  double r = Math.sqrt(256.0 - d * d);
                  double minH = Math.ceil(pos - r);
                  double maxH = Math.floor(pos + r);
                  double minV = Math.ceil(playerSession.getPosY() - r);
                  double maxV = Math.floor(playerSession.getPosY() + r);
                  if (minH < center - radius) {
                     minH = Math.ceil(center - radius);
                  }

                  if (maxH > center + radius) {
                     maxH = Math.floor(center + radius);
                  }

                  if (minV < 0.0) {
                     minV = 0.0;
                  }

                  double centerH = (minH + maxH) / 2.0;
                  double centerV = (minV + maxV) / 2.0;
                  double particleOffset = 2.5;
                  PacketWrapper spawnParticle = PacketWrapper.create(ClientboundPackets1_8.SPAWN_PARTICLE, connection);
                  spawnParticle.write(Type.STRING, ViaRewind.getConfig().getWorldBorderParticle());
                  spawnParticle.write(Type.FLOAT, (float)(side.modX != 0 ? worldBorderEmulatorTracker.getX() + radius * (double)side.modX : centerH));
                  spawnParticle.write(Type.FLOAT, (float)centerV);
                  spawnParticle.write(Type.FLOAT, (float)(side.modX == 0 ? worldBorderEmulatorTracker.getZ() + radius * (double)side.modZ : centerH));
                  spawnParticle.write(Type.FLOAT, (float)(side.modX != 0 ? 0.0 : (maxH - minH) / particleOffset));
                  spawnParticle.write(Type.FLOAT, (float)((maxV - minV) / particleOffset));
                  spawnParticle.write(Type.FLOAT, (float)(side.modX == 0 ? 0.0 : (maxH - minH) / particleOffset));
                  spawnParticle.write(Type.FLOAT, 0.0F);
                  spawnParticle.write(Type.INT, (int)Math.floor((maxH - minH) * (maxV - minV) * 0.5));

                  try {
                     spawnParticle.send(Protocol1_7_6_10To1_8.class, true);
                  } catch (Exception var35) {
                     ViaRewind.getPlatform().getLogger().log(Level.SEVERE, "Failed to send world border particle", (Throwable)var35);
                  }
               }
            }
         }
      }
   }
}
