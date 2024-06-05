package net.minecraft.util;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.IStringSerializable;

public enum EnumFacing$Axis implements Predicate, IStringSerializable {
   X("X", 0, "x", EnumFacing$Plane.HORIZONTAL),
   Y("Y", 1, "y", EnumFacing$Plane.VERTICAL),
   Z("Z", 2, "z", EnumFacing$Plane.HORIZONTAL);

   private static final Map NAME_LOOKUP = Maps.newHashMap();
   private final String name;
   private final EnumFacing$Plane plane;
   private static final EnumFacing$Axis[] $VALUES = new EnumFacing$Axis[]{X, Y, Z};
   private static final String f = "CL_00002321";
   private static final EnumFacing$Axis[] $VALUES$ = new EnumFacing$Axis[]{X, Y, Z};

   private EnumFacing$Axis(String var3, int var4, String var5, EnumFacing$Plane var6) {
      this.name = var5;
      this.plane = var6;
   }

   public static EnumFacing$Axis a(String var0) {
      return null;
   }

   public String getName2() {
      return this.name;
   }

   public boolean isVertical() {
      return this.plane == EnumFacing$Plane.VERTICAL;
   }

   public boolean isHorizontal() {
      return this.plane == EnumFacing$Plane.HORIZONTAL;
   }

   public String toString() {
      return this.name;
   }

   public boolean apply(EnumFacing var1) {
      return var1.getAxis() == this;
   }

   public EnumFacing$Plane getPlane() {
      return this.plane;
   }

   public String getName() {
      return this.name;
   }

   public boolean apply(Object var1) {
      return this.apply((EnumFacing)var1);
   }

   static {
      for(EnumFacing$Axis var4 : values()) {
         NAME_LOOKUP.put(var4.getName2().toLowerCase(), var4);
      }

   }
}
