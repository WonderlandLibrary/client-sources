package com.example.editme.modules.misc;

import com.example.editme.events.GuiScreenEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;

@Module.Info(
   name = "AutoRespawn",
   description = "Respawn utility",
   category = Module.Category.MISC
)
public class AutoRespawn extends Module {
   @EventHandler
   public Listener listener = new Listener(this::lambda$new$0, new Predicate[0]);
   private Setting deathCoords = this.register(SettingsManager.b("DeathCoords", false));
   private Setting antiGlitchScreen = this.register(SettingsManager.b("Anti Glitch Screen", true));
   private Setting respawn = this.register(SettingsManager.b("Respawn", true));

   private void lambda$new$0(GuiScreenEvent.Displayed var1) {
      if (var1.getScreen() instanceof GuiGameOver) {
         if ((Boolean)this.deathCoords.getValue() && mc.field_71439_g.func_110143_aJ() <= 0.0F) {
            this.sendNotification(String.format("You died at x %d y %d z %d", (int)mc.field_71439_g.field_70165_t, (int)mc.field_71439_g.field_70163_u, (int)mc.field_71439_g.field_70161_v));
         }

         if ((Boolean)this.respawn.getValue() || (Boolean)this.antiGlitchScreen.getValue() && mc.field_71439_g.func_110143_aJ() > 0.0F) {
            mc.field_71439_g.func_71004_bE();
            mc.func_147108_a((GuiScreen)null);
         }

      }
   }
}
