package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityFootStepFX extends EntityFX {
   private int footstepAge;
   private int footstepMaxAge;
   private static final ResourceLocation field_110126_a = new ResourceLocation("textures/particle/footprint.png");
   private static final String __OBFID = "CL_00000908";
   private TextureManager currentFootSteps;

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = ((float)this.footstepAge + var3) / (float)this.footstepMaxAge;
      var9 *= var9;
      float var10 = 2.0F - var9 * 2.0F;
      if (var10 > 1.0F) {
         var10 = 1.0F;
      }

      var10 *= 0.2F;
      GlStateManager.disableLighting();
      float var11 = 0.125F;
      float var12 = (float)(this.posX - interpPosX);
      float var13 = (float)(this.posY - interpPosY);
      float var14 = (float)(this.posZ - interpPosZ);
      float var15 = this.worldObj.getLightBrightness(new BlockPos(this));
      this.currentFootSteps.bindTexture(field_110126_a);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      var1.startDrawingQuads();
      var1.func_178960_a(var15, var15, var15, var10);
      var1.addVertexWithUV((double)(var12 - var11), (double)var13, (double)(var14 + var11), 0.0D, 1.0D);
      var1.addVertexWithUV((double)(var12 + var11), (double)var13, (double)(var14 + var11), 1.0D, 1.0D);
      var1.addVertexWithUV((double)(var12 + var11), (double)var13, (double)(var14 - var11), 1.0D, 0.0D);
      var1.addVertexWithUV((double)(var12 - var11), (double)var13, (double)(var14 - var11), 0.0D, 0.0D);
      Tessellator.getInstance().draw();
      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
   }

   public int getFXLayer() {
      return 3;
   }

   protected EntityFootStepFX(TextureManager var1, World var2, double var3, double var5, double var7) {
      super(var2, var3, var5, var7, 0.0D, 0.0D, 0.0D);
      this.currentFootSteps = var1;
      this.motionX = this.motionY = this.motionZ = 0.0D;
      this.footstepMaxAge = 200;
   }

   public void onUpdate() {
      ++this.footstepAge;
      if (this.footstepAge == this.footstepMaxAge) {
         this.setDead();
      }

   }

   public static class Factory implements IParticleFactory {
      private static final String __OBFID = "CL_00002601";

      public EntityFX func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), var2, var3, var5, var7);
      }
   }
}
