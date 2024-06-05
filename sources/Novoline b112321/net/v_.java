package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class v_ extends Type implements TypeConverter {
   public v_() {
      super("Integer", Integer.class);
   }

   public Integer a(ByteBuf var1) {
      return Integer.valueOf(var1.readInt());
   }

   public void a(ByteBuf var1, Integer var2) {
      var1.writeInt(var2.intValue());
   }

   public Integer a(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Integer.valueOf(((Number)var1).intValue()):(var1 instanceof Boolean?Integer.valueOf(((Boolean)var1).booleanValue()?1:0):(Integer)var1);
   }
}
