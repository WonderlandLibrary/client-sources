package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.util.client.Friends;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

@Module.Info(
   name = "ChatFilter",
   category = Module.Category.MISC
)
public class ChatFilter extends Module {
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(ClientChatReceivedEvent var1) {
      if (mc.field_71439_g != null) {
         if (this.isSpam(var1.getMessage().func_150260_c())) {
            var1.setCanceled(true);
         }

      }
   }

   private boolean isSpam(String var1) {
      if (var1.toLowerCase().contains(mc.field_71439_g.func_70005_c_().toLowerCase())) {
         return false;
      } else {
         Iterator var2 = ((ArrayList)Friends.friends.getValue()).iterator();

         Friends.Friend var3;
         do {
            if (!var2.hasNext()) {
               String[] var7 = new String[]{"discord", "creative", "salhack", "join", "play"};
               String[] var8 = var7;
               int var4 = var7.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  String var6 = var8[var5];
                  if (var1.toLowerCase().contains(var6)) {
                     return true;
                  }
               }

               return false;
            }

            var3 = (Friends.Friend)var2.next();
         } while(!var1.toLowerCase().contains(var3.getUsername().toLowerCase()));

         return false;
      }
   }
}
