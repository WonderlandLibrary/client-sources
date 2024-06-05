package net.minecraft.server.management;

import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S44PacketWorldBorder$Action;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.world.border.IBorderListener;
import net.minecraft.world.border.WorldBorder;

class ServerConfigurationManager$1 implements IBorderListener {
   final ServerConfigurationManager this$0;

   ServerConfigurationManager$1(ServerConfigurationManager var1) {
      this.this$0 = var1;
   }

   public void onSizeChanged(WorldBorder var1, double var2) {
      this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder$Action.SET_SIZE));
   }

   public void onTransitionStarted(WorldBorder var1, double var2, double var4, long var6) {
      this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder$Action.LERP_SIZE));
   }

   public void onCenterChanged(WorldBorder var1, double var2, double var4) {
      this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder$Action.SET_CENTER));
   }

   public void onWarningTimeChanged(WorldBorder var1, int var2) {
      this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder$Action.SET_WARNING_TIME));
   }

   public void onWarningDistanceChanged(WorldBorder var1, int var2) {
      this.this$0.sendPacketToAllPlayers(new S44PacketWorldBorder(var1, S44PacketWorldBorder$Action.SET_WARNING_BLOCKS));
   }

   public void onDamageAmountChanged(WorldBorder var1, double var2) {
   }

   public void onDamageBufferChanged(WorldBorder var1, double var2) {
   }
}
