package net.augustus.ui.widgets;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.lorious.MouseUtil;
import net.augustus.utils.skid.lorious.RectUtils;
import net.augustus.utils.skid.lorious.anims.ColorAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class CustomButton extends GuiButton {
   private final Color color;
   private boolean lastHovered = false;
   private int anim;
   private int anim2;
   public ColorAnimation backgroundColor = new ColorAnimation();

   public int initX;

   public int initY;

   public CustomButton(int id, int x, int y, int width, int height, String message, Color color) {
      super(id, x, y, width, height, message);
      this.color = color;
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

   @Override
   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      /*
      if (this.visible) {
         FontRenderer fontrenderer = mc.fontRendererObj;
         mc.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         int i = this.getHoverState(this.hovered);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         // animation :)
         if(!lastHovered && hovered) {
            lastHovered = true;
            anim = 0;
            anim2 = 20;
         }
         if(lastHovered && !hovered) {
            lastHovered = false;
         }
         if(hovered) {
            if(anim2 >= 5) {
               anim2 -= 0.1;
            }
            anim += anim2;
            if(anim > width) {
               anim = width;
            }
         } else {
            if(anim2 >= 5) {
               anim2 -= 0.1;
            }
            anim -= anim2;
            if(anim < 0) {
               anim = 0;
            }
         }

         drawRect(
                 this.xPosition,
                 this.yPosition,
                 (int)(anim + this.xPosition),
                 (int)((float)(this.height + this.yPosition)),
                 this.getHoverColor(this.color, -0.2)
         );

         this.mouseDragged(mc, mouseX, mouseY);
         int j = 14737632;
         if (!this.enabled) {
            j = 10526880;
         } else if (this.hovered) {
            j = 16777120;
         }

         this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
      }
       */
      if(visible) {
         if (isHovered(mouseX, mouseY)) {
            if (!this.backgroundColor.getTarget().equals(new Color(0, 0, 0, 175)))
               this.backgroundColor.animate(300.0D, new Color(0, 0, 0, 175));
         } else if (!this.backgroundColor.getTarget().equals(new Color(15, 15, 15, 100))) {
            this.backgroundColor.animate(300.0D, new Color(15, 15, 15, 100));
         }
         RectUtils.drawRoundedRect(xPosition + this.initX, yPosition + this.initY, xPosition + this.initX + this.width, yPosition + this.initY + this.height, 3, this.backgroundColor.getColor().getRGB());
         Augustus.getInstance().getLoriousFontService().getComfortaa18().drawCenteredString(this.displayString, (xPosition + this.initX + (float) this.width / 2), (yPosition + this.initY + (float) this.height / 2 - (float) Augustus.getInstance().getLoriousFontService().getComfortaa18().getHeight() / 2), -1, isHovered(mouseX, mouseY), ColorUtils.getRainbow(4.0F, 0.5F, 1.0F), new Color(255, 255, 255));
      }
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return MouseUtil.isHovered(mouseX, mouseY, (this.xPosition + this.initX), (this.yPosition + this.initY), this.width, this.height);
   }
}
