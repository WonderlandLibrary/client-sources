package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

@Module.Info(
   name = "ChatMention",
   category = Module.Category.MISC
)
public class ChatMention extends Module {
   @EventHandler
   public Listener listener = new Listener(ChatMention::lambda$new$0, new Predicate[0]);

   private static void lambda$new$0(ClientChatReceivedEvent var0) {
      String[] var1 = new String[]{""};
      var1[0] = var0.getMessage().func_150254_d().replaceAll(String.valueOf((new StringBuilder()).append("(?i)").append(mc.field_71439_g.func_70005_c_())), String.valueOf((new StringBuilder()).append(TextFormatting.GREEN.toString()).append(mc.field_71439_g.func_70005_c_()).append(TextFormatting.RESET.toString())));
      TextComponentString var2 = new TextComponentString(var1[0]);
      var0.setMessage(var2);
   }
}
