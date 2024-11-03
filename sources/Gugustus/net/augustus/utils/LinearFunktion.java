package net.augustus.utils;

public class LinearFunktion {
   double m;
   double b;

   public LinearFunktion(double[] point1, double[] point2) {
      double x1 = point1[0];
      double x2 = point2[0];
      double y1 = point1[0];
      double y2 = point2[0];
      this.b = (x2 * y1 + -y2 * x1) / (x2 - x1);
      this.m = (-this.b + y1) / x1;
   }

   public LinearFunktion(float[] point1, float[] point2) {
      double x1 = (double)point1[0];
      double x2 = (double)point2[0];
      double y1 = (double)point1[0];
      double y2 = (double)point2[0];
      this.b = (x2 * y1 + -y2 * x1) / (x2 - x1);
      this.m = (-this.b + y1) / x1;
   }

   public double getX(double y) {
      return (-this.b + y) / this.m;
   }

   public double getY(double x) {
      return this.m * x + this.b;
   }
}
