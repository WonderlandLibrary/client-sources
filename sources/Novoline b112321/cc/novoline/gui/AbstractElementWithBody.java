package cc.novoline.gui;

import cc.novoline.gui.AbstractElement;
import cc.novoline.gui.ElementWithBody;
import java.util.Objects;
import net.acE;

public abstract class AbstractElementWithBody extends AbstractElement implements ElementWithBody {
   protected int width;
   protected int height;

   public AbstractElementWithBody(int var1, int var2, int var3, int var4) {
      super(var1, var2);
      this.width = var3;
      this.height = var4;
   }

   public boolean isHovered(int var1, int var2) {
      int[] var3 = AbstractElement.b();
      boolean var4 = var1 >= this.x && var1 <= this.x + this.width && var2 >= this.y && var2 <= this.y + this.height;
      return var4;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int var1) {
      this.width = var1;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int var1) {
      this.height = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = AbstractElement.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractElementWithBody)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractElementWithBody var3 = (AbstractElementWithBody)var1;
         return this.width == var3.width && this.height == var3.height;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.width), Integer.valueOf(this.height)});
   }

   public String toString() {
      int[] var1 = AbstractElement.b();
      String var10000 = "AbstractDrawableWithBody{width=" + this.width + ", height=" + this.height + ", visible=" + this.visible + ", x=" + this.x + ", y=" + this.y + '}';
      if(acE.b() == null) {
         AbstractElement.b(new int[3]);
      }

      return var10000;
   }
}
