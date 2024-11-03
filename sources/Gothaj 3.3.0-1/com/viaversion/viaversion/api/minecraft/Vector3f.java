package com.viaversion.viaversion.api.minecraft;

public final class Vector3f {
   private final float x;
   private final float y;
   private final float z;

   public Vector3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public float x() {
      return this.x;
   }

   public float y() {
      return this.y;
   }

   public float z() {
      return this.z;
   }
}
