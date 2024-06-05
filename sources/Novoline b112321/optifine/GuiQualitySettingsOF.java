package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;
import optifine.GuiOptionButtonOF;
import optifine.GuiOptionSliderOF;
import optifine.MatchBlock;
import optifine.TooltipManager;

public class GuiQualitySettingsOF extends GuiScreen {
   private GuiScreen prevScreen;
   protected String title;
   private GameSettings settings;
   private static GameSettings$Options[] enumOptions = new GameSettings$Options[]{GameSettings$Options.MIPMAP_LEVELS, GameSettings$Options.MIPMAP_TYPE, GameSettings$Options.AF_LEVEL, GameSettings$Options.AA_LEVEL, GameSettings$Options.CLEAR_WATER, GameSettings$Options.RANDOM_MOBS, GameSettings$Options.BETTER_GRASS, GameSettings$Options.BETTER_SNOW, GameSettings$Options.CUSTOM_FONTS, GameSettings$Options.CUSTOM_COLORS, GameSettings$Options.SWAMP_COLORS, GameSettings$Options.SMOOTH_BIOMES, GameSettings$Options.CONNECTED_TEXTURES, GameSettings$Options.NATURAL_TEXTURES, GameSettings$Options.CUSTOM_SKY, GameSettings$Options.CUSTOM_ITEMS, GameSettings$Options.DYNAMIC_LIGHTS};
   private TooltipManager tooltipManager = new TooltipManager(this);

   public GuiQualitySettingsOF(GuiScreen var1, GameSettings var2) {
      this.prevScreen = var1;
      this.settings = var2;
   }

   public void initGui() {
      this.title = I18n.format("of.options.qualityTitle", new Object[0]);
      MatchBlock.b();
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

      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
   }

   protected void actionPerformed(GuiButton var1) {
      PacketRemapper[] var2 = MatchBlock.b();
      if(var1.enabled) {
         if(var1.id < 200 && var1 instanceof GuiOptionButton) {
            this.settings.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
            var1.displayString = this.settings.b(GameSettings$Options.getEnumOptions(var1.id));
         }

         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.prevScreen);
         }

         if(var1.id != GameSettings$Options.AA_LEVEL.ordinal()) {
            ScaledResolution var3 = new ScaledResolution(this.mc);
            this.setWorldAndResolution(this.mc, var3.getScaledWidth(), var3.getScaledHeight());
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
      this.tooltipManager.a(var1, var2, this.buttonList);
   }
}
