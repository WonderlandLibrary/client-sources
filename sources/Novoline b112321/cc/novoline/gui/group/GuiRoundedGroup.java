package cc.novoline.gui.group;

import cc.novoline.gui.group.AbstractGroup;
import cc.novoline.gui.group.GroupSupplierLine;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.api.FontRenderer;
import java.awt.Color;
import net.minecraft.client.Minecraft;

public class GuiRoundedGroup extends AbstractGroup {
   protected final int radius;

   public GuiRoundedGroup(String var1, int var2, int var3, int var4, int var5, int var6, FontRenderer var7) {
      super(var1, var2, var3, var4, var5, var7);
      this.radius = var6;
   }

   public GuiRoundedGroup(String var1, int var2, int var3, int var4, int var5, int var6) {
      super(var1, var2, var3, var4, var5);
      this.radius = var6;
   }

   public void drawGroup(Minecraft var1, int var2, int var3) {
      String var4 = GroupSupplierLine.b();
      if(!this.hidden) {
         RenderUtils.drawRoundedRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, (float)this.radius, (new Color(0, 0, 0, 80)).getRGB());
         if(this.title != null) {
            this.titleFontRenderer.drawString(this.title, (float)this.xPosition + (float)(this.width - this.titleFontRenderer.stringWidth(this.title)) / 2.0F, (float)(this.yPosition + 4), (new Color(198, 198, 198)).getRGB());
         }

      }
   }

   public int getRadius() {
      return this.radius;
   }
}
