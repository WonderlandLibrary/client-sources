package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.Ea;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class TypeSerializers$PatternSerializer implements TypeSerializer {
   private TypeSerializers$PatternSerializer() {
   }

   public Pattern deserialize(TypeToken var1, ConfigurationNode var2) throws X9 {
      ConfigurationNode var10000 = var2;

      try {
         return Pattern.compile(var10000.getString());
      } catch (PatternSyntaxException var4) {
         throw new X9(var4);
      }
   }

   public void serialize(TypeToken var1, Pattern var2, ConfigurationNode var3) {
      acE[] var4 = Ea.b();
      var3.setValue(var2 != null?var2.pattern():null);
   }

   TypeSerializers$PatternSerializer(TypeSerializers$1 var1) {
      this();
   }

   private static PatternSyntaxException a(PatternSyntaxException var0) {
      return var0;
   }
}
