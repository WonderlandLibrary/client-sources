package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupSupplierLine;
import java.util.function.Function;

public class GroupNullableLine extends GroupSupplierLine {
   protected GroupNullableLine(Function var1) {
      super(var1);
   }

   static GroupNullableLine of(Function var0) {
      return new GroupNullableLine(var0);
   }
}
