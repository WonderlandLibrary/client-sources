package net.augustus.ui.augustusmanager;

import java.awt.Color;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import net.augustus.Augustus;
import net.augustus.ui.widgets.CustomButton;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

public class AugustusProxy extends GuiScreen {
   private GuiScreen parent;
   public static String type = "Socks4";

   public AugustusProxy(GuiScreen parent) {
      this.parent = parent;
   }

   public GuiScreen start(GuiScreen parent) {
      this.parent = parent;
      return this;
   }

   @Override
   public void initGui() {
      super.initGui();
      ScaledResolution sr = new ScaledResolution(this.mc);
      int scaledWidth = sr.getScaledWidth();
      int scaledHeight = sr.getScaledHeight();
      int startHeight = Math.min(40 + scaledHeight / 7, 135);
      this.buttonList.add(new CustomButton(1, scaledWidth / 2 - 100, startHeight, 200, 20, "ClipboardLogin", Augustus.getInstance().getClientColor()));
      this.buttonList
         .add(new CustomButton(2, scaledWidth / 2 - 100, scaledHeight - scaledHeight / 10, 200, 20, "Back", Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(3, scaledWidth / 2 - 100, startHeight + 30, 200, 20, type, Augustus.getInstance().getClientColor()));
      this.buttonList.add(new CustomButton(4, scaledWidth / 2 - 100, startHeight + 60, 200, 20, "Reset", Augustus.getInstance().getClientColor()));
   }

   @Override
   protected void actionPerformed(GuiButton button) throws IOException {
      if (button.id == 1) {
         String clipboard = GuiScreen.getClipboardString();
         if (clipboard.trim().isEmpty()) {
            Augustus.getInstance().setProxy(null);
         }

         String[] split = clipboard.split(":");
         if (split.length >= 2 && !split[0].contains("@")) {
            String hostname = split[0];
            int port = Integer.parseInt(split[1]);
            InetSocketAddress address = new InetSocketAddress(hostname, port);
            Augustus.getInstance().setProxy(new Proxy(Type.SOCKS, address));
         }
      }

      if (button.id == 2) {
         this.mc.displayGuiScreen(this.parent);
      }

      if (button.id == 3) {
         type = type.equals("Socks4") ? "Socks5" : "Socks4";
         button.displayString = type;
      }

      if (button.id == 4) {
         Augustus.getInstance().setProxy(null);
      }

      super.actionPerformed(button);
   }

   @Override
   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      super.drawBackground(0);
      super.drawScreen(mouseX, mouseY, partialTicks);
      ScaledResolution sr = new ScaledResolution(this.mc);
      GL11.glPushMatrix();
      GL11.glScaled(2.0, 2.0, 1.0);
      this.fontRendererObj
         .drawStringWithShadow(
            "Proxy",
            (float)sr.getScaledWidth() / 4.0F - (float)this.fontRendererObj.getStringWidth("Proxy") / 2.0F,
            10.0F,
            Color.lightGray.getRGB()
         );
      GL11.glScaled(1.0, 1.0, 1.0);
      GL11.glPopMatrix();
      sr = new ScaledResolution(this.mc);
      String status = "Waiting...";
      if (Augustus.getInstance().getProxy() != null) {
         status = "Logged in Proxy";
      }

      this.fontRendererObj
         .drawStringWithShadow(
            status,
            (float)sr.getScaledWidth() / 2.0F - (float)this.fontRendererObj.getStringWidth(status) / 2.0F,
            (float)sr.getScaledHeight() - (float)sr.getScaledHeight() / 10.0F - 20.0F,
            Color.green.getRGB()
         );
   }

   @Override
   protected void keyTyped(char typedChar, int keyCode) throws IOException {
      if (keyCode == 1 && this.mc.theWorld == null) {
         this.mc.displayGuiScreen(this.parent);
      } else {
         super.keyTyped(typedChar, keyCode);
      }
   }
}
