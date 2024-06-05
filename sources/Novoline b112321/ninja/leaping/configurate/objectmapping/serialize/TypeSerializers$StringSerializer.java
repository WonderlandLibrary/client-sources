package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import net.X7;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class TypeSerializers$StringSerializer implements TypeSerializer {
   private TypeSerializers$StringSerializer() {
   }

   public String deserialize(TypeToken var1, ConfigurationNode var2) throws X7 {
      return var2.getString();
   }

   public void serialize(TypeToken var1, String var2, ConfigurationNode var3) {
      var3.setValue(var2);
   }

   TypeSerializers$StringSerializer(TypeSerializers$1 var1) {
      this();
   }
}
