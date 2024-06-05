package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import optifine.MatchBlock;

public class MathUtils {
   public static int getAverage(int[] var0) {
      PacketRemapper[] var1 = MatchBlock.b();
      if(var0.length <= 0) {
         return 0;
      } else {
         int var2 = 0;
         int var3 = 0;
         if(var3 < var0.length) {
            int var4 = var0[var3];
            var2 += var4;
            ++var3;
         }

         var3 = var2 / var0.length;
         return var3;
      }
   }
}
