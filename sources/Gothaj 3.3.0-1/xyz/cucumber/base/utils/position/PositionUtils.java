package xyz.cucumber.base.utils.position;

public class PositionUtils {
   private double x;
   private double y;
   private double width;
   private double height;
   private float scale;

   public PositionUtils(double x, double y, double width, double height, float scale) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.scale = scale;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getX2() {
      return this.x + this.width;
   }

   public double getY2() {
      return this.y + this.height;
   }

   public double getWidth() {
      return this.width;
   }

   public void setWidth(double width) {
      this.width = width;
   }

   public double getHeight() {
      return this.height;
   }

   public void setHeight(double height) {
      this.height = height;
   }

   public float getScale() {
      return this.scale;
   }

   public void setScale(float scale) {
      this.scale = scale;
   }

   public boolean isInside(int mouseX, int mouseY) {
      return (double)mouseX > this.getX()
         && (double)mouseY > this.getY()
         && (double)mouseX < this.getX() + this.getWidth()
         && (double)mouseY < this.getY() + this.getHeight();
   }
}
