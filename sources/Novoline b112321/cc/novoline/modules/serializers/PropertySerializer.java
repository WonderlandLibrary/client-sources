package cc.novoline.modules.serializers;

import cc.novoline.modules.configurations.property.Property;
import cc.novoline.modules.configurations.property.object.PropertyFactory;
import cc.novoline.modules.serializers.PropertySerializer$1;
import cc.novoline.modules.serializers.PropertySerializer$2;
import com.google.common.reflect.TypeToken;
import java.util.Collection;
import net.X9;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public final class PropertySerializer implements TypeSerializer {
   private static int[] b;

   public void serialize(TypeToken var1, Property var2, ConfigurationNode var3) {
      int[] var4 = b();
      if(var2.get() == null) {
         var3.setValue((Object)null);
      } else {
         var3.setValue(var2.get());
      }
   }

   public Property deserialize(TypeToken var1, ConfigurationNode var2) throws X9 {
      int[] var3 = b();
      switch(PropertySerializer$2.$SwitchMap$ninja$leaping$configurate$ValueType[var2.getValueType().ordinal()]) {
      case 1:
         return PropertyFactory.createList((Collection)var2.getList((TypeToken)(new PropertySerializer$1(this))));
      case 2:
         Object var4 = var2.getValue();
         if(var4 instanceof CharSequence) {
            return PropertyFactory.createString(var4.toString());
         } else if(var4 instanceof Integer) {
            return PropertyFactory.createInt(Integer.valueOf(((Integer)var4).intValue()));
         } else if(var4 instanceof Double) {
            return PropertyFactory.createDouble(Double.valueOf(((Double)var4).doubleValue()));
         } else if(var4 instanceof Boolean) {
            return PropertyFactory.createBoolean(Boolean.valueOf(((Boolean)var4).booleanValue()));
         } else if(var4 instanceof Float) {
            return PropertyFactory.createFloat(Float.valueOf(((Float)var4).floatValue()));
         } else if(var4 instanceof Long) {
            return PropertyFactory.createLong(Long.valueOf(((Long)var4).longValue()));
         }
      default:
         return null;
      case 3:
         throw new X9("Unable to deserialize map property");
      }
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   private static X9 a(X9 var0) {
      return var0;
   }

   static {
      if(b() != null) {
         b(new int[3]);
      }

   }
}
