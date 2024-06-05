package net;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiCreateFlatWorld;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.gen.FlatLayerInfo;

class _7 extends GuiSlot {
   public int x;
   final GuiCreateFlatWorld y;

   public _7(GuiCreateFlatWorld var1) {
      super(var1.mc, var1.width, var1.height, 43, var1.height - 60, 24);
      this.y = var1;
      this.x = -1;
   }

   private void a(int var1, int var2, ItemStack var3) {
      this.a(var1 + 1, var2 + 1);
      GlStateManager.enableRescaleNormal();
      if(var3.getItem() != null) {
         RenderHelper.enableGUIStandardItemLighting();
         this.y.itemRender.renderItemIntoGUI(var3, (float)(var1 + 2), (float)(var2 + 2));
         RenderHelper.disableStandardItemLighting();
      }

      GlStateManager.disableRescaleNormal();
   }

   private void a(int var1, int var2) {
      this.d(var1, var2, 0, 0);
   }

   private void d(int var1, int var2, int var3, int var4) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(Gui.statIcons);
      float var5 = 0.0078125F;
      float var6 = 0.0078125F;
      boolean var7 = true;
      boolean var8 = true;
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      var10.begin(7, DefaultVertexFormats.POSITION_TEX);
      var10.pos((double)(var1 + 0), (double)(var2 + 18), (double)this.y.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 18), (double)this.y.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 18) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 18), (double)(var2 + 0), (double)this.y.zLevel).tex((double)((float)(var3 + 18) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var10.pos((double)(var1 + 0), (double)(var2 + 0), (double)this.y.zLevel).tex((double)((float)(var3 + 0) * 0.0078125F), (double)((float)(var4 + 0) * 0.0078125F)).endVertex();
      var9.draw();
   }

   protected int getSize() {
      return GuiCreateFlatWorld.access$000(this.y).getFlatLayers().size();
   }

   protected void elementClicked(int var1, boolean var2, int var3, int var4) {
      this.x = var1;
      this.y.func_146375_g();
   }

   protected boolean isSelected(int var1) {
      return var1 == this.x;
   }

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6) {
      FlatLayerInfo var7 = (FlatLayerInfo)GuiCreateFlatWorld.access$000(this.y).getFlatLayers().get(GuiCreateFlatWorld.access$000(this.y).getFlatLayers().size() - var1 - 1);
      IBlockState var8 = var7.func_175900_c();
      Block var9 = var8.getBlock();
      Item var10 = Item.getItemFromBlock(var9);
      if(var9 != Blocks.air) {
         new ItemStack(var10, 1, var9.getMetaFromState(var8));
      } else {
         Object var10000 = null;
      }

      String var12 = "Air";
      if(var9 != Blocks.water && var9 != Blocks.flowing_water) {
         if(var9 == Blocks.lava || var9 == Blocks.flowing_lava) {
            var10 = Items.lava_bucket;
         }
      } else {
         var10 = Items.water_bucket;
      }

      ItemStack var11 = new ItemStack(var10, 1, var9.getMetaFromState(var8));
      var12 = var9.getLocalizedName();
      this.a(var2, var3, var11);
      this.y.fontRendererObj.drawString(var12, (float)(var2 + 18 + 5), (float)(var3 + 3), 16777215);
      String var13 = I18n.format("createWorld.customize.flat.layer.top", new Object[]{Integer.valueOf(var7.getLayerCount())});
      this.y.fontRendererObj.drawString(var13, (float)(var2 + 2 + 213 - this.y.fontRendererObj.d(var13)), (float)(var3 + 3), 16777215);
   }

   protected int getScrollBarX() {
      return this.width - 70;
   }
}
