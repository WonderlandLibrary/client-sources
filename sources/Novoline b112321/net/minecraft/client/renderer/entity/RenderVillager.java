package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

public class RenderVillager extends RenderLiving {
   private static final ResourceLocation villagerTextures = new ResourceLocation("textures/entity/villager/villager.png");
   private static final ResourceLocation farmerVillagerTextures = new ResourceLocation("textures/entity/villager/farmer.png");
   private static final ResourceLocation librarianVillagerTextures = new ResourceLocation("textures/entity/villager/librarian.png");
   private static final ResourceLocation priestVillagerTextures = new ResourceLocation("textures/entity/villager/priest.png");
   private static final ResourceLocation smithVillagerTextures = new ResourceLocation("textures/entity/villager/smith.png");
   private static final ResourceLocation butcherVillagerTextures = new ResourceLocation("textures/entity/villager/butcher.png");

   public RenderVillager(RenderManager var1) {
      super(var1, new ModelVillager(0.0F), 0.5F);
      this.addLayer(new LayerCustomHead(this.getMainModel().villagerHead));
   }

   public ModelVillager getMainModel() {
      return (ModelVillager)super.getMainModel();
   }

   protected ResourceLocation getEntityTexture(EntityVillager var1) {
      switch(var1.getProfession()) {
      case 0:
         return farmerVillagerTextures;
      case 1:
         return librarianVillagerTextures;
      case 2:
         return priestVillagerTextures;
      case 3:
         return smithVillagerTextures;
      case 4:
         return butcherVillagerTextures;
      default:
         return villagerTextures;
      }
   }

   protected void preRenderCallback(EntityVillager var1, float var2) {
      float var3 = 0.9375F;
      if(var1.getGrowingAge() < 0) {
         var3 = (float)((double)var3 * 0.5D);
         this.shadowSize = 0.25F;
      } else {
         this.shadowSize = 0.5F;
      }

      GlStateManager.scale(var3, var3, var3);
   }
}
