package net;

import java.awt.Color;
import net.CI;
import net.J_;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.MathHelper;

public class afm extends GuiTextField {
   private final String A;

   public afm(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.A = var7;
   }

   public void m() {
      int[] var1 = CI.b();
      if(this.getVisible()) {
         J_.a.b(this.A, this.q - (float)J_.a.a(this.A) - 5.0F, this.k + 5.0F, (new Color(198, 198, 198)).getRGB());
         a((double)this.q, (double)(this.k + 15.0F), (double)(this.q + (float)this.width), (double)(this.k + 16.0F), (new Color(198, 198, 198)).getRGB());
         int var2 = this.isEnabled?this.enabledColor:this.disabledColor;
         int var3 = this.cursorPosition - this.m;
         int var4 = this.v - this.m;
         String var5 = J_.a.a(this.text.substring(this.m), this.getWidth());
         boolean var6 = var3 >= 0 && var3 <= var5.length();
         boolean var7 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var6;
         int var8 = this.enableBackgroundDrawing?(int)this.q + 4:(int)this.q;
         int var9 = this.enableBackgroundDrawing?(int)this.k + (this.height - 8) / 2:(int)this.k;
         int var10 = var8;
         if(var4 > var5.length()) {
            var4 = var5.length();
         }

         if(!var5.isEmpty()) {
            String var11 = var5.substring(0, var3);
            var10 = (int)J_.a.a(var11, (double)((float)var8), (double)((float)var9), var2, true);
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
            J_.a.a(var5.substring(var3), (double)((float)var10), (double)((float)var9), var2, true);
         }

         if(var7) {
            if(var14) {
               Gui.drawRect(var12, var9 - 1, var12 + 1, var9 + 1 + this.fontRendererInstance.f(), -3092272);
            }

            J_.a.a("_", (double)((float)var12), (double)((float)var9), var2, true);
         }

         if(var4 != var3) {
            int var13 = var8 + J_.a.a(var5.substring(0, var4));
            this.drawCursorVertical(var12, var9 - 1, var13 - 1, var9 + 1 + this.fontRendererInstance.f());
         }
      }

   }

   public void mouseClicked(int var1, int var2, int var3) {
      int[] var4 = CI.b();
      boolean var5 = (float)var1 >= this.q && (float)var1 < this.q + (float)this.width && (float)var2 >= this.k && (float)var2 < this.k + (float)this.height;
      if(this.canLoseFocus) {
         this.setFocused(var5);
      }

      if(this.isFocused && var5 && var3 == 0) {
         int var6 = var1 - (int)this.q;
         if(this.enableBackgroundDrawing) {
            var6 -= 4;
         }

         String var7 = J_.a.a(this.text.substring(this.m), this.getWidth());
         this.setCursorPosition(J_.a.a(var7, var6).length() + this.m);
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

      this.v = var1;
      if(this.m > var3) {
         this.m = var3;
      }

      int var4 = this.getWidth();
      String var5 = J_.a.a(this.text.substring(this.m), var4);
      int var6 = var5.length() + this.m;
      if(var1 == this.m) {
         this.m -= J_.a.a(this.text, var4, true).length();
      }

      if(var1 > var6) {
         this.m += var1 - var6;
      }

      if(var1 <= this.m) {
         this.m -= this.m - var1;
      }

      this.m = MathHelper.a(this.m, 0, var3);
   }
}
