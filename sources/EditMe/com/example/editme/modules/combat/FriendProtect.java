package com.example.editme.modules.combat;

import com.example.editme.events.ClientPlayerAttackEvent;
import com.example.editme.modules.Module;
import com.example.editme.util.client.Friends;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;

@Module.Info(
   name = "FriendProtect",
   category = Module.Category.COMBAT,
   description = "Prevents you from hitting friends"
)
public class FriendProtect extends Module {
   @EventHandler
   Listener onPacketEventSend = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(ClientPlayerAttackEvent var1) {
      if (!this.isDisabled()) {
         Entity var2 = mc.field_71476_x.field_72308_g;
         if (var2 instanceof EntityOtherPlayerMP && Friends.isFriend(var2.func_70005_c_())) {
            var1.cancel();
         }

      }
   }
}
