package net;

import viaversion.viarewind.utils.math.AABB;
import viaversion.viarewind.utils.math.Ray3d;
import viaversion.viarewind.utils.math.RayTracing;
import viaversion.viarewind.utils.math.Vector3d;

public class aBm {
   public static Vector3d a(Ray3d var0, AABB var1, double var2) {
      return RayTracing.trace(var0, var1, var2);
   }
}
