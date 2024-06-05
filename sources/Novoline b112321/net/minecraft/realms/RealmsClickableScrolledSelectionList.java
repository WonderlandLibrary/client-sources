package net.minecraft.realms;

import net.minecraft.client.gui.GuiClickableScrolledSelectionListProxy;
import net.minecraft.realms.Tezzelator;

public class RealmsClickableScrolledSelectionList {
   private final GuiClickableScrolledSelectionListProxy proxy;

   public RealmsClickableScrolledSelectionList(int var1, int var2, int var3, int var4, int var5) {
      this.proxy = new GuiClickableScrolledSelectionListProxy(this, var1, var2, var3, var4, var5);
   }

   public void render(int var1, int var2, float var3) {
      this.proxy.drawScreen(var1, var2, var3);
   }

   public int width() {
      return this.proxy.func_178044_e();
   }

   public int ym() {
      return this.proxy.func_178042_f();
   }

   public int xm() {
      return this.proxy.func_178045_g();
   }

   protected void renderItem(int var1, int var2, int var3, int var4, Tezzelator var5, int var6, int var7) {
   }

   public void renderItem(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.renderItem(var1, var2, var3, var4, Tezzelator.instance, var5, var6);
   }

   public int getItemCount() {
      return 0;
   }

   public void selectItem(int var1, boolean var2, int var3, int var4) {
   }

   public boolean isSelectedItem(int var1) {
      return false;
   }

   public void renderBackground() {
   }

   public int getMaxPosition() {
      return 0;
   }

   public int getScrollbarPosition() {
      return this.proxy.func_178044_e() / 2 + 124;
   }

   public void mouseEvent() {
      this.proxy.handleMouseInput();
   }

   public void customMouseEvent(int var1, int var2, int var3, float var4, int var5) {
   }

   public void scroll(int var1) {
      this.proxy.scrollBy(var1);
   }

   public int getScroll() {
      return this.proxy.getAmountScrolled();
   }

   protected void renderList(int var1, int var2, int var3, int var4) {
   }

   public void itemClicked(int var1, int var2, int var3, int var4, int var5) {
   }

   public void renderSelected(int var1, int var2, int var3, Tezzelator var4) {
   }

   public void setLeftPos(int var1) {
      this.proxy.setSlotXBoundsFromLeft(var1);
   }
}
