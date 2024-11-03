package xyz.cucumber.base.utils.math;

public class Vector3d {
   private final double x;
   private final double y;
   private final double z;

   public Vector3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vector3d add(double x, double y, double z) {
      return new Vector3d(this.x + x, this.y + y, this.z + z);
   }

   public Vector3d add(Vector3d vector) {
      return this.add(vector.x, vector.y, vector.z);
   }

   public Vector3d subtract(double x, double y, double z) {
      return this.add(-x, -y, -z);
   }

   public Vector3d subtract(Vector3d vector) {
      return this.add(-vector.x, -vector.y, -vector.z);
   }

   public double length() {
      return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public double getZ() {
      return this.z;
   }

   public Vector3d multiply(double v) {
      return new Vector3d(this.x * v, this.y * v, this.z * v);
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof Vector3d)) {
         return false;
      } else {
         Vector3d vector = (Vector3d)obj;
         return Math.floor(this.x) == Math.floor(vector.x) && Math.floor(this.y) == Math.floor(vector.y) && Math.floor(this.z) == Math.floor(vector.z);
      }
   }
}
