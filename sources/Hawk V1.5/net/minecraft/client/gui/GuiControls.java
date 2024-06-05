package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;

public class GuiControls extends GuiScreen {
   private GuiButton buttonReset;
   public KeyBinding buttonId = null;
   private GuiScreen parentScreen;
   private GuiKeyBindingList keyBindingList;
   private static final String __OBFID = "CL_00000736";
   public long time;
   protected String screenTitle = "Controls";
   private GameSettings options;
   private static final GameSettings.Options[] optionsArr;

   public void initGui() {
      this.keyBindingList = new GuiKeyBindingList(this, this.mc);
      this.buttonList.add(new GuiButton(200, this.width / 2 - 155, this.height - 29, 150, 20, I18n.format("gui.done")));
      this.buttonList.add(this.buttonReset = new GuiButton(201, this.width / 2 - 155 + 160, this.height - 29, 150, 20, I18n.format("controls.resetAll")));
      this.screenTitle = I18n.format("controls.title");
      int var1 = 0;
      GameSettings.Options[] var2 = optionsArr;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         GameSettings.Options var5 = var2[var4];
         if (var5.getEnumFloat()) {
            this.buttonList.add(new GuiOptionSlider(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5));
         } else {
            this.buttonList.add(new GuiOptionButton(var5.returnEnumOrdinal(), this.width / 2 - 155 + var1 % 2 * 160, 18 + 24 * (var1 >> 1), var5, this.options.getKeyBinding(var5)));
         }

         ++var1;
      }

   }

   public GuiControls(GuiScreen var1, GameSettings var2) {
      this.parentScreen = var1;
      this.options = var2;
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (this.buttonId != null) {
         if (var2 == 1) {
            this.options.setOptionKeyBinding(this.buttonId, 0);
         } else if (var2 != 0) {
            this.options.setOptionKeyBinding(this.buttonId, var2);
         } else if (var1 > 0) {
            this.options.setOptionKeyBinding(this.buttonId, var1 + 256);
         }

         this.buttonId = null;
         this.time = Minecraft.getSystemTime();
         KeyBinding.resetKeyBindingArrayAndHash();
      } else {
         super.keyTyped(var1, var2);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.keyBindingList.drawScreen(var1, var2, var3);
      this.drawCenteredString(this.fontRendererObj, this.screenTitle, this.width / 2, 8, 16777215);
      boolean var4 = true;
      KeyBinding[] var5 = this.options.keyBindings;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         KeyBinding var8 = var5[var7];
         if (var8.getKeyCode() != var8.getKeyCodeDefault()) {
            var4 = false;
            break;
         }
      }

      this.buttonReset.enabled = !var4;
      super.drawScreen(var1, var2, var3);
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id == 200) {
         this.mc.displayGuiScreen(this.parentScreen);
      } else if (var1.id == 201) {
         KeyBinding[] var2 = this.mc.gameSettings.keyBindings;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            KeyBinding var5 = var2[var4];
            var5.setKeyCode(var5.getKeyCodeDefault());
         }

         KeyBinding.resetKeyBindingArrayAndHash();
      } else if (var1.id < 100 && var1 instanceof GuiOptionButton) {
         this.options.setOptionValue(((GuiOptionButton)var1).returnEnumOptions(), 1);
         var1.displayString = this.options.getKeyBinding(GameSettings.Options.getEnumOptions(var1.id));
      }

   }

   protected void mouseReleased(int var1, int var2, int var3) {
      if (var3 != 0 || !this.keyBindingList.func_148181_b(var1, var2, var3)) {
         super.mouseReleased(var1, var2, var3);
      }

   }

   static {
      optionsArr = new GameSettings.Options[]{GameSettings.Options.INVERT_MOUSE, GameSettings.Options.SENSITIVITY, GameSettings.Options.TOUCHSCREEN};
   }

   public void handleMouseInput() throws IOException {
      super.handleMouseInput();
      this.keyBindingList.func_178039_p();
   }

   protected void mouseClicked(int var1, int var2, int var3) throws IOException {
      if (this.buttonId != null) {
         this.options.setOptionKeyBinding(this.buttonId, -100 + var3);
         this.buttonId = null;
         KeyBinding.resetKeyBindingArrayAndHash();
      } else if (var3 != 0 || !this.keyBindingList.func_148179_a(var1, var2, var3)) {
         super.mouseClicked(var1, var2, var3);
      }

   }
}
