package net.minecraft.client.resources.model;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Matrix4f;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.model.IModelPart;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ITransformation;
import net.minecraftforge.client.model.TRSRTransformation;
import optifine.Reflector;

public enum ModelRotation implements IModelState, ITransformation {
   private static final ModelRotation[] ENUM$VALUES = new ModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};
   private static final Map field_177546_q = Maps.newHashMap();
   X0_Y270("X0_Y270", 3, 0, 270),
   X0_Y180("X0_Y180", 2, 0, 180),
   X180_Y180("X180_Y180", 10, 180, 180);

   private final Matrix4d field_177544_s;
   private static final String __OBFID = "CL_00002393";
   X270_Y270("X270_Y270", 15, 270, 270),
   X90_Y0("X90_Y0", 4, 90, 0),
   X90_Y180("X90_Y180", 6, 90, 180);

   private final int field_177545_r;
   X270_Y90("X270_Y90", 13, 270, 90);

   private final int field_177542_u;
   X180_Y90("X180_Y90", 9, 180, 90),
   X270_Y180("X270_Y180", 14, 270, 180),
   X270_Y0("X270_Y0", 12, 270, 0),
   X0_Y90("X0_Y90", 1, 0, 90),
   X180_Y0("X180_Y0", 8, 180, 0),
   X180_Y270("X180_Y270", 11, 180, 270),
   X0_Y0("X0_Y0", 0, 0, 0),
   X90_Y90("X90_Y90", 5, 90, 90);

   private final int field_177543_t;
   private static final ModelRotation[] $VALUES = new ModelRotation[]{X0_Y0, X0_Y90, X0_Y180, X0_Y270, X90_Y0, X90_Y90, X90_Y180, X90_Y270, X180_Y0, X180_Y90, X180_Y180, X180_Y270, X270_Y0, X270_Y90, X270_Y180, X270_Y270};
   X90_Y270("X90_Y270", 7, 90, 270);

   public TRSRTransformation apply(IModelPart var1) {
      return new TRSRTransformation(this.getMatrix());
   }

   public int rotate(EnumFacing var1, int var2) {
      return this.func_177520_a(var1, var2);
   }

   private ModelRotation(String var3, int var4, int var5, int var6) {
      this.field_177545_r = func_177521_b(var5, var6);
      this.field_177544_s = new Matrix4d();
      Matrix4d var7 = new Matrix4d();
      var7.setIdentity();
      var7.setRotation(new AxisAngle4d(1.0D, 0.0D, 0.0D, (double)((float)(-var5) * 0.017453292F)));
      this.field_177543_t = MathHelper.abs_int(var5 / 90);
      Matrix4d var8 = new Matrix4d();
      var8.setIdentity();
      var8.setRotation(new AxisAngle4d(0.0D, 1.0D, 0.0D, (double)((float)(-var6) * 0.017453292F)));
      this.field_177542_u = MathHelper.abs_int(var6 / 90);
      this.field_177544_s.mul(var8, var7);
   }

   public static ModelRotation func_177524_a(int var0, int var1) {
      return (ModelRotation)field_177546_q.get(func_177521_b(MathHelper.func_180184_b(var0, 360), MathHelper.func_180184_b(var1, 360)));
   }

   public int func_177520_a(EnumFacing var1, int var2) {
      int var3 = var2;
      if (var1.getAxis() == EnumFacing.Axis.X) {
         var3 = (var2 + this.field_177543_t) % 4;
      }

      EnumFacing var4 = var1;

      for(int var5 = 0; var5 < this.field_177543_t; ++var5) {
         var4 = var4.rotateAround(EnumFacing.Axis.X);
      }

      if (var4.getAxis() == EnumFacing.Axis.Y) {
         var3 = (var3 + this.field_177542_u) % 4;
      }

      return var3;
   }

   private static int func_177521_b(int var0, int var1) {
      return var0 * 360 + var1;
   }

   public Matrix4d func_177525_a() {
      return this.field_177544_s;
   }

   public EnumFacing func_177523_a(EnumFacing var1) {
      EnumFacing var2 = var1;

      int var3;
      for(var3 = 0; var3 < this.field_177543_t; ++var3) {
         var2 = var2.rotateAround(EnumFacing.Axis.X);
      }

      if (var2.getAxis() != EnumFacing.Axis.Y) {
         for(var3 = 0; var3 < this.field_177542_u; ++var3) {
            var2 = var2.rotateAround(EnumFacing.Axis.Y);
         }
      }

      return var2;
   }

   public Object apply(Object var1) {
      return this.apply((IModelPart)var1);
   }

   public Matrix4f getMatrix() {
      return Reflector.ForgeHooksClient_getMatrix.exists() ? (Matrix4f)Reflector.call(Reflector.ForgeHooksClient_getMatrix, this) : new Matrix4f(this.func_177525_a());
   }

   public EnumFacing rotate(EnumFacing var1) {
      return this.func_177523_a(var1);
   }

   static {
      ModelRotation[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         ModelRotation var3 = var0[var2];
         field_177546_q.put(var3.field_177545_r, var3);
      }

   }
}
