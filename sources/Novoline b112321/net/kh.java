package net;

import java.util.function.Predicate;
import net.minecraft.client.gui.GuiPageButtonList$GuiResponder;
import net.minecraft.client.gui.GuiTextField;

public class kh {
   public static void f(GuiTextField var0) {
      var0.drawTextBox();
   }

   public static void a(GuiTextField var0, int var1, int var2, int var3) {
      var0.mouseClicked(var1, var2, var3);
   }

   public static boolean a(GuiTextField var0) {
      return var0.isFocused();
   }

   public static void b(GuiTextField var0, String var1) {
      var0.setText(var1);
   }

   public static void a(GuiTextField var0, int var1) {
      var0.setMaxStringLength(var1);
   }

   public static void e(GuiTextField var0, boolean var1) {
      var0.setEnableBackgroundDrawing(var1);
   }

   public static void c(GuiTextField var0, boolean var1) {
      var0.setVisible(var1);
   }

   public static void c(GuiTextField var0, int var1) {
      var0.setTextColor(var1);
   }

   public static boolean a(GuiTextField var0, char var1, int var2) {
      return var0.textboxKeyTyped(var1, var2);
   }

   public static String c(GuiTextField var0) {
      return var0.getText();
   }

   public static void d(GuiTextField var0, boolean var1) {
      var0.setCanLoseFocus(var1);
   }

   public static void b(GuiTextField var0, boolean var1) {
      var0.setFocused(var1);
   }

   public static void b(GuiTextField var0) {
      var0.updateCursorCounter();
   }

   public static int e(GuiTextField var0) {
      return var0.getId();
   }

   public static void a(GuiTextField var0, boolean var1) {
      var0.setEnabled(var1);
   }

   public static void d(GuiTextField var0, int var1) {
      var0.setDisabledTextColour(var1);
   }

   public static void a(GuiTextField var0, Predicate var1) {
      var0.func_175205_a(var1);
   }

   public static void a(GuiTextField var0, String var1) {
      var0.writeText(var1);
   }

   public static int d(GuiTextField var0) {
      return var0.getCursorPosition();
   }

   public static int a(GuiTextField var0, int var1, int var2, boolean var3) {
      return var0.func_146197_a(var1, var2, var3);
   }

   public static void b(GuiTextField var0, int var1) {
      var0.deleteFromCursor(var1);
   }

   public static void a(GuiTextField var0, GuiPageButtonList$GuiResponder var1) {
      var0.func_175207_a(var1);
   }
}
