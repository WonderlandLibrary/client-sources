package net.minecraft.client.resources.model;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Axis;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ITransformation;
import net.optifine.Reflector;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public enum ModelRotation implements IModelState, ITransformation {
   X0_Y0("X0_Y0", 0, 0, 0),
   X0_Y90("X0_Y90", 1, 0, 90),
   X0_Y180("X0_Y180", 2, 0, 180),
   X0_Y270("X0_Y270", 3, 0, 270),
   X90_Y0("X90_Y0", 4, 90, 0),
   X90_Y90("X90_Y90", 5, 90, 90),
   X90_Y180("X90_Y180", 6, 90, 180),
   X90_Y270("X90_Y270", 7, 90, 270),
   X180_Y0("X180_Y0", 8, 180, 0),
   X180_Y90("X180_Y90", 9, 180, 90),
   X180_Y180("X180_Y180", 10, 180, 180),
   X180_Y270("X180_Y270", 11, 180, 270),
   X270_Y0("X270_Y0", 12, 270, 0),
   X270_Y90("X270_Y90", 13, 270, 90),
   X270_Y180("X270_Y180", 14, 270, 180),
   X270_Y270("X270_Y270", 15, 270, 270);

   private static final Map mapRotations = Maps.newHashMap();
   private final int combinedXY;
   private final Matrix4f matrix4d;
   private final int quartersX;
   private final int quartersY;
   private static final ModelRotation[] $VALUES = new ModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};
   private static final String f = "CL_00002393";
   private static final ModelRotation[] $VALUES$ = new ModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};

   private static int combineXY(int var0, int var1) {
      return var0 * 360 + var1;
   }

   private ModelRotation(String var3, int var4, int var5, int var6) {
      this.combinedXY = combineXY(var5, var6);
      this.matrix4d = new Matrix4f();
      Matrix4f var7 = new Matrix4f();
      var7.setIdentity();
      Matrix4f.rotate((float)(-var5) * 0.017453292F, new Vector3f(1.0F, 0.0F, 0.0F), var7, var7);
      this.quartersX = MathHelper.abs_int(var5 / 90);
      Matrix4f var8 = new Matrix4f();
      var8.setIdentity();
      Matrix4f.rotate((float)(-var6) * 0.017453292F, new Vector3f(0.0F, 1.0F, 0.0F), var8, var8);
      this.quartersY = MathHelper.abs_int(var6 / 90);
      Matrix4f.mul(var8, var7, this.matrix4d);
   }

   public Matrix4f getMatrix4d() {
      return this.matrix4d;
   }

   public EnumFacing rotateFace(EnumFacing var1) {
      EnumFacing var2 = var1;

      for(int var3 = 0; var3 < this.quartersX; ++var3) {
         var2 = var2.rotateAround(EnumFacing$Axis.X);
      }

      if(var2.getAxis() != EnumFacing$Axis.Y) {
         for(int var4 = 0; var4 < this.quartersY; ++var4) {
            var2 = var2.rotateAround(EnumFacing$Axis.Y);
         }
      }

      return var2;
   }

   public int rotateVertex(EnumFacing var1, int var2) {
      int var3 = var2;
      if(var1.getAxis() == EnumFacing$Axis.X) {
         var3 = (var2 + this.quartersX) % 4;
      }

      EnumFacing var4 = var1;

      for(int var5 = 0; var5 < this.quartersX; ++var5) {
         var4 = var4.rotateAround(EnumFacing$Axis.X);
      }

      if(var4.getAxis() == EnumFacing$Axis.Y) {
         var3 = (var3 + this.quartersY) % 4;
      }

      return var3;
   }

   public static ModelRotation getModelRotation(int var0, int var1) {
      return (ModelRotation)mapRotations.get(Integer.valueOf(combineXY(MathHelper.normalizeAngle(var0, 360), MathHelper.normalizeAngle(var1, 360))));
   }

   public Optional apply(Optional var1) {
      return (Optional)Reflector.f(Reflector.ax, new Object[]{this.getMatrix(), var1});
   }

   public javax.vecmath.Matrix4f getMatrix() {
      return Reflector.x.d()?(javax.vecmath.Matrix4f)Reflector.f(Reflector.x, new Object[]{this}):new javax.vecmath.Matrix4f();
   }

   public EnumFacing rotate(EnumFacing var1) {
      return this.rotateFace(var1);
   }

   public int rotate(EnumFacing var1, int var2) {
      return this.rotateVertex(var1, var2);
   }

   static {
      for(ModelRotation var11 : values()) {
         mapRotations.put(Integer.valueOf(var11.combinedXY), var11);
      }

   }
}
