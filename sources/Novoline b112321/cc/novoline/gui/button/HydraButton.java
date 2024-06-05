package cc.novoline.gui.button;

import cc.novoline.gui.button.AbstractButton;
import cc.novoline.utils.fonts.impl.Fonts$SF$SF_20;
import java.awt.Color;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class HydraButton extends GuiButton {
   private int p;
   private int q = Color.WHITE.getRGB();

   public HydraButton(int var1, int var2, int var3, int var4, int var5, String var6) {
      super(var1, var2, var3, var4, var5, var6);
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      acE[] var4 = AbstractButton.a();
      if(this.visible) {
         this.hovered = (double)var2 >= this.xPosition && (double)var3 >= this.yPosition && (double)var2 < this.xPosition + (double)this.width && (double)var3 < this.yPosition + (double)this.height;
         Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + (double)this.width, this.yPosition + (double)this.height, this.p);
         Fonts$SF$SF_20.SF_20.drawString(this.displayString, (double)((float)this.xPosition + (float)(this.width / 2) - (float)(Fonts$SF$SF_20.SF_20.stringWidth(this.displayString) / 2)), (double)((float)this.yPosition + (float)(this.height / 2) - (float)(Fonts$SF$SF_20.SF_20.getHeight() / 2) + 1.0F), this.q, true);
      }

   }

   public void updateCoordinates(float var1, float var2) {
      this.xPosition = (double)var1;
      this.yPosition = (double)var2;
   }

   public void b(int var1) {
      this.p = var1;
   }

   public void c(int var1) {
      this.q = var1;
   }

   public boolean hovered(int var1, int var2) {
      acE[] var3 = AbstractButton.a();
      return (double)var1 >= this.xPosition && (double)var2 >= this.yPosition && (double)var1 < this.xPosition + (double)this.width && (double)var2 < this.yPosition + (double)this.height;
   }
}
