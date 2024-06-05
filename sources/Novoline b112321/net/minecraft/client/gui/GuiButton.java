package net.minecraft.client.gui;

import cc.novoline.utils.ColorUtils;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.impl.Fonts$SFTHIN$SFTHIN_19;
import java.awt.Color;
import net.aHv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class GuiButton extends Gui {
   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
   public double xPosition;
   public double yPosition;
   public String displayString;
   public int id;
   public boolean enabled;
   public boolean visible;
   protected int width;
   protected int height;
   protected boolean hovered;

   public GuiButton(int var1, int var2, int var3, String var4) {
      this(var1, var2, var3, 200, 20, var4);
   }

   public GuiButton(int var1, int var2, int var3, int var4, int var5, String var6) {
      this.enabled = true;
      this.visible = true;
      this.id = var1;
      this.xPosition = (double)var2;
      this.yPosition = (double)var3;
      this.width = var4;
      this.height = var5;
      this.displayString = var6;
   }

   protected int getHoverState(boolean var1) {
      byte var2 = 1;
      if(!this.enabled) {
         var2 = 0;
      } else {
         var2 = 2;
      }

      return var2;
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      if(this.visible) {
         FontRenderer var4 = var1.fontRendererObj;
         var1.getTextureManager().bindTexture(buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.hovered = (double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height;
         int var5 = this.getHoverState(this.hovered);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.blendFunc(770, 771);
         int var6 = this.hovered?-13684945:-13684426;
         int var7 = ColorUtils.getColor(0, 0, 0, this.hovered?140:180);
         if(var1.currentScreen instanceof aHv) {
            RenderUtils.drawRoundedRect((float)((int)this.xPosition), (float)((int)this.yPosition + 1), (float)this.width, 17.0F, 8.0F, (new Color(36, 36, 36, 255)).getRGB());
         } else {
            this.drawTexturedModalRect((int)this.xPosition, (int)this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect((int)this.xPosition + this.width / 2, (int)this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
         }

         this.mouseDragged(var1, var2, var3);
         int var8;
         if(this.hovered && this.enabled) {
            var8 = -2631816;
         } else {
            var8 = -2631721;
         }

         if(var1.currentScreen instanceof aHv) {
            Fonts$SFTHIN$SFTHIN_19.SFTHIN_19.drawCenteredString(this.displayString, (float)((int)this.xPosition) + (float)this.width / 2.0F + 0.5F, (float)((int)this.yPosition) + (float)(this.height - 6) / 2.0F, var8, true);
         } else {
            this.drawCenteredString(var4, this.displayString, (int)(this.xPosition + (double)(this.width / 2) + 0.5D), (int)this.yPosition + (this.height - 8) / 2, var8);
         }
      }

   }

   protected void mouseDragged(Minecraft var1, int var2, int var3) {
   }

   public void mouseReleased(int var1, int var2) {
   }

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      return this.enabled && this.visible && (double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height;
   }

   public boolean isMouseOver() {
      return this.hovered;
   }

   public void drawButtonForegroundLayer(int var1, int var2) {
   }

   public void playPressSound(@NotNull SoundHandler var1) {
      var1.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
   }

   public int getButtonWidth() {
      return this.width;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public void setDisplayString(String var1) {
      this.displayString = var1;
   }

   public String getDisplayString() {
      return this.displayString;
   }
}
