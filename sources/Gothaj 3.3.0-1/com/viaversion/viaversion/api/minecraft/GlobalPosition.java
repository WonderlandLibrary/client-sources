package com.viaversion.viaversion.api.minecraft;

public final class GlobalPosition extends Position {
   private final String dimension;

   public GlobalPosition(String dimension, int x, int y, int z) {
      super(x, y, z);
      this.dimension = dimension;
   }

   public String dimension() {
      return this.dimension;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GlobalPosition position = (GlobalPosition)o;
         if (this.x != position.x) {
            return false;
         } else if (this.y != position.y) {
            return false;
         } else {
            return this.z != position.z ? false : this.dimension.equals(position.dimension);
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.dimension.hashCode();
      result = 31 * result + this.x;
      result = 31 * result + this.y;
      return 31 * result + this.z;
   }

   @Override
   public String toString() {
      return "GlobalPosition{dimension='" + this.dimension + '\'' + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
   }
}
