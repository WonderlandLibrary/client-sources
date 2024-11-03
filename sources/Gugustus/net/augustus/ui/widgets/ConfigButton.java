package net.augustus.ui.widgets;

import java.awt.Color;
import net.augustus.Augustus;
import net.augustus.utils.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ConfigButton extends GuiButton {
   private final Color color;
   private String date;
   private String time;
   private boolean selected;
   private float mouseDelta;

   public ConfigButton(int id, int x, int y, int width, int height, String name, String date, String time, Color color) {
      super(id, x, y, width, height, name);
      this.color = color;
      this.date = date;
      this.time = time;
   }

   public void draw(Minecraft mc, int mouseX, int mouseY) {
      if (this.visible) {
         FontRenderer fontrenderer = mc.fontRendererObj;
         mc.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         drawRect(
            this.xPosition,
            this.yPosition,
            (int)((float)(this.width + this.xPosition)),
            (int)((float)(this.height + this.yPosition)),
            this.getHoverColor(Augustus.getInstance().getClientColor(), -0.2)
         );
         this.mouseDragged(mc, mouseX, mouseY);
         int j = Color.gray.getRGB();
         if (!this.selected) {
            j = 14737632;
         }

         fontrenderer.drawStringWithShadow(
            this.displayString, (float)(this.xPosition + 5), (float)this.yPosition - (float)fontrenderer.FONT_HEIGHT / 2.0F + (float)this.height / 2.0F, j
         );
         fontrenderer.drawStringWithShadow(
            this.date,
            (float)(this.xPosition + this.width - 5 - fontrenderer.getStringWidth(this.date)),
            (float)this.yPosition - 0.5F - (float)fontrenderer.FONT_HEIGHT + (float)this.height / 2.0F,
            j
         );
         fontrenderer.drawStringWithShadow(
            this.time,
            (float)(this.xPosition + this.width - 5 - fontrenderer.getStringWidth(this.time)),
            (float)this.yPosition + 0.5F + (float)this.height / 2.0F,
            j
         );
         RenderUtil.drawFloatRect(
            (float)this.xPosition, (float)this.yPosition, (float)(this.xPosition + this.width), (float)this.yPosition - 0.5F, Color.black.getRGB()
         );
         RenderUtil.drawFloatRect(
            (float)this.xPosition,
            (float)(this.yPosition + this.height) - 0.5F,
            (float)(this.xPosition + this.width),
            (float)(this.yPosition + this.height),
            Color.black.getRGB()
         );
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

   public String getDate() {
      return this.date;
   }

   public void setDate(String date) {
      this.date = date;
   }

   public String getTime() {
      return this.time;
   }

   public void setTime(String time) {
      this.time = time;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
