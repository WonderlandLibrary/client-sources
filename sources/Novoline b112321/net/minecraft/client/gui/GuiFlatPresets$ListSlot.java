package net.minecraft.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiFlatPresets;
import net.minecraft.client.gui.GuiFlatPresets$LayerItem;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

class GuiFlatPresets$ListSlot extends GuiSlot {
   public int field_148175_k;
   final GuiFlatPresets this$0;

   public GuiFlatPresets$ListSlot(GuiFlatPresets var1) {
      super(var1.mc, var1.width, var1.height, 80, var1.height - 37, 24);
      this.this$0 = var1;
      this.field_148175_k = -1;
   }

   private void func_178054_a(int var1, int var2, Item var3, int var4) {
      this.func_148173_e(var1 + 1, var2 + 1);
      GlStateManager.enableRescaleNormal();
      RenderHelper.enableGUIStandardItemLighting();
      this.this$0.itemRender.renderItemIntoGUI(new ItemStack(var3, 1, var4), (float)(var1 + 2), (float)(var2 + 2));
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
   }

   private void func_148173_e(int var1, int var2) {
      this.func_148171_c(var1, var2, 0, 0);
   }

   private void func_148171_c(int var1, int var2, int var3, int var4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Gui.statIcons);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      boolean var7 = true;
      boolean var8 = true;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.begin(7, DefaultVertexFormats.POSITION_TEX);
      var10.pos((double)(var1 + 0), (double)(var2 + 18), (double)this.this$0.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 18), (double)this.this$0.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 0), (double)this.this$0.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 0), (double)(var2 + 0), (double)this.this$0.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var9.draw();
   }

   protected int getSize() {
      return GuiFlatPresets.access$000().size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.field_148175_k = var1;
      this.this$0.func_146426_g();
      GuiFlatPresets.access$200(this.this$0).setText(((GuiFlatPresets$LayerItem)GuiFlatPresets.access$000().get(GuiFlatPresets.access$100(this.this$0).field_148175_k)).field_148233_c);
   }

   protected boolean isSelected(int var1) {
      return var1 == this.field_148175_k;
   }

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      GuiFlatPresets$LayerItem var7 = (GuiFlatPresets$LayerItem)GuiFlatPresets.access$000().get(var1);
      this.func_178054_a(var2, var3, var7.field_148234_a, var7.field_179037_b);
      this.this$0.fontRendererObj.drawString(var7.field_148232_b, (float)(var2 + 18 + 5), (float)(var3 + 6), 16777215);
   }
}
