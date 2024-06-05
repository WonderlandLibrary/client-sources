package net;

import com.google.common.reflect.TypeToken;
import java.util.UUID;
import net.Ea;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class aej implements TypeSerializer {
   private aej() {
   }

   public UUID a(TypeToken var1, ConfigurationNode var2) throws X9 {
      ConfigurationNode var10000 = var2;

      try {
         return UUID.fromString(var10000.getString());
      } catch (IllegalArgumentException var4) {
         throw new X9("Value not a UUID", var4);
      }
   }

   public void a(TypeToken var1, UUID var2, ConfigurationNode var3) {
      acE[] var4 = Ea.b();
      var3.setValue(var2 != null?var2.toString():null);
   }

   aej(TypeSerializers$1 var1) {
      this();
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
