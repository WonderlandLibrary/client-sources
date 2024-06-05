package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.util.function.Predicate;
import net.Ea;
import net.X7;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class TypeSerializers$NumberSerializer implements TypeSerializer {
   private TypeSerializers$NumberSerializer() {
   }

   public static Predicate getPredicate() {
      return TypeSerializers$NumberSerializer::lambda$getPredicate$0;
   }

   public Number deserialize(TypeToken var1, ConfigurationNode var2) throws X7 {
      var1 = var1.wrap();
      Ea.b();
      Class var4 = var1.getRawType();
      return (Number)(Integer.class.equals(var4)?Integer.valueOf(var2.getInt()):(Long.class.equals(var4)?Long.valueOf(var2.getLong()):(Short.class.equals(var4)?Short.valueOf((short)var2.getInt()):(Byte.class.equals(var4)?Byte.valueOf((byte)var2.getInt()):(Float.class.equals(var4)?Float.valueOf(var2.getFloat()):(Double.class.equals(var4)?Double.valueOf(var2.getDouble()):null))))));
   }

   public void serialize(TypeToken var1, Number var2, ConfigurationNode var3) {
      var3.setValue(var2);
   }

   private static boolean lambda$getPredicate$0(TypeToken var0) {
      Ea.b();
      var0 = var0.wrap();
      Class var2 = var0.getRawType();
      return Integer.class.equals(var2) || Long.class.equals(var2) || Short.class.equals(var2) || Byte.class.equals(var2) || Float.class.equals(var2) || Double.class.equals(var2);
   }

   TypeSerializers$NumberSerializer(TypeSerializers$1 var1) {
      this();
   }

   private static X7 a(X7 var0) {
      return var0;
   }
}
