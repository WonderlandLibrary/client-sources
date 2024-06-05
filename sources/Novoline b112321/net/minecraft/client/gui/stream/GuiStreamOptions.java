package net.minecraft.client.gui.stream;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.stream.GuiIngestServers;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;
import net.minecraft.util.EnumChatFormatting;

public class GuiStreamOptions extends GuiScreen {
   private static final GameSettings$Options[] field_152312_a = new GameSettings$Options[]{GameSettings$Options.STREAM_BYTES_PER_PIXEL, GameSettings$Options.STREAM_FPS, GameSettings$Options.STREAM_KBPS, GameSettings$Options.STREAM_SEND_METADATA, GameSettings$Options.STREAM_VOLUME_MIC, GameSettings$Options.STREAM_VOLUME_SYSTEM, GameSettings$Options.STREAM_MIC_TOGGLE_BEHAVIOR, GameSettings$Options.STREAM_COMPRESSION};
   private static final GameSettings$Options[] field_152316_f = new GameSettings$Options[]{GameSettings$Options.STREAM_CHAT_ENABLED, GameSettings$Options.STREAM_CHAT_USER_FILTER};
   private final GuiScreen parentScreen;
   private final GameSettings field_152318_h;
   private String field_152319_i;
   private String field_152313_r;
   private int field_152314_s;
   private boolean field_152315_t = false;

   public GuiStreamOptions(GuiScreen var1, GameSettings var2) {
      this.parentScreen = var1;
      this.field_152318_h = var2;
   }

   public void initGui() {
      int var1 = 0;
      this.field_152319_i = I18n.format("options.stream.title", new Object[0]);
      this.field_152313_r = I18n.format("options.stream.chat.title", new Object[0]);

      for(GameSettings$Options var5 : field_152312_a) {
         if(var5.getEnumFloat()) {
            this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5));
         } else {
            this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var5, this.field_152318_h.b(var5)));
         }

         ++var1;
      }

      if(var1 % 2 == 1) {
         ++var1;
      }

      this.field_152314_s = this.height / 6 + 24 * (var1 >> 1) + 6;
      var1 = var1 + 2;

      for(GameSettings$Options var11 : field_152316_f) {
         if(var11.getEnumFloat()) {
            this.buttonList.add(new GuiOptionSlider(var11.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var11));
         } else {
            this.buttonList.add(new GuiOptionButton(var11.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 + 24 * (var1 >> 1), var11, this.field_152318_h.b(var11)));
         }

         ++var1;
      }

      this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height / 6 + 168, 150, 20, I18n.format("gui.done", new Object[0])));
      GuiButton var8 = new GuiButton(201, this.width / 2 + 5, this.height / 6 + 168, 150, 20, I18n.format("options.stream.ingestSelection", new Object[0]));
      var8.enabled = this.mc.getTwitchStream().isReadyToBroadcast() && this.mc.getTwitchStream().func_152925_v().length > 0 || this.mc.getTwitchStream().func_152908_z();
      this.buttonList.add(var8);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id < 100 && var1 instanceof GuiOptionButton) {
            GameSettings$Options var2 = ((GuiOptionButton)var1).returnEnumOptions();
            this.field_152318_h.setOptionValue(var2, 1);
            var1.displayString = this.field_152318_h.b(GameSettings$Options.getEnumOptions(var1.id));
            if(this.mc.getTwitchStream().isBroadcasting() && var2 != GameSettings$Options.STREAM_CHAT_ENABLED && var2 != GameSettings$Options.STREAM_CHAT_USER_FILTER) {
               this.field_152315_t = true;
            }
         } else if(var1 instanceof GuiOptionSlider) {
            if(var1.id == GameSettings$Options.STREAM_VOLUME_MIC.returnEnumOrdinal()) {
               this.mc.getTwitchStream().updateStreamVolume();
            } else if(var1.id == GameSettings$Options.STREAM_VOLUME_SYSTEM.returnEnumOrdinal()) {
               this.mc.getTwitchStream().updateStreamVolume();
            } else if(this.mc.getTwitchStream().isBroadcasting()) {
               this.field_152315_t = true;
            }
         }

         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
         } else if(var1.id == 201) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiIngestServers(this));
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_152319_i, this.width / 2, 20, 16777215);
      this.drawCenteredString(this.fontRendererObj, this.field_152313_r, this.width / 2, this.field_152314_s, 16777215);
      if(this.field_152315_t) {
         this.drawCenteredString(this.fontRendererObj, EnumChatFormatting.RED + I18n.format("options.stream.changes", new Object[0]), this.width / 2, 20 + this.fontRendererObj.getHeight(), 16777215);
      }

      super.drawScreen(var1, var2, var3);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
