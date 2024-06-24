package cc.slack.ui.alt;

import cc.slack.utils.client.mc;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatFormatting;
import org.lwjgl.input.Keyboard;

public final class GuiAltLogin extends GuiScreen {
   private PasswordField password;
   private final GuiScreen previousScreen;
   private AltLoginThread thread;
   private GuiTextField username;

   public GuiAltLogin(GuiScreen previousScreen) {
      this.previousScreen = previousScreen;
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         this.thread = new AltLoginThread(this.username.getText(), this.password.getText());
         this.thread.start();
         break;
      case 1:
         this.mc.displayGuiScreen(this.previousScreen);
      }

   }

   public void drawScreen(int x2, int y2, float z2) {
      this.drawDefaultBackground();
      this.username.drawTextBox();
      this.password.drawTextBox();
      this.drawCenteredString(mc.getFontRenderer(), "Alt Login", this.width / 2, 20, -1);
      this.drawCenteredString(mc.getFontRenderer(), this.thread == null ? ChatFormatting.GRAY + "Idle..." : this.thread.getStatus(), this.width / 2, 29, -1);
      if (this.username.getText().isEmpty()) {
         this.drawString(mc.getFontRenderer(), "Username / E-Mail", this.width / 2 - 96, 66, -7829368);
      }

      if (this.password.getText().isEmpty()) {
         this.drawString(mc.getFontRenderer(), "Password", this.width / 2 - 96, 106, -7829368);
      }

      super.drawScreen(x2, y2, z2);
   }

   public void initGui() {
      int var3 = this.height / 4 + 24;
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, var3 + 72 + 12, "Login"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, var3 + 72 + 12 + 24, "Back"));
      this.username = new GuiTextField(var3, mc.getFontRenderer(), this.width / 2 - 100, 60, 200, 20);
      this.password = new PasswordField(mc.getFontRenderer(), this.width / 2 - 100, 100, 200, 20);
      this.username.setFocused(true);
      Keyboard.enableRepeatEvents(true);
   }

   protected void keyTyped(char character, int key) {
      try {
         super.keyTyped(character, key);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      if (character == '\t') {
         if (!this.username.isFocused() && !this.password.isFocused()) {
            this.username.setFocused(true);
         } else {
            this.username.setFocused(this.password.isFocused());
            this.password.setFocused(!this.username.isFocused());
         }
      }

      if (character == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

      this.username.textboxKeyTyped(character, key);
      this.password.textboxKeyTyped(character, key);
   }

   protected void mouseClicked(int x2, int y2, int button) {
      try {
         super.mouseClicked(x2, y2, button);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.username.mouseClicked(x2, y2, button);
      this.password.mouseClicked(x2, y2, button);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   public void updateScreen() {
      this.username.updateCursorCounter();
      this.password.updateCursorCounter();
   }
}
