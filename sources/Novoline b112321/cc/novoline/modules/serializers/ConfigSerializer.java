package cc.novoline.modules.serializers;

import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.configurations.holder.CreatingModuleHolder;
import cc.novoline.modules.configurations.property.mapper.PropertyMapper;
import cc.novoline.modules.serializers.PropertySerializer;
import com.google.common.reflect.TypeToken;
import net.X9;
import net.axb;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public final class ConfigSerializer implements TypeSerializer {
   private final ModuleManager moduleManager;

   public ConfigSerializer(ModuleManager var1) {
      this.moduleManager = var1;
   }

   public void serialize(TypeToken var1, Object var2, ConfigurationNode var3) throws X9 {
      int[] var4 = PropertySerializer.b();
      ((PropertyMapper)var3.getOptions().getObjectMapperFactory().getMapper(var2.getClass())).a(var2).a(var3);
      var3.setValue((Object)null);
   }

   public Object deserialize(TypeToken var1, ConfigurationNode var2) throws X9 {
      PropertySerializer.b();
      PropertyMapper var4 = (PropertyMapper)var2.getOptions().getObjectMapperFactory().getMapper(var1.getRawType());
      if(!AbstractModule.class.isAssignableFrom(var4.getClazz())) {
         axb var5 = var4.b();
      }

      ConfigSerializer var10000 = this;

      CreatingModuleHolder var6;
      try {
         var6 = (CreatingModuleHolder)var10000.moduleManager.getModuleManager().getHolderByClass(var4.getClazz());
      } catch (Throwable var8) {
         throw new X9("No registered module is available for class " + var4.getClazz() + " but is required to construct new instances!", var8);
      }

      axb var9 = var4.a(var6.createNew());
      return var9.b(var2);
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
