package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.realms.RealmsClickableScrolledSelectionList;
import net.minecraft.realms.Tezzelator;
import org.lwjgl.input.Mouse;

public class GuiClickableScrolledSelectionListProxy extends GuiSlot {
   private final RealmsClickableScrolledSelectionList field_178046_u;

   public GuiClickableScrolledSelectionListProxy(RealmsClickableScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
      super(Minecraft.getInstance(), var2, var3, var4, var5, var6);
      this.field_178046_u = var1;
   }

   protected int getSize() {
      return this.field_178046_u.getItemCount();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.field_178046_u.selectItem(var1, var2, var3, var4);
   }

   protected boolean isSelected(int var1) {
      return this.field_178046_u.isSelectedItem(var1);
   }

   protected void drawBackground() {
      this.field_178046_u.renderBackground();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.field_178046_u.renderItem(var1, var2, var3, var4, var5, var6);
   }

   public int func_178044_e() {
      return super.width;
   }

   public int func_178042_f() {
      return super.mouseY;
   }

   public int func_178045_g() {
      return super.mouseX;
   }

   protected int getContentHeight() {
      return this.field_178046_u.getMaxPosition();
   }

   protected int getScrollBarX() {
      return this.field_178046_u.getScrollbarPosition();
   }

   public void handleMouseInput() {
      super.handleMouseInput();
      if(this.scrollMultiplier > 0.0F && Mouse.getEventButtonState()) {
         this.field_178046_u.customMouseEvent(this.top, this.bottom, this.headerPadding, this.amountScrolled, this.slotHeight);
      }

   }

   public void func_178043_a(int var1, int var2, int var3, Tezzelator var4) {
      this.field_178046_u.renderSelected(var1, var2, var3, var4);
   }

   protected void drawSelectionBox(int var1, int var2, int var3, int var4) {
      int var5 = this.getSize();

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var2 + var6 * this.slotHeight + this.headerPadding;
         int var8 = this.slotHeight - 4;
         if(var7 > this.bottom || var7 + var8 < this.top) {
            this.func_178040_a(var6, var1, var7);
         }

         if(this.showSelectionBox && this.isSelected(var6)) {
            this.func_178043_a(this.width, var7, var8, Tezzelator.instance);
         }

         this.drawSlot(var6, var1, var7, var8, var3, var4);
      }

   }
}
