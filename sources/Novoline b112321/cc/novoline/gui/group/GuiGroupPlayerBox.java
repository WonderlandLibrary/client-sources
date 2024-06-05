package cc.novoline.gui.group;

import cc.novoline.gui.group.GroupSupplierLine;
import cc.novoline.gui.group.GuiRoundedGroupWithLines;
import cc.novoline.gui.screen.alt.repository.Alt;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;

public class GuiGroupPlayerBox extends GuiRoundedGroupWithLines {
   private final Predicate shouldRender = GuiGroupPlayerBox::lambda$new$0;

   public GuiGroupPlayerBox(int var1, int var2, int var3, int var4, Supplier var5) {
      super("Alt Info", var1, var2, var3, var4, 15, var5);
   }

   public void drawGroup(Minecraft var1, int var2, int var3) {
      GroupSupplierLine.b();
      this.superDrawGroup(var1, var2, var3);
      Alt var5 = (Alt)this.supplier.get();
      if(this.shouldRender.test(var5)) {
         this.drawLines();
      }

   }

   private static boolean lambda$new$0(Alt var0) {
      String var1 = GroupSupplierLine.b();
      return var0 != null && var0.getPlayer() != null;
   }
}
