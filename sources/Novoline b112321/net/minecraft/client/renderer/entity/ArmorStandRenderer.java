package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.ArmorStandRenderer$1;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRenderer extends RendererLivingEntity {
   public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation("textures/entity/armorstand/wood.png");

   public ArmorStandRenderer(RenderManager var1) {
      super(var1, new ModelArmorStand(), 0.0F);
      ArmorStandRenderer$1 var2 = new ArmorStandRenderer$1(this, this);
      this.addLayer(var2);
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
   }

   protected ResourceLocation getEntityTexture(EntityArmorStand var1) {
      return TEXTURE_ARMOR_STAND;
   }

   public ModelArmorStand getMainModel() {
      return (ModelArmorStand)super.getMainModel();
   }

   protected void rotateCorpse(EntityArmorStand var1, float var2, float var3, float var4) {
      GlStateManager.rotate(180.0F - var3, 0.0F, 1.0F, 0.0F);
   }

   protected boolean canRenderName(EntityArmorStand var1) {
      return var1.getAlwaysRenderNameTag();
   }
}
