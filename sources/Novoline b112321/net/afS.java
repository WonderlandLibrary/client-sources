package net;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import java.awt.Color;
import net.a6_;
import net.aZM;
import net.abf;
import net.d3;
import net.pD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Mouse;

public class afS extends GuiTextField {
   private final String C;
   private int D;
   private int A;
   private final d3 B = new d3();

   public afS(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, String var7) {
      super(var1, var2, var3, var4, var5, var6);
      this.C = var7;
   }

   public void m() {
      boolean var1 = pD.c();
      if(this.getVisible()) {
         ScaledResolution var2 = new ScaledResolution(Minecraft.getMinecraft());
         int var3 = var2.getScaledWidth();
         int var4 = var2.getScaledHeight();
         int var5 = Mouse.getX() * var3 / Minecraft.getMinecraft().displayWidth;
         int var6 = var4 - Mouse.getY() * var4 / Minecraft.getMinecraft().displayHeight - 1;
         boolean var7 = (float)var5 >= this.q && (float)var6 >= this.k && (float)var5 < this.q + (float)this.width && (float)var6 < this.k + (float)this.height;
         a6_.a(this.q, this.k, this.q + (float)this.width, this.k + (float)this.height, 0.1F, this.D != -1 || !var7 && !this.isFocused?(new Color(0, 0, 0, 50)).getRGB():(new Color(0, 0, 0, 100)).getRGB(), (new Color(0, 0, 0, 50)).getRGB());
         aZM.a.b("User-ID", this.q + 5.0F, this.k + 5.0F, this.A);
         int var8 = this.isEnabled?this.enabledColor:this.disabledColor;
         int var9 = this.cursorPosition - this.m;
         int var10 = this.v - this.m;
         String var11 = abf.a.a(this.text.substring(this.m), this.getWidth());
         boolean var12 = var9 >= 0 && var9 <= var11.length();
         boolean var13 = this.isFocused && this.cursorCounter / 6 % 2 == 0 && var12;
         int var14 = this.enableBackgroundDrawing?(int)this.q + 4:(int)this.q;
         int var15 = this.enableBackgroundDrawing?(int)this.k + (this.height - 8) / 2:(int)this.k;
         int var16 = var14;
         if(var10 > var11.length()) {
            var10 = var11.length();
         }

         if(!var11.isEmpty()) {
            String var17 = var11.substring(0, var9);
            var16 = (int)abf.a.a(var17, (double)(this.q + 5.0F), (double)(this.k + 17.0F), this.A, this.D != -1);
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
            abf.a.a(var11.substring(var9), (double)((float)var16), (double)((float)var15), var8, true);
         }

         if(var13) {
            if(var20) {
               Gui.drawRect(var18, var15 - 1, var18 + 1, var15 + 1 + this.fontRendererInstance.f(), -3092272);
            }

            abf.a.a("_", (double)(this.q + 5.0F + (float)(this.getText().isEmpty()?0:abf.a.a(this.getText()) + 1)), (double)(this.k + 17.0F), var8, true);
         }

         if(var10 != var9) {
            int var19 = var14 + abf.a.a(var11.substring(0, var10));
            this.drawCursorVertical(var18, var15 - 1, var19 - 1, var15 + 1 + this.fontRendererInstance.f());
         }
      }

   }

   public void a(int var1) {
      this.D = var1;
   }

   public void setTextColor(int var1) {
      this.A = var1;
   }

   public void mouseClicked(int var1, int var2, int var3) {
      boolean var4 = pD.c();
      boolean var5 = (float)var1 >= this.q && (float)var1 < this.q + (float)this.width && (float)var2 >= this.k && (float)var2 < this.k + (float)this.height;
      if(this.canLoseFocus) {
         this.setFocused(var5);
      }

      if(this.isFocused && var5 && var3 == 0) {
         int var6 = var1 - (int)this.q;
         if(this.enableBackgroundDrawing) {
            var6 -= 4;
         }

         String var7 = abf.a.a(this.text.substring(this.m), this.getWidth());
         this.setCursorPosition(abf.a.a(var7, var6).length() + this.m);
      }

      if(PacketRemapper.b() == null) {
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

      this.v = var1;
      if(this.m > var3) {
         this.m = var3;
      }

      int var4 = this.getWidth();
      String var5 = abf.a.a(this.text.substring(this.m), var4);
      int var6 = var5.length() + this.m;
      if(var1 == this.m) {
         this.m -= abf.a.a(this.text, var4, true).length();
      }

      if(var1 > var6) {
         this.m += var1 - var6;
      }

      if(var1 <= this.m) {
         this.m -= this.m - var1;
      }

      this.m = MathHelper.a(this.m, 0, var3);
   }

   public void a(float var1, float var2) {
      this.q = var1;
      this.k = var2;
   }
}
