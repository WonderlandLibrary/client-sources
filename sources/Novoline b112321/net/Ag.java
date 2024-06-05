package net;

import cc.novoline.gui.group.GuiGroupPlayerBox;
import java.util.function.Function;
import net.minecraft.client.Minecraft;

public class Ag {
   public static void a(GuiGroupPlayerBox var0, Function var1) {
      var0.addLine(var1);
   }

   public static void a(GuiGroupPlayerBox var0, Minecraft var1, int var2, int var3) {
      var0.drawGroup(var1, var2, var3);
   }
}
