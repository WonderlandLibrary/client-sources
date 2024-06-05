package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSnooper$List;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;

public class GuiSnooper extends GuiScreen {
   private final GuiScreen field_146608_a;
   private final GameSettings game_settings_2;
   private final List field_146604_g = Lists.newArrayList();
   private final List field_146609_h = Lists.newArrayList();
   private String field_146610_i;
   private String[] field_146607_r;
   private GuiSnooper$List field_146606_s;
   private GuiButton field_146605_t;

   public GuiSnooper(GuiScreen var1, GameSettings var2) {
      this.field_146608_a = var1;
      this.game_settings_2 = var2;
   }

   public void initGui() {
      this.field_146610_i = I18n.format("options.snooper.title", new Object[0]);
      String var1 = I18n.format("options.snooper.desc", new Object[0]);
      ArrayList var2 = Lists.newArrayList();

      for(Object var4 : this.fontRendererObj.listFormattedStringToWidth(var1, this.width - 30)) {
         var2.add((String)var4);
      }

      this.field_146607_r = (String[])var2.toArray(new String[var2.size()]);
      this.field_146604_g.clear();
      this.field_146609_h.clear();
      this.buttonList.add(this.field_146605_t = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.b(GameSettings$Options.SNOOPER_ENABLED)));
      this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done", new Object[0])));
      boolean var6 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;

      for(Entry var5 : (new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
         this.field_146604_g.add("C " + (String)var5.getKey());
         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var5.getValue(), this.width - 220));
      }

      for(Entry var9 : (new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet()) {
         this.field_146604_g.add("S " + (String)var9.getKey());
         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var9.getValue(), this.width - 220));
      }

      this.field_146606_s = new GuiSnooper$List(this);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146606_s.handleMouseInput();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 2) {
            this.game_settings_2.saveOptions();
            this.game_settings_2.saveOptions();
            this.mc.displayGuiScreen(this.field_146608_a);
         }

         if(var1.id == 1) {
            this.game_settings_2.setOptionValue(GameSettings$Options.SNOOPER_ENABLED, 1);
            this.field_146605_t.displayString = this.game_settings_2.b(GameSettings$Options.SNOOPER_ENABLED);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.field_146606_s.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
      int var4 = 22;

      for(String var8 : this.field_146607_r) {
         this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var4, 8421504);
         var4 += this.fontRendererObj.f();
      }

      super.drawScreen(var1, var2, var3);
   }

   static List access$000(GuiSnooper var0) {
      return var0.field_146604_g;
   }

   static List access$100(GuiSnooper var0) {
      return var0.field_146609_h;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
