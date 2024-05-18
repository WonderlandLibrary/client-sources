package rina.turok.bope.bopemod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class BopeUtilEntity {
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static Vec3d process_interpolated_pos(Entity entity, float ticks) {
      return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)).add(get_interpolated_amout(entity, ticks));
   }

   public static Vec3d get_interpolated_render_pos(Entity entity, float ticks) {
      return process_interpolated_pos(entity, ticks).subtract(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY, mc.getRenderManager().renderPosZ);
   }

   public static Vec3d process_interpolated_amount(Entity entity, double x, double y, double z) {
      return new Vec3d((entity.posX - entity.lastTickPosX) * x, (entity.posY - entity.lastTickPosY) * y, (entity.posZ - entity.lastTickPosZ) * z);
   }

   public static Vec3d get_interpolated_amout(Entity entity, float ticks) {
      return process_interpolated_amount(entity, (double)ticks, (double)ticks, (double)ticks);
   }

   public static Vec3d get_interpolated_entity(Entity entity, float ticks) {
      return (new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)).add(get_interpolated_amout(entity, ticks));
   }
}
