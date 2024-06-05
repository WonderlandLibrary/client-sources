package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render {
   private static final String __OBFID = "CL_00000996";
   private static final ResourceLocation field_110792_a = new ResourceLocation("textures/particle/particles.png");

   protected ResourceLocation getEntityTexture(EntityFishHook var1) {
      return field_110792_a;
   }

   public void func_180558_a(EntityFishHook var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
      GlStateManager.enableRescaleNormal();
      GlStateManager.scale(0.5F, 0.5F, 0.5F);
      this.bindEntityTexture(var1);
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      byte var12 = 1;
      byte var13 = 2;
      float var14 = (float)(var12 * 8) / 128.0F;
      float var15 = (float)(var12 * 8 + 8) / 128.0F;
      float var16 = (float)(var13 * 8) / 128.0F;
      float var17 = (float)(var13 * 8 + 8) / 128.0F;
      float var18 = 1.0F;
      float var19 = 0.5F;
      float var20 = 0.5F;
      GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      var11.startDrawingQuads();
      var11.func_178980_d(0.0F, 1.0F, 0.0F);
      var11.addVertexWithUV((double)(0.0F - var19), (double)(0.0F - var20), 0.0D, (double)var14, (double)var17);
      var11.addVertexWithUV((double)(var18 - var19), (double)(0.0F - var20), 0.0D, (double)var15, (double)var17);
      var11.addVertexWithUV((double)(var18 - var19), (double)(1.0F - var20), 0.0D, (double)var15, (double)var16);
      var11.addVertexWithUV((double)(0.0F - var19), (double)(1.0F - var20), 0.0D, (double)var14, (double)var16);
      var10.draw();
      GlStateManager.disableRescaleNormal();
      GlStateManager.popMatrix();
      if (var1.angler != null) {
         float var21 = var1.angler.getSwingProgress(var9);
         float var22 = MathHelper.sin(MathHelper.sqrt_float(var21) * 3.1415927F);
         Vec3 var23 = new Vec3(-0.36D, 0.03D, 0.35D);
         var23 = var23.rotatePitch(-(var1.angler.prevRotationPitch + (var1.angler.rotationPitch - var1.angler.prevRotationPitch) * var9) * 3.1415927F / 180.0F);
         var23 = var23.rotateYaw(-(var1.angler.prevRotationYaw + (var1.angler.rotationYaw - var1.angler.prevRotationYaw) * var9) * 3.1415927F / 180.0F);
         var23 = var23.rotateYaw(var22 * 0.5F);
         var23 = var23.rotatePitch(-var22 * 0.7F);
         double var24 = var1.angler.prevPosX + (var1.angler.posX - var1.angler.prevPosX) * (double)var9 + var23.xCoord;
         double var26 = var1.angler.prevPosY + (var1.angler.posY - var1.angler.prevPosY) * (double)var9 + var23.yCoord;
         double var28 = var1.angler.prevPosZ + (var1.angler.posZ - var1.angler.prevPosZ) * (double)var9 + var23.zCoord;
         double var30 = (double)var1.angler.getEyeHeight();
         if (this.renderManager.options != null && this.renderManager.options.thirdPersonView > 0 || var1.angler != Minecraft.getMinecraft().thePlayer) {
            float var32 = (var1.angler.prevRenderYawOffset + (var1.angler.renderYawOffset - var1.angler.prevRenderYawOffset) * var9) * 3.1415927F / 180.0F;
            double var33 = (double)MathHelper.sin(var32);
            double var35 = (double)MathHelper.cos(var32);
            double var37 = 0.35D;
            double var39 = 0.8D;
            var24 = var1.angler.prevPosX + (var1.angler.posX - var1.angler.prevPosX) * (double)var9 - var35 * 0.35D - var33 * 0.8D;
            var26 = var1.angler.prevPosY + var30 + (var1.angler.posY - var1.angler.prevPosY) * (double)var9 - 0.45D;
            var28 = var1.angler.prevPosZ + (var1.angler.posZ - var1.angler.prevPosZ) * (double)var9 - var33 * 0.35D + var35 * 0.8D;
            var30 = var1.angler.isSneaking() ? -0.1875D : 0.0D;
         }

         double var47 = var1.prevPosX + (var1.posX - var1.prevPosX) * (double)var9;
         double var34 = var1.prevPosY + (var1.posY - var1.prevPosY) * (double)var9 + 0.25D;
         double var36 = var1.prevPosZ + (var1.posZ - var1.prevPosZ) * (double)var9;
         double var38 = (double)((float)(var24 - var47));
         double var40 = (double)((float)(var26 - var34)) + var30;
         double var42 = (double)((float)(var28 - var36));
         GlStateManager.func_179090_x();
         GlStateManager.disableLighting();
         var11.startDrawing(3);
         var11.func_178991_c(0);
         byte var44 = 16;

         for(int var45 = 0; var45 <= var44; ++var45) {
            float var46 = (float)var45 / (float)var44;
            var11.addVertex(var2 + var38 * (double)var46, var4 + var40 * (double)(var46 * var46 + var46) * 0.5D + 0.25D, var6 + var42 * (double)var46);
         }

         var10.draw();
         GlStateManager.enableLighting();
         GlStateManager.func_179098_w();
         super.doRender(var1, var2, var4, var6, var8, var9);
      }

   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180558_a((EntityFishHook)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.getEntityTexture((EntityFishHook)var1);
   }

   public RenderFish(RenderManager var1) {
      super(var1);
   }
}
