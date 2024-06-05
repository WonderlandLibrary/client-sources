package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupSupplierLine;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.fonts.api.FontRenderer;
import cc.novoline.utils.fonts.impl.Fonts$SFBOLD$SFBOLD_35;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractGroup extends Gui {
   protected final FontRenderer titleFontRenderer;
   protected final String title;
   protected boolean hidden;
   protected int xPosition;
   protected final int yPosition;
   protected final int width;
   protected final int height;

   protected AbstractGroup(@Nullable String var1, int var2, int var3, int var4, int var5, @NotNull FontRenderer var6) {
      GroupSupplierLine.b();
      super();
      this.title = var1 != null && !(var1 = var1.trim()).isEmpty()?var1:null;
      this.xPosition = var2;
      this.yPosition = var3;
      this.width = var4;
      this.height = var5;
      this.titleFontRenderer = var6;
   }

   protected AbstractGroup(String var1, int var2, int var3, int var4, int var5) {
      this(var1, var2, var3, var4, var5, Fonts$SFBOLD$SFBOLD_35.SFBOLD_35);
   }

   public void drawGroup(Minecraft var1, int var2, int var3) {
      String var4 = GroupSupplierLine.b();
      if(!this.hidden) {
         RenderUtils.drawRect((float)this.xPosition, (float)this.yPosition, (float)this.width, (float)this.height, -754974721);
         if(this.title != null) {
            this.titleFontRenderer.drawString(this.title, (float)this.xPosition + (float)(this.width - this.titleFontRenderer.stringWidth(this.title)) / 2.0F, (float)(this.yPosition + 4), 0);
         }

      }
   }
}
