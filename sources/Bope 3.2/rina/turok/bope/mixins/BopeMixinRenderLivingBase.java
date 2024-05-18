package rina.turok.bope.mixins;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.hacks.render.BopeEntityESP;
import rina.turok.bope.bopemod.hacks.render.BopePlayerESP;
import rina.turok.turok.draw.TurokGL;

@Mixin({RenderLivingBase.class})
public abstract class BopeMixinRenderLivingBase extends BopeMixinRender {
   public final Minecraft mc = Minecraft.getMinecraft();
   @Shadow
   protected ModelBase mainModel;

   @Inject(
      method = "doRender",
      at = @At("HEAD")
   )
   private void doRender(EntityLivingBase entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
      if (entity instanceof IMob && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPHostile").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1100000.0F);
            GlStateManager.popMatrix();
         }
      }

      if (entity instanceof EntityAnimal && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPAnimals").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1100000.0F);
            GlStateManager.popMatrix();
         }
      }

      if (entity instanceof EntityPlayer && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRange").get_value(1)) {
         BopePlayerESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0F, -1100000.0F);
            GlStateManager.popMatrix();
         }
      }

   }

   @Inject(
      method = "doRender",
      at = @At("RETURN")
   )
   private void doRenderlast(EntityLivingBase entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
      if (entity instanceof IMob && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPHostile").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
            GL11.glEnable(3553);
            GlStateManager.popMatrix();
         }
      }

      if (entity instanceof EntityAnimal && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPAnimals").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
            GL11.glEnable(3553);
            GlStateManager.popMatrix();
         }
      }

      if (entity instanceof EntityPlayer && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRange").get_value(1)) {
         BopePlayerESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
            GL11.glEnable(3553);
            GlStateManager.popMatrix();
         }
      }

   }

   /**
    * @author Made by Rina (I think, this guy do be skidding)
    * @reason LET ME BUILD
    */
   @Overwrite
   protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
      boolean flag = !entitylivingbaseIn.isInvisible();
      boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(this.mc.player);
      if (flag || flag1) {
         if (!this.bindEntityTexture(entitylivingbaseIn)) {
            return;
         }

         if (flag1) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.003921569F);
         }

         Color n;
         if (entitylivingbaseIn instanceof IMob && this.mc.player != null && this.mc.player.getDistance(entitylivingbaseIn) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entitylivingbaseIn) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPHostile").get_value(true) && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Outline")) {
            n = new Color(190, 190, 190);
            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderOne(1.5F);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderTwo();
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderThree();
            TurokGL.renderFour();
            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderFive();
            TurokGL.setColor(Color.WHITE);
         }

         if (entitylivingbaseIn instanceof EntityAnimal && this.mc.player != null && this.mc.player.getDistance(entitylivingbaseIn) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entitylivingbaseIn) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPAnimals").get_value(true) && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Outline")) {
            n = new Color(190, 190, 190);
            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderOne(1.5F);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderTwo();
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderThree();
            TurokGL.renderFour();
            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderFive();
            TurokGL.setColor(Color.WHITE);
         }

         if (entitylivingbaseIn instanceof EntityPlayer && this.mc.player != null && this.mc.player.getDistance(entitylivingbaseIn) > (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPDistanceRender").get_value(1) && this.mc.player.getDistance(entitylivingbaseIn) < (float)Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRange").get_value(1) && Bope.get_setting_manager().get_setting_with_tag("PlayerESP", "PlayerESPRenderEntity").in("Outline") && Bope.get_module_manager().get_module_with_tag("PlayerESP").is_active()) {
            if (Bope.get_friend_manager().is_friend(entitylivingbaseIn.getName())) {
               n = new Color(Bope.client_r, Bope.client_g, Bope.client_b);
            } else {
               n = new Color(190, 190, 190);
            }

            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderOne(1.5F);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderTwo();
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderThree();
            TurokGL.renderFour();
            TurokGL.setColor(n);
            this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
            TurokGL.renderFive();
            TurokGL.setColor(Color.WHITE);
         }

         this.mainModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
         if (flag1) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
         }
      }

   }
}
