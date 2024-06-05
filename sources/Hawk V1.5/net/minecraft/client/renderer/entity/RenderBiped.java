package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped extends RenderLiving {
   private static final ResourceLocation field_177118_j = new ResourceLocation("textures/entity/steve.png");
   private static final String __OBFID = "CL_00001001";
   protected ModelBiped modelBipedMain;
   protected float field_77070_b;

   public void func_82422_c() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityLiving)var1);
   }

   public RenderBiped(RenderManager var1, ModelBiped var2, float var3) {
      this(var1, var2, var3, 1.0F);
      this.addLayer(new LayerHeldItem(this));
   }

   public RenderBiped(RenderManager var1, ModelBiped var2, float var3, float var4) {
      super(var1, var2, var3);
      this.modelBipedMain = var2;
      this.field_77070_b = var4;
      this.addLayer(new LayerCustomHead(var2.bipedHead));
   }

   protected ResourceLocation getEntityTexture(EntityLiving var1) {
      return field_177118_j;
   }
}
