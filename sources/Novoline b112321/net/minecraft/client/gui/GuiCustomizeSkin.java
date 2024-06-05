package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiCustomizeSkin$ButtonPart;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EnumPlayerModelParts;

public class GuiCustomizeSkin extends GuiScreen {
   private final GuiScreen parentScreen;
   private String title;

   public GuiCustomizeSkin(GuiScreen var1) {
      this.parentScreen = var1;
   }

   public void initGui() {
      int var1 = 0;
      this.title = I18n.format("options.skinCustomisation.title", new Object[0]);

      for(EnumPlayerModelParts var5 : EnumPlayerModelParts.values()) {
         this.buttonList.add(new GuiCustomizeSkin$ButtonPart(this, var5.getPartId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), 150, 20, var5));
         ++var1;
      }

      if(var1 % 2 == 1) {
         ++var1;
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 24 * (var1 >> 1), I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(var1 instanceof GuiCustomizeSkin$ButtonPart) {
            EnumPlayerModelParts var2 = GuiCustomizeSkin$ButtonPart.access$100((GuiCustomizeSkin$ButtonPart)var1);
            this.mc.gameSettings.switchModelPartEnabled(var2);
            var1.displayString = this.func_175358_a(var2);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 20, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   private String func_175358_a(EnumPlayerModelParts var1) {
      String var2;
      if(this.mc.gameSettings.getModelParts().contains(var1)) {
         var2 = I18n.format("options.on", new Object[0]);
      } else {
         var2 = I18n.format("options.off", new Object[0]);
      }

      return var1.func_179326_d().getFormattedText() + ": " + var2;
   }

   static String access$200(GuiCustomizeSkin var0, EnumPlayerModelParts var1) {
      return var0.func_175358_a(var1);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
