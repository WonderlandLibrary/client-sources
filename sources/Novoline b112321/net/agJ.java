package net;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import net.Ea;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class agJ implements TypeSerializer {
   private agJ() {
   }

   public List a(TypeToken var1, ConfigurationNode var2) throws X9 {
      acE[] var3 = Ea.b();
      if(!(var1.getType() instanceof ParameterizedType)) {
         throw new X9("Raw types are not supported for collections");
      } else {
         TypeToken var4 = var1.resolveType(List.class.getTypeParameters()[0]);
         TypeSerializer var5 = var2.getOptions().f().a(var4);
         throw new X9("No applicable type serializer for type " + var4);
      }
   }

   public void a(TypeToken var1, List var2, ConfigurationNode var3) throws X9 {
      acE[] var4 = Ea.b();
      if(!(var1.getType() instanceof ParameterizedType)) {
         throw new X9("Raw types are not supported for collections");
      } else {
         var3.setValue((Object)null);
      }
   }

   agJ(TypeSerializers$1 var1) {
      this();
   }

   private static X9 a(X9 var0) {
      return var0;
   }
}
