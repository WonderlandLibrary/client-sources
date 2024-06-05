package cc.novoline.modules.configurations.property.mapper;

import cc.novoline.modules.configurations.property.mapper.PropertyMapper;
import cc.novoline.modules.configurations.property.mapper.PropertyMapperFactory$1;
import cc.novoline.utils.java.Checks;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.ExecutionException;
import net.X9;
import ninja.leaping.configurate.objectmapping.ObjectMapperFactory;

public final class PropertyMapperFactory implements ObjectMapperFactory {
   private static final PropertyMapperFactory INSTANCE = new PropertyMapperFactory();
   private final LoadingCache mapperCache = CacheBuilder.newBuilder().weakKeys().maximumSize(500L).build(new PropertyMapperFactory$1(this));

   public static ObjectMapperFactory getInstance() {
      return INSTANCE;
   }

   public PropertyMapper getMapper(Class var1) throws X9 {
      PropertyMapper.b();
      Checks.notNull(var1, "type");

      try {
         return (PropertyMapper)this.mapperCache.get(var1);
      } catch (ExecutionException var4) {
         if(var4.getCause() instanceof X9) {
            throw (X9)var4.getCause();
         } else {
            throw new X9(var4);
         }
      }
   }

   public String toString() {
      return "PropertyMapperFactory{}";
   }

   private static ExecutionException a(ExecutionException var0) {
      return var0;
   }
}
