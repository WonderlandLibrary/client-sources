package net;

import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import org.lwjgl.util.vector.Matrix4f;

public class Zp {
   public static ModelRotation a(int var0, int var1) {
      return ModelRotation.getModelRotation(var0, var1);
   }

   public static EnumFacing a(ModelRotation var0, EnumFacing var1) {
      return var0.rotateFace(var1);
   }

   public static Matrix4f a(ModelRotation var0) {
      return var0.getMatrix4d();
   }
}
