package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDragon extends RenderLiving {
   private static final String __OBFID = "CL_00000988";
   private static final ResourceLocation enderDragonExplodingTextures = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation enderDragonCrystalBeamTextures = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
   private static final ResourceLocation enderDragonTextures = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   protected ModelDragon modelDragon;

   public RenderDragon(RenderManager var1) {
      super(var1, new ModelDragon(0.0F), 0.5F);
      this.modelDragon = (ModelDragon)this.mainModel;
      this.addLayer(new LayerEnderDragonEyes(this));
      this.addLayer(new LayerEnderDragonDeath());
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityDragon)var1, var2, var4, var6, var8, var9);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityDragon)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityDragon)var1);
   }

   protected void renderModel(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.renderModel((EntityDragon)var1, var2, var3, var4, var5, var6, var7);
   }

   protected ResourceLocation getEntityTexture(EntityDragon var1) {
      return enderDragonTextures;
   }

   protected void func_180575_a(EntityDragon var1, float var2, float var3, float var4) {
      float var5 = (float)var1.getMovementOffsets(7, var4)[0];
      float var6 = (float)(var1.getMovementOffsets(5, var4)[1] - var1.getMovementOffsets(10, var4)[1]);
      GlStateManager.rotate(-var5, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, 1.0F);
      if (var1.deathTime > 0) {
         float var7 = ((float)var1.deathTime + var4 - 1.0F) / 20.0F * 1.6F;
         var7 = MathHelper.sqrt_float(var7);
         if (var7 > 1.0F) {
            var7 = 1.0F;
         }

         GlStateManager.rotate(var7 * this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
      }

   }

   public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityDragon)var1, var2, var4, var6, var8, var9);
   }

   protected void func_180574_a(EntityDragon var1, double var2, double var4, double var6, float var8) {
      float var9 = (float)var1.healingEnderCrystal.innerRotation + var8;
      float var10 = MathHelper.sin(var9 * 0.2F) / 2.0F + 0.5F;
      var10 = (var10 * var10 + var10) * 0.2F;
      float var11 = (float)(var1.healingEnderCrystal.posX - var1.posX - (var1.prevPosX - var1.posX) * (double)(1.0F - var8));
      float var12 = (float)((double)var10 + var1.healingEnderCrystal.posY - 1.0D - var1.posY - (var1.prevPosY - var1.posY) * (double)(1.0F - var8));
      float var13 = (float)(var1.healingEnderCrystal.posZ - var1.posZ - (var1.prevPosZ - var1.posZ) * (double)(1.0F - var8));
      float var14 = MathHelper.sqrt_float(var11 * var11 + var13 * var13);
      float var15 = MathHelper.sqrt_float(var11 * var11 + var12 * var12 + var13 * var13);
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var2, (float)var4 + 2.0F, (float)var6);
      GlStateManager.rotate((float)(-Math.atan2((double)var13, (double)var11)) * 180.0F / 3.1415927F - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((float)(-Math.atan2((double)var14, (double)var12)) * 180.0F / 3.1415927F - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator var16 = Tessellator.getInstance();
      WorldRenderer var17 = var16.getWorldRenderer();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      this.bindTexture(enderDragonCrystalBeamTextures);
      GlStateManager.shadeModel(7425);
      float var18 = 0.0F - ((float)var1.ticksExisted + var8) * 0.01F;
      float var19 = MathHelper.sqrt_float(var11 * var11 + var12 * var12 + var13 * var13) / 32.0F - ((float)var1.ticksExisted + var8) * 0.01F;
      var17.startDrawing(5);
      byte var20 = 8;

      for(int var21 = 0; var21 <= var20; ++var21) {
         float var22 = MathHelper.sin((float)(var21 % var20) * 3.1415927F * 2.0F / (float)var20) * 0.75F;
         float var23 = MathHelper.cos((float)(var21 % var20) * 3.1415927F * 2.0F / (float)var20) * 0.75F;
         float var24 = (float)(var21 % var20) * 1.0F / (float)var20;
         var17.func_178991_c(0);
         var17.addVertexWithUV((double)(var22 * 0.2F), (double)(var23 * 0.2F), 0.0D, (double)var24, (double)var19);
         var17.func_178991_c(16777215);
         var17.addVertexWithUV((double)var22, (double)var23, (double)var15, (double)var24, (double)var18);
      }

      var16.draw();
      GlStateManager.enableCull();
      GlStateManager.shadeModel(7424);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      this.func_180575_a((EntityDragon)var1, var2, var3, var4);
   }

   public void doRender(EntityDragon var1, double var2, double var4, double var6, float var8, float var9) {
      BossStatus.setBossStatus(var1, false);
      super.doRender((EntityLiving)var1, var2, var4, var6, var8, var9);
      if (var1.healingEnderCrystal != null) {
         this.func_180574_a(var1, var2, var4, var6, var9);
      }

   }

   protected void renderModel(EntityDragon var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (var1.deathTicks > 0) {
         float var8 = (float)var1.deathTicks / 200.0F;
         GlStateManager.depthFunc(515);
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, var8);
         this.bindTexture(enderDragonExplodingTextures);
         this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.depthFunc(514);
      }

      this.bindEntityTexture(var1);
      this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
      if (var1.hurtTime > 0) {
         GlStateManager.depthFunc(514);
         GlStateManager.func_179090_x();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.color(1.0F, 0.0F, 0.0F, 0.5F);
         this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
         GlStateManager.func_179098_w();
         GlStateManager.disableBlend();
         GlStateManager.depthFunc(515);
      }

   }
}
