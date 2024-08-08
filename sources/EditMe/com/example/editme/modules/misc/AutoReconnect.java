package com.example.editme.modules.misc;

import com.example.editme.events.GuiScreenEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;

@Module.Info(
   name = "AutoReconnect",
   description = "Automatically reconnects after being disconnected",
   category = Module.Category.MISC,
   alwaysListening = true
)
public class AutoReconnect extends Module {
   @EventHandler
   public Listener displayedListener = new Listener(this::lambda$new$1, new Predicate[0]);
   private static ServerData cServer;
   private Setting seconds = this.register(SettingsManager.integerBuilder("Seconds").withValue((int)5).withMinimum(0).build());
   @EventHandler
   public Listener closedListener = new Listener(AutoReconnect::lambda$new$0, new Predicate[0]);

   static ServerData access$100() {
      return cServer;
   }

   private void lambda$new$1(GuiScreenEvent.Displayed var1) {
      if (this.isEnabled() && var1.getScreen() instanceof GuiDisconnected && (cServer != null || mc.field_71422_O != null)) {
         var1.setScreen(new AutoReconnect.EditmeGuiDisconnected(this, (GuiDisconnected)var1.getScreen()));
      }

   }

   static Setting access$000(AutoReconnect var0) {
      return var0.seconds;
   }

   private static void lambda$new$0(GuiScreenEvent.Closed var0) {
      if (var0.getScreen() instanceof GuiConnecting) {
         cServer = mc.field_71422_O;
      }

   }

   private class EditmeGuiDisconnected extends GuiDisconnected {
      int millis;
      final AutoReconnect this$0;
      long cTime;

      public void func_73863_a(int var1, int var2, float var3) {
         super.func_73863_a(var1, var2, var3);
         long var4 = System.currentTimeMillis();
         this.millis = (int)((long)this.millis - (var4 - this.cTime));
         this.cTime = var4;
         String var6 = String.valueOf((new StringBuilder()).append("Reconnecting in ").append(Math.max(0.0D, Math.floor((double)this.millis / 100.0D) / 10.0D)).append("s"));
         this.field_146289_q.func_175065_a(var6, (float)(this.field_146294_l / 2 - this.field_146289_q.func_78256_a(var6) / 2), (float)(this.field_146295_m - 16), 16777215, true);
      }

      public EditmeGuiDisconnected(AutoReconnect var1, GuiDisconnected var2) {
         super(var2.field_146307_h, var2.field_146306_a, var2.field_146304_f);
         this.this$0 = var1;
         this.millis = (Integer)this.this$0.seconds.getValue() * 1000;
         this.cTime = System.currentTimeMillis();
      }

      public void func_73876_c() {
         if (this.millis <= 0) {
            this.field_146297_k.func_147108_a(new GuiConnecting(this.field_146307_h, this.field_146297_k, AutoReconnect.cServer == null ? this.field_146297_k.field_71422_O : AutoReconnect.cServer));
         }

      }
   }
}
