package com.example.editme.modules.render;

import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;

@Module.Info(
   name = "AntiOverlay",
   category = Module.Category.RENDER
)
public class AntiOverlay extends Module {
   private Setting fire = this.register(SettingsManager.booleanBuilder("Flame").withValue(true).build());
   private Setting lava = this.register(SettingsManager.booleanBuilder("Lava").withValue(true).build());
   public Setting effects = this.register(SettingsManager.booleanBuilder("Effects").withValue(true).build());
   @EventHandler
   public Listener renderBlockOverlayEventListener = new Listener(this::lambda$new$0, new Predicate[0]);
   @EventHandler
   public Listener renderFogDensityEvent = new Listener(this::lambda$new$1, new Predicate[0]);
   private Setting blocks = this.register(SettingsManager.booleanBuilder("Blocks").withValue(true).build());
   public Setting totems = this.register(SettingsManager.booleanBuilder("Pops").withValue(true).build());
   private Setting water = this.register(SettingsManager.booleanBuilder("Water").withValue(true).build());

   private void lambda$new$0(RenderBlockOverlayEvent var1) {
      boolean var2 = false;
      if (this.isEnabled()) {
         switch(var1.getOverlayType()) {
         case FIRE:
            if ((Boolean)this.fire.getValue()) {
               var2 = true;
            }
            break;
         case WATER:
            if ((Boolean)this.water.getValue()) {
               var2 = true;
            }
            break;
         case BLOCK:
            if ((Boolean)this.blocks.getValue()) {
               var2 = true;
            }
         }

         var1.setCanceled(var2);
      }
   }

   public void onUpdate() {
      if ((Boolean)this.effects.getValue()) {
         mc.field_71439_g.func_184596_c(Potion.func_180142_b("blindness"));
         mc.field_71439_g.func_184596_c(Potion.func_180142_b("nausea"));
      }

   }

   private void lambda$new$1(FogDensity var1) {
      if (var1.getState().func_185904_a().equals(Material.field_151587_i) && (Boolean)this.lava.getValue()) {
         var1.setDensity(0.0F);
         var1.setCanceled(true);
      }

   }
}
