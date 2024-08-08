package com.example.editme.modules.misc;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import net.minecraft.item.ItemPickaxe;

@Module.Info(
   name = "NoEntityTrace",
   category = Module.Category.MISC,
   description = "Blocks entities from stopping you from mining"
)
public class NoEntityTrace extends Module {
   private static NoEntityTrace INSTANCE;
   private Setting mode;

   public static boolean shouldBlock() {
      if (INSTANCE.isEnabled()) {
         switch((NoEntityTrace.TraceMode)INSTANCE.mode.getValue()) {
         case STATIC:
            return true;
         case DYNAMIC:
            if (mc.field_71442_b.field_78778_j) {
               return true;
            }
         case PICKAXE:
            if (mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemPickaxe) {
               return true;
            }
         }
      }

      return false;
   }

   public NoEntityTrace() {
      this.mode = this.register(SettingsManager.e("Mode", NoEntityTrace.TraceMode.PICKAXE));
      INSTANCE = this;
   }

   private static enum TraceMode {
      STATIC,
      DYNAMIC;

      private static final NoEntityTrace.TraceMode[] $VALUES = new NoEntityTrace.TraceMode[]{STATIC, DYNAMIC, PICKAXE};
      PICKAXE;
   }
}
