package cc.slack.features.modules.impl.render.hud.arraylist.impl;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.font.MCFontRenderer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BasicArrayList implements IArraylist {
   List<String> modules = new ArrayList();

   public void onUpdate(UpdateEvent event) {
      this.modules.clear();
      Iterator var2 = Slack.getInstance().getModuleManager().getModules().iterator();

      while(var2.hasNext()) {
         Module module = (Module)var2.next();
         if (module.isToggle()) {
            this.modules.add(module.getDisplayName());
         }
      }

      List var10000 = this.modules;
      MCFontRenderer var10001 = Fonts.apple18;
      var10001.getClass();
      var10000.sort(Comparator.comparingInt(var10001::getStringWidth));
      Collections.reverse(this.modules);
   }

   public void onRender(RenderEvent event) {
      int y = 3;

      for(Iterator var3 = this.modules.iterator(); var3.hasNext(); y += Fonts.apple18.getHeight() + 2) {
         String module = (String)var3.next();
         Fonts.apple18.drawStringWithShadow(module, event.getWidth() - (float)Fonts.apple18.getStringWidth(module) - 3.0F, (float)y, 5544391);
      }

   }

   public String toString() {
      return "Basic";
   }
}
