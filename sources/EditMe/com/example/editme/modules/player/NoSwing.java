package com.example.editme.modules.player;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketAnimation;

@Module.Info(
   name = "NoSwing",
   category = Module.Category.PLAYER,
   description = "Prevents arm swing animation server side"
)
public class NoSwing extends Module {
   @EventHandler
   public Listener listener = new Listener(NoSwing::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(PacketEvent.Send var0) {
      if (var0.getPacket() instanceof CPacketAnimation) {
         var0.cancel();
      }

   }
}
