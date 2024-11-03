package net.minecraft.client.gui;

import java.awt.Color;

import net.augustus.Augustus;
import net.augustus.ui.augustusmanager.AugustusSounds;
import net.augustus.utils.skid.lorious.ColorUtils;
import net.augustus.utils.skid.lorious.MouseUtil;
import net.augustus.utils.skid.lorious.RectUtils;
import net.augustus.utils.skid.lorious.anims.ColorAnimation;
import net.augustus.utils.sound.SoundUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   protected int width = 200;
   protected int height = 20;
   public int xPosition;
   public int yPosition;
   public String displayString;
   public int id;
   public boolean enabled = true;
   public boolean visible = true;
   protected boolean hovered;
   public ColorAnimation backgroundColor = new ColorAnimation();

   public int initX;

   public int initY;


   public GuiButton(int buttonId, int x, int y, String buttonText) {
      this(buttonId, x, y, 200, 20, buttonText);
   }

   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      this.id = buttonId;
      this.xPosition = x;
      this.yPosition = y;
      this.width = widthIn;
      this.height = heightIn;
      this.displayString = buttonText;
   }

   protected int getHoverState(boolean mouseOver) {
      int i = 1;
      if (!this.enabled) {
         i = 0;
      } else if (mouseOver) {
         i = 2;
      }

      return i;
   }

   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
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

   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
   }

   public void mouseReleased(int mouseX, int mouseY) {
   }

   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      return this.enabled
              && (this.visible)
              && mouseX >= this.xPosition
              && mouseY >= this.yPosition
              && mouseX < this.xPosition + this.width
              && mouseY < this.yPosition + this.height;
   }

   public boolean isMouseOver() {
      return this.hovered;
   }

   public void drawButtonForegroundLayer(int mouseX, int mouseY) {
   }

   public void playPressSound(SoundHandler soundHandlerIn) {
      String var2 = AugustusSounds.currentSound;
      switch(var2) {
         case "Vanilla":
            soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
            break;
         case "Sigma":
            SoundUtil.play(SoundUtil.button);
      }
   }

   public int getButtonWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setWidth(int width) {
      this.width = width;
   }
}
