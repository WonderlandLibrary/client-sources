package cc.novoline.gui;

import cc.novoline.gui.Element;
import java.util.Objects;

public abstract class AbstractElement implements Element {
   protected boolean visible = true;
   protected int x;
   protected int y;
   private static int[] b;

   public AbstractElement(int var1, int var2) {
      this.x = var1;
      this.y = var2;
   }

   public final void draw(int var1, int var2) {
      int[] var3 = b();
      if(this.visible) {
         this.onDraw(var1, var2);
      }
   }

   public boolean isVisible() {
      return this.visible;
   }

   public void setVisible(boolean var1) {
      this.visible = var1;
   }

   public int getX() {
      return this.x;
   }

   public void setX(int var1) {
      this.x = var1;
   }

   public int getY() {
      return this.y;
   }

   public void setY(int var1) {
      this.y = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractElement)) {
         return false;
      } else {
         AbstractElement var3 = (AbstractElement)var1;
         return this.visible == var3.visible && this.x == var3.x && this.y == var3.y;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Boolean.valueOf(this.visible), Integer.valueOf(this.x), Integer.valueOf(this.y)});
   }

   public String toString() {
      return "AbstractDrawable{visible=" + this.visible + ", x=" + this.x + ", y=" + this.y + '}';
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      b(new int[2]);
   }
}
