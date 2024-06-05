package net.minecraft.client.gui;

import java.io.IOException;
import net.aHG;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiCustomizeSkin;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiLockIconButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.gui.GuiOptions$1;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.gui.stream.GuiStreamOptions;
import net.minecraft.client.gui.stream.GuiStreamUnavailable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.GameSettings$Options;
import net.minecraft.client.stream.IStream;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class GuiOptions extends GuiScreen implements GuiYesNoCallback {
   private static final GameSettings$Options[] field_146440_f = new GameSettings$Options[]{GameSettings$Options.FOV};
   private final GuiScreen field_146441_g;
   private final GameSettings game_settings_1;
   private GuiButton field_175357_i;
   private GuiLockIconButton field_175356_r;
   protected String field_146442_a = "Options";

   public GuiOptions(GuiScreen var1, GameSettings var2) {
      this.field_146441_g = var1;
      this.game_settings_1 = var2;
   }

   public void initGui() {
      int var1 = 0;
      this.field_146442_a = I18n.format("options.title", new Object[0]);

      for(GameSettings$Options var5 : field_146440_f) {
         if(var5.getEnumFloat()) {
            this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), var5));
         } else {
            GuiOptionButton var6 = new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), var5, this.game_settings_1.b(var5));
            this.buttonList.add(var6);
         }

         ++var1;
      }

      if(this.mc.world != null) {
         EnumDifficulty var7 = this.mc.world.getDifficulty();
         this.field_175357_i = new GuiButton(108, this.width / 2 - 155 + var1 % 2 * 160, this.height / 6 - 12 + 24 * (var1 >> 1), 150, 20, this.func_175355_a(var7));
         this.buttonList.add(this.field_175357_i);
         if(this.mc.isSingleplayer() && !this.mc.world.getWorldInfo().isHardcoreModeEnabled()) {
            this.field_175357_i.setWidth(this.field_175357_i.getButtonWidth() - 20);
            this.field_175356_r = new GuiLockIconButton(109, (int)this.field_175357_i.xPosition + this.field_175357_i.getButtonWidth(), (int)this.field_175357_i.yPosition);
            this.buttonList.add(this.field_175356_r);
            this.field_175356_r.func_175229_b(this.mc.world.getWorldInfo().isDifficultyLocked());
            this.field_175356_r.enabled = !this.field_175356_r.func_175230_c();
            this.field_175357_i.enabled = !this.field_175356_r.func_175230_c();
         } else {
            this.field_175357_i.enabled = false;
         }
      }

      this.buttonList.add(new GuiButton(110, this.width / 2 - 155, this.height / 6 + 48 - 6, 150, 20, I18n.format("options.skinCustomisation", new Object[0])));
      this.buttonList.add(new GuiOptions$1(this, 8675309, this.width / 2 + 5, this.height / 6 + 48 - 6, 150, 20, "Super Secret Settings..."));
      this.buttonList.add(new GuiButton(106, this.width / 2 - 155, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.sounds", new Object[0])));
      this.buttonList.add(new GuiButton(107, this.width / 2 + 5, this.height / 6 + 72 - 6, 150, 20, I18n.format("options.stream", new Object[0])));
      this.buttonList.add(new GuiButton(101, this.width / 2 - 155, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.video", new Object[0])));
      this.buttonList.add(new GuiButton(100, this.width / 2 + 5, this.height / 6 + 96 - 6, 150, 20, I18n.format("options.controls", new Object[0])));
      this.buttonList.add(new GuiButton(102, this.width / 2 - 155, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.language", new Object[0])));
      this.buttonList.add(new GuiButton(103, this.width / 2 + 5, this.height / 6 + 120 - 6, 150, 20, I18n.format("options.chat.title", new Object[0])));
      this.buttonList.add(new GuiButton(105, this.width / 2 - 155, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.resourcepack", new Object[0])));
      this.buttonList.add(new GuiButton(104, this.width / 2 + 5, this.height / 6 + 144 - 6, 150, 20, I18n.format("options.snooper.view", new Object[0])));
      this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
   }

   public String func_175355_a(EnumDifficulty var1) {
      ChatComponentText var2 = new ChatComponentText("");
      var2.appendSibling(new ChatComponentTranslation("options.difficulty", new Object[0]));
      var2.appendText(": ");
      var2.appendSibling(new ChatComponentTranslation(var1.getDifficultyResourceKey(), new Object[0]));
      return var2.getFormattedText();
   }

   public void confirmClicked(boolean var1, int var2) {
      this.mc.displayGuiScreen(this);
      if(var2 == 109 && this.mc.world != null) {
         this.mc.world.getWorldInfo().setDifficultyLocked(true);
         this.field_175356_r.func_175229_b(true);
         this.field_175356_r.enabled = false;
         this.field_175357_i.enabled = false;
      }

   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.enabled) {
         if(var1.id < 100 && var1 instanceof GuiOptionButton) {
            GameSettings$Options var2 = ((GuiOptionButton)var1).returnEnumOptions();
            this.game_settings_1.setOptionValue(var2, 1);
            var1.displayString = this.game_settings_1.b(GameSettings$Options.getEnumOptions(var1.id));
         }

         if(var1.id == 108) {
            this.mc.world.getWorldInfo().setDifficulty(EnumDifficulty.getDifficultyEnum(this.mc.world.getDifficulty().getDifficultyId() + 1));
            this.field_175357_i.displayString = this.func_175355_a(this.mc.world.getDifficulty());
         }

         if(var1.id == 109) {
            this.mc.displayGuiScreen(new GuiYesNo(this, (new ChatComponentTranslation("difficulty.lock.title", new Object[0])).getFormattedText(), (new ChatComponentTranslation("difficulty.lock.question", new Object[]{new ChatComponentTranslation(this.mc.world.getWorldInfo().getDifficulty().getDifficultyResourceKey(), new Object[0])})).getFormattedText(), 109));
         }

         if(var1.id == 110) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiCustomizeSkin(this));
         }

         if(var1.id == 8675309) {
            this.mc.entityRenderer.activateNextShader();
         }

         if(var1.id == 101) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiVideoSettings(this, this.game_settings_1));
         }

         if(var1.id == 100) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiControls(this, this.game_settings_1));
         }

         if(var1.id == 102) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiLanguage(this, this.game_settings_1, this.mc.j()));
         }

         if(var1.id == 103) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new ScreenChatOptions(this, this.game_settings_1));
         }

         if(var1.id == 104) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new aHG(this, this.game_settings_1));
         }

         if(var1.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.field_146441_g);
         }

         if(var1.id == 105) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiScreenResourcePacks(this));
         }

         if(var1.id == 106) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(new GuiScreenOptionsSounds(this, this.game_settings_1));
         }

         if(var1.id == 107) {
            this.mc.gameSettings.saveOptions();
            IStream var3 = this.mc.getTwitchStream();
            if(var3.func_152936_l() && var3.func_152928_D()) {
               this.mc.displayGuiScreen(new GuiStreamOptions(this, this.game_settings_1));
            } else {
               GuiStreamUnavailable.func_152321_a(this);
            }
         }
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, this.field_146442_a, this.width / 2, 15, 16777215);
      super.drawScreen(var1, var2, var3);
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
