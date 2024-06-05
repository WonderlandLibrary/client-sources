package cc.novoline.modules.configurations.property.mapper;

import cc.novoline.modules.configurations.property.Property;
import cc.novoline.modules.configurations.property.mapper.PropertyMapper;
import cc.novoline.modules.configurations.property.object.BooleanProperty;
import cc.novoline.modules.configurations.property.object.DoubleProperty;
import cc.novoline.modules.configurations.property.object.FloatProperty;
import cc.novoline.modules.configurations.property.object.IntProperty;
import cc.novoline.modules.configurations.property.object.ListProperty;
import cc.novoline.modules.configurations.property.object.LongProperty;
import cc.novoline.modules.configurations.property.object.StringProperty;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.lang.reflect.Field;
import java.util.Objects;
import net.X9;
import net.aBM;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public final class PropertyFieldData extends aBM {
   public PropertyFieldData(Field var1, String var2) {
      super(var1, var2);
   }

   protected void deserializeAndSet(Object param1, ConfigurationNode param2, TypeSerializer param3) throws X9 {
      // $FF: Couldn't be decompiled
   }

   private Property getPropertyFromContainer(Field var1, Object var2) throws IllegalAccessException {
      return (Property)var1.get(var2);
   }

   private void set(Object var1, Field var2, Object var3) throws X9, IllegalAccessException {
      Property var4 = this.getPropertyFromContainer(var2, var1);
      Object var5 = ((Property)var3).get();
      PropertyFieldData var10000 = this;
      Property var10001 = var4;
      Object var10002 = var5;

      try {
         var10000.updatePropertyValue(var10001, var10002);
      } catch (ClassCastException var9) {
         var10000 = this;
         var10001 = var4;
         var10002 = var5;

         try {
            var10000.castAndUpdatePropertyValue(var10001, Objects.requireNonNull(var10002));
         } catch (Throwable var8) {
            throw new X9("Cannot update value", var8);
         }
      } catch (Throwable var10) {
         throw new X9("Cannot update value", var10);
      }

   }

   private void updatePropertyValue(Property var1, Object var2) {
      var1.set(var2);
   }

   private void castAndUpdatePropertyValue(Property var1, Object var2) throws Throwable {
      String[] var3 = PropertyMapper.b();
      if(var1 instanceof IntProperty) {
         this.updatePropertyValue(var1, Integer.valueOf(((Number)var2).intValue()));
      }

      if(var1 instanceof DoubleProperty) {
         this.updatePropertyValue(var1, Double.valueOf(((Number)var2).doubleValue()));
      }

      if(var1 instanceof FloatProperty) {
         this.updatePropertyValue(var1, Float.valueOf(((Number)var2).floatValue()));
      }

      if(var1 instanceof LongProperty) {
         this.updatePropertyValue(var1, Long.valueOf(((Number)var2).longValue()));
      }

      if(var1 instanceof StringProperty) {
         this.updatePropertyValue(var1, var2.toString());
      }

      if(var1 instanceof ListProperty) {
         this.updatePropertyValue(var1, new ObjectArrayList(new Object[]{var2}));
      }

      if(var1 instanceof BooleanProperty) {
         if(var2 instanceof Number) {
            this.updatePropertyValue(var1, Boolean.valueOf(((Number)var2).shortValue() > 0));
         } else {
            throw new X9();
         }
      }
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
