package com.example.editme.modules.movement;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketPlayer;

@Module.Info(
   name = "AntiHunger",
   category = Module.Category.MOVEMENT,
   description = "Lose hunger less fast. Might cause ghostblocks."
)
public class AntiHunger extends Module {
   @EventHandler
   public Listener packetListener = new Listener(AntiHunger::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(PacketEvent.Send var0) {
      if (var0.getPacket() instanceof CPacketPlayer) {
         ((CPacketPlayer)var0.getPacket()).field_149474_g = false;
      }

   }
}
