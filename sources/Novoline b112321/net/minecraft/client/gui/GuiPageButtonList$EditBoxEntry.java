package net.minecraft.client.gui;

import com.google.common.base.Objects;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiPageButtonList$GuiListEntry;

public class GuiPageButtonList$EditBoxEntry extends GuiPageButtonList$GuiListEntry {
   private final Predicate field_178951_a;

   public GuiPageButtonList$EditBoxEntry(int var1, String var2, boolean var3, Predicate var4) {
      super(var1, var2, var3);
      this.field_178951_a = (Predicate)Objects.firstNonNull(var4, GuiPageButtonList$EditBoxEntry::lambda$new$0);
   }

   public Predicate func_178950_a() {
      return this.field_178951_a;
   }

   private static boolean lambda$new$0(String var0) {
      return true;
   }
}
