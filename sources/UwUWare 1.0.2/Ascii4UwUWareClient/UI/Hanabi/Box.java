package Ascii4UwUWareClient.UI.Hanabi;

public class Box {
   public double minX;
   public double minY;
   public double minZ;
   public double maxX;
   public double maxY;
   public double maxZ;

   public Box(double x, double y, double z, double x1, double y1, double z1) {
      this.minX = x;
      this.minY = y;
      this.minZ = z;
      this.maxX = x1;
      this.maxY = y1;
      this.maxZ = z1;
   }
}
