package cc.novoline.utils.pathfinding;

import cc.novoline.utils.pathfinding.Vec3;
import java.util.Objects;

public final class Point {
   private int posX;
   private int posY;
   private int posZ;

   public Point(int var1, int var2, int var3) {
      this.posX = var1;
      this.posY = var2;
      this.posZ = var3;
   }

   public int getPosX() {
      return this.posX;
   }

   public void setPosX(int var1) {
      this.posX = var1;
   }

   public int getPosY() {
      return this.posY;
   }

   public void setPosY(int var1) {
      this.posY = var1;
   }

   public int getPosZ() {
      return this.posZ;
   }

   public void setPosZ(int var1) {
      this.posZ = var1;
   }

   public boolean equals(Object var1) {
      String var2 = Vec3.b();
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof Point)) {
         return false;
      } else {
         Point var3 = (Point)var1;
         return this.posX == var3.posX && this.posY == var3.posY && this.posZ == var3.posZ;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{Integer.valueOf(this.posX), Integer.valueOf(this.posY), Integer.valueOf(this.posZ)});
   }

   public String toString() {
      return "Point{posX=" + this.posX + ", posY=" + this.posY + ", posZ=" + this.posZ + '}';
   }
}
