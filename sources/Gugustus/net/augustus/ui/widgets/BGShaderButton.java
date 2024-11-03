package net.augustus.ui.widgets;

import java.awt.Color;
import net.augustus.Augustus;
import net.lenni0451.eventapi.manager.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class BGShaderButton extends GuiButton {
   private final Color color;
   private final Color toggledColor;

   public BGShaderButton(int id, int x, int y, int width, int height, String message, Color color, Color toggledColor) {
      super(id, x, y, width, height, message);
      this.color = color;
      this.toggledColor = toggledColor;
      EventManager.register(this);
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if (this.visible) {
         ScaledResolution sr = new ScaledResolution(mc);
         int windowHeight = sr.getScaledHeight();
         double scale = (double)sr.getScaleFactor();
         int scaledWidth = (int)((double)this.width * scale);
         int scaledHeight = (int)((double)this.height * scale);
         FontRenderer fontrenderer = mc.fontRendererObj;
         mc.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         this.mouseDragged(mc, mouseX, mouseY);
         int y = (int)((float)this.yPosition + ((float)this.height - 8.0F) / 2.0F);
         if (Augustus.getInstance().getBackgroundShaderUtil().getCurrentShader().getName().equalsIgnoreCase(this.displayString)) {
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, y, this.getHoverColor(this.toggledColor, 0.2));
         } else {
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, y, this.getHoverColor(this.color, -0.2));
         }
      }
   }

   private int getHoverColor(Color color, double addBrightness) {
      int colorRGB;
      if (this.hovered) {
         float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         float f = (float)((double)hsbColor[2] + addBrightness);
         if ((double)hsbColor[2] + addBrightness > 1.0) {
            f = 1.0F;
         } else if ((double)hsbColor[2] + addBrightness < 0.0) {
            f = 0.0F;
         }

         colorRGB = Color.HSBtoRGB(hsbColor[0], hsbColor[1], f);
      } else {
         colorRGB = color.getRGB();
      }

      return colorRGB;
   }
}
