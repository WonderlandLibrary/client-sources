package net;

import com.google.common.reflect.TypeToken;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Map.Entry;
import net.Ea;
import net.X9;
import net.acE;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.SimpleConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers$1;

class a1b implements TypeSerializer {
   private a1b() {
   }

   public Map a(TypeToken var1, ConfigurationNode var2) throws X9 {
      Ea.b();
      Object2ObjectLinkedOpenHashMap var4 = new Object2ObjectLinkedOpenHashMap();
      if(var2.hasMapChildren()) {
         if(!(var1.getType() instanceof ParameterizedType)) {
            throw new X9("Raw types are not supported for collections");
         }

         TypeToken var5 = var1.resolveType(Map.class.getTypeParameters()[0]);
         TypeToken var6 = var1.resolveType(Map.class.getTypeParameters()[1]);
         TypeSerializer var7 = var2.getOptions().f().a(var5);
         TypeSerializer var8 = var2.getOptions().f().a(var6);
         if(var7 == null) {
            throw new X9("No type serializer available for type " + var5);
         }

         if(var8 == null) {
            throw new X9("No type serializer available for type " + var6);
         }

         for(Entry var10 : var2.getChildrenMap().entrySet()) {
            Object var11 = var7.deserialize(var5, SimpleConfigurationNode.root().setValue(var10.getKey()));
            Object var12 = var8.deserialize(var6, (ConfigurationNode)var10.getValue());
            if(var11 != null) {
               if(var12 == null) {
                  ;
               }

               var4.put(var11, var12);
               break;
            }
         }
      }

      return var4;
   }

   public void a(TypeToken var1, Map var2, ConfigurationNode var3) throws X9 {
      acE[] var4 = Ea.b();
      if(!(var1.getType() instanceof ParameterizedType)) {
         throw new X9("Raw types are not supported for collections");
      } else {
         var3.setValue((Object)null);
      }
   }

   a1b(TypeSerializers$1 var1) {
      this();
   }

   private static X9 a(X9 var0) {
      return var0;
   }
}
