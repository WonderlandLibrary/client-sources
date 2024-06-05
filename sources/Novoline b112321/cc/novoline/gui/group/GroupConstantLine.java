package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupLine;

public final class GroupConstantLine implements GroupLine {
   private final String text;

   private GroupConstantLine(String var1) {
      this.text = var1;
   }

   static GroupConstantLine of(String var0) {
      return new GroupConstantLine(var0);
   }

   public String getText(Object var1) {
      return this.text;
   }
}
