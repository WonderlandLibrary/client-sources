package com.example.editme.modules.player;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(
   name = "Blink",
   category = Module.Category.PLAYER
)
public class Blink extends Module {
   private EntityOtherPlayerMP clonedPlayer;
   Queue packets = new LinkedList();
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$0, new Predicate[0]);

   protected void onDisable() {
      while(!this.packets.isEmpty()) {
         mc.field_71439_g.field_71174_a.func_147297_a((Packet)this.packets.poll());
      }

      EntityPlayerSP var1 = mc.field_71439_g;
      if (var1 != null) {
         mc.field_71441_e.func_73028_b(-100);
         this.clonedPlayer = null;
      }

   }

   protected void onEnable() {
      if (mc.field_71439_g != null) {
         this.clonedPlayer = new EntityOtherPlayerMP(mc.field_71441_e, mc.func_110432_I().func_148256_e());
         this.clonedPlayer.func_82149_j(mc.field_71439_g);
         this.clonedPlayer.field_70759_as = mc.field_71439_g.field_70759_as;
         mc.field_71441_e.func_73027_a(-100, this.clonedPlayer);
      }

   }

   public String getHudInfo() {
      return String.valueOf(this.packets.size());
   }

   private void lambda$new$0(PacketEvent.Send var1) {
      if (this.isEnabled() && var1.getPacket() instanceof CPacketPlayer) {
         var1.cancel();
         this.packets.add((CPacketPlayer)var1.getPacket());
      }

   }
}
