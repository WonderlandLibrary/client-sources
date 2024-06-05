package net.minecraft.client.renderer.entity;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;

public class RenderPlayer extends RendererLivingEntity {
   private boolean field_177140_a;
   private static final String __OBFID = "CL_00001020";

   public void func_180596_a(AbstractClientPlayer var1, double var2, double var4, double var6, float var8, float var9) {
      if (!var1.func_175144_cb() || this.renderManager.livingPlayer == var1) {
         double var10 = var4;
         if (var1.isSneaking() && !(var1 instanceof EntityPlayerSP)) {
            var10 = var4 - 0.125D;
         }

         this.func_177137_d(var1);
         super.doRender((EntityLivingBase)var1, var2, var10, var6, var8, var9);
      }

   }

   protected void renderLivingAt(EntityLivingBase var1, double var2, double var4, double var6) {
      this.renderLivingAt((AbstractClientPlayer)var1, var2, var4, var6);
   }

   public ModelBase getMainModel() {
      return this.func_177136_g();
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      this.func_180595_a((AbstractClientPlayer)var1, var2, var3, var4);
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180596_a((AbstractClientPlayer)var1, var2, var4, var6, var8, var9);
   }

   public void func_177139_c(AbstractClientPlayer var1) {
      float var2 = 1.0F;
      GlStateManager.color(var2, var2, var2);
      ModelPlayer var3 = this.func_177136_g();
      this.func_177137_d(var1);
      var3.isSneak = false;
      var3.swingProgress = 0.0F;
      var3.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.func_178726_b();
   }

   public void func_177138_b(AbstractClientPlayer var1) {
      float var2 = 1.0F;
      GlStateManager.color(var2, var2, var2);
      ModelPlayer var3 = this.func_177136_g();
      this.func_177137_d(var1);
      var3.swingProgress = 0.0F;
      var3.isSneak = false;
      var3.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, var1);
      var3.func_178725_a();
   }

   public RenderPlayer(RenderManager var1) {
      this(var1, false);
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.func_180596_a((AbstractClientPlayer)var1, var2, var4, var6, var8, var9);
   }

   private void func_177137_d(AbstractClientPlayer var1) {
      ModelPlayer var2 = this.func_177136_g();
      if (var1.func_175149_v()) {
         var2.func_178719_a(false);
         var2.bipedHead.showModel = true;
         var2.bipedHeadwear.showModel = true;
      } else {
         ItemStack var3 = var1.inventory.getCurrentItem();
         var2.func_178719_a(true);
         var2.bipedHeadwear.showModel = var1.func_175148_a(EnumPlayerModelParts.HAT);
         var2.field_178730_v.showModel = var1.func_175148_a(EnumPlayerModelParts.JACKET);
         var2.field_178733_c.showModel = var1.func_175148_a(EnumPlayerModelParts.LEFT_PANTS_LEG);
         var2.field_178731_d.showModel = var1.func_175148_a(EnumPlayerModelParts.RIGHT_PANTS_LEG);
         var2.field_178734_a.showModel = var1.func_175148_a(EnumPlayerModelParts.LEFT_SLEEVE);
         var2.field_178732_b.showModel = var1.func_175148_a(EnumPlayerModelParts.RIGHT_SLEEVE);
         var2.heldItemLeft = 0;
         var2.aimedBow = false;
         var2.isSneak = var1.isSneaking();
         if (var3 == null) {
            var2.heldItemRight = 0;
         } else {
            var2.heldItemRight = 1;
            if (var1.getItemInUseCount() > 0) {
               EnumAction var4 = var3.getItemUseAction();
               if (var4 == EnumAction.BLOCK) {
                  var2.heldItemRight = 3;
               } else if (var4 == EnumAction.BOW) {
                  var2.aimedBow = true;
               }
            }
         }
      }

   }

   protected void func_177069_a(Entity var1, double var2, double var4, double var6, String var8, float var9, double var10) {
      this.renderOffsetLivingLabel((AbstractClientPlayer)var1, var2, var4, var6, var8, var9, var10);
   }

   protected void renderOffsetLivingLabel(AbstractClientPlayer var1, double var2, double var4, double var6, String var8, float var9, double var10) {
      if (var10 < 100.0D) {
         Scoreboard var12 = var1.getWorldScoreboard();
         ScoreObjective var13 = var12.getObjectiveInDisplaySlot(2);
         if (var13 != null) {
            Score var14 = var12.getValueFromObjective(var1.getName(), var13);
            this.renderLivingLabel(var1, String.valueOf((new StringBuilder(String.valueOf(var14.getScorePoints()))).append(" ").append(var13.getDisplayName())), var2, var4, var6, 64);
            var4 += (double)((float)this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * var9);
         }
      }

      super.func_177069_a(var1, var2, var4, var6, var8, var9, var10);
   }

   protected ResourceLocation func_180594_a(AbstractClientPlayer var1) {
      return var1.getLocationSkin();
   }

   public ModelPlayer func_177136_g() {
      return (ModelPlayer)super.getMainModel();
   }

   public RenderPlayer(RenderManager var1, boolean var2) {
      super(var1, new ModelPlayer(0.0F, var2), 0.5F);
      this.field_177140_a = var2;
      this.addLayer(new LayerBipedArmor(this));
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerArrow(this));
      this.addLayer(new LayerDeadmau5Head(this));
      this.addLayer(new LayerCape(this));
      this.addLayer(new LayerCustomHead(this.func_177136_g().bipedHead));
   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
      this.preRenderCallback((AbstractClientPlayer)var1, var2);
   }

   protected void func_180595_a(AbstractClientPlayer var1, float var2, float var3, float var4) {
      if (var1.isEntityAlive() && var1.isPlayerSleeping()) {
         GlStateManager.rotate(var1.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.rotateCorpse(var1, var2, var3, var4);
      }

   }

   public void func_82422_c() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(AbstractClientPlayer var1, float var2) {
      float var3 = 0.9375F;
      GlStateManager.scale(var3, var3, var3);
   }

   protected void renderLivingAt(AbstractClientPlayer var1, double var2, double var4, double var6) {
      if (var1.isEntityAlive() && var1.isPlayerSleeping()) {
         super.renderLivingAt(var1, var2 + (double)var1.field_71079_bU, var4 + (double)var1.field_71082_cx, var6 + (double)var1.field_71089_bV);
      } else {
         super.renderLivingAt(var1, var2, var4, var6);
      }

   }

   protected ResourceLocation getEntityTexture(Entity var1) {
      return this.func_180594_a((AbstractClientPlayer)var1);
   }
}
