package net;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import io.netty.buffer.ByteBuf;

public class wD extends Type implements TypeConverter {
   public wD() {
      super("Void", Void.class);
   }

   public Void a(ByteBuf var1) {
      return null;
   }

   public void a(ByteBuf var1, Void var2) {
   }

   public Void a(Object var1) {
      return null;
   }
}
