package cc.novoline.gui.group2;

import cc.novoline.gui.AbstractElementWithBody;
import cc.novoline.gui.group2.Group;
import java.util.Objects;

public abstract class AbstractGroup extends AbstractElementWithBody implements Group {
   protected int color;
   private static int[] g;

   public AbstractGroup(int var1, int var2, int var3, int var4, int var5) {
      super(var2, var3, var4, var5);
      this.color = var1;
   }

   public int getColor() {
      return this.color;
   }

   public void setColor(int var1) {
      this.color = var1;
   }

   public boolean equals(Object var1) {
      int[] var2 = a();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof AbstractGroup)) {
         return false;
      } else if(!super.equals(var1)) {
         return false;
      } else {
         AbstractGroup var3 = (AbstractGroup)var1;
         return this.color == var3.color;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(super.hashCode()), Integer.valueOf(this.color)});
   }

   public String toString() {
      return "AbstractGroup{color=" + this.color + '}';
   }

   public static void a(int[] var0) {
      g = var0;
   }

   public static int[] a() {
      return g;
   }

   static {
      if(a() == null) {
         a(new int[2]);
      }

   }
}
