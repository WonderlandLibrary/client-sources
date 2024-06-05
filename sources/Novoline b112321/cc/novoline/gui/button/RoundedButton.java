package cc.novoline.gui.button;

import cc.novoline.gui.button.AbstractButton;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.api.FontRenderer;
import java.awt.Color;
import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

public class RoundedButton extends GuiButton {
   private final int radius;
   private final FontRenderer fontRenderer;

   public RoundedButton(String var1, int var2, int var3, int var4, int var5, int var6, int var7, @NotNull FontRenderer var8) {
      super(var2, var3, var4, var5, var6, var1);
      AbstractButton.a();
      this.radius = var7;
      this.fontRenderer = var8;
      if(acE.b() == null) {
         AbstractButton.b(new acE[2]);
      }

   }

   public RoundedButton(String var1, int var2, int var3, int var4, int var5, @NotNull FontRenderer var6) {
      this(var1, var2, var3, var4, 200, 20, var5, var6);
   }

   public void drawButton(Minecraft var1, int var2, int var3) {
      acE[] var4 = AbstractButton.a();
      if(this.visible) {
         double var5 = this.xPosition;
         double var7 = this.yPosition;
         int var9 = this.width;
         int var10 = this.height;
         this.hovered = (double)var2 >= var5 && (double)var3 >= var7 && (double)var2 < var5 + (double)var9 && (double)var3 < var7 + (double)var10;
         int var11 = !this.hovered?75:100;
         RenderUtils.drawRoundedRect((float)((int)var5), (float)((int)var7 + 1), (float)var9, (float)var10, (float)this.radius, 0 | var11 << 24);
         this.mouseDragged(var1, var2, var3);
         this.fontRenderer.drawString(this.displayString, (float)((int)var5) + (float)(var9 - this.fontRenderer.stringWidth(this.displayString)) / 2.0F, (float)((int)var7 + 2) + (float)(var10 - this.fontRenderer.getHeight()) / 2.0F, (new Color(198, 198, 198)).getRGB());
      }
   }

   public void playPressSound(@NotNull SoundHandler var1) {
      super.playPressSound(var1);
   }

   public int getRadius() {
      return this.radius;
   }
}
