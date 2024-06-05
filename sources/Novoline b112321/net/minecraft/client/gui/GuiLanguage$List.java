package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;
import net.minecraft.client.settings.GameSettings$Options;

class GuiLanguage$List extends GuiSlot {
   private final List langCodeList;
   private final Map languageMap;
   final GuiLanguage this$0;

   public GuiLanguage$List(GuiLanguage var1, Minecraft var2) {
      super(var2, var1.width, var1.height, 32, var1.height - 65 + 4, 18);
      this.this$0 = var1;
      this.langCodeList = Lists.newArrayList();
      this.languageMap = Maps.newHashMap();

      for(Language var4 : GuiLanguage.c(var1).a()) {
         this.languageMap.put(var4.getLanguageCode(), var4);
         this.langCodeList.add(var4.getLanguageCode());
      }

   }

   protected int getSize() {
      return this.langCodeList.size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      Language var5 = (Language)this.languageMap.get(this.langCodeList.get(var1));
      GuiLanguage.c(this.this$0).a(var5);
      GuiLanguage.access$100(this.this$0).language = var5.getLanguageCode();
      this.mc.refreshResources();
      this.this$0.fontRendererObj.setUnicodeFlag(GuiLanguage.c(this.this$0).b() || GuiLanguage.access$100(this.this$0).forceUnicodeFont);
      this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.c(this.this$0).d());
      GuiLanguage.access$200(this.this$0).displayString = I18n.format("gui.done", new Object[0]);
      GuiLanguage.access$300(this.this$0).displayString = GuiLanguage.access$100(this.this$0).b(GameSettings$Options.FORCE_UNICODE_FONT);
      GuiLanguage.access$100(this.this$0).saveOptions();
   }

   protected boolean isSelected(int var1) {
      return ((String)this.langCodeList.get(var1)).equals(GuiLanguage.c(this.this$0).c().getLanguageCode());
   }

   protected int getContentHeight() {
      return this.getSize() * 18;
   }

   protected void drawBackground() {
      this.this$0.drawDefaultBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.this$0.fontRendererObj.setBidiFlag(true);
      this.this$0.drawCenteredString(this.this$0.fontRendererObj, ((Language)this.languageMap.get(this.langCodeList.get(var1))).toString(), this.width / 2, var3 + 1, 16777215);
      this.this$0.fontRendererObj.setBidiFlag(GuiLanguage.c(this.this$0).c().isBidirectional());
   }
}
