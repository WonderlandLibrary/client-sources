package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiSnooper extends GuiScreen {
   private static final String __OBFID = "CL_00000714";
   private GuiSnooper.List field_146606_s;
   private final GuiScreen field_146608_a;
   private final java.util.List field_146609_h = Lists.newArrayList();
   private final GameSettings game_settings_2;
   private String[] field_146607_r;
   private GuiButton field_146605_t;
   private String field_146610_i;
   private final java.util.List field_146604_g = Lists.newArrayList();

   public void initGui() {
      this.field_146610_i = I18n.format("options.snooper.title");
      String var1 = I18n.format("options.snooper.desc");
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.fontRendererObj.listFormattedStringToWidth(var1, this.width - 30).iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         var2.add(var4);
      }

      this.field_146607_r = (String[])var2.toArray(new String[0]);
      this.field_146604_g.clear();
      this.field_146609_h.clear();
      this.buttonList.add(this.field_146605_t = new GuiButton(1, this.width / 2 - 152, this.height - 30, 150, 20, this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED)));
      this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height - 30, 150, 20, I18n.format("gui.done")));
      boolean var7 = this.mc.getIntegratedServer() != null && this.mc.getIntegratedServer().getPlayerUsageSnooper() != null;
      Iterator var5 = (new TreeMap(this.mc.getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();

      Entry var6;
      while(var5.hasNext()) {
         var6 = (Entry)var5.next();
         this.field_146604_g.add(String.valueOf((new StringBuilder(String.valueOf(var7 ? "C " : ""))).append((String)var6.getKey())));
         this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var6.getValue(), this.width - 220));
      }

      if (var7) {
         var5 = (new TreeMap(this.mc.getIntegratedServer().getPlayerUsageSnooper().getCurrentStats())).entrySet().iterator();

         while(var5.hasNext()) {
            var6 = (Entry)var5.next();
            this.field_146604_g.add(String.valueOf((new StringBuilder("S ")).append((String)var6.getKey())));
            this.field_146609_h.add(this.fontRendererObj.trimStringToWidth((String)var6.getValue(), this.width - 220));
         }
      }

      this.field_146606_s = new GuiSnooper.List(this);
   }

   static java.util.List access$1(GuiSnooper var0) {
      return var0.field_146609_h;
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.field_146606_s.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.field_146610_i, this.width / 2, 8, 16777215);
      int var4 = 22;
      String[] var5 = this.field_146607_r;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String var8 = var5[var7];
         this.drawCenteredString(this.fontRendererObj, var8, this.width / 2, var4, 8421504);
         var4 += this.fontRendererObj.FONT_HEIGHT;
      }

      super.drawScreen(var1, var2, var3);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.field_146606_s.func_178039_p();
   }

   static java.util.List access$0(GuiSnooper var0) {
      return var0.field_146604_g;
   }

   public GuiSnooper(GuiScreen var1, GameSettings var2) {
      this.field_146608_a = var1;
      this.game_settings_2 = var2;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.enabled) {
         if (var1.id == 2) {
            this.game_settings_2.saveOptions();
            this.game_settings_2.saveOptions();
            this.mc.displayGuiScreen(this.field_146608_a);
         }

         if (var1.id == 1) {
            this.game_settings_2.setOptionValue(GameSettings.Options.SNOOPER_ENABLED, 1);
            this.field_146605_t.displayString = this.game_settings_2.getKeyBinding(GameSettings.Options.SNOOPER_ENABLED);
         }
      }

   }

   class List extends GuiSlot {
      private static final String __OBFID = "CL_00000715";
      final GuiSnooper this$0;

      protected boolean isSelected(int var1) {
         return false;
      }

      protected void drawBackground() {
      }

      protected int getSize() {
         return GuiSnooper.access$0(this.this$0).size();
      }

      public List(GuiSnooper var1) {
         super(var1.mc, var1.width, var1.height, 80, var1.height - 40, var1.fontRendererObj.FONT_HEIGHT + 1);
         this.this$0 = var1;
      }

      protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
         this.this$0.fontRendererObj.drawString((String)GuiSnooper.access$0(this.this$0).get(var1), 10.0D, (double)var3, 16777215);
         this.this$0.fontRendererObj.drawString((String)GuiSnooper.access$1(this.this$0).get(var1), 230.0D, (double)var3, 16777215);
      }

      protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      }

      protected int getScrollBarX() {
         return this.width - 10;
      }
   }
}
