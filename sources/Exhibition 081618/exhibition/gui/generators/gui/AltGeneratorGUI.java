package exhibition.gui.generators.gui;

import exhibition.Client;
import exhibition.gui.altmanager.Alt;
import exhibition.gui.altmanager.AltManager;
import exhibition.gui.altmanager.Alts;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

public class AltGeneratorGUI extends GuiScreen {
   private GuiTextField apiKey;
   private GuiTextField altField;
   private GuiScreen previousScreen;

   public AltGeneratorGUI(GuiScreen previousScreen) {
      this.previousScreen = previousScreen;
   }

   protected void actionPerformed(GuiButton button) {
      switch(button.id) {
      case 0:
         if (this.apiKey.getText().equals("") || !this.apiKey.getText().contains("-")) {
            this.apiKey.setText("Invalid key!");
            return;
         }

         if (Client.altGenHandler.getApiKey() == null) {
            Client.altGenHandler.setApiKey(this.apiKey.getText());
            this.apiKey.setEnabled(false);
         }

         this.altField.setText(Client.altGenHandler.getAlt());
         break;
      case 1:
         if (!this.altField.getText().equals("") && this.altField.getText().contains(":")) {
            String[] login = this.altField.getText().split(":");
            AltManager.registry.add(new Alt(login[0], login[1]));
            this.altField.setText("Added to alt manager!");

            try {
               Client.getFileManager().getFile(Alts.class).saveFile();
            } catch (IOException var4) {
               var4.printStackTrace();
            }
         }
      }

   }

   public void drawScreen(int i, int j, float f) {
      ScaledResolution res = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
      RenderingUtil.rectangle(0.0D, 0.0D, (double)res.getScaledWidth(), (double)res.getScaledHeight(), Colors.getColor(0));
      this.apiKey.drawTextBox();
      this.altField.drawTextBox();
      this.drawCenteredString(this.fontRendererObj, "Alt-Gen API Manager", this.width / 2, 20, -1);
      if (this.apiKey.getText().isEmpty() && !this.apiKey.isFocused()) {
         this.drawString(this.mc.fontRendererObj, "API Key", this.width / 2 - 96, 66, -7829368);
      }

      if (this.altField.getText().isEmpty() && !this.altField.isFocused()) {
         this.drawString(this.mc.fontRendererObj, "Alt Information", this.width / 2 - 96, 106, -7829368);
      }

      super.drawScreen(i, j, f);
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 80, "Generate Alt"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 80 + 22, "Add alt to manager"));
      this.altField = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 100, 200, 20);
      this.apiKey = new GuiTextField(this.eventButton, this.mc.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.apiKey.setMaxStringLength(128);
      this.altField.setMaxStringLength(128);
      if (Client.altGenHandler.getApiKey() != null) {
         this.apiKey.setText(Client.altGenHandler.getApiKey());
         this.apiKey.setEnabled(false);
      }

   }

   protected void keyTyped(char par1, int par2) {
      if (par2 == 1) {
         this.mc.displayGuiScreen(this.previousScreen);
      }

      this.apiKey.textboxKeyTyped(par1, par2);
      this.altField.textboxKeyTyped(par1, par2);
      if (par1 == '\t' && (this.apiKey.isFocused() || this.altField.isFocused())) {
         this.apiKey.setFocused(!this.apiKey.isFocused());
         this.altField.setFocused(!this.altField.isFocused());
      }

      if (par1 == '\r') {
         this.actionPerformed((GuiButton)this.buttonList.get(1));
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) {
      try {
         super.mouseClicked(par1, par2, par3);
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.apiKey.mouseClicked(par1, par2, par3);
      this.altField.mouseClicked(par1, par2, par3);
   }

   public void onGuiClosed() {
   }
}
