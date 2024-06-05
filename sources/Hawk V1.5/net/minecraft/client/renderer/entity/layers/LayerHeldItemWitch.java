package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderWitch;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class LayerHeldItemWitch implements LayerRenderer {
   private final RenderWitch field_177144_a;
   private static final String __OBFID = "CL_00002407";

   public void func_177143_a(EntityWitch var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      ItemStack var9 = var1.getHeldItem();
      if (var9 != null) {
         GlStateManager.color(1.0F, 1.0F, 1.0F);
         GlStateManager.pushMatrix();
         if (this.field_177144_a.getMainModel().isChild) {
            GlStateManager.translate(0.0F, 0.625F, 0.0F);
            GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
            float var10 = 0.5F;
            GlStateManager.scale(var10, var10, var10);
         }

         ((ModelWitch)this.field_177144_a.getMainModel()).villagerNose.postRender(0.0625F);
         GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
         Item var13 = var9.getItem();
         Minecraft var11 = Minecraft.getMinecraft();
         float var12;
         if (var13 instanceof ItemBlock && var11.getBlockRendererDispatcher().func_175021_a(Block.getBlockFromItem(var13), var9.getMetadata())) {
            GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
            GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
            var12 = 0.375F;
            GlStateManager.scale(var12, -var12, var12);
         } else if (var13 == Items.bow) {
            GlStateManager.translate(0.0F, 0.125F, 0.3125F);
            GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
            var12 = 0.625F;
            GlStateManager.scale(var12, -var12, var12);
            GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
         } else if (var13.isFull3D()) {
            if (var13.shouldRotateAroundWhenRendering()) {
               GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.translate(0.0F, -0.125F, 0.0F);
            }

            this.field_177144_a.func_82422_c();
            var12 = 0.625F;
            GlStateManager.scale(var12, -var12, var12);
            GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.translate(0.25F, 0.1875F, -0.1875F);
            var12 = 0.375F;
            GlStateManager.scale(var12, var12, var12);
            GlStateManager.rotate(60.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
         var11.getItemRenderer().renderItem(var1, var9, ItemCameraTransforms.TransformType.THIRD_PERSON);
         GlStateManager.popMatrix();
      }

   }

   public void doRenderLayer(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.func_177143_a((EntityWitch)var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public boolean shouldCombineTextures() {
      return false;
   }

   public LayerHeldItemWitch(RenderWitch var1) {
      this.field_177144_a = var1;
   }
}
