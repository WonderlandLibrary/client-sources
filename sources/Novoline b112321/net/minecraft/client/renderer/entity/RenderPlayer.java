package net.minecraft.client.renderer.entity;

import net.a4V;
import net.asJ;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;

public class RenderPlayer extends RendererLivingEntity {
   private boolean smallArms;

   public RenderPlayer(RenderManager var1) {
      this(var1, false);
   }

   public RenderPlayer(RenderManager var1, boolean var2) {
      super(var1, new ModelPlayer(0.0F, var2), 0.5F);
      this.smallArms = var2;
      this.addLayer(new LayerBipedArmor(this));
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerArrow(this));
      this.addLayer(new LayerDeadmau5Head(this));
      this.addLayer(new LayerCape(this));
      this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
   }

   public ModelPlayer getMainModel() {
      return (ModelPlayer)super.getMainModel();
   }

   public void a(asJ var1, double var2, double var4, double var6, float var8, float var9) {
      if(!var1.isUser() || this.renderManager.livingPlayer == var1) {
         double var10 = var4;
         if(var1.isSneaking() && !(var1 instanceof EntityPlayerSP)) {
            var10 = var4 - 0.125D;
         }

         this.b(var1);
         super.doRender((EntityLivingBase)var1, var2, var10, var6, var8, var9);
      }

   }

   private void b(asJ var1) {
      ModelPlayer var2 = this.getMainModel();
      if(var1.isSpectator()) {
         var2.setInvisible(false);
         var2.bipedHead.showModel = true;
         var2.bipedHeadwear.showModel = true;
      } else {
         ItemStack var3 = var1.inventory.getCurrentItem();
         var2.setInvisible(true);
         var2.bipedHeadwear.showModel = var1.isWearing(EnumPlayerModelParts.HAT);
         var2.x.showModel = var1.isWearing(EnumPlayerModelParts.JACKET);
         var2.t.showModel = var1.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
         var2.u.showModel = var1.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
         var2.y.showModel = var1.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
         var2.v.showModel = var1.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
         var2.heldItemLeft = 0;
         var2.aimedBow = false;
         var2.isSneak = var1.isSneaking();
         var2.heldItemRight = 0;
      }

   }

   protected ResourceLocation d(asJ var1) {
      return var1.getLocationSkin();
   }

   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void a(asJ var1, float var2) {
      float var3 = 0.9375F;
      GlStateManager.scale(var3, var3, var3);
   }

   protected void a(asJ var1, double var2, double var4, double var6, String var8, float var9, double var10) {
      if(var10 < 100.0D) {
         Scoreboard var12 = var1.getWorldScoreboard();
         ScoreObjective var13 = var12.getObjectiveInDisplaySlot(2);
         Score var14 = var12.getValueFromObjective(var1.getName(), var13);
         this.renderLivingLabel(var1, var14.getScorePoints() + " " + var13.getDisplayName(), var2, var4, var6, 64);
         var4 += (double)((float)this.getFontRendererFromRenderManager().getHeight() * 1.15F * var9);
      }

      super.renderOffsetLivingLabel(var1, var2, var4, var6, var8, var9, var10);
   }

   public void a(asJ var1) {
      float var2 = 1.0F;
      GlStateManager.color(var2, var2, var2);
      ModelPlayer var3 = this.getMainModel();
      this.b(var1);
      var3.swingProgress = 0.0F;
      var3.isSneak = false;
      a4V.a(var3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.renderRightArm();
   }

   public void c(asJ var1) {
      float var2 = 1.0F;
      GlStateManager.color(var2, var2, var2);
      ModelPlayer var3 = this.getMainModel();
      this.b(var1);
      var3.isSneak = false;
      var3.swingProgress = 0.0F;
      a4V.a(var3, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.renderLeftArm();
   }

   protected void a(asJ var1, double var2, double var4, double var6) {
      if(var1.isEntityAlive() && var1.isPlayerSleeping()) {
         super.renderLivingAt(var1, var2 + (double)var1.renderOffsetX, var4 + (double)var1.renderOffsetY, var6 + (double)var1.renderOffsetZ);
      } else {
         super.renderLivingAt(var1, var2, var4, var6);
      }

   }

   protected void a(asJ var1, float var2, float var3, float var4) {
      if(var1.isEntityAlive() && var1.isPlayerSleeping()) {
         GlStateManager.rotate(var1.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.rotateCorpse(var1, var2, var3, var4);
      }

   }
}
