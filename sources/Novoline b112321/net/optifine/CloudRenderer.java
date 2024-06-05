package net.optifine;

import net.acE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.optifine.MatchBlock;
import org.lwjgl.opengl.GL11;

public class CloudRenderer {
   private Minecraft mc;
   private boolean updated = false;
   private boolean renderFancy = false;
   int cloudTickCounter;
   float partialTicks;
   private int glListClouds = -1;
   private int cloudTickCounterUpdate = 0;
   private double cloudPlayerX = 0.0D;
   private double cloudPlayerY = 0.0D;
   private double cloudPlayerZ = 0.0D;

   public CloudRenderer(Minecraft var1) {
      this.mc = var1;
      this.glListClouds = GLAllocation.generateDisplayLists(1);
   }

   public void a(boolean var1, int var2, float var3) {
      acE[] var4 = MatchBlock.b();
      if(this.renderFancy != var1) {
         this.updated = false;
      }

      this.renderFancy = var1;
      this.cloudTickCounter = var2;
      this.partialTicks = var3;
   }

   public boolean shouldUpdateGlList() {
      acE[] var1 = MatchBlock.b();
      if(!this.updated) {
         return true;
      } else if(this.cloudTickCounter >= this.cloudTickCounterUpdate + 20) {
         return true;
      } else {
         Entity var2 = this.mc.getRenderViewEntity();
         boolean var3 = this.cloudPlayerY + (double)var2.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F);
         boolean var4 = var2.prevPosY + (double)var2.getEyeHeight() < 128.0D + (double)(this.mc.gameSettings.ofCloudsHeight * 128.0F);
         return var4 != var3;
      }
   }

   public void startUpdateGlList() {
      GL11.glNewList(this.glListClouds, 4864);
   }

   public void endUpdateGlList() {
      GL11.glEndList();
      this.cloudTickCounterUpdate = this.cloudTickCounter;
      this.cloudPlayerX = this.mc.getRenderViewEntity().prevPosX;
      this.cloudPlayerY = this.mc.getRenderViewEntity().prevPosY;
      this.cloudPlayerZ = this.mc.getRenderViewEntity().prevPosZ;
      this.updated = true;
      GlStateManager.resetColor();
   }

   public void renderGlList() {
      Entity var2 = this.mc.getRenderViewEntity();
      MatchBlock.b();
      double var3 = var2.prevPosX + (var2.posX - var2.prevPosX) * (double)this.partialTicks;
      double var5 = var2.prevPosY + (var2.posY - var2.prevPosY) * (double)this.partialTicks;
      double var7 = var2.prevPosZ + (var2.posZ - var2.prevPosZ) * (double)this.partialTicks;
      double var9 = (double)((float)(this.cloudTickCounter - this.cloudTickCounterUpdate) + this.partialTicks);
      float var11 = (float)(var3 - this.cloudPlayerX + var9 * 0.03D);
      float var12 = (float)(var5 - this.cloudPlayerY);
      float var13 = (float)(var7 - this.cloudPlayerZ);
      GlStateManager.pushMatrix();
      if(this.renderFancy) {
         GlStateManager.translate(-var11 / 12.0F, -var12, -var13 / 12.0F);
      }

      GlStateManager.translate(-var11, -var12, -var13);
      GlStateManager.callList(this.glListClouds);
      GlStateManager.popMatrix();
      GlStateManager.resetColor();
   }

   public void reset() {
      this.updated = false;
   }
}
