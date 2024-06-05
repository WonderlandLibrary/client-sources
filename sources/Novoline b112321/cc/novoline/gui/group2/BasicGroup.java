package cc.novoline.gui.group2;

import cc.novoline.gui.group2.AbstractGroup;
import cc.novoline.utils.RenderUtils;

public final class BasicGroup extends AbstractGroup {
   public BasicGroup(int var1, int var2, int var3, int var4, int var5) {
      super(var1, var2, var3, var4, var5);
   }

   public void onDraw(int var1, int var2) {
      RenderUtils.drawRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, this.color);
   }
}
