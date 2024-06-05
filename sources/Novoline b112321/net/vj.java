package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;
import net.Gh;

public class vj extends Type implements TypeConverter {
   public vj() {
      super("Long", Long.class);
   }

   public Long a(ByteBuf var1) {
      return Long.valueOf(var1.readLong());
   }

   public void a(ByteBuf var1, Long var2) {
      var1.writeLong(var2.longValue());
   }

   public Long a(Object var1) {
      String var2 = Gh.b();
      return var1 instanceof Number?Long.valueOf(((Number)var1).longValue()):(var1 instanceof Boolean?Long.valueOf(((Boolean)var1).booleanValue()?1L:0L):(Long)var1);
   }
}
