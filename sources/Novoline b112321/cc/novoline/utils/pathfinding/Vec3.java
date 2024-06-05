package cc.novoline.utils.pathfinding;

public class Vec3 {
   private double x;
   private double y;
   private double z;
   private static String b;

   public Vec3(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
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

   public void setX(double var1) {
      this.x = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public Vec3 addVector(double var1, double var3, double var5) {
      return new Vec3(this.x + var1, this.y + var3, this.z + var5);
   }

   public Vec3 floor() {
      return new Vec3(Math.floor(this.x), Math.floor(this.y), Math.floor(this.z));
   }

   public double squareDistanceTo(Vec3 var1) {
      return Math.pow(var1.x - this.x, 2.0D) + Math.pow(var1.y - this.y, 2.0D) + Math.pow(var1.z - this.z, 2.0D);
   }

   public Vec3 add(Vec3 var1) {
      return this.addVector(var1.getX(), var1.getY(), var1.getZ());
   }

   public net.minecraft.util.Vec3 mc() {
      return new net.minecraft.util.Vec3(this.x, this.y, this.z);
   }

   public String toString() {
      return "[" + this.x + ";" + this.y + ";" + this.z + "]";
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() != null) {
         b("kCiSec");
      }

   }
}
