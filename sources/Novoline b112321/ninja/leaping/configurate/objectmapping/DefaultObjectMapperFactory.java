package ninja.leaping.configurate.objectmapping;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import net.X9;
import ninja.leaping.configurate.objectmapping.DefaultObjectMapperFactory$1;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMapperFactory;

public class DefaultObjectMapperFactory implements ObjectMapperFactory {
   private static final ObjectMapperFactory INSTANCE = new DefaultObjectMapperFactory();
   private final LoadingCache mapperCache = CacheBuilder.newBuilder().weakKeys().maximumSize(500L).build(new DefaultObjectMapperFactory$1(this));

   public static ObjectMapperFactory getInstance() {
      return INSTANCE;
   }

   public ObjectMapper getMapper(Class var1) throws X9 {
      X9.b();
      Objects.requireNonNull(var1, "type");

      try {
         return (ObjectMapper)this.mapperCache.get(var1);
      } catch (ExecutionException var4) {
         if(var4.getCause() instanceof X9) {
            throw (X9)var4.getCause();
         } else {
            throw new X9(var4);
         }
      }
   }

   public String toString() {
      return "DefaultObjectMapperFactory{}";
   }

   private static ExecutionException a(ExecutionException var0) {
      return var0;
   }
}
