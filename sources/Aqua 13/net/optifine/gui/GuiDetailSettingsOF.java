package net.optifine.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiDetailSettingsOF extends GuiScreen {
   private GuiScreen prevScreen;
   protected String title;
   private GameSettings settings;
   private static GameSettings.Options[] enumOptions = new GameSettings.Options[]{
      GameSettings.Options.CLOUDS,
      GameSettings.Options.CLOUD_HEIGHT,
      GameSettings.Options.TREES,
      GameSettings.Options.RAIN,
      GameSettings.Options.SKY,
      GameSettings.Options.STARS,
      GameSettings.Options.SUN_MOON,
      GameSettings.Options.SHOW_CAPES,
      GameSettings.Options.FOG_FANCY,
      GameSettings.Options.FOG_START,
      GameSettings.Options.TRANSLUCENT_BLOCKS,
      GameSettings.Options.HELD_ITEM_TOOLTIPS,
      GameSettings.Options.DROPPED_ITEMS,
      GameSettings.Options.ENTITY_SHADOWS,
      GameSettings.Options.VIGNETTE,
      GameSettings.Options.ALTERNATE_BLOCKS,
      GameSettings.Options.SWAMP_COLORS,
      GameSettings.Options.SMOOTH_BIOMES
   };
   private TooltipManager tooltipManager = new TooltipManager(this, new TooltipProviderOptions());

   public GuiDetailSettingsOF(GuiScreen guiscreen, GameSettings gamesettings) {
      this.prevScreen = guiscreen;
      this.settings = gamesettings;
   }

   @Override
   public void initGui() {
      this.title = I18n.format("of.options.detailsTitle");
      this.buttonList.clear();

      for(int i = 0; i < enumOptions.length; ++i) {
         GameSettings.Options gamesettings$options = enumOptions[i];
         int j = width / 2 - 155 + i % 2 * 160;
         int k = height / 6 + 21 * (i / 2) - 12;
         if (!gamesettings$options.getEnumFloat()) {
            this.buttonList
               .add(
                  new GuiOptionButtonOF(
                     gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)
                  )
               );
         } else {
            this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
         }
      }

      this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 168 + 11, I18n.format("gui.done")));
   }

   @Override
   protected void actionPerformed(GuiButton guibutton) {
      if (guibutton.enabled) {
         if (guibutton.id < 200 && guibutton instanceof GuiOptionButton) {
            this.settings.setOptionValue(((GuiOptionButton)guibutton).returnEnumOptions(), 1);
            guibutton.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(guibutton.id));
         }

         if (guibutton.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.prevScreen);
         }
      }
   }

   @Override
   public void drawScreen(int x, int y, float f) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, width / 2, 15, 16777215);
      super.drawScreen(x, y, f);
      this.tooltipManager.drawTooltips(x, y, this.buttonList);
   }
}
