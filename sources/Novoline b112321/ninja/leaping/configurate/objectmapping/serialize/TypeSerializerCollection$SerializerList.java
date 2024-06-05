package ninja.leaping.configurate.objectmapping.serialize;

import com.google.common.reflect.TypeToken;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import net.Ea;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$1;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection$RegisteredSerializer;

final class TypeSerializerCollection$SerializerList extends CopyOnWriteArrayList implements Function {
   private TypeSerializerCollection$SerializerList() {
   }

   public TypeSerializer apply(TypeToken var1) {
      Ea.b();
      Iterator var3 = this.iterator();
      if(var3.hasNext()) {
         TypeSerializerCollection$RegisteredSerializer var4 = (TypeSerializerCollection$RegisteredSerializer)var3.next();
         TypeSerializerCollection$RegisteredSerializer var10000 = var4;

         try {
            if(TypeSerializerCollection$RegisteredSerializer.access$300(var10000).test(var1)) {
               return TypeSerializerCollection$RegisteredSerializer.access$400(var4);
            }
         } catch (Exception var6) {
            throw new RuntimeException(TypeSerializerCollection$RegisteredSerializer.access$400(var4).getClass().getCanonicalName(), var6);
         }
      }

      return null;
   }

   TypeSerializerCollection$SerializerList(TypeSerializerCollection$1 var1) {
      this();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
