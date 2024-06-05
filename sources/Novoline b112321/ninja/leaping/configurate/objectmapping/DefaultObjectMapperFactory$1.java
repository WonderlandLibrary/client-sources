package ninja.leaping.configurate.objectmapping;

import com.google.common.cache.CacheLoader;
import ninja.leaping.configurate.objectmapping.DefaultObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMapper;

class DefaultObjectMapperFactory$1 extends CacheLoader {
   final DefaultObjectMapperFactory this$0;

   DefaultObjectMapperFactory$1(DefaultObjectMapperFactory var1) {
      this.this$0 = var1;
   }

   public ObjectMapper load(Class var1) throws Exception {
      return new ObjectMapper(var1);
   }
}
