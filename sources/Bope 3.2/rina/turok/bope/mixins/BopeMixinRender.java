package rina.turok.bope.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rina.turok.bope.Bope;
import rina.turok.bope.bopemod.hacks.render.BopeEntityESP;

@Mixin({Render.class})
public abstract class BopeMixinRender {
   public final Minecraft mc = Minecraft.getMinecraft();

   @Shadow
   protected abstract boolean bindEntityTexture(Entity var1);

   @Inject(
      method = "doRender",
      at = @At("HEAD")
   )
   private void doRender(Entity entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
      if (entity instanceof EntityEnderCrystal && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPCrystal").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
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
   private void doRenderlast(Entity entity, double x, double y, double z, float yaw, float partial_ticks, CallbackInfo callback) {
      if (entity instanceof EntityEnderCrystal && this.mc.player != null && this.mc.player.getDistance(entity) > (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPDistanceRender").get_value(1) && this.mc.player.getDistance(entity) < (float)Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRange").get_value(1) && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_module_manager().get_module_with_tag("EntityESP").is_active() && Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPCrystal").get_value(true)) {
         BopeEntityESP.distance_player = this.mc.player.getDistance(entity);
         if (Bope.get_setting_manager().get_setting_with_tag("EntityESP", "EntityESPRenderEntity").in("Chams")) {
            GlStateManager.pushMatrix();
            GL11.glDisable(32823);
            GL11.glPolygonOffset(1.0F, 1100000.0F);
            GL11.glEnable(3553);
            GlStateManager.popMatrix();
         }
      }

   }
}
