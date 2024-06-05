package net;

import com.google.common.base.Objects;
import java.util.function.Predicate;
import net.minecraft.client.gui.GuiPageButtonList$GuiListEntry;

public class ba extends GuiPageButtonList$GuiListEntry {
   private final Predicate d;

   public ba(int var1, String var2, boolean var3, Predicate var4) {
      super(var1, var2, var3);
      this.d = (Predicate)Objects.firstNonNull(var4, ba::lambda$new$0);
   }

   public Predicate a() {
      return this.d;
   }

   private static boolean lambda$new$0(String var0) {
      return true;
   }
}
