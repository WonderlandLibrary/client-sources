package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton {
   private final GuiPageButtonList.GuiResponder field_175214_q;
   private String field_175215_p;
   private boolean field_175216_o;
   private static final String __OBFID = "CL_00001953";

   public boolean mousePressed(Minecraft var1, int var2, int var3) {
      if (super.mousePressed(var1, var2, var3)) {
         this.field_175216_o = !this.field_175216_o;
         this.displayString = this.func_175213_c();
         this.field_175214_q.func_175321_a(this.id, this.field_175216_o);
         return true;
      } else {
         return false;
      }
   }

   private String func_175213_c() {
      return String.valueOf((new StringBuilder(String.valueOf(I18n.format(this.field_175215_p)))).append(": ").append(this.field_175216_o ? I18n.format("gui.yes") : I18n.format("gui.no")));
   }

   public void func_175212_b(boolean var1) {
      this.field_175216_o = var1;
      this.displayString = this.func_175213_c();
      this.field_175214_q.func_175321_a(this.id, var1);
   }

   public GuiListButton(GuiPageButtonList.GuiResponder var1, int var2, int var3, int var4, String var5, boolean var6) {
      super(var2, var3, var4, 150, 20, "");
      this.field_175215_p = var5;
      this.field_175216_o = var6;
      this.displayString = this.func_175213_c();
      this.field_175214_q = var1;
   }
}
