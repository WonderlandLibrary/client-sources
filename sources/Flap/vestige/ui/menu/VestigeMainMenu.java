package vestige.ui.menu;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.module.impl.visual.ClientTheme;
import vestige.shaders.impl.GaussianBlur;
import vestige.ui.menu.components.Button;
import vestige.util.misc.AudioUtil;
import vestige.util.render.ColorUtil2;
import vestige.util.render.DrawUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.ClientSession;

public class VestigeMainMenu extends GuiScreen {
   private final Button[] buttons = new Button[]{new Button("Singleplayer"), new Button("Multiplayer"), new Button("Alt Manager")};
   private final int textColor = (new Color(255, 255, 255)).getRGB();
   private static ClientTheme theme;
   private VestigeFontRenderer productSans;
   private int ravenXD$hoverValue1;
   private int ravenXD$hoverValue2;
   private int ravenXD$hoverValue3;
   private int ravenXD$hoverValue4;
   private int ravenXD$hoverValue5;
   private int ravenXD$hoverValue6;

   public void initGui() {
      Flap.getDiscordRP().update("Idling", "Main Menu");
      Button[] var1 = this.buttons;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Button button = var1[var3];
         button.updateState(false);
         button.setAnimationDone(true);
      }

   }

   protected void actionPerformed(GuiButton button) {
      try {
         super.actionPerformed(button);
      } catch (IOException var3) {
         var3.printStackTrace();
      }

   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      RenderUtils2.drawRoundedRectangle(0.0F, 0.0F, 10000.0F, 10000.0F, 0.0F, (new Color(0, 0, 0, 100)).getRGB());
      VestigeFontRenderer bigFont = Flap.instance.getFontManager().getProductSans19();
      Flap.instance.getFontManager().getProductSansTitle();
      Flap.instance.getFontManager().getProductSan();
      VestigeFontRenderer pequenaa = Flap.instance.getFontManager().getProductSan14();
      VestigeFontRenderer pequenabold = Flap.instance.getFontManager().getProductSanBold();
      VestigeFontRenderer productSansbold = Flap.instance.getFontManager().getProductSansBold30();
      ScaledResolution sr = new ScaledResolution(this.mc);
      int buttonWidth = 140;
      int buttonHeight = 25;
      int totalHeight = buttonHeight * this.buttons.length;
      double y = (double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D;
      double clientNameY = Math.max(y - 70.0D, 25.0D);
      Color rectColor = new Color(10, 10, 10, 100);
      rectColor = flap$interpolateColorC(rectColor, ColorUtil2.brighter(rectColor, 0.4F), -1.0F);
      DrawUtil.drawImage(new ResourceLocation("flap/imagens/background.png"), 0, 0, this.mc.displayWidth / 2, this.mc.displayHeight / 2);
      GaussianBlur.startBlur();
      RenderUtils2.drawBloomShadow(0.0F, 0.0F, 1000.0F, 10000.0F, 6, 4, -1, false);
      GaussianBlur.endBlur(5.0F, 2.0F);
      DrawUtil.drawImage(new ResourceLocation("flap/flap.png"), sr.getScaledWidth() / 2 - 20, (int)clientNameY, 40, 40);
      productSansbold.drawStringWithShadow("Flap", (double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth("Flap") / 2.0D - 5.0D, (double)((int)clientNameY + 40), this.textColor);
      int startX = sr.getScaledWidth() / 2 - buttonWidth / 2;
      int endX = sr.getScaledWidth() / 2 + buttonWidth / 2;
      Button[] var21 = this.buttons;
      int var22 = var21.length;

      for(int var23 = 0; var23 < var22; ++var23) {
         Button button = var21[var23];
         button.updateState(mouseX > startX && mouseX < endX && (double)mouseY > y && (double)mouseY < y + (double)buttonHeight);
         String buttonName;
         if (button.getName() == "Singleplayer") {
            if (button.isHovered()) {
               this.ravenXD$hoverValue1 = (int)Math.min((double)this.ravenXD$hoverValue1 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue1 = (int)Math.max((double)this.ravenXD$hoverValue1 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue1);
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            buttonName = button.getName();
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + buttonName + ".png"), (int)((double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D) - 7, (int)y + 7, 11, 11);
            bigFont.drawString(buttonName, (double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D + 7.0D, y + 9.0D, this.textColor);
            y += (double)(buttonHeight + 6);
         }

         if (button.getName() == "Multiplayer") {
            if (button.isHovered()) {
               this.ravenXD$hoverValue2 = (int)Math.min((double)this.ravenXD$hoverValue2 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue2 = (int)Math.max((double)this.ravenXD$hoverValue2 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue2);
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            buttonName = button.getName();
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + buttonName + ".png"), (int)((double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D) - 7, (int)y + 7, 11, 11);
            bigFont.drawString(buttonName, (double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D + 7.0D, y + 9.0D, this.textColor);
            y += (double)(buttonHeight + 6);
         }

         if (button.getName() == "Alt Manager") {
            if (button.isHovered()) {
               this.ravenXD$hoverValue3 = (int)Math.min((double)this.ravenXD$hoverValue3 + 300.0D / (double)Minecraft.getDebugFPS(), 50.0D);
            } else {
               this.ravenXD$hoverValue3 = (int)Math.max((double)this.ravenXD$hoverValue3 - 300.0D / (double)Minecraft.getDebugFPS(), 0.0D);
            }

            rectColor = new Color(100, 100, 100, this.ravenXD$hoverValue3);
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 0.0D, new Color(0, 0, 0, 0), new Color(100, 100, 100, 100));
            RenderUtils2.drawRoundOutline((double)startX, y, (double)buttonWidth, (double)buttonHeight, 6.5D, 1.5D, rectColor, new Color(30, 30, 30, 0));
            buttonName = button.getName();
            DrawUtil.drawImage(new ResourceLocation("flap/imagens/" + buttonName + ".png"), (int)((double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D) - 7, (int)y + 7, 11, 11);
            bigFont.drawString(buttonName, (double)(sr.getScaledWidth() / 2) - bigFont.getStringWidth(buttonName) / 2.0D + 7.0D, y + 9.0D, this.textColor);
            y += (double)(buttonHeight + 9);
         }
      }

      RenderUtils2.drawRect((double)startX, y, (double)(startX + buttonWidth), y + 0.4D, (new Color(100, 100, 100, 100)).getRGB());
      pequenabold.drawString("Flap Client", (double)startX, y + 9.0D, theme.getColor(1000));
      pequenaa.drawString("Public", (double)startX, y + 20.0D, this.textColor);
      pequenabold.drawString("Version - 1.8", (double)endX - pequenabold.getStringWidth("Version - 1.8"), y + 9.0D, theme.getColor(1000));
      pequenaa.drawString(ClientSession.username, (double)endX - pequenaa.getStringWidth(ClientSession.username), y + 20.0D, -1);
   }

   @NotNull
   private static Color flap$interpolateColorC(@NotNull Color color1, @NotNull Color color2, float amount) {
      if (color1 == null) {
         $$$reportNull$$$0(0);
      }

      if (color2 == null) {
         $$$reportNull$$$0(1);
      }

      amount = Math.min(1.0F, Math.max(0.0F, amount));
      return new Color(ColorUtil2.interpolateInt(color1.getRed(), color2.getRed(), (double)amount), ColorUtil2.interpolateInt(color1.getGreen(), color2.getGreen(), (double)amount), ColorUtil2.interpolateInt(color1.getBlue(), color2.getBlue(), (double)amount), ColorUtil2.interpolateInt(color1.getAlpha(), color2.getAlpha(), (double)amount));
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      try {
         super.mouseClicked(mouseX, mouseY, mouseButton);
      } catch (IOException var18) {
         var18.printStackTrace();
      }

      if (mouseButton == 0) {
         int buttonWidth = 140;
         int buttonHeight = 25;
         int totalHeight = buttonHeight * this.buttons.length;
         ScaledResolution sr = new ScaledResolution(this.mc);
         double y = (double)(sr.getScaledHeight() / 2) - (double)totalHeight * 0.2D;
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
               case -2064742086:
                  if (var16.equals("Multiplayer")) {
                     var17 = 1;
                  }
                  break;
               case -1657361418:
                  if (var16.equals("Alt Manager")) {
                     var17 = 2;
                  }
                  break;
               case -1500504759:
                  if (var16.equals("Singleplayer")) {
                     var17 = 0;
                  }
                  break;
               case 2174270:
                  if (var16.equals("Exit")) {
                     var17 = 4;
                  }
                  break;
               case 415178366:
                  if (var16.equals("Options")) {
                     var17 = 3;
                  }
               }

               switch(var17) {
               case 0:
                  this.mc.displayGuiScreen(new GuiSelectWorld(this));
                  break;
               case 1:
                  this.mc.displayGuiScreen(new GuiMultiplayer(this));
                  break;
               case 2:
                  this.mc.displayGuiScreen(new AltMainMenu());
                  break;
               case 3:
                  this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                  break;
               case 4:
                  this.mc.shutdown();
               }

               AudioUtil.buttonClick();
            }

            y += (double)(buttonHeight + 6);
         }
      }

   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "color1";
         break;
      case 1:
         var10001[0] = "color2";
      }

      var10001[1] = "vestige/ui/menu/VestigeMainMenu";
      var10001[2] = "flap$interpolateColorC";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
