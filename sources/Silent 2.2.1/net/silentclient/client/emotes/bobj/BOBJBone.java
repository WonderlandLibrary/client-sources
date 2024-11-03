package net.silentclient.client.emotes.bobj;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BOBJBone {
   public int index;
   public String name;
   public String parent;
   public BOBJBone parentBone;
   public Vector3f head;
   public Vector3f tail;
   public float x;
   public float y;
   public float z;
   public float rotateX;
   public float rotateY;
   public float rotateZ;
   public float scaleX = 1.0F;
   public float scaleY = 1.0F;
   public float scaleZ = 1.0F;
   public Matrix4f mat = new Matrix4f();
   public Matrix4f boneMat;
   public Matrix4f invBoneMat = new Matrix4f();
   public Matrix4f relBoneMat = new Matrix4f();

   public BOBJBone(int i, String s, String s1, Vector3f vector3f, Matrix4f matrix4f) {
      this.index = i;
      this.name = s;
      this.parent = s1;
      this.boneMat = matrix4f;
      this.head = new Vector3f(matrix4f.m30(), matrix4f.m31(), matrix4f.m32());
      this.tail = vector3f;
      this.invBoneMat.set(matrix4f).invert();
   }

   public Matrix4f compute() {
      return this.compute(0.0F, 0.0F);
   }

   public Matrix4f compute(float f, float f1) {
      Matrix4f matrix4f = this.computeMatrix(new Matrix4f(), f, f1);
      this.mat.set(matrix4f);
      matrix4f.mul(this.invBoneMat);
      return matrix4f;
   }

   public Matrix4f computeMatrix(Matrix4f matrix4f, float f, float f1) {
      this.mat.set(this.relBoneMat);
      this.mat.translate(this.x, this.y, this.z);
      this.mat.scale(this.scaleX, this.scaleY, this.scaleZ);
      if (this.rotateZ != 0.0F) {
         this.mat.rotateZ(this.rotateZ);
      }

      float f2 = this.rotateY + f;
      float f3 = this.rotateX + f1;
      if (f2 != 0.0F) {
         this.mat.rotateY(f2);
      }

      if (f3 != 0.0F) {
         this.mat.rotateX(f3);
      }

      if (this.parentBone != null) {
         matrix4f = new Matrix4f(this.parentBone.mat);
      }

      return matrix4f.mul(this.mat);
   }

   public void reset() {
      this.x = this.y = this.z = 0.0F;
      this.rotateX = this.rotateY = this.rotateZ = 0.0F;
      this.scaleX = this.scaleY = this.scaleZ = 1.0F;
   }
}
