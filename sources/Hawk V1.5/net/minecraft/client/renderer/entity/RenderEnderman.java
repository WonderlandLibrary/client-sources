package net.minecraft.client.renderer.entity;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class RenderEnderman extends RenderLiving {
   private Random rnd = new Random();
   private static final String __OBFID = "CL_00000989";
   private static final ResourceLocation endermanTextures = new ResourceLocation("textures/entity/enderman/enderman.png");
   private ModelEnderman endermanModel;

   public void doRender(EntityEnderman var1, double var2, double var4, double var6, float var8, float var9) {
      this.endermanModel.isCarrying = var1.func_175489_ck().getBlock().getMaterial() != Material.air;
      this.endermanModel.isAttacking = var1.isScreaming();
      if (var1.isScreaming()) {
         double var10 = 0.02D;
         var2 += this.rnd.nextGaussian() * var10;
         var6 += this.rnd.nextGaussian() * var10;
      }

      super.doRender((EntityLiving)var1, var2, var4, var6, var8, var9);
   }

   public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityEnderman)var1, var2, var4, var6, var8, var9);
   }

   public RenderEnderman(RenderManager var1) {
      super(var1, new ModelEnderman(0.0F), 0.5F);
      this.endermanModel = (ModelEnderman)super.mainModel;
      this.addLayer(new LayerEndermanEyes(this));
      this.addLayer(new LayerHeldBlock(this));
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityEnderman)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180573_a((EntityEnderman)var1);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityEnderman)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation func_180573_a(EntityEnderman var1) {
      return endermanTextures;
   }
}
