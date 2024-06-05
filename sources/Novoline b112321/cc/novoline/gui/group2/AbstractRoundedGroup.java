package cc.novoline.gui.group2;

import cc.novoline.gui.group2.AbstractGroup;
import cc.novoline.gui.group2.RoundedGroup;
import cc.novoline.utils.RenderUtils;
import java.util.Objects;

public abstract class AbstractRoundedGroup extends AbstractGroup implements RoundedGroup {
   protected int radius;

   public AbstractRoundedGroup(int var1, int var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5, var6);
      this.radius = var1;
   }

   public void onDraw(int var1, int var2) {
      RenderUtils.drawRoundedRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, (float)this.radius, this.color);
   }

   public int getRadius() {
      return this.radius;
   }

   public void setRadius(int var1) {
      this.radius = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = AbstractGroup.a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractRoundedGroup)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractRoundedGroup var3 = (AbstractRoundedGroup)var1;
         return this.radius == var3.radius;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.radius)});
   }

   public String toString() {
      return "AbstractRoundedGroup{radius=" + this.radius + ", color=" + this.color + ", width=" + this.width + ", height=" + this.height + ", visible=" + this.visible + ", x=" + this.x + ", y=" + this.y + '}';
   }
}
