package cc.novoline.gui.group2;

import cc.novoline.gui.group2.AbstractGroup;
import cc.novoline.gui.group2.GroupWithTitle;
import cc.novoline.gui.label.Label;
import cc.novoline.utils.RenderUtils;
import cc.novoline.utils.java.Checks;
import java.util.Objects;
import net.acE;

public abstract class AbstractGroupWithTitle extends AbstractGroup implements GroupWithTitle {
   protected Label title;

   public AbstractGroupWithTitle(Label var1, int var2, int var3, int var4, int var5, int var6) {
      super(var2, var3, var4, var5, var6);
      this.setTitle(var1);
   }

   public void onDraw(int var1, int var2) {
      AbstractGroup.a();
      RenderUtils.drawRect((float)this.x, (float)this.y, (float)this.width, (float)this.height, this.color);
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

   public boolean equals(Object var1) {
      int[] var2 = AbstractGroup.a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractGroupWithTitle)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractGroupWithTitle var3 = (AbstractGroupWithTitle)var1;
         return Objects.equals(this.title, var3.title);
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), this.title});
   }

   public String toString() {
      int[] var1 = AbstractGroup.a();
      String var10000 = "AbstractGroupWithTitle{title=" + this.title + ", color=" + this.color + ", width=" + this.width + ", height=" + this.height + ", visible=" + this.visible + ", x=" + this.x + ", y=" + this.y + '}';
      if(acE.b() == null) {
         AbstractGroup.a(new int[4]);
      }

      return var10000;
   }

   private static NullPointerException a(NullPointerException var0) {
      return var0;
   }
}
