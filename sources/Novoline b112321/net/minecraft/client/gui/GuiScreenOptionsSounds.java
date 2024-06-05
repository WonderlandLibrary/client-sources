package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds$Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiScreenOptionsSounds extends GuiScreen {
   private final GuiScreen field_146505_f;
   private final GameSettings game_settings_4;
   protected String field_146507_a = "Options";
   private String field_146508_h;

   public GuiScreenOptionsSounds(GuiScreen var1, GameSettings var2) {
      this.field_146505_f = var1;
      this.game_settings_4 = var2;
   }

   public void initGui() {
      int var1 = 0;
      this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
      this.field_146508_h = I18n.format("options.off", new Object[0]);
      this.buttonList.add(new GuiScreenOptionsSounds$Button(this, SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), SoundCategory.MASTER, true));
      var1 = var1 + 2;

      for(SoundCategory var5 : SoundCategory.values()) {
         if(var5 != SoundCategory.MASTER) {
            this.buttonList.add(new GuiScreenOptionsSounds$Button(this, var5.getCategoryId(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), var5, false));
            ++var1;
         }
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled && var1.id == 200) {
         this.mc.gameSettings.saveOptions();
         this.mc.displayGuiScreen(this.field_146505_f);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   protected String getSoundVolume(SoundCategory var1) {
      float var2 = this.game_settings_4.getSoundLevel(var1);
      return var2 == 0.0F?this.field_146508_h:(int)(var2 * 100.0F) + "%";
   }

   static GameSettings access$000(GuiScreenOptionsSounds var0) {
      return var0.game_settings_4;
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
