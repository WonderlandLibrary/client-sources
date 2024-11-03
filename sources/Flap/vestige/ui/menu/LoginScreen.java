package vestige.ui.menu;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.shaders.impl.GaussianBlur;
import vestige.ui.menu.components.Button;
import vestige.ui.menu.components.TextFielde;
import vestige.util.misc.AudioUtil;
import vestige.util.render.ColorUtil2;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;

public class LoginScreen extends GuiScreen {
   private final Button[] buttons = new Button[]{new Button("Login")};
   private VestigeFontRenderer font;
   private static String status;
   public TextFielde username;
   private final int textColor = (new Color(220, 220, 220)).getRGB();
   private VestigeFontRenderer productSans;
   private int ravenXD$hoverValue1;
   private int ravenXD$hoverValue2;

   public void initGui() {
      Flap.getDiscordRP().update("Logging on Flap Client...", "Security");
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonHeight = 20;
      int totalHeight = buttonHeight * this.buttons.length;
      int y = Math.max(sr.getScaledHeight() / 2 - totalHeight / 2 - 50, 90);
      this.username = new TextFielde(0, this.mc.fontRendererObj, sr.getScaledWidth() / 2 - 90, y, 180, 25);
      this.font = Flap.instance.getFontManager().getProductSans();
      Button[] var5 = this.buttons;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Button button = var5[var7];
         button.updateState(false);
         button.setAnimationDone(true);
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonWidth = 140;
      int buttonHeight = 25;
      int totalHeighte = buttonHeight * this.buttons.length;
      double ye = (double)(sr.getScaledHeight() / 2) - (double)totalHeighte * 0.3D;
      Math.max(ye - 70.0D, 25.0D);
      DrawUtil.drawImage(new ResourceLocation("flap/imagens/background.png"), 0, 0, this.mc.displayWidth / 2, this.mc.displayHeight / 2);
      GaussianBlur.startBlur();
      RenderUtils2.drawBloomShadow(0.0F, 0.0F, 1000.0F, 10000.0F, 6, 4, -1, false);
      GaussianBlur.endBlur(4.0F, 2.0F);
      this.username.drawTextBox();
      int totalHeight = buttonHeight * this.buttons.length;
      double y = Math.max((double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D, 140.0D);
      double titleY = (double)Math.max(sr.getScaledHeight() / 2 - totalHeight / 2 - 110, 20);
      String altLogin = "";
      this.font.drawStringWithShadow(altLogin, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(altLogin) / 2.0D + 5.0D, titleY, -1);
      this.font.drawStringWithShadow(status, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(status) / 2.0D, titleY + 25.0D, -1);
      Color rectColor = new Color(10, 10, 10, 100);
      rectColor = flap$interpolateColorC(rectColor, ColorUtil2.brighter(rectColor, 0.4F), -1.0F);
      int startX = sr.getScaledWidth() / 2 - buttonWidth / 2;
      int endX = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Button[] var21 = this.buttons;
      int var22 = var21.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         Button button = var21[var23];
         button.updateState(mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight);
         if (button.getName() == "Login") {
            this.ravenXD$hoverValue1 = button.isHovered() ? (int)Math.min((double)this.ravenXD$hoverValue1 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D) : (int)Math.max((double)this.ravenXD$hoverValue1 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue1);
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            String buttonName = button.getName();
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/user.png"), (int)((double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(buttonName) / 2.0D) - 7, (int)y + 9, 9, 9);
            this.font.drawString(buttonName, (double)(sr.getScaledWidth() / 2) - this.font.getStringWidth(buttonName) / 2.0D + 7.0D, y + 9.0D, this.textColor);
            y += (double)(buttonHeight + 6);
         }
      }

   }

   @NotNull
   private static Color flap$interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(ColorUtil2.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), ColorUtil2.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), ColorUtil2.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), ColorUtil2.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   public void keyTyped(char typedChar, int keyCode) {
      try {
         super.keyTyped(typedChar, keyCode);
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      this.username.textboxKeyTyped(typedChar, keyCode);
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      try {
         super.mouseClicked(mouseX, mouseY, mouseButton);
      } catch (IOException var18) {
         var18.printStackTrace();
      }

      this.username.mouseClicked(mouseX, mouseY, mouseButton);
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonWidth = 100;
      int buttonHeight = 25;
      int totalHeight = buttonHeight * this.buttons.length;
      double y = Math.max((double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D, 140.0D);
      int startX = sr.getScaledWidth() / 2 - buttonWidth / 2;
      int endX = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Button[] var12 = this.buttons;
      int var13 = var12.length;

      for(int var14 = 0; var14 < var13; ++var14) {
         Button button = var12[var14];
         if (mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight) {
            String var16 = button.getName();
            byte var17 = -1;
            switch(var16.hashCode()) {
            case -1705615577:
               if (var16.equals("Coppy Serial")) {
                  var17 = 1;
               }
               break;
            case 73596745:
               if (var16.equals("Login")) {
                  var17 = 0;
               }
            }

            switch(var17) {
            case 0:
               status = "Success, Logged-in";
               this.mc.displayGuiScreen(new VestigeMainMenu());
               break;
            case 1:
               this.mc.shutdown();
            }

            AudioUtil.buttonClick();
         }

         y += (double)buttonHeight;
      }

   }

   public static boolean isHWIDInPastebin(String hwid) {
      return true;
   }
}
