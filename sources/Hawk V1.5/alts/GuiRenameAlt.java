package alts;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;

public class GuiRenameAlt extends GuiScreen {
   private GuiTextField nameField;
   private String status;
   private final GuiAltManager manager;
   private PasswordField pwField;

   public void actionPerformed(GuiButton var1) {
      switch(var1.id) {
      case 0:
         this.manager.selectedAlt.setMask(this.nameField.getText());
         this.manager.selectedAlt.setPassword(this.pwField.getText());
         this.status = "Edited!";
         break;
      case 1:
         this.mc.displayGuiScreen(this.manager);
      }

   }

   public void drawScreen(int var1, int var2, float var3) {
      this.drawDefaultBackground();
      this.drawCenteredString(this.fontRendererObj, "Edit Alt", this.width / 2, 10, -1);
      this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 20, -1);
      this.nameField.drawTextBox();
      this.pwField.drawTextBox();
      if (this.nameField.getText().isEmpty()) {
         this.drawString(this.mc.fontRendererObj, "New name", this.width / 2 - 96, 66, -7829368);
      }

      if (this.pwField.getText().isEmpty()) {
         this.drawString(this.mc.fontRendererObj, "New password", this.width / 2 - 96, 106, -7829368);
      }

      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) {
      this.nameField.textboxKeyTyped(var1, var2);
      this.pwField.textboxKeyTyped(var1, var2);
      if (var1 == '\t' && (this.nameField.isFocused() || this.pwField.isFocused())) {
         this.nameField.setFocused(!this.nameField.isFocused());
         this.pwField.setFocused(!this.pwField.isFocused());
      }

      if (var1 == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(0));
      }

   }

   public GuiRenameAlt(GuiAltManager var1) {
      this.status = String.valueOf((new StringBuilder()).append(EnumChatFormatting.GRAY).append("Waiting..."));
      this.manager = var1;
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      try {
         super.mouseClicked(var1, var2, var3);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.nameField.mouseClicked(var1, var2, var3);
      this.pwField.mouseClicked(var1, var2, var3);
   }

   public void initGui() {
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Edit"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Cancel"));
      this.nameField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.pwField = new PasswordField(this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
   }
}
