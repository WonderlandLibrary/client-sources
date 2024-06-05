package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerMooshroomMushroom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.util.ResourceLocation;

public class RenderMooshroom extends RenderLiving {
   private static final ResourceLocation mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
   private static final String __OBFID = "CL_00001016";

   protected ResourceLocation func_180582_a(EntityMooshroom var1) {
      return mooshroomTextures;
   }

   public RenderMooshroom(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
      this.addLayer(new LayerMooshroomMushroom(this));
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180582_a((EntityMooshroom)var1);
   }
}
