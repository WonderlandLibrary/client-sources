package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.realms.RealmsScrolledSelectionList;

public class GuiSlotRealmsProxy extends GuiSlot {
   private final RealmsScrolledSelectionList selectionList;
   private static final String __OBFID = "CL_00001846";

   protected int getSize() {
      return this.selectionList.getItemCount();
   }

   public int func_154338_k() {
      return super.width;
   }

   protected int getContentHeight() {
      return this.selectionList.getMaxPosition();
   }

   protected void drawBackground() {
      this.selectionList.renderBackground();
   }

   protected int getScrollBarX() {
      return this.selectionList.getScrollbarPosition();
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.selectionList.renderItem(var1, var2, var3, var4, var5, var6);
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.selectionList.selectItem(var1, var2, var3, var4);
   }

   public int func_154339_l() {
      return super.mouseY;
   }

   public GuiSlotRealmsProxy(RealmsScrolledSelectionList var1, int var2, int var3, int var4, int var5, int var6) {
      super(Minecraft.getMinecraft(), var2, var3, var4, var5, var6);
      this.selectionList = var1;
   }

   public void func_178039_p() {
      super.func_178039_p();
   }

   protected boolean isSelected(int var1) {
      return this.selectionList.isSelectedItem(var1);
   }

   public int func_154337_m() {
      return super.mouseX;
   }
}
