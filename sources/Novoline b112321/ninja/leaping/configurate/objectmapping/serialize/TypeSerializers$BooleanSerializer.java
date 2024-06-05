package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import net.X7;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.Types;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class TypeSerializers$BooleanSerializer implements TypeSerializer {
   private TypeSerializers$BooleanSerializer() {
   }

   public Boolean deserialize(TypeToken var1, ConfigurationNode var2) throws X7 {
      return Boolean.valueOf(var2.getBoolean());
   }

   public void serialize(TypeToken var1, Boolean var2, ConfigurationNode var3) {
      var3.setValue(Types.asBoolean(var2));
   }

   TypeSerializers$BooleanSerializer(TypeSerializers$1 var1) {
      this();
   }
}
