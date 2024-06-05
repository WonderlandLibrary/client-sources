package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiListExtended$IGuiListEntry;
import net.minecraft.client.gui.GuiTextField;

public class GuiPageButtonList$GuiEntry implements GuiListExtended$IGuiListEntry {
   private final Minecraft field_178031_a = Minecraft.getInstance();
   private final Gui field_178029_b;
   private final Gui field_178030_c;
   private Gui field_178028_d;

   public GuiPageButtonList$GuiEntry(Gui var1, Gui var2) {
      this.field_178029_b = var1;
      this.field_178030_c = var2;
   }

   public Gui func_178022_a() {
      return this.field_178029_b;
   }

   public Gui func_178021_b() {
      return this.field_178030_c;
   }

   public void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.func_178017_a(this.field_178029_b, var3, var6, var7, false);
      this.func_178017_a(this.field_178030_c, var3, var6, var7, false);
   }

   private void func_178017_a(Gui var1, int var2, int var3, int var4, boolean var5) {
      if(var1 instanceof GuiButton) {
         this.func_178024_a((GuiButton)var1, var2, var3, var4, var5);
      } else if(var1 instanceof GuiTextField) {
         this.func_178027_a((GuiTextField)var1, var2, var5);
      } else if(var1 instanceof GuiLabel) {
         this.func_178025_a((GuiLabel)var1, var2, var3, var4, var5);
      }

   }

   private void func_178024_a(GuiButton var1, int var2, int var3, int var4, boolean var5) {
      var1.yPosition = (double)var2;
      var1.drawButton(this.field_178031_a, var3, var4);
   }

   private void func_178027_a(GuiTextField var1, int var2, boolean var3) {
      var1.yPosition = (float)var2;
      var1.drawTextBox();
   }

   private void func_178025_a(GuiLabel var1, int var2, int var3, int var4, boolean var5) {
      var1.field_146174_h = var2;
      var1.drawLabel(this.field_178031_a, var3, var4);
   }

   public void setSelected(int var1, int var2, int var3) {
      this.func_178017_a(this.field_178029_b, var3, 0, 0, true);
      this.func_178017_a(this.field_178030_c, var3, 0, 0, true);
   }

   public boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6) {
      boolean var7 = this.b(this.field_178029_b, var2, var3, var4);
      boolean var8 = this.b(this.field_178030_c, var2, var3, var4);
      return true;
   }

   private boolean b(Gui var1, int var2, int var3, int var4) {
      return false;
   }

   private boolean func_178023_a(GuiButton var1, int var2, int var3, int var4) {
      boolean var5 = var1.mousePressed(this.field_178031_a, var2, var3);
      this.field_178028_d = var1;
      return var5;
   }

   private void func_178018_a(GuiTextField var1, int var2, int var3, int var4) {
      var1.mouseClicked(var2, var3, var4);
      if(var1.isFocused()) {
         this.field_178028_d = var1;
      }

   }

   public void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.func_178016_b(this.field_178029_b, var2, var3, var4);
      this.func_178016_b(this.field_178030_c, var2, var3, var4);
   }

   private void func_178016_b(Gui var1, int var2, int var3, int var4) {
      if(var1 instanceof GuiButton) {
         this.func_178019_b((GuiButton)var1, var2, var3, var4);
      }

   }

   private void func_178019_b(GuiButton var1, int var2, int var3, int var4) {
      var1.mouseReleased(var2, var3);
   }

   static Gui access$000(GuiPageButtonList$GuiEntry var0) {
      return var0.field_178029_b;
   }

   static Gui access$100(GuiPageButtonList$GuiEntry var0) {
      return var0.field_178030_c;
   }

   static Gui access$200(GuiPageButtonList$GuiEntry var0) {
      return var0.field_178028_d;
   }
}
