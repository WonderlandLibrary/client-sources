package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelGuardian;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class RenderGuardian extends RenderLiving {
   int field_177115_a;
   private static final String __OBFID = "CL_00002443";
   private static final ResourceLocation field_177116_j = new ResourceLocation("textures/entity/guardian_elder.png");
   private static final ResourceLocation field_177114_e = new ResourceLocation("textures/entity/guardian.png");
   private static final ResourceLocation field_177117_k = new ResourceLocation("textures/entity/guardian_beam.png");

   public boolean func_177104_a(EntityLiving var1, ICamera var2, double var3, double var5, double var7) {
      return this.func_177113_a((EntityGuardian)var1, var2, var3, var5, var7);
   }

   public void doRender(EntityLiving var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_177109_a((EntityGuardian)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_177111_a((EntityGuardian)var1);
   }

   private Vec3 func_177110_a(EntityLivingBase var1, double var2, float var4) {
      double var5 = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var4;
      double var7 = var2 + var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var4;
      double var9 = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var4;
      return new Vec3(var5, var7, var9);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_177109_a((EntityGuardian)var1, var2, var4, var6, var8, var9);
   }

   public boolean func_177071_a(Entity var1, ICamera var2, double var3, double var5, double var7) {
      return this.func_177113_a((EntityGuardian)var1, var2, var3, var5, var7);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_177109_a((EntityGuardian)var1, var2, var4, var6, var8, var9);
   }

   protected void func_177112_a(EntityGuardian var1, float var2) {
      if (var1.func_175461_cl()) {
         GlStateManager.scale(2.35F, 2.35F, 2.35F);
      }

   }

   public boolean func_177113_a(EntityGuardian var1, ICamera var2, double var3, double var5, double var7) {
      if (super.func_177104_a(var1, var2, var3, var5, var7)) {
         return true;
      } else {
         if (var1.func_175474_cn()) {
            EntityLivingBase var9 = var1.func_175466_co();
            if (var9 != null) {
               Vec3 var10 = this.func_177110_a(var9, (double)var9.height * 0.5D, 1.0F);
               Vec3 var11 = this.func_177110_a(var1, (double)var1.getEyeHeight(), 1.0F);
               if (var2.isBoundingBoxInFrustum(AxisAlignedBB.fromBounds(var11.xCoord, var11.yCoord, var11.zCoord, var10.xCoord, var10.yCoord, var10.zCoord))) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   protected ResourceLocation func_177111_a(EntityGuardian var1) {
      return var1.func_175461_cl() ? field_177116_j : field_177114_e;
   }

   public RenderGuardian(RenderManager var1) {
      super(var1, new ModelGuardian(), 0.5F);
      this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
      this.func_177112_a((EntityGuardian)var1, var2);
   }

   public void func_177109_a(EntityGuardian var1, double var2, double var4, double var6, float var8, float var9) {
      if (this.field_177115_a != ((ModelGuardian)this.mainModel).func_178706_a()) {
         this.mainModel = new ModelGuardian();
         this.field_177115_a = ((ModelGuardian)this.mainModel).func_178706_a();
      }

      super.doRender((EntityLiving)var1, var2, var4, var6, var8, var9);
      EntityLivingBase var10 = var1.func_175466_co();
      if (var10 != null) {
         float var11 = var1.func_175477_p(var9);
         Tessellator var12 = Tessellator.getInstance();
         WorldRenderer var13 = var12.getWorldRenderer();
         this.bindTexture(field_177117_k);
         GL11.glTexParameterf(3553, 10242, 10497.0F);
         GL11.glTexParameterf(3553, 10243, 10497.0F);
         GlStateManager.disableLighting();
         GlStateManager.disableCull();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         float var14 = 240.0F;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var14, var14);
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         float var15 = (float)var1.worldObj.getTotalWorldTime() + var9;
         float var16 = var15 * 0.5F % 1.0F;
         float var17 = var1.getEyeHeight();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)var2, (float)var4 + var17, (float)var6);
         Vec3 var18 = this.func_177110_a(var10, (double)var10.height * 0.5D, var9);
         Vec3 var19 = this.func_177110_a(var1, (double)var17, var9);
         Vec3 var20 = var18.subtract(var19);
         double var21 = var20.lengthVector() + 1.0D;
         var20 = var20.normalize();
         float var23 = (float)Math.acos(var20.yCoord);
         float var24 = (float)Math.atan2(var20.zCoord, var20.xCoord);
         GlStateManager.rotate((1.5707964F + -var24) * 57.295776F, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(var23 * 57.295776F, 1.0F, 0.0F, 0.0F);
         byte var25 = 1;
         double var26 = (double)var15 * 0.05D * (1.0D - (double)(var25 & 1) * 2.5D);
         var13.startDrawingQuads();
         float var28 = var11 * var11;
         var13.func_178961_b(64 + (int)(var28 * 240.0F), 32 + (int)(var28 * 192.0F), 128 - (int)(var28 * 64.0F), 255);
         double var29 = (double)var25 * 0.2D;
         double var31 = var29 * 1.41D;
         double var33 = 0.0D + Math.cos(var26 + 2.356194490192345D) * var31;
         double var35 = 0.0D + Math.sin(var26 + 2.356194490192345D) * var31;
         double var37 = 0.0D + Math.cos(var26 + 0.7853981633974483D) * var31;
         double var39 = 0.0D + Math.sin(var26 + 0.7853981633974483D) * var31;
         double var41 = 0.0D + Math.cos(var26 + 3.9269908169872414D) * var31;
         double var43 = 0.0D + Math.sin(var26 + 3.9269908169872414D) * var31;
         double var45 = 0.0D + Math.cos(var26 + 5.497787143782138D) * var31;
         double var47 = 0.0D + Math.sin(var26 + 5.497787143782138D) * var31;
         double var49 = 0.0D + Math.cos(var26 + 3.141592653589793D) * var29;
         double var51 = 0.0D + Math.sin(var26 + 3.141592653589793D) * var29;
         double var53 = 0.0D + Math.cos(var26 + 0.0D) * var29;
         double var55 = 0.0D + Math.sin(var26 + 0.0D) * var29;
         double var57 = 0.0D + Math.cos(var26 + 1.5707963267948966D) * var29;
         double var59 = 0.0D + Math.sin(var26 + 1.5707963267948966D) * var29;
         double var61 = 0.0D + Math.cos(var26 + 4.71238898038469D) * var29;
         double var63 = 0.0D + Math.sin(var26 + 4.71238898038469D) * var29;
         double var65 = 0.0D;
         double var67 = 0.4999D;
         double var69 = (double)(-1.0F + var16);
         double var71 = var21 * (0.5D / var29) + var69;
         var13.addVertexWithUV(var49, var21, var51, var67, var71);
         var13.addVertexWithUV(var49, 0.0D, var51, var67, var69);
         var13.addVertexWithUV(var53, 0.0D, var55, var65, var69);
         var13.addVertexWithUV(var53, var21, var55, var65, var71);
         var13.addVertexWithUV(var57, var21, var59, var67, var71);
         var13.addVertexWithUV(var57, 0.0D, var59, var67, var69);
         var13.addVertexWithUV(var61, 0.0D, var63, var65, var69);
         var13.addVertexWithUV(var61, var21, var63, var65, var71);
         double var73 = 0.0D;
         if (var1.ticksExisted % 2 == 0) {
            var73 = 0.5D;
         }

         var13.addVertexWithUV(var33, var21, var35, 0.5D, var73 + 0.5D);
         var13.addVertexWithUV(var37, var21, var39, 1.0D, var73 + 0.5D);
         var13.addVertexWithUV(var45, var21, var47, 1.0D, var73);
         var13.addVertexWithUV(var41, var21, var43, 0.5D, var73);
         var12.draw();
         GlStateManager.popMatrix();
      }

   }
}
