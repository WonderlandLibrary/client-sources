package net.minecraft.optifine;

import java.lang.reflect.Field;
import java.util.Map;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;

public class RenderPlayerOF extends RenderPlayer {
   public RenderPlayerOF(RenderManager renderManager, boolean slimArms) {
      super(renderManager, slimArms);
   }

   public void func_177093_a(EntityLivingBase entityLiving, float limbSwing, float limbSwingAmount, float partialTicks, float ticksExisted, float headYaw, float rotationPitch, float scale) {
      super.func_177093_a(entityLiving, limbSwing, limbSwingAmount, partialTicks, ticksExisted, headYaw, rotationPitch, scale);
      this.renderEquippedItems(entityLiving, scale, partialTicks);
   }

   protected void renderEquippedItems(EntityLivingBase entityLiving, float scale, float partialTicks) {
      if (entityLiving instanceof AbstractClientPlayer) {
         AbstractClientPlayer player = (AbstractClientPlayer)entityLiving;
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableRescaleNormal();
         ModelBiped modelBipedMain = (ModelBiped)this.mainModel;
         PlayerConfigurations.renderPlayerItems(modelBipedMain, player, scale, partialTicks);
      }

   }

   public static void register() {
      RenderManager rm = Config.getMinecraft().getRenderManager();
      Map mapRenderTypes = getMapRenderTypes(rm);
      if (mapRenderTypes == null) {
         Config.warn("RenderPlayerOF init() failed: RenderManager.MapRenderTypes not found");
      } else {
         mapRenderTypes.put("default", new RenderPlayerOF(rm, false));
         mapRenderTypes.put("slim", new RenderPlayerOF(rm, true));
      }

   }

   private static Map getMapRenderTypes(RenderManager rm) {
      try {
         Field[] e = Reflector.getFields(RenderManager.class, Map.class);
         Field[] var2 = e;
         int var3 = e.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            Map map = (Map)field.get(rm);
            if (map != null) {
               Object renderSteve = map.get("default");
               if (renderSteve instanceof RenderPlayer) {
                  return map;
               }
            }
         }

         return null;
      } catch (Exception var8) {
         Config.warn("Error getting RenderManager.mapRenderTypes");
         Config.warn(var8.getClass().getName() + ": " + var8.getMessage());
         return null;
      }
   }
}
