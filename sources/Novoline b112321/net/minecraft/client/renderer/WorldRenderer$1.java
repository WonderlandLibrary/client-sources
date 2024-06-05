package net.minecraft.client.renderer;

import com.google.common.primitives.Floats;
import java.util.Comparator;
import net.minecraft.client.renderer.WorldRenderer;

class WorldRenderer$1 implements Comparator {
   final float[] field_181659_a;
   final WorldRenderer field_181660_b;

   WorldRenderer$1(WorldRenderer var1, float[] var2) {
      this.field_181660_b = var1;
      this.field_181659_a = var2;
   }

   public int compare(Integer var1, Integer var2) {
      return Floats.compare(this.field_181659_a[var2.intValue()], this.field_181659_a[var1.intValue()]);
   }

   public int compare(Object var1, Object var2) {
      return this.compare((Integer)var1, (Integer)var2);
   }
}
