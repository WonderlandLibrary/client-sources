package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class vd extends Type implements TypeConverter {
   public vd() {
      super("Double", Double.class);
   }

   public Double a(ByteBuf var1) {
      return Double.valueOf(var1.readDouble());
   }

   public void a(ByteBuf var1, Double var2) {
      var1.writeDouble(var2.doubleValue());
   }

   public Double a(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Double.valueOf(((Number)var1).doubleValue()):(var1 instanceof Boolean?Double.valueOf(((Boolean)var1).booleanValue()?1.0D:0.0D):(Double)var1);
   }
}
