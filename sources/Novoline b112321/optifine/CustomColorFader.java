package optifine;

import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import net.minecraft.util.Vec3;
import optifine.Config;
import optifine.MatchBlock;

public class CustomColorFader {
   private Vec3 color = null;
   private long timeUpdate = System.currentTimeMillis();

   public Vec3 getColor(double var1, double var3, double var5) {
      PacketRemapper[] var7 = MatchBlock.b();
      if(this.color == null) {
         this.color = new Vec3(var1, var3, var5);
         return this.color;
      } else {
         long var8 = System.currentTimeMillis();
         long var10 = var8 - this.timeUpdate;
         if(var10 == 0L) {
            return this.color;
         } else {
            this.timeUpdate = var8;
            if(Math.abs(var1 - this.color.xCoord) < 0.004D && Math.abs(var3 - this.color.yCoord) < 0.004D && Math.abs(var5 - this.color.zCoord) < 0.004D) {
               return this.color;
            } else {
               double var12 = (double)var10 * 0.001D;
               var12 = Config.a(var12, 0.0D, 1.0D);
               double var14 = var1 - this.color.xCoord;
               double var16 = var3 - this.color.yCoord;
               double var18 = var5 - this.color.zCoord;
               double var20 = this.color.xCoord + var14 * var12;
               double var22 = this.color.yCoord + var16 * var12;
               double var24 = this.color.zCoord + var18 * var12;
               this.color = new Vec3(var20, var22, var24);
               return this.color;
            }
         }
      }
   }
}
