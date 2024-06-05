package cc.novoline.gui.group2;

import cc.novoline.gui.group2.AbstractGroup;
import cc.novoline.gui.group2.RoundedGroupWithTitle;
import cc.novoline.gui.label.Label;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.java.Checks;
import java.util.Objects;

public abstract class AbstractRoundedGroupWithTitle extends AbstractGroup implements RoundedGroupWithTitle {
   protected Label title;
   protected int radius;

   public AbstractRoundedGroupWithTitle(Label var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var3, var4, var5, var6, var7);
      this.setTitle(var1);
      this.radius = var2;
   }

   public void onDraw(int var1, int var2) {
      AbstractGroup.a();
      RenderUtils.drawRoundedRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, (float)this.radius, this.color);
      if(this.title != null) {
         this.title.draw(var1, var2);
      }

   }

   public void updateTitlePosition() throws NullPointerException {
      Checks.notNull(this.title, "title");
      int var1 = (int)((float)this.x + (float)(this.width - this.title.getWidth()) / 2.0F);
      int var2 = this.y + 4;
      this.title.setPosition(var1, var2);
   }

   public Label getTitle() {
      return this.title;
   }

   public void setTitle(Label var1) {
      int[] var2 = AbstractGroup.a();
      this.title = var1;
      this.updateTitlePosition();
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
      } else if(!(var1 instanceof AbstractRoundedGroupWithTitle)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractRoundedGroupWithTitle var3 = (AbstractRoundedGroupWithTitle)var1;
         return this.radius == var3.radius && Objects.equals(this.title, var3.title);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.title, Integer.valueOf(this.radius)});
   }

   public String toString() {
      int[] var1 = AbstractGroup.a();
      return "AbstractRoundedGroupWithTitle{title=" + this.title + ", radius=" + this.radius + ", color=" + this.color + ", width=" + this.width + ", height=" + this.height + ", visible=" + this.visible + ", x=" + this.x + ", y=" + this.y + '}';
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
