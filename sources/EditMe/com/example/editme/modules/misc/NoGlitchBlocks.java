package com.example.editme.modules.misc;

import com.example.editme.events.EventPlayerDestroyBlock;
import com.example.editme.events.EventWorldSetBlockState;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;

@Module.Info(
   name = "NoGlitchBlocks",
   category = Module.Category.MISC,
   description = "i doubt this module works"
)
public class NoGlitchBlocks extends Module {
   private Setting place = this.register(SettingsManager.b("Place", true));
   @EventHandler
   private Listener OnSetBlockState = new Listener(this::lambda$new$1, new Predicate[0]);
   private Setting destroy = this.register(SettingsManager.b("Destroy", true));
   @EventHandler
   private Listener OnPlayerDestroyBlock = new Listener(this::lambda$new$0, new Predicate[0]);

   private void lambda$new$0(EventPlayerDestroyBlock var1) {
      if ((Boolean)this.destroy.getValue()) {
         var1.cancel();
      }
   }

   private void lambda$new$1(EventWorldSetBlockState var1) {
      if ((Boolean)this.place.getValue()) {
         if (var1.Flags != 3) {
            var1.cancel();
         }

      }
   }
}
