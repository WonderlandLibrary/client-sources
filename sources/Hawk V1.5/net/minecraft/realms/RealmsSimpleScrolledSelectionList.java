package net.minecraft.realms;

import net.minecraft.client.gui.GuiSimpleScrolledSelectionListProxy;

public class RealmsSimpleScrolledSelectionList {
   private static final String __OBFID = "CL_00002186";
   private final GuiSimpleScrolledSelectionListProxy proxy;

   public int getScrollbarPosition() {
      return this.proxy.func_178048_e() / 2 + 124;
   }

   public int xm() {
      return this.proxy.func_178049_g();
   }

   public int getItemCount() {
      return 0;
   }

   public int getScroll() {
      return this.proxy.getAmountScrolled();
   }

   protected void renderList(int var1, int var2, int var3, int var4) {
   }

   protected void renderItem(int var1, int var2, int var3, int var4, Tezzelator var5, int var6, int var7) {
   }

   public void renderBackground() {
   }

   public void render(int var1, int var2, float var3) {
      this.proxy.drawScreen(var1, var2, var3);
   }

   public int getMaxPosition() {
      return 0;
   }

   public void scroll(int var1) {
      this.proxy.scrollBy(var1);
   }

   public void selectItem(int var1, boolean var2, int var3, int var4) {
   }

   public int ym() {
      return this.proxy.func_178047_f();
   }

   public void renderItem(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.renderItem(var1, var2, var3, var4, Tezzelator.instance, var5, var6);
   }

   public boolean isSelectedItem(int var1) {
      return false;
   }

   public RealmsSimpleScrolledSelectionList(int var1, int var2, int var3, int var4, int var5) {
      this.proxy = new GuiSimpleScrolledSelectionListProxy(this, var1, var2, var3, var4, var5);
   }

   public void mouseEvent() {
      this.proxy.func_178039_p();
   }

   public int width() {
      return this.proxy.func_178048_e();
   }
}
