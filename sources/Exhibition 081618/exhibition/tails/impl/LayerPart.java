package exhibition.tails.impl;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;

public class LayerPart implements LayerRenderer {
   private final ModelRenderer modelRenderer;
   public static ModelCatEars modelCatEars;

   public LayerPart(ModelRenderer modelRenderer) {
      this.modelRenderer = modelRenderer;
      modelCatEars = new ModelCatEars();
   }

   public void doRenderLayer(EntityLivingBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float p_177141_8_) {
      if (!entity.isInvisible()) {
         GlStateManager.pushMatrix();
         if (entity.isSneaking()) {
            GlStateManager.translate(0.0F, 0.2F, 0.0F);
         }

         GlStateManager.rotate(headPitch * 0.017453292F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(netHeadYaw * 0.017453292F, 0.0F, 1.0F, 0.0F);
         this.modelRenderer.postRender(0.0625F);
         modelCatEars.render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, partialTicks);
         GlStateManager.popMatrix();
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
