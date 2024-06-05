package cc.novoline.gui.screen.login.textbox;

import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.Timer;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_16;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;
import java.awt.Color;
import net.acE;
import net.pD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class UIDField extends GuiTextField {
   private final String shit;
   private int color;
   private int textColor;
   private final Timer timer = new Timer();

   public UIDField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.shit = var7;
   }

   public void drawTextBox() {
      boolean var1 = pD.c();
      if(this.getVisible()) {
         ScaledResolution var2 = new ScaledResolution(Minecraft.getInstance());
         int var3 = var2.getScaledWidth();
         int var4 = var2.getScaledHeight();
         int var5 = Mouse.getX() * var3 / Minecraft.getInstance().displayWidth;
         int var6 = var4 - Mouse.getY() * var4 / Minecraft.getInstance().displayHeight - 1;
         boolean var7 = (float)var5 >= this.xPosition && (float)var6 >= this.yPosition && (float)var5 < this.xPosition + (float)this.width && (float)var6 < this.yPosition + (float)this.height;
         RenderUtils.drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + (float)this.width, this.yPosition + (float)this.height, 0.1F, this.color != -1 || !var7 && !this.isFocused?(new Color(0, 0, 0, 50)).getRGB():(new Color(0, 0, 0, 100)).getRGB(), (new Color(0, 0, 0, 50)).getRGB());
         Fonts$SF$SF_16.SF_16.drawString("User-ID", this.xPosition + 5.0F, this.yPosition + 5.0F, this.textColor);
         int var8 = this.isEnabled?this.enabledColor:this.disabledColor;
         int var9 = this.cursorPosition - this.lineScrollOffset;
         int var10 = this.selectionEnd - this.lineScrollOffset;
         String var11 = Fonts$SF$SF_20.SF_20.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         boolean var12 = var9 >= 0 && var9 <= var11.length();
         boolean var13 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var12;
         int var14 = this.enableBackgroundDrawing?(int)this.xPosition + 4:(int)this.xPosition;
         int var15 = this.enableBackgroundDrawing?(int)this.yPosition + (this.height - 8) / 2:(int)this.yPosition;
         int var16 = var14;
         if(var10 > var11.length()) {
            var10 = var11.length();
         }

         if(!var11.isEmpty()) {
            String var17 = var11.substring(0, var9);
            var16 = (int)Fonts$SF$SF_20.SF_20.drawString(var17, (double)(this.xPosition + 5.0F), (double)(this.yPosition + 17.0F), this.textColor, this.color != -1);
         }

         boolean var20 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
         int var18 = var16;
         if(!var12) {
            var18 = var9 > 0?var14 + this.width:var14;
         }

         if(var20) {
            var18 = var16 - 1;
            --var16;
         }

         if(!var11.isEmpty() && var12 && var9 < var11.length()) {
            Fonts$SF$SF_20.SF_20.drawString(var11.substring(var9), (double)((float)var16), (double)((float)var15), var8, true);
         }

         if(var13) {
            if(var20) {
               Gui.drawRect(var18, var15 - 1, var18 + 1, var15 + 1 + this.fontRendererInstance.getHeight(), -3092272);
            }

            Fonts$SF$SF_20.SF_20.drawString("_", (double)(this.xPosition + 5.0F + (float)(this.getText().isEmpty()?0:Fonts$SF$SF_20.SF_20.stringWidth(this.getText()) + 1)), (double)(this.yPosition + 17.0F), var8, true);
         }

         if(var10 != var9) {
            int var19 = var14 + Fonts$SF$SF_20.SF_20.stringWidth(var11.substring(0, var10));
            this.drawCursorVertical(var18, var15 - 1, var19 - 1, var15 + 1 + this.fontRendererInstance.getHeight());
         }
      }

   }

   public void setColor(int var1) {
      this.color = var1;
   }

   public void setTextColor(int var1) {
      this.textColor = var1;
   }

   public void mouseClicked(int var1, int var2, int var3) {
      boolean var4 = pD.c();
      boolean var5 = (float)var1 >= this.xPosition && (float)var1 < this.xPosition + (float)this.width && (float)var2 >= this.yPosition && (float)var2 < this.yPosition + (float)this.height;
      if(this.canLoseFocus) {
         this.setFocused(var5);
      }

      if(this.isFocused && var5 && var3 == 0) {
         int var6 = var1 - (int)this.xPosition;
         if(this.enableBackgroundDrawing) {
            var6 -= 4;
         }

         String var7 = Fonts$SF$SF_20.SF_20.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         this.setCursorPosition(Fonts$SF$SF_20.SF_20.trimStringToWidth(var7, var6).length() + this.lineScrollOffset);
      }

      if(acE.b() == null) {
         pD.b(false);
      }

   }

   public void setSelectionPos(int var1) {
      pD.b();
      int var3 = this.text.length();
      if(var1 > var3) {
         var1 = var3;
      }

      if(var1 < 0) {
         var1 = 0;
      }

      this.selectionEnd = var1;
      if(this.lineScrollOffset > var3) {
         this.lineScrollOffset = var3;
      }

      int var4 = this.getWidth();
      String var5 = Fonts$SF$SF_20.SF_20.trimStringToWidth(this.text.substring(this.lineScrollOffset), var4);
      int var6 = var5.length() + this.lineScrollOffset;
      if(var1 == this.lineScrollOffset) {
         this.lineScrollOffset -= Fonts$SF$SF_20.SF_20.trimStringToWidth(this.text, var4, true).length();
      }

      if(var1 > var6) {
         this.lineScrollOffset += var1 - var6;
      }

      if(var1 <= this.lineScrollOffset) {
         this.lineScrollOffset -= this.lineScrollOffset - var1;
      }

      this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, var3);
   }

   public void updateCoordinates(float var1, float var2) {
      this.xPosition = var1;
      this.yPosition = var2;
   }
}
