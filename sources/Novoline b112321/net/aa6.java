package net;

import java.util.List;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerList;

public class aa6 {
   public static void a(ServerSelectionList var0, ServerList var1) {
      var0.func_148195_a(var1);
   }

   public static void a(ServerSelectionList var0, int var1, int var2, int var3, int var4) {
      var0.setDimensions(var1, var2, var3, var4);
   }

   public static int a(ServerSelectionList var0) {
      return var0.func_148193_k();
   }

   public static void a(ServerSelectionList var0, List var1) {
      var0.func_148194_a(var1);
   }

   public static GuiListExtended$IGuiListEntry a(ServerSelectionList var0, int var1) {
      return var0.getListEntry(var1);
   }

   public static void b(ServerSelectionList var0, int var1) {
      var0.setSelectedSlotIndex(var1);
   }

   public static int b(ServerSelectionList var0) {
      return var0.getSlotHeight();
   }

   public static void c(ServerSelectionList var0, int var1) {
      var0.scrollBy(var1);
   }

   public static boolean b(ServerSelectionList var0, int var1, int var2, int var3) {
      return var0.mouseClicked(var1, var2, var3);
   }

   public static boolean a(ServerSelectionList var0, int var1, int var2, int var3) {
      return var0.mouseReleased(var1, var2, var3);
   }
}
