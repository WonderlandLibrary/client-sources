package net.minecraft.client.gui;

import java.io.IOException;
import net.minecraft.client.resources.I18n;

public class GuiConfirmOpenLink extends GuiYesNo {
   private final String openLinkWarning;
   private boolean showSecurityWarning = true;
   private final String linkText;
   private static final String __OBFID = "CL_00000683";
   private final String copyLinkButtonText;

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id == 2) {
         this.copyLinkToClipboard();
      }

      this.parentScreen.confirmClicked(var1.id == 0, this.parentButtonClickedId);
   }

   public void initGui() {
      this.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, this.confirmButtonText));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 50, this.height / 6 + 96, 100, 20, this.copyLinkButtonText));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, this.cancelButtonText));
   }

   public GuiConfirmOpenLink(GuiYesNoCallback var1, String var2, int var3, boolean var4) {
      super(var1, I18n.format(var4 ? "chat.link.confirmTrusted" : "chat.link.confirm"), var2, var3);
      this.confirmButtonText = I18n.format(var4 ? "chat.link.open" : "gui.yes");
      this.cancelButtonText = I18n.format(var4 ? "gui.cancel" : "gui.no");
      this.copyLinkButtonText = I18n.format("chat.copy");
      this.openLinkWarning = I18n.format("chat.link.warning");
      this.linkText = var2;
   }

   public void copyLinkToClipboard() {
      setClipboardString(this.linkText);
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      if (this.showSecurityWarning) {
         this.drawCenteredString(this.fontRendererObj, this.openLinkWarning, this.width / 2, 110, 16764108);
      }

   }

   public void disableSecurityWarning() {
      this.showSecurityWarning = false;
   }
}
