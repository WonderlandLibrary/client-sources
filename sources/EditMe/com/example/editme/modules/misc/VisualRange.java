package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.Friends;
import com.example.editme.util.setting.SettingsManager;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

@Module.Info(
   name = "VisualRange",
   description = "Says when people enter/leave visual range",
   category = Module.Category.MISC
)
public class VisualRange extends Module {
   private Setting leaving = this.register(SettingsManager.b("Leaving", false));
   private List knownPlayers;

   public void onEnable() {
      this.knownPlayers = new ArrayList();
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         ArrayList var1 = new ArrayList();
         Iterator var2 = mc.field_71441_e.func_72910_y().iterator();

         while(var2.hasNext()) {
            Entity var3 = (Entity)var2.next();
            if (var3 instanceof EntityPlayer) {
               var1.add(var3.func_70005_c_());
            }
         }

         String var4;
         if (var1.size() > 0) {
            var2 = var1.iterator();

            while(var2.hasNext()) {
               var4 = (String)var2.next();
               if (!var4.equals(mc.field_71439_g.func_70005_c_()) && !this.knownPlayers.contains(var4)) {
                  this.knownPlayers.add(var4);
                  if (Friends.isFriend(var4)) {
                     this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN.toString()).append(var4).append(ChatFormatting.RESET.toString()).append(" entered visual range!")));
                  } else {
                     this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append(var4).append(ChatFormatting.RESET.toString()).append(" entered visual range!")));
                  }

                  return;
               }
            }
         }

         if (this.knownPlayers.size() > 0) {
            var2 = this.knownPlayers.iterator();

            while(var2.hasNext()) {
               var4 = (String)var2.next();
               if (!var1.contains(var4)) {
                  this.knownPlayers.remove(var4);
                  if ((Boolean)this.leaving.getValue()) {
                     if (Friends.isFriend(var4)) {
                        this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.GREEN.toString()).append(var4).append(ChatFormatting.RESET.toString()).append(" left visual range!")));
                     } else {
                        this.sendNotification(String.valueOf((new StringBuilder()).append(ChatFormatting.RED.toString()).append(var4).append(ChatFormatting.RESET.toString()).append(" left visual range!")));
                     }
                  }

                  return;
               }
            }
         }

      }
   }
}
