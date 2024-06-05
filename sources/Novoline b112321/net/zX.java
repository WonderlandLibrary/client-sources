package net;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityEnderman;

public class zX {
   public static IBlockState a(EntityEnderman var0) {
      return var0.getHeldBlockState();
   }

   public static boolean b(EntityEnderman var0) {
      return var0.isScreaming();
   }

   public static int a(EntityEnderman var0, float var1) {
      return var0.getBrightnessForRender(var1);
   }

   public static Random c(EntityEnderman var0) {
      return var0.getRNG();
   }

   public static void a(EntityEnderman var0, IBlockState var1) {
      var0.setHeldBlockState(var1);
   }

   public static void a(EntityEnderman var0, boolean var1) {
      var0.setScreaming(var1);
   }

   public static IAttributeInstance a(EntityEnderman var0, IAttribute var1) {
      return var0.getEntityAttribute(var1);
   }

   public static void a(EntityEnderman var0, Entity var1, float var2, float var3) {
      var0.faceEntity(var1, var2, var3);
   }

   public static void a(EntityEnderman var0, String var1, float var2, float var3) {
      var0.playSound(var1, var2, var3);
   }

   public static boolean d(EntityEnderman var0) {
      return var0.isInvisible();
   }
}
