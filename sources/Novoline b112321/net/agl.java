package net;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class agl {
   public static void a(EntityPlayer var0, boolean var1) {
      ActiveRenderInfo.updateRenderInfo(var0, var1);
   }

   public static Block a(World var0, Entity var1, float var2) {
      return ActiveRenderInfo.getBlockAtEntityViewpoint(var0, var1, var2);
   }

   public static Vec3 b() {
      return ActiveRenderInfo.getPosition();
   }

   public static float e() {
      return ActiveRenderInfo.getRotationX();
   }

   public static float a() {
      return ActiveRenderInfo.getRotationZ();
   }

   public static float d() {
      return ActiveRenderInfo.getRotationYZ();
   }

   public static float f() {
      return ActiveRenderInfo.getRotationXY();
   }

   public static float c() {
      return ActiveRenderInfo.getRotationXZ();
   }
}
