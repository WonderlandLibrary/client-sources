package net.augustus.clickgui.buttons;

import java.awt.Color;
import net.augustus.modules.Categorys;
import net.augustus.utils.interfaces.MC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class CategoryButton extends GuiButton implements MC {
   private final Color color;
   private Categorys category;
   private double[] cm = new double[4];
   private boolean unfolded = true;

   public CategoryButton(int id, int x, int y, int width, int height, String message, Color color, Categorys category) {
      super(id, x, y, width, height, message);
      this.color = color;
      this.category = category;
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

   public void click(double mouseX, double mouseY, int button) {
      if (this.visible && this.enabled) {
         if (this.isMouseOver(mouseX, mouseY)) {
            this.cm = new double[]{mouseX, mouseY, (double)this.xPosition, (double)this.yPosition};
         }

         if (button == 1) {
            boolean bl = this.mousePressed(mc, (int)mouseX, (int)mouseY);
            if (bl) {
               this.playPressSound(mc.getSoundHandler());
               this.toggleUnfolded();
            }
         }
      }
   }

   public void toggleUnfolded() {
      this.unfolded = !this.unfolded;
   }

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
      FontRenderer fontrenderer = mc.fontRendererObj;
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.blendFunc(770, 771);
      drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(41, 146, 222).getRGB());
      this.drawCenteredString(
         fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, this.getHoverColor(this.color, -0.2)
      );
   }

   public double[] getCm() {
      return this.cm;
   }

   public void setCm(double[] cmx) {
      this.cm = cmx;
   }

   public Categorys getCategory() {
      return this.category;
   }

   public void setCategory(Categorys category) {
      this.category = category;
   }

   public boolean isUnfolded() {
      return this.unfolded;
   }

   public void setUnfolded(boolean unfolded) {
      this.unfolded = unfolded;
   }

   public boolean isMouseOver(double mouseX, double mouseY) {
      return this.visible
         && mouseX >= (double)this.xPosition
         && mouseY >= (double)this.yPosition
         && mouseX < (double)(this.xPosition + this.width)
         && mouseY < (double)(this.yPosition + this.height);
   }
}
