package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms$TransformType;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer {
   private final RenderWitch witchRenderer;

   public LayerHeldItemWitch(RenderWitch var1) {
      this.witchRenderer = var1;
   }

   public void doRenderLayer(EntityWitch var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack var9 = var1.getHeldItem();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GlStateManager.pushMatrix();
      if(this.witchRenderer.getMainModel().isChild) {
         GlStateManager.translate(0.0F, 0.625F, 0.0F);
         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
         float var10 = 0.5F;
         GlStateManager.scale(var10, var10, var10);
      }

      ((ModelWitch)this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625F);
      GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
      Item var13 = var9.getItem();
      Minecraft var11 = Minecraft.getInstance();
      if(var13 instanceof ItemBlock && var11.getBlockRendererDispatcher().a(Block.getBlockFromItem(var13), var9.getMetadata())) {
         GlStateManager.translate(0.0F, 0.0625F, -0.25F);
         GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-5.0F, 0.0F, 1.0F, 0.0F);
         float var16 = 0.375F;
         GlStateManager.scale(var16, -var16, var16);
      } else if(var13 == Items.bow) {
         GlStateManager.translate(0.0F, 0.125F, -0.125F);
         GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
         float var12 = 0.625F;
         GlStateManager.scale(var12, -var12, var12);
         GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
      } else if(var13.isFull3D()) {
         if(var13.shouldRotateAroundWhenRendering()) {
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -0.0625F, 0.0F);
         }

         this.witchRenderer.transformHeldFull3DItemLayer();
         GlStateManager.translate(0.0625F, -0.125F, 0.0F);
         float var14 = 0.625F;
         GlStateManager.scale(var14, -var14, var14);
         GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
      } else {
         GlStateManager.translate(0.1875F, 0.1875F, 0.0F);
         float var15 = 0.875F;
         GlStateManager.scale(var15, var15, var15);
         GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(-60.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
      var11.getItemRenderer().renderItem(var1, var9, ItemCameraTransforms$TransformType.THIRD_PERSON);
      GlStateManager.popMatrix();
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
