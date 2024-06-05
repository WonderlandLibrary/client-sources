package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupLine;
import java.util.function.Function;

public class GroupSupplierLine implements GroupLine {
   private final Function stringFunction;
   private static String b;

   protected GroupSupplierLine(Function var1) {
      this.stringFunction = var1;
   }

   static GroupSupplierLine of(Function var0) {
      return new GroupSupplierLine(var0);
   }

   public String getText(Object var1) {
      return (String)this.stringFunction.apply(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("YjyEw");
      }

   }
}
