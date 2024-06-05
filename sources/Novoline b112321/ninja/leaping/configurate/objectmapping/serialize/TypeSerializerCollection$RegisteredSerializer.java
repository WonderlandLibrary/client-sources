package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.util.function.Predicate;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$1;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$SuperTypePredicate;

final class TypeSerializerCollection$RegisteredSerializer {
   private final Predicate predicate;
   private final TypeSerializer serializer;

   private TypeSerializerCollection$RegisteredSerializer(Predicate var1, TypeSerializer var2) {
      this.predicate = var1;
      this.serializer = var2;
   }

   private TypeSerializerCollection$RegisteredSerializer(TypeToken var1, TypeSerializer var2) {
      this((Predicate)(new TypeSerializerCollection$SuperTypePredicate(var1)), var2);
   }

   TypeSerializerCollection$RegisteredSerializer(TypeToken var1, TypeSerializer var2, TypeSerializerCollection$1 var3) {
      this(var1, var2);
   }

   TypeSerializerCollection$RegisteredSerializer(Predicate var1, TypeSerializer var2, TypeSerializerCollection$1 var3) {
      this(var1, var2);
   }

   static Predicate access$300(TypeSerializerCollection$RegisteredSerializer var0) {
      return var0.predicate;
   }

   static TypeSerializer access$400(TypeSerializerCollection$RegisteredSerializer var0) {
      return var0.serializer;
   }
}
