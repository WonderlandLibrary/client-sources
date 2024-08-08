package com.example.editme.modules.misc;

import com.example.editme.events.PacketEvent;
import com.example.editme.modules.Module;
import java.util.Iterator;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerListItem.Action;
import net.minecraft.network.play.server.SPacketPlayerListItem.AddPlayerData;
import net.minecraft.util.text.TextComponentString;

@Module.Info(
   name = "AntiRodarg",
   category = Module.Category.MISC
)
public class AntiRodarg extends Module {
   @EventHandler
   private Listener recListener = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(PacketEvent.Receive var1) {
      if (this.isEnabled() && var1.getPacket() instanceof SPacketPlayerListItem && ((SPacketPlayerListItem)var1.getPacket()).func_179768_b() == Action.ADD_PLAYER) {
         SPacketPlayerListItem var2 = (SPacketPlayerListItem)var1.getPacket();
         Iterator var3 = var2.func_179767_a().iterator();

         while(var3.hasNext()) {
            AddPlayerData var4 = (AddPlayerData)var3.next();
            if (var4.func_179962_a().getName().equalsIgnoreCase("Rodarg")) {
               mc.field_71453_ak.func_150718_a(new TextComponentString("YOU HAVE BEEN BACKDOORED BY REMO GRADO!!!"));
            }
         }
      }

   }
}
