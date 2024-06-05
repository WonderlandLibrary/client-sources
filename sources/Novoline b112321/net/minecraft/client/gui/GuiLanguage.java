package net.minecraft.client.gui;

import java.io.IOException;
import net.BA;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage$List;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;

public class GuiLanguage extends GuiScreen {
   protected GuiScreen parentScreen;
   private GuiLanguage$List list;
   private final GameSettings game_settings_3;
   private final BA A;
   private GuiOptionButton forceUnicodeFontBtn;
   private GuiOptionButton confirmSettingsBtn;

   public GuiLanguage(GuiScreen var1, GameSettings var2, BA var3) {
      this.parentScreen = var1;
      this.game_settings_3 = var2;
      this.A = var3;
   }

   public void initGui() {
      this.buttonList.add(this.forceUnicodeFontBtn = new GuiOptionButton(100, this.width / 2 - 155, this.height - 38, GameSettings$Options.FORCE_UNICODE_FONT, this.game_settings_3.b(GameSettings$Options.FORCE_UNICODE_FONT)));
      this.buttonList.add(this.confirmSettingsBtn = new GuiOptionButton(6, this.width / 2 - 155 + 160, this.height - 38, I18n.format("gui.done", new Object[0])));
      this.list = new GuiLanguage$List(this, this.mc);
      this.list.registerScrollButtons(7, 8);
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.list.handleMouseInput();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         switch(var1.id) {
         case 5:
            break;
         case 6:
            this.mc.displayGuiScreen(this.parentScreen);
            break;
         case 100:
            if(var1 instanceof GuiOptionButton) {
               this.game_settings_3.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
               var1.displayString = this.game_settings_3.b(GameSettings$Options.FORCE_UNICODE_FONT);
               ScaledResolution var2 = new ScaledResolution(this.mc);
               int var3 = var2.getScaledWidth();
               int var4 = var2.getScaledHeight();
               this.setWorldAndResolution(this.mc, var3, var4);
            }
            break;
         default:
            this.list.actionPerformed(var1);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.list.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, I18n.format("options.language", new Object[0]), this.width / 2, 16, 16777215);
      this.drawCenteredString(this.fontRendererObj, "(" + I18n.format("options.languageWarning", new Object[0]) + ")", this.width / 2, this.height - 56, 8421504);
      super.drawScreen(var1, var2, var3);
   }

   static BA c(GuiLanguage var0) {
      return var0.A;
   }

   static GameSettings access$100(GuiLanguage var0) {
      return var0.game_settings_3;
   }

   static GuiOptionButton access$200(GuiLanguage var0) {
      return var0.confirmSettingsBtn;
   }

   static GuiOptionButton access$300(GuiLanguage var0) {
      return var0.forceUnicodeFontBtn;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
