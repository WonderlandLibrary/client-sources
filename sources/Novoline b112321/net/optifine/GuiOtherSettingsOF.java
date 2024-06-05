package net.optifine;

import cc.novoline.Novoline;
import cc.novoline.modules.misc.WindowedFullscreen;
import net.acE;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;
import net.optifine.GuiOptionButtonOF;
import net.optifine.GuiOptionSliderOF;
import net.optifine.MatchBlock;
import net.optifine.TooltipManager;

public class GuiOtherSettingsOF extends GuiScreen implements GuiYesNoCallback {
   private GuiScreen prevScreen;
   protected String title;
   private GameSettings settings;
   private static GameSettings$Options[] enumOptions = new GameSettings$Options[]{GameSettings$Options.LAGOMETER, GameSettings$Options.PROFILER, GameSettings$Options.WEATHER, GameSettings$Options.TIME, GameSettings$Options.USE_FULLSCREEN, GameSettings$Options.FULLSCREEN_MODE, GameSettings$Options.SHOW_FPS, GameSettings$Options.AUTOSAVE_TICKS, GameSettings$Options.ANAGLYPH};
   private TooltipManager tooltipManager = new TooltipManager(this);

   public GuiOtherSettingsOF(GuiScreen var1, GameSettings var2) {
      this.prevScreen = var1;
      this.settings = var2;
   }

   public void initGui() {
      MatchBlock.b();
      this.title = I18n.format("of.options.otherTitle", new Object[0]);
      this.buttonList.clear();
      int var2 = 0;
      if(var2 < enumOptions.length) {
         GameSettings$Options var3 = enumOptions[var2];
         int var4 = this.width / 2 - 155 + var2 % 2 * 160;
         int var5 = this.height / 6 + 21 * (var2 / 2) - 12;
         if(!var3.getEnumFloat()) {
            this.buttonList.add(new GuiOptionButtonOF(var3.returnEnumOrdinal(), var4, var5, var3, this.settings.b(var3)));
         }

         this.buttonList.add(new GuiOptionSliderOF(var3.returnEnumOrdinal(), var4, var5, var3));
         ++var2;
      }

      this.buttonList.add(new GuiButton(11001, this.width / 2 + 5, this.height / 13 + 168 + 9 - 44, 150, 20, "Windowed Fullscreen: " + (!Novoline.getInstance().isAnythingNull() && ((WindowedFullscreen)Novoline.getInstance().getModuleManager().getModule(WindowedFullscreen.class)).isEnabled()?"ON":"OFF")));
      this.buttonList.add(new GuiButton(210, this.width / 2 - 100, this.height / 6 + 168 + 11 - 44, I18n.format("of.options.other.reset", new Object[0])));
      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) {
      acE[] var2 = MatchBlock.b();
      if(var1.enabled) {
         if(var1.id < 200 && var1 instanceof GuiOptionButton) {
            this.settings.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
            var1.displayString = this.settings.b(GameSettings$Options.getEnumOptions(var1.id));
         }

         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.prevScreen);
         }

         if(var1.id == 210) {
            this.mc.gameSettings.saveOptions();
            GuiYesNo var3 = new GuiYesNo(this, I18n.format("of.message.other.reset", new Object[0]), "", 9999);
            this.mc.displayGuiScreen(var3);
         }

         if(var1.id == 11001 && !Novoline.getInstance().isAnythingNull()) {
            ((WindowedFullscreen)Novoline.getInstance().getModuleManager().getModule(WindowedFullscreen.class)).toggle();
            var1.displayString = "Windowed Fullscreen: " + (((WindowedFullscreen)Novoline.getInstance().getModuleManager().getModule(WindowedFullscreen.class)).isEnabled()?"ON":"OFF");
         }
      }

   }

   public void confirmClicked(boolean var1, int var2) {
      acE[] var3 = MatchBlock.b();
      this.mc.gameSettings.resetSettings();
      this.mc.displayGuiScreen(this);
   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
      this.tooltipManager.a(var1, var2, this.buttonList);
   }
}
