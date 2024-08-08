package com.example.editme.modules.client;

import com.example.editme.commands.Command;
import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.client.CPacketChatMessage;

@Module.Info(
   name = "ChatSuffix",
   category = Module.Category.CLIENT
)
public class ChatSuffix extends Module {
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(PacketEvent.Send var1) {
      if (this.isEnabled() && var1.getPacket() instanceof CPacketChatMessage) {
         CPacketChatMessage var2 = (CPacketChatMessage)var1.getPacket();
         if (var2.func_149439_c().startsWith("/") || var2.func_149439_c().startsWith(Command.getCommandPrefix())) {
            return;
         }

         if (var2.func_149439_c().toLowerCase().contains("salhack")) {
            var2.field_149440_a = "How do I edit my chat suffix?";
         }

         var2.field_149440_a = String.valueOf((new StringBuilder()).append(var2.func_149439_c()).append(" ⏐ edit me"));
      }

   }
}
