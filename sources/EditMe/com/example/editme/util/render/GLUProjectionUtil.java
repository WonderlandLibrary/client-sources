package com.example.editme.util.render;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Matrix4f;

public final class GLUProjectionUtil {
   private GLUProjectionUtil.Line bb;
   private GLUProjectionUtil.Line rb;
   private GLUProjectionUtil.Vector3D[] frustum;
   private FloatBuffer coords = BufferUtils.createFloatBuffer(3);
   private double widthScale;
   private GLUProjectionUtil.Vector3D[] invFrustum;
   private double tra;
   private double tla;
   private double displayHeight;
   private float fovY;
   private double heightScale;
   private GLUProjectionUtil.Vector3D lookVec;
   private FloatBuffer modelview;
   private float fovX;
   private IntBuffer viewport;
   private GLUProjectionUtil.Line lb;
   private FloatBuffer projection;
   private double displayWidth;
   private static GLUProjectionUtil instance;
   private GLUProjectionUtil.Vector3D viewVec;
   private double bra;
   private GLUProjectionUtil.Line tb;
   private GLUProjectionUtil.Vector3D frustumPos;
   private double bla;

   public GLUProjectionUtil.Vector3D getLookVector() {
      return this.lookVec;
   }

   public GLUProjectionUtil.Vector3D[] getFrustum() {
      return this.frustum;
   }

   public GLUProjectionUtil.Projection project(double var1, double var3, double var5, GLUProjectionUtil.ClampMode var7, boolean var8) {
      if (this.viewport != null && this.modelview != null && this.projection != null) {
         GLUProjectionUtil.Vector3D var9 = new GLUProjectionUtil.Vector3D(var1, var3, var5);
         boolean[] var10 = this.doFrustumCheck(this.frustum, this.frustumPos, var1, var3, var5);
         boolean var11 = var10[0] || var10[1] || var10[2] || var10[3];
         if (!var11) {
            if (GLU.gluProject((float)var1, (float)var3, (float)var5, this.modelview, this.projection, this.viewport, this.coords)) {
               double var24 = (double)this.coords.get(0) * this.widthScale;
               double var25 = (this.displayHeight - (double)this.coords.get(1)) * this.heightScale;
               return new GLUProjectionUtil.Projection(var24, var25, GLUProjectionUtil.Projection.Type.INSIDE);
            }

            return new GLUProjectionUtil.Projection(0.0D, 0.0D, GLUProjectionUtil.Projection.Type.FAIL);
         }

         boolean var12 = var9.sub(this.frustumPos).dot(this.viewVec) <= 0.0D;
         boolean[] var13 = this.doFrustumCheck(this.invFrustum, this.frustumPos, var1, var3, var5);
         boolean var14 = var13[0] || var13[1] || var13[2] || var13[3];
         double var15;
         double var17;
         if ((!var8 || var14) && (!var14 || var7 == GLUProjectionUtil.ClampMode.NONE)) {
            if (GLU.gluProject((float)var1, (float)var3, (float)var5, this.modelview, this.projection, this.viewport, this.coords)) {
               var15 = (double)this.coords.get(0) * this.widthScale;
               var17 = (this.displayHeight - (double)this.coords.get(1)) * this.heightScale;
               if (var12) {
                  var15 = this.displayWidth * this.widthScale - var15;
                  var17 = this.displayHeight * this.heightScale - var17;
               }

               return new GLUProjectionUtil.Projection(var15, var17, var14 ? GLUProjectionUtil.Projection.Type.OUTSIDE : GLUProjectionUtil.Projection.Type.INVERTED);
            } else {
               return new GLUProjectionUtil.Projection(0.0D, 0.0D, GLUProjectionUtil.Projection.Type.FAIL);
            }
         }

         if (var8 && !var14 || var7 == GLUProjectionUtil.ClampMode.DIRECT && var14) {
            var15 = 0.0D;
            var17 = 0.0D;
            if (GLU.gluProject((float)var1, (float)var3, (float)var5, this.modelview, this.projection, this.viewport, this.coords)) {
               if (var12) {
                  var15 = this.displayWidth * this.widthScale - (double)this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
                  var17 = this.displayHeight * this.heightScale - (this.displayHeight - (double)this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
               } else {
                  var15 = (double)this.coords.get(0) * this.widthScale - this.displayWidth * this.widthScale / 2.0D;
                  var17 = (this.displayHeight - (double)this.coords.get(1)) * this.heightScale - this.displayHeight * this.heightScale / 2.0D;
               }

               GLUProjectionUtil.Vector3D var19 = (new GLUProjectionUtil.Vector3D(var15, var17, 0.0D)).snormalize();
               var15 = var19.x;
               var17 = var19.y;
               GLUProjectionUtil.Line var20 = new GLUProjectionUtil.Line(this.displayWidth * this.widthScale / 2.0D, this.displayHeight * this.heightScale / 2.0D, 0.0D, var15, var17, 0.0D);
               double var21 = Math.toDegrees(Math.acos(var19.y / Math.sqrt(var19.x * var19.x + var19.y * var19.y)));
               if (var15 < 0.0D) {
                  var21 = 360.0D - var21;
               }

               new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
               GLUProjectionUtil.Vector3D var23;
               if (var21 >= this.bra && var21 < this.tra) {
                  var23 = this.rb.intersect(var20);
               } else if (var21 >= this.tra && var21 < this.tla) {
                  var23 = this.tb.intersect(var20);
               } else if (var21 >= this.tla && var21 < this.bla) {
                  var23 = this.lb.intersect(var20);
               } else {
                  var23 = this.bb.intersect(var20);
               }

               return new GLUProjectionUtil.Projection(var23.x, var23.y, var14 ? GLUProjectionUtil.Projection.Type.OUTSIDE : GLUProjectionUtil.Projection.Type.INVERTED);
            }

            return new GLUProjectionUtil.Projection(0.0D, 0.0D, GLUProjectionUtil.Projection.Type.FAIL);
         }

         if (var7 == GLUProjectionUtil.ClampMode.ORTHOGONAL && var14) {
            if (GLU.gluProject((float)var1, (float)var3, (float)var5, this.modelview, this.projection, this.viewport, this.coords)) {
               var15 = (double)this.coords.get(0) * this.widthScale;
               var17 = (this.displayHeight - (double)this.coords.get(1)) * this.heightScale;
               if (var12) {
                  var15 = this.displayWidth * this.widthScale - var15;
                  var17 = this.displayHeight * this.heightScale - var17;
               }

               if (var15 < 0.0D) {
                  var15 = 0.0D;
               } else if (var15 > this.displayWidth * this.widthScale) {
                  var15 = this.displayWidth * this.widthScale;
               }

               if (var17 < 0.0D) {
                  var17 = 0.0D;
               } else if (var17 > this.displayHeight * this.heightScale) {
                  var17 = this.displayHeight * this.heightScale;
               }

               return new GLUProjectionUtil.Projection(var15, var17, var14 ? GLUProjectionUtil.Projection.Type.OUTSIDE : GLUProjectionUtil.Projection.Type.INVERTED);
            }

            return new GLUProjectionUtil.Projection(0.0D, 0.0D, GLUProjectionUtil.Projection.Type.FAIL);
         }
      }

      return new GLUProjectionUtil.Projection(0.0D, 0.0D, GLUProjectionUtil.Projection.Type.FAIL);
   }

   public void updateMatrices(IntBuffer var1, FloatBuffer var2, FloatBuffer var3, double var4, double var6) {
      this.viewport = var1;
      this.modelview = var2;
      this.projection = var3;
      this.widthScale = var4;
      this.heightScale = var6;
      float var8 = (float)Math.toDegrees(Math.atan(1.0D / (double)this.projection.get(5)) * 2.0D);
      this.fovY = var8;
      this.displayWidth = (double)this.viewport.get(2);
      this.displayHeight = (double)this.viewport.get(3);
      this.fovX = (float)Math.toDegrees(2.0D * Math.atan(this.displayWidth / this.displayHeight * Math.tan(Math.toRadians((double)this.fovY) / 2.0D)));
      GLUProjectionUtil.Vector3D var9 = new GLUProjectionUtil.Vector3D((double)this.modelview.get(0), (double)this.modelview.get(1), (double)this.modelview.get(2));
      GLUProjectionUtil.Vector3D var10 = new GLUProjectionUtil.Vector3D((double)this.modelview.get(4), (double)this.modelview.get(5), (double)this.modelview.get(6));
      GLUProjectionUtil.Vector3D var11 = new GLUProjectionUtil.Vector3D((double)this.modelview.get(8), (double)this.modelview.get(9), (double)this.modelview.get(10));
      GLUProjectionUtil.Vector3D var12 = new GLUProjectionUtil.Vector3D(0.0D, 1.0D, 0.0D);
      GLUProjectionUtil.Vector3D var13 = new GLUProjectionUtil.Vector3D(1.0D, 0.0D, 0.0D);
      double var14 = Math.toDegrees(Math.atan2(var13.cross(var9).length(), var13.dot(var9))) + 180.0D;
      if (var11.x < 0.0D) {
         var14 = 360.0D - var14;
      }

      double var16 = 0.0D;
      if ((-var11.y <= 0.0D || var14 < 90.0D || var14 >= 270.0D) && (var11.y <= 0.0D || var14 >= 90.0D && var14 < 270.0D)) {
         var16 = -Math.toDegrees(Math.atan2(var12.cross(var10).length(), var12.dot(var10)));
      } else {
         var16 = Math.toDegrees(Math.atan2(var12.cross(var10).length(), var12.dot(var10)));
      }

      this.lookVec = this.getRotationVector(var14, var16);
      Matrix4f var18 = new Matrix4f();
      var18.load(this.modelview.asReadOnlyBuffer());
      var18.invert();
      this.frustumPos = new GLUProjectionUtil.Vector3D((double)var18.m30, (double)var18.m31, (double)var18.m32);
      this.frustum = this.getFrustum(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z, var14, var16, (double)var8, 1.0D, this.displayWidth / this.displayHeight);
      this.invFrustum = this.getFrustum(this.frustumPos.x, this.frustumPos.y, this.frustumPos.z, var14 - 180.0D, -var16, (double)var8, 1.0D, this.displayWidth / this.displayHeight);
      this.viewVec = this.getRotationVector(var14, var16).normalized();
      this.bra = Math.toDegrees(Math.acos(this.displayHeight * var6 / Math.sqrt(this.displayWidth * var4 * this.displayWidth * var4 + this.displayHeight * var6 * this.displayHeight * var6)));
      this.bla = 360.0D - this.bra;
      this.tra = this.bla - 180.0D;
      this.tla = this.bra + 180.0D;
      this.rb = new GLUProjectionUtil.Line(this.displayWidth * this.widthScale, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
      this.tb = new GLUProjectionUtil.Line(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D);
      this.lb = new GLUProjectionUtil.Line(0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D);
      this.bb = new GLUProjectionUtil.Line(0.0D, this.displayHeight * this.heightScale, 0.0D, 1.0D, 0.0D, 0.0D);
   }

   public boolean crossPlane(GLUProjectionUtil.Vector3D[] var1, GLUProjectionUtil.Vector3D var2) {
      GLUProjectionUtil.Vector3D var3 = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
      GLUProjectionUtil.Vector3D var4 = var1[1].sub(var1[0]);
      GLUProjectionUtil.Vector3D var5 = var1[2].sub(var1[0]);
      GLUProjectionUtil.Vector3D var6 = var4.cross(var5).snormalize();
      double var7 = var3.sub(var6).dot(var1[2]);
      double var9 = var6.dot(var2) + var7;
      return var9 >= 0.0D;
   }

   public float getFovX() {
      return this.fovX;
   }

   public boolean[] doFrustumCheck(GLUProjectionUtil.Vector3D[] var1, GLUProjectionUtil.Vector3D var2, double var3, double var5, double var7) {
      GLUProjectionUtil.Vector3D var9 = new GLUProjectionUtil.Vector3D(var3, var5, var7);
      boolean var10 = this.crossPlane(new GLUProjectionUtil.Vector3D[]{var2, var1[3], var1[0]}, var9);
      boolean var11 = this.crossPlane(new GLUProjectionUtil.Vector3D[]{var2, var1[0], var1[1]}, var9);
      boolean var12 = this.crossPlane(new GLUProjectionUtil.Vector3D[]{var2, var1[1], var1[2]}, var9);
      boolean var13 = this.crossPlane(new GLUProjectionUtil.Vector3D[]{var2, var1[2], var1[3]}, var9);
      return new boolean[]{var10, var11, var12, var13};
   }

   public GLUProjectionUtil.Vector3D[] getFrustum(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15) {
      double var17 = 2.0D * Math.tan(Math.toRadians(var11 / 2.0D)) * var13;
      double var19 = var17 * var15;
      GLUProjectionUtil.Vector3D var21 = this.getRotationVector(var7, var9).snormalize();
      GLUProjectionUtil.Vector3D var22 = this.getRotationVector(var7, var9 - 90.0D).snormalize();
      GLUProjectionUtil.Vector3D var23 = this.getRotationVector(var7 + 90.0D, 0.0D).snormalize();
      GLUProjectionUtil.Vector3D var24 = new GLUProjectionUtil.Vector3D(var1, var3, var5);
      GLUProjectionUtil.Vector3D var25 = var21.add(var24);
      GLUProjectionUtil.Vector3D var26 = new GLUProjectionUtil.Vector3D(var25.x * var13, var25.y * var13, var25.z * var13);
      GLUProjectionUtil.Vector3D var27 = new GLUProjectionUtil.Vector3D(var26.x + var22.x * var17 / 2.0D - var23.x * var19 / 2.0D, var26.y + var22.y * var17 / 2.0D - var23.y * var19 / 2.0D, var26.z + var22.z * var17 / 2.0D - var23.z * var19 / 2.0D);
      GLUProjectionUtil.Vector3D var28 = new GLUProjectionUtil.Vector3D(var26.x - var22.x * var17 / 2.0D - var23.x * var19 / 2.0D, var26.y - var22.y * var17 / 2.0D - var23.y * var19 / 2.0D, var26.z - var22.z * var17 / 2.0D - var23.z * var19 / 2.0D);
      GLUProjectionUtil.Vector3D var29 = new GLUProjectionUtil.Vector3D(var26.x + var22.x * var17 / 2.0D + var23.x * var19 / 2.0D, var26.y + var22.y * var17 / 2.0D + var23.y * var19 / 2.0D, var26.z + var22.z * var17 / 2.0D + var23.z * var19 / 2.0D);
      GLUProjectionUtil.Vector3D var30 = new GLUProjectionUtil.Vector3D(var26.x - var22.x * var17 / 2.0D + var23.x * var19 / 2.0D, var26.y - var22.y * var17 / 2.0D + var23.y * var19 / 2.0D, var26.z - var22.z * var17 / 2.0D + var23.z * var19 / 2.0D);
      return new GLUProjectionUtil.Vector3D[]{var27, var28, var30, var29};
   }

   private GLUProjectionUtil() {
   }

   public GLUProjectionUtil.Vector3D getRotationVector(double var1, double var3) {
      double var5 = Math.cos(-var1 * 0.01745329238474369D - 3.141592653589793D);
      double var7 = Math.sin(-var1 * 0.01745329238474369D - 3.141592653589793D);
      double var9 = -Math.cos(-var3 * 0.01745329238474369D);
      double var11 = Math.sin(-var3 * 0.01745329238474369D);
      return new GLUProjectionUtil.Vector3D(var7 * var9, var11, var5 * var9);
   }

   public static GLUProjectionUtil getInstance() {
      if (instance == null) {
         instance = new GLUProjectionUtil();
      }

      return instance;
   }

   public float getFovY() {
      return this.fovY;
   }

   public static enum ClampMode {
      DIRECT;

      private static final GLUProjectionUtil.ClampMode[] $VALUES = new GLUProjectionUtil.ClampMode[]{ORTHOGONAL, DIRECT, NONE};
      NONE,
      ORTHOGONAL;
   }

   public static class Vector3D {
      public double z;
      public double x;
      public double y;

      public double dot(GLUProjectionUtil.Vector3D var1) {
         return this.x * var1.x + this.y * var1.y + this.z * var1.z;
      }

      public GLUProjectionUtil.Vector3D sadd(GLUProjectionUtil.Vector3D var1) {
         this.x += var1.x;
         this.y += var1.y;
         this.z += var1.z;
         return this;
      }

      public GLUProjectionUtil.Vector3D scross(GLUProjectionUtil.Vector3D var1) {
         this.x = this.y * var1.z - this.z * var1.y;
         this.y = this.z * var1.x - this.x * var1.z;
         this.z = this.x * var1.y - this.y * var1.x;
         return this;
      }

      public GLUProjectionUtil.Vector3D sub(double var1, double var3, double var5) {
         return new GLUProjectionUtil.Vector3D(this.x - var1, this.y - var3, this.z - var5);
      }

      public Vector3D(double var1, double var3, double var5) {
         this.x = var1;
         this.y = var3;
         this.z = var5;
      }

      public GLUProjectionUtil.Vector3D smul(double var1) {
         this.x *= var1;
         this.y *= var1;
         this.z *= var1;
         return this;
      }

      public String toString() {
         return String.valueOf((new StringBuilder()).append("(X: ").append(this.x).append(" Y: ").append(this.y).append(" Z: ").append(this.z).append(")"));
      }

      public GLUProjectionUtil.Vector3D add(double var1, double var3, double var5) {
         return new GLUProjectionUtil.Vector3D(this.x + var1, this.y + var3, this.z + var5);
      }

      public GLUProjectionUtil.Vector3D ssub(GLUProjectionUtil.Vector3D var1) {
         this.x -= var1.x;
         this.y -= var1.y;
         this.z -= var1.z;
         return this;
      }

      public GLUProjectionUtil.Vector3D sdiv(double var1) {
         this.x /= var1;
         this.y /= var1;
         this.z /= var1;
         return this;
      }

      public GLUProjectionUtil.Vector3D sadd(double var1, double var3, double var5) {
         this.x += var1;
         this.y += var3;
         this.z += var5;
         return this;
      }

      public GLUProjectionUtil.Vector3D normalized() {
         double var1 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
         return new GLUProjectionUtil.Vector3D(this.x / var1, this.y / var1, this.z / var1);
      }

      public GLUProjectionUtil.Vector3D div(double var1) {
         return new GLUProjectionUtil.Vector3D(this.x / var1, this.y / var1, this.z / var1);
      }

      public GLUProjectionUtil.Vector3D sub(GLUProjectionUtil.Vector3D var1) {
         return new GLUProjectionUtil.Vector3D(this.x - var1.x, this.y - var1.y, this.z - var1.z);
      }

      public double length() {
         return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
      }

      public GLUProjectionUtil.Vector3D add(GLUProjectionUtil.Vector3D var1) {
         return new GLUProjectionUtil.Vector3D(this.x + var1.x, this.y + var1.y, this.z + var1.z);
      }

      public GLUProjectionUtil.Vector3D mul(double var1) {
         return new GLUProjectionUtil.Vector3D(this.x * var1, this.y * var1, this.z * var1);
      }

      public GLUProjectionUtil.Vector3D ssub(double var1, double var3, double var5) {
         this.x -= var1;
         this.y -= var3;
         this.z -= var5;
         return this;
      }

      public GLUProjectionUtil.Vector3D snormalize() {
         double var1 = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
         this.x /= var1;
         this.y /= var1;
         this.z /= var1;
         return this;
      }

      public GLUProjectionUtil.Vector3D cross(GLUProjectionUtil.Vector3D var1) {
         return new GLUProjectionUtil.Vector3D(this.y * var1.z - this.z * var1.y, this.z * var1.x - this.x * var1.z, this.x * var1.y - this.y * var1.x);
      }
   }

   public static class Projection {
      private final double y;
      private final double x;
      private final GLUProjectionUtil.Projection.Type t;

      public Projection(double var1, double var3, GLUProjectionUtil.Projection.Type var5) {
         this.x = var1;
         this.y = var3;
         this.t = var5;
      }

      public boolean isType(GLUProjectionUtil.Projection.Type var1) {
         return this.t == var1;
      }

      public double getY() {
         return this.y;
      }

      public GLUProjectionUtil.Projection.Type getType() {
         return this.t;
      }

      public double getX() {
         return this.x;
      }

      public static enum Type {
         FAIL,
         OUTSIDE,
         INVERTED,
         INSIDE;

         private static final GLUProjectionUtil.Projection.Type[] $VALUES = new GLUProjectionUtil.Projection.Type[]{INSIDE, OUTSIDE, INVERTED, FAIL};
      }
   }

   public static class Line {
      public GLUProjectionUtil.Vector3D sourcePoint = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
      public GLUProjectionUtil.Vector3D direction = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);

      public Line(double var1, double var3, double var5, double var7, double var9, double var11) {
         this.sourcePoint.x = var1;
         this.sourcePoint.y = var3;
         this.sourcePoint.z = var5;
         this.direction.x = var7;
         this.direction.y = var9;
         this.direction.z = var11;
      }

      private GLUProjectionUtil.Vector3D intersectXZ(GLUProjectionUtil.Line var1) {
         double var2 = this.sourcePoint.x;
         double var4 = this.direction.x;
         double var6 = var1.sourcePoint.x;
         double var8 = var1.direction.x;
         double var10 = this.sourcePoint.z;
         double var12 = this.direction.z;
         double var14 = var1.sourcePoint.z;
         double var16 = var1.direction.z;
         double var18 = -(var2 * var16 - var6 * var16 - var8 * (var10 - var14));
         double var20 = var4 * var16 - var8 * var12;
         if (var20 == 0.0D) {
            return this.intersectYZ(var1);
         } else {
            double var22 = var18 / var20;
            GLUProjectionUtil.Vector3D var24 = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
            var24.x = this.sourcePoint.x + this.direction.x * var22;
            var24.y = this.sourcePoint.y + this.direction.y * var22;
            var24.z = this.sourcePoint.z + this.direction.z * var22;
            return var24;
         }
      }

      public GLUProjectionUtil.Vector3D intersectPlane(GLUProjectionUtil.Vector3D var1, GLUProjectionUtil.Vector3D var2) {
         GLUProjectionUtil.Vector3D var3 = new GLUProjectionUtil.Vector3D(this.sourcePoint.x, this.sourcePoint.y, this.sourcePoint.z);
         double var4 = var1.sub(this.sourcePoint).dot(var2) / this.direction.dot(var2);
         var3.sadd(this.direction.mul(var4));
         return this.direction.dot(var2) == 0.0D ? null : var3;
      }

      private GLUProjectionUtil.Vector3D intersectYZ(GLUProjectionUtil.Line var1) {
         double var2 = this.sourcePoint.y;
         double var4 = this.direction.y;
         double var6 = var1.sourcePoint.y;
         double var8 = var1.direction.y;
         double var10 = this.sourcePoint.z;
         double var12 = this.direction.z;
         double var14 = var1.sourcePoint.z;
         double var16 = var1.direction.z;
         double var18 = -(var2 * var16 - var6 * var16 - var8 * (var10 - var14));
         double var20 = var4 * var16 - var8 * var12;
         if (var20 == 0.0D) {
            return null;
         } else {
            double var22 = var18 / var20;
            GLUProjectionUtil.Vector3D var24 = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
            var24.x = this.sourcePoint.x + this.direction.x * var22;
            var24.y = this.sourcePoint.y + this.direction.y * var22;
            var24.z = this.sourcePoint.z + this.direction.z * var22;
            return var24;
         }
      }

      public GLUProjectionUtil.Vector3D intersect(GLUProjectionUtil.Line var1) {
         double var2 = this.sourcePoint.x;
         double var4 = this.direction.x;
         double var6 = var1.sourcePoint.x;
         double var8 = var1.direction.x;
         double var10 = this.sourcePoint.y;
         double var12 = this.direction.y;
         double var14 = var1.sourcePoint.y;
         double var16 = var1.direction.y;
         double var18 = -(var2 * var16 - var6 * var16 - var8 * (var10 - var14));
         double var20 = var4 * var16 - var8 * var12;
         if (var20 == 0.0D) {
            return this.intersectXZ(var1);
         } else {
            double var22 = var18 / var20;
            GLUProjectionUtil.Vector3D var24 = new GLUProjectionUtil.Vector3D(0.0D, 0.0D, 0.0D);
            var24.x = this.sourcePoint.x + this.direction.x * var22;
            var24.y = this.sourcePoint.y + this.direction.y * var22;
            var24.z = this.sourcePoint.z + this.direction.z * var22;
            return var24;
         }
      }
   }
}
