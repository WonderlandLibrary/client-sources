package exhibition.gui.screen.component;

import exhibition.Client;
import exhibition.management.ColorManager;
import exhibition.util.RenderingUtil;
import exhibition.util.render.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiMenuButton extends GuiButton {
   float scale = 1.0F;
   float targ;

   public GuiMenuButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
      super(buttonId, x, y, widthIn, heightIn, buttonText);
   }

   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
      if (this.visible) {
         this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition - 2 && mouseX < this.xPosition + this.width && mouseY < this.yPosition + mc.fontRendererObj.FONT_HEIGHT + 2;
         this.targ = this.hovered ? 1.8F : 1.0F;
         float diff = (this.targ - this.scale) * 0.6F;
         this.scale = 1.0F + diff;
         GlStateManager.pushMatrix();
         GlStateManager.scale(this.scale, this.scale, this.scale);
         this.mouseDragged(mc, mouseX, mouseY);
         int text = this.hovered ? Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255) : -1;
         GlStateManager.pushMatrix();
         float offset = (float)(this.xPosition + this.width / 2) / this.scale;
         GlStateManager.translate(offset, (float)this.yPosition / this.scale, 1.0F);
         RenderingUtil.drawFancy(-31.0D, -2.0D, 31.0D, 14.0D, Colors.getColor(21));
         RenderingUtil.drawFancy(-30.0D, -1.0D, 30.0D, 13.0D, Colors.getColor(28));
         RenderingUtil.rectangle(-29.0D, (double)(-1.0F / this.scale), 31.0D, (double)(-0.5F / this.scale), Colors.getColor(ColorManager.hudColor.red, ColorManager.hudColor.green, ColorManager.hudColor.blue, 255));
         Client.f.drawStringWithShadow(this.displayString, -(Client.f.getWidth(this.displayString) / 2.0F), -0.5F, text);
         GlStateManager.popMatrix();
         GlStateManager.popMatrix();
      }

   }
}
