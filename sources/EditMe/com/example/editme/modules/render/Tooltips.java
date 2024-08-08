package com.example.editme.modules.render;

import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.setting.SettingsManager;
import com.example.editme.util.tooltips.Tooltip;
import com.example.editme.util.tooltips.TooltipRenderer;
import com.example.editme.util.tooltips.TooltipsUtil;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;

@Module.Info(
   name = "Tooltips",
   category = Module.Category.RENDER
)
public class Tooltips extends Module {
   private Setting maxTooltips = this.register(SettingsManager.i("Max Tooltips", 8));
   private LinkedList tooltips = new LinkedList();

   public void onWorldRender(RenderEvent var1) {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         Iterator var2 = this.tooltips.iterator();

         while(var2.hasNext()) {
            Tooltip var3 = (Tooltip)var2.next();
            TooltipRenderer.renderTooltip(var3, (double)var1.getPartialTicks());
         }

      }
   }

   public void onUpdate() {
      if (mc.field_71439_g != null) {
         this.tooltips.removeIf(Objects::isNull);
         this.tooltips.removeIf(Tooltip::isDead);
         this.tooltips.forEach(Tooltip::tick);
         Optional var1 = TooltipsUtil.getMouseOver();
         if (var1.isPresent()) {
            boolean var2 = true;
            EntityItem var3 = (EntityItem)var1.get();
            Iterator var4 = this.tooltips.iterator();

            while(var4.hasNext()) {
               Tooltip var5 = (Tooltip)var4.next();
               if (var5.getEntity() == var3) {
                  var2 = !var5.reset();
               }
            }

            if (var2) {
               this.tooltips.addFirst(new Tooltip(Minecraft.func_71410_x().field_71439_g, var3));
            }
         }

         for(int var6 = (Integer)this.maxTooltips.getValue(); var6 < this.tooltips.size(); ++var6) {
            ((Tooltip)this.tooltips.get(var6)).forceFade();
         }

      }
   }
}
