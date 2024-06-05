package cc.novoline.gui.screen.alt.repository;

import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_16;
import java.awt.Color;
import net.CI;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.MathHelper;

public class TokenField extends GuiTextField {
   private final String shit;

   public TokenField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.shit = var7;
   }

   public void drawTextBox() {
      int[] var1 = CI.b();
      if(this.getVisible()) {
         Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.drawString(this.shit, this.xPosition - (float)Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.stringWidth(this.shit) - 5.0F, this.yPosition + 5.0F, (new Color(198, 198, 198)).getRGB());
         drawRect((double)this.xPosition, (double)(this.yPosition + 15.0F), (double)(this.xPosition + (float)this.width), (double)(this.yPosition + 16.0F), (new Color(198, 198, 198)).getRGB());
         int var2 = this.isEnabled?this.enabledColor:this.disabledColor;
         int var3 = this.cursorPosition - this.lineScrollOffset;
         int var4 = this.selectionEnd - this.lineScrollOffset;
         String var5 = Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         boolean var6 = var3 >= 0 && var3 <= var5.length();
         boolean var7 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var6;
         int var8 = this.enableBackgroundDrawing?(int)this.xPosition + 4:(int)this.xPosition;
         int var9 = this.enableBackgroundDrawing?(int)this.yPosition + (this.height - 8) / 2:(int)this.yPosition;
         int var10 = var8;
         if(var4 > var5.length()) {
            var4 = var5.length();
         }

         if(!var5.isEmpty()) {
            String var11 = var5.substring(0, var3);
            var10 = (int)Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.drawString(var11, (double)((float)var8), (double)((float)var9), var2, true);
         }

         boolean var14 = this.cursorPosition < this.text.length() || this.text.length() >= this.getMaxStringLength();
         int var12 = var10;
         if(!var6) {
            var12 = var3 > 0?var8 + this.width:var8;
         }

         if(var14) {
            var12 = var10 - 1;
            --var10;
         }

         if(!var5.isEmpty() && var6 && var3 < var5.length()) {
            Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.drawString(var5.substring(var3), (double)((float)var10), (double)((float)var9), var2, true);
         }

         if(var7) {
            if(var14) {
               Gui.drawRect(var12, var9 - 1, var12 + 1, var9 + 1 + this.fontRendererInstance.getHeight(), -3092272);
            }

            Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.drawString("_", (double)((float)var12), (double)((float)var9), var2, true);
         }

         if(var4 != var3) {
            int var13 = var8 + Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.stringWidth(var5.substring(0, var4));
            this.drawCursorVertical(var12, var9 - 1, var13 - 1, var9 + 1 + this.fontRendererInstance.getHeight());
         }
      }

   }

   public void mouseClicked(int var1, int var2, int var3) {
      int[] var4 = CI.b();
      boolean var5 = (float)var1 >= this.xPosition && (float)var1 < this.xPosition + (float)this.width && (float)var2 >= this.yPosition && (float)var2 < this.yPosition + (float)this.height;
      if(this.canLoseFocus) {
         this.setFocused(var5);
      }

      if(this.isFocused && var5 && var3 == 0) {
         int var6 = var1 - (int)this.xPosition;
         if(this.enableBackgroundDrawing) {
            var6 -= 4;
         }

         String var7 = Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.trimStringToWidth(this.text.substring(this.lineScrollOffset), this.getWidth());
         this.setCursorPosition(Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.trimStringToWidth(var7, var6).length() + this.lineScrollOffset);
      }

   }

   public void setSelectionPos(int var1) {
      CI.b();
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
      String var5 = Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.trimStringToWidth(this.text.substring(this.lineScrollOffset), var4);
      int var6 = var5.length() + this.lineScrollOffset;
      if(var1 == this.lineScrollOffset) {
         this.lineScrollOffset -= Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.trimStringToWidth(this.text, var4, true).length();
      }

      if(var1 > var6) {
         this.lineScrollOffset += var1 - var6;
      }

      if(var1 <= this.lineScrollOffset) {
         this.lineScrollOffset -= this.lineScrollOffset - var1;
      }

      this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, var3);
   }
}
