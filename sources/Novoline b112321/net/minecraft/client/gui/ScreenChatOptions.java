package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;

public class ScreenChatOptions extends GuiScreen {
   private static final GameSettings$Options[] field_146399_a = new GameSettings$Options[]{GameSettings$Options.CHAT_VISIBILITY, GameSettings$Options.CHAT_COLOR, GameSettings$Options.CHAT_LINKS, GameSettings$Options.CHAT_OPACITY, GameSettings$Options.CHAT_LINKS_PROMPT, GameSettings$Options.CHAT_SCALE, GameSettings$Options.CHAT_HEIGHT_FOCUSED, GameSettings$Options.CHAT_HEIGHT_UNFOCUSED, GameSettings$Options.CHAT_WIDTH, GameSettings$Options.REDUCED_DEBUG_INFO};
   private final GuiScreen parentScreen;
   private final GameSettings game_settings;
   private String field_146401_i;

   public ScreenChatOptions(GuiScreen var1, GameSettings var2) {
      this.parentScreen = var1;
      this.game_settings = var2;
   }

   public void initGui() {
      int var1 = 0;
      this.field_146401_i = I18n.format("options.chat.title", new Object[0]);

      for(GameSettings$Options var5 : field_146399_a) {
         if(var5.getEnumFloat()) {
            this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5));
         } else {
            this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5, this.game_settings.b(var5)));
         }

         ++var1;
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 120, I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id < 100 && var1 instanceof GuiOptionButton) {
            this.game_settings.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
            var1.displayString = this.game_settings.b(GameSettings$Options.getEnumOptions(var1.id));
         }

         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146401_i, this.width / 2, 20, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
