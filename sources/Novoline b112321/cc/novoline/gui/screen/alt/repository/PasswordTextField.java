package cc.novoline.gui.screen.alt.repository;

import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_16;
import java.awt.Color;
import net.CI;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;

public class PasswordTextField extends GuiTextField {
   private final String shit;

   public PasswordTextField(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.shit = var7;
   }

   public void drawTextBox() {
      int[] var1 = CI.b();
      if(this.visible) {
         Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.drawString(this.shit, this.xPosition - (float)Fonts$SFBOLD$SFBOLD_16.SFBOLD_16.stringWidth(this.shit) - 5.0F, this.yPosition + 5.0F, (new Color(198, 198, 198)).getRGB());
         drawRect((double)this.xPosition, (double)(this.yPosition + 15.0F), (double)(this.xPosition + (float)this.width), (double)(this.yPosition + 16.0F), (new Color(198, 198, 198)).getRGB());
         int var2 = this.isEnabled?this.enabledColor:this.disabledColor;
         int var3 = this.cursorPosition - this.lineScrollOffset;
         int var4 = this.selectionEnd - this.lineScrollOffset;
         String var5 = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset).replaceAll("(?s).", "*"), this.getWidth());
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
            var10 = this.fontRendererInstance.drawStringWithShadow(var11, (float)var8, (float)var9, var2);
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
            this.fontRendererInstance.drawStringWithShadow(var5.substring(var3), (float)var10, (float)var9, var2);
         }

         if(var7) {
            if(var14) {
               Gui.drawRect(var12, var9 - 1, var12 + 1, var9 + 1 + this.fontRendererInstance.getHeight(), -3092272);
            }

            this.fontRendererInstance.drawStringWithShadow("_", (float)var12, (float)var9, var2);
         }

         if(var4 != var3) {
            int var13 = var8 + this.fontRendererInstance.d(var5.substring(0, var4));
            this.drawCursorVertical(var12, var9 - 1, var13 - 1, var9 + 1 + this.fontRendererInstance.getHeight());
         }
      }

   }
}
