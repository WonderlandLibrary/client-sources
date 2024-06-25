package cc.slack.features.modules.impl.render.hud.arraylist.impl;

import cc.slack.Slack;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.impl.render.hud.arraylist.IArraylist;
import cc.slack.utils.client.mc;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.font.MCFontRenderer;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.Gui;

public class Basic2ArrayList implements IArraylist {
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
      int y = 1;

      for(Iterator var3 = this.modules.iterator(); var3.hasNext(); y += mc.getFontRenderer().FONT_HEIGHT) {
         String module = (String)var3.next();
         Gui.drawRect((int)event.getWidth() - 1, y, (int)event.getWidth(), mc.getFontRenderer().FONT_HEIGHT, Color.WHITE.getRGB());
         Fonts.SFBold18.drawString(module, event.getWidth() - (float)Fonts.SFBold18.getStringWidth(module) - 4.0F, (float)y, Color.WHITE.getRGB());
      }

   }

   public String toString() {
      return "Basic 2";
   }
}
