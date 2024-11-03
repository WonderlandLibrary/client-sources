package net.minecraft.client.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.RoundedUtils;

public class GuiButton extends Gui {
   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   protected int width;
   protected int height;
   protected int textAnimation = 255;
   public int xPosition;
   public int yPosition;
   public String displayString;
   public int id;
   public boolean enabled;
   public boolean visible;
   protected boolean hovered;

   public GuiButton(int buttonId, int x, int y, String buttonText) {
      this(buttonId, x, y, 200, 20, buttonText);
   }

   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      this.width = 200;
      this.height = 20;
      this.enabled = true;
      this.visible = true;
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
      if (this.visible) {
         RoundedUtils.drawGradientRectSideways(
            (double)this.xPosition, (double)this.yPosition, (double)this.width, (double)this.height, -13929729, -2404361, 4.0F
         );
         RenderUtils.drawRoundedRect(
            (double)this.xPosition + 1.5,
            (double)this.yPosition + 1.5,
            (double)(this.xPosition + this.width) - 1.5,
            (double)(this.yPosition + this.height) - 1.5,
            new Color(28, 28, 28, this.textAnimation).getRGB(),
            3.0F
         );
         Fonts.getFont("rb-b")
            .drawString(
               this.displayString,
               (double)(this.xPosition + this.width / 2) - Fonts.getFont("rb-b").getWidth(this.displayString) / 2.0,
               (double)((float)(this.yPosition + this.height / 2) - Fonts.getFont("rb-b").getHeight(this.displayString) / 2.0F - 1.0F),
               -1
            );
         FontRenderer fontrenderer = mc.fontRendererObj;
         mc.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
         int i = this.getHoverState(this.hovered);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         this.mouseDragged(mc, mouseX, mouseY);
         int j = 14737632;
         if (!this.enabled) {
            j = 10526880;
         } else if (this.hovered) {
            j = 16777120;
         }

         if (this.isMouseOver()) {
            this.textAnimation = this.textAnimation * 9 / 10;
         } else {
            this.textAnimation = (this.textAnimation * 9 + 255) / 10;
         }
      }
   }

   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
   }

   public void mouseReleased(int mouseX, int mouseY) {
   }

   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
      return this.enabled
         && this.visible
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
      soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public int getButtonWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }
}
