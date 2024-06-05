package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo {
   private final String openLinkWarning;
   private final String copyLinkButtonText;
   private final String linkText;
   private boolean showSecurityWarning = true;

   public GuiConfirmOpenLink(GuiYesNoCallback var1, String var2, int var3, boolean var4) {
      super(var1, I18n.format("chat.link.confirmTrusted", new Object[0]), var2, var3);
      this.confirmButtonText = I18n.format("chat.link.open", new Object[0]);
      this.cancelButtonText = I18n.format("gui.cancel", new Object[0]);
      this.copyLinkButtonText = I18n.format("chat.copy", new Object[0]);
      this.openLinkWarning = I18n.format("chat.link.warning", new Object[0]);
      this.linkText = var2;
   }

   public void initGui() {
      super.initGui();
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.copyLinkButtonText));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, this.cancelButtonText));
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if(var1.id == 2) {
         this.copyLinkToClipboard();
      }

      this.parentScreen.confirmClicked(var1.id == 0, this.parentButtonClickedId);
   }

   public void copyLinkToClipboard() {
      setClipboardString(this.linkText);
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      if(this.showSecurityWarning) {
         this.drawCenteredString(this.fontRendererObj, this.openLinkWarning, this.width / 2, 110, 16764108);
      }

   }

   public void disableSecurityWarning() {
      this.showSecurityWarning = false;
   }

   private static IOException b(IOException var0) {
      return var0;
   }
}
