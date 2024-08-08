package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

@Module.Info(
   name = "ChatTimestamps",
   category = Module.Category.MISC
)
public class ChatTimestamps extends Module {
   @EventHandler
   public Listener listener = new Listener(ChatTimestamps::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(ClientChatReceivedEvent var0) {
      if (mc.field_71439_g != null) {
         TextComponentString var1 = new TextComponentString(String.valueOf((new StringBuilder()).append((new SimpleDateFormat("H:mm")).format(new Date())).append(" ")));
         var0.setMessage(var1.func_150257_a(var0.getMessage()));
      }
   }
}
